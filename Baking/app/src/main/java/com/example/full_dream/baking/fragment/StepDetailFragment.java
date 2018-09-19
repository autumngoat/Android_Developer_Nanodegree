/*
 *  PROJECT LICENSE
 *
 *  This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *
 *  As part of Udacity Honor code:
 *    - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *      - Acceptable sources consist of:
 *          - Unmodified or modified code from the Udacity courses
 *          - A modest amount of unmodified or modified code from third-party sources with attribution
 *      - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *    - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *    may result in the cancellation of my enrollment without refund.
 *    - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *    conferred.
 *
 *  I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 *  Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *
 *  Copyright (c) 2018 Seong Wang
 *
 *  Besides the above notice, the following license applies and this license notice must be included in all works derived from
 *  this project.
 *
 *  MIT License
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.example.full_dream.baking.fragment;

// Android Imports
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.databinding.FragmentStepDetailBinding;
import com.example.full_dream.baking.viewmodel.SharedViewModel;

// 3rd Party Imports - com - ExoPlayer2
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

/**
 * Sets up the UI as a MediaPlayer/ExoPlayer of Step.videoUrl, the recipe's step instructions a.k.a.
 * Step.description, and navigational buttons for 'previous' and 'next' Step.
 */
public class StepDetailFragment extends Fragment {

    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";

    // Databinding View access so I can access the player view outside of onCreateView
    FragmentStepDetailBinding mBinding;

    // ViewModel(s)
    private SharedViewModel mSharedViewModel;

    // Player
    private SimpleExoPlayer mExoPlayer;

    // Player and MediaSource setup variables
    private Timeline.Window mWindow;
    private DataSource.Factory mMediaDataSourceFactory;
    private DefaultTrackSelector mTrackSelector;
    private boolean mShouldAutoPlay;
    private BandwidthMeter mBandwidthMeter;

    // Playback variables
    private boolean mPlayWhenReady;
    private int mCurrentWindow;
    private long mPlaybackPosition;

    /**
     * Called to do the initial creation of the fragment.
     *
     * Source:
     *  https://android.jlelse.eu/android-exoplayer-starters-guide-6350433f256c
     *  https://google.github.io/ExoPlayer/guide.html
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use ViewModelProviders to associate an instance of SharedViewModel scoped with the
        // lifecycle of the UIController MainActivity
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // If there is no save state, then initialize playback values
        if(savedInstanceState == null){
            mPlayWhenReady = true;
            mCurrentWindow = 0;
            mPlaybackPosition = 0;
        }
        // If there is save state, then load the saved playback values
        else {
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            mCurrentWindow = savedInstanceState.getInt(KEY_WINDOW);
            mPlaybackPosition = savedInstanceState.getLong(KEY_POSITION);
            Log.e("horse", "baratheon");
        }

        // Play media on true and pause media on false
        mShouldAutoPlay = true;
        // Measures bandwidth during playback.
        //  Can be null if not required.
        mBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded
        mMediaDataSourceFactory = new DefaultDataSourceFactory(
                getContext(),                                               // Context
                Util.getUserAgent(getContext(), "baking"),    // Context, Application Name
                (TransferListener<? super DataSource>) mBandwidthMeter);
        //
        mWindow = new Timeline.Window();
    }

    /**
     * Save state so that there is a state to restore on screen orientation change in onCreate ELSE
     * statement.
     *
     * @param outState Bundle to hold play state (paused or play), video play position (timestamp),
     *                 and current window (track number).
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_PLAY_WHEN_READY, mPlayWhenReady);
        outState.putInt(KEY_WINDOW, mCurrentWindow);
        outState.putLong(KEY_POSITION, mPlaybackPosition);
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

        mBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_step_detail,
                container,
                false);

        //
//        mBinding.playerview.requestFocus();

        // Bind Step data to fragment_step_detail.xml
        mBinding.setStep(mSharedViewModel.getSelectedStep());

        return mBinding.getRoot();
    }

    /**
     * If the API level is 24 or higher, then initialize the player and bind the player to the view.
     *  Starting with API level 24 supports multiple windows.
     *   As the app can be visible but not active in split windows mode, need to initialize the
     *   player in onStart().
     *
     * Source:
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
     */
    @Override
    public void onStart() {
        super.onStart();

        if(Util.SDK_INT > 23){

            initializePlayer();

            // Bind the player to the View
            mBinding.playerview.setPlayer(mExoPlayer);
        }
    }

