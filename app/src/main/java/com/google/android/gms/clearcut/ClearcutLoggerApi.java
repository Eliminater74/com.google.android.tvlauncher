package com.google.android.gms.clearcut;

import android.content.Context;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

import java.util.concurrent.TimeUnit;

public interface ClearcutLoggerApi {

    @Deprecated
    void disconnectAsync(Object obj);

    boolean flush(Object obj, long j, TimeUnit timeUnit);

    @Deprecated
    PendingResult<Status> forceUpload();

    @Deprecated
    PendingResult<Status> forceUpload(Object obj);

    @Deprecated
    PendingResult<ExpiryTimeResult> getCollectForDebugExpiryTime();

    PendingResult<Status> logEvent(LogEventParcelable logEventParcelable);

    @Deprecated
    PendingResult<Status> logEvent(Object obj, LogEventParcelable logEventParcelable);

    @Deprecated
    PendingResult<Status> logEventAsync(Context context, LogEventParcelable logEventParcelable);

    @Deprecated
    PendingResult<Status> logEventAsync(Object obj, LogEventParcelable logEventParcelable);

    @Deprecated
    PendingResult<ExpiryTimeResult> startCollectForDebug();

    @Deprecated
    PendingResult<Status> stopCollectForDebug();

    @Deprecated
    public interface ExpiryTimeResult extends Result {
        long getExpiryTimeMillis();
    }
}
