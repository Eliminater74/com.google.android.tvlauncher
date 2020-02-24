package com.google.android.libraries.stitch.util;

import android.support.p001v4.media.session.PlaybackStateCompat;
import android.text.Html;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public final class StringUtils {
    public static final String NO_WHITESPACE_CHAR_CLASS = "[^\\u0009\\u000A\\u000B\\u000C\\u000D\\u0020\\u0085\\u00A0\\u1680\\u180E\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200A\\u2028\\u2029\\u202F\\u205F\\u3000]";
    private static final char[] NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final String WHITESPACE_CHARS = "\\u0009\\u000A\\u000B\\u000C\\u000D\\u0020\\u0085\\u00A0\\u1680\\u180E\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200A\\u2028\\u2029\\u202F\\u205F\\u3000";
    public static final String WHITESPACE_CHAR_CLASS = "[\\u0009\\u000A\\u000B\\u000C\\u000D\\u0020\\u0085\\u00A0\\u1680\\u180E\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200A\\u2028\\u2029\\u202F\\u205F\\u3000]";
    private static final Random randGen = new Random();
    private static final ThreadLocal<StringBuilderPool> sStringBuilderPool = new ThreadLocal<StringBuilderPool>() {
        /* access modifiers changed from: protected */
        public StringBuilderPool initialValue() {
            return new StringBuilderPool();
        }
    };

    private static final class StringBuilderPool {
        private static final int DEFAULT_CAPACITY = 256;
        private int mAcquiredCount;
        private StringBuilder mStringBuilder;

        private StringBuilderPool() {
            this.mStringBuilder = new StringBuilder(256);
            this.mAcquiredCount = 0;
        }

        public StringBuilder acquire() {
            this.mAcquiredCount++;
            if (this.mAcquiredCount == 1) {
                return this.mStringBuilder;
            }
            return new StringBuilder(256);
        }

        public void release(StringBuilder sb) {
            int i = this.mAcquiredCount;
            if (i < 1) {
                throw new IllegalStateException("Cannot release more StringBuilders than have been acquired");
            } else if (i != 1 || sb == this.mStringBuilder) {
                sb.setLength(0);
                this.mAcquiredCount--;
            } else {
                throw new IllegalArgumentException("Tried to release wrong StringBuilder instance");
            }
        }
    }

    private StringUtils() {
    }

    public static StringBuilder acquireStringBuilder() {
        return sStringBuilderPool.get().acquire();
    }

    public static void releaseStringBuilder(StringBuilder sb) {
        sStringBuilderPool.get().release(sb);
    }

    public static String releaseStringBuilderAndGetString(StringBuilder sb) {
        String retval = sb.toString();
        releaseStringBuilder(sb);
        return retval;
    }

    public static String randomString(int length) {
        if (length < 1) {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = NUMBERS_AND_LETTERS[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }

    public static String unescape(String value) {
        if (value == null) {
            return value;
        }
        return Html.fromHtml(value).toString();
    }

    public static String getDomain(String emailId) {
        String[] parts = emailId.split("@");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }

    public static int safeStringCompareTo(String left, String right) {
        if (left == null && right == null) {
            return 0;
        }
        if (right == null) {
            return -1;
        }
        if (left == null) {
            return 1;
        }
        return left.compareTo(right);
    }

    public static int safeStringCompareToIgnoreCase(String left, String right) {
        if (left == null && right == null) {
            return 0;
        }
        if (right == null) {
            return -1;
        }
        if (left == null) {
            return 1;
        }
        return left.compareToIgnoreCase(right);
    }

    public static boolean equals(CharSequence a, CharSequence b) {
        return TextUtils.equals(a, b) || (TextUtils.isEmpty(a) && TextUtils.isEmpty(b));
    }

    public static void writeStringToFile(String fileName, String string) throws IOException {
        FileOutputStream out;
        try {
            out = new FileOutputStream(fileName);
        } catch (FileNotFoundException e) {
            File parent = new File(fileName).getParentFile();
            if (parent == null || parent.exists()) {
                throw e;
            }
            parent.mkdirs();
            out = new FileOutputStream(fileName);
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
        try {
            writer.write(string);
        } finally {
            writer.close();
        }
    }

    public static String readFirstLineFromFile(String fileName) throws IOException {
        return readFirstLineFromStream(new FileInputStream(fileName));
    }

    public static String readFirstLineFromStream(InputStream inputStream) throws IOException {
        try {
            String result = new BufferedReader(new InputStreamReader(inputStream)).readLine();
            return result != null ? result : "";
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    public static String humanReadableByteCount(long bytes) {
        if (bytes < PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            StringBuilder sb = new StringBuilder(22);
            sb.append(bytes);
            sb.append(" B");
            return sb.toString();
        }
        int exp = (int) (Math.log((double) bytes) / Math.log(1024.0d));
        double d = (double) bytes;
        double pow = Math.pow(1024.0d, (double) exp);
        Double.isNaN(d);
        return String.format("%.1f %sB", Double.valueOf(d / pow), Character.valueOf("KMGTPE".charAt(exp - 1)));
    }

    public static ArrayList<String> split(String string, char separator) {
        ArrayList<String> list = new ArrayList<>();
        if (TextUtils.isEmpty(string)) {
            return list;
        }
        int offset = 0;
        while (offset < string.length()) {
            int separatorIndex = string.indexOf(separator, offset);
            if (separatorIndex == -1) {
                separatorIndex = string.length();
            }
            list.add(string.substring(offset, separatorIndex));
            offset = separatorIndex + 1;
        }
        return list;
    }

    public static String unicodePreservingSubstring(String str, int begin, int end) {
        return str.substring(unicodePreservingIndex(str, begin), unicodePreservingIndex(str, end));
    }

    private static int unicodePreservingIndex(String str, int index) {
        if (index <= 0 || index >= str.length() || !Character.isHighSurrogate(str.charAt(index - 1)) || !Character.isLowSurrogate(str.charAt(index))) {
            return index;
        }
        return index - 1;
    }
}
