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

package com.example.full_dream.popularmoviesstage1.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

/**
 * Provides a data model to represent a Movie POJO to hold parsed JSON data.
 *
 * Used jsonprettyprint.com and jsonschema2pojo.com to auto-generate this POJO
 * from a TMDB JSON HTTP Response.
 *
 * Used jsonschema2pojo.com to auto-generate Parcelable.
 *
 * Used https://medium.com/@adetayo_james/implement-parcelable-in-android-4d20994f0510
 * to troubleshoot the buggy auto-generate mess received from jsonschema2pojo.com.
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
public class Movie implements Parcelable{

    private boolean favorite = false;
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

    // Image query structure: https://developers.themoviedb.org/3/getting-started/images
    private static final String TMDB_IMG_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_FILE_SIZE = "w780";

    // Default constructor
    public Movie(){}

    /**
     * Interface that must be implemented and provided as a public CREATOR field that
     * generates instances of your Parcelable class from a Parcel.
     */
    public final static Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        /**
         * Create a new instance of the Parcelable class, instantiating it from the given Parcel
         * whose data had previously been written by Parcelable.writeToParcel().
         *
         * @param in The Parcel to read the object's data from.
         * @return Returns T, a new instance of the Parcelable class.
         */
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        /**
         * Creates a new array of the Parcelable class.
         *
         * @param size Size of the array.
         * @return Returns an array of the Parcelable class, with every entry initialized to null.
         */
        public Movie[] newArray(int size) {
            return (new Movie[size]);
        }
    };

    /**
     * Parcelable constructor used in createFromParcel() to create a new instance of the Parcelable
     * class, instantiating it from the given Parcel whose data had previously been written by
     * Parcelable.writeToParcel().
     *
     * https://developer.android.com/reference/android/os/Parcel
     *
     * @param in Container for a message (data and object references) that can be sent through an
     *           IBinder
     */
    //  Use the Ignore annotation so Room knows that it has to use the other constructor instead
    @Ignore
    private Movie(Parcel in) {
        // in.readInt() - Read an integer value from the parcel at the current dataPosition()
        //  Kept having ClassCastExceptions with casting ClassLoader until I stopped using object
        //  wrappers and used in.readType() methods... and now
        //  ((Cast) in.readValue((Cast.class.getClassLoader()))) works and I don't know why...
        this.voteCount = in.readInt();
        this.id = in.readInt();
        this.video = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.voteAverage = in.readDouble();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.backdropPath = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    /**
     * Flattens this object into a Parcel.
     *
     * Source: https://developer.android.com/reference/android/os/Parcelable#writeToParcel(android.os.Parcel,%20int)
     *
     * @param dest The Parcel in which object should be written.
     * @param flags Additional flags about how the object should be written. May be 0
     *              or PARCELABLE_WRITE_RETURN_VALUE.
     */
    public void writeToParcel(Parcel dest, int flags) {
        // writeValue() - Flatten a generic object in to a parcel.
        // Using writeValue() seemed to return null values... changed object wrappers to primitives
        // i.e. Integer to int and used specific writeType() methods and now writeValue() works...
        //  I don't know why
        dest.writeInt(voteCount);
        dest.writeInt(id);
        dest.writeValue(video);
        dest.writeDouble(voteAverage);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(backdropPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
    }

    /**
     * Indicates that the Parcelable object's flattened representation includes a file descriptor in
     * the output of writeToParcel(Parcel, int) or returns 0.
     *
     * @return 0 or CONTENTS_FILE_DESCRIPTOR bitmask.
     */
    public int describeContents() {
        return 0;
    }

    /**
     * Movie contructor with Popular Movies, Stage 1 required details.
     */
    public Movie(String title, String releaseDate, String posterPath, double voteAvg,
                 String plotSynopsis){
        this.title = title;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.voteAverage = voteAvg;
        this.overview = plotSynopsis;
    }

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

    // Return a complete URL link to poster image
    public String getPosterPath() {
        return TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + posterPath;
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
        return TMDB_IMG_BASE_URL + IMAGE_FILE_SIZE + backdropPath;
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

    /**
     * Return status of Movie object as favorite or not.
     *
     * @return Return Movie object's favorite status.
     */
    public boolean isFavorite(){
        return favorite;
    }

    /**
     * Set status of Movie object's favorite status.
     *
     * @param favorite Status that indicates whether the Movie object is a favorite
     *                 or not.
     */
    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }
}
