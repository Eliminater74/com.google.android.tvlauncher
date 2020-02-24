package com.google.android.libraries.performance.primes;

import android.support.annotation.Nullable;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public interface JankMetricExtensionProvider {
    @Nullable
    ExtensionMetric.MetricExtension getMetricExtension();
}
