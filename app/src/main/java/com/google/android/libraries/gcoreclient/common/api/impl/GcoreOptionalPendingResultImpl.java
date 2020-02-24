package com.google.android.libraries.gcoreclient.common.api.impl;

import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.libraries.gcoreclient.common.api.GcoreOptionalPendingResult;
import com.google.android.libraries.gcoreclient.common.api.GcoreResult;
import com.google.android.libraries.gcoreclient.common.api.support.GcorePendingResultImpl;
import com.google.android.libraries.gcoreclient.common.api.support.ResultWrapper;

public class GcoreOptionalPendingResultImpl<GR extends GcoreResult, R extends Result> extends GcorePendingResultImpl<GR, R> implements GcoreOptionalPendingResult<GR> {
    private final OptionalPendingResult<R> optionalPendingResult;
    private final ResultWrapper<GR, R> resultWrapper;

    public GcoreOptionalPendingResultImpl(OptionalPendingResult<R> optionalPendingResult2, ResultWrapper<GR, R> resultWrapper2) {
        super(optionalPendingResult2, resultWrapper2);
        this.optionalPendingResult = optionalPendingResult2;
        this.resultWrapper = resultWrapper2;
    }

    public boolean isDone() {
        return this.optionalPendingResult.isDone();
    }

    public GR get() {
        return this.resultWrapper.wrap(this.optionalPendingResult.get());
    }
}
