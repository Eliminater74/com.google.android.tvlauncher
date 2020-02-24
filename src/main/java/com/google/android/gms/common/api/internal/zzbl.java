package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* compiled from: GoogleApiClientStateHolder */
final class zzbl extends Handler {
    private final /* synthetic */ zzbj zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbl(zzbj zzbj, Looper looper) {
        super(looper);
        this.zza = zzbj;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            ((zzbk) message.obj).zza(this.zza);
        } else if (i != 2) {
            int i2 = message.what;
            StringBuilder sb = new StringBuilder(31);
            sb.append("Unknown message id: ");
            sb.append(i2);
            Log.w("GACStateManager", sb.toString());
        } else {
            throw ((RuntimeException) message.obj);
        }
    }
}
