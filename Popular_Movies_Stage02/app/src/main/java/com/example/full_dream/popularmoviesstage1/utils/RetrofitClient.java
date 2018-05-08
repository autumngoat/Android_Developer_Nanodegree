package com.example.full_dream.popularmoviesstage1.utils;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * API client that will create and send the HTTP request and receive the HTTP response.
 *
 * Started with Retrofit setup code from these tutorials:
 * https://medium.com/@shelajev/how-to-make-http-calls-on-android-with-retrofit-2-cfc4a67c6254
 * http://square.github.io/retrofit/
 */
public class RetrofitClient {
    // 'private' is not allowed here
    // 'static final' are redundant for interface fields
    /**
     * Base URLs should always end in /.
     *
     * Source: http://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.Builder.html#baseUrl
     */
    private static final String TMDB_BASE_URL = "https://api.themoviedb.org/3/";

    public Retrofit retrofit;

    /**
     * Builds a new Retrofit,
     *  Calling baseUrl() before build() is MANDATORY.
     *  All other methods are OPTIONAL.
     *   addConverterFactory() provides a default factory to deserialize the JSON response object
     *   into a POJO.
     *    In this case, using the Moshi library.
     *
     * Source: http://square.github.io/retrofit/2.x/retrofit/retrofit2/Retrofit.Builder.html
     */
    public Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        return retrofit;
    }
}
