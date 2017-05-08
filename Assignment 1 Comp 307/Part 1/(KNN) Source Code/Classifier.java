package KNNAlgorithm;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class is responsible for scanning through the test set and classifying each instance
 * of the test set according to the training set. All the 'test-instance' objects will be
 * mapped to a string, which will be the class the KNN Algorithm mapped it to
 * @author Dragos
 *
 */
public class Classifier {

	// the value of K for KNN:
	private int K;

	private DataLoader dataLoader;

	// the Map that will hold the class names that each TestInstance is mapped to according to KNN:
	private Map <TestInstance,String> classifications = new HashMap <TestInstance,String> ();

	// the range values for all the different data values (columns) in the training set:
	private double sepalLengthRange; // range for sepal length
	private double sepalWidthRange; // range for sepal width
	private double petalLengthRange; // range for petal length
	private double petalWidthRange; // range for petal width


	public Classifier (int k, String trainingSet, String testSet) {
		this.dataLoader = new DataLoader (trainingSet, testSet);
		this.K = k;
		calculateRangeValues();
		classifyAllTestInstances();
	}

	/**
	 * Loop through the training set and add each attribute type to it's own individual list
	 * at the end, find the range of each attribute:
	 */
	public void calculateRangeValues () {

		List <TrainingInstance> trainingSet = dataLoader.getTrainingSet();

		List <Double> sepalLengthList = new ArrayList <Double> ();
		List <Double> sepalWidthList = new ArrayList <Double> ();
		List <Double> petalLengthList = new ArrayList <Double> ();
		List <Double> petalWidthList = new ArrayList <Double> ();


		for (TrainingInstance instance: trainingSet) {
			sepalLengthList.add(instance.getSepalLength());
			sepalWidthList.add(instance.getSepalLength());
			petalLengthList.add(instance.getPetalLength());
			petalWidthList.add(instance.getPetalWidth());
		}

		Collections.sort(sepalLengthList);
		Collections.sort(sepalWidthList);
		Collections.sort(petalLengthList);
		Collections.sort(petalWidthList);

		this.sepalLengthRange = sepalLengthList.get(sepalLengthList.size() - 1) - sepalLengthList.get(0);
		this.sepalWidthRange = sepalWidthList.get(sepalWidthList.size() - 1) - sepalWidthList.get(0);
		this.petalLengthRange = petalLengthList.get(petalLengthList.size() - 1) - petalLengthList.get(0);
		this.petalWidthRange = petalWidthList.get(petalWidthList.size() - 1) - petalWidthList.get(0);


	}

	/**
	 * Take in a list of the K closest neighbors to a test instance, and return the string value
	 * of the class name that occurs the most in these training instances:
	 * @param KClosestNeighbors
	 * @return
	 */
	public String getClosestNeighbor (List<TrainingInstance> KClosestNeighbors) {

		Map<String,Integer> classNameFrequencies = new HashMap <String,Integer> ();
		PriorityQueue<FrequencyItem> queue = new PriorityQueue<FrequencyItem> ();

		// go over the list of neighbors, and add the frequencies of each classname to the map
		for (TrainingInstance trainingInstance: KClosestNeighbors) {

			String className = trainingInstance.getClassName();

			if (classNameFrequencies.containsKey(className))
				classNameFrequencies.put(className, classNameFrequencies.get(className) + 1);
			else
				classNameFrequencies.put(className, 1);
		}

		// now go over the hash map and create frequency items to add to the priority queue:
		for (String className : classNameFrequencies.keySet()) {
			int freq = classNameFrequencies.get(className);
			queue.add(new FrequencyItem(freq,className));
		}

		// remove the frequency item from the front of the queue and return its class name:
		return queue.poll().className;
	}

	/**
	 *  uses KNN to classify one instance from the test set according to the training set:
	 * @param testInstance
	 */
	public void classifyATestInstance (TestInstance testInstance) {

		// obtain the training set from the dataLoader:
		List<TrainingInstance> trainingSet = dataLoader.getTrainingSet();
		// create a priority queue to keep track of the closest neighbors from the training set
		PriorityQueue<PriorityQueueItem> queue = new PriorityQueue <PriorityQueueItem>();

		for (TrainingInstance trainingInstance: trainingSet) {
			// how 'far' this instance is from the testInstance:
			double distance = testInstance.getDistanceValueForTrainingInstance(trainingInstance,sepalLengthRange,sepalWidthRange,petalLengthRange,petalWidthRange);

			PriorityQueueItem item = new PriorityQueueItem (distance,trainingInstance);

			queue.add(item);
		}

		List <TrainingInstance> KNearestNeighbors = new ArrayList <TrainingInstance> ();

		// now remove the K neighbors from the priority queue:
		for (int i = 0; i < K; i++)
			KNearestNeighbors.add(queue.poll().getTrainingInstance());

		String classNameForTestInstance = getClosestNeighbor(KNearestNeighbors);
		classifications.put(testInstance, classNameForTestInstance);

	}

	/**
	 * Obtain the test set and classify every single instance (according to the training set)
	 */
	public void classifyAllTestInstances () {
		List<TestInstance> testSet = dataLoader.getTestSet();

		for (TestInstance testInstance: testSet)
			classifyATestInstance(testInstance);
	}

	public Map<TestInstance, String> getClassifications() {
		return classifications;
	}

	/**
	 * Print out a list of all the test instances and their class names according to the
	 * KNN Algorithm:
	 */
	public void displayClassificationResults () {

		int count = 0;
		System.out.println("Classification Results: (Where K = " + K + ")" + "\n");

		// count the number of inaccurate classifications from the KNN Algorithm:
		int countInacurateClassifications = 0;
		List<TestInstance> testInstancesInaccuratelyClassified = new ArrayList <TestInstance> ();

		for (TestInstance testInstance: dataLoader.getTestSet()) {
			count++;

			String classificationFromKNN = classifications.get(testInstance);
			String testInstanceActualClassName = testInstance.getClassName();

			if (!classificationFromKNN.equals(testInstanceActualClassName)) {
				testInstancesInaccuratelyClassified.add(testInstance);
				countInacurateClassifications++;
			}

			System.out.println(testInstance.toString() + "\n" +
					"classification according to KNN: " + classificationFromKNN + "\n");
		}

		// report inaccuracies:
		System.out.println("--------------------------------------------------------");
		System.out.println("Number of test instances inaccurately classified: " + countInacurateClassifications + "\n");
		System.out.println("Instances that were not correctly classified: " + "\n");

		for (TestInstance instance: testInstancesInaccuratelyClassified)
			System.out.println(instance.toString() + "\n" + "\n");

		System.out.println("Total number of test instances : " + count + "\n");

		int correct = count - countInacurateClassifications;
		double percentage = ((double)correct / count)*100;
		System.out.println("Percentage of correct classifications: " + percentage + "%");
	}


	/**
	 * For identifying the most frequently occurring class names within the K closest neighbors:
	 * @author Dragos
	 *
	 */
	private class FrequencyItem implements Comparable<FrequencyItem> {

		private int frequency;
		private String className;

		public FrequencyItem (int freq,String name) {
			this.className = name;
			this.frequency = freq;
		}

		@Override
		public int compareTo(FrequencyItem item) {

			if (this.frequency < item.frequency)
				return 1;
			else
				return -1;
		}

	}

}
