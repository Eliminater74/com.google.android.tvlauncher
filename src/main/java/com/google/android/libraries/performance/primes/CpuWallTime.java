package com.google.android.libraries.performance.primes;

import android.os.Debug;

final class CpuWallTime {
    final long cpuNanos;
    final long wallNanos;

    CpuWallTime(long wallNanos2, long cpuNanos2) {
        this.wallNanos = wallNanos2;
        this.cpuNanos = cpuNanos2;
    }

    /* access modifiers changed from: package-private */
    public long wallMicros() {
        return this.wallNanos / 1000;
    }

    /* access modifiers changed from: package-private */
    public long cpuMicros() {
        return this.cpuNanos / 1000;
    }

    static CpuWallTime minus(CpuWallTime lhs, CpuWallTime rhs) {
        return new CpuWallTime(lhs.wallNanos - rhs.wallNanos, lhs.cpuNanos - rhs.cpuNanos);
    }

    static CpuWallTime now() {
        return new CpuWallTime(System.nanoTime(), Debug.threadCpuTimeNanos());
    }

    static CpuWallTime since(CpuWallTime startTime) {
        return minus(now(), startTime);
    }
}
