package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;

import java.util.concurrent.Callable;

/* compiled from: DataUtils */
final class zzg implements Callable<Long> {
    private final /* synthetic */ SharedPreferences zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Long zzc;

    zzg(SharedPreferences sharedPreferences, String str, Long l) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = l;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Long.valueOf(this.zza.getLong(this.zzb, this.zzc.longValue()));
    }
}
