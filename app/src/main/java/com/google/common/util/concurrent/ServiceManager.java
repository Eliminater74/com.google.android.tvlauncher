package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.GuardedBy;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
@Beta
public final class ServiceManager {
    /* access modifiers changed from: private */
    public static final ListenerCallQueue.Event<Listener> HEALTHY_EVENT = new ListenerCallQueue.Event<Listener>() {
        public void call(Listener listener) {
            listener.healthy();
        }

        public String toString() {
            return "healthy()";
        }
    };
    /* access modifiers changed from: private */
    public static final ListenerCallQueue.Event<Listener> STOPPED_EVENT = new ListenerCallQueue.Event<Listener>() {
        public void call(Listener listener) {
            listener.stopped();
        }

        public String toString() {
            return "stopped()";
        }
    };
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(ServiceManager.class.getName());
    private final ImmutableList<Service> services;
    private final ServiceManagerState state;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, com.google.common.util.concurrent.ServiceManager$EmptyServiceManagerWarning]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    public ServiceManager(Iterable<? extends Service> services2) {
        ImmutableList<Service> copy = ImmutableList.copyOf(services2);
        if (copy.isEmpty()) {
            logger.logp(Level.WARNING, "com.google.common.util.concurrent.ServiceManager", "<init>", "ServiceManager configured with no services.  Is your application configured properly?", (Throwable) new EmptyServiceManagerWarning());
            copy = ImmutableList.m108of(new NoOpService());
        }
        this.state = new ServiceManagerState(copy);
        this.services = copy;
        WeakReference<ServiceManagerState> stateReference = new WeakReference<>(this.state);
        UnmodifiableIterator<Service> it = copy.iterator();
        while (it.hasNext()) {
            Service service = it.next();
            service.addListener(new ServiceListener(service, stateReference), MoreExecutors.directExecutor());
            Preconditions.checkArgument(service.state() == Service.State.NEW, "Can only manage NEW services, %s", service);
        }
        this.state.markReady();
    }

    public void addListener(Listener listener, Executor executor) {
        this.state.addListener(listener, executor);
    }

    public void addListener(Listener listener) {
        this.state.addListener(listener, MoreExecutors.directExecutor());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.IllegalStateException]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    @CanIgnoreReturnValue
    public ServiceManager startAsync() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            Service service = it.next();
            Service.State state2 = service.state();
            Preconditions.checkState(state2 == Service.State.NEW, "Service %s is %s, cannot start it.", service, state2);
        }
        UnmodifiableIterator<Service> it2 = this.services.iterator();
        while (it2.hasNext()) {
            Service service2 = it2.next();
            try {
                this.state.tryStartTiming(service2);
                service2.startAsync();
            } catch (IllegalStateException e) {
                IllegalStateException e2 = e;
                Logger logger2 = logger;
                Level level = Level.WARNING;
                String valueOf = String.valueOf(service2);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 24);
                sb.append("Unable to start Service ");
                sb.append(valueOf);
                logger2.logp(level, "com.google.common.util.concurrent.ServiceManager", "startAsync", sb.toString(), (Throwable) e2);
            }
        }
        return this;
    }

    public void awaitHealthy() {
        this.state.awaitHealthy();
    }

    public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitHealthy(timeout, unit);
    }

    @CanIgnoreReturnValue
    public ServiceManager stopAsync() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            it.next().stopAsync();
        }
        return this;
    }

    public void awaitStopped() {
        this.state.awaitStopped();
    }

    public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
        this.state.awaitStopped(timeout, unit);
    }

    public boolean isHealthy() {
        UnmodifiableIterator<Service> it = this.services.iterator();
        while (it.hasNext()) {
            if (!it.next().isRunning()) {
                return false;
            }
        }
        return true;
    }

    public ImmutableMultimap<Service.State, Service> servicesByState() {
        return this.state.servicesByState();
    }

    public ImmutableMap<Service, Long> startupTimes() {
        return this.state.startupTimes();
    }

    public String toString() {
        return MoreObjects.toStringHelper((Class<?>) ServiceManager.class).add("services", Collections2.filter(this.services, Predicates.not(Predicates.instanceOf(NoOpService.class)))).toString();
    }

    @Beta
    public static abstract class Listener {
        public void healthy() {
        }

        public void stopped() {
        }

        public void failure(Service service) {
        }
    }

    private static final class ServiceManagerState {
        final Monitor.Guard awaitHealthGuard = new AwaitHealthGuard();
        final ListenerCallQueue<Listener> listeners = new ListenerCallQueue<>();
        final Monitor monitor = new Monitor();
        final int numberOfServices;
        @GuardedBy("monitor")
        final SetMultimap<Service.State, Service> servicesByState = MultimapBuilder.enumKeys(Service.State.class).linkedHashSetValues().build();
        @GuardedBy("monitor")
        final Map<Service, Stopwatch> startupTimers = Maps.newIdentityHashMap();
        @GuardedBy("monitor")
        final Multiset<Service.State> states = this.servicesByState.keys();
        final Monitor.Guard stoppedGuard = new StoppedGuard();
        @GuardedBy("monitor")
        boolean ready;
        @GuardedBy("monitor")
        boolean transitioned;

        ServiceManagerState(ImmutableCollection<Service> services) {
            this.numberOfServices = services.size();
            this.servicesByState.putAll(Service.State.NEW, services);
        }

        /* access modifiers changed from: package-private */
        public void tryStartTiming(Service service) {
            this.monitor.enter();
            try {
                if (this.startupTimers.get(service) == null) {
                    this.startupTimers.put(service, Stopwatch.createStarted());
                }
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: package-private */
        public void markReady() {
            this.monitor.enter();
            try {
                if (!this.transitioned) {
                    this.ready = true;
                    return;
                }
                List<Service> servicesInBadStates = Lists.newArrayList();
                UnmodifiableIterator<Service> it = servicesByState().values().iterator();
                while (it.hasNext()) {
                    Service service = it.next();
                    if (service.state() != Service.State.NEW) {
                        servicesInBadStates.add(service);
                    }
                }
                String valueOf = String.valueOf(servicesInBadStates);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 89);
                sb.append("Services started transitioning asynchronously before the ServiceManager was constructed: ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: package-private */
        public void addListener(Listener listener, Executor executor) {
            this.listeners.addListener(listener, executor);
        }

        /* access modifiers changed from: package-private */
        public void awaitHealthy() {
            this.monitor.enterWhenUninterruptibly(this.awaitHealthGuard);
            try {
                checkHealthy();
            } finally {
                this.monitor.leave();
            }
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
         arg types: [com.google.common.collect.SetMultimap<com.google.common.util.concurrent.Service$State, com.google.common.util.concurrent.Service>, com.google.common.base.Predicate]
         candidates:
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.ListMultimap, com.google.common.base.Predicate):com.google.common.collect.ListMultimap<K, V>
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
        /* access modifiers changed from: package-private */
        public void awaitHealthy(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (this.monitor.waitForUninterruptibly(this.awaitHealthGuard, timeout, unit)) {
                    checkHealthy();
                    return;
                }
                String valueOf = String.valueOf(Multimaps.filterKeys((SetMultimap) this.servicesByState, Predicates.m84in(ImmutableSet.m151of(Service.State.NEW, Service.State.STARTING))));
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 93);
                sb.append("Timeout waiting for the services to become healthy. The following services have not started: ");
                sb.append(valueOf);
                throw new TimeoutException(sb.toString());
            } finally {
                this.monitor.leave();
            }
        }

        /* access modifiers changed from: package-private */
        public void awaitStopped() {
            this.monitor.enterWhenUninterruptibly(this.stoppedGuard);
            this.monitor.leave();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
         arg types: [com.google.common.collect.SetMultimap<com.google.common.util.concurrent.Service$State, com.google.common.util.concurrent.Service>, com.google.common.base.Predicate]
         candidates:
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.ListMultimap, com.google.common.base.Predicate):com.google.common.collect.ListMultimap<K, V>
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
        /* access modifiers changed from: package-private */
        public void awaitStopped(long timeout, TimeUnit unit) throws TimeoutException {
            this.monitor.enter();
            try {
                if (!this.monitor.waitForUninterruptibly(this.stoppedGuard, timeout, unit)) {
                    String valueOf = String.valueOf(Multimaps.filterKeys((SetMultimap) this.servicesByState, Predicates.not(Predicates.m84in(EnumSet.of(Service.State.TERMINATED, Service.State.FAILED)))));
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 83);
                    sb.append("Timeout waiting for the services to stop. The following services have not stopped: ");
                    sb.append(valueOf);
                    throw new TimeoutException(sb.toString());
                }
            } finally {
                this.monitor.leave();
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public ImmutableMultimap<Service.State, Service> servicesByState() {
            ImmutableSetMultimap.Builder<Service.State, Service> builder = ImmutableSetMultimap.builder();
            this.monitor.enter();
            try {
                for (Map.Entry<Service.State, Service> entry : this.servicesByState.entries()) {
                    if (!(entry.getValue() instanceof NoOpService)) {
                        builder.put((Map.Entry) entry);
                    }
                }
                this.monitor.leave();
                return builder.build();
            } catch (Throwable th) {
                this.monitor.leave();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: package-private */
        public ImmutableMap<Service, Long> startupTimes() {
            this.monitor.enter();
            try {
                List<Map.Entry<Service, Long>> loadTimes = Lists.newArrayListWithCapacity(this.startupTimers.size());
                for (Map.Entry<Service, Stopwatch> entry : this.startupTimers.entrySet()) {
                    Service service = (Service) entry.getKey();
                    Stopwatch stopWatch = (Stopwatch) entry.getValue();
                    if (!stopWatch.isRunning() && !(service instanceof NoOpService)) {
                        loadTimes.add(Maps.immutableEntry(service, Long.valueOf(stopWatch.elapsed(TimeUnit.MILLISECONDS))));
                    }
                }
                this.monitor.leave();
                Collections.sort(loadTimes, Ordering.natural().onResultOf(new Function<Map.Entry<Service, Long>, Long>(this) {
                    public /* bridge */ /* synthetic */ Object apply(Object obj) {
                        return apply((Map.Entry<Service, Long>) ((Map.Entry) obj));
                    }

                    public Long apply(Map.Entry<Service, Long> input) {
                        return input.getValue();
                    }
                }));
                return ImmutableMap.copyOf(loadTimes);
            } catch (Throwable th) {
                this.monitor.leave();
                throw th;
            }
        }

        /* access modifiers changed from: package-private */
        public void transitionService(Service service, Service.State from, Service.State to) {
            Preconditions.checkNotNull(service);
            Preconditions.checkArgument(from != to);
            this.monitor.enter();
            try {
                this.transitioned = true;
                if (this.ready) {
                    Preconditions.checkState(this.servicesByState.remove(from, service), "Service %s not at the expected location in the state map %s", service, from);
                    Preconditions.checkState(this.servicesByState.put(to, service), "Service %s in the state map unexpectedly at %s", service, to);
                    Stopwatch stopwatch = this.startupTimers.get(service);
                    if (stopwatch == null) {
                        stopwatch = Stopwatch.createStarted();
                        this.startupTimers.put(service, stopwatch);
                    }
                    if (to.compareTo((Enum) Service.State.RUNNING) >= 0 && stopwatch.isRunning()) {
                        stopwatch.stop();
                        if (!(service instanceof NoOpService)) {
                            ServiceManager.logger.logp(Level.FINE, "com.google.common.util.concurrent.ServiceManager$ServiceManagerState", "transitionService", "Started {0} in {1}.", new Object[]{service, stopwatch});
                        }
                    }
                    if (to == Service.State.FAILED) {
                        enqueueFailedEvent(service);
                    }
                    if (this.states.count(Service.State.RUNNING) == this.numberOfServices) {
                        enqueueHealthyEvent();
                    } else if (this.states.count(Service.State.TERMINATED) + this.states.count(Service.State.FAILED) == this.numberOfServices) {
                        enqueueStoppedEvent();
                    }
                    this.monitor.leave();
                    dispatchListenerEvents();
                }
            } finally {
                this.monitor.leave();
                dispatchListenerEvents();
            }
        }

        /* access modifiers changed from: package-private */
        public void enqueueStoppedEvent() {
            this.listeners.enqueue(ServiceManager.STOPPED_EVENT);
        }

        /* access modifiers changed from: package-private */
        public void enqueueHealthyEvent() {
            this.listeners.enqueue(ServiceManager.HEALTHY_EVENT);
        }

        /* access modifiers changed from: package-private */
        public void enqueueFailedEvent(final Service service) {
            this.listeners.enqueue(new ListenerCallQueue.Event<Listener>(this) {
                public void call(Listener listener) {
                    listener.failure(service);
                }

                public String toString() {
                    String valueOf = String.valueOf(service);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                    sb.append("failed({service=");
                    sb.append(valueOf);
                    sb.append("})");
                    return sb.toString();
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void dispatchListenerEvents() {
            Preconditions.checkState(!this.monitor.isOccupiedByCurrentThread(), "It is incorrect to execute listeners with the monitor held.");
            this.listeners.dispatch();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V>
         arg types: [com.google.common.collect.SetMultimap<com.google.common.util.concurrent.Service$State, com.google.common.util.concurrent.Service>, com.google.common.base.Predicate]
         candidates:
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.ListMultimap, com.google.common.base.Predicate):com.google.common.collect.ListMultimap<K, V>
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.Multimap, com.google.common.base.Predicate):com.google.common.collect.Multimap<K, V>
          com.google.common.collect.Multimaps.filterKeys(com.google.common.collect.SetMultimap, com.google.common.base.Predicate):com.google.common.collect.SetMultimap<K, V> */
        /* access modifiers changed from: package-private */
        @GuardedBy("monitor")
        public void checkHealthy() {
            if (this.states.count(Service.State.RUNNING) != this.numberOfServices) {
                String valueOf = String.valueOf(Multimaps.filterKeys((SetMultimap) this.servicesByState, Predicates.not(Predicates.equalTo(Service.State.RUNNING))));
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 79);
                sb.append("Expected to be healthy after starting. The following services are not running: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            }
        }

        final class AwaitHealthGuard extends Monitor.Guard {
            AwaitHealthGuard() {
                super(ServiceManagerState.this.monitor);
            }

            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(Service.State.RUNNING) == ServiceManagerState.this.numberOfServices || ServiceManagerState.this.states.contains(Service.State.STOPPING) || ServiceManagerState.this.states.contains(Service.State.TERMINATED) || ServiceManagerState.this.states.contains(Service.State.FAILED);
            }
        }

        final class StoppedGuard extends Monitor.Guard {
            StoppedGuard() {
                super(ServiceManagerState.this.monitor);
            }

            @GuardedBy("ServiceManagerState.this.monitor")
            public boolean isSatisfied() {
                return ServiceManagerState.this.states.count(Service.State.TERMINATED) + ServiceManagerState.this.states.count(Service.State.FAILED) == ServiceManagerState.this.numberOfServices;
            }
        }
    }

    private static final class ServiceListener extends Service.Listener {
        final Service service;
        final WeakReference<ServiceManagerState> state;

        ServiceListener(Service service2, WeakReference<ServiceManagerState> state2) {
            this.service = service2;
            this.state = state2;
        }

        public void starting() {
            ServiceManagerState state2 = this.state.get();
            if (state2 != null) {
                state2.transitionService(this.service, Service.State.NEW, Service.State.STARTING);
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.logp(Level.FINE, "com.google.common.util.concurrent.ServiceManager$ServiceListener", "starting", "Starting {0}.", this.service);
                }
            }
        }

        public void running() {
            ServiceManagerState state2 = this.state.get();
            if (state2 != null) {
                state2.transitionService(this.service, Service.State.STARTING, Service.State.RUNNING);
            }
        }

        public void stopping(Service.State from) {
            ServiceManagerState state2 = this.state.get();
            if (state2 != null) {
                state2.transitionService(this.service, from, Service.State.STOPPING);
            }
        }

        public void terminated(Service.State from) {
            ServiceManagerState state2 = this.state.get();
            if (state2 != null) {
                if (!(this.service instanceof NoOpService)) {
                    ServiceManager.logger.logp(Level.FINE, "com.google.common.util.concurrent.ServiceManager$ServiceListener", "terminated", "Service {0} has terminated. Previous state was: {1}", new Object[]{this.service, from});
                }
                state2.transitionService(this.service, from, Service.State.TERMINATED);
            }
        }

        public void failed(Service.State from, Throwable failure) {
            ServiceManagerState state2 = this.state.get();
            if (state2 != null) {
                if (!(this.service instanceof NoOpService)) {
                    Logger access$200 = ServiceManager.logger;
                    Level level = Level.SEVERE;
                    String valueOf = String.valueOf(this.service);
                    String valueOf2 = String.valueOf(from);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 34 + String.valueOf(valueOf2).length());
                    sb.append("Service ");
                    sb.append(valueOf);
                    sb.append(" has failed in the ");
                    sb.append(valueOf2);
                    sb.append(" state.");
                    access$200.logp(level, "com.google.common.util.concurrent.ServiceManager$ServiceListener", "failed", sb.toString(), failure);
                }
                state2.transitionService(this.service, from, Service.State.FAILED);
            }
        }
    }

    private static final class NoOpService extends AbstractService {
        private NoOpService() {
        }

        /* access modifiers changed from: protected */
        public void doStart() {
            notifyStarted();
        }

        /* access modifiers changed from: protected */
        public void doStop() {
            notifyStopped();
        }
    }

    private static final class EmptyServiceManagerWarning extends Throwable {
        private EmptyServiceManagerWarning() {
        }
    }
}
