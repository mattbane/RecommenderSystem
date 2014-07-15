package user;

import java.util.List;

public class Movie {
	
	private int id;
	private String title;
	private List<String> genre;
	
	public Movie(int id, String title, List<String> genres){
		this.id = id;
		this.title = title;
		this.genre = genres;
	}
	
	public int getId(){
		return id;
	}
	
	public String getTitle(){
		return title;
	}
	
	public List<String> getGenres(){
		return genre;
	}

}
