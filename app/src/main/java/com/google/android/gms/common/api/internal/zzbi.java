package com.google.android.gms.common.api.internal;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;

/* compiled from: GoogleApiClientState */
public interface zzbi {
    <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(zzn zzn);

    void zza();

    void zza(int i);

    void zza(Bundle bundle);

    void zza(ConnectionResult connectionResult, Api<?> api, boolean z);

    <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(T t);

    boolean zzb();

    void zzc();
}
