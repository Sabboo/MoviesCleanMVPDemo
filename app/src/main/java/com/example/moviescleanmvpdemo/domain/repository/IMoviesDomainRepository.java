package com.example.moviescleanmvpdemo.domain.repository;

import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;

import io.reactivex.Maybe;

public interface IMoviesDomainRepository {

    Maybe<MoviesResponseModel> getMovies(int page);

}
