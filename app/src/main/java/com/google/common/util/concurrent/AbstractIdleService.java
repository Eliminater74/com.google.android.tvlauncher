package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Beta
public abstract class AbstractIdleService implements Service {
    private final Service delegate = new DelegateService();
    /* access modifiers changed from: private */
    public final Supplier<String> threadNameSupplier = new ThreadNameSupplier();

    /* access modifiers changed from: protected */
    public abstract void shutDown() throws Exception;

    /* access modifiers changed from: protected */
    public abstract void startUp() throws Exception;

    private final class ThreadNameSupplier implements Supplier<String> {
        private ThreadNameSupplier() {
        }

        public String get() {
            String serviceName = AbstractIdleService.this.serviceName();
            String valueOf = String.valueOf(AbstractIdleService.this.state());
            StringBuilder sb = new StringBuilder(String.valueOf(serviceName).length() + 1 + String.valueOf(valueOf).length());
            sb.append(serviceName);
            sb.append(" ");
            sb.append(valueOf);
            return sb.toString();
        }
    }

    private final class DelegateService extends AbstractService {
        private DelegateService() {
        }

        /* access modifiers changed from: protected */
        public final void doStart() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
                public void run() {
                    try {
                        AbstractIdleService.this.startUp();
                        DelegateService.this.notifyStarted();
                    } catch (Throwable t) {
                        DelegateService.this.notifyFailed(t);
                    }
                }
            });
        }

        /* access modifiers changed from: protected */
        public final void doStop() {
            MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
                public void run() {
                    try {
                        AbstractIdleService.this.shutDown();
                        DelegateService.this.notifyStopped();
                    } catch (Throwable t) {
                        DelegateService.this.notifyFailed(t);
                    }
                }
            });
        }

        public String toString() {
            return AbstractIdleService.this.toString();
        }
    }

    protected AbstractIdleService() {
    }

    /* access modifiers changed from: protected */
    public Executor executor() {
        return new Executor() {
            public void execute(Runnable command) {
                MoreExecutors.newThread((String) AbstractIdleService.this.threadNameSupplier.get(), command).start();
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
