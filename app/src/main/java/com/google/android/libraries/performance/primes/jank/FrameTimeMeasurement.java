package com.google.android.libraries.performance.primes.jank;

import android.support.annotation.Nullable;

import logs.proto.wireless.performance.mobile.SystemHealthProto;

public interface FrameTimeMeasurement {
    void addFrame(int i, int i2);

    @Nullable
    SystemHealthProto.JankMetric getMetric();

    boolean isMetricReadyToBeSent();
}
