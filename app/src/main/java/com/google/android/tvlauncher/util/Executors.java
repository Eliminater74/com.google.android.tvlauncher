package com.google.android.tvlauncher.util;

import android.os.AsyncTask;
import android.support.annotation.VisibleForTesting;
import java.util.concurrent.Executor;

public final class Executors {
    private static Executor sExecutor = AsyncTask.THREAD_POOL_EXECUTOR;

    @VisibleForTesting
    public static void setThreadPoolExecutorForTesting(Executor executor) {
        sExecutor = executor;
    }

    public static Executor getThreadPoolExecutor() {
        return sExecutor;
    }

    private Executors() {
    }
}
