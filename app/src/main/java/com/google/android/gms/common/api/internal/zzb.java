package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.util.zzp;

/* compiled from: ApiCallRunner */
public abstract class zzb {
    private final int zza;

    public zzb(int i) {
        this.zza = i;
    }

    /* access modifiers changed from: private */
    public static Status zzb(RemoteException remoteException) {
        StringBuilder sb = new StringBuilder();
        if (zzp.zza() && (remoteException instanceof TransactionTooLargeException)) {
            sb.append("TransactionTooLargeException: ");
        }
        sb.append(remoteException.getLocalizedMessage());
        return new Status(8, sb.toString());
    }

    public abstract void zza(@NonNull Status status);

    public abstract void zza(@NonNull zzaf zzaf, boolean z);

    public abstract void zza(zzbp<?> zzbp) throws DeadObjectException;
}
