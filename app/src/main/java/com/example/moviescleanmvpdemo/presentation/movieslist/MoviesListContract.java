package com.example.moviescleanmvpdemo.presentation.movieslist;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.presentation.BasePresenter;
import com.example.moviescleanmvpdemo.presentation.BaseView;

import java.util.List;

public interface MoviesListContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showMovies(List<Movie> moviesList);

        void showNoInternetConnectionView();

        void showNoInternetConnectionToast();

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        boolean isActive();

        void updateCurrentPage();

        void updateFavouriteMoviesList(List<Movie> favouriteMoviesList);
    }

    interface Presenter extends BasePresenter {

        void loadMovies(int page);

        void loadFavouriteMovies();

        void changeMovieFavouriteIconStatus(Movie movie, boolean addToFavourite);

    }
}
