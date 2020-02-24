package com.google.android.exoplayer2.source.dash;

import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.rawcc.RawCcExtractor;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.ChunkExtractorWrapper;
import com.google.android.exoplayer2.source.chunk.ChunkHolder;
import com.google.android.exoplayer2.source.chunk.ContainerMediaChunk;
import com.google.android.exoplayer2.source.chunk.InitializationChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.SingleSampleMediaChunk;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.PlayerEmsgHandler;
import com.google.android.exoplayer2.source.dash.manifest.AdaptationSet;
import com.google.android.exoplayer2.source.dash.manifest.DashManifest;
import com.google.android.exoplayer2.source.dash.manifest.RangedUri;
import com.google.android.exoplayer2.source.dash.manifest.Representation;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.LoaderErrorThrower;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultDashChunkSource implements DashChunkSource {
    private final int[] adaptationSetIndices;
    private final DataSource dataSource;
    private final long elapsedRealtimeOffsetMs;
    private IOException fatalError;
    private long liveEdgeTimeUs = C0841C.TIME_UNSET;
    private DashManifest manifest;
    private final LoaderErrorThrower manifestLoaderErrorThrower;
    private final int maxSegmentsPerLoad;
    private boolean missingLastSegment;
    private int periodIndex;
    @Nullable
    private final PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler;
    protected final RepresentationHolder[] representationHolders;
    private final TrackSelection trackSelection;
    private final int trackType;

    public static final class Factory implements DashChunkSource.Factory {
        private final DataSource.Factory dataSourceFactory;
        private final int maxSegmentsPerLoad;

        public Factory(DataSource.Factory dataSourceFactory2) {
            this(dataSourceFactory2, 1);
        }

        public Factory(DataSource.Factory dataSourceFactory2, int maxSegmentsPerLoad2) {
            this.dataSourceFactory = dataSourceFactory2;
            this.maxSegmentsPerLoad = maxSegmentsPerLoad2;
        }

        public DashChunkSource createDashChunkSource(LoaderErrorThrower manifestLoaderErrorThrower, DashManifest manifest, int periodIndex, int[] adaptationSetIndices, TrackSelection trackSelection, int trackType, long elapsedRealtimeOffsetMs, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerEmsgHandler, @Nullable TransferListener transferListener) {
            TransferListener transferListener2 = transferListener;
            DataSource dataSource = this.dataSourceFactory.createDataSource();
            if (transferListener2 != null) {
                dataSource.addTransferListener(transferListener2);
            }
            return new DefaultDashChunkSource(manifestLoaderErrorThrower, manifest, periodIndex, adaptationSetIndices, trackSelection, trackType, dataSource, elapsedRealtimeOffsetMs, this.maxSegmentsPerLoad, enableEventMessageTrack, closedCaptionFormats, playerEmsgHandler);
        }
    }

    public DefaultDashChunkSource(LoaderErrorThrower manifestLoaderErrorThrower2, DashManifest manifest2, int periodIndex2, int[] adaptationSetIndices2, TrackSelection trackSelection2, int trackType2, DataSource dataSource2, long elapsedRealtimeOffsetMs2, int maxSegmentsPerLoad2, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, @Nullable PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler2) {
        TrackSelection trackSelection3 = trackSelection2;
        this.manifestLoaderErrorThrower = manifestLoaderErrorThrower2;
        this.manifest = manifest2;
        this.adaptationSetIndices = adaptationSetIndices2;
        this.trackSelection = trackSelection3;
        this.trackType = trackType2;
        this.dataSource = dataSource2;
        this.periodIndex = periodIndex2;
        this.elapsedRealtimeOffsetMs = elapsedRealtimeOffsetMs2;
        this.maxSegmentsPerLoad = maxSegmentsPerLoad2;
        this.playerTrackEmsgHandler = playerTrackEmsgHandler2;
        long periodDurationUs = manifest2.getPeriodDurationUs(periodIndex2);
        List<Representation> representations = getRepresentations();
        this.representationHolders = new RepresentationHolder[trackSelection2.length()];
        int i = 0;
        while (i < this.representationHolders.length) {
            int i2 = i;
            this.representationHolders[i2] = new RepresentationHolder(periodDurationUs, trackType2, representations.get(trackSelection3.getIndexInTrackGroup(i)), enableEventMessageTrack, closedCaptionFormats, playerTrackEmsgHandler2);
            i = i2 + 1;
            representations = representations;
        }
    }

    public long getAdjustedSeekPositionUs(long positionUs, SeekParameters seekParameters) {
        long secondSyncUs;
        long j = positionUs;
        for (RepresentationHolder representationHolder : this.representationHolders) {
            if (representationHolder.segmentIndex != null) {
                long segmentNum = representationHolder.getSegmentNum(j);
                long firstSyncUs = representationHolder.getSegmentStartTimeUs(segmentNum);
                if (firstSyncUs >= j || segmentNum >= ((long) (representationHolder.getSegmentCount() - 1))) {
                    secondSyncUs = firstSyncUs;
                } else {
                    secondSyncUs = representationHolder.getSegmentStartTimeUs(1 + segmentNum);
                }
                return Util.resolveSeekPositionUs(positionUs, seekParameters, firstSyncUs, secondSyncUs);
            }
        }
        return j;
    }

    public void updateManifest(DashManifest newManifest, int newPeriodIndex) {
        try {
            this.manifest = newManifest;
            this.periodIndex = newPeriodIndex;
            long periodDurationUs = this.manifest.getPeriodDurationUs(this.periodIndex);
            List<Representation> representations = getRepresentations();
            for (int i = 0; i < this.representationHolders.length; i++) {
                this.representationHolders[i] = this.representationHolders[i].copyWithNewRepresentation(periodDurationUs, representations.get(this.trackSelection.getIndexInTrackGroup(i)));
            }
        } catch (BehindLiveWindowException e) {
            this.fatalError = e;
        }
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

    /* JADX INFO: Multiple debug info for r21v0 long: [D('periodDurationUs' long), D('pendingInitializationUri' com.google.android.exoplayer2.source.dash.manifest.RangedUri)] */
    /* JADX INFO: Multiple debug info for r0v14 com.google.android.exoplayer2.source.dash.DefaultDashChunkSource$RepresentationHolder: [D('representationHolder' com.google.android.exoplayer2.source.dash.DefaultDashChunkSource$RepresentationHolder), D('nowUnixTimeUs' long)] */
    /* JADX INFO: Multiple debug info for r13v1 long: [D('presentationPositionUs' long), D('seekTimeUs' long)] */
    public void getNextChunk(long playbackPositionUs, long loadPositionUs, List<? extends MediaChunk> queue, ChunkHolder out) {
        ChunkHolder chunkHolder;
        int maxSegmentCount;
        RangedUri pendingInitializationUri;
        RangedUri pendingIndexUri;
        MediaChunkIterator[] chunkIterators;
        int i;
        ChunkHolder chunkHolder2 = out;
        if (this.fatalError == null) {
            long bufferedDurationUs = loadPositionUs - playbackPositionUs;
            long timeToLiveEdgeUs = resolveTimeToLiveEdgeUs(playbackPositionUs);
            long presentationPositionUs = C0841C.msToUs(this.manifest.availabilityStartTimeMs) + C0841C.msToUs(this.manifest.getPeriod(this.periodIndex).startMs) + loadPositionUs;
            PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler2 = this.playerTrackEmsgHandler;
            if (playerTrackEmsgHandler2 == null || !playerTrackEmsgHandler2.maybeRefreshManifestBeforeLoadingNextChunk(presentationPositionUs)) {
                long nowUnixTimeUs = getNowUnixTimeUs();
                MediaChunk previous = queue.isEmpty() ? null : (MediaChunk) queue.get(queue.size() - 1);
                MediaChunkIterator[] chunkIterators2 = new MediaChunkIterator[this.trackSelection.length()];
                int i2 = 0;
                while (i2 < chunkIterators2.length) {
                    RepresentationHolder representationHolder = this.representationHolders[i2];
                    if (representationHolder.segmentIndex == null) {
                        chunkIterators2[i2] = MediaChunkIterator.EMPTY;
                        i = i2;
                        chunkIterators = chunkIterators2;
                    } else {
                        long firstAvailableSegmentNum = representationHolder.getFirstAvailableSegmentNum(this.manifest, this.periodIndex, nowUnixTimeUs);
                        long lastAvailableSegmentNum = representationHolder.getLastAvailableSegmentNum(this.manifest, this.periodIndex, nowUnixTimeUs);
                        i = i2;
                        RepresentationHolder representationHolder2 = representationHolder;
                        chunkIterators = chunkIterators2;
                        long segmentNum = getSegmentNum(representationHolder, previous, loadPositionUs, firstAvailableSegmentNum, lastAvailableSegmentNum);
                        if (segmentNum < firstAvailableSegmentNum) {
                            chunkIterators[i] = MediaChunkIterator.EMPTY;
                        } else {
                            chunkIterators[i] = new RepresentationSegmentIterator(representationHolder2, segmentNum, lastAvailableSegmentNum);
                        }
                    }
                    i2 = i + 1;
                    chunkIterators2 = chunkIterators;
                }
                long nowUnixTimeUs2 = nowUnixTimeUs;
                this.trackSelection.updateSelectedTrack(playbackPositionUs, bufferedDurationUs, timeToLiveEdgeUs, queue, chunkIterators2);
                RepresentationHolder representationHolder3 = this.representationHolders[this.trackSelection.getSelectedIndex()];
                if (representationHolder3.extractorWrapper != null) {
                    Representation selectedRepresentation = representationHolder3.representation;
                    if (representationHolder3.extractorWrapper.getSampleFormats() == null) {
                        pendingInitializationUri = selectedRepresentation.getInitializationUri();
                    } else {
                        pendingInitializationUri = null;
                    }
                    if (representationHolder3.segmentIndex == null) {
                        pendingIndexUri = selectedRepresentation.getIndexUri();
                    } else {
                        pendingIndexUri = null;
                    }
                    if (!(pendingInitializationUri == null && pendingIndexUri == null)) {
                        chunkHolder2.chunk = newInitializationChunk(representationHolder3, this.dataSource, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), pendingInitializationUri, pendingIndexUri);
                        return;
                    }
                }
                long periodDurationUs = representationHolder3.periodDurationUs;
                long seekTimeUs = C0841C.TIME_UNSET;
                boolean periodEnded = periodDurationUs != C0841C.TIME_UNSET;
                if (representationHolder3.getSegmentCount() == 0) {
                    chunkHolder2.endOfStream = periodEnded;
                    return;
                }
                long firstAvailableSegmentNum2 = representationHolder3.getFirstAvailableSegmentNum(this.manifest, this.periodIndex, nowUnixTimeUs2);
                long lastAvailableSegmentNum2 = representationHolder3.getLastAvailableSegmentNum(this.manifest, this.periodIndex, nowUnixTimeUs2);
                updateLiveEdgeTimeUs(representationHolder3, lastAvailableSegmentNum2);
                long lastAvailableSegmentNum3 = lastAvailableSegmentNum2;
                boolean periodEnded2 = periodEnded;
                RepresentationHolder representationHolder4 = representationHolder3;
                long segmentNum2 = getSegmentNum(representationHolder3, previous, loadPositionUs, firstAvailableSegmentNum2, lastAvailableSegmentNum3);
                if (segmentNum2 < firstAvailableSegmentNum2) {
                    this.fatalError = new BehindLiveWindowException();
                    return;
                }
                if (segmentNum2 > lastAvailableSegmentNum3) {
                    chunkHolder = chunkHolder2;
                } else if (this.missingLastSegment && segmentNum2 >= lastAvailableSegmentNum3) {
                    chunkHolder = chunkHolder2;
                } else if (!periodEnded2 || representationHolder4.getSegmentStartTimeUs(segmentNum2) < periodDurationUs) {
                    int maxSegmentCount2 = (int) Math.min((long) this.maxSegmentsPerLoad, (lastAvailableSegmentNum3 - segmentNum2) + 1);
                    if (periodDurationUs != C0841C.TIME_UNSET) {
                        while (maxSegmentCount2 > 1 && representationHolder4.getSegmentStartTimeUs((((long) maxSegmentCount2) + segmentNum2) - 1) >= periodDurationUs) {
                            maxSegmentCount2--;
                        }
                        maxSegmentCount = maxSegmentCount2;
                    } else {
                        maxSegmentCount = maxSegmentCount2;
                    }
                    if (queue.isEmpty()) {
                        seekTimeUs = loadPositionUs;
                    }
                    chunkHolder2.chunk = newMediaChunk(representationHolder4, this.dataSource, this.trackType, this.trackSelection.getSelectedFormat(), this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), segmentNum2, maxSegmentCount, seekTimeUs);
                    return;
                } else {
                    chunkHolder2.endOfStream = true;
                    return;
                }
                chunkHolder.endOfStream = periodEnded2;
            }
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        SeekMap seekMap;
        if (chunk instanceof InitializationChunk) {
            int trackIndex = this.trackSelection.indexOf(((InitializationChunk) chunk).trackFormat);
            RepresentationHolder representationHolder = this.representationHolders[trackIndex];
            if (representationHolder.segmentIndex == null && (seekMap = representationHolder.extractorWrapper.getSeekMap()) != null) {
                this.representationHolders[trackIndex] = representationHolder.copyWithNewSegmentIndex(new DashWrappingSegmentIndex((ChunkIndex) seekMap, representationHolder.representation.presentationTimeOffsetUs));
            }
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler2 = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler2 != null) {
            playerTrackEmsgHandler2.onChunkLoadCompleted(chunk);
        }
    }

    public boolean onChunkLoadError(Chunk chunk, boolean cancelable, Exception e, long blacklistDurationMs) {
        RepresentationHolder representationHolder;
        int segmentCount;
        if (!cancelable) {
            return false;
        }
        PlayerEmsgHandler.PlayerTrackEmsgHandler playerTrackEmsgHandler2 = this.playerTrackEmsgHandler;
        if (playerTrackEmsgHandler2 != null && playerTrackEmsgHandler2.maybeRefreshManifestOnLoadingError(chunk)) {
            return true;
        }
        if (!this.manifest.dynamic && (chunk instanceof MediaChunk) && (e instanceof HttpDataSource.InvalidResponseCodeException) && ((HttpDataSource.InvalidResponseCodeException) e).responseCode == 404 && (segmentCount = (representationHolder = this.representationHolders[this.trackSelection.indexOf(chunk.trackFormat)]).getSegmentCount()) != -1 && segmentCount != 0) {
            if (((MediaChunk) chunk).getNextChunkIndex() > (representationHolder.getFirstSegmentNum() + ((long) segmentCount)) - 1) {
                this.missingLastSegment = true;
                return true;
            }
        }
        if (blacklistDurationMs == C0841C.TIME_UNSET) {
            return false;
        }
        TrackSelection trackSelection2 = this.trackSelection;
        if (trackSelection2.blacklist(trackSelection2.indexOf(chunk.trackFormat), blacklistDurationMs)) {
            return true;
        }
        return false;
    }

    private long getSegmentNum(RepresentationHolder representationHolder, @Nullable MediaChunk previousChunk, long loadPositionUs, long firstAvailableSegmentNum, long lastAvailableSegmentNum) {
        if (previousChunk != null) {
            return previousChunk.getNextChunkIndex();
        }
        return Util.constrainValue(representationHolder.getSegmentNum(loadPositionUs), firstAvailableSegmentNum, lastAvailableSegmentNum);
    }

    private ArrayList<Representation> getRepresentations() {
        List<AdaptationSet> manifestAdaptationSets = this.manifest.getPeriod(this.periodIndex).adaptationSets;
        ArrayList<Representation> representations = new ArrayList<>();
        for (int adaptationSetIndex : this.adaptationSetIndices) {
            representations.addAll(manifestAdaptationSets.get(adaptationSetIndex).representations);
        }
        return representations;
    }

    private void updateLiveEdgeTimeUs(RepresentationHolder representationHolder, long lastAvailableSegmentNum) {
        this.liveEdgeTimeUs = this.manifest.dynamic ? representationHolder.getSegmentEndTimeUs(lastAvailableSegmentNum) : C0841C.TIME_UNSET;
    }

    private long getNowUnixTimeUs() {
        if (this.elapsedRealtimeOffsetMs != 0) {
            return (SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs) * 1000;
        }
        return System.currentTimeMillis() * 1000;
    }

    private long resolveTimeToLiveEdgeUs(long playbackPositionUs) {
        if (this.manifest.dynamic && this.liveEdgeTimeUs != C0841C.TIME_UNSET) {
            return this.liveEdgeTimeUs - playbackPositionUs;
        }
        return C0841C.TIME_UNSET;
    }

    /* access modifiers changed from: protected */
    public Chunk newInitializationChunk(RepresentationHolder representationHolder, DataSource dataSource2, Format trackFormat, int trackSelectionReason, Object trackSelectionData, RangedUri initializationUri, RangedUri indexUri) {
        RangedUri requestUri;
        RepresentationHolder representationHolder2 = representationHolder;
        RangedUri rangedUri = initializationUri;
        String baseUrl = representationHolder2.representation.baseUrl;
        if (rangedUri != null) {
            requestUri = rangedUri.attemptMerge(indexUri, baseUrl);
            if (requestUri == null) {
                requestUri = initializationUri;
            }
        } else {
            requestUri = indexUri;
        }
        return new InitializationChunk(dataSource2, new DataSpec(requestUri.resolveUri(baseUrl), requestUri.start, requestUri.length, representationHolder2.representation.getCacheKey()), trackFormat, trackSelectionReason, trackSelectionData, representationHolder2.extractorWrapper);
    }

    /* access modifiers changed from: protected */
    public Chunk newMediaChunk(RepresentationHolder representationHolder, DataSource dataSource2, int trackType2, Format trackFormat, int trackSelectionReason, Object trackSelectionData, long firstSegmentNum, int maxSegmentCount, long seekTimeUs) {
        long clippedEndTimeUs;
        RangedUri mergedSegmentUri;
        RepresentationHolder representationHolder2 = representationHolder;
        long j = firstSegmentNum;
        Representation representation = representationHolder2.representation;
        long startTimeUs = representationHolder2.getSegmentStartTimeUs(j);
        RangedUri segmentUri = representationHolder2.getSegmentUrl(j);
        String baseUrl = representation.baseUrl;
        if (representationHolder2.extractorWrapper == null) {
            return new SingleSampleMediaChunk(dataSource2, new DataSpec(segmentUri.resolveUri(baseUrl), segmentUri.start, segmentUri.length, representation.getCacheKey()), trackFormat, trackSelectionReason, trackSelectionData, startTimeUs, representationHolder2.getSegmentEndTimeUs(j), firstSegmentNum, trackType2, trackFormat);
        }
        String baseUrl2 = baseUrl;
        Representation representation2 = representation;
        int i = 1;
        int segmentCount = 1;
        while (i < maxSegmentCount && (mergedSegmentUri = segmentUri.attemptMerge(representationHolder2.getSegmentUrl(firstSegmentNum + ((long) i)), baseUrl2)) != null) {
            segmentUri = mergedSegmentUri;
            segmentCount++;
            i++;
        }
        long endTimeUs = representationHolder2.getSegmentEndTimeUs((firstSegmentNum + ((long) segmentCount)) - 1);
        long periodDurationUs = representationHolder.periodDurationUs;
        if (periodDurationUs == C0841C.TIME_UNSET || periodDurationUs > endTimeUs) {
            clippedEndTimeUs = -9223372036854775807L;
        } else {
            clippedEndTimeUs = periodDurationUs;
        }
        return new ContainerMediaChunk(dataSource2, new DataSpec(segmentUri.resolveUri(baseUrl2), segmentUri.start, segmentUri.length, representation2.getCacheKey()), trackFormat, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs, seekTimeUs, clippedEndTimeUs, firstSegmentNum, segmentCount, -representation2.presentationTimeOffsetUs, representationHolder2.extractorWrapper);
    }

    protected static final class RepresentationSegmentIterator extends BaseMediaChunkIterator {
        private final RepresentationHolder representationHolder;

        public RepresentationSegmentIterator(RepresentationHolder representation, long firstAvailableSegmentNum, long lastAvailableSegmentNum) {
            super(firstAvailableSegmentNum, lastAvailableSegmentNum);
            this.representationHolder = representation;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            Representation representation = this.representationHolder.representation;
            RangedUri segmentUri = this.representationHolder.getSegmentUrl(getCurrentIndex());
            Uri resolvedUri = segmentUri.resolveUri(representation.baseUrl);
            return new DataSpec(resolvedUri, segmentUri.start, segmentUri.length, representation.getCacheKey());
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentStartTimeUs(getCurrentIndex());
        }

        public long getChunkEndTimeUs() {
            checkInBounds();
            return this.representationHolder.getSegmentEndTimeUs(getCurrentIndex());
        }
    }

    protected static final class RepresentationHolder {
        @Nullable
        final ChunkExtractorWrapper extractorWrapper;
        /* access modifiers changed from: private */
        public final long periodDurationUs;
        public final Representation representation;
        @Nullable
        public final DashSegmentIndex segmentIndex;
        private final long segmentNumShift;

        RepresentationHolder(long periodDurationUs2, int trackType, Representation representation2, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, TrackOutput playerEmsgTrackOutput) {
            this(periodDurationUs2, representation2, createExtractorWrapper(trackType, representation2, enableEventMessageTrack, closedCaptionFormats, playerEmsgTrackOutput), 0, representation2.getIndex());
        }

        private RepresentationHolder(long periodDurationUs2, Representation representation2, @Nullable ChunkExtractorWrapper extractorWrapper2, long segmentNumShift2, @Nullable DashSegmentIndex segmentIndex2) {
            this.periodDurationUs = periodDurationUs2;
            this.representation = representation2;
            this.segmentNumShift = segmentNumShift2;
            this.extractorWrapper = extractorWrapper2;
            this.segmentIndex = segmentIndex2;
        }

        /* access modifiers changed from: package-private */
        @CheckResult
        public RepresentationHolder copyWithNewRepresentation(long newPeriodDurationUs, Representation newRepresentation) throws BehindLiveWindowException {
            long newSegmentNumShift;
            long j = newPeriodDurationUs;
            DashSegmentIndex oldIndex = this.representation.getIndex();
            DashSegmentIndex newIndex = newRepresentation.getIndex();
            if (oldIndex == null) {
                return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.extractorWrapper, this.segmentNumShift, oldIndex);
            } else if (!oldIndex.isExplicit()) {
                return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.extractorWrapper, this.segmentNumShift, newIndex);
            } else {
                int oldIndexSegmentCount = oldIndex.getSegmentCount(j);
                if (oldIndexSegmentCount == 0) {
                    return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.extractorWrapper, this.segmentNumShift, newIndex);
                }
                long oldIndexLastSegmentNum = (oldIndex.getFirstSegmentNum() + ((long) oldIndexSegmentCount)) - 1;
                long oldIndexEndTimeUs = oldIndex.getTimeUs(oldIndexLastSegmentNum) + oldIndex.getDurationUs(oldIndexLastSegmentNum, j);
                long newIndexFirstSegmentNum = newIndex.getFirstSegmentNum();
                long newIndexStartTimeUs = newIndex.getTimeUs(newIndexFirstSegmentNum);
                long newSegmentNumShift2 = this.segmentNumShift;
                if (oldIndexEndTimeUs == newIndexStartTimeUs) {
                    newSegmentNumShift = newSegmentNumShift2 + ((oldIndexLastSegmentNum + 1) - newIndexFirstSegmentNum);
                } else if (oldIndexEndTimeUs >= newIndexStartTimeUs) {
                    newSegmentNumShift = newSegmentNumShift2 + (oldIndex.getSegmentNum(newIndexStartTimeUs, j) - newIndexFirstSegmentNum);
                } else {
                    throw new BehindLiveWindowException();
                }
                return new RepresentationHolder(newPeriodDurationUs, newRepresentation, this.extractorWrapper, newSegmentNumShift, newIndex);
            }
        }

        /* access modifiers changed from: package-private */
        @CheckResult
        public RepresentationHolder copyWithNewSegmentIndex(DashSegmentIndex segmentIndex2) {
            return new RepresentationHolder(this.periodDurationUs, this.representation, this.extractorWrapper, this.segmentNumShift, segmentIndex2);
        }

        public long getFirstSegmentNum() {
            return this.segmentIndex.getFirstSegmentNum() + this.segmentNumShift;
        }

        public int getSegmentCount() {
            return this.segmentIndex.getSegmentCount(this.periodDurationUs);
        }

        public long getSegmentStartTimeUs(long segmentNum) {
            return this.segmentIndex.getTimeUs(segmentNum - this.segmentNumShift);
        }

        public long getSegmentEndTimeUs(long segmentNum) {
            return getSegmentStartTimeUs(segmentNum) + this.segmentIndex.getDurationUs(segmentNum - this.segmentNumShift, this.periodDurationUs);
        }

        public long getSegmentNum(long positionUs) {
            return this.segmentIndex.getSegmentNum(positionUs, this.periodDurationUs) + this.segmentNumShift;
        }

        public RangedUri getSegmentUrl(long segmentNum) {
            return this.segmentIndex.getSegmentUrl(segmentNum - this.segmentNumShift);
        }

        public long getFirstAvailableSegmentNum(DashManifest manifest, int periodIndex, long nowUnixTimeUs) {
            DashManifest dashManifest = manifest;
            if (getSegmentCount() != -1 || dashManifest.timeShiftBufferDepthMs == C0841C.TIME_UNSET) {
                return getFirstSegmentNum();
            }
            return Math.max(getFirstSegmentNum(), getSegmentNum(((nowUnixTimeUs - C0841C.msToUs(dashManifest.availabilityStartTimeMs)) - C0841C.msToUs(manifest.getPeriod(periodIndex).startMs)) - C0841C.msToUs(dashManifest.timeShiftBufferDepthMs)));
        }

        public long getLastAvailableSegmentNum(DashManifest manifest, int periodIndex, long nowUnixTimeUs) {
            int availableSegmentCount = getSegmentCount();
            if (availableSegmentCount == -1) {
                return getSegmentNum((nowUnixTimeUs - C0841C.msToUs(manifest.availabilityStartTimeMs)) - C0841C.msToUs(manifest.getPeriod(periodIndex).startMs)) - 1;
            }
            return (getFirstSegmentNum() + ((long) availableSegmentCount)) - 1;
        }

        private static boolean mimeTypeIsWebm(String mimeType) {
            return mimeType.startsWith(MimeTypes.VIDEO_WEBM) || mimeType.startsWith(MimeTypes.AUDIO_WEBM) || mimeType.startsWith(MimeTypes.APPLICATION_WEBM);
        }

        private static boolean mimeTypeIsRawText(String mimeType) {
            return MimeTypes.isText(mimeType) || MimeTypes.APPLICATION_TTML.equals(mimeType);
        }

        @Nullable
        private static ChunkExtractorWrapper createExtractorWrapper(int trackType, Representation representation2, boolean enableEventMessageTrack, List<Format> closedCaptionFormats, TrackOutput playerEmsgTrackOutput) {
            Extractor extractor;
            String containerMimeType = representation2.format.containerMimeType;
            if (mimeTypeIsRawText(containerMimeType)) {
                return null;
            }
            if (MimeTypes.APPLICATION_RAWCC.equals(containerMimeType)) {
                extractor = new RawCcExtractor(representation2.format);
            } else if (mimeTypeIsWebm(containerMimeType)) {
                extractor = new MatroskaExtractor(1);
            } else {
                int flags = 0;
                if (enableEventMessageTrack) {
                    flags = 0 | 4;
                }
                extractor = new FragmentedMp4Extractor(flags, null, null, null, closedCaptionFormats, playerEmsgTrackOutput);
            }
            return new ChunkExtractorWrapper(extractor, trackType, representation2.format);
        }
    }
}
