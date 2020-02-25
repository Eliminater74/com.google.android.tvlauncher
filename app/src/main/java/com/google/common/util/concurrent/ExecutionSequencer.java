package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

@Beta
public final class ExecutionSequencer {
    private final AtomicReference<ListenableFuture<Object>> ref = new AtomicReference<>(Futures.immediateFuture(null));

    private ExecutionSequencer() {
    }

    public static ExecutionSequencer create() {
        return new ExecutionSequencer();
    }

    public <T> ListenableFuture<T> submit(final Callable<T> callable, Executor executor) {
        Preconditions.checkNotNull(callable);
        return submitAsync(new AsyncCallable<T>(this) {
            public ListenableFuture<T> call() throws Exception {
                return Futures.immediateFuture(callable.call());
            }

            public String toString() {
                return callable.toString();
            }
        }, executor);
    }

    public <T> ListenableFuture<T> submitAsync(AsyncCallable<T> callable, Executor executor) {
        Preconditions.checkNotNull(callable);
        final AtomicReference<RunningState> runningState = new AtomicReference<>(RunningState.NOT_RUN);
        final AsyncCallable<T> asyncCallable = callable;
        C17862 r10 = new AsyncCallable<T>(this) {
            public ListenableFuture<T> call() throws Exception {
                if (!runningState.compareAndSet(RunningState.NOT_RUN, RunningState.STARTED)) {
                    return Futures.immediateCancelledFuture();
                }
                return asyncCallable.call();
            }

            public String toString() {
                return asyncCallable.toString();
            }
        };
        SettableFuture<Object> newFuture = SettableFuture.create();
        final ListenableFuture<?> oldFuture = this.ref.getAndSet(newFuture);
        final Executor executor2 = executor;
        ListenableFuture<T> taskFuture = Futures.submitAsync(r10, new Executor(this) {
            public void execute(Runnable runnable) {
                oldFuture.addListener(runnable, executor2);
            }
        });
        ListenableFuture<T> outputFuture = Futures.nonCancellationPropagating(taskFuture);
        final ListenableFuture<T> listenableFuture = taskFuture;
        final ListenableFuture<T> listenableFuture2 = outputFuture;
        final AtomicReference<RunningState> atomicReference = runningState;
        final SettableFuture<Object> settableFuture = newFuture;
        final ListenableFuture<?> listenableFuture3 = oldFuture;
        Runnable listener = new Runnable(this) {
            public void run() {
                if (listenableFuture.isDone() || (listenableFuture2.isCancelled() && atomicReference.compareAndSet(RunningState.NOT_RUN, RunningState.CANCELLED))) {
                    settableFuture.setFuture(listenableFuture3);
                }
            }
        };
        outputFuture.addListener(listener, MoreExecutors.directExecutor());
        taskFuture.addListener(listener, MoreExecutors.directExecutor());
        return outputFuture;
    }

    enum RunningState {
        NOT_RUN,
        CANCELLED,
        STARTED
    }
}
