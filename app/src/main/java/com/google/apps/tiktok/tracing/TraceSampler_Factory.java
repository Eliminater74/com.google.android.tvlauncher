package com.google.apps.tiktok.tracing;

import dagger.internal.Factory;

public final class TraceSampler_Factory implements Factory<TraceSampler> {
    private static final TraceSampler_Factory INSTANCE = new TraceSampler_Factory();

    public static TraceSampler_Factory create() {
        return INSTANCE;
    }

    public static TraceSampler newInstance() {
        return new TraceSampler();
    }

    public TraceSampler get() {
        return new TraceSampler();
    }
}
