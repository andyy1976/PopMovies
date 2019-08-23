package com.example.popmovies.network.service;


import com.example.popmovies.network.response.MovieResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/popular")
    Single<MovieResponse> getMostPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Single<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);
}
