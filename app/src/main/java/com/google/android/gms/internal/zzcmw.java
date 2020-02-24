package com.google.android.gms.internal;

import android.os.RemoteException;

/* compiled from: Flag */
public final class zzcmw extends zzcmt<Integer> {
    public zzcmw(int i, String str, Integer num) {
        super(0, str, num);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Integer zza(zzcnb zzcnb) {
        try {
            return Integer.valueOf(zzcnb.getIntFlagValue(zza(), ((Integer) zzb()).intValue(), zzc()));
        } catch (RemoteException e) {
            return (Integer) zzb();
        }
    }
}
