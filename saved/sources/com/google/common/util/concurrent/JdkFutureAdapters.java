package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

@GwtIncompatible
@Beta
public final class JdkFutureAdapters {
    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future) {
        if (future instanceof ListenableFuture) {
            return (ListenableFuture) future;
        }
        return new ListenableFutureAdapter(future);
    }

    public static <V> ListenableFuture<V> listenInPoolThread(Future<V> future, Executor executor) {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture) {
            return (ListenableFuture) future;
        }
        return new ListenableFutureAdapter(future, executor);
    }

    private static class ListenableFutureAdapter<V> extends ForwardingFuture<V> implements ListenableFuture<V> {
        private static final Executor defaultAdapterExecutor = Executors.newCachedThreadPool(threadFactory);
        private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat("ListenableFutureAdapter-thread-%d").build();
        private final Executor adapterExecutor;
        /* access modifiers changed from: private */
        public final Future<V> delegate;
        /* access modifiers changed from: private */
        public final ExecutionList executionList;
        private final AtomicBoolean hasListeners;

        ListenableFutureAdapter(Future<V> delegate2) {
            this(delegate2, defaultAdapterExecutor);
        }

        ListenableFutureAdapter(Future<V> delegate2, Executor adapterExecutor2) {
            this.executionList = new ExecutionList();
            this.hasListeners = new AtomicBoolean(false);
            this.delegate = (Future) Preconditions.checkNotNull(delegate2);
            this.adapterExecutor = (Executor) Preconditions.checkNotNull(adapterExecutor2);
        }

        /* access modifiers changed from: protected */
        public Future<V> delegate() {
            return this.delegate;
        }

        public void addListener(Runnable listener, Executor exec) {
            this.executionList.add(listener, exec);
            if (!this.hasListeners.compareAndSet(false, true)) {
                return;
            }
            if (this.delegate.isDone()) {
                this.executionList.execute();
            } else {
                this.adapterExecutor.execute(new Runnable() {
                    public void run() {
                        try {
                            Uninterruptibles.getUninterruptibly(ListenableFutureAdapter.this.delegate);
                        } catch (Throwable th) {
                        }
                        ListenableFutureAdapter.this.executionList.execute();
                    }
                });
            }
        }
    }

    private JdkFutureAdapters() {
    }
}
