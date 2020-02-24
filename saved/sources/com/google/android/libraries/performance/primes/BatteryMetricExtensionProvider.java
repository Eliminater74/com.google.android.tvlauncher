package com.google.android.libraries.performance.primes;

import javax.annotation.Nullable;
import logs.proto.wireless.performance.mobile.BatteryMetric;
import logs.proto.wireless.performance.mobile.ExtensionMetric;

public interface BatteryMetricExtensionProvider {
    @Nullable
    ExtensionMetric.MetricExtension getMetricExtension(@Nullable String str, BatteryMetric.BatteryStatsDiff.SampleInfo sampleInfo);
}
