package com.example.moviescleanmvpdemo.data.source.local;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviescleanmvpdemo.data.model.MoviesResponseModel;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;


/**
 * Data Access Object for the movies table.
 */
@Dao
public interface MoviesDao {

    /**
     * Select all movies from the movies table.
     *
     * @return all movies responses.
     */
    @Query("SELECT * FROM movies")
    Single<List<MoviesResponseModel>> getMoviesResponses();

    /**
     * Select movies response from the movies table based on page.
     *
     * @param pageId required page of MoviesResponseModel
     * @return movies response model with the giver
     */
    @Query("SELECT * FROM movies where page = :pageId limit 1")
    Maybe<MoviesResponseModel> getMoviesResponseModelByPage(int pageId);

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM movies")
    void deleteMoviesResponses();

    /**
     * Delete all movies.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMoviesResponses(MoviesResponseModel moviesResponseModel);
}