package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;

@Hide
/* compiled from: GmsServiceEndpoint */
public final class zzu {
    @NonNull
    private final String zza;
    @NonNull
    private final String zzb;
    private final int zzc;
    private final boolean zzd;

    public zzu(@NonNull String str, @NonNull String str2, boolean z, int i) {
        this.zzb = str;
        this.zza = str2;
        this.zzd = z;
        this.zzc = i;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final String zza() {
        return this.zza;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public final String zzb() {
        return this.zzb;
    }

    /* access modifiers changed from: package-private */
    public final int zzc() {
        return this.zzc;
    }
}
