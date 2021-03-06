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

package com.example.full_dream.baking.fragment;

// Android Imports
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.adapter.RecipeAdapter;
import com.example.full_dream.baking.databinding.FragmentRecipeBinding;
import com.example.full_dream.baking.model.Recipe;
import com.example.full_dream.baking.viewmodel.RecipeViewModel;
import com.example.full_dream.baking.viewmodel.SharedViewModel;

// Java Imports
import java.util.List;

/**
 * Sets up the UI as a list of Recipe objects as a CardView, which contains the Recipe's name,
 * serving size, and image, that once clicked launches RecipeDetailFragment.
 */
public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeAdapterOnClickHandler{

    // ViewModel(s)
    private RecipeViewModel mRecipeViewModel;
    private SharedViewModel mSharedViewModel;

    // Adapter(s)
    private RecipeAdapter mRecipeAdapter;

    /**
     * Called to do the initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use ViewModelProviders to associate an instance of SharedViewModel scoped with the
        // lifecycle of the UIController MainActivity
        mSharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // Use ViewModelProviders to associate an instance of RecipeViewModel scoped with the
        // lifecycle of the UIController RecipeFragment
        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
    }

    /**
     * Creates and returns the view hierarchy associated with the fragment.
     *
     * @param inflater The Layoutinflater object that can be used to inflate any views
     *                 in the fragment.
     * @param container If not null, this is the parent view that the fragment's UI
     *                  should be attached to.
     * @param savedInstanceState If not null, this fragment is being re-constructed from
     *                           a previous saved state as given here.
     * @return Return the view for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //
        // Source:
        //  https://stackoverflow.com/questions/34706399/how-to-use-data-binding-with-fragment
        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe,
                container,
                false);

//        binding.toolbarRecipe.setTitle("Baking Time");

        // Creates a vertical LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        // Set the LayoutManager that recyclerviewRecipe will use
        binding.recyclerviewRecipe.setLayoutManager(linearLayoutManager);
        // Optimization for RecyclerViews who's contents are not likely to change
        binding.recyclerviewRecipe.setHasFixedSize(true);
        // Instantiate the adapter
        mRecipeAdapter = new RecipeAdapter(this);
        // Set a new adapter to provide child views on demand
        binding.recyclerviewRecipe.setAdapter(mRecipeAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
//                Timber.e("onChanged");
                mRecipeAdapter.setRecipeData(recipes);
            }
        });
    }

    /**
     * Handle RecipeAdapter clicks events by replacing the current RecipeFragment with a
     * RecipeDetailFragment.
     *  Decided to implement RecipeAdapter.RecipeAdapterOnClickHandler interface in RecipeFragment
     *  instead of implementing the interface in a separate class because:
     *   1) There is only 1 type of task to execute when this listener is called:
     *    Replacing the current RecipeFragment with RecipeDetailFragment.
     *   2) The event listener does NOT need to check the source of the click event in order to
     *   complete it's action since there is only one class implementing it.
     *
     * Comment Source:
     *  https://softwareengineering.stackexchange.com/questions/110106/what-is-the-proper-way-to-implement-the-onclicklistener-interface-for-many-butto
     *
     * @param recipe The Recipe object associated with the clicked upon RecipeAdapter item.
     */
    public void onClickShowRecipeDetail(Recipe recipe){

        // Set Recipe object in SharedViewModel to transfer that data from RecipeFragment to
        // RecipeDetailFragment (instead of Parcelable or other methods)
        mSharedViewModel.setSelectedRecipe(recipe);

        // Entering fragment from exiting fragment
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();

        getFragmentManager()
                .beginTransaction()
                .addToBackStack("recipeDetail")
                .replace(R.id.fragment_container, recipeDetailFragment)
                .commit();
    }
}
