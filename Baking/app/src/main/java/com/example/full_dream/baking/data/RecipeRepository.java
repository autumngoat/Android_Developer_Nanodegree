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

package com.example.full_dream.baking.data;

// Android Imports
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.model.Ingredient;
import com.example.full_dream.baking.model.Recipe;
import com.example.full_dream.baking.model.Step;
import com.example.full_dream.baking.network.BakingService;
import com.example.full_dream.baking.network.RetrofitClient;

// 3rd Party Imports - Retrofit2
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Java Imports
import java.io.IOException;
import java.util.List;

/**
 * Data layer responsible for fetching data from local or remote data source and sending it to the
 * ViewModel.
 *  Abstracts the data sources from the rest of the app.
 *   Removes network and database calls from ViewModel, Fragment, etc.
 *  Manages query threads and allows use of multiple back-ends.
 */
public class RecipeRepository {

    // Data source(s)
    private BakingService mBakingService;       // Remote Network access

    // Cache data
    //  MutableLiveData is a mutable and thread-safe version of LiveData with public setValue()
    //  and postValue() methods
    //   To be clear, LiveData is immutable and does not have public setValue() and postValue()
    //   methods
    private MutableLiveData<List<Recipe>> mRecipes;

    /**
     * Constructor that calls getApiClient() to get access to the android-baking-app-json interface,
     * and create new LiveData objects of each list of Movie objects.
     */
    public RecipeRepository(){
        // Get remote network access
        mBakingService = RetrofitClient.getApiClient();

        // Instantiate each MutableLiveData object
        mRecipes = new MutableLiveData<>();
    }

    /**
     * Create a Call using an implementation of the API interface/endpoint to asynchronously send a
     * HTTP request for a list of Recipe(s) and process the resulting HTTP response.
     *
     * Note to self:
     *  Difference between P3 (origin of much of this boilerplate code) and P4 (this project):
     *   P3 was querying an API that resulted in a MovieResponse JSONObject with a JSONArray of
     *   Movie objects:
     *      ...onResponse(..., Response<MovieResponse> response){
     *          if(response.isSuccessful()){
     *              List<Movie> movies = response.body().getResults();
     *              mMovies.setValue(movies);
     *          }
     *      }
     *   P4 queries a web-page with a list of 4 Recipe JSONObjects
     *      ...onResponse(..., Response<List<Recipe>> response){
     *          if(response.isSuccessful()){
     *              mRecipes.setValue(response.body());
     *          }
     *      }
     *
     * @return LiveData of a list of Recipe objects.
     */
    public LiveData<List<Recipe>> getRecipes(){
        // A Call represents the HTTP request with a generic parameter representing the response
        // body type which will be converted by one of the Converter.Factory instances
        //  Each call yields its own HTTP request and response pair
        Call<List<Recipe>> call = mBakingService.getRecipes();

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<List<Recipe>>() {

            // Invoked for a received HTTP response
            //  HTTP response may still indicate an application-level failure  such as a 404 or 500
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                // Returns true if in the range [200..300)
                if(response.isSuccessful()){
                    // Set the value and dispatch the value to all the active observers
                    mRecipes.setValue(response.body());

                    for(Recipe recipe : response.body()){
                        Log.e("rabbit","Recipe ID: " + recipe.getId());
                        for(Ingredient ingredient : recipe.getIngredients()){
                            Log.e("rabbit","    " + "Get Ingredients: " + ingredient.getIngredient());
                        }
                        for(Step step : recipe.getSteps()){
                            Log.e("rabbit","    " + "Step ID: " + step.getId());
                        }
                    }
                } else {
//                    Timber.e("RecipeRepository getRecipes Response ERROR");
                }
            }

            // Invoked when a network exception occurred talking to the server or when an unexpected
            // exception occurred creating the request or processing the response
            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                //
                if(t instanceof IOException){
                    //TODO Toast (maybe?)
//                    Timber.e("Actual Network failure: Inform user and possibly retry");
                } else {
//                    Timber.e("Conversion error");
                }
            }
        });

        return mRecipes;
    }
}
