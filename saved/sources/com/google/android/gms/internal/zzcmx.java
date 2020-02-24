package com.google.android.gms.internal;

import android.os.RemoteException;

/* compiled from: Flag */
public final class zzcmx extends zzcmt<Long> {
    public zzcmx(int i, String str, Long l) {
        super(0, str, l);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Long zza(zzcnb zzcnb) {
        try {
            return Long.valueOf(zzcnb.getLongFlagValue(zza(), ((Long) zzb()).longValue(), zzc()));
        } catch (RemoteException e) {
            return (Long) zzb();
        }
    }
}
