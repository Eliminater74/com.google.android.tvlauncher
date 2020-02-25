package com.google.android.libraries.performance.primes;

import com.google.android.libraries.stitch.util.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class PrimesScheduledExecutorService implements ScheduledExecutorService {
    /* access modifiers changed from: private */
    public final FailureCallback failureCallback;
    private final ScheduledExecutorService executor;

    PrimesScheduledExecutorService(ScheduledExecutorService scheduledExecutorService, FailureCallback failureCallback2) {
        this.executor = (ScheduledExecutorService) Preconditions.checkNotNull(scheduledExecutorService);
        this.failureCallback = (FailureCallback) Preconditions.checkNotNull(failureCallback2);
    }

    private final Runnable wrap(final Runnable runnable) {
        return new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable t) {
                    PrimesScheduledExecutorService.this.failureCallback.onFailure(t);
                    throw t;
                }
            }
        };
    }

    private final <V> Callable<V> wrap(final Callable<V> callable) {
        return new Callable<V>() {
            public V call() throws Exception {
                try {
                    return callable.call();
                } catch (Throwable t) {
                    PrimesScheduledExecutorService.this.failureCallback.onFailure(t);
                    throw t;
                }
            }
        };
    }

    private final <V> List<? extends Callable<V>> wrapAll(Collection<? extends Callable<V>> tasks) {
        List<Callable<V>> ret = new ArrayList<>();
        for (Callable<V> callable : tasks) {
            ret.add(wrap(callable));
        }
        return ret;
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return this.executor.schedule(wrap(command), delay, unit);
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return this.executor.schedule(wrap(callable), delay, unit);
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return this.executor.scheduleAtFixedRate(wrap(command), initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return this.executor.scheduleWithFixedDelay(wrap(command), initialDelay, delay, unit);
    }

    public void execute(Runnable command) {
        this.executor.execute(wrap(command));
    }

    public Future<?> submit(Runnable task) {
        return this.executor.submit(wrap(task));
    }

    public <V> Future<V> submit(Runnable task, V result) {
        return this.executor.submit(wrap(task), result);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.concurrent.Callable, java.util.concurrent.Callable<V>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <V> java.util.concurrent.Future<V> submit(java.util.concurrent.Callable<V> r3) {
        /*
            r2 = this;
            java.util.concurrent.ScheduledExecutorService r0 = r2.executor
            java.util.concurrent.Callable r1 = r2.wrap(r3)
            java.util.concurrent.Future r0 = r0.submit(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.PrimesScheduledExecutorService.submit(java.util.concurrent.Callable):java.util.concurrent.Future");
    }

    public void shutdown() {
        this.executor.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return this.executor.shutdownNow();
    }

    public boolean isShutdown() {
        return this.executor.isShutdown();
    }

    public boolean isTerminated() {
        return this.executor.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return this.executor.awaitTermination(timeout, unit);
    }

    public <V> V invokeAny(Collection<? extends Callable<V>> tasks) throws InterruptedException, ExecutionException {
        return this.executor.invokeAny(wrapAll(tasks));
    }

    public <V> V invokeAny(Collection<? extends Callable<V>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.executor.invokeAny(wrapAll(tasks), timeout, unit);
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.Collection<? extends java.util.concurrent.Callable<V>>, java.util.Collection] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <V> java.util.List<java.util.concurrent.Future<V>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<V>> r3) throws java.lang.InterruptedException {
        /*
            r2 = this;
            java.util.concurrent.ScheduledExecutorService r0 = r2.executor
            java.util.List r1 = r2.wrapAll(r3)
            java.util.List r0 = r0.invokeAll(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.PrimesScheduledExecutorService.invokeAll(java.util.Collection):java.util.List");
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [java.util.Collection<? extends java.util.concurrent.Callable<V>>, java.util.Collection] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <V> java.util.List<java.util.concurrent.Future<V>> invokeAll(java.util.Collection<? extends java.util.concurrent.Callable<V>> r3, long r4, java.util.concurrent.TimeUnit r6) throws java.lang.InterruptedException {
        /*
            r2 = this;
            java.util.concurrent.ScheduledExecutorService r0 = r2.executor
            java.util.List r1 = r2.wrapAll(r3)
            java.util.List r0 = r0.invokeAll(r1, r4, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.PrimesScheduledExecutorService.invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit):java.util.List");
    }

    interface FailureCallback {
        void onFailure(Throwable th);
    }
}
