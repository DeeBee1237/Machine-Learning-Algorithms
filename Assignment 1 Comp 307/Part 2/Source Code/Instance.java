package DecisionTree;

import java.util.Arrays;

public abstract class Instance {

	private boolean [] values;
	boolean [] visitiedAttributes = new boolean [17];

	public Instance(boolean[] values) {
		this.values = values;
	}

	public boolean getBooleanValueAtIndex (int index) {
		return this.values[index];
	}

	public boolean getLiveOrDieValue () {
		return this.values[this.values.length - 1];
	}

	public void setVisitedAtIndex (int index) {
		this.visitiedAttributes[index] = true;
	}

	public boolean isVisitedAtIndex (int index) {
		return this.visitiedAttributes[index];
	}




}
