package com.example.popmovies.ui.views.trailers;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popmovies.R;
import com.example.popmovies.network.model.Trailer;

import java.util.List;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private List<Trailer> mTrailerList;
    private OnClickPlayButtonListener mPlayButtonListener;

    public TrailerRecyclerViewAdapter(List<Trailer> trailerList, OnClickPlayButtonListener playButtonListener) {
        mTrailerList = trailerList;
        mPlayButtonListener = playButtonListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int position) {
        trailerViewHolder.bind(mTrailerList.get(position), mPlayButtonListener);
    }

    @Override
    public int getItemCount() {
        return mTrailerList.size();
    }

    public interface OnClickPlayButtonListener {
        void onClickPlayButton(String trailerUrl);
    }
}
