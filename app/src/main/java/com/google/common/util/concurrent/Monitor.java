package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import com.google.j2objc.annotations.Weak;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public final class Monitor {
    @GuardedBy("lock")
    private Guard activeGuards;
    private final boolean fair;
    /* access modifiers changed from: private */
    public final ReentrantLock lock;

    @Beta
    public static abstract class Guard {
        final Condition condition;
        @Weak
        final Monitor monitor;
        @NullableDecl
        @GuardedBy("monitor.lock")
        Guard next;
        @GuardedBy("monitor.lock")
        int waiterCount = 0;

        public abstract boolean isSatisfied();

        protected Guard(Monitor monitor2) {
            this.monitor = (Monitor) Preconditions.checkNotNull(monitor2, "monitor");
            this.condition = monitor2.lock.newCondition();
        }
    }

    public Monitor() {
        this(false);
    }

    public Monitor(boolean fair2) {
        this.activeGuards = null;
        this.fair = fair2;
        this.lock = new ReentrantLock(fair2);
    }

    public void enter() {
        this.lock.lock();
    }

    public boolean enter(long time, TimeUnit unit) {
        long remainingNanos;
        boolean tryLock;
        long timeoutNanos = toSafeNanos(time, unit);
        ReentrantLock lock2 = this.lock;
        if (!this.fair && lock2.tryLock()) {
            return true;
        }
        boolean interrupted = Thread.interrupted();
        try {
            remainingNanos = timeoutNanos;
            while (true) {
                tryLock = lock2.tryLock(remainingNanos, TimeUnit.NANOSECONDS);
                break;
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            return tryLock;
        } catch (InterruptedException e) {
            interrupted = true;
            remainingNanos = remainingNanos(System.nanoTime(), timeoutNanos);
        } catch (Throwable th) {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
            throw th;
        }
    }

    public void enterInterruptibly() throws InterruptedException {
        this.lock.lockInterruptibly();
    }

    public boolean enterInterruptibly(long time, TimeUnit unit) throws InterruptedException {
        return this.lock.tryLock(time, unit);
    }

    public boolean tryEnter() {
        return this.lock.tryLock();
    }

    public void enterWhen(Guard guard) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            boolean signalBeforeWaiting = lock2.isHeldByCurrentThread();
            lock2.lockInterruptibly();
            boolean satisfied = false;
            try {
                if (!guard.isSatisfied()) {
                    await(guard, signalBeforeWaiting);
                }
                satisfied = true;
            } finally {
                if (!satisfied) {
                    leave();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0048 A[Catch:{ all -> 0x0077, all -> 0x0082 }] */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x0065 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean enterWhen(com.google.common.util.concurrent.Monitor.Guard r18, long r19, java.util.concurrent.TimeUnit r21) throws java.lang.InterruptedException {
        /*
            r17 = this;
            r1 = r17
            r2 = r18
            long r3 = toSafeNanos(r19, r21)
            com.google.common.util.concurrent.Monitor r0 = r2.monitor
            if (r0 != r1) goto L_0x008c
            java.util.concurrent.locks.ReentrantLock r5 = r1.lock
            boolean r6 = r5.isHeldByCurrentThread()
            r7 = 0
            boolean r0 = r1.fair
            r9 = 0
            if (r0 != 0) goto L_0x0030
            boolean r0 = java.lang.Thread.interrupted()
            if (r0 != 0) goto L_0x002a
            boolean r0 = r5.tryLock()
            if (r0 == 0) goto L_0x0030
            r10 = r19
            r12 = r21
            goto L_0x003f
        L_0x002a:
            java.lang.InterruptedException r0 = new java.lang.InterruptedException
            r0.<init>()
            throw r0
        L_0x0030:
            long r7 = initNanoTime(r3)
            r10 = r19
            r12 = r21
            boolean r0 = r5.tryLock(r10, r12)
            if (r0 != 0) goto L_0x003f
            return r9
        L_0x003f:
            r13 = 0
            r14 = 1
            boolean r0 = r18.isSatisfied()     // Catch:{ all -> 0x0077 }
            if (r0 != 0) goto L_0x005e
            r15 = 0
            int r0 = (r7 > r15 ? 1 : (r7 == r15 ? 0 : -1))
            if (r0 != 0) goto L_0x0050
            r9 = r3
            goto L_0x0055
        L_0x0050:
            long r15 = remainingNanos(r7, r3)     // Catch:{ all -> 0x0077 }
            r9 = r15
        L_0x0055:
            boolean r9 = r1.awaitNanos(r2, r9, r6)     // Catch:{ all -> 0x0077 }
            if (r9 == 0) goto L_0x005c
            goto L_0x005e
        L_0x005c:
            r0 = 0
            goto L_0x0060
        L_0x005e:
            r9 = 1
            r0 = 1
        L_0x0060:
            r9 = r0
            r10 = 0
            if (r9 != 0) goto L_0x0076
            if (r10 == 0) goto L_0x0073
            if (r6 != 0) goto L_0x0073
            r17.signalNextWaiter()     // Catch:{ all -> 0x006d }
            goto L_0x0073
        L_0x006d:
            r0 = move-exception
            r11 = r0
            r5.unlock()
            throw r11
        L_0x0073:
            r5.unlock()
        L_0x0076:
            return r9
        L_0x0077:
            r0 = move-exception
            if (r13 != 0) goto L_0x008b
            if (r14 == 0) goto L_0x0088
            if (r6 != 0) goto L_0x0088
            r17.signalNextWaiter()     // Catch:{ all -> 0x0082 }
            goto L_0x0088
        L_0x0082:
            r0 = move-exception
            r9 = r0
            r5.unlock()
            throw r9
        L_0x0088:
            r5.unlock()
        L_0x008b:
            throw r0
        L_0x008c:
            r12 = r21
            java.lang.IllegalMonitorStateException r0 = new java.lang.IllegalMonitorStateException
            r0.<init>()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.Monitor.enterWhen(com.google.common.util.concurrent.Monitor$Guard, long, java.util.concurrent.TimeUnit):boolean");
    }

    public void enterWhenUninterruptibly(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            boolean signalBeforeWaiting = lock2.isHeldByCurrentThread();
            lock2.lock();
            boolean satisfied = false;
            try {
                if (!guard.isSatisfied()) {
                    awaitUninterruptibly(guard, signalBeforeWaiting);
                }
                satisfied = true;
            } finally {
                if (!satisfied) {
                    leave();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterWhenUninterruptibly(Guard guard, long time, TimeUnit unit) {
        boolean satisfied;
        long remainingNanos;
        long timeoutNanos = toSafeNanos(time, unit);
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            long startTime = 0;
            boolean signalBeforeWaiting = lock2.isHeldByCurrentThread();
            boolean interrupted = Thread.interrupted();
            try {
                if (this.fair || !lock2.tryLock()) {
                    startTime = initNanoTime(timeoutNanos);
                    long remainingNanos2 = timeoutNanos;
                    while (true) {
                        try {
                            break;
                        } catch (InterruptedException e) {
                            interrupted = true;
                            remainingNanos2 = remainingNanos(startTime, timeoutNanos);
                        }
                    }
                    if (!lock2.tryLock(remainingNanos2, TimeUnit.NANOSECONDS)) {
                        if (interrupted) {
                            Thread.currentThread().interrupt();
                        }
                        return false;
                    }
                }
                while (true) {
                    break;
                }
                if (guard.isSatisfied()) {
                    satisfied = true;
                } else {
                    if (startTime == 0) {
                        long startTime2 = initNanoTime(timeoutNanos);
                        remainingNanos = timeoutNanos;
                    } else {
                        remainingNanos = remainingNanos(startTime, timeoutNanos);
                    }
                    satisfied = awaitNanos(guard, remainingNanos, signalBeforeWaiting);
                }
                if (!satisfied) {
                    lock2.unlock();
                }
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                return satisfied;
            } catch (InterruptedException e2) {
                interrupted = true;
                signalBeforeWaiting = false;
            } catch (Throwable th) {
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                throw th;
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIf(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            lock2.lock();
            boolean satisfied = false;
            try {
                boolean isSatisfied = guard.isSatisfied();
                satisfied = isSatisfied;
                return isSatisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIf(Guard guard, long time, TimeUnit unit) {
        if (guard.monitor != this) {
            throw new IllegalMonitorStateException();
        } else if (!enter(time, unit)) {
            return false;
        } else {
            boolean satisfied = false;
            try {
                boolean isSatisfied = guard.isSatisfied();
                satisfied = isSatisfied;
                return isSatisfied;
            } finally {
                if (!satisfied) {
                    this.lock.unlock();
                }
            }
        }
    }

    public boolean enterIfInterruptibly(Guard guard) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            lock2.lockInterruptibly();
            boolean satisfied = false;
            try {
                boolean isSatisfied = guard.isSatisfied();
                satisfied = isSatisfied;
                return isSatisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean enterIfInterruptibly(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            if (!lock2.tryLock(time, unit)) {
                return false;
            }
            boolean satisfied = false;
            try {
                boolean isSatisfied = guard.isSatisfied();
                satisfied = isSatisfied;
                return isSatisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public boolean tryEnterIf(Guard guard) {
        if (guard.monitor == this) {
            ReentrantLock lock2 = this.lock;
            if (!lock2.tryLock()) {
                return false;
            }
            boolean satisfied = false;
            try {
                boolean isSatisfied = guard.isSatisfied();
                satisfied = isSatisfied;
                return isSatisfied;
            } finally {
                if (!satisfied) {
                    lock2.unlock();
                }
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    public void waitFor(Guard guard) throws InterruptedException {
        if (!(guard.monitor == this) || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            await(guard, true);
        }
    }

    public boolean waitFor(Guard guard, long time, TimeUnit unit) throws InterruptedException {
        long timeoutNanos = toSafeNanos(time, unit);
        if (!(guard.monitor == this) || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied()) {
            return true;
        } else {
            if (!Thread.interrupted()) {
                return awaitNanos(guard, timeoutNanos, true);
            }
            throw new InterruptedException();
        }
    }

    public void waitForUninterruptibly(Guard guard) {
        if (!(guard.monitor == this) || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (!guard.isSatisfied()) {
            awaitUninterruptibly(guard, true);
        }
    }

    public boolean waitForUninterruptibly(Guard guard, long time, TimeUnit unit) {
        long timeoutNanos = toSafeNanos(time, unit);
        if (!(guard.monitor == this) || !this.lock.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException();
        } else if (guard.isSatisfied()) {
            return true;
        } else {
            long startTime = initNanoTime(timeoutNanos);
            long remainingNanos = timeoutNanos;
            boolean interrupted = Thread.interrupted();
            boolean signalBeforeWaiting = true;
            while (true) {
                try {
                    boolean signalBeforeWaiting2 = awaitNanos(guard, remainingNanos, signalBeforeWaiting);
                    if (interrupted) {
                        Thread.currentThread().interrupt();
                    }
                    return signalBeforeWaiting2;
                } catch (InterruptedException e) {
                    interrupted = true;
                    if (guard.isSatisfied()) {
                        if (1 != 0) {
                            Thread.currentThread().interrupt();
                        }
                        return true;
                    }
                    signalBeforeWaiting = false;
                    remainingNanos = remainingNanos(startTime, timeoutNanos);
                } catch (Throwable th) {
                    if (1 != 0) {
                        Thread.currentThread().interrupt();
                    }
                    throw th;
                }
            }
        }
    }

    public void leave() {
        ReentrantLock lock2 = this.lock;
        try {
            if (lock2.getHoldCount() == 1) {
                signalNextWaiter();
            }
        } finally {
            lock2.unlock();
        }
    }

    public boolean isFair() {
        return this.fair;
    }

    public boolean isOccupied() {
        return this.lock.isLocked();
    }

    public boolean isOccupiedByCurrentThread() {
        return this.lock.isHeldByCurrentThread();
    }

    public int getOccupiedDepth() {
        return this.lock.getHoldCount();
    }

    public int getQueueLength() {
        return this.lock.getQueueLength();
    }

    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public boolean hasQueuedThread(Thread thread) {
        return this.lock.hasQueuedThread(thread);
    }

    public boolean hasWaiters(Guard guard) {
        return getWaitQueueLength(guard) > 0;
    }

    public int getWaitQueueLength(Guard guard) {
        if (guard.monitor == this) {
            this.lock.lock();
            try {
                return guard.waiterCount;
            } finally {
                this.lock.unlock();
            }
        } else {
            throw new IllegalMonitorStateException();
        }
    }

    private static long toSafeNanos(long time, TimeUnit unit) {
        long timeoutNanos = unit.toNanos(time);
        if (timeoutNanos <= 0) {
            return 0;
        }
        if (timeoutNanos > 6917529027641081853L) {
            return 6917529027641081853L;
        }
        return timeoutNanos;
    }

    private static long initNanoTime(long timeoutNanos) {
        if (timeoutNanos <= 0) {
            return 0;
        }
        long startTime = System.nanoTime();
        if (startTime == 0) {
            return 1;
        }
        return startTime;
    }

    private static long remainingNanos(long startTime, long timeoutNanos) {
        if (timeoutNanos <= 0) {
            return 0;
        }
        return timeoutNanos - (System.nanoTime() - startTime);
    }

    @GuardedBy("lock")
    private void signalNextWaiter() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            if (isSatisfied(guard)) {
                guard.condition.signal();
                return;
            }
        }
    }

    @GuardedBy("lock")
    private boolean isSatisfied(Guard guard) {
        try {
            return guard.isSatisfied();
        } catch (Throwable throwable) {
            signalAllWaiters();
            throw throwable;
        }
    }

    @GuardedBy("lock")
    private void signalAllWaiters() {
        for (Guard guard = this.activeGuards; guard != null; guard = guard.next) {
            guard.condition.signalAll();
        }
    }

    @GuardedBy("lock")
    private void beginWaitingFor(Guard guard) {
        int waiters = guard.waiterCount;
        guard.waiterCount = waiters + 1;
        if (waiters == 0) {
            guard.next = this.activeGuards;
            this.activeGuards = guard;
        }
    }

    @GuardedBy("lock")
    private void endWaitingFor(Guard guard) {
        int waiters = guard.waiterCount - 1;
        guard.waiterCount = waiters;
        if (waiters == 0) {
            Guard p = this.activeGuards;
            Guard pred = null;
            while (p != guard) {
                pred = p;
                p = p.next;
            }
            if (pred == null) {
                this.activeGuards = p.next;
            } else {
                pred.next = p.next;
            }
            p.next = null;
        }
    }

    @GuardedBy("lock")
    private void await(Guard guard, boolean signalBeforeWaiting) throws InterruptedException {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        do {
            try {
                guard.condition.await();
            } finally {
                endWaitingFor(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy("lock")
    private void awaitUninterruptibly(Guard guard, boolean signalBeforeWaiting) {
        if (signalBeforeWaiting) {
            signalNextWaiter();
        }
        beginWaitingFor(guard);
        do {
            try {
                guard.condition.awaitUninterruptibly();
            } finally {
                endWaitingFor(guard);
            }
        } while (!guard.isSatisfied());
    }

    @GuardedBy("lock")
    private boolean awaitNanos(Guard guard, long nanos, boolean signalBeforeWaiting) throws InterruptedException {
        boolean firstTime = true;
        while (nanos > 0) {
            if (firstTime) {
                if (signalBeforeWaiting) {
                    try {
                        signalNextWaiter();
                    } catch (Throwable th) {
                        if (!firstTime) {
                            endWaitingFor(guard);
                        }
                        throw th;
                    }
                }
                beginWaitingFor(guard);
                firstTime = false;
            }
            nanos = guard.condition.awaitNanos(nanos);
            if (guard.isSatisfied()) {
                if (!firstTime) {
                    endWaitingFor(guard);
                }
                return true;
            }
        }
        if (!firstTime) {
            endWaitingFor(guard);
        }
        return false;
    }
}
