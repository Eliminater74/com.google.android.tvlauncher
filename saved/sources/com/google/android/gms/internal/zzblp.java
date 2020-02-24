package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.zzt;
import java.util.Locale;

/* compiled from: Logger */
public class zzblp {
    private final String zza;
    private final String zzb;
    private final zzt zzc;
    private final int zzd;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public zzblp(java.lang.String r7, java.lang.String... r8) {
        /*
            r6 = this;
            int r0 = r8.length
            if (r0 != 0) goto L_0x0007
            java.lang.String r8 = ""
            goto L_0x0037
        L_0x0007:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 91
            r0.append(r1)
            int r1 = r8.length
            r2 = 0
        L_0x0013:
            if (r2 >= r1) goto L_0x0029
            r3 = r8[r2]
            int r4 = r0.length()
            r5 = 1
            if (r4 <= r5) goto L_0x0023
            java.lang.String r4 = ","
            r0.append(r4)
        L_0x0023:
            r0.append(r3)
            int r2 = r2 + 1
            goto L_0x0013
        L_0x0029:
            r8 = 93
            r0.append(r8)
            r8 = 32
            r0.append(r8)
            java.lang.String r8 = r0.toString()
        L_0x0037:
            r6.<init>(r7, r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzblp.<init>(java.lang.String, java.lang.String[]):void");
    }

    private zzblp(String str, String str2) {
        this.zzb = str2;
        this.zza = str;
        this.zzc = new zzt(str);
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.zza, i)) {
            i++;
        }
        this.zzd = i;
    }

    public final String zza() {
        return this.zza;
    }

    public final boolean zza(int i) {
        return this.zzd <= i;
    }

    public void zza(String str, @Nullable Object... objArr) {
        if (zza(2)) {
            Log.v(this.zza, zzf(str, objArr));
        }
    }

    public void zzb(String str, @Nullable Object... objArr) {
        if (zza(3)) {
            Log.d(this.zza, zzf(str, objArr));
        }
    }

    public final void zzc(String str, @Nullable Object... objArr) {
        Log.i(this.zza, zzf(str, objArr));
    }

    public final void zzd(String str, @Nullable Object... objArr) {
        Log.w(this.zza, zzf(str, objArr));
    }

    public final void zze(String str, @Nullable Object... objArr) {
        Log.e(this.zza, zzf(str, objArr));
    }

    public final void zza(String str, Throwable th, @Nullable Object... objArr) {
        Log.e(this.zza, zzf(str, objArr), th);
    }

    public final void zzb(String str, Throwable th, @Nullable Object... objArr) {
        Log.wtf(this.zza, zzf(str, objArr), th);
    }

    public final void zza(Throwable th) {
        Log.wtf(this.zza, th);
    }

    /* access modifiers changed from: protected */
    public final String zzf(String str, @Nullable Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(Locale.US, str, objArr);
        }
        return this.zzb.concat(str);
    }
}
