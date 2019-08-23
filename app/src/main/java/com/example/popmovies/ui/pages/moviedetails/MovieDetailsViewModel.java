package com.example.popmovies.ui.pages.moviedetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.popmovies.database.AppDatabase;
import com.example.popmovies.network.model.Movie;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<Movie> mFavorite;

    public MovieDetailsViewModel(AppDatabase database, int taskId) {
        mFavorite = database.favoriteDao().findFavoriteById(taskId);
    }

    public LiveData<Movie> getFavorite() {
        return mFavorite;
    }
}
