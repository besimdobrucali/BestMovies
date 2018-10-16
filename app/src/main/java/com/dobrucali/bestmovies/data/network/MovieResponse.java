package com.dobrucali.bestmovies.data.network;

import android.support.annotation.NonNull;

import com.dobrucali.bestmovies.data.database.MovieEntry;

class MovieResponse {

        @NonNull
        private final MovieEntry[] mMovieList;

        public MovieResponse(@NonNull final MovieEntry[] movieList) {
            mMovieList = movieList;
        }

        public MovieEntry[] getMovieList() {
            return mMovieList;
        }
}
