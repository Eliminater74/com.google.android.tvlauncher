package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.MediaChunkListIterator;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Arrays;
import java.util.List;

public final class TrackSelectionUtil {

    public interface AdaptiveTrackSelectionFactory {
        TrackSelection createAdaptiveTrackSelection(TrackSelection.Definition definition);
    }

    private TrackSelectionUtil() {
    }

    public static TrackSelection[] createTrackSelectionsForDefinitions(TrackSelection.Definition[] definitions, AdaptiveTrackSelectionFactory adaptiveTrackSelectionFactory) {
        TrackSelection[] selections = new TrackSelection[definitions.length];
        boolean createdAdaptiveTrackSelection = false;
        for (int i = 0; i < definitions.length; i++) {
            TrackSelection.Definition definition = definitions[i];
            if (definition != null) {
                if (definition.tracks.length <= 1 || createdAdaptiveTrackSelection) {
                    selections[i] = new FixedTrackSelection(definition.group, definition.tracks[0], definition.reason, definition.data);
                } else {
                    createdAdaptiveTrackSelection = true;
                    selections[i] = adaptiveTrackSelectionFactory.createAdaptiveTrackSelection(definition);
                }
            }
        }
        return selections;
    }

    public static DefaultTrackSelector.Parameters updateParametersWithOverride(DefaultTrackSelector.Parameters parameters, int rendererIndex, TrackGroupArray trackGroupArray, boolean isDisabled, @Nullable DefaultTrackSelector.SelectionOverride override) {
        DefaultTrackSelector.ParametersBuilder builder = parameters.buildUpon().clearSelectionOverrides(rendererIndex).setRendererDisabled(rendererIndex, isDisabled);
        if (override != null) {
            builder.setSelectionOverride(rendererIndex, trackGroupArray, override);
        }
        return builder.build();
    }

    public static int getAverageBitrate(MediaChunkIterator iterator, long maxDurationUs) {
        long totalDurationUs = 0;
        long totalLength = 0;
        while (true) {
            if (!iterator.next()) {
                break;
            }
            long chunkLength = iterator.getDataSpec().length;
            if (chunkLength == -1) {
                break;
            }
            long chunkDurationUs = iterator.getChunkEndTimeUs() - iterator.getChunkStartTimeUs();
            if (totalDurationUs + chunkDurationUs >= maxDurationUs) {
                totalLength += ((maxDurationUs - totalDurationUs) * chunkLength) / chunkDurationUs;
                totalDurationUs = maxDurationUs;
                break;
            }
            totalDurationUs += chunkDurationUs;
            totalLength += chunkLength;
        }
        if (totalDurationUs == 0) {
            return -1;
        }
        return (int) (((8 * totalLength) * 1000000) / totalDurationUs);
    }

    @VisibleForTesting
    static int[] getBitratesUsingFutureInfo(MediaChunkIterator[] iterators, Format[] formats, long maxDurationUs, @Nullable int[] bitrates) {
        int trackCount = iterators.length;
        Assertions.checkArgument(trackCount == formats.length);
        if (trackCount == 0) {
            return new int[0];
        }
        if (bitrates == null) {
            bitrates = new int[trackCount];
        }
        if (maxDurationUs == 0) {
            Arrays.fill(bitrates, -1);
            return bitrates;
        }
        int[] formatBitrates = new int[trackCount];
        float[] bitrateRatios = new float[trackCount];
        boolean needEstimateBitrate = false;
        boolean canEstimateBitrate = false;
        for (int i = 0; i < trackCount; i++) {
            int bitrate = getAverageBitrate(iterators[i], maxDurationUs);
            if (bitrate != -1) {
                int formatBitrate = formats[i].bitrate;
                formatBitrates[i] = formatBitrate;
                if (formatBitrate != -1) {
                    bitrateRatios[i] = ((float) bitrate) / ((float) formatBitrate);
                    canEstimateBitrate = true;
                }
            } else {
                needEstimateBitrate = true;
                formatBitrates[i] = -1;
            }
            bitrates[i] = bitrate;
        }
        if (needEstimateBitrate && canEstimateBitrate) {
            estimateBitrates(bitrates, formats, formatBitrates, bitrateRatios);
        }
        return bitrates;
    }

