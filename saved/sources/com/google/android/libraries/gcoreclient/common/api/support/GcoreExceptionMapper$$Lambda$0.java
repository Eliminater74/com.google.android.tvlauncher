package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper;

final /* synthetic */ class GcoreExceptionMapper$$Lambda$0 implements GcoreExceptionMapper.ExceptionSupplier {
    private final GcoreExceptionMapper.SystemExceptionSupplier arg$1;

    GcoreExceptionMapper$$Lambda$0(GcoreExceptionMapper.SystemExceptionSupplier systemExceptionSupplier) {
        this.arg$1 = systemExceptionSupplier;
    }

    public Throwable newException(Throwable th, Throwable th2) {
        return this.arg$1.newException(th.getMessage(), th2);
    }
}
