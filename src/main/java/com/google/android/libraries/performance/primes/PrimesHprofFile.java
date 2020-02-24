package com.google.android.libraries.performance.primes;

import android.content.Context;
import com.google.android.libraries.performance.primes.metriccapture.ProcessStats;
import com.google.android.libraries.stitch.util.Preconditions;
import java.io.File;

final class PrimesHprofFile {
    private static final String HPROF_FILE_NAME = "primeshprof";
    private static final String MINI_HEAP_DUMP_HPROF_FILE_NAME = "primes_mhd.hprof";

    private PrimesHprofFile() {
    }

    static File getHprofFile(Context context) {
        Preconditions.checkNotNull(context);
        File cacheDir = context.getCacheDir();
        String preparePrefix = preparePrefix(context);
        StringBuilder sb = new StringBuilder(String.valueOf(preparePrefix).length() + 12);
        sb.append(preparePrefix);
        sb.append("_");
        sb.append(HPROF_FILE_NAME);
        return new File(cacheDir, sb.toString());
    }

    static void deleteHeapDumpIfExists(Context context) {
        File hprofFile = getHprofFile(context);
        if (hprofFile.exists()) {
            hprofFile.delete();
        }
    }

    static File getMiniHeapDumpHprofFile(Context context) {
        Preconditions.checkNotNull(context);
        File cacheDir = context.getCacheDir();
        String preparePrefix = preparePrefix(context);
        StringBuilder sb = new StringBuilder(String.valueOf(preparePrefix).length() + 17);
        sb.append(preparePrefix);
        sb.append("_");
        sb.append(MINI_HEAP_DUMP_HPROF_FILE_NAME);
        return new File(cacheDir, sb.toString());
    }

    static void deleteMiniHeapDumpHprofIfExists(Context context) {
        File hprofFile = getMiniHeapDumpHprofFile(context);
        if (hprofFile.exists()) {
            hprofFile.delete();
        }
    }

    private static String preparePrefix(Context context) {
        String prefix = ProcessStats.getShortProcessName(context);
        if (prefix == null) {
            return "";
        }
        String prefix2 = prefix.replaceAll("[^a-zA-Z0-9\\._]", "_");
        return prefix2.substring(0, Math.min(32, prefix2.length()));
    }
}
