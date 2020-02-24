package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlag;

/* compiled from: PhenotypeFlag */
final class zzan extends PhenotypeFlag<Integer> {
    zzan(PhenotypeFlag.Factory factory, String str, Integer num) {
        super(factory, str, num, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Integer fromString(String str) {
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (NumberFormatException e) {
            String str2 = this.zza;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 28 + String.valueOf(str).length());
            sb.append("Invalid integer value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Integer fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return Integer.valueOf((int) sharedPreferences.getLong(this.zza, 0));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid integer value in SharedPreferences for ".concat(valueOf) : new String("Invalid integer value in SharedPreferences for "), e);
            return null;
        }
    }
}
