package com.google.android.gms.internal;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Iterator;

/* compiled from: ByteString */
public abstract class zzgnb implements Serializable, Iterable<Byte> {
    public static final zzgnb zza = new zzgni(zzgon.zzb);
    private static final zzgnf zzb = (zzgmt.zza() ? new zzgnj(null) : new zzgnd(null));
    private int zzc = 0;

    zzgnb() {
    }

    public abstract boolean equals(Object obj);

    public abstract byte zza(int i);

    public abstract int zza();

    /* access modifiers changed from: protected */
    public abstract int zza(int i, int i2, int i3);

    public abstract zzgnb zza(int i, int i2);

    /* access modifiers changed from: protected */
    public abstract String zza(Charset charset);

    /* access modifiers changed from: package-private */
    public abstract void zza(zzgna zzgna) throws IOException;

    /* access modifiers changed from: protected */
    public abstract void zza(byte[] bArr, int i, int i2, int i3);

    public abstract boolean zze();

    public abstract zzgnk zzf();

    public final boolean zzb() {
        return zza() == 0;
    }

    public static zzgnb zza(byte[] bArr, int i, int i2) {
        return new zzgni(zzb.zza(bArr, i, i2));
    }

    public static zzgnb zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    static zzgnb zzb(byte[] bArr) {
        return new zzgni(bArr);
    }

    static zzgnb zzb(byte[] bArr, int i, int i2) {
        return new zzgne(bArr, i, i2);
    }

    public static zzgnb zza(String str) {
        return new zzgni(str.getBytes(zzgon.zza));
    }

    public final byte[] zzc() {
        int zza2 = zza();
        if (zza2 == 0) {
            return zzgon.zzb;
        }
        byte[] bArr = new byte[zza2];
        zza(bArr, 0, 0, zza2);
        return bArr;
    }

    public final String zzd() {
        return zza() == 0 ? "" : zza(zzgon.zza);
    }

    public final int hashCode() {
        int i = this.zzc;
        if (i == 0) {
            int zza2 = zza();
            i = zza(zza2, 0, zza2);
            if (i == 0) {
                i = 1;
            }
            this.zzc = i;
        }
        return i;
    }

    static zzgng zzb(int i) {
        return new zzgng(i, null);
    }

    /* access modifiers changed from: protected */
    public final int zzg() {
        return this.zzc;
    }

    static int zzb(int i, int i2, int i3) {
        int i4 = i2 - i;
        if ((i | i2 | i4 | (i3 - i2)) >= 0) {
            return i4;
        }
        if (i < 0) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Beginning index: ");
            sb.append(i);
            sb.append(" < 0");
            throw new IndexOutOfBoundsException(sb.toString());
        } else if (i2 < i) {
            StringBuilder sb2 = new StringBuilder(66);
            sb2.append("Beginning index larger than ending index: ");
            sb2.append(i);
            sb2.append(", ");
            sb2.append(i2);
            throw new IndexOutOfBoundsException(sb2.toString());
        } else {
            StringBuilder sb3 = new StringBuilder(37);
            sb3.append("End index: ");
            sb3.append(i2);
            sb3.append(" >= ");
            sb3.append(i3);
            throw new IndexOutOfBoundsException(sb3.toString());
        }
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), Integer.valueOf(zza()));
    }

    public /* synthetic */ Iterator iterator() {
        return new zzgnc(this);
    }
}
