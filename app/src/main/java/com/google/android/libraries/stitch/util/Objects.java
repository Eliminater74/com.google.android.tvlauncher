package com.google.android.libraries.stitch.util;

import android.text.TextUtils;
import java.util.Arrays;

public final class Objects {
    private static final int DEFAULT_ACCUMULATOR = 17;
    private static final int HASH_MULTIPLIER = 31;

    private Objects() {
    }

    public static boolean equals(Object a, Object b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    public static boolean deepEquals(Object a, Object b) {
        if (a == null || b == null) {
            return a == b;
        }
        if ((a instanceof Object[]) && (b instanceof Object[])) {
            return Arrays.deepEquals((Object[]) a, (Object[]) b);
        }
        if ((a instanceof boolean[]) && (b instanceof boolean[])) {
            return Arrays.equals((boolean[]) a, (boolean[]) b);
        }
        if ((a instanceof byte[]) && (b instanceof byte[])) {
            return Arrays.equals((byte[]) a, (byte[]) b);
        }
        if ((a instanceof char[]) && (b instanceof char[])) {
            return Arrays.equals((char[]) a, (char[]) b);
        }
        if ((a instanceof double[]) && (b instanceof double[])) {
            return Arrays.equals((double[]) a, (double[]) b);
        }
        if ((a instanceof float[]) && (b instanceof float[])) {
            return Arrays.equals((float[]) a, (float[]) b);
        }
        if ((a instanceof int[]) && (b instanceof int[])) {
            return Arrays.equals((int[]) a, (int[]) b);
        }
        if ((a instanceof long[]) && (b instanceof long[])) {
            return Arrays.equals((long[]) a, (long[]) b);
        }
        if (!(a instanceof short[]) || !(b instanceof short[])) {
            return a.equals(b);
        }
        return Arrays.equals((short[]) a, (short[]) b);
    }

    @Deprecated
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static int hashCode(double value) {
        return hashCode(value, 17);
    }

    public static int hashCode(double value, int accumulator) {
        return hashCode(Double.doubleToLongBits(value), accumulator);
    }

    public static int hashCode(float value) {
        return hashCode(value, 17);
    }

    public static int hashCode(float value, int accumulator) {
        return hashCode(Float.floatToIntBits(value), accumulator);
    }

    public static int hashCode(long value) {
        return hashCode(value, 17);
    }

    public static int hashCode(long value, int accumulator) {
        return hashCode((int) ((value >>> 32) ^ value), accumulator);
    }

    public static int hashCode(int value) {
        return hashCode(value, 17);
    }

    public static int hashCode(int value, int accumulator) {
        return (accumulator * 31) + value;
    }

    public static int hashCode(char value) {
        return hashCode(value, 17);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int
     arg types: [char, int]
     candidates:
      com.google.android.libraries.stitch.util.Objects.hashCode(byte, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(char, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(double, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(float, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(long, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(short, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(boolean, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object[], int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int */
    public static int hashCode(char value, int accumulator) {
        return hashCode((int) value, accumulator);
    }

    public static int hashCode(short value) {
        return hashCode(value, 17);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int
     arg types: [short, int]
     candidates:
      com.google.android.libraries.stitch.util.Objects.hashCode(byte, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(char, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(double, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(float, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(long, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(short, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(boolean, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object[], int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int */
    public static int hashCode(short value, int accumulator) {
        return hashCode((int) value, accumulator);
    }

    public static int hashCode(byte value) {
        return hashCode(value, 17);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int
     arg types: [byte, int]
     candidates:
      com.google.android.libraries.stitch.util.Objects.hashCode(byte, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(char, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(double, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(float, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(long, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(short, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(boolean, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object[], int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int */
    public static int hashCode(byte value, int accumulator) {
        return hashCode((int) value, accumulator);
    }

    public static int hashCode(boolean value) {
        return hashCode(value, 17);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int
     arg types: [boolean, int]
     candidates:
      com.google.android.libraries.stitch.util.Objects.hashCode(byte, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(char, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(double, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(float, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(long, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(short, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(boolean, int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(java.lang.Object[], int):int
      com.google.android.libraries.stitch.util.Objects.hashCode(int, int):int */
    public static int hashCode(boolean value, int accumulator) {
        return hashCode((int) (value), accumulator);
    }

    public static int hashCode(Object object) {
        return hashCode(object, 17);
    }

    public static int hashCode(Object object, int accumulator) {
        return hashCode(object == null ? 0 : object.hashCode(), accumulator);
    }

    public static int hashCode(Object[] object, int accumulator) {
        return hashCode(object == null ? 0 : Arrays.hashCode(object), accumulator);
    }

    public static String toString(String className, Object... fields) {
        return className + '(' + TextUtils.join(",", fields) + ')';
    }
}
