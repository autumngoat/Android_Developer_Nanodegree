package com.example.full_dream.popularmoviesstage1.data;

import android.provider.BaseColumns;

/**
 * Defines the tables and columns for each table that are included in the database.
 */
public class FavoriteContract {

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
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_SYNOPSIS = "synopsis";
        public static final String COLUMN_NAME_RATING = "rating";
        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";
    }
}
