package com.google.android.gms.common.api.internal;

/* compiled from: GoogleApiClientStateHolder */
abstract class zzbk {
    private final zzbi zza;

    protected zzbk(zzbi zzbi) {
        this.zza = zzbi;
    }

    /* access modifiers changed from: protected */
    public abstract void zza();

    public final void zza(zzbj zzbj) {
        zzbj.zzf.lock();
        try {
            if (zzbj.zzn == this.zza) {
                zza();
                zzbj.zzf.unlock();
            }
        } finally {
            zzbj.zzf.unlock();
        }
    }
}
