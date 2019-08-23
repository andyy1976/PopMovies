package com.example.popmovies.ui.pages.moviedetails;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popmovies.BuildConfig;
import com.example.popmovies.R;
import com.example.popmovies.database.AppDatabase;
import com.example.popmovies.executor.AppExecutors;
import com.example.popmovies.network.model.Movie;
import com.example.popmovies.ui.reviews.ReviewsAdapter;
import com.example.popmovies.ui.views.trailers.TrailerRecyclerViewAdapter;
import com.example.popmovies.utilities.AppUtils;
import com.example.popmovies.utilities.DataFactory;
import com.squareup.picasso.Picasso;

public class MovieDetailsFragment extends Fragment implements TrailerRecyclerViewAdapter.OnClickPlayButtonListener {

    private static final String MOVIE_ID_BUNDLE_KEY = "movie_id";
    private static final String MOVIE_ID = "movie_id";
    private static final String YOU_TUBE_BASE_URL = "http://www.youtube.com/watch?v=";

    private View mRootView;
    private TextView mMovieTitleTv;
    private ImageView mPosterIv;
    private TextView mReleaseDateTv;
    private TextView mRatingTv;
    private TextView mOverviewTv;
    private TextView mPopularityTv;
    private TextView mGenreTitleTv;
    private TextView mGenresTv;
    private TextView mOriginalTitleTv;
    private TextView mOriginalLanguageTv;
    private TextView mTrailersLabel;
    private RecyclerView mTrailers;
    private TextView mReviewsLabel;
    private RecyclerView mReviews;
    private TextView mFavButton;

    private int mMovieId;
    private AppDatabase mAppDatabase;
    private boolean isFavoriteMovie = false;
    private MovieDetailsViewModel mViewModel;

    public static MovieDetailsFragment newInstance(int movieId) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(MOVIE_ID_BUNDLE_KEY, movieId);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        if (savedInstanceState != null) {
            mMovieId = savedInstanceState.getInt(MOVIE_ID);
        } else {
            if (getArguments() != null)
                mMovieId = getArguments().getInt(MOVIE_ID_BUNDLE_KEY);
        }

        initViews();
        setValues();
        setupMovieDetailsViewModel();

        return mRootView;
    }

    public void setupMovieDetailsViewModel() {
        MovieDetailsViewModelFactory factory = new MovieDetailsViewModelFactory(mAppDatabase, mMovieId);

        mViewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);

        mViewModel.getFavorite().observe(this, movie -> {
            isFavoriteMovie = movie != null;
            setTextToFavoriteButton();
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(MOVIE_ID, mMovieId);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void setAppDatabase(AppDatabase db) {
        mAppDatabase = db;
    }

    private void setValues() {
        final Movie movie = DataFactory.findMovie(mMovieId);
        if (movie != null) {
            String notAvailableInfo = getResources().getString(R.string.not_available);
            mMovieTitleTv.setText(movie.getTitle().isEmpty() ? notAvailableInfo : movie.getTitle());

            Picasso.with(getContext())
                    .load(BuildConfig.IMAGE_TMDB_URL + movie.getPosterPath())
                    .error(android.R.drawable.ic_menu_camera)
                    .into(mPosterIv);

            mReleaseDateTv.setText(movie.getReleaseDate() != null ? AppUtils.formatDate(movie.getReleaseDate()) : notAvailableInfo);

            mRatingTv.setText(getResources().getString(R.string.rating_text, AppUtils.formatDouble(movie.getVoteAverage())));

            mOverviewTv.setText(movie.getOverview().isEmpty() ? notAvailableInfo : movie.getOverview());

            mPopularityTv.setText("" + movie.getPopularity());

            if (movie.getGenreIds().length > 1) {
                mGenreTitleTv.setText(getResources().getString(R.string.genres));
            } else {
                mGenreTitleTv.setText(getResources().getString(R.string.genre));
            }

            if (movie.getGenreIds().length > 0) {
                mGenresTv.setText(formatGenres(movie.getGenreIds()));
            } else {
                mGenresTv.setText(notAvailableInfo);
            }

            mOriginalTitleTv.setText(movie.getOriginalTitle().isEmpty() ? notAvailableInfo : movie.getOriginalTitle());
            mOriginalLanguageTv.setText(movie.getOriginalLanguage().isEmpty() ? notAvailableInfo : AppUtils.formatLanguage(movie.getOriginalLanguage()));

            setTextToFavoriteButton();
            mFavButton.setOnClickListener((v) -> {
                processFavoriteMovie(movie);
                setTextToFavoriteButton();
            });

            if (!DataFactory.getYouTubeTrailerList().isEmpty()) {
                mTrailers.setAdapter(new TrailerRecyclerViewAdapter(DataFactory.getYouTubeTrailerList(), this));
            } else {
                mTrailersLabel.append(" " + notAvailableInfo);
            }

            if (!DataFactory.getReviewList().isEmpty()) {
                mReviews.setAdapter(new ReviewsAdapter(DataFactory.getReviewList()));
            } else {
                mReviewsLabel.append(" " + notAvailableInfo);
            }

        }
    }

    public void processFavoriteMovie(Movie favorite) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (isFavoriteMovie) {
                mAppDatabase.favoriteDao().deleteFavorite(favorite);
            } else {
                mAppDatabase.favoriteDao().insertFavorite(favorite);
            }
        });
    }

    private void setTextToFavoriteButton() {
        mFavButton.setText(isFavoriteMovie ? getString(R.string.favorite_label) : getString(R.string.mark_favorite_label));
    }

    private String formatGenres(int[] genres) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < genres.length; i++) {
            stringBuilder.append(DataFactory.findGenreName(genres[i]));
            if (i < genres.length - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    private void initViews() {
        mMovieTitleTv = mRootView.findViewById(R.id.movie_title);
        mPosterIv = mRootView.findViewById(R.id.movie_poster);
        mReleaseDateTv = mRootView.findViewById(R.id.movie_release_date);
        mRatingTv = mRootView.findViewById(R.id.movie_rating);
        mOverviewTv = mRootView.findViewById(R.id.movie_overview);
        mPopularityTv = mRootView.findViewById(R.id.movie_popularity);
        mGenreTitleTv = mRootView.findViewById(R.id.movie_genre_title);
        mGenresTv = mRootView.findViewById(R.id.movie_genre);
        mOriginalTitleTv = mRootView.findViewById(R.id.movie_original_title);
        mOriginalLanguageTv = mRootView.findViewById(R.id.movie_original_language);
        mFavButton = mRootView.findViewById(R.id.movie_fav_button);

        mTrailersLabel = mRootView.findViewById(R.id.trailers_label);
        mTrailers = mRootView.findViewById(R.id.trailers);
        mTrailers.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mReviewsLabel = mRootView.findViewById(R.id.reviews_label);
        mReviews = mRootView.findViewById(R.id.reviews);
        mReviews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClickPlayButton(String trailerKey) {
        watchYoutubeVideo(requireContext(), trailerKey);
    }

    private void watchYoutubeVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOU_TUBE_BASE_URL + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
