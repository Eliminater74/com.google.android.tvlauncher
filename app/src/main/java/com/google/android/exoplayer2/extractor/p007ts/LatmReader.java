package com.google.android.exoplayer2.extractor.p007ts;

import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;

import java.util.Collections;

/* renamed from: com.google.android.exoplayer2.extractor.ts.LatmReader */
public final class LatmReader implements ElementaryStreamReader {
    private static final int INITIAL_BUFFER_SIZE = 1024;
    private static final int STATE_FINDING_SYNC_1 = 0;
    private static final int STATE_FINDING_SYNC_2 = 1;
    private static final int STATE_READING_HEADER = 2;
    private static final int STATE_READING_SAMPLE = 3;
    private static final int SYNC_BYTE_FIRST = 86;
    private static final int SYNC_BYTE_SECOND = 224;
    private final String language;
    private final ParsableByteArray sampleDataBuffer = new ParsableByteArray(1024);
    private final ParsableBitArray sampleBitArray = new ParsableBitArray(this.sampleDataBuffer.data);
    private int audioMuxVersionA;
    private int bytesRead;
    private int channelCount;
    private Format format;
    private String formatId;
    private int frameLengthType;
    private int numSubframes;
    private long otherDataLenBits;
    private boolean otherDataPresent;
    private TrackOutput output;
    private long sampleDurationUs;
    private int sampleRateHz;
    private int sampleSize;
    private int secondHeaderByte;
    private int state;
    private boolean streamMuxRead;
    private long timeUs;

    public LatmReader(@Nullable String language2) {
        this.language = language2;
    }

    private static long latmGetValue(ParsableBitArray data) {
        return (long) data.readBits((data.readBits(2) + 1) * 8);
    }

