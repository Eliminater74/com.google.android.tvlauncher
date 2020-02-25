package com.google.android.libraries.gcoreclient.common.api;

import java.util.concurrent.TimeUnit;

public interface GcorePendingResult<GR extends GcoreResult> {
    GR await();

    GR await(long j, TimeUnit timeUnit);

    void cancel();

    void setResultCallback(GcoreResultCallback<GR> gcoreResultCallback);

    void setResultCallback(GcoreResultCallback<GR> gcoreResultCallback, long j, TimeUnit timeUnit);
}
