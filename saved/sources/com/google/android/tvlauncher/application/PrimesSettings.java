package com.google.android.tvlauncher.application;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;
import com.google.android.gsf.Gservices;

public class PrimesSettings {
    static final String CLEARCUT_LOG_SOURCE_NAME = "TV_LAUNCHER_ANDROID_PRIMES";
    private static final boolean DEBUG = false;
    private static final boolean PRIMES_CRASH_METRIC_ENABLED_DEFAULT = false;
    private static final String PRIMES_CRASH_METRIC_ENABLED_KEY = "tvlauncher:primes_crash_metric_enabled";
    private static final boolean PRIMES_ENABLED_DEFAULT = false;
    private static final String PRIMES_ENABLED_KEY = "tvlauncher:primes_enabled";
    private static final boolean PRIMES_MEMORY_METRIC_ENABLED_DEFAULT = false;
    private static final String PRIMES_MEMORY_METRIC_ENABLED_KEY = "tvlauncher:primes_memory_metric_enabled";
    private static final boolean PRIMES_PACKAGE_STATS_METRIC_ENABLED_DEFAULT = false;
    private static final String PRIMES_PACKAGE_STATS_METRIC_ENABLED_KEY = "tvlauncher:primes_package_stats_metric_enabled";
    private static final String TAG = "PrimesSettings";
    private final ContentResolver mContentResolver;

    PrimesSettings(Context context) {
        this.mContentResolver = context.getApplicationContext().getContentResolver();
    }

    /* access modifiers changed from: package-private */
    public boolean isPrimesEnabled() {
        return getGservicesBoolean(this.mContentResolver, PRIMES_ENABLED_KEY, false);
    }

    /* access modifiers changed from: package-private */
    public boolean isPackageStatsMetricEnabled() {
        return getGservicesBoolean(this.mContentResolver, PRIMES_PACKAGE_STATS_METRIC_ENABLED_KEY, false);
    }

    /* access modifiers changed from: package-private */
    public boolean isMemoryMetricEnabled() {
        return getGservicesBoolean(this.mContentResolver, PRIMES_MEMORY_METRIC_ENABLED_KEY, false);
    }

    /* access modifiers changed from: package-private */
    public boolean isCrashMetricEnabled() {
        return getGservicesBoolean(this.mContentResolver, PRIMES_CRASH_METRIC_ENABLED_KEY, false);
    }

    private boolean getGservicesBoolean(ContentResolver cr, String key, boolean defaultValue) {
        try {
            return Gservices.getBoolean(cr, key, defaultValue);
        } catch (SecurityException e) {
            Log.w(TAG, "Gservices failed to get value", e);
            return defaultValue;
        }
    }
}
