package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@CanIgnoreReturnValue
@Beta
public class CycleDetectingLockFactory {
    private static final ThreadLocal<ArrayList<LockGraphNode>> acquiredLocks = new ThreadLocal<ArrayList<LockGraphNode>>() {
        /* access modifiers changed from: protected */
        public ArrayList<LockGraphNode> initialValue() {
            return Lists.newArrayListWithCapacity(3);
        }
    };
    private static final ConcurrentMap<Class<? extends Enum>, Map<? extends Enum, LockGraphNode>> lockGraphNodesPerType = new MapMaker().weakKeys().makeMap();
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(CycleDetectingLockFactory.class.getName());
    final Policy policy;

    private interface CycleDetectingLock {
        LockGraphNode getLockGraphNode();

        boolean isAcquiredByCurrentThread();
    }

    @Beta
    public enum Policies implements Policy {
        THROW {
            public void handlePotentialDeadlock(PotentialDeadlockException e) {
                throw e;
            }
        },
        WARN {
            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
             arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, com.google.common.util.concurrent.CycleDetectingLockFactory$PotentialDeadlockException]
             candidates:
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
            public void handlePotentialDeadlock(PotentialDeadlockException e) {
                CycleDetectingLockFactory.logger.logp(Level.SEVERE, "com.google.common.util.concurrent.CycleDetectingLockFactory$Policies$2", "handlePotentialDeadlock", "Detected potential deadlock", (Throwable) e);
            }
        },
        DISABLED {
            public void handlePotentialDeadlock(PotentialDeadlockException e) {
            }
        }
    }

    @Beta
    public interface Policy {
        void handlePotentialDeadlock(PotentialDeadlockException potentialDeadlockException);
    }

    public static CycleDetectingLockFactory newInstance(Policy policy2) {
        return new CycleDetectingLockFactory(policy2);
    }

    public ReentrantLock newReentrantLock(String lockName) {
        return newReentrantLock(lockName, false);
    }

    public ReentrantLock newReentrantLock(String lockName, boolean fair) {
        if (this.policy == Policies.DISABLED) {
            return new ReentrantLock(fair);
        }
        return new CycleDetectingReentrantLock(new LockGraphNode(lockName), fair);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName) {
        return newReentrantReadWriteLock(lockName, false);
    }

    public ReentrantReadWriteLock newReentrantReadWriteLock(String lockName, boolean fair) {
        if (this.policy == Policies.DISABLED) {
            return new ReentrantReadWriteLock(fair);
        }
        return new CycleDetectingReentrantReadWriteLock(new LockGraphNode(lockName), fair);
    }

    public static <E extends Enum<E>> WithExplicitOrdering<E> newInstanceWithExplicitOrdering(Class<E> enumClass, Policy policy2) {
        Preconditions.checkNotNull(enumClass);
        Preconditions.checkNotNull(policy2);
        return new WithExplicitOrdering<>(policy2, getOrCreateNodes(enumClass));
    }

    private static Map<? extends Enum, LockGraphNode> getOrCreateNodes(Class<? extends Enum> clazz) {
        Map<? extends Enum, LockGraphNode> existing = lockGraphNodesPerType.get(clazz);
        if (existing != null) {
            return existing;
        }
        Map<? extends Enum, LockGraphNode> created = createNodes(clazz);
        return (Map) MoreObjects.firstNonNull(lockGraphNodesPerType.putIfAbsent(clazz, created), created);
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [java.lang.Class, java.lang.Class<E>] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.EnumMap.put(java.lang.Enum, java.lang.Object):V}
     arg types: [E, com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode]
     candidates:
      ClspMth{java.util.EnumMap.put(java.lang.Object, java.lang.Object):java.lang.Object}
      ClspMth{java.util.AbstractMap.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.EnumMap.put(java.lang.Enum, java.lang.Object):V} */
    /* JADX WARNING: Unknown variable types count: 1 */
    @com.google.common.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <E extends java.lang.Enum<E>> java.util.Map<E, com.google.common.util.concurrent.CycleDetectingLockFactory.LockGraphNode> createNodes(java.lang.Class<E> r10) {
        /*
            java.util.EnumMap r0 = com.google.common.collect.Maps.newEnumMap(r10)
            java.lang.Object[] r1 = r10.getEnumConstants()
            java.lang.Enum[] r1 = (java.lang.Enum[]) r1
            int r2 = r1.length
            java.util.ArrayList r3 = com.google.common.collect.Lists.newArrayListWithCapacity(r2)
            int r4 = r1.length
            r5 = 0
            r6 = 0
        L_0x0012:
            if (r6 >= r4) goto L_0x0028
            r7 = r1[r6]
            com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode r8 = new com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode
            java.lang.String r9 = getLockName(r7)
            r8.<init>(r9)
            r3.add(r8)
            r0.put(r7, r8)
            int r6 = r6 + 1
            goto L_0x0012
        L_0x0028:
            r4 = 1
        L_0x0029:
            if (r4 >= r2) goto L_0x003d
            java.lang.Object r6 = r3.get(r4)
            com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode r6 = (com.google.common.util.concurrent.CycleDetectingLockFactory.LockGraphNode) r6
            com.google.common.util.concurrent.CycleDetectingLockFactory$Policies r7 = com.google.common.util.concurrent.CycleDetectingLockFactory.Policies.THROW
            java.util.List r8 = r3.subList(r5, r4)
            r6.checkAcquiredLocks(r7, r8)
            int r4 = r4 + 1
            goto L_0x0029
        L_0x003d:
            r4 = 0
        L_0x003e:
            int r5 = r2 + -1
            if (r4 >= r5) goto L_0x0056
            java.lang.Object r5 = r3.get(r4)
            com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode r5 = (com.google.common.util.concurrent.CycleDetectingLockFactory.LockGraphNode) r5
            com.google.common.util.concurrent.CycleDetectingLockFactory$Policies r6 = com.google.common.util.concurrent.CycleDetectingLockFactory.Policies.DISABLED
            int r7 = r4 + 1
            java.util.List r7 = r3.subList(r7, r2)
            r5.checkAcquiredLocks(r6, r7)
            int r4 = r4 + 1
            goto L_0x003e
        L_0x0056:
            java.util.Map r4 = java.util.Collections.unmodifiableMap(r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.CycleDetectingLockFactory.createNodes(java.lang.Class):java.util.Map");
    }

    private static String getLockName(Enum<?> rank) {
        String simpleName = rank.getDeclaringClass().getSimpleName();
        String name = rank.name();
        StringBuilder sb = new StringBuilder(String.valueOf(simpleName).length() + 1 + String.valueOf(name).length());
        sb.append(simpleName);
        sb.append(".");
        sb.append(name);
        return sb.toString();
    }

    @Beta
    public static final class WithExplicitOrdering<E extends Enum<E>> extends CycleDetectingLockFactory {
        private final Map<E, LockGraphNode> lockGraphNodes;

        @VisibleForTesting
        WithExplicitOrdering(Policy policy, Map<E, LockGraphNode> lockGraphNodes2) {
            super(policy);
            this.lockGraphNodes = lockGraphNodes2;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.util.concurrent.CycleDetectingLockFactory.WithExplicitOrdering.newReentrantLock(java.lang.Enum, boolean):java.util.concurrent.locks.ReentrantLock
         arg types: [E, int]
         candidates:
          com.google.common.util.concurrent.CycleDetectingLockFactory.newReentrantLock(java.lang.String, boolean):java.util.concurrent.locks.ReentrantLock
          com.google.common.util.concurrent.CycleDetectingLockFactory.WithExplicitOrdering.newReentrantLock(java.lang.Enum, boolean):java.util.concurrent.locks.ReentrantLock */
        public ReentrantLock newReentrantLock(E rank) {
            return newReentrantLock((Enum) rank, false);
        }

        public ReentrantLock newReentrantLock(E rank, boolean fair) {
            if (this.policy == Policies.DISABLED) {
                return new ReentrantLock(fair);
            }
            return new CycleDetectingReentrantLock(this.lockGraphNodes.get(rank), fair);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.util.concurrent.CycleDetectingLockFactory.WithExplicitOrdering.newReentrantReadWriteLock(java.lang.Enum, boolean):java.util.concurrent.locks.ReentrantReadWriteLock
         arg types: [E, int]
         candidates:
          com.google.common.util.concurrent.CycleDetectingLockFactory.newReentrantReadWriteLock(java.lang.String, boolean):java.util.concurrent.locks.ReentrantReadWriteLock
          com.google.common.util.concurrent.CycleDetectingLockFactory.WithExplicitOrdering.newReentrantReadWriteLock(java.lang.Enum, boolean):java.util.concurrent.locks.ReentrantReadWriteLock */
        public ReentrantReadWriteLock newReentrantReadWriteLock(E rank) {
            return newReentrantReadWriteLock((Enum) rank, false);
        }

        public ReentrantReadWriteLock newReentrantReadWriteLock(E rank, boolean fair) {
            if (this.policy == Policies.DISABLED) {
                return new ReentrantReadWriteLock(fair);
            }
            return new CycleDetectingReentrantReadWriteLock(this.lockGraphNodes.get(rank), fair);
        }
    }

    private CycleDetectingLockFactory(Policy policy2) {
        this.policy = (Policy) Preconditions.checkNotNull(policy2);
    }

    private static class ExampleStackTrace extends IllegalStateException {
        static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
        static final ImmutableSet<String> EXCLUDED_CLASS_NAMES = ImmutableSet.m152of(CycleDetectingLockFactory.class.getName(), ExampleStackTrace.class.getName(), LockGraphNode.class.getName());

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        ExampleStackTrace(com.google.common.util.concurrent.CycleDetectingLockFactory.LockGraphNode r6, com.google.common.util.concurrent.CycleDetectingLockFactory.LockGraphNode r7) {
            /*
                r5 = this;
                java.lang.String r0 = r6.getLockName()
                java.lang.String r1 = r7.getLockName()
                java.lang.String r2 = java.lang.String.valueOf(r0)
                int r2 = r2.length()
                int r2 = r2 + 4
                java.lang.String r3 = java.lang.String.valueOf(r1)
                int r3 = r3.length()
                int r2 = r2 + r3
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>(r2)
                r3.append(r0)
                java.lang.String r0 = " -> "
                r3.append(r0)
                r3.append(r1)
                java.lang.String r0 = r3.toString()
                r5.<init>(r0)
                java.lang.StackTraceElement[] r0 = r5.getStackTrace()
                r1 = 0
                int r2 = r0.length
            L_0x0038:
                if (r1 >= r2) goto L_0x006d
                java.lang.Class<com.google.common.util.concurrent.CycleDetectingLockFactory$WithExplicitOrdering> r3 = com.google.common.util.concurrent.CycleDetectingLockFactory.WithExplicitOrdering.class
                java.lang.String r3 = r3.getName()
                r4 = r0[r1]
                java.lang.String r4 = r4.getClassName()
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L_0x0052
                java.lang.StackTraceElement[] r3 = com.google.common.util.concurrent.CycleDetectingLockFactory.ExampleStackTrace.EMPTY_STACK_TRACE
                r5.setStackTrace(r3)
                goto L_0x006d
            L_0x0052:
                com.google.common.collect.ImmutableSet<java.lang.String> r3 = com.google.common.util.concurrent.CycleDetectingLockFactory.ExampleStackTrace.EXCLUDED_CLASS_NAMES
                r4 = r0[r1]
                java.lang.String r4 = r4.getClassName()
                boolean r3 = r3.contains(r4)
                if (r3 != 0) goto L_0x006a
                java.lang.Object[] r3 = java.util.Arrays.copyOfRange(r0, r1, r2)
                java.lang.StackTraceElement[] r3 = (java.lang.StackTraceElement[]) r3
                r5.setStackTrace(r3)
                goto L_0x006d
            L_0x006a:
                int r1 = r1 + 1
                goto L_0x0038
            L_0x006d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.CycleDetectingLockFactory.ExampleStackTrace.<init>(com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode, com.google.common.util.concurrent.CycleDetectingLockFactory$LockGraphNode):void");
        }
    }

    @Beta
    public static final class PotentialDeadlockException extends ExampleStackTrace {
        private final ExampleStackTrace conflictingStackTrace;

        private PotentialDeadlockException(LockGraphNode node1, LockGraphNode node2, ExampleStackTrace conflictingStackTrace2) {
            super(node1, node2);
            this.conflictingStackTrace = conflictingStackTrace2;
            initCause(conflictingStackTrace2);
        }

        public ExampleStackTrace getConflictingStackTrace() {
            return this.conflictingStackTrace;
        }

        public String getMessage() {
            StringBuilder message = new StringBuilder(super.getMessage());
            for (Throwable t = this.conflictingStackTrace; t != null; t = t.getCause()) {
                message.append(", ");
                message.append(t.getMessage());
            }
            return message.toString();
        }
    }

    private static class LockGraphNode {
        final Map<LockGraphNode, ExampleStackTrace> allowedPriorLocks = new MapMaker().weakKeys().makeMap();
        final Map<LockGraphNode, PotentialDeadlockException> disallowedPriorLocks = new MapMaker().weakKeys().makeMap();
        final String lockName;

        LockGraphNode(String lockName2) {
            this.lockName = (String) Preconditions.checkNotNull(lockName2);
        }

        /* access modifiers changed from: package-private */
        public String getLockName() {
            return this.lockName;
        }

        /* access modifiers changed from: package-private */
        public void checkAcquiredLocks(Policy policy, List<LockGraphNode> acquiredLocks) {
            int size = acquiredLocks.size();
            for (int i = 0; i < size; i++) {
                checkAcquiredLock(policy, acquiredLocks.get(i));
            }
        }

        /* access modifiers changed from: package-private */
        public void checkAcquiredLock(Policy policy, LockGraphNode acquiredLock) {
            Preconditions.checkState(this != acquiredLock, "Attempted to acquire multiple locks with the same rank %s", acquiredLock.getLockName());
            if (!this.allowedPriorLocks.containsKey(acquiredLock)) {
                PotentialDeadlockException previousDeadlockException = this.disallowedPriorLocks.get(acquiredLock);
                if (previousDeadlockException != null) {
                    policy.handlePotentialDeadlock(new PotentialDeadlockException(acquiredLock, this, previousDeadlockException.getConflictingStackTrace()));
                    return;
                }
                ExampleStackTrace path = acquiredLock.findPathTo(this, Sets.newIdentityHashSet());
                if (path == null) {
                    this.allowedPriorLocks.put(acquiredLock, new ExampleStackTrace(acquiredLock, this));
                    return;
                }
                PotentialDeadlockException exception = new PotentialDeadlockException(acquiredLock, this, path);
                this.disallowedPriorLocks.put(acquiredLock, exception);
                policy.handlePotentialDeadlock(exception);
            }
        }

        @NullableDecl
        private ExampleStackTrace findPathTo(LockGraphNode node, Set<LockGraphNode> seen) {
            if (!seen.add(this)) {
                return null;
            }
            ExampleStackTrace found = this.allowedPriorLocks.get(node);
            if (found != null) {
                return found;
            }
            for (Map.Entry<LockGraphNode, ExampleStackTrace> entry : this.allowedPriorLocks.entrySet()) {
                LockGraphNode preAcquiredLock = (LockGraphNode) entry.getKey();
                ExampleStackTrace found2 = preAcquiredLock.findPathTo(node, seen);
                if (found2 != null) {
                    ExampleStackTrace path = new ExampleStackTrace(preAcquiredLock, this);
                    path.setStackTrace(((ExampleStackTrace) entry.getValue()).getStackTrace());
                    path.initCause(found2);
                    return path;
                }
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public void aboutToAcquire(CycleDetectingLock lock) {
        if (!lock.isAcquiredByCurrentThread()) {
            ArrayList<LockGraphNode> acquiredLockList = acquiredLocks.get();
            LockGraphNode node = lock.getLockGraphNode();
            node.checkAcquiredLocks(this.policy, acquiredLockList);
            acquiredLockList.add(node);
        }
    }

    /* access modifiers changed from: private */
    public static void lockStateChanged(CycleDetectingLock lock) {
        if (!lock.isAcquiredByCurrentThread()) {
            ArrayList<LockGraphNode> acquiredLockList = acquiredLocks.get();
            LockGraphNode node = lock.getLockGraphNode();
            for (int i = acquiredLockList.size() - 1; i >= 0; i--) {
                if (acquiredLockList.get(i) == node) {
                    acquiredLockList.remove(i);
                    return;
                }
            }
        }
    }

    final class CycleDetectingReentrantLock extends ReentrantLock implements CycleDetectingLock {
        private final LockGraphNode lockGraphNode;

        private CycleDetectingReentrantLock(LockGraphNode lockGraphNode2, boolean fair) {
            super(fair);
            this.lockGraphNode = (LockGraphNode) Preconditions.checkNotNull(lockGraphNode2);
        }

        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        public boolean isAcquiredByCurrentThread() {
            return isHeldByCurrentThread();
        }

        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this);
            try {
                return super.tryLock(timeout, unit);
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this);
            }
        }
    }

    final class CycleDetectingReentrantReadWriteLock extends ReentrantReadWriteLock implements CycleDetectingLock {
        private final LockGraphNode lockGraphNode;
        private final CycleDetectingReentrantReadLock readLock;
        private final CycleDetectingReentrantWriteLock writeLock;

        private CycleDetectingReentrantReadWriteLock(CycleDetectingLockFactory this$0, LockGraphNode lockGraphNode2, boolean fair) {
            super(fair);
            this.readLock = new CycleDetectingReentrantReadLock(this);
            this.writeLock = new CycleDetectingReentrantWriteLock(this);
            this.lockGraphNode = (LockGraphNode) Preconditions.checkNotNull(lockGraphNode2);
        }

        public ReentrantReadWriteLock.ReadLock readLock() {
            return this.readLock;
        }

        public ReentrantReadWriteLock.WriteLock writeLock() {
            return this.writeLock;
        }

        public LockGraphNode getLockGraphNode() {
            return this.lockGraphNode;
        }

        public boolean isAcquiredByCurrentThread() {
            return isWriteLockedByCurrentThread() || getReadHoldCount() > 0;
        }
    }

    private class CycleDetectingReentrantReadLock extends ReentrantReadWriteLock.ReadLock {
        @Weak
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantReadLock(CycleDetectingReentrantReadWriteLock readWriteLock2) {
            super(readWriteLock2);
            this.readWriteLock = readWriteLock2;
        }

        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock(timeout, unit);
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }
    }

    private class CycleDetectingReentrantWriteLock extends ReentrantReadWriteLock.WriteLock {
        @Weak
        final CycleDetectingReentrantReadWriteLock readWriteLock;

        CycleDetectingReentrantWriteLock(CycleDetectingReentrantReadWriteLock readWriteLock2) {
            super(readWriteLock2);
            this.readWriteLock = readWriteLock2;
        }

        public void lock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public void lockInterruptibly() throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                super.lockInterruptibly();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock() {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
            CycleDetectingLockFactory.this.aboutToAcquire(this.readWriteLock);
            try {
                return super.tryLock(timeout, unit);
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }

        public void unlock() {
            try {
                super.unlock();
            } finally {
                CycleDetectingLockFactory.lockStateChanged(this.readWriteLock);
            }
        }
    }
}
