package user;

import java.util.*;

public class Calculations {
	
	public static double pearsonCorrel(List<MovieContainer> user1, List<MovieContainer> user2)
	{
		List<Double> useroneRatings = new ArrayList<Double>();
		List<Double> usertwoRatings = new ArrayList<Double>();
		double user1Avg=0.0;
		double user2Avg=0.0;
		createSimRatingArrays(useroneRatings, usertwoRatings, user1, user2);
		user1Avg = calcAverage(useroneRatings);
		user2Avg = calcAverage(usertwoRatings);
		return calcSimilarity(useroneRatings, usertwoRatings, user1Avg, user2Avg);
	}
	
	public static void createSimRatingArrays(List<Double> useroneRatings, List<Double> usertwoRatings, 
													List<MovieContainer> user1, List<MovieContainer> user2)
	{
		for(MovieContainer m : user1)
		{
			for(MovieContainer m2 : user2)
			{
				if(m.getMovieID() == m2.getMovieID())
				{
					useroneRatings.add(m.getRating());
					usertwoRatings.add(m2.getRating());
				}
			}
		}
	}
	
	private static double calcSimilarity(List<Double> useroneRatings, List<Double> usertwoRatings, 
																		double user1Avg, double user2Avg)
	{
		double coVar = 0.0;
		double sumOfSqrUser1 = 0.0;
		double sumOfSqrUser2 = 0.0;
		for(int i=0; i<useroneRatings.size(); i++)
		{
			coVar += ((useroneRatings.get(i) - user1Avg) * (usertwoRatings.get(i) - user2Avg));
			sumOfSqrUser1 += Math.pow(useroneRatings.get(i) - user1Avg, 2);
			sumOfSqrUser2 += Math.pow(usertwoRatings.get(i) - user2Avg, 2);
		}
		double stdev = Math.sqrt((sumOfSqrUser1*sumOfSqrUser2));
		return stdev == 0.0 ? 0.0 : coVar / stdev;
	}
	
	private static double calcAverage(List<Double> ratings)
	{
		double sum = 0;
		for(Double i : ratings)
			sum += i;
		return sum/ratings.size();
	}
}