    /**
     * If the API level 23 or less, then initialize the player and bind the player to the view.
     *  Before API level 24, best practice was to wait as long as possible to grab resources, so
     *  wait until onResume() before initializing the player.
     *
     * Source:
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
     */
    @Override
    public void onResume() {
        super.onResume();

        hideSystemUi();

        if(Util.SDK_INT <= 23){
            initializePlayer();

            // Bind the player to the View
            mBinding.playerview.setPlayer(mExoPlayer);
        }
    }

    /**
     * Release the player when app is out of focus.
     *  Before API level 24, there was no guarantee of onStop() being called, so there is a need to
     *  release the player as early as possible in onPause().
     *
     * Source:
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
     */
    @Override
    public void onPause() {
        super.onPause();

        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    /**
     * Starting in API level 24, onStop() is guaranteed to be called and in the paused mode could
     * still be visible since API level 24 introduced multi and split window modes.
     *  Need to wait until onStop() to release the player.
     *
     * Source:
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
     */
    @Override
    public void onStop() {
        super.onStop();

        if(Util.SDK_INT > 24){
            releasePlayer();
        }
    }

    /**
     * Create a new instance of SimpleExoPlayer to set to the player View.
     *
     * Comment Source:
     *  https://google.github.io/ExoPlayer/guide.html
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#4
     */
    private void initializePlayer(){
        if(mExoPlayer == null){
            // Adaptive video playback cuts video and audio files into chunks of a given duration
            //  A player then links them together for playback
            //  The chunks are available in different qualities (size or bitrate)
            //   With this, a player can choose the quality of each chunk according to the network
            //   bandwidth available
            //    After starting with a low quality chunk to be able to render the first frame as
            //    quickly as possible, the player switches to a better quality for the second chunk
            //    if sufficient bandwidth is available (for instance on wifi vs mobile)
            //     At the heart of adaptive streaming is selecting the most appropriate TRACK to
            //     adapt to the environment in which streaming happens

            // The factory method creates an AdaptiveVideoTrackSelection
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
            // Selects a track
            mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
            // Create the player
            //  newSimpleInstance(Context, TrackSelector) returns a SimpleExoPlayer which extends
            //  ExoPlayer to add additional high level player functionality
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), mTrackSelector);

            // Starts and pauses playback
            mExoPlayer.setPlayWhenReady(mShouldAutoPlay);
            boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
            if(haveStartPosition){
                // Seek within the MediaSource
                mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
            }

            // Grab and prepare the MediaSource
            String videoUrl = mSharedViewModel.getSelectedStep().getVideoURL();
            // Prepare the player with the source
            mExoPlayer.prepare(buildMediaSource(videoUrl), !haveStartPosition, false);
        }
    }

    /**
     * Create a MediaSource by loading the media from provided Uri to the DataSource.
     *
     * Source:
     *  https://google.github.io/ExoPlayer/guide.html
     *
     * @param videoSource Video URL from Step.videoURL.
     * @return MediaSource representing the media to be played
     */
    private MediaSource buildMediaSource(String videoSource){
        MediaSource mediaSource = new ExtractorMediaSource
                .Factory(mMediaDataSourceFactory)
                .createMediaSource(Uri.parse(videoSource));

        return mediaSource;
    }

    /**
     * Implementation detail to have a full screen experience.
     *
     * Source:
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
     */
    private void hideSystemUi(){
        mBinding.playerview.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /**
     * Release the player when it is no longer needed while saving the playback state.
     *
     * Source:
     *  https://google.github.io/ExoPlayer/guide.html
     */
    private void releasePlayer(){
        if(mExoPlayer != null){
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            // Grab play/pause state
            mShouldAutoPlay = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
            mTrackSelector = null;
        }
    }
}
