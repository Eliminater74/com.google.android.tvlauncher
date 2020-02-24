package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;

@GwtCompatible
@CanIgnoreReturnValue
public abstract class ForwardingListenableFuture<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
    /* access modifiers changed from: protected */
    public abstract ListenableFuture<? extends V> delegate();

    protected ForwardingListenableFuture() {
    }

    public void addListener(Runnable listener, Executor exec) {
        delegate().addListener(listener, exec);
    }

    public static abstract class SimpleForwardingListenableFuture<V> extends ForwardingListenableFuture<V> {
        private final ListenableFuture<V> delegate;

        protected SimpleForwardingListenableFuture(ListenableFuture<V> delegate2) {
            this.delegate = (ListenableFuture) Preconditions.checkNotNull(delegate2);
        }

        /* access modifiers changed from: protected */
        public final ListenableFuture<V> delegate() {
            return this.delegate;
        }
    }
}
