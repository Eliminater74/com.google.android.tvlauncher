package com.google.android.exoplayer2.source.smoothstreaming;

import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.offline.StreamKey;
import com.google.android.exoplayer2.source.CompositeSequenceableLoaderFactory;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.ChunkSampleStream;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

final class SsMediaPeriod implements MediaPeriod, SequenceableLoader.Callback<ChunkSampleStream<SsChunkSource>> {
    private final Allocator allocator;
    @Nullable
    private MediaPeriod.Callback callback;
    private final SsChunkSource.Factory chunkSourceFactory;
    private SequenceableLoader compositeSequenceableLoader;
    private final CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private boolean notifiedReadingStarted;
    private ChunkSampleStream<SsChunkSource>[] sampleStreams = newSampleStreamArray(0);
    private final TrackGroupArray trackGroups;
    @Nullable
    private final TransferListener transferListener;

    public /* bridge */ /* synthetic */ void onContinueLoadingRequested(SequenceableLoader sequenceableLoader) {
        onContinueLoadingRequested((ChunkSampleStream<SsChunkSource>) ((ChunkSampleStream) sequenceableLoader));
    }

    public SsMediaPeriod(SsManifest manifest2, SsChunkSource.Factory chunkSourceFactory2, @Nullable TransferListener transferListener2, CompositeSequenceableLoaderFactory compositeSequenceableLoaderFactory2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2, LoaderErrorThrower manifestLoaderErrorThrower2, Allocator allocator2) {
        this.manifest = manifest2;
        this.chunkSourceFactory = chunkSourceFactory2;
        this.transferListener = transferListener2;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.allocator = allocator2;
        this.compositeSequenceableLoaderFactory = compositeSequenceableLoaderFactory2;
        this.trackGroups = buildTrackGroups(manifest2);
        this.compositeSequenceableLoader = compositeSequenceableLoaderFactory2.createCompositeSequenceableLoader(this.sampleStreams);
        eventDispatcher2.mediaPeriodCreated();
    }

    public void updateManifest(SsManifest manifest2) {
        this.manifest = manifest2;
        for (ChunkSampleStream<SsChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.getChunkSource().updateManifest(manifest2);
        }
        this.callback.onContinueLoadingRequested(this);
    }

    public void release() {
        for (ChunkSampleStream<SsChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.release();
        }
        this.callback = null;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void prepare(MediaPeriod.Callback callback2, long positionUs) {
        this.callback = callback2;
        callback2.onPrepared(this);
    }

    public void maybeThrowPrepareError() throws IOException {
        this.manifestLoaderErrorThrower.maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        ArrayList<ChunkSampleStream<SsChunkSource>> sampleStreamsList = new ArrayList<>();
        for (int i = 0; i < selections.length; i++) {
            if (streams[i] != null) {
                ChunkSampleStream<SsChunkSource> stream = (ChunkSampleStream) streams[i];
                if (selections[i] == null || !mayRetainStreamFlags[i]) {
                    stream.release();
                    streams[i] = null;
                } else {
                    sampleStreamsList.add(stream);
                }
            }
            if (streams[i] == null && selections[i] != null) {
                ChunkSampleStream<SsChunkSource> stream2 = buildSampleStream(selections[i], positionUs);
                sampleStreamsList.add(stream2);
                streams[i] = stream2;
                streamResetFlags[i] = true;
            }
        }
        this.sampleStreams = newSampleStreamArray(sampleStreamsList.size());
        sampleStreamsList.toArray(this.sampleStreams);
        this.compositeSequenceableLoader = this.compositeSequenceableLoaderFactory.createCompositeSequenceableLoader(this.sampleStreams);
        return positionUs;
    }

    public List<StreamKey> getStreamKeys(List<TrackSelection> trackSelections) {
        List<StreamKey> streamKeys = new ArrayList<>();
        for (int selectionIndex = 0; selectionIndex < trackSelections.size(); selectionIndex++) {
            TrackSelection trackSelection = trackSelections.get(selectionIndex);
            int streamElementIndex = this.trackGroups.indexOf(trackSelection.getTrackGroup());
            for (int i = 0; i < trackSelection.length(); i++) {
                streamKeys.add(new StreamKey(streamElementIndex, trackSelection.getIndexInTrackGroup(i)));
            }
        }
        return streamKeys;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        for (ChunkSampleStream<SsChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.discardBuffer(positionUs, toKeyframe);
        }
    }

    public void reevaluateBuffer(long positionUs) {
        this.compositeSequenceableLoader.reevaluateBuffer(positionUs);
    }

    public boolean continueLoading(long positionUs) {
        return this.compositeSequenceableLoader.continueLoading(positionUs);
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
        for (ChunkSampleStream<SsChunkSource> sampleStream : this.sampleStreams) {
            sampleStream.seekToUs(positionUs);
        }
        return positionUs;
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        for (ChunkSampleStream<SsChunkSource> sampleStream : this.sampleStreams) {
            if (sampleStream.primaryTrackType == 2) {
                return sampleStream.getAdjustedSeekPositionUs(positionUs, seekParameters);
            }
        }
        return positionUs;
    }

    public void onContinueLoadingRequested(ChunkSampleStream<SsChunkSource> chunkSampleStream) {
        this.callback.onContinueLoadingRequested(this);
    }

    private ChunkSampleStream<SsChunkSource> buildSampleStream(TrackSelection selection, long positionUs) {
        int streamElementIndex = this.trackGroups.indexOf(selection.getTrackGroup());
        return new ChunkSampleStream(this.manifest.streamElements[streamElementIndex].type, (int[]) null, (Format[]) null, this.chunkSourceFactory.createChunkSource(this.manifestLoaderErrorThrower, this.manifest, streamElementIndex, selection, this.transferListener), this, this.allocator, positionUs, this.loadErrorHandlingPolicy, this.eventDispatcher);
    }

    private static TrackGroupArray buildTrackGroups(SsManifest manifest2) {
        TrackGroup[] trackGroups2 = new TrackGroup[manifest2.streamElements.length];
        for (int i = 0; i < manifest2.streamElements.length; i++) {
            trackGroups2[i] = new TrackGroup(manifest2.streamElements[i].formats);
        }
        return new TrackGroupArray(trackGroups2);
    }

    private static ChunkSampleStream<SsChunkSource>[] newSampleStreamArray(int length) {
        return new ChunkSampleStream[length];
    }
}
