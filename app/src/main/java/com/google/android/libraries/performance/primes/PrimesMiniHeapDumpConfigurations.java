package com.google.android.libraries.performance.primes;

public class PrimesMiniHeapDumpConfigurations {
    private final boolean enabled;

    public PrimesMiniHeapDumpConfigurations(boolean enabled2) {
        this.enabled = enabled2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
