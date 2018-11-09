package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BayesClassifier {

    public static final int LEARNING_DOCUMENT_NUM = 80000;

    private ArrayList<Document> documents = new ArrayList<>();
    private HashMap<Integer, Word> vocabulary = new HashMap<>();
    private HashMap<Classification, Integer> classificationFreqOfDocs = new HashMap<>();
    private HashMap<Classification, Integer> classificationFreqOfWords = new HashMap<>();
    private HashMap<Classification, Integer> emptyDocumentCountByClass = new HashMap<>();

    public void readLearningDocuments(Scanner scanner) {

        int lineCount = 1;
        while (scanner.hasNext() && lineCount <= 2 * LEARNING_DOCUMENT_NUM) {


            if (lineCount <= LEARNING_DOCUMENT_NUM) {

                Document document = new Document();

                String line = scanner.nextLine();

                if (line.length() > 0) {

                    String[] wordCodes = line.split(" ");

                    for (String wordCodeString : wordCodes) {

                        //int wordCode = Integer.parseInt(wordCodeString);
                        Word word = new Word(Integer.parseInt(wordCodeString));

                        vocabulary.put(word.getCode(), word);
                        document.addWord(word);
                    }
                }

                documents.add(document);

            } else {

                int classCode = scanner.nextInt();

                Classification classification = classCode == 1 ? Classification.POSITIVE : Classification.NEGATIVE;

                classificationFreqOfDocs.putIfAbsent(classification, 0);
                classificationFreqOfDocs.put(classification, classificationFreqOfDocs.get(classification) + 1);

                documents.get(lineCount - LEARNING_DOCUMENT_NUM - 1).setClassification(classification);

            }

            lineCount++;
        }



    }

    public void analyzeDocuments() {
        documents.forEach(document -> {

            Classification classification = document.getClassification();

            if (document.getWordFreqsInDocument().isEmpty()) {
                emptyDocumentCountByClass.putIfAbsent(classification, 0);
                emptyDocumentCountByClass.putIfAbsent(classification, emptyDocumentCountByClass.get(classification) + 1);
            }

            document.getWordFreqsInDocument().forEach((word, freq) -> {



                word.incrementFreqInClass(classification);
                classificationFreqOfWords.putIfAbsent(classification, 0);
                classificationFreqOfWords.put(classification, classificationFreqOfWords.get(classification) + 1);

            });

        });

        vocabulary.forEach((wordCode, word) -> {
            classificationFreqOfWords.forEach((classification, sumOfWordsInClass) -> {
                word.setProbInClass(classification, sumOfWordsInClass, vocabulary.size());
            });
        });
        //System.out.println("END OF ANALYZE");
    }

    public void test(Scanner scanner) {
        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            Classification estimatedClass = Classification.NEGATIVE;

            //ures uzenet
            if (line.length() == 0) {

                int numOfMostEmptyDocInAClass = Integer.MIN_VALUE;


                for (Classification classification : emptyDocumentCountByClass.keySet()) {
                    int actualCount = emptyDocumentCountByClass.get(classification);
                    if (actualCount > numOfMostEmptyDocInAClass) {
                        numOfMostEmptyDocInAClass = actualCount;
                        estimatedClass = classification;
                    }
                }

            // van szo az uzenetben
            } else {

                estimatedClass = estimateClass(line.split(" "));
            }


            System.out.println(estimatedClass.ordinal());

        }
    }

    private Classification estimateClass(String[] wordCodeStrings) {
        HashMap<Classification, Double> probsByClass = new HashMap<>();

        for (Classification classification : Classification.values()) {
            double probOfClass = (double) classificationFreqOfDocs.get(classification) / documents.size();
            double prob = 1;
            for (String wordCodeString : wordCodeStrings) {
                try {
                    prob *= vocabulary.get(Integer.parseInt(wordCodeString)).getProbInClass(classification);
                } catch (NullPointerException ex) {
                    prob *= (double) 1 / (classificationFreqOfWords.get(classification) + vocabulary.size());
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
