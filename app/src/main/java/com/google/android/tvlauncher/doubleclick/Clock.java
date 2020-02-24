package com.google.android.tvlauncher.doubleclick;

public interface Clock {
    long getCurrentTimeMillis();

    void sleep(long j) throws InterruptedException;
}
