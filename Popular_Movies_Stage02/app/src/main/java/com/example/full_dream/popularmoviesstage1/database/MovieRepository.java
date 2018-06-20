/*
 *
 *  * PROJECT LICENSE
 *  *
 *  * This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *  *
 *  * As part of Udacity Honor code:
 *  *   - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *  *     - Acceptable sources consist of:
 *  *         - Unmodified or modified code from the Udacity courses
 *  *         - A modest amount of unmodified or modified code from third-party sources with attribution
 *  *     - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *  *   - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *  *   may result in the cancellation of my enrollment without refund.
 *  *   - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *  *   conferred.
 *  *
 *  * I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 *  * Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *  *
 *  * Copyright (c) 2018 Seong Wang
 *  *
 *  * Besides the above notice, the following license applies and this license notice must be included in all works derived from
 *  * this project.
 *  *
 *  * MIT License
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package com.example.full_dream.popularmoviesstage1.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.full_dream.popularmoviesstage1.BuildConfig;
import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.network.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.network.TheMovieDBService;
import com.example.full_dream.popularmoviesstage1.thread.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Data layer responsible for fetching data from local or remote data source and sending it to the
 * ViewModel.
 *  Abstracts the data sources from the rest of the app.
 *   Removes network and database calls from ViewModel, Fragment, etc.
 *  Manages query threads and allows use of multiple back-ends.
 */
public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    private static final int MOST_POPULAR = 0;
    private static final int TOP_RATED = 1;
    private static final int FAVORITES = 2;
    private String API_KEY = BuildConfig.API_KEY;
//    private LiveData<List<Movie>> mMovieList;

    // For Singleton instantiation
    private static final Object LOCK = new Object();    // Synch lock
    private static MovieRepository sInstance;           // Singleton instance
    private MovieDao mMovieDao;                         // Local Database access
    private TheMovieDBService tmdbService;              // Remote Network access

    // Cache data
    //  MutableLiveData is a mutable and thread-safe version of LiveData with public setValue()
    //  and postValue() methods
    //   To be clear, LiveData is immutable and does not have public setValue() and postValue()
    //   methods
    private MutableLiveData<List<Movie>> popularMovies;
    private MutableLiveData<List<Movie>> toprateMovies;
    private LiveData<List<Movie>> favoriteMovies;
//    private MutableLiveData<Integer> settingOption;

    public MovieRepository(Application application){
        // Get local database access
        AppDatabase database = AppDatabase.getInstance(application);
        mMovieDao = database.movieDao();
        // Get remote network access
        tmdbService = RetrofitClient.getApiClient();
    }

    public LiveData<List<Movie>> getMovieList(int settingOption){
        // MutableLiveData is a mutable and thread-safe version of LiveData with public setValue()
        // and postValue() methods
        //  To be clear, LiveData is immutable and does not have public setValue() and postValue()
        //  methods
//        final MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

        // Popular or Top Rated or Favorites?
        switch(settingOption){
            case MOST_POPULAR:
                // Null check, if null, create new instance and fill it
                if(popularMovies == null){
                    Log.e(TAG, "REPOSITORY popularMovies == null");
                    popularMovies = new MutableLiveData<>();
                }
                getPopularMoviesList();
                Log.e(TAG, "REPOSITORY popularMovies.getValue()");
                return popularMovies;
            case TOP_RATED:
                // Null check, if null, create new instance and fill it
                if(toprateMovies == null){
                    Log.e(TAG, "REPOSITORY toprateMovies == null");
                    toprateMovies = new MutableLiveData<>();
                }
                getTopMoviesList();
                Log.e(TAG, "REPOSITORY toprateMovies.getValue()");
                return toprateMovies;
            case FAVORITES:
                // Null check, if null, create new instance and fill it
                if(favoriteMovies == null){
                    Log.e(TAG, "REPOSITORY favoriteMovies == null");
                    favoriteMovies = new MutableLiveData<>();
                }
                // Set the value and dispatch the value to all the active observers
                //  setValue vs postValue
                //   setValue MUST be done on the main thread
                //   postValue is done on a background thread
//                movieList.postValue(mMovieDao.loadAllMovies().getValue());
//                Log.e(TAG, "MutableLiveDta: " + movieList.toString());
//                return movieList;
                return favoriteMovies = mMovieDao.loadAllMovies();
            default:
                throw new UnknownError("This is not a CTF, go somewhere else for decompiling fun.");
        }
    }

    /**
     * Create a Call using an implementation of the API interface (TheMovieDBService) to
     * asynchronously send a HTTP request for Popular Movies and process the resulting HTTP response.
     *
     * Comment Source:
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Callback.html
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Response.html#isSuccessful--
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.html#create-java.lang.Class-
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
     */
    public void getPopularMoviesList(){
        // A Call represents the HTTP request with a generic parameter representing the response
        // body type which will be converted by one of the Converter.Factory instances
        //  Each call yields its own HTTP request and response pair
        Call<MovieResponse> call = tmdbService.getPopularMovies(API_KEY);

//        Log.e(TAG, "REPOSITORY apiKey " + API_KEY);

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<MovieResponse>() {

            // Invoked for a received HTTP response
            //  HTTP response may still indicate an application-level failure  such as a 404 or 500
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                // Returns true if in the range [200..300)
                if(response.isSuccessful()){
                    List<Movie> movies = response.body().getResults();
//                    Log.e(TAG, "REPOSITORY response.body(): " + response.body().toString());
//                    Log.e(TAG, "REPOSITORY response.body().getResults: " + response.body().getResults().toString());
                    // Set the value and dispatch the value to all the active observers
//                        movieList.setValue(movies);
                    popularMovies.setValue(movies);
                } else {
                    Log.e(TAG, "REPOSITORY response NOT successful");
                }
            }

            // Invoked when a network exception occurred talking to the server or when an unexpected
            // exception occurred creating the request or processing the response
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                popularMovies = null;
                Log.e(TAG, "REPOSITORY call.enqueue FAILED");
            }
        });
    }

    /**
     * Create a Call using an implementation of the API interface (TheMovieDBService) to
     * asynchronously send a HTTP request for Top Rated Movies and process the resulting HTTP response.
     *
     * Comment Source:
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Callback.html
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Response.html#isSuccessful--
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.html#create-java.lang.Class-
     * https://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
     */
    public void getTopMoviesList(){
        // A Call represents the HTTP request with a generic parameter representing the response
        // body type which will be converted by one of the Converter.Factory instances
        //  Each call yields its own HTTP request and response pair
        Call<MovieResponse> call = tmdbService.getTopRatedMovies(API_KEY);

        // Asynchronously send the HTTP request and notify the callback of its HTTP response
        // or if an error occurred talking to the server, creating the HTTP request
        call.enqueue(new Callback<MovieResponse>() {

            // Invoked for a received HTTP response
            //  HTTP response may still indicate an application-level failure  such as a 404 or 500
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                // Returns true if in the range [200..300)
                if(response.isSuccessful()){
                    List<Movie> movies = response.body().getResults();
                    toprateMovies.setValue(movies);
                } else {
                    Log.e(TAG, "REPOSITORY response NOT successful");
                }
            }

            // Invoked when a network exception occurred talking to the server or when an unexpected
            // exception occurred creating the request or processing the response
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                toprateMovies = null;
                Log.e(TAG, "REPOSITORY call.enqueue FAILED");
            }
        });
    }

    public void insertMovie(final Movie movie){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insertMovie(movie);
            }
        });
    }

    public void deleteMovie(final Movie movie){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.deleteMovie(movie);
            }
        });
    }
}
