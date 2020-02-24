package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: DataHolderResult */
public class zzak implements Releasable, Result {
    protected final DataHolder zza;
    private final Status zzb;

    protected zzak(DataHolder dataHolder, Status status) {
        this.zzb = status;
        this.zza = dataHolder;
    }

    public Status getStatus() {
        return this.zzb;
    }

    public void release() {
        DataHolder dataHolder = this.zza;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
