package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import user.*;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;

public class Recommend implements ActionListener{
	List<MovieContainer> userRatings; 
	Map<JLabel,ButtonGroup> selections;
	MovieFrame frame = new MovieFrame();
	
	public Recommend(Map<JLabel,ButtonGroup> selections){
		this.selections = selections;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Calculate")){
			userRatings = new ArrayList<MovieContainer>();
			for(Map.Entry<JLabel, ButtonGroup> select : selections.entrySet()){
				String rating = select.getValue().getSelection().getActionCommand();
				String movieTitle = select.getKey().getText();
				int movieID = Integer.parseInt(select.getKey().getName());
				System.out.println(movieID + " " + movieTitle + " " + rating);
				userRatings.add(new MovieContainer(movieID, Double.parseDouble(rating)));
			}
			List<MovieContainer> newUserPredictions = Prediction.predictUnratedMovies(findKNearestClusters(), userRatings);
			for(MovieContainer mc : newUserPredictions){
				System.out.println(MainGui.movieInfo.get(mc.getMovieID()).getTitle() + " " + mc.getMovieID() + ": " + mc.getRating());
			}

			Collections.sort(newUserPredictions);
			frame.presentRatingsGui(newUserPredictions);
		}
	}
	
	private List<SimilarityScores> findKNearestClusters(){
		int k = 3;
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		List<SimilarityScores> sim = new ArrayList<SimilarityScores>();
		
		for(Map.Entry<Integer, List<MovieContainer>> clusterEntry : SinglePassClustering.clusters.entrySet())
		{
			double num = Calculations.pearsonCorrel(userRatings, clusterEntry.getValue());
			sim.add(new SimilarityScores(clusterEntry.getKey(), num));
		}
		Collections.sort(sim);
		return sim.subList(0, k);
	}
	
}
