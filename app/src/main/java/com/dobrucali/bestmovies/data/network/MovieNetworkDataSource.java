package com.dobrucali.bestmovies.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.dobrucali.bestmovies.data.database.MovieEntry;
import com.dobrucali.bestmovies.utilities.AppExecutors;

import java.net.URL;

public class MovieNetworkDataSource {

    private static final String LOG_TAG = MovieNetworkDataSource.class.getSimpleName();

    private static final Object LOCK = new Object();
    public static final double MOVIE_COUNT = 50.0;
    public static final double MOVIE_COUNT_PER_PAGE = 20.0;
    private static MovieNetworkDataSource sInstance;
    private final Context mContext;

    private final MutableLiveData<MovieEntry[]> mDownloadedMovies;
    private final AppExecutors mExecutors;

    private MovieNetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        mDownloadedMovies = new MutableLiveData<MovieEntry[]>();
    }

    public static MovieNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieNetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public LiveData<MovieEntry[]> getMovieList() {
        return mDownloadedMovies;
    }

    public void fetchMovie(int page) {

        Log.d(LOG_TAG, "Fetch movie started");
        mExecutors.networkIO().execute(() -> {
            try {

                String sortBy = "popularity.desc";
                URL movieRequestUrl = NetworkUtils.getUrl(sortBy,page);
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);

                MovieResponse response = new MovieJsonParser().parse(jsonMovieResponse);
                Log.d(LOG_TAG, "JSON Parsing finished");

                if (response != null && response.getMovieList().length != 0) {
                    mDownloadedMovies.postValue(response.getMovieList());
                }
            } catch (Exception e) {
                // Server probably invalid
                e.printStackTrace();
            }
        });
    }

}
