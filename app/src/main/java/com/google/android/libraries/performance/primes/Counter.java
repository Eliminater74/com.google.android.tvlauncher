package com.google.android.libraries.performance.primes;

import com.google.android.libraries.performance.primes.aggregation.impl.SummaryAggregator;

public final class Counter {
    static final Counter EMPTY_COUNTER = new Counter();
    private final SummaryAggregator summaryAggregator = new SummaryAggregator();

    public void addValue(long value) {
        this.summaryAggregator.recordValue(value);
    }

    /* access modifiers changed from: package-private */
    public SummaryAggregator asSummaryAggregator() {
        return this.summaryAggregator;
    }
}
