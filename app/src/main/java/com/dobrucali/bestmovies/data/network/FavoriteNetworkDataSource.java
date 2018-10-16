package com.dobrucali.bestmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.dobrucali.bestmovies.data.database.FavoriteEntry;
import com.dobrucali.bestmovies.data.database.MovieEntry;
import com.dobrucali.bestmovies.utilities.AppExecutors;

import java.net.URL;

public class FavoriteNetworkDataSource {

    private static final String LOG_TAG = FavoriteNetworkDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static FavoriteNetworkDataSource sInstance;
    private final Context mContext;

    private final MutableLiveData<FavoriteEntry[]> mDownloadedMovies;
    private final AppExecutors mExecutors;

    private FavoriteNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedMovies = new MutableLiveData<FavoriteEntry[]>();
    }

    public static FavoriteNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new FavoriteNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public LiveData<FavoriteEntry[]> getMovieList() {
        return mDownloadedMovies;
    }

}
