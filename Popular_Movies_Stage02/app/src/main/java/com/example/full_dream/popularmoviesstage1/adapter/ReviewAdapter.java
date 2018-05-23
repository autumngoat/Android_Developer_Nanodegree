package com.example.full_dream.popularmoviesstage1.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public class ReviewAdapter extends RecyclerView.Adapter{

    /**
     * Constructor.
     */
    public ReviewAdapter(){ }

    /**
     * Fills/refills the ViewHolders by binding the data to the UI components.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    /**
     * Total number of ViewHolders by returning the list size.
     */
    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Creates ViewHolders by inflating the review_list_item view.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }
}
