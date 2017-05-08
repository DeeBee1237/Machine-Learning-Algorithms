package DecisionTree;


public class AttributeValue implements Comparable<AttributeValue> {

	private double gain;
	private String name;
	private int columnOfAttribute;

	public AttributeValue(double gain, String name, int columnOfAttribute) {
		this.gain = gain;
		this.name = name;
		this.columnOfAttribute = columnOfAttribute;
	}

	@Override
	public int compareTo(AttributeValue item) {
		if (this.gain < item.gain)
			return 1;
		else
			return -1;
	}

	// GETTERS AND SETTERS:

	public double getGain() {
		return gain;
	}

	public String getName() {
		return name;
	}

	public int getColumnOfAttribute() {
		return columnOfAttribute;
	}


}