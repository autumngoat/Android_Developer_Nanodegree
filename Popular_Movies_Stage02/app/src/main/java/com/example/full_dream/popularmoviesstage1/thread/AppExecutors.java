package com.example.full_dream.popularmoviesstage1.thread;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

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
    private final Executor mainThread;
    private final Executor networkIO;

    private AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO){
        this.diskIO = diskIO;
        this.mainThread = mainThread;
        this.networkIO = networkIO;
    }

    public static AppExecutors getsInstance() {
        if(sInstance == null){
            synchronized (LOCK) {
                sInstance = new AppExecutors(
                        // The diskIO is a single thread Executor to ensure that database
                        // transactions are done in order to avoid race conditions
                        Executors.newSingleThreadExecutor(),
                        // The networkIO Executor is a pool of 3 threads to allow different network
                        // calls simultaneously while controlling the number of threads possible
                        Executors.newFixedThreadPool(3),
                        // Access the main/UI thread using this Executor
                        new MainThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor diskIO(){ return diskIO; }

    public Executor mainThread(){ return mainThread; }

    public Executor networkIO(){ return networkIO; }

    /**
     * Posts runnables using a handle associated with the main looper.
     */
    public static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        /**
         * Executes the given command sometime in the future.
         *
         * @param command The Runnable task.
         */
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
