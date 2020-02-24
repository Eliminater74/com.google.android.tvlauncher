package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

final class PrimesForPrimesMeasurements {
    private PrimesForPrimesMeasurements() {
    }

    static final class PrimesInitializationMeasurement {
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesConfigsCreated;
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesFlagsCreated;
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesInitEnd;
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesInitStart;
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesShutdownInitialized;

        PrimesInitializationMeasurement() {
        }

        /* access modifiers changed from: package-private */
        public void onInitStart() {
            this.primesInitStart = CpuWallTime.now();
        }

        /* access modifiers changed from: package-private */
        public void onShutdownInitialized() {
            this.primesShutdownInitialized = CpuWallTime.now();
        }

        /* access modifiers changed from: package-private */
        public void onConfigsCreated() {
            this.primesConfigsCreated = CpuWallTime.now();
        }

        /* access modifiers changed from: package-private */
        public void onFlagsCreated() {
            this.primesFlagsCreated = CpuWallTime.now();
        }

        /* access modifiers changed from: package-private */
        public void onInitEnd() {
            this.primesInitEnd = CpuWallTime.now();
        }

        @Nullable
        public CpuWallTime getInitStartTime() {
            return this.primesInitStart;
        }

        @Nullable
        public CpuWallTime getShutdownInitializedTime() {
            return this.primesShutdownInitialized;
        }

        @Nullable
        public CpuWallTime getConfigsCreatedTime() {
            return this.primesConfigsCreated;
        }

        @Nullable
        public CpuWallTime getFlagsCreatedTime() {
            return this.primesFlagsCreated;
        }

        @Nullable
        public CpuWallTime getInitEndTime() {
            return this.primesInitEnd;
        }
    }

    static final class PrimesApiMeasurement {
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesInitializeEnd;
        /* access modifiers changed from: private */
        public volatile CpuWallTime primesInitializeStart;

        PrimesApiMeasurement() {
        }

        /* access modifiers changed from: package-private */
        public void onPrimesInitializeStart() {
            this.primesInitializeStart = CpuWallTime.now();
        }

        /* access modifiers changed from: package-private */
        public void onPrimesInitializeEnd() {
            this.primesInitializeEnd = CpuWallTime.now();
        }

        @Nullable
        public CpuWallTime getPrimesInitializeStartTime() {
            return this.primesInitializeStart;
        }

        @Nullable
        public CpuWallTime getPrimesInitializeEndTime() {
            return this.primesInitializeEnd;
        }
    }

    private static final class MeasurementsHolder {
        /* access modifiers changed from: private */
        public static final PrimesApiMeasurement apiMeasurement = new PrimesApiMeasurement();
        /* access modifiers changed from: private */
        public static final PrimesInitializationMeasurement initializationMeasurement = new PrimesInitializationMeasurement();

        private MeasurementsHolder() {
        }
    }

    static PrimesInitializationMeasurement initializationMeasurement() {
        return MeasurementsHolder.initializationMeasurement;
    }

    static PrimesApiMeasurement apiMeasurement() {
        return MeasurementsHolder.apiMeasurement;
    }

    @VisibleForTesting(otherwise = 5)
    static void reset() {
        CpuWallTime unused = apiMeasurement().primesInitializeStart = null;
        CpuWallTime unused2 = apiMeasurement().primesInitializeEnd = null;
        CpuWallTime unused3 = initializationMeasurement().primesInitStart = null;
        CpuWallTime unused4 = initializationMeasurement().primesShutdownInitialized = null;
        CpuWallTime unused5 = initializationMeasurement().primesConfigsCreated = null;
        CpuWallTime unused6 = initializationMeasurement().primesFlagsCreated = null;
        CpuWallTime unused7 = initializationMeasurement().primesInitEnd = null;
    }
}
