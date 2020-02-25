package com.google.android.libraries.performance.primes.sampling;

import android.os.SystemClock;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.stitch.util.Preconditions;

import java.util.Random;

public final class ProbabilitySampler {
    private final Random random;
    private final float samplingRate;

    public ProbabilitySampler(float samplingRate2) {
        this(samplingRate2, new Random(SystemClock.elapsedRealtime()));
    }

    @VisibleForTesting
    public ProbabilitySampler(float samplingRate2, Random random2) {
        Preconditions.checkArgument(samplingRate2 >= 0.0f && samplingRate2 <= 1.0f, "Sampling rate should be a floating number >= 0 and <= 1.");
        this.samplingRate = samplingRate2;
        this.random = random2;
    }

    public boolean isSampleAllowed() {
        return this.random.nextFloat() < this.samplingRate;
    }
}
