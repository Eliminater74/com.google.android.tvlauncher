package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;

/* compiled from: PendingResultFacade */
final class zzcs implements ResultCallback<A> {
    private final /* synthetic */ ResultCallback zza;
    private final /* synthetic */ zzcq zzb;

    zzcs(zzcq zzcq, ResultCallback resultCallback) {
        this.zzb = zzcq;
        this.zza = resultCallback;
    }

    public final void onResult(@NonNull A a) {
        this.zza.onResult(this.zzb.zza((Result) a));
    }
}
