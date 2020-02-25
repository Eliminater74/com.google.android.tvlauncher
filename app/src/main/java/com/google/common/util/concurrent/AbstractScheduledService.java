package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public abstract class AbstractScheduledService implements Service {
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
    /* access modifiers changed from: private */
    public final AbstractService delegate = new ServiceDelegate();

    protected AbstractScheduledService() {
    }

    /* access modifiers changed from: protected */
    public abstract void runOneIteration() throws Exception;

    /* access modifiers changed from: protected */
    public abstract Scheduler scheduler();

    /* access modifiers changed from: protected */
    public void startUp() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void shutDown() throws Exception {
    }

    /* access modifiers changed from: protected */
    public ScheduledExecutorService executor() {
        final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                return MoreExecutors.newThread(AbstractScheduledService.this.serviceName(), runnable);
            }
        });
        addListener(new Service.Listener(this) {
            public void terminated(Service.State from) {
                executor.shutdown();
            }

            public void failed(Service.State from, Throwable failure) {
                executor.shutdown();
            }
        }, MoreExecutors.directExecutor());
        return executor;
    }

    /* access modifiers changed from: protected */
    public String serviceName() {
        return getClass().getSimpleName();
    }

    public String toString() {
        String serviceName = serviceName();
        String valueOf = String.valueOf(state());
        StringBuilder sb = new StringBuilder(String.valueOf(serviceName).length() + 3 + String.valueOf(valueOf).length());
        sb.append(serviceName);
        sb.append(" [");
        sb.append(valueOf);
        sb.append("]");
        return sb.toString();
    }

    public final boolean isRunning() {
        return this.delegate.isRunning();
    }

    public final Service.State state() {
        return this.delegate.state();
    }

    public final void addListener(Service.Listener listener, Executor executor) {
        this.delegate.addListener(listener, executor);
    }

    public final Throwable failureCause() {
        return this.delegate.failureCause();
    }

    @CanIgnoreReturnValue
    public final Service startAsync() {
        this.delegate.startAsync();
        return this;
    }

    @CanIgnoreReturnValue
    public final Service stopAsync() {
        this.delegate.stopAsync();
        return this;
    }

    public final void awaitRunning() {
        this.delegate.awaitRunning();
    }

    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitRunning(timeout, unit);
    }

    public final void awaitTerminated() {
        this.delegate.awaitTerminated();
    }

    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        this.delegate.awaitTerminated(timeout, unit);
    }

    public static abstract class Scheduler {
        private Scheduler() {
        }

        public static Scheduler newFixedDelaySchedule(long initialDelay, long delay, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            Preconditions.checkArgument(delay > 0, "delay must be > 0, found %s", delay);
            final long j = initialDelay;
            final long j2 = delay;
            final TimeUnit timeUnit = unit;
            return new Scheduler() {
                public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
                    return executor.scheduleWithFixedDelay(task, j, j2, timeUnit);
                }
            };
        }

        public static Scheduler newFixedRateSchedule(long initialDelay, long period, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            Preconditions.checkArgument(period > 0, "period must be > 0, found %s", period);
            final long j = initialDelay;
            final long j2 = period;
            final TimeUnit timeUnit = unit;
            return new Scheduler() {
                public Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable task) {
                    return executor.scheduleAtFixedRate(task, j, j2, timeUnit);
                }
            };
        }

        /* access modifiers changed from: package-private */
        public abstract Future<?> schedule(AbstractService abstractService, ScheduledExecutorService scheduledExecutorService, Runnable runnable);
    }

    @Beta
    public static abstract class CustomScheduler extends Scheduler {
        public CustomScheduler() {
            super();
        }

        /* access modifiers changed from: protected */
        public abstract Schedule getNextSchedule() throws Exception;

        /* access modifiers changed from: package-private */
        public final Future<?> schedule(AbstractService service, ScheduledExecutorService executor, Runnable runnable) {
            ReschedulableCallable task = new ReschedulableCallable(service, executor, runnable);
            task.reschedule();
            return task;
        }

        @Beta
        protected static final class Schedule {
            /* access modifiers changed from: private */
            public final long delay;
            /* access modifiers changed from: private */
            public final TimeUnit unit;

            public Schedule(long delay2, TimeUnit unit2) {
                this.delay = delay2;
                this.unit = (TimeUnit) Preconditions.checkNotNull(unit2);
            }
        }

        private class ReschedulableCallable extends ForwardingFuture<Void> implements Callable<Void> {
            private final ScheduledExecutorService executor;
            private final ReentrantLock lock = new ReentrantLock();
            private final AbstractService service;
            private final Runnable wrappedRunnable;
            @NullableDecl
            @GuardedBy("lock")
            private Future<Void> currentFuture;

            ReschedulableCallable(AbstractService service2, ScheduledExecutorService executor2, Runnable runnable) {
                this.wrappedRunnable = runnable;
                this.executor = executor2;
                this.service = service2;
            }

            public Void call() throws Exception {
                this.wrappedRunnable.run();
                reschedule();
                return null;
            }

            public void reschedule() {
                try {
                    Schedule schedule = CustomScheduler.this.getNextSchedule();
                    Throwable scheduleFailure = null;
                    this.lock.lock();
                    try {
                        if (this.currentFuture == null || !this.currentFuture.isCancelled()) {
                            this.currentFuture = this.executor.schedule(this, schedule.delay, schedule.unit);
                        }
                    } catch (Throwable e) {
                        scheduleFailure = e;
                    }
                    this.lock.unlock();
                    if (scheduleFailure != null) {
                        this.service.notifyFailed(scheduleFailure);
                    }
                } catch (Throwable t) {
                    this.service.notifyFailed(t);
                }
            }

            public boolean cancel(boolean mayInterruptIfRunning) {
                this.lock.lock();
                try {
                    return this.currentFuture.cancel(mayInterruptIfRunning);
                } finally {
                    this.lock.unlock();
                }
            }

            public boolean isCancelled() {
                this.lock.lock();
                try {
                    return this.currentFuture.isCancelled();
                } finally {
                    this.lock.unlock();
                }
            }

            /* access modifiers changed from: protected */
            public Future<Void> delegate() {
                throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
            }
        }
    }

    private final class ServiceDelegate extends AbstractService {
        /* access modifiers changed from: private */
        public final ReentrantLock lock;
        /* access modifiers changed from: private */
        public final Runnable task;
        /* access modifiers changed from: private */
        @MonotonicNonNullDecl
        public volatile ScheduledExecutorService executorService;
        /* access modifiers changed from: private */
        @MonotonicNonNullDecl
        public volatile Future<?> runningTask;

        private ServiceDelegate() {
            this.lock = new ReentrantLock();
            this.task = new Task();
        }

        /* access modifiers changed from: protected */
        public final void doStart() {
            this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), (Supplier<String>) new Supplier<String>() {
                public String get() {
                    String serviceName = AbstractScheduledService.this.serviceName();
                    String valueOf = String.valueOf(ServiceDelegate.this.state());
                    StringBuilder sb = new StringBuilder(String.valueOf(serviceName).length() + 1 + String.valueOf(valueOf).length());
                    sb.append(serviceName);
                    sb.append(" ");
                    sb.append(valueOf);
                    return sb.toString();
                }
            });
            this.executorService.execute(new Runnable() {
                public void run() {
                    ServiceDelegate.this.lock.lock();
                    try {
                        AbstractScheduledService.this.startUp();
                        Future unused = ServiceDelegate.this.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                        ServiceDelegate.this.notifyStarted();
                    } catch (Throwable th) {
                        ServiceDelegate.this.lock.unlock();
                        throw th;
                    }
                    ServiceDelegate.this.lock.unlock();
                }
            });
        }

        /* access modifiers changed from: protected */
        public final void doStop() {
            this.runningTask.cancel(false);
            this.executorService.execute(new Runnable() {
                public void run() {
                    try {
                        ServiceDelegate.this.lock.lock();
                        if (ServiceDelegate.this.state() != Service.State.STOPPING) {
                            ServiceDelegate.this.lock.unlock();
                            return;
                        }
                        AbstractScheduledService.this.shutDown();
                        ServiceDelegate.this.lock.unlock();
                        ServiceDelegate.this.notifyStopped();
                    } catch (Throwable t) {
                        ServiceDelegate.this.notifyFailed(t);
                    }
                }
            });
        }

        public String toString() {
            return AbstractScheduledService.this.toString();
        }

        class Task implements Runnable {
            Task() {
            }

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
             arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Exception]
             candidates:
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
              ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
            public void run() {
                ServiceDelegate.this.lock.lock();
                try {
                    if (ServiceDelegate.this.runningTask.isCancelled()) {
                        ServiceDelegate.this.lock.unlock();
                        return;
                    }
                    AbstractScheduledService.this.runOneIteration();
                    ServiceDelegate.this.lock.unlock();
                } catch (Throwable t) {
                    ServiceDelegate.this.lock.unlock();
                    throw t;
                }
            }
        }
    }
}
