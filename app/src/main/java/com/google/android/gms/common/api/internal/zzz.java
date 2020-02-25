package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;

/* compiled from: CompositeGoogleApiClient */
final class zzz implements zzcd {
    private final /* synthetic */ zzw zza;

    private zzz(zzw zzw) {
        this.zza = zzw;
    }

    /* synthetic */ zzz(zzw zzw, zzx zzx) {
        this(zzw);
    }

    public final void zza(@Nullable Bundle bundle) {
        this.zza.zzm.lock();
        try {
            ConnectionResult unused = this.zza.zzk = ConnectionResult.zza;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        this.zza.zzm.lock();
        try {
            ConnectionResult unused = this.zza.zzk = connectionResult;
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
            if (this.zza.zzl) {
                boolean unused = this.zza.zzl = false;
                this.zza.zza(i, z);
                return;
            }
            boolean unused2 = this.zza.zzl = true;
            this.zza.zzd.onConnectionSuspended(i);
            this.zza.zzm.unlock();
        } finally {
            this.zza.zzm.unlock();
        }
    }
}
