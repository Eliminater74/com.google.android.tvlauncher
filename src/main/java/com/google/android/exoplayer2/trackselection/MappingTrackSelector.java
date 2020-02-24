package com.google.android.exoplayer2.trackselection;

import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public abstract class MappingTrackSelector extends TrackSelector {
    @Nullable
    private MappedTrackInfo currentMappedTrackInfo;

    /* access modifiers changed from: protected */
    public abstract Pair<RendererConfiguration[], TrackSelection[]> selectTracks(MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2) throws ExoPlaybackException;

    public static final class MappedTrackInfo {
        public static final int RENDERER_SUPPORT_EXCEEDS_CAPABILITIES_TRACKS = 2;
        public static final int RENDERER_SUPPORT_NO_TRACKS = 0;
        public static final int RENDERER_SUPPORT_PLAYABLE_TRACKS = 3;
        public static final int RENDERER_SUPPORT_UNSUPPORTED_TRACKS = 1;
        @Deprecated
        public final int length = this.rendererCount;
        private final int rendererCount;
        private final int[][][] rendererFormatSupports;
        private final int[] rendererMixedMimeTypeAdaptiveSupports;
        private final TrackGroupArray[] rendererTrackGroups;
        private final int[] rendererTrackTypes;
        private final TrackGroupArray unmappedTrackGroups;

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        @interface RendererSupport {
        }

        MappedTrackInfo(int[] rendererTrackTypes2, TrackGroupArray[] rendererTrackGroups2, int[] rendererMixedMimeTypeAdaptiveSupports2, int[][][] rendererFormatSupports2, TrackGroupArray unmappedTrackGroups2) {
            this.rendererTrackTypes = rendererTrackTypes2;
            this.rendererTrackGroups = rendererTrackGroups2;
            this.rendererFormatSupports = rendererFormatSupports2;
            this.rendererMixedMimeTypeAdaptiveSupports = rendererMixedMimeTypeAdaptiveSupports2;
            this.unmappedTrackGroups = unmappedTrackGroups2;
            this.rendererCount = rendererTrackTypes2.length;
        }

        public int getRendererCount() {
            return this.rendererCount;
        }

        public int getRendererType(int rendererIndex) {
            return this.rendererTrackTypes[rendererIndex];
        }

        public TrackGroupArray getTrackGroups(int rendererIndex) {
            return this.rendererTrackGroups[rendererIndex];
        }

        public int getRendererSupport(int rendererIndex) {
            int trackRendererSupport;
            int bestRendererSupport = 0;
            int[][] rendererFormatSupport = this.rendererFormatSupports[rendererIndex];
            for (int i = 0; i < rendererFormatSupport.length; i++) {
                for (int i2 : rendererFormatSupport[i]) {
                    int i3 = i2 & 7;
                    if (i3 == 3) {
                        trackRendererSupport = 2;
                    } else if (i3 == 4) {
                        return 3;
                    } else {
                        trackRendererSupport = 1;
                    }
                    bestRendererSupport = Math.max(bestRendererSupport, trackRendererSupport);
                }
            }
            return bestRendererSupport;
        }

        @Deprecated
        public int getTrackTypeRendererSupport(int trackType) {
            return getTypeSupport(trackType);
        }

        public int getTypeSupport(int trackType) {
            int bestRendererSupport = 0;
            for (int i = 0; i < this.rendererCount; i++) {
                if (this.rendererTrackTypes[i] == trackType) {
                    bestRendererSupport = Math.max(bestRendererSupport, getRendererSupport(i));
                }
            }
            return bestRendererSupport;
        }

        @Deprecated
        public int getTrackFormatSupport(int rendererIndex, int groupIndex, int trackIndex) {
            return getTrackSupport(rendererIndex, groupIndex, trackIndex);
        }

        public int getTrackSupport(int rendererIndex, int groupIndex, int trackIndex) {
            return this.rendererFormatSupports[rendererIndex][groupIndex][trackIndex] & 7;
        }

        public int getAdaptiveSupport(int rendererIndex, int groupIndex, boolean includeCapabilitiesExceededTracks) {
            int trackCount = this.rendererTrackGroups[rendererIndex].get(groupIndex).length;
            int[] trackIndices = new int[trackCount];
            int trackIndexCount = 0;
            for (int i = 0; i < trackCount; i++) {
                int fixedSupport = getTrackSupport(rendererIndex, groupIndex, i);
                if (fixedSupport == 4 || (includeCapabilitiesExceededTracks && fixedSupport == 3)) {
                    trackIndices[trackIndexCount] = i;
                    trackIndexCount++;
                }
            }
            return getAdaptiveSupport(rendererIndex, groupIndex, Arrays.copyOf(trackIndices, trackIndexCount));
        }

        public int getAdaptiveSupport(int rendererIndex, int groupIndex, int[] trackIndices) {
            int handledTrackCount = 0;
            int adaptiveSupport = 16;
            boolean multipleMimeTypes = false;
            String firstSampleMimeType = null;
            int i = 0;
            while (i < trackIndices.length) {
                String sampleMimeType = this.rendererTrackGroups[rendererIndex].get(groupIndex).getFormat(trackIndices[i]).sampleMimeType;
                int handledTrackCount2 = handledTrackCount + 1;
                if (handledTrackCount == 0) {
                    firstSampleMimeType = sampleMimeType;
                } else {
                    multipleMimeTypes = (!Util.areEqual(firstSampleMimeType, sampleMimeType)) | multipleMimeTypes;
                }
                adaptiveSupport = Math.min(adaptiveSupport, this.rendererFormatSupports[rendererIndex][groupIndex][i] & 24);
                i++;
                handledTrackCount = handledTrackCount2;
            }
            if (multipleMimeTypes) {
                return Math.min(adaptiveSupport, this.rendererMixedMimeTypeAdaptiveSupports[rendererIndex]);
            }
            return adaptiveSupport;
        }

        @Deprecated
        public TrackGroupArray getUnassociatedTrackGroups() {
            return getUnmappedTrackGroups();
        }

        public TrackGroupArray getUnmappedTrackGroups() {
            return this.unmappedTrackGroups;
        }
    }

    @Nullable
    public final MappedTrackInfo getCurrentMappedTrackInfo() {
        return this.currentMappedTrackInfo;
    }

    public final void onSelectionActivated(Object info) {
        this.currentMappedTrackInfo = (MappedTrackInfo) info;
    }

    public final TrackSelectorResult selectTracks(RendererCapabilities[] rendererCapabilities, TrackGroupArray trackGroups, MediaSource.MediaPeriodId periodId, Timeline timeline) throws ExoPlaybackException {
        RendererCapabilities[] rendererCapabilitiesArr = rendererCapabilities;
        TrackGroupArray trackGroupArray = trackGroups;
        int[] rendererTrackGroupCounts = new int[(rendererCapabilitiesArr.length + 1)];
        TrackGroup[][] rendererTrackGroups = new TrackGroup[(rendererCapabilitiesArr.length + 1)][];
        int[][][] rendererFormatSupports = new int[(rendererCapabilitiesArr.length + 1)][][];
        for (int i = 0; i < rendererTrackGroups.length; i++) {
            rendererTrackGroups[i] = new TrackGroup[trackGroupArray.length];
            rendererFormatSupports[i] = new int[trackGroupArray.length][];
        }
        int[] rendererMixedMimeTypeAdaptationSupports = getMixedMimeTypeAdaptationSupports(rendererCapabilities);
        for (int groupIndex = 0; groupIndex < trackGroupArray.length; groupIndex++) {
            TrackGroup group = trackGroupArray.get(groupIndex);
            int rendererIndex = findRenderer(rendererCapabilitiesArr, group);
            int[] rendererFormatSupport = rendererIndex == rendererCapabilitiesArr.length ? new int[group.length] : getFormatSupport(rendererCapabilitiesArr[rendererIndex], group);
            int rendererTrackGroupCount = rendererTrackGroupCounts[rendererIndex];
            rendererTrackGroups[rendererIndex][rendererTrackGroupCount] = group;
            rendererFormatSupports[rendererIndex][rendererTrackGroupCount] = rendererFormatSupport;
            rendererTrackGroupCounts[rendererIndex] = rendererTrackGroupCounts[rendererIndex] + 1;
        }
        TrackGroupArray[] rendererTrackGroupArrays = new TrackGroupArray[rendererCapabilitiesArr.length];
        int[] rendererTrackTypes = new int[rendererCapabilitiesArr.length];
        for (int i2 = 0; i2 < rendererCapabilitiesArr.length; i2++) {
            int rendererTrackGroupCount2 = rendererTrackGroupCounts[i2];
            rendererTrackGroupArrays[i2] = new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(rendererTrackGroups[i2], rendererTrackGroupCount2));
            rendererFormatSupports[i2] = (int[][]) Util.nullSafeArrayCopy(rendererFormatSupports[i2], rendererTrackGroupCount2);
            rendererTrackTypes[i2] = rendererCapabilitiesArr[i2].getTrackType();
        }
        MappedTrackInfo mappedTrackInfo = new MappedTrackInfo(rendererTrackTypes, rendererTrackGroupArrays, rendererMixedMimeTypeAdaptationSupports, rendererFormatSupports, new TrackGroupArray((TrackGroup[]) Util.nullSafeArrayCopy(rendererTrackGroups[rendererCapabilitiesArr.length], rendererTrackGroupCounts[rendererCapabilitiesArr.length])));
        Pair<RendererConfiguration[], TrackSelection[]> result = selectTracks(mappedTrackInfo, rendererFormatSupports, rendererMixedMimeTypeAdaptationSupports);
        return new TrackSelectorResult((RendererConfiguration[]) result.first, (TrackSelection[]) result.second, mappedTrackInfo);
    }

    private static int findRenderer(RendererCapabilities[] rendererCapabilities, TrackGroup group) throws ExoPlaybackException {
        int bestRendererIndex = rendererCapabilities.length;
        int bestFormatSupportLevel = 0;
        for (int rendererIndex = 0; rendererIndex < rendererCapabilities.length; rendererIndex++) {
            RendererCapabilities rendererCapability = rendererCapabilities[rendererIndex];
            for (int trackIndex = 0; trackIndex < group.length; trackIndex++) {
                int formatSupportLevel = rendererCapability.supportsFormat(group.getFormat(trackIndex)) & 7;
                if (formatSupportLevel > bestFormatSupportLevel) {
                    bestRendererIndex = rendererIndex;
                    bestFormatSupportLevel = formatSupportLevel;
                    if (bestFormatSupportLevel == 4) {
                        return bestRendererIndex;
                    }
                }
            }
        }
        return bestRendererIndex;
    }

    private static int[] getFormatSupport(RendererCapabilities rendererCapabilities, TrackGroup group) throws ExoPlaybackException {
        int[] formatSupport = new int[group.length];
        for (int i = 0; i < group.length; i++) {
            formatSupport[i] = rendererCapabilities.supportsFormat(group.getFormat(i));
        }
        return formatSupport;
    }

    private static int[] getMixedMimeTypeAdaptationSupports(RendererCapabilities[] rendererCapabilities) throws ExoPlaybackException {
        int[] mixedMimeTypeAdaptationSupport = new int[rendererCapabilities.length];
        for (int i = 0; i < mixedMimeTypeAdaptationSupport.length; i++) {
            mixedMimeTypeAdaptationSupport[i] = rendererCapabilities[i].supportsMixedMimeTypeAdaptation();
        }
        return mixedMimeTypeAdaptationSupport;
    }
}
