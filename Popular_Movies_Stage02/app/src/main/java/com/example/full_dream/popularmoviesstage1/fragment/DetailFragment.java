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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.full_dream.popularmoviesstage1.BuildConfig;
import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.adapter.ReviewAdapter;
import com.example.full_dream.popularmoviesstage1.adapter.TrailerAdapter;
import com.example.full_dream.popularmoviesstage1.database.AppDatabase;
import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.Review;
import com.example.full_dream.popularmoviesstage1.model.ReviewResponse;
import com.example.full_dream.popularmoviesstage1.model.Trailer;
import com.example.full_dream.popularmoviesstage1.model.TrailerResponse;
import com.example.full_dream.popularmoviesstage1.utils.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.utils.TheMovieDBService;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 *
 *
 */
public class DetailFragment extends Fragment implements TrailerAdapter.TrailerAdapterOnClickHandler {

    private Movie mMovie;
    private Unbinder mUnbinder;
    private String API_KEY = BuildConfig.API_KEY;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    // Member variable for the Database
    private AppDatabase mDb;
    @BindView(R.id.iv_background_poster)
    ImageView mBackgroundPoster;
    @BindView(R.id.tv_rating)
    TextView mRating;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.tv_original_title)
    TextView mOriginalTitle;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.iv_backdrop)
    ImageView mBackdrop;
    @BindView(R.id.tv_original_language)
    TextView mOriginalLanguage;
    @BindView(R.id.tv_summary)
    TextView mSummary;
    @BindView(R.id.rv_review_list)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.rv_trailer_list)
    RecyclerView mTrailerRecyclerView;
    @BindView(R.id.detail_fab_fav)
    FloatingActionButton fab;

    /**
     * Mandatory empty constructor for the Fragment Manager to instantiate the fragment.
     */
    public DetailFragment(){}

    /**
     * Setup the data component of the Fragment.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMovie = getArguments().getParcelable("deets");
        if(mMovie == null){
            Log.e("rabbit", "not again");
        }

        // Initialize the Database
        mDb = AppDatabase.getInstance(getContext());

        callRetrofitForTrailers();
        callRetrofitForReviews();
    }

    /**
     * Inflates the fragment layout file and sets the contents to be displayed.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        // Non-Activity Binding - Fragments
        // Binding Reset - Set views to null in onDestroyView
        //  source: http://jakewharton.github.io/butterknife/
        mUnbinder = ButterKnife.bind(this, rootView);

        // Icons made by "https://www.flaticon.com/authors/freepik"
        // Title: "Popcorn"
        // Licensed by Creative Commons BY 3.0
        Picasso.get()
                .load(mMovie.getPosterPath())
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(mBackgroundPoster);

        String rating = Double.toString(mMovie.getVoteAverage());
        String title = mMovie.getTitle();
        String originalTitle = mMovie.getOriginalTitle();
        String date = mMovie.getReleaseDate();

        Picasso.get()
                .load(mMovie.getBackdropPath())
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(mBackdrop);

        String originalLanguage = mMovie.getOriginalLanguage();
        String summary = mMovie.getOverview();

        mRating.setText(rating);
        mTitle.setText(title);
        mOriginalTitle.setText(originalTitle);
        mReleaseDate.setText(date);
        mOriginalLanguage.setText(originalLanguage);
        mSummary.setText(summary);

        // Cannot use the same LinearLayoutManager for both Trailer and Review RecyclerViews
        LinearLayoutManager mTrailerLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        // Setup Trailer RecyclerView and Adapter
        mTrailerRecyclerView.setLayoutManager(mTrailerLayoutManager);
        mTrailerAdapter = new TrailerAdapter(this);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);

        LinearLayoutManager mReviewLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        // Setup Review RecyclerView and Adapter
        mReviewRecyclerView.setLayoutManager(mReviewLayoutManager);
        // Need to initialize ReviewAdapter or else NPE when running callRetrofitForReviews()
        mReviewAdapter = new ReviewAdapter();
        mReviewRecyclerView.setAdapter(mReviewAdapter);

        // DetailFragment for 'most popular' and 'highest rated' return 'WHITE and fav status is false' for all movies
        // DetailFragment for 'favorites' returns 'RED and fav status is true' for all movies
        //  This is a problem
        if(mMovie.isFavorite()){
            fab.setImageResource(R.drawable.ic_favorite_red);
            Toast.makeText(getContext(), "RED and fav status is " + mMovie.isFavorite(), Toast.LENGTH_SHORT).show();
        } else {
            fab.setImageResource(R.drawable.ic_favorite_white);
            Toast.makeText(getContext(), "WHITE and fav status is " + mMovie.isFavorite(), Toast.LENGTH_SHORT).show();
        }

        // Setup FAB onClick
        //  Icons made by "https://www.flaticon.com/authors/freepik"
        //  Title: "Favorite"
        //  Licensed by Creative Commons BY 3.0
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Set/toggle favorite status of Movie object on click
                mMovie.setFavorite(!mMovie.isFavorite());

                // Query database for favorite status
                if(mMovie.isFavorite()){
                    // Insert Movie to Database

                    // 1) Get fields
                    // 2) Create Movie object with fields as parameters
                    // 3) mDb.movieDao().insertMovie(movie);
                    //  Followed the Udacity course "Developing Android Apps" >>
                    //  Lesson 12: Android Architecture Components >>
                    //  10. Exercise: Save the Task
                    mDb.movieDao().insertMovie(mMovie);

                    Toast.makeText(getContext(), "Favorited " + mMovie.getTitle() + " and fav status is " + mMovie.isFavorite(), Toast.LENGTH_SHORT).show();

                    // Toggle FAB image resource based on favorite status
                    fab.setImageResource(R.drawable.ic_favorite_red);
                } else {
                    // Delete Movie from Database

                    //  Followed the Udacity course "Developing Android Apps" >>
                    //  Lesson 12: Android Architecture Components >>
                    //  14. Exercise: Delete Task
                    mDb.movieDao().deleteMovie(mMovie);

                    Toast.makeText(getContext(), "Unfavorited " + mMovie.getTitle() + " and fav status is " + mMovie.isFavorite(), Toast.LENGTH_SHORT).show();

                    // Toggle FAB image resource based on favorite status
                    fab.setImageResource(R.drawable.ic_favorite_white);
                }
            }
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
     */
    public void callRetrofitForTrailers(){
        // Instantiate the Retrofit (type safe HTTP) client
        RetrofitClient client = new RetrofitClient();

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<TrailerResponse> call;

        call = service.getTrailers(mMovie.getId(), API_KEY);

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                List<Trailer> trailers = response.body().getResults();

                mTrailerAdapter.setTrailerList(trailers);
                mTrailerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Log.e("rabbit", "Problems retrieving Trailers");
            }
        });
    }

    /**
     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
     */
    public void callRetrofitForReviews(){
        // Instantiate the Retrofit (type safe HTTP) client
        RetrofitClient client = new RetrofitClient();

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<ReviewResponse> call;
        call = service.getReviews(mMovie.getId(), API_KEY);

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                List<Review> review = response.body().getReviews();

                mReviewAdapter.setReviewList(review);
                mReviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("rabbit", "Problems retrieving Reviews");
            }
        });
    }

    /**
     * Handle RecyclerView item clicks to play the YouTube video.
     */
    public void onClick(String youtubeKey){
        try {
            // Launch YouTube app
            getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube:" + youtubeKey)));
        } catch (ActivityNotFoundException ex) {
            // Launch YouTube website
            getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + youtubeKey)));
        }
    }
}
