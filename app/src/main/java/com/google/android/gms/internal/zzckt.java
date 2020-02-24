package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.feedback.ErrorReport;
import com.google.android.gms.feedback.FeedbackOptions;

/* compiled from: IFeedbackService */
public interface zzckt extends IInterface {
    void zza(Bundle bundle, long j) throws RemoteException;

    void zza(ErrorReport errorReport, long j) throws RemoteException;

    void zza(FeedbackOptions feedbackOptions, Bundle bundle, long j) throws RemoteException;

    boolean zza(ErrorReport errorReport) throws RemoteException;

    boolean zzb(ErrorReport errorReport) throws RemoteException;
}
