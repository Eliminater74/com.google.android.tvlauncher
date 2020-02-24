package com.google.android.gms.common.api.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IStatusCallback */
public final class zzcb extends zzez implements IStatusCallback {
    zzcb(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.api.internal.IStatusCallback");
    }

    public final void onResult(Status status) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, status);
        zzc(1, a_);
    }
}
