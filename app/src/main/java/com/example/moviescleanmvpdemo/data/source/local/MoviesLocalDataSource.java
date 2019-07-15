package com.example.moviescleanmvpdemo.data.source.local;


import com.example.moviescleanmvpdemo.App;
import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;
import com.example.moviescleanmvpdemo.data.source.IMoviesDataSource;

import io.reactivex.Maybe;

public class MoviesLocalDataSource implements IMoviesDataSource {

    private static MoviesLocalDataSource INSTANCE;

    public static MoviesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MoviesLocalDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private MoviesLocalDataSource() {
    }

    @Override
    public Maybe<MoviesResponseModel> getMovies(int page) {
        return AppDatabase
                .getInstance(App.getContext())
                .moviesDao()
                .getMoviesResponseModelByPage(page);
    }

    public void insertMoviesResponse(MoviesResponseModel moviesResponseModel) {
        AppDatabase
                .getInstance(App.getContext())
                .moviesDao()
                .insertMoviesResponses(moviesResponseModel);
    }


}
