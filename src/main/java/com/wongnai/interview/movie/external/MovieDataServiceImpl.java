package com.wongnai.interview.movie.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class MovieDataServiceImpl implements MovieDataService {
	public static final String MOVIE_DATA_URL
			= "https://raw.githubusercontent.com/";

	@Autowired
	private RestOperations restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public MoviesResponse fetchAll() {
		//TODO:
		// Step 1 => Implement this method to download data from MOVIE_DATA_URL and fix any error you may found.
		// Please noted that you must only read data remotely and only from given source,
		// do not download and use local file or put the file anywhere else.

		// Init response
		MoviesResponse moviesResponse = null;

		try {
		// Create retrofit
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(MOVIE_DATA_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		// Create interface
		IMovieData apis = retrofit.create(IMovieData.class);

		// Requests
		Call<MoviesResponse> allMovies = apis.getAllMovies();
		Response<MoviesResponse> res = allMovies.execute();
		moviesResponse = res.body();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return moviesResponse;
	}
}
