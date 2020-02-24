package com.google.android.libraries.performance.primes;

public final class PrimesProfilingConfigurations {
    private static final int DEFAULT_FREQUENCY_MICRO = 1000;
    private static final int DEFAULT_MAX_BUFFER_SIZE_BYTES = 2097152;
    private static final double DEFAULT_SAMPLES_PER_EPOCH = 5.0d;
    private static final int DEFAULT_SAMPLE_DURATION_MS = 30000;
    private static final int DEFAULT_SAMPLE_DURATION_SKEW_MS = 5000;
    static final int MAX_BUFFER_SIZE_BYTES_UPPER_BOUND = 3145728;
    private final boolean enabled;
    private final int maxBufferSizeBytes;
    private final int sampleDurationMs;
    private final int sampleDurationSkewMs;
    private final int sampleFrequencyMicro;
    private final double samplesPerEpoch;

    public PrimesProfilingConfigurations(boolean enabled2) {
        this(enabled2, 2097152, DEFAULT_SAMPLE_DURATION_MS, 5000, 1000, DEFAULT_SAMPLES_PER_EPOCH);
    }

    public PrimesProfilingConfigurations(boolean enabled2, int maxBufferSizeBytes2, int sampleDurationMs2, int sampleDurationSkewMs2, int sampleFrequencyMicro2, double samplesPerEpoch2) {
        this.enabled = enabled2;
        this.maxBufferSizeBytes = maxBufferSizeBytes2;
        this.sampleDurationMs = sampleDurationMs2;
        this.sampleDurationSkewMs = sampleDurationSkewMs2;
        this.sampleFrequencyMicro = sampleFrequencyMicro2;
        this.samplesPerEpoch = samplesPerEpoch2;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getMaxBufferSizeBytes() {
        return this.maxBufferSizeBytes;
    }

    public int getSampleDurationMs() {
        return this.sampleDurationMs;
    }

    public int getSampleDurationSkewMs() {
        return this.sampleDurationSkewMs;
    }

    public int getSampleFrequencyMicro() {
        return this.sampleFrequencyMicro;
    }

    public double getSamplesPerEpoch() {
        return this.samplesPerEpoch;
    }
}
