package com.google.android.libraries.gcoreclient.common.api.support;

final /* synthetic */ class GcoreExceptionMapper$$Lambda$4 implements GcoreExceptionMapper.SystemExceptionSupplier {
    static final GcoreExceptionMapper.SystemExceptionSupplier $instance = new GcoreExceptionMapper$$Lambda$4();

    private GcoreExceptionMapper$$Lambda$4() {
    }

    public Throwable newException(String str, Throwable th) {
        return new GcoreExceptionMapper.UnrecognizedThrowable(str, th);
    }
}
