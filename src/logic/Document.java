package logic;

import java.util.HashMap;

public class Document {
    private HashMap<Long, Integer> wordFreqsInDocument = new HashMap<>();
    private Classification classification;



    public void addWord(long wordCode) {
        wordFreqsInDocument.putIfAbsent(wordCode, 0);
        wordFreqsInDocument.put(wordCode, wordFreqsInDocument.get(wordCode) + 1);
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public HashMap<Long, Integer> getWordFreqsInDocument() {
        return wordFreqsInDocument;
    }


}
