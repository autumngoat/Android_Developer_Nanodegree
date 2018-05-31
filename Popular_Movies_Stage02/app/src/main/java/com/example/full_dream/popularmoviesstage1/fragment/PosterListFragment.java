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

package com.example.full_dream.popularmoviesstage1.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.full_dream.popularmoviesstage1.BuildConfig;
import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.adapter.PosterAdapter;
import com.example.full_dream.popularmoviesstage1.data.FavoriteContract.*;
import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.utils.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.utils.TheMovieDBService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 11: Building a Content Provider >>
 * 24. Implement Query
 *
 * Other source(s):
 *  https://developer.android.com/reference/android/support/v4/content/CursorLoader
 */
public class PosterListFragment extends Fragment implements PosterAdapter.PosterAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor> {

    private boolean mToggleSearchOption =  true;
    private Unbinder mUnbinder;
    private static final String MOST_POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private String API_KEY = BuildConfig.API_KEY;
    private PosterAdapter mPosterAdapter;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;
    private static final int FAVORITE_LOADER_ID = 1000;

    /**
     * Mandatory empty constructor for the Fragment Manager to instantiate the fragment.
     */
    public  PosterListFragment(){}

    /**
     * Called to do the initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fill the PosterAdapter with the initial data from the network call
        callRetrofit();
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater The Layoutinflater object that can be used to inflate any views
     *                 in the fragment.
     * @param container If not null, this is the parent view that the fragment's UI
     *                  should be attached to.
     * @param savedInstanceState If not null, this fragment is being re-constructed from
     *                           a previous saved state as given here.
     * @return Return the view for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate fragment layout
        View rootView = inflater.inflate(R.layout.fragment_poster_list, container, false);

        // Non-Activity Binding - Fragments
        // Binding Reset - Set views to null in onDestroyView
        //  source: http://jakewharton.github.io/butterknife/
        mUnbinder = ButterKnife.bind(this, rootView);

        // Calculate auto-fit number of columns for GridLayoutManager
        // Source: https://stackoverflow.com/questions/33575731/gridlayoutmanager-how-to-auto-fit-columns
//        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
//        int widthOfRecyclerView = 120;
//        int numberOfColumns = (int) (dpWidth / widthOfRecyclerView);

        // Least common multiple of 2 and 3 is 6
        int numberOfColumns = 6;

        // Use a Grid Layout Manager as per the rubric
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);

        // Experimented with different span counts on different rows from this code:
        // https://stackoverflow.com/questions/31112291/recyclerview-layoutmanager-different-span-counts-on-different-rows
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // Five is the total number of items in a repeated section:
                //  2 items spanning 3 columns in row 1,
                //  3 items spanning 2 columns in row 2,
                //  and then repeat
                switch(position % 5){
                    // First row of 2 items span 3 columns each (6 columns total)
                    case 0:
                    case 1:
                        return 3;
                    // Second row of 3 items span 2 columns each (6 columns total)
                    case 2:
                    case 3:
                    case 4:
                        return 2;
                    default:
                        return 1;
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setHasFixedSize(true);

        mPosterAdapter = new PosterAdapter(this);
        // Set the view to the empty PosterAdapter
        mRecyclerView.setAdapter(mPosterAdapter);

        // Checked out https://developer.android.com/training/basics/network-ops/managing
        // for how to check for network status check
//        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if(netInfo != null && netInfo.isConnected()){
//            callRetrofit();
//        } else {
//            Toast.makeText(this, netConnectMsg, Toast.LENGTH_LONG).show();
//        }

        // Return the fragment view
        return rootView;
    }

    /**
     * Tells the fragment that its activity has completed its own Activity.onCreate().
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Recommended to place call to sethasOptionsMenu(true) in
        // onActivityCreated() for consistent calls after onCreateView()
        // Source:
        //  https://github.com/JakeWharton/ActionBarSherlock/issues/935
        setHasOptionsMenu(true);
    }

    /**
     * Makes the fragment begin interacting with the user (based on its containing
     * activity being resumed).
     */
    @Override
    public void onResume() {
        super.onResume();

        // Re-queries for all favorites
        getLoaderManager().restartLoader(FAVORITE_LOADER_ID, null, this);
    }

