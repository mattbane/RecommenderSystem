package gui;

import user.*;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.text.DecimalFormat;
import java.util.*;

public class RatingsPanel extends JPanel {
	List<MovieContainer> usersRatings;
	
	public RatingsPanel(List<MovieContainer> usersRatings){
		this.usersRatings = usersRatings;
		createPanel();
	}
	
	private void createPanel(){
		JTextArea text = new JTextArea("I think you might like the following movies."
				+ "  User user collaborative filtering with pearsons correlation as the similarity"
				+ " measure was used to generate the predicted ratings for each movie."
				+ "  Single pass clustering was performed on the database of movie ratings to "
				+ "create the initial clusters.  These clusters were then reassigned using kmeans "
				+ "clustering.\n\n", 26, 70);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		text.append("Top 20 movies recommended for you: \n");
		DecimalFormat df = new DecimalFormat("#.#");
		for(int i=0; i<20; i++)
			text.append(MainGui.movieInfo.get(usersRatings.get(i).getMovieID()).getTitle() + ":	" 
						+ df.format(usersRatings.get(i).getRating()) +  "\n");
		add(text);
	}
}
