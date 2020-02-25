package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

/* compiled from: ISignInCallbacks */
public abstract class zzelz extends zzfa implements zzely {
    public zzelz() {
        attachInterface(this, "com.google.android.gms.signin.internal.ISignInCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        if (i == 3) {
            zza((ConnectionResult) zzfb.zza(parcel, ConnectionResult.CREATOR), (zzelv) zzfb.zza(parcel, zzelv.CREATOR));
        } else if (i == 4) {
            zza((Status) zzfb.zza(parcel, Status.CREATOR));
        } else if (i == 6) {
            zzb((Status) zzfb.zza(parcel, Status.CREATOR));
        } else if (i == 7) {
            zza((Status) zzfb.zza(parcel, Status.CREATOR), (GoogleSignInAccount) zzfb.zza(parcel, GoogleSignInAccount.CREATOR));
        } else if (i != 8) {
            return false;
        } else {
            zza((zzemf) zzfb.zza(parcel, zzemf.CREATOR));
        }
        parcel2.writeNoException();
        return true;
    }
}
