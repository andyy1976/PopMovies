package com.example.popmovies.ui.reviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.popmovies.R;
import com.example.popmovies.network.model.Review;

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    private TextView mAuthorTv;
    private TextView mContentTv;
    private View mDividerView;

    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        init(itemView);
    }

    private void init(View itemView) {
        mAuthorTv = itemView.findViewById(R.id.author_tv);
        mContentTv = itemView.findViewById(R.id.content_tv);
        mDividerView = itemView.findViewById(R.id.divider);
    }

    public void bind(final Review review, boolean dividerVisible) {

        mAuthorTv.setText(review.getAuthor());
        mContentTv.setText(review.getContent());
        if (!dividerVisible) mDividerView.setVisibility(View.GONE);
    }
}
