package com.google.android.exoplayer2.extractor.p007ts;

import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.p007ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* renamed from: com.google.android.exoplayer2.extractor.ts.TsExtractor */
public final class TsExtractor implements Extractor {
    /* access modifiers changed from: private */
    public static final long AC3_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("AC-3"));
    /* access modifiers changed from: private */
    public static final long AC4_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("AC-4"));
    private static final int BUFFER_SIZE = 9400;
    /* access modifiers changed from: private */
    public static final long E_AC3_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("EAC3"));
    public static final ExtractorsFactory FACTORY = TsExtractor$$Lambda$0.$instance;
    /* access modifiers changed from: private */
    public static final long HEVC_FORMAT_IDENTIFIER = ((long) Util.getIntegerCodeForString("HEVC"));
    private static final int MAX_PID_PLUS_ONE = 8192;
    public static final int MODE_HLS = 2;
    public static final int MODE_MULTI_PMT = 0;
    public static final int MODE_SINGLE_PMT = 1;
    private static final int SNIFF_TS_PACKET_COUNT = 5;
    public static final int TS_PACKET_SIZE = 188;
    private static final int TS_PAT_PID = 0;
    public static final int TS_STREAM_TYPE_AAC_ADTS = 15;
    public static final int TS_STREAM_TYPE_AAC_LATM = 17;
    public static final int TS_STREAM_TYPE_AC3 = 129;
    public static final int TS_STREAM_TYPE_AC4 = 172;
    public static final int TS_STREAM_TYPE_DTS = 138;
    public static final int TS_STREAM_TYPE_DVBSUBS = 89;
    public static final int TS_STREAM_TYPE_E_AC3 = 135;
    public static final int TS_STREAM_TYPE_H262 = 2;
    public static final int TS_STREAM_TYPE_H264 = 27;
    public static final int TS_STREAM_TYPE_H265 = 36;
    public static final int TS_STREAM_TYPE_HDMV_DTS = 130;
    public static final int TS_STREAM_TYPE_ID3 = 21;
    public static final int TS_STREAM_TYPE_MPA = 3;
    public static final int TS_STREAM_TYPE_MPA_LSF = 4;
    public static final int TS_STREAM_TYPE_SPLICE_INFO = 134;
    public static final int TS_SYNC_BYTE = 71;
    private int bytesSinceLastSync;
    private final SparseIntArray continuityCounters;
    private final TsDurationReader durationReader;
    private boolean hasOutputSeekMap;
    /* access modifiers changed from: private */
    public TsPayloadReader id3Reader;
    /* access modifiers changed from: private */
    public final int mode;
    /* access modifiers changed from: private */
    public ExtractorOutput output;
    /* access modifiers changed from: private */
    public final TsPayloadReader.Factory payloadReaderFactory;
    /* access modifiers changed from: private */
    public int pcrPid;
    private boolean pendingSeekToStart;
    /* access modifiers changed from: private */
    public int remainingPmts;
    /* access modifiers changed from: private */
    public final List<TimestampAdjuster> timestampAdjusters;
    /* access modifiers changed from: private */
    public final SparseBooleanArray trackIds;
    /* access modifiers changed from: private */
    public final SparseBooleanArray trackPids;
    /* access modifiers changed from: private */
    public boolean tracksEnded;
    private TsBinarySearchSeeker tsBinarySearchSeeker;
    private final ParsableByteArray tsPacketBuffer;
    /* access modifiers changed from: private */
    public final SparseArray<TsPayloadReader> tsPayloadReaders;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsExtractor$Mode */
    public @interface Mode {
    }

    static /* synthetic */ int access$108(TsExtractor x0) {
        int i = x0.remainingPmts;
        x0.remainingPmts = i + 1;
        return i;
    }

    static final /* synthetic */ Extractor[] lambda$static$0$TsExtractor() {
        return new Extractor[]{new TsExtractor()};
    }

    public TsExtractor() {
        this(0);
    }

    public TsExtractor(int defaultTsPayloadReaderFlags) {
        this(1, defaultTsPayloadReaderFlags);
    }

    public TsExtractor(int mode2, int defaultTsPayloadReaderFlags) {
        this(mode2, new TimestampAdjuster(0), new DefaultTsPayloadReaderFactory(defaultTsPayloadReaderFlags));
    }

    public TsExtractor(int mode2, TimestampAdjuster timestampAdjuster, TsPayloadReader.Factory payloadReaderFactory2) {
        this.payloadReaderFactory = (TsPayloadReader.Factory) Assertions.checkNotNull(payloadReaderFactory2);
        this.mode = mode2;
        if (mode2 == 1 || mode2 == 2) {
            this.timestampAdjusters = Collections.singletonList(timestampAdjuster);
        } else {
            this.timestampAdjusters = new ArrayList();
            this.timestampAdjusters.add(timestampAdjuster);
        }
        this.tsPacketBuffer = new ParsableByteArray(new byte[BUFFER_SIZE], 0);
        this.trackIds = new SparseBooleanArray();
        this.trackPids = new SparseBooleanArray();
        this.tsPayloadReaders = new SparseArray<>();
        this.continuityCounters = new SparseIntArray();
        this.durationReader = new TsDurationReader();
        this.pcrPid = -1;
        resetPayloadReaders();
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        byte[] buffer = this.tsPacketBuffer.data;
        input.peekFully(buffer, 0, ClientAnalytics.LogRequest.LogSource.TOPAZ_TEAMS_VALUE);
        for (int startPosCandidate = 0; startPosCandidate < 188; startPosCandidate++) {
            boolean isSyncBytePatternCorrect = true;
            int i = 0;
            while (true) {
                if (i >= 5) {
                    break;
                } else if (buffer[(i * 188) + startPosCandidate] != 71) {
                    isSyncBytePatternCorrect = false;
                    break;
                } else {
                    i++;
                }
            }
            if (isSyncBytePatternCorrect) {
                input.skipFully(startPosCandidate);
                return true;
            }
        }
        return false;
    }

    public void init(ExtractorOutput output2) {
        this.output = output2;
    }

    public void seek(long position, long timeUs) {
        TsBinarySearchSeeker tsBinarySearchSeeker2;
        long j = timeUs;
        Assertions.checkState(this.mode != 2);
        int timestampAdjustersCount = this.timestampAdjusters.size();
        for (int i = 0; i < timestampAdjustersCount; i++) {
            TimestampAdjuster timestampAdjuster = this.timestampAdjusters.get(i);
            if ((timestampAdjuster.getTimestampOffsetUs() == C0841C.TIME_UNSET) || !(timestampAdjuster.getTimestampOffsetUs() == 0 || timestampAdjuster.getFirstSampleTimestampUs() == j)) {
                timestampAdjuster.reset();
                timestampAdjuster.setFirstSampleTimestampUs(j);
            }
        }
        if (!(j == 0 || (tsBinarySearchSeeker2 = this.tsBinarySearchSeeker) == null)) {
            tsBinarySearchSeeker2.setSeekTargetUs(j);
        }
        this.tsPacketBuffer.reset();
        this.continuityCounters.clear();
        for (int i2 = 0; i2 < this.tsPayloadReaders.size(); i2++) {
            this.tsPayloadReaders.valueAt(i2).seek();
        }
        this.bytesSinceLastSync = 0;
    }

    public void release() {
    }

    /* JADX INFO: Multiple debug info for r7v1 boolean: [D('wereTracksEnded' boolean), D('adaptationFieldLength' int)] */
    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        int i;
        ExtractorInput extractorInput = input;
        PositionHolder positionHolder = seekPosition;
        long inputLength = input.getLength();
        TsPayloadReader payloadReader = null;
        if (this.tracksEnded) {
            if (((inputLength == -1 || this.mode == 2) ? false : true) && !this.durationReader.isDurationReadFinished()) {
                return this.durationReader.readDuration(extractorInput, positionHolder, this.pcrPid);
            }
            maybeOutputSeekMap(inputLength);
            if (this.pendingSeekToStart) {
                this.pendingSeekToStart = false;
                seek(0, 0);
                if (input.getPosition() != 0) {
                    positionHolder.position = 0;
                    return 1;
                }
            }
            TsBinarySearchSeeker tsBinarySearchSeeker2 = this.tsBinarySearchSeeker;
            if (tsBinarySearchSeeker2 != null && tsBinarySearchSeeker2.isSeeking()) {
                return this.tsBinarySearchSeeker.handlePendingSeek(extractorInput, positionHolder, null);
            }
        }
        if (!fillBufferWithAtLeastOnePacket(input)) {
            return -1;
        }
        int endOfPacket = findEndOfFirstTsPacketInBuffer();
        int limit = this.tsPacketBuffer.limit();
        if (endOfPacket > limit) {
            return 0;
        }
        int tsPacketHeader = this.tsPacketBuffer.readInt();
        if ((8388608 & tsPacketHeader) != 0) {
            this.tsPacketBuffer.setPosition(endOfPacket);
            return 0;
        }
        int packetHeaderFlags = 0 | ((4194304 & tsPacketHeader) != 0 ? 1 : 0);
        int pid = (2096896 & tsPacketHeader) >> 8;
        boolean adaptationFieldExists = (tsPacketHeader & 32) != 0;
        if ((tsPacketHeader & 16) != 0) {
            payloadReader = this.tsPayloadReaders.get(pid);
        }
        if (payloadReader == null) {
            this.tsPacketBuffer.setPosition(endOfPacket);
            return 0;
        }
        if (this.mode != 2) {
            int continuityCounter = tsPacketHeader & 15;
            int previousCounter = this.continuityCounters.get(pid, continuityCounter - 1);
            this.continuityCounters.put(pid, continuityCounter);
            if (previousCounter == continuityCounter) {
                this.tsPacketBuffer.setPosition(endOfPacket);
                return 0;
            } else if (continuityCounter != ((previousCounter + 1) & 15)) {
                payloadReader.seek();
            }
        }
        if (adaptationFieldExists) {
            int adaptationFieldLength = this.tsPacketBuffer.readUnsignedByte();
            if ((this.tsPacketBuffer.readUnsignedByte() & 64) != 0) {
                i = 2;
            } else {
                i = 0;
            }
            packetHeaderFlags |= i;
            this.tsPacketBuffer.skipBytes(adaptationFieldLength - 1);
        }
        int adaptationFieldLength2 = this.tracksEnded;
        if (shouldConsumePacketPayload(pid)) {
            this.tsPacketBuffer.setLimit(endOfPacket);
            payloadReader.consume(this.tsPacketBuffer, packetHeaderFlags);
            this.tsPacketBuffer.setLimit(limit);
        }
        if (this.mode != 2 && adaptationFieldLength2 == 0 && this.tracksEnded && inputLength != -1) {
            this.pendingSeekToStart = true;
        }
        this.tsPacketBuffer.setPosition(endOfPacket);
        return 0;
    }

    private void maybeOutputSeekMap(long inputLength) {
        if (!this.hasOutputSeekMap) {
            this.hasOutputSeekMap = true;
            if (this.durationReader.getDurationUs() != C0841C.TIME_UNSET) {
                this.tsBinarySearchSeeker = new TsBinarySearchSeeker(this.durationReader.getPcrTimestampAdjuster(), this.durationReader.getDurationUs(), inputLength, this.pcrPid);
                this.output.seekMap(this.tsBinarySearchSeeker.getSeekMap());
                return;
            }
            this.output.seekMap(new SeekMap.Unseekable(this.durationReader.getDurationUs()));
        }
    }

    private boolean fillBufferWithAtLeastOnePacket(ExtractorInput input) throws IOException, InterruptedException {
        byte[] data = this.tsPacketBuffer.data;
        if (9400 - this.tsPacketBuffer.getPosition() < 188) {
            int bytesLeft = this.tsPacketBuffer.bytesLeft();
            if (bytesLeft > 0) {
                System.arraycopy(data, this.tsPacketBuffer.getPosition(), data, 0, bytesLeft);
            }
            this.tsPacketBuffer.reset(data, bytesLeft);
        }
        while (this.tsPacketBuffer.bytesLeft() < 188) {
            int limit = this.tsPacketBuffer.limit();
            int read = input.read(data, limit, 9400 - limit);
            if (read == -1) {
                return false;
            }
            this.tsPacketBuffer.setLimit(limit + read);
        }
        return true;
    }

    private int findEndOfFirstTsPacketInBuffer() throws ParserException {
        int searchStart = this.tsPacketBuffer.getPosition();
        int limit = this.tsPacketBuffer.limit();
        int syncBytePosition = TsUtil.findSyncBytePosition(this.tsPacketBuffer.data, searchStart, limit);
        this.tsPacketBuffer.setPosition(syncBytePosition);
        int endOfPacket = syncBytePosition + 188;
        if (endOfPacket > limit) {
            this.bytesSinceLastSync += syncBytePosition - searchStart;
            if (this.mode == 2 && this.bytesSinceLastSync > 376) {
                throw new ParserException("Cannot find sync byte. Most likely not a Transport Stream.");
            }
        } else {
            this.bytesSinceLastSync = 0;
        }
        return endOfPacket;
    }

    private boolean shouldConsumePacketPayload(int packetPid) {
        if (this.mode == 2 || this.tracksEnded || !this.trackPids.get(packetPid, false)) {
            return true;
        }
        return false;
    }

    private void resetPayloadReaders() {
        this.trackIds.clear();
        this.tsPayloadReaders.clear();
        SparseArray<TsPayloadReader> initialPayloadReaders = this.payloadReaderFactory.createInitialPayloadReaders();
        int initialPayloadReadersSize = initialPayloadReaders.size();
        for (int i = 0; i < initialPayloadReadersSize; i++) {
            this.tsPayloadReaders.put(initialPayloadReaders.keyAt(i), initialPayloadReaders.valueAt(i));
        }
        this.tsPayloadReaders.put(0, new SectionReader(new PatReader()));
        this.id3Reader = null;
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsExtractor$PatReader */
    private class PatReader implements SectionPayloadReader {
        private final ParsableBitArray patScratch = new ParsableBitArray(new byte[4]);

        public PatReader() {
        }

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        }

        public void consume(ParsableByteArray sectionData) {
            if (sectionData.readUnsignedByte() == 0) {
                sectionData.skipBytes(7);
                int programCount = sectionData.bytesLeft() / 4;
                for (int i = 0; i < programCount; i++) {
                    sectionData.readBytes(this.patScratch, 4);
                    int programNumber = this.patScratch.readBits(16);
                    this.patScratch.skipBits(3);
                    if (programNumber == 0) {
                        this.patScratch.skipBits(13);
                    } else {
                        int pid = this.patScratch.readBits(13);
                        TsExtractor.this.tsPayloadReaders.put(pid, new SectionReader(new PmtReader(pid)));
                        TsExtractor.access$108(TsExtractor.this);
                    }
                }
                if (TsExtractor.this.mode != 2) {
                    TsExtractor.this.tsPayloadReaders.remove(0);
                }
            }
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.TsExtractor$PmtReader */
    private class PmtReader implements SectionPayloadReader {
        private static final int TS_PMT_DESC_AC3 = 106;
        private static final int TS_PMT_DESC_DTS = 123;
        private static final int TS_PMT_DESC_DVBSUBS = 89;
        private static final int TS_PMT_DESC_DVB_EXT = 127;
        private static final int TS_PMT_DESC_DVB_EXT_AC4 = 21;
        private static final int TS_PMT_DESC_EAC3 = 122;
        private static final int TS_PMT_DESC_ISO639_LANG = 10;
        private static final int TS_PMT_DESC_REGISTRATION = 5;
        private final int pid;
        private final ParsableBitArray pmtScratch = new ParsableBitArray(new byte[5]);
        private final SparseIntArray trackIdToPidScratch = new SparseIntArray();
        private final SparseArray<TsPayloadReader> trackIdToReaderScratch = new SparseArray<>();

        public PmtReader(int pid2) {
            this.pid = pid2;
        }

        public void init(TimestampAdjuster timestampAdjuster, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        }

        public void consume(ParsableByteArray sectionData) {
            TimestampAdjuster timestampAdjuster;
            TsPayloadReader reader;
            ParsableByteArray parsableByteArray = sectionData;
            if (sectionData.readUnsignedByte() == 2) {
                if (TsExtractor.this.mode == 1 || TsExtractor.this.mode == 2 || TsExtractor.this.remainingPmts == 1) {
                    timestampAdjuster = (TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0);
                } else {
                    timestampAdjuster = new TimestampAdjuster(((TimestampAdjuster) TsExtractor.this.timestampAdjusters.get(0)).getFirstSampleTimestampUs());
                    TsExtractor.this.timestampAdjusters.add(timestampAdjuster);
                }
                parsableByteArray.skipBytes(2);
                int programNumber = sectionData.readUnsignedShort();
                int i = 3;
                parsableByteArray.skipBytes(3);
                parsableByteArray.readBytes(this.pmtScratch, 2);
                this.pmtScratch.skipBits(3);
                int i2 = 13;
                int unused = TsExtractor.this.pcrPid = this.pmtScratch.readBits(13);
                parsableByteArray.readBytes(this.pmtScratch, 2);
                int i3 = 4;
                this.pmtScratch.skipBits(4);
                int i4 = 12;
                parsableByteArray.skipBytes(this.pmtScratch.readBits(12));
                int i5 = 21;
                if (TsExtractor.this.mode == 2 && TsExtractor.this.id3Reader == null) {
                    TsPayloadReader.EsInfo dummyEsInfo = new TsPayloadReader.EsInfo(21, null, null, Util.EMPTY_BYTE_ARRAY);
                    TsExtractor tsExtractor = TsExtractor.this;
                    TsPayloadReader unused2 = tsExtractor.id3Reader = tsExtractor.payloadReaderFactory.createPayloadReader(21, dummyEsInfo);
                    TsExtractor.this.id3Reader.init(timestampAdjuster, TsExtractor.this.output, new TsPayloadReader.TrackIdGenerator(programNumber, 21, 8192));
                }
                this.trackIdToReaderScratch.clear();
                this.trackIdToPidScratch.clear();
                int remainingEntriesLength = sectionData.bytesLeft();
                while (remainingEntriesLength > 0) {
                    parsableByteArray.readBytes(this.pmtScratch, 5);
                    int streamType = this.pmtScratch.readBits(8);
                    this.pmtScratch.skipBits(i);
                    int elementaryPid = this.pmtScratch.readBits(i2);
                    this.pmtScratch.skipBits(i3);
                    int esInfoLength = this.pmtScratch.readBits(i4);
                    TsPayloadReader.EsInfo esInfo = readEsInfo(parsableByteArray, esInfoLength);
                    if (streamType == 6) {
                        streamType = esInfo.streamType;
                    }
                    remainingEntriesLength -= esInfoLength + 5;
                    int trackId = TsExtractor.this.mode == 2 ? streamType : elementaryPid;
                    if (!TsExtractor.this.trackIds.get(trackId)) {
                        if (TsExtractor.this.mode == 2 && streamType == i5) {
                            reader = TsExtractor.this.id3Reader;
                        } else {
                            reader = TsExtractor.this.payloadReaderFactory.createPayloadReader(streamType, esInfo);
                        }
                        if (TsExtractor.this.mode != 2 || elementaryPid < this.trackIdToPidScratch.get(trackId, 8192)) {
                            this.trackIdToPidScratch.put(trackId, elementaryPid);
                            this.trackIdToReaderScratch.put(trackId, reader);
                        }
                    }
                    i = 3;
                    i3 = 4;
                    i2 = 13;
                    i4 = 12;
                    i5 = 21;
                }
                int trackIdCount = this.trackIdToPidScratch.size();
                for (int i6 = 0; i6 < trackIdCount; i6++) {
                    int trackId2 = this.trackIdToPidScratch.keyAt(i6);
                    int trackPid = this.trackIdToPidScratch.valueAt(i6);
                    TsExtractor.this.trackIds.put(trackId2, true);
                    TsExtractor.this.trackPids.put(trackPid, true);
                    TsPayloadReader reader2 = this.trackIdToReaderScratch.valueAt(i6);
                    if (reader2 != null) {
                        if (reader2 != TsExtractor.this.id3Reader) {
                            reader2.init(timestampAdjuster, TsExtractor.this.output, new TsPayloadReader.TrackIdGenerator(programNumber, trackId2, 8192));
                        }
                        TsExtractor.this.tsPayloadReaders.put(trackPid, reader2);
                    }
                }
                if (TsExtractor.this.mode != 2) {
                    int i7 = 0;
                    TsExtractor.this.tsPayloadReaders.remove(this.pid);
                    TsExtractor tsExtractor2 = TsExtractor.this;
                    if (tsExtractor2.mode != 1) {
                        i7 = TsExtractor.this.remainingPmts - 1;
                    }
                    int unused3 = tsExtractor2.remainingPmts = i7;
                    if (TsExtractor.this.remainingPmts == 0) {
                        TsExtractor.this.output.endTracks();
                        boolean unused4 = TsExtractor.this.tracksEnded = true;
                    }
                } else if (!TsExtractor.this.tracksEnded) {
                    TsExtractor.this.output.endTracks();
                    int unused5 = TsExtractor.this.remainingPmts = 0;
                    boolean unused6 = TsExtractor.this.tracksEnded = true;
                }
            }
        }

        private TsPayloadReader.EsInfo readEsInfo(ParsableByteArray data, int length) {
            ParsableByteArray parsableByteArray = data;
            int descriptorsStartPosition = data.getPosition();
            int descriptorsEndPosition = descriptorsStartPosition + length;
            int streamType = -1;
            String language = null;
            List<TsPayloadReader.DvbSubtitleInfo> dvbSubtitleInfos = null;
            while (data.getPosition() < descriptorsEndPosition) {
                int descriptorTag = data.readUnsignedByte();
                int positionOfNextDescriptor = data.getPosition() + data.readUnsignedByte();
                if (descriptorTag == 5) {
                    long formatIdentifier = data.readUnsignedInt();
                    if (formatIdentifier == TsExtractor.AC3_FORMAT_IDENTIFIER) {
                        streamType = 129;
                    } else if (formatIdentifier == TsExtractor.E_AC3_FORMAT_IDENTIFIER) {
                        streamType = 135;
                    } else if (formatIdentifier == TsExtractor.AC4_FORMAT_IDENTIFIER) {
                        streamType = 172;
                    } else if (formatIdentifier == TsExtractor.HEVC_FORMAT_IDENTIFIER) {
                        streamType = 36;
                    }
                } else if (descriptorTag == 106) {
                    streamType = 129;
                } else if (descriptorTag == 122) {
                    streamType = 135;
                } else if (descriptorTag == 127) {
                    if (data.readUnsignedByte() == 21) {
                        streamType = 172;
                    }
                } else if (descriptorTag == 123) {
                    streamType = 138;
                } else if (descriptorTag == 10) {
                    language = parsableByteArray.readString(3).trim();
                } else if (descriptorTag == 89) {
                    streamType = 89;
                    dvbSubtitleInfos = new ArrayList<>();
                    while (data.getPosition() < positionOfNextDescriptor) {
                        String dvbLanguage = parsableByteArray.readString(3).trim();
                        int dvbSubtitlingType = data.readUnsignedByte();
                        byte[] initializationData = new byte[4];
                        parsableByteArray.readBytes(initializationData, 0, 4);
                        dvbSubtitleInfos.add(new TsPayloadReader.DvbSubtitleInfo(dvbLanguage, dvbSubtitlingType, initializationData));
                    }
                }
                parsableByteArray.skipBytes(positionOfNextDescriptor - data.getPosition());
            }
            parsableByteArray.setPosition(descriptorsEndPosition);
            return new TsPayloadReader.EsInfo(streamType, language, dvbSubtitleInfos, Arrays.copyOfRange(parsableByteArray.data, descriptorsStartPosition, descriptorsEndPosition));
        }
    }
}
