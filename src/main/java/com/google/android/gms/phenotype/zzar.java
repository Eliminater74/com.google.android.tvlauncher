package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlag;

/* compiled from: PhenotypeFlag */
final class zzar extends PhenotypeFlag<String> {
    zzar(PhenotypeFlag.Factory factory, String str, String str2) {
        super(factory, str, str2, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final String fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return sharedPreferences.getString(this.zza, null);
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid string value in SharedPreferences for ".concat(valueOf) : new String("Invalid string value in SharedPreferences for "), e);
            return null;
        }
    }

    public final /* synthetic */ Object fromString(String str) {
        return str;
    }
}
