package com.google.android.gms.feedback;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.feedback.Feedback;
import com.google.android.gms.internal.zzckq;

/* compiled from: Feedback */
final class zzd extends Feedback.zza {
    private final /* synthetic */ FeedbackOptions zza;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzd(GoogleApiClient googleApiClient, FeedbackOptions feedbackOptions) {
        super(googleApiClient);
        this.zza = feedbackOptions;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb) throws RemoteException {
        ((zzckq) zzb).zzb(this.zza);
        zza((Result) Status.zza);
    }
}
