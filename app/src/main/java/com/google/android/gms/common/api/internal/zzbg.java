package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* compiled from: GoogleApiClientImpl */
final class zzbg extends Handler {
    private final /* synthetic */ zzbb zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzbg(zzbb zzbb, Looper looper) {
        super(looper);
        this.zza = zzbb;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 1) {
            this.zza.zzk();
        } else if (i != 2) {
            int i2 = message.what;
            StringBuilder sb = new StringBuilder(31);
            sb.append("Unknown message id: ");
            sb.append(i2);
            Log.w("GoogleApiClientImpl", sb.toString());
        } else {
            this.zza.zzj();
        }
    }
}
