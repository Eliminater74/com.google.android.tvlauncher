package com.google.android.libraries.performance.primes;

import android.support.annotation.VisibleForTesting;

@VisibleForTesting(otherwise = 3)
public final class PrimesStartupEvents {
    @VisibleForTesting(otherwise = 3)
    public static final String COLD_STARTUP_EVENT_NAME = "Cold startup";
    static final String COLD_STARTUP_INTERACTIVE_BEFORE_DRAWN_EVENT_NAME = "Cold startup interactive before onDraw";
    @VisibleForTesting(otherwise = 3)
    public static final String COLD_STARTUP_INTERACTIVE_EVENT_NAME = "Cold startup interactive";
    @VisibleForTesting(otherwise = 3)
    public static final String WARM_STARTUP_ACTIVITY_ON_START_EVENT_NAME = "Warm startup activity onStart";
    @VisibleForTesting(otherwise = 3)
    public static final String WARM_STARTUP_EVENT_NAME = "Warm startup";
    static final String WARM_STARTUP_INTERACTIVE_BEFORE_DRAWN_EVENT_NAME = "Warm startup interactive before onDraw";
    @VisibleForTesting(otherwise = 3)
    public static final String WARM_STARTUP_INTERACTIVE_EVENT_NAME = "Warm startup interactive";
}
