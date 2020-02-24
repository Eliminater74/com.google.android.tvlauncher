package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* compiled from: DataUtils */
final class zzc implements Callable<Boolean> {
    private final /* synthetic */ SharedPreferences zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ Boolean zzc;

    zzc(SharedPreferences sharedPreferences, String str, Boolean bool) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = bool;
    }

    public final /* synthetic */ Object call() throws Exception {
        return Boolean.valueOf(this.zza.getBoolean(this.zzb, this.zzc.booleanValue()));
    }
}
