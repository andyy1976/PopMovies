package com.example.popmovies.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.popmovies.network.model.Movie;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    LiveData<List<Movie>> getAllFavorite();

    @Insert
    void insertFavorite(Movie favorite);

    @Delete
    void deleteFavorite(Movie favorite);

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<Movie> findFavoriteById(int id);
}
