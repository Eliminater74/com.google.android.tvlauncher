package com.google.android.libraries.performance.primes;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.AppLifecycleListener;
import com.google.android.libraries.performance.primes.MetricRecorder;
import com.google.android.libraries.performance.primes.metriccapture.DisplayStats;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

@Deprecated
final class JankMetricService extends AbstractMetricService {
    private static final String TAG = "JankMetricService";
    private final AppLifecycleMonitor appLifecycleMonitor;
    private final Map<String, JankEvent> jankEvents = new HashMap();
    private final int maxAcceptedFrameRenderTimeMs;
    /* access modifiers changed from: private */
    public final JankMetricExtensionProvider metricExtensionProvider;
    private final AppLifecycleListener.OnActivityPaused onActivityPaused = new AppLifecycleListener.OnActivityPaused() {
        public void onActivityPaused(Activity activity) {
            JankMetricService.this.cleanUpPendingEvents();
        }
    };

    @VisibleForTesting
    static SystemHealthProto.SystemHealthMetric getMetric(JankEvent event) {
        return (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().setJankMetric((SystemHealthProto.JankMetric) SystemHealthProto.JankMetric.newBuilder().setJankyFrameCount(event.getJankyFrameCount()).setRenderedFrameCount(event.getRenderedFrameCount()).setMaxFrameRenderTimeMs(event.getMaxRenderTimeMs()).setDurationMs(event.getElapsedTimeMs()).build()).build();
    }

    static JankMetricService createService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, PrimesJankConfigurations configs) {
        return new JankMetricService(transmitter, application, metricStamperSupplier, executorServiceSupplier, AppLifecycleMonitor.getInstance(application), configs.getMetricExtensionProvider(), configs.getSampleRatePerSecond());
    }

    @VisibleForTesting
    JankMetricService(MetricTransmitter transmitter, Application application, Supplier<MetricStamper> metricStamperSupplier, Supplier<ScheduledExecutorService> executorServiceSupplier, AppLifecycleMonitor appLifecycleMonitor2, JankMetricExtensionProvider metricExtensionProvider2, int sampleRatePerSecond) {
        super(transmitter, application, metricStamperSupplier, executorServiceSupplier, MetricRecorder.RunIn.BACKGROUND_THREAD, sampleRatePerSecond);
        this.appLifecycleMonitor = (AppLifecycleMonitor) Preconditions.checkNotNull(appLifecycleMonitor2);
        this.metricExtensionProvider = metricExtensionProvider2;
        appLifecycleMonitor2.register(this.onActivityPaused);
        this.maxAcceptedFrameRenderTimeMs = DisplayStats.maxAcceptedFrameRenderTimeMs(application);
    }

    /* access modifiers changed from: package-private */
    public JankEvent start(String eventName) {
        JankEvent oldEvent;
        if (!shouldRecord()) {
            return null;
        }
        if (eventName == null) {
            PrimesLog.m54w(TAG, "Can't start an event with null name.", new Object[0]);
            return null;
        }
        JankEvent event = new JankEvent(this.maxAcceptedFrameRenderTimeMs);
        synchronized (this) {
            oldEvent = this.jankEvents.put(eventName, event);
        }
        if (oldEvent != null) {
            oldEvent.stop();
        }
        return event;
    }

    /* access modifiers changed from: package-private */
    public void stop(String eventName, boolean isEventNameConstant, @Nullable ExtensionMetric.MetricExtension metricExtension) {
        JankEvent event;
        if (shouldRecord()) {
            if (eventName == null) {
                PrimesLog.m54w(TAG, "Can't stop an event with null name.", new Object[0]);
                return;
            }
            synchronized (this) {
                event = this.jankEvents.remove(eventName);
            }
            if (event == null) {
                PrimesLog.m54w(TAG, "Can't stop an event that was never started or has been stopped already.", new Object[0]);
                return;
            }
            event.stop();
            if (event.getRenderedFrameCount() > 0) {
                final JankEvent jankEvent = event;
                final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
                final String str = eventName;
                final boolean z = isEventNameConstant;
                getScheduledExecutorService().submit(new Runnable() {
                    public void run() {
                        SystemHealthProto.SystemHealthMetric.Builder metric = (SystemHealthProto.SystemHealthMetric.Builder) JankMetricService.getMetric(jankEvent).toBuilder();
                        ExtensionMetric.MetricExtension metricExtension = metricExtension2;
                        if (metricExtension == null) {
                            try {
                                ExtensionMetric.MetricExtension metricExtension1 = JankMetricService.this.metricExtensionProvider.getMetricExtension();
                                if (metricExtension1 != null) {
                                    metric.setMetricExtension(metricExtension1);
                                } else {
                                    metric.clearMetricExtension();
                                }
                            } catch (Exception e) {
                                PrimesLog.m53w(JankMetricService.TAG, "Exception while getting jank metric extension!", e, new Object[0]);
                            }
                        } else {
                            metric.setMetricExtension(metricExtension);
                        }
                        JankMetricService.this.recordSystemHealthMetric(str, z, (SystemHealthProto.SystemHealthMetric) metric.build());
                    }
                });
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void cancel(String eventName) {
        if (eventName == null) {
            PrimesLog.m54w(TAG, "Can't cancel an event with null name.", new Object[0]);
            return;
        }
        synchronized (this) {
            this.jankEvents.remove(eventName);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void cleanUpPendingEvents() {
        if (!this.jankEvents.isEmpty()) {
            for (JankEvent jankEvent : this.jankEvents.values()) {
                jankEvent.stop();
            }
            this.jankEvents.clear();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Map<String, JankEvent> getJankEvents() {
        return this.jankEvents;
    }

    /* access modifiers changed from: package-private */
    public void shutdownService() {
        this.appLifecycleMonitor.unregister(this.onActivityPaused);
        cleanUpPendingEvents();
    }
}
