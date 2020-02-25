package com.google.android.exoplayer2.extractor.flv;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class FlvExtractor implements Extractor {
    public static final ExtractorsFactory FACTORY = FlvExtractor$$Lambda$0.$instance;
    private static final int FLV_HEADER_SIZE = 9;
    private static final int FLV_TAG = Util.getIntegerCodeForString("FLV");
    private static final int FLV_TAG_HEADER_SIZE = 11;
    private static final int STATE_READING_FLV_HEADER = 1;
    private static final int STATE_READING_TAG_DATA = 4;
    private static final int STATE_READING_TAG_HEADER = 3;
    private static final int STATE_SKIPPING_TO_TAG_HEADER = 2;
    private static final int TAG_TYPE_AUDIO = 8;
    private static final int TAG_TYPE_SCRIPT_DATA = 18;
    private static final int TAG_TYPE_VIDEO = 9;
    private final ParsableByteArray headerBuffer = new ParsableByteArray(9);
    private final ScriptTagPayloadReader metadataReader = new ScriptTagPayloadReader();
    private final ParsableByteArray scratch = new ParsableByteArray(4);
    private final ParsableByteArray tagData = new ParsableByteArray();
    private final ParsableByteArray tagHeaderBuffer = new ParsableByteArray(11);
    private AudioTagPayloadReader audioReader;
    private int bytesToNextTagHeader;
    private ExtractorOutput extractorOutput;
    private long mediaTagTimestampOffsetUs = C0841C.TIME_UNSET;
    private boolean outputSeekMap;
    private int state = 1;
    private int tagDataSize;
    private long tagTimestampUs;
    private int tagType;
    private VideoTagPayloadReader videoReader;

    static final /* synthetic */ Extractor[] lambda$static$0$FlvExtractor() {
        return new Extractor[]{new FlvExtractor()};
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        input.peekFully(this.scratch.data, 0, 3);
        this.scratch.setPosition(0);
        if (this.scratch.readUnsignedInt24() != FLV_TAG) {
            return false;
        }
        input.peekFully(this.scratch.data, 0, 2);
        this.scratch.setPosition(0);
        if ((this.scratch.readUnsignedShort() & 250) != 0) {
            return false;
        }
        input.peekFully(this.scratch.data, 0, 4);
        this.scratch.setPosition(0);
        int dataOffset = this.scratch.readInt();
        input.resetPeekPosition();
        input.advancePeekPosition(dataOffset);
        input.peekFully(this.scratch.data, 0, 4);
        this.scratch.setPosition(0);
        if (this.scratch.readInt() == 0) {
            return true;
        }
        return false;
    }

    public void init(ExtractorOutput output) {
        this.extractorOutput = output;
    }

    public void seek(long position, long timeUs) {
        this.state = 1;
        this.mediaTagTimestampOffsetUs = C0841C.TIME_UNSET;
        this.bytesToNextTagHeader = 0;
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        while (true) {
            int i = this.state;
            if (i != 1) {
                if (i == 2) {
                    skipToTagHeader(input);
                } else if (i != 3) {
                    if (i != 4) {
                        throw new IllegalStateException();
                    } else if (readTagData(input)) {
                        return 0;
                    }
                } else if (!readTagHeader(input)) {
                    return -1;
                }
            } else if (!readFlvHeader(input)) {
                return -1;
            }
        }
    }

    private boolean readFlvHeader(ExtractorInput input) throws IOException, InterruptedException {
        boolean hasVideo = false;
        if (!input.readFully(this.headerBuffer.data, 0, 9, true)) {
            return false;
        }
        this.headerBuffer.setPosition(0);
        this.headerBuffer.skipBytes(4);
        int flags = this.headerBuffer.readUnsignedByte();
        boolean hasAudio = (flags & 4) != 0;
        if ((flags & 1) != 0) {
            hasVideo = true;
        }
        if (hasAudio && this.audioReader == null) {
            this.audioReader = new AudioTagPayloadReader(this.extractorOutput.track(8, 1));
        }
        if (hasVideo && this.videoReader == null) {
            this.videoReader = new VideoTagPayloadReader(this.extractorOutput.track(9, 2));
        }
        this.extractorOutput.endTracks();
        this.bytesToNextTagHeader = (this.headerBuffer.readInt() - 9) + 4;
        this.state = 2;
        return true;
    }

    private void skipToTagHeader(ExtractorInput input) throws IOException, InterruptedException {
        input.skipFully(this.bytesToNextTagHeader);
        this.bytesToNextTagHeader = 0;
        this.state = 3;
    }

    private boolean readTagHeader(ExtractorInput input) throws IOException, InterruptedException {
        if (!input.readFully(this.tagHeaderBuffer.data, 0, 11, true)) {
            return false;
        }
        this.tagHeaderBuffer.setPosition(0);
        this.tagType = this.tagHeaderBuffer.readUnsignedByte();
        this.tagDataSize = this.tagHeaderBuffer.readUnsignedInt24();
        this.tagTimestampUs = (long) this.tagHeaderBuffer.readUnsignedInt24();
        this.tagTimestampUs = (((long) (this.tagHeaderBuffer.readUnsignedByte() << 24)) | this.tagTimestampUs) * 1000;
        this.tagHeaderBuffer.skipBytes(3);
        this.state = 4;
        return true;
    }

    private boolean readTagData(ExtractorInput input) throws IOException, InterruptedException {
        boolean wasConsumed = true;
        if (this.tagType == 8 && this.audioReader != null) {
            ensureReadyForMediaOutput();
            this.audioReader.consume(prepareTagData(input), this.mediaTagTimestampOffsetUs + this.tagTimestampUs);
        } else if (this.tagType == 9 && this.videoReader != null) {
            ensureReadyForMediaOutput();
            this.videoReader.consume(prepareTagData(input), this.mediaTagTimestampOffsetUs + this.tagTimestampUs);
        } else if (this.tagType != 18 || this.outputSeekMap) {
            input.skipFully(this.tagDataSize);
            wasConsumed = false;
        } else {
            this.metadataReader.consume(prepareTagData(input), this.tagTimestampUs);
            long durationUs = this.metadataReader.getDurationUs();
            if (durationUs != C0841C.TIME_UNSET) {
                this.extractorOutput.seekMap(new SeekMap.Unseekable(durationUs));
                this.outputSeekMap = true;
            }
        }
        this.bytesToNextTagHeader = 4;
        this.state = 2;
        return wasConsumed;
    }

    private ParsableByteArray prepareTagData(ExtractorInput input) throws IOException, InterruptedException {
        if (this.tagDataSize > this.tagData.capacity()) {
            ParsableByteArray parsableByteArray = this.tagData;
            parsableByteArray.reset(new byte[Math.max(parsableByteArray.capacity() * 2, this.tagDataSize)], 0);
        } else {
            this.tagData.setPosition(0);
        }
        this.tagData.setLimit(this.tagDataSize);
        input.readFully(this.tagData.data, 0, this.tagDataSize);
        return this.tagData;
    }

    private void ensureReadyForMediaOutput() {
        if (!this.outputSeekMap) {
            this.extractorOutput.seekMap(new SeekMap.Unseekable(C0841C.TIME_UNSET));
            this.outputSeekMap = true;
        }
        if (this.mediaTagTimestampOffsetUs == C0841C.TIME_UNSET) {
            this.mediaTagTimestampOffsetUs = this.metadataReader.getDurationUs() == C0841C.TIME_UNSET ? -this.tagTimestampUs : 0;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    private @interface States {
    }
}
