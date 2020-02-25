package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.ForOverride;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.Executor;

@GwtCompatible
abstract class AbstractCatchingFuture<V, X extends Throwable, F, T> extends FluentFuture.TrustedFuture<V> implements Runnable {
    @NullableDecl
    Class<X> exceptionType;
    @NullableDecl
    F fallback;
    @NullableDecl
    ListenableFuture<? extends V> inputFuture;

    AbstractCatchingFuture(ListenableFuture<? extends V> inputFuture2, Class<X> exceptionType2, F fallback2) {
        this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(inputFuture2);
        this.exceptionType = (Class) Preconditions.checkNotNull(exceptionType2);
        this.fallback = Preconditions.checkNotNull(fallback2);
    }

    static <V, X extends Throwable> ListenableFuture<V> create(ListenableFuture<? extends V> input, Class<X> exceptionType2, Function<? super X, ? extends V> fallback2, Executor executor) {
        CatchingFuture<V, X> future = new CatchingFuture<>(input, exceptionType2, fallback2);
        input.addListener(future, MoreExecutors.rejectionPropagatingExecutor(executor, future));
        return future;
    }

    static <X extends Throwable, V> ListenableFuture<V> create(ListenableFuture<? extends V> input, Class<X> exceptionType2, AsyncFunction<? super X, ? extends V> fallback2, Executor executor) {
        AsyncCatchingFuture<V, X> future = new AsyncCatchingFuture<>(input, exceptionType2, fallback2);
        input.addListener(future, MoreExecutors.rejectionPropagatingExecutor(executor, future));
        return future;
    }

    /* access modifiers changed from: package-private */
    @ForOverride
    @NullableDecl
    public abstract T doFallback(F f, X x) throws Exception;

    /* access modifiers changed from: package-private */
    @ForOverride
    public abstract void setResult(@NullableDecl T t);

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r8 = this;
            com.google.common.util.concurrent.ListenableFuture<? extends V> r0 = r8.inputFuture
            java.lang.Class<X> r1 = r8.exceptionType
            F r2 = r8.fallback
            r3 = 1
            r4 = 0
            if (r0 != 0) goto L_0x000c
            r5 = 1
            goto L_0x000d
        L_0x000c:
            r5 = 0
        L_0x000d:
            if (r1 != 0) goto L_0x0011
            r6 = 1
            goto L_0x0012
        L_0x0011:
            r6 = 0
        L_0x0012:
            r5 = r5 | r6
            if (r2 != 0) goto L_0x0016
            goto L_0x0017
        L_0x0016:
            r3 = 0
        L_0x0017:
            r3 = r3 | r5
            boolean r4 = r8.isCancelled()
            r3 = r3 | r4
            if (r3 == 0) goto L_0x0020
            return
        L_0x0020:
            r3 = 0
            r8.inputFuture = r3
            r4 = 0
            r5 = 0
            java.lang.Object r6 = com.google.common.util.concurrent.Futures.getDone(r0)     // Catch:{ ExecutionException -> 0x002e, all -> 0x002b }
            r4 = r6
        L_0x002a:
            goto L_0x003b
        L_0x002b:
            r6 = move-exception
            r5 = r6
            goto L_0x003b
        L_0x002e:
            r6 = move-exception
            java.lang.Throwable r7 = r6.getCause()
            java.lang.Object r7 = com.google.common.base.Preconditions.checkNotNull(r7)
            r5 = r7
            java.lang.Throwable r5 = (java.lang.Throwable) r5
            goto L_0x002a
        L_0x003b:
            if (r5 != 0) goto L_0x0041
            r8.set(r4)
            return
        L_0x0041:
            boolean r6 = com.google.common.util.concurrent.Platform.isInstanceOfThrowableClass(r5, r1)
            if (r6 != 0) goto L_0x004b
            r8.setFuture(r0)
            return
        L_0x004b:
            r6 = r5
            java.lang.Object r7 = r8.doFallback(r2, r6)     // Catch:{ all -> 0x0059 }
            r8.exceptionType = r3
            r8.fallback = r3
            r8.setResult(r7)
            return
        L_0x0059:
            r7 = move-exception
            r8.setException(r7)     // Catch:{ all -> 0x0062 }
            r8.exceptionType = r3
            r8.fallback = r3
            return
        L_0x0062:
            r7 = move-exception
            r8.exceptionType = r3
            r8.fallback = r3
            throw r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractCatchingFuture.run():void");
    }

    /* access modifiers changed from: protected */
    public String pendingToString() {
        ListenableFuture<? extends V> localInputFuture = this.inputFuture;
        Class<X> localExceptionType = this.exceptionType;
        F localFallback = this.fallback;
        String superString = super.pendingToString();
        String resultString = "";
        if (localInputFuture != null) {
            String valueOf = String.valueOf(localInputFuture);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 16);
            sb.append("inputFuture=[");
            sb.append(valueOf);
            sb.append("], ");
            resultString = sb.toString();
        }
        if (localExceptionType != null && localFallback != null) {
            String valueOf2 = String.valueOf(localExceptionType);
            String valueOf3 = String.valueOf(localFallback);
            StringBuilder sb2 = new StringBuilder(String.valueOf(resultString).length() + 29 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
            sb2.append(resultString);
            sb2.append("exceptionType=[");
            sb2.append(valueOf2);
            sb2.append("], fallback=[");
            sb2.append(valueOf3);
            sb2.append("]");
            return sb2.toString();
        } else if (superString == null) {
            return null;
        } else {
            String valueOf4 = String.valueOf(resultString);
            String valueOf5 = String.valueOf(superString);
            return valueOf5.length() != 0 ? valueOf4.concat(valueOf5) : new String(valueOf4);
        }
    }

    /* access modifiers changed from: protected */
    public final void afterDone() {
        maybePropagateCancellationTo(this.inputFuture);
        this.inputFuture = null;
        this.exceptionType = null;
        this.fallback = null;
    }

    private static final class AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>, ListenableFuture<? extends V>> {
        AsyncCatchingFuture(ListenableFuture<? extends V> input, Class<X> exceptionType, AsyncFunction<? super X, ? extends V> fallback) {
            super(input, exceptionType, fallback);
        }

        /* access modifiers changed from: package-private */
        public ListenableFuture<? extends V> doFallback(AsyncFunction<? super X, ? extends V> fallback, X cause) throws Exception {
            ListenableFuture<? extends V> replacement = fallback.apply(cause);
            Preconditions.checkNotNull(replacement, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", fallback);
            return replacement;
        }

        /* access modifiers changed from: package-private */
        public void setResult(ListenableFuture<? extends V> result) {
            setFuture(result);
        }
    }

    private static final class CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>, V> {
        CatchingFuture(ListenableFuture<? extends V> input, Class<X> exceptionType, Function<? super X, ? extends V> fallback) {
            super(input, exceptionType, fallback);
        }

        /* access modifiers changed from: package-private */
        @NullableDecl
        public V doFallback(Function<? super X, ? extends V> fallback, X cause) throws Exception {
            return fallback.apply(cause);
        }

        /* access modifiers changed from: package-private */
        public void setResult(@NullableDecl V result) {
            set(result);
        }
    }
}
