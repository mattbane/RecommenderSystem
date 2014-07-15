package user;

import java.util.*;
import java.io.*;

public class ReadData {	
	
	public static Map<Integer, List<MovieContainer>> processUserData(File file) throws FileNotFoundException, IOException {
		Map<Integer, List<MovieContainer>> userData = new HashMap<Integer, List<MovieContainer>>();
		BufferedReader br = new BufferedReader(new FileReader(file));
		String data = null;
		while((data = br.readLine()) != null) {
			String [] items = data.split("\t");
			MovieContainer mc = 
					new MovieContainer(Integer.parseInt(items[1]), Integer.parseInt(items[2]));
			if(!userData.containsKey(Integer.parseInt(items[0])))
			{
				List<MovieContainer> movies = new LinkedList<MovieContainer>();
				movies.add(mc);
				userData.put(Integer.parseInt(items[0]), movies);
			}
			else	
				userData.get(Integer.parseInt(items[0])).add(mc);
		}
		return userData;
	}
	
	public static List<String> processGenre(File file) throws FileNotFoundException, IOException {
		List<String> genre = new ArrayList<String>();
	
		BufferedReader br = new BufferedReader(new FileReader(file));
		String data = null;
		while((data = br.readLine()) != null) {
			String [] items = data.split("\t");
			genre.add(Integer.parseInt(items[1]), items[0]);
		}
		return genre;
	}
	
	public static Map<Integer, Movie> processMovieItems(File file, List<String> genre) throws FileNotFoundException, IOException {
		Map<Integer, Movie> movies = new HashMap<Integer, Movie>();
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		String data = null;
		while((data = br.readLine()) != null) {
			String [] items = data.split("\t");
			List<String> movieGenre = new ArrayList<String>();
			
			for(int i=5; i<24; i++){
				if(items[i].equals("1"))
					movieGenre.add(genre.get(i-5));
			}
			Movie m = new Movie(Integer.parseInt(items[0]), items[1], movieGenre);
			movies.put(Integer.parseInt(items[0]), m);
		}
		return movies;
	}
}
