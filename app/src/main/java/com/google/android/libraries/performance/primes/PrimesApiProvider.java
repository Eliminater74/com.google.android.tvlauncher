package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import com.google.android.libraries.performance.primes.flags.ServiceFlags;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitterProvider;
import com.google.android.libraries.stitch.util.Preconditions;

import java.util.concurrent.atomic.AtomicInteger;

public final class PrimesApiProvider {
    @VisibleForTesting(otherwise = 5)
    public static final AtomicInteger providerInstanceCounter = new AtomicInteger();

    public static PrimesApiProviderBuilder newBuilder(Application application) {
        PrimesApiProviderBuilder builder = new PrimesApiProviderBuilder(application);
        builder.setFlagsSupplier(new ServiceFlags.DefaultFlagsSupplier());
        builder.setApiProviderFactory(new ApiProviderDefault());
        builder.setFlagsSupplier(new ServiceFlags.GserviceFlagsSupplier(application));
        return builder;
    }

    public static ApiProvider newInstance(Application application, PrimesConfigurationsProvider configurationsProvider) {
        return newInstance(application, configurationsProvider, PrimesThreadsConfigurations.newBuilder().build());
    }

    @Deprecated
    public static ApiProvider newInstance(Application application, final MetricTransmitter transmitter, final PrimesConfigurations configurations) {
        Preconditions.checkNotNull(transmitter);
        Preconditions.checkNotNull(configurations);
        return newInstance(application, new PrimesConfigurationsProvider() {
            public PrimesConfigurations get() {
                return PrimesConfigurations.newBuilder().cloneFrom(PrimesConfigurations.this).setMetricTransmitter(transmitter).build();
            }
        });
    }

    @Deprecated
    public static ApiProvider newInstance(Application application, final MetricTransmitterProvider transmitterProvider, final PrimesConfigurations configurations) {
        Preconditions.checkNotNull(transmitterProvider);
        Preconditions.checkNotNull(configurations);
        return newInstance(application, new PrimesConfigurationsProvider() {
            public PrimesConfigurations get() {
                return PrimesConfigurations.newBuilder().cloneFrom(PrimesConfigurations.this).setMetricTransmitter(transmitterProvider.getTransmitter()).build();
            }
        });
    }

    public static ApiProvider newInstance(Application application, PrimesConfigurationsProvider configurationsProvider, PrimesThreadsConfigurations threadsConfigurations) {
        return newBuilder((Application) Preconditions.checkNotNull(application)).setConfigurationsProvider((PrimesConfigurationsProvider) Preconditions.checkNotNull(configurationsProvider)).setThreadsConfigurations((PrimesThreadsConfigurations) Preconditions.checkNotNull(threadsConfigurations)).build();
    }
}
