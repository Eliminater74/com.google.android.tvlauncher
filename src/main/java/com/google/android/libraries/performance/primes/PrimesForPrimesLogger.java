package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import com.google.android.libraries.performance.primes.PrimesForPrimesMeasurements;
import java.util.ArrayList;
import java.util.Arrays;
import logs.proto.wireless.performance.mobile.SystemHealthProto;
import logs.proto.wireless.performance.mobile.internal.PrimesForPrimesEventProto;

final class PrimesForPrimesLogger {
    private static final String TAG = "PrimesForPrimes";

    interface Queue {
        void enqueueMessage(Supplier<SystemHealthProto.PrimesForPrimes> supplier);
    }

    private PrimesForPrimesLogger() {
    }

    private static final class NoOpQueue implements Queue {
        private NoOpQueue() {
        }

        public void enqueueMessage(Supplier<SystemHealthProto.PrimesForPrimes> supplier) {
        }
    }

    static final Queue noOpQueue() {
        return new NoOpQueue();
    }

    static final class TimerBuilder {
        private final ArrayList<SystemHealthProto.PrimesForPrimes.InternalTimer> timers;

        private TimerBuilder() {
            this.timers = new ArrayList<>();
        }

        /* access modifiers changed from: package-private */
        public TimerBuilder addTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent primesForPrimesEvent, CpuWallTime start, CpuWallTime end) {
            if (!(start == null || end == null)) {
                CpuWallTime duration = CpuWallTime.minus(end, start);
                this.timers.add((SystemHealthProto.PrimesForPrimes.InternalTimer) SystemHealthProto.PrimesForPrimes.InternalTimer.newBuilder().setDuration(SystemHealthProto.MicroCpuTime.newBuilder().setCpuMicros(duration.cpuMicros()).setWallMicros(duration.wallMicros())).setPrimesForPrimesEvent(primesForPrimesEvent).build());
            }
            return this;
        }

        /* access modifiers changed from: package-private */
        public TimerBuilder addWallTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent primesForPrimesEvent, CpuWallTime start, CpuWallTime end) {
            if (!(start == null || end == null)) {
                CpuWallTime duration = CpuWallTime.minus(end, start);
                this.timers.add((SystemHealthProto.PrimesForPrimes.InternalTimer) SystemHealthProto.PrimesForPrimes.InternalTimer.newBuilder().setDuration(SystemHealthProto.MicroCpuTime.newBuilder().setWallMicros(duration.wallMicros())).setPrimesForPrimesEvent(primesForPrimesEvent).build());
            }
            return this;
        }

        /* access modifiers changed from: package-private */
        public SystemHealthProto.PrimesForPrimes.InternalTimer[] build() {
            return (SystemHealthProto.PrimesForPrimes.InternalTimer[]) this.timers.toArray(new SystemHealthProto.PrimesForPrimes.InternalTimer[0]);
        }
    }

    static Supplier<SystemHealthProto.PrimesForPrimes> primesInitMetricsSupplier() {
        return new Supplier<SystemHealthProto.PrimesForPrimes>() {
            public SystemHealthProto.PrimesForPrimes get() {
                return PrimesForPrimesLogger.primesInitMetrics();
            }
        };
    }

    /* access modifiers changed from: private */
    @Nullable
    public static SystemHealthProto.PrimesForPrimes primesInitMetrics() {
        try {
            PrimesForPrimesMeasurements.PrimesInitializationMeasurement init = PrimesForPrimesMeasurements.initializationMeasurement();
            PrimesForPrimesMeasurements.PrimesApiMeasurement api = PrimesForPrimesMeasurements.apiMeasurement();
            return messageFor(new TimerBuilder().addTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent.PRIMES_INITIALIZE, api.getPrimesInitializeStartTime(), api.getPrimesInitializeEndTime()).addTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent.INIT_ALL, init.getInitStartTime(), init.getInitEndTime()).addTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent.INIT_SHUTDOWN, init.getInitStartTime(), init.getShutdownInitializedTime()).addTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent.INIT_CONFIGS, init.getShutdownInitializedTime(), init.getConfigsCreatedTime()).addTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent.INIT_FLAGS, init.getConfigsCreatedTime(), init.getFlagsCreatedTime()).addWallTimer(PrimesForPrimesEventProto.PrimesForPrimesEvent.INIT_DELAY, api.getPrimesInitializeStartTime(), init.getInitStartTime()).build());
        } catch (RuntimeException ex) {
            PrimesLog.m53w(TAG, "Exception getting Primes Init timers", ex, new Object[0]);
            return null;
        }
    }

    @Nullable
    static SystemHealthProto.PrimesForPrimes messageFor(SystemHealthProto.PrimesForPrimes.InternalTimer[] timers) {
        if (timers == null || timers.length == 0) {
            return null;
        }
        return (SystemHealthProto.PrimesForPrimes) SystemHealthProto.PrimesForPrimes.newBuilder().addAllInternalTimers(Arrays.asList(timers)).build();
    }
}
