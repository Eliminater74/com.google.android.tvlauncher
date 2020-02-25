package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.internal.zzfa;
import com.google.android.gms.internal.zzfb;

public interface IGmsCallbacks extends IInterface {
    void onAccountValidationComplete(int i, Bundle bundle) throws RemoteException;

    void onPostInitComplete(int i, IBinder iBinder, Bundle bundle) throws RemoteException;

    public static abstract class zza extends zzfa implements IGmsCallbacks {
        public zza() {
            attachInterface(this, "com.google.android.gms.common.internal.IGmsCallbacks");
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (zza(i, parcel, parcel2, i2)) {
                return true;
            }
            if (i == 1) {
                onPostInitComplete(parcel.readInt(), parcel.readStrongBinder(), (Bundle) zzfb.zza(parcel, Bundle.CREATOR));
            } else if (i != 2) {
                return false;
            } else {
                onAccountValidationComplete(parcel.readInt(), (Bundle) zzfb.zza(parcel, Bundle.CREATOR));
            }
            parcel2.writeNoException();
            return true;
        }
    }
}
