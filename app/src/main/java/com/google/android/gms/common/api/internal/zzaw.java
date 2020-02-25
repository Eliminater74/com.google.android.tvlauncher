package com.google.android.gms.common.api.internal;

import android.support.annotation.BinderThread;

import com.google.android.gms.internal.zzelx;
import com.google.android.gms.internal.zzemf;

import java.lang.ref.WeakReference;

/* compiled from: GoogleApiClientConnecting */
final class zzaw extends zzelx {
    private final WeakReference<zzap> zza;

    zzaw(zzap zzap) {
        this.zza = new WeakReference<>(zzap);
    }

    @BinderThread
    public final void zza(zzemf zzemf) {
        zzap zzap = this.zza.get();
        if (zzap != null) {
            zzap.zza.zza(new zzax(this, zzap, zzap, zzemf));
        }
    }
}
