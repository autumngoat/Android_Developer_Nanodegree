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

// Android Imports
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

// 3rd Party Imports - ButterKnife
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.adapter.ReviewAdapter;
import com.example.full_dream.popularmoviesstage1.adapter.TrailerAdapter;
import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.Review;
import com.example.full_dream.popularmoviesstage1.model.Trailer;
import com.example.full_dream.popularmoviesstage1.viewmodel.DetailViewModel;
import com.example.full_dream.popularmoviesstage1.viewmodel.SharedViewModel;

// 3rd Party Imports - com - Picasso
import com.squareup.picasso.Picasso;

// Java Imports
import java.util.List;

/**
 * UI component that shows off the details of a particular Movie
 */
public class DetailFragment extends Fragment implements TrailerAdapter.TrailerAdapterOnClickHandler {

    // Constant
    private static final String TAG = DetailFragment.class.getSimpleName();

    // Favorite status messages
    @BindString(R.string.favorites_plus)
    String mAddFavoriteMsg;
    @BindString(R.string.favorites_minus)
    String mRemoveFavoriteMsg;

    // IU related variables
    @BindView(R.id.iv_poster_icon)
    ImageView mPosterIcon;
    @BindView(R.id.tv_rating)
    TextView mRating;
    @BindView(R.id.tv_title)
    TextView mTitle;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mOriginalTitle;
    @BindView(R.id.tv_release_date)
    TextView mReleaseDate;
    @BindView(R.id.iv_backdrop)
    ImageView mBackdrop;
    @BindView(R.id.tv_summary)
    TextView mSummary;
    @BindView(R.id.rv_review_list)
    RecyclerView mReviewRecyclerView;
    @BindView(R.id.rv_trailer_list)
    RecyclerView mTrailerRecyclerView;
    @BindView(R.id.detail_fab_fav)
    FloatingActionButton fab;

    // State saving variables
    private Parcelable mTrailerLayoutManagerState;
    private Parcelable mReviewLayoutManagerState;

    // Convenience variables
    private Movie mSelectedMovie;                   // Exists because it made insert/delete easier for FAB onClick()
    private boolean mIsFavorite;                    // Seemed to be the easiest way to deal with FAB favorite status and onClick()
    private String mTransitionName;

    // Adapters
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;

    // ViewModel
    private DetailViewModel mDetailViewModel;

    // Binding Reset
    //  An unbinder contract that will unbind views when called
    //  Source: https://jakewharton.github.io/butterknife/javadoc/butterknife/Unbinder.html
    private Unbinder mUnbinder;

    // Bundle key(s)
    @BindString(R.string.trailer_list_state)
    String mTrailerListStateKey;
    @BindString(R.string.review_list_state)
    String mReviewListStateKey;

    /**
     * Mandatory empty constructor for the Fragment Manager to instantiate the fragment.
     */
    public DetailFragment() {}

