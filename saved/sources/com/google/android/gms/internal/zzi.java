package com.google.android.gms.internal;

import android.os.Handler;
import java.util.concurrent.Executor;

/* compiled from: ExecutorDelivery */
public final class zzi implements zzaa {
    private final Executor zza;

    public zzi(Handler handler) {
        this.zza = new zzj(this, handler);
    }

    public final void zza(zzr<?> zzr, zzx<?> zzx) {
        zza(zzr, zzx, null);
    }

    public final void zza(zzr<?> zzr, zzx<?> zzx, Runnable runnable) {
        zzr.zzk();
        zzr.zza("post-response");
        this.zza.execute(new zzk(this, zzr, zzx, runnable));
    }

    public final void zza(zzr<?> zzr, zzae zzae) {
        zzr.zza("post-error");
        this.zza.execute(new zzk(this, zzr, zzx.zza(zzae), null));
    }
}
