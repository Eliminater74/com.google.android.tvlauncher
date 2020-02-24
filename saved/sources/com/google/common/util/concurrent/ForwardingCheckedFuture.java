package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.Exception;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Deprecated
@Beta
public abstract class ForwardingCheckedFuture<V, X extends Exception> extends ForwardingListenableFuture<V> implements CheckedFuture<V, X> {
    /* access modifiers changed from: protected */
    public abstract CheckedFuture<V, X> delegate();

    @CanIgnoreReturnValue
    public V checkedGet() throws Exception {
        return delegate().checkedGet();
    }

    @CanIgnoreReturnValue
    public V checkedGet(long timeout, TimeUnit unit) throws TimeoutException, Exception {
        return delegate().checkedGet(timeout, unit);
    }

    @Deprecated
    @Beta
    public static abstract class SimpleForwardingCheckedFuture<V, X extends Exception> extends ForwardingCheckedFuture<V, X> {
        private final CheckedFuture<V, X> delegate;

        protected SimpleForwardingCheckedFuture(CheckedFuture<V, X> delegate2) {
            this.delegate = (CheckedFuture) Preconditions.checkNotNull(delegate2);
        }

        /* access modifiers changed from: protected */
        public final CheckedFuture<V, X> delegate() {
            return this.delegate;
        }
    }
}
