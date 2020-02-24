package com.google.android.libraries.performance.primes.sampling;

import android.os.SystemClock;

public final class PrimesSampling {
    private static final int TIME_UNIT_MS = 1000;
    private long firstSampleInLastSecond = 0;
    private final int maxSamplesRate;
    private final Object mutex = new Object();
    private int samplesCount = 0;

    public PrimesSampling(int samplesRate) {
        this.maxSamplesRate = samplesRate;
    }

    public boolean isSampleRateExceeded() {
        synchronized (this.mutex) {
            if (SystemClock.elapsedRealtime() - this.firstSampleInLastSecond > 1000) {
                return false;
            }
            if (this.samplesCount >= this.maxSamplesRate) {
                return true;
            }
            return false;
        }
    }

    public void incrementSampleCount() {
        synchronized (this.mutex) {
            this.samplesCount++;
            long currentTime = SystemClock.elapsedRealtime();
            if (currentTime - this.firstSampleInLastSecond > 1000) {
                this.samplesCount = 0;
                this.firstSampleInLastSecond = currentTime;
            }
        }
    }
}
