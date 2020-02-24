package com.google.android.libraries.performance.primes.aggregation.impl;

import com.google.android.libraries.performance.primes.aggregation.MetricAggregatorIdentifier;
import logs.proto.wireless.performance.mobile.AggregatedMetricProto;

public final class MetricAggregator {
    private final SummaryAggregator data = new SummaryAggregator();
    private final MetricAggregatorIdentifier identifier;

    MetricAggregator(MetricAggregatorIdentifier identifier2) {
        this.identifier = identifier2;
    }

    public void recordValue(long value) {
        this.data.threadSafeRecordValue(value);
    }

    public void recordAllFrom(SummaryAggregator aggregator) {
        this.data.record(aggregator);
    }

    public AggregatedMetricProto.AggregatedMetric getMetric() {
        return (AggregatedMetricProto.AggregatedMetric) AggregatedMetricProto.AggregatedMetric.newBuilder().setAggregatedData(this.data.toProto()).setIdentifier(this.identifier.toProto()).build();
    }
}
