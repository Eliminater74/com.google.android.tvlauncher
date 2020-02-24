package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;

/* compiled from: DeferredLifecycleHelper */
final class zzc implements zzi {
    private final /* synthetic */ Activity zza;
    private final /* synthetic */ Bundle zzb;
    private final /* synthetic */ Bundle zzc;
    private final /* synthetic */ zza zzd;

    zzc(zza zza2, Activity activity, Bundle bundle, Bundle bundle2) {
        this.zzd = zza2;
        this.zza = activity;
        this.zzb = bundle;
        this.zzc = bundle2;
    }

    public final int zza() {
        return 0;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zzd.zza.onInflate(this.zza, this.zzb, this.zzc);
    }
}
