package com.example.full_dream.popularmoviesstage1.utils;

import com.example.full_dream.popularmoviesstage1.model.MovieResponse;

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
 */
public interface TheMovieDBService {
    String API_KEY = "api_key";

    /**
     * Make a GET HTTP request for the most popular movies and returns
     * a HTTP response as a MovieResponse POJO.
     *
     * Source:
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/http/GET.html
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
     */
    @GET("movie/{search}/") Call<MovieResponse> getPopularMovies(
            @Path("search") String user,
            @Query(API_KEY) String apiKey);

    /**
     * Make a GET HTTP request for the top rated movies and returns
     * a HTTP response as a Movieresponse POJO.
     *
     * Source:
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/http/GET.html
     * http://square.github.io/retrofit/2.x/retrofit/retrofit2/Call.html
     */
    @GET("movie/{search}/")
    Call<MovieResponse> getTopRatedMovies(
            @Path("search") String user,
            @Query(API_KEY) String apiKey);

}
