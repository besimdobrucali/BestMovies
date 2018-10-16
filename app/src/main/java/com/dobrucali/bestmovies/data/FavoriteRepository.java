package com.dobrucali.bestmovies.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.dobrucali.bestmovies.data.database.FavoriteDao;
import com.dobrucali.bestmovies.data.database.FavoriteEntry;
import com.dobrucali.bestmovies.data.network.FavoriteNetworkDataSource;
import com.dobrucali.bestmovies.utilities.AppExecutors;

import java.util.List;

public class FavoriteRepository {
    private static final String LOG_TAG = FavoriteRepository.class.getSimpleName();

     // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static FavoriteRepository sInstance;
    private FavoriteDao mFavoriteDao;
    private final AppExecutors mExecutors;
    private final FavoriteNetworkDataSource mFavoriteNetworkDataSource;


    private FavoriteRepository(FavoriteDao favoriteDao,
                               FavoriteNetworkDataSource favNetworkDataSource,
                               AppExecutors executors) {
        mFavoriteNetworkDataSource = favNetworkDataSource;
        mFavoriteDao = favoriteDao;
        mExecutors = executors;

        LiveData<FavoriteEntry[]> networkData = mFavoriteNetworkDataSource.getMovieList();
        networkData.observeForever(newMoviesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                Log.d(LOG_TAG, "Old movies deleted");
                mFavoriteDao.bulkInsert(newMoviesFromNetwork);
                Log.d(LOG_TAG, "New movies inserted");
            });
        });

    }

    public synchronized static FavoriteRepository getInstance(
            FavoriteDao favoriteDao,
            FavoriteNetworkDataSource favNetworkDataSource,
            AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new FavoriteRepository(favoriteDao,favNetworkDataSource, executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<FavoriteEntry> getFavouriteById(Integer movieId) {
        return mFavoriteDao.getFavouriteMovieByMovieId(movieId);
    }

    public LiveData<List<FavoriteEntry>> getAllFavourites() {
        return mFavoriteDao.getAllFavourites();
    }

    public void addMovieToFavorites(FavoriteEntry favoriteEntry) {
        mFavoriteDao.insert(favoriteEntry);
    }

    public void deleteMovieFromFavorites(int favoriteEntryId) {
        mFavoriteDao.deleteFromFavorite(favoriteEntryId);
    }


}
