package androidx.tvprovider.media.p005tv;

import android.support.annotation.RestrictTo;

import java.util.Arrays;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: androidx.tvprovider.media.tv.CollectionUtils */
public class CollectionUtils {
    private CollectionUtils() {
    }

    public static <T> T[] concatAll(T[] first, T[]... rest) {
        int totalLength = first.length;
        for (T[] array : rest) {
            totalLength += array.length;
        }
        T[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        int offset2 = offset;
        for (T[] array2 : rest) {
            System.arraycopy(array2, 0, result, offset2, array2.length);
            offset2 += array2.length;
        }
        return result;
    }
}
