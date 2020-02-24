package com.google.android.libraries.performance.primes;

public final class PrimesCpuConfigurations {
    private static final int DEFAULT_INITIAL_DELAY = 15000;
    private static final int DEFAULT_NUMBER_SAMPLES_TO_COLLECT = 5;
    private static final int DEFAULT_TIME_BETWEEN_SAMPLES = 2000;
    static final int MIN_TIME_IN_MS_BETWEEN_SAMPLES = 100;
    private final boolean enabled;
    private final int initialDelay;
    private final int numSamples;
    private final int timeBetweenSamples;

    public PrimesCpuConfigurations(boolean enabled2) {
        this(enabled2, 5, 15000, 2000);
    }

    public PrimesCpuConfigurations(boolean enabled2, int numSamples2, int initialDelay2, int timeBetweenSamples2) {
        this.enabled = enabled2;
        this.numSamples = numSamples2;
        this.initialDelay = initialDelay2;
        this.timeBetweenSamples = timeBetweenSamples2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getNumSamples() {
        return this.numSamples;
    }

    public int getInitialDelay() {
        return this.initialDelay;
    }

    public int getTimeBetweenSamples() {
        return this.timeBetweenSamples;
    }
}
