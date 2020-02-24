package com.google.apps.tiktok.tracing;

import javax.annotation.CheckReturnValue;

public final class SpanExtraKey<T> {
    private SpanExtraKey() {
    }

    @CheckReturnValue
    /* renamed from: of */
    public static <T> SpanExtraKey<T> m67of(Class<T> cls) {
        return new SpanExtraKey<>();
    }
}
