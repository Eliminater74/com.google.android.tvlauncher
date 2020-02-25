package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.feedback.BaseFeedbackProductSpecificData;
import com.google.android.gms.feedback.Feedback;
import com.google.android.gms.feedback.FeedbackOptions;
import com.google.android.gms.feedback.FileTeleporter;

import java.io.File;
import java.util.List;

/* compiled from: GetAsyncFeedbackPsbdRunnable */
public final class zzckr implements Runnable {
    private final Context zza;
    private final BaseFeedbackProductSpecificData zzb;
    private final File zzc;
    private final long zzd;

    public zzckr(@NonNull Context context, @NonNull BaseFeedbackProductSpecificData baseFeedbackProductSpecificData, @Nullable File file, long j) {
        this.zza = context;
        this.zzb = baseFeedbackProductSpecificData;
        this.zzc = file;
        this.zzd = j;
    }

    public final void run() {
        List<FileTeleporter> list;
        Bundle bundle = new Bundle(1);
        try {
            zzckw zzckw = new zzckw();
            zzckw.zza();
            list = this.zzb.getAsyncFeedbackPsbd();
            if (!(list == null || list.isEmpty() || this.zzc == null)) {
                zzckq.zza(list, this.zzc);
            }
            bundle.putString(BaseFeedbackProductSpecificData.KEY_ASYNC_FEEDBACK_PSBD_COLLECTION_TIME_MS, String.valueOf(zzckw.zzb()));
        } catch (Exception e) {
            Log.w("gF_GetAsyncFeedbackPsbd", "Failed to get async Feedback psbd.", e);
            list = null;
            bundle.putString(BaseFeedbackProductSpecificData.KEY_ASYNC_FEEDBACK_PSBD_FAILURE, BaseFeedbackProductSpecificData.FEEDBACK_PSD_FAILURE_VALUE_EXCEPTION);
        }
        long j = this.zzd;
        zzan.zza(Feedback.zza(Feedback.getClient(this.zza).asGoogleApiClient(), FeedbackOptions.zza(list), bundle, j));
    }
}
