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

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.full_dream.popularmoviesstage1.model.Movie;
import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.network.RetrofitClient;
import com.example.full_dream.popularmoviesstage1.network.TheMovieDBService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Data layer responsible for fetching data from local or remote data source and send it to the
 * ViewModel.
 *  Abstracts the data sources from the rest of the app.
 *   Removes network and database calls from ViewModel, Fragment, etc.
 */
public class MovieRepository {


    public LiveData<Movie> getMovie(int movieId){
        final MutableLiveData<Movie> data = new MutableLiveData<>();

        return data;
    }

    public LiveData<List<Movie>> getMovieList(){
        final MutableLiveData<List<Movie>> dataList = new MutableLiveData<>();

        RetrofitClient client = new RetrofitClient();
        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);
        Call<MovieResponse> call;

//        // Asynchronously send the HTTP request and notify the callback of its HTTP response
//        // or if an error occurred talking to the server, creating the HTTP request
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//
//                if(response.isSuccessful()){
//                    List<Movie> movies = response.body().getResults();
//                    if(movies != null){
//                        dataList.setValue(movies);
//                    }
//                }
////                mPosterAdapter.setMovieData(movies);
////                mPosterAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
////                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//            }
//        });

        return dataList;
    }

//    /**
//     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
//     */
//    public void callRetrofit(){
//
//        // Popular or Top Rated or Favorites?
//        switch(mOption){
//            case MOST_POPULAR:
//                call = service.getPopularMovies(API_KEY);
//                break;
//            case TOP_RATED:
//                call = service.getTopRatedMovies(API_KEY);
//                break;
//            case FAVORITES:
//                setupViewModel();
//                return;
//            default:
//                throw new UnknownError("This is not a CTF, go somewhere else for decompiling fun.");
//        }
//    }

//    /**
//     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
//     */
//    public void callRetrofitForTrailers(){
//        // Instantiate the Retrofit (type safe HTTP) client
//        RetrofitClient client = new RetrofitClient();
//
//        // Pass service interface to create() to generate an implementation of the API endpoint
//        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);
//
//        // Call represents the HTTP request while the generic parameter, in this case
//        // MovieResponse, represents the HTTP response body type which will be converted
//        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
//        Call<TrailerResponse> call;
//
//        call = service.getTrailers(mMovie.getId(), API_KEY);
//
//        // Asynchronously send the HTTP request and notify the callback of its HTTP response
//        // or if an error occurred talking to the server, creating the HTTP request
//        call.enqueue(new Callback<TrailerResponse>() {
//            @Override
//            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
//                List<Trailer> trailers = response.body().getResults();
//
//                mTrailerAdapter.setTrailerList(trailers);
//                mTrailerAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<TrailerResponse> call, Throwable t) {
//                Log.e("rabbit", "Problems retrieving Trailers");
//            }
//        });
//    }
//
//    /**
//     * Helper function to be able to call Retrofit whenever data needs to be retrieved.
//     */
//    public void callRetrofitForReviews(){
//        // Instantiate the Retrofit (type safe HTTP) client
//        RetrofitClient client = new RetrofitClient();
//
//        // Pass service interface to create() to generate an implementation of the API endpoint
//        TheMovieDBService service = client.getClient().create(TheMovieDBService.class);
//
//        // Call represents the HTTP request while the generic parameter, in this case
//        // MovieResponse, represents the HTTP response body type which will be converted
//        // by one of the Converter.Factory instances (Moshi) to JSON to POJO(s).
//        Call<ReviewResponse> call;
//        call = service.getReviews(mMovie.getId(), API_KEY);
//
//        // Asynchronously send the HTTP request and notify the callback of its HTTP response
//        // or if an error occurred talking to the server, creating the HTTP request
//        call.enqueue(new Callback<ReviewResponse>() {
//            @Override
//            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
//                List<Review> review = response.body().getReviews();
//
//                mReviewAdapter.setReviewList(review);
//                mReviewAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ReviewResponse> call, Throwable t) {
//                Log.e("rabbit", "Problems retrieving Reviews");
//            }
//        });
//    }
}
