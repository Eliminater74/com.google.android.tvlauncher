package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.TimerEvent;
import com.google.android.libraries.performance.primes.sampling.ProbabilitySampler;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.common.base.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

final class TimerMetricServiceWithTracing extends TimerMetricService {
    private final TraceMetricService traceMetricService;

    static TimerMetricServiceWithTracing createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, TraceMetricService traceMetricService2, PrimesTimerConfigurations configs, Optional<ConcurrentHashMap<String, TimerEvent>> timerEvents) {
        return new TimerMetricServiceWithTracing(transmitter, application, metricStamperSupplier, executorServiceSupplier, traceMetricService2, new ProbabilitySampler(configs.getSamplingProbability()), configs.getSampleRatePerSecond(), configs.getPerEventConfigFlags(), timerEvents.mo22987or((PrimesBatteryConfigurations) new ConcurrentHashMap()));
    }

    TimerMetricServiceWithTracing(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, TraceMetricService traceMetricService2, ProbabilitySampler probabilitySampler, int maxSamplesPerSecond, Optional<PrimesPerEventConfigurationFlags> perEventConfigFlags, ConcurrentHashMap<String, TimerEvent> timerEvents) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, probabilitySampler, maxSamplesPerSecond, perEventConfigFlags, timerEvents);
        this.traceMetricService = traceMetricService2;
    }

    /* access modifiers changed from: package-private */
    public synchronized TimerEvent startGlobal(String eventName) {
        TimerEvent timerEvent;
        timerEvent = super.startGlobal(eventName);
        timerStarted(timerEvent, eventName);
        return timerEvent;
    }

    /* access modifiers changed from: package-private */
    public synchronized TimerEvent stopGlobal(String eventName, String newEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        TimerEvent timerEvent;
        timerEvent = super.stopGlobal(eventName, newEventName, isEventNameConstant, metricExtension, timerStatus);
        timerStopped(timerEvent, eventName, newEventName);
        return timerEvent;
    }

    /* access modifiers changed from: package-private */
    public synchronized TimerEvent cancelGlobal(String eventName) {
        TimerEvent timerEvent;
        timerEvent = super.cancelGlobal(eventName);
        if (!TimerEvent.isEmpty(timerEvent) && timerEvent.hasTrace) {
            this.traceMetricService.cancelTracingIfActive();
        }
        return timerEvent;
    }

    /* access modifiers changed from: package-private */
    public synchronized TimerEvent start() {
        TimerEvent timerEvent;
        timerEvent = super.start();
        timerStarted(timerEvent, "");
        return timerEvent;
    }

    /* access modifiers changed from: package-private */
    public synchronized void recordTimer(TimerEvent event, String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
        super.recordTimer(event, customEventName, isEventNameConstant, metricExtension);
        timerStopped(event, customEventName, customEventName);
    }

    private void timerStarted(TimerEvent event, String customEventName) {
        if (!TimerEvent.isEmpty(event) && this.traceMetricService.beginTracingIfNotStarted(customEventName)) {
            event.hasTrace = true;
        }
    }

    private void timerStopped(TimerEvent event, String customEventName, @Nullable String newEventName) {
        if (!TimerEvent.isEmpty(event)) {
            if (event.hasTrace) {
                this.traceMetricService.endTracingIfStarted(customEventName, newEventName);
                return;
            }
            this.traceMetricService.sideLoadSpan(customEventName, event.getStartTimeMs(), event.getDuration());
        }
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        super.shutdownService();
        this.traceMetricService.cancelTracingIfActive();
    }
}
