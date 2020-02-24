package androidx.concurrent.futures;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;
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

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public abstract class AbstractResolvableFuture<V> implements ListenableFuture<V> {
    static final AtomicHelper ATOMIC_HELPER;
    static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Object NULL = new Object();
    private static final long SPIN_THRESHOLD_NANOS = 1000;
    private static final Logger log = Logger.getLogger(AbstractResolvableFuture.class.getName());
    @Nullable
    volatile Listener listeners;
    @Nullable
    volatile Object value;
    @Nullable
    volatile Waiter waiters;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.String r0 = "guava.concurrent.generate_cancellation_cause"
            java.lang.String r1 = "false"
            java.lang.String r0 = java.lang.System.getProperty(r0, r1)
            boolean r0 = java.lang.Boolean.parseBoolean(r0)
            androidx.concurrent.futures.AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES = r0
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture> r0 = androidx.concurrent.futures.AbstractResolvableFuture.class
            java.lang.String r0 = r0.getName()
            java.util.logging.Logger r0 = java.util.logging.Logger.getLogger(r0)
            androidx.concurrent.futures.AbstractResolvableFuture.log = r0
            r0 = 0
            androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper r7 = new androidx.concurrent.futures.AbstractResolvableFuture$SafeAtomicHelper     // Catch:{ all -> 0x0059 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r1 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.Class<java.lang.Thread> r2 = java.lang.Thread.class
            java.lang.String r3 = "thread"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r1, r2, r3)     // Catch:{ all -> 0x0059 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r1 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r3 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.String r4 = "next"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r3 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r1, r3, r4)     // Catch:{ all -> 0x0059 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture> r1 = androidx.concurrent.futures.AbstractResolvableFuture.class
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Waiter> r4 = androidx.concurrent.futures.AbstractResolvableFuture.Waiter.class
            java.lang.String r5 = "waiters"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r4 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r1, r4, r5)     // Catch:{ all -> 0x0059 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture> r1 = androidx.concurrent.futures.AbstractResolvableFuture.class
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture$Listener> r5 = androidx.concurrent.futures.AbstractResolvableFuture.Listener.class
            java.lang.String r6 = "listeners"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r5 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r1, r5, r6)     // Catch:{ all -> 0x0059 }
            java.lang.Class<androidx.concurrent.futures.AbstractResolvableFuture> r1 = androidx.concurrent.futures.AbstractResolvableFuture.class
            java.lang.Class<java.lang.Object> r6 = java.lang.Object.class
            java.lang.String r8 = "value"
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r6 = java.util.concurrent.atomic.AtomicReferenceFieldUpdater.newUpdater(r1, r6, r8)     // Catch:{ all -> 0x0059 }
            r1 = r7
            r1.<init>(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0059 }
            r1 = r7
            goto L_0x0061
        L_0x0059:
            r1 = move-exception
            r0 = r1
            androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper r2 = new androidx.concurrent.futures.AbstractResolvableFuture$SynchronizedHelper
            r2.<init>()
            r1 = r2
        L_0x0061:
            androidx.concurrent.futures.AbstractResolvableFuture.ATOMIC_HELPER = r1
            java.lang.Class<java.util.concurrent.locks.LockSupport> r2 = java.util.concurrent.locks.LockSupport.class
            if (r0 == 0) goto L_0x0070
            java.util.logging.Logger r3 = androidx.concurrent.futures.AbstractResolvableFuture.log
            java.util.logging.Level r4 = java.util.logging.Level.SEVERE
            java.lang.String r5 = "SafeAtomicHelper is broken!"
            r3.log(r4, r5, r0)
        L_0x0070:
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            androidx.concurrent.futures.AbstractResolvableFuture.NULL = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.concurrent.futures.AbstractResolvableFuture.<clinit>():void");
    }

    private static final class Waiter {
        static final Waiter TOMBSTONE = new Waiter(false);
        @Nullable
        volatile Waiter next;
        @Nullable
        volatile Thread thread;

        Waiter(boolean unused) {
        }

        Waiter() {
            AbstractResolvableFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
        }

        /* access modifiers changed from: package-private */
        public void setNext(Waiter next2) {
            AbstractResolvableFuture.ATOMIC_HELPER.putNext(this, next2);
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
        @Nullable
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
            this.exception = (Throwable) AbstractResolvableFuture.checkNotNull(exception2);
        }
    }

    private static final class Cancellation {
        static final Cancellation CAUSELESS_CANCELLED;
        static final Cancellation CAUSELESS_INTERRUPTED;
        @Nullable
        final Throwable cause;
        final boolean wasInterrupted;

        static {
            if (AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES) {
                CAUSELESS_CANCELLED = null;
                CAUSELESS_INTERRUPTED = null;
                return;
            }
            CAUSELESS_CANCELLED = new Cancellation(false, null);
            CAUSELESS_INTERRUPTED = new Cancellation(true, null);
        }

        Cancellation(boolean wasInterrupted2, @Nullable Throwable cause2) {
            this.wasInterrupted = wasInterrupted2;
            this.cause = cause2;
        }
    }

    private static final class SetFuture<V> implements Runnable {
        final ListenableFuture<? extends V> future;
        final AbstractResolvableFuture<V> owner;

        SetFuture(AbstractResolvableFuture<V> owner2, ListenableFuture<? extends V> future2) {
            this.owner = owner2;
            this.future = future2;
        }

        public void run() {
            if (this.owner.value == this) {
                if (AbstractResolvableFuture.ATOMIC_HELPER.casValue(this.owner, this, AbstractResolvableFuture.getFutureValue(this.future))) {
                    AbstractResolvableFuture.complete(this.owner);
                }
            }
        }
    }

    protected AbstractResolvableFuture() {
    }

    /* JADX INFO: Multiple debug info for r4v3 long: [D('overWaitUnits' long), D('timeoutNanos' long)] */
    /* JADX INFO: Multiple debug info for r6v3 long: [D('remainingNanos' long), D('overWaitLeftoverNanos' long)] */
    public final V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
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
            String message = "Waited " + j + " " + unit.toString().toLowerCase(Locale.ROOT);
            if (remainingNanos + 1000 < 0) {
                String message2 = message + " (plus ";
                long overWaitNanos = -remainingNanos;
                long timeoutNanos = timeUnit.convert(overWaitNanos, TimeUnit.NANOSECONDS);
                long overWaitLeftoverNanos = overWaitNanos - timeUnit.toNanos(timeoutNanos);
                boolean shouldShowExtraNanos = timeoutNanos == 0 || overWaitLeftoverNanos > 1000;
                if (timeoutNanos > 0) {
                    String message3 = message2 + timeoutNanos + " " + unitString;
                    if (shouldShowExtraNanos) {
                        message3 = message3 + ",";
                    }
                    message2 = message3 + " ";
                }
                if (shouldShowExtraNanos) {
                    message2 = message2 + overWaitLeftoverNanos + " nanoseconds ";
                }
                message = message2 + "delay)";
            }
            if (isDone()) {
                throw new TimeoutException(message + " but future completed as timeout expired");
            }
            throw new TimeoutException(message + " for " + futureToString);
        }
        throw new InterruptedException();
    }

    public final V get() throws InterruptedException, ExecutionException {
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

    public final boolean isDone() {
        Object localValue = this.value;
        boolean z = true;
        boolean z2 = localValue != null;
        if (localValue instanceof SetFuture) {
            z = false;
        }
        return z & z2;
    }

    public final boolean isCancelled() {
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
    public final boolean cancel(boolean r11) {
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
            boolean r5 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            r4 = r4 | r5
            if (r4 == 0) goto L_0x0062
            boolean r4 = androidx.concurrent.futures.AbstractResolvableFuture.GENERATE_CANCELLATION_CAUSES
            if (r4 == 0) goto L_0x0020
            androidx.concurrent.futures.AbstractResolvableFuture$Cancellation r4 = new androidx.concurrent.futures.AbstractResolvableFuture$Cancellation
            java.util.concurrent.CancellationException r5 = new java.util.concurrent.CancellationException
            java.lang.String r6 = "Future.cancel() was called."
            r5.<init>(r6)
            r4.<init>(r11, r5)
            goto L_0x0027
        L_0x0020:
            if (r11 == 0) goto L_0x0025
            androidx.concurrent.futures.AbstractResolvableFuture$Cancellation r4 = androidx.concurrent.futures.AbstractResolvableFuture.Cancellation.CAUSELESS_INTERRUPTED
            goto L_0x0027
        L_0x0025:
            androidx.concurrent.futures.AbstractResolvableFuture$Cancellation r4 = androidx.concurrent.futures.AbstractResolvableFuture.Cancellation.CAUSELESS_CANCELLED
        L_0x0027:
            r5 = r10
        L_0x0028:
            androidx.concurrent.futures.AbstractResolvableFuture$AtomicHelper r6 = androidx.concurrent.futures.AbstractResolvableFuture.ATOMIC_HELPER
            boolean r6 = r6.casValue(r5, r0, r4)
            if (r6 == 0) goto L_0x005c
            r1 = 1
            if (r11 == 0) goto L_0x0036
            r5.interruptTask()
        L_0x0036:
            complete(r5)
            boolean r6 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            if (r6 == 0) goto L_0x0062
            r6 = r0
            androidx.concurrent.futures.AbstractResolvableFuture$SetFuture r6 = (androidx.concurrent.futures.AbstractResolvableFuture.SetFuture) r6
            com.google.common.util.concurrent.ListenableFuture<? extends V> r6 = r6.future
            boolean r7 = r6 instanceof androidx.concurrent.futures.AbstractResolvableFuture
            if (r7 == 0) goto L_0x0058
            r7 = r6
            androidx.concurrent.futures.AbstractResolvableFuture r7 = (androidx.concurrent.futures.AbstractResolvableFuture) r7
            java.lang.Object r0 = r7.value
            if (r0 != 0) goto L_0x004f
            r8 = 1
            goto L_0x0050
        L_0x004f:
            r8 = 0
        L_0x0050:
            boolean r9 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            r8 = r8 | r9
            if (r8 == 0) goto L_0x0057
            r5 = r7
            goto L_0x0028
        L_0x0057:
            goto L_0x005b
        L_0x0058:
            r6.cancel(r11)
        L_0x005b:
            goto L_0x0062
        L_0x005c:
            java.lang.Object r0 = r5.value
            boolean r6 = r0 instanceof androidx.concurrent.futures.AbstractResolvableFuture.SetFuture
            if (r6 != 0) goto L_0x0028
        L_0x0062:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.concurrent.futures.AbstractResolvableFuture.cancel(boolean):boolean");
    }

    /* access modifiers changed from: protected */
    public void interruptTask() {
    }

    /* access modifiers changed from: protected */
    public final boolean wasInterrupted() {
        Object localValue = this.value;
        return (localValue instanceof Cancellation) && ((Cancellation) localValue).wasInterrupted;
    }

    public final void addListener(Runnable listener, Executor executor) {
        checkNotNull(listener);
        checkNotNull(executor);
        Listener oldHead = this.listeners;
        if (oldHead != Listener.TOMBSTONE) {
            Listener newNode = new Listener(listener, executor);
            do {
                newNode.next = oldHead;
                if (!ATOMIC_HELPER.casListeners(this, oldHead, newNode)) {
                    oldHead = this.listeners;
                } else {
                    return;
                }
            } while (oldHead != Listener.TOMBSTONE);
        }
        executeListener(listener, executor);
    }

    /* access modifiers changed from: protected */
    public boolean set(@Nullable V value2) {
        if (!ATOMIC_HELPER.casValue(this, null, value2 == null ? NULL : value2)) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setException(Throwable throwable) {
        if (!ATOMIC_HELPER.casValue(this, null, new Failure((Throwable) checkNotNull(throwable)))) {
            return false;
        }
        complete(this);
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean setFuture(ListenableFuture<? extends V> future) {
        SetFuture valueToSet;
        Failure failure;
        checkNotNull(future);
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

    /* JADX INFO: Multiple debug info for r0v1 boolean: [D('v' java.lang.Object), D('wasCancelled' boolean)] */
    static Object getFutureValue(ListenableFuture<?> future) {
        if (future instanceof AbstractResolvableFuture) {
            Object v = ((AbstractResolvableFuture) future).value;
            if (!(v instanceof Cancellation)) {
                return v;
            }
            Cancellation c = (Cancellation) v;
            if (!c.wasInterrupted) {
                return v;
            }
            return c.cause != null ? new Cancellation(false, c.cause) : Cancellation.CAUSELESS_CANCELLED;
        }
        boolean wasCancelled = future.isCancelled();
        if ((!GENERATE_CANCELLATION_CAUSES) && wasCancelled) {
            return Cancellation.CAUSELESS_CANCELLED;
        }
        try {
            Object v2 = getUninterruptibly(future);
            return v2 == null ? NULL : v2;
        } catch (ExecutionException exception) {
            return new Failure(exception.getCause());
        } catch (CancellationException cancellation) {
            if (wasCancelled) {
                return new Cancellation(false, cancellation);
            }
            return new Failure(new IllegalArgumentException("get() threw CancellationException, despite reporting isCancelled() == false: " + future, cancellation));
        } catch (Throwable t) {
            return new Failure(t);
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
    static void complete(AbstractResolvableFuture<?> abstractResolvableFuture) {
        Listener next = null;
        AbstractResolvableFuture abstractResolvableFuture2 = abstractResolvableFuture;
        while (true) {
            abstractResolvableFuture2.releaseWaiters();
            abstractResolvableFuture2.afterDone();
            next = abstractResolvableFuture2.clearListeners(next);
            while (true) {
                if (next != null) {
                    Listener curr = next;
                    next = next.next;
                    Runnable task = curr.task;
                    if (task instanceof SetFuture) {
                        SetFuture<?> setFuture = (SetFuture) task;
                        abstractResolvableFuture2 = setFuture.owner;
                        if (abstractResolvableFuture2.value == setFuture) {
                            if (ATOMIC_HELPER.casValue(abstractResolvableFuture2, setFuture, getFutureValue(setFuture.future))) {
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
    public void afterDone() {
    }

    /* access modifiers changed from: package-private */
    public final void maybePropagateCancellationTo(@Nullable Future<?> related) {
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
                pendingDescription = "Exception thrown from implementation: " + e.getClass();
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
    @Nullable
    public String pendingToString() {
        Object localValue = this.value;
        if (localValue instanceof SetFuture) {
            return "setFuture=[" + userObjectToString(((SetFuture) localValue).future) + "]";
        } else if (!(this instanceof ScheduledFuture)) {
            return null;
        } else {
            return "remaining delay=[" + ((ScheduledFuture) this).getDelay(TimeUnit.MILLISECONDS) + " ms]";
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
     method: ClspMth{java.util.logging.Logger.log(java.util.logging.Level, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.RuntimeException]
     candidates:
      ClspMth{java.util.logging.Logger.log(java.util.logging.Level, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.log(java.util.logging.Level, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.log(java.util.logging.Level, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.log(java.util.logging.Level, java.lang.String, java.lang.Throwable):void} */
    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            logger.log(level, "RuntimeException while executing runnable " + runnable + " with executor " + executor, (Throwable) e);
        }
    }

    private static abstract class AtomicHelper {
        /* access modifiers changed from: package-private */
        public abstract boolean casListeners(AbstractResolvableFuture<?> abstractResolvableFuture, Listener listener, Listener listener2);

        /* access modifiers changed from: package-private */
        public abstract boolean casValue(AbstractResolvableFuture<?> abstractResolvableFuture, Object obj, Object obj2);

        /* access modifiers changed from: package-private */
        public abstract boolean casWaiters(AbstractResolvableFuture<?> abstractResolvableFuture, Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: package-private */
        public abstract void putNext(Waiter waiter, Waiter waiter2);

        /* access modifiers changed from: package-private */
        public abstract void putThread(Waiter waiter, Thread thread);

        private AtomicHelper() {
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<Waiter, Thread> waiterThreadUpdater2, AtomicReferenceFieldUpdater<Waiter, Waiter> waiterNextUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Waiter> waitersUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Listener> listenersUpdater2, AtomicReferenceFieldUpdater<AbstractResolvableFuture, Object> valueUpdater2) {
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
        public boolean casWaiters(AbstractResolvableFuture<?> future, Waiter expect, Waiter update) {
            return this.waitersUpdater.compareAndSet(future, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractResolvableFuture<?> future, Listener expect, Listener update) {
            return this.listenersUpdater.compareAndSet(future, expect, update);
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractResolvableFuture<?> future, Object expect, Object update) {
            return this.valueUpdater.compareAndSet(future, expect, update);
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        SynchronizedHelper() {
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
        public boolean casWaiters(AbstractResolvableFuture<?> future, Waiter expect, Waiter update) {
            synchronized (future) {
                if (future.waiters != expect) {
                    return false;
                }
                future.waiters = update;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean casListeners(AbstractResolvableFuture<?> future, Listener expect, Listener update) {
            synchronized (future) {
                if (future.listeners != expect) {
                    return false;
                }
                future.listeners = update;
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean casValue(AbstractResolvableFuture<?> future, Object expect, Object update) {
            synchronized (future) {
                if (future.value != expect) {
                    return false;
                }
                future.value = update;
                return true;
            }
        }
    }

    private static CancellationException cancellationExceptionWithCause(@Nullable String message, @Nullable Throwable cause) {
        CancellationException exception = new CancellationException(message);
        exception.initCause(cause);
        return exception;
    }

    @NonNull
    static <T> T checkNotNull(@Nullable T reference) {
        if (reference != null) {
            return reference;
        }
        throw new NullPointerException();
    }
}
