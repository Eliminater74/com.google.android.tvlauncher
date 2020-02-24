package com.google.android.libraries.performance.primes.aggregation.impl;

import logs.proto.wireless.performance.mobile.AggregatedMetricProto;

public class SummaryAggregator {
    private int count;
    private long max = Long.MIN_VALUE;
    private long min = Long.MAX_VALUE;
    private long sum;

    public void recordValue(long value) {
        this.count++;
        this.sum += value;
        this.max = Math.max(this.max, value);
        this.min = Math.min(this.min, value);
    }

    /* access modifiers changed from: package-private */
    public synchronized void threadSafeRecordValue(long value) {
        recordValue(value);
    }

    /* access modifiers changed from: package-private */
    public synchronized void record(SummaryAggregator other) {
        this.count += other.count;
        this.sum += other.sum;
        this.max = Math.max(this.max, other.max);
        this.min = Math.min(this.min, other.min);
    }

    public synchronized AggregatedMetricProto.AggregatedData toProto() {
        return (AggregatedMetricProto.AggregatedData) AggregatedMetricProto.AggregatedData.newBuilder().setCount(this.count).setSum(this.sum).setMax(this.max).setMin(this.min).build();
    }
}
