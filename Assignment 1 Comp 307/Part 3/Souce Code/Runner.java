package Perceptron;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {

	public static void main (String [] args) {

		Scanner scan = new Scanner (System.in);

		System.out.println("Please enter the image file here: ");
		String fileName = scan.next();

		// load the data, and obtain a list of all the images from the data file:
		DataLoader dataLoader = new DataLoader (fileName);
		List <Image> images = dataLoader.getImages();

		// create a new perceptron to classify the images from the file:
		Perceptron perceptron = new Perceptron ();

		// TODO: The perceptron should learn a set of weights that work well for classifying ALL images!
		for (int i = 0; i < 1000; i++) {
			perceptron.setNumberOfCorrectClassifications(0);
			for (Image image: images) {
				perceptron.setImage(image);
				perceptron.learnWeights();
			}
			if (perceptron.getNumberOfCorrectClassifications() == 100) {
				System.out.println("Number of training cycles to convergence: " + i);
				break;
			}
		}

		System.out.println("\n" + "Weights Learned by Perceptron: " + perceptron.getWeights().toString() + "\n");

		// now test the weights learned by the perceptron:

		List <Image> imagesIncorrectlyClassified = new ArrayList <Image> ();
		List <Image> imagesCorrectlyClassified = new ArrayList <Image> ();

		int wrongCount = 0;
		int rightCount = 0;

		for (Image image: images) {
			perceptron.setImage(image);
			String classificationAccordingToPerceptron = "";

			if (perceptron.classify() == 1) 
				classificationAccordingToPerceptron = "Yes";
			if (perceptron.classify() == 0) 
				classificationAccordingToPerceptron = "other";

			if (!classificationAccordingToPerceptron.equals(image.getClassName())) {
				imagesIncorrectlyClassified.add(image);
				wrongCount++;
			}

			else {
				imagesCorrectlyClassified.add(image);
				rightCount++;
			}
		}

		System.out.println("Number of images innacurately classified: " + wrongCount + "\n");
		System.out.println("Number of images correctly classified: " + rightCount + "\n");


		System.out.println("Images innacurately classified: ");
		System.out.println("-------------------------------" + "\n");

		for (Image image: imagesIncorrectlyClassified) {
			System.out.println(image.getClassName()); 
			System.out.println("Features used for this image: " + image.getFeatures().toString()+ "\n");
		}

		System.out.println("Images correctly classified: ");
		System.out.println("-------------------------------" + "\n");


		for (Image image: imagesCorrectlyClassified) {
			System.out.println(image.getClassName()); 
			System.out.println("Features used for this image: " + image.getFeatures().toString() + "\n");
		}
		
		// now test the perceptron and it's newly learned weights on unseen data:
//		DataLoader dataLoader2 = new DataLoader ("image2.data");
//		List <Image> unseenImages = dataLoader2.getImages();
//		
//		List <Image> unseenImagesCorrectlyClassified = new ArrayList <Image> ();
//
//		rightCount = 0;
//		wrongCount = 0;
//		
//		for (Image image: unseenImages) {
//			perceptron.setImage(image);
//			String classificationAccordingToPerceptron = "";
//
//			if (perceptron.classify() == 1) 
//				classificationAccordingToPerceptron = "Yes";
//			if (perceptron.classify() == 0) 
//				classificationAccordingToPerceptron = "other";
//
//			if (classificationAccordingToPerceptron.equals(image.getClassName())) {
//				rightCount++;
//				unseenImagesCorrectlyClassified.add(image);
//			}
//		}
//		
//		System.out.println("number of unseen images: " + unseenImages.size());
//		System.out.println("Numer of unseen images, correctly classified: " + rightCount);
//		
//		for (Image image: unseenImagesCorrectlyClassified) {
//			System.out.println("Image type: " + image.getClassName() + " rows: "+ image.getRows() + " cols: " + image.getCols());
//		}

	}
}
