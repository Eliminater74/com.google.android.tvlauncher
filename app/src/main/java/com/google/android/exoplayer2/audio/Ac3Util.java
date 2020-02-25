package com.google.android.exoplayer2.audio;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;

public final class Ac3Util {
    public static final int TRUEHD_RECHUNK_SAMPLE_COUNT = 16;
    public static final int TRUEHD_SYNCFRAME_PREFIX_LENGTH = 10;
    private static final int AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT = 1536;
    private static final int AUDIO_SAMPLES_PER_AUDIO_BLOCK = 256;
    private static final int[] BITRATE_BY_HALF_FRMSIZECOD = {32, 40, 48, 56, 64, 80, 96, 112, 128, ClientAnalytics.LogRequest.LogSource.JAM_KIOSK_ANDROID_PRIMES_VALUE, 192, 224, 256, ClientAnalytics.LogRequest.LogSource.ANDROID_MIGRATE_VALUE, ClientAnalytics.LogRequest.LogSource.TOOLKIT_QUICKSTART_VALUE, ClientAnalytics.LogRequest.LogSource.ANDROID_CREATIVE_PREVIEW_PRIMES_VALUE, 512, ClientAnalytics.LogRequest.LogSource.CLEARCUT_LOG_LOSS_VALUE, ClientAnalytics.LogRequest.LogSource.GMSCORE_BACKEND_COUNTERS_VALUE};
    private static final int[] BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD = {1, 2, 3, 6};
    private static final int[] CHANNEL_COUNT_BY_ACMOD = {2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] SAMPLE_RATE_BY_FSCOD = {48000, 44100, 32000};
    private static final int[] SAMPLE_RATE_BY_FSCOD2 = {24000, 22050, 16000};
    private static final int[] SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1 = {69, 87, 104, 121, 139, ClientAnalytics.LogRequest.LogSource.NQLOOKUP_VALUE, ClientAnalytics.LogRequest.LogSource.BLUETOOTH_VALUE, ClientAnalytics.LogRequest.LogSource.CW_COUNTERS_VALUE, ClientAnalytics.LogRequest.LogSource.YOUTUBE_DIRECTOR_APP_VALUE, ClientAnalytics.LogRequest.LogSource.SMARTCAM_VALUE, ClientAnalytics.LogRequest.LogSource.GMAIL_IOS_VALUE, ClientAnalytics.LogRequest.LogSource.LINKS_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.GOOGLETTS_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.YETI_TLS_PROXY_VALUE, ClientAnalytics.LogRequest.LogSource.GOOGLE_WIFI_IOS_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.SPEAKEASY_WEBRTC_STATS_VALUE, 1114, 1253, 1393};

    private Ac3Util() {
    }

    public static Format parseAc3AnnexFFormat(ParsableByteArray data, String trackId, String language, DrmInitData drmInitData) {
        int channelCount;
        int sampleRate = SAMPLE_RATE_BY_FSCOD[(data.readUnsignedByte() & 192) >> 6];
        int nextByte = data.readUnsignedByte();
        int channelCount2 = CHANNEL_COUNT_BY_ACMOD[(nextByte & 56) >> 3];
        if ((nextByte & 4) != 0) {
            channelCount = channelCount2 + 1;
        } else {
            channelCount = channelCount2;
        }
        return Format.createAudioSampleFormat(trackId, MimeTypes.AUDIO_AC3, null, -1, -1, channelCount, sampleRate, null, drmInitData, 0, language);
    }

    public static Format parseEAc3AnnexFFormat(ParsableByteArray data, String trackId, String language, DrmInitData drmInitData) {
        int channelCount;
        data.skipBytes(2);
        int sampleRate = SAMPLE_RATE_BY_FSCOD[(data.readUnsignedByte() & 192) >> 6];
        int nextByte = data.readUnsignedByte();
        int channelCount2 = CHANNEL_COUNT_BY_ACMOD[(nextByte & 14) >> 1];
        if ((nextByte & 1) != 0) {
            channelCount2++;
        }
        if (((data.readUnsignedByte() & 30) >> 1) <= 0 || (data.readUnsignedByte() & 2) == 0) {
            channelCount = channelCount2;
        } else {
            channelCount = channelCount2 + 2;
        }
        return Format.createAudioSampleFormat(trackId, data.bytesLeft() > 0 ? (data.readUnsignedByte() & 1) != 0 ? MimeTypes.AUDIO_E_AC3_JOC : MimeTypes.AUDIO_E_AC3 : MimeTypes.AUDIO_E_AC3, null, -1, -1, channelCount, sampleRate, null, drmInitData, 0, language);
    }

