package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: CompositeGoogleApiClient */
final class zzy implements zzcd {
    private final /* synthetic */ zzw zza;

    private zzy(zzw zzw) {
        this.zza = zzw;
    }

    /* synthetic */ zzy(zzw zzw, zzx zzx) {
        this(zzw);
    }

    public final void zza(@Nullable Bundle bundle) {
        this.zza.zzm.lock();
        try {
            this.zza.zza(bundle);
            ConnectionResult unused = this.zza.zzj = ConnectionResult.zza;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        this.zza.zzm.lock();
        try {
            ConnectionResult unused = this.zza.zzj = connectionResult;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzw.zza(com.google.android.gms.common.api.internal.zzw, boolean):boolean
     arg types: [com.google.android.gms.common.api.internal.zzw, int]
     candidates:
      com.google.android.gms.common.api.internal.zzw.zza(com.google.android.gms.common.api.internal.zzw, com.google.android.gms.common.ConnectionResult):com.google.android.gms.common.ConnectionResult
      com.google.android.gms.common.api.internal.zzw.zza(int, boolean):void
      com.google.android.gms.common.api.internal.zzw.zza(com.google.android.gms.common.api.internal.zzw, android.os.Bundle):void
      com.google.android.gms.common.api.internal.zzw.zza(long, java.util.concurrent.TimeUnit):com.google.android.gms.common.ConnectionResult
      com.google.android.gms.common.api.internal.zzcc.zza(long, java.util.concurrent.TimeUnit):com.google.android.gms.common.ConnectionResult
      com.google.android.gms.common.api.internal.zzw.zza(com.google.android.gms.common.api.internal.zzw, boolean):boolean */
    public final void zza(int i, boolean z) {
        this.zza.zzm.lock();
        try {
            if (!this.zza.zzl && this.zza.zzk != null) {
                if (this.zza.zzk.isSuccess()) {
                    boolean unused = this.zza.zzl = true;
                    this.zza.zze.onConnectionSuspended(i);
                    this.zza.zzm.unlock();
                    return;
                }
            }
            boolean unused2 = this.zza.zzl = false;
            this.zza.zza(i, z);
        } finally {
            this.zza.zzm.unlock();
        }
    }
}
