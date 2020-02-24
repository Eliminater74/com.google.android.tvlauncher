package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import com.google.android.libraries.performance.primes.tracing.SpanEvent;
import com.google.android.libraries.performance.primes.tracing.TraceData;
import com.google.common.base.Optional;
import java.util.Arrays;
import java.util.UUID;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

final class PrimesStartupMetricHandler implements AppLifecycleListener.OnAppToBackground, ShutdownListener {
    private static final String TAG = "PrimesStartupHandler";
    private final AppLifecycleMonitor appLifecycleMonitor;
    @VisibleForTesting
    final PrimesStartupTracer startupTracer;
    private final Supplier<TimerMetricService> timerServiceSupplier;
    private final Supplier<Optional<TraceMetricRecordingService>> traceServiceSupplier;

    private static PrimesTraceOuterClass.PrimesTrace createTraceMetric(PrimesTraceOuterClass.Span[] spans) {
        return (PrimesTraceOuterClass.PrimesTrace) PrimesTraceOuterClass.PrimesTrace.newBuilder().setTraceId(UUID.randomUUID().getLeastSignificantBits()).addAllSpans(Arrays.asList(spans)).setTraceType(PrimesTraceOuterClass.PrimesTrace.TraceType.MINI_TRACE).build();
    }

    PrimesStartupMetricHandler(AppLifecycleMonitor appLifecycleMonitor2, Supplier<TimerMetricService> timerServiceSupplier2, Supplier<Optional<TraceMetricRecordingService>> traceServiceSupplier2, Optional<PrimesTraceConfigurations> traceConfigs) {
        this.appLifecycleMonitor = appLifecycleMonitor2;
        this.appLifecycleMonitor.register(this);
        this.timerServiceSupplier = timerServiceSupplier2;
        this.traceServiceSupplier = traceServiceSupplier2;
        if (!traceConfigs.isPresent() || !traceConfigs.get().isEnabled()) {
            this.startupTracer = null;
            return;
        }
        this.startupTracer = new PrimesStartupTracer(traceConfigs.get().getMinSpanDurationMs());
        PrimesStartupMeasure.get().registerOrRunOnActivityInitListener(this.startupTracer);
        PrimesStartupMeasure.get().registerOrRunOnDrawListener(this.startupTracer);
    }

    public void onAppToBackground(Activity activity) {
        this.appLifecycleMonitor.unregister(this);
        PrimesStartupMeasure startupMeasure = PrimesStartupMeasure.get();
        if (startupMeasure.getFirstDrawnAt() > 0) {
            recordStartupTimer(startupMeasure);
            recordStartupTrace(startupMeasure);
            return;
        }
        PrimesLog.m50i(TAG, "missing firstDraw timestamp", new Object[0]);
    }

    private static long getStartTimeMs(PrimesStartupMeasure startupMeasure) {
        if (startupMeasure.isColdStartup()) {
            return startupMeasure.getAppClassLoadedAt();
        }
        return startupMeasure.getFirstOnActivityInitAt();
    }

    private void recordTimer(PrimesStartupMeasure startupMeasure, long startTimeMs, long endTimeMs, String eventName) {
        if (endTimeMs >= startTimeMs) {
            this.timerServiceSupplier.get().recordStartupTimer(new TimerEvent(startTimeMs, endTimeMs), eventName, true, NoPiiString.safeToString(startupMeasure.getStartupType()));
            return;
        }
        PrimesLog.m50i(TAG, "non-positive duration for startup timer %s", eventName);
    }

    private void recordStartupTimer(PrimesStartupMeasure startupMeasure) {
        String eventName;
        String str;
        String str2;
        long startTimeMs = getStartTimeMs(startupMeasure);
        if (this.timerServiceSupplier.get() == null || startTimeMs <= 0) {
            PrimesLog.m50i(TAG, "not recording startup timer (start time: %d)", Long.valueOf(startTimeMs));
            return;
        }
        long endTimeMs = startupMeasure.getFirstDrawnAt();
        boolean isColdStartup = startupMeasure.isColdStartup();
        recordTimer(startupMeasure, startTimeMs, endTimeMs, isColdStartup ? PrimesStartupEvents.COLD_STARTUP_EVENT_NAME : PrimesStartupEvents.WARM_STARTUP_EVENT_NAME);
        long endTimeMs2 = startupMeasure.getFirstAppInteractiveAt();
        if (startupMeasure.getFirstAppInteractiveAt() < startupMeasure.getFirstDrawnAt()) {
            if (isColdStartup) {
                str2 = "Cold startup interactive before onDraw";
            } else {
                str2 = "Warm startup interactive before onDraw";
            }
            eventName = str2;
        } else {
            if (isColdStartup) {
                str = PrimesStartupEvents.COLD_STARTUP_INTERACTIVE_EVENT_NAME;
            } else {
                str = PrimesStartupEvents.WARM_STARTUP_INTERACTIVE_EVENT_NAME;
            }
            eventName = str;
        }
        recordTimer(startupMeasure, startTimeMs, endTimeMs2, eventName);
        long startTimeMs2 = startupMeasure.getFirstOnActivityStartedAt();
        long endTimeMs3 = startupMeasure.getFirstDrawnAt();
        if (!isColdStartup && startTimeMs2 != 0) {
            recordTimer(startupMeasure, startTimeMs2, endTimeMs3, PrimesStartupEvents.WARM_STARTUP_ACTIVITY_ON_START_EVENT_NAME);
        }
    }

    private void recordStartupTrace(PrimesStartupMeasure startupMeasure) {
        if (this.traceServiceSupplier.get().isPresent() && getStartTimeMs(startupMeasure) > 0) {
            if (startupMeasure.getFirstDrawnAt() >= getStartTimeMs(startupMeasure)) {
                SpanEvent miniTrace = PrimesStartupTraceGenerator.prepareMiniTrace(startupMeasure);
                TraceData startupTraceData = null;
                PrimesStartupTracer primesStartupTracer = this.startupTracer;
                if (primesStartupTracer != null) {
                    startupTraceData = primesStartupTracer.getStartupTraceData();
                }
                ((TraceMetricRecordingService) this.traceServiceSupplier.get().get()).record(createTraceMetric(PrimesStartupTraceGenerator.createStartupTraceProto(miniTrace, startupTraceData)), null, NoPiiString.safeToString(startupMeasure.getStartupType()));
            }
        }
    }

    public void onShutdown() {
        this.appLifecycleMonitor.unregister(this);
        PrimesStartupTracer primesStartupTracer = this.startupTracer;
        if (primesStartupTracer != null) {
            primesStartupTracer.shutdown();
        }
    }
}
