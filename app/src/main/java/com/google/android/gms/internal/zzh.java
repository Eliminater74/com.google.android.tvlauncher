package com.google.android.gms.internal;

/* compiled from: DefaultRetryPolicy */
public final class zzh implements zzab {
    private final int zzc;
    private final float zzd;
    private int zza;
    private int zzb;

    public zzh() {
        this(2500, 1, 1.0f);
    }

    private zzh(int i, int i2, float f) {
        this.zza = 2500;
        this.zzc = 1;
        this.zzd = 1.0f;
    }

    public final int zza() {
        return this.zza;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final void zza(zzae zzae) throws zzae {
        boolean z = true;
        this.zzb++;
        int i = this.zza;
        this.zza = (int) (((float) i) + (((float) i) * this.zzd));
        if (this.zzb > this.zzc) {
            z = false;
        }
        if (!z) {
            throw zzae;
        }
    }
}
