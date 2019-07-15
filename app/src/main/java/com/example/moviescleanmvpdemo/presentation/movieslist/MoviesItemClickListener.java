package com.example.moviescleanmvpdemo.presentation.movieslist;

import com.example.moviescleanmvpdemo.data.model.Movie;

public interface MoviesItemClickListener {

    void onMovieClick(Movie clickedMovie);

    void onFavouriteMovieClick(Movie clickedMovie, boolean addToFavourite);

}
