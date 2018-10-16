package com.dobrucali.bestmovies.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.dobrucali.bestmovies.data.MovieRepository;
import com.dobrucali.bestmovies.data.database.MovieEntry;

import java.util.List;

public class MoviesFragmentViewModel extends ViewModel {

    private final MovieRepository mRepository;
    private final LiveData<List<MovieEntry>> mMovie;

    public MoviesFragmentViewModel(MovieRepository repository) {
        mRepository = repository;
        mMovie = mRepository.getFiftyMovies();
    }

    public LiveData<List<MovieEntry>> getMovies() {
        return mMovie;
    }

}
