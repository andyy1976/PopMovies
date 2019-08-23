package com.example.popmovies.network.service;


import com.example.popmovies.network.response.ReviewResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewService {

    @GET("movie/{movie_id}/reviews")
    Single<ReviewResponse> getReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
}
