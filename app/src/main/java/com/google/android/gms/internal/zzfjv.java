package com.google.android.gms.internal;

import java.util.Arrays;

/* compiled from: MoreObjects */
public final class zzfjv {
    private final String zza;
    private final zzfjw zzb;
    private zzfjw zzc;
    private boolean zzd;

    private zzfjv(String str) {
        this.zzb = new zzfjw();
        this.zzc = this.zzb;
        this.zzd = false;
        this.zza = (String) zzfkb.zza(str);
    }

    public final zzfjv zza(Object obj) {
        zzfjw zzfjw = new zzfjw();
        this.zzc.zzb = zzfjw;
        this.zzc = zzfjw;
        zzfjw.zza = obj;
        return this;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder}
     arg types: [java.lang.String, int, int]
     candidates:
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(char[], int, int):java.lang.StringBuilder}
      ClspMth{java.lang.Appendable.append(java.lang.CharSequence, int, int):java.lang.Appendable throws java.io.IOException}
      ClspMth{java.lang.StringBuilder.append(java.lang.CharSequence, int, int):java.lang.StringBuilder} */
    public final String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.zza);
        sb.append('{');
        zzfjw zzfjw = this.zzb.zzb;
        String str = "";
        while (zzfjw != null) {
            Object obj = zzfjw.zza;
            sb.append(str);
            if (obj == null || !obj.getClass().isArray()) {
                sb.append(obj);
            } else {
                String deepToString = Arrays.deepToString(new Object[]{obj});
                sb.append((CharSequence) deepToString, 1, deepToString.length() - 1);
            }
            zzfjw = zzfjw.zzb;
            str = ", ";
        }
        sb.append('}');
        return sb.toString();
    }
}
