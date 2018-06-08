package com.example.full_dream.popularmoviesstage1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.full_dream.popularmoviesstage1.model.Movie;

import java.util.List;

/**
 * Marks the class as a Data Access Object.
 *  DAO are the main classes where you define your database interactions.
 *   They can include a variety of query methods.
 *
 * Comments Source:
 * https://developer.android.com/reference/android/arch/persistence/room/Dao
 *
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 12: Android Architecture Components >>
 * 06. Creating a DAO
 */
@Dao
public interface MovieDao {

    // Create
    @Insert
    void insertMovie(Movie movie);

    // Read
    @Query("SELECT * FROM movie")
    List<Movie> loadAllMovies();

    // Update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    // Delete
    @Delete
    void deleteMovie(Movie movie);
}
