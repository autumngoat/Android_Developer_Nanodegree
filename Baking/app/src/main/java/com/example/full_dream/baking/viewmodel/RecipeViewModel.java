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

package com.example.full_dream.baking.viewmodel;

// Android Imports
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.data.RecipeRepository;
import com.example.full_dream.baking.model.Recipe;

// Java Imports
import java.util.List;

/**
 * This ViewModel is used to cache the list of Recipe objects wrapped in a LiveData object and
 * modify data passed into it.
 *  Acts as a bridge between the RecipeRepository and the View (RecipeFragment).
 *   This is where the business logic lives, much like a Presenter in MVP or Controller in MVC.
 *  Was also true for P3 but I am still learning what is or isn't MVVM.
 *
 * Comment Source:
 *  https://android.jlelse.eu/android-architecture-components-now-with-100-more-mvvm-11629a630125
 */
public class RecipeViewModel extends ViewModel {

    // Hold a reference to the repository
    private RecipeRepository mRecipeRepository;

    /**
     * Constructor to instantiate repository reference.
     */
    public RecipeViewModel() {
        this.mRecipeRepository = new RecipeRepository();
    }

    /**
     * RecipeRepository getter method for retrieving a LiveData object of a list of Recipe objects
     * that hides the implementation from the UI.
     *
     * @return A LiveData object of a list of Recipe objects.
     */
    public LiveData<List<Recipe>> getRecipes() {
        return mRecipeRepository.getRecipes();
    }


}
