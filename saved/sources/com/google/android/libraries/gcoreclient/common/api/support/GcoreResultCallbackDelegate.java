package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResultCallback;

public class GcoreResultCallbackDelegate<GR extends GcoreResult, R extends Result> implements ResultCallback<R> {
    private final GcoreResultCallback<GR> callback;
    private final ResultWrapper<GR, R> resultWrapper;

    public GcoreResultCallbackDelegate(GcoreResultCallback<GR> callback2, ResultWrapper<GR, R> resultWrapper2) {
        this.callback = callback2;
        this.resultWrapper = resultWrapper2;
    }

    public void onResult(R result) {
        this.callback.onResult(this.resultWrapper.wrap(result));
    }
}
