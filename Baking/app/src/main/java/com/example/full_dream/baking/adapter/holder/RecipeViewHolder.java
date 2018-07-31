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

package com.example.full_dream.baking.adapter.holder;

// Android Imports
import android.support.v7.widget.RecyclerView;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.R;
import com.example.full_dream.baking.databinding.RecipeListItemBinding;
import com.example.full_dream.baking.model.Recipe;

// 3rd Party Imports - Picasso
import com.squareup.picasso.Picasso;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    //
    RecipeListItemBinding mBinding;

    /**
     * The ViewHolder that will be used to display the data in each item shown in the RecyclerView.
     *
     * @param binding The layout ViewGroup used to display the data.
     */
    public RecipeViewHolder(final RecipeListItemBinding binding){
        super(binding.getRoot());

        // Initialize View here
        this.mBinding = binding;

        // Works
//        // Set click listener
//        //  Source: https://stackoverflow.com/questions/46874441/recyclerview-item-click-listener-with-databinding
//        binding.getRoot().findViewById(R.id.iv_recipe_image).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mBinding.getRoot().getContext(),
//                        mBinding.getRecipe().getName(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * The method that is used to bind the data to the ViewHolder.
     *
     * @param recipe
     */
    public void bind(Recipe recipe){

        //
        mBinding.setRecipe(recipe);

        // Icons made by "https://www.flaticon.com/authors/freepik"
        // Title: "Cookie"
        // Site: https://www.flaticon.com/free-icon/cookie_1047711
        // Licensed by Creative Commons BY 3.0
        Picasso.get().load(recipe.getImage())
                .placeholder(R.drawable.ic_cookie)
                .error(R.drawable.ic_cookie)
                .into(mBinding.ivRecipeImage);

//        // Does not work, causes causes onClickShowRecipeDetail fire as ViewHolders show instead of
//        // onClick
//        clickHandler.onClickShowRecipeDetail(recipe);
    }
}
