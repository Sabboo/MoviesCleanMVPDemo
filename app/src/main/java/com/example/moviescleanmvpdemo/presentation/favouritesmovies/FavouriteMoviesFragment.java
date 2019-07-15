package com.example.moviescleanmvpdemo.presentation.favouritesmovies;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviescleanmvpdemo.R;
import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.databinding.FragmentFavouriteMoviesBinding;
import com.example.moviescleanmvpdemo.presentation.movieslist.MoviesItemClickListener;
import com.example.moviescleanmvpdemo.presentation.movieslist.MoviesListAdapter;

import java.util.List;

import static android.view.View.GONE;

public class FavouriteMoviesFragment extends Fragment implements FavouriteMoviesContract.View, MoviesItemClickListener {

    private FragmentFavouriteMoviesBinding mBinding;
    private FavouriteMoviesContract.Presenter mPresenter;

    public FavouriteMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new FavouriteMoviesPresenter(this);
    }

    @Override
    public void setPresenter(FavouriteMoviesContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favourite_movies,
                container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());

        mPresenter.start();

        return mBinding.getRoot();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) mBinding.progressBarFavouriteMoviesList.setVisibility(View.VISIBLE);
        else mBinding.progressBarFavouriteMoviesList.setVisibility(GONE);
    }

    @Override
    public void showFavouriteMovies(List<Movie> moviesList) {
        if (moviesList.size() == 0) {
            mBinding.rvFavouriteMovies.setAdapter(null);
            return;
        }
        MoviesListAdapter moviesListAdapter =
                new MoviesListAdapter(getContext()
                        , moviesList
                        , moviesList
                        , this);
        setupFavouriteMoviesRecyclerView(moviesListAdapter);
    }

    private void setupFavouriteMoviesRecyclerView(MoviesListAdapter moviesListAdapter) {
        mBinding.rvFavouriteMovies.setLayoutManager(
                new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        mBinding.rvFavouriteMovies.setAdapter(moviesListAdapter);
    }

    @Override
    public void showEmptyFavouriteMovies() {
        mBinding.flNoFavouriteMovies.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }


    @Override
    public void onMovieClick(Movie clickedMovie) {
        FavouriteMoviesFragmentDirections.ActionFavouriteMoviesFragmentToMovieDetailsFragment action =
                FavouriteMoviesFragmentDirections.actionFavouriteMoviesFragmentToMovieDetailsFragment(clickedMovie);
        action.setMovieArgument(clickedMovie);
        Navigation.findNavController(mBinding.getRoot()).navigate(action);
    }

    @Override
    public void onFavouriteMovieClick(Movie clickedMovie, boolean addToFavourite) {
        mPresenter.removeMovieFromFavourites(clickedMovie);
    }
}
