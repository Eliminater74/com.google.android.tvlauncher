package com.google.android.exoplayer2.extractor.mp3;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.Id3Peeker;
import com.google.android.exoplayer2.extractor.MpegAudioHeader;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.io.EOFException;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Mp3Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = Mp3Extractor$$Lambda$0.$instance;
    public static final int FLAG_DISABLE_ID3_METADATA = 2;
    public static final int FLAG_ENABLE_CONSTANT_BITRATE_SEEKING = 1;
    private static final int MAX_SNIFF_BYTES = 16384;
    private static final int MAX_SYNC_BYTES = 131072;
    private static final int MPEG_AUDIO_HEADER_MASK = -128000;
    private static final Id3Decoder.FramePredicate REQUIRED_ID3_FRAME_PREDICATE = Mp3Extractor$$Lambda$1.$instance;
    private static final int SCRATCH_LENGTH = 10;
    private static final int SEEK_HEADER_INFO = Util.getIntegerCodeForString("Info");
    private static final int SEEK_HEADER_UNSET = 0;
    private static final int SEEK_HEADER_VBRI = Util.getIntegerCodeForString("VBRI");
    private static final int SEEK_HEADER_XING = Util.getIntegerCodeForString("Xing");
    private final int flags;
    private final long forcedFirstSampleTimestampUs;
    private final GaplessInfoHolder gaplessInfoHolder;
    private final Id3Peeker id3Peeker;
    private final ParsableByteArray scratch;
    private final MpegAudioHeader synchronizedHeader;
    private long basisTimeUs;
    private ExtractorOutput extractorOutput;
    private Metadata metadata;
    private int sampleBytesRemaining;
    private long samplesRead;
    private Seeker seeker;
    private int synchronizedHeaderData;
    private TrackOutput trackOutput;

    public Mp3Extractor() {
        this(0);
    }

    public Mp3Extractor(int flags2) {
        this(flags2, C0841C.TIME_UNSET);
    }

    public Mp3Extractor(int flags2, long forcedFirstSampleTimestampUs2) {
        this.flags = flags2;
        this.forcedFirstSampleTimestampUs = forcedFirstSampleTimestampUs2;
        this.scratch = new ParsableByteArray(10);
        this.synchronizedHeader = new MpegAudioHeader();
        this.gaplessInfoHolder = new GaplessInfoHolder();
        this.basisTimeUs = C0841C.TIME_UNSET;
        this.id3Peeker = new Id3Peeker();
    }

    static final /* synthetic */ Extractor[] lambda$static$0$Mp3Extractor() {
        return new Extractor[]{new Mp3Extractor()};
    }

    static final /* synthetic */ boolean lambda$static$1$Mp3Extractor(int majorVersion, int id0, int id1, int id2, int id3) {
        return (id0 == 67 && id1 == 79 && id2 == 77 && (id3 == 77 || majorVersion == 2)) || (id0 == 77 && id1 == 76 && id2 == 76 && (id3 == 84 || majorVersion == 2));
    }

    private static boolean headersMatch(int headerA, long headerB) {
        return ((long) (MPEG_AUDIO_HEADER_MASK & headerA)) == (-128000 & headerB);
    }

    private static int getSeekFrameHeader(ParsableByteArray frame, int xingBase) {
        if (frame.limit() >= xingBase + 4) {
            frame.setPosition(xingBase);
            int headerData = frame.readInt();
            if (headerData == SEEK_HEADER_XING || headerData == SEEK_HEADER_INFO) {
                return headerData;
            }
        }
        if (frame.limit() < 40) {
            return 0;
        }
        frame.setPosition(36);
        int readInt = frame.readInt();
        int i = SEEK_HEADER_VBRI;
        if (readInt == i) {
            return i;
        }
        return 0;
    }

    @Nullable
    private static MlltSeeker maybeHandleSeekMetadata(Metadata metadata2, long firstFramePosition) {
        if (metadata2 == null) {
            return null;
        }
        int length = metadata2.length();
        for (int i = 0; i < length; i++) {
            Metadata.Entry entry = metadata2.get(i);
            if (entry instanceof MlltFrame) {
                return MlltSeeker.create(firstFramePosition, (MlltFrame) entry);
            }
        }
        return null;
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return synchronize(input, true);
    }

    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
        this.trackOutput = this.extractorOutput.track(0, 1);
        this.extractorOutput.endTracks();
    }

    public void seek(long position, long timeUs) {
        this.synchronizedHeaderData = 0;
        this.basisTimeUs = C0841C.TIME_UNSET;
        this.samplesRead = 0;
        this.sampleBytesRemaining = 0;
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        if (this.synchronizedHeaderData == 0) {
            try {
                synchronize(input, false);
            } catch (EOFException e) {
                return -1;
            }
        }
        if (this.seeker == null) {
            Seeker seekFrameSeeker = maybeReadSeekFrame(input);
            Seeker metadataSeeker = maybeHandleSeekMetadata(this.metadata, input.getPosition());
            if (metadataSeeker != null) {
                this.seeker = metadataSeeker;
            } else if (seekFrameSeeker != null) {
                this.seeker = seekFrameSeeker;
            }
            Seeker seeker2 = this.seeker;
            if (seeker2 == null || (!seeker2.isSeekable() && (this.flags & 1) != 0)) {
                this.seeker = getConstantBitrateSeeker(input);
            }
            this.extractorOutput.seekMap(this.seeker);
            this.trackOutput.format(Format.createAudioSampleFormat(null, this.synchronizedHeader.mimeType, null, -1, 4096, this.synchronizedHeader.channels, this.synchronizedHeader.sampleRate, -1, this.gaplessInfoHolder.encoderDelay, this.gaplessInfoHolder.encoderPadding, null, null, 0, null, (this.flags & 2) != 0 ? null : this.metadata));
        }
        return readSample(input);
    }

    private int readSample(ExtractorInput extractorInput) throws IOException, InterruptedException {
        if (this.sampleBytesRemaining == 0) {
            extractorInput.resetPeekPosition();
            if (peekEndOfStreamOrHeader(extractorInput)) {
                return -1;
            }
            this.scratch.setPosition(0);
            int sampleHeaderData = this.scratch.readInt();
            if (!headersMatch(sampleHeaderData, (long) this.synchronizedHeaderData) || MpegAudioHeader.getFrameSize(sampleHeaderData) == -1) {
                extractorInput.skipFully(1);
                this.synchronizedHeaderData = 0;
                return 0;
            }
            MpegAudioHeader.populateHeader(sampleHeaderData, this.synchronizedHeader);
            if (this.basisTimeUs == C0841C.TIME_UNSET) {
                this.basisTimeUs = this.seeker.getTimeUs(extractorInput.getPosition());
                if (this.forcedFirstSampleTimestampUs != C0841C.TIME_UNSET) {
                    this.basisTimeUs += this.forcedFirstSampleTimestampUs - this.seeker.getTimeUs(0);
                }
            }
            this.sampleBytesRemaining = this.synchronizedHeader.frameSize;
        }
        int bytesAppended = this.trackOutput.sampleData(extractorInput, this.sampleBytesRemaining, true);
        if (bytesAppended == -1) {
            return -1;
        }
        this.sampleBytesRemaining -= bytesAppended;
        if (this.sampleBytesRemaining > 0) {
            return 0;
        }
        this.trackOutput.sampleMetadata(this.basisTimeUs + ((this.samplesRead * 1000000) / ((long) this.synchronizedHeader.sampleRate)), 1, this.synchronizedHeader.frameSize, 0, null);
        this.samplesRead += (long) this.synchronizedHeader.samplesPerFrame;
        this.sampleBytesRemaining = 0;
        return 0;
    }

    private boolean synchronize(ExtractorInput input, boolean sniffing) throws IOException, InterruptedException {
        int validFrameCount = 0;
        int candidateSynchronizedHeaderData = 0;
        int peekedId3Bytes = 0;
        int searchedBytes = 0;
        int searchLimitBytes = sniffing ? 16384 : 131072;
        input.resetPeekPosition();
        if (input.getPosition() == 0) {
            this.metadata = this.id3Peeker.peekId3Data(input, (this.flags & 2) == 0 ? null : REQUIRED_ID3_FRAME_PREDICATE);
            Metadata metadata2 = this.metadata;
            if (metadata2 != null) {
                this.gaplessInfoHolder.setFromMetadata(metadata2);
            }
            peekedId3Bytes = (int) input.getPeekPosition();
            if (!sniffing) {
                input.skipFully(peekedId3Bytes);
            }
        }
        while (true) {
            if (!peekEndOfStreamOrHeader(input)) {
                this.scratch.setPosition(0);
                int headerData = this.scratch.readInt();
                if (candidateSynchronizedHeaderData == 0 || headersMatch(headerData, (long) candidateSynchronizedHeaderData)) {
                    int frameSize = MpegAudioHeader.getFrameSize(headerData);
                    int frameSize2 = frameSize;
                    if (frameSize != -1) {
                        validFrameCount++;
                        if (validFrameCount != 1) {
                            if (validFrameCount == 4) {
                                break;
                            }
                        } else {
                            MpegAudioHeader.populateHeader(headerData, this.synchronizedHeader);
                            candidateSynchronizedHeaderData = headerData;
                        }
                        input.advancePeekPosition(frameSize2 - 4);
                    }
                }
                int searchedBytes2 = searchedBytes + 1;
                if (searchedBytes != searchLimitBytes) {
                    validFrameCount = 0;
                    candidateSynchronizedHeaderData = 0;
                    if (sniffing) {
                        input.resetPeekPosition();
                        input.advancePeekPosition(peekedId3Bytes + searchedBytes2);
                    } else {
                        input.skipFully(1);
                    }
                    searchedBytes = searchedBytes2;
                } else if (sniffing) {
                    return false;
                } else {
                    throw new ParserException("Searched too many bytes.");
                }
            } else if (validFrameCount <= 0) {
                throw new EOFException();
            }
        }
        if (sniffing) {
            input.skipFully(peekedId3Bytes + searchedBytes);
        } else {
            input.resetPeekPosition();
        }
        this.synchronizedHeaderData = candidateSynchronizedHeaderData;
        return true;
    }

    private boolean peekEndOfStreamOrHeader(ExtractorInput extractorInput) throws IOException, InterruptedException {
        Seeker seeker2 = this.seeker;
        if (seeker2 != null) {
            long dataEndPosition = seeker2.getDataEndPosition();
            if (dataEndPosition != -1 && extractorInput.getPeekPosition() > dataEndPosition - 4) {
                return true;
            }
        }
        try {
            return !extractorInput.peekFully(this.scratch.data, 0, 4, true);
        } catch (EOFException e) {
            return true;
        }
    }

    private Seeker maybeReadSeekFrame(ExtractorInput input) throws IOException, InterruptedException {
        ParsableByteArray frame = new ParsableByteArray(this.synchronizedHeader.frameSize);
        input.peekFully(frame.data, 0, this.synchronizedHeader.frameSize);
        int i = 21;
        if ((this.synchronizedHeader.version & 1) != 0) {
            if (this.synchronizedHeader.channels != 1) {
                i = 36;
            }
        } else if (this.synchronizedHeader.channels == 1) {
            i = 13;
        }
        int xingBase = i;
        int seekHeader = getSeekFrameHeader(frame, xingBase);
        if (seekHeader == SEEK_HEADER_XING || seekHeader == SEEK_HEADER_INFO) {
            Seeker seeker2 = XingSeeker.create(input.getLength(), input.getPosition(), this.synchronizedHeader, frame);
            if (seeker2 != null && !this.gaplessInfoHolder.hasGaplessInfo()) {
                input.resetPeekPosition();
                input.advancePeekPosition(xingBase + 141);
                input.peekFully(this.scratch.data, 0, 3);
                this.scratch.setPosition(0);
                this.gaplessInfoHolder.setFromXingHeaderValue(this.scratch.readUnsignedInt24());
            }
            input.skipFully(this.synchronizedHeader.frameSize);
            if (seeker2 == null || seeker2.isSeekable() || seekHeader != SEEK_HEADER_INFO) {
                return seeker2;
            }
            return getConstantBitrateSeeker(input);
        } else if (seekHeader == SEEK_HEADER_VBRI) {
            Seeker seeker3 = VbriSeeker.create(input.getLength(), input.getPosition(), this.synchronizedHeader, frame);
            input.skipFully(this.synchronizedHeader.frameSize);
            return seeker3;
        } else {
            input.resetPeekPosition();
            return null;
        }
    }

    private Seeker getConstantBitrateSeeker(ExtractorInput input) throws IOException, InterruptedException {
        input.peekFully(this.scratch.data, 0, 4);
        this.scratch.setPosition(0);
        MpegAudioHeader.populateHeader(this.scratch.readInt(), this.synchronizedHeader);
        return new ConstantBitrateSeeker(input.getLength(), input.getPosition(), this.synchronizedHeader);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    interface Seeker extends SeekMap {
        long getDataEndPosition();

        long getTimeUs(long j);
    }
}
