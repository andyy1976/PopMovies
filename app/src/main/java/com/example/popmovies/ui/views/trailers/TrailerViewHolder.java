package com.example.popmovies.ui.views.trailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.popmovies.R;
import com.example.popmovies.network.model.Trailer;
import com.example.popmovies.ui.views.trailers.TrailerRecyclerViewAdapter.OnClickPlayButtonListener;
import com.squareup.picasso.Picasso;

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    private ImageView mTrailerImageView;
    private FrameLayout mTrailerContainer;
    private Context mContext;

    public TrailerViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        init(itemView);
    }

    private void init(View itemView) {
        mTrailerImageView = itemView.findViewById(R.id.trailer_image);
        mTrailerContainer = itemView.findViewById(R.id.trailer_container);
    }

    public void bind(final Trailer trailer, final OnClickPlayButtonListener listener) {

        Picasso.with(mContext)
                .load(mContext.getResources().getString(R.string.youTubeImageUrl, trailer.getKey()))
                .error(R.drawable.ic_play)
                .into(mTrailerImageView);

        mTrailerContainer.setOnClickListener((v) -> listener.onClickPlayButton(trailer.getKey()));
    }
}
