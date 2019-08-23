package com.example.popmovies.network.response;

import com.example.popmovies.network.model.Trailer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {
    @SerializedName("results") public List<Trailer> trailers;
}
