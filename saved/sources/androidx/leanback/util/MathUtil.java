package androidx.leanback.util;

import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public final class MathUtil {
    private MathUtil() {
    }

    public static int safeLongToInt(long numLong) {
        if (((long) ((int) numLong)) == numLong) {
            return (int) numLong;
        }
        throw new ArithmeticException("Input overflows int.\n");
    }
}
