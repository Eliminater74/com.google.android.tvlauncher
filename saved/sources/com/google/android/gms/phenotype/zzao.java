package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.android.gms.internal.zzfij;
import com.google.android.gms.phenotype.PhenotypeFlag;

/* compiled from: PhenotypeFlag */
final class zzao extends PhenotypeFlag<Boolean> {
    zzao(PhenotypeFlag.Factory factory, String str, Boolean bool) {
        super(factory, str, bool, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final Boolean fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return Boolean.valueOf(sharedPreferences.getBoolean(this.zza, false));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid boolean value in SharedPreferences for ".concat(valueOf) : new String("Invalid boolean value in SharedPreferences for "), e);
            return null;
        }
    }

    public final /* synthetic */ Object fromString(String str) {
        if (zzfij.zza.matcher(str).matches()) {
            return true;
        }
        if (zzfij.zzb.matcher(str).matches()) {
            return false;
        }
        String str2 = this.zza;
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 28 + String.valueOf(str).length());
        sb.append("Invalid boolean value for ");
        sb.append(str2);
        sb.append(": ");
        sb.append(str);
        Log.e("PhenotypeFlag", sb.toString());
        return null;
    }
}
