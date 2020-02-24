package com.google.android.libraries.performance.primes.tracing;

import java.util.Comparator;

final /* synthetic */ class TraceData$$Lambda$0 implements Comparator {
    static final Comparator $instance = new TraceData$$Lambda$0();

    private TraceData$$Lambda$0() {
    }

    public int compare(Object obj, Object obj2) {
        return TraceData.lambda$linkTraceAndGetRootSpan$0$TraceData((SpanEvent) obj, (SpanEvent) obj2);
    }
}
