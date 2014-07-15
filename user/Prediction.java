package user;

import java.util.*;
import java.util.Map;

public class Prediction {
	
	public static double predictRatedMovies(Map<Integer, List<MovieContainer>> userData, 
										List<SimilarityScores> scores, Map.Entry<Integer, List<MovieContainer>> user){
		List<MovieContainer> userToPredictRatings = user.getValue();
		double mae = 0.0;
		double prediction = 0.0;
		double userToPredictAvg = calAverage(userToPredictRatings);
		//System.out.println("Avg for user " + user.getKey() + ": " + userToPredictAvg);
		for(MovieContainer mc : userToPredictRatings){
			double numerator = 0;
			double denominator = 0;
			for(SimilarityScores sc : scores){
				List<MovieContainer> neighborRatings = userData.get(sc.getUserID());
				double neighborAvgRating = calAverage(neighborRatings);
				//System.out.println("Avg for user " + sc.getUserID() + ": " + neighborAvgRating);
				for(MovieContainer nc : neighborRatings) {
					if(nc.getMovieID() == mc.getMovieID())
					{
						numerator +=  (nc.getRating() - neighborAvgRating) * sc.getSimScore();
						denominator += sc.getSimScore();
					}
				}
			}
			if(denominator == 0)
				prediction = userToPredictAvg;
			else
				prediction = userToPredictAvg + (numerator / denominator);
			//System.out.println("Prediction for " + user.getKey() + ": " + prediction);
			mae += Math.abs(prediction - mc.getRating());
		}
		return mae;
	}
	
	public static List<MovieContainer> predictUnratedMovies(List<SimilarityScores> scores, List<MovieContainer> user){
		List<MovieContainer> predictions = new ArrayList<MovieContainer>();
		double prediction = 0.0;
		double userToPredictAvg = calAverage(user);
		for(int i=1; i<=1682; i++){
			double numerator = 0;
			double denominator = 0;
			MovieContainer fakeMovieContainer =new MovieContainer(i,0);
			if(user.contains(fakeMovieContainer)) { continue; }
			for(SimilarityScores sc : scores){
				List<MovieContainer> cluster = SinglePassClustering.clusters.get(sc.getUserID());
				if(cluster.contains(fakeMovieContainer)){
					double neighborAvgRating = calAverage(cluster);
					numerator +=  (cluster.get(cluster.indexOf(fakeMovieContainer)).getRating() - neighborAvgRating) * sc.getSimScore();
					denominator += sc.getSimScore();
				}
			}
			if(denominator == 0)
				prediction = 0;
			else
				prediction = userToPredictAvg + (numerator / denominator);
			predictions.add(new MovieContainer(i, prediction));
		}
		return predictions;
	}
	
	private static double calAverage(List<MovieContainer> movieContainers){
		double avg = 0.0;
		for(MovieContainer mc : movieContainers){
			avg += mc.getRating();
		}
		return avg / movieContainers.size();
	}
	
}
