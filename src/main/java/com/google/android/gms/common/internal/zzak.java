package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;

@Hide
/* compiled from: Objects */
public final class zzak {
    public static boolean zza(@Nullable Object obj, @Nullable Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public static zzam zza(Object obj) {
        return new zzam(obj);
    }
}
