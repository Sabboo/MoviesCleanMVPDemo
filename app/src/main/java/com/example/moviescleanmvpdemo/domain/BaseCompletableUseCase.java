package com.example.moviescleanmvpdemo.domain;

import io.reactivex.Completable;
import rx.subjects.PublishSubject;

public abstract class BaseCompletableUseCase<ParamType, ResultType, RetrievedDataType> implements IBaseUseCase {

    private final PublishSubject<RetrievedDataType> mRetrievedData = PublishSubject.create();

    protected abstract Completable buildUseCaseObservable(
            ParamType param,
            PublishSubject<RetrievedDataType> retrieveData);

    public Completable execute(ParamType param) {
        return buildUseCaseObservable(param, mRetrievedData);
    }

    @Override
    public PublishSubject<RetrievedDataType> observeRetrievedData() {
        return mRetrievedData;
    }

}
