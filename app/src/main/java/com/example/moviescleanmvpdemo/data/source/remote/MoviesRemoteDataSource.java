package com.example.moviescleanmvpdemo.data.source.remote;


import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;
import com.example.moviescleanmvpdemo.data.source.IMoviesDataSource;
import com.example.moviescleanmvpdemo.data.source.local.MoviesLocalDataSource;

import io.reactivex.Maybe;

public class MoviesRemoteDataSource implements IMoviesDataSource {

    private static MoviesRemoteDataSource INSTANCE;
    private final MoviesLocalDataSource localDataSource = MoviesLocalDataSource.getInstance();

    public static MoviesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private MoviesRemoteDataSource() {
    }

    @Override
    public Maybe<MoviesResponseModel> getMovies(int page) {
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getAllMovies(page)
                .doAfterSuccess(localDataSource::insertMoviesResponse);
    }
}
