package user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Kmeans 
{	
	public static void performKmeans(Map<Integer, List<MovieContainer>> userData){
		System.out.println("Performing Kmeans clustering on the clusters created by Single Pass Clustering.");
		Map<Integer, Double> cosineSimScores;
		double cosineSim = 0.0;
		boolean clustersChanged = true;
		int iterations =0;
		while(clustersChanged && iterations < 2000){
			clustersChanged = false;
			for(Map.Entry<Integer, List<MovieContainer>> entry : userData.entrySet()){
				
				cosineSimScores = new HashMap<Integer, Double>();
				double normEntry = SinglePassClustering.findNorm(entry.getValue());
				
				for(Map.Entry<Integer, List<MovieContainer>> cluster : SinglePassClustering.clusters.entrySet()){
					
					List<Double> comparableRatings1 = new ArrayList<Double>();
					List<Double> comparableRatings2 = new ArrayList<Double>();
	
					double normCluster = SinglePassClustering.findNorm(cluster.getValue());
					Calculations.createSimRatingArrays(comparableRatings1, comparableRatings2, 
																entry.getValue(), cluster.getValue());
					double numerator = SinglePassClustering.dotProduct(comparableRatings1, comparableRatings2);
					cosineSim = numerator/Math.sqrt(normEntry*normCluster);
					cosineSimScores.put(cluster.getKey(), cosineSim);
				}
				//find the cluster in which the user is most similar
				int index = SinglePassClustering.findMaxClusterIndex(cosineSimScores);
				//update the Map of clusters to userID
				if(!SinglePassClustering.clusterbyUser.get(index).contains(entry.getKey())){
					for(Map.Entry<Integer, List<Integer>> cbu : SinglePassClustering.clusterbyUser.entrySet())
						if(cbu.getValue().contains(entry.getKey())){ cbu.getValue().remove(entry.getKey()); }
					SinglePassClustering.clusterbyUser.get(index).add(entry.getKey());
					clustersChanged = true;
					iterations += 1;
				}
			}
			//calc new centroids
			Set<Integer> clusterKeys = SinglePassClustering.clusters.keySet();
			for(Integer key : clusterKeys)
				SinglePassClustering.calcNewCentroid(userData, key);				
		}
	}
}
