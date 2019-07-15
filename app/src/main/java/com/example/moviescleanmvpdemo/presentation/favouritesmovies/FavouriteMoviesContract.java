package com.example.moviescleanmvpdemo.presentation.favouritesmovies;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.presentation.BasePresenter;
import com.example.moviescleanmvpdemo.presentation.BaseView;

import java.util.List;

public interface FavouriteMoviesContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showFavouriteMovies(List<Movie> moviesList);

        void showEmptyFavouriteMovies();

        boolean isActive();

    }

    interface Presenter extends BasePresenter {

        void loadFavouriteMovies();

        void removeMovieFromFavourites(Movie movie);

    }
}
