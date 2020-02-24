package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.flags.PrimesShutdown;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.concurrent.Executor;

public final class PrimesApiProviderBuilder {
    private static final String DEFAULT_SHARED_PREFS = "primes";
    @VisibleForTesting
    volatile ApiProviderFactory apiProviderFactory;
    /* access modifiers changed from: private */
    public final Application application;
    private volatile PrimesConfigurationsProvider configurationsProvider;
    @VisibleForTesting
    volatile Supplier<PrimesFlags> flagsSupplier;
    @Nullable
    private volatile Supplier<SharedPreferences> sharedPrefsSupplier;
    @Nullable
    private volatile Supplier<Shutdown> shutdownSupplier;
    @Nullable
    private volatile PrimesThreadsConfigurations threadsConfigurations;

    /* access modifiers changed from: private */
    public static SharedPreferences defaultSharedPreferences(Context context) {
        return context.getSharedPreferences(DEFAULT_SHARED_PREFS, 0);
    }

    PrimesApiProviderBuilder(Application application2) {
        this.application = application2;
    }

    /* access modifiers changed from: package-private */
    public PrimesApiProviderBuilder setApiProviderFactory(ApiProviderFactory apiProviderFactory2) {
        this.apiProviderFactory = apiProviderFactory2;
        return this;
    }

    public PrimesApiProviderBuilder setFlagsSupplier(Supplier<PrimesFlags> flagsSupplier2) {
        this.flagsSupplier = flagsSupplier2;
        return this;
    }

    public PrimesApiProviderBuilder setSharedPrefsSupplier(Supplier<SharedPreferences> sharedPrefsSupplier2) {
        this.sharedPrefsSupplier = sharedPrefsSupplier2;
        return this;
    }

    public PrimesApiProviderBuilder setShutdownSupplier(Supplier<Shutdown> shutdownSupplier2) {
        this.shutdownSupplier = shutdownSupplier2;
        return this;
    }

    public PrimesApiProviderBuilder setConfigurationsProvider(PrimesConfigurationsProvider configurationsProvider2) {
        this.configurationsProvider = configurationsProvider2;
        return this;
    }

    public PrimesApiProviderBuilder setThreadsConfigurations(PrimesThreadsConfigurations threadsConfigurations2) {
        this.threadsConfigurations = threadsConfigurations2;
        return this;
    }

    public PrimesApiProviderBuilder setInitExecutor(Executor initExecutor) {
        return setApiProviderFactory(new ApiProviderCustomInitExecutor(initExecutor));
    }

    public ApiProvider build() {
        C10861 r5;
        PrimesThreadsConfigurations primesThreadsConfigurations;
        C10872 r7;
        ApiProviderFactory apiProviderFactory2 = (ApiProviderFactory) Preconditions.checkNotNull(this.apiProviderFactory);
        Application application2 = (Application) Preconditions.checkNotNull(this.application);
        PrimesConfigurationsProvider primesConfigurationsProvider = (PrimesConfigurationsProvider) Preconditions.checkNotNull(this.configurationsProvider);
        Supplier supplier = (Supplier) Preconditions.checkNotNull(this.flagsSupplier);
        if (this.sharedPrefsSupplier == null) {
            r5 = new Supplier<SharedPreferences>() {
                public SharedPreferences get() {
                    return PrimesApiProviderBuilder.defaultSharedPreferences(PrimesApiProviderBuilder.this.application);
                }
            };
        } else {
            r5 = this.sharedPrefsSupplier;
        }
        if (this.threadsConfigurations == null) {
            primesThreadsConfigurations = PrimesThreadsConfigurations.newBuilder().build();
        } else {
            primesThreadsConfigurations = this.threadsConfigurations;
        }
        if (this.shutdownSupplier == null) {
            r7 = new Supplier<Shutdown>(this) {
                public Shutdown get() {
                    return PrimesShutdown.createInstance();
                }
            };
        } else {
            r7 = this.shutdownSupplier;
        }
        return apiProviderFactory2.create(application2, primesConfigurationsProvider, supplier, r5, primesThreadsConfigurations, r7);
    }
}