    /**
     * Sets up the data component of the Fragment.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve the common/shared ViewModel to share data between two Fragments
        //  Both Fragments use getActivity() when getting the ViewModelProvider
        //   As a result, both Fragments receive the same SharedViewModel instance, which is scoped
        //   to the Activity
        // Source: https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
        SharedViewModel model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // Retrieve the selected/clicked-on RecyclerView item Movie object
        mSelectedMovie = model.getSelected().getValue();
        // Retrieve the selected/clicked-on ReyclerView item shared element's/view's transition name
        mTransitionName = model.getTransitionName();

        // Create a ViewModel (similar to the PosterListViewModel in setupViewModel(), but with
        // an instance of factory as a parameter)
        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
    }

    /**
     * Inflates the fragment layout file and sets the contents to be displayed.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        // Non-Activity Binding - Fragments
        // Binding Fragment in onCreateView to later set all bound views to null in onDestroyView()
        //  source: http://jakewharton.github.io/butterknife/
        mUnbinder = ButterKnife.bind(this, rootView);

        // Populate DetailFragment UI
        populateUI(mSelectedMovie);

        // Setup RecyclerViews of Trailers and Reviews
        setupTrailers(savedInstanceState);
        setupReviews(savedInstanceState);

        mDetailViewModel.getMovieById(mSelectedMovie.getId()).observe(this, new Observer<Movie>() {
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
                //   viewModel.getMovieById(mSelectedMovie.getId()).removeObserver(this);
                //   Initial setup cost: (Same but happens twice for some reason)
                //    Call DetailViewModel getMovieById
                //     Which calls MovieRepository getMovieId
                //   On every click event:
                //    FAB onClick is triggered
                //    Call DetailViewModel getMovieById
                //     Which calls MovieRepository getMovieId
                //    Followed by onChanged()

                if (movieEntry != null) {
                    // ...change FAB icon to show as a Favorite and...
                    fab.setImageResource(R.drawable.ic_favorite_red);
                    // ...mark as a Favorite
                    mIsFavorite = true;
                } else {
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
        fab.setOnClickListener(new View.OnClickListener() {

            String snackBarText;

            @Override
            public void onClick(View v) {
                // If already a Favorite, then...
                if (mIsFavorite) {
                    // ...delete movie from the local database and...
                    mDetailViewModel.deleteMovie(mSelectedMovie);

                    // ...toggle FAB image resource based on favorite status
                    fab.setImageResource(R.drawable.ic_favorite_white);

                    // Set SnackBar text
                    snackBarText = mRemoveFavoriteMsg;
                } else {
                    // Insert Movie to Database
                    mDetailViewModel.insertMovie(mSelectedMovie);

                    // Toggle FAB image resource based on favorite status
                    fab.setImageResource(R.drawable.ic_favorite_red);

                    // Set SnackBar text
                    snackBarText = mAddFavoriteMsg;
                }
                // Show SnackBar text
                Snackbar.make(v, snackBarText, Snackbar.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    /**
     * Called to ask the fragment to save its current dynamic state, so it can later be
     * reconstructed in a new instance of its process is restarted.
     *  Save TrailerLayoutManager onSaveInstanceState() Parcelable state, which we will need in order
     *  to restore the scroll position in setupTrailers() before the first layout pass.
     *
     *  Save ReviewLayoutManager onSaveInstanceState() Parcelable state, which we will need in order
     *  to restore the scroll position in setupReviews() before the first layout pass.
     *
     * Comments source:
     * https://developer.android.com/reference/android/support/v4/app/Fragment#onSaveInstanceState(android.os.Bundle)
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mTrailerRecyclerView != null){
            mTrailerLayoutManagerState = mTrailerRecyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(mTrailerListStateKey, mTrailerLayoutManagerState);
        }

        if(mReviewRecyclerView != null){
            mReviewLayoutManagerState = mReviewRecyclerView.getLayoutManager().onSaveInstanceState();
            outState.putParcelable(mReviewListStateKey, mReviewLayoutManagerState);
        }
    }

    /**
     * Tells the fragment that its activity has completed its own Activity.onCreate().
     *  Used here to limit network calls, and fill the TrailerAdapter and ReviewAdapter with
     *  their network call contents.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Did this mostly to limit network calls instead of actually wanting to observe data that
        // will likely never change
        //  It did help me consolidate all network call related code into the Repository pattern,
        //  so there is that
        mDetailViewModel.getTrailerList(mSelectedMovie.getId()).observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                mTrailerAdapter.setTrailerList(trailers);
            }
        });

        // Ditto
        mDetailViewModel.getReviewList(mSelectedMovie.getId()).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                mReviewAdapter.setReviewList(reviews);
            }
        });
    }

    /**
     * Called when the view previously created by onCreatedView() has been detached from the fragment.
     * The next time the fragment needs to be displayed, a new view will be created.
     * This is called after onStop().
     * This is called before onDestroy().
     * It is called regardless of whether onCreateView() returned a non-null view.
     * The recommended place to call Butterknife's unbind().
     *
     * Comment source:
     * https://developer.android.com/reference/android/support/v4/app/Fragment
     * http://jakewharton.github.io/butterknife/
     *
     * Used here to understand the fragment lifecycle and detect duplicate DetailFragments.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Sets the views to null
        mUnbinder.unbind();
    }

    /**
     * Handles ReviewAdapter item clicks to play the YouTube video.
     */
    public void onClick(String youtubeKey) {
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
     * Updates the UI with Movie instance details except for the Trailers and Reviews RecyclerViews.
     *
     * @param movie Selected Movie instance.
     */
    public void populateUI(Movie movie) {

        // Icons made by "https://www.flaticon.com/authors/freepik"
        // Title: "Popcorn"
        // Licensed by Creative Commons BY 3.0
        Picasso.get()
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(mPosterIcon);

        // Set transition name
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            mPosterIcon.setTransitionName(mTransitionName);
        }

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
        mOriginalTitle.setTitle(originalTitle + " (" + originalLanguage + ")");
        mReleaseDate.setText(date);
        mSummary.setText(summary);
    }

