package com.example.full_dream.popularmoviesstage1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.full_dream.popularmoviesstage1.data.FavoriteContract.*;

/**
 * Exists to create the database for the first time and upgrade it when the schema changes.
 *  Also provides other classes with a reference to the database, giving them a way to
 *  access it's information through queries.
 *
 *  Followed the Udacity course "Developing Android Apps" >>
 *  Lesson 9: Storing Data in SQLite >>
 *  8. Creating the database
 */
public class FavoriteDbHelper extends SQLiteOpenHelper {

    // Specifies the database file name
    //  This will be the name of the local file on the Android device that will store all
    //  of our data
    private static final String DATABASE_NAME = "favorite.db";
    // Current database version number which should always start at 1
    private static final int DATABASE_VERSION = 3;

    // The constructor shouldn't do anything special so just call the parent constructor
    public FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Responsible for creating the actual database for the first time.
     *
     * @param db SQLiteDatabase to call execSQL on to create our database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Start by writing the SQL statement that will describe the DB schema as defined
        // as written in FavoriteContract.java
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +
                FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoriteEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL," +
                FavoriteEntry.COLUMN_NAME_MOVIE_ID + " TEXT NOT NULL," +
                FavoriteEntry.COLUMN_NAME_POSTER + " TEXT NOT NULL," +
                FavoriteEntry.COLUMN_NAME_SYNOPSIS + " TEXT NOT NULL," +
                FavoriteEntry.COLUMN_NAME_RATING + " TEXT NOT NULL," +
                FavoriteEntry.COLUMN_NAME_RELEASE_DATE + " TEXT NOT NULL" +
                ");";

        // Execute the query by calling execSQL on the SQLiteDatabase, passing in the above
        // string, which creates our database
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    /**
     * Responsible for making sure the database schema is up-to-date, without losing any
     * user data, so ideally this should contain a schema update statement.
     *
     * @param db SQLiteDatabase to drop, update and/or create.
     * @param oldVersion Previous database version number.
     * @param newVersion Previous + 1 database version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, just delete the database if it exists...
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        //...and recreate it using onCreate()
        onCreate(db);
        // In the future, increment the DATABASE_VERSION number, write an update as SQL,
        // and execute using execSQL
    }
}
