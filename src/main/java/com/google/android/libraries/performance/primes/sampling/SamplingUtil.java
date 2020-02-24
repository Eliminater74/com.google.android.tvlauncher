package com.google.android.libraries.performance.primes.sampling;

import android.content.SharedPreferences;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.metriccapture.TimeCapture;
import com.google.android.libraries.stitch.util.ThreadUtil;

public final class SamplingUtil {
    private static final String TAG = "SamplingUtil";

    public static boolean hasRecentTimeStamp(SharedPreferences sharedPrefs, String key, long maxAgeMs) {
        ThreadUtil.ensureBackgroundThread();
        long lastTimeStampMs = readTimeStamp(sharedPrefs, key);
        long currentTimeMs = TimeCapture.getTime();
        if (currentTimeMs < lastTimeStampMs) {
            if (!sharedPrefs.edit().remove(key).commit()) {
                PrimesLog.m46d(TAG, "Failure storing timestamp to SharedPreferences", new Object[0]);
            }
            lastTimeStampMs = -1;
        }
        if (lastTimeStampMs == -1 || currentTimeMs > lastTimeStampMs + maxAgeMs) {
            return false;
        }
        return true;
    }

    public static boolean writeTimeStamp(SharedPreferences sharedPrefs, String key) {
        return writeTimeStamp(sharedPrefs, key, TimeCapture.getTime());
    }

    private static boolean writeTimeStamp(SharedPreferences sharedPrefs, String key, long timeStampMs) {
        return sharedPrefs.edit().putLong(key, timeStampMs).commit();
    }

    public static long readTimeStamp(SharedPreferences sharedPrefs, String key) {
        return sharedPrefs.getLong(key, -1);
    }

    private SamplingUtil() {
    }
}
