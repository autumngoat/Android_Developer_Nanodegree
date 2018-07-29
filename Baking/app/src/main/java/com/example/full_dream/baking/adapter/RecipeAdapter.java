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
import android.util.Log;
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
    private List<Recipe> mRecipeData;

    // Empty Constructor
    public RecipeAdapter(){}

    /**
     * Creates ViewHolders by inflating the recipe_list_item view.
     *
     * @param parent
     * @param viewType
     *
     * @return
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.e("rabbit", "RecipeAdapter onCreateViewHolder");

        // Local variables
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        //
        RecipeListItemBinding binding = DataBindingUtil.inflate(
                inflater,
                layoutIdForListItem,
                parent,
                false);

        return new RecipeViewHolder(binding);
    }

    /**
     * Fills/refills the ViewHolders by binding the data to the UI components.
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = mRecipeData.get(position);

        Log.e("rabbit", "RecipeAdapter onBindViewHolder recipe name: " + recipe.getName());

        // TODO: Set up Recipe CardView
        holder.bind(recipe);
    }

    /**
     * Total number of ViewHolders by returning the list size.
     */
    @Override
    public int getItemCount() {
        // Condition ? if : else
        return (mRecipeData != null) ? mRecipeData.size() : 0;
    }

    /**
     * Updates the adapter's cached copy of the list of Recipe objects.
     *
     * @param recipeData New list of Recipe objects to update the older cached data.
     */
    public void setRecipeData(List<Recipe> recipeData) {
        mRecipeData = recipeData;
        notifyDataSetChanged();
    }
}
