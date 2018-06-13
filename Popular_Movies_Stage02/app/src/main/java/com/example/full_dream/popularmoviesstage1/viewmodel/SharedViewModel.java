package com.example.full_dream.popularmoviesstage1.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.full_dream.popularmoviesstage1.model.Movie;

/**
 * Share data between two Fragments (Master: PosterListFragment, Detail: DetailFragment) using a
 * ViewModel object using their common Activity (MainActivity) to handle this communication.
 *
 * Source:
 * https://developer.android.com/topic/libraries/architecture/viewmodel#sharing
 */
public class SharedViewModel extends ViewModel {

    // LiveData which publicly exposes setValue(Movie) and postValue(Movie) methods, if available
    private final MutableLiveData<Movie> selected = new MutableLiveData<Movie>();

    // Sets the value to the selected Movie
    public void select(Movie movie){
        selected.setValue(movie);
    }

    // Returns the selected Movie
    public LiveData<Movie> getSelected(){
        return selected;
    }
}
