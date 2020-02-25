package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;

import com.google.android.libraries.performance.primes.transmitter.StackTraceTransmitter;

public class PrimesCrashConfigurations {
    static final int MAX_STARTUP_SAMPLING = 100;
    private final boolean deferredInitLogging;
    private final boolean enabled;
    @Nullable
    private final CrashMetricExtensionProvider metricExtensionProvider;
    private final boolean sendStackTraces;
    private final StackTraceTransmitter stackTraceTransmitter;
    private final float startupSamplePercentage;

    @Deprecated
    public PrimesCrashConfigurations() {
        this(false);
    }

    @Deprecated
    public PrimesCrashConfigurations(boolean enabled2, @Nullable CrashMetricExtensionProvider metricExtensionProvider2) {
        this(enabled2, 100.0f, metricExtensionProvider2, StackTraceTransmitter.NOOP_TRANSMITTER, false, false);
    }

    @Deprecated
    public PrimesCrashConfigurations(boolean enabled2) {
        this(enabled2, (CrashMetricExtensionProvider) null);
    }

    @Deprecated
    public PrimesCrashConfigurations(boolean enabled2, float startupSamplePercentage2) {
        this(enabled2, startupSamplePercentage2, null, StackTraceTransmitter.NOOP_TRANSMITTER, false, false);
    }

    @Deprecated
    public PrimesCrashConfigurations(boolean enabled2, StackTraceTransmitter stackTraceTransmitter2, boolean sendStackTraces2) {
        this(enabled2, null, stackTraceTransmitter2, sendStackTraces2);
    }

    @Deprecated
    public PrimesCrashConfigurations(boolean enabled2, @Nullable CrashMetricExtensionProvider metricExtensionProvider2, StackTraceTransmitter stackTraceTransmitter2, boolean sendStackTraces2) {
        this(enabled2, 100.0f, metricExtensionProvider2, stackTraceTransmitter2, sendStackTraces2, false);
    }

    private PrimesCrashConfigurations(boolean enabled2, float startupSamplePercentage2, @Nullable CrashMetricExtensionProvider metricExtensionProvider2, StackTraceTransmitter stackTraceTransmitter2, boolean sendStackTraces2, boolean deferredInitLogging2) {
        this.enabled = enabled2;
        this.startupSamplePercentage = startupSamplePercentage2;
        this.sendStackTraces = sendStackTraces2;
        if (!sendStackTraces2) {
            this.stackTraceTransmitter = StackTraceTransmitter.NOOP_TRANSMITTER;
        } else {
            this.stackTraceTransmitter = stackTraceTransmitter2;
        }
        this.metricExtensionProvider = metricExtensionProvider2;
        this.deferredInitLogging = deferredInitLogging2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    @Deprecated
    public int getSampleRatePerSecond() {
        return Integer.MAX_VALUE;
    }

    public float getStartupSamplePercentage() {
        return this.startupSamplePercentage;
    }

    /* access modifiers changed from: package-private */
    public StackTraceTransmitter getStackTraceTransmitter() {
        return this.stackTraceTransmitter;
    }

    /* access modifiers changed from: package-private */
    public boolean isSendStackTraces() {
        return this.sendStackTraces;
    }

    /* access modifiers changed from: package-private */
    public boolean isDeferredInitLogging() {
        return this.deferredInitLogging;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public CrashMetricExtensionProvider getMetricExtensionProvider() {
        return this.metricExtensionProvider;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        float unused2 = builder.startupSamplePercentage = this.startupSamplePercentage;
        boolean unused3 = builder.sendStackTraces = this.sendStackTraces;
        StackTraceTransmitter unused4 = builder.stackTraceTransmitter = this.stackTraceTransmitter;
        CrashMetricExtensionProvider unused5 = builder.metricExtensionProvider = this.metricExtensionProvider;
        boolean unused6 = builder.deferredInitLogging = this.deferredInitLogging;
        return builder;
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean deferredInitLogging;
        /* access modifiers changed from: private */
        public boolean enabled;
        /* access modifiers changed from: private */
        @Nullable
        public CrashMetricExtensionProvider metricExtensionProvider;
        /* access modifiers changed from: private */
        public boolean sendStackTraces;
        /* access modifiers changed from: private */
        public StackTraceTransmitter stackTraceTransmitter = StackTraceTransmitter.NOOP_TRANSMITTER;
        /* access modifiers changed from: private */
        public float startupSamplePercentage = 100.0f;

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setStartupSamplePercentage(float startupSamplePercentage2) {
            this.startupSamplePercentage = startupSamplePercentage2;
            return this;
        }

        public Builder setDeferredInitLogging(boolean deferredInitLogging2) {
            this.deferredInitLogging = deferredInitLogging2;
            return this;
        }

        public Builder setSendStackTraces(boolean sendStackTraces2) {
            this.sendStackTraces = sendStackTraces2;
            return this;
        }

        public Builder setStackTraceTransmitter(StackTraceTransmitter stackTraceTransmitter2) {
            this.stackTraceTransmitter = stackTraceTransmitter2;
            return this;
        }

        public Builder setCrashMetricExtensionProvider(CrashMetricExtensionProvider metricExtensionProvider2) {
            this.metricExtensionProvider = metricExtensionProvider2;
            return this;
        }

        public PrimesCrashConfigurations build() {
            return new PrimesCrashConfigurations(this.enabled, this.startupSamplePercentage, this.metricExtensionProvider, this.stackTraceTransmitter, this.sendStackTraces, this.deferredInitLogging);
        }
    }
}
