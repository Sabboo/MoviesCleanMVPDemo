package com.example.moviescleanmvpdemo.presentation.movieslist;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviescleanmvpdemo.R;
import com.example.moviescleanmvpdemo.data.model.Movie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieItemViewHolder> {

    private final List<Movie> moviesList;
    private final Context context;
    private final MoviesItemClickListener moviesItemClickListener;
    private HashSet<Movie> favouriteMoviesList;

    public MoviesListAdapter(Context context,
                             List<Movie> movies,
                             List<Movie> favouriteMovies,
                             MoviesItemClickListener moviesItemClickListener) {
        this.context = context;
        this.moviesList = movies;
        this.favouriteMoviesList = new HashSet<>(favouriteMovies);
        this.moviesItemClickListener = moviesItemClickListener;
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        // We will get the screen width to make each item takes half of the screen width
        // and it's height will be the same width plus the const value
        int screenWidth = getScreenWidth();

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = screenWidth / 2 + 150;
        layoutParams.width = screenWidth / 2;
        view.setLayoutParams(layoutParams);

        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {
        Movie movie = moviesList.get(position);

        // Bind normal members
        Glide.with(holder.mainConstraintLayout.getContext())
                .load("http://image.tmdb.org/t/p/w185/" + movie.getPoster_path())
                .into(holder.movieImageView);
        holder.movieTitleTextView.setText(movie.getTitle());
        holder.movieYearTextView.setText(movie.getRelease_date());

        // Listener for full movie item click
        holder.itemView.setOnClickListener(v ->
                moviesItemClickListener.onMovieClick(movie));

        // Check whether this movie in the favourite movies hash set to change
        // the favourite icon for it
        if (favouriteMoviesList.contains(movie)) {
            changeFavouriteIndication(holder, R.drawable.ic_favorite_white_24dp);
        } else
            changeFavouriteIndication(holder, R.drawable.ic_favorite_border_white_24dp);

        // Listener for favourite movie click
        holder.movieFavouriteImageView.setOnClickListener(v -> {
            if (favouriteMoviesList.contains(movie)) {
                moviesItemClickListener.onFavouriteMovieClick(movie, false);
            } else {
                moviesItemClickListener.onFavouriteMovieClick(movie, true);
            }
        });
    }

    private void changeFavouriteIndication(@NonNull MovieItemViewHolder holder, int resource) {
        holder.movieFavouriteImageView.setImageDrawable(
                ContextCompat.getDrawable(context, resource));
    }

    void addMovies(List<Movie> movies) {
        this.moviesList.addAll(movies);
        notifyDataSetChanged();
    }

    void updateFavouriteMoviesList(List<Movie> favouritesMovies) {
        List<Movie> tempFavouriteMoviesList = new ArrayList<>();
        for (Movie movie : favouritesMovies) {
            // Movie not exist in favourites set, then it's the newly added movie
            if (this.favouriteMoviesList.add(movie)) {
                notifyItemChanged(moviesList.indexOf(movie));
                return;
            }
            if (this.favouriteMoviesList.contains(movie))
                tempFavouriteMoviesList.add(movie);
        }
        // If the for loop finished without returning then no new movies inserted
        // and this is a remove movie from favourite operation
        this.favouriteMoviesList.clear();
        this.favouriteMoviesList.addAll(tempFavouriteMoviesList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    private int getScreenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    class MovieItemViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout mainConstraintLayout;
        final ImageView movieImageView;
        final TextView movieTitleTextView;
        final TextView movieYearTextView;
        final ImageView movieFavouriteImageView;

        MovieItemViewHolder(View view) {
            super(view);
            this.mainConstraintLayout = (ConstraintLayout) view;
            this.movieImageView = view.findViewById(R.id.iv_movie);
            this.movieTitleTextView = view.findViewById(R.id.tv_movie_title);
            this.movieYearTextView = view.findViewById(R.id.tv_movie_year);
            this.movieFavouriteImageView = view.findViewById(R.id.iv_favourite);
        }
    }
}
