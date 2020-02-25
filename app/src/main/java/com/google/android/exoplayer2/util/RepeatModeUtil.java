package com.google.android.exoplayer2.util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RepeatModeUtil {
    public static final int REPEAT_TOGGLE_MODE_ALL = 2;
    public static final int REPEAT_TOGGLE_MODE_NONE = 0;
    public static final int REPEAT_TOGGLE_MODE_ONE = 1;

    private RepeatModeUtil() {
    }

    public static int getNextRepeatMode(int currentMode, int enabledModes) {
        for (int offset = 1; offset <= 2; offset++) {
            int proposedMode = (currentMode + offset) % 3;
            if (isRepeatModeEnabled(proposedMode, enabledModes)) {
                return proposedMode;
            }
        }
        return currentMode;
    }

    public static boolean isRepeatModeEnabled(int repeatMode, int enabledModes) {
        if (repeatMode == 0) {
            return true;
        }
        if (repeatMode != 1) {
            if (repeatMode == 2 && (enabledModes & 2) != 0) {
                return true;
            }
            return false;
        } else if ((enabledModes & 1) != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatToggleModes {
    }
}
