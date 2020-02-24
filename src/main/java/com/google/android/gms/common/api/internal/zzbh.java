package com.google.android.gms.common.api.internal;

import java.lang.ref.WeakReference;

/* compiled from: GoogleApiClientImpl */
final class zzbh extends zzbz {
    private WeakReference<zzbb> zza;

    zzbh(zzbb zzbb) {
        this.zza = new WeakReference<>(zzbb);
    }

    public final void zza() {
        zzbb zzbb = this.zza.get();
        if (zzbb != null) {
            zzbb.zzj();
        }
    }
}
