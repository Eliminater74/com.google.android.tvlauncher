package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Ascii;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import com.google.protos.datapol.SemanticAnnotations;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.nio.ByteBuffer;

public final class Ac4Util {
    public static final int AC40_SYNCWORD = 44096;
    public static final int AC41_SYNCWORD = 44097;
    private static final int CHANNEL_COUNT_2 = 2;
    public static final int HEADER_SIZE_FOR_PARSER = 16;
    private static final int[] SAMPLE_COUNT = {2002, 2000, 1920, SemanticAnnotations.SemanticType.ST_DEMOGRAPHIC_INFO_VALUE, SemanticAnnotations.SemanticType.ST_ANONYMOUS_DATA_VALUE, 1001, 1000, ClientAnalytics.LogRequest.LogSource.NBU_GCONNECT_KIMCHI_VALUE, ClientAnalytics.LogRequest.LogSource.BETTERBUG_ANDROID_VALUE, ClientAnalytics.LogRequest.LogSource.BETTERBUG_ANDROID_VALUE, ClientAnalytics.LogRequest.LogSource.PIGEON_EXPERIMENTAL_VALUE, 400, 400, 2048};

    public static final class SyncFrameInfo {
        public final int bitstreamVersion;
        public final int channelCount;
        public final int frameSize;
        public final int sampleCount;
        public final int sampleRate;

        private SyncFrameInfo(int bitstreamVersion2, int channelCount2, int sampleRate2, int frameSize2, int sampleCount2) {
            this.bitstreamVersion = bitstreamVersion2;
            this.channelCount = channelCount2;
            this.sampleRate = sampleRate2;
            this.frameSize = frameSize2;
            this.sampleCount = sampleCount2;
        }
    }

    public static Format parseAc4AnnexEFormat(ParsableByteArray data, String trackId, String language, DrmInitData drmInitData) {
        data.skipBytes(1);
        return Format.createAudioSampleFormat(trackId, MimeTypes.AUDIO_AC4, null, -1, -1, 2, ((data.readUnsignedByte() & 32) >> 5) == 1 ? 48000 : 44100, null, drmInitData, 0, language);
    }

    public static SyncFrameInfo parseAc4SyncframeInfo(ParsableBitArray data) {
        int bitstreamVersion;
        int sampleCount;
        ParsableBitArray parsableBitArray = data;
        int syncWord = parsableBitArray.readBits(16);
        int frameSize = parsableBitArray.readBits(16);
        int headerSize = 0 + 2 + 2;
        if (frameSize == 65535) {
            frameSize = parsableBitArray.readBits(24);
            headerSize += 3;
        }
        int frameSize2 = frameSize + headerSize;
        if (syncWord == 44097) {
            frameSize2 += 2;
        }
        int bitstreamVersion2 = parsableBitArray.readBits(2);
        if (bitstreamVersion2 == 3) {
            bitstreamVersion = bitstreamVersion2 + readVariableBits(parsableBitArray, 2);
        } else {
            bitstreamVersion = bitstreamVersion2;
        }
        int sequenceCounter = parsableBitArray.readBits(10);
        if (data.readBit() && parsableBitArray.readBits(3) > 0) {
            parsableBitArray.skipBits(2);
        }
        int sampleRate = data.readBit() ? 48000 : 44100;
        int frameRateIndex = parsableBitArray.readBits(4);
        if (sampleRate == 44100 && frameRateIndex == 13) {
            sampleCount = SAMPLE_COUNT[frameRateIndex];
        } else {
            if (sampleRate == 48000) {
                int[] iArr = SAMPLE_COUNT;
                if (frameRateIndex < iArr.length) {
                    int sampleCount2 = iArr[frameRateIndex];
                    int i = sequenceCounter % 5;
                    if (i != 1) {
                        if (i == 2) {
                            if (frameRateIndex == 8 || frameRateIndex == 11) {
                                sampleCount = sampleCount2 + 1;
                            }
                            sampleCount = sampleCount2;
                        } else if (i != 3) {
                            if (i == 4 && (frameRateIndex == 3 || frameRateIndex == 8 || frameRateIndex == 11)) {
                                sampleCount = sampleCount2 + 1;
                            }
                            sampleCount = sampleCount2;
                        }
                    }
                    if (frameRateIndex == 3 || frameRateIndex == 8) {
                        sampleCount = sampleCount2 + 1;
                    }
                    sampleCount = sampleCount2;
                }
            }
            sampleCount = 0;
        }
        return new SyncFrameInfo(bitstreamVersion, 2, sampleRate, frameSize2, sampleCount);
    }

    public static int parseAc4SyncframeSize(byte[] data, int syncword) {
        if (data.length < 7) {
            return -1;
        }
        int frameSize = ((data[2] & UnsignedBytes.MAX_VALUE) << 8) | (data[3] & 255);
        int headerSize = 2 + 2;
        if (frameSize == 65535) {
            frameSize = ((data[4] & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((data[5] & UnsignedBytes.MAX_VALUE) << 8) | (data[6] & 255);
            headerSize += 3;
        }
        if (syncword == 44097) {
            headerSize += 2;
        }
        return frameSize + headerSize;
    }

    public static int parseAc4SyncframeAudioSampleCount(ByteBuffer buffer) {
        byte[] bufferBytes = new byte[16];
        int position = buffer.position();
        buffer.get(bufferBytes);
        buffer.position(position);
        return parseAc4SyncframeInfo(new ParsableBitArray(bufferBytes)).sampleCount;
    }

    public static void getAc4SampleHeader(int size, ParsableByteArray buffer) {
        buffer.reset(7);
        buffer.data[0] = -84;
        buffer.data[1] = SignedBytes.MAX_POWER_OF_TWO;
        buffer.data[2] = -1;
        buffer.data[3] = -1;
        buffer.data[4] = (byte) ((size >> 16) & 255);
        buffer.data[5] = (byte) ((size >> 8) & 255);
        buffer.data[6] = (byte) (size & 255);
    }

    private static int readVariableBits(ParsableBitArray data, int bitsPerRead) {
        int value = 0;
        while (true) {
            int value2 = value + data.readBits(bitsPerRead);
            if (!data.readBit()) {
                return value2;
            }
            value = (value2 + 1) << bitsPerRead;
        }
    }

    private Ac4Util() {
    }
}
