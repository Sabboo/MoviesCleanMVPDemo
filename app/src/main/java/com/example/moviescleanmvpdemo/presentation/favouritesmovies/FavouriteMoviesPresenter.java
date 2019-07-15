package com.example.moviescleanmvpdemo.presentation.favouritesmovies;

import androidx.annotation.NonNull;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.domain.usecase.GetFavouriteMoviesUseCase;
import com.example.moviescleanmvpdemo.domain.usecase.RemoveFavouriteMovieUseCase;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class FavouriteMoviesPresenter implements FavouriteMoviesContract.Presenter {

    private final FavouriteMoviesContract.View mFavouriteMoviesView;

    private final GetFavouriteMoviesUseCase getFavouriteMoviesUseCase;
    private final RemoveFavouriteMovieUseCase removeFavouriteMovieUseCase;

    private final CompositeDisposable mCompositeDisposables;

    FavouriteMoviesPresenter(@NonNull FavouriteMoviesContract.View view) {
        this.mFavouriteMoviesView = view;
        this.getFavouriteMoviesUseCase = new GetFavouriteMoviesUseCase();
        this.removeFavouriteMovieUseCase = new RemoveFavouriteMovieUseCase();

        mCompositeDisposables = new CompositeDisposable();

        mFavouriteMoviesView.setPresenter(this);
    }

    @Override
    public void loadFavouriteMovies() {
        Disposable subscribe =
                getFavouriteMoviesUseCase.execute(null)
                        .subscribe(movies -> {
                            if (!mFavouriteMoviesView.isActive())
                                return;
                            if (movies.size() > 0)
                                mFavouriteMoviesView.showFavouriteMovies(movies);
                            else {
                                mFavouriteMoviesView.showFavouriteMovies(movies);
                                mFavouriteMoviesView.showEmptyFavouriteMovies();

                            }

                            mFavouriteMoviesView.setLoadingIndicator(false);
                        });

        mCompositeDisposables.add(subscribe);
    }

    @Override
    public void removeMovieFromFavourites(Movie movie) {
        removeFavouriteMovieUseCase.execute(movie)
                .subscribe();
    }

    @Override
    public void start() {
        loadFavouriteMovies();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposables.dispose();
    }
}
