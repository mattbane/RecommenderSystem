package gui;

import user.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainGui {
	static Map<Integer, Movie> movieInfo;
	static Map<Integer, List<MovieContainer>> usersData;
	public static void main(String[] args) throws IOException{
		if(args.length < 3) { 
			System.out.println("Usage: <genre file> <movie info file> <user data file>");
			return;
		}
		File genreFile = new File(args[0]);
		File movieFile = new File(args[1]);
		File usersFile = new File(args[2]); 
 		List<String> genre = ReadData.processGenre(genreFile);
 		movieInfo = ReadData.processMovieItems(movieFile, genre);
 		final Map<Integer, Movie> userMovieInfo = createRandomMovieSample(movieInfo);
 		usersData = ReadData.processUserData(usersFile);
 		SinglePassClustering.createClusters(usersData);			
		Kmeans.performKmeans(usersData);
		
		final MovieFrame frame = new MovieFrame();
	
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				frame.getRatingsGui(userMovieInfo);
			}
		});
	}
	
	private static Map<Integer, Movie> createRandomMovieSample(Map<Integer, Movie> movieInfo){
		Map<Integer, Movie> userRandomSelection = new HashMap<Integer, Movie>();
		Random random = new Random();
		for(int i=0; i<20; i++){
			int randomNum = random.nextInt((1682-1) + 1) + 1;
			if(userRandomSelection.containsKey(randomNum)) { continue; }
			userRandomSelection.put(randomNum, movieInfo.get(randomNum));
		}
		return userRandomSelection;
	}
}
