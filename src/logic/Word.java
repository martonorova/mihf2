package logic;

import java.util.HashMap;
import java.util.Objects;

public class Word {
    private long code;
    private HashMap<Classification, Integer> freqsInClasses = new HashMap<>();
    private HashMap<Classification, Double> probsGivenClasses = new HashMap<>();

    public Word(long code) {

        this.code = code;
        initFreqMap();
    }


    private void initFreqMap() {
        for (Classification classification : Classification.values()) {
            freqsInClasses.put(classification, 0);
        }
    }


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void incrementFreqInClass(Classification classification, int freq) {

        freqsInClasses.put(classification, freqsInClasses.get(classification) + freq);

    }

    public int getFreqInClass(Classification classification) {
        return freqsInClasses.get(classification);
    }

    public void setProbGivenClass(Classification classification, int wordCountInClass, int sizeOfVocabulary) {

        double probGivenClass = (double) (getFreqInClass(classification) + 1) / (double) (wordCountInClass + sizeOfVocabulary);

        probsGivenClasses.put(classification, probGivenClass);
    }

    public double getProbGivenClass(Classification classification) {
        return probsGivenClasses.get(classification);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Word word = (Word) o;
//        return code == word.code;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(code);
//    }
}
