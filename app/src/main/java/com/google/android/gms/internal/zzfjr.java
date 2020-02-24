package com.google.android.gms.internal;

/* compiled from: Ascii */
public final class zzfjr {
    public static String zza(String str) {
        int length = str.length();
        int i = 0;
        while (i < length) {
            if (zza(str.charAt(i))) {
                char[] charArray = str.toCharArray();
                while (i < length) {
                    char c = charArray[i];
                    if (zza(c)) {
                        charArray[i] = (char) (c ^ ' ');
                    }
                    i++;
                }
                return String.valueOf(charArray);
            }
            i++;
        }
        return str;
    }

    private static boolean zza(char c) {
        return c >= 'A' && c <= 'Z';
    }
}
