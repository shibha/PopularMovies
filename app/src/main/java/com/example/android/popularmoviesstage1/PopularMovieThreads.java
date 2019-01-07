package com.example.android.popularmoviesstage1;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class PopularMovieThreads {

    private final Executor secondaryIOThread;
    private final Executor applicationThread;
    private final Executor bandwidthUsageInputOuputThread;
    private static final Object LOCK = new Object();
    private static PopularMovieThreads sInstance;


    private PopularMovieThreads(Executor secondaryIOThread, Executor bandwidthUsageInputOuputThread, Executor applicationThread) {
        this.secondaryIOThread = secondaryIOThread;
        this.bandwidthUsageInputOuputThread = bandwidthUsageInputOuputThread;
        this.applicationThread = applicationThread;
    }

    public static PopularMovieThreads getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new PopularMovieThreads(Executors.newSingleThreadExecutor(),
                        Executors.newFixedThreadPool(5),
                        new ApplicationThreadExecutor());
            }
        }
        return sInstance;
    }

    public Executor secondaryStorageIOThread() {
        return secondaryIOThread;
    }


    private static class ApplicationThreadExecutor implements Executor {
        private Handler applicationThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            applicationThreadHandler.post(command);
        }
    }
}
