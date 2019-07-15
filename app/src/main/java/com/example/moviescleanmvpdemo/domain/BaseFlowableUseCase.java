package com.example.moviescleanmvpdemo.domain;

import io.reactivex.Flowable;
import rx.subjects.PublishSubject;

public abstract class BaseFlowableUseCase<ParamType, ResultType, RetrievedDataType> implements IBaseUseCase {

    private final PublishSubject<RetrievedDataType> mRetrievedData = PublishSubject.create();

    protected abstract Flowable<ResultType> buildUseCaseObservable(
            ParamType param,
            PublishSubject<RetrievedDataType> retrieveData);

    public Flowable<ResultType> execute(ParamType param) {
        return buildUseCaseObservable(param, mRetrievedData);
    }

    @Override
    public PublishSubject<RetrievedDataType> observeRetrievedData() {
        return mRetrievedData;
    }

}
