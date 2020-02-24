package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper;

final /* synthetic */ class GcoreExceptionMapper$$Lambda$3 implements GcoreExceptionMapper.SystemExceptionSupplier {
    static final GcoreExceptionMapper.SystemExceptionSupplier $instance = new GcoreExceptionMapper$$Lambda$3();

    private GcoreExceptionMapper$$Lambda$3() {
    }

    public Throwable newException(String str, Throwable th) {
        return new GcoreExceptionMapper.UnrecognizedException(str, th);
    }
}
