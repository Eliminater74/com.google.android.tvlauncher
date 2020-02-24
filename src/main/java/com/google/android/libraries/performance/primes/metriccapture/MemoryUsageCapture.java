package com.google.android.libraries.performance.primes.metriccapture;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.android.libraries.stitch.util.ThreadUtil;
import java.lang.reflect.Method;
import java.util.Map;
import logs.proto.wireless.performance.mobile.MemoryMetric;
import logs.proto.wireless.performance.mobile.ProcessProto;

public final class MemoryUsageCapture {
    private static final int DEBUG_MEMORY_INFO_OTHER_GRAPHICS = 14;
    private static final String SUMMARY_STATS_CODE = "summary.code";
    private static final String SUMMARY_STATS_GRAPHICS = "summary.graphics";
    private static final String SUMMARY_STATS_JAVA_HEAP = "summary.java-heap";
    private static final String SUMMARY_STATS_PRIVATE_OTHER = "summary.private-other";
    private static final String SUMMARY_STATS_STACK = "summary.stack";
    private static final String SUMMARY_STATS_SYSTEM = "summary.system";
    private static final String TAG = "PrimesMemoryCapture";
    private static Method otherPssGetter;
    private static volatile boolean otherPssGetterInitialized;

    private static Method getOtherPssGetter() {
        if (!otherPssGetterInitialized) {
            synchronized (MemoryUsageCapture.class) {
                if (!otherPssGetterInitialized) {
                    Class<Debug.MemoryInfo> cls = Debug.MemoryInfo.class;
                    try {
                        otherPssGetter = cls.getDeclaredMethod("getOtherPss", Integer.TYPE);
                    } catch (NoSuchMethodException me) {
                        PrimesLog.m45d(TAG, "MemoryInfo.getOtherPss(which) not found", me, new Object[0]);
                    } catch (Error | Exception e) {
                        PrimesLog.m47e(TAG, "MemoryInfo.getOtherPss(which) failure", e, new Object[0]);
                    }
                    otherPssGetterInitialized = true;
                }
            }
        }
        return otherPssGetter;
    }

    static int getOtherGraphicsPss(Debug.MemoryInfo memInfo) {
        Method method = getOtherPssGetter();
        if (method == null) {
            return -1;
        }
        try {
            return ((Integer) method.invoke(memInfo, 14)).intValue();
        } catch (Error | Exception e) {
            otherPssGetter = null;
            PrimesLog.m47e(TAG, "MemoryInfo.getOtherPss(which) invocation failure", e, new Object[0]);
            return -1;
        }
    }

    private MemoryUsageCapture() {
    }

    public static MemoryMetric.MemoryUsageMetric getMemoryUsageMetric(MemoryMetric.MemoryUsageMetric.MemoryEventCode eventCode, Context appContext, String activityName, boolean skipMemorySummaryStats) {
        return getMemoryUsageMetric(eventCode, Process.myPid(), null, appContext, activityName, skipMemorySummaryStats);
    }

    public static MemoryMetric.MemoryUsageMetric getMemoryUsageMetric(MemoryMetric.MemoryUsageMetric.MemoryEventCode eventCode, int processId, String processName, Context appContext, String activityName, boolean skipMemorySummaryStats) {
        ThreadUtil.ensureBackgroundThread();
        Preconditions.checkNotNull(appContext);
        Debug.MemoryInfo[] debugInfos = ProcessStats.getActivityManager(appContext).getProcessMemoryInfo(new int[]{processId});
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ProcessStats.getActivityManager(appContext).getMemoryInfo(memoryInfo);
        MemoryMetric.MemoryUsageMetric.Builder result = MemoryMetric.MemoryUsageMetric.newBuilder().setMemoryStats(MemoryMetric.MemoryStats.newBuilder().setAndroidMemoryStats(toAndroidMemoryStats(debugInfos[0], memoryInfo, skipMemorySummaryStats))).setProcessStats(ProcessProto.ProcessStats.newBuilder().setAndroidProcessStats(ProcessStatsCapture.getAndroidProcessStats(processName, appContext))).setDeviceStats(MemoryMetric.DeviceStats.newBuilder().setIsScreenOn(ProcessStats.isScreenOn(appContext))).setMemoryEventCode(eventCode);
        if (activityName != null) {
            result.setActivityName(activityName);
        }
        return (MemoryMetric.MemoryUsageMetric) result.build();
    }

