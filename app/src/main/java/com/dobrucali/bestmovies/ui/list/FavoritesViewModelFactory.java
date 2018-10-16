package com.dobrucali.bestmovies.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.dobrucali.bestmovies.data.FavoriteRepository;
import com.dobrucali.bestmovies.data.MovieRepository;

public class FavoritesViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final FavoriteRepository mRepository;

    public FavoritesViewModelFactory(FavoriteRepository repository) {
        this.mRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new FavoritesFragmentViewModel(mRepository);
    }
}