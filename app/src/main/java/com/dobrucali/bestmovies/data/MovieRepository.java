package com.dobrucali.bestmovies.data;

import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.dobrucali.bestmovies.data.database.FavoriteEntry;
import com.dobrucali.bestmovies.data.database.MovieDao;
import com.dobrucali.bestmovies.data.database.MovieEntry;
import com.dobrucali.bestmovies.data.network.MovieNetworkDataSource;
import com.dobrucali.bestmovies.utilities.AppExecutors;

import java.util.List;

public class MovieRepository {
    private static final String LOG_TAG = MovieRepository.class.getSimpleName();

     // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MovieRepository sInstance;
    private MovieDao mMovieDao;
    private final MovieNetworkDataSource mMovieNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private MovieRepository(MovieDao movieDao,
                            MovieNetworkDataSource movieNetworkDataSource,
                            AppExecutors executors) {
        mMovieDao = movieDao;
        mMovieNetworkDataSource = movieNetworkDataSource;
        mExecutors = executors;

        LiveData<MovieEntry[]> networkData = mMovieNetworkDataSource.getMovieList();
        networkData.observeForever(newMoviesFromNetwork -> {
            mExecutors.diskIO().execute(() -> {
                Log.d(LOG_TAG, "Old movies deleted");
                mMovieDao.bulkInsert(newMoviesFromNetwork);
                Log.d(LOG_TAG, "New movies inserted");
            });
        });

    }

    public synchronized static MovieRepository getInstance(
            MovieDao movieDao, MovieNetworkDataSource movieNetworkDataSource,
            AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new MovieRepository(movieDao, movieNetworkDataSource,
                        executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    public LiveData<MovieEntry> getMovieById(Integer movieId) {
        return mMovieDao.getMoviesByMovieId(movieId);
    }

    public LiveData<List<MovieEntry>> getAllMovies() {
        initializeData();
        return mMovieDao.getAllMovies();
    }

    public LiveData<List<MovieEntry>> getFiftyMovies() {
        initializeData();
        return mMovieDao.getFiftyMovies();
    }

    public synchronized void initializeData() {

        if (mInitialized){
            return;
        }
        mInitialized = true;

        mExecutors.diskIO().execute(() -> {
            for (int i = 1; i <= pageCountForMovies() ; i++ ){
                mMovieNetworkDataSource.fetchMovie(i);
            }
        });
    }

    private int pageCountForMovies(){
       int pageCount = (int) Math.ceil(MovieNetworkDataSource.MOVIE_COUNT / MovieNetworkDataSource.MOVIE_COUNT_PER_PAGE);
       return pageCount;
    }

}
