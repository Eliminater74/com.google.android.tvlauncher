package com.google.android.libraries.performance.primes.aggregation.impl;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.aggregation.MetricAggregatorIdentifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import logs.proto.wireless.performance.mobile.AggregatedMetricProto;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

public final class MetricAggregatorStore {
    @VisibleForTesting
    final ConcurrentHashMap<MetricAggregatorIdentifier, MetricAggregator> aggregations = new ConcurrentHashMap<>();

    public MetricAggregator getAggregation(MetricAggregatorIdentifier identifier) {
        MetricAggregator aggregator = this.aggregations.get(identifier);
        if (aggregator != null) {
            return aggregator;
        }
        this.aggregations.putIfAbsent(identifier, new MetricAggregator(identifier));
        return this.aggregations.get(identifier);
    }

    @Nullable
    public SystemHealthProto.SystemHealthMetric flushMetrics() {
        List<AggregatedMetricProto.AggregatedMetric> metrics = new ArrayList<>(this.aggregations.size());
        for (MetricAggregatorIdentifier identifier : this.aggregations.keySet()) {
            AggregatedMetricProto.AggregatedMetric aggregatedMetric = this.aggregations.remove(identifier).getMetric();
            if (aggregatedMetric.getAggregatedData().getCount() > 0) {
                metrics.add(aggregatedMetric);
            }
        }
        if (metrics.isEmpty()) {
            return null;
        }
        return (SystemHealthProto.SystemHealthMetric) SystemHealthProto.SystemHealthMetric.newBuilder().addAllAggregatedMetrics(metrics).build();
    }
}
