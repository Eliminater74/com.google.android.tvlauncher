package com.google.android.libraries.performance.primes.miniheapdump;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.persistent.PersistentStorage;
import com.google.android.libraries.performance.proto.primes.persistent.PersistentFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class MiniHeapDumpMemorySampler {
    private static final double DEFAULT_MIN_SAMPLE_DISTRIBUTION_FACTOR = 1.2d;
    static final int MAX_NUMBER_STORED_SAMPLES = 100;
    private static final String STORAGE_KEY = "primes.miniheapdump.memorySamples";
    private static final String TAG = "MhdMemorySampler";
    private final double minSampleDistributionFactor;
    private final Random random;
    private final ArrayList<Integer> samples = new ArrayList<>();
    private final PersistentStorage storage;
    private final int versionNameHash;

    private static PersistentFormat.PersistentMemorySamples loadMemorySamples(PersistentStorage storage2, int versionNameHash2) {
        PersistentFormat.PersistentMemorySamples persistentMemorySamples = (PersistentFormat.PersistentMemorySamples) storage2.readProto(STORAGE_KEY, PersistentFormat.PersistentMemorySamples.parser());
        if (persistentMemorySamples == null || !persistentMemorySamples.hasVersionNameHash() || persistentMemorySamples.getVersionNameHash() == versionNameHash2) {
            return persistentMemorySamples;
        }
        return PersistentFormat.PersistentMemorySamples.getDefaultInstance();
    }

    public static MiniHeapDumpMemorySampler newInstance(SharedPreferences sharedPreferences, int versionNameHash2) {
        PersistentStorage storage2 = new PersistentStorage(sharedPreferences);
        return new MiniHeapDumpMemorySampler(storage2, loadMemorySamples(storage2, versionNameHash2), DEFAULT_MIN_SAMPLE_DISTRIBUTION_FACTOR, versionNameHash2, new Random());
    }

    @VisibleForTesting
    MiniHeapDumpMemorySampler(PersistentStorage storage2, PersistentFormat.PersistentMemorySamples samples2, double minSampleDistributionFactor2, int versionNameHash2, Random random2) {
        this.storage = storage2;
        this.minSampleDistributionFactor = minSampleDistributionFactor2;
        this.versionNameHash = versionNameHash2;
        this.random = random2;
        addAllMemorySamples(samples2);
    }

    private void addAllMemorySamples(@Nullable PersistentFormat.PersistentMemorySamples samples2) {
        if (samples2 != null) {
            for (PersistentFormat.MemorySample sample : samples2.getSamplesList()) {
                this.samples.add(sample.hasTotalPssKb() ? Integer.valueOf(sample.getTotalPssKb()) : null);
            }
        }
    }

    public synchronized void addSample(int totalPssKb) {
        if (this.samples.size() + 1 > 100) {
            this.samples.remove(this.random.nextInt(this.samples.size()));
        }
        this.samples.add(Integer.valueOf(totalPssKb));
        if (!saveMemorySamples()) {
            PrimesLog.m46d(TAG, "Failed to save mini heap dump memory samples.", new Object[0]);
        }
    }

    public double calculateQuantile(int measurement) {
        double numberOfSamplesBelowMeasurement = 0.0d;
        Iterator<Integer> it = this.samples.iterator();
        while (it.hasNext()) {
            if (measurement >= it.next().intValue()) {
                numberOfSamplesBelowMeasurement += 1.0d;
            }
        }
        double size = (double) this.samples.size();
        Double.isNaN(size);
        return numberOfSamplesBelowMeasurement / size;
    }

    public boolean canComputePercentile() {
        if (this.samples.size() == 100) {
            double d = this.minSampleDistributionFactor;
            double intValue = (double) ((Integer) Collections.min(this.samples)).intValue();
            Double.isNaN(intValue);
            if (d * intValue <= ((double) ((Integer) Collections.max(this.samples)).intValue())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> getSamples() {
        return this.samples;
    }

    public boolean isSampleAtLeastPercentile(int totalPssKb, double percentile) {
        return canComputePercentile() && calculateQuantile(totalPssKb) >= percentile;
    }

    private boolean saveMemorySamples() {
        PersistentFormat.PersistentMemorySamples.Builder memorySamples = PersistentFormat.PersistentMemorySamples.newBuilder().setVersionNameHash(this.versionNameHash);
        for (int i = 0; i < this.samples.size(); i++) {
            memorySamples.addSamples((PersistentFormat.MemorySample) PersistentFormat.MemorySample.newBuilder().setTotalPssKb(this.samples.get(i).intValue()).build());
        }
        return this.storage.writeProto(STORAGE_KEY, (PersistentFormat.PersistentMemorySamples) memorySamples.build());
    }
}
