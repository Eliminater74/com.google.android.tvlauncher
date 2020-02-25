package com.google.android.exoplayer2.extractor.p007ts;

import android.util.Pair;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.DummyTrackOutput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.UnsignedBytes;

import java.util.Arrays;
import java.util.Collections;

/* renamed from: com.google.android.exoplayer2.extractor.ts.AdtsReader */
public final class AdtsReader implements ElementaryStreamReader {
    private static final int CRC_SIZE = 2;
    private static final int HEADER_SIZE = 5;
    private static final int ID3_HEADER_SIZE = 10;
    private static final byte[] ID3_IDENTIFIER = {73, 68, 51};
    private static final int ID3_SIZE_OFFSET = 6;
    private static final int MATCH_STATE_FF = 512;
    private static final int MATCH_STATE_I = 768;
    private static final int MATCH_STATE_ID = 1024;
    private static final int MATCH_STATE_START = 256;
    private static final int MATCH_STATE_VALUE_SHIFT = 8;
    private static final int STATE_CHECKING_ADTS_HEADER = 1;
    private static final int STATE_FINDING_SAMPLE = 0;
    private static final int STATE_READING_ADTS_HEADER = 3;
    private static final int STATE_READING_ID3_HEADER = 2;
    private static final int STATE_READING_SAMPLE = 4;
    private static final String TAG = "AdtsReader";
    private static final int VERSION_UNSET = -1;
    private final ParsableBitArray adtsScratch;
    private final boolean exposeId3;
    private final ParsableByteArray id3HeaderBuffer;
    private final String language;
    private int bytesRead;
    private int currentFrameVersion;
    private TrackOutput currentOutput;
    private long currentSampleDuration;
    private int firstFrameSampleRateIndex;
    private int firstFrameVersion;
    private String formatId;
    private boolean foundFirstFrame;
    private boolean hasCrc;
    private boolean hasOutputFormat;
    private TrackOutput id3Output;
    private int matchState;
    private TrackOutput output;
    private long sampleDurationUs;
    private int sampleSize;
    private int state;
    private long timeUs;

    public AdtsReader(boolean exposeId32) {
        this(exposeId32, null);
    }

    public AdtsReader(boolean exposeId32, String language2) {
        this.adtsScratch = new ParsableBitArray(new byte[7]);
        this.id3HeaderBuffer = new ParsableByteArray(Arrays.copyOf(ID3_IDENTIFIER, 10));
        setFindingSampleState();
        this.firstFrameVersion = -1;
        this.firstFrameSampleRateIndex = -1;
        this.sampleDurationUs = C0841C.TIME_UNSET;
        this.exposeId3 = exposeId32;
        this.language = language2;
    }

    public static boolean isAdtsSyncWord(int candidateSyncWord) {
        return (65526 & candidateSyncWord) == 65520;
    }

    public void seek() {
        resetSync();
    }

