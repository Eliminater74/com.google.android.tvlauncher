package com.google.android.exoplayer2.extractor.p007ts;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: com.google.android.exoplayer2.extractor.ts.Ac4Reader */
public final class Ac4Reader implements ElementaryStreamReader {
    private static final int STATE_FINDING_SYNC = 0;
    private static final int STATE_READING_HEADER = 1;
    private static final int STATE_READING_SAMPLE = 2;
    private final ParsableBitArray headerScratchBits;
    private final ParsableByteArray headerScratchBytes;
    private final String language;
    private int bytesRead;
    private Format format;
    private boolean hasCRC;
    private boolean lastByteWasAC;
    private TrackOutput output;
    private long sampleDurationUs;
    private int sampleSize;
    private int state;
    private long timeUs;
    private String trackFormatId;

    public Ac4Reader() {
        this(null);
    }

    public Ac4Reader(String language2) {
        this.headerScratchBits = new ParsableBitArray(new byte[16]);
        this.headerScratchBytes = new ParsableByteArray(this.headerScratchBits.data);
        this.state = 0;
        this.bytesRead = 0;
        this.lastByteWasAC = false;
        this.hasCRC = false;
        this.language = language2;
    }

    public void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.lastByteWasAC = false;
        this.hasCRC = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator generator) {
        generator.generateNewId();
        this.trackFormatId = generator.getFormatId();
        this.output = extractorOutput.track(generator.getTrackId(), 1);
    }

    public void packetStarted(long pesTimeUs, int flags) {
        this.timeUs = pesTimeUs;
    }

    public void consume(ParsableByteArray data) {
        while (data.bytesLeft() > 0) {
            int i = this.state;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        int bytesToRead = Math.min(data.bytesLeft(), this.sampleSize - this.bytesRead);
                        this.output.sampleData(data, bytesToRead);
                        this.bytesRead += bytesToRead;
                        int i2 = this.bytesRead;
                        int i3 = this.sampleSize;
                        if (i2 == i3) {
                            this.output.sampleMetadata(this.timeUs, 1, i3, 0, null);
                            this.timeUs += this.sampleDurationUs;
                            this.state = 0;
                        }
                    }
                } else if (continueRead(data, this.headerScratchBytes.data, 16)) {
                    parseHeader();
                    this.headerScratchBytes.setPosition(0);
                    this.output.sampleData(this.headerScratchBytes, 16);
                    this.state = 2;
                }
            } else if (skipToNextSync(data)) {
                this.state = 1;
                this.headerScratchBytes.data[0] = -84;
                this.headerScratchBytes.data[1] = (byte) (this.hasCRC ? 65 : 64);
                this.bytesRead = 2;
            }
        }
    }

    public void packetFinished() {
    }

    private boolean continueRead(ParsableByteArray source, byte[] target, int targetLength) {
        int bytesToRead = Math.min(source.bytesLeft(), targetLength - this.bytesRead);
        source.readBytes(target, this.bytesRead, bytesToRead);
        this.bytesRead += bytesToRead;
        return this.bytesRead == targetLength;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x002f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean skipToNextSync(com.google.android.exoplayer2.util.ParsableByteArray r6) {
        /*
            r5 = this;
        L_0x0000:
            int r0 = r6.bytesLeft()
            r1 = 0
            if (r0 <= 0) goto L_0x0033
            boolean r0 = r5.lastByteWasAC
            r2 = 172(0xac, float:2.41E-43)
            r3 = 1
            if (r0 != 0) goto L_0x0018
            int r0 = r6.readUnsignedByte()
            if (r0 != r2) goto L_0x0015
            r1 = 1
        L_0x0015:
            r5.lastByteWasAC = r1
            goto L_0x0000
        L_0x0018:
            int r0 = r6.readUnsignedByte()
            if (r0 != r2) goto L_0x0020
            r2 = 1
            goto L_0x0021
        L_0x0020:
            r2 = 0
        L_0x0021:
            r5.lastByteWasAC = r2
            r2 = 64
            r4 = 65
            if (r0 == r2) goto L_0x002d
            if (r0 != r4) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            goto L_0x0000
        L_0x002d:
            if (r0 != r4) goto L_0x0030
            r1 = 1
        L_0x0030:
            r5.hasCRC = r1
            return r3
        L_0x0033:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.p007ts.Ac4Reader.skipToNextSync(com.google.android.exoplayer2.util.ParsableByteArray):boolean");
    }

    private void parseHeader() {
        this.headerScratchBits.setPosition(0);
        Ac4Util.SyncFrameInfo frameInfo = Ac4Util.parseAc4SyncframeInfo(this.headerScratchBits);
        if (this.format == null || frameInfo.channelCount != this.format.channelCount || frameInfo.sampleRate != this.format.sampleRate || !MimeTypes.AUDIO_AC4.equals(this.format.sampleMimeType)) {
            this.format = Format.createAudioSampleFormat(this.trackFormatId, MimeTypes.AUDIO_AC4, null, -1, -1, frameInfo.channelCount, frameInfo.sampleRate, null, null, 0, this.language);
            this.output.format(this.format);
        }
        this.sampleSize = frameInfo.frameSize;
        this.sampleDurationUs = (((long) frameInfo.sampleCount) * 1000000) / ((long) this.format.sampleRate);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.extractor.ts.Ac4Reader$State */
    private @interface State {
    }
}
