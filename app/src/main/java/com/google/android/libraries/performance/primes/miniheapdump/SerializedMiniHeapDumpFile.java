package com.google.android.libraries.performance.primes.miniheapdump;

import android.content.Context;

import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.metriccapture.ProcessStats;
import com.google.android.libraries.stitch.util.Preconditions;

import java.io.File;

public final class SerializedMiniHeapDumpFile {
    private static final String SERIALIZED_MINI_HEAP_DUMP_FILE_NAME = "primes_mhd";
    private static final String TAG = "SerializedMhdFile";

    private SerializedMiniHeapDumpFile() {
    }

    public static File getSerializedObjectGraphFile(Context context) {
        Preconditions.checkNotNull(context);
        File cacheDir = context.getCacheDir();
        String preparePrefix = preparePrefix(context);
        StringBuilder sb = new StringBuilder(String.valueOf(preparePrefix).length() + 11);
        sb.append(preparePrefix);
        sb.append("_");
        sb.append(SERIALIZED_MINI_HEAP_DUMP_FILE_NAME);
        return new File(cacheDir, sb.toString());
    }

    private static String preparePrefix(Context context) {
        String prefix = ProcessStats.getShortProcessName(context);
        if (prefix == null) {
            return "";
        }
        String prefix2 = prefix.replaceAll("[^a-zA-Z0-9\\._]", "_");
        return prefix2.substring(0, Math.min(32, prefix2.length()));
    }

    public static void deleteSerializedObjectGraphFileIfExists(Context context) {
        try {
            File serializedObjectGraphFile = getSerializedObjectGraphFile(context);
            if (serializedObjectGraphFile.exists()) {
                serializedObjectGraphFile.delete();
            }
        } catch (SecurityException e) {
            PrimesLog.m47e(TAG, "Error deleting file", e, new Object[0]);
        }
    }
}
