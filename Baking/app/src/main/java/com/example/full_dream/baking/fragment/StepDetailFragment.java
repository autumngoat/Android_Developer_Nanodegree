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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.databinding.FragmentStepDetailBinding;
import com.example.full_dream.baking.viewmodel.SharedViewModel;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
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

    // ViewModel(s)
    private SharedViewModel mSharedViewModel;

    // MediaPlayer
    private SimpleExoPlayer mExoPlayer;

    private Timeline.Window mWindow;
    private DataSource.Factory mMediaDataSourceFactory;
    private DefaultTrackSelector mTrackSelector;
    private boolean mShouldAutoPlay;
    private BandwidthMeter mBandwidthMeter;

    private boolean mPlayWhenReady;
    private int mCurrentWindow;
    private long mPlaybackPosition;

    /**
     * Called to do the initial creation of the fragment.
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

        if(savedInstanceState == null){
            mPlayWhenReady = true;
            mCurrentWindow = 0;
            mPlaybackPosition = 0;
        } else {
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
            mCurrentWindow = savedInstanceState.getInt(KEY_WINDOW);
            mPlaybackPosition = savedInstanceState.getLong(KEY_POSITION);
        }

        mShouldAutoPlay = true;
        mBandwidthMeter = new DefaultBandwidthMeter();
        mMediaDataSourceFactory = new DefaultDataSourceFactory(
                getContext(),                                               // Context
                Util.getUserAgent(getContext(), "baking"),    // Context, Application Name
                (TransferListener<? super DataSource>) mBandwidthMeter);
        mWindow = new Timeline.Window();
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

        FragmentStepDetailBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_step_detail,
                container,
                false);

//        if(Util.SDK_INT > 23){
//            initializePlayer();
//        } else {
//            initializePlayer();
//
//            binding.playerview.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LOW_PROFILE
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }

        binding.playerview.requestFocus();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(mBandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), mTrackSelector);
        // Setup PlayerView by passing the SimpleExoPlayer instance to the PlayerView
        binding.playerview.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(mShouldAutoPlay);

//        String blah = mSharedViewModel.getSelectedRecipe().getSteps().getId;
        String blah="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";
        MediaSource mediaSource = new ExtractorMediaSource
                .Factory(mMediaDataSourceFactory)
                .createMediaSource(Uri.parse(blah));

        boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
        if(haveStartPosition){
            mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);
        }

        mExoPlayer.prepare(mediaSource, !haveStartPosition, false);

        return binding.getRoot();
    }

    @Override
    public void onPause() {
        super.onPause();

        if(Util.SDK_INT <= 23){
            releasePlayer();
        } else {
            releasePlayer();
        }
    }

    /**
     * Create a new instance of SimpleExoPlayer.
     *  Convenient, default, all-purpose implementation of the ExoPlayer interface, which is the
     *  public interface of the actual player in the ExoPlayer library.
     *
     * Comment Source:
     *  https://codelabs.developers.google.com/codelabs/exoplayer-intro/index.html?index=..%2F..%2Findex#2
     */
    private void initializePlayer(){

        // Pass default components to the factory method newSimpleInstance and assign the returned
        // instance to the member field mExoPlayer
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());

        // Setup resumption
        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

        // Create a MediaSource 2
//        String blah = "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd9a6_2-mix-sugar-crackers-creampie/2-mix-sugar-crackers-creampie.mp4";
//        DefaultExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(blah), mMediaDataSourceFactory,)

        // Create a MediaSource 1
//        Uri uri = Uri.parse(mSharedViewModel.getSelectedRecipe().getSteps().get(1).getVideoURL());
//        MediaSource mediaSource = buildMediaSource(uri);
//        mExoPlayer.prepare(mediaSource, true, false);
    }

    private void releasePlayer(){
        if(mExoPlayer != null){
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mShouldAutoPlay = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
            mTrackSelector = null;
        }
    }
}
