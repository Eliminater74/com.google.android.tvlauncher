package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.IStatusCallback;

/* compiled from: StatusCallback */
public final class zzdf extends IStatusCallback.zza {
    private final zzo<Status> zza;

    public zzdf(zzo<Status> zzo) {
        this.zza = zzo;
    }

    public final void onResult(Status status) {
        this.zza.zza(status);
    }
}
