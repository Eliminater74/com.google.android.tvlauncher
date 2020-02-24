package com.google.android.libraries.performance.primes;

final /* synthetic */ class LazyMetricServices$$Lambda$0 implements Supplier {
    private final PrimesConfigurations arg$1;

    private LazyMetricServices$$Lambda$0(PrimesConfigurations primesConfigurations) {
        this.arg$1 = primesConfigurations;
    }

    static Supplier get$Lambda(PrimesConfigurations primesConfigurations) {
        return new LazyMetricServices$$Lambda$0(primesConfigurations);
    }

    public Object get() {
        return this.arg$1.metricTransmitter();
    }
}
