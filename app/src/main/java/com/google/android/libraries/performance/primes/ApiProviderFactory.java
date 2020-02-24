package com.google.android.libraries.performance.primes;

import android.app.Application;
import android.content.SharedPreferences;

interface ApiProviderFactory {
    ApiProvider create(Application application, PrimesConfigurationsProvider primesConfigurationsProvider, Supplier<PrimesFlags> supplier, Supplier<SharedPreferences> supplier2, PrimesThreadsConfigurations primesThreadsConfigurations, Supplier<Shutdown> supplier3);
}
