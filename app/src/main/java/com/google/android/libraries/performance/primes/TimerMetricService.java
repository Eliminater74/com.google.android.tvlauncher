package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.sampling.ProbabilitySampler;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.common.base.Optional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

class TimerMetricService extends AbstractMetricService {
    private static final String TAG = "TimerMetricService";
    private static final Set<String> reservedEventNames = new HashSet(Arrays.asList(PrimesStartupEvents.COLD_STARTUP_EVENT_NAME, PrimesStartupEvents.COLD_STARTUP_INTERACTIVE_EVENT_NAME, "Cold startup interactive before onDraw", PrimesStartupEvents.WARM_STARTUP_EVENT_NAME, PrimesStartupEvents.WARM_STARTUP_INTERACTIVE_EVENT_NAME, "Warm startup interactive before onDraw", PrimesStartupEvents.WARM_STARTUP_ACTIVITY_ON_START_EVENT_NAME));
    /* access modifiers changed from: private */
    public final Optional<PrimesPerEventConfigurationFlags> perEventConfigFlags;
    @VisibleForTesting
    final ConcurrentHashMap<String, TimerEvent> timerEvents;
    private final ProbabilitySampler probabilitySampler;

    TimerMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, ProbabilitySampler probabilitySampler2, int maxSamplesPerSecond, Optional<PrimesPerEventConfigurationFlags> perEventConfigFlags2, ConcurrentHashMap<String, TimerEvent> timerEvents2) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.SAME_THREAD, maxSamplesPerSecond);
        this.probabilitySampler = probabilitySampler2;
        this.timerEvents = timerEvents2;
        this.perEventConfigFlags = perEventConfigFlags2;
    }

    private static SystemHealthProto.TimerMetric getTimerMetric(TimerEvent event) {
        return (SystemHealthProto.TimerMetric) SystemHealthProto.TimerMetric.newBuilder().setDurationMs(event.getDuration()).setEndStatus(event.getTimerStatus().toProto()).build();
    }

    private static SystemHealthProto.SystemHealthMetric getMetric(TimerEvent event) {
        return getMetric(event, null);
    }

    private static SystemHealthProto.SystemHealthMetric getMetric(TimerEvent event, @Nullable String accountableComponentName) {
        SystemHealthProto.SystemHealthMetric.Builder metric = SystemHealthProto.SystemHealthMetric.newBuilder().setTimerMetric(getTimerMetric(event));
        if (accountableComponentName != null) {
            metric.setAccountableComponent(SystemHealthProto.AccountableComponent.newBuilder().setCustomName(accountableComponentName));
        }
        return (SystemHealthProto.SystemHealthMetric) metric.build();
    }

    static TimerMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, PrimesTimerConfigurations configs, Optional<ConcurrentHashMap<String, TimerEvent>> timerEvents2) {
        return new TimerMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, new ProbabilitySampler(configs.getSamplingProbability()), configs.getSampleRatePerSecond(), configs.getPerEventConfigFlags(), timerEvents2.mo22987or((PrimesBatteryConfigurations) new ConcurrentHashMap()));
    }

    private boolean isReserved(String eventName) {
        return reservedEventNames.contains(eventName);
    }

    /* access modifiers changed from: package-private */
    public TimerEvent startGlobal(String eventName) {
        if (isReserved(eventName)) {
            PrimesLog.m54w(TAG, "%s is reserved event. Dropping timer.", eventName);
            return TimerEvent.EMPTY_TIMER;
        } else if (!this.probabilitySampler.isSampleAllowed() || !shouldRecord()) {
            PrimesLog.m46d(TAG, "Sampling rate exceeded. Dropping timer: %s", eventName);
            return TimerEvent.EMPTY_TIMER;
        } else {
            TimerEvent timer = new TimerEvent();
            this.timerEvents.put(eventName, timer);
            return timer;
        }
    }

    /* access modifiers changed from: package-private */
    public TimerEvent start() {
        if (!this.probabilitySampler.isSampleAllowed() || !shouldRecord()) {
            return TimerEvent.EMPTY_TIMER;
        }
        return new TimerEvent();
    }

    /* access modifiers changed from: package-private */
    public TimerEvent stopGlobal(String eventName, String newEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        TimerEvent timerEvent = this.timerEvents.remove(eventName);
        if (timerEvent == null) {
            PrimesLog.m46d(TAG, "Can't stop global event that was never started or has been stopped already", new Object[0]);
            return null;
        }
        timerEvent.stop();
        if (shouldRecord()) {
            recordGlobalTimer(timerEvent, eventName, newEventName, isEventNameConstant, metricExtension, timerStatus);
        }
        PrimesLog.m46d(TAG, "Stopped global timer for event name %s.", eventName);
        return timerEvent;
    }

    /* access modifiers changed from: package-private */
    public void recordGlobalTimer(TimerEvent timerEvent, String eventName, @Nullable String newEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable TimerEvent.TimerStatus timerStatus) {
        String timerName = eventName;
        if (newEventName != null && !newEventName.isEmpty()) {
            timerName = newEventName;
        }
        timerEvent.setTimerStatus(timerStatus);
        recordSystemHealthMetricInBackground(timerName, isEventNameConstant, getMetric(timerEvent), metricExtension);
    }

    /* access modifiers changed from: package-private */
    public TimerEvent cancelGlobal(String eventName) {
        TimerEvent event = this.timerEvents.remove(eventName);
        if (event == null) {
            PrimesLog.m46d(TAG, "Can't cancel global event that was never started or has been stopped already", new Object[0]);
            return TimerEvent.EMPTY_TIMER;
        }
        PrimesLog.m46d(TAG, "Cancelled global timer for event name %s", eventName);
        return event;
    }

    /* access modifiers changed from: package-private */
    public void recordStartupTimer(TimerEvent event, String customEventName, boolean isEventNameConstant, @Nullable String accountableComponentName) {
        if (this.probabilitySampler.isSampleAllowed() && shouldRecord()) {
            recordSystemHealthMetricInBackground(customEventName, isEventNameConstant, getMetric(event, accountableComponentName), null);
        }
    }

    /* access modifiers changed from: package-private */
    public void recordTimer(TimerEvent event, String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
        recordTimer(event, customEventName, isEventNameConstant, metricExtension, null);
    }

    /* access modifiers changed from: package-private */
    public void recordTimer(TimerEvent event, String customEventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension, @Nullable String accountableComponentName) {
        if (event == null || event == TimerEvent.EMPTY_TIMER || customEventName == null || customEventName.isEmpty()) {
            PrimesLog.m46d(TAG, "Can't record an event that was never started or has been stopped already", new Object[0]);
        } else if (isReserved(customEventName)) {
            PrimesLog.m54w(TAG, "%s is reserved event. Dropping timer.", customEventName);
        } else if (shouldRecord()) {
            recordSystemHealthMetricInBackground(customEventName, isEventNameConstant, getMetric(event, accountableComponentName), metricExtension);
        }
    }

    /* access modifiers changed from: package-private */
    public void recordEvent(String eventName, boolean isEventNameConstant, long startMs, long endMs, ExtensionMetric.MetricExtension metricExtension) {
        if (this.probabilitySampler.isSampleAllowed() && shouldRecord()) {
            if (startMs > endMs) {
                PrimesLog.m54w(TAG, "Skip timer event: end time %d is before start time %d", Long.valueOf(endMs), Long.valueOf(startMs));
                return;
            }
            recordSystemHealthMetricInBackground(eventName, isEventNameConstant, getMetric(new TimerEvent(startMs, endMs)), metricExtension);
        }
    }

    private void recordSystemHealthMetricInBackground(String eventName, boolean isEventNameConstant, SystemHealthProto.SystemHealthMetric metric, ExtensionMetric.MetricExtension extension) {
        final String str = eventName;
        final boolean z = isEventNameConstant;
        final SystemHealthProto.SystemHealthMetric systemHealthMetric = metric;
        final ExtensionMetric.MetricExtension metricExtension = extension;
        PrimesExecutors.handleFuture(getScheduledExecutorService().submit(new Runnable() {
            public void run() {
                if (!TimerMetricService.this.perEventConfigFlags.isPresent()) {
                    TimerMetricService.this.recordSystemHealthMetric(str, z, systemHealthMetric, metricExtension);
                } else if (((PrimesPerEventConfigurationFlags) TimerMetricService.this.perEventConfigFlags.get()).isFlagEnabled(str)) {
                    TimerMetricService.this.recordSystemHealthMetric(str, z, systemHealthMetric, metricExtension);
                }
            }
        }));
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        this.timerEvents.clear();
    }
}
