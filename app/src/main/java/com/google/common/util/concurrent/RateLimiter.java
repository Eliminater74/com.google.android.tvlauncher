package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@Beta
public abstract class RateLimiter {
    private final SleepingStopwatch stopwatch;
    @MonotonicNonNullDecl
    private volatile Object mutexDoNotUseDirectly;

    RateLimiter(SleepingStopwatch stopwatch2) {
        this.stopwatch = (SleepingStopwatch) Preconditions.checkNotNull(stopwatch2);
    }

    public static RateLimiter create(double permitsPerSecond) {
        return create(permitsPerSecond, SleepingStopwatch.createFromSystemTimer());
    }

    @VisibleForTesting
    static RateLimiter create(double permitsPerSecond, SleepingStopwatch stopwatch2) {
        RateLimiter rateLimiter = new SmoothRateLimiter.SmoothBursty(stopwatch2, 1.0d);
        rateLimiter.setRate(permitsPerSecond);
        return rateLimiter;
    }

    public static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit) {
        Preconditions.checkArgument(warmupPeriod >= 0, "warmupPeriod must not be negative: %s", warmupPeriod);
        return create(permitsPerSecond, warmupPeriod, unit, 3.0d, SleepingStopwatch.createFromSystemTimer());
    }

    @VisibleForTesting
    static RateLimiter create(double permitsPerSecond, long warmupPeriod, TimeUnit unit, double coldFactor, SleepingStopwatch stopwatch2) {
        RateLimiter rateLimiter = new SmoothRateLimiter.SmoothWarmingUp(stopwatch2, warmupPeriod, unit, coldFactor);
        rateLimiter.setRate(permitsPerSecond);
        return rateLimiter;
    }

    private static void checkPermits(int permits) {
        Preconditions.checkArgument(permits > 0, "Requested permits (%s) must be positive", permits);
    }

    /* access modifiers changed from: package-private */
    public abstract double doGetRate();

    /* access modifiers changed from: package-private */
    public abstract void doSetRate(double d, long j);

    /* access modifiers changed from: package-private */
    public abstract long queryEarliestAvailable(long j);

    /* access modifiers changed from: package-private */
    public abstract long reserveEarliestAvailable(int i, long j);

    private Object mutex() {
        Object mutex = this.mutexDoNotUseDirectly;
        if (mutex == null) {
            synchronized (this) {
                mutex = this.mutexDoNotUseDirectly;
                if (mutex == null) {
                    Object obj = new Object();
                    mutex = obj;
                    this.mutexDoNotUseDirectly = obj;
                }
            }
        }
        return mutex;
    }

    public final double getRate() {
        double doGetRate;
        synchronized (mutex()) {
            doGetRate = doGetRate();
        }
        return doGetRate;
    }

    public final void setRate(double permitsPerSecond) {
        Preconditions.checkArgument(permitsPerSecond > 0.0d && !Double.isNaN(permitsPerSecond), "rate must be positive");
        synchronized (mutex()) {
            doSetRate(permitsPerSecond, this.stopwatch.readMicros());
        }
    }

    @CanIgnoreReturnValue
    public double acquire() {
        return acquire(1);
    }

    @CanIgnoreReturnValue
    public double acquire(int permits) {
        long microsToWait = reserve(permits);
        this.stopwatch.sleepMicrosUninterruptibly(microsToWait);
        double d = (double) microsToWait;
        Double.isNaN(d);
        double micros = (double) TimeUnit.SECONDS.toMicros(1);
        Double.isNaN(micros);
        return (d * 1.0d) / micros;
    }

    /* access modifiers changed from: package-private */
    public final long reserve(int permits) {
        long reserveAndGetWaitLength;
        checkPermits(permits);
        synchronized (mutex()) {
            reserveAndGetWaitLength = reserveAndGetWaitLength(permits, this.stopwatch.readMicros());
        }
        return reserveAndGetWaitLength;
    }

    public boolean tryAcquire(long timeout, TimeUnit unit) {
        return tryAcquire(1, timeout, unit);
    }

    public boolean tryAcquire(int permits) {
        return tryAcquire(permits, 0, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire() {
        return tryAcquire(1, 0, TimeUnit.MICROSECONDS);
    }

    /* JADX INFO: Multiple debug info for r3v3 long: [D('nowMicros' long), D('microsToWait' long)] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    public boolean tryAcquire(int permits, long timeout, TimeUnit unit) {
        long timeoutMicros = Math.max(unit.toMicros(timeout), 0L);
        checkPermits(permits);
        synchronized (mutex()) {
            long nowMicros = this.stopwatch.readMicros();
            if (!canAcquire(nowMicros, timeoutMicros)) {
                return false;
            }
            long microsToWait = reserveAndGetWaitLength(permits, nowMicros);
            this.stopwatch.sleepMicrosUninterruptibly(microsToWait);
            return true;
        }
    }

    private boolean canAcquire(long nowMicros, long timeoutMicros) {
        return queryEarliestAvailable(nowMicros) - timeoutMicros <= nowMicros;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    /* access modifiers changed from: package-private */
    public final long reserveAndGetWaitLength(int permits, long nowMicros) {
        return Math.max(reserveEarliestAvailable(permits, nowMicros) - nowMicros, 0L);
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", Double.valueOf(getRate()));
    }

    static abstract class SleepingStopwatch {
        protected SleepingStopwatch() {
        }

        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch() {
                final Stopwatch stopwatch = Stopwatch.createStarted();

                /* access modifiers changed from: protected */
                public long readMicros() {
                    return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
                }

                /* access modifiers changed from: protected */
                public void sleepMicrosUninterruptibly(long micros) {
                    if (micros > 0) {
                        Uninterruptibles.sleepUninterruptibly(micros, TimeUnit.MICROSECONDS);
                    }
                }
            };
        }

        /* access modifiers changed from: protected */
        public abstract long readMicros();

        /* access modifiers changed from: protected */
        public abstract void sleepMicrosUninterruptibly(long j);
    }
}
