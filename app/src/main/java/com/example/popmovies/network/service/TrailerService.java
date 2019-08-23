package com.example.popmovies.network.service;


import com.example.popmovies.network.response.TrailerResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TrailerService {

    @GET("movie/{movie_id}/videos")
    Single<TrailerResponse> getTrailers(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
