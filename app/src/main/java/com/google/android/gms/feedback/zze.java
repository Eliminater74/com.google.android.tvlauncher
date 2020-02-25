package com.google.android.gms.feedback;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzckq;
import com.google.android.gms.internal.zzckt;

/* compiled from: Feedback */
final class zze extends Feedback.zza {
    private final /* synthetic */ Bundle zza;
    private final /* synthetic */ long zzb;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zze(GoogleApiClient googleApiClient, Bundle bundle, long j) {
        super(googleApiClient);
        this.zza = bundle;
        this.zzb = j;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ void zza(Api.zzb zzb2) throws RemoteException {
        zzckq zzckq = (zzckq) zzb2;
        try {
            ((zzckt) zzckq.zzag()).zza(this.zza, this.zzb);
            zza((Result) Status.zza);
        } catch (Exception e) {
            Log.e("gF_Feedback", "Requesting to save the async feedback psd failed!", e);
            zzd(Feedback.zza);
        }
    }
}
