package com.example.popmovies.ui.views.movies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.popmovies.BuildConfig;
import com.example.popmovies.R;
import com.example.popmovies.network.model.Movie;
import com.example.popmovies.ui.views.movies.MovieRecyclerViewAdapter.OnMovieClickListener;
import com.squareup.picasso.Picasso;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView mPosterIv;
    private Context mContext;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        mContext = itemView.getContext();
        mPosterIv = itemView.findViewById(R.id.poster);
    }

    public void bind(final Movie movie, final OnMovieClickListener clickListener) {
        Picasso.with(mContext)
                .load(BuildConfig.IMAGE_TMDB_URL + movie.getPosterPath())
                .error(android.R.drawable.ic_menu_camera)
                .into(mPosterIv);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickListener != null) {
                    clickListener.onMovieClicked(movie.getId());
                }
            }
        });
    }
}
