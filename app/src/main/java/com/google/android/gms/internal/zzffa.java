package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

/* compiled from: IUsageReportingService */
public final class zzffa extends zzez implements zzfez {
    zzffa(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.usagereporting.internal.IUsageReportingService");
    }

    public final void zza(zzfev zzfev) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzfev);
        zzb(2, a_);
    }

    public final void zza(UsageReportingOptInOptions usageReportingOptInOptions, zzfev zzfev) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, usageReportingOptInOptions);
        zzfb.zza(a_, zzfev);
        zzb(3, a_);
    }

    public final void zza(zzfex zzfex, zzfev zzfev) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzfex);
        zzfb.zza(a_, zzfev);
        zzb(4, a_);
    }

    public final void zzb(zzfex zzfex, zzfev zzfev) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzfex);
        zzfb.zza(a_, zzfev);
        zzb(5, a_);
    }
}
