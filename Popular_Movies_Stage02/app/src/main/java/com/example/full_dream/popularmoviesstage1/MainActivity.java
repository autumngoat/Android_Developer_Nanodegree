/*
 *
 *  * PROJECT LICENSE
 *  *
 *  * This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *  *
 *  * As part of Udacity Honor code:
 *  *   - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *  *     - Acceptable sources consist of:
 *  *         - Unmodified or modified code from the Udacity courses
 *  *         - A modest amount of unmodified or modified code from third-party sources with attribution
 *  *     - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *  *   - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *  *   may result in the cancellation of my enrollment without refund.
 *  *   - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *  *   conferred.
 *  *
 *  * I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 *  * Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *  *
 *  * Copyright (c) 2018 Seong Wang
 *  *
 *  * Besides the above notice, the following license applies and this license notice must be included in all works derived from
 *  * this project.
 *  *
 *  * MIT License
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package com.example.full_dream.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.utils.JsonUtils;
import com.example.full_dream.popularmoviesstage1.utils.NetworkUtils;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Setup the main page (list of posters)
 */
public class MainActivity extends AppCompatActivity implements PosterAdapter.PosterAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private boolean mToggleSearchOption = true;
    private static final int NUMBER_OF_COLUMNS = 3;
    private static final int MOVIE_SEARCH_LOADER_ID = 100;
    private static final String TAG = MainActivity.class.getSimpleName();
    // Is there any point in doing this over just making it a String constant?
    @BindString(R.string.network_err)
    String networkErr;
    @BindString(R.string.network_connection_msg)
    String netConnectMsg;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;
    private PosterAdapter mPosterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Use a Grid Layout Manager as per the rubric
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mPosterAdapter = new PosterAdapter(this);
        mRecyclerView.setAdapter(mPosterAdapter);

        // Checked out https://developer.android.com/training/basics/network-ops/managing
        // for how to check for network status check
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            getSupportLoaderManager().initLoader(MOVIE_SEARCH_LOADER_ID, null, this);
        } else {
            Toast.makeText(this, netConnectMsg, Toast.LENGTH_LONG).show();
        }
    }

    // Looked to my code for T05b.02-Exercise-AddAsyncTaskLoader for reference for how to setup
    // AsyncTaskLoader

    /**
     * Create and return a new Loader for the given id.
     */
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<ArrayList<Movie>>(this) {
            // Cache the result
            ArrayList<Movie> mMovieData;

            // Equivalent to AsyncTask doInBackground

            /**
             * Perform the actual task/load, which is to make a network call
             * to TMDB, parse the JSON response, and return the resultant
             * Movie objects.
             */
            @Nullable
            @Override
            public ArrayList<Movie> loadInBackground() {
                ArrayList<Movie> films = new ArrayList<>();

                URL searchUrl;

                if(mToggleSearchOption){
                    searchUrl = NetworkUtils.buildSearchUrl(getResources()
                            .getString(R.string.most_popular_desc));
                } else {
                    searchUrl = NetworkUtils.buildSearchUrl(getResources()
                            .getString(R.string.highest_rated_desc));
                }
                try {
                    String jsonResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                    films = JsonUtils.parseMovieJson(jsonResult);
                } catch (IOException e) {
                    Log.e(TAG, networkErr + e);
                }

                return films;
            }

            // Equivalent to AsyncTask onPreExecute
            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mMovieData != null) {
                    deliverResult(mMovieData);
                } else {
                    forceLoad();
                }
            }

            /**
             * Cache the data in a member variable and deliver it in onStartLoading.
             *
             * @param data Data to cache and deliver to onStartLoading
             */
            @Override
            public void deliverResult(@Nullable ArrayList<Movie> data) {
                mMovieData = data;
                super.deliverResult(data);
            }
        };
    }

    /**
     * Show data when load is finished or do nothing
     *
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if (data != null) {
            mPosterAdapter.setMovieData(data);
        }
    }

    /**
     * Called when a Loader is reset to make data unavailable
     */
    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        // Empty on purpose
    }

    /**
     * Handle RecyclerView item clicks to launch DetailActivity.
     *
     * @param movieDetails String array of parsed JSON data to pass to DetailActivity
     */
    @Override
    public void onClick(String[] movieDetails) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, movieDetails);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * Inflate the menu for this Activity, only called once.
     *
     * @param menu Menu resource file to inflate for this activity
     * @return Must return true for menu to appear
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return getResources().getBoolean(R.bool.hasMenuInflated);
    }

    /**
     * Handle clicks on menu items.
     *
     * @param item Option selected
     * @return True to consume and false to continue menu processing
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_popular:
                mPosterAdapter.setMovieData(null);
                mToggleSearchOption = true;
                getSupportLoaderManager().restartLoader(MOVIE_SEARCH_LOADER_ID, null, this);
                return true;
            case R.id.action_top_rated:
                mPosterAdapter.setMovieData(null);
                mToggleSearchOption = false;
                getSupportLoaderManager().restartLoader(MOVIE_SEARCH_LOADER_ID, null, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
