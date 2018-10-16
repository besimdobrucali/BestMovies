package com.dobrucali.bestmovies.ui.detail;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.dobrucali.bestmovies.data.FavoriteRepository;
import com.dobrucali.bestmovies.data.MovieRepository;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final MovieRepository mRepository;
    private final FavoriteRepository mFavRepository;
    private final Integer movieId;

    public DetailViewModelFactory(MovieRepository repository,FavoriteRepository favRepository, Integer movieId) {
        this.mRepository = repository;
        this.mFavRepository = favRepository;
        this.movieId = movieId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new DetailFragmentViewModel(mRepository, mFavRepository, movieId);
    }
}