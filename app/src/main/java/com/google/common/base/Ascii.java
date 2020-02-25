package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public final class Ascii {
    public static final byte ACK = 6;
    public static final byte BEL = 7;

    /* renamed from: BS */
    public static final byte f146BS = 8;
    public static final byte CAN = 24;
    /* renamed from: CR */
    public static final byte f147CR = 13;
    public static final byte DC1 = 17;
    public static final byte DC2 = 18;
    public static final byte DC3 = 19;
    public static final byte DC4 = 20;
    public static final byte DEL = Byte.MAX_VALUE;
    public static final byte DLE = 16;
    /* renamed from: EM */
    public static final byte f148EM = 25;
    public static final byte ENQ = 5;
    public static final byte EOT = 4;
    public static final byte ESC = 27;
    public static final byte ETB = 23;
    public static final byte ETX = 3;
    /* renamed from: FF */
    public static final byte f149FF = 12;
    /* renamed from: FS */
    public static final byte f150FS = 28;
    /* renamed from: GS */
    public static final byte f151GS = 29;
    /* renamed from: HT */
    public static final byte f152HT = 9;
    /* renamed from: LF */
    public static final byte f153LF = 10;
    public static final char MAX = '';
    public static final char MIN = '\u0000';
    public static final byte NAK = 21;
    /* renamed from: NL */
    public static final byte f154NL = 10;
    public static final byte NUL = 0;
    /* renamed from: RS */
    public static final byte f155RS = 30;
    /* renamed from: SI */
    public static final byte f156SI = 15;
    /* renamed from: SO */
    public static final byte f157SO = 14;
    public static final byte SOH = 1;
    /* renamed from: SP */
    public static final byte f158SP = 32;
    public static final byte SPACE = 32;
    public static final byte STX = 2;
    public static final byte SUB = 26;
    public static final byte SYN = 22;
    /* renamed from: US */
    public static final byte f159US = 31;
    /* renamed from: VT */
    public static final byte f160VT = 11;
    public static final byte XOFF = 19;
    public static final byte XON = 17;
    private static final char CASE_MASK = ' ';

    private Ascii() {
    }

    public static String toLowerCase(String string) {
        int length = string.length();
        int i = 0;
        while (i < length) {
            if (isUpperCase(string.charAt(i))) {
                char[] chars = string.toCharArray();
                while (i < length) {
                    char c = chars[i];
                    if (isUpperCase(c)) {
                        chars[i] = (char) (c ^ CASE_MASK);
                    }
                    i++;
                }
                return String.valueOf(chars);
            }
            i++;
        }
        return string;
    }

    public static String toLowerCase(CharSequence chars) {
        if (chars instanceof String) {
            return toLowerCase((String) chars);
        }
        char[] newChars = new char[chars.length()];
        for (int i = 0; i < newChars.length; i++) {
            newChars[i] = toLowerCase(chars.charAt(i));
        }
        return String.valueOf(newChars);
    }

    public static char toLowerCase(char c) {
        return isUpperCase(c) ? (char) (c ^ CASE_MASK) : c;
    }

    public static String toUpperCase(String string) {
        int length = string.length();
        int i = 0;
        while (i < length) {
            if (isLowerCase(string.charAt(i))) {
                char[] chars = string.toCharArray();
                while (i < length) {
                    char c = chars[i];
                    if (isLowerCase(c)) {
                        chars[i] = (char) (c ^ CASE_MASK);
                    }
                    i++;
                }
                return String.valueOf(chars);
            }
            i++;
        }
        return string;
    }

    public static String toUpperCase(CharSequence chars) {
        if (chars instanceof String) {
            return toUpperCase((String) chars);
        }
        char[] newChars = new char[chars.length()];
        for (int i = 0; i < newChars.length; i++) {
            newChars[i] = toUpperCase(chars.charAt(i));
        }
        return String.valueOf(newChars);
    }

    public static char toUpperCase(char c) {
        return isLowerCase(c) ? (char) (c ^ CASE_MASK) : c;
    }

    public static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder}
     arg types: [java.lang.CharSequence, int, int]
     candidates:
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(char[], int, int):java.lang.StringBuilder}
      ClspMth{java.lang.Appendable.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder} */
    public static String truncate(CharSequence seq, int maxLength, String truncationIndicator) {
        Preconditions.checkNotNull(seq);
        int truncationLength = maxLength - truncationIndicator.length();
        Preconditions.checkArgument(truncationLength >= 0, "maxLength (%s) must be >= length of the truncation indicator (%s)", maxLength, truncationIndicator.length());
        if (seq.length() <= maxLength) {
            String string = seq.toString();
            if (string.length() <= maxLength) {
                return string;
            }
            seq = string;
        }
        StringBuilder sb = new StringBuilder(maxLength);
        sb.append(seq, 0, truncationLength);
        sb.append(truncationIndicator);
        return sb.toString();
    }

    public static boolean equalsIgnoreCase(CharSequence s1, CharSequence s2) {
        int alphaIndex;
        int length = s1.length();
        if (s1 == s2) {
            return true;
        }
        if (length != s2.length()) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 != c2 && ((alphaIndex = getAlphaIndex(c1)) >= 26 || alphaIndex != getAlphaIndex(c2))) {
                return false;
            }
        }
        return true;
    }

    private static int getAlphaIndex(char c) {
        return (char) ((c | CASE_MASK) - 'a');
    }
}
