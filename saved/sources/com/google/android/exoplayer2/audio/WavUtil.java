package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Util;

public final class WavUtil {
    public static final int DATA_FOURCC = Util.getIntegerCodeForString("data");
    public static final int FMT_FOURCC = Util.getIntegerCodeForString("fmt ");
    public static final int RIFF_FOURCC = Util.getIntegerCodeForString("RIFF");
    private static final int TYPE_A_LAW = 6;
    private static final int TYPE_FLOAT = 3;
    private static final int TYPE_MU_LAW = 7;
    private static final int TYPE_PCM = 1;
    private static final int TYPE_WAVE_FORMAT_EXTENSIBLE = 65534;
    public static final int WAVE_FOURCC = Util.getIntegerCodeForString("WAVE");

    public static int getTypeForEncoding(int encoding) {
        if (encoding == Integer.MIN_VALUE) {
            return 1;
        }
        if (encoding == 268435456) {
            return 7;
        }
        if (encoding == 536870912) {
            return 6;
        }
        if (encoding == 1073741824 || encoding == 2 || encoding == 3) {
            return 1;
        }
        if (encoding == 4) {
            return 3;
        }
        throw new IllegalArgumentException();
    }

    public static int getEncodingForType(int type, int bitsPerSample) {
        if (type != 1) {
            if (type != 3) {
                if (type != TYPE_WAVE_FORMAT_EXTENSIBLE) {
                    if (type == 6) {
                        return 536870912;
                    }
                    if (type != 7) {
                        return 0;
                    }
                    return C0841C.ENCODING_PCM_MU_LAW;
                }
            } else if (bitsPerSample == 32) {
                return 4;
            } else {
                return 0;
            }
        }
        return Util.getPcmEncoding(bitsPerSample);
    }

    private WavUtil() {
    }
}
