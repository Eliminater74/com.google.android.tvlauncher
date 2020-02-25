package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public abstract class AbstractExecutionThreadService implements Service {
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
    private final Service delegate = new AbstractService() {
        /* access modifiers changed from: protected */
        public final void doStart() {
            MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier<String>() {
                public String get() {
                    return AbstractExecutionThreadService.this.serviceName();
                }
            }).execute(new Runnable() {
                /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
                 method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
                 arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Exception]
                 candidates:
                  ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
                  ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
                  ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
                  ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
                public void run() {
                    try {
                        AbstractExecutionThreadService.this.startUp();
                        C17521.this.notifyStarted();
                        if (C17521.this.isRunning()) {
                            AbstractExecutionThreadService.this.run();
                        }
                        AbstractExecutionThreadService.this.shutDown();
                        C17521.this.notifyStopped();
                    } catch (Throwable t) {
                        C17521.this.notifyFailed(t);
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public void doStop() {
            AbstractExecutionThreadService.this.triggerShutdown();
        }

        public String toString() {
            return AbstractExecutionThreadService.this.toString();
        }
    };

    protected AbstractExecutionThreadService() {
    }

    /* access modifiers changed from: protected */
    public abstract void run() throws Exception;

    /* access modifiers changed from: protected */
    public void startUp() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void shutDown() throws Exception {
    }

    /* access modifiers changed from: protected */
    public void triggerShutdown() {
    }

    /* access modifiers changed from: protected */
    public Executor executor() {
        return new Executor() {
            public void execute(Runnable command) {
                MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), command).start();
            }
        };
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

    /* access modifiers changed from: protected */
    public String serviceName() {
        return getClass().getSimpleName();
    }
}
