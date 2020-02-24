package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzau;
import java.util.concurrent.TimeUnit;

/* compiled from: Stopwatch */
public final class zzckw {
    private long zza;
    private long zzb;

    public zzckw() {
        this(-1);
    }

    private zzckw(long j) {
        this.zza = -1;
        this.zzb = -1;
    }

    public final zzckw zza() {
        this.zzb = zzc();
        return this;
    }

    public final long zzb() {
        zzau.zzb(this.zzb != -1);
        return TimeUnit.NANOSECONDS.toMillis(zzc() - this.zzb);
    }

    private final long zzc() {
        long j = this.zza;
        if (j == -1) {
            return System.nanoTime();
        }
        this.zza = -1;
        return j;
    }
}
