package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzau;

/* compiled from: ListenerHolder */
final class zzck extends Handler {
    private final /* synthetic */ zzcj zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzck(zzcj zzcj, Looper looper) {
        super(looper);
        this.zza = zzcj;
    }

    public final void handleMessage(Message message) {
        boolean z = true;
        if (message.what != 1) {
            z = false;
        }
        zzau.zzb(z);
        this.zza.zzb((zzcm) message.obj);
    }
}
