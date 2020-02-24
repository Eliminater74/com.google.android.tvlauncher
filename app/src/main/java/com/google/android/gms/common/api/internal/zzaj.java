package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.data.DataHolder;

/* compiled from: DataHolderNotifier */
public abstract class zzaj<L> implements zzcm<L> {
    private final DataHolder zza;

    protected zzaj(DataHolder dataHolder) {
        this.zza = dataHolder;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(L l, DataHolder dataHolder);

    public final void zza(L l) {
        zza(l, this.zza);
    }

    public final void zza() {
        DataHolder dataHolder = this.zza;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
