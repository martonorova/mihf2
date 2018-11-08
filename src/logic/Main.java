package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static final int LEARNING_DOCUMENT_NUM = 80000;

    public static void main (String[] args) {

        BayesClassifier bayesClassifier = new BayesClassifier();

        Scanner scanner = new Scanner(System.in);

        if (args.length > 0) {
            try {
                scanner.close();
                scanner = new Scanner(new File("mi_input.txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        bayesClassifier.readLearningDocuments(scanner);

        bayesClassifier.analyzeDocuments();



//        ArrayList<Document> documents = new ArrayList<>();
//        HashMap<Integer, Word> vocabulary = new HashMap<>();
//        HashMap<Classification, Integer> classificationFreqOfDocs = new HashMap<>();
//        HashMap<Classification, Integer> classificationFreqOfWords = new HashMap<>();
//
//        Scanner scanner = new Scanner(System.in);
//
//        if (args.length > 0) {
//            try {
//                scanner.close();
//                scanner = new Scanner(new File("mi_input.txt"));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//
//        int lineCount = 1;
//        while (scanner.hasNext() && lineCount <= 2 * LEARNING_DOCUMENT_NUM) {
//
//
//            if (lineCount <= LEARNING_DOCUMENT_NUM) {
//
//                Document document = new Document();
//
//                String line = scanner.nextLine();
//
//                if (line.length() > 0) {
//
//                    String[] wordCodes = line.split(" ");
//
//                    for (String wordCodeString : wordCodes) {
//
//                        //int wordCode = Integer.parseInt(wordCodeString);
//                        Word word = new Word(Integer.parseInt(wordCodeString));
//
//                        vocabulary.put(word.getCode(), word);
//                        document.addWord(word);
//                    }
//                }
//
//                documents.add(document);
//
//            } else {
//
//                int classCode = scanner.nextInt();
//
//                Classification classification = classCode == 1 ? Classification.POSITIVE : Classification.NEGATIVE;
//
//                classificationFreqOfDocs.putIfAbsent(classification, 0);
//                classificationFreqOfDocs.put(classification, classificationFreqOfDocs.get(classification) + 1);
//
//                documents.get(lineCount - LEARNING_DOCUMENT_NUM - 1).setClassification(classification);
//
//            }
//
//            lineCount++;
//        }
//
//        //TODO analyze data
//        System.out.println("ANALYZE");
//
//        documents.forEach(document -> {
//
//            document.getWordFreqsInDocument().forEach((word, freq) -> {
//
//                Classification classification = document.getClassification();
//
//                word.incrementFreqInClass(classification);
//                classificationFreqOfWords.putIfAbsent(classification, 0);
//                classificationFreqOfWords.put(classification, classificationFreqOfWords.get(classification) + 1);
//
//            });
//
//        });
//
//        vocabulary.forEach((wordCode, word) -> {
//            classificationFreqOfWords.forEach((classification, sumOfWordsInClass) -> {
//                word.setProbInClass(classification, sumOfWordsInClass, vocabulary.size());
//            });
//        });











//
//        //TODO test
//        while (scanner.hasNext()) {
//            //System.out.println("test");
//
//
//
//
//        }

    }
}
