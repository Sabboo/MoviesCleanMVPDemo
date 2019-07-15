package com.example.moviescleanmvpdemo.domain;

import rx.subjects.PublishSubject;

interface IBaseUseCase<RetrievedDataType> {

    PublishSubject<RetrievedDataType> observeRetrievedData();

}
