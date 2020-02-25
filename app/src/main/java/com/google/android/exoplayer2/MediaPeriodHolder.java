package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.source.ClippingMediaPeriod;
import com.google.android.exoplayer2.source.EmptySampleStream;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectorResult;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

final class MediaPeriodHolder {
    private static final String TAG = "MediaPeriodHolder";
    public final MediaPeriod mediaPeriod;
    public final SampleStream[] sampleStreams;
    public final Object uid;
    private final boolean[] mayRetainStreamFlags;
    private final MediaSource mediaSource;
    private final RendererCapabilities[] rendererCapabilities;
    private final TrackSelector trackSelector;
    public boolean hasEnabledTracks;
    public MediaPeriodInfo info;
    public boolean prepared;
    @Nullable
    private MediaPeriodHolder next;
    private long rendererPositionOffsetUs;
    @Nullable
    private TrackGroupArray trackGroups;
    @Nullable
    private TrackSelectorResult trackSelectorResult;

    public MediaPeriodHolder(RendererCapabilities[] rendererCapabilities2, long rendererPositionOffsetUs2, TrackSelector trackSelector2, Allocator allocator, MediaSource mediaSource2, MediaPeriodInfo info2) {
        this.rendererCapabilities = rendererCapabilities2;
        this.rendererPositionOffsetUs = rendererPositionOffsetUs2 - info2.startPositionUs;
        this.trackSelector = trackSelector2;
        this.mediaSource = mediaSource2;
        this.uid = info2.f73id.periodUid;
        this.info = info2;
        this.sampleStreams = new SampleStream[rendererCapabilities2.length];
        this.mayRetainStreamFlags = new boolean[rendererCapabilities2.length];
        this.mediaPeriod = createMediaPeriod(info2.f73id, mediaSource2, allocator, info2.startPositionUs, info2.endPositionUs);
    }

    private static MediaPeriod createMediaPeriod(MediaSource.MediaPeriodId id, MediaSource mediaSource2, Allocator allocator, long startPositionUs, long endPositionUs) {
        MediaPeriod mediaPeriod2 = mediaSource2.createPeriod(id, allocator, startPositionUs);
        if (endPositionUs == C0841C.TIME_UNSET || endPositionUs == Long.MIN_VALUE) {
            return mediaPeriod2;
        }
        return new ClippingMediaPeriod(mediaPeriod2, true, 0, endPositionUs);
    }

    private static void releaseMediaPeriod(long endPositionUs, MediaSource mediaSource2, MediaPeriod mediaPeriod2) {
        if (endPositionUs == C0841C.TIME_UNSET || endPositionUs == Long.MIN_VALUE) {
            mediaSource2.releasePeriod(mediaPeriod2);
            return;
        }
        try {
            mediaSource2.releasePeriod(((ClippingMediaPeriod) mediaPeriod2).mediaPeriod);
        } catch (RuntimeException e) {
            Log.m27e(TAG, "Period release failed.", e);
        }
    }

    public long toRendererTime(long periodTimeUs) {
        return getRendererOffset() + periodTimeUs;
    }

    public long toPeriodTime(long rendererTimeUs) {
        return rendererTimeUs - getRendererOffset();
    }

    public long getRendererOffset() {
        return this.rendererPositionOffsetUs;
    }

    public long getStartPositionRendererTime() {
        return this.info.startPositionUs + this.rendererPositionOffsetUs;
    }

    public boolean isFullyBuffered() {
        return this.prepared && (!this.hasEnabledTracks || this.mediaPeriod.getBufferedPositionUs() == Long.MIN_VALUE);
    }

    public long getBufferedPositionUs() {
        if (!this.prepared) {
            return this.info.startPositionUs;
        }
        long bufferedPositionUs = this.hasEnabledTracks ? this.mediaPeriod.getBufferedPositionUs() : Long.MIN_VALUE;
        return bufferedPositionUs == Long.MIN_VALUE ? this.info.durationUs : bufferedPositionUs;
    }

    public long getNextLoadPositionUs() {
        if (!this.prepared) {
            return 0;
        }
        return this.mediaPeriod.getNextLoadPositionUs();
    }

    public void handlePrepared(float playbackSpeed, Timeline timeline) throws ExoPlaybackException {
        this.prepared = true;
        this.trackGroups = this.mediaPeriod.getTrackGroups();
        long newStartPositionUs = applyTrackSelection((TrackSelectorResult) Assertions.checkNotNull(selectTracks(playbackSpeed, timeline)), this.info.startPositionUs, false);
        this.rendererPositionOffsetUs += this.info.startPositionUs - newStartPositionUs;
        this.info = this.info.copyWithStartPositionUs(newStartPositionUs);
    }

    public void reevaluateBuffer(long rendererPositionUs) {
        Assertions.checkState(isLoadingMediaPeriod());
        if (this.prepared) {
            this.mediaPeriod.reevaluateBuffer(toPeriodTime(rendererPositionUs));
        }
    }

    public void continueLoading(long rendererPositionUs) {
        Assertions.checkState(isLoadingMediaPeriod());
        this.mediaPeriod.continueLoading(toPeriodTime(rendererPositionUs));
    }

    @Nullable
    public TrackSelectorResult selectTracks(float playbackSpeed, Timeline timeline) throws ExoPlaybackException {
        TrackSelectorResult selectorResult = this.trackSelector.selectTracks(this.rendererCapabilities, getTrackGroups(), this.info.f73id, timeline);
        if (selectorResult.isEquivalent(this.trackSelectorResult)) {
            return null;
        }
        for (TrackSelection trackSelection : selectorResult.selections.getAll()) {
            if (trackSelection != null) {
                trackSelection.onPlaybackSpeed(playbackSpeed);
            }
        }
        return selectorResult;
    }

