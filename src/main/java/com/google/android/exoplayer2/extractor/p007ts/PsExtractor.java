package com.google.android.exoplayer2.extractor.p007ts;

import android.support.p001v4.view.InputDeviceCompat;
import android.util.SparseArray;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.p007ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;

/* renamed from: com.google.android.exoplayer2.extractor.ts.PsExtractor */
public final class PsExtractor implements Extractor {
    public static final int AUDIO_STREAM = 192;
    public static final int AUDIO_STREAM_MASK = 224;
    public static final ExtractorsFactory FACTORY = PsExtractor$$Lambda$0.$instance;
    private static final long MAX_SEARCH_LENGTH = 1048576;
    private static final long MAX_SEARCH_LENGTH_AFTER_AUDIO_AND_VIDEO_FOUND = 8192;
    private static final int MAX_STREAM_ID_PLUS_ONE = 256;
    static final int MPEG_PROGRAM_END_CODE = 441;
    static final int PACKET_START_CODE_PREFIX = 1;
    static final int PACK_START_CODE = 442;
    public static final int PRIVATE_STREAM_1 = 189;
    static final int SYSTEM_HEADER_START_CODE = 443;
    public static final int VIDEO_STREAM = 224;
    public static final int VIDEO_STREAM_MASK = 240;
    private final PsDurationReader durationReader;
    private boolean foundAllTracks;
    private boolean foundAudioTrack;
    private boolean foundVideoTrack;
    private boolean hasOutputSeekMap;
    private long lastTrackPosition;
    private ExtractorOutput output;
    private PsBinarySearchSeeker psBinarySearchSeeker;
    private final ParsableByteArray psPacketBuffer;
    private final SparseArray<PesReader> psPayloadReaders;
    private final TimestampAdjuster timestampAdjuster;

    static final /* synthetic */ Extractor[] lambda$static$0$PsExtractor() {
        return new Extractor[]{new PsExtractor()};
    }

    public PsExtractor() {
        this(new TimestampAdjuster(0));
    }

