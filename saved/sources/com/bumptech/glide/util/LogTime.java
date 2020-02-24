package com.bumptech.glide.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.SystemClock;

public final class LogTime {
    private static final double MILLIS_MULTIPLIER;

    static {
        double d = 1.0d;
        if (Build.VERSION.SDK_INT >= 17) {
            d = 1.0d / Math.pow(10.0d, 6.0d);
        }
        MILLIS_MULTIPLIER = d;
    }

    private LogTime() {
    }

    @TargetApi(17)
    public static long getLogTime() {
        if (Build.VERSION.SDK_INT >= 17) {
            return SystemClock.elapsedRealtimeNanos();
        }
        return SystemClock.uptimeMillis();
    }

    public static double getElapsedMillis(long logTime) {
        double logTime2 = (double) (getLogTime() - logTime);
        double d = MILLIS_MULTIPLIER;
        Double.isNaN(logTime2);
        return logTime2 * d;
    }
}
