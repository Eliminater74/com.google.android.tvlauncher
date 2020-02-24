package com.google.android.libraries.performance.primes.metriccapture;

import android.os.SystemClock;

public final class TimeCapture {
    public static long getTime() {
        return SystemClock.elapsedRealtime();
    }
}
