package com.example.moviescleanmvpdemo.data.source.local;


import com.example.moviescleanmvpdemo.App;
import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.data.source.IFavouriteMoviesDataSource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class FavouriteMoviesLocalDataSource implements IFavouriteMoviesDataSource {

    private static FavouriteMoviesLocalDataSource INSTANCE;

    public static FavouriteMoviesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavouriteMoviesLocalDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private FavouriteMoviesLocalDataSource() {
    }

    @Override
    public Flowable<List<Movie>> getFavouriteMovies() {
        return AppDatabase
                .getInstance(App.getContext())
                .favouriteMoviesDao()
                .getAllFavouriteMovies();
    }

    @Override
    public Completable insertFavouriteMovie(Movie movie) {
        return AppDatabase
                .getInstance(App.getContext())
                .favouriteMoviesDao()
                .insertFavouriteMovie(movie);
    }

    @Override
    public Completable removeFavouriteMovie(Movie movie) {
        return AppDatabase
                .getInstance(App.getContext())
                .favouriteMoviesDao()
                .removeFavouriteMovie(movie);
    }
}
