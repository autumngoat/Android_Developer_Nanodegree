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

package com.example.full_dream.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.model.Movie;

import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.List;

/**
 * Provides ViewHolders to display inside RecyclerView
 */
public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {
    // I adapted much of the Adapter code from my code for S05.01-Exercise-AsyncTaskLoader
    private final PosterAdapterOnClickHandler mClickHandler;
    private List<Movie> mMovieData;

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

        View view = inflater.inflate(layoutIdForListItem,
                parent,
                context.getResources().getBoolean(R.bool.shouldAttachToParentImmediately));
        return new PosterAdapterViewHolder(view);
    }

    /**
     * Fills/refills the ViewHolders by binding the data to the UI components
     */
    @Override
    public void onBindViewHolder(@NonNull PosterAdapterViewHolder posterViewHolder, int position) {
        Movie movie = mMovieData.get(position);

        // Icons made by "https://www.flaticon.com/authors/freprikepik"
        // Title: "Popcorn"
        // Licensed by Creative Commons BY 3.0
        Picasso.get()
                .load(movie.getPosterPath())
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(posterViewHolder.mPosterImageView);
    }

    /**
     * Total number of ViewHolders by returning the list size
     *
     * @return
     */
    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieData = null;
        mMovieData = movieData;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface PosterAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * ViewHolder inner class.
     */
    public class PosterAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_poster)
        ImageView mPosterImageView;

        /**
         * ViewHolder constructor that sets click listener on newly created empty ViewHolder
         */
        public PosterAdapterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        /**
         * Sends selected View's associated Movie object to click-handler.
         */
        @OnClick(R.id.iv_poster)
        public void onClickShowDetails() {
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = mMovieData.get(adapterPosition);
            mClickHandler.onClick(selectedMovie);
        }
    }
}
