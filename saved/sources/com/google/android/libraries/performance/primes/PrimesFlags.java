package com.google.android.libraries.performance.primes;

public final class PrimesFlags {
    private final boolean leakDetectionEnabled;
    private final boolean leakDetectionV2Enabled;
    private final boolean magicEyeLogEnabled;
    private final boolean memorySummaryDisabled;
    private final boolean persistCrashStatsEnabled;
    private final boolean primesForPrimesEnabled;
    private final boolean startupTraceEnabled;
    private final boolean urlAutoSanitizationEnabled;

    public static Builder newBuilder() {
        return new Builder();
    }

    PrimesFlags(boolean leakDetectionEnabled2, boolean leakDetectionV2Enabled2, boolean memorySummaryDisabled2, boolean magicEyeLogEnabled2, boolean persistCrashStatsEnabled2, boolean startupTraceEnabled2, boolean urlAutoSanitizationEnabled2, boolean primesForPrimesEnabled2) {
        this.leakDetectionEnabled = leakDetectionEnabled2;
        this.leakDetectionV2Enabled = leakDetectionV2Enabled2;
        this.memorySummaryDisabled = memorySummaryDisabled2;
        this.magicEyeLogEnabled = magicEyeLogEnabled2;
        this.persistCrashStatsEnabled = persistCrashStatsEnabled2;
        this.startupTraceEnabled = startupTraceEnabled2;
        this.urlAutoSanitizationEnabled = urlAutoSanitizationEnabled2;
        this.primesForPrimesEnabled = primesForPrimesEnabled2;
    }

    public boolean isLeakDetectionEnabled() {
        return this.leakDetectionEnabled;
    }

    public boolean isLeakDetectionV2Enabled() {
        return this.leakDetectionV2Enabled;
    }

    public boolean isMemorySummaryDisabled() {
        return this.memorySummaryDisabled;
    }

    public boolean isMagicEyeLogEnabled() {
        return this.magicEyeLogEnabled;
    }

    public boolean isPersistCrashStatsEnabled() {
        return this.persistCrashStatsEnabled;
    }

    public boolean isStartupTraceEnabled() {
        return this.startupTraceEnabled;
    }

    public boolean isUrlAutoSanitizationEnabled() {
        return this.urlAutoSanitizationEnabled;
    }

    public boolean isPrimesForPrimesEnabled() {
        return this.primesForPrimesEnabled;
    }

    public static class Builder {
        private boolean leakDetectionEnable;
        private boolean leakDetectionV2Enable;
        private boolean magicEyeLogEnable;
        private boolean memorySummaryDisable;
        private boolean persistCrashStatsEnable;
        private boolean primesForPrimesEnabled;
        private boolean startupTraceEnable = true;
        private boolean urlAutoSanitizationEnable;

        public Builder enableLeakDetection(boolean enable) {
            this.leakDetectionEnable = enable;
            return this;
        }

        public Builder enableLeakDetectionV2(boolean enable) {
            this.leakDetectionV2Enable = enable;
            return this;
        }

        public Builder disableMemorySummary(boolean disable) {
            this.memorySummaryDisable = disable;
            return this;
        }

        public Builder enableMagicEyeLog(boolean enable) {
            this.magicEyeLogEnable = enable;
            return this;
        }

        public Builder enablePersistCrashStats(boolean enable) {
            this.persistCrashStatsEnable = enable;
            return this;
        }

        public Builder enableStartupTrace(boolean enable) {
            this.startupTraceEnable = enable;
            return this;
        }

        public Builder enableUrlAutoSanitization(boolean enable) {
            this.urlAutoSanitizationEnable = enable;
            return this;
        }

        public Builder enablePrimesForPrimes(boolean enable) {
            this.primesForPrimesEnabled = enable;
            return this;
        }

        public Builder cloneFrom(PrimesFlags primesFlags) {
            this.leakDetectionEnable = primesFlags.isLeakDetectionEnabled();
            this.leakDetectionV2Enable = primesFlags.isLeakDetectionV2Enabled();
            this.memorySummaryDisable = primesFlags.isMemorySummaryDisabled();
            this.magicEyeLogEnable = primesFlags.isMagicEyeLogEnabled();
            this.persistCrashStatsEnable = primesFlags.isPersistCrashStatsEnabled();
            this.startupTraceEnable = primesFlags.isStartupTraceEnabled();
            this.urlAutoSanitizationEnable = primesFlags.isUrlAutoSanitizationEnabled();
            this.primesForPrimesEnabled = primesFlags.isPrimesForPrimesEnabled();
            return this;
        }

        public PrimesFlags build() {
            return new PrimesFlags(this.leakDetectionEnable, this.leakDetectionV2Enable, this.memorySummaryDisable, this.magicEyeLogEnable, this.persistCrashStatsEnable, this.startupTraceEnable, this.urlAutoSanitizationEnable, this.primesForPrimesEnabled);
        }
    }
}
