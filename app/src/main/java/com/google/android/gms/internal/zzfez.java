package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

@Hide
/* compiled from: IUsageReportingService */
public interface zzfez extends IInterface {
    void zza(zzfev zzfev) throws RemoteException;

    void zza(zzfex zzfex, zzfev zzfev) throws RemoteException;

    void zza(UsageReportingOptInOptions usageReportingOptInOptions, zzfev zzfev) throws RemoteException;

    void zzb(zzfex zzfex, zzfev zzfev) throws RemoteException;
}
