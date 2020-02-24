package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.IAccountAccessor;

/* compiled from: ISignInService */
public final class zzemb extends zzez implements zzema {
    zzemb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zza(int i) throws RemoteException {
        Parcel a_ = mo17545a_();
        a_.writeInt(i);
        zzb(7, a_);
    }

    public final void zza(IAccountAccessor iAccountAccessor, int i, boolean z) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, iAccountAccessor);
        a_.writeInt(i);
        zzfb.zza(a_, z);
        zzb(9, a_);
    }

    public final void zza(zzemd zzemd, zzely zzely) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzemd);
        zzfb.zza(a_, zzely);
        zzb(12, a_);
    }
}
