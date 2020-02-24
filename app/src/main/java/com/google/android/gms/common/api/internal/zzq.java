package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzau;

/* compiled from: BaseLifecycleHelper */
final class zzq {
    private final int zza;
    private final ConnectionResult zzb;

    zzq(ConnectionResult connectionResult, int i) {
        zzau.zza(connectionResult);
        this.zzb = connectionResult;
        this.zza = i;
    }

    /* access modifiers changed from: package-private */
    public final int zza() {
        return this.zza;
    }

    /* access modifiers changed from: package-private */
    public final ConnectionResult zzb() {
        return this.zzb;
    }
}
