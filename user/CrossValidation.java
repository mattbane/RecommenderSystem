package user;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CrossValidation  {
	
	public static void start() throws IOException {
		Map<Integer, List<MovieContainer>> userData;
		Map<Integer, List<MovieContainer>> testData;
		List<SimilarityScores> sim;
		int k = 3;
		double errors = 0.0;
		double totalPredictions = 0.0;
		double crossFoldVal = 0.0;
		//CrossValidation directory should live in the directory that contains your src directory
		File folder = new File("CrossValidation");
		File[] files = folder.listFiles();
		int j=0;
		for(int i=0; i<5; i++){
			System.out.println("Processing files: " + files[j].getPath() + " and " + files[j+1].getPath());
			testData = ReadData.processUserData(files[j]);
			userData = ReadData.processUserData(files[j+1]);
			j += 2;
			
			SinglePassClustering.createClusters(userData);			
			Kmeans.performKmeans(userData);
			
			System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
			
			for(Map.Entry<Integer, List<MovieContainer>> entry1 : testData.entrySet())
			{
				sim = new ArrayList<SimilarityScores>();
				totalPredictions += entry1.getValue().size();
				for(Map.Entry<Integer, List<MovieContainer>> entry2 : SinglePassClustering.clusters.entrySet())
				{
					if(entry1.getKey() != entry2.getKey()){
						double num = Calculations.pearsonCorrel(entry1.getValue(), entry2.getValue());
						sim.add(new SimilarityScores(entry2.getKey(), num));
					}
				}
				Collections.sort(sim);
				List<SimilarityScores> knn = sim.subList(0, k);
				errors += Prediction.predictRatedMovies(userData, knn, entry1);
			}
			crossFoldVal += errors / totalPredictions;
			System.out.println("The MAE for " + files[j-2].getPath() + " is: " + (errors / totalPredictions));
		}
		System.out.println("The MAE for 5 fold cross validation is: " + (crossFoldVal / 5));
	}
}
