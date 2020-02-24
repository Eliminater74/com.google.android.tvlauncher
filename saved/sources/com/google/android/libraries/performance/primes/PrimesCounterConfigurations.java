package com.google.android.libraries.performance.primes;

public final class PrimesCounterConfigurations {
    private final boolean isEnabled;

    public boolean isEnabled() {
        return this.isEnabled;
    }

    private PrimesCounterConfigurations(boolean isEnabled2) {
        this.isEnabled = isEnabled2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private boolean isEnabled;

        private Builder() {
        }

        public Builder setEnabled(boolean enabled) {
            this.isEnabled = enabled;
            return this;
        }

        public PrimesCounterConfigurations build() {
            return new PrimesCounterConfigurations(this.isEnabled);
        }
    }
}
