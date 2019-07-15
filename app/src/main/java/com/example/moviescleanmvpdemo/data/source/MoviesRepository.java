package com.example.moviescleanmvpdemo.data.source;


import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;
import com.example.moviescleanmvpdemo.data.source.local.MoviesLocalDataSource;
import com.example.moviescleanmvpdemo.data.source.remote.MoviesRemoteDataSource;
import com.example.moviescleanmvpdemo.domain.repository.IMoviesDomainRepository;

import java.io.IOException;

import io.reactivex.Maybe;

public class MoviesRepository implements IMoviesDomainRepository {

    private static MoviesRepository INSTANCE = null;

    private final IMoviesDataSource mMoviesRemoteDataSource;

    private final IMoviesDataSource mMoviesLocalDataSource;

    // Prevent direct instantiation.
    private MoviesRepository() {
        mMoviesRemoteDataSource = MoviesRemoteDataSource.getInstance();
        mMoviesLocalDataSource = MoviesLocalDataSource.getInstance();
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @return the {@link MoviesRepository} instance
     */
    public static MoviesRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRepository();
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
    public Maybe<MoviesResponseModel> getMovies(int page) {
        return mMoviesRemoteDataSource.getMovies(page)
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof IOException) {
                        return mMoviesLocalDataSource.getMovies(page);
                    }
                    return Maybe.error(throwable);
                });
    }

}
