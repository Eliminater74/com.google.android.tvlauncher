package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.math.LongMath;

import java.util.concurrent.TimeUnit;

@GwtIncompatible
abstract class SmoothRateLimiter extends RateLimiter {
    double maxPermits;
    double stableIntervalMicros;
    double storedPermits;
    private long nextFreeTicketMicros;

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch stopwatch) {
        super(stopwatch);
        this.nextFreeTicketMicros = 0;
    }

    /* access modifiers changed from: package-private */
    public abstract double coolDownIntervalMicros();

    /* access modifiers changed from: package-private */
    public abstract void doSetRate(double d, double d2);

    /* access modifiers changed from: package-private */
    public abstract long storedPermitsToWaitTime(double d, double d2);

    /* access modifiers changed from: package-private */
    public final void doSetRate(double permitsPerSecond, long nowMicros) {
        resync(nowMicros);
        double micros = (double) TimeUnit.SECONDS.toMicros(1);
        Double.isNaN(micros);
        double stableIntervalMicros2 = micros / permitsPerSecond;
        this.stableIntervalMicros = stableIntervalMicros2;
        doSetRate(permitsPerSecond, stableIntervalMicros2);
    }

    /* access modifiers changed from: package-private */
    public final double doGetRate() {
        double micros = (double) TimeUnit.SECONDS.toMicros(1);
        double d = this.stableIntervalMicros;
        Double.isNaN(micros);
        return micros / d;
    }

    /* access modifiers changed from: package-private */
    public final long queryEarliestAvailable(long nowMicros) {
        return this.nextFreeTicketMicros;
    }

    /* access modifiers changed from: package-private */
    public final long reserveEarliestAvailable(int requiredPermits, long nowMicros) {
        resync(nowMicros);
        long returnValue = this.nextFreeTicketMicros;
        double storedPermitsToSpend = Math.min((double) requiredPermits, this.storedPermits);
        double d = (double) requiredPermits;
        Double.isNaN(d);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, storedPermitsToWaitTime(this.storedPermits, storedPermitsToSpend) + ((long) (this.stableIntervalMicros * (d - storedPermitsToSpend))));
        this.storedPermits -= storedPermitsToSpend;
        return returnValue;
    }

    /* access modifiers changed from: package-private */
    public void resync(long nowMicros) {
        long j = this.nextFreeTicketMicros;
        if (nowMicros > j) {
            double d = (double) (nowMicros - j);
            double coolDownIntervalMicros = coolDownIntervalMicros();
            Double.isNaN(d);
            this.storedPermits = Math.min(this.maxPermits, this.storedPermits + (d / coolDownIntervalMicros));
            this.nextFreeTicketMicros = nowMicros;
        }
    }

    static final class SmoothWarmingUp extends SmoothRateLimiter {
        private final long warmupPeriodMicros;
        private double coldFactor;
        private double slope;
        private double thresholdPermits;

        SmoothWarmingUp(RateLimiter.SleepingStopwatch stopwatch, long warmupPeriod, TimeUnit timeUnit, double coldFactor2) {
            super(stopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(warmupPeriod);
            this.coldFactor = coldFactor2;
        }

        /* access modifiers changed from: package-private */
        public void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double d;
            double oldMaxPermits = this.maxPermits;
            double coldIntervalMicros = this.coldFactor * stableIntervalMicros;
            long j = this.warmupPeriodMicros;
            double d2 = (double) j;
            Double.isNaN(d2);
            this.thresholdPermits = (d2 * 0.5d) / stableIntervalMicros;
            double d3 = this.thresholdPermits;
            double d4 = (double) j;
            Double.isNaN(d4);
            this.maxPermits = d3 + ((d4 * 2.0d) / (stableIntervalMicros + coldIntervalMicros));
            this.slope = (coldIntervalMicros - stableIntervalMicros) / (this.maxPermits - this.thresholdPermits);
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                this.storedPermits = 0.0d;
                return;
            }
            if (oldMaxPermits == 0.0d) {
                d = this.maxPermits;
            } else {
                d = (this.storedPermits * this.maxPermits) / oldMaxPermits;
            }
            this.storedPermits = d;
        }

        /* access modifiers changed from: package-private */
        public long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            double permitsToTake2 = permitsToTake;
            double availablePermitsAboveThreshold = storedPermits - this.thresholdPermits;
            long micros = 0;
            if (availablePermitsAboveThreshold > 0.0d) {
                double permitsAboveThresholdToTake = Math.min(availablePermitsAboveThreshold, permitsToTake2);
                micros = (long) ((permitsAboveThresholdToTake * (permitsToTime(availablePermitsAboveThreshold) + permitsToTime(availablePermitsAboveThreshold - permitsAboveThresholdToTake))) / 2.0d);
                permitsToTake2 -= permitsAboveThresholdToTake;
            }
            return micros + ((long) (this.stableIntervalMicros * permitsToTake2));
        }

        private double permitsToTime(double permits) {
            return this.stableIntervalMicros + (this.slope * permits);
        }

        /* access modifiers changed from: package-private */
        public double coolDownIntervalMicros() {
            double d = (double) this.warmupPeriodMicros;
            double d2 = this.maxPermits;
            Double.isNaN(d);
            return d / d2;
        }
    }

    static final class SmoothBursty extends SmoothRateLimiter {
        final double maxBurstSeconds;

        SmoothBursty(RateLimiter.SleepingStopwatch stopwatch, double maxBurstSeconds2) {
            super(stopwatch);
            this.maxBurstSeconds = maxBurstSeconds2;
        }

        /* access modifiers changed from: package-private */
        public void doSetRate(double permitsPerSecond, double stableIntervalMicros) {
            double oldMaxPermits = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * permitsPerSecond;
            if (oldMaxPermits == Double.POSITIVE_INFINITY) {
                this.storedPermits = this.maxPermits;
                return;
            }
            double d = 0.0d;
            if (oldMaxPermits != 0.0d) {
                d = (this.storedPermits * this.maxPermits) / oldMaxPermits;
            }
            this.storedPermits = d;
        }

        /* access modifiers changed from: package-private */
        public long storedPermitsToWaitTime(double storedPermits, double permitsToTake) {
            return 0;
        }

        /* access modifiers changed from: package-private */
        public double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }
    }
}
