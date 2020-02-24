package com.google.android.libraries.performance.primes;

final /* synthetic */ class NetworkMetricService$$Lambda$0 implements Runnable {
    private final NetworkMetricService arg$1;
    private final NetworkEvent arg$2;

    NetworkMetricService$$Lambda$0(NetworkMetricService networkMetricService, NetworkEvent networkEvent) {
        this.arg$1 = networkMetricService;
        this.arg$2 = networkEvent;
    }

    public void run() {
        this.arg$1.lambda$recordEvent$0$NetworkMetricService(this.arg$2);
    }
}
