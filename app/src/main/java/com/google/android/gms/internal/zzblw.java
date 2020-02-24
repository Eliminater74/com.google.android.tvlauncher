package com.google.android.gms.internal;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: PooledExecutorsProvider */
final class zzblw implements zzblx {
    zzblw() {
    }

    public final ScheduledExecutorService zza() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
