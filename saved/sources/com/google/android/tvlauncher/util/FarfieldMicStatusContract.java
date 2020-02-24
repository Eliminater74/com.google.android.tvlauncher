package com.google.android.tvlauncher.util;

import android.net.Uri;

public class FarfieldMicStatusContract {
    private static final String AUTHORITY = "tvlauncher.mic";
    public static final String COLUMN_MIC_STATUS = "mic_status";
    public static final Uri FARFIELD_MIC_STATUS_CONTENT_URI = new Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(PATH_FARFIELD_MIC_STATUS).build();
    public static final int MIC_OFF = 2;
    public static final int MIC_ON = 3;
    public static final int NOT_SUPPORTED = 1;
    private static final String PATH_FARFIELD_MIC_STATUS = "farfield_mic_status";
}
