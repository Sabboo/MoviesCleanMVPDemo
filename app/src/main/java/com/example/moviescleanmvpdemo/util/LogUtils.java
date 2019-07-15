package com.example.moviescleanmvpdemo.util;

import android.util.Log;

import com.example.moviescleanmvpdemo.BuildConfig;

public final class LogUtils {

    private LogUtils() {
        // for preventing new LogUtils()
    }

    private static final String TAG = "MoviesCleanMVP";

    public static void v(String str) {
        if (BuildConfig.DEBUG)
            Log.v(TAG, str);
    }

    public static void i(String str) {
        if (BuildConfig.DEBUG)
            Log.i(TAG, str);
    }

    public static void e(String str, Throwable e) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, str, e);
    }

    public static void d(String str) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, str);
    }
}
