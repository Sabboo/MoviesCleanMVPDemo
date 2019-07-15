package com.example.moviescleanmvpdemo.presentation.movieslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviescleanmvpdemo.R;
import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.databinding.FragmentMoviesListBinding;
import com.example.moviescleanmvpdemo.util.LogUtils;
import com.example.moviescleanmvpdemo.util.OnVerticalScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MoviesListFragment extends Fragment implements MoviesListContract.View, MoviesItemClickListener {

    private FragmentMoviesListBinding mBinding;
    private MoviesListContract.Presenter mPresenter;
    private RecyclerView.Adapter adapter;

    private int currentPage;

    public MoviesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MoviesListPresenter(this);
    }

    @Override
    public void setPresenter(MoviesListContract.Presenter presenter) {
        LogUtils.d("setPresenter-MoviesListFragment");
        this.mPresenter = presenter;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies_list,
                container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());

        currentPage = 1;
        mPresenter.start();

        mBinding.rvMovies.addOnScrollListener(onVerticalScrollListener);
        mBinding.rvMovies.setItemAnimator(null);

        return mBinding.getRoot();
    }

    private final OnVerticalScrollListener onVerticalScrollListener = new OnVerticalScrollListener() {
        @Override
        public void onScrolledToBottom() {
            super.onScrolledToBottom();
            mPresenter.loadMovies(currentPage);
        }
    };

    @Override
    public void setLoadingIndicator(boolean active) {
        LogUtils.d("setLoadingIndicator-MoviesListFragment");
        if (active) mBinding.progressBarMoviesList.setVisibility(View.VISIBLE);
        else mBinding.progressBarMoviesList.setVisibility(View.GONE);
    }

    @Override
    public void showMovies(List<Movie> moviesList) {
        LogUtils.d("showMovies-MoviesListFragment");
        adapter = mBinding.rvMovies.getAdapter();
        if (adapter == null) {
            MoviesListAdapter moviesListAdapter =
                    new MoviesListAdapter(getContext()
                            , moviesList
                            , new ArrayList<>()
                            , this);
            setupMoviesRecyclerView(moviesListAdapter);
        } else {
            ((MoviesListAdapter) adapter).addMovies(moviesList);
        }
    }

    @Override
    public void updateCurrentPage() {
        currentPage++;
    }

    @Override
    public void updateFavouriteMoviesList(List<Movie> favouriteMovies) {
        adapter = mBinding.rvMovies.getAdapter();
        if (adapter == null) {
            MoviesListAdapter moviesListAdapter =
                    new MoviesListAdapter(getContext()
                            , new ArrayList<>()
                            , favouriteMovies
                            , this);
            setupMoviesRecyclerView(moviesListAdapter);
        } else {
            ((MoviesListAdapter) adapter).updateFavouriteMoviesList(favouriteMovies);
        }
    }

    private void setupMoviesRecyclerView(MoviesListAdapter moviesListAdapter) {
        mBinding.rvMovies.setLayoutManager(
                new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        mBinding.rvMovies.setAdapter(moviesListAdapter);
    }

    @Override
    public void showNoInternetConnectionView() {
        LogUtils.d("showNoInternetConnectionView-MoviesListFragment");
        mBinding.flNoConnection.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetConnectionToast() {
        LogUtils.d("showNoInternetConnectionToast-MoviesListFragment");
        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean isActive() {
        LogUtils.d("isActive-MoviesListFragment");
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }

    @Override
    public void onMovieClick(Movie clickedMovie) {
        LogUtils.d("onMovieClick-MoviesListFragment " + clickedMovie.getTitle());
        MoviesListFragmentDirections.ActionMoviesListFragmentToMovieDetailsFragment2 action =
                MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment2(clickedMovie);
        action.setMovieArgument(clickedMovie);
        Navigation.findNavController(mBinding.getRoot())
                .navigate(action);
    }

    @Override
    public void onFavouriteMovieClick(Movie clickedMovie, boolean addToFavourite) {
        mPresenter.changeMovieFavouriteIconStatus(clickedMovie, addToFavourite);
    }

}
