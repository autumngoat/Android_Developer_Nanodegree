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

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Context;
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
import com.example.full_dream.popularmoviesstage1.network.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.network.TheMovieDBService;
import com.example.full_dream.popularmoviesstage1.viewmodel.DetailViewModel;
import com.example.full_dream.popularmoviesstage1.viewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * UI component that shows off the details of a particular Movie
 */
public class DetailFragment extends Fragment implements TrailerAdapter.TrailerAdapterOnClickHandler {

    // Constant(s)
    private static final String TAG = DetailFragment.class.getSimpleName();

    //
    private Movie mSelectedMovie;
    private String API_KEY = BuildConfig.API_KEY;
    private boolean mIsFavorite;

    // Adapters
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;

    // ViewModels
    private SharedViewModel model;
    private DetailViewModel viewModel;

    // UI related elements
    private Unbinder mUnbinder;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.e(TAG, "DETAILFRAGMENT onAttach");
    }

    /**
     * Setup the data component of the Fragment.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Initialize the Database
//        mDb = AppDatabase.getInstance(getContext());

        // Retrieve the common/shared ViewModel to share data between two Fragments
        //  Both Fragments use getActivity() when getting the ViewModelProvider
        //   As a result, both Fragments receive the same SharedViewModel instance, which is scoped
        //   to the Activity
        // Source: https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // Retrieve the selected/clicked-on RecyclerView item
        mSelectedMovie = model.getSelected().getValue();

        // Create an instance of the factory by passing the database and the movie ID to its
        // constructor
//        DetailViewModelFactory factory = new DetailViewModelFactory(mDb, mMovie.getId());

        // Create a ViewModel (similar to the PosterListViewModel in setupViewModel(), but with
        // an instance of factory as a parameter)
//        final DetailViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        viewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

//        Log.e(TAG, "DETAILFRAGMENT model.getSelected().getValue()" + mMovie.toString());

        callRetrofitForTrailers();
        callRetrofitForReviews();

        Log.e(TAG, "DETAILFRAGMENT onCreate");
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

        // Populate DetailFragment UI
        populateUI(mSelectedMovie);

        viewModel.getMovieById(mSelectedMovie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movieEntry) {

                // Removing the observer is not warranted
                //  w/ the observer:
                //   Initial setup cost:
                //    Call DetailViewModel getMovieById
                //     Which calls MovieRepository getMovieById
                //   On every click event:
                //    FAB onClick is triggered
                //     Followed by onChanged()
                //      Nothing else -> Perfect
                //  w/o the observer (Observer<Movie> observer):
//                   viewModel.getMovieById(mSelectedMovie.getId()).removeObserver(this);
                //   Initial setup cost: (Same but happens twice for some reason)
                //    Call DetailViewModel getMovieById
                //     Which calls MovieRepository getMovieId
                //   On every click event:
                //    FAB onClick is triggered
                //    Call DetailViewModel getMovieById
                //     Which calls MovieRepository getMovieId
                //    Followed by onChanged()

                if(movieEntry != null){
                    Log.e(TAG, "DETAILFRAGMENT movieEntry NOT null");
                    // ...change FAB icon to show as a Favorite and...
                    fab.setImageResource(R.drawable.ic_favorite_red);
                    // ...mark as a Favorite
                    mIsFavorite = true;
                } else {
                    Log.e(TAG, "DETAILFRAGMENT movieEntry NULL");
                    // Change FAB icon to show as NOT a Favorite
                    fab.setImageResource(R.drawable.ic_favorite_white);
                    // Mark as NOT a Favorite
                    mIsFavorite = false;
                }
            }
        });

        // Setup FAB onClick
        //  Icons made by "https://www.flaticon.com/authors/freepik"
        //  Title: "Favorite"
        //  Licensed by Creative Commons BY 3.0
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // If already a Favorite, then...
                if(mIsFavorite) {
                    Log.e(TAG, "DETAILFRAGMENT delete favorite");
                    // ...delete movie from the local database and...
                    viewModel.deleteMovie(mSelectedMovie);

                    // ...toggle FAB image resource based on favorite status
                    fab.setImageResource(R.drawable.ic_favorite_white);
                } else {
                    Log.e(TAG, "DETAILFRAGMENT insert favorite");
                    // Insert Movie to Database
                    viewModel.insertMovie(mSelectedMovie);

                    // Toggle FAB image resource based on favorite status
                    fab.setImageResource(R.drawable.ic_favorite_red);
                }
            }
        });

        Log.e(TAG, "DETAILFRAGMENT onCreateView");

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e(TAG, "DETAILFRAGMENT onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e(TAG, "DETAILFRAGMENT onStart");
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e(TAG, "DETAILFRAGMENT onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e(TAG, "DETAILFRAGMENT onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.e(TAG, "DETAILFRAGMENT onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();

        Log.e(TAG, "DETAILFRAGMENT onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "DETAILFRAGMENT onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.e(TAG, "DETAILFRAGMENT onDetach");
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

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = RetrofitClient.getApiClient();

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<TrailerResponse> call;

        call = service.getTrailers(mSelectedMovie.getId(), API_KEY);

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

        // Pass service interface to create() to generate an implementation of the API endpoint
        TheMovieDBService service = RetrofitClient.getApiClient();

        // Call represents the HTTP request while the generic parameter, in this case
        // MovieResponse, represents the HTTP response body type which will be converted
        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
        Call<ReviewResponse> call;
        call = service.getReviews(mSelectedMovie.getId(), API_KEY);

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

    /**
     * Update the UI with Movie instance details.
     *
     * @param movie Selected Movie instance.
     */
    public void populateUI(Movie movie){

        // Icons made by "https://www.flaticon.com/authors/freepik"
        // Title: "Popcorn"
        // Licensed by Creative Commons BY 3.0
        Picasso.get()
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(mBackgroundPoster);

        String rating = Double.toString(movie.getVoteAverage());
        String title = movie.getTitle();
        String originalTitle = movie.getOriginalTitle();
        String date = movie.getReleaseDate();

        Picasso.get()
                .load(movie.getBackdropPath())
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(mBackdrop);

        String originalLanguage = movie.getOriginalLanguage();
        String summary = movie.getOverview();

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
    }
}
