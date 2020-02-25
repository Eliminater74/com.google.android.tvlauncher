package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IDynamiteLoaderV2 */
public final class zzn extends zzez implements zzm {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iObjectWrapper);
        a_.writeString(str);
        a_.writeInt(i);
        zzfb.zza(a_, iObjectWrapper2);
        Parcel zza = zza(2, a_);
        IObjectWrapper zza2 = IObjectWrapper.zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }
}
