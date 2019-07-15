package com.example.moviescleanmvpdemo.data.source.local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;

/**
 * The Room Database that contains the MoviesResponseModel table.
 */
@Database(entities = {MoviesResponseModel.class, Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract MoviesDao moviesDao();

    public abstract FavouriteMoviesDao favouriteMoviesDao();

    private static final Object sLock = new Object();

    static AppDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "AppData.db")
                        .build();
            }
            return INSTANCE;
        }
    }

}
