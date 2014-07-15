package user;

public class SimilarityScores implements Comparable<SimilarityScores>{
	private int userID = 0;
	private double simScore = 0;
	
	public SimilarityScores(int userID, double simScore){
		this.userID = userID;
		this.simScore = simScore;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public double getSimScore(){
		return simScore;
	}

	@Override
	public int compareTo(SimilarityScores o) {
		int i = 0;
		if(o != null)
			i = (this.simScore - o.simScore) == 0 ? 0 : (this.simScore - o.simScore) < 0 ? 1 : -1;
		else 
			throw new IllegalArgumentException();
		return i;
	}

}
