package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.*;

import user.*;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class MoviePanel extends JPanel {
	Map<JLabel, ButtonGroup> labels = new HashMap<JLabel, ButtonGroup>();
	Map<Integer, Movie> movieTitles;
	
	public MoviePanel(Map<Integer, Movie> movieTitles){
		this.movieTitles = movieTitles;
		createPanel();
	}
	
	private void createPanel(){
		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(movieTitles.size(), 6, 0, 0));
		topPanel.add(new JLabel("Please rate the following movies."));
		JButton button = new JButton("Calculate");
		button.setActionCommand("Calculate");
		bottomPanel.add(button);
		button.addActionListener(new Recommend(labels));
		
		for(Map.Entry<Integer, Movie> movie : movieTitles.entrySet()){
			JLabel movieName = new JLabel(movie.getValue().getTitle());
			movieName.setName(movie.getKey().toString());
			centerPanel.add(movieName);
			
			JRadioButton rating1 = new JRadioButton("1");
			rating1.setActionCommand("1");
			centerPanel.add(rating1);
			JRadioButton rating2 = new JRadioButton("2");
			rating2.setActionCommand("2");
			centerPanel.add(rating2);
			JRadioButton rating3 = new JRadioButton("3");
			rating3.setActionCommand("3");
			centerPanel.add(rating3);
			JRadioButton rating4 = new JRadioButton("4");
			rating4.setActionCommand("4");
			centerPanel.add(rating4);
			JRadioButton rating5 = new JRadioButton("5");
			rating5.setActionCommand("5");
			centerPanel.add(rating5);
			
			ButtonGroup gp = new ButtonGroup();
			gp.add(rating1);
			gp.add(rating2);
			gp.add(rating3);
			gp.add(rating4);
			gp.add(rating5);
			
			labels.put(movieName, gp);
		}
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
	}
}