    public void seek() {
        this.state = 0;
        this.streamMuxRead = false;
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 1);
        this.formatId = idGenerator.getFormatId();
    }

    public void packetStarted(long pesTimeUs, int flags) {
        this.timeUs = pesTimeUs;
    }

    public void consume(ParsableByteArray data) throws ParserException {
        while (data.bytesLeft() > 0) {
            int i = this.state;
            if (i != 0) {
                if (i == 1) {
                    int secondByte = data.readUnsignedByte();
                    if ((secondByte & 224) == 224) {
                        this.secondHeaderByte = secondByte;
                        this.state = 2;
                    } else if (secondByte != 86) {
                        this.state = 0;
                    }
                } else if (i == 2) {
                    this.sampleSize = ((this.secondHeaderByte & -225) << 8) | data.readUnsignedByte();
                    if (this.sampleSize > this.sampleDataBuffer.data.length) {
                        resetBufferForSize(this.sampleSize);
                    }
                    this.bytesRead = 0;
                    this.state = 3;
                } else if (i == 3) {
                    int bytesToRead = Math.min(data.bytesLeft(), this.sampleSize - this.bytesRead);
                    data.readBytes(this.sampleBitArray.data, this.bytesRead, bytesToRead);
                    this.bytesRead += bytesToRead;
                    if (this.bytesRead == this.sampleSize) {
                        this.sampleBitArray.setPosition(0);
                        parseAudioMuxElement(this.sampleBitArray);
                        this.state = 0;
                    }
                } else {
                    throw new IllegalStateException();
                }
            } else if (data.readUnsignedByte() == 86) {
                this.state = 1;
            }
        }
    }

    public void packetFinished() {
    }

    private void parseAudioMuxElement(ParsableBitArray data) throws ParserException {
        if (!data.readBit()) {
            this.streamMuxRead = true;
            parseStreamMuxConfig(data);
        } else if (!this.streamMuxRead) {
            return;
        }
        if (this.audioMuxVersionA != 0) {
            throw new ParserException();
        } else if (this.numSubframes == 0) {
            parsePayloadMux(data, parsePayloadLengthInfo(data));
            if (this.otherDataPresent) {
                data.skipBits((int) this.otherDataLenBits);
            }
        } else {
            throw new ParserException();
        }
    }

    /* JADX INFO: Multiple debug info for r2v4 boolean: [D('otherDataLenEsc' boolean), D('crcCheckPresent' boolean)] */
    private void parseStreamMuxConfig(ParsableBitArray data) throws ParserException {
        boolean otherDataLenEsc;
        ParsableBitArray parsableBitArray = data;
        int audioMuxVersion = parsableBitArray.readBits(1);
        this.audioMuxVersionA = audioMuxVersion == 1 ? parsableBitArray.readBits(1) : 0;
        if (this.audioMuxVersionA == 0) {
            if (audioMuxVersion == 1) {
                latmGetValue(data);
            }
            if (data.readBit()) {
                this.numSubframes = parsableBitArray.readBits(6);
                int numProgram = parsableBitArray.readBits(4);
                int numLayer = parsableBitArray.readBits(3);
                if (numProgram == 0 && numLayer == 0) {
                    if (audioMuxVersion == 0) {
                        int startPosition = data.getPosition();
                        int readBits = parseAudioSpecificConfig(data);
                        parsableBitArray.setPosition(startPosition);
                        byte[] initData = new byte[((readBits + 7) / 8)];
                        parsableBitArray.readBits(initData, 0, readBits);
                        Format format2 = Format.createAudioSampleFormat(this.formatId, MimeTypes.AUDIO_AAC, null, -1, -1, this.channelCount, this.sampleRateHz, Collections.singletonList(initData), null, 0, this.language);
                        if (!format2.equals(this.format)) {
                            this.format = format2;
                            this.sampleDurationUs = 1024000000 / ((long) format2.sampleRate);
                            this.output.format(format2);
                        }
                    } else {
                        parsableBitArray.skipBits(((int) latmGetValue(data)) - parseAudioSpecificConfig(data));
                    }
                    parseFrameLength(data);
                    this.otherDataPresent = data.readBit();
                    this.otherDataLenBits = 0;
                    if (this.otherDataPresent) {
                        if (audioMuxVersion == 1) {
                            this.otherDataLenBits = latmGetValue(data);
                        } else {
                            do {
                                otherDataLenEsc = data.readBit();
                                this.otherDataLenBits = (this.otherDataLenBits << 8) + ((long) parsableBitArray.readBits(8));
                            } while (otherDataLenEsc);
                        }
                    }
                    if (data.readBit()) {
                        parsableBitArray.skipBits(8);
                        return;
                    }
                    return;
                }
                throw new ParserException();
            }
            throw new ParserException();
        }
        throw new ParserException();
    }

    private void parseFrameLength(ParsableBitArray data) {
        this.frameLengthType = data.readBits(3);
        int i = this.frameLengthType;
        if (i == 0) {
            data.skipBits(8);
        } else if (i == 1) {
            data.skipBits(9);
        } else if (i == 3 || i == 4 || i == 5) {
            data.skipBits(6);
        } else if (i == 6 || i == 7) {
            data.skipBits(1);
        } else {
            throw new IllegalStateException();
        }
    }

    private int parseAudioSpecificConfig(ParsableBitArray data) throws ParserException {
        int bitsLeft = data.bitsLeft();
        Pair<Integer, Integer> config = CodecSpecificDataUtil.parseAacAudioSpecificConfig(data, true);
        this.sampleRateHz = ((Integer) config.first).intValue();
        this.channelCount = ((Integer) config.second).intValue();
        return bitsLeft - data.bitsLeft();
    }

    private int parsePayloadLengthInfo(ParsableBitArray data) throws ParserException {
        int tmp;
        int muxSlotLengthBytes = 0;
        if (this.frameLengthType == 0) {
            do {
                tmp = data.readBits(8);
                muxSlotLengthBytes += tmp;
            } while (tmp == 255);
            return muxSlotLengthBytes;
        }
        throw new ParserException();
    }

    private void parsePayloadMux(ParsableBitArray data, int muxLengthBytes) {
        int bitPosition = data.getPosition();
        if ((bitPosition & 7) == 0) {
            this.sampleDataBuffer.setPosition(bitPosition >> 3);
        } else {
            data.readBits(this.sampleDataBuffer.data, 0, muxLengthBytes * 8);
            this.sampleDataBuffer.setPosition(0);
        }
        this.output.sampleData(this.sampleDataBuffer, muxLengthBytes);
        this.output.sampleMetadata(this.timeUs, 1, muxLengthBytes, 0, null);
        this.timeUs += this.sampleDurationUs;
    }

    private void resetBufferForSize(int newSize) {
        this.sampleDataBuffer.reset(newSize);
        this.sampleBitArray.reset(this.sampleDataBuffer.data);
    }
}
