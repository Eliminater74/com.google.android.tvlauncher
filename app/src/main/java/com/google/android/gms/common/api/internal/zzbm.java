package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzbmg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: GoogleApiExecutor */
public final class zzbm {
    private static final ExecutorService zza = Executors.newFixedThreadPool(2, new zzbmg("GAC_Executor"));

    public static ExecutorService zza() {
        return zza;
    }
}
