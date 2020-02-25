package com.google.android.gms.common.util;

import com.google.android.gms.common.internal.Hide;

import java.util.ArrayList;
import java.util.Collection;

@Hide
/* compiled from: ArrayUtils */
public final class zzb {
    public static <T> void zza(StringBuilder sb, T[] tArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(tArr[i].toString());
        }
    }

    public static <T> ArrayList<T> zza(T[] tArr) {
        ArrayList<T> arrayList = new ArrayList<>(r0);
        for (T add : tArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static int[] zza(Collection<Integer> collection) {
        int i = 0;
        if (collection == null || collection.size() == 0) {
            return new int[0];
        }
        int[] iArr = new int[collection.size()];
        for (Integer intValue : collection) {
            iArr[i] = intValue.intValue();
            i++;
        }
        return iArr;
    }
}
