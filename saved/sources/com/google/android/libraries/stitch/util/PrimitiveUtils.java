package com.google.android.libraries.stitch.util;

public final class PrimitiveUtils {
    private PrimitiveUtils() {
    }

    public static boolean safeBoolean(Boolean b) {
        return safeBoolean(b, false);
    }

    public static boolean safeBoolean(Boolean b, boolean defaultValue) {
        return b == null ? defaultValue : b.booleanValue();
    }

    public static byte safeByte(Byte y) {
        return safeByte(y, (byte) 0);
    }

    public static byte safeByte(Byte y, byte defaultValue) {
        return y == null ? defaultValue : y.byteValue();
    }

    public static short safeShort(Integer i) {
        if (i == null) {
            return 0;
        }
        return i.shortValue();
    }

    public static short safeShort(Short s) {
        return safeShort(s, 0);
    }

    public static short safeShort(Short s, short defaultValue) {
        return s == null ? defaultValue : s.shortValue();
    }

    public static int safeInt(Integer i) {
        return safeInt(i, 0);
    }

    public static int safeInt(Integer i, int defaultValue) {
        return i == null ? defaultValue : i.intValue();
    }

    public static long safeLong(Long l) {
        return safeLong(l, 0);
    }

    public static long safeLong(Long l, long defaultValue) {
        return l == null ? defaultValue : l.longValue();
    }

    public static long safeLong(String s) {
        return safeLong(s, 0);
    }

    public static long safeLong(String s, long defaultValue) {
        return s == null ? defaultValue : safeLong(Long.valueOf(s));
    }

    public static double safeDouble(Double d) {
        return safeDouble(d, 0.0d);
    }

    public static double safeDouble(Double d, double defaultValue) {
        return d == null ? defaultValue : d.doubleValue();
    }

    public static double safeDouble(String s) {
        return safeDouble(s, 0.0d);
    }

    public static double safeDouble(String s, double defaultValue) {
        return s == null ? defaultValue : safeDouble(Double.valueOf(s));
    }

    public static float safeFloat(Float f) {
        return safeFloat(f, 0.0f);
    }

    public static float safeFloat(Float f, float defaultValue) {
        return f == null ? defaultValue : f.floatValue();
    }

    public static boolean equals(Integer lhs, Integer rhs) {
        if (lhs != null) {
            if (rhs == null || safeInt(lhs) != safeInt(rhs)) {
                return false;
            }
            return true;
        } else if (rhs == null) {
            return true;
        } else {
            return false;
        }
    }
}
