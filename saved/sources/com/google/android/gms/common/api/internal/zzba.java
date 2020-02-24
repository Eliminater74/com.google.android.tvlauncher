package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import java.util.Collections;

/* compiled from: GoogleApiClientDisconnected */
public final class zzba implements zzbi {
    private final zzbj zza;

    public zzba(zzbj zzbj) {
        this.zza = zzbj;
    }

    public final void zza() {
        for (Api.Client disconnect : this.zza.zza.values()) {
            disconnect.disconnect();
        }
        this.zza.zzd.zzc = Collections.emptySet();
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(T t) {
        this.zza.zzd.zza.add(t);
        return t;
    }

    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(T t) {
        throw new IllegalStateException("GoogleApiClient is not connected yet.");
    }

    public final boolean zzb() {
        return true;
    }

    public final void zzc() {
        this.zza.zzh();
    }

    public final void zza(Bundle bundle) {
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final void zza(int i) {
    }
}
