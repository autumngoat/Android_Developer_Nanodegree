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

// Android Imports
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage1.BuildConfig;
import com.example.full_dream.popularmoviesstage1.R;
import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.network.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.network.TheMovieDBService;
import com.example.full_dream.popularmoviesstage1.thread.AppExecutors;

// 3rd Party Imports - Retrofit2
import butterknife.BindString;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Java Imports
import java.util.List;

/**
 * Data layer responsible for fetching data from local or remote data source and sending it to the
 * ViewModel.
 *  Abstracts the data sources from the rest of the app.
 *   Removes network and database calls from ViewModel, Fragment, etc.
 *  Manages query threads and allows use of multiple back-ends.
 */
public class MovieRepository {

    // Constants
    private static final String TAG = MovieRepository.class.getSimpleName();
    private static final int MOST_POPULAR = 0;
    private static final int TOP_RATED = 1;
    private static final int FAVORITES = 2;
    private static final String API_KEY = BuildConfig.API_KEY;

    // Data sources
    private MovieDao mMovieDao;                         // Local Database access
    private TheMovieDBService mTmdbService;             // Remote Network access

    // Cache data
    //  MutableLiveData is a mutable and thread-safe version of LiveData with public setValue()
    //  and postValue() methods
    //   To be clear, LiveData is immutable and does not have public setValue() and postValue()
    //   methods
    private MutableLiveData<List<Movie>> mPopularMovies;
    private MutableLiveData<List<Movie>> mToprateMovies;
    private LiveData<List<Movie>> mFavoriteMovies;

    // Error messages
    @BindString(R.string.response_not_successful)
    String responseNotSuccessful;
    @BindString(R.string.network_exception)
    String networkException;
    @BindString(R.string.impossible_error)
    String cheekyException;

    /**
     * Constructor that takes the application context to create/retrieve an instance of the local
     * SQL database, calls getApiClient() to get access to the TMDB API interface, and create new
     * LiveData objects of each list of Movie objects.
     *
     * @param application Application context used to gain access to the local SQL database instance.
     */
    public MovieRepository(Application application){
        // Get local database access
        AppDatabase database = AppDatabase.getInstance(application);
        mMovieDao = database.movieDao();

        // Get remote network access
        mTmdbService = RetrofitClient.getApiClient();

        // Create each MutableLiveData object
        mPopularMovies = new MutableLiveData<>();
        mToprateMovies = new MutableLiveData<>();
        mFavoriteMovies = new MutableLiveData<>();
    }

    /**
     * Retrieve the correct list of Movie objects based on an integer/choice.
     *
     * @param settingOption Integer values set in PosterListFragment that correspond to 'most popular',
     *                      'top rated', and 'favorites'.
     * @return LiveData of a list of Movie objects pertaining to the setting option.
     */
    public LiveData<List<Movie>> getMovieList(int settingOption){
        // Popular or Top Rated or Favorites?
        switch(settingOption){
            case MOST_POPULAR:
                getPopularMoviesList();
                return mPopularMovies;
            case TOP_RATED:
                getTopMoviesList();
                return mToprateMovies;
            case FAVORITES:
                return mFavoriteMovies = mMovieDao.loadAllMovies();
            default:
                // Required, otherwise 'Missing return statement' error
                throw new UnknownError(cheekyException);
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
    private void getPopularMoviesList(){
        // A Call represents the HTTP request with a generic parameter representing the response
        // body type which will be converted by one of the Converter.Factory instances
        //  Each call yields its own HTTP request and response pair
        Call<MovieResponse> call = mTmdbService.getPopularMovies(API_KEY);

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
                    // Set the value and dispatch the value to all the active observers
                    mPopularMovies.setValue(movies);
                } else {
                    Log.e(TAG, responseNotSuccessful);
                }
            }

            // Invoked when a network exception occurred talking to the server or when an unexpected
            // exception occurred creating the request or processing the response
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mPopularMovies = null;
                Log.e(TAG, networkException);
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
    private void getTopMoviesList(){
        // A Call represents the HTTP request with a generic parameter representing the response
        // body type which will be converted by one of the Converter.Factory instances
        //  Each call yields its own HTTP request and response pair
        Call<MovieResponse> call = mTmdbService.getTopRatedMovies(API_KEY);

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
                    mToprateMovies.setValue(movies);
                } else {
                    Log.e(TAG, responseNotSuccessful);
                }
            }

            // Invoked when a network exception occurred talking to the server or when an unexpected
            // exception occurred creating the request or processing the response
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                mToprateMovies = null;
                Log.e(TAG, networkException);
            }
        });
    }

    /**
     * Create a Movie entry into the 'favorites' database.
     *
     * @param movie Movie object to insert into the 'favorites' database.
     */
    public void insertMovie(final Movie movie){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.insertMovie(movie);
            }
        });
    }

    /**
     * Delete a Movie entry from the 'favorites' database.
     *
     * @param movie Movie object to delete from the 'favorites' database.
     */
    public void deleteMovie(final Movie movie){
        AppExecutors.getsInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mMovieDao.deleteMovie(movie);
            }
        });
    }

    /**
     * Read/retrieve Movie object from the 'favorites' database by its primary key.
     *
     * @param movieId Unique Movie ID from TMDB being used as the primary key of the 'favorites'
     *                database.
     * @return Return either a Movie object that is inside the 'favorites' database or return null.
     */
    public LiveData<Movie> getMovieById(final int movieId){
        return mMovieDao.loadMovieById(movieId);
    }
}
