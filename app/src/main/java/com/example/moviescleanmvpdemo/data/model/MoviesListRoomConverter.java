package com.example.moviescleanmvpdemo.data.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MoviesListRoomConverter {

    private final Gson gson = new Gson();
    private final Type type = new TypeToken<List<Movie>>() {
    }.getType();

    @TypeConverter
    public String fromMoviesList(List<Movie> moviesList) {
        if (moviesList == null) {
            return (null);
        }
        return gson.toJson(moviesList, type);
    }

    @TypeConverter
    public List<Movie> toMoviesList(String moviesListAsString) {
        if (moviesListAsString == null) {
            return (null);
        }
        return gson.fromJson(moviesListAsString, type);
    }
}
