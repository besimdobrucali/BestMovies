package com.dobrucali.bestmovies.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.dobrucali.bestmovies.data.FavoriteRepository;
import com.dobrucali.bestmovies.data.MovieRepository;
import com.dobrucali.bestmovies.data.database.FavoriteEntry;
import com.dobrucali.bestmovies.data.database.MovieEntry;

import java.util.List;

public class FavoritesFragmentViewModel extends ViewModel {

    private final FavoriteRepository mRepository;
    private final LiveData<List<FavoriteEntry>> mFavoriteList;

    public FavoritesFragmentViewModel(FavoriteRepository repository) {
        mRepository = repository;
        mFavoriteList = mRepository.getAllFavourites();
    }

    public LiveData<List<FavoriteEntry>> getMovies() {
        return mFavoriteList;
    }

}
