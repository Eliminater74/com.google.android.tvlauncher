package com.google.android.exoplayer2.source.chunk;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.source.SampleStream;
import com.google.android.exoplayer2.source.SequenceableLoader;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DefaultLoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChunkSampleStream<T extends ChunkSource> implements SampleStream, SequenceableLoader, Loader.Callback<Chunk>, Loader.ReleaseCallback {
    private static final String TAG = "ChunkSampleStream";
    /* access modifiers changed from: private */
    public final Format[] embeddedTrackFormats;
    /* access modifiers changed from: private */
    public final int[] embeddedTrackTypes;
    /* access modifiers changed from: private */
    public final boolean[] embeddedTracksSelected;
    /* access modifiers changed from: private */
    public final MediaSourceEventListener.EventDispatcher eventDispatcher;
    public final int primaryTrackType;
    private final SequenceableLoader.Callback<ChunkSampleStream<T>> callback;
    private final T chunkSource;
    private final SampleQueue[] embeddedSampleQueues;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Loader loader;
    private final BaseMediaChunkOutput mediaChunkOutput;
    private final ArrayList<BaseMediaChunk> mediaChunks;
    private final ChunkHolder nextChunkHolder;
    private final SampleQueue primarySampleQueue;
    private final List<BaseMediaChunk> readOnlyMediaChunks;
    /* access modifiers changed from: private */
    public long lastSeekPositionUs;
    long decodeOnlyUntilPositionUs;
    boolean loadingFinished;
    private int nextNotifyPrimaryFormatMediaChunkIndex;
    private long pendingResetPositionUs;
    private Format primaryDownstreamTrackFormat;
    @Nullable
    private ReleaseCallback<T> releaseCallback;

    @Deprecated
    public ChunkSampleStream(int primaryTrackType2, int[] embeddedTrackTypes2, Format[] embeddedTrackFormats2, T chunkSource2, SequenceableLoader.Callback<ChunkSampleStream<T>> callback2, Allocator allocator, long positionUs, int minLoadableRetryCount, MediaSourceEventListener.EventDispatcher eventDispatcher2) {
        this(primaryTrackType2, embeddedTrackTypes2, embeddedTrackFormats2, chunkSource2, callback2, allocator, positionUs, new DefaultLoadErrorHandlingPolicy(minLoadableRetryCount), eventDispatcher2);
    }

    public ChunkSampleStream(int primaryTrackType2, int[] embeddedTrackTypes2, Format[] embeddedTrackFormats2, T chunkSource2, SequenceableLoader.Callback<ChunkSampleStream<T>> callback2, Allocator allocator, long positionUs, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2) {
        int i = primaryTrackType2;
        int[] iArr = embeddedTrackTypes2;
        Allocator allocator2 = allocator;
        long j = positionUs;
        this.primaryTrackType = i;
        this.embeddedTrackTypes = iArr;
        this.embeddedTrackFormats = embeddedTrackFormats2;
        this.chunkSource = chunkSource2;
        this.callback = callback2;
        this.eventDispatcher = eventDispatcher2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.loader = new Loader("Loader:ChunkSampleStream");
        this.nextChunkHolder = new ChunkHolder();
        this.mediaChunks = new ArrayList<>();
        this.readOnlyMediaChunks = Collections.unmodifiableList(this.mediaChunks);
        int embeddedTrackCount = iArr == null ? 0 : iArr.length;
        this.embeddedSampleQueues = new SampleQueue[embeddedTrackCount];
        this.embeddedTracksSelected = new boolean[embeddedTrackCount];
        int[] trackTypes = new int[(embeddedTrackCount + 1)];
        SampleQueue[] sampleQueues = new SampleQueue[(embeddedTrackCount + 1)];
        this.primarySampleQueue = new SampleQueue(allocator2);
        trackTypes[0] = i;
        sampleQueues[0] = this.primarySampleQueue;
        for (int i2 = 0; i2 < embeddedTrackCount; i2++) {
            SampleQueue sampleQueue = new SampleQueue(allocator2);
            this.embeddedSampleQueues[i2] = sampleQueue;
            sampleQueues[i2 + 1] = sampleQueue;
            trackTypes[i2 + 1] = iArr[i2];
        }
        this.mediaChunkOutput = new BaseMediaChunkOutput(trackTypes, sampleQueues);
        this.pendingResetPositionUs = j;
        this.lastSeekPositionUs = j;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        if (!isPendingReset()) {
            int oldFirstSampleIndex = this.primarySampleQueue.getFirstIndex();
            this.primarySampleQueue.discardTo(positionUs, toKeyframe, true);
            int newFirstSampleIndex = this.primarySampleQueue.getFirstIndex();
            if (newFirstSampleIndex > oldFirstSampleIndex) {
                long discardToUs = this.primarySampleQueue.getFirstTimestampUs();
                int i = 0;
                while (true) {
                    SampleQueue[] sampleQueueArr = this.embeddedSampleQueues;
                    if (i >= sampleQueueArr.length) {
                        break;
                    }
                    sampleQueueArr[i].discardTo(discardToUs, toKeyframe, this.embeddedTracksSelected[i]);
                    i++;
                }
            }
            discardDownstreamMediaChunks(newFirstSampleIndex);
        }
    }

    public ChunkSampleStream<T>.EmbeddedSampleStream selectEmbeddedTrack(long positionUs, int trackType) {
        for (int i = 0; i < this.embeddedSampleQueues.length; i++) {
            if (this.embeddedTrackTypes[i] == trackType) {
                Assertions.checkState(!this.embeddedTracksSelected[i]);
                this.embeddedTracksSelected[i] = true;
                this.embeddedSampleQueues[i].rewind();
                this.embeddedSampleQueues[i].advanceTo(positionUs, true, true);
                return new EmbeddedSampleStream(this, this.embeddedSampleQueues[i], i);
            }
        }
        throw new IllegalStateException();
    }

    public T getChunkSource() {
        return this.chunkSource;
    }

    public long getBufferedPositionUs() {
        BaseMediaChunk lastCompletedMediaChunk;
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long bufferedPositionUs = this.lastSeekPositionUs;
        BaseMediaChunk lastMediaChunk = getLastMediaChunk();
        if (lastMediaChunk.isLoadCompleted()) {
            lastCompletedMediaChunk = lastMediaChunk;
        } else if (this.mediaChunks.size() > 1) {
            ArrayList<BaseMediaChunk> arrayList = this.mediaChunks;
            lastCompletedMediaChunk = arrayList.get(arrayList.size() - 2);
        } else {
            lastCompletedMediaChunk = null;
        }
        if (lastCompletedMediaChunk != null) {
            bufferedPositionUs = Math.max(bufferedPositionUs, lastCompletedMediaChunk.endTimeUs);
        }
        return Math.max(bufferedPositionUs, this.primarySampleQueue.getLargestQueuedTimestampUs());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public long getAdjustedSeekPositionUs(long r3, com.google.android.exoplayer2.SeekParameters r5) {
        /*
            r2 = this;
            T r0 = r2.chunkSource
            long r0 = r0.getAdjustedSeekPositionUs(r3, r5)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.getAdjustedSeekPositionUs(long, com.google.android.exoplayer2.SeekParameters):long");
    }

    public void seekToUs(long positionUs) {
        boolean seekInsideBuffer;
        this.lastSeekPositionUs = positionUs;
        if (isPendingReset()) {
            this.pendingResetPositionUs = positionUs;
            return;
        }
        BaseMediaChunk seekToMediaChunk = null;
        int i = 0;
        while (true) {
            if (i >= this.mediaChunks.size()) {
                break;
            }
            BaseMediaChunk mediaChunk = this.mediaChunks.get(i);
            long mediaChunkStartTimeUs = mediaChunk.startTimeUs;
            if (mediaChunkStartTimeUs == positionUs && mediaChunk.clippedStartTimeUs == C0841C.TIME_UNSET) {
                seekToMediaChunk = mediaChunk;
                break;
            } else if (mediaChunkStartTimeUs > positionUs) {
                break;
            } else {
                i++;
            }
        }
        this.primarySampleQueue.rewind();
        if (seekToMediaChunk != null) {
            seekInsideBuffer = this.primarySampleQueue.setReadPosition(seekToMediaChunk.getFirstSampleIndex(0));
            this.decodeOnlyUntilPositionUs = 0;
        } else {
            seekInsideBuffer = this.primarySampleQueue.advanceTo(positionUs, true, (positionUs > getNextLoadPositionUs() ? 1 : (positionUs == getNextLoadPositionUs() ? 0 : -1)) < 0) != -1;
            this.decodeOnlyUntilPositionUs = this.lastSeekPositionUs;
        }
        if (seekInsideBuffer) {
            this.nextNotifyPrimaryFormatMediaChunkIndex = primarySampleIndexToMediaChunkIndex(this.primarySampleQueue.getReadIndex(), 0);
            for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
                embeddedSampleQueue.rewind();
                embeddedSampleQueue.advanceTo(positionUs, true, false);
            }
            return;
        }
        this.pendingResetPositionUs = positionUs;
        this.loadingFinished = false;
        this.mediaChunks.clear();
        this.nextNotifyPrimaryFormatMediaChunkIndex = 0;
        if (this.loader.isLoading()) {
            this.loader.cancelLoading();
            return;
        }
        this.primarySampleQueue.reset();
        for (SampleQueue embeddedSampleQueue2 : this.embeddedSampleQueues) {
            embeddedSampleQueue2.reset();
        }
    }

    public void release() {
        release(null);
    }

    public void release(@Nullable ReleaseCallback<T> callback2) {
        this.releaseCallback = callback2;
        this.primarySampleQueue.discardToEnd();
        for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
            embeddedSampleQueue.discardToEnd();
        }
        this.loader.release(this);
    }

    public void onLoaderReleased() {
        this.primarySampleQueue.reset();
        for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
            embeddedSampleQueue.reset();
        }
        ReleaseCallback<T> releaseCallback2 = this.releaseCallback;
        if (releaseCallback2 != null) {
            releaseCallback2.onSampleStreamReleased(this);
        }
    }

    public boolean isReady() {
        return this.loadingFinished || (!isPendingReset() && this.primarySampleQueue.hasNextSample());
    }

    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError();
        if (!this.loader.isLoading()) {
            this.chunkSource.maybeThrowError();
        }
    }

    public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
        if (isPendingReset()) {
            return -3;
        }
        maybeNotifyPrimaryTrackFormatChanged();
        return this.primarySampleQueue.read(formatHolder, buffer, formatRequired, this.loadingFinished, this.decodeOnlyUntilPositionUs);
    }

    public int skipData(long positionUs) {
        int skipCount;
        if (isPendingReset()) {
            return 0;
        }
        if (!this.loadingFinished || positionUs <= this.primarySampleQueue.getLargestQueuedTimestampUs()) {
            skipCount = this.primarySampleQueue.advanceTo(positionUs, true, true);
            if (skipCount == -1) {
                skipCount = 0;
            }
        } else {
            skipCount = this.primarySampleQueue.advanceToEnd();
        }
        maybeNotifyPrimaryTrackFormatChanged();
        return skipCount;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public void onLoadCompleted(com.google.android.exoplayer2.source.chunk.Chunk r22, long r23, long r25) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r15 = r23
            r17 = r25
            T r2 = r0.chunkSource
            r2.onChunkLoadCompleted(r1)
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r2 = r0.eventDispatcher
            com.google.android.exoplayer2.upstream.DataSpec r3 = r1.dataSpec
            android.net.Uri r4 = r22.getUri()
            java.util.Map r5 = r22.getResponseHeaders()
            int r6 = r1.type
            int r7 = r0.primaryTrackType
            com.google.android.exoplayer2.Format r8 = r1.trackFormat
            int r9 = r1.trackSelectionReason
            java.lang.Object r10 = r1.trackSelectionData
            long r11 = r1.startTimeUs
            long r13 = r1.endTimeUs
            long r19 = r22.bytesLoaded()
            r2.loadCompleted(r3, r4, r5, r6, r7, r8, r9, r10, r11, r13, r15, r17, r19)
            com.google.android.exoplayer2.source.SequenceableLoader$Callback<com.google.android.exoplayer2.source.chunk.ChunkSampleStream<T>> r2 = r0.callback
            r2.onContinueLoadingRequested(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.onLoadCompleted(com.google.android.exoplayer2.source.chunk.Chunk, long, long):void");
    }

    public void onLoadCanceled(Chunk loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released) {
        Chunk chunk = loadable;
        this.eventDispatcher.loadCanceled(chunk.dataSpec, loadable.getUri(), loadable.getResponseHeaders(), chunk.type, this.primaryTrackType, chunk.trackFormat, chunk.trackSelectionReason, chunk.trackSelectionData, chunk.startTimeUs, chunk.endTimeUs, elapsedRealtimeMs, loadDurationMs, loadable.bytesLoaded());
        if (!released) {
            this.primarySampleQueue.reset();
            for (SampleQueue embeddedSampleQueue : this.embeddedSampleQueues) {
                embeddedSampleQueue.reset();
            }
            this.callback.onContinueLoadingRequested(this);
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public com.google.android.exoplayer2.upstream.Loader.LoadErrorAction onLoadError(com.google.android.exoplayer2.source.chunk.Chunk r35, long r36, long r38, java.io.IOException r40, int r41) {
        /*
            r34 = this;
            r0 = r34
            r7 = r35
            long r29 = r35.bytesLoaded()
            boolean r31 = r34.isMediaChunk(r35)
            java.util.ArrayList<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r1 = r0.mediaChunks
            int r1 = r1.size()
            r8 = 1
            int r15 = r1 + -1
            r9 = 0
            r1 = 0
            int r3 = (r29 > r1 ? 1 : (r29 == r1 ? 0 : -1))
            if (r3 == 0) goto L_0x0027
            if (r31 == 0) goto L_0x0027
            boolean r1 = r0.haveReadFromMediaChunk(r15)
            if (r1 != 0) goto L_0x0025
            goto L_0x0027
        L_0x0025:
            r1 = 0
            goto L_0x0028
        L_0x0027:
            r1 = 1
        L_0x0028:
            r32 = r1
            r10 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r32 == 0) goto L_0x0041
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r1 = r0.loadErrorHandlingPolicy
            int r2 = r7.type
            r3 = r38
            r5 = r40
            r6 = r41
            long r1 = r1.getBlacklistDurationMsFor(r2, r3, r5, r6)
            r5 = r1
            goto L_0x0042
        L_0x0041:
            r5 = r10
        L_0x0042:
            r12 = 0
            T r1 = r0.chunkSource
            r2 = r35
            r3 = r32
            r4 = r40
            boolean r1 = r1.onChunkLoadError(r2, r3, r4, r5)
            if (r1 == 0) goto L_0x0078
            if (r32 == 0) goto L_0x0071
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r12 = com.google.android.exoplayer2.upstream.Loader.DONT_RETRY
            if (r31 == 0) goto L_0x0078
            com.google.android.exoplayer2.source.chunk.BaseMediaChunk r1 = r0.discardUpstreamMediaChunksFromIndex(r15)
            if (r1 != r7) goto L_0x0060
            r2 = 1
            goto L_0x0061
        L_0x0060:
            r2 = 0
        L_0x0061:
            com.google.android.exoplayer2.util.Assertions.checkState(r2)
            java.util.ArrayList<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r2 = r0.mediaChunks
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0070
            long r2 = r0.lastSeekPositionUs
            r0.pendingResetPositionUs = r2
        L_0x0070:
            goto L_0x0078
        L_0x0071:
            java.lang.String r1 = "ChunkSampleStream"
            java.lang.String r2 = "Ignoring attempt to cancel non-cancelable load."
            com.google.android.exoplayer2.util.Log.m30w(r1, r2)
        L_0x0078:
            if (r12 != 0) goto L_0x009a
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r1 = r0.loadErrorHandlingPolicy
            int r2 = r7.type
            r16 = r1
            r17 = r2
            r18 = r38
            r20 = r40
            r21 = r41
            long r1 = r16.getRetryDelayMsFor(r17, r18, r20, r21)
            int r3 = (r1 > r10 ? 1 : (r1 == r10 ? 0 : -1))
            if (r3 == 0) goto L_0x0095
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r3 = com.google.android.exoplayer2.upstream.Loader.createRetryAction(r9, r1)
            goto L_0x0097
        L_0x0095:
            com.google.android.exoplayer2.upstream.Loader$LoadErrorAction r3 = com.google.android.exoplayer2.upstream.Loader.DONT_RETRY_FATAL
        L_0x0097:
            r12 = r3
            r1 = r12
            goto L_0x009b
        L_0x009a:
            r1 = r12
        L_0x009b:
            boolean r2 = r1.isRetry()
            r2 = r2 ^ r8
            r28 = r2
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r8 = r0.eventDispatcher
            com.google.android.exoplayer2.upstream.DataSpec r9 = r7.dataSpec
            android.net.Uri r10 = r35.getUri()
            java.util.Map r11 = r35.getResponseHeaders()
            int r12 = r7.type
            int r13 = r0.primaryTrackType
            com.google.android.exoplayer2.Format r14 = r7.trackFormat
            int r3 = r7.trackSelectionReason
            r4 = r15
            r15 = r3
            java.lang.Object r3 = r7.trackSelectionData
            r16 = r3
            r33 = r4
            long r3 = r7.startTimeUs
            r17 = r3
            long r3 = r7.endTimeUs
            r19 = r3
            r21 = r36
            r23 = r38
            r25 = r29
            r27 = r40
            r8.loadError(r9, r10, r11, r12, r13, r14, r15, r16, r17, r19, r21, r23, r25, r27, r28)
            if (r2 == 0) goto L_0x00d8
            com.google.android.exoplayer2.source.SequenceableLoader$Callback<com.google.android.exoplayer2.source.chunk.ChunkSampleStream<T>> r3 = r0.callback
            r3.onContinueLoadingRequested(r0)
        L_0x00d8:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.onLoadError(com.google.android.exoplayer2.source.chunk.Chunk, long, long, java.io.IOException, int):com.google.android.exoplayer2.upstream.Loader$LoadErrorAction");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public boolean continueLoading(long r28) {
        /*
            r27 = this;
            r0 = r27
            boolean r1 = r0.loadingFinished
            r2 = 0
            if (r1 != 0) goto L_0x00ab
            com.google.android.exoplayer2.upstream.Loader r1 = r0.loader
            boolean r1 = r1.isLoading()
            if (r1 == 0) goto L_0x0011
            goto L_0x00ab
        L_0x0011:
            boolean r1 = r27.isPendingReset()
            if (r1 == 0) goto L_0x001e
            java.util.List r3 = java.util.Collections.emptyList()
            long r4 = r0.pendingResetPositionUs
            goto L_0x0026
        L_0x001e:
            java.util.List<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r3 = r0.readOnlyMediaChunks
            com.google.android.exoplayer2.source.chunk.BaseMediaChunk r4 = r27.getLastMediaChunk()
            long r4 = r4.endTimeUs
        L_0x0026:
            T r6 = r0.chunkSource
            com.google.android.exoplayer2.source.chunk.ChunkHolder r12 = r0.nextChunkHolder
            r7 = r28
            r9 = r4
            r11 = r3
            r6.getNextChunk(r7, r9, r11, r12)
            com.google.android.exoplayer2.source.chunk.ChunkHolder r6 = r0.nextChunkHolder
            boolean r6 = r6.endOfStream
            com.google.android.exoplayer2.source.chunk.ChunkHolder r7 = r0.nextChunkHolder
            com.google.android.exoplayer2.source.chunk.Chunk r7 = r7.chunk
            com.google.android.exoplayer2.source.chunk.ChunkHolder r8 = r0.nextChunkHolder
            r8.clear()
            r8 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r10 = 1
            if (r6 == 0) goto L_0x004b
            r0.pendingResetPositionUs = r8
            r0.loadingFinished = r10
            return r10
        L_0x004b:
            if (r7 != 0) goto L_0x004e
            return r2
        L_0x004e:
            boolean r11 = r0.isMediaChunk(r7)
            if (r11 == 0) goto L_0x0077
            r11 = r7
            com.google.android.exoplayer2.source.chunk.BaseMediaChunk r11 = (com.google.android.exoplayer2.source.chunk.BaseMediaChunk) r11
            if (r1 == 0) goto L_0x006d
            long r12 = r11.startTimeUs
            long r14 = r0.pendingResetPositionUs
            int r16 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r16 != 0) goto L_0x0062
            r2 = 1
        L_0x0062:
            if (r2 == 0) goto L_0x0067
            r12 = 0
            goto L_0x0069
        L_0x0067:
            long r12 = r0.pendingResetPositionUs
        L_0x0069:
            r0.decodeOnlyUntilPositionUs = r12
            r0.pendingResetPositionUs = r8
        L_0x006d:
            com.google.android.exoplayer2.source.chunk.BaseMediaChunkOutput r2 = r0.mediaChunkOutput
            r11.init(r2)
            java.util.ArrayList<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r2 = r0.mediaChunks
            r2.add(r11)
        L_0x0077:
            com.google.android.exoplayer2.upstream.Loader r2 = r0.loader
            com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy r8 = r0.loadErrorHandlingPolicy
            int r9 = r7.type
            int r8 = r8.getMinimumLoadableRetryCount(r9)
            long r8 = r2.startLoading(r7, r0, r8)
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r11 = r0.eventDispatcher
            com.google.android.exoplayer2.upstream.DataSpec r12 = r7.dataSpec
            int r13 = r7.type
            int r14 = r0.primaryTrackType
            com.google.android.exoplayer2.Format r15 = r7.trackFormat
            int r2 = r7.trackSelectionReason
            java.lang.Object r10 = r7.trackSelectionData
            r24 = r1
            long r0 = r7.startTimeUs
            r25 = r4
            r5 = r3
            long r3 = r7.endTimeUs
            r16 = r2
            r17 = r10
            r18 = r0
            r20 = r3
            r22 = r8
            r11.loadStarted(r12, r13, r14, r15, r16, r17, r18, r20, r22)
            r0 = 1
            return r0
        L_0x00ab:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.continueLoading(long):boolean");
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

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public void reevaluateBuffer(long r13) {
        /*
            r12 = this;
            com.google.android.exoplayer2.upstream.Loader r0 = r12.loader
            boolean r0 = r0.isLoading()
            if (r0 != 0) goto L_0x0056
            boolean r0 = r12.isPendingReset()
            if (r0 == 0) goto L_0x000f
            goto L_0x0056
        L_0x000f:
            java.util.ArrayList<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r0 = r12.mediaChunks
            int r0 = r0.size()
            T r1 = r12.chunkSource
            java.util.List<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r2 = r12.readOnlyMediaChunks
            int r1 = r1.getPreferredQueueSize(r13, r2)
            if (r0 > r1) goto L_0x0020
            return
        L_0x0020:
            r2 = r0
            r3 = r1
        L_0x0022:
            if (r3 >= r0) goto L_0x002f
            boolean r4 = r12.haveReadFromMediaChunk(r3)
            if (r4 != 0) goto L_0x002c
            r2 = r3
            goto L_0x002f
        L_0x002c:
            int r3 = r3 + 1
            goto L_0x0022
        L_0x002f:
            if (r2 != r0) goto L_0x0032
            return
        L_0x0032:
            com.google.android.exoplayer2.source.chunk.BaseMediaChunk r3 = r12.getLastMediaChunk()
            long r10 = r3.endTimeUs
            com.google.android.exoplayer2.source.chunk.BaseMediaChunk r3 = r12.discardUpstreamMediaChunksFromIndex(r2)
            java.util.ArrayList<com.google.android.exoplayer2.source.chunk.BaseMediaChunk> r4 = r12.mediaChunks
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L_0x0048
            long r4 = r12.lastSeekPositionUs
            r12.pendingResetPositionUs = r4
        L_0x0048:
            r4 = 0
            r12.loadingFinished = r4
            com.google.android.exoplayer2.source.MediaSourceEventListener$EventDispatcher r4 = r12.eventDispatcher
            int r5 = r12.primaryTrackType
            long r6 = r3.startTimeUs
            r8 = r10
            r4.upstreamDiscarded(r5, r6, r8)
            return
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.chunk.ChunkSampleStream.reevaluateBuffer(long):void");
    }

    private boolean isMediaChunk(Chunk chunk) {
        return chunk instanceof BaseMediaChunk;
    }

    private boolean haveReadFromMediaChunk(int mediaChunkIndex) {
        BaseMediaChunk mediaChunk = this.mediaChunks.get(mediaChunkIndex);
        if (this.primarySampleQueue.getReadIndex() > mediaChunk.getFirstSampleIndex(0)) {
            return true;
        }
        int i = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.embeddedSampleQueues;
            if (i >= sampleQueueArr.length) {
                return false;
            }
            if (sampleQueueArr[i].getReadIndex() > mediaChunk.getFirstSampleIndex(i + 1)) {
                return true;
            }
            i++;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isPendingReset() {
        return this.pendingResetPositionUs != C0841C.TIME_UNSET;
    }

    private void discardDownstreamMediaChunks(int discardToSampleIndex) {
        int discardToMediaChunkIndex = Math.min(primarySampleIndexToMediaChunkIndex(discardToSampleIndex, 0), this.nextNotifyPrimaryFormatMediaChunkIndex);
        if (discardToMediaChunkIndex > 0) {
            Util.removeRange(this.mediaChunks, 0, discardToMediaChunkIndex);
            this.nextNotifyPrimaryFormatMediaChunkIndex -= discardToMediaChunkIndex;
        }
    }

    private void maybeNotifyPrimaryTrackFormatChanged() {
        int notifyToMediaChunkIndex = primarySampleIndexToMediaChunkIndex(this.primarySampleQueue.getReadIndex(), this.nextNotifyPrimaryFormatMediaChunkIndex - 1);
        while (true) {
            int i = this.nextNotifyPrimaryFormatMediaChunkIndex;
            if (i <= notifyToMediaChunkIndex) {
                this.nextNotifyPrimaryFormatMediaChunkIndex = i + 1;
                maybeNotifyPrimaryTrackFormatChanged(i);
            } else {
                return;
            }
        }
    }

    private void maybeNotifyPrimaryTrackFormatChanged(int mediaChunkReadIndex) {
        BaseMediaChunk currentChunk = this.mediaChunks.get(mediaChunkReadIndex);
        Format trackFormat = currentChunk.trackFormat;
        if (!trackFormat.equals(this.primaryDownstreamTrackFormat)) {
            this.eventDispatcher.downstreamFormatChanged(this.primaryTrackType, trackFormat, currentChunk.trackSelectionReason, currentChunk.trackSelectionData, currentChunk.startTimeUs);
        }
        this.primaryDownstreamTrackFormat = trackFormat;
    }

    private int primarySampleIndexToMediaChunkIndex(int primarySampleIndex, int minChunkIndex) {
        for (int i = minChunkIndex + 1; i < this.mediaChunks.size(); i++) {
            if (this.mediaChunks.get(i).getFirstSampleIndex(0) > primarySampleIndex) {
                return i - 1;
            }
        }
        return this.mediaChunks.size() - 1;
    }

    private BaseMediaChunk getLastMediaChunk() {
        ArrayList<BaseMediaChunk> arrayList = this.mediaChunks;
        return arrayList.get(arrayList.size() - 1);
    }

    private BaseMediaChunk discardUpstreamMediaChunksFromIndex(int chunkIndex) {
        BaseMediaChunk firstRemovedChunk = this.mediaChunks.get(chunkIndex);
        ArrayList<BaseMediaChunk> arrayList = this.mediaChunks;
        Util.removeRange(arrayList, chunkIndex, arrayList.size());
        this.nextNotifyPrimaryFormatMediaChunkIndex = Math.max(this.nextNotifyPrimaryFormatMediaChunkIndex, this.mediaChunks.size());
        this.primarySampleQueue.discardUpstreamSamples(firstRemovedChunk.getFirstSampleIndex(0));
        int i = 0;
        while (true) {
            SampleQueue[] sampleQueueArr = this.embeddedSampleQueues;
            if (i >= sampleQueueArr.length) {
                return firstRemovedChunk;
            }
            sampleQueueArr[i].discardUpstreamSamples(firstRemovedChunk.getFirstSampleIndex(i + 1));
            i++;
        }
    }

    public interface ReleaseCallback<T extends ChunkSource> {
        void onSampleStreamReleased(ChunkSampleStream<T> chunkSampleStream);
    }

    public final class EmbeddedSampleStream implements SampleStream {
        public final ChunkSampleStream<T> parent;
        private final int index;
        private final SampleQueue sampleQueue;
        private boolean notifiedDownstreamFormat;

        public EmbeddedSampleStream(ChunkSampleStream<T> parent2, SampleQueue sampleQueue2, int index2) {
            this.parent = parent2;
            this.sampleQueue = sampleQueue2;
            this.index = index2;
        }

        public boolean isReady() {
            return ChunkSampleStream.this.loadingFinished || (!ChunkSampleStream.this.isPendingReset() && this.sampleQueue.hasNextSample());
        }

        public int skipData(long positionUs) {
            if (ChunkSampleStream.this.isPendingReset()) {
                return 0;
            }
            maybeNotifyDownstreamFormat();
            if (ChunkSampleStream.this.loadingFinished && positionUs > this.sampleQueue.getLargestQueuedTimestampUs()) {
                return this.sampleQueue.advanceToEnd();
            }
            int skipCount = this.sampleQueue.advanceTo(positionUs, true, true);
            if (skipCount == -1) {
                return 0;
            }
            return skipCount;
        }

        public void maybeThrowError() throws IOException {
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
            if (ChunkSampleStream.this.isPendingReset()) {
                return -3;
            }
            maybeNotifyDownstreamFormat();
            return this.sampleQueue.read(formatHolder, buffer, formatRequired, ChunkSampleStream.this.loadingFinished, ChunkSampleStream.this.decodeOnlyUntilPositionUs);
        }

        public void release() {
            Assertions.checkState(ChunkSampleStream.this.embeddedTracksSelected[this.index]);
            ChunkSampleStream.this.embeddedTracksSelected[this.index] = false;
        }

        private void maybeNotifyDownstreamFormat() {
            if (!this.notifiedDownstreamFormat) {
                ChunkSampleStream.this.eventDispatcher.downstreamFormatChanged(ChunkSampleStream.this.embeddedTrackTypes[this.index], ChunkSampleStream.this.embeddedTrackFormats[this.index], 0, null, ChunkSampleStream.this.lastSeekPositionUs);
                this.notifiedDownstreamFormat = true;
            }
        }
    }
}
