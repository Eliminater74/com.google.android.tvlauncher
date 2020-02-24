package com.google.android.libraries.stitch.flags;

import com.google.android.libraries.stitch.util.SystemProperties;

public final class Flags {
    private static final String DEBUG_FLAGS_ENABLED = "debug.social";

    private Flags() {
    }

    public static boolean get(DefaultTrueFlag f) {
        return "true".equals(SystemProperties.getString(f.getName(), f.getDefaultValue() ? "true" : "false"));
    }

    public static boolean get(DefaultFalseFlag f) {
        return "true".equals(SystemProperties.getString(f.getName(), f.getDefaultValue() ? "true" : "false"));
    }

    public static boolean get(DebugFlag f) {
        return "true".equals(SystemProperties.getString(DEBUG_FLAGS_ENABLED, "true")) && "true".equals(SystemProperties.getString(f.getName(), f.getDefaultDebugValue() ? "true" : "false"));
    }

    public static boolean get(TestFlag f) {
        return false;
    }
}
