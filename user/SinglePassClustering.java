package user;

import java.util.*;

public class SinglePassClustering 
{
	//clusters with centroids
	public static Map<Integer, List<MovieContainer>> clusters; 
	//cluster index with users who make up cluster
	public static Map<Integer, List<Integer>> clusterbyUser; 
	public static double threshold = 0.2;
	
	public static void createClusters(Map<Integer, List<MovieContainer>> userData)
	{
		System.out.println("Performing Single Pass Clustering on the user data.");
		int key = 1;
		clusters = new HashMap<Integer, List<MovieContainer>>();
		clusterbyUser = new HashMap<Integer, List<Integer>>();
		Map<Integer, Double> cosineSimScores;
		Integer firstCluster = userData.keySet().iterator().next();
		clusters.put(key, userData.get(firstCluster));
		List<Integer> userIndex = new ArrayList<Integer>();
		userIndex.add(firstCluster);
		clusterbyUser.put(key, userIndex);
		double cosineSim = 0.0;
		for(Map.Entry<Integer, List<MovieContainer>> entry : userData.entrySet()){
			
			if(entry.getKey() == 1) { continue; }
			cosineSimScores = new HashMap<Integer, Double>();
			double normEntry = findNorm(entry.getValue());
			
			for(Map.Entry<Integer, List<MovieContainer>> cluster : clusters.entrySet()){
				
				List<Double> comparableRatings1 = new ArrayList<Double>();
				List<Double> comparableRatings2 = new ArrayList<Double>();

				double normCluster = findNorm(cluster.getValue());
				Calculations.createSimRatingArrays(comparableRatings1, comparableRatings2, 
															entry.getValue(), cluster.getValue());
				double numerator = dotProduct(comparableRatings1, comparableRatings2);
				cosineSim = numerator/Math.sqrt(normEntry*normCluster);
				if(cosineSim >= threshold) { cosineSimScores.put(cluster.getKey(), cosineSim); }
			}
			
			if(cosineSimScores.size() != 0){
				int index = findMaxClusterIndex(cosineSimScores); 
				clusterbyUser.get(index).add(entry.getKey());
				calcNewCentroid(userData, index);
			}else{
				clusters.put(++key, entry.getValue());
				List<Integer> usersByIndex = new ArrayList<Integer>();
				usersByIndex.add(entry.getKey());
				clusterbyUser.put(key, usersByIndex);
			}
		}
		System.out.println(clusters.size() + " clusters were created by Single Pass Clustering. With a threshold of " 
										+ threshold);
	}
	
	public static double findNorm(List<MovieContainer> ratings){
		double norm = 0.0;
		for(MovieContainer mc : ratings)
			norm += Math.pow(mc.getRating(), 2);
		return norm;
	}
	
	public static double dotProduct(List<Double> array1, List<Double> array2){
		double vectorsSim = 0.0;
		for(int i=0; i<array1.size(); i++)
			vectorsSim += array1.get(i) * array2.get(i);
		return vectorsSim;
	}
	
	public static int findMaxClusterIndex(Map<Integer, Double> simScores){
		double max=Double.MIN_VALUE;
		int clusterIndex = 0;
		for(Map.Entry<Integer, Double> entry : simScores.entrySet()){
			if(entry.getValue() >= max) {
				max = entry.getValue();
				clusterIndex = entry.getKey();
			} 
		}
		return clusterIndex;
	}
	
	public static void calcNewCentroid(Map<Integer, List<MovieContainer>> data, int index){
		//1682 possible items
		List<MovieContainer> centroid = new ArrayList<MovieContainer>();
		List<Integer> usersInCluster = clusterbyUser.get(index);
 		for(int i=1; i<=1682; i++){ //need to find a better way 
 			double sum=0.0;
 			int numOfUsers=0;
			for(Integer user : usersInCluster){
				if(data.get(user).contains(new MovieContainer(i, 0))){
					int movieIndex = data.get(user).indexOf(new MovieContainer(i, 0));
					sum += data.get(user).get(movieIndex).getRating();
					numOfUsers += 1;
				}
			}
			if(sum == 0) { continue; }
			sum /= numOfUsers; 
			centroid.add(new MovieContainer(i, sum));
		}
 		clusters.put(index, centroid);
	}
}
