package com.google.android.libraries.performance.primes.metriccapture;

import android.content.Context;
import android.os.Process;
import logs.proto.wireless.performance.mobile.ProcessProto;

public final class ProcessStatsCapture {
    private ProcessStatsCapture() {
    }

    public static ProcessProto.AndroidProcessStats getAndroidProcessStats(Context appContext) {
        return getAndroidProcessStats(null, appContext);
    }

    static ProcessProto.AndroidProcessStats getAndroidProcessStats(String processName, Context appContext) {
        ProcessProto.AndroidProcessStats.Builder stats = ProcessProto.AndroidProcessStats.newBuilder().setProcessElapsedTimeMs(Process.getElapsedCpuTime()).setIsInForeground(ProcessStats.isAppInForeground(appContext)).setThreadCount(Thread.activeCount());
        if (processName != null) {
            stats.setProcessName(processName);
        }
        return (ProcessProto.AndroidProcessStats) stats.build();
    }
}
