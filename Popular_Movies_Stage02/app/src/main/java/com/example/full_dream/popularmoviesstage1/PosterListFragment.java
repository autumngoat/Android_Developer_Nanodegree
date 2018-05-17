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
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.utils.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.utils.TheMovieDBService;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PosterListFragment extends Fragment implements PosterAdapter.PosterAdapterOnClickHandler {

    private boolean mToggleSearchOption =  true;
    private static final String MOST_POPULAR = "popular";
    private static final String TOP_RATED = "top_rated";
    private static final String API_KEY = "GetYourOwnApiKey";
    private static final int NUMBER_OF_COLUMNS = 3;
    private GridLayoutManager mLayoutManager;
    private PosterAdapter mPosterAdapter;
    @BindString(R.string.title)
    String title;
    @BindString(R.string.release_date)
    String releaseDate;
    @BindString(R.string.poster_path)
    String posterPath;
    @BindString(R.string.vote_average)
    String voteAvg;
    @BindString(R.string.plot_synopsis)
    String plotSynopsis;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;

    /**
     * Mandatory empty constructor for the Fragment Manager to instantiate the fragment.
     */
    public  PosterListFragment(){}

    /**
     * Setup the data component of the Fragment.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //
        setHasOptionsMenu(true);

        // Fill the PosterAdapter with the initial data from the network call
        callRetrofit();
    }

    /**
     * Setup the UI component of the Fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate fragment layout
        View rootView = inflater.inflate(R.layout.fragment_poster_list, container, false);
        // Grab the view from the fragment layout
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_poster_list);
        // Use a Grid Layout Manager as per the rubric
        mLayoutManager = new GridLayoutManager(getActivity(), NUMBER_OF_COLUMNS);
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
            call = service.getPopularMovies(MOST_POPULAR, API_KEY);
        } else {
            call = service.getTopRatedMovies(TOP_RATED, API_KEY);
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
        Context context = getActivity();
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        // I didn't know you could name the EXTRA's!!!
        // No need for enumeration or remembering the order of the EXTRAs for extraction like before
//        intentToStartDetailActivity.putExtra(title, movie.getTitle());
//        intentToStartDetailActivity.putExtra(releaseDate, movie.getReleaseDate());
//        intentToStartDetailActivity.putExtra(posterPath, movie.getPosterPath());
//        intentToStartDetailActivity.putExtra(voteAvg, Double.toString(movie.getVoteAverage()));
//        intentToStartDetailActivity.putExtra(plotSynopsis, movie.getOverview());

        // Returns 'title'
        Toast.makeText(getActivity(), "Movie: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
        // All return the 'plotSynopsis'
//        Log.e("rabbit", "intent title: " + intentToStartDetailActivity.getStringExtra(title));
//        Log.e("rabbit", "intent releasedate: " + intentToStartDetailActivity.getStringExtra(releaseDate));
//        Log.e("rabbit", "intent posterpath: " + intentToStartDetailActivity.getStringExtra(posterPath));

        Log.e("rabbit", "PosterListFragment: " + movie.toString());

        intentToStartDetailActivity.putExtra("deets", movie);

        startActivity(intentToStartDetailActivity);
    }

    /**
     * Inflate the menu for this Activity, only called once.
     *
     * @param menu Menu resource file to inflate for this activity
     * @return Must return true for menu to appear
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

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
                callRetrofit();
                return true;
            case R.id.action_top_rated:
                mToggleSearchOption = false;
                callRetrofit();
                return true;
            case R.id.action_favorites:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}