package com.google.android.libraries.performance.primes.transmitter;

import logs.proto.wireless.performance.mobile.SystemHealthProto;

public interface MetricTransmitter {
    public static final MetricTransmitter NOOP_TRANSMITTER = new MetricTransmitter() {
        public void send(SystemHealthProto.SystemHealthMetric message) {
        }
    };

    void send(SystemHealthProto.SystemHealthMetric systemHealthMetric);
}
