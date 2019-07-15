package com.example.moviescleanmvpdemo.domain;

import io.reactivex.Maybe;
import rx.subjects.PublishSubject;

public abstract class BaseMaybeUseCase<ParamType, ResultType, RetrievedDataType> implements IBaseUseCase {

    private final PublishSubject<RetrievedDataType> mRetrievedData = PublishSubject.create();

    protected abstract Maybe<ResultType> buildUseCaseObservable(
            ParamType param,
            PublishSubject<RetrievedDataType> retrieveData);

    public Maybe<ResultType> execute(ParamType param) {
        return buildUseCaseObservable(param, mRetrievedData);
    }

    @Override
    public PublishSubject<RetrievedDataType> observeRetrievedData() {
        return mRetrievedData;
    }

}
