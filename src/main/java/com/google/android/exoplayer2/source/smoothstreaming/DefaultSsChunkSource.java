package com.google.android.exoplayer2.source.smoothstreaming;

import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Track;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.smoothstreaming.SsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.manifest.SsManifest;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.List;

public class DefaultSsChunkSource implements SsChunkSource {
    private int currentManifestChunkOffset;
    private final DataSource dataSource;
    private final ChunkExtractorWrapper[] extractorWrappers;
    private IOException fatalError;
    private SsManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int streamElementIndex;
    private final TrackSelection trackSelection;

    public static final class Factory implements SsChunkSource.Factory {
        private final DataSource.Factory dataSourceFactory;

        public Factory(DataSource.Factory dataSourceFactory2) {
            this.dataSourceFactory = dataSourceFactory2;
        }

        public SsChunkSource createChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, SsManifest manifest, int elementIndex, TrackSelection trackSelection, @Nullable TransferListener transferListener) {
            DataSource dataSource = this.dataSourceFactory.createDataSource();
            if (transferListener != null) {
                dataSource.addTransferListener(transferListener);
            }
            return new DefaultSsChunkSource(manifestLoaderErrorThrower, manifest, elementIndex, trackSelection, dataSource);
        }
    }

    public DefaultSsChunkSource(LoaderErrorThrower manifestLoaderErrorThrower2, SsManifest manifest2, int streamElementIndex2, TrackSelection trackSelection2, DataSource dataSource2) {
        SsManifest ssManifest = manifest2;
        int i = streamElementIndex2;
        TrackSelection trackSelection3 = trackSelection2;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower2;
        this.manifest = ssManifest;
        this.streamElementIndex = i;
        this.trackSelection = trackSelection3;
        this.dataSource = dataSource2;
        SsManifest.StreamElement streamElement = ssManifest.streamElements[i];
        this.extractorWrappers = new ChunkExtractorWrapper[trackSelection2.length()];
        int i2 = 0;
        while (i2 < this.extractorWrappers.length) {
            int manifestTrackIndex = trackSelection3.getIndexInTrackGroup(i2);
            Format format = streamElement.formats[manifestTrackIndex];
            Format format2 = format;
            this.extractorWrappers[i2] = new ChunkExtractorWrapper(new FragmentedMp4Extractor(3, null, new Track(manifestTrackIndex, streamElement.type, streamElement.timescale, C0841C.TIME_UNSET, ssManifest.durationUs, format2, 0, format.drmInitData != null ? ssManifest.protectionElement.trackEncryptionBoxes : null, streamElement.type == 2 ? 4 : 0, null, null), null), streamElement.type, format2);
            i2++;
            ssManifest = manifest2;
        }
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        long secondSyncUs;
        SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
        int chunkIndex = streamElement.getChunkIndex(positionUs);
        long firstSyncUs = streamElement.getStartTimeUs(chunkIndex);
        if (firstSyncUs >= positionUs || chunkIndex >= streamElement.chunkCount - 1) {
            secondSyncUs = firstSyncUs;
        } else {
            secondSyncUs = streamElement.getStartTimeUs(chunkIndex + 1);
        }
        return Util.resolveSeekPositionUs(positionUs, seekParameters, firstSyncUs, secondSyncUs);
    }

    public void updateManifest(SsManifest newManifest) {
        SsManifest.StreamElement currentElement = this.manifest.streamElements[this.streamElementIndex];
        int currentElementChunkCount = currentElement.chunkCount;
        SsManifest.StreamElement newElement = newManifest.streamElements[this.streamElementIndex];
        if (currentElementChunkCount == 0 || newElement.chunkCount == 0) {
            this.currentManifestChunkOffset += currentElementChunkCount;
        } else {
            long currentElementEndTimeUs = currentElement.getStartTimeUs(currentElementChunkCount - 1) + currentElement.getChunkDurationUs(currentElementChunkCount - 1);
            long newElementStartTimeUs = newElement.getStartTimeUs(0);
            if (currentElementEndTimeUs <= newElementStartTimeUs) {
                this.currentManifestChunkOffset += currentElementChunkCount;
            } else {
                this.currentManifestChunkOffset += currentElement.getChunkIndex(newElementStartTimeUs);
            }
        }
        this.manifest = newManifest;
    }

    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException == null) {
            this.manifestLoaderErrorThrower.maybeThrowError();
            return;
        }
        throw iOException;
    }

    public int getPreferredQueueSize(long playbackPositionUs, List<? extends MediaChunk> queue) {
        if (this.fatalError != null || this.trackSelection.length() < 2) {
            return queue.size();
        }
        return this.trackSelection.evaluateQueueSize(playbackPositionUs, queue);
    }

    public final void getNextChunk(long playbackPositionUs, long loadPositionUs, List<? extends MediaChunk> queue, ChunkHolder out) {
        int chunkIndex;
        long j = loadPositionUs;
        ChunkHolder chunkHolder = out;
        if (this.fatalError == null) {
            SsManifest.StreamElement streamElement = this.manifest.streamElements[this.streamElementIndex];
            if (streamElement.chunkCount == 0) {
                chunkHolder.endOfStream = !this.manifest.isLive;
                return;
            }
            if (queue.isEmpty()) {
                chunkIndex = streamElement.getChunkIndex(j);
            } else {
                chunkIndex = (int) (((MediaChunk) queue.get(queue.size() - 1)).getNextChunkIndex() - ((long) this.currentManifestChunkOffset));
                if (chunkIndex < 0) {
                    this.fatalError = new BehindLiveWindowException();
                    return;
                }
            }
            if (chunkIndex >= streamElement.chunkCount) {
                chunkHolder.endOfStream = !this.manifest.isLive;
                return;
            }
            long bufferedDurationUs = j - playbackPositionUs;
            long timeToLiveEdgeUs = resolveTimeToLiveEdgeUs(playbackPositionUs);
            MediaChunkIterator[] chunkIterators = new MediaChunkIterator[this.trackSelection.length()];
            for (int i = 0; i < chunkIterators.length; i++) {
                chunkIterators[i] = new StreamElementIterator(streamElement, this.trackSelection.getIndexInTrackGroup(i), chunkIndex);
            }
            this.trackSelection.updateSelectedTrack(playbackPositionUs, bufferedDurationUs, timeToLiveEdgeUs, queue, chunkIterators);
            long chunkStartTimeUs = streamElement.getStartTimeUs(chunkIndex);
            long chunkEndTimeUs = streamElement.getChunkDurationUs(chunkIndex) + chunkStartTimeUs;
            long chunkSeekTimeUs = queue.isEmpty() ? j : -9223372036854775807L;
            int trackSelectionIndex = this.trackSelection.getSelectedIndex();
            ChunkExtractorWrapper extractorWrapper = this.extractorWrappers[trackSelectionIndex];
            chunkHolder.chunk = newMediaChunk(this.trackSelection.getSelectedFormat(), this.dataSource, streamElement.buildRequestUri(this.trackSelection.getIndexInTrackGroup(trackSelectionIndex), chunkIndex), null, this.currentManifestChunkOffset + chunkIndex, chunkStartTimeUs, chunkEndTimeUs, chunkSeekTimeUs, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), extractorWrapper);
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
    }

    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, Exception e, long blacklistDurationMs) {
        if (cancelable && blacklistDurationMs != C0841C.TIME_UNSET) {
            TrackSelection trackSelection2 = this.trackSelection;
            if (trackSelection2.blacklist(trackSelection2.indexOf(chunk.trackFormat), blacklistDurationMs)) {
                return true;
            }
        }
        return false;
    }

    private static MediaChunk newMediaChunk(Format format, DataSource dataSource2, Uri uri, String cacheKey, int chunkIndex, long chunkStartTimeUs, long chunkEndTimeUs, long chunkSeekTimeUs, int trackSelectionReason, Object trackSelectionData, ChunkExtractorWrapper extractorWrapper) {
        int i = trackSelectionReason;
        Object obj = trackSelectionData;
        return new ContainerMediaChunk(dataSource2, new DataSpec(uri, 0, -1, cacheKey), format, i, obj, chunkStartTimeUs, chunkEndTimeUs, chunkSeekTimeUs, C0841C.TIME_UNSET, (long) chunkIndex, 1, chunkStartTimeUs, extractorWrapper);
    }

    private long resolveTimeToLiveEdgeUs(long playbackPositionUs) {
        if (!this.manifest.isLive) {
            return C0841C.TIME_UNSET;
        }
        SsManifest.StreamElement currentElement = this.manifest.streamElements[this.streamElementIndex];
        int lastChunkIndex = currentElement.chunkCount - 1;
        return (currentElement.getStartTimeUs(lastChunkIndex) + currentElement.getChunkDurationUs(lastChunkIndex)) - playbackPositionUs;
    }

    private static final class StreamElementIterator extends BaseMediaChunkIterator {
        private final SsManifest.StreamElement streamElement;
        private final int trackIndex;

        public StreamElementIterator(SsManifest.StreamElement streamElement2, int trackIndex2, int chunkIndex) {
            super((long) chunkIndex, (long) (streamElement2.chunkCount - 1));
            this.streamElement = streamElement2;
            this.trackIndex = trackIndex2;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            return new DataSpec(this.streamElement.buildRequestUri(this.trackIndex, (int) getCurrentIndex()));
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.streamElement.getStartTimeUs((int) getCurrentIndex());
        }

        public long getChunkEndTimeUs() {
            return this.streamElement.getChunkDurationUs((int) getCurrentIndex()) + getChunkStartTimeUs();
        }
    }
}
