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

package com.example.full_dream.popularmoviesstage2.model;

// Android Imports
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

// 3rd Party Imports - Butterknife
import butterknife.BindString;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage2.R;

// 3rd Party Imports - com - Moshi
import com.squareup.moshi.Json;

/**
 * Provides a data model to represent a Movie POJO to hold parsed JSON data.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 *
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 12: Android Architecture Components >>
 * 04. Exercise: Creating an Entity
 *
 * Annotate the class with Entity and use "movie" for the table name.
 *  This annotation marks a class as a database row.
 *   For each Entity, a database table is created to hold the items.
 *
 * Comments Source:
 * https://developer.android.com/reference/android/arch/persistence/room/package-summary
 * https://developer.android.com/reference/android/arch/persistence/room/Entity
 */
@Entity(tableName = "movie")
public class Movie {

    @Json(name = "vote_count")
    private int voteCount;
    // Each Entity class MUST have at least 1 field annotated with PrimaryKey
    @PrimaryKey
    @Json(name = "id")
    private int id;
    @Json(name = "video")
    private boolean video;
    @Json(name = "vote_average")
    private double voteAverage;
    @Json(name = "title")
    private String title;
    @Json(name = "popularity")
    private double popularity;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "original_language")
    private String originalLanguage;
    @Json(name = "original_title")
    private String originalTitle;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "overview")
    private String overview;
    @Json(name = "release_date")
    private String releaseDate;
    @Ignore
    @BindString(R.string.placeholder)
    String placeholder;

    // Image query structure: https://developers.themoviedb.org/3/getting-started/images
    private static final String TMDB_IMG_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_FILE_SIZE = "w780";

    // Constructor for use by Entity
    //  Constructor must be public and either empty or have parameters that match the fields by type
    //  and name (a.k.a., cannot change 'video' to 'hasVideo' or 'voteAverage' to 'rating'
    public Movie(){}

    /**
     * Formatted String representation of Movie object for better debugging purposes.
     *
     * @return Return String representation of Movie object.
     */
    @Override
    public String toString() {
        return "\ntitle: " + this.title +
                "\nreleaseDate: " + this.releaseDate +
                "\nposterPath: " + this.posterPath +
                "\nvoteAverage: " + this.voteAverage +
                "\noverview: " + this.overview;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        // Null check and empty String check
        //  Must return non-empty non-null String
        //   Picasso.load() states that it will throw an IllegalArgumentException if the URL is
        //   empty or null.
        //    Source:
        //    https://square.github.io/picasso/2.x/picasso/
        if(posterPath == null || posterPath.isEmpty()){
            return placeholder;
        }
        // Most Popular and Top Rated always get their path from a network call so no issue there,
        // but Favorites would save the path as 'TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + posterPath'
        // and then return getBackdropPath as 'TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE +
        // TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + posterPath' which is obviously wrong.
        else if(posterPath.startsWith("http")){
            return posterPath;
        } else {
            return TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + posterPath;
        }
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getBackdropPath() {
        // Null check and empty String check
        //  Must return non-empty non-null String
        //   Picasso.load() states that it will throw an IllegalArgumentException if the URL is
        //   empty or null.
        //    Source:
        //    https://square.github.io/picasso/2.x/picasso/
        if(backdropPath == null || backdropPath.isEmpty()){
            return placeholder;
        }
        // Most Popular and Top Rated always get their path from a network call so no issue there,
        // but Favorites would save the path as 'TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + backdropPath'
        // and then return getBackdropPath as 'TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE +
        // TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + backdropPath' which is obviously wrong.
        else if(backdropPath.startsWith("http")){
            return backdropPath;
        } else {
            return TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + backdropPath;
        }
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
