package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IUsageReportingOptInOptionsChangedListener */
public abstract class zzfey extends zzfa implements zzfex {
    public zzfey() {
        attachInterface(this, "com.google.android.gms.usagereporting.internal.IUsageReportingOptInOptionsChangedListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 2) {
            return false;
        }
        zza();
        return true;
    }
}
