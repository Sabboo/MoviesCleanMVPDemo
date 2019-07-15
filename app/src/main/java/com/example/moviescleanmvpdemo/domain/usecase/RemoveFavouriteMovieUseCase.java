package com.example.moviescleanmvpdemo.domain.usecase;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.data.source.FavouriteMoviesRepository;
import com.example.moviescleanmvpdemo.domain.BaseCompletableUseCase;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class RemoveFavouriteMovieUseCase extends BaseCompletableUseCase<Movie, Void, Void> {

    private final FavouriteMoviesRepository repository;

    public RemoveFavouriteMovieUseCase() {
        this.repository = FavouriteMoviesRepository.getInstance();
    }

    @Override
    protected Completable buildUseCaseObservable(Movie param, PublishSubject<Void> retrieveData) {
        return repository
                .removeFavouriteMovie(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
