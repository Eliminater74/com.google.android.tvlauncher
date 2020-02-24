package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtCompatible
@CanIgnoreReturnValue
public abstract class ForwardingFuture<V> extends ForwardingObject implements Future<V> {
    /* access modifiers changed from: protected */
    public abstract Future<? extends V> delegate();

    protected ForwardingFuture() {
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return delegate().cancel(mayInterruptIfRunning);
    }

    public boolean isCancelled() {
        return delegate().isCancelled();
    }

    public boolean isDone() {
        return delegate().isDone();
    }

    public V get() throws InterruptedException, ExecutionException {
        return delegate().get();
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return delegate().get(timeout, unit);
    }

    public static abstract class SimpleForwardingFuture<V> extends ForwardingFuture<V> {
        private final Future<V> delegate;

        protected SimpleForwardingFuture(Future<V> delegate2) {
            this.delegate = (Future) Preconditions.checkNotNull(delegate2);
        }

        /* access modifiers changed from: protected */
        public final Future<V> delegate() {
            return this.delegate;
        }
    }
}
