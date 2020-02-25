package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IAccountAccessor */
public final class zzw extends zzez implements IAccountAccessor {
    zzw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
    }

    public final Account getAccount() throws RemoteException {
        Parcel zza = zza(2, mo17545a_());
        Account account = (Account) zzfb.zza(zza, Account.CREATOR);
        zza.recycle();
        return account;
    }
}
