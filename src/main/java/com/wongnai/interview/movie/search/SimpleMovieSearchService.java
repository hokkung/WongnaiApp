package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		List<Movie> result = getMovieFromText(moviesResponse, queryText);

		return result;
	}

	private List<Movie> getMovieFromText(MoviesResponse list, String text) {
		List<Movie> movies = new ArrayList<>();

		for (MovieData movieData : list) {
			String[] arrMovie = movieData.getTitle().split(" ");

			for (String title : arrMovie) {
				if (title.equalsIgnoreCase(text)) {
					// Add actors
					List<String> actors = new ArrayList<>(movieData.getCast());
					// Create movie obj
					Movie movie = new Movie(movieData.getTitle(), actors);
					// Add to list movie
					movies.add(movie);
				}
			}
		}
		return movies;
	}

	private void print(String message) {
		System.out.println(message);
	}
}
