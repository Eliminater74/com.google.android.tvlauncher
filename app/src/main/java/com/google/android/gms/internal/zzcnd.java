package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;

/* compiled from: IFlagProvider */
public final class zzcnd extends zzez implements zzcnb {
    zzcnd(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.flags.IFlagProvider");
    }

    public final void init(IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iObjectWrapper);
        zzb(1, a_);
    }

    public final boolean getBooleanFlagValue(String str, boolean z, int i) throws RemoteException {
        Parcel a_ = mo17545a_();
        a_.writeString(str);
        zzfb.zza(a_, z);
        a_.writeInt(i);
        Parcel zza = zza(2, a_);
        boolean zza2 = zzfb.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final int getIntFlagValue(String str, int i, int i2) throws RemoteException {
        Parcel a_ = mo17545a_();
        a_.writeString(str);
        a_.writeInt(i);
        a_.writeInt(i2);
        Parcel zza = zza(3, a_);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final long getLongFlagValue(String str, long j, int i) throws RemoteException {
        Parcel a_ = mo17545a_();
        a_.writeString(str);
        a_.writeLong(j);
        a_.writeInt(i);
        Parcel zza = zza(4, a_);
        long readLong = zza.readLong();
        zza.recycle();
        return readLong;
    }

    public final String getStringFlagValue(String str, String str2, int i) throws RemoteException {
        Parcel a_ = mo17545a_();
        a_.writeString(str);
        a_.writeString(str2);
        a_.writeInt(i);
        Parcel zza = zza(5, a_);
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }
}
