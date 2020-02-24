package com.google.android.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.FormatHolder;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.decoder.DecoderInputBuffer;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.IcyDataSource;
import com.google.android.exoplayer2.source.MediaPeriod;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.SampleQueue;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.Allocator;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.upstream.Loader;
import com.google.android.exoplayer2.upstream.StatsDataSource;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ConditionVariable;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.EOFException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

final class ProgressiveMediaPeriod implements MediaPeriod, ExtractorOutput, Loader.Callback<ExtractingLoadable>, Loader.ReleaseCallback, SampleQueue.UpstreamFormatChangedListener {
    private static final long DEFAULT_LAST_SAMPLE_DURATION_US = 10000;
    /* access modifiers changed from: private */
    public static final Format ICY_FORMAT = Format.createSampleFormat("icy", MimeTypes.APPLICATION_ICY, Long.MAX_VALUE);
    private final Allocator allocator;
    @Nullable
    private MediaPeriod.Callback callback;
    /* access modifiers changed from: private */
    public final long continueLoadingCheckIntervalBytes;
    /* access modifiers changed from: private */
    @Nullable
    public final String customCacheKey;
    private final DataSource dataSource;
    private int dataType;
    private long durationUs;
    private int enabledTrackCount;
    private final MediaSourceEventListener.EventDispatcher eventDispatcher;
    private int extractedSamplesCountAtStartOfLoad;
    private final ExtractorHolder extractorHolder;
    /* access modifiers changed from: private */
    public final Handler handler;
    private boolean haveAudioVideoTracks;
    /* access modifiers changed from: private */
    @Nullable
    public IcyHeaders icyHeaders;
    private long lastSeekPositionUs;
    private long length;
    private final Listener listener;
    private final ConditionVariable loadCondition;
    private final LoadErrorHandlingPolicy loadErrorHandlingPolicy;
    private final Loader loader = new Loader("Loader:ProgressiveMediaPeriod");
    private boolean loadingFinished;
    private final Runnable maybeFinishPrepareRunnable;
    private boolean notifiedReadingStarted;
    private boolean notifyDiscontinuity;
    /* access modifiers changed from: private */
    public final Runnable onContinueLoadingRequestedRunnable;
    private boolean pendingDeferredRetry;
    private long pendingResetPositionUs;
    private boolean prepared;
    @Nullable
    private PreparedState preparedState;
    private boolean released;
    private TrackId[] sampleQueueTrackIds;
    private SampleQueue[] sampleQueues;
    private boolean sampleQueuesBuilt;
    @Nullable
    private SeekMap seekMap;
    private boolean seenFirstTrackSelection;
    private final Uri uri;

    interface Listener {
        void onSourceInfoRefreshed(long j, boolean z);
    }

    public List getStreamKeys(List list) {
        return MediaPeriod$$CC.getStreamKeys$$dflt$$(this, list);
    }

