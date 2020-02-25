package com.google.apps.tiktok.tracing;

import java.util.UUID;

import javax.inject.Inject;

public final class TraceSampler {
    @Inject
    TraceSampler() {
    }

    public static TraceSampler getDefaultInstance() {
        return new TraceSampler();
    }

    public boolean shouldSampleAtProbability(TraceRecord traceRecord, float probability) {
        return shouldSampleAtProbability(traceRecord.getUuidLeastSignificantBits(), probability);
    }

    public boolean shouldSampleAtProbability(UUID traceId, float probability) {
        return shouldSampleAtProbability(traceId.getLeastSignificantBits(), probability);
    }

    private boolean shouldSampleAtProbability(long uuidLeastSignificantBits, float probability) {
        return (((int) uuidLeastSignificantBits) & 1073741823) < ((int) (1.07374182E9f * probability));
    }
}
