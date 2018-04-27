package com.example.full_dream.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.utils.JsonUtils;
import com.example.full_dream.popularmoviesstage1.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PosterAdapter.PosterAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private static final int MOVIE_SEARCH_LOADER_ID = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String mostPopular = "popularity.desc";
    private static final String topRated = "vote_average.desc";

    // Following the tutorial code for initial setup of RecyclerView:
    // https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
    // ^Fuck that tutorial, looked back at my code for S05.01-Exercise-AsyncTaskLoader
    // for a sanity check
//    @BindView(R.id.rv_poster_list) RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView;
    private PosterAdapter mPosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this, mRecyclerView);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_poster_list);

        // Use a Grid Layout Manager as per the rubric
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mPosterAdapter = new PosterAdapter(this);
        mRecyclerView.setAdapter(mPosterAdapter);

        getSupportLoaderManager().initLoader(MOVIE_SEARCH_LOADER_ID, null, this);
    }

    // Looked to my code for T05b.02-Exercise-AddAsyncTaskLoader for reference for how to setup
    // AsyncTaskLoader
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Movie>>(this) {
            // Cache the result
            ArrayList<Movie> mMovieData;

            // Equivalent to AsyncTask doInBackground
            @Nullable
            @Override
            public ArrayList<Movie> loadInBackground() {
                ArrayList<Movie> films = new ArrayList<>();

                URL searchUrl = NetworkUtils.buildSearchUrl(mostPopular);
                try{
                    String jsonResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                    films = JsonUtils.parseMovieJson(jsonResult);
                } catch (IOException e) {
                    Log.e(TAG, "Error with network or stream reading: " + e);
                }

                return films;
            }

            // Equivalent to AsyncTask onPreExecute
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if(mMovieData != null){
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public void deliverResult(@Nullable ArrayList<Movie> data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if(data == null){

        } else {
            mPosterAdapter.setMovieData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        // Empty on purpose
    }

    @Override
    public void onClick(String todoLater) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, todoLater);
        startActivity(intentToStartDetailActivity);
    }
}
