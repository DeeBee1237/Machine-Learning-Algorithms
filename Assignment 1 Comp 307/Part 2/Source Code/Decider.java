package DecisionTree;
import java.util.ArrayList;
import java.util.List;

/**
 * Will have the responsibility for searching the attributes of a TrainingSet and finding the
 * attribute to choose for a new node that is being constructed. The Decider will find
 * an attribute to give to a new node that offers homogeneous branches (basically it will choose
 * the attribute with the greatest 'gain' value)
 * @author Dragos
 *
 */
public class Decider {

	/**
	 * Calculate the entropy given a number of positive and negative instances
	 * @param positiveInstances
	 * @param negativeInstances
	 * @return
	 */
	public double calculateEntropy (int positiveInstances, int negativeInstances) {

		// calculate the probability values:
		double probabilityForPositive; // (double)positiveInstances/(positiveInstances + negativeInstances);
		double probabilityForNegative; //(double)negativeInstances/(positiveInstances + negativeInstances);

		// if both parameters are equal to zero, then this must be donbe to avoid NaN being returned, from dividing by 0
		if (positiveInstances == 0 && negativeInstances == 0) {
			probabilityForPositive = 0;
			probabilityForNegative = 0;
		}

		else {
			probabilityForPositive = (double)positiveInstances/(positiveInstances + negativeInstances);
			probabilityForNegative = (double)negativeInstances/(positiveInstances + negativeInstances);
		}

		// get the base 2 logarithm values
		double logBaseTwoForPositive; // = Math.log10(probabilityForPositive)/Math.log10(2);
		double logBaseTwoForNegative; // = Math.log10(probabilityForNegative)/Math.log10(2);

		if (positiveInstances == 0)
			logBaseTwoForPositive = 0;
		else
			logBaseTwoForPositive = Math.log10(probabilityForPositive)/Math.log10(2);

		if (negativeInstances == 0)
			logBaseTwoForNegative = 0;
		else
			logBaseTwoForNegative = Math.log10(probabilityForNegative)/Math.log10(2);



		return (-probabilityForPositive)*logBaseTwoForPositive -
				(probabilityForNegative)*logBaseTwoForNegative;
	}


	public double calculateEntropyForLive (List<TrainingInstance> trainingSet) {

		int liveCount = 0;
		int dieCount = 0;

		for (TrainingInstance instance: trainingSet) {

			if (instance.getLiveOrDieValue())
				liveCount++;
			else
				dieCount++;
		}

		return calculateEntropy(liveCount,dieCount);
	}


	public double calculateEntropyForTwoAttributes (int attributeColumnNumber, List<TrainingInstance> trainingSet) {

		int trueAndLiveCount = 0;
		int trueAndDieCount = 0;
		int falseAndLiveCount = 0;
		int falseAndDieCount = 0;

		for (TrainingInstance instance: trainingSet) {

			boolean currentBooleaValue = instance.getBooleanValueAtIndex(attributeColumnNumber);
			boolean doesLive = instance.getLiveOrDieValue();

			if (currentBooleaValue == true) {
				if (doesLive)
					trueAndLiveCount++;
				else
					trueAndDieCount++;
			}

			if (currentBooleaValue == false) {
				if (doesLive)
					falseAndLiveCount++;
				else
					falseAndDieCount++;
			}

		}

		int trueSum = trueAndLiveCount + trueAndDieCount;
		int falseSum = falseAndLiveCount + falseAndDieCount;

		int totalSum = trueSum + falseSum;

		double pTrue = (double) trueSum/totalSum;
		double pFalse = (double) falseSum/totalSum;

		double e1 = calculateEntropy(trueAndLiveCount,trueAndDieCount);
		double e2 = calculateEntropy(falseAndLiveCount,falseAndDieCount);

		double result = pTrue*e1 +
				pFalse*e2;


		return result;


	}


	public double calculateGain (int attributeColumnNumber, List<TrainingInstance> trainingSet) {
		return calculateEntropyForLive(trainingSet) - calculateEntropyForTwoAttributes(attributeColumnNumber, trainingSet);
	}



	/**
	 * Split a training list into a new training list, based on one attribute and one boolean value that this attribute will have
	 *
	 * @param columnOfAttribute: the integer value of the column number, that the attribute is found in the trainingSet
	 * @param splitOn: extract all the instances into the sub list, only when the attribute has value 'splitOn' (true or false)
	 * @param trainingList: the larger training list
	 * @return
	 */
	public List<TrainingInstance> createSubTrainingList (int columnOfAttribute, boolean splitOn, List<TrainingInstance> trainingList) {

		List<TrainingInstance> subTrainingSet = new ArrayList <TrainingInstance> ();

		for (TrainingInstance instance: trainingList) {
			if (instance.getBooleanValueAtIndex(columnOfAttribute) ==  splitOn)
				subTrainingSet.add(instance);
		}

		return subTrainingSet;
	}


}
