package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.hprof.PrimesHprofs;
import com.google.android.libraries.performance.primes.jank.FrameTimeHistogramFactory;
import com.google.android.libraries.performance.primes.metriccapture.ProcessStats;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.stitch.util.ThreadUtil;
import com.google.common.base.Optional;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

final class LazyMetricServices {
    private static final String TAG = "LazyMetricServices";
    /* access modifiers changed from: private */
    public final Application application;
    /* access modifiers changed from: private */
    public final Supplier<ScheduledExecutorService> executorServiceSupplier;
    /* access modifiers changed from: private */
    public final Supplier<MetricStamper> metricStamperSupplier;
    /* access modifiers changed from: private */
    public final PrimesFlags primesFlags;
    private final PrimesConfigurations configs;
    private final Supplier<Optional<ScenarioMetricService>> scenarioMetricServiceSupplier;
    private final SharedPreferences sharedPreferences;
    private final Shutdown shutdown;
    private final Supplier<Optional<TimerMetricService>> timerMetricServiceSupplier;
    private volatile BatteryMetricService batteryMetricServiceInstance;
    private volatile CounterMetricService counterMetricServiceInstance;
    private volatile CpuMetricService cpuMetricServiceInstance;
    private volatile CpuProfilingService cpuProfilingServiceInstance;
    private volatile CrashMetricService crashMetricServiceInstance;
    private volatile FrameMetricService frameMetricServiceInstance;
    private volatile JankMetricService jankMetricServiceInstance;
    private volatile MagicEyeLogService magicEyeLogServiceInstance;
    private volatile MemoryLeakMetricService memoryLeakMetricServiceInstance;
    private volatile MemoryMetricService memoryMetricServiceInstance;
    private volatile MetricTransmitter metricTransmitterInstance;
    private volatile MiniHeapDumpMetricService miniHeapDumpMetricServiceInstance;
    private volatile NetworkMetricService networkMetricServiceInstance;
    private volatile Supplier<Optional<ScenarioSamplingMetricService>> scenarioSamplingMetricServiceSupplier;
    private volatile StrictModeService strictModeServiceInstance;
    private volatile TraceMetricRecordingService traceMetricRecordingServiceInstance;
    private volatile TraceMetricService traceMetricServiceInstance;

    LazyMetricServices(Application application2, Supplier<ScheduledExecutorService> executorServiceSupplier2, Supplier<Optional<ScenarioMetricService>> scenarioMetricServiceSupplier2, PrimesConfigurations configs2, PrimesFlags primesFlags2, SharedPreferences sharedPreferences2, Shutdown shutdown2, Optional<ConcurrentHashMap<String, TimerEvent>> timerEvents) {
        final Application application3 = application2;
        final Supplier<ScheduledExecutorService> supplier = executorServiceSupplier2;
        final PrimesConfigurations primesConfigurations = configs2;
        this.application = application3;
        this.executorServiceSupplier = supplier;
        this.scenarioMetricServiceSupplier = new Supplier.Lazy(scenarioMetricServiceSupplier2);
        this.configs = primesConfigurations;
        this.primesFlags = primesFlags2;
        this.sharedPreferences = sharedPreferences2;
        this.shutdown = shutdown2;
        this.metricStamperSupplier = new Supplier.Lazy(new Supplier<MetricStamper>(this) {
            public MetricStamper get() {
                MetricStamper.Builder builder = MetricStamper.newBuilder().setApplication(application3);
                if (primesConfigurations.globalConfigurations().isPresent()) {
                    builder.setComponentNameSupplier(primesConfigurations.globalConfigurations().get().getComponentNameSupplier());
                }
                return builder.build();
            }
        });
        this.scenarioSamplingMetricServiceSupplier = new Supplier.Lazy(new Supplier<Optional<ScenarioSamplingMetricService>>() {
            public Optional<ScenarioSamplingMetricService> get() {
                if (!primesConfigurations.scenarioConfigurations().isPresent() || !primesConfigurations.scenarioConfigurations().get().isEnabled()) {
                    return Optional.absent();
                }
                LazyMetricServices lazyMetricServices = LazyMetricServices.this;
                return Optional.m80of((ScenarioSamplingMetricService) lazyMetricServices.registerShutdownListener(ScenarioSamplingMetricService.createService(lazyMetricServices.metricTransmitter(), application3, LazyMetricServices.this.metricStamperSupplier, supplier, primesConfigurations.scenarioConfigurations().get())));
            }
        });
        final PrimesConfigurations primesConfigurations2 = configs2;
        final Application application4 = application2;
        final Supplier<ScheduledExecutorService> supplier2 = executorServiceSupplier2;
        final Optional<ConcurrentHashMap<String, TimerEvent>> optional = timerEvents;
        this.timerMetricServiceSupplier = new Supplier.Lazy(new Supplier<Optional<TimerMetricService>>() {
            public Optional<TimerMetricService> get() {
                Object obj;
                if (!primesConfigurations2.timerConfigurations().isPresent() || !primesConfigurations2.timerConfigurations().get().isEnabled()) {
                    return Optional.absent();
                }
                LazyMetricServices lazyMetricServices = LazyMetricServices.this;
                if (lazyMetricServices.primesTraceEnabled()) {
                    obj = TimerMetricServiceWithTracing.createService(LazyMetricServices.this.metricTransmitter(), application4, LazyMetricServices.this.metricStamperSupplier, supplier2, LazyMetricServices.this.traceMetricService(), primesConfigurations2.timerConfigurations().get(), optional);
                } else {
                    obj = TimerMetricService.createService(LazyMetricServices.this.metricTransmitter(), application4, LazyMetricServices.this.metricStamperSupplier, supplier2, primesConfigurations2.timerConfigurations().get(), optional);
                }
                return Optional.m80of((TimerMetricService) lazyMetricServices.registerShutdownListener(obj));
            }
        });
    }

