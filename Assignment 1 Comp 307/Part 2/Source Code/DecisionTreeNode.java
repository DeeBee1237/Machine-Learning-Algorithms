package DecisionTree;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class DecisionTreeNode {


	private List <String> attributeNames = DataLoader.getAttributeNames();
	private Decider decider = new Decider ();



	private String value;
	private DecisionTreeNode trueChild;
	private DecisionTreeNode falseChild;

	// to be used for leaf nodes only
	private boolean decison;

	public DecisionTreeNode(String value, DecisionTreeNode trueChild, DecisionTreeNode falseChild) {
		this.value = value;
		this.trueChild = trueChild;
		this.falseChild = falseChild;
	}

	public void constructDecisionTreeNode (List<TrainingInstance> trainingSet) {

		// if the training set has 0 entropy, then it is pure, and this treeNode should be a leaf node
		if (decider.calculateEntropyForLive(trainingSet) == 0) {
			this.decison = trainingSet.get(0).getLiveOrDieValue();
			return;
		}

		// create a queue which will store attribute items, and prioritize the ones with the greatest 'gain' value
		PriorityQueue <AttributeValue> queue = new PriorityQueue <AttributeValue> ();

		// iterate over the different attributes in the training set, finding the attribute which offers the greatest 'gain'
		// this will ensure the tree branches are as homogeneous as possible
		for (int i = 0; i < attributeNames.size(); i++) {

			if (trainingSet.get(0).isVisitedAtIndex(i))
				continue;

			double gainForCurrentAttribute = decider.calculateGain(i, trainingSet);
			String stringForCurrentAttribute = attributeNames.get(i);


			queue.add(new AttributeValue(gainForCurrentAttribute,stringForCurrentAttribute,i));
		}

		// the AtrributeValue with the most gain:
		AttributeValue attributeWithMostGain = queue.poll();

		String nameOfAttributeWithHighestGain = attributeWithMostGain.getName();

		int columnOfAttributeWithHighestGain = attributeWithMostGain.getColumnOfAttribute();

		// set the value of this Decision Tree Node to the name of the attribute with the most gain:
		this.setValue(nameOfAttributeWithHighestGain);

		// create 'empty' true and false Decision Tree Nodes for this node:
		this.setTrueChild(new DecisionTreeNode ("",null,null));

		this.setFalseChild(new DecisionTreeNode ("",null,null));

		for (TrainingInstance instance: trainingSet)
			instance.setVisitedAtIndex(columnOfAttributeWithHighestGain);

		// Recurse on the false child, with the sub training set for which the value of attributeWithMostGain is always false:
		this.getFalseChild().constructDecisionTreeNode(decider.createSubTrainingList(columnOfAttributeWithHighestGain,false,trainingSet));

		// Recurse on the true child, with the sub training set for which the value of attributeWithMostGain is always true:
		this.getTrueChild().constructDecisionTreeNode(decider.createSubTrainingList(columnOfAttributeWithHighestGain,true,trainingSet));


	}

	public String predictOutCome (TestInstance instance) {

		if (this.falseChild == null && this.trueChild == null) {
			if(this.decison == true)
				return "Live";
			else
				return "Die";
		}

		String valueOfCurrentNode = this.value;

		int indexOfValueInTestInstance = this.attributeNames.indexOf(valueOfCurrentNode);
		boolean booleanValueInTestInstance = instance.getBooleanValueAtIndex(indexOfValueInTestInstance);

		if (booleanValueInTestInstance == true)
			return this.trueChild.predictOutCome(instance);

		else
			return this.falseChild.predictOutCome(instance);
	}


	// FOR TESTING:
	public void printTree (String tab) {
		// for a leaf node:
		if (this.getTrueChild() == null && this.getFalseChild() == null) {
			boolean decision = this.getDecison();

			if (decision)
				System.out.println(tab + "Then Live");
			else
				System.out.println(tab + "Then Die");

			return;
		}

		System.out.println(tab + "If: " + this.getValue() + " == true: ");
		this.trueChild.printTree("   " + tab);

		System.out.println(tab + "If: " + this.getValue() + " == false: ");
		this.falseChild.printTree("   " + tab);
	}


	// GETTERS AND SETTERS:

	public DecisionTreeNode getTrueChild() {
		return trueChild;
	}
	public void setTrueChild(DecisionTreeNode trueChild) {
		this.trueChild = trueChild;
	}
	public DecisionTreeNode getFalseChild() {
		return falseChild;
	}
	public void setFalseChild(DecisionTreeNode falseChild) {
		this.falseChild = falseChild;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean getDecison() {
		return decison;
	}
	public void setDecison(boolean decison) {
		this.decison = decison;
	}


}
