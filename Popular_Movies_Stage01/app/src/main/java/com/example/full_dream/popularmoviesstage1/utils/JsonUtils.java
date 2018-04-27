package com.example.full_dream.popularmoviesstage1.utils;

import android.util.Log;

import com.example.full_dream.popularmoviesstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Provides methods to parse JSON and load parsed data into data model(s).
 */
public class JsonUtils {
    // Tweaked code from previous project - Sandwich Club app
    // JSON schema from: https://developers.themoviedb.org/3/discover/movie-discover
    // Apparently total_results != results.length()
    private static String TAG = JsonUtils.class.getSimpleName();
    private static String KEY_RESULTS = "results";
    private static String KEY_TITLE = "title";
    private static String KEY_RELEASE_DATE = "release_date";
    private static String KEY_POSTER_PATH = "poster_path";
    private static String KEY_VOTE_AVG = "vote_average";
    private static String KEY_PLOT_SYNOPSIS = "overview";

    /**
     * Parses the JSON string and loads the contents into a Movie object,
     * which is then added to an ArrayList of Movie objects.
     */
    public static ArrayList<Movie> parseMovieJson(String json){
        ArrayList<Movie> movies = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(json);
            JSONArray results = root.getJSONArray(KEY_RESULTS);
            for(int i = 0; i < results.length(); i++){
                JSONObject result = results.getJSONObject(i);
                String title = result.getString(KEY_TITLE);
                String releaseDate = result.getString(KEY_RELEASE_DATE);
                String posterPath = result.getString(KEY_POSTER_PATH);
                double voteAvg = result.getDouble(KEY_VOTE_AVG);
                String plotSynopsis = result.getString(KEY_PLOT_SYNOPSIS);
                movies.add(new Movie(title, releaseDate, posterPath, voteAvg, plotSynopsis));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing TMDB JSON " + e);
        }

        return movies;
    }
}
