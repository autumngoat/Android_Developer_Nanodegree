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

package com.example.full_dream.baking.adapter;

// Android Imports
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.adapter.holder.StepViewHolder;
import com.example.full_dream.baking.databinding.StepListItemBinding;
import com.example.full_dream.baking.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    // Cached cop of list of Step objects
    private List<Step> mStepList;
    // Instance of the StepAdapterOnClickHandler interface
    private StepAdapterOnClickHandler mStepAdapterOnClickHandler;

    /**
     * The interface that defines what the method signature of onClickShowStepDetail() in
     * RecipeDetailFragment.
     */
    public interface StepAdapterOnClickHandler{

        // onClick callback method
        void onClickShowStepDetail(Step step);
    }

    /**
     * StepAdapter constructor.
     *  Pass StepAdapterOnClickHandler listener to StepAdapter.
     *
     * @param clickHandler An object that implements the StepAdapterOnClickHandler interface.
     */
    public StepAdapter(StepAdapterOnClickHandler clickHandler){
        mStepAdapterOnClickHandler = clickHandler;
    }

    /**
     * Creates ViewHolders by inflating the step_list_item View.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     *               adapter position.
     * @param viewType The view type of the new View.
     *
     * @return New StepViewHolder.
     */
    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflate and create a new View
        StepListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.step_list_item,
                parent,
                false
        );

        // Binds the click event to the layout
        binding.setStepDetail(mStepAdapterOnClickHandler);

        return new StepViewHolder(binding);
    }

    /**
     * This is where the data is bound to each StepViewHolder.
     *  This method is called at least once and will be called each time the adapter is notified
     *  that the data set has changed.
     *
     * @param holder The StepViewHolder.
     * @param position The position in the collection of data.
     */
    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {

        // Get Step object at adapter position
        Step step = mStepList.get(position);
        // Bind Step object to StepViewHolder
        holder.bind(step);
    }

    /**
     * Gets the size of the collection of items in the list.
     *
     * @return An integer representing the size of the collection that will be displayed.
     */
    @Override
    public int getItemCount() {
        // Condition ? if : else
        return (mStepList != null) ? mStepList.size() : 0;
    }

    /**
     * Updates the adapter's cached copy of the list of Step objects.
     *
     * @param stepList New list of Recipe objects to update the older cached data.
     */
    public void setStepData(List<Step> stepList) {
        mStepList = stepList;
        notifyDataSetChanged();
    }
}
