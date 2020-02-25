package com.google.android.libraries.performance.primes;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.libraries.clock.Clock;
import com.google.android.libraries.performance.primes.sampling.SamplingUtils;

import java.util.Objects;
import java.util.Random;
import java.util.TreeSet;

final class CpuProfilingServiceScheduler {
    static final long AVERAGE_MILLIS_PER_YEAR = 31557600000L;
    private static final long ALLOWED_MECHANICAL_DELAY = 100;
    private static final String TAG = "CpuProfilingServiceScheduler";
    private final String androidId;
    private final Clock clock;
    private final String processName;
    private final int sampleDurationMs;
    private final double samplesPerEpoch;

    CpuProfilingServiceScheduler(Clock clock2, double samplesPerEpoch2, int sampleDurationMs2, String processName2, Context context) {
        this.clock = clock2;
        this.samplesPerEpoch = samplesPerEpoch2;
        this.sampleDurationMs = sampleDurationMs2;
        this.processName = processName2;
        this.androidId = SamplingUtils.getAndroidId(context);
    }

    private static long getFirstDayOfYear(long millis) {
        return millis - (millis % AVERAGE_MILLIS_PER_YEAR);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Long getNextWindow() {
        return getNextWindow(getFirstDayOfYear());
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Long getNextWindow(long epochStartMs) {
        Random generator = getRandomGenerator(epochStartMs);
        int randomNumSamples = getNumSamples(generator);
        long nextYearMidnightMs = epochStartMs + AVERAGE_MILLIS_PER_YEAR;
        long maxOffsetMs = (nextYearMidnightMs - epochStartMs) - ((long) (this.sampleDurationMs * 2));
        TreeSet<Long> windows = new TreeSet<>();
        while (windows.size() < randomNumSamples) {
            long candidateStartMs = epochStartMs + (Math.abs(Math.max(generator.nextLong(), (long) C0841C.TIME_UNSET)) % maxOffsetMs);
            long buffer = ((long) this.sampleDurationMs) * 2;
            Random generator2 = generator;
            int randomNumSamples2 = randomNumSamples;
            if (!windows.subSet(Long.valueOf(candidateStartMs - buffer), Long.valueOf(candidateStartMs + buffer)).isEmpty()) {
                generator = generator2;
                randomNumSamples = randomNumSamples2;
            } else {
                windows.add(Long.valueOf(candidateStartMs));
                generator = generator2;
                randomNumSamples = randomNumSamples2;
            }
        }
        Long timeToReturn = (Long) windows.ceiling(Long.valueOf(this.clock.currentTimeMillis() + ALLOWED_MECHANICAL_DELAY));
        if (timeToReturn == null && epochStartMs < this.clock.currentTimeMillis()) {
            return getNextWindow(nextYearMidnightMs);
        }
        return timeToReturn;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.min(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.min(double, double):double}
      ClspMth{java.lang.Math.min(float, float):float}
      ClspMth{java.lang.Math.min(int, int):int}
      ClspMth{java.lang.Math.min(long, long):long} */
    /* access modifiers changed from: package-private */
    public int getNumSamples(Random generator) {
        double randNumber = generator.nextDouble();
        double d = this.samplesPerEpoch;
        if (d >= 1.0d) {
            return (int) Math.min(Math.round(d * 2.0d * randNumber), 2147483646L);
        }
        if (randNumber < d) {
            return 1;
        }
        return 0;
    }

    @TargetApi(19)
    private Random getRandomGenerator(long epochStartDateMs) {
        return new Random((long) Objects.hash(Long.valueOf(epochStartDateMs), this.androidId, this.processName));
    }

    private long getFirstDayOfYear() {
        return getFirstDayOfYear(this.clock.currentTimeMillis());
    }
}
