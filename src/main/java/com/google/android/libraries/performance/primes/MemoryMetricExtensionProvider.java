package com.google.android.libraries.performance.primes;

import javax.annotation.Nullable;
import logs.proto.wireless.performance.mobile.ExtensionMetric;
import logs.proto.wireless.performance.mobile.MemoryMetric;

public interface MemoryMetricExtensionProvider {
    @Nullable
    ExtensionMetric.MetricExtension getMetricExtension(@Nullable String str, MemoryMetric.MemoryUsageMetric.MemoryEventCode memoryEventCode);
}
