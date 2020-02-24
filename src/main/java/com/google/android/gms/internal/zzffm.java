package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzo;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: UsageReportingClientImpl */
final class zzffm extends zzffi {
    private final zzfez zza;
    private final zzo<Status> zzb;
    private final AtomicReference<zzffn> zzc;
    private final zzffn zzd;

    public zzffm(zzfez zzfez, zzo<Status> zzo, AtomicReference<zzffn> atomicReference, zzffn zzffn) {
        super();
        this.zza = zzfez;
        this.zzb = zzo;
        this.zzc = atomicReference;
        this.zzd = zzffn;
    }

    public final void zzc(Status status) throws RemoteException {
        this.zzc.set(null);
        if (!status.isSuccess()) {
            this.zzb.zza(status);
            return;
        }
        zzffn zzffn = this.zzd;
        if (zzffn == null) {
            this.zzb.zza(Status.zza);
            return;
        }
        this.zzc.set(zzffn);
        this.zza.zza(this.zzd, this);
    }

    public final void zzb(Status status) {
        if (!status.isSuccess()) {
            this.zzc.set(null);
            this.zzb.zza(status);
            return;
        }
        this.zzb.zza(Status.zza);
    }
}
