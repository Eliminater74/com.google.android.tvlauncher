package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: ICommonService */
public final class zzblo extends zzez implements zzbln {
    zzblo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.service.ICommonService");
    }

    public final void zza(zzbll zzbll) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzbll);
        zzc(1, a_);
    }
}
