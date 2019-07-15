package com.example.moviescleanmvpdemo.domain.repository;

import com.example.moviescleanmvpdemo.data.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface IFavouriteMoviesDomainRepository {

    Flowable<List<Movie>> getFavouriteMovies();

    Completable insertFavouriteMovie(Movie movie);

    Completable removeFavouriteMovie(Movie movie);

}