    public static int getTotalPssKb(Context appContext) {
        return ProcessStats.getActivityManager(appContext).getProcessMemoryInfo(new int[]{Process.myPid()})[0].getTotalPss();
    }

    public static long getAllocatedBytes() {
        try {
            return Long.parseLong(Debug.getRuntimeStat("art.gc.bytes-allocated")) - Long.parseLong(Debug.getRuntimeStat("art.gc.bytes-freed"));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @VisibleForTesting
    static MemoryMetric.AndroidMemoryStats toAndroidMemoryStats(Debug.MemoryInfo debugInfo, ActivityManager.MemoryInfo memInfo, boolean skipMemorySummaryStats) {
        int eglMtrack;
        MemoryMetric.AndroidMemoryStats.Builder stats = MemoryMetric.AndroidMemoryStats.newBuilder().setDalvikPssKb(debugInfo.dalvikPss).setNativePssKb(debugInfo.nativePss).setOtherPssKb(debugInfo.otherPss).setDalvikPrivateDirtyKb(debugInfo.dalvikPrivateDirty).setNativePrivateDirtyKb(debugInfo.nativePrivateDirty).setOtherPrivateDirtyKb(debugInfo.otherPrivateDirty).setTotalPssByMemInfoKb(debugInfo.getTotalPss());
        if (Build.VERSION.SDK_INT >= 19) {
            stats.setTotalPrivateCleanKb(debugInfo.getTotalPrivateClean()).setTotalSwappablePssKb(debugInfo.getTotalSwappablePss());
        }
        stats.setTotalSharedDirtyKb(debugInfo.getTotalSharedDirty());
        if (Build.VERSION.SDK_INT >= 19 && (eglMtrack = getOtherGraphicsPss(debugInfo)) != -1) {
            stats.setOtherGraphicsPssKb(eglMtrack);
        }
        if (Build.VERSION.SDK_INT >= 23 && !skipMemorySummaryStats) {
            try {
                Map<String, String> summaryStats = debugInfo.getMemoryStats();
                Integer summaryCodeKb = toInteger(summaryStats.get(SUMMARY_STATS_CODE));
                if (summaryCodeKb != null) {
                    stats.setSummaryCodeKb(summaryCodeKb.intValue());
                }
                Integer summaryStackKb = toInteger(summaryStats.get(SUMMARY_STATS_STACK));
                if (summaryStackKb != null) {
                    stats.setSummaryStackKb(summaryStackKb.intValue());
                }
                Integer summaryGraphicsKb = toInteger(summaryStats.get(SUMMARY_STATS_GRAPHICS));
                if (summaryGraphicsKb != null) {
                    stats.setSummaryGraphicsKb(summaryGraphicsKb.intValue());
                }
                Integer summarySystemKb = toInteger(summaryStats.get(SUMMARY_STATS_SYSTEM));
                if (summarySystemKb != null) {
                    stats.setSummarySystemKb(summarySystemKb.intValue());
                }
                Integer summaryJavaHeapKb = toInteger(summaryStats.get(SUMMARY_STATS_JAVA_HEAP));
                if (summaryJavaHeapKb != null) {
                    stats.setSummaryJavaHeapKb(summaryJavaHeapKb.intValue());
                }
                Integer summaryPrivateOtherKb = toInteger(summaryStats.get(SUMMARY_STATS_PRIVATE_OTHER));
                if (summaryPrivateOtherKb != null) {
                    stats.setSummaryPrivateOtherKb(summaryPrivateOtherKb.intValue());
                }
            } catch (NumberFormatException e) {
                PrimesLog.m48e(TAG, "failed to collect memory summary stats", new Object[0]);
            }
        }
        return (MemoryMetric.AndroidMemoryStats) stats.setAvailableMemoryKb((int) (memInfo.availMem >> 10)).setTotalMemoryMb((int) (memInfo.totalMem >> 20)).build();
    }

    @Nullable
    private static Integer toInteger(@Nullable String stats) {
        if (stats == null) {
            return null;
        }
        return Integer.valueOf(Integer.parseInt(stats));
    }
}
