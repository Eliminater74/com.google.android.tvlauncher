package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;

import logs.proto.wireless.performance.mobile.BatteryMetric;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public final class PrimesBatteryConfigurations {
    /* access modifiers changed from: private */
    public static final BatteryMetricExtensionProvider DEFAULT_METRIC_EXTENSION_PROVIDER = new BatteryMetricExtensionProvider() {
        @Nullable
        public ExtensionMetric.MetricExtension getMetricExtension(@Nullable String customEventName, BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo) {
            return null;
        }
    };
    private final boolean deferredLogging;
    private final boolean enabled;
    private final BatteryMetricExtensionProvider metricExtensionProvider;

    public PrimesBatteryConfigurations(boolean enabled2) {
        this(enabled2, DEFAULT_METRIC_EXTENSION_PROVIDER);
    }

    @Deprecated
    public PrimesBatteryConfigurations(boolean enabled2, BatteryMetricExtensionProvider metricExtensionProvider2) {
        this(enabled2, false, metricExtensionProvider2);
    }

    private PrimesBatteryConfigurations(boolean enabled2, boolean deferredLogging2, BatteryMetricExtensionProvider metricExtensionProvider2) {
        this.enabled = enabled2;
        this.deferredLogging = deferredLogging2;
        this.metricExtensionProvider = metricExtensionProvider2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isDeferredLogging() {
        return this.deferredLogging;
    }

    public BatteryMetricExtensionProvider getMetricExtensionProvider() {
        return this.metricExtensionProvider;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        boolean unused2 = builder.deferredLogging = this.deferredLogging;
        BatteryMetricExtensionProvider unused3 = builder.metricExtensionProvider = this.metricExtensionProvider;
        return builder;
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public boolean deferredLogging;
        /* access modifiers changed from: private */
        public boolean enabled;
        /* access modifiers changed from: private */
        public BatteryMetricExtensionProvider metricExtensionProvider;

        private Builder() {
        }

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setDeferredLogging(boolean deferredLogging2) {
            this.deferredLogging = deferredLogging2;
            return this;
        }

        public Builder setMetricExtensionProvider(BatteryMetricExtensionProvider metricExtensionProvider2) {
            this.metricExtensionProvider = metricExtensionProvider2;
            return this;
        }

        public PrimesBatteryConfigurations build() {
            if (this.metricExtensionProvider == null) {
                this.metricExtensionProvider = PrimesBatteryConfigurations.DEFAULT_METRIC_EXTENSION_PROVIDER;
            }
            return new PrimesBatteryConfigurations(this.enabled, this.deferredLogging, this.metricExtensionProvider);
        }
    }
}
