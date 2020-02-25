package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;

/* compiled from: PhenotypeFlag */
final class zzam extends PhenotypeFlag<Long> {
    zzam(PhenotypeFlag.Factory factory, String str, Long l) {
        super(factory, str, l, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Long fromString(String str) {
        try {
            return Long.valueOf(Long.parseLong(str));
        } catch (NumberFormatException e) {
            String str2 = this.zza;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 25 + String.valueOf(str).length());
            sb.append("Invalid long value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Long fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return Long.valueOf(sharedPreferences.getLong(this.zza, 0));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid long value in SharedPreferences for ".concat(valueOf) : new String("Invalid long value in SharedPreferences for "), e);
            return null;
        }
    }
}
