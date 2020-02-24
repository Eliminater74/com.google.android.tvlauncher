package com.google.android.gms.feedback;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.internal.zzan;
import com.google.android.gms.tasks.Task;

public class FeedbackClient extends GoogleApi<Api.ApiOptions.NoOptions> {
    public FeedbackClient(@NonNull Context context) {
        super(context, Feedback.API, (Api.ApiOptions) null, GoogleApi.zza.zza);
    }

    public Task<Void> startFeedback(@Nullable FeedbackOptions feedbackOptions) {
        return zzan.zza(Feedback.zza(asGoogleApiClient(), feedbackOptions));
    }

    public Task<Void> silentSendFeedback(@Nullable FeedbackOptions feedbackOptions) {
        return zzan.zza(Feedback.zzb(asGoogleApiClient(), feedbackOptions));
    }
}
