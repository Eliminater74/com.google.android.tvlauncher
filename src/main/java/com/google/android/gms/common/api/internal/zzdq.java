package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

/* compiled from: TransformedResultImpl */
final class zzdq extends Handler {
    private final /* synthetic */ zzdo zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public zzdq(zzdo zzdo, Looper looper) {
        super(looper);
        this.zza = zzdo;
    }

    public final void handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            PendingResult pendingResult = (PendingResult) message.obj;
            synchronized (this.zza.zze) {
                if (pendingResult == null) {
                    this.zza.zzb.zza(new Status(13, "Transform returned null"));
                } else if (pendingResult instanceof zzcz) {
                    this.zza.zzb.zza(((zzcz) pendingResult).zza());
                } else {
                    this.zza.zzb.zza(pendingResult);
                }
            }
        } else if (i != 1) {
            int i2 = message.what;
            StringBuilder sb = new StringBuilder(70);
            sb.append("TransformationResultHandler received unknown message type: ");
            sb.append(i2);
            Log.e("TransformedResultImpl", sb.toString());
        } else {
            RuntimeException runtimeException = (RuntimeException) message.obj;
            String valueOf = String.valueOf(runtimeException.getMessage());
            Log.e("TransformedResultImpl", valueOf.length() != 0 ? "Runtime exception on the transformation worker thread: ".concat(valueOf) : new String("Runtime exception on the transformation worker thread: "));
            throw runtimeException;
        }
    }
}
