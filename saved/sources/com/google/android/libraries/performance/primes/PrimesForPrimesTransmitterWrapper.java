package com.google.android.libraries.performance.primes;

import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.PrimesForPrimesLogger;
import com.google.android.libraries.performance.primes.transmitter.MetricTransmitter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class PrimesForPrimesTransmitterWrapper implements MetricTransmitter, PrimesForPrimesLogger.Queue {
    @VisibleForTesting
    static final int DEFAULT_QUEUE_SIZE = 5;
    private final Supplier<MetricTransmitter> delegateSupplier;
    private final BlockingQueue<Supplier<SystemHealthProto.PrimesForPrimes>> primesForPrimesQueue;

    PrimesForPrimesTransmitterWrapper(Supplier<MetricTransmitter> delegateSupplier2) {
        this(delegateSupplier2, new ArrayBlockingQueue(5));
    }

    private PrimesForPrimesTransmitterWrapper(Supplier<MetricTransmitter> delegateSupplier2, BlockingQueue<Supplier<SystemHealthProto.PrimesForPrimes>> primesForPrimesQueue2) {
        this.delegateSupplier = delegateSupplier2;
        this.primesForPrimesQueue = primesForPrimesQueue2;
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.google.protobuf.GeneratedMessageLite] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void send(logs.proto.wireless.performance.mobile.SystemHealthProto.SystemHealthMetric r5) {
        /*
            r4 = this;
            boolean r0 = r5.hasPrimesForPrimes()
            if (r0 != 0) goto L_0x0028
            java.util.concurrent.BlockingQueue<com.google.android.libraries.performance.primes.Supplier<logs.proto.wireless.performance.mobile.SystemHealthProto$PrimesForPrimes>> r0 = r4.primesForPrimesQueue
            java.lang.Object r0 = r0.poll()
            com.google.android.libraries.performance.primes.Supplier r0 = (com.google.android.libraries.performance.primes.Supplier) r0
            if (r0 == 0) goto L_0x0028
            com.google.protobuf.GeneratedMessageLite$Builder r1 = r5.toBuilder()
            logs.proto.wireless.performance.mobile.SystemHealthProto$SystemHealthMetric$Builder r1 = (logs.proto.wireless.performance.mobile.SystemHealthProto.SystemHealthMetric.Builder) r1
            java.lang.Object r2 = r0.get()
            logs.proto.wireless.performance.mobile.SystemHealthProto$PrimesForPrimes r2 = (logs.proto.wireless.performance.mobile.SystemHealthProto.PrimesForPrimes) r2
            if (r2 == 0) goto L_0x0021
            r1.setPrimesForPrimes(r2)
        L_0x0021:
            com.google.protobuf.GeneratedMessageLite r3 = r1.build()
            r5 = r3
            logs.proto.wireless.performance.mobile.SystemHealthProto$SystemHealthMetric r5 = (logs.proto.wireless.performance.mobile.SystemHealthProto.SystemHealthMetric) r5
        L_0x0028:
            com.google.android.libraries.performance.primes.Supplier<com.google.android.libraries.performance.primes.transmitter.MetricTransmitter> r0 = r4.delegateSupplier
            java.lang.Object r0 = r0.get()
            com.google.android.libraries.performance.primes.transmitter.MetricTransmitter r0 = (com.google.android.libraries.performance.primes.transmitter.MetricTransmitter) r0
            java.lang.Object r0 = com.google.android.libraries.stitch.util.Preconditions.checkNotNull(r0)
            com.google.android.libraries.performance.primes.transmitter.MetricTransmitter r0 = (com.google.android.libraries.performance.primes.transmitter.MetricTransmitter) r0
            r0.send(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.PrimesForPrimesTransmitterWrapper.send(logs.proto.wireless.performance.mobile.SystemHealthProto$SystemHealthMetric):void");
    }

    public void enqueueMessage(Supplier<SystemHealthProto.PrimesForPrimes> primesForPrimesSupplier) {
        if (!this.primesForPrimesQueue.offer(primesForPrimesSupplier)) {
            PrimesLog.m54w("PrimesForPrimes", "Queue overflow", new Object[0]);
        }
    }
}
