package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.common.base.Ascii;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class DtsUtil {
    private static final int[] CHANNELS_BY_AMODE = {1, 2, 2, 2, 2, 3, 3, 4, 4, 5, 6, 6, 6, 7, 8, 8};
    private static final byte FIRST_BYTE_14B_BE = 31;
    private static final byte FIRST_BYTE_14B_LE = -1;
    private static final byte FIRST_BYTE_BE = Byte.MAX_VALUE;
    private static final byte FIRST_BYTE_LE = -2;
    private static final int[] SAMPLE_RATE_BY_SFREQ = {-1, 8000, 16000, 32000, -1, -1, 11025, 22050, 44100, -1, -1, 12000, 24000, 48000, -1, -1};
    private static final int SYNC_VALUE_14B_BE = 536864768;
    private static final int SYNC_VALUE_14B_LE = -14745368;
    private static final int SYNC_VALUE_BE = 2147385345;
    private static final int SYNC_VALUE_LE = -25230976;
    private static final int[] TWICE_BITRATE_KBPS_BY_RATE = {64, 112, 128, 192, 224, 256, ClientAnalytics.LogRequest.LogSource.TOOLKIT_QUICKSTART_VALUE, ClientAnalytics.LogRequest.LogSource.ANDROID_CREATIVE_PREVIEW_PRIMES_VALUE, 512, ClientAnalytics.LogRequest.LogSource.GMSCORE_BACKEND_COUNTERS_VALUE, ClientAnalytics.LogRequest.LogSource.JELLY_IOS_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.EASTWORLD_STATS_VALUE, 1024, 1152, 1280, 1536, 1920, 2048, 2304, 2560, 2688, 2816, 2823, 2944, 3072, 3840, 4096, 6144, 7680};

    private DtsUtil() {
    }

    public static boolean isSyncWord(int word) {
        return word == SYNC_VALUE_BE || word == SYNC_VALUE_LE || word == SYNC_VALUE_14B_BE || word == SYNC_VALUE_14B_LE;
    }

    public static Format parseDtsFormat(byte[] frame, String trackId, String language, DrmInitData drmInitData) {
        int bitrate;
        ParsableBitArray frameBits = getNormalizedFrameHeader(frame);
        frameBits.skipBits(60);
        int channelCount = CHANNELS_BY_AMODE[frameBits.readBits(6)];
        int sampleRate = SAMPLE_RATE_BY_SFREQ[frameBits.readBits(4)];
        int rate = frameBits.readBits(5);
        int[] iArr = TWICE_BITRATE_KBPS_BY_RATE;
        if (rate >= iArr.length) {
            bitrate = -1;
        } else {
            bitrate = (iArr[rate] * 1000) / 2;
        }
        frameBits.skipBits(10);
        return Format.createAudioSampleFormat(trackId, MimeTypes.AUDIO_DTS, null, bitrate, -1, channelCount + (frameBits.readBits(2) > 0 ? 1 : 0), sampleRate, null, drmInitData, 0, language);
    }

    public static int parseDtsAudioSampleCount(byte[] data) {
        int nblks;
        byte b = data[0];
        if (b == -2) {
            nblks = ((data[5] & 1) << 6) | ((data[4] & 252) >> 2);
        } else if (b == -1) {
            nblks = ((data[4] & 7) << 4) | ((data[7] & 60) >> 2);
        } else if (b != 31) {
            nblks = ((data[4] & 1) << 6) | ((data[5] & 252) >> 2);
        } else {
            nblks = ((data[5] & 7) << 4) | ((data[6] & 60) >> 2);
        }
        return (nblks + 1) * 32;
    }

    public static int parseDtsAudioSampleCount(ByteBuffer buffer) {
        int nblks;
        int position = buffer.position();
        byte b = buffer.get(position);
        if (b == -2) {
            nblks = ((buffer.get(position + 5) & 1) << 6) | ((buffer.get(position + 4) & 252) >> 2);
        } else if (b == -1) {
            nblks = ((buffer.get(position + 4) & 7) << 4) | ((buffer.get(position + 7) & 60) >> 2);
        } else if (b != 31) {
            nblks = ((buffer.get(position + 4) & 1) << 6) | ((buffer.get(position + 5) & 252) >> 2);
        } else {
            nblks = ((buffer.get(position + 5) & 7) << 4) | ((buffer.get(position + 6) & 60) >> 2);
        }
        return (nblks + 1) * 32;
    }

    public static int getDtsFrameSize(byte[] data) {
        int fsize;
        boolean uses14BitPerWord = false;
        byte b = data[0];
        if (b == -2) {
            fsize = (((data[4] & 3) << Ascii.f149FF) | ((data[7] & 255) << 4) | ((data[6] & 240) >> 4)) + 1;
        } else if (b == -1) {
            fsize = (((data[7] & 3) << Ascii.f149FF) | ((data[6] & 255) << 4) | ((data[9] & 60) >> 2)) + 1;
            uses14BitPerWord = true;
        } else if (b != 31) {
            fsize = (((data[5] & 3) << Ascii.f149FF) | ((data[6] & 255) << 4) | ((data[7] & 240) >> 4)) + 1;
        } else {
            fsize = (((data[6] & 3) << Ascii.f149FF) | ((data[7] & 255) << 4) | ((data[8] & 60) >> 2)) + 1;
            uses14BitPerWord = true;
        }
        return uses14BitPerWord ? (fsize * 16) / 14 : fsize;
    }

    private static ParsableBitArray getNormalizedFrameHeader(byte[] frameHeader) {
        if (frameHeader[0] == Byte.MAX_VALUE) {
            return new ParsableBitArray(frameHeader);
        }
        byte[] frameHeader2 = Arrays.copyOf(frameHeader, frameHeader.length);
        if (isLittleEndianFrameHeader(frameHeader2)) {
            for (int i = 0; i < frameHeader2.length - 1; i += 2) {
                byte temp = frameHeader2[i];
                frameHeader2[i] = frameHeader2[i + 1];
                frameHeader2[i + 1] = temp;
            }
        }
        ParsableBitArray frameBits = new ParsableBitArray(frameHeader2);
        if (frameHeader2[0] == 31) {
            ParsableBitArray scratchBits = new ParsableBitArray(frameHeader2);
            while (scratchBits.bitsLeft() >= 16) {
                scratchBits.skipBits(2);
                frameBits.putInt(scratchBits.readBits(14), 14);
            }
        }
        frameBits.reset(frameHeader2);
        return frameBits;
    }

    private static boolean isLittleEndianFrameHeader(byte[] frameHeader) {
        return frameHeader[0] == -2 || frameHeader[0] == -1;
    }
}
