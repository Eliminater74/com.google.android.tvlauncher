package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

@GwtCompatible(emulated = true)
public final class Futures extends GwtFuturesCatchingSpecialization {
    private Futures() {
    }

    @GwtIncompatible
    @Deprecated
    @Beta
    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> future, Function<? super Exception, X> mapper) {
        return new MappingCheckedFuture((ListenableFuture) Preconditions.checkNotNull(future), mapper);
    }

    public static <V> ListenableFuture<V> immediateFuture(@NullableDecl V value) {
        if (value == null) {
            return ImmediateFuture.ImmediateSuccessfulFuture.NULL;
        }
        return new ImmediateFuture.ImmediateSuccessfulFuture(value);
    }

    @GwtIncompatible
    @Deprecated
    @Beta
    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@NullableDecl V value) {
        return new ImmediateFuture.ImmediateSuccessfulCheckedFuture(value);
    }

    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new ImmediateFuture.ImmediateFailedFuture(throwable);
    }

    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        return new ImmediateFuture.ImmediateCancelledFuture();
    }

    @GwtIncompatible
    @Deprecated
    @Beta
    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(X exception) {
        Preconditions.checkNotNull(exception);
        return new ImmediateFuture.ImmediateFailedCheckedFuture(exception);
    }

    @Beta
    public static <O> ListenableFuture<O> submitAsync(AsyncCallable<O> callable, Executor executor) {
        TrustedListenableFutureTask<O> task = TrustedListenableFutureTask.create(callable);
        executor.execute(task);
        return task;
    }

    @GwtIncompatible
    @Beta
    public static <O> ListenableFuture<O> scheduleAsync(AsyncCallable<O> callable, long delay, TimeUnit timeUnit, ScheduledExecutorService executorService) {
        TrustedListenableFutureTask<O> task = TrustedListenableFutureTask.create(callable);
        final Future<?> scheduled = executorService.schedule(task, delay, timeUnit);
        task.addListener(new Runnable() {
            public void run() {
                scheduled.cancel(false);
            }
        }, MoreExecutors.directExecutor());
        return task;
    }

    @Beta
    @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> input, Class<X> exceptionType, Function<? super X, ? extends V> fallback, Executor executor) {
        return AbstractCatchingFuture.create(input, exceptionType, fallback, executor);
    }

    @Beta
    @Partially.GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> input, Class<X> exceptionType, AsyncFunction<? super X, ? extends V> fallback, Executor executor) {
        return AbstractCatchingFuture.create(input, exceptionType, fallback, executor);
    }

    @GwtIncompatible
    @Beta
    public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> delegate, long time, TimeUnit unit, ScheduledExecutorService scheduledExecutor) {
        if (delegate.isDone()) {
            return delegate;
        }
        return TimeoutFuture.create(delegate, time, unit, scheduledExecutor);
    }

    @Beta
    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function, Executor executor) {
        return AbstractTransformFuture.create(input, function, executor);
    }

    @Beta
    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function, Executor executor) {
        return AbstractTransformFuture.create(input, function, executor);
    }

    @GwtIncompatible
    @Beta
    public static <I, O> Future<O> lazyTransform(final Future<I> input, final Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(function);
        return new Future<O>() {
            public boolean cancel(boolean mayInterruptIfRunning) {
                return input.cancel(mayInterruptIfRunning);
            }

            public boolean isCancelled() {
                return input.isCancelled();
            }

            public boolean isDone() {
                return input.isDone();
            }

            public O get() throws InterruptedException, ExecutionException {
                return applyTransformation(input.get());
            }

            public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return applyTransformation(input.get(timeout, unit));
            }

            private O applyTransformation(I input) throws ExecutionException {
                try {
                    return function.apply(input);
                } catch (Throwable t) {
                    throw new ExecutionException(t);
                }
            }
        };
    }

    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(futures), true);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(futures), true);
    }

    @SafeVarargs
    @Beta
    public static <V> FutureCombiner<V> whenAllComplete(ListenableFuture<? extends V>... futures) {
        return new FutureCombiner<>(false, ImmutableList.copyOf(futures));
    }

    @Beta
    public static <V> FutureCombiner<V> whenAllComplete(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new FutureCombiner<>(false, ImmutableList.copyOf(futures));
    }

    @SafeVarargs
    @Beta
    public static <V> FutureCombiner<V> whenAllSucceed(ListenableFuture<? extends V>... futures) {
        return new FutureCombiner<>(true, ImmutableList.copyOf(futures));
    }

    @Beta
    public static <V> FutureCombiner<V> whenAllSucceed(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new FutureCombiner<>(true, ImmutableList.copyOf(futures));
    }

    @Beta
    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> future) {
        if (future.isDone()) {
            return future;
        }
        NonCancellationPropagatingFuture<V> output = new NonCancellationPropagatingFuture<>(future);
        future.addListener(output, MoreExecutors.directExecutor());
        return output;
    }

    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(futures), false);
    }

    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new CollectionFuture.ListFuture(ImmutableList.copyOf(futures), false);
    }

    /* JADX INFO: Multiple debug info for r5v4 com.google.common.collect.ImmutableList<com.google.common.util.concurrent.AbstractFuture<T>>: [D('i' int), D('delegatesCast' com.google.common.collect.ImmutableList<com.google.common.util.concurrent.ListenableFuture<T>>)] */
    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.Iterable<? extends com.google.common.util.concurrent.ListenableFuture<? extends T>>, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    @com.google.common.annotations.Beta
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.google.common.collect.ImmutableList<com.google.common.util.concurrent.ListenableFuture<T>> inCompletionOrder(java.lang.Iterable<? extends com.google.common.util.concurrent.ListenableFuture<? extends T>> r10) {
        /*
            boolean r0 = r10 instanceof java.util.Collection
            if (r0 == 0) goto L_0x0008
            r0 = r10
            java.util.Collection r0 = (java.util.Collection) r0
            goto L_0x000c
        L_0x0008:
            com.google.common.collect.ImmutableList r0 = com.google.common.collect.ImmutableList.copyOf(r10)
        L_0x000c:
            int r1 = r0.size()
            com.google.common.util.concurrent.ListenableFuture[] r1 = new com.google.common.util.concurrent.ListenableFuture[r1]
            java.lang.Object[] r1 = r0.toArray(r1)
            com.google.common.util.concurrent.ListenableFuture[] r1 = (com.google.common.util.concurrent.ListenableFuture[]) r1
            com.google.common.util.concurrent.Futures$InCompletionOrderState r2 = new com.google.common.util.concurrent.Futures$InCompletionOrderState
            r3 = 0
            r2.<init>(r1)
            com.google.common.collect.ImmutableList$Builder r4 = com.google.common.collect.ImmutableList.builder()
            r5 = 0
        L_0x0024:
            int r6 = r1.length
            if (r5 >= r6) goto L_0x0032
            com.google.common.util.concurrent.Futures$InCompletionOrderFuture r6 = new com.google.common.util.concurrent.Futures$InCompletionOrderFuture
            r6.<init>(r2)
            r4.add(r6)
            int r5 = r5 + 1
            goto L_0x0024
        L_0x0032:
            com.google.common.collect.ImmutableList r3 = r4.build()
            r5 = 0
        L_0x0037:
            int r6 = r1.length
            if (r5 >= r6) goto L_0x004c
            r6 = r5
            r7 = r1[r5]
            com.google.common.util.concurrent.Futures$3 r8 = new com.google.common.util.concurrent.Futures$3
            r8.<init>(r3, r6)
            java.util.concurrent.Executor r9 = com.google.common.util.concurrent.MoreExecutors.directExecutor()
            r7.addListener(r8, r9)
            int r5 = r5 + 1
            goto L_0x0037
        L_0x004c:
            r5 = r3
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Futures.inCompletionOrder(java.lang.Iterable):com.google.common.collect.ImmutableList");
    }

    public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback, Executor executor) {
        Preconditions.checkNotNull(callback);
        future.addListener(new CallbackListener(future, callback), executor);
    }

    @CanIgnoreReturnValue
    public static <V> V getDone(Future<V> future) throws ExecutionException {
        Preconditions.checkState(future.isDone(), "Future was expected to be done: %s", future);
        return Uninterruptibles.getUninterruptibly(future);
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> exceptionClass) throws Exception {
        return FuturesGetChecked.getChecked(future, exceptionClass);
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> exceptionClass, long timeout, TimeUnit unit) throws Exception {
        return FuturesGetChecked.getChecked(future, exceptionClass, timeout, unit);
    }

    @CanIgnoreReturnValue
    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException e) {
            wrapAndThrowUnchecked(e.getCause());
            throw new AssertionError();
        }
    }

    private static void wrapAndThrowUnchecked(Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        throw new UncheckedExecutionException(cause);
    }

    @GwtCompatible
    @CanIgnoreReturnValue
    @Beta
    public static final class FutureCombiner<V> {
        private final boolean allMustSucceed;
        private final ImmutableList<ListenableFuture<? extends V>> futures;

        private FutureCombiner(boolean allMustSucceed2, ImmutableList<ListenableFuture<? extends V>> futures2) {
            this.allMustSucceed = allMustSucceed2;
            this.futures = futures2;
        }

        public <C> ListenableFuture<C> callAsync(AsyncCallable<C> combiner, Executor executor) {
            return new CombinedFuture(this.futures, this.allMustSucceed, executor, combiner);
        }

        @CanIgnoreReturnValue
        public <C> ListenableFuture<C> call(Callable<C> combiner, Executor executor) {
            return new CombinedFuture(this.futures, this.allMustSucceed, executor, combiner);
        }

        public ListenableFuture<?> run(final Runnable combiner, Executor executor) {
            return call(new Callable<Void>(this) {
                public Void call() throws Exception {
                    combiner.run();
                    return null;
                }
            }, executor);
        }
    }

    private static final class NonCancellationPropagatingFuture<V> extends AbstractFuture.TrustedFuture<V> implements Runnable {
        private ListenableFuture<V> delegate;

        NonCancellationPropagatingFuture(ListenableFuture<V> delegate2) {
            this.delegate = delegate2;
        }

        public void run() {
            ListenableFuture<V> localDelegate = this.delegate;
            if (localDelegate != null) {
                setFuture(localDelegate);
            }
        }

        /* access modifiers changed from: protected */
        public String pendingToString() {
            ListenableFuture<V> localDelegate = this.delegate;
            if (localDelegate == null) {
                return null;
            }
            String valueOf = String.valueOf(localDelegate);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 11);
            sb.append("delegate=[");
            sb.append(valueOf);
            sb.append("]");
            return sb.toString();
        }

        /* access modifiers changed from: protected */
        public void afterDone() {
            this.delegate = null;
        }
    }

    private static final class InCompletionOrderFuture<T> extends AbstractFuture<T> {
        private InCompletionOrderState<T> state;

        private InCompletionOrderFuture(InCompletionOrderState<T> state2) {
            this.state = state2;
        }

        public boolean cancel(boolean interruptIfRunning) {
            InCompletionOrderState<T> localState = this.state;
            if (!super.cancel(interruptIfRunning)) {
                return false;
            }
            localState.recordOutputCancellation(interruptIfRunning);
            return true;
        }

        /* access modifiers changed from: protected */
        public void afterDone() {
            this.state = null;
        }

        /* access modifiers changed from: protected */
        public String pendingToString() {
            InCompletionOrderState<T> localState = this.state;
            if (localState == null) {
                return null;
            }
            int length = localState.inputFutures.length;
            int i = localState.incompleteOutputCount.get();
            StringBuilder sb = new StringBuilder(49);
            sb.append("inputCount=[");
            sb.append(length);
            sb.append("], remaining=[");
            sb.append(i);
            sb.append("]");
            return sb.toString();
        }
    }

    private static final class InCompletionOrderState<T> {
        /* access modifiers changed from: private */
        public final AtomicInteger incompleteOutputCount;
        /* access modifiers changed from: private */
        public final ListenableFuture<? extends T>[] inputFutures;
        private volatile int delegateIndex;
        private boolean shouldInterrupt;
        private boolean wasCancelled;

        private InCompletionOrderState(ListenableFuture<? extends T>[] inputFutures2) {
            this.wasCancelled = false;
            this.shouldInterrupt = true;
            this.delegateIndex = 0;
            this.inputFutures = inputFutures2;
            this.incompleteOutputCount = new AtomicInteger(inputFutures2.length);
        }

        /* access modifiers changed from: private */
        public void recordOutputCancellation(boolean interruptIfRunning) {
            this.wasCancelled = true;
            if (!interruptIfRunning) {
                this.shouldInterrupt = false;
            }
            recordCompletion();
        }

        /* access modifiers changed from: private */
        public void recordInputCompletion(ImmutableList<AbstractFuture<T>> delegates, int inputFutureIndex) {
            ListenableFuture<? extends T>[] listenableFutureArr = this.inputFutures;
            ListenableFuture<? extends T> inputFuture = listenableFutureArr[inputFutureIndex];
            listenableFutureArr[inputFutureIndex] = null;
            for (int i = this.delegateIndex; i < delegates.size(); i++) {
                if (delegates.get(i).setFuture(inputFuture)) {
                    recordCompletion();
                    this.delegateIndex = i + 1;
                    return;
                }
            }
            this.delegateIndex = delegates.size();
        }

        private void recordCompletion() {
            if (this.incompleteOutputCount.decrementAndGet() == 0 && this.wasCancelled) {
                for (ListenableFuture<?> toCancel : this.inputFutures) {
                    if (toCancel != null) {
                        toCancel.cancel(this.shouldInterrupt);
                    }
                }
            }
        }
    }

    private static final class CallbackListener<V> implements Runnable {
        final FutureCallback<? super V> callback;
        final Future<V> future;

        CallbackListener(Future<V> future2, FutureCallback<? super V> callback2) {
            this.future = future2;
            this.callback = callback2;
        }

        public void run() {
            try {
                this.callback.onSuccess(Futures.getDone(this.future));
            } catch (ExecutionException e) {
                this.callback.onFailure(e.getCause());
            } catch (Error | RuntimeException e2) {
                this.callback.onFailure(e2);
            }
        }

        public String toString() {
            return MoreObjects.toStringHelper(this).addValue(this.callback).toString();
        }
    }

    @GwtIncompatible
    private static class MappingCheckedFuture<V, X extends Exception> extends AbstractCheckedFuture<V, X> {
        final Function<? super Exception, X> mapper;

        MappingCheckedFuture(ListenableFuture<V> delegate, Function<? super Exception, X> mapper2) {
            super(delegate);
            this.mapper = (Function) Preconditions.checkNotNull(mapper2);
        }

        /* access modifiers changed from: protected */
        public X mapException(Exception e) {
            return (Exception) this.mapper.apply(e);
        }
    }
}
