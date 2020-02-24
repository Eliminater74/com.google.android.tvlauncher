package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.content.SharedPreferences;
import java.util.concurrent.Executor;

final class ApiProviderCustomInitExecutor implements ApiProviderFactory {
    private static final String TAG = "PrimesInitCustom";
    /* access modifiers changed from: private */
    public final Executor initExecutor;

    ApiProviderCustomInitExecutor(Executor initExecutor2) {
        this.initExecutor = initExecutor2;
    }

    public ApiProvider create(Application application, PrimesConfigurationsProvider configurationsProvider, Supplier<PrimesFlags> flagsSupplier, Supplier<SharedPreferences> sharedPreferencesSupplier, PrimesThreadsConfigurations threadsConfigurations, Supplier<Shutdown> shutdownSupplier) {
        if (!PrimesApiImpl.isPrimesSupported()) {
            return ApiProviderCustomInitExecutor$$Lambda$0.$instance;
        }
        AppLifecycleMonitor.getInstance(application);
        final Application application2 = application;
        final PrimesThreadsConfigurations primesThreadsConfigurations = threadsConfigurations;
        final PrimesConfigurationsProvider primesConfigurationsProvider = configurationsProvider;
        final Supplier<PrimesFlags> supplier = flagsSupplier;
        final Supplier<SharedPreferences> supplier2 = sharedPreferencesSupplier;
        final Supplier<Shutdown> supplier3 = shutdownSupplier;
        return new ApiProvider() {
            public PrimesApi getPrimesApi() {
                PrimesApiImpl primesApiImpl = new PrimesApiImpl(application2, PrimesExecutors.newPrimesExecutorSupplier(primesThreadsConfigurations), primesThreadsConfigurations.enableEarlyTimers());
                Runnable initTask = PrimesApiImpl.createInitTask(primesApiImpl, primesConfigurationsProvider, supplier, supplier2, supplier3);
                primesApiImpl.registerInitTask(initTask);
                ApiProviderCustomInitExecutor.this.initExecutor.execute(initTask);
                return primesApiImpl;
            }
        };
    }
}
