package com.example.popmovies.network.response;

import com.example.popmovies.network.model.Genre;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenreResponse {
    @SerializedName("genres")
    public List<Genre> genres;
}
