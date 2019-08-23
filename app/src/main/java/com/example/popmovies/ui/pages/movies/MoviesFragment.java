package com.example.popmovies.ui.pages.movies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popmovies.MainActivity;
import com.example.popmovies.R;
import com.example.popmovies.network.model.Movie;
import com.example.popmovies.ui.views.movies.MovieRecyclerViewAdapter;

import java.util.ArrayList;

public class MoviesFragment extends Fragment implements MovieRecyclerViewAdapter.OnMovieClickListener {
    private static final String MOVIES = "movies";
    private static final int NUMBER_OF_COLUMNS = 2;

    private ArrayList<Movie> mMovieList;

    public MoviesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIES)) {
            if (getArguments() != null)
                mMovieList = getArguments().getParcelableArrayList(MainActivity.MOVIES_BUNDLE_KEY);
        } else {
            mMovieList = savedInstanceState.getParcelableArrayList(MOVIES);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(MOVIES, mMovieList);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        RecyclerView moviesRv = (RecyclerView) inflater.inflate(
                R.layout.fragment_movies, container, false);

        moviesRv.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS));
        moviesRv.setAdapter(new MovieRecyclerViewAdapter(mMovieList, this));

        return moviesRv;
    }

    @Override
    public void onMovieClicked(int movieId) {
        ((MainActivity) requireActivity()).loadMovieDetails(movieId);
    }
}
