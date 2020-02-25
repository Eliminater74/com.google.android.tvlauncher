package com.google.android.gms.common.api.internal;

import com.google.android.gms.internal.zzbmg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: ResultTransformExecutor */
public final class zzcy {
    private static final ExecutorService zza = new ThreadPoolExecutor(0, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue(), new zzbmg("GAC_Transform"));

    public static ExecutorService zza() {
        return zza;
    }
}
