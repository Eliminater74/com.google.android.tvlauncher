package com.google.android.libraries.performance.primes;

final /* synthetic */ class ApiProviderCustomInitExecutor$$Lambda$0 implements ApiProvider {
    static final ApiProvider $instance = new ApiProviderCustomInitExecutor$$Lambda$0();

    private ApiProviderCustomInitExecutor$$Lambda$0() {
    }

    public PrimesApi getPrimesApi() {
        return new NoopPrimesApi();
    }
}
