package com.example.full_dream.popularmoviesstage1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Creates and binds the Trailer ViewHolders for the Trailer RecyclerView
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerAdapterViewHolder> {

    private List<Trailer> mTrailerList;
    // Alternatively could have used 'i3.ytimg.com'
    private static final String YOUTUBE_THUMBNAIL_PATH_START = "https://img.youtube.com/vi/";
    // High-quality Default image
    private static final String YOUTUBE_THUMBNAIL_PATH_END = "/hqdefault.jpg";

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
    }
}
