package com.example.full_dream.popularmoviesstage1.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.full_dream.popularmoviesstage1.data.FavoriteContract.*;

/**
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 11: Building a Content Provider >>
 * 4. Steps to Create a Provider
 * 5. Create  Content Provider
 * 6. Create and Register a ContentProvider
 * 7. Define the URI Structure
 * 11. Build the URIMatcher
 * 19. Insert
 * 20. Hook it up to the UI
 */
public class FavoriteContentProvider extends ContentProvider {

    // Integer constant for the directory of favorites
    public static final int FAVORITES = 100;
    // Integer constant for items by primary key for the directory of favorites
    public static final int FAVORITE_WITH_ID = 101;
    //
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Member variable for a FavoriteDbHelper that is initialized in the onCreate() method
    private FavoriteDbHelper mFavoriteDbHelper;

    /**
     * Match URI patterns to integer constants.
     *
     * @return A UriMatcher that will help the ContentProvider recognize and respond correctly to
     * different types of URIs.
     */
    public static UriMatcher buildUriMatcher(){
        // Create an new empty UriMatcher instance
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // directory
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITE, FAVORITES);
        // single item
        uriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.PATH_FAVORITE + "/#", FAVORITE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        // Retrieve the content
        Context context = getContext();
        // Create the DbHelper instance
        mFavoriteDbHelper = new FavoriteDbHelper(context);
        // Instead of returning false, return true because this method is done
        return true;
    }

    /**
     * Insert a row of new data into the ContentProvider.
     *
     * @param uri Identifies the correct directory to insert data into.
     * @param values Object with new data to insert.
     * @return A newly created content Uri that tells the location of the inserted data.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Get access to the favorites database to write new data to it
        //  Marked as final because it will be used throughout this function
        final SQLiteDatabase db = mFavoriteDbHelper.getWritableDatabase();

        // This match will either be 100 for all favorites, 101 for a favorite with ID, or an
        // unrecognized URI
        int match = sUriMatcher.match(uri);

        // Uri to return at the end of the function
        Uri returnUri;

        switch(match){
            case FAVORITES:
                // Attempt to insert values into favorites table
                long id = db.insert(FavoriteEntry.TABLE_NAME, null, values);
                if(id > 0){
                    // Insert successful
                    returnUri = ContentUris.withAppendedId(FavoriteEntry.CONTENT_URI, id);
                } else {
                    // Insert failed
                    throw new SQLiteException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Notify the resolver if the uri has been changed so that it can update the database and
        // any associated UI accordingly
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
