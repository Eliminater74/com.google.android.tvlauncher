package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlag;

/* compiled from: PhenotypeFlag */
final class zzap extends PhenotypeFlag<Double> {
    zzap(PhenotypeFlag.Factory factory, String str, Double d) {
        super(factory, str, d, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Double fromString(String str) {
        try {
            return Double.valueOf(Double.parseDouble(str));
        } catch (NumberFormatException e) {
            String str2 = this.zza;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 27 + String.valueOf(str).length());
            sb.append("Invalid double value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Double fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return Double.valueOf((double) sharedPreferences.getFloat(this.zza, 0.0f));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid double value in SharedPreferences for ".concat(valueOf) : new String("Invalid double value in SharedPreferences for "), e);
            return null;
        }
    }
}
