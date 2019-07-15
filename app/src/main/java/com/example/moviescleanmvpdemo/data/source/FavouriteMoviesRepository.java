package com.example.moviescleanmvpdemo.data.source;


import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.data.source.local.FavouriteMoviesLocalDataSource;
import com.example.moviescleanmvpdemo.domain.repository.IFavouriteMoviesDomainRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class FavouriteMoviesRepository implements IFavouriteMoviesDomainRepository {

    private static FavouriteMoviesRepository INSTANCE = null;

    private final IFavouriteMoviesDataSource mFavouriteMoviesDataSource;


    // Prevent direct instantiation.
    private FavouriteMoviesRepository() {
        mFavouriteMoviesDataSource = FavouriteMoviesLocalDataSource.getInstance();
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link FavouriteMoviesRepository} instance
     */
    public static FavouriteMoviesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FavouriteMoviesRepository();
        }
        return INSTANCE;
    }

    /**
     * Used to force {@link #getInstance()} to create a new instance
     * next time it's called.
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }


    @Override
    public Flowable<List<Movie>> getFavouriteMovies() {
        return mFavouriteMoviesDataSource
                .getFavouriteMovies();
    }

    @Override
    public Completable insertFavouriteMovie(Movie movie) {
        return mFavouriteMoviesDataSource
                .insertFavouriteMovie(movie);
    }

    @Override
    public Completable removeFavouriteMovie(Movie movie) {
        return mFavouriteMoviesDataSource
                .removeFavouriteMovie(movie);
    }
}
