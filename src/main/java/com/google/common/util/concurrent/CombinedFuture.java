package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
final class CombinedFuture<V> extends AggregateFuture<Object, V> {
    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> futures, boolean allMustSucceed, Executor listenerExecutor, AsyncCallable asyncCallable) {
        init(new CombinedFutureRunningState(futures, allMustSucceed, new AsyncCallableInterruptibleTask(asyncCallable, listenerExecutor)));
    }

    CombinedFuture(ImmutableCollection<? extends ListenableFuture<?>> futures, boolean allMustSucceed, Executor listenerExecutor, Callable callable) {
        init(new CombinedFutureRunningState(futures, allMustSucceed, new CallableInterruptibleTask(callable, listenerExecutor)));
    }

    private final class CombinedFutureRunningState extends AggregateFuture<Object, V>.RunningState {
        private CombinedFutureInterruptibleTask task;

        CombinedFutureRunningState(ImmutableCollection<? extends ListenableFuture<?>> futures, boolean allMustSucceed, CombinedFutureInterruptibleTask task2) {
            super(futures, allMustSucceed, false);
            this.task = task2;
        }

        /* access modifiers changed from: package-private */
        public void collectOneValue(boolean allMustSucceed, int index, @NullableDecl Object returnValue) {
        }

        /* access modifiers changed from: package-private */
        public void handleAllCompleted() {
            CombinedFutureInterruptibleTask localTask = this.task;
            if (localTask != null) {
                localTask.execute();
            } else {
                Preconditions.checkState(CombinedFuture.this.isDone());
            }
        }

        /* access modifiers changed from: package-private */
        public void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.task = null;
        }

        /* access modifiers changed from: package-private */
        public void interruptTask() {
            CombinedFutureInterruptibleTask localTask = this.task;
            if (localTask != null) {
                localTask.interruptTask();
            }
        }
    }

    private abstract class CombinedFutureInterruptibleTask<T> extends InterruptibleTask<T> {
        private final Executor listenerExecutor;
        boolean thrownByExecute = true;

        /* access modifiers changed from: package-private */
        public abstract void setValue(T t);

        public CombinedFutureInterruptibleTask(Executor listenerExecutor2) {
            this.listenerExecutor = (Executor) Preconditions.checkNotNull(listenerExecutor2);
        }

        /* access modifiers changed from: package-private */
        public final boolean isDone() {
            return CombinedFuture.this.isDone();
        }

        /* access modifiers changed from: package-private */
        public final void execute() {
            try {
                this.listenerExecutor.execute(this);
            } catch (RejectedExecutionException e) {
                if (this.thrownByExecute) {
                    CombinedFuture.this.setException(e);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public final void afterRanInterruptibly(T result, Throwable error) {
            if (error == null) {
                setValue(result);
            } else if (error instanceof ExecutionException) {
                CombinedFuture.this.setException(error.getCause());
            } else if (error instanceof CancellationException) {
                CombinedFuture.this.cancel(false);
            } else {
                CombinedFuture.this.setException(error);
            }
        }
    }

    private final class AsyncCallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask<ListenableFuture<V>> {
        private final AsyncCallable<V> callable;

        public AsyncCallableInterruptibleTask(AsyncCallable<V> callable2, Executor listenerExecutor) {
            super(listenerExecutor);
            this.callable = (AsyncCallable) Preconditions.checkNotNull(callable2);
        }

        /* access modifiers changed from: package-private */
        public ListenableFuture<V> runInterruptibly() throws Exception {
            this.thrownByExecute = false;
            return (ListenableFuture) Preconditions.checkNotNull(this.callable.call(), "AsyncCallable.call returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", this.callable);
        }

        /* access modifiers changed from: package-private */
        public void setValue(ListenableFuture<V> value) {
            CombinedFuture.this.setFuture(value);
        }

        /* access modifiers changed from: package-private */
        public String toPendingString() {
            return this.callable.toString();
        }
    }

    private final class CallableInterruptibleTask extends CombinedFuture<V>.CombinedFutureInterruptibleTask<V> {
        private final Callable<V> callable;

        public CallableInterruptibleTask(Callable<V> callable2, Executor listenerExecutor) {
            super(listenerExecutor);
            this.callable = (Callable) Preconditions.checkNotNull(callable2);
        }

        /* access modifiers changed from: package-private */
        public V runInterruptibly() throws Exception {
            this.thrownByExecute = false;
            return this.callable.call();
        }

        /* access modifiers changed from: package-private */
        public void setValue(V value) {
            CombinedFuture.this.set(value);
        }

        /* access modifiers changed from: package-private */
        public String toPendingString() {
            return this.callable.toString();
        }
    }
}
