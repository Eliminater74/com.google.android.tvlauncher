package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper;

final /* synthetic */ class GcoreExceptionMapper$$Lambda$6 implements GcoreExceptionMapper.SystemExceptionSupplier {
    static final GcoreExceptionMapper.SystemExceptionSupplier $instance = new GcoreExceptionMapper$$Lambda$6();

    private GcoreExceptionMapper$$Lambda$6() {
    }

    public Throwable newException(String str, Throwable th) {
        return new IllegalStateException(str, th);
    }
}