    public void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator idGenerator) {
        idGenerator.generateNewId();
        this.formatId = idGenerator.getFormatId();
        this.output = extractorOutput.track(idGenerator.getTrackId(), 1);
        if (this.exposeId3) {
            idGenerator.generateNewId();
            this.id3Output = extractorOutput.track(idGenerator.getTrackId(), 4);
            this.id3Output.format(Format.createSampleFormat(idGenerator.getFormatId(), MimeTypes.APPLICATION_ID3, null, -1, null));
            return;
        }
        this.id3Output = new DummyTrackOutput();
    }

    public void packetStarted(long pesTimeUs, int flags) {
        this.timeUs = pesTimeUs;
    }

    public void consume(ParsableByteArray data) throws ParserException {
        while (data.bytesLeft() > 0) {
            int i = this.state;
            if (i == 0) {
                findNextSample(data);
            } else if (i == 1) {
                checkAdtsHeader(data);
            } else if (i != 2) {
                if (i == 3) {
                    if (continueRead(data, this.adtsScratch.data, this.hasCrc ? 7 : 5)) {
                        parseAdtsHeader();
                    }
                } else if (i == 4) {
                    readSample(data);
                } else {
                    throw new IllegalStateException();
                }
            } else if (continueRead(data, this.id3HeaderBuffer.data, 10)) {
                parseId3Header();
            }
        }
    }

    public void packetFinished() {
    }

    public long getSampleDurationUs() {
        return this.sampleDurationUs;
    }

    private void resetSync() {
        this.foundFirstFrame = false;
        setFindingSampleState();
    }

    private boolean continueRead(ParsableByteArray source, byte[] target, int targetLength) {
        int bytesToRead = Math.min(source.bytesLeft(), targetLength - this.bytesRead);
        source.readBytes(target, this.bytesRead, bytesToRead);
        this.bytesRead += bytesToRead;
        return this.bytesRead == targetLength;
    }

    private void setFindingSampleState() {
        this.state = 0;
        this.bytesRead = 0;
        this.matchState = 256;
    }

    private void setReadingId3HeaderState() {
        this.state = 2;
        this.bytesRead = ID3_IDENTIFIER.length;
        this.sampleSize = 0;
        this.id3HeaderBuffer.setPosition(0);
    }

    private void setReadingSampleState(TrackOutput outputToUse, long currentSampleDuration2, int priorReadBytes, int sampleSize2) {
        this.state = 4;
        this.bytesRead = priorReadBytes;
        this.currentOutput = outputToUse;
        this.currentSampleDuration = currentSampleDuration2;
        this.sampleSize = sampleSize2;
    }

    private void setReadingAdtsHeaderState() {
        this.state = 3;
        this.bytesRead = 0;
    }

    private void setCheckingAdtsHeaderState() {
        this.state = 1;
        this.bytesRead = 0;
    }

    private void findNextSample(ParsableByteArray pesBuffer) {
        byte[] adtsData = pesBuffer.data;
        int data = pesBuffer.getPosition();
        int endOffset = pesBuffer.limit();
        while (data < endOffset) {
            int position = data + 1;
            int data2 = adtsData[data] & 255;
            if (this.matchState != 512 || !isAdtsSyncBytes((byte) -1, (byte) data2) || (!this.foundFirstFrame && !checkSyncPositionValid(pesBuffer, position - 2))) {
                int i = this.matchState;
                int i2 = i | data2;
                if (i2 == 329) {
                    this.matchState = 768;
                } else if (i2 == 511) {
                    this.matchState = 512;
                } else if (i2 == 836) {
                    this.matchState = 1024;
                } else if (i2 == 1075) {
                    setReadingId3HeaderState();
                    pesBuffer.setPosition(position);
                    return;
                } else if (i != 256) {
                    this.matchState = 256;
                    data = position - 1;
                }
                data = position;
            } else {
                this.currentFrameVersion = (data2 & 8) >> 3;
                this.hasCrc = (data2 & 1) == 0;
                if (!this.foundFirstFrame) {
                    setCheckingAdtsHeaderState();
                } else {
                    setReadingAdtsHeaderState();
                }
                pesBuffer.setPosition(position);
                return;
            }
        }
        pesBuffer.setPosition(data);
    }

    private void checkAdtsHeader(ParsableByteArray buffer) {
        if (buffer.bytesLeft() != 0) {
            this.adtsScratch.data[0] = buffer.data[buffer.getPosition()];
            this.adtsScratch.setPosition(2);
            int currentFrameSampleRateIndex = this.adtsScratch.readBits(4);
            int i = this.firstFrameSampleRateIndex;
            if (i == -1 || currentFrameSampleRateIndex == i) {
                if (!this.foundFirstFrame) {
                    this.foundFirstFrame = true;
                    this.firstFrameVersion = this.currentFrameVersion;
                    this.firstFrameSampleRateIndex = currentFrameSampleRateIndex;
                }
                setReadingAdtsHeaderState();
                return;
            }
            resetSync();
        }
    }

    private boolean checkSyncPositionValid(ParsableByteArray pesBuffer, int syncPositionCandidate) {
        pesBuffer.setPosition(syncPositionCandidate + 1);
        if (!tryRead(pesBuffer, this.adtsScratch.data, 1)) {
            return false;
        }
        this.adtsScratch.setPosition(4);
        int currentFrameVersion2 = this.adtsScratch.readBits(1);
        int i = this.firstFrameVersion;
        if (i != -1 && currentFrameVersion2 != i) {
            return false;
        }
        if (this.firstFrameSampleRateIndex != -1) {
            if (!tryRead(pesBuffer, this.adtsScratch.data, 1)) {
                return true;
            }
            this.adtsScratch.setPosition(2);
            if (this.adtsScratch.readBits(4) != this.firstFrameSampleRateIndex) {
                return false;
            }
            pesBuffer.setPosition(syncPositionCandidate + 2);
        }
        if (!tryRead(pesBuffer, this.adtsScratch.data, 4)) {
            return true;
        }
        this.adtsScratch.setPosition(14);
        int frameSize = this.adtsScratch.readBits(13);
        if (frameSize <= 6) {
            return false;
        }
        int nextSyncPosition = syncPositionCandidate + frameSize;
        if (nextSyncPosition + 1 >= pesBuffer.limit()) {
            return true;
        }
        if (!isAdtsSyncBytes(pesBuffer.data[nextSyncPosition], pesBuffer.data[nextSyncPosition + 1]) || (this.firstFrameVersion != -1 && ((pesBuffer.data[nextSyncPosition + 1] & 8) >> 3) != currentFrameVersion2)) {
            return false;
        }
        return true;
    }

    private boolean isAdtsSyncBytes(byte firstByte, byte secondByte) {
        return isAdtsSyncWord(((firstByte & UnsignedBytes.MAX_VALUE) << 8) | (secondByte & 255));
    }

    private boolean tryRead(ParsableByteArray source, byte[] target, int targetLength) {
        if (source.bytesLeft() < targetLength) {
            return false;
        }
        source.readBytes(target, 0, targetLength);
        return true;
    }

    private void parseId3Header() {
        this.id3Output.sampleData(this.id3HeaderBuffer, 10);
        this.id3HeaderBuffer.setPosition(6);
        setReadingSampleState(this.id3Output, 0, 10, this.id3HeaderBuffer.readSynchSafeInt() + 10);
    }

    private void parseAdtsHeader() throws ParserException {
        int sampleSize2;
        this.adtsScratch.setPosition(0);
        if (!this.hasOutputFormat) {
            int audioObjectType = this.adtsScratch.readBits(2) + 1;
            if (audioObjectType != 2) {
                StringBuilder sb = new StringBuilder(61);
                sb.append("Detected audio object type: ");
                sb.append(audioObjectType);
                sb.append(", but assuming AAC LC.");
                Log.m30w(TAG, sb.toString());
                audioObjectType = 2;
            }
            this.adtsScratch.skipBits(5);
            byte[] audioSpecificConfig = CodecSpecificDataUtil.buildAacAudioSpecificConfig(audioObjectType, this.firstFrameSampleRateIndex, this.adtsScratch.readBits(3));
            Pair<Integer, Integer> audioParams = CodecSpecificDataUtil.parseAacAudioSpecificConfig(audioSpecificConfig);
            Format format = Format.createAudioSampleFormat(this.formatId, MimeTypes.AUDIO_AAC, null, -1, -1, ((Integer) audioParams.second).intValue(), ((Integer) audioParams.first).intValue(), Collections.singletonList(audioSpecificConfig), null, 0, this.language);
            this.sampleDurationUs = 1024000000 / ((long) format.sampleRate);
            this.output.format(format);
            this.hasOutputFormat = true;
        } else {
            this.adtsScratch.skipBits(10);
        }
        this.adtsScratch.skipBits(4);
        int sampleSize3 = (this.adtsScratch.readBits(13) - 2) - 5;
        if (this.hasCrc) {
            sampleSize2 = sampleSize3 - 2;
        } else {
            sampleSize2 = sampleSize3;
        }
        setReadingSampleState(this.output, this.sampleDurationUs, 0, sampleSize2);
    }

    private void readSample(ParsableByteArray data) {
        int bytesToRead = Math.min(data.bytesLeft(), this.sampleSize - this.bytesRead);
        this.currentOutput.sampleData(data, bytesToRead);
        this.bytesRead += bytesToRead;
        int i = this.bytesRead;
        int i2 = this.sampleSize;
        if (i == i2) {
            this.currentOutput.sampleMetadata(this.timeUs, 1, i2, 0, null);
            this.timeUs += this.currentSampleDuration;
            setFindingSampleState();
        }
    }
}
