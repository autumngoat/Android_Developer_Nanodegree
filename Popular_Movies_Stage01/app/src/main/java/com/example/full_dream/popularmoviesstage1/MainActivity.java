package com.example.full_dream.popularmoviesstage1;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.utils.JsonUtils;
import com.example.full_dream.popularmoviesstage1.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private static final int MOVIE_SEARCH_LOADER_ID = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String mostPopular = "popularity.desc";
    private static final String topRated = "vote_average.desc";

    // Following the tutorial code for initial setup of RecyclerView:
    // https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
    @BindView(R.id.recyclerview) RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // This setting is used to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView, which
        // should be true of the movie posters based on the sample UI of Stage 2
        mRecyclerView.setHasFixedSize(true);

        // Use a Grid Layout Manager as per the rubric
        mLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //TODO Specify an adapter
        //mAdapter = new mAdapter(dataSet);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize the loader with MOVIE_SEARCH_LOADER as the ID, null for the bundle, and this
        // for the context
        // Remember to use 'android.support.v4.app.LoaderManager' not 'android.app.LoaderManager'
        // AndroidStudio auto-imports the latter and that class was deprecated in API level P
        getSupportLoaderManager().initLoader(MOVIE_SEARCH_LOADER_ID, null, this);
    }

    // Looked to my code for T05b.02-Exercise-AddAsyncTaskLoader for reference for how to setup
    // AsyncTaskLoader
    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Movie>>(this) {
            // Called on a worker thread to perform the actual load and return the result of the
            // load operation
            @Nullable
            @Override
            public ArrayList<Movie> loadInBackground() {
                ArrayList<Movie> films = new ArrayList<>();

                try{
                    URL searchUrl = NetworkUtils.buildUrl(mostPopular);
                    String jsonResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                    films = JsonUtils.parseMovieJson(jsonResult);
                } catch (IOException e) {
                    Log.e(TAG, "Error with network or stream reading: " + e);
                }

                return films;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        // Empty on purpose
    }
}
