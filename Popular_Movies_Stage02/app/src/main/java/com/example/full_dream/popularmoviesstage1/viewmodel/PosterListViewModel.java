package com.example.full_dream.popularmoviesstage1.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.full_dream.popularmoviesstage1.database.AppDatabase;
import com.example.full_dream.popularmoviesstage1.model.Movie;

import java.util.List;

/**
 * This ViewModel is used to cache the list of Movie objects wrapped in a LiveData object.
 *  Extending AndroidViewModel requires an implementation of it's constructor.
 *   The AndroidViewModel class has a constructor that receives a parameter of type application.
 *
 * Followed the Udacity course "Developing Android Apps" >>
 * Lesson 12: Android Architecture Components >>
 * 22. Exercise: Adding the ViewModel
 */
public class PosterListViewModel extends AndroidViewModel {

    private static final String TAG = PosterListViewModel.class.getSimpleName();
    private LiveData<List<Movie>> movies;

    /**
     * Subclasses of AndroidViewModel MUST have a constructor that accepts Application as the ONLY
     * parameter, according to:
     *  https://developer.android.com/reference/android/arch/lifecycle/AndroidViewModel)
     *
     * @param application Base class for maintaining global application state.
     */
    public PosterListViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the movies from the Database");
        this.movies = database.movieDao().loadAllMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