    /**
     * Setup the UI for the RecyclerView, Adapter, and LayoutManager for a list of Trailer objects.
     *
     * @param savedInstanceState Used to persist the previous scroll positions, if any.
     */
    public void setupTrailers(Bundle savedInstanceState){

        // Cannot use the same LinearLayoutManager for both Trailer and Review RecyclerViews
        LinearLayoutManager mTrailerLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        if(savedInstanceState != null){
            // Restore trailer list state Parcelable to get previous scroll position (among other things)
            mTrailerLayoutManagerState = savedInstanceState.getParcelable(mTrailerListStateKey);
        }

        if(mTrailerLayoutManagerState != null){
            // Update the LayoutManager with the scroll position previous to orientation change
            mTrailerLayoutManager.onRestoreInstanceState(mTrailerLayoutManagerState);
        }

        // Set the RecyclerView.LayoutManager that this RecyclerView will use, according to:
        //  https://developer.android.com/reference/android/support/v7/widget/RecyclerView#setlayoutmanager
        mTrailerRecyclerView.setLayoutManager(mTrailerLayoutManager);

        // Size of the RecyclerViewer does NOT depend on the adapter content (meaning that the
        // content is unlikely to change often enough to require resizing the RecyclerView)
        // according to:
        //  https://stackoverflow.com/questions/28827597/when-do-we-use-the-recyclerview-sethasfixedsize/28828749
        mTrailerRecyclerView.setHasFixedSize(true);

        // Need to initialize TrailerAdapter or else NPE when doing the network call
        mTrailerAdapter = new TrailerAdapter(this);

        // Set the view to the empty TrailerAdapter
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
    }

    /**
     * Setup the UI for the RecyclerView, Adapter, and LayoutManager for a list of Review objects.
     *
     * @param savedInstanceState Used to persist the previous scroll positions, if any.
     */
    public void setupReviews(Bundle savedInstanceState){

        LinearLayoutManager mReviewLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL,
                false);

        if(savedInstanceState != null){
            // Restore review list state Parcelable to get previous scroll position (among other things)
            mReviewLayoutManagerState = savedInstanceState.getParcelable(mReviewListStateKey);
        }

        if(mReviewLayoutManagerState != null){
            // Update the LayoutManager with the scroll position previous to orientation change
            mReviewLayoutManager.onRestoreInstanceState(mReviewLayoutManagerState);
        }

        // Set the RecyclerView.LayoutManager that this RecyclerView will use, according to:
        //  https://developer.android.com/reference/android/support/v7/widget/RecyclerView#setlayoutmanager
        mReviewRecyclerView.setLayoutManager(mReviewLayoutManager);

        // Size of the RecyclerViewer does NOT depend on the adapter content (meaning that the
        // content is unlikely to change often enough to require resizing the RecyclerView)
        // according to:
        //  https://stackoverflow.com/questions/28827597/when-do-we-use-the-recyclerview-sethasfixedsize/28828749
        mReviewRecyclerView.setHasFixedSize(true);

        // Need to initialize ReviewAdapter or else NPE when doing the network call
        mReviewAdapter = new ReviewAdapter();

        // Set the view to the empty ReviewAdapter
        mReviewRecyclerView.setAdapter(mReviewAdapter);
    }
}
