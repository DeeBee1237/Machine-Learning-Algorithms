package KNNAlgorithm;
/**
 * A Class that will define all the information that an 'instance' will have
 * Instances can be from the test set or training set
 * @author Dragos
 *
 */
public abstract class Instance {
	
	private double sepalLength;
	private double sepalWidth;
	private double petalLength;
	private double petalWidth;
	private String className;
	
	public Instance (double sepalLength, double sepalWidth, double petalLength, double petalWidth,String className) {
		this.sepalLength = sepalLength;
		this.sepalWidth = sepalWidth;
		this.petalLength = petalLength;
		this.petalWidth = petalWidth;
		this.className = className;
	}
	
	// Getters:

	public double getSepalLength() {
		return sepalLength;
	}

	public double getSepalWidth() {
		return sepalWidth;
	}

	public double getPetalLength() {
		return petalLength;
	}

	public double getPetalWidth() {
		return petalWidth;
	}

	public String getClassName() {
		return className;
	}

//	return "Instance [sepalLength=" + sepalLength + ", sepalWidth=" + sepalWidth + ", petalLength=" + petalLength
//			+ ", petalWidth=" + petalWidth + ", className=" + className + "]";
	@Override
	public String toString() {
		return  sepalLength + "  " + sepalWidth + "  " + petalLength
				+ "  " + petalWidth + "  " + className;
	}
		
}
