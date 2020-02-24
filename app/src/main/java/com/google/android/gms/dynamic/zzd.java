package com.google.android.gms.dynamic;

import android.os.Bundle;

/* compiled from: DeferredLifecycleHelper */
final class zzd implements zzi {
    private final /* synthetic */ Bundle zza;
    private final /* synthetic */ zza zzb;

    zzd(zza zza2, Bundle bundle) {
        this.zzb = zza2;
        this.zza = bundle;
    }

    public final int zza() {
        return 1;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zzb.zza.onCreate(this.zza);
    }
}
