package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.common.internal.IAccountAccessor;

/* compiled from: ISignInService */
public interface zzema extends IInterface {
    void zza(int i) throws RemoteException;

    void zza(IAccountAccessor iAccountAccessor, int i, boolean z) throws RemoteException;

    void zza(zzemd zzemd, zzely zzely) throws RemoteException;
}
