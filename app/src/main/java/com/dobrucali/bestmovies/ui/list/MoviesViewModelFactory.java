package com.dobrucali.bestmovies.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.dobrucali.bestmovies.data.MovieRepository;

public class MoviesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository mRepository;

    public MoviesViewModelFactory(MovieRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MoviesFragmentViewModel(mRepository);
    }
}
