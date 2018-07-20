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

package com.example.full_dream.popularmoviesstage2.viewmodel;

// Android Imports
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage2.database.MovieRepository;
import com.example.full_dream.popularmoviesstage2.model.Movie;

// Java Imports
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

    // Hold a reference to the repository
    private MovieRepository mRepository;

    /**
     * Subclasses of AndroidViewModel MUST have a constructor that accepts Application as the ONLY
     * parameter, according to:
     *  https://developer.android.com/reference/android/arch/lifecycle/AndroidViewModel)
     *
     * @param application Base class for maintaining global application state.
     */
    public PosterListViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
    }

    /**
     * MovieRepository getter method for retrieving a LiveData object of a list of Movie objects that
     * hides the implementation from the UI.
     *
     * @return A LiveData object of a list of Movie objects.
     */
    public LiveData<List<Movie>> getMovies(int settingsOption) {
        return mRepository.getMovieList(settingsOption);
    }


    /**
     * MovieRepository getter method for retrieving a LiveData object of an Integer that represents
     * network connection or no network connection based on its value.
     *
     * @return A LiveData object of an Integer object.
     */
    public LiveData<Integer> getInternetStatus(){
        return mRepository.getInternetStatus();
    }
}
