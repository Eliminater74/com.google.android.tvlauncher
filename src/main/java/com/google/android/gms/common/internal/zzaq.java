package com.google.android.gms.common.internal;

import com.google.android.gms.common.api.Response;
import com.google.android.gms.common.api.Result;

/* compiled from: PendingResultUtil */
final class zzaq implements zzas<R, T> {
    private final /* synthetic */ Response zza;

    zzaq(Response response) {
        this.zza = response;
    }

    public final /* synthetic */ Object zza(Result result) {
        this.zza.setResult(result);
        return this.zza;
    }
}
