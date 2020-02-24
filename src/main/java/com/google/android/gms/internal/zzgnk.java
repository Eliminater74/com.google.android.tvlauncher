package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: CodedInputStream */
public abstract class zzgnk {
    private static volatile boolean zzf;
    int zza;
    int zzb;
    zzgnn zzc;
    private int zzd;
    private boolean zze;

    public static zzgnk zza(byte[] bArr, int i, int i2) {
        return zza(bArr, i, i2, false);
    }

    public abstract int zza() throws IOException;

    public abstract <T extends zzgpt> T zza(zzgqe<T> zzgqe, zzgnv zzgnv) throws IOException;

    public abstract void zza(int i) throws zzgot;

    public abstract double zzb() throws IOException;

    public abstract boolean zzb(int i) throws IOException;

    public abstract float zzc() throws IOException;

    public abstract int zzd(int i) throws zzgot;

    public abstract long zzd() throws IOException;

    public abstract long zze() throws IOException;

    public abstract void zze(int i);

    public abstract int zzf() throws IOException;

    public abstract void zzf(int i) throws IOException;

    public abstract long zzg() throws IOException;

    public abstract int zzh() throws IOException;

    public abstract boolean zzi() throws IOException;

    public abstract String zzj() throws IOException;

    public abstract String zzk() throws IOException;

    public abstract zzgnb zzl() throws IOException;

    public abstract int zzm() throws IOException;

    public abstract int zzn() throws IOException;

    public abstract int zzo() throws IOException;

    public abstract long zzp() throws IOException;

    public abstract int zzq() throws IOException;

    public abstract long zzr() throws IOException;

    /* access modifiers changed from: package-private */
    public abstract long zzs() throws IOException;

    public abstract boolean zzt() throws IOException;

    public abstract int zzu();

    static zzgnk zza(byte[] bArr, int i, int i2, boolean z) {
        zzgnm zzgnm = new zzgnm(bArr, i, i2, z);
        try {
            zzgnm.zzd(i2);
            return zzgnm;
        } catch (zzgot e) {
            throw new IllegalArgumentException(e);
        }
    }

    private zzgnk() {
        this.zzb = 100;
        this.zzd = Integer.MAX_VALUE;
        this.zze = false;
    }

    public final int zzc(int i) {
        if (i >= 0) {
            int i2 = this.zzb;
            this.zzb = i;
            return i2;
        }
        StringBuilder sb = new StringBuilder(47);
        sb.append("Recursion limit cannot be negative: ");
        sb.append(i);
        throw new IllegalArgumentException(sb.toString());
    }

    public static int zzg(int i) {
        return (-(i & 1)) ^ (i >>> 1);
    }

    public static long zza(long j) {
        return (-(j & 1)) ^ (j >>> 1);
    }

    static {
        zzf = false;
        zzf = true;
    }
}
