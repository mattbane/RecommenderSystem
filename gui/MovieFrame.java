package gui;

import javax.swing.JFrame;

import user.*;

import java.awt.Color;
import java.util.*;

public class MovieFrame extends JFrame {	
	
	public void getRatingsGui(Map<Integer, Movie> movieTitles){
		JFrame frame = new JFrame("Movie Ratings");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new MoviePanel(movieTitles));
		frame.pack();
		frame.setVisible(true);
	}
	
	public void presentRatingsGui(List<MovieContainer> userPredictedRatings){
		JFrame frame = new JFrame("Movie Ratings");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new RatingsPanel(userPredictedRatings));
		frame.pack();
		frame.setVisible(true);
	}
}
