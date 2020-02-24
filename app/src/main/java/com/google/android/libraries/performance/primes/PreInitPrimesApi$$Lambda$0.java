package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.PreInitPrimesApi;

final /* synthetic */ class PreInitPrimesApi$$Lambda$0 implements PreInitPrimesApi.ScheduledApiCall {
    static final PreInitPrimesApi.ScheduledApiCall $instance = new PreInitPrimesApi$$Lambda$0();

    private PreInitPrimesApi$$Lambda$0() {
    }

    public void callApi(ConfiguredPrimesApi configuredPrimesApi) {
        configuredPrimesApi.sendPendingNetworkEvents();
    }
}
