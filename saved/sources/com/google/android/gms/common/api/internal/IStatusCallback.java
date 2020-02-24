package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzfa;
import com.google.android.gms.internal.zzfb;

@Hide
public interface IStatusCallback extends IInterface {
    void onResult(Status status) throws RemoteException;

    public static abstract class zza extends zzfa implements IStatusCallback {
        public zza() {
            attachInterface(this, "com.google.android.gms.common.api.internal.IStatusCallback");
        }

        public static IStatusCallback zza(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.api.internal.IStatusCallback");
            if (queryLocalInterface instanceof IStatusCallback) {
                return (IStatusCallback) queryLocalInterface;
            }
            return new zzcb(iBinder);
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (zza(i, parcel, parcel2, i2)) {
                return true;
            }
            if (i != 1) {
                return false;
            }
            onResult((Status) zzfb.zza(parcel, Status.CREATOR));
            return true;
        }
    }
}
