/*
 *  PROJECT LICENSE
 *
 *  This project was submitted by Seong Wang as part of the Android Developer Nanodegree at Udacity.
 *
 *  As part of Udacity Honor code:
 *    - I hereby confirm that all project submissions consist of my own work. Accordingly, I will document and cite the origins of any part(s) of my project submissions that were taken from websites, books, forums, blog posts, GitHub repositories, or any other source and explain why I used them for any part of my submission.
 *      - Acceptable sources consist of:
 *          - Unmodified or modified code from the Udacity courses
 *          - A modest amount of unmodified or modified code from third-party sources with attribution
 *      - NOT ACCEPTABLE: Any part of another student’s work, even with attribution
 *    - I understand that Udacity will check my submission for plagiarism, and that failure to adhere to the Udacity Honor Code
 *    may result in the cancellation of my enrollment without refund.
 *    - I understand that I may be asked to explain my work in a video call with a Udacity Representative before my Nanodegree is
 *    conferred.
 *
 *  I, the author of the project, allow you to check the code as a reference so long as you are not a fellow Android Developer
 *  Nanodegree student, but if you submit it, it's your own responsibility if you get expelled.
 *
 *  Copyright (c) 2018 Seong Wang
 *
 *  Besides the above notice, the following license applies and this license notice must be included in all works derived from
 *  this project.
 *
 *  MIT License
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.example.full_dream.baking;

// Android Imports
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// 3rd Party Imports - com - Baking
import com.example.full_dream.baking.databinding.ActivityMainBinding;
import com.example.full_dream.baking.fragment.RecipeFragment;

/**
 *
 */
public class MainActivity extends AppCompatActivity {
    public static final String FRAGMENT_KEY = "fragment";

    /**
     * Initialize first (and only) RecipeFragment instance here if it's the first time the app is
     * starting.
     *
     * @param savedInstanceState Null state means the app is starting for the first time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Must be here (not sure why)
        //  Otherwise:
        //   java.lang.IllegalArgumentException: No view found for id 0x7f070052 (com.example.full_dream.baking:id/fragment_container) for fragment RecipeFragment{b6ccaef #0 id=0x7f070052}
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        // If first time app is starting, create a RecipeFragment instance and add it to the
        // fragment back stack
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, new RecipeFragment())
                    .commit();
        }
    }

    /**
     * Store a reference to a Fragment to handle rotation/orientation/configuration change.
     *
     * @param outState Bundle that will hold a reference to a Fragment.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Put a reference to a Fragment in a Bundle
        getSupportFragmentManager().putFragment(outState,
                FRAGMENT_KEY,
                getSupportFragmentManager().findFragmentById(R.id.fragment_container));
    }

    /**
     * Flag whether or not to restore whichever Fragment in onResume based on the Bundle.
     *
     * @param savedInstanceState Bundle containing a reference to a Fragment.
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Retrieve the current Fragment instance for a reference previously placed with putFragment()
        getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
    }
}
