package com.example.popmovies.network;

import com.example.popmovies.BuildConfig;
import com.example.popmovies.network.service.GenreService;
import com.example.popmovies.network.service.MovieService;
import com.example.popmovies.network.service.ReviewService;
import com.example.popmovies.network.service.TrailerService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APICallFactory {
    private static GenreService mGenreService;
    private static MovieService mMovieService;
    private static TrailerService mTrailerService;
    private static ReviewService mReviewService;

    public static void getConfiguration() {
        Retrofit sRetrofit = getRetrofitConfiguration();
        initServices(sRetrofit);
    }

    private static void initServices(Retrofit retrofit) {
        mGenreService = retrofit.create(GenreService.class);
        mMovieService = retrofit.create(MovieService.class);
        mReviewService = retrofit.create(ReviewService.class);
        mTrailerService = retrofit.create(TrailerService.class);
    }

    private static Retrofit getRetrofitConfiguration() {
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static GenreService getGenreService() {
        return mGenreService;
    }

    public static MovieService getMovieService() {
        return mMovieService;
    }

    public static TrailerService getTrailerService() {
        return mTrailerService;
    }

    public static ReviewService getReviewService() {
        return mReviewService;
    }
}
