package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.util.concurrent.AbstractFuture;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
abstract class AggregateFuture<InputT, OutputT> extends AbstractFuture.TrustedFuture<OutputT> {
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    /* access modifiers changed from: private */
    @NullableDecl
    public AggregateFuture<InputT, OutputT>.RunningState runningState;

    AggregateFuture() {
    }

    /* access modifiers changed from: protected */
    public final void afterDone() {
        super.afterDone();
        AggregateFuture<InputT, OutputT>.RunningState localRunningState = this.runningState;
        if (localRunningState != null) {
            this.runningState = null;
            ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures = localRunningState.futures;
            boolean wasInterrupted = wasInterrupted();
            if (wasInterrupted) {
                localRunningState.interruptTask();
            }
            if (isCancelled() && (futures != null)) {
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = futures.iterator();
                while (it.hasNext()) {
                    ((ListenableFuture) it.next()).cancel(wasInterrupted);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public String pendingToString() {
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> localFutures;
        AggregateFuture<InputT, OutputT>.RunningState localRunningState = this.runningState;
        if (localRunningState == null || (localFutures = localRunningState.futures) == null) {
            return null;
        }
        String valueOf = String.valueOf(localFutures);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 10);
        sb.append("futures=[");
        sb.append(valueOf);
        sb.append("]");
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public final void init(AggregateFuture<InputT, OutputT>.RunningState runningState2) {
        this.runningState = runningState2;
        runningState2.init();
    }

    abstract class RunningState extends AggregateFutureState implements Runnable {
        private final boolean allMustSucceed;
        private final boolean collectsValues;
        /* access modifiers changed from: private */
        public ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

        /* access modifiers changed from: package-private */
        public abstract void collectOneValue(boolean z, int i, @NullableDecl InputT inputt);

        /* access modifiers changed from: package-private */
        public abstract void handleAllCompleted();

        RunningState(ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures2, boolean allMustSucceed2, boolean collectsValues2) {
            super(futures2.size());
            this.futures = (ImmutableCollection) Preconditions.checkNotNull(futures2);
            this.allMustSucceed = allMustSucceed2;
            this.collectsValues = collectsValues2;
        }

        public final void run() {
            decrementCountAndMaybeComplete();
        }

        /* JADX INFO: Multiple debug info for r0v6 'i'  int: [D('i' int), D('index' int)] */
        /* access modifiers changed from: private */
        public void init() {
            if (this.futures.isEmpty()) {
                handleAllCompleted();
            } else if (this.allMustSucceed) {
                final int index = 0;
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = this.futures.iterator();
                while (it.hasNext()) {
                    final ListenableFuture<? extends InputT> listenable = (ListenableFuture) it.next();
                    listenable.addListener(new Runnable() {
                        public void run() {
                            try {
                                RunningState.this.handleOneInputDone(index, listenable);
                            } finally {
                                RunningState.this.decrementCountAndMaybeComplete();
                            }
                        }
                    }, MoreExecutors.directExecutor());
                    index++;
                }
            } else {
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it2 = this.futures.iterator();
                while (it2.hasNext()) {
                    ((ListenableFuture) it2.next()).addListener(this, MoreExecutors.directExecutor());
                }
            }
        }

        private void handleException(Throwable throwable) {
            String message;
            Preconditions.checkNotNull(throwable);
            boolean completedWithFailure = false;
            boolean firstTimeSeeingThisException = true;
            if (this.allMustSucceed) {
                completedWithFailure = AggregateFuture.this.setException(throwable);
                if (completedWithFailure) {
                    releaseResourcesAfterFailure();
                } else {
                    firstTimeSeeingThisException = AggregateFuture.addCausalChain(getOrInitSeenExceptions(), throwable);
                }
            }
            if ((throwable instanceof Error) || (this.allMustSucceed & (!completedWithFailure) & firstTimeSeeingThisException)) {
                if (throwable instanceof Error) {
                    message = "Input Future failed with Error";
                } else {
                    message = "Got more than one input Future failure. Logging failures after the first";
                }
                AggregateFuture.logger.logp(Level.SEVERE, "com.google.common.util.concurrent.AggregateFuture$RunningState", "handleException", message, throwable);
            }
        }

        /* access modifiers changed from: package-private */
        public final void addInitialException(Set<Throwable> seen) {
            if (!AggregateFuture.this.isCancelled()) {
                boolean unused = AggregateFuture.addCausalChain(seen, AggregateFuture.this.tryInternalFastPathGetFailure());
            }
        }

        /* access modifiers changed from: private */
        public void handleOneInputDone(int index, Future<? extends InputT> future) {
            Preconditions.checkState(this.allMustSucceed || !AggregateFuture.this.isDone() || AggregateFuture.this.isCancelled(), "Future was done before all dependencies completed");
            try {
                Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
                if (this.allMustSucceed) {
                    if (future.isCancelled()) {
                        RunningState unused = AggregateFuture.this.runningState = null;
                        AggregateFuture.this.cancel(false);
                        return;
                    }
                    InputT result = Futures.getDone(future);
                    if (this.collectsValues) {
                        collectOneValue(this.allMustSucceed, index, result);
                    }
                } else if (this.collectsValues && !future.isCancelled()) {
                    collectOneValue(this.allMustSucceed, index, Futures.getDone(future));
                }
            } catch (ExecutionException e) {
                handleException(e.getCause());
            } catch (Throwable t) {
                handleException(t);
            }
        }

        /* access modifiers changed from: private */
        public void decrementCountAndMaybeComplete() {
            int newRemaining = decrementRemainingAndGet();
            Preconditions.checkState(newRemaining >= 0, "Less than 0 remaining futures");
            if (newRemaining == 0) {
                processCompleted();
            }
        }

        private void processCompleted() {
            if (this.collectsValues && (!this.allMustSucceed)) {
                int i = 0;
                UnmodifiableIterator<? extends ListenableFuture<? extends InputT>> it = this.futures.iterator();
                while (it.hasNext()) {
                    handleOneInputDone(i, (ListenableFuture) it.next());
                    i++;
                }
            }
            handleAllCompleted();
        }

        /* access modifiers changed from: package-private */
        @ForOverride
        @OverridingMethodsMustInvokeSuper
        public void releaseResourcesAfterFailure() {
            this.futures = null;
        }

        /* access modifiers changed from: package-private */
        public void interruptTask() {
        }
    }

    /* access modifiers changed from: private */
    public static boolean addCausalChain(Set<Throwable> seen, Throwable t) {
        while (t != null) {
            if (!seen.add(t)) {
                return false;
            }
            t = t.getCause();
        }
        return true;
    }
}
