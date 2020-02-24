package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.libraries.gcoreclient.common.api.GcorePendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResultCallback;
import java.util.concurrent.TimeUnit;

public class GcorePendingResultImpl<GR extends GcoreResult, R extends Result> implements GcorePendingResult<GR> {
    private final PendingResult<R> pendingResult;
    private final ResultWrapper<GR, R> resultWrapper;

    public GcorePendingResultImpl(PendingResult<R> pendingResult2, ResultWrapper<GR, R> resultWrapper2) {
        this.pendingResult = pendingResult2;
        this.resultWrapper = resultWrapper2;
    }

    public GR await() {
        return this.resultWrapper.wrap(this.pendingResult.await());
    }

    public GR await(long time, TimeUnit units) {
        return this.resultWrapper.wrap(this.pendingResult.await(time, units));
    }

    public void cancel() {
        this.pendingResult.cancel();
    }

    public void setResultCallback(GcoreResultCallback<GR> callback) {
        this.pendingResult.setResultCallback(new GcoreResultCallbackDelegate(callback, this.resultWrapper));
    }

    public void setResultCallback(GcoreResultCallback<GR> callback, long time, TimeUnit units) {
        this.pendingResult.setResultCallback(new GcoreResultCallbackDelegate(callback, this.resultWrapper), time, units);
    }
}
