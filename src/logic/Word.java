package logic;

import java.util.HashMap;
import java.util.Objects;

public class Word {
    private int code;
    private HashMap<Classification, Integer> freqsInClasses = new HashMap<>();
    private HashMap<Classification, Double> probsInClasses = new HashMap<>();

    public Word(int code) {

        this.code = code;
        initFreqMap();
    }


    private void initFreqMap() {
        for (Classification classification : Classification.values()) {
            freqsInClasses.put(classification, 0);
        }
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void incrementFreqInClass(Classification classification) {
        freqsInClasses.putIfAbsent(classification, 0);
        freqsInClasses.put(classification, freqsInClasses.get(classification) + 1);
    }

    public int getFreqInClass(Classification classification) {
        return freqsInClasses.get(classification);
    }

    public void setProbInClass(Classification classification, int sumOfWordsInClass, int sizeOfVocabulary) {
        double probGivenClass = (double) (sumOfWordsInClass + 1) / (freqsInClasses.get(classification) + sizeOfVocabulary);
        probsInClasses.put(classification, probGivenClass);
    }

    public double getProbInClass(Classification classification) {
        return probsInClasses.get(classification);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return code == word.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
