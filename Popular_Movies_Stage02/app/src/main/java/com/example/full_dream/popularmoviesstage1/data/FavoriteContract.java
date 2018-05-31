package com.example.full_dream.popularmoviesstage1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines the tables and columns for each table that are included in the database.
 *
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 9: Storing Data in SQLite >>
 * 5. Creating the Contract
 * Lesson 11: Building a Content Provider >>
 * 10. Change the Contract
 */
public class FavoriteContract {

    // The content authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.full_dream.popularmoviesstage1";
    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    // This is the path for the "favorites" directory
    public static final String PATH_FAVORITE = "favorite";

    /**
     * Prevent someone from accidentally making an instance of FavoriteContract.
     */
    private FavoriteContract(){}

    /**
     * Inner class that defines a table's contents.
     *  Implementing the BaseColumns interface auto-includes a constant representing the
     *  primary key field called _ID.
     */
    public static final class FavoriteEntry implements BaseColumns{
        // FavoriteEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI
                .buildUpon()
                .appendPath(PATH_FAVORITE)
                .build();

        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_MOVIE_ID = "movieId";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_SYNOPSIS = "synopsis";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
    }
}
