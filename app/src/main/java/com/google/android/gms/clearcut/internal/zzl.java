package com.google.android.gms.clearcut.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzfa;
import com.google.android.gms.internal.zzfb;

/* compiled from: IBootCountCallbacks */
public abstract class zzl extends zzfa implements zzk {
    public zzl() {
        attachInterface(this, "com.google.android.gms.clearcut.internal.IBootCountCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i != 1) {
            return false;
        }
        zza((Status) zzfb.zza(parcel, Status.CREATOR), parcel.readInt());
        return true;
    }
}
