package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.j2objc.annotations.ReflectionSupport;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

@GwtCompatible(emulated = true)
@ReflectionSupport(ReflectionSupport.Level.FULL)
public abstract class AbstractFuture<V> extends InternalFutureFailureAccess implements ListenableFuture<V> {
    /* access modifiers changed from: private */
    public static final AtomicHelper ATOMIC_HELPER;
    /* access modifiers changed from: private */
    public static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Object NULL = new Object();
    private static final long SPIN_THRESHOLD_NANOS = 1000;
    private static final Logger log = Logger.getLogger(AbstractFuture.class.getName());
    /* access modifiers changed from: private */
    @NullableDecl
    public volatile Listener listeners;
    /* access modifiers changed from: private */
    @NullableDecl
    public volatile Object value;
    /* access modifiers changed from: private */
    @NullableDecl
    public volatile Waiter waiters;

    interface Trusted<V> extends ListenableFuture<V> {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: com.google.common.util.concurrent.AbstractFuture$SynchronizedHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: com.google.common.util.concurrent.AbstractFuture$SynchronizedHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: com.google.common.util.concurrent.AbstractFuture$SafeAtomicHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: com.google.common.util.concurrent.AbstractFuture$SynchronizedHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.String r0 = "guava.concurrent.generate_cancellation_cause"
            java.lang.String r1 = "false"
            java.lang.String r0 = java.lang.System.getProperty(r0, r1)
            boolean r0 = java.lang.Boolean.parseBoolean(r0)
            com.google.common.util.concurrent.AbstractFuture.GENERATE_CANCELLATION_CAUSES = r0
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture> r0 = com.google.common.util.concurrent.AbstractFuture.class
            java.lang.String r0 = r0.getName()
            java.util.logging.Logger r0 = java.util.logging.Logger.getLogger(r0)
            com.google.common.util.concurrent.AbstractFuture.log = r0
            r0 = 0
            r1 = 0
            r2 = 0
            com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper r3 = new com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper     // Catch:{ all -> 0x0025 }
            r3.<init>()     // Catch:{ all -> 0x0025 }
            r2 = r3
            goto L_0x0069
        L_0x0025:
            r3 = move-exception
            r0 = r3
            com.google.common.util.concurrent.AbstractFuture$SafeAtomicHelper r10 = new com.google.common.util.concurrent.AbstractFuture$SafeAtomicHelper     // Catch:{ all -> 0x0061 }
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r4 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
            java.lang.Class<java.lang.Thread> r5 = java.lang.Thread.class
            java.lang.String r6 = "thread"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r4, r5, r6)     // Catch:{ all -> 0x0061 }
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r4 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r6 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
            java.lang.String r7 = "next"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r6 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r4, r6, r7)     // Catch:{ all -> 0x0061 }
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture> r4 = com.google.common.util.concurrent.AbstractFuture.class
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Waiter> r7 = com.google.common.util.concurrent.AbstractFuture.Waiter.class
            java.lang.String r8 = "waiters"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r7 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r4, r7, r8)     // Catch:{ all -> 0x0061 }
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture> r4 = com.google.common.util.concurrent.AbstractFuture.class
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture$Listener> r8 = com.google.common.util.concurrent.AbstractFuture.Listener.class
            java.lang.String r9 = "listeners"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r8 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r4, r8, r9)     // Catch:{ all -> 0x0061 }
            java.lang.Class<com.google.common.util.concurrent.AbstractFuture> r4 = com.google.common.util.concurrent.AbstractFuture.class
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            java.lang.String r11 = "value"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r9 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r4, r9, r11)     // Catch:{ all -> 0x0061 }
            r4 = r10
            r4.<init>(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0061 }
            r2 = r10
            goto L_0x0069
        L_0x0061:
            r4 = move-exception
            r1 = r4
            com.google.common.util.concurrent.AbstractFuture$SynchronizedHelper r5 = new com.google.common.util.concurrent.AbstractFuture$SynchronizedHelper
            r5.<init>()
            r2 = r5
        L_0x0069:
            com.google.common.util.concurrent.AbstractFuture.ATOMIC_HELPER = r2
            java.lang.Class<java.util.concurrent.locks.LockSupport> r9 = java.util.concurrent.locks.LockSupport.class
            if (r1 == 0) goto L_0x008b
            java.util.logging.Logger r3 = com.google.common.util.concurrent.AbstractFuture.log
            java.util.logging.Level r4 = java.util.logging.Level.SEVERE
            java.lang.String r5 = "com.google.common.util.concurrent.AbstractFuture"
            java.lang.String r6 = "<clinit>"
            java.lang.String r7 = "UnsafeAtomicHelper is broken!"
            r8 = r0
            r3.logp(r4, r5, r6, r7, r8)
            java.util.logging.Logger r3 = com.google.common.util.concurrent.AbstractFuture.log
            java.util.logging.Level r4 = java.util.logging.Level.SEVERE
            java.lang.String r5 = "com.google.common.util.concurrent.AbstractFuture"
            java.lang.String r6 = "<clinit>"
            java.lang.String r7 = "SafeAtomicHelper is broken!"
            r8 = r1
            r3.logp(r4, r5, r6, r7, r8)
        L_0x008b:
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            com.google.common.util.concurrent.AbstractFuture.NULL = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.<clinit>():void");
    }

    static abstract class TrustedFuture<V> extends AbstractFuture<V> implements Trusted<V> {
        TrustedFuture() {
        }

        @CanIgnoreReturnValue
        public final V get() throws InterruptedException, ExecutionException {
            return AbstractFuture.super.get();
        }

        @CanIgnoreReturnValue
        public final V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return AbstractFuture.super.get(timeout, unit);
        }

        public final boolean isDone() {
            return AbstractFuture.super.isDone();
        }

        public final boolean isCancelled() {
            return AbstractFuture.super.isCancelled();
        }

        public final void addListener(Runnable listener, Executor executor) {
            AbstractFuture.super.addListener(listener, executor);
        }

        @CanIgnoreReturnValue
        public final boolean cancel(boolean mayInterruptIfRunning) {
            return AbstractFuture.super.cancel(mayInterruptIfRunning);
        }
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        @NullableDecl
        volatile Waiter next;
        @NullableDecl
        volatile Thread thread;

        Waiter(boolean unused) {
        }

        Waiter() {
            AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        /* access modifiers changed from: package-private */
        public void setNext(Waiter next2) {
            AbstractFuture.ATOMIC_HELPER.putNext(this, next2);
        }

        /* access modifiers changed from: package-private */
        public void unpark() {
            Thread w = this.thread;
            if (w != null) {
                this.thread = null;
                LockSupport.unpark(w);
            }
        }
    }

    private void removeWaiter(Waiter node) {
        node.thread = null;
        while (true) {
            Waiter pred = null;
            Waiter curr = this.waiters;
            if (curr != Waiter.TOMBSTONE) {
                while (curr != null) {
                    Waiter succ = curr.next;
                    if (curr.thread != null) {
                        pred = curr;
                    } else if (pred != null) {
                        pred.next = succ;
                        if (pred.thread == null) {
                        }
                    } else if (!ATOMIC_HELPER.casWaiters(this, curr, succ)) {
                    }
                    curr = succ;
                }
                return;
            }
            return;
        }
    }

    private static final class Listener {
        static final Listener TOMBSTONE = new Listener(null, null);
        final Executor executor;
        @NullableDecl
        Listener next;
        final Runnable task;

        Listener(Runnable task2, Executor executor2) {
            this.task = task2;
            this.executor = executor2;
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable exception2) {
            this.exception = (Throwable) Preconditions.checkNotNull(exception2);
        }
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        @NullableDecl
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (AbstractFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, null);
        }

        Cancellation(boolean wasInterrupted2, @NullableDecl Throwable cause2) {
            this.wasInterrupted = wasInterrupted2;
            this.cause = cause2;
        }
    }

    private static final class SetFuture<V> implements Runnable {
        final ListenableFuture<? extends V> future;
        final AbstractFuture<V> owner;

        SetFuture(AbstractFuture<V> owner2, ListenableFuture<? extends V> future2) {
            this.owner = owner2;
            this.future = future2;
        }

        public void run() {
            if (this.owner.value == this) {
                if (AbstractFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractFuture.getFutureValue(this.future))) {
                    AbstractFuture.complete(this.owner);
                }
            }
        }
    }

    protected AbstractFuture() {
    }

    /* JADX INFO: Multiple debug info for r4v5 long: [D('overWaitUnits' long), D('timeoutNanos' long)] */
    /* JADX INFO: Multiple debug info for r6v3 long: [D('remainingNanos' long), D('overWaitLeftoverNanos' long)] */
    @CanIgnoreReturnValue
    public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        String message;
        long j = timeout;
        TimeUnit timeUnit = unit;
        long remainingNanos = timeUnit.toNanos(j);
        if (!Thread.interrupted()) {
            Object localValue = this.value;
            if ((localValue != null) && (!(localValue instanceof SetFuture))) {
                return getDoneValue(localValue);
            }
            long endNanos = remainingNanos > 0 ? System.nanoTime() + remainingNanos : 0;
            if (remainingNanos >= 1000) {
                Waiter oldHead = this.waiters;
                if (oldHead != Waiter.TOMBSTONE) {
                    Waiter node = new Waiter();
                    while (true) {
                        node.setNext(oldHead);
                        if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                            do {
                                LockSupport.parkNanos(this, remainingNanos);
                                if (!Thread.interrupted()) {
                                    Object localValue2 = this.value;
                                    if ((localValue2 != null) && (!(localValue2 instanceof SetFuture))) {
                                        return getDoneValue(localValue2);
                                    }
                                    remainingNanos = endNanos - System.nanoTime();
                                } else {
                                    removeWaiter(node);
                                    throw new InterruptedException();
                                }
                            } while (remainingNanos >= 1000);
                            removeWaiter(node);
                        } else {
                            oldHead = this.waiters;
                            if (oldHead == Waiter.TOMBSTONE) {
                                break;
                            }
                        }
                    }
                }
                return getDoneValue(this.value);
            }
            while (remainingNanos > 0) {
                Object localValue3 = this.value;
                if ((localValue3 != null) && (!(localValue3 instanceof SetFuture))) {
                    return getDoneValue(localValue3);
                }
                if (!Thread.interrupted()) {
                    remainingNanos = endNanos - System.nanoTime();
                } else {
                    throw new InterruptedException();
                }
            }
            String futureToString = toString();
            String unitString = unit.toString().toLowerCase(Locale.ROOT);
            String lowerCase = unit.toString().toLowerCase(Locale.ROOT);
            StringBuilder sb = new StringBuilder(String.valueOf(lowerCase).length() + 28);
            sb.append("Waited ");
            sb.append(j);
            sb.append(" ");
            sb.append(lowerCase);
            String message2 = sb.toString();
            if (remainingNanos + 1000 < 0) {
                String message3 = String.valueOf(message2).concat(" (plus ");
                long overWaitNanos = -remainingNanos;
                long timeoutNanos = timeUnit.convert(overWaitNanos, TimeUnit.NANOSECONDS);
                long overWaitLeftoverNanos = overWaitNanos - timeUnit.toNanos(timeoutNanos);
                boolean shouldShowExtraNanos = timeoutNanos == 0 || overWaitLeftoverNanos > 1000;
                if (timeoutNanos > 0) {
                    String valueOf = String.valueOf(message3);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 21 + String.valueOf(unitString).length());
                    sb2.append(valueOf);
                    sb2.append(timeoutNanos);
                    sb2.append(" ");
                    sb2.append(unitString);
                    String message4 = sb2.toString();
                    if (shouldShowExtraNanos) {
                        message4 = String.valueOf(message4).concat(",");
                    }
                    message3 = String.valueOf(message4).concat(" ");
                }
                if (shouldShowExtraNanos) {
                    String valueOf2 = String.valueOf(message3);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
                    sb3.append(valueOf2);
                    sb3.append(overWaitLeftoverNanos);
                    sb3.append(" nanoseconds ");
                    message3 = sb3.toString();
                }
                message = String.valueOf(message3).concat("delay)");
            } else {
                message = message2;
            }
            if (isDone()) {
                throw new TimeoutException(String.valueOf(message).concat(" but future completed as timeout expired"));
            }
            StringBuilder sb4 = new StringBuilder(String.valueOf(message).length() + 5 + String.valueOf(futureToString).length());
            sb4.append(message);
            sb4.append(" for ");
            sb4.append(futureToString);
            throw new TimeoutException(sb4.toString());
        }
        throw new InterruptedException();
    }

    @CanIgnoreReturnValue
    public V get() throws InterruptedException, ExecutionException {
        Object localValue;
        if (!Thread.interrupted()) {
            Object localValue2 = this.value;
            if ((localValue2 != null) && (!(localValue2 instanceof SetFuture))) {
                return getDoneValue(localValue2);
            }
            Waiter oldHead = this.waiters;
            if (oldHead != Waiter.TOMBSTONE) {
                Waiter node = new Waiter();
                do {
                    node.setNext(oldHead);
                    if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                        do {
                            LockSupport.park(this);
                            if (!Thread.interrupted()) {
                                localValue = this.value;
                            } else {
                                removeWaiter(node);
                                throw new InterruptedException();
                            }
                        } while (!((localValue != null) & (!(localValue instanceof SetFuture))));
                        return getDoneValue(localValue);
                    }
                    oldHead = this.waiters;
                } while (oldHead != Waiter.TOMBSTONE);
            }
            return getDoneValue(this.value);
        }
        throw new InterruptedException();
    }

    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((Cancellation) obj).cause);
        } else if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        } else if (obj == NULL) {
            return null;
        } else {
            return obj;
        }
    }

    public boolean isDone() {
        Object localValue = this.value;
        boolean z = true;
        boolean z2 = localValue != null;
        if (localValue instanceof SetFuture) {
            z = false;
        }
        return z & z2;
    }

    public boolean isCancelled() {
        return this.value instanceof Cancellation;
    }

    /* JADX WARN: Type inference failed for: r6v6, types: [com.google.common.util.concurrent.ListenableFuture, com.google.common.util.concurrent.ListenableFuture<? extends V>] */
    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: ?
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    public boolean cancel(boolean r11) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.value
            r1 = 0
            r2 = 1
            r3 = 0
            if (r0 != 0) goto L_0x0009
            r4 = 1
            goto L_0x000a
        L_0x0009:
            r4 = 0
        L_0x000a:
            boolean r5 = r0 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            r4 = r4 | r5
            if (r4 == 0) goto L_0x0063
            boolean r4 = com.google.common.util.concurrent.AbstractFuture.GENERATE_CANCELLATION_CAUSES
            if (r4 == 0) goto L_0x0020
            com.google.common.util.concurrent.AbstractFuture$Cancellation r4 = new com.google.common.util.concurrent.AbstractFuture$Cancellation
            java.util.concurrent.CancellationException r5 = new java.util.concurrent.CancellationException
            java.lang.String r6 = "Future.cancel() was called."
            r5.<init>(r6)
            r4.<init>(r11, r5)
            goto L_0x0027
        L_0x0020:
            if (r11 == 0) goto L_0x0025
            com.google.common.util.concurrent.AbstractFuture$Cancellation r4 = com.google.common.util.concurrent.AbstractFuture.Cancellation.CAUSELESS_INTERRUPTED
            goto L_0x0027
        L_0x0025:
            com.google.common.util.concurrent.AbstractFuture$Cancellation r4 = com.google.common.util.concurrent.AbstractFuture.Cancellation.CAUSELESS_CANCELLED
        L_0x0027:
            r5 = r10
        L_0x0029:
            com.google.common.util.concurrent.AbstractFuture$AtomicHelper r6 = com.google.common.util.concurrent.AbstractFuture.ATOMIC_HELPER
            boolean r6 = r6.casValue(r5, r0, r4)
            if (r6 == 0) goto L_0x005d
            r1 = 1
            if (r11 == 0) goto L_0x0037
            r5.interruptTask()
        L_0x0037:
            complete(r5)
            boolean r6 = r0 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            if (r6 == 0) goto L_0x0063
            r6 = r0
            com.google.common.util.concurrent.AbstractFuture$SetFuture r6 = (com.google.common.util.concurrent.AbstractFuture.SetFuture) r6
            com.google.common.util.concurrent.ListenableFuture<? extends V> r6 = r6.future
            boolean r7 = r6 instanceof com.google.common.util.concurrent.AbstractFuture.Trusted
            if (r7 == 0) goto L_0x0059
            r7 = r6
            com.google.common.util.concurrent.AbstractFuture r7 = (com.google.common.util.concurrent.AbstractFuture) r7
            java.lang.Object r0 = r7.value
            if (r0 != 0) goto L_0x0050
            r8 = 1
            goto L_0x0051
        L_0x0050:
            r8 = 0
        L_0x0051:
            boolean r9 = r0 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            r8 = r8 | r9
            if (r8 == 0) goto L_0x0058
            r5 = r7
            goto L_0x0029
        L_0x0058:
            goto L_0x005c
        L_0x0059:
            r6.cancel(r11)
        L_0x005c:
            goto L_0x0063
        L_0x005d:
            java.lang.Object r0 = r5.value
            boolean r6 = r0 instanceof com.google.common.util.concurrent.AbstractFuture.SetFuture
            if (r6 != 0) goto L_0x0029
        L_0x0063:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.AbstractFuture.cancel(boolean):boolean");
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
    }

    /* access modifiers changed from: protected */
    public final boolean wasInterrupted() {
        Object localValue = this.value;
        return (localValue instanceof Cancellation) && ((Cancellation) localValue).wasInterrupted;
    }

    public void addListener(Runnable listener, Executor executor) {
        Listener oldHead;
        Preconditions.checkNotNull(listener, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        if (isDone() || (oldHead = this.listeners) == Listener.TOMBSTONE) {
            executeListener(listener, executor);
        }
        Listener newNode = new Listener(listener, executor);
        do {
            newNode.next = oldHead;
            if (!ATOMIC_HELPER.casListeners(this, oldHead, newNode)) {
                oldHead = this.listeners;
            } else {
                return;
            }
        } while (oldHead != Listener.TOMBSTONE);
        executeListener(listener, executor);
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    public boolean set(@NullableDecl V value2) {
        if (!ATOMIC_HELPER.casValue(this, null, value2 == null ? NULL : value2)) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    public boolean setException(Throwable throwable) {
        if (!ATOMIC_HELPER.casValue(this, null, new Failure((Throwable) Preconditions.checkNotNull(throwable)))) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    @CanIgnoreReturnValue
    @Beta
    public boolean setFuture(ListenableFuture<? extends V> future) {
        SetFuture valueToSet;
        Failure failure;
        Preconditions.checkNotNull(future);
        Object localValue = this.value;
        if (localValue == null) {
            if (future.isDone()) {
                if (!ATOMIC_HELPER.casValue(this, null, getFutureValue(future))) {
                    return false;
                }
                complete(this);
                return true;
            }
            valueToSet = new SetFuture(this, future);
            if (ATOMIC_HELPER.casValue(this, null, valueToSet)) {
                try {
                    future.addListener(valueToSet, DirectExecutor.INSTANCE);
                } catch (Throwable th) {
                    failure = Failure.FALLBACK_INSTANCE;
                }
                return true;
            }
            localValue = this.value;
        }
        if (localValue instanceof Cancellation) {
            future.cancel(((Cancellation) localValue).wasInterrupted);
        }
        return false;
        ATOMIC_HELPER.casValue(this, valueToSet, failure);
        return true;
    }

    /* JADX INFO: Multiple debug info for r1v2 boolean: [D('wasCancelled' boolean), D('throwable' java.lang.Throwable)] */
    /* access modifiers changed from: private */
    public static Object getFutureValue(ListenableFuture<?> future) {
        Throwable throwable;
        Object v;
        if (future instanceof Trusted) {
            Object v2 = ((AbstractFuture) future).value;
            if (!(v2 instanceof Cancellation)) {
                return v2;
            }
            Cancellation c = (Cancellation) v2;
            if (!c.wasInterrupted) {
                return v2;
            }
            if (c.cause != null) {
                v = new Cancellation(false, c.cause);
            } else {
                v = Cancellation.CAUSELESS_CANCELLED;
            }
            return v;
        } else if ((future instanceof InternalFutureFailureAccess) && (throwable = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess) future)) != null) {
            return new Failure(throwable);
        } else {
            boolean wasCancelled = future.isCancelled();
            if ((!GENERATE_CANCELLATION_CAUSES) && wasCancelled) {
                return Cancellation.CAUSELESS_CANCELLED;
            }
            try {
                Object v3 = getUninterruptibly(future);
                if (!wasCancelled) {
                    return v3 == null ? NULL : v3;
                }
                String valueOf = String.valueOf(future);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 84);
                sb.append("get() did not throw CancellationException, despite reporting isCancelled() == true: ");
                sb.append(valueOf);
                return new Cancellation(false, new IllegalArgumentException(sb.toString()));
            } catch (ExecutionException exception) {
                if (!wasCancelled) {
                    return new Failure(exception.getCause());
                }
                String valueOf2 = String.valueOf(future);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 84);
                sb2.append("get() did not throw CancellationException, despite reporting isCancelled() == true: ");
                sb2.append(valueOf2);
                return new Cancellation(false, new IllegalArgumentException(sb2.toString(), exception));
            } catch (CancellationException cancellation) {
                if (wasCancelled) {
                    return new Cancellation(false, cancellation);
                }
                String valueOf3 = String.valueOf(future);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 77);
                sb3.append("get() threw CancellationException, despite reporting isCancelled() == false: ");
                sb3.append(valueOf3);
                return new Failure(new IllegalArgumentException(sb3.toString(), cancellation));
            } catch (Throwable t) {
                return new Failure(t);
            }
        }
    }

    private static <V> V getUninterruptibly(Future<V> future) throws ExecutionException {
        V v;
        boolean interrupted = false;
        while (true) {
            try {
                v = future.get();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            } catch (Throwable th) {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        return v;
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:13:0x0001 */
    /* access modifiers changed from: private */
    public static void complete(AbstractFuture<?> abstractFuture) {
        Listener next = null;
        AbstractFuture abstractFuture2 = abstractFuture;
        while (true) {
            abstractFuture2.releaseWaiters();
            abstractFuture2.afterDone();
            next = abstractFuture2.clearListeners(next);
            while (true) {
                if (next != null) {
                    Listener curr = next;
                    next = next.next;
                    Runnable task = curr.task;
                    if (task instanceof SetFuture) {
                        SetFuture<?> setFuture = (SetFuture) task;
                        abstractFuture2 = setFuture.owner;
                        if (abstractFuture2.value == setFuture) {
                            if (ATOMIC_HELPER.casValue(abstractFuture2, setFuture, getFutureValue(setFuture.future))) {
                            }
                        } else {
                            continue;
                        }
                    } else {
                        executeListener(task, curr.executor);
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    @ForOverride
    @Beta
    public void afterDone() {
    }

    /* access modifiers changed from: protected */
    @NullableDecl
    public final Throwable tryInternalFastPathGetFailure() {
        if (!(this instanceof Trusted)) {
            return null;
        }
        Object obj = this.value;
        if (obj instanceof Failure) {
            return ((Failure) obj).exception;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final void maybePropagateCancellationTo(@NullableDecl Future<?> related) {
        if ((related != null) && isCancelled()) {
            related.cancel(wasInterrupted());
        }
    }

    private void releaseWaiters() {
        Waiter head;
        do {
            head = this.waiters;
        } while (!ATOMIC_HELPER.casWaiters(this, head, Waiter.TOMBSTONE));
        for (Waiter currentWaiter = head; currentWaiter != null; currentWaiter = currentWaiter.next) {
            currentWaiter.unpark();
        }
    }

    private Listener clearListeners(Listener onto) {
        Listener head;
        do {
            head = this.listeners;
        } while (!ATOMIC_HELPER.casListeners(this, head, Listener.TOMBSTONE));
        Listener reversedList = onto;
        while (head != null) {
            Listener tmp = head;
            head = head.next;
            tmp.next = reversedList;
            reversedList = tmp;
        }
        return reversedList;
    }

    public String toString() {
        String pendingDescription;
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        StringBuilder builder = sb.append("[status=");
        if (isCancelled()) {
            builder.append("CANCELLED");
        } else if (isDone()) {
            addDoneString(builder);
        } else {
            try {
                pendingDescription = pendingToString();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(e.getClass());
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 38);
                sb2.append("Exception thrown from implementation: ");
                sb2.append(valueOf);
                pendingDescription = sb2.toString();
            }
            if (pendingDescription != null && !pendingDescription.isEmpty()) {
                builder.append("PENDING, info=[");
                builder.append(pendingDescription);
                builder.append("]");
            } else if (isDone()) {
                addDoneString(builder);
            } else {
                builder.append("PENDING");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    @NullableDecl
    public String pendingToString() {
        Object localValue = this.value;
        if (localValue instanceof SetFuture) {
            String userObjectToString = userObjectToString(((SetFuture) localValue).future);
            StringBuilder sb = new StringBuilder(String.valueOf(userObjectToString).length() + 12);
            sb.append("setFuture=[");
            sb.append(userObjectToString);
            sb.append("]");
            return sb.toString();
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            long delay = ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS);
            StringBuilder sb2 = new StringBuilder(41);
            sb2.append("remaining delay=[");
            sb2.append(delay);
            sb2.append(" ms]");
            return sb2.toString();
        }
    }

    private void addDoneString(StringBuilder builder) {
        try {
            V value2 = getUninterruptibly(this);
            builder.append("SUCCESS, result=[");
            builder.append(userObjectToString(value2));
            builder.append("]");
        } catch (ExecutionException e) {
            builder.append("FAILURE, cause=[");
            builder.append(e.getCause());
            builder.append("]");
        } catch (CancellationException e2) {
            builder.append("CANCELLED");
        } catch (RuntimeException e3) {
            builder.append("UNKNOWN, cause=[");
            builder.append(e3.getClass());
            builder.append(" thrown from get()]");
        }
    }

    private String userObjectToString(Object o) {
        if (o == this) {
            return "this future";
        }
        return String.valueOf(o);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.RuntimeException]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            String valueOf = String.valueOf(runnable);
            String valueOf2 = String.valueOf(executor);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 57 + String.valueOf(valueOf2).length());
            sb.append("RuntimeException while executing runnable ");
            sb.append(valueOf);
            sb.append(" with executor ");
            sb.append(valueOf2);
            logger.logp(level, "com.google.common.util.concurrent.AbstractFuture", "executeListener", sb.toString(), (Throwable) e);
        }
    }

    private static abstract class AtomicHelper {
        /* access modifiers changed from: package-private */
        public abstract boolean casListeners(AbstractFuture<?> abstractFuture, Listener listener, Listener listener2);

        /* access modifiers changed from: package-private */
        public abstract boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        /* access modifiers changed from: package-private */
        public abstract boolean casWaiters(AbstractFuture<?> abstractFuture, Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: package-private */
        public abstract void putNext(Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: package-private */
        public abstract void putThread(Waiter waiter, Thread thread);

        private AtomicHelper() {
        }
    }

    private static final class UnsafeAtomicHelper extends AtomicHelper {
        static final long LISTENERS_OFFSET;
        static final Unsafe UNSAFE;
        static final long VALUE_OFFSET;
        static final long WAITERS_OFFSET;
        static final long WAITER_NEXT_OFFSET;
        static final long WAITER_THREAD_OFFSET;

        private UnsafeAtomicHelper() {
            super();
        }

        /* JADX INFO: Multiple debug info for r1v0 java.lang.Class<com.google.common.util.concurrent.AbstractFuture>: [D('abstractFuture' java.lang.Class<?>), D('tryReflectionInstead' java.lang.SecurityException)] */
        static {
            Unsafe unsafe;
            try {
                unsafe = Unsafe.getUnsafe();
            } catch (SecurityException e) {
                try {
                    unsafe = (Unsafe) AccessController.doPrivileged(new PrivilegedExceptionAction<Unsafe>() {
                        public Unsafe run() throws Exception {
                            Class<Unsafe> k = Unsafe.class;
                            for (Field f : k.getDeclaredFields()) {
                                f.setAccessible(true);
                                Object x = f.get(null);
                                if (k.isInstance(x)) {
                                    return k.cast(x);
                                }
                            }
                            throw new NoSuchFieldError("the Unsafe");
                        }
                    });
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            }
            Class<?> abstractFuture = AbstractFuture.class;
            try {
                WAITERS_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("waiters"));
                LISTENERS_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("listeners"));
                VALUE_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("value"));
                WAITER_THREAD_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("thread"));
                WAITER_NEXT_OFFSET = unsafe.objectFieldOffset(Waiter.class.getDeclaredField("next"));
                UNSAFE = unsafe;
            } catch (Exception e3) {
                Throwables.throwIfUnchecked(e3);
                throw new RuntimeException(e3);
            }
        }

        /* access modifiers changed from: package-private */
        public void putThread(Waiter waiter, Thread newValue) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, newValue);
        }

        /* access modifiers changed from: package-private */
        public void putNext(Waiter waiter, Waiter newValue) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, newValue);
        }

        /* access modifiers changed from: package-private */
        public boolean casWaiters(AbstractFuture<?> future, Waiter expect, Waiter update) {
            return UNSAFE.compareAndSwapObject(future, WAITERS_OFFSET, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractFuture<?> future, Listener expect, Listener update) {
            return UNSAFE.compareAndSwapObject(future, LISTENERS_OFFSET, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractFuture<?> future, Object expect, Object update) {
            return UNSAFE.compareAndSwapObject(future, VALUE_OFFSET, expect, update);
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater2, AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Waiter> waitersUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Listener> listenersUpdater2, AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater2) {
            super();
            this.waiterThreadUpdater = waiterThreadUpdater2;
            this.waiterNextUpdater = waiterNextUpdater2;
            this.waitersUpdater = waitersUpdater2;
            this.listenersUpdater = listenersUpdater2;
            this.valueUpdater = valueUpdater2;
        }

        /* access modifiers changed from: package-private */
        public void putThread(Waiter waiter, Thread newValue) {
            this.waiterThreadUpdater.lazySet(waiter, newValue);
        }

        /* access modifiers changed from: package-private */
        public void putNext(Waiter waiter, Waiter newValue) {
            this.waiterNextUpdater.lazySet(waiter, newValue);
        }

        /* access modifiers changed from: package-private */
        public boolean casWaiters(AbstractFuture<?> future, Waiter expect, Waiter update) {
            return this.waitersUpdater.compareAndSet(future, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractFuture<?> future, Listener expect, Listener update) {
            return this.listenersUpdater.compareAndSet(future, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractFuture<?> future, Object expect, Object update) {
            return this.valueUpdater.compareAndSet(future, expect, update);
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        private SynchronizedHelper() {
            super();
        }

        /* access modifiers changed from: package-private */
        public void putThread(Waiter waiter, Thread newValue) {
            waiter.thread = newValue;
        }

        /* access modifiers changed from: package-private */
        public void putNext(Waiter waiter, Waiter newValue) {
            waiter.next = newValue;
        }

        /* access modifiers changed from: package-private */
        public boolean casWaiters(AbstractFuture<?> future, Waiter expect, Waiter update) {
            synchronized (future) {
                if (future.waiters != expect) {
                    return false;
                }
                Waiter unused = future.waiters = update;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractFuture<?> future, Listener expect, Listener update) {
            synchronized (future) {
                if (future.listeners != expect) {
                    return false;
                }
                Listener unused = future.listeners = update;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractFuture<?> future, Object expect, Object update) {
            synchronized (future) {
                if (future.value != expect) {
                    return false;
                }
                Object unused = future.value = update;
                return true;
            }
        }
    }

    private static CancellationException cancellationExceptionWithCause(@NullableDecl String message, @NullableDecl Throwable cause) {
        CancellationException exception = new CancellationException(message);
        exception.initCause(cause);
        return exception;
    }
}
