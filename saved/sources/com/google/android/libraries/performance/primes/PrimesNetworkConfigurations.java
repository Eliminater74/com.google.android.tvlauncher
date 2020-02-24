package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import com.google.common.base.Optional;

public class PrimesNetworkConfigurations {
    private static final int DEFAULT_BATCH_SIZE = 50;
    private final int batchSize;
    private final boolean enableUrlAutoSanitization;
    private final boolean enabled;
    private final Optional<NetworkMetricExtensionProvider> metricExtensionProvider;
    @Nullable
    private final UrlSanitizer urlSanitizer;

    @Deprecated
    public PrimesNetworkConfigurations() {
        this(false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.performance.primes.PrimesNetworkConfigurations.<init>(boolean, boolean):void
     arg types: [boolean, int]
     candidates:
      com.google.android.libraries.performance.primes.PrimesNetworkConfigurations.<init>(boolean, com.google.android.libraries.performance.primes.UrlSanitizer):void
      com.google.android.libraries.performance.primes.PrimesNetworkConfigurations.<init>(boolean, boolean):void */
    @Deprecated
    public PrimesNetworkConfigurations(boolean enabled2) {
        this(enabled2, false);
    }

    @Deprecated
    public PrimesNetworkConfigurations(boolean enabled2, boolean enableUrlAutoSanitization2) {
        this(enabled2, enableUrlAutoSanitization2, 50);
    }

    @Deprecated
    public PrimesNetworkConfigurations(boolean enabled2, boolean enableUrlAutoSanitization2, int batchSize2) {
        this(enabled2, null, enableUrlAutoSanitization2, batchSize2, Optional.absent());
    }

    @Deprecated
    public PrimesNetworkConfigurations(boolean enabled2, @Nullable UrlSanitizer urlSanitizer2) {
        this(enabled2, urlSanitizer2, false, 50, Optional.absent());
    }

    @Deprecated
    public PrimesNetworkConfigurations(boolean enabled2, @Nullable UrlSanitizer urlSanitizer2, int batchSize2) {
        this(enabled2, urlSanitizer2, false, batchSize2, Optional.absent());
    }

    private PrimesNetworkConfigurations(boolean enabled2, @Nullable UrlSanitizer urlSanitizer2, boolean enableUrlAutoSanitization2, int batchSize2, Optional<NetworkMetricExtensionProvider> metricExtensionProvider2) {
        this.enabled = enabled2;
        this.urlSanitizer = urlSanitizer2;
        this.enableUrlAutoSanitization = enableUrlAutoSanitization2;
        this.batchSize = batchSize2;
        this.metricExtensionProvider = metricExtensionProvider2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int batchSize() {
        return this.batchSize;
    }

    @Nullable
    public UrlSanitizer getUrlSanitizer() {
        return this.urlSanitizer;
    }

    public boolean isUrlAutoSanitizationEnabled() {
        return this.enableUrlAutoSanitization;
    }

    public Optional<NetworkMetricExtensionProvider> getMetricExtensionProvider() {
        return this.metricExtensionProvider;
    }

    public Builder toBuilder() {
        Builder builder = newBuilder();
        boolean unused = builder.enabled = this.enabled;
        UrlSanitizer unused2 = builder.urlSanitizer = this.urlSanitizer;
        boolean unused3 = builder.enableUrlAutoSanitization = this.enableUrlAutoSanitization;
        int unused4 = builder.batchSize = this.batchSize;
        Optional unused5 = builder.metricExtensionProvider = this.metricExtensionProvider;
        return builder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public int batchSize;
        /* access modifiers changed from: private */
        public boolean enableUrlAutoSanitization;
        /* access modifiers changed from: private */
        public boolean enabled;
        /* access modifiers changed from: private */
        public Optional<NetworkMetricExtensionProvider> metricExtensionProvider;
        /* access modifiers changed from: private */
        @Nullable
        public UrlSanitizer urlSanitizer;

        private Builder() {
            this.batchSize = 50;
            this.metricExtensionProvider = Optional.absent();
        }

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setEnableUrlAutoSanitization(boolean enableUrlAutoSanitization2) {
            this.enableUrlAutoSanitization = enableUrlAutoSanitization2;
            if (enableUrlAutoSanitization2) {
                this.urlSanitizer = null;
            }
            return this;
        }

        @Deprecated
        public Builder setUrlSanitizer(UrlSanitizer urlSanitizer2) {
            this.urlSanitizer = urlSanitizer2;
            this.enableUrlAutoSanitization = false;
            return this;
        }

        public Builder setBatchSize(int batchSize2) {
            this.batchSize = batchSize2;
            return this;
        }

        public Builder setMetricExtensionProvider(NetworkMetricExtensionProvider metricExtensionProvider2) {
            this.metricExtensionProvider = Optional.m80of(metricExtensionProvider2);
            return this;
        }

        public PrimesNetworkConfigurations build() {
            return new PrimesNetworkConfigurations(this.enabled, this.urlSanitizer, this.enableUrlAutoSanitization, this.batchSize, this.metricExtensionProvider);
        }
    }
}
