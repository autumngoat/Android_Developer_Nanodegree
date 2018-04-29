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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Provides ViewHolders to display inside RecyclerView
 */
public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {
    private ArrayList<Movie> mMovieData;

    // I adapted much of the Adapter code from my code for S05.01-Exercise-AsyncTaskLoader
    private final PosterAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface PosterAdapterOnClickHandler {
        void onClick(String[] movieData);
    }

    /**
     * Creates a PosterAdapter.
     */
    public PosterAdapter(PosterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Creates ViewHolders by inflating the movie_list_item view
     */
    @NonNull
    @Override
    public PosterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new PosterAdapterViewHolder(view);
    }

    /**
     * Fills/refills the ViewHolders by binding the data to the UI components
     */
    @Override
    public void onBindViewHolder(@NonNull PosterAdapterViewHolder posterViewHolder, int position) {
        Movie movie = mMovieData.get(position);
        String path = NetworkUtils.buildImgPath(movie.getPoster());
        Picasso.get()
                .load(path)
                .into(posterViewHolder.mPosterImageView);
        posterViewHolder.mTitleTextView.setText(movie.getTitle());

    }

    /**
     * Total number of ViewHolders by returning the list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if(null == mMovieData) return 0;
        return mMovieData.size();
    }

    public void setMovieData(ArrayList<Movie> movieData){
        mMovieData = movieData;
        for(Movie movie : movieData){
            Log.e("Please Not Null", movie.getTitle());
        }
        notifyDataSetChanged();
    }

    /**
     * ViewHolder constructor
     */
    public class PosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitleTextView;
        ImageView mPosterImageView;

        /**
         * Sets click listener on newly created empty ViewHolder
         */
        public PosterAdapterViewHolder(View view){
            super(view);
            mTitleTextView = (TextView)view.findViewById(R.id.tv_title);
            mPosterImageView = (ImageView)view.findViewById(R.id.iv_poster);
            view.setOnClickListener(this);
        }

        /**
         * Sends selected View's associated Movie object to click-handler.
         *
         * @param view The View that was clicked
         */
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = mMovieData.get(adapterPosition);
            String[] movieData = {selectedMovie.getTitle(), selectedMovie.getReleaseDate(),
                    Double.toString(selectedMovie.getVoteAvg()), selectedMovie.getPlotSynopsis()};
            mClickHandler.onClick(movieData);
        }
    }
}
