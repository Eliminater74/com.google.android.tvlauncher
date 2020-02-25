package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

@GwtIncompatible
@CanIgnoreReturnValue
@Beta
public abstract class AbstractListeningExecutorService extends AbstractExecutorService implements ListeningExecutorService {
    /* access modifiers changed from: protected */
    public final <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return TrustedListenableFutureTask.create(runnable, value);
    }

    /* access modifiers changed from: protected */
    public final <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return TrustedListenableFutureTask.create(callable);
    }

    public ListenableFuture<?> submit(Runnable task) {
        return (ListenableFuture) super.submit(task);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{<T> java.util.concurrent.AbstractExecutorService.submit(java.lang.Runnable, java.lang.Object):java.util.concurrent.Future<T>}
     arg types: [java.lang.Runnable, T]
     candidates:
      com.google.common.util.concurrent.AbstractListeningExecutorService.submit(java.lang.Runnable, java.lang.Object):com.google.common.util.concurrent.ListenableFuture<T>
      com.google.common.util.concurrent.ListeningExecutorService.submit(java.lang.Runnable, java.lang.Object):com.google.common.util.concurrent.ListenableFuture<T>
      ClspMth{<T> java.util.concurrent.AbstractExecutorService.submit(java.lang.Runnable, java.lang.Object):java.util.concurrent.Future<T>} */
    public <T> ListenableFuture<T> submit(Runnable task, @NullableDecl T result) {
        return (ListenableFuture) super.submit(task, (Object) result);
    }

    public <T> ListenableFuture<T> submit(Callable<T> task) {
        return (ListenableFuture) super.submit((Callable) task);
    }
}
