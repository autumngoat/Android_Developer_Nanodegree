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
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.model.Recipe;
import com.example.full_dream.baking.model.Step;

/**
 * Share data between two Fragments using a ViewModel object using their common Activity
 * (MainActivity) to handle this communication.
 *
 * Source:
 * https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
 */
public class SharedViewModel extends ViewModel {
    // LiveData which publicly exposes setValue(Recipe) and postValue(Recipe) methods, if available
    private final MutableLiveData<Recipe> selectedRecipe = new MutableLiveData<>();
    // LiveData which publicly exposes setValue(Step) and postValue(Step) methods, if available
    private final MutableLiveData<Step> selectedStep = new MutableLiveData<>();
    // LiveData which publicly exposes setValue(Integer) and postValue(Integer), if available
    private final MutableLiveData<Integer> selectedStepId = new MutableLiveData<>();

    // Sets the value to the selected Recipe
    public void setSelectedRecipe(Recipe recipe){ selectedRecipe.setValue(recipe); }

    // Returns the selected Recipe
    public Recipe getSelectedRecipe(){
        return selectedRecipe.getValue();
    }

    // Sets the value to the selected Step and it's ID
    public void setSelectedStep(Step step){
        selectedStep.setValue(step);
        // Initialize 'selectedStepId' to avoid NPE
        setSelectedStepId();
    }

    // Returns the selected Step
    public Step getSelectedStep(){
        return selectedStep.getValue();
    }

    // Sets the value of the selected Step's ID
    private void setSelectedStepId(){ selectedStepId.setValue(selectedStep.getValue().getId()); }

    // Returns the selected Step's ID
    private Integer getSelectedStepId(){ return selectedStepId.getValue(); }

    // Increment the value of the Step's ID by 1 and set the selected Step to the next Step
    public void incrementStepId(){
        selectedStepId.setValue(getSelectedStepId() + 1);
        setSelectedStep(selectedRecipe.getValue().getSteps().get(selectedStepId.getValue()));
    }

    // Decrement the value of the Step's ID by 1 and set the selected Step to the previous Step
    public void decrementStepId(){
        selectedStepId.setValue(getSelectedStepId() - 1);
        setSelectedStep(selectedRecipe.getValue().getSteps().get(selectedStepId.getValue()));
    }

    /**
     * Used in StepDetailFragment to observe changes to the Step's ID so as to change the UI in
     * response.
     *
     * @return A LiveData object of the Step's ID as an Integer.
     */
    public LiveData<Integer> getStepId(){
        return selectedStepId;
    }
}