package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.PreInitPrimesApi;
import java.util.concurrent.ThreadFactory;

final /* synthetic */ class PreInitPrimesApi$EarlyUncaughtExceptionHandler$$Lambda$0 implements ThreadFactory {
    static final ThreadFactory $instance = new PreInitPrimesApi$EarlyUncaughtExceptionHandler$$Lambda$0();

    private PreInitPrimesApi$EarlyUncaughtExceptionHandler$$Lambda$0() {
    }

    public Thread newThread(Runnable runnable) {
        return PreInitPrimesApi.EarlyUncaughtExceptionHandler.m44xf391977d(runnable);
    }
}
