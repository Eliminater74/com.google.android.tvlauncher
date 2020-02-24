package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@GwtIncompatible
@CanIgnoreReturnValue
abstract class WrappingScheduledExecutorService extends WrappingExecutorService implements ScheduledExecutorService {
    final ScheduledExecutorService delegate;

    protected WrappingScheduledExecutorService(ScheduledExecutorService delegate2) {
        super(delegate2);
        this.delegate = delegate2;
    }

    public final ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return this.delegate.schedule(wrapTask(command), delay, unit);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.concurrent.Callable, java.util.concurrent.Callable<V>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final <V> java.util.concurrent.ScheduledFuture<V> schedule(java.util.concurrent.Callable<V> r3, long r4, java.util.concurrent.TimeUnit r6) {
        /*
            r2 = this;
            java.util.concurrent.ScheduledExecutorService r0 = r2.delegate
            java.util.concurrent.Callable r1 = r2.wrapTask(r3)
            java.util.concurrent.ScheduledFuture r0 = r0.schedule(r1, r4, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.WrappingScheduledExecutorService.schedule(java.util.concurrent.Callable, long, java.util.concurrent.TimeUnit):java.util.concurrent.ScheduledFuture");
    }

    public final ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return this.delegate.scheduleAtFixedRate(wrapTask(command), initialDelay, period, unit);
    }

    public final ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return this.delegate.scheduleWithFixedDelay(wrapTask(command), initialDelay, delay, unit);
    }
}
