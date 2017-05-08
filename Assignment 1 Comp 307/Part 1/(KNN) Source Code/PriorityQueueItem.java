package KNNAlgorithm;

/**
 * An object that will store the distance that an instance from the the training set is from
 * an instance in the test set. It will also store that instance from the training set
 * @author Dragos
 *
 */
public class PriorityQueueItem implements Comparable <PriorityQueueItem> {
	
	private double distanceFromNeighbor;
	private TrainingInstance trainingInstance;
	
	public PriorityQueueItem(double distanceFromNeighbor, TrainingInstance trainingInstance) {
		this.distanceFromNeighbor = distanceFromNeighbor;
		this.trainingInstance = trainingInstance;
	}
	
	// compareTo method so that Items with smaller values for distance Values 
	// are placed at the front of the priority queue:
	@Override
	public int compareTo(PriorityQueueItem item) {

		if (this.distanceFromNeighbor > item.distanceFromNeighbor)
			return 1;
		else 
			return -1;
	}

	public TrainingInstance getTrainingInstance() {
		return trainingInstance;
	}

}
