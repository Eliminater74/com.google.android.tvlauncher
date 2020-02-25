package com.google.android.gms.common.api.internal;

import android.support.annotation.Nullable;
import android.support.p001v4.util.ArrayMap;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.AvailabilityException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.Map;
import java.util.Set;

@Hide
/* compiled from: AvailabilityTaskWrapper */
public final class zzk {
    private final ArrayMap<zzi<?>, ConnectionResult> zza = new ArrayMap<>();
    private final ArrayMap<zzi<?>, String> zzb = new ArrayMap<>();
    private final TaskCompletionSource<Map<zzi<?>, String>> zzc = new TaskCompletionSource<>();
    private int zzd;
    private boolean zze = false;

    public zzk(Iterable<? extends GoogleApi<?>> iterable) {
        for (GoogleApi zzd2 : iterable) {
            this.zza.put(zzd2.zzd(), null);
        }
        this.zzd = this.zza.keySet().size();
    }

    public final Set<zzi<?>> zza() {
        return this.zza.keySet();
    }

    public final Task<Map<zzi<?>, String>> zzb() {
        return this.zzc.getTask();
    }

    public final void zza(zzi<?> zzi, ConnectionResult connectionResult, @Nullable String str) {
        this.zza.put(zzi, connectionResult);
        this.zzb.put(zzi, str);
        this.zzd--;
        if (!connectionResult.isSuccess()) {
            this.zze = true;
        }
        if (this.zzd != 0) {
            return;
        }
        if (this.zze) {
            this.zzc.setException(new AvailabilityException(this.zza));
            return;
        }
        this.zzc.setResult(this.zzb);
    }
}
