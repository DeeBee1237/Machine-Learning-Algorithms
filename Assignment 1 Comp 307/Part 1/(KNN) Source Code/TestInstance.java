package KNNAlgorithm;

public class TestInstance extends Instance {

	public TestInstance (double sepalLength, double sepalWidth, double petalLength, double petalWidth,String className) {
		super(sepalLength,sepalWidth,petalLength,petalWidth,className);
	}

	/**
	 * For KNN, obtain the distance that the trainingInstance is from this testInstance:
	 * @param trainingInstance
	 * @return
	 */
	public double getDistanceValueForTrainingInstance (TrainingInstance trainingInstance, double R1, double R2, double R3, double R4) {

		double squareSum = (Math.pow(this.getSepalLength() - trainingInstance.getSepalLength(),2)/R1) +
				(Math.pow(this.getSepalWidth() - trainingInstance.getSepalWidth(),2)/R2) +
				(Math.pow(this.getPetalLength() - trainingInstance.getPetalLength(),2)/R3) +
				(Math.pow(this.getPetalWidth() - trainingInstance.getPetalWidth(),2)/R4);

		return Math.sqrt(squareSum);
	}
}
