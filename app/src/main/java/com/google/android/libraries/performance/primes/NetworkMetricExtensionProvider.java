package com.google.android.libraries.performance.primes;

import com.google.common.base.Optional;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public interface NetworkMetricExtensionProvider {
    Optional<ExtensionMetric.MetricExtension> getMetricExtension();
}
