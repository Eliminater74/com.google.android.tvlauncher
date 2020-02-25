package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.feedback.BaseFeedbackProductSpecificData;
import com.google.android.gms.feedback.Feedback;
import com.google.android.gms.feedback.FeedbackClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: GetAsyncFeedbackPsdRunnable */
public final class zzcks implements Runnable {
    private final Context zza;
    private final BaseFeedbackProductSpecificData zzb;
    private final long zzc;

    public zzcks(@NonNull Context context, @NonNull BaseFeedbackProductSpecificData baseFeedbackProductSpecificData, long j) {
        this.zza = context;
        this.zzb = baseFeedbackProductSpecificData;
        this.zzc = j;
    }

    public final void run() {
        List list;
        try {
            zzckw zzckw = new zzckw();
            zzckw.zza();
            list = this.zzb.getAsyncFeedbackPsd();
            if (list == null) {
                list = new ArrayList(1);
            }
            try {
                list.add(Pair.create(BaseFeedbackProductSpecificData.KEY_ASYNC_FEEDBACK_PSD_COLLECTION_TIME_MS, String.valueOf(zzckw.zzb())));
            } catch (UnsupportedOperationException e) {
                ArrayList arrayList = new ArrayList(list);
                arrayList.add(Pair.create(BaseFeedbackProductSpecificData.KEY_ASYNC_FEEDBACK_PSD_COLLECTION_TIME_MS, String.valueOf(zzckw.zzb())));
                list = arrayList;
            }
        } catch (Exception e2) {
            Log.w("gF_GetAsyncFeedbackPsd", "Failed to get async Feedback psd.", e2);
            list = Collections.singletonList(Pair.create(BaseFeedbackProductSpecificData.KEY_ASYNC_FEEDBACK_PSD_FAILURE, BaseFeedbackProductSpecificData.FEEDBACK_PSD_FAILURE_VALUE_EXCEPTION));
        }
        FeedbackClient client = Feedback.getClient(this.zza);
        zzan.zza(Feedback.zza(client.asGoogleApiClient(), zzckv.zza(list), this.zzc));
    }
}
