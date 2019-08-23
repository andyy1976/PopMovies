package com.example.popmovies.ui.pages.moviedetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.popmovies.database.AppDatabase;

public class MovieDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mTaskId;

    public MovieDetailsViewModelFactory(AppDatabase database, int taskId) {
        mDb = database;
        mTaskId = taskId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mDb, mTaskId);
    }
}