    public long applyTrackSelection(TrackSelectorResult trackSelectorResult2, long positionUs, boolean forceRecreateStreams) {
        return applyTrackSelection(trackSelectorResult2, positionUs, forceRecreateStreams, new boolean[this.rendererCapabilities.length]);
    }

    public long applyTrackSelection(TrackSelectorResult newTrackSelectorResult, long positionUs, boolean forceRecreateStreams, boolean[] streamResetFlags) {
        TrackSelectorResult trackSelectorResult2 = newTrackSelectorResult;
        int i = 0;
        while (true) {
            boolean z = false;
            if (i >= trackSelectorResult2.length) {
                break;
            }
            boolean[] zArr = this.mayRetainStreamFlags;
            if (!forceRecreateStreams && newTrackSelectorResult.isEquivalent(this.trackSelectorResult, i)) {
                z = true;
            }
            zArr[i] = z;
            i++;
        }
        disassociateNoSampleRenderersWithEmptySampleStream(this.sampleStreams);
        disableTrackSelectionsInResult();
        this.trackSelectorResult = trackSelectorResult2;
        enableTrackSelectionsInResult();
        TrackSelectionArray trackSelections = trackSelectorResult2.selections;
        long positionUs2 = this.mediaPeriod.selectTracks(trackSelections.getAll(), this.mayRetainStreamFlags, this.sampleStreams, streamResetFlags, positionUs);
        associateNoSampleRenderersWithEmptySampleStream(this.sampleStreams);
        this.hasEnabledTracks = false;
        int i2 = 0;
        while (true) {
            SampleStream[] sampleStreamArr = this.sampleStreams;
            if (i2 >= sampleStreamArr.length) {
                return positionUs2;
            }
            if (sampleStreamArr[i2] != null) {
                Assertions.checkState(newTrackSelectorResult.isRendererEnabled(i2));
                if (this.rendererCapabilities[i2].getTrackType() != 6) {
                    this.hasEnabledTracks = true;
                }
            } else {
                Assertions.checkState(trackSelections.get(i2) == null);
            }
            i2++;
        }
    }

    public void release() {
        disableTrackSelectionsInResult();
        this.trackSelectorResult = null;
        releaseMediaPeriod(this.info.endPositionUs, this.mediaSource, this.mediaPeriod);
    }

    @Nullable
    public MediaPeriodHolder getNext() {
        return this.next;
    }

    public void setNext(@Nullable MediaPeriodHolder nextMediaPeriodHolder) {
        if (nextMediaPeriodHolder != this.next) {
            disableTrackSelectionsInResult();
            this.next = nextMediaPeriodHolder;
            enableTrackSelectionsInResult();
        }
    }

    public TrackGroupArray getTrackGroups() {
        return (TrackGroupArray) Assertions.checkNotNull(this.trackGroups);
    }

    public TrackSelectorResult getTrackSelectorResult() {
        return (TrackSelectorResult) Assertions.checkNotNull(this.trackSelectorResult);
    }

    private void enableTrackSelectionsInResult() {
        TrackSelectorResult trackSelectorResult2 = this.trackSelectorResult;
        if (isLoadingMediaPeriod() && trackSelectorResult2 != null) {
            for (int i = 0; i < trackSelectorResult2.length; i++) {
                boolean rendererEnabled = trackSelectorResult2.isRendererEnabled(i);
                TrackSelection trackSelection = trackSelectorResult2.selections.get(i);
                if (rendererEnabled && trackSelection != null) {
                    trackSelection.enable();
                }
            }
        }
    }

    private void disableTrackSelectionsInResult() {
        TrackSelectorResult trackSelectorResult2 = this.trackSelectorResult;
        if (isLoadingMediaPeriod() && trackSelectorResult2 != null) {
            for (int i = 0; i < trackSelectorResult2.length; i++) {
                boolean rendererEnabled = trackSelectorResult2.isRendererEnabled(i);
                TrackSelection trackSelection = trackSelectorResult2.selections.get(i);
                if (rendererEnabled && trackSelection != null) {
                    trackSelection.disable();
                }
            }
        }
    }

    private void disassociateNoSampleRenderersWithEmptySampleStream(SampleStream[] sampleStreams2) {
        int i = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.rendererCapabilities;
            if (i < rendererCapabilitiesArr.length) {
                if (rendererCapabilitiesArr[i].getTrackType() == 6) {
                    sampleStreams2[i] = null;
                }
                i++;
            } else {
                return;
            }
        }
    }

    private void associateNoSampleRenderersWithEmptySampleStream(SampleStream[] sampleStreams2) {
        TrackSelectorResult trackSelectorResult2 = (TrackSelectorResult) Assertions.checkNotNull(this.trackSelectorResult);
        int i = 0;
        while (true) {
            RendererCapabilities[] rendererCapabilitiesArr = this.rendererCapabilities;
            if (i < rendererCapabilitiesArr.length) {
                if (rendererCapabilitiesArr[i].getTrackType() == 6 && trackSelectorResult2.isRendererEnabled(i)) {
                    sampleStreams2[i] = new EmptySampleStream();
                }
                i++;
            } else {
                return;
            }
        }
    }

    private boolean isLoadingMediaPeriod() {
        return this.next == null;
    }
}
