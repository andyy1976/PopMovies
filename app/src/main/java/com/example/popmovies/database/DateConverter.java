package com.example.popmovies.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;

public class DateConverter {

    @TypeConverter
    public static int[] fromString(String json) {
        Gson gson = new Gson();
        int[] screenshots = gson.fromJson(json, int[].class);
        return screenshots;
    }

    @TypeConverter
    public static String toString(int[] list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
