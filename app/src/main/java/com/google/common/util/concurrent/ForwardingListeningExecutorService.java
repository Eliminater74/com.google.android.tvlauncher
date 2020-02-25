package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.concurrent.Callable;

@GwtIncompatible
@CanIgnoreReturnValue
public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService {
    protected ForwardingListeningExecutorService() {
    }

    /* access modifiers changed from: protected */
    public abstract ListeningExecutorService delegate();

    public <T> ListenableFuture<T> submit(Callable<T> task) {
        return delegate().submit((Callable) task);
    }

    public ListenableFuture<?> submit(Runnable task) {
        return delegate().submit(task);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.util.concurrent.ListeningExecutorService.submit(java.lang.Runnable, java.lang.Object):com.google.common.util.concurrent.ListenableFuture<T>
     arg types: [java.lang.Runnable, T]
     candidates:
      ClspMth{<T> java.util.concurrent.ExecutorService.submit(java.lang.Runnable, java.lang.Object):java.util.concurrent.Future<T>}
      com.google.common.util.concurrent.ListeningExecutorService.submit(java.lang.Runnable, java.lang.Object):com.google.common.util.concurrent.ListenableFuture<T> */
    public <T> ListenableFuture<T> submit(Runnable task, T result) {
        return delegate().submit(task, (Object) result);
    }
}
