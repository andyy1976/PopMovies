package com.example.popmovies.utilities;

import com.example.popmovies.network.model.Genre;
import com.example.popmovies.network.model.Movie;
import com.example.popmovies.network.model.Review;
import com.example.popmovies.network.model.Trailer;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    private static ArrayList<Movie> mMovieList;
    private static ArrayList<Genre> mGenreList;
    private static List<Trailer> mTrailersList;
    private static List<Review> mReviewList;

    private static final String YOU_TUBE = "YouTube";
    private static final String TRAILER = "Trailer";

    public static void setGenres(List<Genre> genres) {
        mGenreList = new ArrayList<>(genres);
    }

    public static void setMovies(List<Movie> movies) {
        mMovieList = new ArrayList<>(movies);
    }

    public static void setTrailers(List<Trailer> trailers) {
        mTrailersList = trailers;
    }

    public static void setReviews(List<Review> reviews) {
        mReviewList = reviews;
    }

    public static ArrayList<Movie> getMovieList() {
        return mMovieList;
    }

    public static ArrayList<Genre> getGenreList() {
        return mGenreList;
    }

    public static List<Review> getReviewList() {
        return mReviewList;
    }

    public static Movie findMovie(int movieId) {
        if (!mMovieList.isEmpty()) {
            for (Movie movie : mMovieList) {
                if (movie.getId() == movieId)
                    return movie;
            }
        }
        return null;
    }

    public static String findGenreName(int genreId) {
        if (!mGenreList.isEmpty()) {
            for (Genre genre : mGenreList) {
                if (genre.getId() == genreId)
                    return genre.getName();
            }
        }
        return null;
    }

    public static List<Trailer> getYouTubeTrailerList() {
        List<Trailer> trailers = new ArrayList<>();
        for (Trailer trailer : mTrailersList) {
            if (YOU_TUBE.toLowerCase().equals(trailer.getSite().toLowerCase()) && TRAILER.toLowerCase().equals(trailer.getType().toLowerCase())) {
                trailers.add(trailer);
            }
        }
        return trailers;
    }
}
