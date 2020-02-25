package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: ISignInButtonCreator */
public final class zzai extends zzez implements zzah {
    zzai(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, zzaz zzaz) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iObjectWrapper);
        zzfb.zza(a_, zzaz);
        Parcel zza = zza(2, a_);
        IObjectWrapper zza2 = IObjectWrapper.zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }
}
