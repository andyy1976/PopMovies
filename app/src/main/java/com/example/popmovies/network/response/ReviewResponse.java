package com.example.popmovies.network.response;

import com.example.popmovies.network.model.Review;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {
    @SerializedName("results") public List<Review> reviews;
}
