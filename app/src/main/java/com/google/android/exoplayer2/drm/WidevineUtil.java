package com.google.android.exoplayer2.drm;

import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.android.exoplayer2.C0841C;

import java.util.Map;

public final class WidevineUtil {
    public static final String PROPERTY_LICENSE_DURATION_REMAINING = "LicenseDurationRemaining";
    public static final String PROPERTY_PLAYBACK_DURATION_REMAINING = "PlaybackDurationRemaining";

    private WidevineUtil() {
    }

    @Nullable
    public static Pair<Long, Long> getLicenseDurationRemainingSec(DrmSession<?> drmSession) {
        Map<String, String> keyStatus = drmSession.queryKeyStatus();
        if (keyStatus == null) {
            return null;
        }
        return new Pair<>(Long.valueOf(getDurationRemainingSec(keyStatus, PROPERTY_LICENSE_DURATION_REMAINING)), Long.valueOf(getDurationRemainingSec(keyStatus, PROPERTY_PLAYBACK_DURATION_REMAINING)));
    }

    private static long getDurationRemainingSec(Map<String, String> keyStatus, String property) {
        if (keyStatus == null) {
            return C0841C.TIME_UNSET;
        }
        try {
            String value = keyStatus.get(property);
            if (value != null) {
                return Long.parseLong(value);
            }
            return C0841C.TIME_UNSET;
        } catch (NumberFormatException e) {
            return C0841C.TIME_UNSET;
        }
    }
}
