package com.google.android.gms.internal;

import com.google.android.gms.internal.zzgoj;

/* compiled from: RawMessageInfo */
final class zzgqh implements zzgpr {
    private final zzgpt zza;
    private final String zzb;
    private final zzgqi zzc;

    zzgqh(zzgpt zzgpt, String str, Object[] objArr) {
        this.zza = zzgpt;
        this.zzb = str;
        this.zzc = new zzgqi(zzgpt.getClass(), str, objArr);
    }

    /* access modifiers changed from: package-private */
    public final zzgqi zzd() {
        return this.zzc;
    }

    public final int zza() {
        return (this.zzc.zzd & 1) == 1 ? zzgoj.zzg.zzi : zzgoj.zzg.zzj;
    }

    public final boolean zzb() {
        return (this.zzc.zzd & 2) == 2;
    }

    public final zzgpt zzc() {
        return this.zza;
    }

    public final int zze() {
        return this.zzc.zzh;
    }

    public final int zzf() {
        return this.zzc.zzi;
    }

    public final int zzg() {
        return this.zzc.zze;
    }

    public final int zzh() {
        return this.zzc.zzj;
    }

    public final int zzi() {
        return this.zzc.zzm;
    }

    /* access modifiers changed from: package-private */
    public final int[] zzj() {
        return this.zzc.zzn;
    }

    public final int zzk() {
        return this.zzc.zzl;
    }

    public final int zzl() {
        return this.zzc.zzk;
    }
}
