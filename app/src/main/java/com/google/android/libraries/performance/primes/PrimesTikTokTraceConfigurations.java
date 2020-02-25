package com.google.android.libraries.performance.primes;

import android.util.SparseArray;

import com.google.apps.tiktok.tracing.SpanExtras;
import com.google.apps.tiktok.tracing.TraceRecord;
import com.google.apps.tiktok.tracing.TraceSampler;
import com.google.common.base.Optional;

public final class PrimesTikTokTraceConfigurations {
    private static final boolean DEFAULT_RECORD_TIMER_DURATION = true;
    private static final int DEFAULT_TRACING_SAMPLING_RATE_PER_SECOND = 10;
    private final DynamicSampler dynamicSampler;
    private final boolean isEnabled;
    private final boolean recordTimerDuration;
    private final int sampleRatePerSecond;

    private PrimesTikTokTraceConfigurations(boolean isEnabled2, int sampleRatePerSecond2, boolean recordTimerDuration2, DynamicSampler dynamicSampler2) {
        this.isEnabled = isEnabled2;
        this.sampleRatePerSecond = sampleRatePerSecond2;
        this.recordTimerDuration = recordTimerDuration2;
        this.dynamicSampler = dynamicSampler2;
    }

    @Deprecated
    public static Builder builder() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return new Builder().setSampleRatePerSecond(10);
    }

    public boolean isEnabled() {
        return this.isEnabled;
    }

    public int getSampleRatePerSecond() {
        return this.sampleRatePerSecond;
    }

    public DynamicSampler getDynamicSampler() {
        return this.dynamicSampler;
    }

    public boolean shouldRecordTimerDuration() {
        return this.recordTimerDuration;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.isEnabled = this.isEnabled;
        int unused2 = builder.sampleRatePerSecond = this.sampleRatePerSecond;
        boolean unused3 = builder.recordTimerDuration = this.recordTimerDuration;
        DynamicSampler unused4 = builder.dynamicSampler = this.dynamicSampler;
        return builder;
    }

    public interface DynamicSampler {
        Optional<Float> getSamplingRateIfConstant();

        Optional<Float> shouldSample(SparseArray<SpanExtras> sparseArray, TraceRecord traceRecord, TraceSampler traceSampler);
    }

    public static final class UniformSampler implements DynamicSampler {
        final float probability;

        public UniformSampler(float probability2) {
            this.probability = probability2;
        }

        public Optional<Float> shouldSample(SparseArray<SpanExtras> sparseArray, TraceRecord trace, TraceSampler sampler) {
            if (sampler.shouldSampleAtProbability(trace, this.probability)) {
                return Optional.m80of(Float.valueOf(this.probability));
            }
            return Optional.absent();
        }

        public Optional<Float> getSamplingRateIfConstant() {
            return Optional.m80of(Float.valueOf(this.probability));
        }
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public DynamicSampler dynamicSampler;
        /* access modifiers changed from: private */
        public boolean isEnabled;
        /* access modifiers changed from: private */
        public boolean recordTimerDuration;
        /* access modifiers changed from: private */
        public int sampleRatePerSecond;

        private Builder() {
            this.recordTimerDuration = true;
            this.dynamicSampler = new UniformSampler(1.0f);
        }

        public Builder setEnabled(boolean enabled) {
            this.isEnabled = enabled;
            return this;
        }

        public Builder setSampleRatePerSecond(int sampleRatePerSecond2) {
            this.sampleRatePerSecond = sampleRatePerSecond2;
            return this;
        }

        public Builder setSamplingProbability(float samplingProbability) {
            setDynamicSampler(new UniformSampler(samplingProbability));
            return this;
        }

        public Builder setRecordTimerDuration(boolean recordTimerDuration2) {
            this.recordTimerDuration = recordTimerDuration2;
            return this;
        }

        public Builder setDynamicSampler(DynamicSampler dynamicSampler2) {
            this.dynamicSampler = dynamicSampler2;
            return this;
        }

        public PrimesTikTokTraceConfigurations build() {
            return new PrimesTikTokTraceConfigurations(this.isEnabled, this.sampleRatePerSecond, this.recordTimerDuration, this.dynamicSampler);
        }
    }
}
