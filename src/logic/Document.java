package logic;

import java.util.HashMap;

public class Document {
    private HashMap<Word, Integer> wordFreqsInDocument = new HashMap<>();
    private Classification classification;



    public void addWord(Word word) {
        wordFreqsInDocument.putIfAbsent(word, 0);
        wordFreqsInDocument.put(word, wordFreqsInDocument.get(word) + 1);
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }

    public HashMap<Word, Integer> getWordFreqsInDocument() {
        return wordFreqsInDocument;
    }


}
