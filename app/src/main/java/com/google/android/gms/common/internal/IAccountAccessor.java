package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzfa;

public interface IAccountAccessor extends IInterface {

    public static abstract class zza extends zzfa implements IAccountAccessor {
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            throw new NoSuchMethodError();
        }
    }

    Account getAccount() throws RemoteException;
}
