package com.google.android.libraries.stitch.util;

import android.os.Handler;
import android.os.Looper;

public final class ThreadUtil {
    private static Thread mainThread;
    private static volatile Handler sMainThreadHandler;

    public static boolean isMainThread() {
        if (mainThread == null) {
            mainThread = Looper.getMainLooper().getThread();
        }
        return Thread.currentThread() == mainThread;
    }

    public static boolean isOnCurrentThread(Handler handler) {
        return handler.getLooper().getThread() == Thread.currentThread();
    }

    public static void ensureMainThread() {
        if (!isMainThread()) {
            throw new RuntimeException("Must be called on the UI thread");
        }
    }

    public static void ensureBackgroundThread() {
        if (isMainThread()) {
            throw new RuntimeException("Must be called on a background thread");
        }
    }

    public static Handler getUiThreadHandler() {
        if (sMainThreadHandler == null) {
            sMainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return sMainThreadHandler;
    }

    public static void postOnUiThread(Runnable runnable) {
        getUiThreadHandler().post(runnable);
    }

    public static void postOnUiThread(Runnable runnable, boolean cancelPrevious) {
        if (cancelPrevious) {
            removeCallbacksOnUiThread(runnable);
        }
        postOnUiThread(runnable);
    }

    public static void postDelayedOnUiThread(Runnable runnable, long millis) {
        getUiThreadHandler().postDelayed(runnable, millis);
    }

    public static void removeCallbacksOnUiThread(Runnable runnable) {
        getUiThreadHandler().removeCallbacks(runnable);
    }
}
