package com.dobrucali.bestmovies.data.network;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String MOVIE_DB_API_KEY = "e8707d3ec1101427f393a542a73b8b19";
    private static final String SORT_BY_PARAM = "sort_by";
    private static final String API_KEY_PARAM = "api_key";
    private static final String PAGE_PARAM = "page";

    static URL getUrl(String sortBy,int page) {
        return buildUrlWithQuery(sortBy, page);
    }


    private static URL buildUrlWithQuery(String sortBy, int page) {
        Uri movieQueryUri = Uri.parse(MOVIE_DB_BASE_URL).buildUpon()
                .appendQueryParameter(SORT_BY_PARAM, sortBy)
                .appendQueryParameter(API_KEY_PARAM, MOVIE_DB_API_KEY)
                .appendQueryParameter(PAGE_PARAM, String.valueOf(page))
                .build();

        try {
            URL movieQueryUrl = new URL(movieQueryUri.toString());
            Log.v(TAG, "URL: " + movieQueryUrl);
            return movieQueryUrl;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }


}
