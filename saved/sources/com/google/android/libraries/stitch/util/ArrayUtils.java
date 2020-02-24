package com.google.android.libraries.stitch.util;

import java.util.Arrays;

public final class ArrayUtils {
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(byte[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(char[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(short[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(float[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(double[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(boolean[] array) {
        return array == null || array.length == 0;
    }

    @Deprecated
    public static String[] prependStrings(String[] oldArgList, String... args) {
        return (String[]) concat(args, oldArgList);
    }

    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        int firstLen = first.length;
        if (firstLen == 0) {
            return second;
        }
        int secondLen = second.length;
        if (secondLen == 0) {
            return first;
        }
        T[] result = Arrays.copyOf(first, firstLen + secondLen);
        System.arraycopy(second, 0, result, firstLen, secondLen);
        return result;
    }
}
