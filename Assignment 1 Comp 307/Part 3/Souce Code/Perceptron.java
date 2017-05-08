package Perceptron;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Perceptron {

	//	private List<Integer> features = new ArrayList <Integer> (); // 50  0's and 1's
	private List<Double> weights = new ArrayList <Double> ();
	private Image image;
	
	private int numberOfCorrectClassifications = 0;

	public Perceptron () {
		loadWeightsRandomly();
	}

	/**
	 * Randomly initialize the weights of the perceptron (to begin with)
	 */
	public void loadWeightsRandomly () {

		// initialize the weights:
		Random r = new Random();
		for (int i = 0; i < 51; i ++)
			this.weights.add(Math.random());  ///(Math.random()); //-100.5 + (0.5 - (-100.5)) * r.nextDouble()
	}

	/**
	 *
	 * @param sign detrmines whether to add or subtract the feature vector from the weight vector:
	 */
	public void addOrSubtractVector (String sign) {

		List<Double> newWeights = new ArrayList <Double> ();

		for (int i = 0; i < this.weights.size(); i++) {

			double currentWeight = this.weights.get(i);
			if (sign.equals("+"))
				newWeights.add(i,currentWeight + this.image.getFeatures().get(i));
			else
				newWeights.add(i,currentWeight - this.image.getFeatures().get(i));
		}

		this.weights = newWeights;
	}



	/**
	 * Classify the Perceptrons image as a positive (return 1) or negative (return 0) example:
	 * @return
	 */
	public int classify () {
		double sum = 0.0;
		// for the dummy feature, f0 = 1: w0*f0 = w0
		//		sum += Math.random(); // or 1 IF w0 is also supposed to be 1
		for (int i = 0; i < 51; i++)
				sum += this.image.getFeatures().get(i)*weights.get(i);

		return (sum > 0) ? 1:0;
	}

	public void learnWeights () {

		int classification = classify ();

		// if the perceptron was right:
		if ((classification == 0 && this.image.getClassName().equals("other")) ||
				(classification == 1 && this.image.getClassName().equals("Yes"))) 
			this.numberOfCorrectClassifications++;
		// if -ve example and wrong:
		// means the classification should be 1, the weights need to be [increased] so that next time the sum (f*w)
		// can be > 0, so that the classification will be 1.
		if (classification == 0 && this.image.getClassName().equals("Yes")) { //System.out.println("add");
			addOrSubtractVector("+");  }

		// if +ve example and wrong
		// means the classification should be 0, the weights need to be [decreased] so that next time the sum (f*w)
		// can be < 0, so that the classification will be 0.
		if (classification == 1 && this.image.getClassName().equals("other")) { //System.out.println("minus");
			addOrSubtractVector("-");}
	}


	public void setImage(Image image) {
		this.image = image;
	}

	public List<Double> getWeights() {
		return weights;
	}
	
	public void setWeights(List<Double> weights) {
		this.weights = weights;
	}


	public int getNumberOfCorrectClassifications() {
		return numberOfCorrectClassifications;
	}

	public void setNumberOfCorrectClassifications(int numberOfCorrectClassifications) {
		this.numberOfCorrectClassifications = numberOfCorrectClassifications;
	}


}
