package com.google.android.gms.clearcut.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.clearcut.CollectForDebugParcelable;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.internal.zzez;
import com.google.android.gms.internal.zzfb;

/* compiled from: IClearcutLoggerService */
public final class zzr extends zzez implements zzq {
    zzr(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.clearcut.internal.IClearcutLoggerService");
    }

    public final void zza(zzo zzo, LogEventParcelable logEventParcelable) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzfb.zza(a_, logEventParcelable);
        zzc(1, a_);
    }

    public final void zza(zzo zzo) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzc(2, a_);
    }

    public final void zzb(zzo zzo) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzc(3, a_);
    }

    public final void zza(zzo zzo, CollectForDebugParcelable collectForDebugParcelable) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzfb.zza(a_, collectForDebugParcelable);
        zzc(8, a_);
    }

    public final void zzc(zzo zzo) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzc(4, a_);
    }

    public final void zzd(zzo zzo) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzc(5, a_);
    }

    public final void zze(zzo zzo) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        zzc(9, a_);
    }

    public final void zza(zzo zzo, String str) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        a_.writeString(str);
        zzc(6, a_);
    }

    public final void zzb(zzo zzo, String str) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, zzo);
        a_.writeString(str);
        zzc(7, a_);
    }
}
