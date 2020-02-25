package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible(emulated = true)
abstract class ImmediateFuture<V> implements ListenableFuture<V> {
    private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());

    ImmediateFuture() {
    }

    public abstract V get() throws ExecutionException;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.RuntimeException]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    public void addListener(Runnable listener, Executor executor) {
        Preconditions.checkNotNull(listener, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        try {
            executor.execute(listener);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            String valueOf = String.valueOf(listener);
            String valueOf2 = String.valueOf(executor);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 57 + String.valueOf(valueOf2).length());
            sb.append("RuntimeException while executing runnable ");
            sb.append(valueOf);
            sb.append(" with executor ");
            sb.append(valueOf2);
            logger.logp(level, "com.google.common.util.concurrent.ImmediateFuture", "addListener", sb.toString(), (Throwable) e);
        }
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    public V get(long timeout, TimeUnit unit) throws ExecutionException {
        Preconditions.checkNotNull(unit);
        return get();
    }

    public boolean isCancelled() {
        return false;
    }

    public boolean isDone() {
        return true;
    }

    static class ImmediateSuccessfulFuture<V> extends ImmediateFuture<V> {
        static final ImmediateSuccessfulFuture<Object> NULL = new ImmediateSuccessfulFuture<>(null);
        @NullableDecl
        private final V value;

        ImmediateSuccessfulFuture(@NullableDecl V value2) {
            this.value = value2;
        }

        public V get() {
            return this.value;
        }

        public String toString() {
            String obj = super.toString();
            String valueOf = String.valueOf(this.value);
            StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 27 + String.valueOf(valueOf).length());
            sb.append(obj);
            sb.append("[status=SUCCESS, result=[");
            sb.append(valueOf);
            sb.append("]]");
            return sb.toString();
        }
    }

    @GwtIncompatible
    static class ImmediateSuccessfulCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        @NullableDecl
        private final V value;

        ImmediateSuccessfulCheckedFuture(@NullableDecl V value2) {
            this.value = value2;
        }

        public V get() {
            return this.value;
        }

        public V checkedGet() {
            return this.value;
        }

        public V checkedGet(long timeout, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            return this.value;
        }

        public String toString() {
            String obj = super.toString();
            String valueOf = String.valueOf(this.value);
            StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 27 + String.valueOf(valueOf).length());
            sb.append(obj);
            sb.append("[status=SUCCESS, result=[");
            sb.append(valueOf);
            sb.append("]]");
            return sb.toString();
        }
    }

    static final class ImmediateFailedFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateFailedFuture(Throwable thrown) {
            setException(thrown);
        }
    }

    static final class ImmediateCancelledFuture<V> extends AbstractFuture.TrustedFuture<V> {
        ImmediateCancelledFuture() {
            cancel(false);
        }
    }

    @GwtIncompatible
    static class ImmediateFailedCheckedFuture<V, X extends Exception> extends ImmediateFuture<V> implements CheckedFuture<V, X> {
        private final X thrown;

        ImmediateFailedCheckedFuture(X thrown2) {
            this.thrown = thrown2;
        }

        public V get() throws ExecutionException {
            throw new ExecutionException((Throwable) this.thrown);
        }

        public V checkedGet() throws Exception {
            throw this.thrown;
        }

        public V checkedGet(long timeout, TimeUnit unit) throws Exception {
            Preconditions.checkNotNull(unit);
            throw this.thrown;
        }

        public String toString() {
            String obj = super.toString();
            String valueOf = String.valueOf(this.thrown);
            StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 26 + String.valueOf(valueOf).length());
            sb.append(obj);
            sb.append("[status=FAILURE, cause=[");
            sb.append(valueOf);
            sb.append("]]");
            return sb.toString();
        }
    }
}
