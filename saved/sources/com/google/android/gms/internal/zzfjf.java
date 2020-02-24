package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.util.Log;

/* compiled from: PhenotypeFlag */
final class zzfjf extends zzfja<String> {
    zzfjf(zzfjh zzfjh, String str, String str2) {
        super(zzfjh, str, str2, null);
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final String zza(SharedPreferences sharedPreferences) {
        try {
            return sharedPreferences.getString(this.zza, null);
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid string value in SharedPreferences for ".concat(valueOf) : new String("Invalid string value in SharedPreferences for "), e);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ Object zza(String str) {
        return str;
    }
}
