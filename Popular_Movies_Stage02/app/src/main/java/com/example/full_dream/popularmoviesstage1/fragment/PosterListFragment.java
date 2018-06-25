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
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.adapter.PosterAdapter;
import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.network.TheMovieDBService;
import com.example.full_dream.popularmoviesstage1.viewmodel.PosterListViewModel;
import com.example.full_dream.popularmoviesstage1.viewmodel.SharedViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PosterListFragment extends Fragment implements PosterAdapter.PosterAdapterOnClickHandler {

    // Constants
    private static final String TAG = PosterListFragment.class.getSimpleName();
    private static final int MOST_POPULAR = 0;
    private static final int TOP_RATED = 1;
    private static final int FAVORITES = 2;

    // ViewModels
    private SharedViewModel model;
    private PosterListViewModel viewModel;

    // UI related elements
    private Unbinder mUnbinder;
    private int mSettingsOption;
    private PosterAdapter mPosterAdapter;
    @BindView(R.id.rv_poster_list)
    RecyclerView mRecyclerView;


    /**
     * Mandatory empty constructor for the Fragment Manager to instantiate the fragment.
     */
    public  PosterListFragment(){}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.e(TAG, "PLFRAGMENT onAttach");
    }

    /**
     * Called to do the initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use ViewModelProviders to associate an instance of PosterListViewModel scoped with the
        // lifecycle of the UIController MainActivity
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // Use ViewModelProviders to associate an instance of PosterListViewModel scoped with the
        // lifecyce of the UIController PosterListFragment
        viewModel = ViewModelProviders.of(this).get(PosterListViewModel.class);

        Log.e(TAG, "PLFRAGMENT onCreate");
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

        Log.e(TAG, "PLFRAGMENT onCreateView");

        // Return the fragment view
        return rootView;
    }

    /**
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("settingsOption", mSettingsOption);
        Log.e(TAG, "PLFRAGMENT saveInstanceState: " + mSettingsOption);
    }

    /**
     * Tells the fragment that its activity has completed its own Activity.onCreate().
     *  Re-populate the PosterAdapter based on previously selected Menu setting option, or
     *  populate UI initially if first time round.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     *                            Contains the Menu setting option to restore PosterAdapter on
     *                            orientation change, else null.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Recommended to place call to setHasOptionsMenu(true) in
        // onActivityCreated() for consistent calls after onCreateView()
        // Source:
        //  https://github.com/JakeWharton/ActionBarSherlock/issues/935
        setHasOptionsMenu(true);

        // If first time initialize UI, else populate UI using bundled settings option int
        if(savedInstanceState == null){
            populateUI(mSettingsOption);
            Log.e(TAG, "PLFRAGMENT first time");
        } else {
            mSettingsOption = savedInstanceState.getInt("settingsOption");
            populateUI(mSettingsOption);
            Log.e(TAG, "PLFRAGMENT populate using " + mSettingsOption);
        }

        Log.e(TAG, "PLFRAGMENT onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.e(TAG, "PLFRAGMENT onCreateView");
    }

    /**
     * Makes the fragment begin interacting with the user (based on its containing
     * activity being resumed).
     *  Called after Fragment has been paused or restarted.
     */
    @Override
    public void onResume() {
        super.onResume();

        Log.e(TAG, "PLFRAGMENT onResume");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.e(TAG, "PLFRAGMENT onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        Log.e(TAG, "PLFRAGMENT onStop");
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

        Log.e(TAG, "PLFRAGMENT onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.e(TAG, "PLFRAGMENT onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.e(TAG, "PLFRAGMENT onDetach");
    }

    /**
     * Handle RecyclerView item clicks by replacing the current Fragment with a DetailFragment of
     * the selected/clicked-on RecyclerView item.
     *
     * @param movie The clicked on RecyclerView item.
     */
    @Override
    public void onClick(Movie movie) {
        // Set the SharedViewModel to the RecyclerView item click
        model.select(movie);
        Log.e(TAG, "PLFRAGMENT onClick: " + movie.toString());
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.fragment_container, new DetailFragment());
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
     * @param item Option selected.
     * @return True to consume and false to continue menu processing.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.action_popular:
                Log.e(TAG, "PLFRAGMENT menu popular");
                mSettingsOption = MOST_POPULAR;
                item.setChecked(!item.isChecked());
                populateUI(mSettingsOption);
                break;
            case R.id.action_top_rated:
                Log.e(TAG, "PLFRAGMENT menu toprated");
                mSettingsOption = TOP_RATED;
                item.setChecked(!item.isChecked());
                populateUI(mSettingsOption);
                break;
            case R.id.action_favorites:
            Log.e(TAG, "PLFRAGMENT menu favorited");
                mSettingsOption = FAVORITES;
                item.setChecked(!item.isChecked());
                populateUI(mSettingsOption);
        }
        // Reset scroll position to the top
        mRecyclerView.smoothScrollToPosition(0);
        return super.onOptionsItemSelected(item);
    }

    /**
     * Call PosterListViewModel getMovies(), observe the LiveData<List<Movie>>, and populate the
     * RecyclerView by setting PosterAdapter to the returned List<Movie>.
     *
     * @param settingsOption Menu setting option that determines what List<Movie> PosterListViewModel
     *                       getMovies() returns.
     */
    public void populateUI(int settingsOption){
        viewModel.getMovies(settingsOption).observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                // Update UI with favorite movies
                mPosterAdapter.setMovieData(movies);
            }
        });
    }
}
