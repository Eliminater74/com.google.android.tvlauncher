package com.google.android.libraries.stitch.util;

public final class LongHashCode {
    public static String hashCode(String string) {
        long code = 1125899906842597L;
        int i = string.length();
        while (true) {
            i--;
            if (i < 0) {
                return Long.toHexString(1152921504606846975L & code);
            }
            code = (31 * code) + ((long) string.charAt(i));
        }
    }

    private LongHashCode() {
    }
}
