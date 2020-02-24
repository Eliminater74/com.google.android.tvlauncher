package com.google.android.exoplayer2.mediacodec;

import android.media.MediaFormat;
import android.support.annotation.Nullable;
import com.google.android.exoplayer2.video.ColorInfo;
import java.nio.ByteBuffer;
import java.util.List;

public final class MediaFormatUtil {
    private MediaFormatUtil() {
    }

    public static void setString(MediaFormat format, String key, String value) {
        format.setString(key, value);
    }

    public static void setCsdBuffers(MediaFormat format, List<byte[]> csdBuffers) {
        for (int i = 0; i < csdBuffers.size(); i++) {
            StringBuilder sb = new StringBuilder(15);
            sb.append("csd-");
            sb.append(i);
            format.setByteBuffer(sb.toString(), ByteBuffer.wrap(csdBuffers.get(i)));
        }
    }

    public static void maybeSetInteger(MediaFormat format, String key, int value) {
        if (value != -1) {
            format.setInteger(key, value);
        }
    }

    public static void maybeSetFloat(MediaFormat format, String key, float value) {
        if (value != -1.0f) {
            format.setFloat(key, value);
        }
    }

    public static void maybeSetByteBuffer(MediaFormat format, String key, @Nullable byte[] value) {
        if (value != null) {
            format.setByteBuffer(key, ByteBuffer.wrap(value));
        }
    }

    public static void maybeSetColorInfo(MediaFormat format, @Nullable ColorInfo colorInfo) {
        if (colorInfo != null) {
            maybeSetInteger(format, "color-transfer", colorInfo.colorTransfer);
            maybeSetInteger(format, "color-standard", colorInfo.colorSpace);
            maybeSetInteger(format, "color-range", colorInfo.colorRange);
            maybeSetByteBuffer(format, "hdr-static-info", colorInfo.hdrStaticInfo);
        }
    }
}
