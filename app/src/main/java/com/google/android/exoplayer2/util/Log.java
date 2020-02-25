package com.google.android.exoplayer2.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Log {
    public static final int LOG_LEVEL_ALL = 0;
    public static final int LOG_LEVEL_ERROR = 3;
    public static final int LOG_LEVEL_INFO = 1;
    public static final int LOG_LEVEL_OFF = Integer.MAX_VALUE;
    public static final int LOG_LEVEL_WARNING = 2;
    private static int logLevel = 0;
    private static boolean logStackTraces = true;

    private Log() {
    }

    public static int getLogLevel() {
        return logLevel;
    }

    public static void setLogLevel(int logLevel2) {
        logLevel = logLevel2;
    }

    /* renamed from: d */
    public static void m24d(String tag, String message) {
        if (logLevel == 0) {
            android.util.Log.d(tag, message);
        }
    }

    /* renamed from: d */
    public static void m25d(String tag, String message, @Nullable Throwable throwable) {
        if (!logStackTraces) {
            m24d(tag, appendThrowableMessage(message, throwable));
        }
        if (logLevel == 0) {
            android.util.Log.d(tag, message, throwable);
        }
    }

    /* renamed from: i */
    public static void m28i(String tag, String message) {
        if (logLevel <= 1) {
            android.util.Log.i(tag, message);
        }
    }

    /* renamed from: i */
    public static void m29i(String tag, String message, @Nullable Throwable throwable) {
        if (!logStackTraces) {
            m28i(tag, appendThrowableMessage(message, throwable));
        }
        if (logLevel <= 1) {
            android.util.Log.i(tag, message, throwable);
        }
    }

    /* renamed from: w */
    public static void m30w(String tag, String message) {
        if (logLevel <= 2) {
            android.util.Log.w(tag, message);
        }
    }

    /* renamed from: w */
    public static void m31w(String tag, String message, @Nullable Throwable throwable) {
        if (!logStackTraces) {
            m30w(tag, appendThrowableMessage(message, throwable));
        }
        if (logLevel <= 2) {
            android.util.Log.w(tag, message, throwable);
        }
    }

    /* renamed from: e */
    public static void m26e(String tag, String message) {
        if (logLevel <= 3) {
            android.util.Log.e(tag, message);
        }
    }

    /* renamed from: e */
    public static void m27e(String tag, String message, @Nullable Throwable throwable) {
        if (!logStackTraces) {
            m26e(tag, appendThrowableMessage(message, throwable));
        }
        if (logLevel <= 3) {
            android.util.Log.e(tag, message, throwable);
        }
    }

    private static String appendThrowableMessage(String message, @Nullable Throwable throwable) {
        if (throwable == null) {
            return message;
        }
        String throwableMessage = throwable.getMessage();
        if (TextUtils.isEmpty(throwableMessage)) {
            return message;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(message).length() + 3 + String.valueOf(throwableMessage).length());
        sb.append(message);
        sb.append(" - ");
        sb.append(throwableMessage);
        return sb.toString();
    }

    public boolean getLogStackTraces() {
        return logStackTraces;
    }

    public static void setLogStackTraces(boolean logStackTraces2) {
        logStackTraces = logStackTraces2;
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @interface LogLevel {
    }
}
