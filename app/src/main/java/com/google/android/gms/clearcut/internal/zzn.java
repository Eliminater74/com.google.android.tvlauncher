package com.google.android.gms.clearcut.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IBootCountService */
public final class zzn extends zzez implements zzm {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.clearcut.internal.IBootCountService");
    }

    public final void zza(zzk zzk) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzk);
        zzc(1, a_);
    }
}
