package com.google.android.gms.clearcut.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: ClearcutLoggerApiImpl */
final class zzf extends zzd {
    private final /* synthetic */ zze zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzf(zze zze) {
        super(null);
        this.zza = zze;
    }

    public final void zzb(Status status) {
        this.zza.zza((Result) status);
    }
}
