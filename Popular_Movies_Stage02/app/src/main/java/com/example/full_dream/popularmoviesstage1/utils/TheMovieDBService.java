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

package com.example.full_dream.popularmoviesstage1.utils;

import com.example.full_dream.popularmoviesstage1.model.MovieResponse;
import com.example.full_dream.popularmoviesstage1.model.Review;
import com.example.full_dream.popularmoviesstage1.model.ReviewResponse;
import com.example.full_dream.popularmoviesstage1.model.Trailer;
import com.example.full_dream.popularmoviesstage1.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * API Service that specifics and defines each API endpoint to indicate how a HTTP request will be
 * handled.
 *
 * Started with Retrofit setup code from these tutorials:
 * https://medium.com/@shelajev/how-to-make-http-calls-on-android-with-retrofit-2-cfc4a67c6254
 * http://square.github.io/retrofit/
 * http://square.github.io/retrofit/2.x/retrofit/
 */
public interface TheMovieDBService {
    String API_KEY = "api_key";

    /**
     * Make a GET HTTP request for:
     *  the most popular movies or
     *  the top rated movies,
     * and returns a HTTP response as a MovieResponse POJO.
     *
     * Source:
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/http/GET.html
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
     */
    @GET("movie/{search}/") Call<MovieResponse> getMovies(
            @Path("search") String searchTerms,
            @Query(API_KEY) String apiKey);

    /**
     * Make a GET HTTP request for the videos (trailers) related to the selected movie and returns
     * a HTTP response as a Trailer POJO.
     *
     * Source:
     * https://developers.themoviedb.org/3/movies/get-movie-videos
     */
    @GET("movie/{movie_id}/videos?")
    Call<TrailerResponse> getTrailers(
            @Path("movie_id") int movieId,
            @Query(API_KEY) String apiKey);

    /**
     *
     *
     * Source:
     * https://developers.themoviedb.org/3/movies/get-movie-reviews
     */
    @GET("movie/{movie_id}/reviews?")
    Call<ReviewResponse> getReviews(
            @Path("movie_id") int movieId,
            @Query(API_KEY) String apiKey);
}