    @VisibleForTesting
    static int[] getBitratesUsingPastInfo(List<? extends MediaChunk> queue, Format[] formats, long maxDurationUs, @Nullable int[] bitrates) {
        int queueAverageBitrate;
        int queueFormatBitrate;
        if (bitrates == null) {
            bitrates = new int[formats.length];
            Arrays.fill(bitrates, -1);
        }
        if (!(maxDurationUs == 0 || (queueAverageBitrate = getAverageQueueBitrate(queue, maxDurationUs)) == -1 || (queueFormatBitrate = ((MediaChunk) queue.get(queue.size() - 1)).trackFormat.bitrate) == -1)) {
            estimateBitrates(bitrates, formats, new int[]{queueFormatBitrate}, new float[]{((float) queueAverageBitrate) / ((float) queueFormatBitrate)});
        }
        return bitrates;
    }

    public static int[] getBitratesUsingPastAndFutureInfo(Format[] formats, List<? extends MediaChunk> queue, long maxPastDurationUs, MediaChunkIterator[] iterators, long maxFutureDurationUs, boolean useFormatBitrateAsLowerBound, @Nullable int[] bitrates) {
        int[] bitrates2 = getBitratesUsingFutureInfo(iterators, formats, maxFutureDurationUs, bitrates);
        getBitratesUsingPastInfo(queue, formats, maxPastDurationUs, bitrates2);
        for (int i = 0; i < bitrates2.length; i++) {
            int bitrate = bitrates2[i];
            if (bitrate == -1 || (useFormatBitrateAsLowerBound && formats[i].bitrate != -1 && bitrate < formats[i].bitrate)) {
                bitrates2[i] = formats[i].bitrate;
            }
        }
        return bitrates2;
    }

    public static int[] getFormatBitrates(Format[] formats, @Nullable int[] bitrates) {
        int trackCount = formats.length;
        if (bitrates == null) {
            bitrates = new int[trackCount];
        }
        for (int i = 0; i < trackCount; i++) {
            bitrates[i] = formats[i].bitrate;
        }
        return bitrates;
    }

    private static void estimateBitrates(int[] bitrates, Format[] formats, int[] referenceBitrates, float[] referenceBitrateRatios) {
        int formatBitrate;
        for (int i = 0; i < bitrates.length; i++) {
            if (bitrates[i] == -1 && (formatBitrate = formats[i].bitrate) != -1) {
                bitrates[i] = (int) (referenceBitrateRatios[getClosestBitrateIndex(formatBitrate, referenceBitrates)] * ((float) formatBitrate));
            }
        }
    }

    private static int getAverageQueueBitrate(List<? extends MediaChunk> queue, long maxDurationUs) {
        if (queue.isEmpty()) {
            return -1;
        }
        return getAverageBitrate(new MediaChunkListIterator(getSingleFormatSubQueue(queue), true), maxDurationUs);
    }

    private static List<? extends MediaChunk> getSingleFormatSubQueue(List<? extends MediaChunk> queue) {
        Format queueFormat = ((MediaChunk) queue.get(queue.size() - 1)).trackFormat;
        int queueSize = queue.size();
        for (int i = queueSize - 2; i >= 0; i--) {
            if (!((MediaChunk) queue.get(i)).trackFormat.equals(queueFormat)) {
                return queue.subList(i + 1, queueSize);
            }
        }
        return queue;
    }

    private static int getClosestBitrateIndex(int formatBitrate, int[] formatBitrates) {
        int distance;
        int closestDistance = Integer.MAX_VALUE;
        int closestFormat = -1;
        for (int j = 0; j < formatBitrates.length; j++) {
            if (formatBitrates[j] != -1 && (distance = Math.abs(formatBitrates[j] - formatBitrate)) < closestDistance) {
                closestDistance = distance;
                closestFormat = j;
            }
        }
        return closestFormat;
    }
}
