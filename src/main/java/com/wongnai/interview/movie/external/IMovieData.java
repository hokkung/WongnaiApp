package com.wongnai.interview.movie.external;



import retrofit2.Call;
import retrofit2.http.GET;

public interface IMovieData {
    @GET("prust/wikipedia-movie-data/master/movies.json")
    Call<MoviesResponse> getAllMovies();
}
