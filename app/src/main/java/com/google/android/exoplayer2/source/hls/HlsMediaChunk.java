package com.google.android.exoplayer2.source.hls;

import android.net.Uri;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.DefaultExtractorInput;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.id3.PrivFrame;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.source.hls.playlist.HlsMediaPlaylist;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

final class HlsMediaChunk extends MediaChunk {
    public static final String PRIV_TIMESTAMP_FRAME_OWNER = "com.apple.streaming.transportStreamTimestamp";
    private static final AtomicInteger uidSource = new AtomicInteger();
    public final int discontinuitySequenceNumber;
    public final Uri playlistUrl;
    public final int uid;
    @Nullable
    private final DrmInitData drmInitData;
    private final HlsExtractorFactory extractorFactory;
    private final boolean hasGapTag;
    private final Id3Decoder id3Decoder;
    @Nullable
    private final DataSource initDataSource;
    @Nullable
    private final DataSpec initDataSpec;
    private final boolean initSegmentEncrypted;
    private final boolean isMasterTimestampSource;
    private final boolean mediaSegmentEncrypted;
    @Nullable
    private final List<Format> muxedCaptionFormats;
    @Nullable
    private final Extractor previousExtractor;
    private final ParsableByteArray scratchId3Data;
    private final boolean shouldSpliceIn;
    private final TimestampAdjuster timestampAdjuster;
    private Extractor extractor;
    private boolean initDataLoadRequired;
    private boolean isExtractorReusable;
    private volatile boolean loadCanceled;
    private boolean loadCompleted;
    private int nextLoadPosition;
    private HlsSampleStreamWrapper output;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    private HlsMediaChunk(HlsExtractorFactory extractorFactory2, DataSource mediaDataSource, DataSpec dataSpec, Format format, boolean mediaSegmentEncrypted2, DataSource initDataSource2, @Nullable DataSpec initDataSpec2, boolean initSegmentEncrypted2, Uri playlistUrl2, @Nullable List<Format> muxedCaptionFormats2, int trackSelectionReason, Object trackSelectionData, long startTimeUs, long endTimeUs, long chunkMediaSequence, int discontinuitySequenceNumber2, boolean hasGapTag2, boolean isMasterTimestampSource2, TimestampAdjuster timestampAdjuster2, @Nullable DrmInitData drmInitData2, @Nullable Extractor previousExtractor2, Id3Decoder id3Decoder2, ParsableByteArray scratchId3Data2, boolean shouldSpliceIn2) {
        super(mediaDataSource, dataSpec, format, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs, chunkMediaSequence);
        DataSpec dataSpec2 = initDataSpec2;
        this.mediaSegmentEncrypted = mediaSegmentEncrypted2;
        this.discontinuitySequenceNumber = discontinuitySequenceNumber2;
        this.initDataSource = initDataSource2;
        this.initDataSpec = dataSpec2;
        this.initSegmentEncrypted = initSegmentEncrypted2;
        this.playlistUrl = playlistUrl2;
        this.isMasterTimestampSource = isMasterTimestampSource2;
        this.timestampAdjuster = timestampAdjuster2;
        this.hasGapTag = hasGapTag2;
        this.extractorFactory = extractorFactory2;
        this.muxedCaptionFormats = muxedCaptionFormats2;
        this.drmInitData = drmInitData2;
        this.previousExtractor = previousExtractor2;
        this.id3Decoder = id3Decoder2;
        this.scratchId3Data = scratchId3Data2;
        this.shouldSpliceIn = shouldSpliceIn2;
        this.initDataLoadRequired = dataSpec2 != null;
        this.uid = uidSource.getAndIncrement();
    }

