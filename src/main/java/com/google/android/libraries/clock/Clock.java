package com.google.android.libraries.clock;

public interface Clock {
    long currentThreadTimeMillis();

    long currentTimeMillis();

    long elapsedRealtime();

    long elapsedRealtimeNanos();

    long nanoTime();

    long uptimeMillis();
}
