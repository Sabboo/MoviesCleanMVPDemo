package com.example.moviescleanmvpdemo.data.source.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviescleanmvpdemo.data.model.Movie;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Data Access Object for the favourite movies table.
 */
@Dao
public interface FavouriteMoviesDao {

    /**
     * Select all movies from the movies table.
     *
     * @return all movies responses.
     */
    @Query("SELECT * FROM favourites_movies")
    Flowable<List<Movie>> getAllFavouriteMovies();

    /**
     * Insert new favourite movie.
     *
     * @return Completable whether success or error
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertFavouriteMovie(Movie movie);

    /**
     * Delete this movie from movies table.
     *
     * @return Completable whether success or error
     */
    @Delete
    Completable removeFavouriteMovie(Movie movie);
}
