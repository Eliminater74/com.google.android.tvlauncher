package com.google.android.libraries.performance.primes.jank;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.stitch.util.Preconditions;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import logs.proto.wireless.performance.mobile.SystemHealthProto;

final class FrameTimeHistogram implements FrameTimeMeasurement {
    private static final int[] BUCKETS_BOUNDS = {0, 4, 8, 10, 12, 14, 16, 18, 20, 25, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, ClientAnalytics.LogRequest.LogSource.CHIME_VALUE, 400, ClientAnalytics.LogRequest.LogSource.GOR_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.ANDROID_OTA_VALUE, ClientAnalytics.LogRequest.LogSource.ARCORE_VALUE, ClientAnalytics.LogRequest.LogSource.BETTERBUG_ANDROID_VALUE, ClientAnalytics.LogRequest.LogSource.COGSWORTH_ANDROID_PRIMES_VALUE, 1000};
    private static final String TAG = "FrameTimeHistogram";
    private final int[] buckets = new int[BUCKETS_BOUNDS.length];
    private int jankyFrameCount;
    private int maxRenderTimeMs;
    private int renderedFrameCount;
    private int totalFrameTimeMs;

    FrameTimeHistogram() {
    }

    @VisibleForTesting
    static int[] bucketBounds() {
        return (int[]) BUCKETS_BOUNDS.clone();
    }

    public void addFrame(int frameTimeMs, int maxAcceptedFrameTimeMs) {
        Preconditions.checkArgument(frameTimeMs >= 0);
        this.renderedFrameCount++;
        if (frameTimeMs > maxAcceptedFrameTimeMs) {
            this.jankyFrameCount++;
        }
        int[] iArr = this.buckets;
        int indexForFrameTime = indexForFrameTime(frameTimeMs);
        iArr[indexForFrameTime] = iArr[indexForFrameTime] + 1;
        this.maxRenderTimeMs = Math.max(this.maxRenderTimeMs, frameTimeMs);
        this.totalFrameTimeMs += frameTimeMs;
    }

    public boolean isMetricReadyToBeSent() {
        return this.renderedFrameCount != 0;
    }

    @Nullable
    public SystemHealthProto.JankMetric getMetric() {
        if (!isMetricReadyToBeSent()) {
            return null;
        }
        return (SystemHealthProto.JankMetric) SystemHealthProto.JankMetric.newBuilder().setJankyFrameCount(this.jankyFrameCount).setRenderedFrameCount(this.renderedFrameCount).setDurationMs(this.totalFrameTimeMs).setMaxFrameRenderTimeMs(this.maxRenderTimeMs).addAllFrameTimeHistogram(Arrays.asList(createFrameTimeHistogram(this.buckets))).build();
    }

    public int getRenderedFrameCount() {
        return this.renderedFrameCount;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public int[] getBuckets() {
        return (int[]) this.buckets.clone();
    }

    @VisibleForTesting
    static int indexForFrameTime(int frameTimeMs) {
        int result = Arrays.binarySearch(BUCKETS_BOUNDS, frameTimeMs);
        return result < 0 ? -(result + 2) : result;
    }

    private static SystemHealthProto.HistogramBucket[] createFrameTimeHistogram(int[] buckets2) {
        List<SystemHealthProto.HistogramBucket> histogram = new ArrayList<>();
        int i = 0;
        while (true) {
            int[] iArr = BUCKETS_BOUNDS;
            boolean z = false;
            if (i >= iArr.length) {
                return (SystemHealthProto.HistogramBucket[]) histogram.toArray(new SystemHealthProto.HistogramBucket[0]);
            }
            if (buckets2[i] > 0) {
                if (i + 1 == iArr.length) {
                    z = true;
                }
                boolean last = z;
                int i2 = buckets2[i];
                int[] iArr2 = BUCKETS_BOUNDS;
                histogram.add(makeBucket(i2, iArr2[i], last ? null : Integer.valueOf(iArr2[i + 1] - 1)));
            }
            i++;
        }
    }

    private static SystemHealthProto.HistogramBucket makeBucket(int count, int minIdx, Integer maxIdx) {
        Preconditions.checkArgument(count > 0);
        SystemHealthProto.HistogramBucket.Builder result = SystemHealthProto.HistogramBucket.newBuilder().setMin(minIdx).setCount(count);
        if (maxIdx != null) {
            result.setMax(maxIdx.intValue());
        }
        return (SystemHealthProto.HistogramBucket) result.build();
    }
}
