package com.google.android.libraries.performance.primes.transmitter;

import com.google.android.libraries.performance.primes.NoPiiString;

public interface StackTraceTransmitter {
    public static final StackTraceTransmitter NOOP_TRANSMITTER = new StackTraceTransmitter() {
        public void send(Throwable throwable, NoPiiString eventName) {
        }
    };

    void send(Throwable th, NoPiiString noPiiString);
}
