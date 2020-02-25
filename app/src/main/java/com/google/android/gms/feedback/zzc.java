package com.google.android.gms.feedback;

import android.content.Context;
import android.os.RemoteException;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzckq;

/* compiled from: Feedback */
final class zzc extends Feedback.zza {
    private final /* synthetic */ FeedbackOptions zza;
    private final /* synthetic */ Context zzb;
    private final /* synthetic */ long zzd;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzc(GoogleApiClient googleApiClient, FeedbackOptions feedbackOptions, Context context, long j) {
        super(googleApiClient);
        this.zza = feedbackOptions;
        this.zzb = context;
        this.zzd = j;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        BaseFeedbackProductSpecificData zzo;
        zzckq zzckq = (zzckq) zzb2;
        FeedbackOptions feedbackOptions = this.zza;
        if (feedbackOptions == null || (zzo = feedbackOptions.zzo()) == null) {
            zzckq.zza(this.zza);
            zza((Result) Status.zza);
            return;
        }
        Feedback.zzb(this.zzb, zzo, this.zzb.getCacheDir(), this.zzd);
        zzckq.zza(this.zza, this.zzd);
        zza((Result) Status.zza);
    }
}
