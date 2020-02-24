package com.google.android.gms.clearcut.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.clearcut.CollectForDebugParcelable;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;

/* compiled from: IClearcutLoggerCallbacks */
public interface zzo extends IInterface {
    void zza(Status status) throws RemoteException;

    void zza(Status status, long j) throws RemoteException;

    void zza(Status status, CollectForDebugParcelable collectForDebugParcelable) throws RemoteException;

    void zza(Status status, LogEventParcelable[] logEventParcelableArr) throws RemoteException;

    void zza(DataHolder dataHolder) throws RemoteException;

    void zzb(Status status) throws RemoteException;

    void zzb(Status status, long j) throws RemoteException;

    void zzb(Status status, CollectForDebugParcelable collectForDebugParcelable) throws RemoteException;

    void zzc(Status status) throws RemoteException;
}
