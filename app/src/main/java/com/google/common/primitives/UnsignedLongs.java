package com.google.common.primitives;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;

@GwtCompatible
@Beta
public final class UnsignedLongs {
    public static final long MAX_VALUE = -1;

    private UnsignedLongs() {
    }

    private static long flip(long a) {
        return Long.MIN_VALUE ^ a;
    }

    public static int compare(long a, long b) {
        return Longs.compare(flip(a), flip(b));
    }

    public static long min(long... array) {
        Preconditions.checkArgument(array.length > 0);
        long min = flip(array[0]);
        for (int i = 1; i < array.length; i++) {
            long next = flip(array[i]);
            if (next < min) {
                min = next;
            }
        }
        return flip(min);
    }

    public static long max(long... array) {
        Preconditions.checkArgument(array.length > 0);
        long max = flip(array[0]);
        for (int i = 1; i < array.length; i++) {
            long next = flip(array[i]);
            if (next > max) {
                max = next;
            }
        }
        return flip(max);
    }

    public static String join(String separator, long... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(array.length * 5);
        builder.append(toString(array[0]));
        for (int i = 1; i < array.length; i++) {
            builder.append(separator);
            builder.append(toString(array[i]));
        }
        return builder.toString();
    }

    public static Comparator<long[]> lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }

    public static void sort(long[] array) {
        Preconditions.checkNotNull(array);
        sort(array, 0, array.length);
    }

    public static void sort(long[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        for (int i = fromIndex; i < toIndex; i++) {
            array[i] = flip(array[i]);
        }
        Arrays.sort(array, fromIndex, toIndex);
        for (int i2 = fromIndex; i2 < toIndex; i2++) {
            array[i2] = flip(array[i2]);
        }
    }

    public static void sortDescending(long[] array) {
        Preconditions.checkNotNull(array);
        sortDescending(array, 0, array.length);
    }

    public static void sortDescending(long[] array, int fromIndex, int toIndex) {
        Preconditions.checkNotNull(array);
        Preconditions.checkPositionIndexes(fromIndex, toIndex, array.length);
        for (int i = fromIndex; i < toIndex; i++) {
            array[i] = Long.MAX_VALUE ^ array[i];
        }
        Arrays.sort(array, fromIndex, toIndex);
        for (int i2 = fromIndex; i2 < toIndex; i2++) {
            array[i2] = array[i2] ^ Long.MAX_VALUE;
        }
    }

    public static long divide(long dividend, long divisor) {
        if (divisor < 0) {
            if (compare(dividend, divisor) < 0) {
                return 0;
            }
            return 1;
        } else if (dividend >= 0) {
            return dividend / divisor;
        } else {
            int i = 1;
            long quotient = ((dividend >>> 1) / divisor) << 1;
            if (compare(dividend - (quotient * divisor), divisor) < 0) {
                i = 0;
            }
            return ((long) i) + quotient;
        }
    }

    public static long remainder(long dividend, long divisor) {
        long j = 0;
        if (divisor < 0) {
            if (compare(dividend, divisor) < 0) {
                return dividend;
            }
            return dividend - divisor;
        } else if (dividend >= 0) {
            return dividend % divisor;
        } else {
            long rem = dividend - ((((dividend >>> 1) / divisor) << 1) * divisor);
            if (compare(rem, divisor) >= 0) {
                j = divisor;
            }
            return rem - j;
        }
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String string) {
        return parseUnsignedLong(string, 10);
    }

    @CanIgnoreReturnValue
    public static long parseUnsignedLong(String string, int radix) {
        Preconditions.checkNotNull(string);
        if (string.length() == 0) {
            throw new NumberFormatException("empty string");
        } else if (radix < 2 || radix > 36) {
            StringBuilder sb = new StringBuilder(26);
            sb.append("illegal radix: ");
            sb.append(radix);
            throw new NumberFormatException(sb.toString());
        } else {
            int maxSafePos = ParseOverflowDetection.maxSafeDigits[radix] - 1;
            long value = 0;
            int pos = 0;
            while (pos < string.length()) {
                int digit = Character.digit(string.charAt(pos), radix);
                if (digit == -1) {
                    throw new NumberFormatException(string);
                } else if (pos <= maxSafePos || !ParseOverflowDetection.overflowInParse(value, digit, radix)) {
                    value = (((long) radix) * value) + ((long) digit);
                    pos++;
                } else {
                    String valueOf = String.valueOf(string);
                    throw new NumberFormatException(valueOf.length() != 0 ? "Too large for unsigned long: ".concat(valueOf) : new String("Too large for unsigned long: "));
                }
            }
            return value;
        }
    }

    @CanIgnoreReturnValue
    public static long decode(String stringValue) {
        ParseRequest request = ParseRequest.fromString(stringValue);
        try {
            return parseUnsignedLong(request.rawValue, request.radix);
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(stringValue);
            NumberFormatException decodeException = new NumberFormatException(valueOf.length() != 0 ? "Error parsing value: ".concat(valueOf) : new String("Error parsing value: "));
            decodeException.initCause(e);
            throw decodeException;
        }
    }

    public static String toString(long x) {
        return toString(x, 10);
    }

    public static String toString(long x, int radix) {
        long quotient;
        Preconditions.checkArgument(radix >= 2 && radix <= 36, "radix (%s) must be between Character.MIN_RADIX and Character.MAX_RADIX", radix);
        if (x == 0) {
            return "0";
        }
        if (x > 0) {
            return Long.toString(x, radix);
        }
        char[] buf = new char[64];
        int i = buf.length;
        if (((radix - 1) & radix) == 0) {
            int shift = Integer.numberOfTrailingZeros(radix);
            int mask = radix - 1;
            do {
                i--;
                buf[i] = Character.forDigit(((int) x) & mask, radix);
                x >>>= shift;
            } while (x != 0);
        } else {
            if ((radix & 1) == 0) {
                quotient = (x >>> 1) / ((long) (radix >>> 1));
            } else {
                quotient = divide(x, (long) radix);
            }
            i--;
            buf[i] = Character.forDigit((int) (x - (((long) radix) * quotient)), radix);
            long x2 = quotient;
            while (x2 > 0) {
                i--;
                buf[i] = Character.forDigit((int) (x2 % ((long) radix)), radix);
                x2 /= (long) radix;
            }
        }
        return new String(buf, i, buf.length - i);
    }

    enum LexicographicalComparator implements Comparator<long[]> {
        INSTANCE;

        public int compare(long[] left, long[] right) {
            int minLength = Math.min(left.length, right.length);
            for (int i = 0; i < minLength; i++) {
                if (left[i] != right[i]) {
                    return UnsignedLongs.compare(left[i], right[i]);
                }
            }
            return left.length - right.length;
        }

        public String toString() {
            return "UnsignedLongs.lexicographicalComparator()";
        }
    }

    private static final class ParseOverflowDetection {
        static final int[] maxSafeDigits = new int[37];
        static final long[] maxValueDivs = new long[37];
        static final int[] maxValueMods = new int[37];

        static {
            BigInteger overflow = new BigInteger("10000000000000000", 16);
            for (int i = 2; i <= 36; i++) {
                maxValueDivs[i] = UnsignedLongs.divide(-1, (long) i);
                maxValueMods[i] = (int) UnsignedLongs.remainder(-1, (long) i);
                maxSafeDigits[i] = overflow.toString(i).length() - 1;
            }
        }

        private ParseOverflowDetection() {
        }

        static boolean overflowInParse(long current, int digit, int radix) {
            if (current < 0) {
                return true;
            }
            long[] jArr = maxValueDivs;
            if (current < jArr[radix]) {
                return false;
            }
            if (current <= jArr[radix] && digit <= maxValueMods[radix]) {
                return false;
            }
            return true;
        }
    }
}
