package com.example.popmovies.network.service;


import com.example.popmovies.network.response.GenreResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenreService {

    @GET("genre/movie/list")
    Single<GenreResponse> getGenres(@Query("api_key") String apiKey);
}
