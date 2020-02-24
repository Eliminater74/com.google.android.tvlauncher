package com.google.android.libraries.performance.primes.metriccapture;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;
import logs.proto.wireless.performance.mobile.CpuMetric;

public final class CpuUsageCapture {
    private static final String TAG = "PrimesCpuCapture";

    private CpuUsageCapture() {
    }

    public static CpuMetric.CpuUsageMetric getCpuUsageMetric() {
        return toProto(new ArrayList<>(Thread.getAllStackTraces().values()));
    }

    @VisibleForTesting
    static CpuMetric.CpuUsageMetric toProto(@NonNull List<StackTraceElement[]> stackTraces) {
        CpuMetric.CpuUsageMetric.Builder metric = CpuMetric.CpuUsageMetric.newBuilder();
        for (StackTraceElement[] stackTraceElements : stackTraces) {
            metric.addStackTraces(CpuMetric.StackTrace.newBuilder().addAllStackElements(getStackElements(stackTraceElements)));
        }
        return (CpuMetric.CpuUsageMetric) metric.build();
    }

    private static ArrayList<CpuMetric.StackElement> getStackElements(StackTraceElement[] stackTraceElements) {
        ArrayList<CpuMetric.StackElement> stackElements = new ArrayList<>(stackTraceElements.length);
        for (StackTraceElement elementToConvert : stackTraceElements) {
            CpuMetric.StackElement.Builder newBuilder = CpuMetric.StackElement.newBuilder();
            String className = elementToConvert.getClassName();
            String methodName = elementToConvert.getMethodName();
            StringBuilder sb = new StringBuilder(String.valueOf(className).length() + 1 + String.valueOf(methodName).length());
            sb.append(className);
            sb.append(".");
            sb.append(methodName);
            stackElements.add((CpuMetric.StackElement) newBuilder.setFunctionName(sb.toString()).build());
        }
        return stackElements;
    }
}