    public ProgressiveMediaPeriod(Uri uri2, DataSource dataSource2, Extractor[] extractors, LoadErrorHandlingPolicy loadErrorHandlingPolicy2, MediaSourceEventListener.EventDispatcher eventDispatcher2, Listener listener2, Allocator allocator2, @Nullable String customCacheKey2, int continueLoadingCheckIntervalBytes2) {
        this.uri = uri2;
        this.dataSource = dataSource2;
        this.loadErrorHandlingPolicy = loadErrorHandlingPolicy2;
        this.eventDispatcher = eventDispatcher2;
        this.listener = listener2;
        this.allocator = allocator2;
        this.customCacheKey = customCacheKey2;
        this.continueLoadingCheckIntervalBytes = (long) continueLoadingCheckIntervalBytes2;
        this.extractorHolder = new ExtractorHolder(extractors);
        this.loadCondition = new ConditionVariable();
        this.maybeFinishPrepareRunnable = new ProgressiveMediaPeriod$$Lambda$0(this);
        this.onContinueLoadingRequestedRunnable = new ProgressiveMediaPeriod$$Lambda$1(this);
        this.handler = new Handler();
        this.sampleQueueTrackIds = new TrackId[0];
        this.sampleQueues = new SampleQueue[0];
        this.pendingResetPositionUs = C0841C.TIME_UNSET;
        this.length = -1;
        this.durationUs = C0841C.TIME_UNSET;
        this.dataType = 1;
        eventDispatcher2.mediaPeriodCreated();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$ProgressiveMediaPeriod() {
        if (!this.released) {
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
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
        this.callback = null;
        this.released = true;
        this.eventDispatcher.mediaPeriodReleased();
    }

    public void onLoaderReleased() {
        for (SampleQueue sampleQueue : this.sampleQueues) {
            sampleQueue.reset();
        }
        this.extractorHolder.release();
    }

    public void prepare(MediaPeriod.Callback callback2, long positionUs) {
        this.callback = callback2;
        this.loadCondition.open();
        startLoading();
    }

    public void maybeThrowPrepareError() throws IOException {
        maybeThrowError();
        if (this.loadingFinished && !this.prepared) {
            throw new ParserException("Loading finished before preparation is complete.");
        }
    }

    public TrackGroupArray getTrackGroups() {
        return getPreparedState().tracks;
    }

    public long selectTracks(TrackSelection[] selections, boolean[] mayRetainStreamFlags, SampleStream[] streams, boolean[] streamResetFlags, long positionUs) {
        int i;
        boolean z;
        TrackSelection[] trackSelectionArr = selections;
        SampleStream[] sampleStreamArr = streams;
        long positionUs2 = positionUs;
        PreparedState preparedState2 = getPreparedState();
        TrackGroupArray tracks = preparedState2.tracks;
        boolean[] trackEnabledStates = preparedState2.trackEnabledStates;
        int oldEnabledTrackCount = this.enabledTrackCount;
        int i2 = 0;
        while (true) {
            i = 0;
            z = true;
            if (i2 >= trackSelectionArr.length) {
                break;
            }
            if (sampleStreamArr[i2] != null && (trackSelectionArr[i2] == null || !mayRetainStreamFlags[i2])) {
                int track = ((SampleStreamImpl) sampleStreamArr[i2]).track;
                Assertions.checkState(trackEnabledStates[track]);
                this.enabledTrackCount--;
                trackEnabledStates[track] = false;
                sampleStreamArr[i2] = null;
            }
            i2++;
        }
        boolean seekRequired = this.seenFirstTrackSelection == 0 ? positionUs2 != 0 : oldEnabledTrackCount == 0;
        int i3 = 0;
        while (i3 < trackSelectionArr.length) {
            if (sampleStreamArr[i3] == null && trackSelectionArr[i3] != null) {
                TrackSelection selection = trackSelectionArr[i3];
                Assertions.checkState(selection.length() == z);
                Assertions.checkState(selection.getIndexInTrackGroup(i) == 0);
                int track2 = tracks.indexOf(selection.getTrackGroup());
                Assertions.checkState(trackEnabledStates[track2] ^ z);
                this.enabledTrackCount += z ? 1 : 0;
                trackEnabledStates[track2] = z;
                sampleStreamArr[i3] = new SampleStreamImpl(track2);
                streamResetFlags[i3] = z;
                if (!seekRequired) {
                    SampleQueue sampleQueue = this.sampleQueues[track2];
                    sampleQueue.rewind();
                    seekRequired = sampleQueue.advanceTo(positionUs2, z, z) == -1 && sampleQueue.getReadIndex() != 0;
                }
            }
            i3++;
            i = 0;
            z = true;
        }
        if (this.enabledTrackCount == 0) {
            int i4 = 0;
            this.pendingDeferredRetry = false;
            this.notifyDiscontinuity = false;
            if (this.loader.isLoading()) {
                SampleQueue[] sampleQueueArr = this.sampleQueues;
                int length2 = sampleQueueArr.length;
                while (i4 < length2) {
                    sampleQueueArr[i4].discardToEnd();
                    i4++;
                }
                this.loader.cancelLoading();
            } else {
                SampleQueue[] sampleQueueArr2 = this.sampleQueues;
                int length3 = sampleQueueArr2.length;
                while (i4 < length3) {
                    sampleQueueArr2[i4].reset();
                    i4++;
                }
            }
        } else if (seekRequired) {
            positionUs2 = seekToUs(positionUs2);
            for (int i5 = 0; i5 < sampleStreamArr.length; i5++) {
                if (sampleStreamArr[i5] != null) {
                    streamResetFlags[i5] = true;
                }
            }
        }
        this.seenFirstTrackSelection = true;
        return positionUs2;
    }

    public void discardBuffer(long positionUs, boolean toKeyframe) {
        if (!isPendingReset()) {
            boolean[] trackEnabledStates = getPreparedState().trackEnabledStates;
            int trackCount = this.sampleQueues.length;
            for (int i = 0; i < trackCount; i++) {
                this.sampleQueues[i].discardTo(positionUs, toKeyframe, trackEnabledStates[i]);
            }
        }
    }

    public void reevaluateBuffer(long positionUs) {
    }

    public boolean continueLoading(long playbackPositionUs) {
        if (this.loadingFinished || this.pendingDeferredRetry) {
            return false;
        }
        if (this.prepared && this.enabledTrackCount == 0) {
            return false;
        }
        boolean continuedLoading = this.loadCondition.open();
        if (this.loader.isLoading()) {
            return continuedLoading;
        }
        startLoading();
        return true;
    }

    public long getNextLoadPositionUs() {
        if (this.enabledTrackCount == 0) {
            return Long.MIN_VALUE;
        }
        return getBufferedPositionUs();
    }

    public long readDiscontinuity() {
        if (!this.notifiedReadingStarted) {
            this.eventDispatcher.readingStarted();
            this.notifiedReadingStarted = true;
        }
        if (!this.notifyDiscontinuity) {
            return C0841C.TIME_UNSET;
        }
        if (!this.loadingFinished && getExtractedSamplesCount() <= this.extractedSamplesCountAtStartOfLoad) {
            return C0841C.TIME_UNSET;
        }
        this.notifyDiscontinuity = false;
        return this.lastSeekPositionUs;
    }

    public long getBufferedPositionUs() {
        boolean[] trackIsAudioVideoFlags = getPreparedState().trackIsAudioVideoFlags;
        if (this.loadingFinished) {
            return Long.MIN_VALUE;
        }
        if (isPendingReset()) {
            return this.pendingResetPositionUs;
        }
        long largestQueuedTimestampUs = Long.MAX_VALUE;
        if (this.haveAudioVideoTracks) {
            int trackCount = this.sampleQueues.length;
            for (int i = 0; i < trackCount; i++) {
                if (trackIsAudioVideoFlags[i] && !this.sampleQueues[i].isLastSampleQueued()) {
                    largestQueuedTimestampUs = Math.min(largestQueuedTimestampUs, this.sampleQueues[i].getLargestQueuedTimestampUs());
                }
            }
        }
        if (largestQueuedTimestampUs == Long.MAX_VALUE) {
            largestQueuedTimestampUs = getLargestQueuedTimestampUs();
        }
        if (largestQueuedTimestampUs == Long.MIN_VALUE) {
            return this.lastSeekPositionUs;
        }
        return largestQueuedTimestampUs;
    }

    public long seekToUs(long positionUs) {
        PreparedState preparedState2 = getPreparedState();
        SeekMap seekMap2 = preparedState2.seekMap;
        boolean[] trackIsAudioVideoFlags = preparedState2.trackIsAudioVideoFlags;
        long positionUs2 = seekMap2.isSeekable() ? positionUs : 0;
        this.notifyDiscontinuity = false;
        this.lastSeekPositionUs = positionUs2;
        if (isPendingReset()) {
            this.pendingResetPositionUs = positionUs2;
            return positionUs2;
        } else if (this.dataType != 7 && seekInsideBufferUs(trackIsAudioVideoFlags, positionUs2)) {
            return positionUs2;
        } else {
            this.pendingDeferredRetry = false;
            this.pendingResetPositionUs = positionUs2;
            this.loadingFinished = false;
            if (this.loader.isLoading()) {
                this.loader.cancelLoading();
            } else {
                for (SampleQueue sampleQueue : this.sampleQueues) {
                    sampleQueue.reset();
                }
            }
            return positionUs2;
        }
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        SeekMap seekMap2 = getPreparedState().seekMap;
        if (!seekMap2.isSeekable()) {
            return 0;
        }
        SeekMap.SeekPoints seekPoints = seekMap2.getSeekPoints(positionUs);
        return Util.resolveSeekPositionUs(positionUs, seekParameters, seekPoints.first.timeUs, seekPoints.second.timeUs);
    }

    /* access modifiers changed from: package-private */
    public boolean isReady(int track) {
        return !suppressRead() && (this.loadingFinished || this.sampleQueues[track].hasNextSample());
    }

    /* access modifiers changed from: package-private */
    public void maybeThrowError() throws IOException {
        this.loader.maybeThrowError(this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(this.dataType));
    }

    /* access modifiers changed from: package-private */
    public int readData(int track, FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
        if (suppressRead()) {
            return -3;
        }
        maybeNotifyDownstreamFormat(track);
        int result = this.sampleQueues[track].read(formatHolder, buffer, formatRequired, this.loadingFinished, this.lastSeekPositionUs);
        if (result == -3) {
            maybeStartDeferredRetry(track);
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public int skipData(int track, long positionUs) {
        int skipCount;
        if (suppressRead()) {
            return 0;
        }
        maybeNotifyDownstreamFormat(track);
        SampleQueue sampleQueue = this.sampleQueues[track];
        if (!this.loadingFinished || positionUs <= sampleQueue.getLargestQueuedTimestampUs()) {
            skipCount = sampleQueue.advanceTo(positionUs, true, true);
            if (skipCount == -1) {
                skipCount = 0;
            }
        } else {
            skipCount = sampleQueue.advanceToEnd();
        }
        if (skipCount == 0) {
            maybeStartDeferredRetry(track);
        }
        return skipCount;
    }

    private void maybeNotifyDownstreamFormat(int track) {
        PreparedState preparedState2 = getPreparedState();
        boolean[] trackNotifiedDownstreamFormats = preparedState2.trackNotifiedDownstreamFormats;
        if (!trackNotifiedDownstreamFormats[track]) {
            Format trackFormat = preparedState2.tracks.get(track).getFormat(0);
            this.eventDispatcher.downstreamFormatChanged(MimeTypes.getTrackType(trackFormat.sampleMimeType), trackFormat, 0, null, this.lastSeekPositionUs);
            trackNotifiedDownstreamFormats[track] = true;
        }
    }

    private void maybeStartDeferredRetry(int track) {
        boolean[] trackIsAudioVideoFlags = getPreparedState().trackIsAudioVideoFlags;
        if (this.pendingDeferredRetry && trackIsAudioVideoFlags[track] && !this.sampleQueues[track].hasNextSample()) {
            this.pendingResetPositionUs = 0;
            this.pendingDeferredRetry = false;
            this.notifyDiscontinuity = true;
            this.lastSeekPositionUs = 0;
            this.extractedSamplesCountAtStartOfLoad = 0;
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.reset();
            }
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
        }
    }

    private boolean suppressRead() {
        return this.notifyDiscontinuity || isPendingReset();
    }

    public void onLoadCompleted(ExtractingLoadable loadable, long elapsedRealtimeMs, long loadDurationMs) {
        SeekMap seekMap2;
        long j;
        if (this.durationUs == C0841C.TIME_UNSET && (seekMap2 = this.seekMap) != null) {
            boolean isSeekable = seekMap2.isSeekable();
            long largestQueuedTimestampUs = getLargestQueuedTimestampUs();
            if (largestQueuedTimestampUs == Long.MIN_VALUE) {
                j = 0;
            } else {
                j = DEFAULT_LAST_SAMPLE_DURATION_US + largestQueuedTimestampUs;
            }
            this.durationUs = j;
            this.listener.onSourceInfoRefreshed(this.durationUs, isSeekable);
        }
        this.eventDispatcher.loadCompleted(loadable.dataSpec, loadable.dataSource.getLastOpenedUri(), loadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, loadable.seekTimeUs, this.durationUs, elapsedRealtimeMs, loadDurationMs, loadable.dataSource.getBytesRead());
        copyLengthFromLoader(loadable);
        this.loadingFinished = true;
        ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
    }

    public void onLoadCanceled(ExtractingLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, boolean released2) {
        this.eventDispatcher.loadCanceled(loadable.dataSpec, loadable.dataSource.getLastOpenedUri(), loadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, loadable.seekTimeUs, this.durationUs, elapsedRealtimeMs, loadDurationMs, loadable.dataSource.getBytesRead());
        if (!released2) {
            copyLengthFromLoader(loadable);
            for (SampleQueue sampleQueue : this.sampleQueues) {
                sampleQueue.reset();
            }
            if (this.enabledTrackCount > 0) {
                ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onContinueLoadingRequested(this);
            }
        }
    }

    /* JADX INFO: Multiple debug info for r4v2 int: [D('loadErrorAction' com.google.android.exoplayer2.upstream.Loader$LoadErrorAction), D('extractedSamplesCount' int)] */
    public Loader.LoadErrorAction onLoadError(ExtractingLoadable loadable, long elapsedRealtimeMs, long loadDurationMs, IOException error, int errorCount) {
        Loader.LoadErrorAction loadErrorAction;
        Loader.LoadErrorAction loadErrorAction2;
        copyLengthFromLoader(loadable);
        long retryDelayMs = this.loadErrorHandlingPolicy.getRetryDelayMsFor(this.dataType, loadDurationMs, error, errorCount);
        if (retryDelayMs == C0841C.TIME_UNSET) {
            loadErrorAction = Loader.DONT_RETRY_FATAL;
        } else {
            int extractedSamplesCount = getExtractedSamplesCount();
            boolean madeProgress = extractedSamplesCount > this.extractedSamplesCountAtStartOfLoad;
            if (configureRetry(loadable, extractedSamplesCount)) {
                loadErrorAction2 = Loader.createRetryAction(madeProgress, retryDelayMs);
            } else {
                loadErrorAction2 = Loader.DONT_RETRY;
            }
            loadErrorAction = loadErrorAction2;
        }
        this.eventDispatcher.loadError(loadable.dataSpec, loadable.dataSource.getLastOpenedUri(), loadable.dataSource.getLastResponseHeaders(), 1, -1, null, 0, null, loadable.seekTimeUs, this.durationUs, elapsedRealtimeMs, loadDurationMs, loadable.dataSource.getBytesRead(), error, !loadErrorAction.isRetry());
        return loadErrorAction;
    }

    public TrackOutput track(int id, int type) {
        return prepareTrackOutput(new TrackId(id, false));
    }

    public void endTracks() {
        this.sampleQueuesBuilt = true;
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    public void seekMap(SeekMap seekMap2) {
        this.seekMap = this.icyHeaders == null ? seekMap2 : new SeekMap.Unseekable(C0841C.TIME_UNSET);
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    /* access modifiers changed from: package-private */
    public TrackOutput icyTrack() {
        return prepareTrackOutput(new TrackId(0, true));
    }

    public void onUpstreamFormatChanged(Format format) {
        this.handler.post(this.maybeFinishPrepareRunnable);
    }

    private TrackOutput prepareTrackOutput(TrackId id) {
        int trackCount = this.sampleQueues.length;
        for (int i = 0; i < trackCount; i++) {
            if (id.equals(this.sampleQueueTrackIds[i])) {
                return this.sampleQueues[i];
            }
        }
        SampleQueue trackOutput = new SampleQueue(this.allocator);
        trackOutput.setUpstreamFormatChangeListener(this);
        TrackId[] sampleQueueTrackIds2 = (TrackId[]) Arrays.copyOf(this.sampleQueueTrackIds, trackCount + 1);
        sampleQueueTrackIds2[trackCount] = id;
        this.sampleQueueTrackIds = (TrackId[]) Util.castNonNullTypeArray(sampleQueueTrackIds2);
        SampleQueue[] sampleQueues2 = (SampleQueue[]) Arrays.copyOf(this.sampleQueues, trackCount + 1);
        sampleQueues2[trackCount] = trackOutput;
        this.sampleQueues = (SampleQueue[]) Util.castNonNullTypeArray(sampleQueues2);
        return trackOutput;
    }

    /* access modifiers changed from: private */
    /* renamed from: maybeFinishPrepare */
    public void bridge$lambda$0$ProgressiveMediaPeriod() {
        int i;
        Metadata metadata;
        SeekMap seekMap2 = this.seekMap;
        if (!this.released && !this.prepared && this.sampleQueuesBuilt && seekMap2 != null) {
            SampleQueue[] sampleQueueArr = this.sampleQueues;
            int length2 = sampleQueueArr.length;
            int i2 = 0;
            while (i2 < length2) {
                if (sampleQueueArr[i2].getUpstreamFormat() != null) {
                    i2++;
                } else {
                    return;
                }
            }
            this.loadCondition.close();
            int trackCount = this.sampleQueues.length;
            TrackGroup[] trackArray = new TrackGroup[trackCount];
            boolean[] trackIsAudioVideoFlags = new boolean[trackCount];
            this.durationUs = seekMap2.getDurationUs();
            for (int i3 = 0; i3 < trackCount; i3++) {
                Format trackFormat = this.sampleQueues[i3].getUpstreamFormat();
                String mimeType = trackFormat.sampleMimeType;
                boolean isAudio = MimeTypes.isAudio(mimeType);
                boolean isAudioVideo = isAudio || MimeTypes.isVideo(mimeType);
                trackIsAudioVideoFlags[i3] = isAudioVideo;
                this.haveAudioVideoTracks |= isAudioVideo;
                IcyHeaders icyHeaders2 = this.icyHeaders;
                if (icyHeaders2 != null) {
                    if (isAudio || this.sampleQueueTrackIds[i3].isIcyTrack) {
                        Metadata metadata2 = trackFormat.metadata;
                        if (metadata2 == null) {
                            metadata = new Metadata(icyHeaders2);
                        } else {
                            metadata = metadata2.copyWithAppendedEntries(icyHeaders2);
                        }
                        trackFormat = trackFormat.copyWithMetadata(metadata);
                    }
                    if (isAudio && trackFormat.bitrate == -1 && icyHeaders2.bitrate != -1) {
                        trackFormat = trackFormat.copyWithBitrate(icyHeaders2.bitrate);
                    }
                }
                trackArray[i3] = new TrackGroup(trackFormat);
            }
            if (this.length == -1 && seekMap2.getDurationUs() == C0841C.TIME_UNSET) {
                i = 7;
            } else {
                i = 1;
            }
            this.dataType = i;
            this.preparedState = new PreparedState(seekMap2, new TrackGroupArray(trackArray), trackIsAudioVideoFlags);
            this.prepared = true;
            this.listener.onSourceInfoRefreshed(this.durationUs, seekMap2.isSeekable());
            ((MediaPeriod.Callback) Assertions.checkNotNull(this.callback)).onPrepared(this);
        }
    }

    private PreparedState getPreparedState() {
        return (PreparedState) Assertions.checkNotNull(this.preparedState);
    }

    private void copyLengthFromLoader(ExtractingLoadable loadable) {
        if (this.length == -1) {
            this.length = loadable.length;
        }
    }

    private void startLoading() {
        ExtractingLoadable loadable = new ExtractingLoadable(this.uri, this.dataSource, this.extractorHolder, this, this.loadCondition);
        if (this.prepared) {
            SeekMap seekMap2 = getPreparedState().seekMap;
            Assertions.checkState(isPendingReset());
            long j = this.durationUs;
            if (j == C0841C.TIME_UNSET || this.pendingResetPositionUs < j) {
                loadable.setLoadPosition(seekMap2.getSeekPoints(this.pendingResetPositionUs).first.position, this.pendingResetPositionUs);
                this.pendingResetPositionUs = C0841C.TIME_UNSET;
            } else {
                this.loadingFinished = true;
                this.pendingResetPositionUs = C0841C.TIME_UNSET;
                return;
            }
        }
        this.extractedSamplesCountAtStartOfLoad = getExtractedSamplesCount();
        this.eventDispatcher.loadStarted(loadable.dataSpec, 1, -1, null, 0, null, loadable.seekTimeUs, this.durationUs, this.loader.startLoading(loadable, this, this.loadErrorHandlingPolicy.getMinimumLoadableRetryCount(this.dataType)));
    }

    private boolean configureRetry(ExtractingLoadable loadable, int currentExtractedSampleCount) {
        SeekMap seekMap2;
        if (this.length == -1 && ((seekMap2 = this.seekMap) == null || seekMap2.getDurationUs() == C0841C.TIME_UNSET)) {
            if (!this.prepared || suppressRead()) {
                this.notifyDiscontinuity = this.prepared;
                this.lastSeekPositionUs = 0;
                this.extractedSamplesCountAtStartOfLoad = 0;
                for (SampleQueue sampleQueue : this.sampleQueues) {
                    sampleQueue.reset();
                }
                loadable.setLoadPosition(0, 0);
                return true;
            }
            this.pendingDeferredRetry = true;
            return false;
        }
        this.extractedSamplesCountAtStartOfLoad = currentExtractedSampleCount;
        return true;
    }

    private boolean seekInsideBufferUs(boolean[] trackIsAudioVideoFlags, long positionUs) {
        int trackCount = this.sampleQueues.length;
        int i = 0;
        while (true) {
            boolean seekInsideQueue = true;
            if (i >= trackCount) {
                return true;
            }
            SampleQueue sampleQueue = this.sampleQueues[i];
            sampleQueue.rewind();
            if (sampleQueue.advanceTo(positionUs, true, false) == -1) {
                seekInsideQueue = false;
            }
            if (seekInsideQueue || (!trackIsAudioVideoFlags[i] && this.haveAudioVideoTracks)) {
                i++;
            }
        }
        return false;
    }

    private int getExtractedSamplesCount() {
        int extractedSamplesCount = 0;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            extractedSamplesCount += sampleQueue.getWriteIndex();
        }
        return extractedSamplesCount;
    }

    /* access modifiers changed from: private */
    public long getLargestQueuedTimestampUs() {
        long largestQueuedTimestampUs = Long.MIN_VALUE;
        for (SampleQueue sampleQueue : this.sampleQueues) {
            largestQueuedTimestampUs = Math.max(largestQueuedTimestampUs, sampleQueue.getLargestQueuedTimestampUs());
        }
        return largestQueuedTimestampUs;
    }

    private boolean isPendingReset() {
        return this.pendingResetPositionUs != C0841C.TIME_UNSET;
    }

    private final class SampleStreamImpl implements SampleStream {
        /* access modifiers changed from: private */
        public final int track;

        public SampleStreamImpl(int track2) {
            this.track = track2;
        }

        public boolean isReady() {
            return ProgressiveMediaPeriod.this.isReady(this.track);
        }

        public void maybeThrowError() throws IOException {
            ProgressiveMediaPeriod.this.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer buffer, boolean formatRequired) {
            return ProgressiveMediaPeriod.this.readData(this.track, formatHolder, buffer, formatRequired);
        }

        public int skipData(long positionUs) {
            return ProgressiveMediaPeriod.this.skipData(this.track, positionUs);
        }
    }

    final class ExtractingLoadable implements Loader.Loadable, IcyDataSource.Listener {
        /* access modifiers changed from: private */
        public final StatsDataSource dataSource;
        /* access modifiers changed from: private */
        public DataSpec dataSpec = buildDataSpec(0);
        private final ExtractorHolder extractorHolder;
        private final ExtractorOutput extractorOutput;
        @Nullable
        private TrackOutput icyTrackOutput;
        /* access modifiers changed from: private */
        public long length = -1;
        private volatile boolean loadCanceled;
        private final ConditionVariable loadCondition;
        private boolean pendingExtractorSeek = true;
        private final PositionHolder positionHolder = new PositionHolder();
        /* access modifiers changed from: private */
        public long seekTimeUs;
        private boolean seenIcyMetadata;
        private final Uri uri;

        public ExtractingLoadable(Uri uri2, DataSource dataSource2, ExtractorHolder extractorHolder2, ExtractorOutput extractorOutput2, ConditionVariable loadCondition2) {
            this.uri = uri2;
            this.dataSource = new StatsDataSource(dataSource2);
            this.extractorHolder = extractorHolder2;
            this.extractorOutput = extractorOutput2;
            this.loadCondition = loadCondition2;
        }

        public void cancelLoad() {
            this.loadCanceled = true;
        }

        public void load() throws IOException, InterruptedException {
            DataSource extractorDataSource;
            int result = 0;
            while (result == 0 && !this.loadCanceled) {
                ExtractorInput input = null;
                try {
                    long position = this.positionHolder.position;
                    this.dataSpec = buildDataSpec(position);
                    this.length = this.dataSource.open(this.dataSpec);
                    if (this.length != -1) {
                        this.length += position;
                    }
                    Uri uri2 = (Uri) Assertions.checkNotNull(this.dataSource.getUri());
                    IcyHeaders unused = ProgressiveMediaPeriod.this.icyHeaders = IcyHeaders.parse(this.dataSource.getResponseHeaders());
                    DataSource extractorDataSource2 = this.dataSource;
                    if (ProgressiveMediaPeriod.this.icyHeaders == null || ProgressiveMediaPeriod.this.icyHeaders.metadataInterval == -1) {
                        extractorDataSource = extractorDataSource2;
                    } else {
                        DataSource extractorDataSource3 = new IcyDataSource(this.dataSource, ProgressiveMediaPeriod.this.icyHeaders.metadataInterval, this);
                        this.icyTrackOutput = ProgressiveMediaPeriod.this.icyTrack();
                        this.icyTrackOutput.format(ProgressiveMediaPeriod.ICY_FORMAT);
                        extractorDataSource = extractorDataSource3;
                    }
                    ExtractorInput input2 = new DefaultExtractorInput(extractorDataSource, position, this.length);
                    Extractor extractor = this.extractorHolder.selectExtractor(input2, this.extractorOutput, uri2);
                    if (this.pendingExtractorSeek) {
                        extractor.seek(position, this.seekTimeUs);
                        this.pendingExtractorSeek = false;
                    }
                    while (result == 0 && !this.loadCanceled) {
                        this.loadCondition.block();
                        result = extractor.read(input2, this.positionHolder);
                        if (input2.getPosition() > ProgressiveMediaPeriod.this.continueLoadingCheckIntervalBytes + position) {
                            position = input2.getPosition();
                            this.loadCondition.close();
                            ProgressiveMediaPeriod.this.handler.post(ProgressiveMediaPeriod.this.onContinueLoadingRequestedRunnable);
                        }
                    }
                    if (result == 1) {
                        result = 0;
                    } else {
                        this.positionHolder.position = input2.getPosition();
                    }
                    Util.closeQuietly(this.dataSource);
                } catch (Throwable th) {
                    if (result != 1) {
                        if (input != null) {
                            this.positionHolder.position = input.getPosition();
                        }
                    }
                    Util.closeQuietly(this.dataSource);
                    throw th;
                }
            }
        }

        public void onIcyMetadata(ParsableByteArray metadata) {
            long timeUs = !this.seenIcyMetadata ? this.seekTimeUs : Math.max(ProgressiveMediaPeriod.this.getLargestQueuedTimestampUs(), this.seekTimeUs);
            int length2 = metadata.bytesLeft();
            TrackOutput icyTrackOutput2 = (TrackOutput) Assertions.checkNotNull(this.icyTrackOutput);
            icyTrackOutput2.sampleData(metadata, length2);
            icyTrackOutput2.sampleMetadata(timeUs, 1, length2, 0, null);
            this.seenIcyMetadata = true;
        }

        private DataSpec buildDataSpec(long position) {
            return new DataSpec(this.uri, position, -1, ProgressiveMediaPeriod.this.customCacheKey, 22);
        }

        /* access modifiers changed from: private */
        public void setLoadPosition(long position, long timeUs) {
            this.positionHolder.position = position;
            this.seekTimeUs = timeUs;
            this.pendingExtractorSeek = true;
            this.seenIcyMetadata = false;
        }
    }

    private static final class ExtractorHolder {
        @Nullable
        private Extractor extractor;
        private final Extractor[] extractors;

        public ExtractorHolder(Extractor[] extractors2) {
            this.extractors = extractors2;
        }

        public Extractor selectExtractor(ExtractorInput input, ExtractorOutput output, Uri uri) throws IOException, InterruptedException {
            Extractor extractor2 = this.extractor;
            if (extractor2 != null) {
                return extractor2;
            }
            Extractor[] extractorArr = this.extractors;
            int length = extractorArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Extractor extractor3 = extractorArr[i];
                try {
                    if (extractor3.sniff(input)) {
                        this.extractor = extractor3;
                        input.resetPeekPosition();
                        break;
                    }
                    input.resetPeekPosition();
                    i++;
                } catch (EOFException e) {
                } catch (Throwable th) {
                    input.resetPeekPosition();
                    throw th;
                }
            }
            Extractor extractor4 = this.extractor;
            if (extractor4 != null) {
                extractor4.init(output);
                return this.extractor;
            }
            String commaDelimitedSimpleClassNames = Util.getCommaDelimitedSimpleClassNames(this.extractors);
            StringBuilder sb = new StringBuilder(String.valueOf(commaDelimitedSimpleClassNames).length() + 58);
            sb.append("None of the available extractors (");
            sb.append(commaDelimitedSimpleClassNames);
            sb.append(") could read the stream.");
            throw new UnrecognizedInputFormatException(sb.toString(), uri);
        }

        public void release() {
            Extractor extractor2 = this.extractor;
            if (extractor2 != null) {
                extractor2.release();
                this.extractor = null;
            }
        }
    }

    private static final class PreparedState {
        public final SeekMap seekMap;
        public final boolean[] trackEnabledStates;
        public final boolean[] trackIsAudioVideoFlags;
        public final boolean[] trackNotifiedDownstreamFormats;
        public final TrackGroupArray tracks;

        public PreparedState(SeekMap seekMap2, TrackGroupArray tracks2, boolean[] trackIsAudioVideoFlags2) {
            this.seekMap = seekMap2;
            this.tracks = tracks2;
            this.trackIsAudioVideoFlags = trackIsAudioVideoFlags2;
            this.trackEnabledStates = new boolean[tracks2.length];
            this.trackNotifiedDownstreamFormats = new boolean[tracks2.length];
        }
    }

    private static final class TrackId {

        /* renamed from: id */
        public final int f92id;
        public final boolean isIcyTrack;

        public TrackId(int id, boolean isIcyTrack2) {
            this.f92id = id;
            this.isIcyTrack = isIcyTrack2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            TrackId other = (TrackId) obj;
            if (this.f92id == other.f92id && this.isIcyTrack == other.isIcyTrack) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.f92id * 31) + (this.isIcyTrack ? 1 : 0);
        }
    }
}
