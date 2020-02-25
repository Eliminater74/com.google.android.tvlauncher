package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

/* compiled from: ISignInCallbacks */
public interface zzely extends IInterface {
    void zza(ConnectionResult connectionResult, zzelv zzelv) throws RemoteException;

    void zza(Status status) throws RemoteException;

    void zza(Status status, GoogleSignInAccount googleSignInAccount) throws RemoteException;

    void zza(zzemf zzemf) throws RemoteException;

    void zzb(Status status) throws RemoteException;
}
