package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;

import java.io.IOException;

/* renamed from: com.google.android.exoplayer2.extractor.ts.Ac4Extractor */
public final class Ac4Extractor implements Extractor {
    public static final ExtractorsFactory FACTORY = Ac4Extractor$$Lambda$0.$instance;
    private static final int FRAME_HEADER_SIZE = 7;
    private static final int ID3_TAG = Util.getIntegerCodeForString("ID3");
    private static final int MAX_SNIFF_BYTES = 8192;
    private static final int READ_BUFFER_SIZE = 16384;
    private final long firstSampleTimestampUs;
    private final Ac4Reader reader;
    private final ParsableByteArray sampleData;
    private boolean startedPacket;

    public Ac4Extractor() {
        this(0);
    }

    public Ac4Extractor(long firstSampleTimestampUs2) {
        this.firstSampleTimestampUs = firstSampleTimestampUs2;
        this.reader = new Ac4Reader();
        this.sampleData = new ParsableByteArray(16384);
    }

    static final /* synthetic */ Extractor[] lambda$static$0$Ac4Extractor() {
        return new Extractor[]{new Ac4Extractor()};
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        ParsableByteArray scratch = new ParsableByteArray(10);
        int startPosition = 0;
        while (true) {
            input.peekFully(scratch.data, 0, 10);
            scratch.setPosition(0);
            if (scratch.readUnsignedInt24() != ID3_TAG) {
                break;
            }
            scratch.skipBytes(3);
            int length = scratch.readSynchSafeInt();
            startPosition += length + 10;
            input.advancePeekPosition(length);
        }
        input.resetPeekPosition();
        input.advancePeekPosition(startPosition);
        int headerPosition = startPosition;
        int validFramesCount = 0;
        while (true) {
            input.peekFully(scratch.data, 0, 7);
            scratch.setPosition(0);
            int syncBytes = scratch.readUnsignedShort();
            if (syncBytes == 44096 || syncBytes == 44097) {
                validFramesCount++;
                if (validFramesCount >= 4) {
                    return true;
                }
                int frameSize = Ac4Util.parseAc4SyncframeSize(scratch.data, syncBytes);
                if (frameSize == -1) {
                    return false;
                }
                input.advancePeekPosition(frameSize - 7);
            } else {
                validFramesCount = 0;
                input.resetPeekPosition();
                headerPosition++;
                if (headerPosition - startPosition >= 8192) {
                    return false;
                }
                input.advancePeekPosition(headerPosition);
            }
        }
    }

    public void init(ExtractorOutput output) {
        this.reader.createTracks(output, new TsPayloadReader.TrackIdGenerator(0, 1));
        output.endTracks();
        output.seekMap(new SeekMap.Unseekable(C0841C.TIME_UNSET));
    }

    public void seek(long position, long timeUs) {
        this.startedPacket = false;
        this.reader.seek();
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        int bytesRead = input.read(this.sampleData.data, 0, 16384);
        if (bytesRead == -1) {
            return -1;
        }
        this.sampleData.setPosition(0);
        this.sampleData.setLimit(bytesRead);
        if (!this.startedPacket) {
            this.reader.packetStarted(this.firstSampleTimestampUs, 4);
            this.startedPacket = true;
        }
        this.reader.consume(this.sampleData);
        return 0;
    }
}
