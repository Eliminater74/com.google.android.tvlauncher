package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ListenerCallQueue;
import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.Service;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.ForOverride;
import com.google.errorprone.annotations.concurrent.GuardedBy;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public abstract class AbstractService implements Service {
    private static final ListenerCallQueue.Event<Service.Listener> RUNNING_EVENT = new ListenerCallQueue.Event<Service.Listener>() {
        public void call(Service.Listener listener) {
            listener.running();
        }

        public String toString() {
            return "running()";
        }
    };
    private static final ListenerCallQueue.Event<Service.Listener> STARTING_EVENT = new ListenerCallQueue.Event<Service.Listener>() {
        public void call(Service.Listener listener) {
            listener.starting();
        }

        public String toString() {
            return "starting()";
        }
    };
    private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_RUNNING_EVENT = stoppingEvent(Service.State.RUNNING);
    private static final ListenerCallQueue.Event<Service.Listener> STOPPING_FROM_STARTING_EVENT = stoppingEvent(Service.State.STARTING);
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_NEW_EVENT = terminatedEvent(Service.State.NEW);
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_RUNNING_EVENT = terminatedEvent(Service.State.RUNNING);
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STARTING_EVENT = terminatedEvent(Service.State.STARTING);
    private static final ListenerCallQueue.Event<Service.Listener> TERMINATED_FROM_STOPPING_EVENT = terminatedEvent(Service.State.STOPPING);
    private final Monitor.Guard hasReachedRunning = new HasReachedRunningGuard();
    private final Monitor.Guard isStartable = new IsStartableGuard();
    private final Monitor.Guard isStoppable = new IsStoppableGuard();
    private final Monitor.Guard isStopped = new IsStoppedGuard();
    private final ListenerCallQueue<Service.Listener> listeners = new ListenerCallQueue<>();
    /* access modifiers changed from: private */
    public final Monitor monitor = new Monitor();
    private volatile StateSnapshot snapshot = new StateSnapshot(Service.State.NEW);

    /* access modifiers changed from: protected */
    @ForOverride
    public abstract void doStart();

    /* access modifiers changed from: protected */
    @ForOverride
    public abstract void doStop();

    private static ListenerCallQueue.Event<Service.Listener> terminatedEvent(final Service.State from) {
        return new ListenerCallQueue.Event<Service.Listener>() {
            public void call(Service.Listener listener) {
                listener.terminated(Service.State.this);
            }

            public String toString() {
                String valueOf = String.valueOf(Service.State.this);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 21);
                sb.append("terminated({from = ");
                sb.append(valueOf);
                sb.append("})");
                return sb.toString();
            }
        };
    }

    private static ListenerCallQueue.Event<Service.Listener> stoppingEvent(final Service.State from) {
        return new ListenerCallQueue.Event<Service.Listener>() {
            public void call(Service.Listener listener) {
                listener.stopping(Service.State.this);
            }

            public String toString() {
                String valueOf = String.valueOf(Service.State.this);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
                sb.append("stopping({from = ");
                sb.append(valueOf);
                sb.append("})");
                return sb.toString();
            }
        };
    }

    private final class IsStartableGuard extends Monitor.Guard {
        IsStartableGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state() == Service.State.NEW;
        }
    }

    private final class IsStoppableGuard extends Monitor.Guard {
        IsStoppableGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) <= 0;
        }
    }

    private final class HasReachedRunningGuard extends Monitor.Guard {
        HasReachedRunningGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().compareTo(Service.State.RUNNING) >= 0;
        }
    }

    private final class IsStoppedGuard extends Monitor.Guard {
        IsStoppedGuard() {
            super(AbstractService.this.monitor);
        }

        public boolean isSatisfied() {
            return AbstractService.this.state().isTerminal();
        }
    }

    protected AbstractService() {
    }

    /* access modifiers changed from: protected */
    @ForOverride
    public void doCancelStart() {
    }

    @CanIgnoreReturnValue
    public final Service startAsync() {
        if (this.monitor.enterIf(this.isStartable)) {
            try {
                this.snapshot = new StateSnapshot(Service.State.STARTING);
                enqueueStartingEvent();
                doStart();
            } catch (Throwable th) {
                this.monitor.leave();
                dispatchListenerEvents();
                throw th;
            }
            this.monitor.leave();
            dispatchListenerEvents();
            return this;
        }
        String valueOf = String.valueOf(this);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 33);
        sb.append("Service ");
        sb.append(valueOf);
        sb.append(" has already been started");
        throw new IllegalStateException(sb.toString());
    }

    @CanIgnoreReturnValue
    public final Service stopAsync() {
        if (this.monitor.enterIf(this.isStoppable)) {
            try {
                Service.State previous = state();
                switch (previous) {
                    case NEW:
                        this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                        enqueueTerminatedEvent(Service.State.NEW);
                        break;
                    case STARTING:
                        this.snapshot = new StateSnapshot(Service.State.STARTING, true, null);
                        enqueueStoppingEvent(Service.State.STARTING);
                        doCancelStart();
                        break;
                    case RUNNING:
                        this.snapshot = new StateSnapshot(Service.State.STOPPING);
                        enqueueStoppingEvent(Service.State.RUNNING);
                        doStop();
                        break;
                    case STOPPING:
                    case TERMINATED:
                    case FAILED:
                        String valueOf = String.valueOf(previous);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 45);
                        sb.append("isStoppable is incorrectly implemented, saw: ");
                        sb.append(valueOf);
                        throw new AssertionError(sb.toString());
                }
            } catch (Throwable th) {
                this.monitor.leave();
                dispatchListenerEvents();
                throw th;
            }
            this.monitor.leave();
            dispatchListenerEvents();
        }
        return this;
    }

    public final void awaitRunning() {
        this.monitor.enterWhenUninterruptibly(this.hasReachedRunning);
        try {
            checkCurrentState(Service.State.RUNNING);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitRunning(long timeout, TimeUnit unit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.hasReachedRunning, timeout, unit)) {
            try {
                checkCurrentState(Service.State.RUNNING);
            } finally {
                this.monitor.leave();
            }
        } else {
            String valueOf = String.valueOf(this);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 50);
            sb.append("Timed out waiting for ");
            sb.append(valueOf);
            sb.append(" to reach the RUNNING state.");
            throw new TimeoutException(sb.toString());
        }
    }

    public final void awaitTerminated() {
        this.monitor.enterWhenUninterruptibly(this.isStopped);
        try {
            checkCurrentState(Service.State.TERMINATED);
        } finally {
            this.monitor.leave();
        }
    }

    public final void awaitTerminated(long timeout, TimeUnit unit) throws TimeoutException {
        if (this.monitor.enterWhenUninterruptibly(this.isStopped, timeout, unit)) {
            try {
                checkCurrentState(Service.State.TERMINATED);
            } finally {
                this.monitor.leave();
            }
        } else {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(state());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 65 + String.valueOf(valueOf2).length());
            sb.append("Timed out waiting for ");
            sb.append(valueOf);
            sb.append(" to reach a terminal state. Current state: ");
            sb.append(valueOf2);
            throw new TimeoutException(sb.toString());
        }
    }

    @GuardedBy("monitor")
    private void checkCurrentState(Service.State expected) {
        Service.State actual = state();
        if (actual == expected) {
            return;
        }
        if (actual == Service.State.FAILED) {
            String valueOf = String.valueOf(this);
            String valueOf2 = String.valueOf(expected);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 56 + String.valueOf(valueOf2).length());
            sb.append("Expected the service ");
            sb.append(valueOf);
            sb.append(" to be ");
            sb.append(valueOf2);
            sb.append(", but the service has FAILED");
            throw new IllegalStateException(sb.toString(), failureCause());
        }
        String valueOf3 = String.valueOf(this);
        String valueOf4 = String.valueOf(expected);
        String valueOf5 = String.valueOf(actual);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 38 + String.valueOf(valueOf4).length() + String.valueOf(valueOf5).length());
        sb2.append("Expected the service ");
        sb2.append(valueOf3);
        sb2.append(" to be ");
        sb2.append(valueOf4);
        sb2.append(", but was ");
        sb2.append(valueOf5);
        throw new IllegalStateException(sb2.toString());
    }

    /* access modifiers changed from: protected */
    public final void notifyStarted() {
        this.monitor.enter();
        try {
            if (this.snapshot.state == Service.State.STARTING) {
                if (this.snapshot.shutdownWhenStartupFinishes) {
                    this.snapshot = new StateSnapshot(Service.State.STOPPING);
                    doStop();
                } else {
                    this.snapshot = new StateSnapshot(Service.State.RUNNING);
                    enqueueRunningEvent();
                }
                return;
            }
            String valueOf = String.valueOf(this.snapshot.state);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 43);
            sb.append("Cannot notifyStarted() when the service is ");
            sb.append(valueOf);
            IllegalStateException failure = new IllegalStateException(sb.toString());
            notifyFailed(failure);
            throw failure;
        } finally {
            this.monitor.leave();
            dispatchListenerEvents();
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyStopped() {
        this.monitor.enter();
        try {
            Service.State previous = state();
            switch (previous) {
                case NEW:
                case TERMINATED:
                case FAILED:
                    String valueOf = String.valueOf(previous);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 43);
                    sb.append("Cannot notifyStopped() when the service is ");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                case STARTING:
                case RUNNING:
                case STOPPING:
                    this.snapshot = new StateSnapshot(Service.State.TERMINATED);
                    enqueueTerminatedEvent(previous);
                    break;
            }
        } finally {
            this.monitor.leave();
            dispatchListenerEvents();
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyFailed(Throwable cause) {
        Preconditions.checkNotNull(cause);
        this.monitor.enter();
        try {
            Service.State previous = state();
            int i = C17736.$SwitchMap$com$google$common$util$concurrent$Service$State[previous.ordinal()];
            if (i != 1) {
                if (i == 2 || i == 3 || i == 4) {
                    this.snapshot = new StateSnapshot(Service.State.FAILED, false, cause);
                    enqueueFailedEvent(previous, cause);
                } else if (i != 5) {
                }
                return;
            }
            String valueOf = String.valueOf(previous);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 22);
            sb.append("Failed while in state:");
            sb.append(valueOf);
            throw new IllegalStateException(sb.toString(), cause);
        } finally {
            this.monitor.leave();
            dispatchListenerEvents();
        }
    }

    public final boolean isRunning() {
        return state() == Service.State.RUNNING;
    }

    public final Service.State state() {
        return this.snapshot.externalState();
    }

    public final Throwable failureCause() {
        return this.snapshot.failureCause();
    }

    public final void addListener(Service.Listener listener, Executor executor) {
        this.listeners.addListener(listener, executor);
    }

    public String toString() {
        String simpleName = getClass().getSimpleName();
        String valueOf = String.valueOf(state());
        StringBuilder sb = new StringBuilder(String.valueOf(simpleName).length() + 3 + String.valueOf(valueOf).length());
        sb.append(simpleName);
        sb.append(" [");
        sb.append(valueOf);
        sb.append("]");
        return sb.toString();
    }

    private void dispatchListenerEvents() {
        if (!this.monitor.isOccupiedByCurrentThread()) {
            this.listeners.dispatch();
        }
    }

    private void enqueueStartingEvent() {
        this.listeners.enqueue(STARTING_EVENT);
    }

    private void enqueueRunningEvent() {
        this.listeners.enqueue(RUNNING_EVENT);
    }

    private void enqueueStoppingEvent(Service.State from) {
        if (from == Service.State.STARTING) {
            this.listeners.enqueue(STOPPING_FROM_STARTING_EVENT);
        } else if (from == Service.State.RUNNING) {
            this.listeners.enqueue(STOPPING_FROM_RUNNING_EVENT);
        } else {
            throw new AssertionError();
        }
    }

    private void enqueueTerminatedEvent(Service.State from) {
        switch (from) {
            case NEW:
                this.listeners.enqueue(TERMINATED_FROM_NEW_EVENT);
                return;
            case STARTING:
                this.listeners.enqueue(TERMINATED_FROM_STARTING_EVENT);
                return;
            case RUNNING:
                this.listeners.enqueue(TERMINATED_FROM_RUNNING_EVENT);
                return;
            case STOPPING:
                this.listeners.enqueue(TERMINATED_FROM_STOPPING_EVENT);
                return;
            case TERMINATED:
            case FAILED:
                throw new AssertionError();
            default:
                return;
        }
    }

    private void enqueueFailedEvent(final Service.State from, final Throwable cause) {
        this.listeners.enqueue(new ListenerCallQueue.Event<Service.Listener>(this) {
            public void call(Service.Listener listener) {
                listener.failed(from, cause);
            }

            public String toString() {
                String valueOf = String.valueOf(from);
                String valueOf2 = String.valueOf(cause);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27 + String.valueOf(valueOf2).length());
                sb.append("failed({from = ");
                sb.append(valueOf);
                sb.append(", cause = ");
                sb.append(valueOf2);
                sb.append("})");
                return sb.toString();
            }
        });
    }

    private static final class StateSnapshot {
        @NullableDecl
        final Throwable failure;
        final boolean shutdownWhenStartupFinishes;
        final Service.State state;

        StateSnapshot(Service.State internalState) {
            this(internalState, false, null);
        }

        StateSnapshot(Service.State internalState, boolean shutdownWhenStartupFinishes2, @NullableDecl Throwable failure2) {
            boolean z = false;
            Preconditions.checkArgument(!shutdownWhenStartupFinishes2 || internalState == Service.State.STARTING, "shutdownWhenStartupFinishes can only be set if state is STARTING. Got %s instead.", internalState);
            Preconditions.checkArgument(!((failure2 != null) ^ (internalState == Service.State.FAILED)) ? true : z, "A failure cause should be set if and only if the state is failed.  Got %s and %s instead.", internalState, failure2);
            this.state = internalState;
            this.shutdownWhenStartupFinishes = shutdownWhenStartupFinishes2;
            this.failure = failure2;
        }

        /* access modifiers changed from: package-private */
        public Service.State externalState() {
            if (!this.shutdownWhenStartupFinishes || this.state != Service.State.STARTING) {
                return this.state;
            }
            return Service.State.STOPPING;
        }

        /* access modifiers changed from: package-private */
        public Throwable failureCause() {
            Preconditions.checkState(this.state == Service.State.FAILED, "failureCause() is only valid if the service has failed, service is %s", this.state);
            return this.failure;
        }
    }
}
