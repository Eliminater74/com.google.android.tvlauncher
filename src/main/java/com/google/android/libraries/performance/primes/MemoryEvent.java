package com.google.android.libraries.performance.primes;

import com.google.android.libraries.stitch.util.Preconditions;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import logs.proto.wireless.performance.mobile.MemoryMetric;

final class MemoryEvent {
    static final MemoryEvent EMPTY_SNAPSHOT = new MemoryEvent();
    private static final String TAG = "MemoryEvent";
    private final Future<MemoryMetric.MemoryUsageMetric> capture;
    private final long createTimestamp;

    private MemoryEvent() {
        this.createTimestamp = 0;
        this.capture = null;
    }

    MemoryEvent(long createTimestamp2, Future<MemoryMetric.MemoryUsageMetric> capture2) {
        this.createTimestamp = createTimestamp2;
        this.capture = (Future) Preconditions.checkNotNull(capture2);
    }

    /* access modifiers changed from: package-private */
    public long getCreateTimestamp() {
        return this.createTimestamp;
    }

    /* access modifiers changed from: package-private */
    public MemoryMetric.MemoryUsageMetric getMemoryUsageMetric() {
        if (this == EMPTY_SNAPSHOT) {
            PrimesLog.m54w(TAG, "metric requested for EMPTY_SNAPSHOT", new Object[0]);
            return null;
        }
        try {
            return this.capture.get();
        } catch (InterruptedException ex) {
            PrimesLog.m53w(TAG, "exception during memory snapshot", ex, new Object[0]);
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException ex2) {
            PrimesLog.m53w(TAG, "exception during memory snapshot", ex2, new Object[0]);
            return null;
        }
    }

    static MemoryMetric.MemoryUsageMetric.Builder diff(MemoryMetric.MemoryUsageMetric start, MemoryMetric.MemoryUsageMetric end) {
        if (start == null || end == null || !start.hasMemoryStats() || !end.hasMemoryStats() || !start.getMemoryStats().hasAndroidMemoryStats() || !end.getMemoryStats().hasAndroidMemoryStats()) {
            return null;
        }
        return MemoryMetric.MemoryUsageMetric.newBuilder().setMemoryStats(MemoryMetric.MemoryStats.newBuilder().setAndroidMemoryStats(diff(start.getMemoryStats().getAndroidMemoryStats(), end.getMemoryStats().getAndroidMemoryStats())));
    }

    private static MemoryMetric.AndroidMemoryStats diff(MemoryMetric.AndroidMemoryStats start, MemoryMetric.AndroidMemoryStats end) {
        MemoryMetric.AndroidMemoryStats.Builder result = MemoryMetric.AndroidMemoryStats.newBuilder();
        if (start.hasDalvikPssKb() && end.hasDalvikPssKb()) {
            result.setDalvikPssKb(end.getDalvikPssKb() - start.getDalvikPssKb());
        }
        if (start.hasNativePssKb() && end.hasNativePssKb()) {
            result.setNativePssKb(end.getNativePssKb() - start.getNativePssKb());
        }
        if (start.hasOtherPssKb() && end.hasOtherPssKb()) {
            result.setOtherPssKb(end.getOtherPssKb() - start.getOtherPssKb());
        }
        if (start.hasDalvikPrivateDirtyKb() && end.hasDalvikPrivateDirtyKb()) {
            result.setDalvikPrivateDirtyKb(end.getDalvikPrivateDirtyKb() - start.getDalvikPrivateDirtyKb());
        }
        if (start.hasNativePrivateDirtyKb() && end.hasNativePrivateDirtyKb()) {
            result.setNativePrivateDirtyKb(end.getNativePrivateDirtyKb() - start.getNativePrivateDirtyKb());
        }
        if (start.hasOtherPrivateDirtyKb() && end.hasOtherPrivateDirtyKb()) {
            result.setOtherPrivateDirtyKb(end.getOtherPrivateDirtyKb() - start.getOtherPrivateDirtyKb());
        }
        if (start.hasTotalPrivateCleanKb() && end.hasTotalPrivateCleanKb()) {
            result.setTotalPrivateCleanKb(end.getTotalPrivateCleanKb() - start.getTotalPrivateCleanKb());
        }
        if (start.hasTotalSharedDirtyKb() && end.hasTotalSharedDirtyKb()) {
            result.setTotalSharedDirtyKb(end.getTotalSharedDirtyKb() - start.getTotalSharedDirtyKb());
        }
        if (start.hasTotalSwappablePssKb() && end.hasTotalSwappablePssKb()) {
            result.setTotalSwappablePssKb(end.getTotalSwappablePssKb() - start.getTotalSwappablePssKb());
        }
        if (start.hasOtherGraphicsPssKb() && end.hasOtherGraphicsPssKb()) {
            result.setOtherGraphicsPssKb(end.getOtherGraphicsPssKb() - start.getOtherGraphicsPssKb());
        }
        if (start.hasSummaryJavaHeapKb() && end.hasSummaryJavaHeapKb()) {
            result.setSummaryJavaHeapKb(end.getSummaryJavaHeapKb() - start.getSummaryJavaHeapKb());
        }
        if (start.hasSummaryCodeKb() && end.hasSummaryCodeKb()) {
            result.setSummaryCodeKb(end.getSummaryCodeKb() - start.getSummaryCodeKb());
        }
        if (start.hasSummaryStackKb() && end.hasSummaryStackKb()) {
            result.setSummaryStackKb(end.getSummaryStackKb() - start.getSummaryStackKb());
        }
        if (start.hasSummaryGraphicsKb() && end.hasSummaryGraphicsKb()) {
            result.setSummaryGraphicsKb(end.getSummaryGraphicsKb() - start.getSummaryGraphicsKb());
        }
        if (start.hasSummaryPrivateOtherKb() && end.hasSummaryPrivateOtherKb()) {
            result.setSummaryPrivateOtherKb(end.getSummaryPrivateOtherKb() - start.getSummaryPrivateOtherKb());
        }
        if (start.hasSummarySystemKb() && end.hasSummarySystemKb()) {
            result.setSummarySystemKb(end.getSummarySystemKb() - start.getSummarySystemKb());
        }
        if (start.hasAvailableMemoryKb() && end.hasAvailableMemoryKb()) {
            result.setAvailableMemoryKb(end.getAvailableMemoryKb() - start.getAvailableMemoryKb());
        }
        if (start.hasTotalMemoryMb() && end.hasTotalMemoryMb()) {
            result.setTotalMemoryMb(end.getTotalMemoryMb() - start.getTotalMemoryMb());
        }
        return (MemoryMetric.AndroidMemoryStats) result.build();
    }

    /* access modifiers changed from: package-private */
    public boolean isEmptySnapshot() {
        return this == EMPTY_SNAPSHOT;
    }
}
