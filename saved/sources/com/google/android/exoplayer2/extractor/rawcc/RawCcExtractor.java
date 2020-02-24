package com.google.android.exoplayer2.extractor.rawcc;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

public final class RawCcExtractor implements Extractor {
    private static final int HEADER_ID = Util.getIntegerCodeForString("RCC\u0001");
    private static final int HEADER_SIZE = 8;
    private static final int SCRATCH_SIZE = 9;
    private static final int STATE_READING_HEADER = 0;
    private static final int STATE_READING_SAMPLES = 2;
    private static final int STATE_READING_TIMESTAMP_AND_COUNT = 1;
    private static final int TIMESTAMP_SIZE_V0 = 4;
    private static final int TIMESTAMP_SIZE_V1 = 8;
    private final ParsableByteArray dataScratch = new ParsableByteArray(9);
    private final Format format;
    private int parserState = 0;
    private int remainingSampleCount;
    private int sampleBytesWritten;
    private long timestampUs;
    private TrackOutput trackOutput;
    private int version;

    public RawCcExtractor(Format format2) {
        this.format = format2;
    }

    public void init(ExtractorOutput output) {
        output.seekMap(new SeekMap.Unseekable(C0841C.TIME_UNSET));
        this.trackOutput = output.track(0, 3);
        output.endTracks();
        this.trackOutput.format(this.format);
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        this.dataScratch.reset();
        input.peekFully(this.dataScratch.data, 0, 8);
        if (this.dataScratch.readInt() == HEADER_ID) {
            return true;
        }
        return false;
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        while (true) {
            int i = this.parserState;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        parseSamples(input);
                        this.parserState = 1;
                        return 0;
                    }
                    throw new IllegalStateException();
                } else if (parseTimestampAndSampleCount(input)) {
                    this.parserState = 2;
                } else {
                    this.parserState = 0;
                    return -1;
                }
            } else if (!parseHeader(input)) {
                return -1;
            } else {
                this.parserState = 1;
            }
        }
    }

    public void seek(long position, long timeUs) {
        this.parserState = 0;
    }

    public void release() {
    }

    private boolean parseHeader(ExtractorInput input) throws IOException, InterruptedException {
        this.dataScratch.reset();
        if (!input.readFully(this.dataScratch.data, 0, 8, true)) {
            return false;
        }
        if (this.dataScratch.readInt() == HEADER_ID) {
            this.version = this.dataScratch.readUnsignedByte();
            return true;
        }
        throw new IOException("Input not RawCC");
    }

    private boolean parseTimestampAndSampleCount(ExtractorInput input) throws IOException, InterruptedException {
        this.dataScratch.reset();
        int i = this.version;
        if (i == 0) {
            if (!input.readFully(this.dataScratch.data, 0, 5, true)) {
                return false;
            }
            this.timestampUs = (this.dataScratch.readUnsignedInt() * 1000) / 45;
        } else if (i != 1) {
            StringBuilder sb = new StringBuilder(39);
            sb.append("Unsupported version number: ");
            sb.append(i);
            throw new ParserException(sb.toString());
        } else if (!input.readFully(this.dataScratch.data, 0, 9, true)) {
            return false;
        } else {
            this.timestampUs = this.dataScratch.readLong();
        }
        this.remainingSampleCount = this.dataScratch.readUnsignedByte();
        this.sampleBytesWritten = 0;
        return true;
    }

    private void parseSamples(ExtractorInput input) throws IOException, InterruptedException {
        while (this.remainingSampleCount > 0) {
            this.dataScratch.reset();
            input.readFully(this.dataScratch.data, 0, 3);
            this.trackOutput.sampleData(this.dataScratch, 3);
            this.sampleBytesWritten += 3;
            this.remainingSampleCount--;
        }
        int i = this.sampleBytesWritten;
        if (i > 0) {
            this.trackOutput.sampleMetadata(this.timestampUs, 1, i, 0, null);
        }
    }
}
