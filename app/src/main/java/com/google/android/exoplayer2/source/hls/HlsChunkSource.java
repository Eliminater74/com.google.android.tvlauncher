package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.chunk.BaseMediaChunkIterator;
import com.google.android.exoplayer2.source.chunk.Chunk;
import com.google.android.exoplayer2.source.chunk.DataChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.chunk.MediaChunkIterator;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import com.google.android.exoplayer2.trackselection.BaseTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class HlsChunkSource {
    private static final int KEY_CACHE_SIZE = 4;
    private final DataSource encryptionDataSource;
    private final HlsExtractorFactory extractorFactory;
    private final FullSegmentEncryptionKeyCache keyCache = new FullSegmentEncryptionKeyCache();
    private final DataSource mediaDataSource;
    private final List<Format> muxedCaptionFormats;
    private final Format[] playlistFormats;
    private final HlsPlaylistTracker playlistTracker;
    private final Uri[] playlistUrls;
    private final TimestampAdjusterProvider timestampAdjusterProvider;
    private final TrackGroup trackGroup;
    private Uri expectedPlaylistUrl;
    private IOException fatalError;
    private boolean independentSegments;
    private boolean isTimestampMaster;
    private long liveEdgeInPeriodTimeUs = C0841C.TIME_UNSET;
    private byte[] scratchSpace;
    private boolean seenExpectedPlaylistError;
    private TrackSelection trackSelection;

    public HlsChunkSource(HlsExtractorFactory extractorFactory2, HlsPlaylistTracker playlistTracker2, Uri[] playlistUrls2, Format[] playlistFormats2, HlsDataSourceFactory dataSourceFactory, @Nullable TransferListener mediaTransferListener, TimestampAdjusterProvider timestampAdjusterProvider2, List<Format> muxedCaptionFormats2) {
        this.extractorFactory = extractorFactory2;
        this.playlistTracker = playlistTracker2;
        this.playlistUrls = playlistUrls2;
        this.playlistFormats = playlistFormats2;
        this.timestampAdjusterProvider = timestampAdjusterProvider2;
        this.muxedCaptionFormats = muxedCaptionFormats2;
        this.mediaDataSource = dataSourceFactory.createDataSource(1);
        if (mediaTransferListener != null) {
            this.mediaDataSource.addTransferListener(mediaTransferListener);
        }
        this.encryptionDataSource = dataSourceFactory.createDataSource(3);
        this.trackGroup = new TrackGroup(playlistFormats2);
        int[] initialTrackSelection = new int[playlistUrls2.length];
        for (int i = 0; i < playlistUrls2.length; i++) {
            initialTrackSelection[i] = i;
        }
        this.trackSelection = new InitializationTrackSelection(this.trackGroup, initialTrackSelection);
    }

    @Nullable
    private static Uri getFullEncryptionKeyUri(HlsMediaPlaylist playlist, @Nullable HlsMediaPlaylist.Segment segment) {
        if (segment == null || segment.fullSegmentEncryptionKeyUri == null) {
            return null;
        }
        return UriUtil.resolveToUri(playlist.baseUri, segment.fullSegmentEncryptionKeyUri);
    }

    public void maybeThrowError() throws IOException {
        IOException iOException = this.fatalError;
        if (iOException == null) {
            Uri uri = this.expectedPlaylistUrl;
            if (uri != null && this.seenExpectedPlaylistError) {
                this.playlistTracker.maybeThrowPlaylistRefreshError(uri);
                return;
            }
            return;
        }
        throw iOException;
    }

    public TrackGroup getTrackGroup() {
        return this.trackGroup;
    }

    public void selectTracks(TrackSelection trackSelection2) {
        this.trackSelection = trackSelection2;
    }

    public TrackSelection getTrackSelection() {
        return this.trackSelection;
    }

    public void reset() {
        this.fatalError = null;
    }

    public void setIsTimestampMaster(boolean isTimestampMaster2) {
        this.isTimestampMaster = isTimestampMaster2;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [int, long]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    public void getNextChunk(long playbackPositionUs, long loadPositionUs, List<HlsMediaChunk> queue, HlsChunkHolder out) {
        long timeToLiveEdgeUs;
        long bufferedDurationUs;
        long startOfPlaylistInPeriodUs;
        Uri selectedPlaylistUrl;
        HlsMediaPlaylist mediaPlaylist;
        int selectedTrackIndex;
        long j = loadPositionUs;
        HlsChunkHolder hlsChunkHolder = out;
        HlsMediaChunk previous = queue.isEmpty() ? null : queue.get(queue.size() - 1);
        int oldTrackIndex = previous == null ? -1 : this.trackGroup.indexOf(previous.trackFormat);
        long bufferedDurationUs2 = j - playbackPositionUs;
        long timeToLiveEdgeUs2 = resolveTimeToLiveEdgeUs(playbackPositionUs);
        if (previous == null || this.independentSegments) {
            timeToLiveEdgeUs = timeToLiveEdgeUs2;
            bufferedDurationUs = bufferedDurationUs2;
        } else {
            long subtractedDurationUs = previous.getDurationUs();
            bufferedDurationUs = Math.max(0L, bufferedDurationUs2 - subtractedDurationUs);
            if (timeToLiveEdgeUs2 != C0841C.TIME_UNSET) {
                timeToLiveEdgeUs = Math.max(0L, timeToLiveEdgeUs2 - subtractedDurationUs);
            } else {
                timeToLiveEdgeUs = timeToLiveEdgeUs2;
            }
        }
        this.trackSelection.updateSelectedTrack(playbackPositionUs, bufferedDurationUs, timeToLiveEdgeUs, queue, createMediaChunkIterators(previous, j));
        int selectedTrackIndex2 = this.trackSelection.getSelectedIndexInTrackGroup();
        boolean switchingTrack = oldTrackIndex != selectedTrackIndex2;
        Uri selectedPlaylistUrl2 = this.playlistUrls[selectedTrackIndex2];
        if (!this.playlistTracker.isSnapshotValid(selectedPlaylistUrl2)) {
            hlsChunkHolder.playlistUrl = selectedPlaylistUrl2;
            this.seenExpectedPlaylistError &= selectedPlaylistUrl2.equals(this.expectedPlaylistUrl);
            this.expectedPlaylistUrl = selectedPlaylistUrl2;
            return;
        }
        HlsMediaPlaylist mediaPlaylist2 = this.playlistTracker.getPlaylistSnapshot(selectedPlaylistUrl2, true);
        this.independentSegments = mediaPlaylist2.hasIndependentSegments;
        updateLiveEdgeTimeUs(mediaPlaylist2);
        long startOfPlaylistInPeriodUs2 = mediaPlaylist2.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
        int oldTrackIndex2 = oldTrackIndex;
        HlsMediaPlaylist mediaPlaylist3 = mediaPlaylist2;
        Uri selectedPlaylistUrl3 = selectedPlaylistUrl2;
        HlsMediaChunk previous2 = previous;
        long chunkMediaSequence = getChunkMediaSequence(previous, switchingTrack, mediaPlaylist2, startOfPlaylistInPeriodUs2, loadPositionUs);
        if (chunkMediaSequence >= mediaPlaylist3.mediaSequence) {
            selectedTrackIndex = selectedTrackIndex2;
            mediaPlaylist = mediaPlaylist3;
            startOfPlaylistInPeriodUs = startOfPlaylistInPeriodUs2;
            selectedPlaylistUrl = selectedPlaylistUrl3;
        } else if (previous2 == null || !switchingTrack) {
            this.fatalError = new BehindLiveWindowException();
            return;
        } else {
            int selectedTrackIndex3 = oldTrackIndex2;
            Uri selectedPlaylistUrl4 = this.playlistUrls[selectedTrackIndex3];
            mediaPlaylist = this.playlistTracker.getPlaylistSnapshot(selectedPlaylistUrl4, true);
            chunkMediaSequence = previous2.getNextChunkIndex();
            startOfPlaylistInPeriodUs = mediaPlaylist.startTimeUs - this.playlistTracker.getInitialStartTimeUs();
            selectedPlaylistUrl = selectedPlaylistUrl4;
            selectedTrackIndex = selectedTrackIndex3;
        }
        int segmentIndexInPlaylist = (int) (chunkMediaSequence - mediaPlaylist.mediaSequence);
        if (segmentIndexInPlaylist < mediaPlaylist.segments.size()) {
            this.seenExpectedPlaylistError = false;
            this.expectedPlaylistUrl = null;
            HlsMediaPlaylist.Segment segment = mediaPlaylist.segments.get(segmentIndexInPlaylist);
            Uri initSegmentKeyUri = getFullEncryptionKeyUri(mediaPlaylist, segment.initializationSegment);
            hlsChunkHolder.chunk = maybeCreateEncryptionChunkFor(initSegmentKeyUri, selectedTrackIndex);
            if (hlsChunkHolder.chunk == null) {
                Uri mediaSegmentKeyUri = getFullEncryptionKeyUri(mediaPlaylist, segment);
                hlsChunkHolder.chunk = maybeCreateEncryptionChunkFor(mediaSegmentKeyUri, selectedTrackIndex);
                if (hlsChunkHolder.chunk == null) {
                    hlsChunkHolder.chunk = HlsMediaChunk.createInstance(this.extractorFactory, this.mediaDataSource, this.playlistFormats[selectedTrackIndex], startOfPlaylistInPeriodUs, mediaPlaylist, segmentIndexInPlaylist, selectedPlaylistUrl, this.muxedCaptionFormats, this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.isTimestampMaster, this.timestampAdjusterProvider, previous2, this.keyCache.get((Object) mediaSegmentKeyUri), this.keyCache.get((Object) initSegmentKeyUri));
                }
            }
        } else if (mediaPlaylist.hasEndTag) {
            hlsChunkHolder.endOfStream = true;
        } else {
            hlsChunkHolder.playlistUrl = selectedPlaylistUrl;
            this.seenExpectedPlaylistError &= selectedPlaylistUrl.equals(this.expectedPlaylistUrl);
            this.expectedPlaylistUrl = selectedPlaylistUrl;
        }
    }

    public void onChunkLoadCompleted(Chunk chunk) {
        if (chunk instanceof EncryptionKeyChunk) {
            EncryptionKeyChunk encryptionKeyChunk = (EncryptionKeyChunk) chunk;
            this.scratchSpace = encryptionKeyChunk.getDataHolder();
            this.keyCache.put(encryptionKeyChunk.dataSpec.uri, encryptionKeyChunk.getResult());
        }
    }

    public boolean maybeBlacklistTrack(Chunk chunk, long blacklistDurationMs) {
        TrackSelection trackSelection2 = this.trackSelection;
        return trackSelection2.blacklist(trackSelection2.indexOf(this.trackGroup.indexOf(chunk.trackFormat)), blacklistDurationMs);
    }

    public boolean onPlaylistError(Uri playlistUrl, long blacklistDurationMs) {
        int trackSelectionIndex;
        int trackGroupIndex = -1;
        int i = 0;
        while (true) {
            Uri[] uriArr = this.playlistUrls;
            if (i >= uriArr.length) {
                break;
            } else if (uriArr[i].equals(playlistUrl)) {
                trackGroupIndex = i;
                break;
            } else {
                i++;
            }
        }
        if (trackGroupIndex == -1 || (trackSelectionIndex = this.trackSelection.indexOf(trackGroupIndex)) == -1) {
            return true;
        }
        this.seenExpectedPlaylistError |= playlistUrl.equals(this.expectedPlaylistUrl);
        if (blacklistDurationMs == C0841C.TIME_UNSET || this.trackSelection.blacklist(trackSelectionIndex, blacklistDurationMs)) {
            return true;
        }
        return false;
    }

    public MediaChunkIterator[] createMediaChunkIterators(@Nullable HlsMediaChunk previous, long loadPositionUs) {
        HlsChunkSource hlsChunkSource = this;
        HlsMediaChunk hlsMediaChunk = previous;
        int oldTrackIndex = hlsMediaChunk == null ? -1 : hlsChunkSource.trackGroup.indexOf(hlsMediaChunk.trackFormat);
        MediaChunkIterator[] chunkIterators = new MediaChunkIterator[hlsChunkSource.trackSelection.length()];
        int i = 0;
        while (i < chunkIterators.length) {
            int trackIndex = hlsChunkSource.trackSelection.getIndexInTrackGroup(i);
            Uri playlistUrl = hlsChunkSource.playlistUrls[trackIndex];
            if (!hlsChunkSource.playlistTracker.isSnapshotValid(playlistUrl)) {
                chunkIterators[i] = MediaChunkIterator.EMPTY;
            } else {
                HlsMediaPlaylist playlist = hlsChunkSource.playlistTracker.getPlaylistSnapshot(playlistUrl, false);
                long startOfPlaylistInPeriodUs = playlist.startTimeUs - hlsChunkSource.playlistTracker.getInitialStartTimeUs();
                long startOfPlaylistInPeriodUs2 = startOfPlaylistInPeriodUs;
                long chunkMediaSequence = getChunkMediaSequence(previous, trackIndex != oldTrackIndex, playlist, startOfPlaylistInPeriodUs, loadPositionUs);
                if (chunkMediaSequence < playlist.mediaSequence) {
                    chunkIterators[i] = MediaChunkIterator.EMPTY;
                } else {
                    chunkIterators[i] = new HlsMediaPlaylistSegmentIterator(playlist, startOfPlaylistInPeriodUs2, (int) (chunkMediaSequence - playlist.mediaSequence));
                }
            }
            i++;
            hlsChunkSource = this;
        }
        return chunkIterators;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int
     arg types: [java.util.List<com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist$Segment>, java.lang.Long, int, boolean]
     candidates:
      com.google.android.exoplayer2.util.Util.binarySearchFloor(int[], int, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(long[], long, boolean, boolean):int
      com.google.android.exoplayer2.util.Util.binarySearchFloor(java.util.List, java.lang.Comparable, boolean, boolean):int */
    private long getChunkMediaSequence(@Nullable HlsMediaChunk previous, boolean switchingTrack, HlsMediaPlaylist mediaPlaylist, long startOfPlaylistInPeriodUs, long loadPositionUs) {
        HlsMediaChunk hlsMediaChunk = previous;
        HlsMediaPlaylist hlsMediaPlaylist = mediaPlaylist;
        if (hlsMediaChunk != null && !switchingTrack) {
            return previous.getNextChunkIndex();
        }
        long endOfPlaylistInPeriodUs = startOfPlaylistInPeriodUs + hlsMediaPlaylist.durationUs;
        long targetPositionInPeriodUs = (hlsMediaChunk == null || this.independentSegments) ? loadPositionUs : hlsMediaChunk.startTimeUs;
        if (!hlsMediaPlaylist.hasEndTag && targetPositionInPeriodUs >= endOfPlaylistInPeriodUs) {
            return hlsMediaPlaylist.mediaSequence + ((long) hlsMediaPlaylist.segments.size());
        }
        return ((long) Util.binarySearchFloor((List) hlsMediaPlaylist.segments, (Comparable) Long.valueOf(targetPositionInPeriodUs - startOfPlaylistInPeriodUs), true, !this.playlistTracker.isLive() || hlsMediaChunk == null)) + hlsMediaPlaylist.mediaSequence;
    }

    private long resolveTimeToLiveEdgeUs(long playbackPositionUs) {
        if (this.liveEdgeInPeriodTimeUs != C0841C.TIME_UNSET) {
            return this.liveEdgeInPeriodTimeUs - playbackPositionUs;
        }
        return C0841C.TIME_UNSET;
    }

    private void updateLiveEdgeTimeUs(HlsMediaPlaylist mediaPlaylist) {
        long j;
        if (mediaPlaylist.hasEndTag) {
            j = C0841C.TIME_UNSET;
        } else {
            j = mediaPlaylist.getEndTimeUs() - this.playlistTracker.getInitialStartTimeUs();
        }
        this.liveEdgeInPeriodTimeUs = j;
    }

    @Nullable
    private Chunk maybeCreateEncryptionChunkFor(@Nullable Uri keyUri, int selectedTrackIndex) {
        if (keyUri == null) {
            return null;
        }
        if (this.keyCache.containsKey(keyUri)) {
            FullSegmentEncryptionKeyCache fullSegmentEncryptionKeyCache = this.keyCache;
            fullSegmentEncryptionKeyCache.put(keyUri, (byte[]) fullSegmentEncryptionKeyCache.remove(keyUri));
            return null;
        }
        return new EncryptionKeyChunk(this.encryptionDataSource, new DataSpec(keyUri, 0, -1, null, 1), this.playlistFormats[selectedTrackIndex], this.trackSelection.getSelectionReason(), this.trackSelection.getSelectionData(), this.scratchSpace);
    }

    public static final class HlsChunkHolder {
        public Chunk chunk;
        public boolean endOfStream;
        public Uri playlistUrl;

        public HlsChunkHolder() {
            clear();
        }

        public void clear() {
            this.chunk = null;
            this.endOfStream = false;
            this.playlistUrl = null;
        }
    }

    private static final class InitializationTrackSelection extends BaseTrackSelection {
        private int selectedIndex;

        public InitializationTrackSelection(TrackGroup group, int[] tracks) {
            super(group, tracks);
            this.selectedIndex = indexOf(group.getFormat(0));
        }

        public void updateSelectedTrack(long playbackPositionUs, long bufferedDurationUs, long availableDurationUs, List<? extends MediaChunk> list, MediaChunkIterator[] mediaChunkIterators) {
            long nowMs = SystemClock.elapsedRealtime();
            if (isBlacklisted(this.selectedIndex, nowMs)) {
                for (int i = this.length - 1; i >= 0; i--) {
                    if (!isBlacklisted(i, nowMs)) {
                        this.selectedIndex = i;
                        return;
                    }
                }
                throw new IllegalStateException();
            }
        }

        public int getSelectedIndex() {
            return this.selectedIndex;
        }

        public int getSelectionReason() {
            return 0;
        }

        public Object getSelectionData() {
            return null;
        }
    }

    private static final class EncryptionKeyChunk extends DataChunk {
        private byte[] result;

        public EncryptionKeyChunk(DataSource dataSource, DataSpec dataSpec, Format trackFormat, int trackSelectionReason, Object trackSelectionData, byte[] scratchSpace) {
            super(dataSource, dataSpec, 3, trackFormat, trackSelectionReason, trackSelectionData, scratchSpace);
        }

        /* access modifiers changed from: protected */
        public void consume(byte[] data, int limit) {
            this.result = Arrays.copyOf(data, limit);
        }

        public byte[] getResult() {
            return this.result;
        }
    }

    private static final class HlsMediaPlaylistSegmentIterator extends BaseMediaChunkIterator {
        private final HlsMediaPlaylist playlist;
        private final long startOfPlaylistInPeriodUs;

        public HlsMediaPlaylistSegmentIterator(HlsMediaPlaylist playlist2, long startOfPlaylistInPeriodUs2, int chunkIndex) {
            super((long) chunkIndex, (long) (playlist2.segments.size() - 1));
            this.playlist = playlist2;
            this.startOfPlaylistInPeriodUs = startOfPlaylistInPeriodUs2;
        }

        public DataSpec getDataSpec() {
            checkInBounds();
            HlsMediaPlaylist.Segment segment = this.playlist.segments.get((int) getCurrentIndex());
            return new DataSpec(UriUtil.resolveToUri(this.playlist.baseUri, segment.url), segment.byterangeOffset, segment.byterangeLength, null);
        }

        public long getChunkStartTimeUs() {
            checkInBounds();
            return this.startOfPlaylistInPeriodUs + this.playlist.segments.get((int) getCurrentIndex()).relativeStartTimeUs;
        }

        public long getChunkEndTimeUs() {
            checkInBounds();
            HlsMediaPlaylist.Segment segment = this.playlist.segments.get((int) getCurrentIndex());
            return segment.durationUs + this.startOfPlaylistInPeriodUs + segment.relativeStartTimeUs;
        }
    }

    private static final class FullSegmentEncryptionKeyCache extends LinkedHashMap<Uri, byte[]> {
        public FullSegmentEncryptionKeyCache() {
            super(8, 1.0f, false);
        }

        public byte[] get(Object keyUri) {
            if (keyUri == null) {
                return null;
            }
            return (byte[]) super.get(keyUri);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.util.HashMap.put(java.lang.Object, java.lang.Object):V}
         arg types: [android.net.Uri, byte[]]
         candidates:
          com.google.android.exoplayer2.source.hls.HlsChunkSource.FullSegmentEncryptionKeyCache.put(android.net.Uri, byte[]):byte[]
          ClspMth{java.util.HashMap.put(java.lang.Object, java.lang.Object):V} */
        public byte[] put(Uri keyUri, byte[] key) {
            return (byte[]) super.put((Object) keyUri, (Object) ((byte[]) Assertions.checkNotNull(key)));
        }

        /* access modifiers changed from: protected */
        public boolean removeEldestEntry(Map.Entry<Uri, byte[]> entry) {
            return size() > 4;
        }
    }
}
