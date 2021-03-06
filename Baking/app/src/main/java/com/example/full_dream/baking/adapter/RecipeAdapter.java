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
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.adapter.holder.RecipeViewHolder;
import com.example.full_dream.baking.databinding.RecipeListItemBinding;
import com.example.full_dream.baking.model.Recipe;

// Java Imports
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder> {

    // Cached copy of list of Recipe objects
    private List<Recipe> mRecipeList;
    // Instance of the RecipeAdapterOnClickHandler interface
    private RecipeAdapterOnClickHandler mRecipeAdapterOnClickHandler;

    /**
     * RecipeAdapter constructor.
     *  Pass RecipeAdapterOnClickHandler listener to RecipeAdapter.
     *
     * @param clickHandler An object that implements the RecipeAdapterOnClickHandler interface.
     */
    public RecipeAdapter(RecipeAdapterOnClickHandler clickHandler){
        mRecipeAdapterOnClickHandler = clickHandler;
    }

    /**
     * The interface that defines what the method signature of onClickShowRecipeDetail() in
     * RecipeFragment.
     */
    public interface RecipeAdapterOnClickHandler {

        // onClick callback method
        void onClickShowRecipeDetail(Recipe recipe);
    }

    /**
     * Creates ViewHolders by inflating the recipe_list_item View.
     *  Bound/set the click event/listener in onCreateViewHolder instead of onBindViewHolder because
     *  it is only done once (per ViewHolder) instead of every time a ViewHolder gets new data
     *  bound to it (recycled or initialized).
     *
     * Comment Source:
     *  https://stackoverflow.com/questions/33845846/why-is-adding-an-onclicklistener-inside-onbindviewholder-of-a-recyclerview-adapt
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     *               adapter position.
     * @param viewType The view type of the new View.
     *
     * @return New RecipeViewHolder.
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Local variables
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate and create a new View
        RecipeListItemBinding binding = DataBindingUtil.inflate(
                inflater,
                layoutIdForListItem,
                parent,
                false);

        // Binds the click event to the layout
        binding.setCallback(mRecipeAdapterOnClickHandler);

        // Set the View's layout parameters
        return new RecipeViewHolder(binding);
    }

    /**
     * This is where the data is bound to each RecipeViewHolder.
     *  This method is called at least once and will be called each time the adapter is notified
     *  that the data set has changed.
     *
     * @param holder The RecipeViewHolder.
     * @param position The position in the collection of data.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        // Get Recipe object from List<Recipe> at adapter position
        Recipe recipe = mRecipeList.get(position);
        // Bind Recipe object to RecipeViewHolder
        holder.bind(recipe);
    }

    /**
     * Gets the size of the collection of items in the list.
     *
     * @return An integer representing the size of the collection that will be displayed.
     */
    @Override
    public int getItemCount() {
        // Condition ? if : else
        return (mRecipeList != null) ? mRecipeList.size() : 0;
    }

    /**
     * Updates the adapter's cached copy of the list of Recipe objects.
     *
     * @param recipeList New list of Recipe objects to update the older cached data.
     */
    public void setRecipeData(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }
}
