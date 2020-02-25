package com.google.android.gms.common;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.internal.Hide;

@Hide
        /* compiled from: GoogleCertificatesResult */
class zzn {
    private static final zzn zzb = new zzn(true, null, null);
    final boolean zza;
    private final String zzc;
    private final Throwable zzd;

    zzn(boolean z, String str, Throwable th) {
        this.zza = z;
        this.zzc = str;
        this.zzd = th;
    }

    static zzn zza() {
        return zzb;
    }

    static zzn zza(String str, zzf zzf, boolean z, boolean z2) {
        return new zzp(str, zzf, z, z2);
    }

    static zzn zza(@NonNull String str) {
        return new zzn(false, str, null);
    }

    static zzn zza(@NonNull String str, @NonNull Throwable th) {
        return new zzn(false, str, th);
    }

    /* access modifiers changed from: package-private */
    public String zzb() {
        return this.zzc;
    }

    /* access modifiers changed from: package-private */
    public final void zzc() throws SecurityException {
        if (!this.zza) {
            String valueOf = String.valueOf("GoogleCertificatesRslt: ");
            String valueOf2 = String.valueOf(zzb());
            String concat = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            Throwable th = this.zzd;
            if (th != null) {
                throw new SecurityException(concat, th);
            }
            throw new SecurityException(concat);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzd() {
        if (this.zza) {
            return;
        }
        if (this.zzd != null) {
            Log.d("GoogleCertificatesRslt", zzb(), this.zzd);
        } else {
            Log.d("GoogleCertificatesRslt", zzb());
        }
    }
}