    public static HlsMediaChunk createInstance(HlsExtractorFactory extractorFactory2, DataSource dataSource, Format format, long startOfPlaylistInPeriodUs, HlsMediaPlaylist mediaPlaylist, int segmentIndexInPlaylist, Uri playlistUrl2, @Nullable List<Format> muxedCaptionFormats2, int trackSelectionReason, @Nullable Object trackSelectionData, boolean isMasterTimestampSource2, TimestampAdjusterProvider timestampAdjusterProvider, @Nullable HlsMediaChunk previousChunk, @Nullable byte[] mediaSegmentKey, @Nullable byte[] initSegmentKey) {
        boolean initSegmentEncrypted2;
        DataSource initDataSource2;
        DataSpec initDataSpec2;
        boolean shouldSpliceIn2;
        ParsableByteArray scratchId3Data2;
        Id3Decoder id3Decoder2;
        Extractor previousExtractor2;
        DataSource dataSource2 = dataSource;
        HlsMediaPlaylist hlsMediaPlaylist = mediaPlaylist;
        byte[] bArr = mediaSegmentKey;
        byte[] bArr2 = initSegmentKey;
        HlsMediaPlaylist.Segment mediaSegment = hlsMediaPlaylist.segments.get(segmentIndexInPlaylist);
        DataSpec dataSpec = new DataSpec(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, mediaSegment.url), mediaSegment.byterangeOffset, mediaSegment.byterangeLength, null);
        boolean mediaSegmentEncrypted2 = bArr != null;
        DataSource mediaDataSource = buildDataSource(dataSource2, bArr, mediaSegmentEncrypted2 ? getEncryptionIvArray(mediaSegment.encryptionIV) : null);
        HlsMediaPlaylist.Segment initSegment = mediaSegment.initializationSegment;
        if (initSegment != null) {
            boolean initSegmentEncrypted3 = bArr2 != null;
            byte[] initSegmentIv = initSegmentEncrypted3 ? getEncryptionIvArray(initSegment.encryptionIV) : null;
            initDataSpec2 = new DataSpec(UriUtil.resolveToUri(hlsMediaPlaylist.baseUri, initSegment.url), initSegment.byterangeOffset, initSegment.byterangeLength, null);
            initDataSource2 = buildDataSource(dataSource2, bArr2, initSegmentIv);
            initSegmentEncrypted2 = initSegmentEncrypted3;
        } else {
            initSegmentEncrypted2 = false;
            initDataSource2 = null;
            initDataSpec2 = null;
        }
        long segmentStartTimeInPeriodUs = startOfPlaylistInPeriodUs + mediaSegment.relativeStartTimeUs;
        long segmentEndTimeInPeriodUs = segmentStartTimeInPeriodUs + mediaSegment.durationUs;
        int discontinuitySequenceNumber2 = hlsMediaPlaylist.discontinuitySequence + mediaSegment.relativeDiscontinuitySequence;
        HlsMediaChunk hlsMediaChunk = previousChunk;
        if (hlsMediaChunk != null) {
            Id3Decoder id3Decoder3 = hlsMediaChunk.id3Decoder;
            ParsableByteArray scratchId3Data3 = hlsMediaChunk.scratchId3Data;
            boolean shouldSpliceIn3 = !playlistUrl2.equals(hlsMediaChunk.playlistUrl) || !hlsMediaChunk.loadCompleted;
            if (!hlsMediaChunk.isExtractorReusable || hlsMediaChunk.discontinuitySequenceNumber != discontinuitySequenceNumber2 || shouldSpliceIn3) {
                previousExtractor2 = null;
            } else {
                previousExtractor2 = hlsMediaChunk.extractor;
            }
            id3Decoder2 = id3Decoder3;
            scratchId3Data2 = scratchId3Data3;
            shouldSpliceIn2 = shouldSpliceIn3;
        } else {
            id3Decoder2 = new Id3Decoder();
            previousExtractor2 = null;
            scratchId3Data2 = new ParsableByteArray(10);
            shouldSpliceIn2 = false;
        }
        return new HlsMediaChunk(extractorFactory2, mediaDataSource, dataSpec, format, mediaSegmentEncrypted2, initDataSource2, initDataSpec2, initSegmentEncrypted2, playlistUrl2, muxedCaptionFormats2, trackSelectionReason, trackSelectionData, segmentStartTimeInPeriodUs, segmentEndTimeInPeriodUs, hlsMediaPlaylist.mediaSequence + ((long) segmentIndexInPlaylist), discontinuitySequenceNumber2, mediaSegment.hasGapTag, isMasterTimestampSource2, timestampAdjusterProvider.getAdjuster(discontinuitySequenceNumber2), mediaSegment.drmInitData, previousExtractor2, id3Decoder2, scratchId3Data2, shouldSpliceIn2);
    }

    private static byte[] getEncryptionIvArray(String ivString) {
        String trimmedIv;
        if (Util.toLowerInvariant(ivString).startsWith("0x")) {
            trimmedIv = ivString.substring(2);
        } else {
            trimmedIv = ivString;
        }
        byte[] ivData = new BigInteger(trimmedIv, 16).toByteArray();
        byte[] ivDataWithPadding = new byte[16];
        int offset = ivData.length > 16 ? ivData.length - 16 : 0;
        System.arraycopy(ivData, offset, ivDataWithPadding, (ivDataWithPadding.length - ivData.length) + offset, ivData.length - offset);
        return ivDataWithPadding;
    }

    private static DataSource buildDataSource(DataSource dataSource, byte[] fullSegmentEncryptionKey, byte[] encryptionIv) {
        if (fullSegmentEncryptionKey != null) {
            return new Aes128DataSource(dataSource, fullSegmentEncryptionKey, encryptionIv);
        }
        return dataSource;
    }

    public void init(HlsSampleStreamWrapper output2) {
        this.output = output2;
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public void cancelLoad() {
        this.loadCanceled = true;
    }

    public void load() throws IOException, InterruptedException {
        Extractor extractor2;
        if (this.extractor == null && (extractor2 = this.previousExtractor) != null) {
            this.extractor = extractor2;
            this.isExtractorReusable = true;
            this.initDataLoadRequired = false;
            this.output.init(this.uid, this.shouldSpliceIn, true);
        }
        maybeLoadInitData();
        if (!this.loadCanceled) {
            if (!this.hasGapTag) {
                loadMedia();
            }
            this.loadCompleted = true;
        }
    }

    private void maybeLoadInitData() throws IOException, InterruptedException {
        if (this.initDataLoadRequired) {
            feedDataToExtractor(this.initDataSource, this.initDataSpec, this.initSegmentEncrypted);
            this.nextLoadPosition = 0;
            this.initDataLoadRequired = false;
        }
    }

    private void loadMedia() throws IOException, InterruptedException {
        if (!this.isMasterTimestampSource) {
            this.timestampAdjuster.waitUntilInitialized();
        } else if (this.timestampAdjuster.getFirstSampleTimestampUs() == Long.MAX_VALUE) {
            this.timestampAdjuster.setFirstSampleTimestampUs(this.startTimeUs);
        }
        feedDataToExtractor(this.dataSource, this.dataSpec, this.mediaSegmentEncrypted);
    }

    private void feedDataToExtractor(DataSource dataSource, DataSpec dataSpec, boolean dataIsEncrypted) throws IOException, InterruptedException {
        boolean skipLoadedBytes;
        DataSpec loadDataSpec;
        ExtractorInput input;
        if (dataIsEncrypted) {
            loadDataSpec = dataSpec;
            skipLoadedBytes = this.nextLoadPosition != 0;
        } else {
            loadDataSpec = dataSpec.subrange((long) this.nextLoadPosition);
            skipLoadedBytes = false;
        }
        try {
            input = prepareExtraction(dataSource, loadDataSpec);
            if (skipLoadedBytes) {
                input.skipFully(this.nextLoadPosition);
            }
            int result = 0;
            while (result == 0) {
                if (this.loadCanceled) {
                    break;
                }
                result = this.extractor.read(input, null);
            }
            this.nextLoadPosition = (int) (input.getPosition() - dataSpec.absoluteStreamPosition);
            Util.closeQuietly(dataSource);
        } catch (Throwable th) {
            Util.closeQuietly(dataSource);
            throw th;
        }
    }

    private DefaultExtractorInput prepareExtraction(DataSource dataSource, DataSpec dataSpec) throws IOException, InterruptedException {
        long j;
        DataSpec dataSpec2 = dataSpec;
        DataSource dataSource2 = dataSource;
        DefaultExtractorInput extractorInput = new DefaultExtractorInput(dataSource2, dataSpec2.absoluteStreamPosition, dataSource.open(dataSpec));
        if (this.extractor == null) {
            long id3Timestamp = peekId3PrivTimestamp(extractorInput);
            extractorInput.resetPeekPosition();
            HlsExtractorFactory hlsExtractorFactory = this.extractorFactory;
            Extractor extractor2 = this.previousExtractor;
            Uri uri = dataSpec2.uri;
            Format format = this.trackFormat;
            List<Format> list = this.muxedCaptionFormats;
            DrmInitData drmInitData2 = this.drmInitData;
            HlsExtractorFactory.Result result = hlsExtractorFactory.createExtractor(extractor2, uri, format, list, drmInitData2, this.timestampAdjuster, dataSource.getResponseHeaders(), extractorInput);
            this.extractor = result.extractor;
            this.isExtractorReusable = result.isReusable;
            if (result.isPackedAudioExtractor) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper = this.output;
                if (id3Timestamp != C0841C.TIME_UNSET) {
                    j = this.timestampAdjuster.adjustTsTimestamp(id3Timestamp);
                } else {
                    j = this.startTimeUs;
                }
                hlsSampleStreamWrapper.setSampleOffsetUs(j);
            }
            this.output.init(this.uid, this.shouldSpliceIn, false);
            this.extractor.init(this.output);
        }
        return extractorInput;
    }

    private long peekId3PrivTimestamp(ExtractorInput input) throws IOException, InterruptedException {
        input.resetPeekPosition();
        try {
            input.peekFully(this.scratchId3Data.data, 0, 10);
            this.scratchId3Data.reset(10);
            if (this.scratchId3Data.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
                return C0841C.TIME_UNSET;
            }
            this.scratchId3Data.skipBytes(3);
            int id3Size = this.scratchId3Data.readSynchSafeInt();
            int requiredCapacity = id3Size + 10;
            if (requiredCapacity > this.scratchId3Data.capacity()) {
                byte[] data = this.scratchId3Data.data;
                this.scratchId3Data.reset(requiredCapacity);
                System.arraycopy(data, 0, this.scratchId3Data.data, 0, 10);
            }
            input.peekFully(this.scratchId3Data.data, 10, id3Size);
            Metadata metadata = this.id3Decoder.decode(this.scratchId3Data.data, id3Size);
            if (metadata == null) {
                return C0841C.TIME_UNSET;
            }
            int metadataLength = metadata.length();
            for (int i = 0; i < metadataLength; i++) {
                Metadata.Entry frame = metadata.get(i);
                if (frame instanceof PrivFrame) {
                    PrivFrame privFrame = (PrivFrame) frame;
                    if (PRIV_TIMESTAMP_FRAME_OWNER.equals(privFrame.owner)) {
                        System.arraycopy(privFrame.privateData, 0, this.scratchId3Data.data, 0, 8);
                        this.scratchId3Data.reset(8);
                        return this.scratchId3Data.readLong() & 8589934591L;
                    }
                }
            }
            return C0841C.TIME_UNSET;
        } catch (EOFException e) {
            return C0841C.TIME_UNSET;
        }
    }
}
