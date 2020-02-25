package com.google.android.gms.common;

import com.google.android.gms.common.util.Hex;

/* compiled from: GoogleCertificatesResult */
final class zzp extends zzn {
    private final String zzb;
    private final zzf zzc;
    private final boolean zzd;
    private final boolean zze;

    private zzp(String str, zzf zzf, boolean z, boolean z2) {
        super(false, null, null);
        this.zzb = str;
        this.zzc = zzf;
        this.zzd = z;
        this.zze = z2;
    }

    /* access modifiers changed from: package-private */
    public final String zzb() {
        String str = this.zze ? "debug cert rejected" : "not whitelisted";
        String str2 = this.zzb;
        String bytesToStringLowercase = Hex.bytesToStringLowercase(zza.zza("SHA-1").digest(this.zzc.zza()));
        boolean z = this.zzd;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 44 + String.valueOf(str2).length() + String.valueOf(bytesToStringLowercase).length());
        sb.append(str);
        sb.append(": pkg=");
        sb.append(str2);
        sb.append(", sha1=");
        sb.append(bytesToStringLowercase);
        sb.append(", atk=");
        sb.append(z);
        sb.append(", ver=12525009.false");
        return sb.toString();
    }
}
