package com.google.android.libraries.performance.primes;

import com.google.common.base.Optional;

public final class PrimesPackageConfigurations {
    private static final String DEV_ENABLE_PACKAGESTATS = "primes.dev.enable_packagestats";
    private final Optional<PrimesDirStatsConfigurations> dirStatsConfigs;
    private final boolean enabled;
    private final boolean manualCapture;

    public PrimesPackageConfigurations(boolean enabled2) {
        this(enabled2, false);
    }

    public PrimesPackageConfigurations(boolean enabled2, boolean manualCapture2) {
        this(enabled2, manualCapture2, Optional.absent());
    }

    public PrimesPackageConfigurations(boolean enabled2, boolean manualCapture2, boolean captureDirStats) {
        this(enabled2, manualCapture2, Optional.m80of(PrimesDirStatsConfigurations.newBuilder().setEnabled(captureDirStats).build()));
    }

    private PrimesPackageConfigurations(boolean enabled2, boolean manualCapture2, Optional<PrimesDirStatsConfigurations> dirStatsConfigs2) {
        this.enabled = enabled2;
        this.manualCapture = manualCapture2;
        this.dirStatsConfigs = dirStatsConfigs2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isManualCapture() {
        return this.manualCapture;
    }

    public Optional<PrimesDirStatsConfigurations> getDirStatsConfigurations() {
        return this.dirStatsConfigs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private volatile Optional<PrimesDirStatsConfigurations> dirStatsConfigs;
        private volatile boolean enabled;
        private volatile boolean manualCapture;

        private Builder() {
            this.dirStatsConfigs = Optional.absent();
        }

        public Builder setEnabled(boolean enabled2) {
            this.enabled = enabled2;
            return this;
        }

        public Builder setManualCapture(boolean manualCapture2) {
            this.manualCapture = manualCapture2;
            return this;
        }

        public Builder setDirStatsConfigurations(PrimesDirStatsConfigurations dirStatsConfigs2) {
            this.dirStatsConfigs = Optional.m80of(dirStatsConfigs2);
            return this;
        }

        public PrimesPackageConfigurations build() {
            return new PrimesPackageConfigurations(this.enabled, this.manualCapture, this.dirStatsConfigs);
        }
    }
}
