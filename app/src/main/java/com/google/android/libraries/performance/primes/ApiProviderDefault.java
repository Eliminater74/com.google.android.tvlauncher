package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.metriccapture.ProcessStats;
import com.google.android.libraries.stitch.util.ThreadUtil;

import java.util.concurrent.ExecutorService;

final class ApiProviderDefault implements ApiProviderFactory {
    private static final long MAX_INIT_DELAY_MS = 7000;
    private static final String TAG = "PrimesInit";

    ApiProviderDefault() {
    }

    @VisibleForTesting
    static void scheduleOrRunInitTask(PrimesThreadsConfigurations.InitAfterResumedFlag initAfterResumedFlag, PrimesThreadsConfigurations.ActivityResumedCallback activityResumedCallback, AppLifecycleMonitor lifecycleMonitor, ExecutorService initExecutor, Runnable initTask, Supplier<Boolean> foregroundOracle) {
        if (initAfterResumedFlag != null) {
            final AppLifecycleMonitor appLifecycleMonitor = lifecycleMonitor;
            final Runnable runnable = initTask;
            final PrimesThreadsConfigurations.InitAfterResumedFlag initAfterResumedFlag2 = initAfterResumedFlag;
            final PrimesThreadsConfigurations.ActivityResumedCallback activityResumedCallback2 = activityResumedCallback;
            final Supplier<Boolean> supplier = foregroundOracle;
            PrimesExecutors.handleFuture(initExecutor.submit(new Runnable() {
                public void run() {
                    ApiProviderDefault.initialize(AppLifecycleMonitor.this, runnable, initAfterResumedFlag2, activityResumedCallback2, supplier);
                }
            }));
            ThreadUtil.postDelayedOnUiThread(initTask, MAX_INIT_DELAY_MS);
            return;
        }
        PrimesLog.m54w(TAG, "Primes instant initialization", new Object[0]);
        initTask.run();
    }

    @VisibleForTesting
    static void initialize(AppLifecycleMonitor lifecycleMonitor, Runnable initTask, PrimesThreadsConfigurations.InitAfterResumedFlag initAfterResumedFlag, PrimesThreadsConfigurations.ActivityResumedCallback activityResumedCallback, Supplier<Boolean> foregroundOracle) {
        boolean initAfterResumed = initAfterResumedFlag != null && initAfterResumedFlag.isEnabled();
        PrimesLog.m54w(TAG, "initAfterResumed: %b", Boolean.valueOf(initAfterResumed));
        if (!initAfterResumed || !foregroundOracle.get().booleanValue()) {
            PrimesLog.m54w(TAG, "executing Primes-init task", new Object[0]);
            initTask.run();
            return;
        }
        PrimesLog.m54w(TAG, "scheduling Primes-init task", new Object[0]);
        PrimesExecutors.onActivityResumedTrigger(lifecycleMonitor, activityResumedCallback).execute(initTask);
    }

    public ApiProvider create(Application application, PrimesConfigurationsProvider configurationsProvider, Supplier<PrimesFlags> flagsSupplier, Supplier<SharedPreferences> sharedPreferencesSupplier, PrimesThreadsConfigurations threadsConfigurations, Supplier<Shutdown> shutdownSupplier) {
        if (!PrimesApiImpl.isPrimesSupported()) {
            return new ApiProvider(this) {
                public PrimesApi getPrimesApi() {
                    return new NoopPrimesApi();
                }
            };
        }
        final Application application2 = application;
        final PrimesThreadsConfigurations primesThreadsConfigurations = threadsConfigurations;
        final PrimesConfigurationsProvider primesConfigurationsProvider = configurationsProvider;
        final Supplier<PrimesFlags> supplier = flagsSupplier;
        final Supplier<SharedPreferences> supplier2 = sharedPreferencesSupplier;
        final Supplier<Shutdown> supplier3 = shutdownSupplier;
        final AppLifecycleMonitor instance = AppLifecycleMonitor.getInstance(application);
        return new ApiProvider(this) {
            public PrimesApi getPrimesApi() {
                Runnable initTask;
                PrimesApiImpl primesApiImpl = new PrimesApiImpl(application2, PrimesExecutors.newPrimesExecutorSupplier(primesThreadsConfigurations), primesThreadsConfigurations.enableEarlyTimers());
                ExecutorService initExecutor = PrimesExecutors.initExecutor(primesThreadsConfigurations);
                PrimesThreadsConfigurations.InitAfterResumedFlag initAfterResumedFlag = primesThreadsConfigurations.getInitAfterResumed();
                if (initAfterResumedFlag == null) {
                    initTask = primesApiImpl.createInitTriggerTask(initExecutor, primesConfigurationsProvider, supplier, supplier2, supplier3, primesThreadsConfigurations.getPrimesExecutorService() == null);
                } else {
                    initTask = primesApiImpl.createAndRegisterInitTriggerTask(initExecutor, primesConfigurationsProvider, supplier, supplier2, supplier3, primesThreadsConfigurations.getPrimesExecutorService() == null);
                }
                ApiProviderDefault.scheduleOrRunInitTask(initAfterResumedFlag, primesThreadsConfigurations.getActivityResumedCallback(), instance, initExecutor, initTask, new Supplier<Boolean>() {
                    public Boolean get() {
                        return Boolean.valueOf(ProcessStats.isMyProcessInForeground(application2));
                    }
                });
                return primesApiImpl;
            }
        };
    }
}
