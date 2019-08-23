package com.example.popmovies.network.response;

import com.example.popmovies.network.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("results") public List<Movie> movies;
}
