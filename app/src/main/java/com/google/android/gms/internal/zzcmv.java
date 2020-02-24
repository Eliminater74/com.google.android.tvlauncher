package com.google.android.gms.internal;

import android.os.RemoteException;

/* compiled from: Flag */
public final class zzcmv extends zzcmt<Boolean> {
    public zzcmv(int i, String str, Boolean bool) {
        super(0, str, bool);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final Boolean zza(zzcnb zzcnb) {
        try {
            return Boolean.valueOf(zzcnb.getBooleanFlagValue(zza(), ((Boolean) zzb()).booleanValue(), zzc()));
        } catch (RemoteException e) {
            return (Boolean) zzb();
        }
    }
}
