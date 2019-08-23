package com.example.popmovies.network.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorite")
public class Movie implements Parcelable {
    @PrimaryKey
    private int id;

    private String title;

    @SerializedName("original_title")
    @ColumnInfo(name="original_title")
    private String originalTitle;

    @SerializedName("original_language")
    @ColumnInfo(name="original_language")
    private String originalLanguage;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    @ColumnInfo(name="vote_average")
    private double voteAverage;

    private double popularity;

    @SerializedName("poster_path")
    @ColumnInfo(name="poster_path")
    private String posterPath;


    @SerializedName("backdrop_path")
    @ColumnInfo(name="backdrop_path")
    private String backDropPath;

    private String overview;

    @SerializedName("release_date")
    @ColumnInfo(name="release_date")
    private String releaseDate;

    @SerializedName("genre_ids")
    @ColumnInfo(name="genre_ids")
    private int[] genreIds;

    @Ignore
    public Movie(String title, String originalTitle, String originalLanguage, int voteCount, double voteAverage,
                 double popularity, String posterPath, String backDropPath, String overview, String releaseDate, int[] genreIds) {
        this.title = title;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
    }

    public Movie(int id, String title, String originalTitle, String originalLanguage, int voteCount, double voteAverage,
                 double popularity, String posterPath, String backDropPath, String overview, String releaseDate, int[] genreIds) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.popularity = popularity;
        this.posterPath = posterPath;
        this.backDropPath = backDropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    @Ignore
    public Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        voteCount = in.readInt();
        voteAverage = in.readDouble();
        popularity = in.readDouble();
        posterPath = in.readString();
        backDropPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        in.readIntArray(genreIds);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeInt(voteCount);
        dest.writeDouble(voteAverage);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(backDropPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeIntArray(genreIds);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
