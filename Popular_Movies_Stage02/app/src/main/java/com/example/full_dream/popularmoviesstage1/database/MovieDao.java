package com.example.full_dream.popularmoviesstage1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.full_dream.popularmoviesstage1.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    // Create
    @Insert
    void insertMovie(Movie movie);

    // Read
    @Query("SELECT * FROM favorites")
    List<Movie> loadAllMovies();

    // Update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    // Delete
    @Delete
    void deleteMovie(Movie movie);
}
