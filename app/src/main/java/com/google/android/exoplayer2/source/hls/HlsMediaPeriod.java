package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper;
import com.google.android.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class HlsMediaPeriod implements MediaPeriod, HlsSampleStreamWrapper.Callback, HlsPlaylistTracker.PlaylistEventListener {
    private final Allocator allocator;
    private final boolean allowChunklessPreparation;
    @Nullable
    private MediaPeriod.Callback callback;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final HlsDataSourceFactory dataSourceFactory;
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final HlsExtractorFactory extractorFactory;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private int[][] manifestUrlIndicesPerWrapper = new int[0][];
    @Nullable
    private final TransferListener mediaTransferListener;
    private boolean notifiedReadingStarted;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap<>();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private TrackGroupArray trackGroups;
    private final boolean useSessionKeys;

    public HlsMediaPeriod(HlsExtractorFactory extractorFactory2, HlsPlaylistTracker playlistTracker2, HlsDataSourceFactory dataSourceFactory2, @Nullable TransferListener mediaTransferListener2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2, Allocator allocator2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, boolean allowChunklessPreparation2, boolean useSessionKeys2) {
        this.extractorFactory = extractorFactory2;
        this.playlistTracker = playlistTracker2;
        this.dataSourceFactory = dataSourceFactory2;
        this.mediaTransferListener = mediaTransferListener2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.allocator = allocator2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.allowChunklessPreparation = allowChunklessPreparation2;
        this.useSessionKeys = useSessionKeys2;
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory2.createCompositeSequenceableLoader(new SequenceableLoader[0]);
        eventDispatcher2.mediaPeriodCreated();
    }

    public void release() {
        this.playlistTracker.removeListener(this);
        for (HlsSampleStreamWrapper sampleStreamWrapper : this.sampleStreamWrappers) {
            sampleStreamWrapper.release();
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void prepare(MediaPeriod.Callback callback2, long positionUs) {
        this.callback = callback2;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(positionUs);
    }

    public void maybeThrowPrepareError() throws IOException {
        for (HlsSampleStreamWrapper sampleStreamWrapper : this.sampleStreamWrappers) {
            sampleStreamWrapper.maybeThrowPrepareError();
        }
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public List<StreamKey> getStreamKeys(List<TrackSelection> trackSelections) {
        TrackGroupArray mainWrapperTrackGroups;
        int[] mainWrapperVariantIndices;
        int mainWrapperPrimaryGroupIndex;
        int audioWrapperOffset;
        boolean hasVariants;
        int groupIndexType;
        HlsMediaPeriod hlsMediaPeriod = this;
        HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(hlsMediaPeriod.playlistTracker.getMasterPlaylist());
        boolean hasVariants2 = !masterPlaylist.variants.isEmpty();
        int audioWrapperOffset2 = hasVariants2 ? 1 : 0;
        int subtitleWrapperOffset = hlsMediaPeriod.sampleStreamWrappers.length - masterPlaylist.subtitles.size();
        if (hasVariants2) {
            HlsSampleStreamWrapper mainWrapper = hlsMediaPeriod.sampleStreamWrappers[0];
            mainWrapperVariantIndices = hlsMediaPeriod.manifestUrlIndicesPerWrapper[0];
            mainWrapperTrackGroups = mainWrapper.getTrackGroups();
            mainWrapperPrimaryGroupIndex = mainWrapper.getPrimaryTrackGroupIndex();
        } else {
            mainWrapperVariantIndices = new int[0];
            mainWrapperTrackGroups = TrackGroupArray.EMPTY;
            mainWrapperPrimaryGroupIndex = 0;
        }
        List<StreamKey> streamKeys = new ArrayList<>();
        boolean needsPrimaryTrackGroupSelection = false;
        boolean i = false;
        for (TrackSelection trackSelection : trackSelections) {
            TrackGroup trackSelectionGroup = trackSelection.getTrackGroup();
            int mainWrapperTrackGroupIndex = mainWrapperTrackGroups.indexOf(trackSelectionGroup);
            if (mainWrapperTrackGroupIndex == -1) {
                hasVariants = hasVariants2;
                int i2 = audioWrapperOffset2;
                while (true) {
                    HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = hlsMediaPeriod.sampleStreamWrappers;
                    if (i2 >= hlsSampleStreamWrapperArr.length) {
                        audioWrapperOffset = audioWrapperOffset2;
                        break;
                    } else if (hlsSampleStreamWrapperArr[i2].getTrackGroups().indexOf(trackSelectionGroup) != -1) {
                        if (i2 < subtitleWrapperOffset) {
                            groupIndexType = 1;
                        } else {
                            groupIndexType = 2;
                        }
                        int[] selectedWrapperUrlIndices = hlsMediaPeriod.manifestUrlIndicesPerWrapper[i2];
                        int trackIndex = 0;
                        while (true) {
                            audioWrapperOffset = audioWrapperOffset2;
                            if (trackIndex >= trackSelection.length()) {
                                break;
                            }
                            streamKeys.add(new StreamKey(groupIndexType, selectedWrapperUrlIndices[trackSelection.getIndexInTrackGroup(trackIndex)]));
                            trackIndex++;
                            audioWrapperOffset2 = audioWrapperOffset;
                            selectedWrapperUrlIndices = selectedWrapperUrlIndices;
                        }
                    } else {
                        i2++;
                        hlsMediaPeriod = this;
                    }
                }
            } else if (mainWrapperTrackGroupIndex == mainWrapperPrimaryGroupIndex) {
                boolean hasPrimaryTrackGroupSelection = true;
                int i3 = 0;
                while (true) {
                    hasVariants = hasVariants2;
                    if (i3 >= trackSelection.length()) {
                        break;
                    }
                    streamKeys.add(new StreamKey(0, mainWrapperVariantIndices[trackSelection.getIndexInTrackGroup(i3)]));
                    i3++;
                    hasVariants2 = hasVariants;
                    mainWrapperTrackGroupIndex = mainWrapperTrackGroupIndex;
                    hasPrimaryTrackGroupSelection = hasPrimaryTrackGroupSelection;
                }
                audioWrapperOffset = audioWrapperOffset2;
                i = hasPrimaryTrackGroupSelection;
            } else {
                hasVariants = hasVariants2;
                needsPrimaryTrackGroupSelection = true;
                audioWrapperOffset = audioWrapperOffset2;
            }
            hlsMediaPeriod = this;
            hasVariants2 = hasVariants;
            audioWrapperOffset2 = audioWrapperOffset;
        }
        if (needsPrimaryTrackGroupSelection && !i) {
            int lowestBitrateIndex = mainWrapperVariantIndices[0];
            int lowestBitrate = masterPlaylist.variants.get(mainWrapperVariantIndices[0]).format.bitrate;
            for (int i4 = 1; i4 < mainWrapperVariantIndices.length; i4++) {
                int variantBitrate = masterPlaylist.variants.get(mainWrapperVariantIndices[i4]).format.bitrate;
                if (variantBitrate < lowestBitrate) {
                    lowestBitrate = variantBitrate;
                    lowestBitrateIndex = mainWrapperVariantIndices[i4];
                }
            }
            streamKeys.add(new StreamKey(0, lowestBitrateIndex));
        }
        return streamKeys;
    }

    /* JADX INFO: Multiple debug info for r2v4 int: [D('sampleStreamWrapper' com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper), D('newEnabledSampleStreamWrapperCount' int)] */
    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int i;
        TrackSelection[] trackSelectionArr = selections;
        SampleStream[] sampleStreamArr = streams;
        int[] streamChildIndices = new int[trackSelectionArr.length];
        int[] selectionChildIndices = new int[trackSelectionArr.length];
        for (int i2 = 0; i2 < trackSelectionArr.length; i2++) {
            if (sampleStreamArr[i2] == null) {
                i = -1;
            } else {
                i = this.streamWrapperIndices.get(sampleStreamArr[i2]).intValue();
            }
            streamChildIndices[i2] = i;
            selectionChildIndices[i2] = -1;
            if (trackSelectionArr[i2] != null) {
                TrackGroup trackGroup = trackSelectionArr[i2].getTrackGroup();
                int j = 0;
                while (true) {
                    HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
                    if (j >= hlsSampleStreamWrapperArr.length) {
                        break;
                    } else if (hlsSampleStreamWrapperArr[j].getTrackGroups().indexOf(trackGroup) != -1) {
                        selectionChildIndices[i2] = j;
                        break;
                    } else {
                        j++;
                    }
                }
            }
        }
        this.streamWrapperIndices.clear();
        SampleStream[] newStreams = new SampleStream[trackSelectionArr.length];
        SampleStream[] childStreams = new SampleStream[trackSelectionArr.length];
        TrackSelection[] childSelections = new TrackSelection[trackSelectionArr.length];
        HlsSampleStreamWrapper[] newEnabledSampleStreamWrappers = new HlsSampleStreamWrapper[this.sampleStreamWrappers.length];
        boolean forceReset = false;
        int newEnabledSampleStreamWrapperCount = 0;
        int i3 = 0;
        while (i3 < this.sampleStreamWrappers.length) {
            for (int j2 = 0; j2 < trackSelectionArr.length; j2++) {
                TrackSelection trackSelection = null;
                childStreams[j2] = streamChildIndices[j2] == i3 ? sampleStreamArr[j2] : null;
                if (selectionChildIndices[j2] == i3) {
                    trackSelection = trackSelectionArr[j2];
                }
                childSelections[j2] = trackSelection;
            }
            HlsSampleStreamWrapper sampleStreamWrapper = this.sampleStreamWrappers[i3];
            HlsSampleStreamWrapper sampleStreamWrapper2 = sampleStreamWrapper;
            HlsSampleStreamWrapper[] newEnabledSampleStreamWrappers2 = newEnabledSampleStreamWrappers;
            int newEnabledSampleStreamWrapperCount2 = newEnabledSampleStreamWrapperCount;
            TrackSelection[] childSelections2 = childSelections;
            boolean wasReset = sampleStreamWrapper.selectTracks(childSelections, mayRetainStreamFlags, childStreams, streamResetFlags, positionUs, forceReset);
            boolean wrapperEnabled = false;
            int j3 = 0;
            while (true) {
                boolean z = true;
                if (j3 >= trackSelectionArr.length) {
                    break;
                }
                if (selectionChildIndices[j3] == i3) {
                    if (childStreams[j3] == null) {
                        z = false;
                    }
                    Assertions.checkState(z);
                    newStreams[j3] = childStreams[j3];
                    wrapperEnabled = true;
                    this.streamWrapperIndices.put(childStreams[j3], Integer.valueOf(i3));
                } else if (streamChildIndices[j3] == i3) {
                    if (childStreams[j3] != null) {
                        z = false;
                    }
                    Assertions.checkState(z);
                }
                j3++;
            }
            if (wrapperEnabled) {
                newEnabledSampleStreamWrappers2[newEnabledSampleStreamWrapperCount2] = sampleStreamWrapper2;
                newEnabledSampleStreamWrapperCount = newEnabledSampleStreamWrapperCount2 + 1;
                if (newEnabledSampleStreamWrapperCount2 == 0) {
                    HlsSampleStreamWrapper sampleStreamWrapper3 = sampleStreamWrapper2;
                    sampleStreamWrapper3.setIsTimestampMaster(true);
                    if (!wasReset) {
                        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = this.enabledSampleStreamWrappers;
                        if (hlsSampleStreamWrapperArr2.length != 0 && sampleStreamWrapper3 == hlsSampleStreamWrapperArr2[0]) {
                        }
                    }
                    this.timestampAdjusterProvider.reset();
                    forceReset = true;
                } else {
                    sampleStreamWrapper2.setIsTimestampMaster(false);
                }
            } else {
                newEnabledSampleStreamWrapperCount = newEnabledSampleStreamWrapperCount2;
            }
            i3++;
            sampleStreamArr = streams;
            newEnabledSampleStreamWrappers = newEnabledSampleStreamWrappers2;
            childSelections = childSelections2;
        }
        System.arraycopy(newStreams, 0, streams, 0, newStreams.length);
        this.enabledSampleStreamWrappers = (HlsSampleStreamWrapper[]) Arrays.copyOf(newEnabledSampleStreamWrappers, newEnabledSampleStreamWrapperCount);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.enabledSampleStreamWrappers);
        return positionUs;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (HlsSampleStreamWrapper sampleStreamWrapper : this.enabledSampleStreamWrappers) {
            sampleStreamWrapper.discardBuffer(positionUs, toKeyframe);
        }
    }

    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    public boolean continueLoading(long positionUs) {
        if (this.trackGroups != null) {
            return this.compositeSequenceableLoader.continueLoading(positionUs);
        }
        for (HlsSampleStreamWrapper wrapper : this.sampleStreamWrappers) {
            wrapper.continuePreparing();
        }
        return false;
    }

    public long getNextLoadPositionUs() {
        return this.compositeSequenceableLoader.getNextLoadPositionUs();
    }

    public long readDiscontinuity() {
        if (this.notifiedReadingStarted) {
            return C0841C.TIME_UNSET;
        }
        this.eventDispatcher.readingStarted();
        this.notifiedReadingStarted = true;
        return C0841C.TIME_UNSET;
    }

    public long getBufferedPositionUs() {
        return this.compositeSequenceableLoader.getBufferedPositionUs();
    }

    public long seekToUs(long positionUs) {
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.enabledSampleStreamWrappers;
        if (hlsSampleStreamWrapperArr.length > 0) {
            boolean forceReset = hlsSampleStreamWrapperArr[0].seekToUs(positionUs, false);
            int i = 1;
            while (true) {
                HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr2 = this.enabledSampleStreamWrappers;
                if (i >= hlsSampleStreamWrapperArr2.length) {
                    break;
                }
                hlsSampleStreamWrapperArr2[i].seekToUs(positionUs, forceReset);
                i++;
            }
            if (forceReset) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return positionUs;
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        return positionUs;
    }

    public void onPrepared() {
        int i = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i;
        if (i <= 0) {
            int totalTrackGroupCount = 0;
            for (HlsSampleStreamWrapper sampleStreamWrapper : this.sampleStreamWrappers) {
                totalTrackGroupCount += sampleStreamWrapper.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArray = new TrackGroup[totalTrackGroupCount];
            int trackGroupIndex = 0;
            for (HlsSampleStreamWrapper sampleStreamWrapper2 : this.sampleStreamWrappers) {
                int wrapperTrackGroupCount = sampleStreamWrapper2.getTrackGroups().length;
                int j = 0;
                while (j < wrapperTrackGroupCount) {
                    trackGroupArray[trackGroupIndex] = sampleStreamWrapper2.getTrackGroups().get(j);
                    j++;
                    trackGroupIndex++;
                }
            }
            this.trackGroups = new TrackGroupArray(trackGroupArray);
            this.callback.onPrepared(this);
        }
    }

    public void onPlaylistRefreshRequired(Uri url) {
        this.playlistTracker.refreshPlaylist(url);
    }

    public void onContinueLoadingRequested(HlsSampleStreamWrapper sampleStreamWrapper) {
        this.callback.onContinueLoadingRequested(this);
    }

    public void onPlaylistChanged() {
        this.callback.onContinueLoadingRequested(this);
    }

    public boolean onPlaylistError(Uri url, long blacklistDurationMs) {
        boolean noBlacklistingFailure = true;
        for (HlsSampleStreamWrapper streamWrapper : this.sampleStreamWrappers) {
            noBlacklistingFailure &= streamWrapper.onPlaylistError(url, blacklistDurationMs);
        }
        this.callback.onContinueLoadingRequested(this);
        return noBlacklistingFailure;
    }

    private void buildAndPrepareSampleStreamWrappers(long positionUs) {
        Map<String, DrmInitData> overridingDrmInitData;
        HlsMasterPlaylist masterPlaylist = (HlsMasterPlaylist) Assertions.checkNotNull(this.playlistTracker.getMasterPlaylist());
        if (this.useSessionKeys) {
            overridingDrmInitData = deriveOverridingDrmInitData(masterPlaylist.sessionKeyDrmInitData);
        } else {
            overridingDrmInitData = Collections.emptyMap();
        }
        boolean hasVariants = !masterPlaylist.variants.isEmpty();
        List<HlsMasterPlaylist.Rendition> audioRenditions = masterPlaylist.audios;
        List<HlsMasterPlaylist.Rendition> subtitleRenditions = masterPlaylist.subtitles;
        this.pendingPrepareCount = 0;
        List<HlsMasterPlaylist.Rendition> sampleStreamWrappers2 = new ArrayList<>();
        List<HlsMasterPlaylist.Rendition> manifestUrlIndicesPerWrapper2 = new ArrayList<>();
        if (hasVariants) {
            buildAndPrepareMainSampleStreamWrapper(masterPlaylist, positionUs, sampleStreamWrappers2, manifestUrlIndicesPerWrapper2, overridingDrmInitData);
        }
        List<HlsMasterPlaylist.Rendition> sampleStreamWrappers3 = sampleStreamWrappers2;
        List<HlsMasterPlaylist.Rendition> manifestUrlIndicesPerWrapper3 = manifestUrlIndicesPerWrapper2;
        List<HlsMasterPlaylist.Rendition> subtitleRenditions2 = subtitleRenditions;
        buildAndPrepareAudioSampleStreamWrappers(positionUs, audioRenditions, sampleStreamWrappers3, manifestUrlIndicesPerWrapper3, overridingDrmInitData);
        int i = 0;
        while (i < subtitleRenditions2.size()) {
            HlsMasterPlaylist.Rendition subtitleRendition = subtitleRenditions2.get(i);
            int i2 = i;
            HlsSampleStreamWrapper sampleStreamWrapper = buildSampleStreamWrapper(3, new Uri[]{subtitleRendition.url}, new Format[]{subtitleRendition.format}, null, Collections.emptyList(), overridingDrmInitData, positionUs);
            manifestUrlIndicesPerWrapper3.add(new int[]{i2});
            sampleStreamWrappers3.add(sampleStreamWrapper);
            sampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray(new TrackGroup(subtitleRendition.format)), 0, TrackGroupArray.EMPTY);
            i = i2 + 1;
            masterPlaylist = masterPlaylist;
        }
        this.sampleStreamWrappers = (HlsSampleStreamWrapper[]) sampleStreamWrappers3.toArray(new HlsSampleStreamWrapper[0]);
        this.manifestUrlIndicesPerWrapper = (int[][]) manifestUrlIndicesPerWrapper3.toArray(new int[0][]);
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
        this.pendingPrepareCount = hlsSampleStreamWrapperArr.length;
        hlsSampleStreamWrapperArr[0].setIsTimestampMaster(true);
        for (HlsSampleStreamWrapper sampleStreamWrapper2 : this.sampleStreamWrappers) {
            sampleStreamWrapper2.continuePreparing();
        }
        this.enabledSampleStreamWrappers = this.sampleStreamWrappers;
    }

    /* JADX INFO: Multiple debug info for r1v10 com.google.android.exoplayer2.Format[]: [D('videoFormats' com.google.android.exoplayer2.Format[]), D('variantTypes' int[])] */
    /* JADX INFO: Multiple debug info for r1v11 java.util.List<com.google.android.exoplayer2.Format>: [D('videoFormats' com.google.android.exoplayer2.Format[]), D('ccFormats' java.util.List<com.google.android.exoplayer2.Format>)] */
    private void buildAndPrepareMainSampleStreamWrapper(HlsMasterPlaylist masterPlaylist, long positionUs, List<HlsSampleStreamWrapper> sampleStreamWrappers2, List<int[]> manifestUrlIndicesPerWrapper2, Map<String, DrmInitData> overridingDrmInitData) {
        HlsMasterPlaylist hlsMasterPlaylist = masterPlaylist;
        int[] variantTypes = new int[hlsMasterPlaylist.variants.size()];
        int videoVariantCount = 0;
        int audioVariantCount = 0;
        for (int i = 0; i < hlsMasterPlaylist.variants.size(); i++) {
            Format format = hlsMasterPlaylist.variants.get(i).format;
            if (format.height > 0 || Util.getCodecsOfType(format.codecs, 2) != null) {
                variantTypes[i] = 2;
                videoVariantCount++;
            } else if (Util.getCodecsOfType(format.codecs, 1) != null) {
                variantTypes[i] = 1;
                audioVariantCount++;
            } else {
                variantTypes[i] = -1;
            }
        }
        boolean useVideoVariantsOnly = false;
        boolean useNonAudioVariantsOnly = false;
        int selectedVariantsCount = variantTypes.length;
        if (videoVariantCount > 0) {
            useVideoVariantsOnly = true;
            selectedVariantsCount = videoVariantCount;
        } else if (audioVariantCount < variantTypes.length) {
            useNonAudioVariantsOnly = true;
            selectedVariantsCount = variantTypes.length - audioVariantCount;
        }
        Uri[] selectedPlaylistUrls = new Uri[selectedVariantsCount];
        Format[] selectedPlaylistFormats = new Format[selectedVariantsCount];
        int[] selectedVariantIndices = new int[selectedVariantsCount];
        int outIndex = 0;
        for (int i2 = 0; i2 < hlsMasterPlaylist.variants.size(); i2++) {
            if ((!useVideoVariantsOnly || variantTypes[i2] == 2) && (!useNonAudioVariantsOnly || variantTypes[i2] != 1)) {
                HlsMasterPlaylist.Variant variant = hlsMasterPlaylist.variants.get(i2);
                selectedPlaylistUrls[outIndex] = variant.url;
                selectedPlaylistFormats[outIndex] = variant.format;
                selectedVariantIndices[outIndex] = i2;
                outIndex++;
            }
        }
        String codecs = selectedPlaylistFormats[0].codecs;
        Format[] selectedPlaylistFormats2 = selectedPlaylistFormats;
        HlsSampleStreamWrapper sampleStreamWrapper = buildSampleStreamWrapper(0, selectedPlaylistUrls, selectedPlaylistFormats, hlsMasterPlaylist.muxedAudioFormat, hlsMasterPlaylist.muxedCaptionFormats, overridingDrmInitData, positionUs);
        sampleStreamWrappers2.add(sampleStreamWrapper);
        manifestUrlIndicesPerWrapper2.add(selectedVariantIndices);
        if (this.allowChunklessPreparation && codecs != null) {
            boolean variantsContainVideoCodecs = Util.getCodecsOfType(codecs, 2) != null;
            boolean variantsContainAudioCodecs = Util.getCodecsOfType(codecs, 1) != null;
            List<TrackGroup> muxedTrackGroups = new ArrayList<>();
            if (variantsContainVideoCodecs) {
                Format[] videoFormats = new Format[selectedVariantsCount];
                int i3 = 0;
                while (true) {
                    int audioVariantCount2 = audioVariantCount;
                    if (i3 >= videoFormats.length) {
                        break;
                    }
                    videoFormats[i3] = deriveVideoFormat(selectedPlaylistFormats2[i3]);
                    i3++;
                    audioVariantCount = audioVariantCount2;
                }
                muxedTrackGroups.add(new TrackGroup(videoFormats));
                if (variantsContainAudioCodecs) {
                    if (hlsMasterPlaylist.muxedAudioFormat != null || hlsMasterPlaylist.audios.isEmpty()) {
                        muxedTrackGroups.add(new TrackGroup(deriveAudioFormat(selectedPlaylistFormats2[0], hlsMasterPlaylist.muxedAudioFormat, false)));
                    }
                }
                List<Format> ccFormats = hlsMasterPlaylist.muxedCaptionFormats;
                if (ccFormats != null) {
                    for (int i4 = 0; i4 < ccFormats.size(); i4++) {
                        muxedTrackGroups.add(new TrackGroup(ccFormats.get(i4)));
                    }
                }
            } else if (variantsContainAudioCodecs) {
                Format[] audioFormats = new Format[selectedVariantsCount];
                for (int i5 = 0; i5 < audioFormats.length; i5++) {
                    audioFormats[i5] = deriveAudioFormat(selectedPlaylistFormats2[i5], hlsMasterPlaylist.muxedAudioFormat, true);
                }
                muxedTrackGroups.add(new TrackGroup(audioFormats));
            } else {
                String valueOf = String.valueOf(codecs);
                throw new IllegalArgumentException(valueOf.length() != 0 ? "Unexpected codecs attribute: ".concat(valueOf) : new String("Unexpected codecs attribute: "));
            }
            TrackGroup id3TrackGroup = new TrackGroup(Format.createSampleFormat("ID3", MimeTypes.APPLICATION_ID3, null, -1, null));
            muxedTrackGroups.add(id3TrackGroup);
            sampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray((TrackGroup[]) muxedTrackGroups.toArray(new TrackGroup[0])), 0, new TrackGroupArray(id3TrackGroup));
        }
    }

    private void buildAndPrepareAudioSampleStreamWrappers(long positionUs, List<HlsMasterPlaylist.Rendition> audioRenditions, List<HlsSampleStreamWrapper> sampleStreamWrappers2, List<int[]> manifestUrlsIndicesPerWrapper, Map<String, DrmInitData> overridingDrmInitData) {
        List<HlsMasterPlaylist.Rendition> list = audioRenditions;
        ArrayList<Uri> scratchPlaylistUrls = new ArrayList<>(audioRenditions.size());
        ArrayList<Format> scratchPlaylistFormats = new ArrayList<>(audioRenditions.size());
        ArrayList<Integer> scratchIndicesList = new ArrayList<>(audioRenditions.size());
        HashSet<String> alreadyGroupedNames = new HashSet<>();
        int renditionByNameIndex = 0;
        while (renditionByNameIndex < audioRenditions.size()) {
            String name = list.get(renditionByNameIndex).name;
            if (alreadyGroupedNames.add(name)) {
                boolean renditionsHaveCodecs = true;
                scratchPlaylistUrls.clear();
                scratchPlaylistFormats.clear();
                scratchIndicesList.clear();
                int renditionIndex = 0;
                while (true) {
                    boolean z = true;
                    if (renditionIndex >= audioRenditions.size()) {
                        break;
                    }
                    if (Util.areEqual(name, list.get(renditionIndex).name)) {
                        HlsMasterPlaylist.Rendition rendition = list.get(renditionIndex);
                        scratchIndicesList.add(Integer.valueOf(renditionIndex));
                        scratchPlaylistUrls.add(rendition.url);
                        scratchPlaylistFormats.add(rendition.format);
                        if (rendition.format.codecs == null) {
                            z = false;
                        }
                        renditionsHaveCodecs &= z;
                    }
                    renditionIndex++;
                }
                HlsSampleStreamWrapper sampleStreamWrapper = buildSampleStreamWrapper(1, (Uri[]) scratchPlaylistUrls.toArray(new Uri[0]), (Format[]) scratchPlaylistFormats.toArray(new Format[0]), null, Collections.emptyList(), overridingDrmInitData, positionUs);
                manifestUrlsIndicesPerWrapper.add(Util.toArray(scratchIndicesList));
                sampleStreamWrappers2.add(sampleStreamWrapper);
                if (this.allowChunklessPreparation && renditionsHaveCodecs) {
                    sampleStreamWrapper.prepareWithMasterPlaylistInfo(new TrackGroupArray(new TrackGroup((Format[]) scratchPlaylistFormats.toArray(new Format[0]))), 0, TrackGroupArray.EMPTY);
                }
            }
            renditionByNameIndex++;
            list = audioRenditions;
        }
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int trackType, Uri[] playlistUrls, Format[] playlistFormats, Format muxedAudioFormat, List<Format> muxedCaptionFormats, Map<String, DrmInitData> overridingDrmInitData, long positionUs) {
        return new HlsSampleStreamWrapper(trackType, this, new HlsChunkSource(this.extractorFactory, this.playlistTracker, playlistUrls, playlistFormats, this.dataSourceFactory, this.mediaTransferListener, this.timestampAdjusterProvider, muxedCaptionFormats), overridingDrmInitData, this.allocator, positionUs, muxedAudioFormat, this.loadErrorHandlingPolicy, this.eventDispatcher);
    }

    private static Map<String, DrmInitData> deriveOverridingDrmInitData(List<DrmInitData> sessionKeyDrmInitData) {
        ArrayList<DrmInitData> mutableSessionKeyDrmInitData = new ArrayList<>(sessionKeyDrmInitData);
        HashMap<String, DrmInitData> drmInitDataBySchemeType = new HashMap<>();
        for (int i = 0; i < mutableSessionKeyDrmInitData.size(); i++) {
            DrmInitData drmInitData = sessionKeyDrmInitData.get(i);
            String scheme = drmInitData.schemeType;
            int j = i + 1;
            while (j < mutableSessionKeyDrmInitData.size()) {
                DrmInitData nextDrmInitData = (DrmInitData) mutableSessionKeyDrmInitData.get(j);
                if (TextUtils.equals(nextDrmInitData.schemeType, scheme)) {
                    drmInitData = drmInitData.merge(nextDrmInitData);
                    mutableSessionKeyDrmInitData.remove(j);
                } else {
                    j++;
                }
            }
            drmInitDataBySchemeType.put(scheme, drmInitData);
        }
        return drmInitDataBySchemeType;
    }

    private static Format deriveVideoFormat(Format variantFormat) {
        String codecs = Util.getCodecsOfType(variantFormat.codecs, 2);
        return Format.createVideoContainerFormat(variantFormat.f72id, variantFormat.label, variantFormat.containerMimeType, MimeTypes.getMediaMimeType(codecs), codecs, variantFormat.bitrate, variantFormat.width, variantFormat.height, variantFormat.frameRate, null, variantFormat.selectionFlags, variantFormat.roleFlags);
    }

    private static Format deriveAudioFormat(Format variantFormat, Format mediaTagFormat, boolean isPrimaryTrackInVariant) {
        String codecs;
        Format format = variantFormat;
        Format format2 = mediaTagFormat;
        int channelCount = -1;
        int selectionFlags = 0;
        int roleFlags = 0;
        String language = null;
        String label = null;
        if (format2 != null) {
            codecs = format2.codecs;
            channelCount = format2.channelCount;
            selectionFlags = format2.selectionFlags;
            roleFlags = format2.roleFlags;
            language = format2.language;
            label = format2.label;
        } else {
            codecs = Util.getCodecsOfType(format.codecs, 1);
            if (isPrimaryTrackInVariant) {
                channelCount = format.channelCount;
                selectionFlags = format.selectionFlags;
                roleFlags = format2.roleFlags;
                language = format.language;
                label = format.label;
            }
        }
        return Format.createAudioContainerFormat(format.f72id, label, format.containerMimeType, MimeTypes.getMediaMimeType(codecs), codecs, isPrimaryTrackInVariant ? format.bitrate : -1, channelCount, -1, null, selectionFlags, roleFlags, language);
    }
}
