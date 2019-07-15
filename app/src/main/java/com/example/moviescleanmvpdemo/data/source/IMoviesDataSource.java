package com.example.moviescleanmvpdemo.data.source;

import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;

import io.reactivex.Maybe;

public interface IMoviesDataSource {

    Maybe<MoviesResponseModel> getMovies(int page);

}
