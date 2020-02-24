package com.google.android.gms.clearcut.internal;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

/* compiled from: ClearcutLoggerApiImpl */
final class zzh extends zzd {
    private final /* synthetic */ zzg zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzh(zzg zzg) {
        super(null);
        this.zza = zzg;
    }

    public final void zza(Status status) {
        this.zza.zza((Result) status);
    }
}
