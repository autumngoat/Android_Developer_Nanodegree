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

// Android Imports
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

// 3rd Party Imports - Butterknife
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.model.Trailer;

// 3rd Party Imports - com - Picasso
import com.squareup.picasso.Picasso;

// Java Imports
import java.util.List;

/**
 * Creates and binds the Trailer ViewHolders for the Trailer RecyclerView.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    // Constants
    private static final String YOUTUBE_THUMBNAIL_PATH_START = "https://img.youtube.com/vi/";   // Alternatively could have used 'i3.ytimg.com'
    private static final String YOUTUBE_THUMBNAIL_PATH_END = "/hqdefault.jpg";                  // High-quality Default image
    // Cached copy of list of Trailer objects
    private List<Trailer> mTrailerList;
    private final TrailerAdapterOnClickHandler mClickHandler;

    /**
     * Creates a PosterAdapter.
     */
    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(String youtubeKey);
    }

    /**
     * Updates the adapter's cached copy of the list of Trailer objects.
     *
     * @param trailers New list of Trailer objects to update the older cached data.
     */
    public void setTrailerList(List<Trailer> trailers){
        mTrailerList = trailers;
    }

    /**
     * Creates and returns new ViewHolders by inflating the trailer_list_item view.
     */
    @NonNull
    @Override
    public TrailerAdapter.TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate() converts a XML layout file into corresponding ViewGroups and Widgets
        //  Source: https://www.bignerdranch.com/blog/understanding-androids-layoutinflater-inflate/
        View view = inflater.inflate(
                // int resource
                layoutIdForListItem,
                // ViewGroup root
                parent,
                // boolean attachToRoot
                //  If TRUE, then specified layout file is inflated and attached to ViewGroup root
                //  If FALSE, then specified layout file is inflated and returned as a View
                context.getResources().getBoolean(R.bool.shouldAttachToParentImmediately)
        );

        return new TrailerAdapterViewHolder(view);
    }

    /**
     * Fills/refills the ViewHolders by binding the data to the UI components.
     */
    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.TrailerAdapterViewHolder holder, int position) {
        Trailer trailer = mTrailerList.get(position);

        String videoTitle = trailer.getName();
        String videoKey = trailer.getKey();

        holder.mTrailerName.setText(videoTitle);

        Picasso.get()
                .load(YOUTUBE_THUMBNAIL_PATH_START + videoKey + YOUTUBE_THUMBNAIL_PATH_END)
                .placeholder(R.drawable.ic_popcorn)
                .error(R.drawable.ic_popcorn)
                .into(holder.mTrailerThumbnail);
    }

    /**
     * Total number of ViewHolders by returning the list size.
     */
    @Override
    public int getItemCount() {
        if(null == mTrailerList) return 0;
        return mTrailerList.size();
    }

    /**
     * ViewHolder inner class.
     */
    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.video_name)
        TextView mTrailerName;
        @BindView(R.id.video_thumbnail)
        ImageView mTrailerThumbnail;

        /**
         * ViewHolder constructor for new empty ViewHolders
         */
        TrailerAdapterViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

        /**
         * Sends selected View's associated Movie object to click-handler.
         */
        @OnClick(R.id.video_thumbnail)
        public void onClickPlayVideo() {
            int adapterPosition = getAdapterPosition();
            String youtubeKey = mTrailerList.get(adapterPosition).getKey();
            mClickHandler.onClick(youtubeKey);
        }
    }
}
