package com.google.android.exoplayer2.util;

import java.util.Comparator;

final /* synthetic */ class SlidingPercentile$$Lambda$1 implements Comparator {
    static final Comparator $instance = new SlidingPercentile$$Lambda$1();

    private SlidingPercentile$$Lambda$1() {
    }

    public int compare(Object obj, Object obj2) {
        return Float.compare(((SlidingPercentile.Sample) obj).value, ((SlidingPercentile.Sample) obj2).value);
    }
}
