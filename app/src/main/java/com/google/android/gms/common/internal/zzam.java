package com.google.android.gms.common.internal;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: Objects */
public final class zzam {
    private final List<String> zza;
    private final Object zzb;

    private zzam(Object obj) {
        this.zzb = zzau.zza(obj);
        this.zza = new ArrayList();
    }

    public final zzam zza(String str, @Nullable Object obj) {
        List<String> list = this.zza;
        String str2 = (String) zzau.zza((Object) str);
        String valueOf = String.valueOf(obj);
        StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(valueOf).length());
        sb.append(str2);
        sb.append("=");
        sb.append(valueOf);
        list.add(sb.toString());
        return this;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(100);
        sb.append(this.zzb.getClass().getSimpleName());
        sb.append('{');
        int size = this.zza.size();
        for (int i = 0; i < size; i++) {
            sb.append(this.zza.get(i));
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
