package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: IAdvertisingIdListener */
public abstract class zzgf extends zzfa implements zzge {
    public zzgf() {
        attachInterface(this, "com.google.android.gms.ads.identifier.internal.IAdvertisingIdListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((Bundle) zzfb.zza(parcel, Bundle.CREATOR));
        return true;
    }
}
