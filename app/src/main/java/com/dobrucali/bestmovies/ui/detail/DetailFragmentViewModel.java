package com.dobrucali.bestmovies.ui.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dobrucali.bestmovies.data.FavoriteRepository;
import com.dobrucali.bestmovies.data.MovieRepository;
import com.dobrucali.bestmovies.data.database.FavoriteEntry;
import com.dobrucali.bestmovies.data.database.MovieEntry;

import java.util.List;

public class DetailFragmentViewModel extends ViewModel{

    private LiveData<MovieEntry> mMovie;
    private LiveData<FavoriteEntry> mFavorite;
    private final Integer mMovieId;
    private final MovieRepository mRepository;
    private final FavoriteRepository mFavRepository;

    public DetailFragmentViewModel(MovieRepository repository,FavoriteRepository favRepository, Integer movieId) {
        mRepository = repository;
        mFavRepository = favRepository;
        mMovieId = movieId;
        mMovie = mRepository.getMovieById(movieId);
        mFavorite = mFavRepository.getFavouriteById(movieId);
    }

    public LiveData<MovieEntry> getMovie() {
        return mMovie;
    }

    public LiveData<FavoriteEntry> getFavorite() { return mFavorite;}

    public LiveData<List<FavoriteEntry>> getFavorites() {
        LiveData<List<FavoriteEntry>> favoriteEntry = mFavRepository.getAllFavourites();
        return favoriteEntry;
    }

    public void deleteFromFavorites() {
        mFavRepository.deleteMovieFromFavorites(mMovie.getValue().getId());
    }

    public void addMovieToFavorites() {
        MovieEntry movieEntry = mMovie.getValue();
        FavoriteEntry favoriteEntry = new FavoriteEntry();
        favoriteEntry.setUniqueId(movieEntry.getUniqueId());
        favoriteEntry.setId(movieEntry.getId());
        favoriteEntry.setTitle(movieEntry.getTitle());
        favoriteEntry.setOverview(movieEntry.getOverview());
        favoriteEntry.setVoteAverage(movieEntry.getVoteAverage());
        favoriteEntry.setVoteCount(movieEntry.getVoteCount());
        favoriteEntry.setPosterPath(movieEntry.getPosterPath());
        mFavRepository.addMovieToFavorites(favoriteEntry);
    }

}
