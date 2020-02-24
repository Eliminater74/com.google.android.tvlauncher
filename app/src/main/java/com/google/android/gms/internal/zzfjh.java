package com.google.android.gms.internal;

import android.net.Uri;

/* compiled from: PhenotypeFlag */
public final class zzfjh {
    /* access modifiers changed from: private */
    public final String zza;
    /* access modifiers changed from: private */
    public final Uri zzb;
    /* access modifiers changed from: private */
    public final String zzc;
    /* access modifiers changed from: private */
    public final String zzd;
    /* access modifiers changed from: private */
    public final boolean zze;
    /* access modifiers changed from: private */
    public final boolean zzf;

    public zzfjh(Uri uri) {
        this(null, uri, "", "", false, false);
    }

    private zzfjh(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
        this.zza = str;
        this.zzb = uri;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = z;
        this.zzf = z2;
    }

    public final zzfjh zza(String str) {
        boolean z = this.zze;
        if (!z) {
            return new zzfjh(this.zza, this.zzb, str, this.zzd, z, this.zzf);
        }
        throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
    }

    public final zzfjh zzb(String str) {
        return new zzfjh(this.zza, this.zzb, this.zzc, str, this.zze, this.zzf);
    }

    public final zzfja<String> zza(String str, String str2) {
        return zzfja.zzb(this, str, null);
    }
}
