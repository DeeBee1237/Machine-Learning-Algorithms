package DecisionTree;

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
	private List <TestInstance> testSet = new ArrayList <TestInstance> ();

	private static List<String> attributeNames = new ArrayList <String> ();

	public DataLoader (String trainingFile, String testFile) {
		loadInstanceLists(trainingFile, testFile);
	}

	public void loadAttributeNames (String attributeNames) {
		String [] attributes = attributeNames.split("\t");

		for (int i = 0; i < attributes.length; i++) {
			String attribute = attributes [i];
			if (attribute.isEmpty())
				continue;
			this.attributeNames.add(attribute.trim());
		}
	}

	/**
	 * Take in a String array of boolean values (as well as live or die) and add only the non empty
	 * strings to a new array that the method returns
	 * @param inputArray
	 * @return
	 */
	public String [] getFormattedArray (String [] inputArray) {
		List<String> resultList = new ArrayList <String> ();

		for (String currentString: inputArray) {
			if (currentString.isEmpty())
				continue;
			resultList.add(currentString);
		}

		String [] resultArray = new String [resultList.size()];
		for (int i = 0; i < resultList.size(); i ++)
			resultArray[i] = resultList.get(i);

		return resultArray;

	}

	/**
	 * Take in a file name and what type of list to load (the test or training set)
	 * and read the file specified, parse it, and create the appropriate instance objects
	 * adding them to the appropriate set
	 *
	 * @param fileName
	 * @param listToLoad
	 */
	public void loadAnInstanceList (String fileName, String listToLoad) {
		try {
			// create a file reader and a BufferedReader to read the text file:
			FileReader fileReader = new FileReader (new File(fileName));
			BufferedReader scanner = new BufferedReader (fileReader);

			// move the scanner past the first lines:
			scanner.readLine();
			String attributeNames = scanner.readLine();

			// load the attribute names, only when reading the test file:
			if (listToLoad.equals("Test"))
				loadAttributeNames(attributeNames);

			// maintain the current line of data being scanned from the text file:
			String currentInstance = scanner.readLine();

			// read the current instance from the file, parse it, make an object and
			// add it to the list:
			while (currentInstance != null) {

				// check for a line with empty space (this will occur at the end of the files):
				if (currentInstance.trim().isEmpty())
					break;


				// get the string values for the current instance being scanned:
				String [] dataValuesOfCurrentInstance = currentInstance.split("\t");

				// because of the inconsistent file formatting (some files are tab separated, but one is separated by a variety of white space)
				// I will on " ", and remove all empty strings from the array, and use the resulting array:
				if (dataValuesOfCurrentInstance.length == 1)
					dataValuesOfCurrentInstance = getFormattedArray(currentInstance.split(" "));

				boolean [] booleanValues = new boolean [dataValuesOfCurrentInstance.length];

				for (int i = 1; i < dataValuesOfCurrentInstance.length; i++)
					booleanValues[i-1] = Boolean.parseBoolean(dataValuesOfCurrentInstance[i]);


				if (dataValuesOfCurrentInstance[0].equals("live"))
					booleanValues[dataValuesOfCurrentInstance.length - 1] = true; // 6
				else
					booleanValues[dataValuesOfCurrentInstance.length - 1] = false;



				if (listToLoad.equals("Test")) {
					//	create a new TestInstance Object:
					TestInstance testInstance = new TestInstance (booleanValues);
					testSet.add(testInstance);
				}

				else {
					// create a new TrainingInstance Object:
					TrainingInstance testInstance = new TrainingInstance (booleanValues);
					trainingSet.add(testInstance);
				}

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
	public void loadInstanceLists (String trainingFile, String testFile) {
		loadAnInstanceList(testFile,"Test");
		loadAnInstanceList(trainingFile,"Training");
	}

	// Getters for the training set and the test set:

	public List<TrainingInstance> getTrainingSet() {
		return trainingSet;
	}

	public List<TestInstance> getTestSet() {
		return testSet;
	}

	public static List<String> getAttributeNames() {
		return attributeNames;
	}



}
