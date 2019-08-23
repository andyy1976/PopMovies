package com.example.popmovies.ui.reviews;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popmovies.R;
import com.example.popmovies.network.model.Review;
import com.example.popmovies.ui.reviews.ReviewViewHolder;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private List<Review> mReviewList;

    public ReviewsAdapter(List<Review> reviewList) {
        mReviewList = reviewList;
    }

    @NonNull
    @Override
    public com.example.popmovies.ui.reviews.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item, viewGroup, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder trailerViewHolder, int position) {
        trailerViewHolder.bind(mReviewList.get(position), position < mReviewList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mReviewList.size();
    }
}
