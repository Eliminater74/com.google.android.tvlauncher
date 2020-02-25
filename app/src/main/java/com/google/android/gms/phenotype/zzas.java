package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

/* compiled from: PhenotypeFlag */
final class zzas extends PhenotypeFlag<byte[]> {
    zzas(PhenotypeFlag.Factory factory, String str, byte[] bArr) {
        super(factory, str, bArr, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final byte[] fromString(String str) {
        try {
            return Base64.decode(str, 3);
        } catch (IllegalArgumentException e) {
            String str2 = this.zza;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 27 + String.valueOf(str).length());
            sb.append("Invalid byte[] value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final byte[] fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return fromString(sharedPreferences.getString(this.zza, ""));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid byte[] value in SharedPreferences for ".concat(valueOf) : new String("Invalid byte[] value in SharedPreferences for "), e);
            return null;
        }
    }
}
