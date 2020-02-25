package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
public final class MoreExecutors {
    private MoreExecutors() {
    }

    @GwtIncompatible
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
        return new Application().getExitingExecutorService(executor, terminationTimeout, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
        return new Application().getExitingExecutorService(executor);
    }

    @GwtIncompatible
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
        return new Application().getExitingScheduledExecutorService(executor, terminationTimeout, timeUnit);
    }

    @GwtIncompatible
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
        return new Application().getExitingScheduledExecutorService(executor);
    }

    @GwtIncompatible
    @Beta
    public static void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        new Application().addDelayedShutdownHook(service, terminationTimeout, timeUnit);
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static void useDaemonThreadFactory(ThreadPoolExecutor executor) {
        executor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(executor.getThreadFactory()).build());
    }

    @GwtIncompatible
    public static ListeningExecutorService newDirectExecutorService() {
        return new DirectExecutorService();
    }

    public static Executor directExecutor() {
        return DirectExecutor.INSTANCE;
    }

    @GwtIncompatible
    @Beta
    public static Executor newSequentialExecutor(Executor delegate) {
        return new SequentialExecutor(delegate);
    }

    @GwtIncompatible
    public static ListeningExecutorService listeningDecorator(ExecutorService delegate) {
        if (delegate instanceof ListeningExecutorService) {
            return (ListeningExecutorService) delegate;
        }
        if (delegate instanceof ScheduledExecutorService) {
            return new ScheduledListeningDecorator((ScheduledExecutorService) delegate);
        }
        return new ListeningDecorator(delegate);
    }

    @GwtIncompatible
    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService delegate) {
        if (delegate instanceof ListeningScheduledExecutorService) {
            return (ListeningScheduledExecutorService) delegate;
        }
        return new ScheduledListeningDecorator(delegate);
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00e2 A[LOOP:2: B:49:0x00dc->B:51:0x00e2, LOOP_END] */
    @com.google.common.annotations.GwtIncompatible
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> T invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService r21, java.util.Collection<? extends java.util.concurrent.Callable<T>> r22, boolean r23, long r24, java.util.concurrent.TimeUnit r26) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
            r1 = r21
            com.google.common.base.Preconditions.checkNotNull(r21)
            com.google.common.base.Preconditions.checkNotNull(r26)
            int r2 = r22.size()
            r3 = 1
            if (r2 <= 0) goto L_0x0011
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            com.google.common.base.Preconditions.checkArgument(r0)
            java.util.ArrayList r4 = com.google.common.collect.Lists.newArrayListWithCapacity(r2)
            java.util.concurrent.LinkedBlockingQueue r5 = com.google.common.collect.Queues.newLinkedBlockingQueue()
            r6 = r24
            r8 = r26
            long r9 = r8.toNanos(r6)
            r0 = 0
            if (r23 == 0) goto L_0x0030
            long r11 = java.lang.System.nanoTime()     // Catch:{ all -> 0x002d }
            goto L_0x0032
        L_0x002d:
            r0 = move-exception
            goto L_0x00d8
        L_0x0030:
            r11 = 0
        L_0x0032:
            java.util.Iterator r13 = r22.iterator()     // Catch:{ all -> 0x002d }
            java.lang.Object r14 = r13.next()     // Catch:{ all -> 0x002d }
            java.util.concurrent.Callable r14 = (java.util.concurrent.Callable) r14     // Catch:{ all -> 0x002d }
            com.google.common.util.concurrent.ListenableFuture r14 = submitAndAddQueueListener(r1, r14, r5)     // Catch:{ all -> 0x002d }
            r4.add(r14)     // Catch:{ all -> 0x002d }
            int r2 = r2 + -1
            r14 = r3
            r15 = r11
            r11 = r0
        L_0x0048:
            java.lang.Object r0 = r5.poll()     // Catch:{ all -> 0x002d }
            java.util.concurrent.Future r0 = (java.util.concurrent.Future) r0     // Catch:{ all -> 0x002d }
            if (r0 != 0) goto L_0x009e
            if (r2 <= 0) goto L_0x0066
            int r2 = r2 + -1
            java.lang.Object r12 = r13.next()     // Catch:{ all -> 0x002d }
            java.util.concurrent.Callable r12 = (java.util.concurrent.Callable) r12     // Catch:{ all -> 0x002d }
            com.google.common.util.concurrent.ListenableFuture r12 = submitAndAddQueueListener(r1, r12, r5)     // Catch:{ all -> 0x002d }
            r4.add(r12)     // Catch:{ all -> 0x002d }
            int r14 = r14 + 1
            r12 = r2
            r2 = r0
            goto L_0x00a0
        L_0x0066:
            if (r14 != 0) goto L_0x0074
            if (r11 != 0) goto L_0x0072
            java.util.concurrent.ExecutionException r0 = new java.util.concurrent.ExecutionException     // Catch:{ all -> 0x002d }
            r12 = 0
            r0.<init>(r12)     // Catch:{ all -> 0x002d }
            r11 = r0
        L_0x0072:
            throw r11     // Catch:{ all -> 0x002d }
        L_0x0074:
            if (r23 == 0) goto L_0x0094
            java.util.concurrent.TimeUnit r12 = java.util.concurrent.TimeUnit.NANOSECONDS     // Catch:{ all -> 0x002d }
            java.lang.Object r12 = r5.poll(r9, r12)     // Catch:{ all -> 0x002d }
            java.util.concurrent.Future r12 = (java.util.concurrent.Future) r12     // Catch:{ all -> 0x002d }
            r0 = r12
            if (r0 == 0) goto L_0x008e
            long r17 = java.lang.System.nanoTime()     // Catch:{ all -> 0x002d }
            long r19 = r17 - r15
            long r9 = r9 - r19
            r15 = r17
            r12 = r2
            r2 = r0
            goto L_0x00a0
        L_0x008e:
            java.util.concurrent.TimeoutException r12 = new java.util.concurrent.TimeoutException     // Catch:{ all -> 0x002d }
            r12.<init>()     // Catch:{ all -> 0x002d }
            throw r12     // Catch:{ all -> 0x002d }
        L_0x0094:
            java.lang.Object r12 = r5.take()     // Catch:{ all -> 0x002d }
            java.util.concurrent.Future r12 = (java.util.concurrent.Future) r12     // Catch:{ all -> 0x002d }
            r0 = r12
            r12 = r2
            r2 = r0
            goto L_0x00a0
        L_0x009e:
            r12 = r2
            r2 = r0
        L_0x00a0:
            if (r2 == 0) goto L_0x00d3
            int r14 = r14 + -1
            java.lang.Object r0 = r2.get()     // Catch:{ ExecutionException -> 0x00ce, RuntimeException -> 0x00c4 }
            java.util.Iterator r17 = r4.iterator()
        L_0x00ac:
            boolean r18 = r17.hasNext()
            if (r18 == 0) goto L_0x00c0
            java.lang.Object r18 = r17.next()
            r1 = r18
            java.util.concurrent.Future r1 = (java.util.concurrent.Future) r1
            r1.cancel(r3)
            r1 = r21
            goto L_0x00ac
        L_0x00c0:
            return r0
        L_0x00c1:
            r0 = move-exception
            r2 = r12
            goto L_0x00d8
        L_0x00c4:
            r0 = move-exception
            r1 = r0
            r0 = r1
            java.util.concurrent.ExecutionException r1 = new java.util.concurrent.ExecutionException     // Catch:{ all -> 0x00c1 }
            r1.<init>(r0)     // Catch:{ all -> 0x00c1 }
            r11 = r1
            goto L_0x00d3
        L_0x00ce:
            r0 = move-exception
            r1 = r0
            r0 = r1
            r11 = r0
        L_0x00d3:
            r1 = r21
            r2 = r12
            goto L_0x0048
        L_0x00d8:
            java.util.Iterator r1 = r4.iterator()
        L_0x00dc:
            boolean r11 = r1.hasNext()
            if (r11 == 0) goto L_0x00ec
            java.lang.Object r11 = r1.next()
            java.util.concurrent.Future r11 = (java.util.concurrent.Future) r11
            r11.cancel(r3)
            goto L_0x00dc
        L_0x00ec:
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.MoreExecutors.invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService, java.util.Collection, boolean, long, java.util.concurrent.TimeUnit):java.lang.Object");
    }

    @GwtIncompatible
    private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService executorService, Callable<T> task, final BlockingQueue<Future<T>> queue) {
        final ListenableFuture<T> future = executorService.submit((Callable) task);
        future.addListener(new Runnable() {
            public void run() {
                queue.add(future);
            }
        }, directExecutor());
        return future;
    }

    @GwtIncompatible
    @Beta
    public static ThreadFactory platformThreadFactory() {
        if (!isAppEngine()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (InvocationTargetException e2) {
            throw Throwables.propagate(e2.getCause());
        }
    }

    @GwtIncompatible
    private static boolean isAppEngine() {
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return false;
        }
        try {
            if (Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]) != null) {
                return true;
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (InvocationTargetException e2) {
            return false;
        } catch (IllegalAccessException e3) {
            return false;
        } catch (NoSuchMethodException e4) {
            return false;
        }
    }

    @GwtIncompatible
    static Thread newThread(String name, Runnable runnable) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(runnable);
        Thread result = platformThreadFactory().newThread(runnable);
        try {
            result.setName(name);
        } catch (SecurityException e) {
        }
        return result;
    }

    @GwtIncompatible
    static Executor renamingDecorator(final Executor executor, final Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(nameSupplier);
        if (isAppEngine()) {
            return executor;
        }
        return new Executor() {
            public void execute(Runnable command) {
                executor.execute(Callables.threadRenaming(command, nameSupplier));
            }
        };
    }

    @GwtIncompatible
    static ExecutorService renamingDecorator(ExecutorService service, final Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(service);
        Preconditions.checkNotNull(nameSupplier);
        if (isAppEngine()) {
            return service;
        }
        return new WrappingExecutorService(service) {
            /* access modifiers changed from: protected */
            public <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming(callable, nameSupplier);
            }

            /* access modifiers changed from: protected */
            public Runnable wrapTask(Runnable command) {
                return Callables.threadRenaming(command, nameSupplier);
            }
        };
    }

    @GwtIncompatible
    static ScheduledExecutorService renamingDecorator(ScheduledExecutorService service, final Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(service);
        Preconditions.checkNotNull(nameSupplier);
        if (isAppEngine()) {
            return service;
        }
        return new WrappingScheduledExecutorService(service) {
            /* access modifiers changed from: protected */
            public <T> Callable<T> wrapTask(Callable<T> callable) {
                return Callables.threadRenaming(callable, nameSupplier);
            }

            /* access modifiers changed from: protected */
            public Runnable wrapTask(Runnable command) {
                return Callables.threadRenaming(command, nameSupplier);
            }
        };
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Beta
    public static boolean shutdownAndAwaitTermination(ExecutorService service, long timeout, TimeUnit unit) {
        long halfTimeoutNanos = unit.toNanos(timeout) / 2;
        service.shutdown();
        try {
            if (!service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS)) {
                service.shutdownNow();
                service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            service.shutdownNow();
        }
        return service.isTerminated();
    }

    static Executor rejectionPropagatingExecutor(final Executor delegate, final AbstractFuture<?> future) {
        Preconditions.checkNotNull(delegate);
        Preconditions.checkNotNull(future);
        if (delegate == directExecutor()) {
            return delegate;
        }
        return new Executor() {
            boolean thrownFromDelegate = true;

            public void execute(final Runnable command) {
                try {
                    delegate.execute(new Runnable() {
                        public void run() {
                            C18015.this.thrownFromDelegate = false;
                            command.run();
                        }
                    });
                } catch (RejectedExecutionException e) {
                    if (this.thrownFromDelegate) {
                        future.setException(e);
                    }
                }
            }
        };
    }

    @GwtIncompatible
    @VisibleForTesting
    static class Application {
        Application() {
        }

        /* access modifiers changed from: package-private */
        public final ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(executor);
            ExecutorService service = Executors.unconfigurableExecutorService(executor);
            addDelayedShutdownHook(executor, terminationTimeout, timeUnit);
            return service;
        }

        /* access modifiers changed from: package-private */
        public final ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
            return getExitingExecutorService(executor, 120, TimeUnit.SECONDS);
        }

        /* access modifiers changed from: package-private */
        public final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
            MoreExecutors.useDaemonThreadFactory(executor);
            ScheduledExecutorService service = Executors.unconfigurableScheduledExecutorService(executor);
            addDelayedShutdownHook(executor, terminationTimeout, timeUnit);
            return service;
        }

        /* access modifiers changed from: package-private */
        public final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
            return getExitingScheduledExecutorService(executor, 120, TimeUnit.SECONDS);
        }

        /* access modifiers changed from: package-private */
        public final void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
            Preconditions.checkNotNull(service);
            Preconditions.checkNotNull(timeUnit);
            String valueOf = String.valueOf(service);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
            sb.append("DelayedShutdownHook-for-");
            sb.append(valueOf);
            final ExecutorService executorService = service;
            final long j = terminationTimeout;
            final TimeUnit timeUnit2 = timeUnit;
            addShutdownHook(MoreExecutors.newThread(sb.toString(), new Runnable(this) {
                public void run() {
                    try {
                        executorService.shutdown();
                        executorService.awaitTermination(j, timeUnit2);
                    } catch (InterruptedException e) {
                    }
                }
            }));
        }

        /* access modifiers changed from: package-private */
        @VisibleForTesting
        public void addShutdownHook(Thread hook) {
            Runtime.getRuntime().addShutdownHook(hook);
        }
    }

    @GwtIncompatible
    private static final class DirectExecutorService extends AbstractListeningExecutorService {
        private final Object lock;
        @GuardedBy("lock")
        private int runningTasks;
        @GuardedBy("lock")
        private boolean shutdown;

        private DirectExecutorService() {
            this.lock = new Object();
            this.runningTasks = 0;
            this.shutdown = false;
        }

        public void execute(Runnable command) {
            startTask();
            try {
                command.run();
            } finally {
                endTask();
            }
        }

        public boolean isShutdown() {
            boolean z;
            synchronized (this.lock) {
                z = this.shutdown;
            }
            return z;
        }

        public void shutdown() {
            synchronized (this.lock) {
                this.shutdown = true;
                if (this.runningTasks == 0) {
                    this.lock.notifyAll();
                }
            }
        }

        public List<Runnable> shutdownNow() {
            shutdown();
            return Collections.emptyList();
        }

        public boolean isTerminated() {
            boolean z;
            synchronized (this.lock) {
                z = this.shutdown && this.runningTasks == 0;
            }
            return z;
        }

        public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            long nanos = unit.toNanos(timeout);
            synchronized (this.lock) {
                while (true) {
                    if (this.shutdown && this.runningTasks == 0) {
                        return true;
                    }
                    if (nanos <= 0) {
                        return false;
                    }
                    long now = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.lock, nanos);
                    nanos -= System.nanoTime() - now;
                }
            }
        }

        private void startTask() {
            synchronized (this.lock) {
                if (!this.shutdown) {
                    this.runningTasks++;
                } else {
                    throw new RejectedExecutionException("Executor already shutdown");
                }
            }
        }

        private void endTask() {
            synchronized (this.lock) {
                int numRunning = this.runningTasks - 1;
                this.runningTasks = numRunning;
                if (numRunning == 0) {
                    this.lock.notifyAll();
                }
            }
        }
    }

    @GwtIncompatible
    private static class ListeningDecorator extends AbstractListeningExecutorService {
        private final ExecutorService delegate;

        ListeningDecorator(ExecutorService delegate2) {
            this.delegate = (ExecutorService) Preconditions.checkNotNull(delegate2);
        }

        public final boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
            return this.delegate.awaitTermination(timeout, unit);
        }

        public final boolean isShutdown() {
            return this.delegate.isShutdown();
        }

        public final boolean isTerminated() {
            return this.delegate.isTerminated();
        }

        public final void shutdown() {
            this.delegate.shutdown();
        }

        public final List<Runnable> shutdownNow() {
            return this.delegate.shutdownNow();
        }

        public final void execute(Runnable command) {
            this.delegate.execute(command);
        }
    }

    @GwtIncompatible
    private static final class ScheduledListeningDecorator extends ListeningDecorator implements ListeningScheduledExecutorService {
        final ScheduledExecutorService delegate;

        ScheduledListeningDecorator(ScheduledExecutorService delegate2) {
            super(delegate2);
            this.delegate = (ScheduledExecutorService) Preconditions.checkNotNull(delegate2);
        }

        public ListenableScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
            TrustedListenableFutureTask<Void> task = TrustedListenableFutureTask.create(command, null);
            return new ListenableScheduledTask(task, this.delegate.schedule(task, delay, unit));
        }

        public <V> ListenableScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
            TrustedListenableFutureTask<V> task = TrustedListenableFutureTask.create(callable);
            return new ListenableScheduledTask(task, this.delegate.schedule(task, delay, unit));
        }

        public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
            NeverSuccessfulListenableFutureTask task = new NeverSuccessfulListenableFutureTask(command);
            return new ListenableScheduledTask(task, this.delegate.scheduleAtFixedRate(task, initialDelay, period, unit));
        }

        public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
            NeverSuccessfulListenableFutureTask task = new NeverSuccessfulListenableFutureTask(command);
            return new ListenableScheduledTask(task, this.delegate.scheduleWithFixedDelay(task, initialDelay, delay, unit));
        }

        private static final class ListenableScheduledTask<V> extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V> implements ListenableScheduledFuture<V> {
            private final ScheduledFuture<?> scheduledDelegate;

            public ListenableScheduledTask(ListenableFuture<V> listenableDelegate, ScheduledFuture<?> scheduledDelegate2) {
                super(listenableDelegate);
                this.scheduledDelegate = scheduledDelegate2;
            }

            public boolean cancel(boolean mayInterruptIfRunning) {
                boolean cancelled = super.cancel(mayInterruptIfRunning);
                if (cancelled) {
                    this.scheduledDelegate.cancel(mayInterruptIfRunning);
                }
                return cancelled;
            }

            public long getDelay(TimeUnit unit) {
                return this.scheduledDelegate.getDelay(unit);
            }

            public int compareTo(Delayed other) {
                return this.scheduledDelegate.compareTo(other);
            }
        }

        @GwtIncompatible
        private static final class NeverSuccessfulListenableFutureTask extends AbstractFuture.TrustedFuture<Void> implements Runnable {
            private final Runnable delegate;

            public NeverSuccessfulListenableFutureTask(Runnable delegate2) {
                this.delegate = (Runnable) Preconditions.checkNotNull(delegate2);
            }

            public void run() {
                try {
                    this.delegate.run();
                } catch (Throwable t) {
                    setException(t);
                    throw Throwables.propagate(t);
                }
            }
        }
    }
}
