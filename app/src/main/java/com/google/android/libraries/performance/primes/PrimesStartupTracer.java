package com.google.android.libraries.performance.primes;

import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.tracing.TraceData;
import com.google.android.libraries.performance.primes.tracing.Tracer;

final class PrimesStartupTracer implements PrimesStartupMeasureListener.OnActivityInit, PrimesStartupMeasureListener.OnDraw {
    @VisibleForTesting
    static final String STARTUP_TRACE_ROOT_SPAN = "More Insights";
    private static final int MAX_START_TRACE_BUFFER_SIZE = 100;
    private boolean activeStartupTrace = false;
    private volatile int minSpanDurationMs;
    private volatile TraceData startupTraceData = null;

    PrimesStartupTracer(int minSpanDurationMs2) {
        this.minSpanDurationMs = minSpanDurationMs2;
    }

    public void onActivityInit() {
        startPrimesTrace();
    }

    public void onDraw() {
        stopPrimesTrace();
    }

    private synchronized void startPrimesTrace() {
        this.activeStartupTrace = Tracer.start(PrimesToken.PRIMES_TOKEN, STARTUP_TRACE_ROOT_SPAN, this.minSpanDurationMs, 100);
    }

    private void stopPrimesTrace() {
        if (this.activeStartupTrace) {
            this.startupTraceData = Tracer.stop(PrimesToken.PRIMES_TOKEN, STARTUP_TRACE_ROOT_SPAN);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x000b, code lost:
        r0 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void shutdown() {
        /*
            r1 = this;
            monitor-enter(r1)
            monitor-enter(r1)     // Catch:{ all -> 0x000d }
            r1.stopPrimesTrace()     // Catch:{ all -> 0x0008 }
            monitor-exit(r1)     // Catch:{ all -> 0x0008 }
            monitor-exit(r1)
            return
        L_0x0008:
            r0 = move-exception
        L_0x0009:
            monitor-exit(r1)     // Catch:{ all -> 0x000b }
            throw r0     // Catch:{ all -> 0x000d }
        L_0x000b:
            r0 = move-exception
            goto L_0x0009
        L_0x000d:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.PrimesStartupTracer.shutdown():void");
    }

    /* access modifiers changed from: package-private */
    public TraceData getStartupTraceData() {
        return this.startupTraceData;
    }
}
