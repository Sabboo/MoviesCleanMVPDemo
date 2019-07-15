package com.example.moviescleanmvpdemo.data.source.remote;

import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface NetworkInterface {

    @GET("/3/movie/popular?api_key=a2b40f8daf4edc833a05610f3a019457&language=en-US")
    Maybe<MoviesResponseModel> getAllMovies(
            @Query("page") int page
    );
}