    /**
     * Called when the fragment is no longer in use. This is called after onStop() and
     * before onDetach().
     * If you override this method you must call through the superclass implementation.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
     */
    public void callRetrofit(){
        // Instantiate the Retrofit (type safe HTTP) client
        RetrofitClient client = new RetrofitClient();

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<MovieResponse> call;

        // Popular or Top Rated?
        if(mToggleSearchOption){
            call = service.getMovies(MOST_POPULAR, API_KEY);
        } else {
            call = service.getMovies(TOP_RATED, API_KEY);
        }

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                mPosterAdapter.setMovieData(movies);
                mPosterAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Handle RecyclerView item clicks to launch DetailActivity.
     */
    @Override
    public void onClick(Movie movie) {
        // Create a DetailFragment instance
        DetailFragment detailFragment = new DetailFragment();
        // Create a Bundle instance
        Bundle bundle = new Bundle();
        // Place Movie Parcelable inside the Bundle with "deets" key
        bundle.putParcelable("deets", movie);
        // Pass a bundle to the Fragment's constructor
        detailFragment.setArguments(bundle);
        // Return the FragmentManager for interacting with Fragments in this Fragment
        FragmentManager fm = getFragmentManager();
        // Start a series of Fragment edit operations associated with this FragmentManager
        FragmentTransaction ft = fm.beginTransaction();
        // Add this transaction to the back stack
        ft.addToBackStack(null);
        // Replace the PosterListFragment with the DetailsFragment
        ft.replace(R.id.fragment_container, detailFragment);
        // Schedules a commit of this transaction (does not happen immediately) to be done
        // on the main/UI thread when the thread next becomes available
        ft.commit();
    }

    /**
     * Inflate the menu for this Activity, only called once.
     *
     * @param menu Menu resource file to inflate for this activity
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Remove existing menu before inflating another menu
        // Source:
        //  https://stackoverflow.com/questions/8472776/fragments-with-the-same-menu-on-the-same-layout-cause-duplicated-menuitem/8495697#8495697
        menu.clear();
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    /**
     * Report that this fragment would like to populate the options menu by receiving
     * a call to onCreateOptionsMenu().
     *
     * @param hasMenu If true, then the fragment has menu options to contribute.
     */
    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
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
                mToggleSearchOption = true;
                item.setChecked(!item.isChecked());
                // Stop Favorite Loader
                getLoaderManager().destroyLoader(FAVORITE_LOADER_ID);
                callRetrofit();
                break;
            case R.id.action_top_rated:
                mToggleSearchOption = false;
                item.setChecked(!item.isChecked());
                // Stop Favorite Loader
                getLoaderManager().destroyLoader(FAVORITE_LOADER_ID);
                callRetrofit();
                break;
            case R.id.action_favorites:
                item.setChecked(!item.isChecked());
                // Ensure a loader is initialized and active.  If the loader DNE, one is created,
                // otherwise the last created loader is re-used.
                getLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
                break;
        }
        // Reset scroll position to the top
        mRecyclerView.smoothScrollToPosition(0);
        return super.onOptionsItemSelected(item);
    }

    //////////////////////
    // Loader Callbacks //
    //////////////////////

    /**
     * Instantiate and return a new Loader for the given ID.
     *
     * @param id The ID whose loader is to be created.
     * @param args Any arguments supplied by the caller.
     * @return Return a new Loader instance that is ready to start loading.
     */
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Creates a fully-specified CursorLoader
        return new CursorLoader(getContext(),
                FavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    /**
     * Called when a previously created loader has finished its load.
     *
     * @param loader The Loader that has finished.
     * @param data The data generated by the Loader.
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        List<Movie> movies = new ArrayList<>();
        if(data.getCount() > 0) {
            // Iterate over rows and create Movie objects using Cursor data,
            // and add to List of Movie objects
            while(data.moveToNext()) {
                Movie movie = new Movie();

                // As required by the rubric
                //  movieId required for Reviews and Trailers
                movie.setTitle(data.getString(data.getColumnIndex(FavoriteEntry.COLUMN_NAME_TITLE)));
                movie.setId(data.getInt(data.getColumnIndex(FavoriteEntry.COLUMN_NAME_MOVIE_ID)));
                // As mentioned as bonus on the rubric
                movie.setReleaseDate(data.getString(data.getColumnIndex(FavoriteEntry.COLUMN_NAME_RELEASE_DATE)));
                movie.setOverview(data.getString(data.getColumnIndex(FavoriteEntry.COLUMN_NAME_SYNOPSIS)));
                movie.setVoteAverage(data.getDouble(data.getColumnIndex(FavoriteEntry.COLUMN_NAME_RATING)));
                movie.setPosterPath(data.getString(data.getColumnIndex(FavoriteEntry.COLUMN_NAME_POSTER)));

                movies.add(movie);
            }

            // Close the Cursor
            data.close();
        }
        // Set Adapter to new data
        mPosterAdapter.setMovieData(movies);
        // Notify Adapter of data set change to update UI
        mPosterAdapter.notifyDataSetChanged();
    }

    /**
     * Called when the previously created loader is being reset, and thus making its data unavailable.
     *
     * @param loader The Loader that is being reset.
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        // Remove any references to the Loader's data
        mPosterAdapter.setMovieData(null);
    }
}
