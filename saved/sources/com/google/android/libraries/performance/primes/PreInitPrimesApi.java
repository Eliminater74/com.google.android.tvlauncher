package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.TimerEvent;
import com.google.android.libraries.performance.primes.scenario.ScenarioEvent;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.common.base.Optional;
import java.lang.Thread;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.PrimesTraceOuterClass;

final class PreInitPrimesApi implements PrimesApi {
    private static final String TAG = "Primes";
    private volatile ConfiguredPrimesApi initializedPrimesApi;
    private final AtomicReference<CountDownLatch> primesInitDoneRef = new AtomicReference<>();
    private final AtomicReference<Runnable> primesInitTaskRef = new AtomicReference<>();
    private final Queue<ScheduledApiCall> scheduledApiCalls = new ConcurrentLinkedQueue();
    private final Optional<ConcurrentHashMap<String, TimerEvent>> timerEvents;

    interface ScheduledApiCall {
        void callApi(ConfiguredPrimesApi configuredPrimesApi);
    }

    PreInitPrimesApi(boolean earlyTimerSupport) {
        Optional<ConcurrentHashMap<String, TimerEvent>> optional;
        if (earlyTimerSupport) {
            optional = Optional.m80of(new ConcurrentHashMap());
        } else {
            optional = Optional.absent();
        }
        this.timerEvents = optional;
    }

    /* access modifiers changed from: package-private */
    public Optional<ConcurrentHashMap<String, TimerEvent>> getTimerEvents() {
        return this.timerEvents;
    }

