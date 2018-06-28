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

package com.example.full_dream.popularmoviesstage1.viewmodel;

// Android Imports
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

// 3rd Party Imports - com - Popular Movies Stage 2
import com.example.full_dream.popularmoviesstage1.database.MovieRepository;
import com.example.full_dream.popularmoviesstage1.model.Movie;

/**
 * This ViewModel is used to cache the list of Movie objects wrapped in a LiveData object.
 *  Extending AndroidViewModel requires an implementation of it's constructor.
 *   The AndroidViewModel class has a constructor that receives a parameter of type application.
 */
public class DetailViewModel extends AndroidViewModel {

    // Hold a reference to the repository
    private MovieRepository mRepository;

    /**
     * Subclasses of AndroidViewModel MUST have a constructor that accepts Application as the ONLY
     * parameter, according to:
     *  https://developer.android.com/reference/android/arch/lifecycle/AndroidViewModel)
     *
     * @param application Base class for maintaining global application state.
     */
    public DetailViewModel(@NonNull Application application){
        super(application);
        mRepository = new MovieRepository(application);
    }

    /**
     * Wrapper method that calls the MovieRepository's getMovieById() method, which hides the
     * implementation from the UI.
     *
     * @param movieId Unique Movie ID from TMDB used in the local database as the primary key.
     * @return Movie object with the unique Movie ID
     */
    public LiveData<Movie> getMovieById(int movieId){
        return mRepository.getMovieById(movieId);
    }

    /**
     * Wrapper method that calls the MovieRepository's insertMovie() method, which hides the
     * implementation from the UI.
     *
     * @param movie Movie instance to insert into the AppDatabase.
     */
    public void insertMovie(Movie movie){ mRepository.insertMovie(movie); }

    /**
     * Wrapper method that calls the MovieRepository's deleteMovie() method, which hides the
     * implementation from the UI.
     *
     * @param movie Movie instance to delete from the AppDatabase.
     */
    public void deleteMovie(Movie movie){ mRepository.deleteMovie(movie); }
}
