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

package com.example.full_dream.baking.network;

// 3rd Party Imports - Retrofit2
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * API client that will create and send the HTTP request and receive the HTTP response.
 */
public class RetrofitClient {

    /**
     * Base URLs should always end in / and must start with 'http' or 'https' else:
     *  java.lang.IllegalArgumentException: Illegal URL:
     *
     * Comment source:
     *  https://stackoverflow.com/questions/35114456/java-lang-illegalargumentexception-illegal-url-with-retrofit
     */
    private static final String BAKING_URL = "http://go.udacity.com/";

    /**
     * Specify the base URL for the Retrofit instance and pass it to create(Class<T> service) to
     * generate an API service.
     *
     * @return An API service
     */
    public static BakingService getApiClient(){
        // Build a new Retrofit object using builder...
        Retrofit retrofit = new Retrofit.Builder()
                // Set the API base URL.  Calling baseUrl() before calling build() is required.
                .baseUrl(BAKING_URL)
                // Add a converter factory for serialization and deserialization of objects
                .addConverterFactory(MoshiConverterFactory.create())
                // Create the Retrofit instance using the configured values
                .build();

        // ...and pass Retrofit instance to create(Class<T> service) to generate an API service
        return retrofit.create(BakingService.class);
    }
}
