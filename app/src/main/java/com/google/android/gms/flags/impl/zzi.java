package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* compiled from: DataUtils */
final class zzi implements Callable<String> {
    private final /* synthetic */ SharedPreferences zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;

    zzi(SharedPreferences sharedPreferences, String str, String str2) {
        this.zza = sharedPreferences;
        this.zzb = str;
        this.zzc = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        return this.zza.getString(this.zzb, this.zzc);
    }
}
