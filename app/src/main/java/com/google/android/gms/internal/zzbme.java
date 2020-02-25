package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/* compiled from: HandlerExecutor */
public final class zzbme implements Executor {
    private final Handler zza;

    public zzbme(Looper looper) {
        this.zza = new Handler(looper);
    }

    public final void execute(@NonNull Runnable runnable) {
        this.zza.post(runnable);
    }
}
