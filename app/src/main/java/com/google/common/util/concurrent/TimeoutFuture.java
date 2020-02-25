package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
final class TimeoutFuture<V> extends FluentFuture.TrustedFuture<V> {
    /* access modifiers changed from: private */
    @NullableDecl
    public ListenableFuture<V> delegateRef;
    /* access modifiers changed from: private */
    @NullableDecl
    public ScheduledFuture<?> timer;

    private TimeoutFuture(ListenableFuture<V> delegate) {
        this.delegateRef = (ListenableFuture) Preconditions.checkNotNull(delegate);
    }

    static <V> ListenableFuture<V> create(ListenableFuture<V> delegate, long time, TimeUnit unit, ScheduledExecutorService scheduledExecutor) {
        TimeoutFuture<V> result = new TimeoutFuture<>(delegate);
        Fire<V> fire = new Fire<>(result);
        result.timer = scheduledExecutor.schedule(fire, time, unit);
        delegate.addListener(fire, MoreExecutors.directExecutor());
        return result;
    }

    /* access modifiers changed from: protected */
    public String pendingToString() {
        ListenableFuture<? extends V> localInputFuture = this.delegateRef;
        ScheduledFuture<?> localTimer = this.timer;
        if (localInputFuture == null) {
            return null;
        }
        String valueOf = String.valueOf(localInputFuture);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
        sb.append("inputFuture=[");
        sb.append(valueOf);
        sb.append("]");
        String message = sb.toString();
        if (localTimer == null) {
            return message;
        }
        long delay = localTimer.getDelay(TimeUnit.MILLISECONDS);
        if (delay <= 0) {
            return message;
        }
        String valueOf2 = String.valueOf(message);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 43);
        sb2.append(valueOf2);
        sb2.append(", remaining delay=[");
        sb2.append(delay);
        sb2.append(" ms]");
        return sb2.toString();
    }

    /* access modifiers changed from: protected */
    public void afterDone() {
        maybePropagateCancellationTo(this.delegateRef);
        Future<?> localTimer = this.timer;
        if (localTimer != null) {
            localTimer.cancel(false);
        }
        this.delegateRef = null;
        this.timer = null;
    }

    private static final class Fire<V> implements Runnable {
        @NullableDecl
        TimeoutFuture<V> timeoutFutureRef;

        Fire(TimeoutFuture<V> timeoutFuture) {
            this.timeoutFutureRef = timeoutFuture;
        }

        public void run() {
            ListenableFuture<V> delegate;
            TimeoutFuture<V> timeoutFuture = this.timeoutFutureRef;
            if (timeoutFuture != null && (delegate = timeoutFuture.delegateRef) != null) {
                this.timeoutFutureRef = null;
                if (delegate.isDone()) {
                    timeoutFuture.setFuture(delegate);
                    return;
                }
                try {
                    ScheduledFuture<?> timer = timeoutFuture.timer;
                    String message = "Timed out";
                    if (timer != null) {
                        long overDelayMs = Math.abs(timer.getDelay(TimeUnit.MILLISECONDS));
                        if (overDelayMs > 10) {
                            String valueOf = String.valueOf(message);
                            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 66);
                            sb.append(valueOf);
                            sb.append(" (timeout delayed by ");
                            sb.append(overDelayMs);
                            sb.append(" ms after scheduled time)");
                            message = sb.toString();
                        }
                    }
                    ScheduledFuture unused = timeoutFuture.timer = null;
                    String valueOf2 = String.valueOf(delegate);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(message).length() + 2 + String.valueOf(valueOf2).length());
                    sb2.append(message);
                    sb2.append(": ");
                    sb2.append(valueOf2);
                    timeoutFuture.setException(new TimeoutFutureException(sb2.toString()));
                } finally {
                    delegate.cancel(true);
                }
            }
        }
    }

    private static final class TimeoutFutureException extends TimeoutException {
        private TimeoutFutureException(String message) {
            super(message);
        }

        public synchronized Throwable fillInStackTrace() {
            setStackTrace(new StackTraceElement[0]);
            return this;
        }
    }
}
