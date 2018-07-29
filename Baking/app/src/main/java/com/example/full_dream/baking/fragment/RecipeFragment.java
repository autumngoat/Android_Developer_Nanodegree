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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.adapter.RecipeAdapter;
import com.example.full_dream.baking.databinding.FragmentRecipeBinding;
import com.example.full_dream.baking.model.Recipe;
import com.example.full_dream.baking.viewmodel.RecipeViewModel;

import java.util.List;

public class RecipeFragment extends Fragment {

    // ViewModel(s)
    private RecipeViewModel mRecipeViewModel;

    // Adapter(s)
    private RecipeAdapter mRecipeAdapter;

//    /**
//     * Mandatory empty constructor for the Fragment Manager to instantiate the fragment.
//     */
//    public  RecipeFragment(){}

    /**
     * Called to do the initial creation of the fragment.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous
     *                           saved state, this is the state.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // asdf
        // Source:
        //  https://stackoverflow.com/questions/34706399/how-to-use-data-binding-with-fragment
        FragmentRecipeBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipe,
                container,
                false);

        // Creates a vertical LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        // Set the LayoutManager that recyclerviewRecipe will use
        binding.recyclerviewRecipe.setLayoutManager(linearLayoutManager);
        // Optimization for RecyclerViews who's contents are not likely to change
        binding.recyclerviewRecipe.setHasFixedSize(true);
        // Instantiate the adapter
        mRecipeAdapter = new RecipeAdapter();
        // Set a new adapter to provide child views on demand
        binding.recyclerviewRecipe.setAdapter(mRecipeAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("rabbit", "onActivityCreated");

        mRecipeViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.e("rabbit", "onChanged");
                mRecipeAdapter.setRecipeData(recipes);
            }
        });
    }
}
