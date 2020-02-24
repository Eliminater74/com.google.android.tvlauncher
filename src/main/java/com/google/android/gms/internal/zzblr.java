package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: ISocketFactoryCreator */
public final class zzblr extends zzez implements zzblq {
    zzblr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.net.ISocketFactoryCreator");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, IObjectWrapper iObjectWrapper2, IObjectWrapper iObjectWrapper3, boolean z) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iObjectWrapper);
        zzfb.zza(a_, iObjectWrapper2);
        zzfb.zza(a_, iObjectWrapper3);
        zzfb.zza(a_, z);
        Parcel zza = zza(1, a_);
        IObjectWrapper zza2 = IObjectWrapper.zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }
}
