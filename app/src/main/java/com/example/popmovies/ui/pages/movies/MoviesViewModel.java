package com.example.popmovies.ui.pages.movies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.popmovies.database.AppDatabase;
import com.example.popmovies.network.model.Movie;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private static final String TAG = MoviesViewModel.class.getSimpleName();
    private LiveData<List<Movie>> mFavorites;

    public MoviesViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the favorites from the DataBase");
        mFavorites = database.favoriteDao().getAllFavorite();
    }

    public LiveData<List<Movie>> getFavorites() {
        return mFavorites;
    }
}
