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

package com.example.full_dream.popularmoviesstage2.adapter;

// Android Imports
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// 3rd Paty Imports - Butterknife
import butterknife.BindView;
import butterknife.ButterKnife;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage2.R;
import com.example.full_dream.popularmoviesstage2.model.Review;

// Java Imports
import java.util.List;

/**
 * Creates and binds the Review ViewHolders for the Review RecyclerView
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    // Cached local copy of list of Review objects
    private List<Review> mReviewList;

    /**
     * Updates the adapter's cached copy of the list of Review objects.
     *  Note to self: If you update the adapter's data, then you need to notifyDataSetChanged() to
     *  see it!
     *
     * @param reviewList New list of Review objects to update the older cached data.
     */
    public void setReviewList(List<Review> reviewList){
        mReviewList = reviewList;
        notifyDataSetChanged();
    }

    /**
     * Fills/refills the ViewHolders by binding the data to the UI components.
     */
    @Override
    public void onBindViewHolder(@NonNull ReviewAdapterViewHolder holder, int position) {
        Review review = mReviewList.get(position);

        String author = review.getAuthor();
        String content = review.getContent();

        holder.mAuthor.setText(author);
        holder.mContent.setText(content);
    }

    /**
     * Total number of ViewHolders by returning the list size.
     */
    @Override
    public int getItemCount() {
        if(null == mReviewList) return 0;
        return mReviewList.size();
    }

    /**
     * Creates ViewHolders by inflating the review_list_item view.
     */
    @NonNull
    @Override
    public ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem,
                parent,
                context.getResources().getBoolean(R.bool.shouldAttachToParentImmediately));

        return new ReviewAdapterViewHolder(view);
    }

    /**
     * ViewHolder inner class.
     */
    public class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_author)
        TextView mAuthor;
        @BindView(R.id.review_content)
        TextView mContent;

        /**
         * ViewHolder constructor for new empty ViewHolders
         */
        ReviewAdapterViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
