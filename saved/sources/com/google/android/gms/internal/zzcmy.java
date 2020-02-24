package com.google.android.gms.internal;

import android.os.RemoteException;

/* compiled from: Flag */
public final class zzcmy extends zzcmt<String> {
    public zzcmy(int i, String str, String str2) {
        super(0, str, str2);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final String zza(zzcnb zzcnb) {
        try {
            return zzcnb.getStringFlagValue(zza(), (String) zzb(), zzc());
        } catch (RemoteException e) {
            return (String) zzb();
        }
    }
}
