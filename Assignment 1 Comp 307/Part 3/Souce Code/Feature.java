package Perceptron;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;


public class Feature {

	// the row and col positions of the 4 chosen pixels for an image:
	private int [] rows = new int [4];
	private int [] cols = new int [4];
	// the 4 random guesses for what the pixels are:
	private int [] predictions = new int [4];

	// the image to generate a feature for:
	private Image image;

	public Feature(Image image) {
		this.image = image;
	}

	/**
	 * Pick four random pixels by loading the rows and cols array with random index positions
	 * and load the predictions array as well
	 * @return
	 */
	public void generate4RandomPixels () {

		// Pick four random row positions from the image:
		List<Integer> rowPositionsForPixelsMatrix = new ArrayList <Integer> ();
		for (int i = 0; i < this.image.getRows(); i++)
			rowPositionsForPixelsMatrix.add(i);

		Collections.shuffle(rowPositionsForPixelsMatrix);

		for (int i = 0; i < rows.length; i++)
			rows[i] = rowPositionsForPixelsMatrix.get(i);

		// pick four random col positions from the image
		List<Integer> colPositionsForPixelsMatrix = new ArrayList <Integer> ();
		for (int i = 0; i < this.image.getCols(); i++)
			colPositionsForPixelsMatrix.add(i);
		
		Collections.shuffle(colPositionsForPixelsMatrix);
		for (int i = 0; i < cols.length; i++)
			cols[i] = colPositionsForPixelsMatrix.get(i);

		// now make predictions for what those pixels will look like:
		Random r = new Random (); // 10
		for (int i = 0; i < predictions.length;i++)
			predictions[i] = r.nextInt(2);

	}

	/**
	 * return a feature (return either 0 or 1) based on how many of the four randomly selected pixels, from the image,
	 * were correctly predicted
	 * @return
	 */
	public int generateFeature () {
		generate4RandomPixels();
		int [][] pixelsForThisImage = this.image.getPixels();

		int sum=0;
		for(int i=0; i < 4; i++)
		if (pixelsForThisImage[this.rows[i]] [this.cols[i]] == this.predictions[i])
			sum++;
		return (sum>=3)?1:0;
	}
}
