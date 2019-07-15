package com.example.moviescleanmvpdemo.presentation.movieslist;

import androidx.annotation.NonNull;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;
import com.example.moviescleanmvpdemo.domain.usecase.GetFavouriteMoviesUseCase;
import com.example.moviescleanmvpdemo.domain.usecase.GetMoviesUseCase;
import com.example.moviescleanmvpdemo.domain.usecase.InsertNewFavoriteMovieUseCase;
import com.example.moviescleanmvpdemo.domain.usecase.RemoveFavouriteMovieUseCase;
import com.example.moviescleanmvpdemo.util.LogUtils;

import java.io.IOException;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MoviesListPresenter implements MoviesListContract.Presenter {

    private final MoviesListContract.View mMoviesView;
    private final GetMoviesUseCase getMoviesUseCase;
    private final GetFavouriteMoviesUseCase getFavouriteMoviesUseCase;
    private final InsertNewFavoriteMovieUseCase insertNewFavoriteMovieUseCase;
    private final RemoveFavouriteMovieUseCase removeFavouriteMovieUseCase;

    private final CompositeDisposable mCompositeDisposables;

    MoviesListPresenter(@NonNull MoviesListContract.View view) {
        this.mMoviesView = view;
        this.getMoviesUseCase = new GetMoviesUseCase();
        this.getFavouriteMoviesUseCase = new GetFavouriteMoviesUseCase();
        this.insertNewFavoriteMovieUseCase = new InsertNewFavoriteMovieUseCase();
        this.removeFavouriteMovieUseCase = new RemoveFavouriteMovieUseCase();

        mMoviesView.setPresenter(this);

        mCompositeDisposables = new CompositeDisposable();
    }

    @Override
    public void start() {
        loadFavouriteMovies();
        loadMovies(1);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposables.dispose();
    }

    @Override
    public void loadMovies(int page) {
        mMoviesView.setLoadingIndicator(true);
        getMoviesUseCase.execute(page)
                .subscribe(
                        new MaybeObserver<MoviesResponseModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                LogUtils.d("onSubscribe-loadMovies-MoviesListPresenter");
                                mCompositeDisposables.add(d);
                            }

                            @Override
                            public void onSuccess(MoviesResponseModel moviesResponseModel) {
                                LogUtils.d("onSuccess-loadMovies-MoviesListPresenter");
                                if (!mMoviesView.isActive()) {
                                    return;
                                }
                                mMoviesView.setLoadingIndicator(false);
                                mMoviesView.showMovies(moviesResponseModel.getResults());
                                mMoviesView.updateCurrentPage();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                LogUtils.e("onError-loadMovies-MoviesListPresenter", throwable);
                                LogUtils.d(throwable.getMessage() + throwable.getClass());
                                if (!mMoviesView.isActive()) {
                                    return;
                                }
                                mMoviesView.setLoadingIndicator(false);
                                if (throwable instanceof IOException) {
                                    showNoInternetConnectionIndication(page);
                                }
                            }

                            @Override
                            public void onComplete() {
                                LogUtils.d("onComplete-loadMovies-MoviesListPresenter");
                                if (!mMoviesView.isActive()) {
                                    return;
                                }
                                mMoviesView.setLoadingIndicator(false);
                                showNoInternetConnectionIndication(page);
                            }
                        });
    }

    @Override
    public void loadFavouriteMovies() {
        Disposable subscribe =
                getFavouriteMoviesUseCase.execute(null)
                        .subscribe(mMoviesView::updateFavouriteMoviesList);

        mCompositeDisposables.add(subscribe);
    }

    @Override
    public void changeMovieFavouriteIconStatus(Movie movie, boolean addToFavourite) {
        if (addToFavourite)
            insertNewFavoriteMovieUseCase.execute(movie)
                    .subscribe();
        else
            removeFavouriteMovieUseCase.execute(movie)
                    .subscribe();
    }

    private void showNoInternetConnectionIndication(int page) {
        if (page == 1)
            mMoviesView.showNoInternetConnectionView();
        else
            mMoviesView.showNoInternetConnectionToast();
    }

}
