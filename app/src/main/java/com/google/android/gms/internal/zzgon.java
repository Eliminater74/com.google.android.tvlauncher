package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* compiled from: Internal */
public final class zzgon {
    public static final byte[] zzb;
    static final Charset zza = Charset.forName("UTF-8");
    private static final Charset zzc = Charset.forName("ISO-8859-1");
    private static final ByteBuffer zzd;
    private static final zzgnk zze;

    static {
        byte[] bArr = new byte[0];
        zzb = bArr;
        zzd = ByteBuffer.wrap(bArr);
        byte[] bArr2 = zzb;
        zze = zzgnk.zza(bArr2, 0, bArr2.length, false);
    }

    static <T> T zza(Object obj) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException();
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    public static boolean zza(byte[] bArr) {
        return zzgrl.zza(bArr);
    }

    public static String zzb(byte[] bArr) {
        return new String(bArr, zza);
    }

    public static int zza(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static int zza(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zzc(byte[] bArr) {
        int length = bArr.length;
        int zza2 = zza(length, bArr, 0, length);
        if (zza2 == 0) {
            return 1;
        }
        return zza2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (int i5 = i2; i5 < i2 + i3; i5++) {
            i4 = (i4 * 31) + bArr[i5];
        }
        return i4;
    }

    static boolean zza(zzgpt zzgpt) {
        return false;
    }

    static Object zza(Object obj, Object obj2) {
        return ((zzgpt) obj).zzp().zza((zzgpt) obj2).zze();
    }
}
