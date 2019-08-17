package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository

        // Request get apis
		MoviesResponse moviesResponse = movieDataService.fetchAll();
		// Create list movies
        List<Movie> movies = new ArrayList<>();

		for (MovieData movieData : moviesResponse) {
		    // Create movie data
		    Movie movie = createMovieData(movieData);
            // Add to list
            movies.add(movie);
        }
		// Save to database
        movieRepository.saveAll(movies);

	}

	private Movie createMovieData(MovieData movieData) {
        // Create movie obj
	    return new Movie(movieData.getTitle(), movieData.getCast());
    }

}
