package com.example.moviescleanmvpdemo.data.source;


import com.example.moviescleanmvpdemo.data.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface IFavouriteMoviesDataSource {

    Flowable<List<Movie>> getFavouriteMovies();

    Completable insertFavouriteMovie(Movie movie);

    Completable removeFavouriteMovie(Movie movie);

}
