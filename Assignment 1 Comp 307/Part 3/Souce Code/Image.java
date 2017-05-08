package Perceptron;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Image {

	// #yes or #other
	private String className;
	private int rows;
	private int cols;
	private int [][] pixels;
	
	private List<Integer> features = new ArrayList <Integer> ();

	public Image(String className, int rows, int cols ,int[][] pixels) {
		this.className = className;
		this.rows = rows;
		this.cols = cols;
		this.pixels = pixels;
		loadFeatures();
	}

	public void loadFeatures () {
		// generate 50 random features:
		Feature feature = new Feature (this);

		List <Integer> featuresForImage = new ArrayList <Integer> ();
		for (int i = 0; i < 51; i ++)
			if (i == 0)
				featuresForImage.add(1); // for the dummy value.
			else
				featuresForImage.add(feature.generateFeature());

		this.features = featuresForImage;
	}



	public String getClassName() {
		return className;
	}

	public int[][] getPixels() {
		return pixels;
	}

	public String arrayString () {

		String result = "";

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				result += (this.pixels[i][j]);
			}
			result+="\n";
		}

		return result;
	}


	@Override
	public String toString() {
		return "Image [className=" + className + " rows: " + rows + " cols: " + cols + ", pixels=" + arrayString() + "]";
	}

	public List<Integer> getFeatures() {
		return features;
	}
	
	public int getRows () {
		return rows;
	}
	
	public int getCols () {
		return cols;
	}


}
