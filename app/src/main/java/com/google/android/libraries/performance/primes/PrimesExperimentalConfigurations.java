package com.google.android.libraries.performance.primes;

import com.google.common.base.Optional;

public final class PrimesExperimentalConfigurations {
    final Optional<PrimesCounterConfigurations> counterConfigurations;
    final Optional<PrimesProfilingConfigurations> profilingConfigurations;
    final Optional<PrimesStrictModeConfigurations> strictModeConfigurations;

    private PrimesExperimentalConfigurations(Optional<PrimesCounterConfigurations> counterConfigurations2, Optional<PrimesProfilingConfigurations> profilingConfigurations2, Optional<PrimesStrictModeConfigurations> strictModeConfigs) {
        this.counterConfigurations = counterConfigurations2;
        this.profilingConfigurations = profilingConfigurations2;
        this.strictModeConfigurations = strictModeConfigs;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Optional<PrimesCounterConfigurations> counterConfigurations() {
        return this.counterConfigurations;
    }

    public Optional<PrimesProfilingConfigurations> profilingConfigurations() {
        return this.profilingConfigurations;
    }

    public Optional<PrimesStrictModeConfigurations> strictModeConfigurations() {
        return this.strictModeConfigurations;
    }

    public static final class Builder {
        private Optional<PrimesCounterConfigurations> counterConfigurations = Optional.absent();
        private Optional<PrimesProfilingConfigurations> profilingConfigurations = Optional.absent();
        private Optional<PrimesStrictModeConfigurations> strictModeConfigurations = Optional.absent();

        public Builder setCounterConfigurations(PrimesCounterConfigurations counterConfigurations2) {
            this.counterConfigurations = Optional.m80of(counterConfigurations2);
            return this;
        }

        public Builder setProfilingConfigurations(PrimesProfilingConfigurations profilingConfigurations2) {
            if (profilingConfigurations2 != null && profilingConfigurations2.getMaxBufferSizeBytes() > 0 && profilingConfigurations2.getMaxBufferSizeBytes() <= 3145728 && profilingConfigurations2.getSampleDurationMs() > 0 && profilingConfigurations2.getSampleFrequencyMicro() > 0 && profilingConfigurations2.getSamplesPerEpoch() > 0.0d) {
                this.profilingConfigurations = Optional.m80of(profilingConfigurations2);
            }
            return this;
        }

        public Builder setStrictModeConfigurations(PrimesStrictModeConfigurations strictModeConfigurations2) {
            this.strictModeConfigurations = Optional.m80of(strictModeConfigurations2);
            return this;
        }

        public PrimesExperimentalConfigurations build() {
            return new PrimesExperimentalConfigurations(this.counterConfigurations, this.profilingConfigurations, this.strictModeConfigurations);
        }
    }
}