    public PsExtractor(TimestampAdjuster timestampAdjuster2) {
        this.timestampAdjuster = timestampAdjuster2;
        this.psPacketBuffer = new ParsableByteArray(4096);
        this.psPayloadReaders = new SparseArray<>();
        this.durationReader = new PsDurationReader();
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        byte[] scratch = new byte[14];
        input.peekFully(scratch, 0, 14);
        if (442 != (((scratch[0] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | ((scratch[1] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((scratch[2] & UnsignedBytes.MAX_VALUE) << 8) | (scratch[3] & UnsignedBytes.MAX_VALUE)) || (scratch[4] & 196) != 68 || (scratch[6] & 4) != 4 || (scratch[8] & 4) != 4 || (scratch[9] & 1) != 1 || (scratch[12] & 3) != 3) {
            return false;
        }
        input.advancePeekPosition(scratch[13] & 7);
        input.peekFully(scratch, 0, 3);
        if (1 == ((scratch[2] & UnsignedBytes.MAX_VALUE) | ((scratch[0] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((scratch[1] & UnsignedBytes.MAX_VALUE) << 8))) {
            return true;
        }
        return false;
    }

    public void init(ExtractorOutput output2) {
        this.output = output2;
    }

    public void seek(long position, long timeUs) {
        if ((this.timestampAdjuster.getTimestampOffsetUs() == C0841C.TIME_UNSET) || !(this.timestampAdjuster.getFirstSampleTimestampUs() == 0 || this.timestampAdjuster.getFirstSampleTimestampUs() == timeUs)) {
            this.timestampAdjuster.reset();
            this.timestampAdjuster.setFirstSampleTimestampUs(timeUs);
        }
        PsBinarySearchSeeker psBinarySearchSeeker2 = this.psBinarySearchSeeker;
        if (psBinarySearchSeeker2 != null) {
            psBinarySearchSeeker2.setSeekTargetUs(timeUs);
        }
        for (int i = 0; i < this.psPayloadReaders.size(); i++) {
            this.psPayloadReaders.valueAt(i).seek();
        }
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        long maxSearchPosition;
        ExtractorInput extractorInput = input;
        PositionHolder positionHolder = seekPosition;
        long inputLength = input.getLength();
        if ((inputLength != -1) && !this.durationReader.isDurationReadFinished()) {
            return this.durationReader.readDuration(extractorInput, positionHolder);
        }
        maybeOutputSeekMap(inputLength);
        PsBinarySearchSeeker psBinarySearchSeeker2 = this.psBinarySearchSeeker;
        if (psBinarySearchSeeker2 != null && psBinarySearchSeeker2.isSeeking()) {
            return this.psBinarySearchSeeker.handlePendingSeek(extractorInput, positionHolder, null);
        }
        input.resetPeekPosition();
        long peekBytesLeft = inputLength != -1 ? inputLength - input.getPeekPosition() : -1;
        if ((peekBytesLeft != -1 && peekBytesLeft < 4) || !extractorInput.peekFully(this.psPacketBuffer.data, 0, 4, true)) {
            return -1;
        }
        this.psPacketBuffer.setPosition(0);
        int nextStartCode = this.psPacketBuffer.readInt();
        if (nextStartCode == 441) {
            return -1;
        }
        if (nextStartCode == 442) {
            extractorInput.peekFully(this.psPacketBuffer.data, 0, 10);
            this.psPacketBuffer.setPosition(9);
            extractorInput.skipFully((this.psPacketBuffer.readUnsignedByte() & 7) + 14);
            return 0;
        } else if (nextStartCode == 443) {
            extractorInput.peekFully(this.psPacketBuffer.data, 0, 2);
            this.psPacketBuffer.setPosition(0);
            extractorInput.skipFully(this.psPacketBuffer.readUnsignedShort() + 6);
            return 0;
        } else if (((nextStartCode & InputDeviceCompat.SOURCE_ANY) >> 8) != 1) {
            extractorInput.skipFully(1);
            return 0;
        } else {
            int streamId = nextStartCode & 255;
            PesReader payloadReader = this.psPayloadReaders.get(streamId);
            if (!this.foundAllTracks) {
                if (payloadReader == null) {
                    ElementaryStreamReader elementaryStreamReader = null;
                    if (streamId == 189) {
                        elementaryStreamReader = new Ac3Reader();
                        this.foundAudioTrack = true;
                        this.lastTrackPosition = input.getPosition();
                    } else if ((streamId & 224) == 192) {
                        elementaryStreamReader = new MpegAudioReader();
                        this.foundAudioTrack = true;
                        this.lastTrackPosition = input.getPosition();
                    } else if ((streamId & 240) == 224) {
                        elementaryStreamReader = new H262Reader();
                        this.foundVideoTrack = true;
                        this.lastTrackPosition = input.getPosition();
                    }
                    if (elementaryStreamReader != null) {
                        elementaryStreamReader.createTracks(this.output, new TsPayloadReader.TrackIdGenerator(streamId, 256));
                        PesReader payloadReader2 = new PesReader(elementaryStreamReader, this.timestampAdjuster);
                        this.psPayloadReaders.put(streamId, payloadReader2);
                        payloadReader = payloadReader2;
                    }
                }
                if (!this.foundAudioTrack || !this.foundVideoTrack) {
                    maxSearchPosition = 1048576;
                } else {
                    maxSearchPosition = this.lastTrackPosition + 8192;
                }
                if (input.getPosition() > maxSearchPosition) {
                    this.foundAllTracks = true;
                    this.output.endTracks();
                }
            }
            extractorInput.peekFully(this.psPacketBuffer.data, 0, 2);
            this.psPacketBuffer.setPosition(0);
            int pesLength = this.psPacketBuffer.readUnsignedShort() + 6;
            if (payloadReader == null) {
                extractorInput.skipFully(pesLength);
                return 0;
            }
            this.psPacketBuffer.reset(pesLength);
            extractorInput.readFully(this.psPacketBuffer.data, 0, pesLength);
            this.psPacketBuffer.setPosition(6);
            payloadReader.consume(this.psPacketBuffer);
            ParsableByteArray parsableByteArray = this.psPacketBuffer;
            parsableByteArray.setLimit(parsableByteArray.capacity());
            return 0;
        }
    }

    private void maybeOutputSeekMap(long inputLength) {
        if (!this.hasOutputSeekMap) {
            this.hasOutputSeekMap = true;
            if (this.durationReader.getDurationUs() != C0841C.TIME_UNSET) {
                this.psBinarySearchSeeker = new PsBinarySearchSeeker(this.durationReader.getScrTimestampAdjuster(), this.durationReader.getDurationUs(), inputLength);
                this.output.seekMap(this.psBinarySearchSeeker.getSeekMap());
                return;
            }
            this.output.seekMap(new SeekMap.Unseekable(this.durationReader.getDurationUs()));
        }
    }

    /* renamed from: com.google.android.exoplayer2.extractor.ts.PsExtractor$PesReader */
    private static final class PesReader {
        private static final int PES_SCRATCH_SIZE = 64;
        private boolean dtsFlag;
        private int extendedHeaderLength;
        private final ElementaryStreamReader pesPayloadReader;
        private final ParsableBitArray pesScratch = new ParsableBitArray(new byte[64]);
        private boolean ptsFlag;
        private boolean seenFirstDts;
        private long timeUs;
        private final TimestampAdjuster timestampAdjuster;

        public PesReader(ElementaryStreamReader pesPayloadReader2, TimestampAdjuster timestampAdjuster2) {
            this.pesPayloadReader = pesPayloadReader2;
            this.timestampAdjuster = timestampAdjuster2;
        }

        public void seek() {
            this.seenFirstDts = false;
            this.pesPayloadReader.seek();
        }

        public void consume(ParsableByteArray data) throws ParserException {
            data.readBytes(this.pesScratch.data, 0, 3);
            this.pesScratch.setPosition(0);
            parseHeader();
            data.readBytes(this.pesScratch.data, 0, this.extendedHeaderLength);
            this.pesScratch.setPosition(0);
            parseHeaderExtension();
            this.pesPayloadReader.packetStarted(this.timeUs, 4);
            this.pesPayloadReader.consume(data);
            this.pesPayloadReader.packetFinished();
        }

        private void parseHeader() {
            this.pesScratch.skipBits(8);
            this.ptsFlag = this.pesScratch.readBit();
            this.dtsFlag = this.pesScratch.readBit();
            this.pesScratch.skipBits(6);
            this.extendedHeaderLength = this.pesScratch.readBits(8);
        }

        private void parseHeaderExtension() {
            this.timeUs = 0;
            if (this.ptsFlag) {
                this.pesScratch.skipBits(4);
                this.pesScratch.skipBits(1);
                this.pesScratch.skipBits(1);
                long pts = (((long) this.pesScratch.readBits(3)) << 30) | ((long) (this.pesScratch.readBits(15) << 15)) | ((long) this.pesScratch.readBits(15));
                this.pesScratch.skipBits(1);
                if (!this.seenFirstDts && this.dtsFlag) {
                    this.pesScratch.skipBits(4);
                    this.pesScratch.skipBits(1);
                    this.pesScratch.skipBits(1);
                    this.pesScratch.skipBits(1);
                    this.timestampAdjuster.adjustTsTimestamp((((long) this.pesScratch.readBits(3)) << 30) | ((long) (this.pesScratch.readBits(15) << 15)) | ((long) this.pesScratch.readBits(15)));
                    this.seenFirstDts = true;
                }
                this.timeUs = this.timestampAdjuster.adjustTsTimestamp(pts);
            }
        }
    }
}
