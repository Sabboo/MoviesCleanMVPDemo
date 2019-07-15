package com.example.moviescleanmvpdemo.presentation.moviesdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.moviescleanmvpdemo.R;
import com.example.moviescleanmvpdemo.data.model.Movie;
import com.example.moviescleanmvpdemo.databinding.FragmentMovieDetailsBinding;

public class MovieDetailsFragment extends Fragment {

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMovieDetailsBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details,
                container, false);
        mBinding.setLifecycleOwner(getViewLifecycleOwner());


        // Inflate the layout for this fragment
        if (getArguments() != null) {
            Movie clickedMovie = MovieDetailsFragmentArgs.fromBundle(getArguments()).getMovieArgument();

            mBinding.tvMovieDetailsTitle.setText(clickedMovie.getTitle());
            mBinding.tvMovieDetailsYear.setText(clickedMovie.getRelease_date());
            mBinding.tvMovieDetailsVote.setText(String.valueOf(clickedMovie.getVote_average()));
            mBinding.tvMovieDetailsOverview.setText(clickedMovie.getOverview());

            if (getContext() != null)
                Glide.with(getContext())
                        .load("http://image.tmdb.org/t/p/w185/" + clickedMovie.getPoster_path())
                        .into(mBinding.ivMovieDetailsPoster);
        }

        return mBinding.getRoot();
    }

}
