package com.google.android.libraries.stitch.util;

import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import java.util.Date;

public final class LogUtil {
    private static final int TAG_LENGTH_LIMIT = 23;

    public static void writeToLog(int priority, String tag, String logEntry) {
        if (Log.isLoggable(tag, priority)) {
            doWriteToLog(priority, tag, logEntry);
        }
    }

    public static void doWriteToLog(int priority, String tag, String logEntry) {
        int lastIndex = 0;
        int index = logEntry.indexOf(10, 0);
        while (index != -1) {
            Log.println(priority, tag, logEntry.substring(lastIndex, index));
            lastIndex = index + 1;
            index = logEntry.indexOf(10, lastIndex);
        }
        Log.println(priority, tag, logEntry.substring(lastIndex));
    }

    public static String truncateLogTagIfNecessary(String tag) {
        return (tag == null || tag.length() <= 23) ? tag : tag.substring(0, 23);
    }

    public static String getDeltaTime(long startTime) {
        return getFormattedDeltaTime(System.currentTimeMillis() - startTime);
    }

    public static String getThreadDeltaTime(long startThreadTime) {
        return getFormattedDeltaTime(SystemClock.currentThreadTimeMillis() - startThreadTime);
    }

    private static String getFormattedDeltaTime(long delta) {
        double d = (double) delta;
        Double.isNaN(d);
        return String.format("%.3f seconds", Double.valueOf(d / 1000.0d));
    }

    public static String formatDate(long timestamp) {
        return DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date(timestamp)).toString();
    }
}
