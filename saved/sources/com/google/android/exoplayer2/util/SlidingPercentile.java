package com.google.android.exoplayer2.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SlidingPercentile {
    private static final Comparator<Sample> INDEX_COMPARATOR = SlidingPercentile$$Lambda$0.$instance;
    private static final int MAX_RECYCLED_SAMPLES = 5;
    private static final int SORT_ORDER_BY_INDEX = 1;
    private static final int SORT_ORDER_BY_VALUE = 0;
    private static final int SORT_ORDER_NONE = -1;
    private static final Comparator<Sample> VALUE_COMPARATOR = SlidingPercentile$$Lambda$1.$instance;
    private int currentSortOrder = -1;
    private final int maxWeight;
    private int nextSampleIndex;
    private int recycledSampleCount;
    private final Sample[] recycledSamples = new Sample[5];
    private final ArrayList<Sample> samples = new ArrayList<>();
    private int totalWeight;

    static final /* synthetic */ int lambda$static$0$SlidingPercentile(Sample a, Sample b) {
        return a.index - b.index;
    }

    public SlidingPercentile(int maxWeight2) {
        this.maxWeight = maxWeight2;
    }

    public void reset() {
        this.samples.clear();
        this.currentSortOrder = -1;
        this.nextSampleIndex = 0;
        this.totalWeight = 0;
    }

    public void addSample(int weight, float value) {
        Sample newSample;
        ensureSortedByIndex();
        int i = this.recycledSampleCount;
        if (i > 0) {
            Sample[] sampleArr = this.recycledSamples;
            int i2 = i - 1;
            this.recycledSampleCount = i2;
            newSample = sampleArr[i2];
        } else {
            newSample = new Sample();
        }
        int i3 = this.nextSampleIndex;
        this.nextSampleIndex = i3 + 1;
        newSample.index = i3;
        newSample.weight = weight;
        newSample.value = value;
        this.samples.add(newSample);
        this.totalWeight += weight;
        while (true) {
            int i4 = this.totalWeight;
            int i5 = this.maxWeight;
            if (i4 > i5) {
                int excessWeight = i4 - i5;
                Sample oldestSample = this.samples.get(0);
                if (oldestSample.weight <= excessWeight) {
                    this.totalWeight -= oldestSample.weight;
                    this.samples.remove(0);
                    int i6 = this.recycledSampleCount;
                    if (i6 < 5) {
                        Sample[] sampleArr2 = this.recycledSamples;
                        this.recycledSampleCount = i6 + 1;
                        sampleArr2[i6] = oldestSample;
                    }
                } else {
                    oldestSample.weight -= excessWeight;
                    this.totalWeight -= excessWeight;
                }
            } else {
                return;
            }
        }
    }

    public float getPercentile(float percentile) {
        ensureSortedByValue();
        float desiredWeight = ((float) this.totalWeight) * percentile;
        int accumulatedWeight = 0;
        for (int i = 0; i < this.samples.size(); i++) {
            Sample currentSample = this.samples.get(i);
            accumulatedWeight += currentSample.weight;
            if (((float) accumulatedWeight) >= desiredWeight) {
                return currentSample.value;
            }
        }
        if (this.samples.isEmpty()) {
            return Float.NaN;
        }
        ArrayList<Sample> arrayList = this.samples;
        return arrayList.get(arrayList.size() - 1).value;
    }

    private void ensureSortedByIndex() {
        if (this.currentSortOrder != 1) {
            Collections.sort(this.samples, INDEX_COMPARATOR);
            this.currentSortOrder = 1;
        }
    }

    private void ensureSortedByValue() {
        if (this.currentSortOrder != 0) {
            Collections.sort(this.samples, VALUE_COMPARATOR);
            this.currentSortOrder = 0;
        }
    }

    private static class Sample {
        public int index;
        public float value;
        public int weight;

        private Sample() {
        }
    }
}
