package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzo;

/* compiled from: CommonApiImpl */
final class zzblh extends zzblb {
    private final zzo<Status> zza;

    public zzblh(zzo<Status> zzo) {
        this.zza = zzo;
    }

    public final void zza(int i) throws RemoteException {
        this.zza.zza(new Status(i));
    }
}
