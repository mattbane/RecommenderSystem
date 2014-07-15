package user;

public class MovieContainer implements Comparable<MovieContainer>{
	private int movieID;
	private double rating;
	
	public MovieContainer(int movieID, double rating){
		this.movieID = movieID;
		this.rating = rating;
	}
	
	public int getMovieID(){
		return movieID;
	}
	
	public double getRating(){
		return rating;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + movieID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MovieContainer)) {
			return false;
		}
		MovieContainer other = (MovieContainer) obj;
		if (movieID != other.movieID) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(MovieContainer o) {
		int i = 0;
		if(o != null)
			i = (this.rating - o.rating) == 0 ? 0 : (this.rating - o.rating) < 0 ? 1 : -1;
		else 
			throw new IllegalArgumentException();
		return i;
	}
}