    private void schedule(ScheduledApiCall call) {
        synchronized (this.scheduledApiCalls) {
            if (this.initializedPrimesApi == null) {
                this.scheduledApiCalls.add(call);
            } else {
                call.callApi(this.initializedPrimesApi);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void executeScheduledCallsOn(ConfiguredPrimesApi primesApi) {
        flushQueue(primesApi);
        synchronized (this.scheduledApiCalls) {
            this.initializedPrimesApi = primesApi;
        }
        flushQueue(primesApi);
    }

    private void flushQueue(ConfiguredPrimesApi primesApi) {
        ScheduledApiCall call = this.scheduledApiCalls.poll();
        while (call != null) {
            call.callApi(primesApi);
            call = this.scheduledApiCalls.poll();
        }
    }

    /* access modifiers changed from: package-private */
    public void setPrimesInitTask(Runnable primesInitTask, CountDownLatch initDoneSignal) {
        this.primesInitTaskRef.set(primesInitTask);
        this.primesInitDoneRef.set(initDoneSignal);
    }

    public void executeAfterInitialized(final Runnable runnable) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                runnable.run();
            }
        });
    }

    public void startMemoryMonitor() {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.startMemoryMonitor();
            }
        });
    }

    public void recordMemory(String customEventName, boolean isEventNameConstant) {
        recordMemory(customEventName, isEventNameConstant, null);
    }

    public void recordMemory(final String customEventName, final boolean isEventNameConstant, @Nullable final ExtensionMetric.MetricExtension metricExtension) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.recordMemory(customEventName, isEventNameConstant, metricExtension);
            }
        });
    }

    public void startMemoryDiffMeasurement(final String customEventName) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.startMemoryDiffMeasurement(customEventName);
            }
        });
    }

    public void stopMemoryDiffMeasurement(final String customEventName, final boolean isEventNameConstant, @Nullable final ExtensionMetric.MetricExtension metricExtension) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.stopMemoryDiffMeasurement(customEventName, isEventNameConstant, metricExtension);
            }
        });
    }

    public void cancelMemoryDiffMeasurement(final String customEventName) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.cancelMemoryDiffMeasurement(customEventName);
            }
        });
    }

    public void recordNetwork(final NetworkEvent networkEvent) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.recordNetwork(networkEvent);
            }
        });
    }

    public void sendPendingNetworkEvents() {
        schedule(PreInitPrimesApi$$Lambda$0.$instance);
    }

    public void startGlobalTimer(String customEventName) {
        if (this.timerEvents.isPresent()) {
            this.timerEvents.get().put(customEventName, TimerEvent.newTimer());
        }
    }

    public void stopGlobalTimer(String customEventName, boolean isEventNameConstant, TimerEvent.TimerStatus endStatus) {
        stopGlobalTimer(customEventName, null, isEventNameConstant, null, endStatus);
    }

    public void stopGlobalTimer(String customEventName, String newEventName, boolean isEventNameConstant, TimerEvent.TimerStatus endStatus) {
        stopGlobalTimer(customEventName, newEventName, isEventNameConstant, null, endStatus);
    }

    public void stopGlobalTimer(String customEventName, String newEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus endStatus) {
        TimerEvent timer;
        if (this.timerEvents.isPresent() && (timer = (TimerEvent) this.timerEvents.get().remove(customEventName)) != null) {
            timer.stop();
            final TimerEvent timerEvent = timer;
            final String str = customEventName;
            final String str2 = newEventName;
            final boolean z = isEventNameConstant;
            final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
            final TimerEvent.TimerStatus timerStatus = endStatus;
            schedule(new ScheduledApiCall(this) {
                public void callApi(ConfiguredPrimesApi primesApi) {
                    primesApi.recordGlobalTimer(timerEvent, str, str2, z, metricExtension2, timerStatus);
                }
            });
        }
    }

    public void cancelGlobalTimer(String eventName) {
    }

    public void recordDuration(WhitelistToken whitelistToken, String customEventName, boolean isEventNameConstant, long startMs, long endMs, ExtensionMetric.MetricExtension metricExtension) {
        final WhitelistToken whitelistToken2 = whitelistToken;
        final String str = customEventName;
        final boolean z = isEventNameConstant;
        final long j = startMs;
        final long j2 = endMs;
        final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.recordDuration(whitelistToken2, str, z, j, j2, metricExtension2);
            }
        });
    }

    public TimerEvent startTimer() {
        return this.timerEvents.isPresent() ? TimerEvent.newTimer() : TimerEvent.EMPTY_TIMER;
    }

    public void stopTimer(@Nullable TimerEvent event, String customEventName, boolean isEventNameConstant, TimerEvent.TimerStatus timerStatus) {
        stopTimer(event, customEventName, isEventNameConstant, null, timerStatus);
    }

    public void stopTimer(@Nullable TimerEvent event, String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension, TimerEvent.TimerStatus timerStatus) {
        if (event != null && event != TimerEvent.EMPTY_TIMER) {
            event.stop();
            final TimerEvent timerEvent = event;
            final String str = customEventName;
            final boolean z = isEventNameConstant;
            final ExtensionMetric.MetricExtension metricExtension2 = metricExtension;
            final TimerEvent.TimerStatus timerStatus2 = timerStatus;
            schedule(new ScheduledApiCall(this) {
                public void callApi(ConfiguredPrimesApi primesApi) {
                    primesApi.stopTimer(timerEvent, str, z, metricExtension2, timerStatus2);
                }
            });
        }
    }

    public void startCrashMonitor() {
        Thread.setDefaultUncaughtExceptionHandler(wrapCrashReportingIntoUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()));
    }

    public Thread.UncaughtExceptionHandler wrapCrashReportingIntoUncaughtExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        EarlyUncaughtExceptionHandler earlyHandler = new EarlyUncaughtExceptionHandler(handler, this.primesInitTaskRef, this.primesInitDoneRef);
        schedule(earlyHandler);
        return earlyHandler;
    }

    public void startJankRecorder(final String eventName) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.startJankRecorder(eventName);
            }
        });
    }

    public void stopJankRecorder(final String eventName, final boolean isEventNameConstant, @Nullable final ExtensionMetric.MetricExtension metricExtension) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.stopJankRecorder(eventName, isEventNameConstant, metricExtension);
            }
        });
    }

    public void cancelJankRecorder(final String eventName) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.cancelJankRecorder(eventName);
            }
        });
    }

    public MetricTransmitter getTransmitter() {
        return NoopPrimesApi.noopTransmitter;
    }

    public boolean isNetworkEnabled() {
        return false;
    }

    public void watchForMemoryLeak(Object object) {
    }

    public void recordScenario(PrimesTraceOuterClass.PrimesTrace primesTrace) {
    }

    public void recordTrace(TikTokWhitelistToken token, PrimesTraceOuterClass.PrimesTrace primesTrace, @Nullable ExtensionMetric.MetricExtension metricExtension) {
    }

    public void recordPackageStats() {
    }

    public void recordBatterySnapshot(final String eventName, final boolean isEventNameConstant) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.recordBatterySnapshot(eventName, isEventNameConstant);
            }
        });
    }

    public void startBatteryDiffMeasurement(String customEventName, boolean isEventNameConstant) {
    }

    public void stopBatteryDiffMeasurement(String customEventName, boolean isEventNameConstant, ExtensionMetric.MetricExtension metricExtension) {
    }

    public void cancelBatteryDiffMeasurement(String customEventName) {
    }

    public void recordBatterySnapshotOnForegroundServiceStart() {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.recordBatterySnapshotOnForegroundServiceStart();
            }
        });
    }

    public void recordBatterySnapshotOnForegroundServiceStop() {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.recordBatterySnapshotOnForegroundServiceStop();
            }
        });
    }

    public void addValueToCounter(String counterName, @Nullable String componentName, boolean areNamesConstant, long value) {
    }

    public Counter startCounter() {
        return Counter.EMPTY_COUNTER;
    }

    public void stopCounter(Counter counter, String counterName, @Nullable String componentName, boolean areNamesConstant) {
    }

    public void addScenarioEvent(final ScenarioEvent scenarioEvent) {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.addScenarioEvent(scenarioEvent);
            }
        });
    }

    public void startScenarioSampling(String scenarioName) {
    }

    public void recordScenarioSampledMetrics(String scenarioName) {
    }

    public void cancelScenarioSampling(String scenarioName) {
    }

    public void shutdown() {
        this.scheduledApiCalls.clear();
    }

    public Supplier<ScheduledExecutorService> getExecutorServiceSupplier() {
        return null;
    }

    public boolean registerShutdownListener(ShutdownListener shutdownListener) {
        return false;
    }

    public void sendCustomLaunchedEvent() {
        schedule(new ScheduledApiCall(this) {
            public void callApi(ConfiguredPrimesApi primesApi) {
                primesApi.sendCustomLaunchedEvent();
            }
        });
    }

    @VisibleForTesting
    static final class EarlyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler, ScheduledApiCall {
        private static final int AWAIT_INITIALIZATION_TIMEOUT_MS = 1000;
        private static final int WAIT_FOR_INITIALIZATION_MS = 100;
        private volatile PrimesApi initializedPrimesApi;
        private final Thread.UncaughtExceptionHandler prevHandler;
        private final AtomicReference<CountDownLatch> primesInitDoneRef;
        private final AtomicReference<Runnable> primesInitTaskRef;

        private EarlyUncaughtExceptionHandler(Thread.UncaughtExceptionHandler prevHandler2, AtomicReference<Runnable> primesInitTaskRef2, AtomicReference<CountDownLatch> primesInitDoneRef2) {
            this.prevHandler = prevHandler2;
            this.primesInitTaskRef = primesInitTaskRef2;
            this.primesInitDoneRef = primesInitDoneRef2;
        }

        public void callApi(ConfiguredPrimesApi primesApi) {
            this.initializedPrimesApi = primesApi;
        }

        public void uncaughtException(Thread thread, Throwable throwable) {
            if (this.initializedPrimesApi == null) {
                Runnable initTask = this.primesInitTaskRef.getAndSet(null);
                CountDownLatch initDoneSignal = this.primesInitDoneRef.getAndSet(null);
                if (initTask == null || initDoneSignal == null) {
                    Thread.sleep(100);
                } else {
                    try {
                        Executors.newSingleThreadExecutor(PreInitPrimesApi$EarlyUncaughtExceptionHandler$$Lambda$0.$instance).execute(initTask);
                        initDoneSignal.await(1000, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        PrimesLog.m54w(PreInitPrimesApi.TAG, "Wait for initialization is interrupted", new Object[0]);
                        Thread.currentThread().interrupt();
                    }
                }
            }
            if (this.initializedPrimesApi != null) {
                this.initializedPrimesApi.wrapCrashReportingIntoUncaughtExceptionHandler(this.prevHandler).uncaughtException(thread, throwable);
                return;
            }
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.prevHandler;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, throwable);
            }
        }

        /* renamed from: lambda$uncaughtException$0$PreInitPrimesApi$EarlyUncaughtExceptionHandler */
        static final /* synthetic */ Thread m44xf391977d(Runnable runnable) {
            return new Thread(runnable, "Primes-preInit");
        }
    }
}