    public static SyncFrameInfo parseAc3SyncframeInfo(ParsableBitArray data) {
        int channelCount;
        int sampleRate;
        int frameSize;
        int sampleCount;
        String mimeType;
        int i;
        int audioBlocks;
        int numblkscod;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        ParsableBitArray parsableBitArray = data;
        int initialPosition = data.getPosition();
        parsableBitArray.skipBits(40);
        boolean isEac3 = parsableBitArray.readBits(5) == 16;
        parsableBitArray.setPosition(initialPosition);
        int streamType = -1;
        if (isEac3) {
            parsableBitArray.skipBits(16);
            int readBits = parsableBitArray.readBits(2);
            if (readBits == 0) {
                streamType = 0;
            } else if (readBits == 1) {
                streamType = 1;
            } else if (readBits != 2) {
                streamType = -1;
            } else {
                streamType = 2;
            }
            parsableBitArray.skipBits(3);
            frameSize = (parsableBitArray.readBits(11) + 1) * 2;
            int fscod = parsableBitArray.readBits(2);
            if (fscod == 3) {
                numblkscod = 3;
                sampleRate = SAMPLE_RATE_BY_FSCOD2[parsableBitArray.readBits(2)];
                audioBlocks = 6;
            } else {
                numblkscod = parsableBitArray.readBits(2);
                audioBlocks = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[numblkscod];
                sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
            }
            sampleCount = audioBlocks * 256;
            int acmod = parsableBitArray.readBits(3);
            boolean lfeon = data.readBit();
            channelCount = CHANNEL_COUNT_BY_ACMOD[acmod] + (lfeon);
            parsableBitArray.skipBits(10);
            if (data.readBit()) {
                parsableBitArray.skipBits(8);
            }
            if (acmod == 0) {
                parsableBitArray.skipBits(5);
                if (data.readBit()) {
                    parsableBitArray.skipBits(8);
                }
            }
            if (streamType == 1 && data.readBit()) {
                parsableBitArray.skipBits(16);
            }
            if (data.readBit()) {
                if (acmod > 2) {
                    parsableBitArray.skipBits(2);
                }
                if ((acmod & 1) == 0 || acmod <= 2) {
                    i5 = 6;
                } else {
                    i5 = 6;
                    parsableBitArray.skipBits(6);
                }
                if ((acmod & 4) != 0) {
                    parsableBitArray.skipBits(i5);
                }
                if (lfeon && data.readBit()) {
                    parsableBitArray.skipBits(5);
                }
                if (streamType == 0) {
                    if (data.readBit()) {
                        i6 = 6;
                        parsableBitArray.skipBits(6);
                    } else {
                        i6 = 6;
                    }
                    if (acmod == 0 && data.readBit()) {
                        parsableBitArray.skipBits(i6);
                    }
                    if (data.readBit()) {
                        parsableBitArray.skipBits(i6);
                    }
                    int mixdef = parsableBitArray.readBits(2);
                    if (mixdef == 1) {
                        parsableBitArray.skipBits(5);
                    } else if (mixdef == 2) {
                        parsableBitArray.skipBits(12);
                    } else if (mixdef == 3) {
                        int mixdeflen = parsableBitArray.readBits(5);
                        if (data.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                parsableBitArray.skipBits(4);
                            }
                            if (data.readBit()) {
                                if (data.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                                if (data.readBit()) {
                                    parsableBitArray.skipBits(4);
                                }
                            }
                        }
                        if (data.readBit()) {
                            parsableBitArray.skipBits(5);
                            if (data.readBit()) {
                                parsableBitArray.skipBits(7);
                                if (data.readBit()) {
                                    parsableBitArray.skipBits(8);
                                }
                            }
                        }
                        parsableBitArray.skipBits((mixdeflen + 2) * 8);
                        data.byteAlign();
                    }
                    if (acmod < 2) {
                        if (data.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                        if (acmod == 0 && data.readBit()) {
                            parsableBitArray.skipBits(14);
                        }
                    }
                    if (data.readBit()) {
                        if (numblkscod == 0) {
                            parsableBitArray.skipBits(5);
                        } else {
                            for (int blk = 0; blk < audioBlocks; blk++) {
                                if (data.readBit()) {
                                    parsableBitArray.skipBits(5);
                                }
                            }
                        }
                    }
                }
            }
            if (data.readBit() != 0) {
                parsableBitArray.skipBits(5);
                if (acmod == 2) {
                    parsableBitArray.skipBits(4);
                }
                if (acmod >= 6) {
                    parsableBitArray.skipBits(2);
                }
                if (data.readBit()) {
                    i4 = 8;
                    parsableBitArray.skipBits(8);
                } else {
                    i4 = 8;
                }
                if (acmod == 0 && data.readBit()) {
                    parsableBitArray.skipBits(i4);
                }
                i2 = 3;
                if (fscod < 3) {
                    data.skipBit();
                }
            } else {
                i2 = 3;
            }
            if (streamType == 0 && numblkscod != i2) {
                data.skipBit();
            }
            if (streamType != 2) {
                i3 = 6;
            } else if (numblkscod == i2 || data.readBit()) {
                i3 = 6;
                parsableBitArray.skipBits(6);
            } else {
                i3 = 6;
            }
            mimeType = MimeTypes.AUDIO_E_AC3;
            if (data.readBit() && parsableBitArray.readBits(i3) == 1 && parsableBitArray.readBits(8) == 1) {
                mimeType = MimeTypes.AUDIO_E_AC3_JOC;
            }
        } else {
            parsableBitArray.skipBits(32);
            int fscod2 = parsableBitArray.readBits(2);
            mimeType = fscod2 == 3 ? null : MimeTypes.AUDIO_AC3;
            frameSize = getAc3SyncframeSize(fscod2, parsableBitArray.readBits(6));
            parsableBitArray.skipBits(8);
            int acmod2 = parsableBitArray.readBits(3);
            if ((acmod2 & 1) == 0 || acmod2 == 1) {
                i = 2;
            } else {
                i = 2;
                parsableBitArray.skipBits(2);
            }
            if ((acmod2 & 4) != 0) {
                parsableBitArray.skipBits(i);
            }
            if (acmod2 == i) {
                parsableBitArray.skipBits(i);
            }
            int[] iArr = SAMPLE_RATE_BY_FSCOD;
            sampleRate = fscod2 < iArr.length ? iArr[fscod2] : -1;
            sampleCount = AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
            channelCount = CHANNEL_COUNT_BY_ACMOD[acmod2] + (data.readBit());
        }
        return new SyncFrameInfo(mimeType, streamType, channelCount, sampleRate, frameSize, sampleCount);
    }

    public static int parseAc3SyncframeSize(byte[] data) {
        if (data.length < 6) {
            return -1;
        }
        if (!(((data[5] & UnsignedBytes.MAX_VALUE) >> 3) == 16)) {
            return getAc3SyncframeSize((data[4] & 192) >> 6, data[4] & 63);
        }
        return (((data[3] & 255) | ((data[2] & 7) << 8)) + 1) * 2;
    }

    public static int getAc3SyncframeAudioSampleCount() {
        return AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
    }

    public static int parseEAc3SyncframeAudioSampleCount(ByteBuffer buffer) {
        int i = 6;
        if (((buffer.get(buffer.position() + 4) & 192) >> 6) != 3) {
            i = BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[(buffer.get(buffer.position() + 4) & 48) >> 4];
        }
        return i * 256;
    }

    public static int findTrueHdSyncframeOffset(ByteBuffer buffer) {
        int startIndex = buffer.position();
        int endIndex = buffer.limit() - 10;
        for (int i = startIndex; i <= endIndex; i++) {
            if ((buffer.getInt(i + 4) & -16777217) == -1167101192) {
                return i - startIndex;
            }
        }
        return -1;
    }

    public static int parseTrueHdSyncframeAudioSampleCount(byte[] syncframe) {
        boolean isMlp = false;
        if (syncframe[4] != -8 || syncframe[5] != 114 || syncframe[6] != 111 || (syncframe[7] & 254) != 186) {
            return 0;
        }
        if ((syncframe[7] & UnsignedBytes.MAX_VALUE) == 187) {
            isMlp = true;
        }
        return 40 << ((syncframe[isMlp ? (char) 9 : 8] >> 4) & 7);
    }

    public static int parseTrueHdSyncframeAudioSampleCount(ByteBuffer buffer, int offset) {
        return 40 << ((buffer.get((buffer.position() + offset) + ((buffer.get((buffer.position() + offset) + 7) & UnsignedBytes.MAX_VALUE) == 187 ? 9 : 8)) >> 4) & 7);
    }

    private static int getAc3SyncframeSize(int fscod, int frmsizecod) {
        int halfFrmsizecod = frmsizecod / 2;
        if (fscod < 0) {
            return -1;
        }
        int[] iArr = SAMPLE_RATE_BY_FSCOD;
        if (fscod >= iArr.length || frmsizecod < 0) {
            return -1;
        }
        int[] iArr2 = SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1;
        if (halfFrmsizecod >= iArr2.length) {
            return -1;
        }
        int sampleRate = iArr[fscod];
        if (sampleRate == 44100) {
            return (iArr2[halfFrmsizecod] + (frmsizecod % 2)) * 2;
        }
        int bitrate = BITRATE_BY_HALF_FRMSIZECOD[halfFrmsizecod];
        if (sampleRate == 32000) {
            return bitrate * 6;
        }
        return bitrate * 4;
    }

    public static final class SyncFrameInfo {
        public static final int STREAM_TYPE_TYPE0 = 0;
        public static final int STREAM_TYPE_TYPE1 = 1;
        public static final int STREAM_TYPE_TYPE2 = 2;
        public static final int STREAM_TYPE_UNDEFINED = -1;
        public final int channelCount;
        public final int frameSize;
        @Nullable
        public final String mimeType;
        public final int sampleCount;
        public final int sampleRate;
        public final int streamType;

        private SyncFrameInfo(@Nullable String mimeType2, int streamType2, int channelCount2, int sampleRate2, int frameSize2, int sampleCount2) {
            this.mimeType = mimeType2;
            this.streamType = streamType2;
            this.channelCount = channelCount2;
            this.sampleRate = sampleRate2;
            this.frameSize = frameSize2;
            this.sampleCount = sampleCount2;
        }

        @Documented
        @Retention(RetentionPolicy.SOURCE)
        public @interface StreamType {
        }
    }
}
