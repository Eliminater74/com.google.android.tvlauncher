package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.logging.Level;
import java.util.logging.Logger;

@GwtCompatible
public final class Strings {
    private Strings() {
    }

    public static String nullToEmpty(@NullableDecl String string) {
        return Platform.nullToEmpty(string);
    }

    @NullableDecl
    public static String emptyToNull(@NullableDecl String string) {
        return Platform.emptyToNull(string);
    }

    public static boolean isNullOrEmpty(@NullableDecl String string) {
        return Platform.stringIsNullOrEmpty(string);
    }

    public static String padStart(String string, int minLength, char padChar) {
        Preconditions.checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }

    public static String padEnd(String string, int minLength, char padChar) {
        Preconditions.checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    public static String repeat(String string, int count) {
        Preconditions.checkNotNull(string);
        boolean z = true;
        if (count <= 1) {
            if (count < 0) {
                z = false;
            }
            Preconditions.checkArgument(z, "invalid count: %s", count);
            return count == 0 ? "" : string;
        }
        int len = string.length();
        long longSize = ((long) len) * ((long) count);
        int size = (int) longSize;
        if (((long) size) == longSize) {
            char[] array = new char[size];
            string.getChars(0, len, array, 0);
            int n = len;
            while (n < size - n) {
                System.arraycopy(array, 0, array, n, n);
                n <<= 1;
            }
            System.arraycopy(array, 0, array, n, size - n);
            return new String(array);
        }
        StringBuilder sb = new StringBuilder(51);
        sb.append("Required array size too large: ");
        sb.append(longSize);
        throw new ArrayIndexOutOfBoundsException(sb.toString());
    }

    public static String commonPrefix(CharSequence a, CharSequence b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        int maxPrefixLength = Math.min(a.length(), b.length());
        int p = 0;
        while (p < maxPrefixLength && a.charAt(p) == b.charAt(p)) {
            p++;
        }
        if (validSurrogatePairAt(a, p - 1) || validSurrogatePairAt(b, p - 1)) {
            p--;
        }
        return a.subSequence(0, p).toString();
    }

    public static String commonSuffix(CharSequence a, CharSequence b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);
        int maxSuffixLength = Math.min(a.length(), b.length());
        int s = 0;
        while (s < maxSuffixLength && a.charAt((a.length() - s) - 1) == b.charAt((b.length() - s) - 1)) {
            s++;
        }
        if (validSurrogatePairAt(a, (a.length() - s) - 1) || validSurrogatePairAt(b, (b.length() - s) - 1)) {
            s--;
        }
        return a.subSequence(a.length() - s, a.length()).toString();
    }

    @VisibleForTesting
    static boolean validSurrogatePairAt(CharSequence string, int index) {
        return index >= 0 && index <= string.length() + -2 && Character.isHighSurrogate(string.charAt(index)) && Character.isLowSurrogate(string.charAt(index + 1));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder}
     arg types: [java.lang.String, int, int]
     candidates:
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(char[], int, int):java.lang.StringBuilder}
      ClspMth{java.lang.Appendable.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder} */
    public static String lenientFormat(@NullableDecl String template, @NullableDecl Object... args) {
        int placeholderStart;
        String template2 = String.valueOf(template);
        if (args == null) {
            args = new Object[]{"(Object[])null"};
        } else {
            for (int i = 0; i < args.length; i++) {
                args[i] = lenientToString(args[i]);
            }
        }
        StringBuilder builder = new StringBuilder(template2.length() + (args.length * 16));
        int templateStart = 0;
        int i2 = 0;
        while (i2 < args.length && (placeholderStart = template2.indexOf("%s", templateStart)) != -1) {
            builder.append((CharSequence) template2, templateStart, placeholderStart);
            builder.append(args[i2]);
            templateStart = placeholderStart + 2;
            i2++;
        }
        builder.append((CharSequence) template2, templateStart, template2.length());
        if (i2 < args.length) {
            builder.append(" [");
            int i3 = i2 + 1;
            builder.append(args[i2]);
            while (true) {
                int i4 = i3;
                if (i4 >= args.length) {
                    break;
                }
                builder.append(", ");
                i3 = i4 + 1;
                builder.append(args[i4]);
            }
            builder.append(']');
        }
        return builder.toString();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
     arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Exception]
     candidates:
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
      ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
    private static String lenientToString(@NullableDecl Object o) {
        try {
            return String.valueOf(o);
        } catch (Exception e) {
            String name = o.getClass().getName();
            String hexString = Integer.toHexString(System.identityHashCode(o));
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 1 + String.valueOf(hexString).length());
            sb.append(name);
            sb.append('@');
            sb.append(hexString);
            String objectToString = sb.toString();
            Logger logger = Logger.getLogger("com.google.common.base.Strings");
            Level level = Level.WARNING;
            String valueOf = String.valueOf(objectToString);
            logger.logp(level, "com.google.common.base.Strings", "lenientToString", valueOf.length() != 0 ? "Exception during lenientFormat for ".concat(valueOf) : new String("Exception during lenientFormat for "), (Throwable) e);
            String name2 = e.getClass().getName();
            StringBuilder sb2 = new StringBuilder(String.valueOf(objectToString).length() + 9 + String.valueOf(name2).length());
            sb2.append("<");
            sb2.append(objectToString);
            sb2.append(" threw ");
            sb2.append(name2);
            sb2.append(">");
            return sb2.toString();
        }
    }
}
