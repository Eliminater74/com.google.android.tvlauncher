package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.common.base.Optional;

public class PrimesTimerConfigurations {
    static final float DEFAULT_SAMPLING_PROBABILITY = 1.0f;
    static final int DEFAULT_TIMER_SAMPLING_RATE_PER_SECOND = 10;
    private final boolean enabled;
    private final Optional<PrimesPerEventConfigurationFlags> perEventConfigFlags;
    private final int sampleRatePerSecond;
    private final float samplingProbability;

    @Deprecated
    public PrimesTimerConfigurations() {
        this(false);
    }

    @Deprecated
    public PrimesTimerConfigurations(boolean enabled2) {
        this(enabled2, 10);
    }

    @Deprecated
    public PrimesTimerConfigurations(boolean enabled2, int sampleRatePerSecond2) {
        this(enabled2, sampleRatePerSecond2, null);
    }

    @Deprecated
    public PrimesTimerConfigurations(boolean enabled2, int sampleRatePerSecond2, @Nullable PrimesPerEventConfigurationFlags perEventConfigFlags2) {
        this(enabled2, DEFAULT_SAMPLING_PROBABILITY, sampleRatePerSecond2, Optional.fromNullable(perEventConfigFlags2));
    }

    private PrimesTimerConfigurations(boolean enabled2, float samplingProbability2, int sampleRatePerSecond2, Optional<PrimesPerEventConfigurationFlags> perEventConfigFlags2) {
        this.enabled = enabled2;
        this.samplingProbability = samplingProbability2;
        this.sampleRatePerSecond = sampleRatePerSecond2;
        this.perEventConfigFlags = perEventConfigFlags2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public float getSamplingProbability() {
        return this.samplingProbability;
    }

    public int getSampleRatePerSecond() {
        return this.sampleRatePerSecond;
    }

    public Optional<PrimesPerEventConfigurationFlags> getPerEventConfigFlags() {
        return this.perEventConfigFlags;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.isEnabled = this.enabled;
        int unused2 = builder.sampleRatePerSecond = this.sampleRatePerSecond;
        float unused3 = builder.samplingProbability = this.samplingProbability;
        Optional unused4 = builder.perEventConfigFlags = this.perEventConfigFlags;
        return builder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean isEnabled;
        /* access modifiers changed from: private */
        public Optional<PrimesPerEventConfigurationFlags> perEventConfigFlags;
        /* access modifiers changed from: private */
        public int sampleRatePerSecond;
        /* access modifiers changed from: private */
        public float samplingProbability;

        private Builder() {
            this.sampleRatePerSecond = 10;
            this.samplingProbability = PrimesTimerConfigurations.DEFAULT_SAMPLING_PROBABILITY;
            this.perEventConfigFlags = Optional.absent();
        }

        public Builder setEnabled(boolean enabled) {
            this.isEnabled = enabled;
            return this;
        }

        public Builder setSamplingProbability(float samplingProbability2) {
            Preconditions.checkState(samplingProbability2 > 0.0f && samplingProbability2 <= PrimesTimerConfigurations.DEFAULT_SAMPLING_PROBABILITY, "Sampling Probability shall be > 0 and <= 1");
            this.samplingProbability = samplingProbability2;
            return this;
        }

        public Builder setSamplesPerSecond(int samplesPerSecond) {
            Preconditions.checkState(samplesPerSecond >= 0, "Samples per second shall be >= 0");
            this.sampleRatePerSecond = samplesPerSecond;
            return this;
        }

        public Builder setPerEventConfiguration(PrimesPerEventConfigurationFlags perEventConfigFlags2) {
            this.perEventConfigFlags = Optional.m80of(perEventConfigFlags2);
            return this;
        }

        public PrimesTimerConfigurations build() {
            return new PrimesTimerConfigurations(this.isEnabled, this.samplingProbability, this.sampleRatePerSecond, this.perEventConfigFlags);
        }
    }
}
