package com.google.android.gms.internal;

/* compiled from: VolleyError */
public class zzae extends Exception {
    private final zzp zza;
    private long zzb;

    public zzae() {
        this.zza = null;
    }

    public zzae(zzp zzp) {
        this.zza = zzp;
    }

    public zzae(String str) {
        super(str);
        this.zza = null;
    }

    public zzae(Throwable th) {
        super(th);
        this.zza = null;
    }

    /* access modifiers changed from: package-private */
    public final void zza(long j) {
        this.zzb = j;
    }
}
