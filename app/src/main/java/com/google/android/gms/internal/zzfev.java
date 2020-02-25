package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.usagereporting.UsageReportingOptInOptions;

@Hide
/* compiled from: IUsageReportingCallbacks */
public interface zzfev extends IInterface {
    void zza(Status status) throws RemoteException;

    void zza(Status status, UsageReportingOptInOptions usageReportingOptInOptions) throws RemoteException;

    void zzb(Status status) throws RemoteException;

    void zzc(Status status) throws RemoteException;
}
