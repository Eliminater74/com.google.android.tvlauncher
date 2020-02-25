package com.google.android.gms.internal;

/* compiled from: ByteString */
final class zzgng {
    private final zzgnp zza;
    private final byte[] zzb;

    private zzgng(int i) {
        this.zzb = new byte[i];
        this.zza = zzgnp.zza(this.zzb);
    }

    /* synthetic */ zzgng(int i, zzgnc zzgnc) {
        this(i);
    }

    public final zzgnb zza() {
        this.zza.zzc();
        return new zzgni(this.zzb);
    }

    public final zzgnp zzb() {
        return this.zza;
    }
}
