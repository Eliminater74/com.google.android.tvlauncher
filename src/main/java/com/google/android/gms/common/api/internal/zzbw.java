package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.ConnectionResult;
import java.util.Collections;

/* compiled from: GoogleApiManager */
final class zzbw implements Runnable {
    private final /* synthetic */ ConnectionResult zza;
    private final /* synthetic */ zzbv zzb;

    zzbw(zzbv zzbv, ConnectionResult connectionResult) {
        this.zzb = zzbv;
        this.zza = connectionResult;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.internal.zzbv.zza(com.google.android.gms.common.api.internal.zzbv, boolean):boolean
     arg types: [com.google.android.gms.common.api.internal.zzbv, int]
     candidates:
      com.google.android.gms.common.api.internal.zzbv.zza(com.google.android.gms.common.internal.IAccountAccessor, java.util.Set<com.google.android.gms.common.api.Scope>):void
      com.google.android.gms.common.api.internal.zzde.zza(com.google.android.gms.common.internal.IAccountAccessor, java.util.Set<com.google.android.gms.common.api.Scope>):void
      com.google.android.gms.common.api.internal.zzbv.zza(com.google.android.gms.common.api.internal.zzbv, boolean):boolean */
    public final void run() {
        if (this.zza.isSuccess()) {
            boolean unused = this.zzb.zzf = true;
            if (this.zzb.zzb.requiresSignIn()) {
                this.zzb.zza();
            } else {
                this.zzb.zzb.getRemoteService(null, Collections.emptySet());
            }
        } else {
            ((zzbp) this.zzb.zza.zzm.get(this.zzb.zzc)).onConnectionFailed(this.zza);
        }
    }
}
