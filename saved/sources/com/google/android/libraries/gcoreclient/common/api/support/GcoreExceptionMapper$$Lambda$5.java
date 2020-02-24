package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper;
import java.util.concurrent.ExecutionException;

final /* synthetic */ class GcoreExceptionMapper$$Lambda$5 implements GcoreExceptionMapper.SystemExceptionSupplier {
    static final GcoreExceptionMapper.SystemExceptionSupplier $instance = new GcoreExceptionMapper$$Lambda$5();

    private GcoreExceptionMapper$$Lambda$5() {
    }

    public Throwable newException(String str, Throwable th) {
        return new ExecutionException(str, th);
    }
}
