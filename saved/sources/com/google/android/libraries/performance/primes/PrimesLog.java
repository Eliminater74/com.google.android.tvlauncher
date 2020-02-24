package com.google.android.libraries.performance.primes;

import android.os.Process;
import android.util.Log;
import com.google.android.libraries.stitch.util.Preconditions;
import java.util.ArrayDeque;
import java.util.Locale;

public final class PrimesLog {
    private static final String MICROS_UNIT = "μs";
    private static final String MILLIS_UNIT = "ms";
    private static final String NANOS_UNIT = "ns";
    static final String PRIMES_TAG = "Primes";
    private static boolean logAll;

    public static void enableAllLogs() {
        logAll = true;
    }

    private PrimesLog() {
    }

    private static final class TicsHolder {
        private static final ThreadLocal<ArrayDeque<CpuWallTime>> tics = new ThreadLocal<ArrayDeque<CpuWallTime>>() {
            /* access modifiers changed from: protected */
            public ArrayDeque<CpuWallTime> initialValue() {
                return new ArrayDeque<>();
            }
        };

        private TicsHolder() {
        }

        /* access modifiers changed from: private */
        public static void tic() {
            tics.get().add(CpuWallTime.now());
        }

        /* access modifiers changed from: private */
        public static CpuWallTime toc() {
            return (CpuWallTime) Preconditions.checkNotNull((CpuWallTime) tics.get().pollLast());
        }

        /* access modifiers changed from: private */
        public static int ticSize() {
            return tics.get().size();
        }
    }

    public static void tic() {
        TicsHolder.tic();
    }

    private static String durationToString(long durationNanos) {
        if (durationNanos < 1000) {
            return String.format(Locale.US, "%d %s", Long.valueOf(durationNanos), NANOS_UNIT);
        } else if (durationNanos < 1000000) {
            return String.format(Locale.US, "%d %s", Long.valueOf(durationNanos / 1000), MICROS_UNIT);
        } else {
            Locale locale = Locale.US;
            double d = (double) durationNanos;
            Double.isNaN(d);
            return String.format(locale, "%.3f %s", Double.valueOf(d / 1000000.0d), MILLIS_UNIT);
        }
    }

    private static String durationToString(CpuWallTime duration) {
        Locale locale = Locale.US;
        double d = (double) duration.cpuNanos;
        Double.isNaN(d);
        double d2 = (double) duration.wallNanos;
        Double.isNaN(d2);
        return String.format(locale, "%s / %s / %.2f %%", durationToString(duration.wallNanos), durationToString(duration.cpuNanos), Double.valueOf((d * 100.0d) / d2));
    }

    private static String threadInfoString() {
        return String.format(Locale.US, "%s (%d)", Thread.currentThread().getName(), Integer.valueOf(Process.getThreadPriority(Process.myTid())));
    }

    private static String indent(int len) {
        if (len == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(len * 2);
        for (int i = 0; i < (len * 2) - 1; i++) {
            builder.append('-');
        }
        builder.append('>');
        return builder.toString();
    }

    public static <T> void toc(String tag, String msg, T... args) {
        toc(3, tag, msg, args);
    }

    public static <T> void toc(int priority, String tag, String msg, T... args) {
        try {
            log(priority, tag, "%s[%s: %s] %s", indent(TicsHolder.ticSize() * 2), threadInfoString(), durationToString(CpuWallTime.since(TicsHolder.toc())), message(msg, args));
        } catch (RuntimeException re) {
            m45d(PRIMES_TAG, "toc failed %s: tag:%s msg:%s]", re, threadInfoString(), tag, message(msg, args));
        }
    }

    public static <T> void toctic(String tag, String msg, T... args) {
        toc(tag, msg, args);
        tic();
    }

    private static <T> String message(String msg, T... args) {
        Preconditions.checkNotNull(args);
        return args.length == 0 ? msg : String.format(Locale.US, msg, args);
    }

    private static <T> void log(int priority, String tag, String msg, T... args) {
        if (logAll || Log.isLoggable(tag, priority)) {
            Log.println(priority, tag, message(msg, args));
        }
    }

    private static <T> void log(int priority, String tag, Throwable ex, String msg, T... args) {
        if (!Log.isLoggable(tag, priority)) {
            return;
        }
        if (priority == 2) {
            Log.v(tag, message(msg, args), ex);
        } else if (priority == 3) {
            Log.d(tag, message(msg, args), ex);
        } else if (priority == 4) {
            Log.i(tag, message(msg, args), ex);
        } else if (priority == 5) {
            Log.w(tag, message(msg, args), ex);
        } else if (priority != 6) {
            m54w("PrimesLog", "unexpected priority: %d for log %s: %s", Integer.valueOf(priority), tag, message(msg, args));
        } else {
            Log.e(tag, message(msg, args), ex);
        }
    }

    public static <T> void warning(String msg, T... args) {
        Log.w(PRIMES_TAG, message(msg, args));
    }

    /* renamed from: v */
    public static <T> void m52v(String tag, String msg, T... args) {
        log(2, tag, msg, args);
    }

    /* renamed from: v */
    public static <T> void m51v(String tag, String msg, Throwable ex, T... args) {
        log(2, tag, ex, msg, args);
    }

    public static boolean vLoggable(String tag) {
        return Log.isLoggable(tag, 2);
    }

    /* renamed from: d */
    public static <T> void m46d(String tag, String msg, T... args) {
        log(3, tag, msg, args);
    }

    /* renamed from: d */
    public static <T> void m45d(String tag, String msg, Throwable ex, T... args) {
        log(3, tag, ex, msg, args);
    }

    public static boolean dLoggable(String tag) {
        return Log.isLoggable(tag, 3);
    }

    /* renamed from: i */
    public static <T> void m50i(String tag, String msg, T... args) {
        log(4, tag, msg, args);
    }

    /* renamed from: i */
    public static <T> void m49i(String tag, String msg, Throwable ex, T... args) {
        log(4, tag, ex, msg, args);
    }

    public static boolean iLoggable(String tag) {
        return Log.isLoggable(tag, 4);
    }

    /* renamed from: w */
    public static <T> void m54w(String tag, String msg, T... args) {
        log(5, tag, msg, args);
    }

    /* renamed from: w */
    public static <T> void m53w(String tag, String msg, Throwable ex, T... args) {
        log(5, tag, ex, msg, args);
    }

    public static boolean wLoggable(String tag) {
        return Log.isLoggable(tag, 5);
    }

    /* renamed from: e */
    public static <T> void m48e(String tag, String msg, T... args) {
        log(6, tag, msg, args);
    }

    /* renamed from: e */
    public static <T> void m47e(String tag, String msg, Throwable ex, T... args) {
        log(6, tag, ex, msg, args);
    }

    public static boolean eLoggable(String tag) {
        return Log.isLoggable(tag, 6);
    }
}
