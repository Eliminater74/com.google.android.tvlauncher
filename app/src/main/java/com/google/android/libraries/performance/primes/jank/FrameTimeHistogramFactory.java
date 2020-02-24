package com.google.android.libraries.performance.primes.jank;

public final class FrameTimeHistogramFactory implements FrameTimeMeasurementFactory {
    public FrameTimeMeasurement newMeasurement(String eventName) {
        return new FrameTimeHistogram();
    }
}
