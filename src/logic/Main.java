package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

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

        bayesClassifier.test(scanner);

        scanner.close();

    }
}
