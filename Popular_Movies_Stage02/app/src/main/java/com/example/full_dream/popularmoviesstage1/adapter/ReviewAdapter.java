package com.example.full_dream.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.model.Review;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creates and binds the Review ViewHolders for the Review RecyclerView
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewAdapterViewHolder>{

    private List<Review> mReviewList;

    public void setReviewList(List<Review> reviewList){
//        mReviewList = null;
        mReviewList = reviewList;
//        notifyDataSetChanged();
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
