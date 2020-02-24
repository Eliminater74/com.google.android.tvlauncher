package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.battery.BatteryCapture;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import javax.annotation.Nullable;

public class PrimesBatterySnapshot {
    static final PrimesBatterySnapshot EMPTY_SNAPSHOT = new PrimesBatterySnapshot();
    private static final String TAG = "BatterySnapshot";
    private final Future<BatteryCapture.Snapshot> capture;

    private PrimesBatterySnapshot() {
        this.capture = null;
    }

    PrimesBatterySnapshot(Future<BatteryCapture.Snapshot> capture2) {
        this.capture = (Future) Preconditions.checkNotNull(capture2);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public BatteryCapture.Snapshot getBatterySnapshot() {
        if (this == EMPTY_SNAPSHOT) {
            PrimesLog.m54w(TAG, "metric requested for EMPTY_SNAPSHOT", new Object[0]);
            return null;
        }
        try {
            return this.capture.get();
        } catch (InterruptedException ex) {
            PrimesLog.m53w(TAG, "exception during battery snapshot", ex, new Object[0]);
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException ex2) {
            PrimesLog.m53w(TAG, "exception during battery snapshot", ex2, new Object[0]);
            return null;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isEmptySnapshot() {
        return this == EMPTY_SNAPSHOT;
    }
}
