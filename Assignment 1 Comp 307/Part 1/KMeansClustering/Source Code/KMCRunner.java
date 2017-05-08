package KMeansClustering;

import java.util.Set;

public class KMCRunner {

	/**
	 * Displays how many instances from each class, are in a cluster
	 * @param cluster
	 */
	public static void displayClusterStats (Set<TrainingInstance> cluster) {

		int cluster1Count = 0;
		int cluster2Count = 0;
		int cluster3Count = 0;

		for (TrainingInstance instance: cluster) {
			if (instance.getClassName().equals("Iris-setosa"))
				cluster1Count++;
			if (instance.getClassName().equals("Iris-versicolor"))
				cluster2Count++;
			if (instance.getClassName().equals("Iris-virginica"))
				cluster3Count++;
		}

		System.out.println("Number of Iris-setosa : " + cluster1Count);
		System.out.println("Number of Iris-versicolor : " + cluster2Count);
		System.out.println("Number of Iris-virginica : " + cluster3Count);

	}



	public static void main (String [] args) {
		KMeansClustering kmc = new KMeansClustering ();

		System.out.println("--------------------------------------------------------");
		System.out.println("Cluster 1: " + "\n");
		for (TrainingInstance instance: kmc.getCluster1())
			System.out.println(instance.toString());
		System.out.println("\n");
		displayClusterStats(kmc.getCluster1());
		System.out.println("--------------------------------------------------------");

		System.out.println("\n");

		System.out.println("--------------------------------------------------------");
		System.out.println("Cluster 2: " + "\n");
		for (TrainingInstance instance: kmc.getCluster2())
			System.out.println(instance.toString());
		System.out.println("\n");
		displayClusterStats(kmc.getCluster2());
		System.out.println("--------------------------------------------------------");

		System.out.println("\n");

		System.out.println("--------------------------------------------------------");
		System.out.println("Cluster 3: " + "\n");
		for (TrainingInstance instance: kmc.getCluster3())
			System.out.println(instance.toString());
		System.out.println("\n");
		displayClusterStats(kmc.getCluster3());
		System.out.println("--------------------------------------------------------");

		System.out.println("\n");

	}

}