    /* access modifiers changed from: package-private */
    public Supplier<ScheduledExecutorService> getExecutorServiceSupplier() {
        return this.executorServiceSupplier;
    }

    /* access modifiers changed from: package-private */
    public Shutdown getShutdown() {
        return this.shutdown;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public <X extends ShutdownListener> X registerShutdownListener(X shutdownListener) {
        if (!this.shutdown.registerShutdownListener(shutdownListener)) {
            shutdownListener.onShutdown();
        }
        return shutdownListener;
    }

    /* access modifiers changed from: package-private */
    public MetricTransmitter metricTransmitter() {
        MetricTransmitter metricTransmitter;
        if (this.metricTransmitterInstance == null) {
            synchronized (MetricTransmitter.class) {
                if (this.metricTransmitterInstance == null) {
                    if (this.primesFlags.isPrimesForPrimesEnabled()) {
                        PrimesConfigurations primesConfigurations = this.configs;
                        primesConfigurations.getClass();
                        metricTransmitter = new PrimesForPrimesTransmitterWrapper(LazyMetricServices$$Lambda$0.get$Lambda(primesConfigurations));
                    } else {
                        metricTransmitter = this.configs.metricTransmitter();
                    }
                    this.metricTransmitterInstance = metricTransmitter;
                }
            }
        }
        return this.metricTransmitterInstance;
    }

    /* access modifiers changed from: package-private */
    public PrimesForPrimesLogger.Queue primesForPrimesQueue() {
        if (this.primesFlags.isPrimesForPrimesEnabled()) {
            return (PrimesForPrimesTransmitterWrapper) metricTransmitter();
        }
        return PrimesForPrimesLogger.noOpQueue();
    }

    /* access modifiers changed from: package-private */
    public boolean cpuMetricEnabled() {
        return this.configs.cpuConfigurations().isPresent() && this.configs.cpuConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public CpuMetricService cpuMetricService() {
        if (this.cpuMetricServiceInstance == null) {
            synchronized (CpuMetricService.class) {
                if (this.cpuMetricServiceInstance == null) {
                    this.cpuMetricServiceInstance = (CpuMetricService) registerShutdownListener(CpuMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.configs.cpuConfigurations().get()));
                }
            }
        }
        return this.cpuMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean cpuProfilingEnabled() {
        if (Build.VERSION.SDK_INT < 21) {
            PrimesLog.m46d(TAG, "Service unsupported on SDK %d", Integer.valueOf(Build.VERSION.SDK_INT));
            return false;
        } else if (!this.configs.experimentalConfigurations().isPresent() || !this.configs.experimentalConfigurations().get().profilingConfigurations.isPresent() || !this.configs.experimentalConfigurations().get().profilingConfigurations.get().isEnabled()) {
            return false;
        } else {
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public CpuProfilingService cpuProfilingService() {
        if (this.cpuProfilingServiceInstance == null) {
            synchronized (CpuProfilingService.class) {
                if (this.cpuProfilingServiceInstance == null) {
                    this.cpuProfilingServiceInstance = (CpuProfilingService) registerShutdownListener(CpuProfilingService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.configs.experimentalConfigurations().get().profilingConfigurations.get()));
                }
            }
        }
        return this.cpuProfilingServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean magicEyeLogEnabled() {
        return this.primesFlags.isMagicEyeLogEnabled();
    }

    /* access modifiers changed from: package-private */
    public MagicEyeLogService magicEyeLogService() {
        if (this.magicEyeLogServiceInstance == null) {
            synchronized (MagicEyeLogService.class) {
                if (this.magicEyeLogServiceInstance == null) {
                    this.magicEyeLogServiceInstance = (MagicEyeLogService) registerShutdownListener(MagicEyeLogService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier));
                }
            }
        }
        return this.magicEyeLogServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean miniHeapDumpMetricEnabled() {
        return this.configs.miniHeapDumpConfigurations().isPresent() && this.configs.miniHeapDumpConfigurations().get().isEnabled() && MiniHeapDumpMetricService.isFileUploadEnabled(this.application) && PrimesHprofs.isHeapDumpEligible(this.application);
    }

    /* access modifiers changed from: package-private */
    public MiniHeapDumpMetricService miniHeapDumpMetricService() {
        if (this.miniHeapDumpMetricServiceInstance == null) {
            synchronized (MiniHeapDumpMetricService.class) {
                if (this.miniHeapDumpMetricServiceInstance == null) {
                    this.miniHeapDumpMetricServiceInstance = (MiniHeapDumpMetricService) registerShutdownListener(MiniHeapDumpMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.sharedPreferences, 0.95d));
                }
            }
        }
        return this.miniHeapDumpMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean jankMetricEnabled() {
        return this.configs.jankConfigurations().isPresent() && this.configs.jankConfigurations().get().isEnabled() && (Build.VERSION.SDK_INT < 24 || this.configs.jankConfigurations().get().isUseAnimator());
    }

    /* access modifiers changed from: package-private */
    public JankMetricService jankMetricService() {
        if (this.jankMetricServiceInstance == null) {
            synchronized (JankMetricService.class) {
                if (this.jankMetricServiceInstance == null) {
                    this.jankMetricServiceInstance = (JankMetricService) registerShutdownListener(JankMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.configs.jankConfigurations().get()));
                }
            }
        }
        return this.jankMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean frameMetricEnabled() {
        return Build.VERSION.SDK_INT >= 24 && this.configs.jankConfigurations().isPresent() && this.configs.jankConfigurations().get().isEnabled() && !this.configs.jankConfigurations().get().isUseAnimator();
    }

    /* access modifiers changed from: package-private */
    public FrameMetricService frameMetricService() {
        if (this.frameMetricServiceInstance == null) {
            synchronized (FrameMetricService.class) {
                if (this.frameMetricServiceInstance == null) {
                    this.frameMetricServiceInstance = (FrameMetricService) registerShutdownListener(FrameMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, new FrameTimeHistogramFactory(), this.configs.jankConfigurations().get()));
                }
            }
        }
        return this.frameMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean crashMetricEnabled() {
        return this.configs.crashConfigurations().isPresent() && this.configs.crashConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public CrashMetricService crashMetricService() {
        if (this.crashMetricServiceInstance == null) {
            synchronized (CrashMetricService.class) {
                if (this.crashMetricServiceInstance == null) {
                    PrimesCrashConfigurations crashConfigs = this.configs.crashConfigurations().get();
                    this.crashMetricServiceInstance = (CrashMetricService) registerShutdownListener(CrashMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, crashConfigs, crashConfigs.isDeferredInitLogging() && ProcessStats.isMyProcessInForeground(this.application), this.primesFlags.isPersistCrashStatsEnabled()));
                }
            }
        }
        return this.crashMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean tiktokTraceEnabled() {
        return this.configs.tiktokTraceConfigurations().isPresent() && this.configs.tiktokTraceConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public boolean primesTraceEnabled() {
        return this.configs.primesTraceConfigurations().isPresent() && this.configs.primesTraceConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public boolean traceMetricEnabled() {
        return tiktokTraceEnabled() || primesTraceEnabled();
    }

    /* access modifiers changed from: package-private */
    public TraceMetricService traceMetricService() {
        TraceMetricService traceMetricService;
        if (this.traceMetricServiceInstance == null) {
            synchronized (TraceMetricService.class) {
                if (this.traceMetricServiceInstance == null) {
                    if (tiktokTraceEnabled()) {
                        traceMetricService = TraceMetricService.createServiceWithTikTokTracing(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.configs.tiktokTraceConfigurations());
                    } else {
                        traceMetricService = TraceMetricService.createServiceWithPrimesTracing(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.configs.primesTraceConfigurations());
                    }
                    this.traceMetricServiceInstance = (TraceMetricService) registerShutdownListener(traceMetricService);
                }
            }
        }
        return this.traceMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public TraceMetricRecordingService traceMetricRecordingService() {
        if (this.traceMetricRecordingServiceInstance == null) {
            synchronized (TraceMetricService.class) {
                if (this.traceMetricRecordingServiceInstance == null) {
                    this.traceMetricRecordingServiceInstance = (TraceMetricRecordingService) registerShutdownListener(TraceMetricRecordingService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier));
                }
            }
        }
        return this.traceMetricRecordingServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean networkMetricEnabled() {
        return this.configs.networkConfigurations().isPresent() && this.configs.networkConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public NetworkMetricService networkMetricService() {
        boolean z;
        if (this.networkMetricServiceInstance == null) {
            synchronized (NetworkMetricService.class) {
                if (this.networkMetricServiceInstance == null) {
                    MetricTransmitter metricTransmitter = metricTransmitter();
                    Application application2 = this.application;
                    Supplier<MetricStamper> supplier = this.metricStamperSupplier;
                    Supplier<ScheduledExecutorService> supplier2 = this.executorServiceSupplier;
                    Supplier<Optional<ScenarioMetricService>> supplier3 = this.scenarioMetricServiceSupplier;
                    PrimesNetworkConfigurations primesNetworkConfigurations = this.configs.networkConfigurations().get();
                    if (!this.configs.networkConfigurations().get().isUrlAutoSanitizationEnabled()) {
                        if (!this.primesFlags.isUrlAutoSanitizationEnabled()) {
                            z = false;
                            this.networkMetricServiceInstance = (NetworkMetricService) registerShutdownListener(NetworkMetricService.createService(metricTransmitter, application2, supplier, supplier2, supplier3, primesNetworkConfigurations, z));
                        }
                    }
                    z = true;
                    this.networkMetricServiceInstance = (NetworkMetricService) registerShutdownListener(NetworkMetricService.createService(metricTransmitter, application2, supplier, supplier2, supplier3, primesNetworkConfigurations, z));
                }
            }
        }
        return this.networkMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean memoryMetricEnabled() {
        return this.configs.memoryConfigurations().isPresent() && this.configs.memoryConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public MemoryMetricService memoryMetricService() {
        if (this.memoryMetricServiceInstance == null) {
            synchronized (MemoryMetricService.class) {
                if (this.memoryMetricServiceInstance == null) {
                    this.memoryMetricServiceInstance = (MemoryMetricService) registerShutdownListener(MemoryMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.configs.memoryConfigurations().get(), this.primesFlags.isMemorySummaryDisabled()));
                }
            }
        }
        return this.memoryMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean timerMetricEnabled() {
        return this.timerMetricServiceSupplier.get().isPresent();
    }

    /* access modifiers changed from: package-private */
    public TimerMetricService timerMetricService() {
        return (TimerMetricService) this.timerMetricServiceSupplier.get().get();
    }

    /* access modifiers changed from: package-private */
    public boolean memoryLeakMetricEnabled() {
        return (this.configs.memoryLeakConfigurations().isPresent() && this.configs.memoryLeakConfigurations().get().isEnabled()) || this.primesFlags.isLeakDetectionEnabled() || this.primesFlags.isLeakDetectionV2Enabled();
    }

    /* access modifiers changed from: package-private */
    public MemoryLeakMetricService memoryLeakMetricService() {
        if (this.memoryLeakMetricServiceInstance == null) {
            synchronized (MemoryLeakMetricService.class) {
                if (this.memoryLeakMetricServiceInstance == null) {
                    this.memoryLeakMetricServiceInstance = (MemoryLeakMetricService) registerShutdownListener(MemoryLeakMetricService.createService(metricTransmitter(), this.application, this.primesFlags.isLeakDetectionV2Enabled(), this.metricStamperSupplier, this.executorServiceSupplier, this.configs.memoryLeakConfigurations(), AppLifecycleMonitor.getInstance(this.application)));
                }
            }
        }
        return this.memoryLeakMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean batteryMetricEnabled() {
        return Build.VERSION.SDK_INT >= 24 && this.configs.batteryConfigurations().isPresent() && this.configs.batteryConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public BatteryMetricService batteryMetricService() {
        if (this.batteryMetricServiceInstance == null) {
            synchronized (BatteryMetricService.class) {
                if (this.batteryMetricServiceInstance == null) {
                    this.batteryMetricServiceInstance = (BatteryMetricService) registerShutdownListener(BatteryMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.sharedPreferences, this.configs.batteryConfigurations()));
                }
            }
        }
        return this.batteryMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public boolean counterMetricEnabled() {
        return this.configs.experimentalConfigurations().isPresent() && this.configs.experimentalConfigurations().get().counterConfigurations.isPresent() && this.configs.experimentalConfigurations().get().counterConfigurations.get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public CounterMetricService counterMetricService() {
        if (this.counterMetricServiceInstance == null) {
            synchronized (CounterMetricService.class) {
                if (this.counterMetricServiceInstance == null) {
                    this.counterMetricServiceInstance = (CounterMetricService) registerShutdownListener(CounterMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier));
                }
            }
        }
        return this.counterMetricServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public Optional<ScenarioSamplingMetricService> scenarioSamplingMetricServiceOptional() {
        return this.scenarioSamplingMetricServiceSupplier.get();
    }

    /* access modifiers changed from: package-private */
    public boolean startupMetricEnabled() {
        return timerMetricEnabled() && PrimesStartupMeasure.get().getAppClassLoadedAt() > 0;
    }

    /* access modifiers changed from: package-private */
    public PrimesStartupMetricHandler startupMetricHandler() {
        Optional<PrimesTraceConfigurations> optional;
        AppLifecycleMonitor instance = AppLifecycleMonitor.getInstance(this.application);
        C10374 r2 = new Supplier<TimerMetricService>() {
            public TimerMetricService get() {
                return LazyMetricServices.this.timerMetricService();
            }
        };
        C10385 r3 = new Supplier<Optional<TraceMetricRecordingService>>() {
            public Optional<TraceMetricRecordingService> get() {
                if (!LazyMetricServices.this.primesFlags.isStartupTraceEnabled()) {
                    return Optional.absent();
                }
                LazyMetricServices lazyMetricServices = LazyMetricServices.this;
                return Optional.m80of((TraceMetricRecordingService) lazyMetricServices.registerShutdownListener(TraceMetricRecordingService.createService(lazyMetricServices.metricTransmitter(), LazyMetricServices.this.application, LazyMetricServices.this.metricStamperSupplier, LazyMetricServices.this.executorServiceSupplier)));
            }
        };
        if (this.primesFlags.isStartupTraceEnabled()) {
            optional = this.configs.primesTraceConfigurations();
        } else {
            optional = Optional.absent();
        }
        return (PrimesStartupMetricHandler) registerShutdownListener(new PrimesStartupMetricHandler(instance, r2, r3, optional));
    }

    private boolean packageMetricEnabled() {
        return this.configs.packageConfigurations().isPresent() && this.configs.packageConfigurations().get().isEnabled();
    }

    /* access modifiers changed from: package-private */
    public boolean autoPackageMetricEnabled() {
        return packageMetricEnabled() && !this.configs.packageConfigurations().get().isManualCapture();
    }

    /* access modifiers changed from: package-private */
    public boolean manualPackageMetricEnabled() {
        return packageMetricEnabled() && this.configs.packageConfigurations().get().isManualCapture();
    }

    /* access modifiers changed from: package-private */
    public boolean skipPackageMetric() {
        ThreadUtil.ensureBackgroundThread();
        return PackageMetricService.skipPackageMetric(this.sharedPreferences);
    }

    /* access modifiers changed from: package-private */
    public PackageMetricService packageMetricService() {
        return (PackageMetricService) registerShutdownListener(PackageMetricService.createService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier, this.sharedPreferences, this.configs.packageConfigurations().get().getDirStatsConfigurations()));
    }

    /* access modifiers changed from: package-private */
    public boolean strictModeEnabled() {
        if (Build.VERSION.SDK_INT >= 28 && StrictMode.ThreadPolicy.LAX.equals(StrictMode.getThreadPolicy()) && StrictMode.VmPolicy.LAX.equals(StrictMode.getVmPolicy()) && this.configs.experimentalConfigurations().isPresent() && this.configs.experimentalConfigurations().get().strictModeConfigurations.isPresent() && this.configs.experimentalConfigurations().get().strictModeConfigurations.get().isEnabled()) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public StrictModeService strictModeService() {
        if (this.strictModeServiceInstance == null) {
            synchronized (StrictModeService.class) {
                if (this.strictModeServiceInstance == null) {
                    this.strictModeServiceInstance = (StrictModeService) registerShutdownListener(new StrictModeService(metricTransmitter(), this.application, this.metricStamperSupplier, this.executorServiceSupplier));
                }
            }
        }
        return this.strictModeServiceInstance;
    }

    /* access modifiers changed from: package-private */
    public Optional<ScenarioMetricService> scenarioMetricServiceOptional() {
        return this.scenarioMetricServiceSupplier.get();
    }
}
