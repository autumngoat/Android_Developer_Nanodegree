package com.example.full_dream.popularmoviesstage1.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.full_dream.popularmoviesstage1.database.AppDatabase;

/**
 *
 *
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 12: Android Architecture Components >>
 * 23. Exercise: Adding the ViewModel to AddTaskActivity
 */
public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    // Instance of the database
    private final AppDatabase mDb;
    // ID of the Movie to update
    private final int mMovieId;

    public DetailViewModelFactory(AppDatabase database, int movieId){
        mDb = database;
        mMovieId = movieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        // No inspection unchecked
        return (T) new DetailViewModel(mDb, mMovieId);
    }
}
