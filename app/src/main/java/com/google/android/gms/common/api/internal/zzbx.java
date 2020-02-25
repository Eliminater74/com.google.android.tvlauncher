package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;

/* compiled from: GoogleApiWrapper */
public final class zzbx<O extends Api.ApiOptions> extends zzal {
    private final GoogleApi<O> zza;

    public zzbx(GoogleApi<O> googleApi) {
        super("Method is not supported by connectionless client. APIs supporting connectionless client must not call this method.");
        this.zza = googleApi;
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(@NonNull T t) {
        return this.zza.zza((zzn) t);
    }

    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull T t) {
        return this.zza.zzb((zzn) t);
    }

    public final Looper zzc() {
        return this.zza.zzf();
    }

    public final void zza(zzdo zzdo) {
    }

    public final void zzb(zzdo zzdo) {
    }

    public final Context zzb() {
        return this.zza.zzg();
    }
}
