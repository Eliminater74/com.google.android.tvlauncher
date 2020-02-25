package com.google.android.gms.clearcut.internal;

import android.os.IInterface;
import android.os.RemoteException;

import com.google.android.gms.clearcut.CollectForDebugParcelable;
import com.google.android.gms.clearcut.LogEventParcelable;

/* compiled from: IClearcutLoggerService */
public interface zzq extends IInterface {
    void zza(zzo zzo) throws RemoteException;

    void zza(zzo zzo, CollectForDebugParcelable collectForDebugParcelable) throws RemoteException;

    void zza(zzo zzo, LogEventParcelable logEventParcelable) throws RemoteException;

    void zza(zzo zzo, String str) throws RemoteException;

    void zzb(zzo zzo) throws RemoteException;

    void zzb(zzo zzo, String str) throws RemoteException;

    void zzc(zzo zzo) throws RemoteException;

    void zzd(zzo zzo) throws RemoteException;

    void zze(zzo zzo) throws RemoteException;
}
