package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;

/* compiled from: PhenotypeFlag */
final class zzaq extends PhenotypeFlag<Float> {
    zzaq(PhenotypeFlag.Factory factory, String str, Float f) {
        super(factory, str, f, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Float fromString(String str) {
        try {
            return Float.valueOf(Float.parseFloat(str));
        } catch (NumberFormatException e) {
            String str2 = this.zza;
            StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 26 + String.valueOf(str).length());
            sb.append("Invalid float value for ");
            sb.append(str2);
            sb.append(": ");
            sb.append(str);
            Log.e("PhenotypeFlag", sb.toString());
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Float fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return Float.valueOf(sharedPreferences.getFloat(this.zza, 0.0f));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid float value in SharedPreferences for ".concat(valueOf) : new String("Invalid float value in SharedPreferences for "), e);
            return null;
        }
    }
}
