package com.google.android.gms.common.util;

import android.os.SystemClock;

/* compiled from: DefaultClock */
public final class zzh implements Clock {
    private static zzh zza = new zzh();

    private zzh() {
    }

    public static Clock zza() {
        return zza;
    }

    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public final long nanoTime() {
        return System.nanoTime();
    }

    public final long currentThreadTimeMillis() {
        return SystemClock.currentThreadTimeMillis();
    }
}
