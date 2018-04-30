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

import android.net.Uri;
import android.util.Log;

import com.example.full_dream.popularmoviesstage1.MainActivity;
import com.example.full_dream.popularmoviesstage1.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import butterknife.BindString;

/**
 * Utilities used to communicate with the network.
 */
public class NetworkUtils {

    // Looked to T05b.02-Exercise-AddAsyncTaskLoader for network handling utilities
    // Search query structure: https://developers.themoviedb.org/3/discover/movie-discover
    // Image query structure: https://developers.themoviedb.org/3/getting-started/images
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String TMDB_IMG_BASE_URL = "https://image.tmdb.org/t/p";
    private static final String IMAGE_FILE_SIZE = "w185";
    private static final String TMDB_DISCOVER_BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String PARAM_API_KEY = "api_key";
    private static final String FORWARD_SLASH = "/";
    private static final String apiKey = "GetYourOwnApi";

    /**
     * Builds the URL used to query TheMovieDB (TMDB).
     *
     * @param tmdbDiscoveryQuery The keyword that will be queried for
     * @return The URL used to query TheMovieDB
     */
    public static URL buildSearchUrl(String tmdbDiscoveryQuery) {
        Uri builtUri = Uri.parse(TMDB_DISCOVER_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_SORT_BY, tmdbDiscoveryQuery)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG,"TMDB Discovery query resulted in an malformed URL:" + e);
        }

        return url;
    }

    /**
     * Returns the entire JSON result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from
     * @return The JSON contents of the HTTP response
     * @throws IOException related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Builds the URL used to retrieve the movie poster image.
     *
     * @param posterPath File path portion of image link
     * @return Movie poster image link
     */
    public static String buildImgPath(String posterPath) {
        if (posterPath.isEmpty()) {
            return null;
        }

        // Uri and URL appendPath returns broken links
        StringBuilder sb = new StringBuilder();
        sb.append(TMDB_IMG_BASE_URL)
                .append(FORWARD_SLASH)
                .append(IMAGE_FILE_SIZE)
                .append(FORWARD_SLASH)
                .append(posterPath);
        return sb.toString();
    }
}
