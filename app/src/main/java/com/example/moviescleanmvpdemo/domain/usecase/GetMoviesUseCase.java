package com.example.moviescleanmvpdemo.domain.usecase;

import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;
import com.example.moviescleanmvpdemo.data.source.MoviesRepository;
import com.example.moviescleanmvpdemo.domain.BaseMaybeUseCase;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class GetMoviesUseCase extends BaseMaybeUseCase<Integer, MoviesResponseModel, Void> {

    private final MoviesRepository repository;

    public GetMoviesUseCase() {
        this.repository = MoviesRepository.getInstance();
    }

    @Override
    public Maybe<MoviesResponseModel> buildUseCaseObservable(Integer param, PublishSubject<Void> retrieveData) {
        return repository.getMovies(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
