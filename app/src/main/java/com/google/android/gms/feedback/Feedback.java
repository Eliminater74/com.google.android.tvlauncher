package com.google.android.gms.feedback;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzckq;
import com.google.android.gms.internal.zzckr;
import com.google.android.gms.internal.zzcks;

import java.io.File;

public final class Feedback {
    public static final String ANONYMOUS = "anonymous";
    /* access modifiers changed from: private */
    public static final Status zza = new Status(13);
    private static final Api.ClientKey<zzckq> zzb = new Api.ClientKey<>();
    private static final Api.zza<zzckq, Api.ApiOptions.NoOptions> zzc = new zzb();
    public static final Api<Api.ApiOptions.NoOptions> API = new Api<>("Feedback.API", zzc, zzb);

    private Feedback() {
    }

    public static FeedbackClient getClient(@NonNull Context context) {
        return new FeedbackClient(context);
    }

    @Deprecated
    public static PendingResult<Status> zza(@NonNull GoogleApiClient googleApiClient, @Nullable FeedbackOptions feedbackOptions) {
        return googleApiClient.zza((zzn) new zzc(googleApiClient, feedbackOptions, googleApiClient.zzb(), System.nanoTime()));
    }

    /* access modifiers changed from: private */
    public static void zzb(@NonNull Context context, @NonNull BaseFeedbackProductSpecificData baseFeedbackProductSpecificData, @Nullable File file, long j) {
        zza(new zzckr(context, baseFeedbackProductSpecificData, file, j));
        zza(new zzcks(context, baseFeedbackProductSpecificData, j));
    }

    private static void zza(@NonNull Runnable runnable) {
        Thread thread = new Thread(runnable, "Feedback");
        thread.setPriority(4);
        thread.start();
    }

    @Deprecated
    public static PendingResult<Status> zzb(GoogleApiClient googleApiClient, @Nullable FeedbackOptions feedbackOptions) {
        return googleApiClient.zza((zzn) new zzd(googleApiClient, feedbackOptions));
    }

    @Hide
    public static PendingResult<Status> zza(GoogleApiClient googleApiClient, Bundle bundle, long j) {
        return googleApiClient.zza((zzn) new zze(googleApiClient, bundle, j));
    }

    @Hide
    public static PendingResult<Status> zza(GoogleApiClient googleApiClient, FeedbackOptions feedbackOptions, Bundle bundle, long j) {
        return googleApiClient.zza((zzn) new zzf(googleApiClient, feedbackOptions, bundle, j));
    }

    @VisibleForTesting
    static abstract class zza extends zzn<Status, zzckq> {
        public zza(GoogleApiClient googleApiClient) {
            super(Feedback.API, googleApiClient);
        }

        @Hide
        public final /* bridge */ /* synthetic */ void zza(Object obj) {
            super.zza((Result) ((Status) obj));
        }

        public final /* synthetic */ Result zza(Status status) {
            if (status == null) {
                return Status.zzc;
            }
            return status;
        }
    }
}
