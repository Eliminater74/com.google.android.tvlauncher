package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IDynamiteLoader */
public final class zzl extends zzez implements zzk {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iObjectWrapper);
        a_.writeString(str);
        a_.writeInt(i);
        Parcel zza = zza(2, a_);
        IObjectWrapper zza2 = IObjectWrapper.zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }

    public final int zza(IObjectWrapper iObjectWrapper, String str, boolean z) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iObjectWrapper);
        a_.writeString(str);
        zzfb.zza(a_, z);
        Parcel zza = zza(3, a_);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }
}
