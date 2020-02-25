package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Clock;
import com.google.android.exoplayer2.util.Util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AdaptiveTrackSelection extends BaseTrackSelection {
    public static final float DEFAULT_BANDWIDTH_FRACTION = 0.75f;
    public static final float DEFAULT_BUFFERED_FRACTION_TO_LIVE_EDGE_FOR_QUALITY_INCREASE = 0.75f;
    public static final int DEFAULT_MAX_DURATION_FOR_QUALITY_DECREASE_MS = 25000;
    public static final int DEFAULT_MIN_DURATION_FOR_QUALITY_INCREASE_MS = 10000;
    public static final int DEFAULT_MIN_DURATION_TO_RETAIN_AFTER_DISCARD_MS = 25000;
    public static final long DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS = 2000;
    private final BandwidthProvider bandwidthProvider;
    private final float bufferedFractionToLiveEdgeForQualityIncrease;
    private final Clock clock;
    private final int[] formatBitrates;
    private final Format[] formats;
    private final long maxDurationForQualityDecreaseUs;
    private final long minDurationForQualityIncreaseUs;
    private final long minDurationToRetainAfterDiscardUs;
    private final long minTimeBetweenBufferReevaluationMs;
    private final int[] trackBitrates;
    private long lastBufferEvaluationMs;
    private float playbackSpeed;
    private int reason;
    private int selectedIndex;
    private TrackBitrateEstimator trackBitrateEstimator;

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter) {
        this(group, tracks, bandwidthMeter, 10000, 25000, 25000, 0.75f, 0.75f, (long) DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
    }

    public AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthMeter bandwidthMeter, long minDurationForQualityIncreaseMs, long maxDurationForQualityDecreaseMs, long minDurationToRetainAfterDiscardMs, float bandwidthFraction, float bufferedFractionToLiveEdgeForQualityIncrease2, long minTimeBetweenBufferReevaluationMs2, Clock clock2) {
        this(group, tracks, new DefaultBandwidthProvider(bandwidthMeter, bandwidthFraction), minDurationForQualityIncreaseMs, maxDurationForQualityDecreaseMs, minDurationToRetainAfterDiscardMs, bufferedFractionToLiveEdgeForQualityIncrease2, minTimeBetweenBufferReevaluationMs2, clock2);
    }

    private AdaptiveTrackSelection(TrackGroup group, int[] tracks, BandwidthProvider bandwidthProvider2, long minDurationForQualityIncreaseMs, long maxDurationForQualityDecreaseMs, long minDurationToRetainAfterDiscardMs, float bufferedFractionToLiveEdgeForQualityIncrease2, long minTimeBetweenBufferReevaluationMs2, Clock clock2) {
        super(group, tracks);
        this.bandwidthProvider = bandwidthProvider2;
        this.minDurationForQualityIncreaseUs = minDurationForQualityIncreaseMs * 1000;
        this.maxDurationForQualityDecreaseUs = maxDurationForQualityDecreaseMs * 1000;
        this.minDurationToRetainAfterDiscardUs = 1000 * minDurationToRetainAfterDiscardMs;
        this.bufferedFractionToLiveEdgeForQualityIncrease = bufferedFractionToLiveEdgeForQualityIncrease2;
        this.minTimeBetweenBufferReevaluationMs = minTimeBetweenBufferReevaluationMs2;
        this.clock = clock2;
        this.playbackSpeed = 1.0f;
        this.reason = 0;
        this.lastBufferEvaluationMs = C0841C.TIME_UNSET;
        this.trackBitrateEstimator = TrackBitrateEstimator.DEFAULT;
        this.formats = new Format[this.length];
        this.formatBitrates = new int[this.length];
        this.trackBitrates = new int[this.length];
        for (int i = 0; i < this.length; i++) {
            Format format = getFormat(i);
            Format[] formatArr = this.formats;
            formatArr[i] = format;
            this.formatBitrates[i] = formatArr[i].bitrate;
        }
    }

    /* access modifiers changed from: private */
    public static long[][][] getAllocationCheckpoints(long[][] trackBitrates2) {
        long[][] jArr = trackBitrates2;
        double[][] logBitrates = getLogArrayValues(trackBitrates2);
        double[][] switchPoints = getSwitchPoints(logBitrates);
        int checkpointCount = countArrayElements(switchPoints) + 3;
        long[][][] checkpoints = (long[][][]) Array.newInstance(long.class, logBitrates.length, checkpointCount, 2);
        int[] currentSelection = new int[logBitrates.length];
        setCheckpointValues(checkpoints, 1, jArr, currentSelection);
        for (int checkpointIndex = 2; checkpointIndex < checkpointCount - 1; checkpointIndex++) {
            int nextUpdateIndex = 0;
            double nextUpdateSwitchPoint = Double.MAX_VALUE;
            for (int i = 0; i < logBitrates.length; i++) {
                if (currentSelection[i] + 1 != logBitrates[i].length) {
                    double switchPoint = switchPoints[i][currentSelection[i]];
                    if (switchPoint < nextUpdateSwitchPoint) {
                        nextUpdateSwitchPoint = switchPoint;
                        nextUpdateIndex = i;
                    }
                }
            }
            currentSelection[nextUpdateIndex] = currentSelection[nextUpdateIndex] + 1;
            setCheckpointValues(checkpoints, checkpointIndex, jArr, currentSelection);
        }
        for (long[][] points : checkpoints) {
            points[checkpointCount - 1][0] = points[checkpointCount - 2][0] * 2;
            points[checkpointCount - 1][1] = points[checkpointCount - 2][1] * 2;
        }
        return checkpoints;
    }

    private static double[][] getLogArrayValues(long[][] values) {
        double[][] logValues = new double[values.length][];
        for (int i = 0; i < values.length; i++) {
            logValues[i] = new double[values[i].length];
            for (int j = 0; j < values[i].length; j++) {
                logValues[i][j] = Math.log((double) values[i][j]);
            }
        }
        return logValues;
    }

    private static double[][] getSwitchPoints(double[][] logBitrates) {
        double[][] switchPoints = new double[logBitrates.length][];
        for (int i = 0; i < logBitrates.length; i++) {
            switchPoints[i] = new double[(logBitrates[i].length - 1)];
            if (switchPoints[i].length != 0) {
                double totalBitrateDiff = logBitrates[i][logBitrates[i].length - 1] - logBitrates[i][0];
                for (int j = 0; j < logBitrates[i].length - 1; j++) {
                    switchPoints[i][j] = (((logBitrates[i][j] + logBitrates[i][j + 1]) * 0.5d) - logBitrates[i][0]) / totalBitrateDiff;
                }
            }
        }
        return switchPoints;
    }

    private static int countArrayElements(double[][] array) {
        int count = 0;
        for (double[] subArray : array) {
            count += subArray.length;
        }
        return count;
    }

    private static void setCheckpointValues(long[][][] checkpoints, int checkpointIndex, long[][] trackBitrates2, int[] selectedTracks) {
        long totalBitrate = 0;
        for (int i = 0; i < checkpoints.length; i++) {
            checkpoints[i][checkpointIndex][1] = trackBitrates2[i][selectedTracks[i]];
            totalBitrate += checkpoints[i][checkpointIndex][1];
        }
        for (long[][] points : checkpoints) {
            points[checkpointIndex][0] = totalBitrate;
        }
    }

    public void experimental_setTrackBitrateEstimator(TrackBitrateEstimator trackBitrateEstimator2) {
        this.trackBitrateEstimator = trackBitrateEstimator2;
    }

    public void experimental_setNonAllocatableBandwidth(long nonAllocatableBandwidth) {
        ((DefaultBandwidthProvider) this.bandwidthProvider).experimental_setNonAllocatableBandwidth(nonAllocatableBandwidth);
    }

    public void experimental_setBandwidthAllocationCheckpoints(long[][] allocationCheckpoints) {
        ((DefaultBandwidthProvider) this.bandwidthProvider).experimental_setBandwidthAllocationCheckpoints(allocationCheckpoints);
    }

    public void enable() {
        this.lastBufferEvaluationMs = C0841C.TIME_UNSET;
    }

    public void onPlaybackSpeed(float playbackSpeed2) {
        this.playbackSpeed = playbackSpeed2;
    }

    public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> queue, MediaChunkIterator[] mediaChunkIterators) {
        long nowMs = this.clock.elapsedRealtime();
        this.trackBitrateEstimator.getBitrates(this.formats, queue, mediaChunkIterators, this.trackBitrates);
        if (this.reason == 0) {
            this.reason = 1;
            this.selectedIndex = determineIdealSelectedIndex(nowMs, this.trackBitrates);
            return;
        }
        int currentSelectedIndex = this.selectedIndex;
        this.selectedIndex = determineIdealSelectedIndex(nowMs, this.trackBitrates);
        if (this.selectedIndex != currentSelectedIndex) {
            if (!isBlacklisted(currentSelectedIndex, nowMs)) {
                Format currentFormat = getFormat(currentSelectedIndex);
                Format selectedFormat = getFormat(this.selectedIndex);
                if (selectedFormat.bitrate > currentFormat.bitrate) {
                    if (bufferedDurationUs < minDurationForQualityIncreaseUs(availableDurationUs)) {
                        this.selectedIndex = currentSelectedIndex;
                    }
                }
                if (selectedFormat.bitrate < currentFormat.bitrate && bufferedDurationUs >= this.maxDurationForQualityDecreaseUs) {
                    this.selectedIndex = currentSelectedIndex;
                }
            }
            if (this.selectedIndex != currentSelectedIndex) {
                this.reason = 3;
            }
        }
    }

    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    public int getSelectionReason() {
        return this.reason;
    }

    @Nullable
    public Object getSelectionData() {
        return null;
    }

    public int evaluateQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        AdaptiveTrackSelection adaptiveTrackSelection = this;
        List<? extends MediaChunk> list = queue;
        long nowMs = adaptiveTrackSelection.clock.elapsedRealtime();
        if (!adaptiveTrackSelection.shouldEvaluateQueueSize(nowMs)) {
            return queue.size();
        }
        adaptiveTrackSelection.lastBufferEvaluationMs = nowMs;
        if (queue.isEmpty()) {
            return 0;
        }
        int queueSize = queue.size();
        long playoutBufferedDurationBeforeLastChunkUs = Util.getPlayoutDurationForMediaDuration(((MediaChunk) list.get(queueSize - 1)).startTimeUs - playbackPositionUs, adaptiveTrackSelection.playbackSpeed);
        long minDurationToRetainAfterDiscardUs2 = getMinDurationToRetainAfterDiscardUs();
        if (playoutBufferedDurationBeforeLastChunkUs < minDurationToRetainAfterDiscardUs2) {
            return queueSize;
        }
        Format idealFormat = adaptiveTrackSelection.getFormat(adaptiveTrackSelection.determineIdealSelectedIndex(nowMs, adaptiveTrackSelection.formatBitrates));
        int i = 0;
        while (i < queueSize) {
            MediaChunk chunk = (MediaChunk) list.get(i);
            Format format = chunk.trackFormat;
            long nowMs2 = nowMs;
            if (Util.getPlayoutDurationForMediaDuration(chunk.startTimeUs - playbackPositionUs, adaptiveTrackSelection.playbackSpeed) >= minDurationToRetainAfterDiscardUs2 && format.bitrate < idealFormat.bitrate && format.height != -1 && format.height < 720 && format.width != -1 && format.width < 1280 && format.height < idealFormat.height) {
                return i;
            }
            i++;
            adaptiveTrackSelection = this;
            list = queue;
            nowMs = nowMs2;
        }
        return queueSize;
    }

    /* access modifiers changed from: protected */
    public boolean canSelectFormat(Format format, int trackBitrate, float playbackSpeed2, long effectiveBitrate) {
        return ((long) Math.round(((float) trackBitrate) * playbackSpeed2)) <= effectiveBitrate;
    }

    /* access modifiers changed from: protected */
    public boolean shouldEvaluateQueueSize(long nowMs) {
        long j = this.lastBufferEvaluationMs;
        return j == C0841C.TIME_UNSET || nowMs - j >= this.minTimeBetweenBufferReevaluationMs;
    }

    /* access modifiers changed from: protected */
    public long getMinDurationToRetainAfterDiscardUs() {
        return this.minDurationToRetainAfterDiscardUs;
    }

    private int determineIdealSelectedIndex(long nowMs, int[] trackBitrates2) {
        long effectiveBitrate = this.bandwidthProvider.getAllocatedBandwidth();
        int lowestBitrateNonBlacklistedIndex = 0;
        for (int i = 0; i < this.length; i++) {
            if (nowMs == Long.MIN_VALUE || !isBlacklisted(i, nowMs)) {
                if (canSelectFormat(getFormat(i), trackBitrates2[i], this.playbackSpeed, effectiveBitrate)) {
                    return i;
                }
                lowestBitrateNonBlacklistedIndex = i;
            }
        }
        return lowestBitrateNonBlacklistedIndex;
    }

    private long minDurationForQualityIncreaseUs(long availableDurationUs) {
        if (availableDurationUs != C0841C.TIME_UNSET && availableDurationUs <= this.minDurationForQualityIncreaseUs) {
            return (long) (((float) availableDurationUs) * this.bufferedFractionToLiveEdgeForQualityIncrease);
        }
        return this.minDurationForQualityIncreaseUs;
    }

    private interface BandwidthProvider {
        long getAllocatedBandwidth();
    }

    public static class Factory implements TrackSelection.Factory {
        private final float bandwidthFraction;
        @Nullable
        private final BandwidthMeter bandwidthMeter;
        private final float bufferedFractionToLiveEdgeForQualityIncrease;
        private final Clock clock;
        private final int maxDurationForQualityDecreaseMs;
        private final int minDurationForQualityIncreaseMs;
        private final int minDurationToRetainAfterDiscardMs;
        private final long minTimeBetweenBufferReevaluationMs;
        private boolean blockFixedTrackSelectionBandwidth;
        private TrackBitrateEstimator trackBitrateEstimator;

        public Factory() {
            this(10000, 25000, 25000, 0.75f, 0.75f, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
        }

        @Deprecated
        public Factory(BandwidthMeter bandwidthMeter2) {
            this(bandwidthMeter2, 10000, 25000, 25000, 0.75f, 0.75f, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
        }

        public Factory(int minDurationForQualityIncreaseMs2, int maxDurationForQualityDecreaseMs2, int minDurationToRetainAfterDiscardMs2, float bandwidthFraction2) {
            this(minDurationForQualityIncreaseMs2, maxDurationForQualityDecreaseMs2, minDurationToRetainAfterDiscardMs2, bandwidthFraction2, 0.75f, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
        }

        @Deprecated
        public Factory(BandwidthMeter bandwidthMeter2, int minDurationForQualityIncreaseMs2, int maxDurationForQualityDecreaseMs2, int minDurationToRetainAfterDiscardMs2, float bandwidthFraction2) {
            this(bandwidthMeter2, minDurationForQualityIncreaseMs2, maxDurationForQualityDecreaseMs2, minDurationToRetainAfterDiscardMs2, bandwidthFraction2, 0.75f, AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS, Clock.DEFAULT);
        }

        public Factory(int minDurationForQualityIncreaseMs2, int maxDurationForQualityDecreaseMs2, int minDurationToRetainAfterDiscardMs2, float bandwidthFraction2, float bufferedFractionToLiveEdgeForQualityIncrease2, long minTimeBetweenBufferReevaluationMs2, Clock clock2) {
            this(null, minDurationForQualityIncreaseMs2, maxDurationForQualityDecreaseMs2, minDurationToRetainAfterDiscardMs2, bandwidthFraction2, bufferedFractionToLiveEdgeForQualityIncrease2, minTimeBetweenBufferReevaluationMs2, clock2);
        }

        @Deprecated
        public Factory(@Nullable BandwidthMeter bandwidthMeter2, int minDurationForQualityIncreaseMs2, int maxDurationForQualityDecreaseMs2, int minDurationToRetainAfterDiscardMs2, float bandwidthFraction2, float bufferedFractionToLiveEdgeForQualityIncrease2, long minTimeBetweenBufferReevaluationMs2, Clock clock2) {
            this.bandwidthMeter = bandwidthMeter2;
            this.minDurationForQualityIncreaseMs = minDurationForQualityIncreaseMs2;
            this.maxDurationForQualityDecreaseMs = maxDurationForQualityDecreaseMs2;
            this.minDurationToRetainAfterDiscardMs = minDurationToRetainAfterDiscardMs2;
            this.bandwidthFraction = bandwidthFraction2;
            this.bufferedFractionToLiveEdgeForQualityIncrease = bufferedFractionToLiveEdgeForQualityIncrease2;
            this.minTimeBetweenBufferReevaluationMs = minTimeBetweenBufferReevaluationMs2;
            this.clock = clock2;
            this.trackBitrateEstimator = TrackBitrateEstimator.DEFAULT;
        }

        public TrackSelection createTrackSelection(TrackGroup trackGroup, BandwidthMeter bandwidthMeter2, int... iArr) {
            return TrackSelection$Factory$$CC.createTrackSelection$$dflt$$(this, trackGroup, bandwidthMeter2, iArr);
        }

        public final void experimental_setTrackBitrateEstimator(TrackBitrateEstimator trackBitrateEstimator2) {
            this.trackBitrateEstimator = trackBitrateEstimator2;
        }

        public final void experimental_enableBlockFixedTrackSelectionBandwidth() {
            this.blockFixedTrackSelectionBandwidth = true;
        }

        /* JADX INFO: Multiple debug info for r4v3 long[][][]: [D('i' int), D('bandwidthCheckpoints' long[][][])] */
        public final TrackSelection[] createTrackSelections(TrackSelection.Definition[] definitions, BandwidthMeter bandwidthMeter2) {
            if (this.bandwidthMeter != null) {
                bandwidthMeter2 = this.bandwidthMeter;
            }
            TrackSelection[] selections = new TrackSelection[definitions.length];
            List<AdaptiveTrackSelection> adaptiveSelections = new ArrayList<>();
            int totalFixedBandwidth = 0;
            for (int i = 0; i < definitions.length; i++) {
                TrackSelection.Definition definition = definitions[i];
                if (definition != null) {
                    if (definition.tracks.length > 1) {
                        AdaptiveTrackSelection adaptiveSelection = createAdaptiveTrackSelection(definition.group, bandwidthMeter2, definition.tracks);
                        adaptiveSelection.experimental_setTrackBitrateEstimator(this.trackBitrateEstimator);
                        adaptiveSelections.add(adaptiveSelection);
                        selections[i] = adaptiveSelection;
                    } else {
                        selections[i] = new FixedTrackSelection(definition.group, definition.tracks[0], definition.reason, definition.data);
                        int trackBitrate = definition.group.getFormat(definition.tracks[0]).bitrate;
                        if (trackBitrate != -1) {
                            totalFixedBandwidth += trackBitrate;
                        }
                    }
                }
            }
            if (this.blockFixedTrackSelectionBandwidth != 0) {
                for (int i2 = 0; i2 < adaptiveSelections.size(); i2++) {
                    ((AdaptiveTrackSelection) adaptiveSelections.get(i2)).experimental_setNonAllocatableBandwidth((long) totalFixedBandwidth);
                }
            }
            if (adaptiveSelections.size() > 1) {
                long[][] adaptiveTrackBitrates = new long[adaptiveSelections.size()][];
                for (int i3 = 0; i3 < adaptiveSelections.size(); i3++) {
                    AdaptiveTrackSelection adaptiveSelection2 = (AdaptiveTrackSelection) adaptiveSelections.get(i3);
                    adaptiveTrackBitrates[i3] = new long[adaptiveSelection2.length()];
                    for (int j = 0; j < adaptiveSelection2.length(); j++) {
                        adaptiveTrackBitrates[i3][j] = (long) adaptiveSelection2.getFormat((adaptiveSelection2.length() - j) - 1).bitrate;
                    }
                }
                long[][][] bandwidthCheckpoints = AdaptiveTrackSelection.getAllocationCheckpoints(adaptiveTrackBitrates);
                for (int i4 = 0; i4 < adaptiveSelections.size(); i4++) {
                    ((AdaptiveTrackSelection) adaptiveSelections.get(i4)).experimental_setBandwidthAllocationCheckpoints(bandwidthCheckpoints[i4]);
                }
            }
            return selections;
        }

        /* access modifiers changed from: protected */
        public AdaptiveTrackSelection createAdaptiveTrackSelection(TrackGroup group, BandwidthMeter bandwidthMeter2, int[] tracks) {
            return new AdaptiveTrackSelection(group, tracks, new DefaultBandwidthProvider(bandwidthMeter2, this.bandwidthFraction), (long) this.minDurationForQualityIncreaseMs, (long) this.maxDurationForQualityDecreaseMs, (long) this.minDurationToRetainAfterDiscardMs, this.bufferedFractionToLiveEdgeForQualityIncrease, this.minTimeBetweenBufferReevaluationMs, this.clock);
        }
    }

    private static final class DefaultBandwidthProvider implements BandwidthProvider {
        private final float bandwidthFraction;
        private final BandwidthMeter bandwidthMeter;
        @Nullable
        private long[][] allocationCheckpoints;
        private long nonAllocatableBandwidth;

        DefaultBandwidthProvider(BandwidthMeter bandwidthMeter2, float bandwidthFraction2) {
            this.bandwidthMeter = bandwidthMeter2;
            this.bandwidthFraction = bandwidthFraction2;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.lang.Math.max(long, long):long}
         arg types: [int, long]
         candidates:
          ClspMth{java.lang.Math.max(double, double):double}
          ClspMth{java.lang.Math.max(int, int):int}
          ClspMth{java.lang.Math.max(float, float):float}
          ClspMth{java.lang.Math.max(long, long):long} */
        public long getAllocatedBandwidth() {
            long allocatableBandwidth = Math.max(0L, ((long) (((float) this.bandwidthMeter.getBitrateEstimate()) * this.bandwidthFraction)) - this.nonAllocatableBandwidth);
            if (this.allocationCheckpoints == null) {
                return allocatableBandwidth;
            }
            int nextIndex = 1;
            while (true) {
                long[][] jArr = this.allocationCheckpoints;
                if (nextIndex >= jArr.length - 1 || jArr[nextIndex][0] >= allocatableBandwidth) {
                    long[][] jArr2 = this.allocationCheckpoints;
                    long[] previous = jArr2[nextIndex - 1];
                    long[] next = jArr2[nextIndex];
                } else {
                    nextIndex++;
                }
            }
            long[][] jArr22 = this.allocationCheckpoints;
            long[] previous2 = jArr22[nextIndex - 1];
            long[] next2 = jArr22[nextIndex];
            return previous2[1] + ((long) (((float) (next2[1] - previous2[1])) * (((float) (allocatableBandwidth - previous2[0])) / ((float) (next2[0] - previous2[0])))));
        }

        /* access modifiers changed from: package-private */
        public void experimental_setNonAllocatableBandwidth(long nonAllocatableBandwidth2) {
            this.nonAllocatableBandwidth = nonAllocatableBandwidth2;
        }

        /* access modifiers changed from: package-private */
        public void experimental_setBandwidthAllocationCheckpoints(long[][] allocationCheckpoints2) {
            Assertions.checkArgument(allocationCheckpoints2.length >= 2);
            this.allocationCheckpoints = allocationCheckpoints2;
        }
    }
}
