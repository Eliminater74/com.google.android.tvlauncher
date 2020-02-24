package com.google.android.libraries.performance.primes;

import com.google.android.libraries.stitch.util.Preconditions;

public final class PrimesTraceConfigurations {
    private static final int DEFAULT_MAX_TRACING_BUFFER_SIZE = 1000;
    private static final int DEFAULT_MIN_SPAN_DURATION_MS = 5;
    private static final float DEFAULT_TRACING_SAMPLING_PROPABILITY = 0.5f;
    private final boolean isEnabled;
    private final int maxTracingBufferSize;
    private final int minSpanDurationMs;
    private final float samplingPropability;

    private PrimesTraceConfigurations(boolean isEnabled2, float samplingPropability2, int minSpanDurationMs2, int maxTracingBufferSize2) {
        this.isEnabled = isEnabled2;
        this.samplingPropability = samplingPropability2;
        this.minSpanDurationMs = minSpanDurationMs2;
        this.maxTracingBufferSize = maxTracingBufferSize2;
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public float getSamplingPropability() {
        return this.samplingPropability;
    }

    public int getMinSpanDurationMs() {
        return this.minSpanDurationMs;
    }

    public int getMaxTracingBufferSize() {
        return this.maxTracingBufferSize;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.isEnabled = this.isEnabled;
        float unused2 = builder.samplingPropability = this.samplingPropability;
        int unused3 = builder.minSpanDurationMs = this.minSpanDurationMs;
        int unused4 = builder.maxTracingBufferSize = this.maxTracingBufferSize;
        return builder;
    }

    public static Builder newBuilder() {
        return new Builder().setSamplingPropability(DEFAULT_TRACING_SAMPLING_PROPABILITY).setSpanDurationThresholdMs(5).setMaxTracingBufferSize(1000);
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean isEnabled;
        /* access modifiers changed from: private */
        public int maxTracingBufferSize;
        /* access modifiers changed from: private */
        public int minSpanDurationMs;
        /* access modifiers changed from: private */
        public float samplingPropability;

        private Builder() {
        }

        public Builder setEnabled(boolean enabled) {
            this.isEnabled = enabled;
            return this;
        }

        public Builder setSamplingPropability(float samplingPropability2) {
            Preconditions.checkState(samplingPropability2 >= 0.0f && samplingPropability2 <= 1.0f, "Probability shall be between 0 and 1.");
            this.samplingPropability = samplingPropability2;
            return this;
        }

        public Builder setSpanDurationThresholdMs(int durationThresholdMs) {
            this.minSpanDurationMs = durationThresholdMs;
            return this;
        }

        public Builder setMaxTracingBufferSize(int maxTracingBufferSize2) {
            this.maxTracingBufferSize = maxTracingBufferSize2;
            return this;
        }

        public PrimesTraceConfigurations build() {
            return new PrimesTraceConfigurations(this.isEnabled, this.samplingPropability, this.minSpanDurationMs, this.maxTracingBufferSize);
        }
    }
}
