package com.example.full_dream.popularmoviesstage1.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Utilities used to communicate with the network.
 */
public class NetworkUtils {

    // Looked to T05b.02-Exercise-AddAsyncTaskLoader for network handling utilities
    // Search query structure: https://developers.themoviedb.org/3/discover/movie-discover
    // Image query structure: https://developers.themoviedb.org/3/getting-started/images
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String TMDB_IMG_BASE_URL = "https://image.tmdb.org/t/p";
    private static final String IMAGE_FILE_SIZE = "w185";
    private static final String TMDB_DISCOVER_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String PARAM_API_KEY = "api_key";
    private static final String apiKey = "getYourOwnApiKey";

    /**
     * Bulds the URL used to query TheMovieDB (TMDB).
     *
     * @param tmdbDiscoveryQuery The keyword that will be queried for.
     * @return The URL used to query TheMovieDB.
     */
    public static URL buildSearchUrl(String tmdbDiscoveryQuery){
        Uri builtUri = Uri.parse(TMDB_DISCOVER_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_SORT_BY, tmdbDiscoveryQuery)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "TMDB Discovery query resulted in an malformed URL: " + e);
        }

        return url;
    }

    /**
     * Returns the entire JSON result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The JSON contents of the HTTP response.
     * @throws IOException related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static Uri buildImgUri(String posterPath){
        if(posterPath.isEmpty()){
            return null;
        }

        Uri builtUri = Uri.parse(TMDB_IMG_BASE_URL).buildUpon()
                .appendPath(IMAGE_FILE_SIZE)
                .appendPath(posterPath)
                .build();
        return builtUri;
//        URL url = null;
//        try {
//            url = new URL(builtUri.toString());
//        } catch (MalformedURLException e) {
//            Log.e(TAG, "TMDB Image query resulted in an malformed URL: " + e);
//        }
//
//        return url;
    }
}
