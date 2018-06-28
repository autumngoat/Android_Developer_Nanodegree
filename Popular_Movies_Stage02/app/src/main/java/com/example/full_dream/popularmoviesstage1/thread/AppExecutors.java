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

package com.example.full_dream.popularmoviesstage1.thread;

// Android Imports
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

// Java Imports
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 *  Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait
 *  behind webservice requests).
 *
 * Taken whole-sale from:
 *  Udacity course "Developing Android Apps" >>
 *  Lesson 12: Android Architecture Components >>
 *  13. Exercise: Executors >>
 *  T09b.04-Exercise-Executors >>
 *  AppExecutors.java
 *
 *  Comment source:
 *  https://developer.android.com/reference/java/util/concurrent/Executor
 */
public class AppExecutors {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static AppExecutors sInstance;
    // An Executor is normally used instead of explicitly creating threads
    //  Rather than invoking a new thread for each RunnableTask:
    //   new Thread(new RunnableTask1().start());
    //   new Thread(new RunnableTask2().start());
    //  Use an Executor for all RunnableTasks:
    //   Executor exeggutor = anExecutor();
    //   exeggutor.execute(new RunnableTask1());
    //   exeggutor.execute(new RunanbleTask2());
    private final Executor diskIO;

    private AppExecutors(Executor diskIO){
        this.diskIO = diskIO;
    }

    public static AppExecutors getsInstance() {
        if(sInstance == null){
            synchronized (LOCK) {
                sInstance = new AppExecutors(
                        // The diskIO is a single thread Executor to ensure that database
                        // transactions are done in order to avoid race conditions
                        Executors.newSingleThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO(){ return diskIO; }
}
