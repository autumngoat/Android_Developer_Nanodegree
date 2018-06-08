package com.example.full_dream.popularmoviesstage1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.full_dream.popularmoviesstage1.model.Movie;

/**
 * This annotation marks a class as a database. It should be an abstract class that extends
 * RoomDatabase.  At runtime, you can acquire an instance of it via Room.databaseBuilder or
 * Room.inMemoryDatabaseBuilder.
 *
 * This class defines the list of entities and data access objects (DAO) in the database.
 * It is also the main access point for the underlying connection.
 *
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 12: Android Architecture Components >>
 * 07. Creating the Database
 *
 * Other sources:
 * https://developer.android.com/reference/android/arch/persistence/room/package-summary
 */
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;
    private static final Object LOCK = new Object();
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "favorites";

    /**
     * Singleton constructor that creates a persistent database.
     *  Once built, refer to sInstance reference for re-use rather than creating a new instance.
     *
     * Comments Source:
     * https://developer.android.com/reference/android/arch/persistence/room/Room#databaseBuilder(android.content.Context,%20java.lang.Class%3CT%3E,%20java.lang.String)
     *
     * @param context Context for the database, which is often the application context.
     * @return Return an instance of AppDatabase if it doesn't already exist, otherwise return a
     * reference to it.
     */
    public static AppDatabase getInstance(Context context) {
        if(sInstance == null){
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating a new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    /**
     * MovieDao is a class annotated with @Dao
     *  At compile time, Room will generate an implementation of this class when it is referenced by
     *  a Database.
     *  The class that is annotated with Database must have an abstract method that has 0 arguments
     *  and returns the class that is annotated with Dao.
     *   Using Dao classes for database access rather than query builders or direct queries allows
     *   you to keep a separation of different components and easily mock the database access while
     *   testing your application.
     *
     * Comments Source:
     * https://developer.android.com/reference/android/arch/persistence/room/Database
     * https://developer.android.com/reference/android/arch/persistence/room/Dao
     * https://developer.android.com/reference/android/arch/persistence/room/package-summary
     *
     * @return Returns a DAO class used to access it.
     */
    public abstract MovieDao movieDao();
}
