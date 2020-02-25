package com.google.android.libraries.clock.impl;

import android.os.Build;
import android.os.SystemClock;

import com.google.android.libraries.clock.Clock;

public class SystemClockImpl implements Clock {
    private static final boolean ELAPSED_REALTIME_NANOS_EXISTS = elapsedRealtimeNanosExists();
    private static final long NS_IN_MS = 1000000;

    private static boolean elapsedRealtimeNanosExists() {
        try {
            if (Build.VERSION.SDK_INT < 17) {
                return false;
            }
            long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long nanoTime() {
        return System.nanoTime();
    }

    public long currentThreadTimeMillis() {
        return SystemClock.currentThreadTimeMillis();
    }

    public long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public long elapsedRealtimeNanos() {
        if (ELAPSED_REALTIME_NANOS_EXISTS) {
            return SystemClock.elapsedRealtimeNanos();
        }
        return SystemClock.elapsedRealtime() * 1000000;
    }

    public long uptimeMillis() {
        return SystemClock.uptimeMillis();
    }
}
