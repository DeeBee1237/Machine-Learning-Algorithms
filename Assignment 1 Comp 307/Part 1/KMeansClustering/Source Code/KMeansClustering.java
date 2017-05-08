package KMeansClustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.PriorityQueue;



public class KMeansClustering {

	private DataLoader dataLoader = new DataLoader ();
	private List<TrainingInstance> instancesFromTrainingSet;

	// The 3 Clusters for using K = 3 means clustering, on the training data:
	private Set<TrainingInstance> cluster1 = new HashSet <TrainingInstance> ();
	private Set<TrainingInstance> cluster2 = new HashSet <TrainingInstance> ();
	private Set<TrainingInstance> cluster3 = new HashSet <TrainingInstance> ();

	// The three current centers in the algorithm,
	private TrainingInstance center1;
	private TrainingInstance center2;
	private TrainingInstance center3;



	public KMeansClustering () {
		this.instancesFromTrainingSet = dataLoader.getTrainingSet();
		initializeClusters();
		groupTrainingInstances();
	}


	/**
	 * Randomly select three Training instances for the 'centers' of this algorithm
	 */
	public void initializeClusters () {

		ArrayList <Integer> possibleIndexPositions = new ArrayList <Integer> ();

		for (int i = 0; i < this.instancesFromTrainingSet.size(); i++)
			possibleIndexPositions.add(i);

		Collections.shuffle(possibleIndexPositions);

		this.center1 = instancesFromTrainingSet.get(possibleIndexPositions.get(0));
		this.center2 = instancesFromTrainingSet.get(possibleIndexPositions.get(1));
		this.center3 = instancesFromTrainingSet.get(possibleIndexPositions.get(2));
	}



	/**
	 * Take in a cluster and return the average TrainingInstance in the cluster
	 * @param cluster
	 * @return
	 */
	public TrainingInstance getAverageTrainingInstance (Set <TrainingInstance> cluster) {

		double averageSepalLength = 0;
		double averageSepalWidth = 0;
		double averagePetalLength = 0;
		double averagePetalWidth = 0;

		double numberOfInstancesInCluster = 0.0;

		for (TrainingInstance instance: cluster) {

			numberOfInstancesInCluster++;

			averageSepalLength += instance.getSepalLength();
			averageSepalWidth += instance.getSepalWidth();
			averagePetalLength += instance.getPetalLength();
			averagePetalWidth += instance.getPetalWidth();

		}

		averageSepalLength = Math.round(averageSepalLength/numberOfInstancesInCluster*100.0)/100.0;
		averageSepalWidth = Math.round(averageSepalWidth/numberOfInstancesInCluster*100.0)/100.0;
		averagePetalLength = Math.round(averagePetalLength/numberOfInstancesInCluster*100.0)/100.0;
		averagePetalWidth = Math.round(averagePetalWidth/numberOfInstancesInCluster*100.0)/100.0;

		return new TrainingInstance(averageSepalLength,averageSepalWidth,averagePetalLength,averagePetalWidth,"");

	}
	
	/**
	 * Return the training instance from the training set, that is closest to the averageInstance:
	 * @param averageInstance
	 * @return
	 */
	public TrainingInstance getInstanceClosestToAverage (TrainingInstance averageInstance) {
		
		PriorityQueue <DistanceItem> queue = new PriorityQueue <DistanceItem> (); 
		for (TrainingInstance instance: this.instancesFromTrainingSet) 
			queue.add(new DistanceItem(instance.getDistanceValueForTrainingInstance(averageInstance),instance));
		
		return queue.poll().getTrainingInstance();
	}


	/**
	 * Apply K-Means-Clustering here:
	 */
	public void groupTrainingInstances () {

		while (true) {
			// Go through the training set, and classify each Training Instance into one of the three clusters:
			for (TrainingInstance instance: this.instancesFromTrainingSet) {

				if (instance.equals(center1) || instance.equals(center2) || instance.equals(center3))
					continue;

				PriorityQueue <DistanceItem> queue = new PriorityQueue <DistanceItem>();

				queue.add(new DistanceItem(instance.getDistanceValueForTrainingInstance(center1),center1));
				queue.add(new DistanceItem(instance.getDistanceValueForTrainingInstance(center2),center2));
				queue.add(new DistanceItem(instance.getDistanceValueForTrainingInstance(center3),center3));

				DistanceItem item = queue.poll();
				TrainingInstance closestCenter = item.getTrainingInstance();

				if (closestCenter.equals(center1)) {
					this.cluster1.add(instance);
					this.cluster3.remove(instance);
					this.cluster2.remove(instance);
				}
				if (closestCenter.equals(center2)) {
					this.cluster2.add(instance);
					this.cluster3.remove(instance);
					this.cluster1.remove(instance);
				}
				if (closestCenter.equals(center3)) {
					this.cluster3.add(instance);
					this.cluster1.remove(instance);
					this.cluster2.remove(instance);
				}
			}

			// select new training centers:
			TrainingInstance averageForCluster1 = getInstanceClosestToAverage(getAverageTrainingInstance(cluster1));
			TrainingInstance averageForCluster2 = getInstanceClosestToAverage(getAverageTrainingInstance(cluster2));
			TrainingInstance averageForCluster3 = getInstanceClosestToAverage(getAverageTrainingInstance(cluster3));

			// no changes in any of the centers means that the algorithm needs to stop
			if (this.center1.equals(averageForCluster1) && this.center2.equals(averageForCluster2) && this.center3.equals(averageForCluster3))
				break;
				
			this.center1 = averageForCluster1;
			this.center2 = averageForCluster2;
			this.center3 = averageForCluster3;

		}


	}



	private class DistanceItem implements Comparable <DistanceItem> {

		private double distanceFromNeighbor; // I am this far away
		private TrainingInstance trainingInstance; // from this training instance

		public DistanceItem(double distanceFromNeighbor, TrainingInstance trainingInstance) {
			this.distanceFromNeighbor = distanceFromNeighbor;
			this.trainingInstance = trainingInstance;
		}

		// compareTo method so that Items with smaller values for distance Values
		// are placed at the front of the priority queue:
		@Override
		public int compareTo(DistanceItem item) {

			if (this.distanceFromNeighbor > item.distanceFromNeighbor)
				return 1;
			else
				return -1;
		}

		public TrainingInstance getTrainingInstance() {
			return trainingInstance;
		}

	}

	// GETTERS FOR THE CLUSTER SETS:

	public Set<TrainingInstance> getCluster1() {
		return cluster1;
	}


	public Set<TrainingInstance> getCluster2() {
		return cluster2;
	}


	public Set<TrainingInstance> getCluster3() {
		return cluster3;
	}


}
