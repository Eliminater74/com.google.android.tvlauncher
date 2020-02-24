package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: ICommonCallbacks */
public abstract class zzblm extends zzfa implements zzbll {
    public zzblm() {
        attachInterface(this, "com.google.android.gms.common.internal.service.ICommonCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza(parcel.readInt());
        parcel2.writeNoException();
        return true;
    }
}
