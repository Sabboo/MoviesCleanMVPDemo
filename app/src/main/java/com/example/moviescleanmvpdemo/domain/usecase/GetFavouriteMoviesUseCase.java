package com.example.moviescleanmvpdemo.domain.usecase;

import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.data.source.FavouriteMoviesRepository;
import com.example.moviescleanmvpdemo.domain.BaseFlowableUseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class GetFavouriteMoviesUseCase extends BaseFlowableUseCase<Void, List<Movie>, Void> {

    private final FavouriteMoviesRepository repository;

    public GetFavouriteMoviesUseCase() {
        this.repository = FavouriteMoviesRepository.getInstance();
    }

    @Override
    protected Flowable<List<Movie>> buildUseCaseObservable(Void param, PublishSubject<Void> retrieveData) {
        return repository.getFavouriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
