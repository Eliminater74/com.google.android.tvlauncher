package com.google.android.gms.common.util;

import android.util.Base64;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: Base64Utils */
public final class zzc {
    public static String zza(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 0);
    }

    public static String zzb(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 10);
    }

    public static String zzc(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 11);
    }
}
