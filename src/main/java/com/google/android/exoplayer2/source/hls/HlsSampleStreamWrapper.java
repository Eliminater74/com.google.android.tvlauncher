package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.hls.HlsChunkSource;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class HlsSampleStreamWrapper implements Loader.Callback<Chunk>, Loader.ReleaseCallback, SequenceableLoader, ExtractorOutput, SampleQueue.UpstreamFormatChangedListener {
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_FATAL = -2;
    public static final int SAMPLE_QUEUE_INDEX_NO_MAPPING_NON_FATAL = -3;
    public static final int SAMPLE_QUEUE_INDEX_PENDING = -1;
    private static final String TAG = "HlsSampleStreamWrapper";
    private final Allocator allocator;
    private int audioSampleQueueIndex = -1;
    private boolean audioSampleQueueMappingDone;
    private final Callback callback;
    private final HlsChunkSource chunkSource;
    private int chunkUid;
    private Format downstreamTrackFormat;
    private int enabledTrackGroupCount;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private final Handler handler = new Handler();
    private boolean haveAudioVideoSampleQueues;
    private final ArrayList<HlsSampleStream> hlsSampleStreams = new ArrayList<>();
    private long lastSeekPositionUs;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Loader loader = new Loader("Loader:HlsSampleStreamWrapper");
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable = new HlsSampleStreamWrapper$$Lambda$0(this);
    private final ArrayList<HlsMediaChunk> mediaChunks = new ArrayList<>();
    private final Format muxedAudioFormat;
    private final HlsChunkSource.HlsChunkHolder nextChunkHolder = new HlsChunkSource.HlsChunkHolder();
    private final Runnable onTracksEndedRunnable = new HlsSampleStreamWrapper$$Lambda$1(this);
    private TrackGroupArray optionalTrackGroups;
    private final Map<String, DrmInitData> overridingDrmInitData;
    private long pendingResetPositionUs;
    private boolean pendingResetUpstreamFormats;
    private boolean prepared;
    private int primarySampleQueueIndex;
    private int primarySampleQueueType;
    private int primaryTrackGroupIndex;
    private final List<HlsMediaChunk> readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);
    private boolean released;
    private long sampleOffsetUs;
    private boolean[] sampleQueueIsAudioVideoFlags = new boolean[0];
    private int[] sampleQueueTrackIds = new int[0];
    private SampleQueue[] sampleQueues = new SampleQueue[0];
    private boolean sampleQueuesBuilt;
    private boolean[] sampleQueuesEnabledStates = new boolean[0];
    private boolean seenFirstTrackSelection;
    private int[] trackGroupToSampleQueueIndex;
    private TrackGroupArray trackGroups;
    private final int trackType;
    private boolean tracksEnded;
    private Format upstreamTrackFormat;
    private int videoSampleQueueIndex = -1;
    private boolean videoSampleQueueMappingDone;

    public interface Callback extends SequenceableLoader.Callback<HlsSampleStreamWrapper> {
        void onPlaylistRefreshRequired(Uri uri);

        void onPrepared();
    }

    public HlsSampleStreamWrapper(int trackType2, Callback callback2, HlsChunkSource chunkSource2, Map<String, DrmInitData> overridingDrmInitData2, Allocator allocator2, long positionUs, Format muxedAudioFormat2, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2) {
        this.trackType = trackType2;
        this.callback = callback2;
        this.chunkSource = chunkSource2;
        this.overridingDrmInitData = overridingDrmInitData2;
        this.allocator = allocator2;
        this.muxedAudioFormat = muxedAudioFormat2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.lastSeekPositionUs = positionUs;
        this.pendingResetPositionUs = positionUs;
    }

    public void continuePreparing() {
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        }
    }

    public void prepareWithMasterPlaylistInfo(TrackGroupArray trackGroups2, int primaryTrackGroupIndex2, TrackGroupArray optionalTrackGroups2) {
        this.prepared = true;
        this.trackGroups = trackGroups2;
        this.optionalTrackGroups = optionalTrackGroups2;
        this.primaryTrackGroupIndex = primaryTrackGroupIndex2;
        Handler handler2 = this.handler;
        Callback callback2 = this.callback;
        callback2.getClass();
        handler2.post(HlsSampleStreamWrapper$$Lambda$2.get$Lambda(callback2));
    }

    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public int getPrimaryTrackGroupIndex() {
        return this.primaryTrackGroupIndex;
    }

    public int bindSampleQueueToSampleStream(int trackGroupIndex) {
        int sampleQueueIndex = this.trackGroupToSampleQueueIndex[trackGroupIndex];
        if (sampleQueueIndex != -1) {
            boolean[] zArr = this.sampleQueuesEnabledStates;
            if (zArr[sampleQueueIndex]) {
                return -2;
            }
            zArr[sampleQueueIndex] = true;
            return sampleQueueIndex;
        } else if (this.optionalTrackGroups.indexOf(this.trackGroups.get(trackGroupIndex)) == -1) {
            return -2;
        } else {
            return -3;
        }
    }

    public void unbindSampleQueue(int trackGroupIndex) {
        int sampleQueueIndex = this.trackGroupToSampleQueueIndex[trackGroupIndex];
        Assertions.checkState(this.sampleQueuesEnabledStates[sampleQueueIndex]);
        this.sampleQueuesEnabledStates[sampleQueueIndex] = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:74:0x015d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[] r23, boolean[] r24, com.google.android.exoplayer2.source.SampleStream[] r25, boolean[] r26, long r27, boolean r29) {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            r2 = r25
            r12 = r27
            boolean r3 = r0.prepared
            com.google.android.exoplayer2.util.Assertions.checkState(r3)
            int r14 = r0.enabledTrackGroupCount
            r3 = 0
        L_0x0010:
            int r4 = r1.length
            r5 = 0
            r15 = 1
            if (r3 >= r4) goto L_0x0032
            r4 = r2[r3]
            if (r4 == 0) goto L_0x002f
            r4 = r1[r3]
            if (r4 == 0) goto L_0x0021
            boolean r4 = r24[r3]
            if (r4 != 0) goto L_0x002f
        L_0x0021:
            int r4 = r0.enabledTrackGroupCount
            int r4 = r4 - r15
            r0.enabledTrackGroupCount = r4
            r4 = r2[r3]
            com.google.android.exoplayer2.source.hls.HlsSampleStream r4 = (com.google.android.exoplayer2.source.hls.HlsSampleStream) r4
            r4.unbindSampleQueue()
            r2[r3] = r5
        L_0x002f:
            int r3 = r3 + 1
            goto L_0x0010
        L_0x0032:
            if (r29 != 0) goto L_0x0044
            boolean r4 = r0.seenFirstTrackSelection
            if (r4 == 0) goto L_0x003b
            if (r14 != 0) goto L_0x0042
            goto L_0x0044
        L_0x003b:
            long r6 = r0.lastSeekPositionUs
            int r4 = (r12 > r6 ? 1 : (r12 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x0042
            goto L_0x0044
        L_0x0042:
            r4 = 0
            goto L_0x0045
        L_0x0044:
            r4 = 1
        L_0x0045:
            com.google.android.exoplayer2.source.hls.HlsChunkSource r6 = r0.chunkSource
            com.google.android.exoplayer2.trackselection.TrackSelection r11 = r6.getTrackSelection()
            r6 = r11
            r7 = 0
            r16 = r4
            r10 = r6
        L_0x0050:
            int r4 = r1.length
            if (r7 >= r4) goto L_0x00b0
            r4 = r2[r7]
            if (r4 != 0) goto L_0x00ad
            r4 = r1[r7]
            if (r4 == 0) goto L_0x00ad
            int r4 = r0.enabledTrackGroupCount
            int r4 = r4 + r15
            r0.enabledTrackGroupCount = r4
            r4 = r1[r7]
            com.google.android.exoplayer2.source.TrackGroupArray r6 = r0.trackGroups
            com.google.android.exoplayer2.source.TrackGroup r8 = r4.getTrackGroup()
            int r6 = r6.indexOf(r8)
            int r8 = r0.primaryTrackGroupIndex
            if (r6 != r8) goto L_0x0076
            r10 = r4
            com.google.android.exoplayer2.source.hls.HlsChunkSource r8 = r0.chunkSource
            r8.selectTracks(r4)
        L_0x0076:
            com.google.android.exoplayer2.source.hls.HlsSampleStream r8 = new com.google.android.exoplayer2.source.hls.HlsSampleStream
            r8.<init>(r0, r6)
            r2[r7] = r8
            r26[r7] = r15
            int[] r8 = r0.trackGroupToSampleQueueIndex
            if (r8 == 0) goto L_0x008a
            r8 = r2[r7]
            com.google.android.exoplayer2.source.hls.HlsSampleStream r8 = (com.google.android.exoplayer2.source.hls.HlsSampleStream) r8
            r8.bindSampleQueue()
        L_0x008a:
            boolean r8 = r0.sampleQueuesBuilt
            if (r8 == 0) goto L_0x00ad
            if (r16 != 0) goto L_0x00ad
            com.google.android.exoplayer2.source.SampleQueue[] r8 = r0.sampleQueues
            int[] r9 = r0.trackGroupToSampleQueueIndex
            r9 = r9[r6]
            r8 = r8[r9]
            r8.rewind()
            int r9 = r8.advanceTo(r12, r15, r15)
            r3 = -1
            if (r9 != r3) goto L_0x00aa
            int r3 = r8.getReadIndex()
            if (r3 == 0) goto L_0x00aa
            r3 = 1
            goto L_0x00ab
        L_0x00aa:
            r3 = 0
        L_0x00ab:
            r16 = r3
        L_0x00ad:
            int r7 = r7 + 1
            goto L_0x0050
        L_0x00b0:
            int r3 = r0.enabledTrackGroupCount
            if (r3 != 0) goto L_0x00eb
            com.google.android.exoplayer2.source.hls.HlsChunkSource r3 = r0.chunkSource
            r3.reset()
            r0.downstreamTrackFormat = r5
            java.util.ArrayList<com.google.android.exoplayer2.source.hls.HlsMediaChunk> r3 = r0.mediaChunks
            r3.clear()
            com.google.android.exoplayer2.upstream.Loader r3 = r0.loader
            boolean r3 = r3.isLoading()
            if (r3 == 0) goto L_0x00e0
            boolean r3 = r0.sampleQueuesBuilt
            if (r3 == 0) goto L_0x00da
            com.google.android.exoplayer2.source.SampleQueue[] r3 = r0.sampleQueues
            int r4 = r3.length
            r5 = 0
        L_0x00d0:
            if (r5 >= r4) goto L_0x00da
            r6 = r3[r5]
            r6.discardToEnd()
            int r5 = r5 + 1
            goto L_0x00d0
        L_0x00da:
            com.google.android.exoplayer2.upstream.Loader r3 = r0.loader
            r3.cancelLoading()
            goto L_0x00e3
        L_0x00e0:
            r22.resetSampleQueues()
        L_0x00e3:
            r3 = r29
            r19 = r10
            r20 = r11
            goto L_0x016e
        L_0x00eb:
            java.util.ArrayList<com.google.android.exoplayer2.source.hls.HlsMediaChunk> r3 = r0.mediaChunks
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L_0x0155
            boolean r3 = com.google.android.exoplayer2.util.Util.areEqual(r10, r11)
            if (r3 != 0) goto L_0x0150
            r17 = 0
            boolean r3 = r0.seenFirstTrackSelection
            if (r3 != 0) goto L_0x0140
            r3 = 0
            int r5 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x0106
            long r3 = -r12
        L_0x0106:
            r6 = r3
            com.google.android.exoplayer2.source.hls.HlsMediaChunk r8 = r22.getLastMediaChunk()
            com.google.android.exoplayer2.source.hls.HlsChunkSource r3 = r0.chunkSource
            com.google.android.exoplayer2.source.chunk.MediaChunkIterator[] r18 = r3.createMediaChunkIterators(r8, r12)
            r19 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            java.util.List<com.google.android.exoplayer2.source.hls.HlsMediaChunk> r9 = r0.readOnlyMediaChunks
            r3 = r10
            r4 = r27
            r15 = r8
            r21 = r9
            r8 = r19
            r19 = r10
            r10 = r21
            r20 = r11
            r11 = r18
            r3.updateSelectedTrack(r4, r6, r8, r10, r11)
            com.google.android.exoplayer2.source.hls.HlsChunkSource r3 = r0.chunkSource
            com.google.android.exoplayer2.source.TrackGroup r3 = r3.getTrackGroup()
            com.google.android.exoplayer2.Format r4 = r15.trackFormat
            int r3 = r3.indexOf(r4)
            int r4 = r19.getSelectedIndexInTrackGroup()
            if (r4 == r3) goto L_0x013f
            r17 = 1
        L_0x013f:
            goto L_0x0146
        L_0x0140:
            r19 = r10
            r20 = r11
            r17 = 1
        L_0x0146:
            if (r17 == 0) goto L_0x0159
            r3 = 1
            r4 = 1
            r5 = 1
            r0.pendingResetUpstreamFormats = r5
            r16 = r4
            goto L_0x015b
        L_0x0150:
            r19 = r10
            r20 = r11
            goto L_0x0159
        L_0x0155:
            r19 = r10
            r20 = r11
        L_0x0159:
            r3 = r29
        L_0x015b:
            if (r16 == 0) goto L_0x016e
            r0.seekToUs(r12, r3)
            r4 = 0
        L_0x0161:
            int r5 = r2.length
            if (r4 >= r5) goto L_0x016e
            r5 = r2[r4]
            if (r5 == 0) goto L_0x016b
            r5 = 1
            r26[r4] = r5
        L_0x016b:
            int r4 = r4 + 1
            goto L_0x0161
        L_0x016e:
            r0.updateSampleStreams(r2)
            r4 = 1
            r0.seenFirstTrackSelection = r4
            return r16
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.hls.HlsSampleStreamWrapper.selectTracks(com.google.android.exoplayer2.trackselection.TrackSelection[], boolean[], com.google.android.exoplayer2.source.SampleStream[], boolean[], long, boolean):boolean");
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        if (this.sampleQueuesBuilt && !isPendingReset()) {
            int sampleQueueCount = this.sampleQueues.length;
            for (int i = 0; i < sampleQueueCount; i++) {
                this.sampleQueues[i].discardTo(positionUs, toKeyframe, this.sampleQueuesEnabledStates[i]);
            }
        }
    }

    public boolean seekToUs(long positionUs, boolean forceReset) {
        this.lastSeekPositionUs = positionUs;
        if (isPendingReset()) {
            this.pendingResetPositionUs = positionUs;
            return true;
        } else if (this.sampleQueuesBuilt && !forceReset && seekInsideBufferUs(positionUs)) {
            return false;
        } else {
            this.pendingResetPositionUs = positionUs;
            this.loadingFinished = false;
            this.mediaChunks.clear();
            if (this.loader.isLoading()) {
                this.loader.cancelLoading();
            } else {
                resetSampleQueues();
            }
            return true;
        }
    }

    public void release() {
        if (this.prepared) {
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.discardToEnd();
            }
        }
        this.loader.release(this);
        this.handler.removeCallbacksAndMessages(null);
        this.released = true;
        this.hlsSampleStreams.clear();
    }

    public void onLoaderReleased() {
        resetSampleQueues();
    }

    public void setIsTimestampMaster(boolean isTimestampMaster) {
        this.chunkSource.setIsTimestampMaster(isTimestampMaster);
    }

    public boolean onPlaylistError(Uri playlistUrl, long blacklistDurationMs) {
        return this.chunkSource.onPlaylistError(playlistUrl, blacklistDurationMs);
    }

    public boolean isReady(int sampleQueueIndex) {
        return this.loadingFinished || (!isPendingReset() && this.sampleQueues[sampleQueueIndex].hasNextSample());
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        this.chunkSource.maybeThrowError();
    }

    public int readData(int sampleQueueIndex, FormatHolder formatHolder, DecoderInputBuffer buffer, boolean requireFormat) {
        DrmInitData drmInitData;
        Format trackFormat;
        if (isPendingReset()) {
            return -3;
        }
        if (!this.mediaChunks.isEmpty()) {
            int discardToMediaChunkIndex = 0;
            while (discardToMediaChunkIndex < this.mediaChunks.size() - 1 && finishedReadingChunk(this.mediaChunks.get(discardToMediaChunkIndex))) {
                discardToMediaChunkIndex++;
            }
            Util.removeRange(this.mediaChunks, 0, discardToMediaChunkIndex);
            HlsMediaChunk currentChunk = this.mediaChunks.get(0);
            Format trackFormat2 = currentChunk.trackFormat;
            if (!trackFormat2.equals(this.downstreamTrackFormat)) {
                this.eventDispatcher.downstreamFormatChanged(this.trackType, trackFormat2, currentChunk.trackSelectionReason, currentChunk.trackSelectionData, currentChunk.startTimeUs);
            }
            this.downstreamTrackFormat = trackFormat2;
        }
        int result = this.sampleQueues[sampleQueueIndex].read(formatHolder, buffer, requireFormat, this.loadingFinished, this.lastSeekPositionUs);
        if (result == -5) {
            Format format = formatHolder.format;
            if (sampleQueueIndex == this.primarySampleQueueIndex) {
                int chunkUid2 = this.sampleQueues[sampleQueueIndex].peekSourceId();
                int chunkIndex = 0;
                while (chunkIndex < this.mediaChunks.size() && this.mediaChunks.get(chunkIndex).uid != chunkUid2) {
                    chunkIndex++;
                }
                if (chunkIndex < this.mediaChunks.size()) {
                    trackFormat = this.mediaChunks.get(chunkIndex).trackFormat;
                } else {
                    trackFormat = this.upstreamTrackFormat;
                }
                format = format.copyWithManifestFormatInfo(trackFormat);
            }
            if (!(format.drmInitData == null || (drmInitData = this.overridingDrmInitData.get(format.drmInitData.schemeType)) == null)) {
                format = format.copyWithDrmInitData(drmInitData);
            }
            formatHolder.format = format;
        }
        return result;
    }

    public int skipData(int sampleQueueIndex, long positionUs) {
        if (isPendingReset()) {
            return 0;
        }
        SampleQueue sampleQueue = this.sampleQueues[sampleQueueIndex];
        if (this.loadingFinished && positionUs > sampleQueue.getLargestQueuedTimestampUs()) {
            return sampleQueue.advanceToEnd();
        }
        int skipCount = sampleQueue.advanceTo(positionUs, true, true);
        if (skipCount == -1) {
            return 0;
        }
        return skipCount;
    }

    public long getBufferedPositionUs() {
        HlsMediaChunk lastCompletedMediaChunk;
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long bufferedPositionUs = this.lastSeekPositionUs;
        HlsMediaChunk lastMediaChunk = getLastMediaChunk();
        if (lastMediaChunk.isLoadCompleted()) {
            lastCompletedMediaChunk = lastMediaChunk;
        } else if (this.mediaChunks.size() > 1) {
            ArrayList<HlsMediaChunk> arrayList = this.mediaChunks;
            lastCompletedMediaChunk = arrayList.get(arrayList.size() - 2);
        } else {
            lastCompletedMediaChunk = null;
        }
        if (lastCompletedMediaChunk != null) {
            bufferedPositionUs = Math.max(bufferedPositionUs, lastCompletedMediaChunk.endTimeUs);
        }
        if (this.sampleQueuesBuilt) {
            for (SampleQueue sampleQueue : this.sampleQueues) {
                bufferedPositionUs = Math.max(bufferedPositionUs, sampleQueue.getLargestQueuedTimestampUs());
            }
        }
        return bufferedPositionUs;
    }

    public long getNextLoadPositionUs() {
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        return getLastMediaChunk().endTimeUs;
    }

    public boolean continueLoading(long positionUs) {
        long loadPositionUs;
        List<HlsMediaChunk> chunkQueue;
        long j;
        if (this.loadingFinished || this.loader.isLoading()) {
            return false;
        }
        if (isPendingReset()) {
            chunkQueue = Collections.emptyList();
            loadPositionUs = this.pendingResetPositionUs;
        } else {
            chunkQueue = this.readOnlyMediaChunks;
            HlsMediaChunk lastMediaChunk = getLastMediaChunk();
            if (lastMediaChunk.isLoadCompleted()) {
                j = lastMediaChunk.endTimeUs;
            } else {
                j = Math.max(this.lastSeekPositionUs, lastMediaChunk.startTimeUs);
            }
            loadPositionUs = j;
        }
        this.chunkSource.getNextChunk(positionUs, loadPositionUs, chunkQueue, this.nextChunkHolder);
        boolean endOfStream = this.nextChunkHolder.endOfStream;
        Chunk loadable = this.nextChunkHolder.chunk;
        Uri playlistUrlToLoad = this.nextChunkHolder.playlistUrl;
        this.nextChunkHolder.clear();
        if (endOfStream) {
            this.pendingResetPositionUs = C0841C.TIME_UNSET;
            this.loadingFinished = true;
            return true;
        } else if (loadable == null) {
            if (playlistUrlToLoad != null) {
                this.callback.onPlaylistRefreshRequired(playlistUrlToLoad);
            }
            return false;
        } else {
            if (isMediaChunk(loadable)) {
                this.pendingResetPositionUs = C0841C.TIME_UNSET;
                HlsMediaChunk mediaChunk = (HlsMediaChunk) loadable;
                mediaChunk.init(this);
                this.mediaChunks.add(mediaChunk);
                this.upstreamTrackFormat = mediaChunk.trackFormat;
            }
            long elapsedRealtimeMs = this.loader.startLoading(loadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(loadable.type));
            this.eventDispatcher.loadStarted(loadable.dataSpec, loadable.type, this.trackType, loadable.trackFormat, loadable.trackSelectionReason, loadable.trackSelectionData, loadable.startTimeUs, loadable.endTimeUs, elapsedRealtimeMs);
            return true;
        }
    }

    public void reevaluateBuffer(long positionUs) {
    }

    public void onLoadCompleted(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs) {
        Chunk chunk = loadable;
        this.chunkSource.onChunkLoadCompleted(chunk);
        this.eventDispatcher.loadCompleted(chunk.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        if (!this.prepared) {
            continueLoading(this.lastSeekPositionUs);
        } else {
            this.callback.onContinueLoadingRequested(this);
        }
    }

    public void onLoadCanceled(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released2) {
        Chunk chunk = loadable;
        this.eventDispatcher.loadCanceled(chunk.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        if (!released2) {
            resetSampleQueues();
            if (this.enabledTrackGroupCount > 0) {
                this.callback.onContinueLoadingRequested(this);
            }
        }
    }

    public Loader.LoadErrorAction onLoadError(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        boolean blacklistSucceeded;
        Loader.LoadErrorAction loadErrorAction;
        Loader.LoadErrorAction loadErrorAction2;
        Chunk chunk = loadable;
        long bytesLoaded = loadable.bytesLoaded();
        boolean isMediaChunk = isMediaChunk(loadable);
        long blacklistDurationMs = this.loadErrorHandlingPolicy.getBlacklistDurationMsFor(chunk.type, loadDurationMs, error, errorCount);
        if (blacklistDurationMs != C0841C.TIME_UNSET) {
            blacklistSucceeded = this.chunkSource.maybeBlacklistTrack(chunk, blacklistDurationMs);
        } else {
            blacklistSucceeded = false;
        }
        boolean blacklistSucceeded2 = false;
        if (blacklistSucceeded) {
            if (isMediaChunk && bytesLoaded == 0) {
                ArrayList<HlsMediaChunk> arrayList = this.mediaChunks;
                if (arrayList.remove(arrayList.size() - 1) == chunk) {
                    blacklistSucceeded2 = true;
                }
                Assertions.checkState(blacklistSucceeded2);
                if (this.mediaChunks.isEmpty()) {
                    this.pendingResetPositionUs = this.lastSeekPositionUs;
                }
            }
            loadErrorAction = Loader.DONT_RETRY;
        } else {
            long retryDelayMs = this.loadErrorHandlingPolicy.getRetryDelayMsFor(chunk.type, loadDurationMs, error, errorCount);
            if (retryDelayMs != C0841C.TIME_UNSET) {
                loadErrorAction2 = Loader.createRetryAction(false, retryDelayMs);
            } else {
                loadErrorAction2 = Loader.DONT_RETRY_FATAL;
            }
            loadErrorAction = loadErrorAction2;
        }
        this.eventDispatcher.loadError(chunk.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), chunk.type, this.trackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, elapsedRealtimeMs, loadDurationMs, bytesLoaded, error, !loadErrorAction.isRetry());
        if (blacklistSucceeded) {
            if (!this.prepared) {
                continueLoading(this.lastSeekPositionUs);
            } else {
                this.callback.onContinueLoadingRequested(this);
            }
        }
        return loadErrorAction;
    }

    public void init(int chunkUid2, boolean shouldSpliceIn, boolean reusingExtractor) {
        if (!reusingExtractor) {
            this.audioSampleQueueMappingDone = false;
            this.videoSampleQueueMappingDone = false;
        }
        this.chunkUid = chunkUid2;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.sourceId(chunkUid2);
        }
        if (shouldSpliceIn) {
            for (SampleQueue sampleQueue2 : this.sampleQueues) {
                sampleQueue2.splice();
            }
        }
    }

    public TrackOutput track(int id, int type) {
        SampleQueue[] sampleQueueArr = this.sampleQueues;
        int trackCount = sampleQueueArr.length;
        if (type == 1) {
            int i = this.audioSampleQueueIndex;
            if (i != -1) {
                if (!this.audioSampleQueueMappingDone) {
                    this.audioSampleQueueMappingDone = true;
                    this.sampleQueueTrackIds[i] = id;
                    return sampleQueueArr[i];
                } else if (this.sampleQueueTrackIds[i] == id) {
                    return sampleQueueArr[i];
                } else {
                    return createDummyTrackOutput(id, type);
                }
            } else if (this.tracksEnded) {
                return createDummyTrackOutput(id, type);
            }
        } else if (type == 2) {
            int i2 = this.videoSampleQueueIndex;
            if (i2 != -1) {
                if (!this.videoSampleQueueMappingDone) {
                    this.videoSampleQueueMappingDone = true;
                    this.sampleQueueTrackIds[i2] = id;
                    return sampleQueueArr[i2];
                } else if (this.sampleQueueTrackIds[i2] == id) {
                    return sampleQueueArr[i2];
                } else {
                    return createDummyTrackOutput(id, type);
                }
            } else if (this.tracksEnded) {
                return createDummyTrackOutput(id, type);
            }
        } else {
            for (int i3 = 0; i3 < trackCount; i3++) {
                if (this.sampleQueueTrackIds[i3] == id) {
                    return this.sampleQueues[i3];
                }
            }
            if (this.tracksEnded != 0) {
                return createDummyTrackOutput(id, type);
            }
        }
        SampleQueue trackOutput = new PrivTimestampStrippingSampleQueue(this.allocator);
        trackOutput.setSampleOffsetUs(this.sampleOffsetUs);
        trackOutput.sourceId(this.chunkUid);
        trackOutput.setUpstreamFormatChangeListener(this);
        this.sampleQueueTrackIds = Arrays.copyOf(this.sampleQueueTrackIds, trackCount + 1);
        this.sampleQueueTrackIds[trackCount] = id;
        this.sampleQueues = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, trackCount + 1);
        this.sampleQueues[trackCount] = trackOutput;
        this.sampleQueueIsAudioVideoFlags = Arrays.copyOf(this.sampleQueueIsAudioVideoFlags, trackCount + 1);
        this.sampleQueueIsAudioVideoFlags[trackCount] = type == 1 || type == 2;
        this.haveAudioVideoSampleQueues |= this.sampleQueueIsAudioVideoFlags[trackCount];
        if (type == 1) {
            this.audioSampleQueueMappingDone = true;
            this.audioSampleQueueIndex = trackCount;
        } else if (type == 2) {
            this.videoSampleQueueMappingDone = true;
            this.videoSampleQueueIndex = trackCount;
        }
        if (getTrackTypeScore(type) > getTrackTypeScore(this.primarySampleQueueType)) {
            this.primarySampleQueueIndex = trackCount;
            this.primarySampleQueueType = type;
        }
        this.sampleQueuesEnabledStates = Arrays.copyOf(this.sampleQueuesEnabledStates, trackCount + 1);
        return trackOutput;
    }

    public void endTracks() {
        this.tracksEnded = true;
        this.handler.post(this.onTracksEndedRunnable);
    }

    public void seekMap(SeekMap seekMap) {
    }

    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void setSampleOffsetUs(long sampleOffsetUs2) {
        this.sampleOffsetUs = sampleOffsetUs2;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.setSampleOffsetUs(sampleOffsetUs2);
        }
    }

    private void updateSampleStreams(SampleStream[] streams) {
        this.hlsSampleStreams.clear();
        for (SampleStream stream : streams) {
            if (stream != null) {
                this.hlsSampleStreams.add((HlsSampleStream) stream);
            }
        }
    }

    private boolean finishedReadingChunk(HlsMediaChunk chunk) {
        int chunkUid2 = chunk.uid;
        int sampleQueueCount = this.sampleQueues.length;
        for (int i = 0; i < sampleQueueCount; i++) {
            if (this.sampleQueuesEnabledStates[i] && this.sampleQueues[i].peekSourceId() == chunkUid2) {
                return false;
            }
        }
        return true;
    }

    private void resetSampleQueues() {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.reset(this.pendingResetUpstreamFormats);
        }
        this.pendingResetUpstreamFormats = false;
    }

    /* access modifiers changed from: private */
    /* renamed from: onTracksEnded */
    public void bridge$lambda$1$HlsSampleStreamWrapper() {
        this.sampleQueuesBuilt = true;
        bridge$lambda$0$HlsSampleStreamWrapper();
    }

    /* access modifiers changed from: private */
    /* renamed from: maybeFinishPrepare */
    public void bridge$lambda$0$HlsSampleStreamWrapper() {
        if (!this.released && this.trackGroupToSampleQueueIndex == null && this.sampleQueuesBuilt) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            int length = sampleQueueArr.length;
            int i = 0;
            while (i < length) {
                if (sampleQueueArr[i].getUpstreamFormat() != null) {
                    i++;
                } else {
                    return;
                }
            }
            if (this.trackGroups != null) {
                mapSampleQueuesToMatchTrackGroups();
                return;
            }
            buildTracksFromSampleStreams();
            this.prepared = true;
            this.callback.onPrepared();
        }
    }

    private void mapSampleQueuesToMatchTrackGroups() {
        int trackGroupCount = this.trackGroups.length;
        this.trackGroupToSampleQueueIndex = new int[trackGroupCount];
        Arrays.fill(this.trackGroupToSampleQueueIndex, -1);
        for (int i = 0; i < trackGroupCount; i++) {
            int queueIndex = 0;
            while (true) {
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                if (queueIndex >= sampleQueueArr.length) {
                    break;
                } else if (formatsMatch(sampleQueueArr[queueIndex].getUpstreamFormat(), this.trackGroups.get(i).getFormat(0))) {
                    this.trackGroupToSampleQueueIndex[i] = queueIndex;
                    break;
                } else {
                    queueIndex++;
                }
            }
        }
        Iterator<HlsSampleStream> it = this.hlsSampleStreams.iterator();
        while (it.hasNext()) {
            it.next().bindSampleQueue();
        }
    }

    /* JADX INFO: Multiple debug info for r4v4 com.google.android.exoplayer2.source.TrackGroup[]: [D('i' int), D('trackGroups' com.google.android.exoplayer2.source.TrackGroup[])] */
    private void buildTracksFromSampleStreams() {
        boolean z;
        Format trackFormat;
        int trackType2;
        int primaryExtractorTrackType = 6;
        int primaryExtractorTrackIndex = -1;
        int extractorTrackCount = this.sampleQueues.length;
        for (int i = 0; i < extractorTrackCount; i++) {
            String sampleMimeType = this.sampleQueues[i].getUpstreamFormat().sampleMimeType;
            if (MimeTypes.isVideo(sampleMimeType)) {
                trackType2 = 2;
            } else if (MimeTypes.isAudio(sampleMimeType) != 0) {
                trackType2 = 1;
            } else if (MimeTypes.isText(sampleMimeType) != 0) {
                trackType2 = 3;
            } else {
                trackType2 = 6;
            }
            if (getTrackTypeScore(trackType2) > getTrackTypeScore(primaryExtractorTrackType)) {
                primaryExtractorTrackType = trackType2;
                primaryExtractorTrackIndex = i;
            } else if (trackType2 == primaryExtractorTrackType && primaryExtractorTrackIndex != -1) {
                primaryExtractorTrackIndex = -1;
            }
        }
        TrackGroup chunkSourceTrackGroup = this.chunkSource.getTrackGroup();
        int chunkSourceTrackCount = chunkSourceTrackGroup.length;
        this.primaryTrackGroupIndex = -1;
        this.trackGroupToSampleQueueIndex = new int[extractorTrackCount];
        for (int i2 = 0; i2 < extractorTrackCount; i2++) {
            this.trackGroupToSampleQueueIndex[i2] = i2;
        }
        TrackGroup[] trackGroups2 = new TrackGroup[extractorTrackCount];
        int i3 = 0;
        while (true) {
            z = false;
            if (i3 >= extractorTrackCount) {
                break;
            }
            Format sampleFormat = this.sampleQueues[i3].getUpstreamFormat();
            if (i3 == primaryExtractorTrackIndex) {
                Format[] formats = new Format[chunkSourceTrackCount];
                if (chunkSourceTrackCount == 1) {
                    formats[0] = sampleFormat.copyWithManifestFormatInfo(chunkSourceTrackGroup.getFormat(0));
                } else {
                    for (int j = 0; j < chunkSourceTrackCount; j++) {
                        formats[j] = deriveFormat(chunkSourceTrackGroup.getFormat(j), sampleFormat, true);
                    }
                }
                trackGroups2[i3] = new TrackGroup(formats);
                this.primaryTrackGroupIndex = i3;
            } else {
                if (primaryExtractorTrackType != 2 || !MimeTypes.isAudio(sampleFormat.sampleMimeType)) {
                    trackFormat = null;
                } else {
                    trackFormat = this.muxedAudioFormat;
                }
                trackGroups2[i3] = new TrackGroup(deriveFormat(trackFormat, sampleFormat, false));
            }
            i3++;
        }
        this.trackGroups = new TrackGroupArray(trackGroups2);
        if (this.optionalTrackGroups == null) {
            z = true;
        }
        Assertions.checkState(z);
        this.optionalTrackGroups = TrackGroupArray.EMPTY;
    }

    private HlsMediaChunk getLastMediaChunk() {
        ArrayList<HlsMediaChunk> arrayList = this.mediaChunks;
        return arrayList.get(arrayList.size() - 1);
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C0841C.TIME_UNSET;
    }

    private boolean seekInsideBufferUs(long positionUs) {
        int sampleQueueCount = this.sampleQueues.length;
        int i = 0;
        while (true) {
            boolean seekInsideQueue = true;
            if (i >= sampleQueueCount) {
                return true;
            }
            SampleQueue sampleQueue = this.sampleQueues[i];
            sampleQueue.rewind();
            if (sampleQueue.advanceTo(positionUs, true, false) == -1) {
                seekInsideQueue = false;
            }
            if (seekInsideQueue || (!this.sampleQueueIsAudioVideoFlags[i] && this.haveAudioVideoSampleQueues)) {
                i++;
            }
        }
        return false;
    }

    private static int getTrackTypeScore(int trackType2) {
        if (trackType2 == 1) {
            return 2;
        }
        if (trackType2 == 2) {
            return 3;
        }
        if (trackType2 != 3) {
            return 0;
        }
        return 1;
    }

    private static Format deriveFormat(Format playlistFormat, Format sampleFormat, boolean propagateBitrate) {
        int channelCount;
        String mimeType;
        Format format = playlistFormat;
        Format format2 = sampleFormat;
        if (format == null) {
            return format2;
        }
        int bitrate = propagateBitrate ? format.bitrate : -1;
        if (format.channelCount != -1) {
            channelCount = format.channelCount;
        } else {
            channelCount = format2.channelCount;
        }
        String codecs = Util.getCodecsOfType(format.codecs, MimeTypes.getTrackType(format2.sampleMimeType));
        String mimeType2 = MimeTypes.getMediaMimeType(codecs);
        if (mimeType2 == null) {
            mimeType = format2.sampleMimeType;
        } else {
            mimeType = mimeType2;
        }
        return sampleFormat.copyWithContainerInfo(format.f72id, format.label, mimeType, codecs, format.metadata, bitrate, format.width, format.height, channelCount, format.selectionFlags, format.language);
    }

    private static boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof HlsMediaChunk;
    }

    private static boolean formatsMatch(Format manifestFormat, Format sampleFormat) {
        String manifestFormatMimeType = manifestFormat.sampleMimeType;
        String sampleFormatMimeType = sampleFormat.sampleMimeType;
        int manifestFormatTrackType = MimeTypes.getTrackType(manifestFormatMimeType);
        if (manifestFormatTrackType != 3) {
            if (manifestFormatTrackType == MimeTypes.getTrackType(sampleFormatMimeType)) {
                return true;
            }
            return false;
        } else if (!Util.areEqual(manifestFormatMimeType, sampleFormatMimeType)) {
            return false;
        } else {
            if ((MimeTypes.APPLICATION_CEA608.equals(manifestFormatMimeType) || MimeTypes.APPLICATION_CEA708.equals(manifestFormatMimeType)) && manifestFormat.accessibilityChannel != sampleFormat.accessibilityChannel) {
                return false;
            }
            return true;
        }
    }

    private static DummyTrackOutput createDummyTrackOutput(int id, int type) {
        StringBuilder sb = new StringBuilder(54);
        sb.append("Unmapped track with id ");
        sb.append(id);
        sb.append(" of type ");
        sb.append(type);
        Log.m30w(TAG, sb.toString());
        return new DummyTrackOutput();
    }

    private static final class PrivTimestampStrippingSampleQueue extends SampleQueue {
        public PrivTimestampStrippingSampleQueue(Allocator allocator) {
            super(allocator);
        }

        public void format(Format format) {
            super.format(format.copyWithMetadata(getAdjustedMetadata(format.metadata)));
        }

        @Nullable
        private Metadata getAdjustedMetadata(@Nullable Metadata metadata) {
            if (metadata == null) {
                return null;
            }
            int length = metadata.length();
            int transportStreamTimestampMetadataIndex = -1;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Metadata.Entry metadataEntry = metadata.get(i);
                if ((metadataEntry instanceof PrivFrame) && HlsMediaChunk.PRIV_TIMESTAMP_FRAME_OWNER.equals(((PrivFrame) metadataEntry).owner)) {
                    transportStreamTimestampMetadataIndex = i;
                    break;
                }
                i++;
            }
            if (transportStreamTimestampMetadataIndex == -1) {
                return metadata;
            }
            if (length == 1) {
                return null;
            }
            Metadata.Entry[] newMetadataEntries = new Metadata.Entry[(length - 1)];
            int i2 = 0;
            while (i2 < length) {
                if (i2 != transportStreamTimestampMetadataIndex) {
                    newMetadataEntries[i2 < transportStreamTimestampMetadataIndex ? i2 : i2 - 1] = metadata.get(i2);
                }
                i2++;
            }
            return new Metadata(newMetadataEntries);
        }
    }
}
