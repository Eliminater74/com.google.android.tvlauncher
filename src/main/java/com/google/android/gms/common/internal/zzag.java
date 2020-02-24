package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.zzl;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IGoogleCertificatesApi */
public final class zzag extends zzez implements zzae {
    zzag(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    public final boolean zza(zzl zzl, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzl);
        zzfb.zza(a_, iObjectWrapper);
        Parcel zza = zza(5, a_);
        boolean zza2 = zzfb.zza(zza);
        zza.recycle();
        return zza2;
    }
}
