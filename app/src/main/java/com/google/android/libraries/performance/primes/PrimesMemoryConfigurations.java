package com.google.android.libraries.performance.primes;

import android.support.annotation.VisibleForTesting;

import com.google.common.base.Optional;

public class PrimesMemoryConfigurations {
    private static final int DEFAULT_MEMORY_SAMPLING_RATE_PER_SECOND = 3;
    private final boolean enabled;
    private final boolean forceGcBeforeRecordMemory;
    private final Optional<MemoryMetricExtensionProvider> metricExtensionProvider;
    private final boolean recordMetricPerProcess;
    private final int sampleRatePerSecond;

    @Deprecated
    public PrimesMemoryConfigurations() {
        this(false);
    }

    @Deprecated
    public PrimesMemoryConfigurations(boolean enabled2) {
        this(enabled2, 3);
    }

    @Deprecated
    public PrimesMemoryConfigurations(boolean enabled2, int sampleRatePerSecond2) {
        this(enabled2, sampleRatePerSecond2, false);
    }

    @Deprecated
    public PrimesMemoryConfigurations(boolean enabled2, int sampleRatePerSecond2, boolean recordMetricPerProcess2) {
        this(enabled2, sampleRatePerSecond2, recordMetricPerProcess2, Optional.absent(), false);
    }

    @Deprecated
    public PrimesMemoryConfigurations(boolean enabled2, int sampleRatePerSecond2, boolean recordMetricPerProcess2, MemoryMetricExtensionProvider metricExtensionProvider2) {
        this(enabled2, sampleRatePerSecond2, recordMetricPerProcess2, Optional.m80of(metricExtensionProvider2), false);
    }

    PrimesMemoryConfigurations(boolean enabled2, int sampleRatePerSecond2, boolean recordMetricPerProcess2, Optional<MemoryMetricExtensionProvider> metricExtensionProvider2, boolean forceGcBeforeRecordMemory2) {
        this.enabled = enabled2;
        this.sampleRatePerSecond = sampleRatePerSecond2;
        this.recordMetricPerProcess = recordMetricPerProcess2;
        this.metricExtensionProvider = metricExtensionProvider2;
        this.forceGcBeforeRecordMemory = forceGcBeforeRecordMemory2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getSampleRatePerSecond() {
        return this.sampleRatePerSecond;
    }

    public boolean recordMetricPerProcess() {
        return this.recordMetricPerProcess;
    }

    public Optional<MemoryMetricExtensionProvider> getMetricExtensionProvider() {
        return this.metricExtensionProvider;
    }

    public boolean getForceGcBeforeRecordMemory() {
        return this.forceGcBeforeRecordMemory;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        int unused2 = builder.sampleRatePerSecond = this.sampleRatePerSecond;
        boolean unused3 = builder.recordMetricPerProcess = this.recordMetricPerProcess;
        Optional unused4 = builder.metricExtensionProvider = this.metricExtensionProvider;
        boolean unused5 = builder.forceGcBeforeRecordMemory = this.forceGcBeforeRecordMemory;
        return builder;
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean enabled;
        /* access modifiers changed from: private */
        public boolean forceGcBeforeRecordMemory;
        /* access modifiers changed from: private */
        public Optional<MemoryMetricExtensionProvider> metricExtensionProvider;
        /* access modifiers changed from: private */
        public boolean recordMetricPerProcess;
        /* access modifiers changed from: private */
        public int sampleRatePerSecond;

        private Builder() {
            this.sampleRatePerSecond = 3;
            this.metricExtensionProvider = Optional.absent();
        }

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setSampleRatePerSecond(int sampleRatePerSecond2) {
            this.sampleRatePerSecond = sampleRatePerSecond2;
            return this;
        }

        public Builder setRecordMetricPerProcess(boolean recordMetricPerProcess2) {
            this.recordMetricPerProcess = recordMetricPerProcess2;
            return this;
        }

        public Builder setMetricExtensionProvider(MemoryMetricExtensionProvider metricExtensionProvider2) {
            this.metricExtensionProvider = Optional.m80of(metricExtensionProvider2);
            return this;
        }

        @VisibleForTesting
        public Builder setForceGcBeforeRecordMemory(boolean forceGcBeforeRecordMemory2) {
            this.forceGcBeforeRecordMemory = forceGcBeforeRecordMemory2;
            return this;
        }

        public PrimesMemoryConfigurations build() {
            return new PrimesMemoryConfigurations(this.enabled, this.sampleRatePerSecond, this.recordMetricPerProcess, this.metricExtensionProvider, this.forceGcBeforeRecordMemory);
        }
    }
}
