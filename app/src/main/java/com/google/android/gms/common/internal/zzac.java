package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzez;

/* compiled from: ICertData */
public final class zzac extends zzez implements zzaa {
    zzac(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ICertData");
    }

    public final IObjectWrapper zzb() throws RemoteException {
        Parcel zza = zza(1, mo17545a_());
        IObjectWrapper zza2 = IObjectWrapper.zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }

    public final int zzc() throws RemoteException {
        Parcel zza = zza(2, mo17545a_());
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }
}
