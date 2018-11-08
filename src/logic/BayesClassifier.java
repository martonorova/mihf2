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

            document.getWordFreqsInDocument().forEach((word, freq) -> {

                Classification classification = document.getClassification();

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
        System.out.println("END OF ANALYZE");
    }

    public void test(Scanner scanner) {
        System.out.println("test");
    }

}
