package com.dobrucali.bestmovies.data.network;

import android.support.annotation.Nullable;

import com.dobrucali.bestmovies.data.database.MovieEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

final class MovieJsonParser {

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String VOTE_COUNT = "vote_count";
    private static final String VIDEO = "video";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String TITLE = "title";
    private static final String POPULARITY = "popularity";
    private static final String POSTER_PATH = "poster_path";
    private static final String ORIGINAL_LANGUAGE = "original_language";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String GENRE_IDS = "genre_ids";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String ADULT = "adult";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String STATUS_CODE = "status_code";

    private static boolean hasHttpError(JSONObject movieJson) throws JSONException {
        if (movieJson.has(STATUS_CODE)) {
            int errorCode = movieJson.getInt(STATUS_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    return false;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    // Location invalid
                default:
                    // Server probably down
                    return true;
            }
        }
        return false;
    }

    private static MovieEntry[] fromJson(final JSONObject movieJson) throws JSONException {
        JSONArray jsonMovieArray = movieJson.getJSONArray(RESULTS);

        MovieEntry[] movieEntries = new MovieEntry[jsonMovieArray.length()];

        for (int i = 0; i < jsonMovieArray.length(); i++) {
            JSONObject movieObject = jsonMovieArray.getJSONObject(i);
            MovieEntry movie = fromJsonToMovie(movieObject);

            movieEntries[i] = movie;
        }
        return movieEntries;
    }

    private static MovieEntry fromJsonToMovie(final JSONObject movieResultObject) throws JSONException {

        int id = 0;
        int voteCount = 0;
        boolean video = false;
        double voteAverage = 0;
        String title = null;
        double popularity = 0;
        String posterPath = null;
        String originalLanguage = null;
        String originalTitle = null;
        List<Integer> genreIds = new ArrayList<>();
        String backdropPath = null;
        boolean adult = false;
        String overview = null;
        String releaseDate = null;

        if(movieResultObject.has(ID)) {
            id = movieResultObject.getInt(ID);
        }

        if(movieResultObject.has(VOTE_COUNT)) {
            voteCount = movieResultObject.getInt(VOTE_COUNT);
        }

        if(movieResultObject.has(VIDEO)) {
            video = movieResultObject.getBoolean(VIDEO);
        }

        if(movieResultObject.has(VOTE_AVERAGE)) {
            voteAverage = movieResultObject.getDouble(VOTE_AVERAGE);
        }

        if(movieResultObject.has(TITLE)) {
            title = movieResultObject.getString(TITLE);
        }

        if(movieResultObject.has(POPULARITY)) {
            popularity = movieResultObject.getDouble(POPULARITY);
        }

        if(movieResultObject.has(POSTER_PATH)) {
            posterPath = movieResultObject.getString(POSTER_PATH);
        }

        if(movieResultObject.has(ORIGINAL_LANGUAGE)) {
            originalLanguage = movieResultObject.getString(ORIGINAL_LANGUAGE);
        }

        if(movieResultObject.has(ORIGINAL_TITLE)) {
            originalTitle = movieResultObject.getString(ORIGINAL_TITLE);
        }

        if(movieResultObject.has(GENRE_IDS)) {
            JSONArray genreIdsJsonArray = movieResultObject.getJSONArray(GENRE_IDS);
            for (int i = 0; i < genreIdsJsonArray.length() ; i++) {
                genreIds.add(genreIdsJsonArray.getInt(i));
            }
        }

        if(movieResultObject.has(BACKDROP_PATH)) {
            backdropPath = movieResultObject.getString(BACKDROP_PATH);
        }

        if(movieResultObject.has(ADULT)) {
            adult = movieResultObject.getBoolean(ADULT);
        }

        if(movieResultObject.has(OVERVIEW)) {
            overview = movieResultObject.getString(OVERVIEW);
        }

        if(movieResultObject.has(RELEASE_DATE)) {
            releaseDate = movieResultObject.getString(RELEASE_DATE);
        }

        return new MovieEntry(id, voteCount, video, voteAverage, title, popularity, posterPath, originalLanguage, originalTitle, backdropPath, adult, overview, releaseDate);
    }

    @Nullable
    MovieResponse parse(final String movieJsonStr) throws JSONException {
        JSONObject movieListJson = new JSONObject(movieJsonStr);

        if (hasHttpError(movieListJson)) {
            return null;
        }

        MovieEntry[] movieList = fromJson(movieListJson);

        return new MovieResponse(movieList);
    }


}
