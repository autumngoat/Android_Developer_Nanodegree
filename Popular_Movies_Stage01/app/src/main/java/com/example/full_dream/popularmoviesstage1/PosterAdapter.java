package com.example.full_dream.popularmoviesstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.full_dream.popularmoviesstage1.model.Movie;

import java.util.ArrayList;

/**
 * Provides ViewHolders to display inside RecyclerView
 */
public class PosterAdapter extends RecyclerView.Adapter<PosterAdapter.PosterAdapterViewHolder> {
    private ArrayList<Movie> mMovieData;
    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final PosterAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface PosterAdapterOnClickHandler {
        void onClick(String weatherForDay);
    }

    /**
     * Creates a PosterAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public PosterAdapter(PosterAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Creates ViewHolders
     *
     * @param parent
     * @param viewType
     * @return
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
     * Fills/refills the ViewHolders
     *
     * @param posterViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull PosterAdapterViewHolder posterViewHolder, int position) {
        Movie movie = mMovieData.get(position);
//        Uri uri = NetworkUtils.buildImgUri(movie.getPoster());
//        Glide.with(mContext).load(uri).into(posterViewHolder.mPosterImageView);
        posterViewHolder.mTitleTextView.setText(movie.getTitle());
    }

    /**
     * Total number of ViewHolders
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
        notifyDataSetChanged();
    }

    /**
     * ViewHolder constructor
     */
    public class PosterAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitleTextView;
//        ImageView mPosterImageView;

        public PosterAdapterViewHolder(View view){
            super(view);
            mTitleTextView = (TextView)view.findViewById(R.id.tv_title);
//            mPosterImageView = (ImageView)view.findViewById(R.id.iv_poster);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieData.get(adapterPosition);
            mClickHandler.onClick(movie.toString());
        }
    }
}
