package com.example.popmovies.ui.views.movies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popmovies.R;
import com.example.popmovies.network.model.Movie;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private OnMovieClickListener mClickListener;
    private List<Movie> mMovieList;

    public MovieRecyclerViewAdapter(List<Movie> movieList, OnMovieClickListener listener) {
        mClickListener = listener;
        mMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        //inflate recycler view item layout and instantiate view holder
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        movieViewHolder.bind(mMovieList.get(position), mClickListener);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public interface OnMovieClickListener {
        void onMovieClicked(int movieId);
    }
}
