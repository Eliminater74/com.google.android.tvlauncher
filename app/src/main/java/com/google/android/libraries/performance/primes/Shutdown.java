package com.google.android.libraries.performance.primes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.trace.PrimesTrace;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Shutdown {
    private static final String TAG = "PrimesShutdown";
    @VisibleForTesting
    final List<ShutdownListener> listeners = new ArrayList();
    private volatile boolean shutdown;

    public abstract void init(Context context, Supplier<SharedPreferences> supplier, Supplier<ScheduledExecutorService> supplier2);

    public final boolean isShutdown() {
        return this.shutdown;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0049, code lost:
        r1 = th;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void shutdown() {
        /*
            r8 = this;
            monitor-enter(r8)
            boolean r0 = r8.shutdown     // Catch:{ all -> 0x004d }
            if (r0 != 0) goto L_0x004b
            r0 = 1
            r8.shutdown = r0     // Catch:{ all -> 0x004d }
            java.lang.String r0 = "PrimesShutdown"
            java.lang.String r1 = "Shutdown ..."
            r2 = 0
            java.lang.Object[] r3 = new java.lang.Object[r2]     // Catch:{ all -> 0x004d }
            com.google.android.libraries.performance.primes.PrimesLog.m46d(r0, r1, r3)     // Catch:{ all -> 0x004d }
            java.util.List<com.google.android.libraries.performance.primes.ShutdownListener> r0 = r8.listeners     // Catch:{ all -> 0x004d }
            monitor-enter(r0)     // Catch:{ all -> 0x004d }
            java.util.List<com.google.android.libraries.performance.primes.ShutdownListener> r1 = r8.listeners     // Catch:{ all -> 0x0046 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x0046 }
        L_0x001b:
            boolean r3 = r1.hasNext()     // Catch:{ all -> 0x0046 }
            if (r3 == 0) goto L_0x0036
            java.lang.Object r3 = r1.next()     // Catch:{ all -> 0x0046 }
            com.google.android.libraries.performance.primes.ShutdownListener r3 = (com.google.android.libraries.performance.primes.ShutdownListener) r3     // Catch:{ all -> 0x0046 }
            r3.onShutdown()     // Catch:{ RuntimeException -> 0x002b }
            goto L_0x0035
        L_0x002b:
            r4 = move-exception
            java.lang.String r5 = "PrimesShutdown"
            java.lang.String r6 = "ShutdownListener crashed"
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ all -> 0x0046 }
            com.google.android.libraries.performance.primes.PrimesLog.m45d(r5, r6, r4, r7)     // Catch:{ all -> 0x0046 }
        L_0x0035:
            goto L_0x001b
        L_0x0036:
            java.util.List<com.google.android.libraries.performance.primes.ShutdownListener> r1 = r8.listeners     // Catch:{ all -> 0x0046 }
            r1.clear()     // Catch:{ all -> 0x0046 }
            java.lang.String r1 = "PrimesShutdown"
            java.lang.String r3 = "All ShutdownListeners notified."
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0046 }
            com.google.android.libraries.performance.primes.PrimesLog.m46d(r1, r3, r2)     // Catch:{ all -> 0x0046 }
            monitor-exit(r0)     // Catch:{ all -> 0x0046 }
            goto L_0x004b
        L_0x0046:
            r1 = move-exception
        L_0x0047:
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            throw r1     // Catch:{ all -> 0x004d }
        L_0x0049:
            r1 = move-exception
            goto L_0x0047
        L_0x004b:
            monitor-exit(r8)
            return
        L_0x004d:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.Shutdown.shutdown():void");
    }

    public final void updateShutdownFlag(Supplier<Boolean> shutdownFlag) {
        try {
            PrimesTrace.beginSection("Shutdown-updateFlag");
            if (!isShutdown() && shutdownFlag.get().booleanValue()) {
                shutdown();
            }
        } finally {
            PrimesTrace.endSection();
        }
    }

    public final boolean registerShutdownListener(ShutdownListener listener) {
        synchronized (this.listeners) {
            if (isShutdown()) {
                return false;
            }
            this.listeners.add((ShutdownListener) Preconditions.checkNotNull(listener));
            return true;
        }
    }
}
