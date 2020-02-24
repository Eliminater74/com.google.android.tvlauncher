package com.google.android.libraries.clock;

import android.util.Log;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class ClockUtils {
    private static final String TAG = "ClockUtils";

    public static String fixTimeZone(String timeZone) {
        if (timeZone == null) {
            return timeZone;
        }
        if ((!timeZone.startsWith("GMT+") && !timeZone.startsWith("GMT-")) || timeZone.indexOf(46) <= 0) {
            return timeZone;
        }
        try {
            char sign = timeZone.charAt(3);
            float offset = Math.abs(Float.parseFloat(timeZone.substring(4))) % 24.0f;
            return String.format(Locale.US, "GMT%c%d:%d", Character.valueOf(sign), Integer.valueOf((int) offset), Integer.valueOf((int) ((offset * 60.0f) % 60.0f)));
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(timeZone);
            Log.w(TAG, valueOf.length() != 0 ? "Invalid time zone: ".concat(valueOf) : new String("Invalid time zone: "));
            return timeZone;
        }
    }

    public static long elapsedToCurrentTime(Clock clock, long elapsedRealtime) {
        return (elapsedRealtime - clock.elapsedRealtime()) + clock.currentTimeMillis();
    }

    public static long elapsedNanosToMillis(long timeInNanos) {
        return TimeUnit.NANOSECONDS.toMillis(timeInNanos);
    }

    public static long nanoTimeToElapsedNanos(Clock clock, long nanoTime) {
        return (nanoTime - clock.nanoTime()) + clock.elapsedRealtimeNanos();
    }

    public static long uptimeMillisToElapsedNanos(Clock clock, long uptimeMillis) {
        return TimeUnit.NANOSECONDS.convert(uptimeMillis - clock.uptimeMillis(), TimeUnit.MILLISECONDS) + clock.elapsedRealtimeNanos();
    }

    private ClockUtils() {
    }
}
