package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.feedback.ErrorReport;
import com.google.android.gms.feedback.FeedbackOptions;

/* compiled from: IFeedbackService */
public final class zzcku extends zzez implements zzckt {
    zzcku(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.feedback.internal.IFeedbackService");
    }

    public final boolean zza(ErrorReport errorReport) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, errorReport);
        Parcel zza = zza(1, a_);
        boolean zza2 = zzfb.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final boolean zzb(ErrorReport errorReport) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, errorReport);
        Parcel zza = zza(3, a_);
        boolean zza2 = zzfb.zza(zza);
        zza.recycle();
        return zza2;
    }

    public final void zza(Bundle bundle, long j) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, bundle);
        a_.writeLong(j);
        zzb(4, a_);
    }

    public final void zza(FeedbackOptions feedbackOptions, Bundle bundle, long j) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, feedbackOptions);
        zzfb.zza(a_, bundle);
        a_.writeLong(j);
        zzb(5, a_);
    }

    public final void zza(ErrorReport errorReport, long j) throws RemoteException {
        Parcel a_ = mo17545a_();
        zzfb.zza(a_, errorReport);
        a_.writeLong(j);
        zzc(6, a_);
    }
}
