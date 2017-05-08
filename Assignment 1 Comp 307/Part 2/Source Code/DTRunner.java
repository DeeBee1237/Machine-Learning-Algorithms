package DecisionTree;

import java.util.Scanner;

public class DTRunner {

	public static void main (String [] args) {

		Scanner scan = new Scanner (System.in);

		String trainingFileName = "";
		String testFileName = "";

		System.out.println("Please Enter the name of your training data file: ");
		trainingFileName = scan.next();

		System.out.println("Please Enter the name of your test data file: ");
		testFileName = scan.next();

		new Predictor (trainingFileName,testFileName).displayPredicitions();

		scan.close();

	}

}
