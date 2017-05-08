package Perceptron;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataLoader {

	private List<Image> images = new ArrayList<Image> ();

	public DataLoader (String fileName) {
		loadAllImages(fileName);
	}

	/**
	 * return a 10 by 10 int array for all the bits representing the pixels, in a line of text:
	 * @param pixels
	 * @return
	 */
	public int [][] parsePixels (String pixels, int rows, int cols) {
		int [][] result = new int [rows][cols];


		for (int row = 0; row < rows; row ++) {

			String currentRow = pixels.substring(cols*row,cols*(row+1));

			for (int col = 0;col < cols; col ++) {
				result[row][col] = Integer.parseInt(currentRow.substring(col,col+1));
			}
		}
		return result;
	}

	/**
	 * Create an image object from the string that is passed in, and add it to the data loaders list
	 * @param imageString
	 */
	public void loadAnImageInstance (String imageString) {

		String className = "";
		int rows;
		int cols;
		int[][] pixels;


		if (imageString.substring(0,4).equals("#Yes")) {
			className = "Yes";
			rows = Integer.parseInt(imageString.substring(4,6));
			cols = Integer.parseInt(imageString.substring(7,9));
			pixels = parsePixels(imageString.substring(9),rows,cols);
		}
		else {
			className = "other";
			rows = Integer.parseInt(imageString.substring(6,8));
			cols = Integer.parseInt(imageString.substring(9,11));
			pixels = parsePixels(imageString.substring(11),rows,cols);
		}

				this.images.add(new Image(className,rows,cols,pixels));
	}

	/**
	 * Read a file and make several calls to the above method, in order to load all the images into the
	 * loaders image list
	 * @param fileName
	 */
	public void loadAllImages (String fileName) {

		try {
			String entireFileContent = "";

			FileReader fileReader = new FileReader (new File(fileName));
			BufferedReader scanner = new BufferedReader (fileReader);

			String currentLine = scanner.readLine();

			while (currentLine != null) {
				entireFileContent += currentLine;
				currentLine = scanner.readLine();
			}

			String [] imagesAsStrings = entireFileContent.split("P1");
			for (int i = 1; i < imagesAsStrings.length; i++) {
				loadAnImageInstance(imagesAsStrings[i]);
			}
		}

		catch (IOException e) {
			System.out.println("Oops, Sorry: This file was not found!");
		}
	}

	public List<Image> getImages() {
		return images;
	}

}
