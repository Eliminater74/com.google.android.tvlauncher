package com.google.android.libraries.performance.primes;

import android.os.Build;
import android.support.annotation.Nullable;
import com.google.android.libraries.stitch.util.Preconditions;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public class PrimesJankConfigurations {
    /* access modifiers changed from: private */
    public static final JankMetricExtensionProvider DEFAULT_METRIC_EXTENSION_PROVIDER = new JankMetricExtensionProvider() {
        @Nullable
        public ExtensionMetric.MetricExtension getMetricExtension() {
            return null;
        }
    };
    private final boolean enabled;
    private final JankMetricExtensionProvider metricExtensionProvider;
    private final boolean monitorActivities;
    private final int sampleRatePerSecond;
    private final boolean useAnimator;

    private PrimesJankConfigurations(boolean enabled2, boolean monitorActivities2, boolean useAnimator2, int sampleRatePerSecond2, JankMetricExtensionProvider metricExtensionProvider2) {
        this.enabled = enabled2;
        this.monitorActivities = monitorActivities2;
        this.useAnimator = useAnimator2;
        this.sampleRatePerSecond = sampleRatePerSecond2;
        this.metricExtensionProvider = metricExtensionProvider2;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        boolean unused2 = builder.monitorActivities = this.monitorActivities;
        boolean unused3 = builder.useAnimator = this.useAnimator;
        int unused4 = builder.sampleRatePerSecond = this.sampleRatePerSecond;
        JankMetricExtensionProvider unused5 = builder.metricExtensionProvider = this.metricExtensionProvider;
        return builder;
    }

    @Deprecated
    public static Builder builder() {
        return newBuilder();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @Deprecated
    public PrimesJankConfigurations(boolean enabled2) {
        this(enabled2, false, Builder.DEFAULT_JANK_USE_ANIMATOR, 10, DEFAULT_METRIC_EXTENSION_PROVIDER);
    }

    @Deprecated
    public PrimesJankConfigurations(boolean enabled2, boolean useAnimator2) {
        this(enabled2, false, useAnimator2, 10, DEFAULT_METRIC_EXTENSION_PROVIDER);
    }

    @Deprecated
    public PrimesJankConfigurations(boolean enabled2, int sampleRatePerSecond2) {
        this(enabled2, false, Builder.DEFAULT_JANK_USE_ANIMATOR, sampleRatePerSecond2, DEFAULT_METRIC_EXTENSION_PROVIDER);
    }

    @Deprecated
    public PrimesJankConfigurations(boolean enabled2, int sampleRatePerSecond2, boolean useAnimator2) {
        this(enabled2, false, useAnimator2, sampleRatePerSecond2, DEFAULT_METRIC_EXTENSION_PROVIDER);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isMonitorActivities() {
        return this.monitorActivities;
    }

    /* access modifiers changed from: package-private */
    public boolean isUseAnimator() {
        return this.useAnimator;
    }

    public int getSampleRatePerSecond() {
        return this.sampleRatePerSecond;
    }

    public JankMetricExtensionProvider getMetricExtensionProvider() {
        return this.metricExtensionProvider;
    }

    public static class Builder {
        private static final boolean DEFAULT_JANK_MONITOR_ACTIVITIES = false;
        private static final int DEFAULT_JANK_SAMPLING_RATE_PER_SECOND = 10;
        /* access modifiers changed from: private */
        public static final boolean DEFAULT_JANK_USE_ANIMATOR = (Build.VERSION.SDK_INT < 26);
        private static final boolean DISABLED_BY_DEFAULT = false;
        /* access modifiers changed from: private */
        public boolean enabled = false;
        /* access modifiers changed from: private */
        public JankMetricExtensionProvider metricExtensionProvider = PrimesJankConfigurations.DEFAULT_METRIC_EXTENSION_PROVIDER;
        /* access modifiers changed from: private */
        public boolean monitorActivities = false;
        /* access modifiers changed from: private */
        public int sampleRatePerSecond = 10;
        /* access modifiers changed from: private */
        public boolean useAnimator = DEFAULT_JANK_USE_ANIMATOR;

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setMonitorActivities(boolean monitorActivities2) {
            this.monitorActivities = monitorActivities2;
            return this;
        }

        public Builder setUseAnimator(boolean useAnimator2) {
            this.useAnimator = useAnimator2;
            return this;
        }

        public Builder setSampleRatePerSecond(int sampleRatePerSecond2) {
            this.sampleRatePerSecond = sampleRatePerSecond2;
            return this;
        }

        public Builder setMetricExtensionProvider(JankMetricExtensionProvider metricExtensionProvider2) {
            this.metricExtensionProvider = (JankMetricExtensionProvider) Preconditions.checkNotNull(metricExtensionProvider2);
            return this;
        }

        public PrimesJankConfigurations build() {
            return new PrimesJankConfigurations(this.enabled, this.monitorActivities, this.useAnimator, this.sampleRatePerSecond, this.metricExtensionProvider);
        }
    }
}
