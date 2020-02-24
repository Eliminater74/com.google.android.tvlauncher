package com.google.android.exoplayer2;

import java.util.HashSet;

public final class ExoPlayerLibraryInfo {
    public static final boolean ASSERTIONS_ENABLED = true;
    public static final boolean GL_ASSERTIONS_ENABLED = false;
    public static final String TAG = "ExoPlayer";
    public static final boolean TRACE_ENABLED = true;
    public static final String VERSION = "2.10.0";
    public static final int VERSION_INT = 2010000;
    public static final String VERSION_SLASHY = "ExoPlayerLib/2.10.0";
    private static final HashSet<String> registeredModules = new HashSet<>();
    private static String registeredModulesString = "goog.exo.core";

    private ExoPlayerLibraryInfo() {
    }

    public static synchronized String registeredModules() {
        String str;
        synchronized (ExoPlayerLibraryInfo.class) {
            str = registeredModulesString;
        }
        return str;
    }

    public static synchronized void registerModule(String name) {
        synchronized (ExoPlayerLibraryInfo.class) {
            if (registeredModules.add(name)) {
                String str = registeredModulesString;
                StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 2 + String.valueOf(name).length());
                sb.append(str);
                sb.append(", ");
                sb.append(name);
                registeredModulesString = sb.toString();
            }
        }
    }
}
