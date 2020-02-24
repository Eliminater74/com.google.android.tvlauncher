package com.google.android.libraries.performance.primes;

public final class PrimesStrictModeConfigurations {
    static final PrimesStrictModeConfigurations DEFAULT = new PrimesStrictModeConfigurations(false);
    private final boolean enabled;

    public PrimesStrictModeConfigurations(boolean enabled2) {
        this.enabled = enabled2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
