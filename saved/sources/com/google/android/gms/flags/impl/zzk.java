package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.concurrent.Callable;

/* compiled from: SharedPreferencesFactory */
final class zzk implements Callable<SharedPreferences> {
    private final /* synthetic */ Context zza;

    zzk(Context context) {
        this.zza = context;
    }

    public final /* synthetic */ Object call() throws Exception {
        return this.zza.getSharedPreferences("google_sdk_flags", 0);
    }
}
