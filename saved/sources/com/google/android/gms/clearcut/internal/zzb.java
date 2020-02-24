package com.google.android.gms.clearcut.internal;

import android.content.Context;
import android.support.annotation.NonNull;
import com.google.android.gms.clearcut.ClearcutLogger;
import com.google.android.gms.clearcut.ClearcutLoggerApi;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.tasks.Tasks;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: ClearcutLoggerApiImpl */
public final class zzb extends GoogleApi<Api.ApiOptions.NoOptions> implements ClearcutLoggerApi {
    private zzb(@NonNull Context context) {
        super(context, ClearcutLogger.API, (Api.ApiOptions) null, new zzh());
    }

    public static ClearcutLoggerApi zza(@NonNull Context context) {
        return new zzb(context);
    }

    public final PendingResult<Status> forceUpload() {
        return zzb(new zze(asGoogleApiClient()));
    }

    @Deprecated
    public final PendingResult<Status> forceUpload(Object obj) {
        return forceUpload();
    }

    public final boolean flush(Object obj, long j, TimeUnit timeUnit) {
        try {
            Tasks.await(zza(new zzc(this)), j, timeUnit);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (ExecutionException | TimeoutException e2) {
            return false;
        }
    }

    @Deprecated
    public final void disconnectAsync(Object obj) {
    }

    public final PendingResult<Status> logEvent(LogEventParcelable logEventParcelable) {
        return zzc(new zzg(logEventParcelable, asGoogleApiClient()));
    }

    @Deprecated
    public final PendingResult<Status> logEventAsync(Object obj, LogEventParcelable logEventParcelable) {
        return logEvent(logEventParcelable);
    }

    @Deprecated
    public final PendingResult<Status> logEventAsync(Context context, LogEventParcelable logEventParcelable) {
        return logEvent(logEventParcelable);
    }

    @Deprecated
    public final PendingResult<Status> logEvent(Object obj, LogEventParcelable logEventParcelable) {
        return logEvent(logEventParcelable);
    }

    @Deprecated
    public final PendingResult<ClearcutLoggerApi.ExpiryTimeResult> startCollectForDebug() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final PendingResult<Status> stopCollectForDebug() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final PendingResult<ClearcutLoggerApi.ExpiryTimeResult> getCollectForDebugExpiryTime() {
        throw new UnsupportedOperationException();
    }
}
