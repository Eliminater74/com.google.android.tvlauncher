package com.google.android.gms.phenotype;

import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.phenotype.PhenotypeFlag;
import java.io.IOException;

/* compiled from: PhenotypeFlag */
final class zzat extends PhenotypeFlag<T> {
    private final Object zzb = new Object();
    private String zzc;
    private T zzd;
    private final /* synthetic */ PhenotypeFlag.BytesConverter zze;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    zzat(PhenotypeFlag.Factory factory, String str, Object obj, PhenotypeFlag.BytesConverter bytesConverter) {
        super(factory, str, obj, null);
        this.zze = bytesConverter;
    }

    public final T fromString(String str) {
        T t;
        try {
            synchronized (this.zzb) {
                if (!str.equals(this.zzc)) {
                    T fromBytes = this.zze.fromBytes(Base64.decode(str, 3));
                    this.zzc = str;
                    this.zzd = fromBytes;
                }
                t = this.zzd;
            }
            return t;
        } catch (IOException | IllegalArgumentException e) {
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

    public final T fromSharedPreferences(SharedPreferences sharedPreferences) {
        try {
            return fromString(sharedPreferences.getString(this.zza, ""));
        } catch (ClassCastException e) {
            String valueOf = String.valueOf(this.zza);
            Log.e("PhenotypeFlag", valueOf.length() != 0 ? "Invalid byte[] value in SharedPreferences for ".concat(valueOf) : new String("Invalid byte[] value in SharedPreferences for "), e);
            return null;
        }
    }
}
