package com.google.android.libraries.gcoreclient.common.api.support;

final /* synthetic */ class GcoreExceptionMapper$$Lambda$7 implements GcoreExceptionMapper.SystemExceptionSupplier {
    static final GcoreExceptionMapper.SystemExceptionSupplier $instance = new GcoreExceptionMapper$$Lambda$7();

    private GcoreExceptionMapper$$Lambda$7() {
    }

    public Throwable newException(String str, Throwable th) {
        return new IllegalArgumentException(str, th);
    }
}
