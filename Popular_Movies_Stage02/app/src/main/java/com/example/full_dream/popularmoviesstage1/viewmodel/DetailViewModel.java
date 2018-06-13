package com.example.full_dream.popularmoviesstage1.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.full_dream.popularmoviesstage1.database.AppDatabase;
import com.example.full_dream.popularmoviesstage1.model.Movie;

/**
 * This ViewModel is used to cache the list of Movie objects wrapped in a LiveData object.
 *  Extending AndroidViewModel requires an implementation of it's constructor.
 *   The AndroidViewModel class has a constructor that receives a parameter of type application.
 */
public class DetailViewModel extends ViewModel {

    public static final String TAG = DetailViewModel.class.getSimpleName();
    private LiveData<Movie> movie;

    public DetailViewModel(AppDatabase database, int movieId){
        movie = database.movieDao().loadMovieById(movieId);
    }

    public LiveData<Movie> getMovie(){
        return movie;
    }
}
