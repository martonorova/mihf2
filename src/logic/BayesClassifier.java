package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BayesClassifier {

    public static final int LEARNING_DOCUMENT_NUM = 80000;
    public static final int TEST_DOCUMENT_NUM = 20000;

    private ArrayList<Document> documents = new ArrayList<>();
    private HashMap<Long, Word> vocabulary = new HashMap<>();
    private HashMap<Classification, Integer> docsCountPerClassification = new HashMap<>();
    private HashMap<Classification, Integer> wordsCountPerClassification = new HashMap<>();
    private HashMap<Classification, Integer> emptyDocCountPerClassification = new HashMap<>();

    public void readLearningDocuments(Scanner scanner) {

        int lineCount = 1;
        while (scanner.hasNext() && lineCount <= 2 * LEARNING_DOCUMENT_NUM) {


            if (lineCount <= LEARNING_DOCUMENT_NUM) {

                Document document = new Document();

                String line = scanner.nextLine();

                if (line.length() > 0) {

                    String[] wordCodes = line.split("\t");

                    for (String wordCodeString : wordCodes) {


                        Word word = new Word(Long.parseLong(wordCodeString));

                        vocabulary.put(word.getCode(), word);
                        document.addWord(word.getCode());
                    }
                }

                documents.add(document);

            } else {

                String line = scanner.nextLine();

                //int classCode = scanner.nextInt();
                long classCode = Long.parseLong(line);

                Classification classification = classCode == 1 ? Classification.POSITIVE : Classification.NEGATIVE;

                docsCountPerClassification.putIfAbsent(classification, 0);
                docsCountPerClassification.put(classification, docsCountPerClassification.get(classification) + 1);

                documents.get(lineCount - LEARNING_DOCUMENT_NUM - 1).setClassification(classification);

            }

            lineCount++;
        }



    }

    public void analyzeDocuments() {

        documents.forEach(document -> {

            Classification classification = document.getClassification();


            if (document.getWordFreqsInDocument().isEmpty()) {

                emptyDocCountPerClassification.putIfAbsent(classification, 0);
                emptyDocCountPerClassification.put(classification, emptyDocCountPerClassification.get(classification) + 1);
            }

            document.getWordFreqsInDocument().forEach((wordCode, freq) -> {

                vocabulary.get(wordCode).incrementFreqInClass(classification, freq);
                wordsCountPerClassification.putIfAbsent(classification, 0);
                wordsCountPerClassification.put(classification, wordsCountPerClassification.get(classification) + freq);

            });

        });

        vocabulary.forEach((wordCode, word) -> {
            wordsCountPerClassification.forEach((classification, wordCountInClass) -> {
                word.setProbGivenClass(classification, wordCountInClass, vocabulary.size());
            });
        });
        //System.out.println("END OF ANALYZE");
    }

    public void test(Scanner scanner) {

        int testDocCount = 1;

        while (scanner.hasNext() && testDocCount <= TEST_DOCUMENT_NUM) {
            String line = scanner.nextLine();

            Classification estimatedClass = Classification.NEGATIVE;

            //ures uzenet
            if (line.length() == 0) {

                int countOfMostEmptyDocInAClass = Integer.MIN_VALUE;


                for (Classification classification : emptyDocCountPerClassification.keySet()) {
                    int actualCount = emptyDocCountPerClassification.get(classification);
                    if (actualCount > countOfMostEmptyDocInAClass) {
                        countOfMostEmptyDocInAClass = actualCount;
                        estimatedClass = classification;
                    }
                }

            // van szo az uzenetben
            } else {

                estimatedClass = estimateClass(line.split("\t"));
            }

            if (testDocCount == TEST_DOCUMENT_NUM) {
                System.out.print(estimatedClass.ordinal());
            } else {
                System.out.println(estimatedClass.ordinal());
            }


            ++testDocCount;
        }
    }

    private Classification estimateClass(String[] wordCodeStrings) {
        HashMap<Classification, Double> probsByClass = new HashMap<>();

        for (Classification classification : Classification.values()) {
            double probOfClass = (double) docsCountPerClassification.get(classification) / (double) documents.size();
            double prob = 1;
            for (String wordCodeString : wordCodeStrings) {

                try {

                    prob *= vocabulary.get(Long.parseLong(wordCodeString)).getProbGivenClass(classification);

                } catch (NullPointerException ex) {

                    prob *= (double) 1 / (double) (wordsCountPerClassification.get(classification) + vocabulary.size());
                }

            }

            prob *= probOfClass;

            probsByClass.put(classification, prob);

        }

        Classification estimatedClass = Classification.NEGATIVE;
        double maxProb = 0;

        for (Classification classification : probsByClass.keySet()) {
            double actualProb = probsByClass.get(classification);

            if (actualProb > maxProb) {
                maxProb = actualProb;
                estimatedClass = classification;
            }
        }

        return estimatedClass;
    }

}
