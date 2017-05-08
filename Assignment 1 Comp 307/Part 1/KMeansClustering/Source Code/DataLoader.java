package KMeansClustering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The role of this class will be to read the instances from both the test 
 * and training sets, create objects for them, and store collections of data
 * for both the test and training sets. Scan,Load and Store.
 * @author Dragos
 *
 */
public class DataLoader {

	private List<TrainingInstance> trainingSet = new ArrayList <TrainingInstance> ();

	public DataLoader () {
		loadInstanceLists();
	}

	/**
	 * Take in a file name and what type of list to load (the test or training set)
	 * and read the file specified, parse it, and create the appropriate instance objects
	 * adding them to the appropriate set
	 * 
	 * @param fileName
	 * @param listToLoad
	 */
	public void loadAnInstanceList (String fileName) {
		try {
			// create a file reader and a BufferedReader to read the text file:
			FileReader fileReader = new FileReader (new File(fileName));
			BufferedReader scanner = new BufferedReader (fileReader);

			// maintain the current line of data being scanned from the text file:
			String currentInstance = scanner.readLine();

			// read the current instance from the file, parse it, make an object and 
			// add it to the list:
			while (currentInstance != null) {
				// check for a line with empty space (this will occur at the end of the files):
				if (currentInstance.trim().isEmpty())
					break;
				// get the string values for the current instance being scanned:
				String [] dataValuesOfCurrentInstance = currentInstance.split("  ");

				// get the numeric values for petalLength, petalWidth etc ...
				double sepalLength = Double.parseDouble(dataValuesOfCurrentInstance[0]);
				double sepalWidth = Double.parseDouble(dataValuesOfCurrentInstance[1]);
				double petalLength = Double.parseDouble(dataValuesOfCurrentInstance[2]);
				double petalWidth = Double.parseDouble(dataValuesOfCurrentInstance[3]);
				String className = dataValuesOfCurrentInstance[4];


				// create a new TrainingInstance Object:
				TrainingInstance testInstance = new TrainingInstance (sepalLength,sepalWidth,petalLength,petalWidth,className);
				trainingSet.add(testInstance);

				currentInstance = scanner.readLine();
			}

			scanner.close();
		} 

		catch (IOException e) {
			System.out.println("File Wasn't Found!");
			e.printStackTrace();
		}
	}

	/**
	 * Read both text fields (the test and training sets) and load both of the lists of
	 * the class, with InstanceObjects
	 */
	public void loadInstanceLists () {
		loadAnInstanceList("iris-training.txt");
	}

	// Getters for the training set and the test set:

	public List<TrainingInstance> getTrainingSet() {
		return trainingSet;
	}


}
