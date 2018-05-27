package com.example.full_dream.popularmoviesstage1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 11: Building a Content Provider >>
 * 4. Steps to Create a Provider
 * 5. Create  Content Provider
 * 6. Create and Register a ContentProvider
 * 7. Define the URI Structure
 * 11. Build the URIMatcher
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
     * @return a UriMatcher that will help the ContentProvider recognize and respond correctly to
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

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
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

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
