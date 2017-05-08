package KNNAlgorithm;

import java.util.Scanner;

/**
 * The main runner class that prints out the clasifications of each instance of the test set
 * according to the instances in the training set
 * @author Dragos
 *
 */
public class RunnerOfKNN {

	public static void main (String [] args) {

		String fileNameOfTrainingSet = "";
		String fileNameOfTestSet = "";

		// ask the user for the value of K:
		Scanner scan = new Scanner (System.in);

		System.out.println("Please enter a value for K");

		Integer KValue = Integer.parseInt(scan.next());

		// ask the user for the training set:
		System.out.println("Please enter the name of the Training Set text file: ");

		fileNameOfTrainingSet = scan.next();

		// ask the user for the test set:
		System.out.println("Please enter the name of the Test Set text file: ");

		fileNameOfTestSet = scan.next();

		Classifier classifier = new Classifier (KValue, fileNameOfTrainingSet, fileNameOfTestSet);

		classifier.displayClassificationResults();
	}
}
