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
@Database(entities = {Movie.class}, version = 2, exportSchema = false)
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
     * Followed the Udacity course "Developing Android Apps" >>
     * Lesson 12: Android Architecture Components >>
     * 10. Exercise: Save the Task
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
