package com.google.android.exoplayer2.extractor.ogg;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.io.IOException;

abstract class StreamReader {
    private static final int STATE_END_OF_INPUT = 3;
    private static final int STATE_READ_HEADERS = 0;
    private static final int STATE_READ_PAYLOAD = 2;
    private static final int STATE_SKIP_HEADERS = 1;
    private final OggPacket oggPacket = new OggPacket();
    private long currentGranule;
    private ExtractorOutput extractorOutput;
    private boolean formatSet;
    private long lengthOfReadPacket;
    private OggSeeker oggSeeker;
    private long payloadStartPosition;
    private int sampleRate;
    private boolean seekMapSet;
    private SetupData setupData;
    private int state;
    private long targetGranule;
    private TrackOutput trackOutput;

    /* access modifiers changed from: protected */
    public abstract long preparePayload(ParsableByteArray parsableByteArray);

    /* access modifiers changed from: protected */
    public abstract boolean readHeaders(ParsableByteArray parsableByteArray, long j, SetupData setupData2) throws IOException, InterruptedException;

    /* access modifiers changed from: package-private */
    public void init(ExtractorOutput output, TrackOutput trackOutput2) {
        this.extractorOutput = output;
        this.trackOutput = trackOutput2;
        reset(true);
    }

    /* access modifiers changed from: protected */
    public void reset(boolean headerData) {
        if (headerData) {
            this.setupData = new SetupData();
            this.payloadStartPosition = 0;
            this.state = 0;
        } else {
            this.state = 1;
        }
        this.targetGranule = -1;
        this.currentGranule = 0;
    }

    /* access modifiers changed from: package-private */
    public final void seek(long position, long timeUs) {
        this.oggPacket.reset();
        if (position == 0) {
            reset(!this.seekMapSet);
        } else if (this.state != 0) {
            this.targetGranule = this.oggSeeker.startSeek(timeUs);
            this.state = 2;
        }
    }

    /* access modifiers changed from: package-private */
    public final int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        int i = this.state;
        if (i == 0) {
            return readHeaders(input);
        }
        if (i == 1) {
            input.skipFully((int) this.payloadStartPosition);
            this.state = 2;
            return 0;
        } else if (i == 2) {
            return readPayload(input, seekPosition);
        } else {
            throw new IllegalStateException();
        }
    }

    private int readHeaders(ExtractorInput input) throws IOException, InterruptedException {
        boolean readingHeaders = true;
        while (readingHeaders) {
            if (!this.oggPacket.populate(input)) {
                this.state = 3;
                return -1;
            }
            this.lengthOfReadPacket = input.getPosition() - this.payloadStartPosition;
            readingHeaders = readHeaders(this.oggPacket.getPayload(), this.payloadStartPosition, this.setupData);
            if (readingHeaders) {
                this.payloadStartPosition = input.getPosition();
            }
        }
        this.sampleRate = this.setupData.format.sampleRate;
        if (!this.formatSet) {
            this.trackOutput.format(this.setupData.format);
            this.formatSet = true;
        }
        if (this.setupData.oggSeeker != null) {
            this.oggSeeker = this.setupData.oggSeeker;
        } else if (input.getLength() == -1) {
            this.oggSeeker = new UnseekableOggSeeker();
        } else {
            OggPageHeader firstPayloadPageHeader = this.oggPacket.getPageHeader();
            DefaultOggSeeker defaultOggSeeker = r0;
            DefaultOggSeeker defaultOggSeeker2 = new DefaultOggSeeker(this.payloadStartPosition, input.getLength(), this, (long) (firstPayloadPageHeader.headerSize + firstPayloadPageHeader.bodySize), firstPayloadPageHeader.granulePosition, (firstPayloadPageHeader.type & 4) != 0);
            this.oggSeeker = defaultOggSeeker;
        }
        this.setupData = null;
        this.state = 2;
        this.oggPacket.trimPayload();
        return 0;
    }

    private int readPayload(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        ExtractorInput extractorInput = input;
        long position = this.oggSeeker.read(extractorInput);
        if (position >= 0) {
            seekPosition.position = position;
            return 1;
        }
        if (position < -1) {
            onSeekEnd(-(2 + position));
        }
        if (!this.seekMapSet) {
            this.extractorOutput.seekMap(this.oggSeeker.createSeekMap());
            this.seekMapSet = true;
        }
        if (this.lengthOfReadPacket > 0 || this.oggPacket.populate(extractorInput)) {
            this.lengthOfReadPacket = 0;
            ParsableByteArray payload = this.oggPacket.getPayload();
            long granulesInPacket = preparePayload(payload);
            if (granulesInPacket >= 0) {
                long j = this.currentGranule;
                if (j + granulesInPacket >= this.targetGranule) {
                    long timeUs = convertGranuleToTime(j);
                    this.trackOutput.sampleData(payload, payload.limit());
                    this.trackOutput.sampleMetadata(timeUs, 1, payload.limit(), 0, null);
                    this.targetGranule = -1;
                }
            }
            this.currentGranule += granulesInPacket;
            return 0;
        }
        this.state = 3;
        return -1;
    }

    /* access modifiers changed from: protected */
    public long convertGranuleToTime(long granule) {
        return (1000000 * granule) / ((long) this.sampleRate);
    }

    /* access modifiers changed from: protected */
    public long convertTimeToGranule(long timeUs) {
        return (((long) this.sampleRate) * timeUs) / 1000000;
    }

    /* access modifiers changed from: protected */
    public void onSeekEnd(long currentGranule2) {
        this.currentGranule = currentGranule2;
    }

    static class SetupData {
        Format format;
        OggSeeker oggSeeker;

        SetupData() {
        }
    }

    private static final class UnseekableOggSeeker implements OggSeeker {
        private UnseekableOggSeeker() {
        }

        public long read(ExtractorInput input) throws IOException, InterruptedException {
            return -1;
        }

        public long startSeek(long timeUs) {
            return 0;
        }

        public SeekMap createSeekMap() {
            return new SeekMap.Unseekable(C0841C.TIME_UNSET);
        }
    }
}
