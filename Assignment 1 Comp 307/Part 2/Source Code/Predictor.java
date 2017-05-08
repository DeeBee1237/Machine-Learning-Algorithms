package DecisionTree;

import java.util.List;
import java.util.ArrayList;


public class Predictor {

	private DataLoader dataLoader;
	private DecisionTreeNode root = new DecisionTreeNode ("",null,null);

	public Predictor(String trainingFile, String testFile) {

		this.dataLoader = new DataLoader (trainingFile,testFile);

		this.root.constructDecisionTreeNode(dataLoader.getTrainingSet());
		System.out.println("The Decision Tree: ");
		System.out.println("---------------------------------------------" + "\n");
		this.root.printTree("");
		System.out.println("---------------------------------------------" + "\n");
	}

	/**
	 * Return the baseline classifier for people who live, in the test set.
	 * @return
	 */
	public double getBaseLineClassifier () {

		int liveCount = 0;

		for (TestInstance testInstance: this.dataLoader.getTestSet())
			if (testInstance.getLiveOrDieValue() == true)
				liveCount++;

		double ratio = (double) liveCount/this.dataLoader.getTestSet().size();
		return ratio*100;
	}




	public void displayPredicitions () {

		System.out.println("\n" + "BaseLine Classifier (Live): " + getBaseLineClassifier() + "%" );
		System.out.println("\n" + "Tree Predictions: " + "\n");

		List<TestInstance> testInstances = dataLoader.getTestSet();

		int countWrong = 0;
		int count = 0;


		for (TestInstance instance: testInstances) {
			count++;
			boolean actual = instance.getLiveOrDieValue();
			String actualString = "";

			if (actual)
				actualString = "Live";

			else
				actualString = "Die";

			String prediction = this.root.predictOutCome(instance);

			if (!actualString.equals(prediction))
				countWrong++;

			System.out.println("what actually happens on this test instance: " + actualString);
			System.out.println("Decision tree prediction of what will happen: " + prediction + "\n");
		}

		int correct = (count - countWrong);
		double percentage = (double) correct/count;

		System.out.println("---------------------------------------------" + "\n");
		System.out.println("Total Number of test instances: " + count);
		System.out.println("Total Number of correct predictions: " + correct);
		System.out.println("Percentage of correct predictions: " + percentage*100 + "%" + "\n");

	}






}
