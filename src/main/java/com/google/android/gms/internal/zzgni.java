package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: ByteString */
class zzgni extends zzgnh {
    protected final byte[] zzb;

    zzgni(byte[] bArr) {
        this.zzb = bArr;
    }

    public byte zza(int i) {
        return this.zzb[i];
    }

    public int zza() {
        return this.zzb.length;
    }

    public final zzgnb zza(int i, int i2) {
        int zzb2 = zzb(0, i2, zza());
        if (zzb2 == 0) {
            return zzgnb.zza;
        }
        return new zzgne(this.zzb, zzh(), zzb2);
    }

    /* access modifiers changed from: protected */
    public void zza(byte[] bArr, int i, int i2, int i3) {
        System.arraycopy(this.zzb, 0, bArr, 0, i3);
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgna zzgna) throws IOException {
        zzgna.zza(this.zzb, zzh(), zza());
    }

    /* access modifiers changed from: protected */
    public final String zza(Charset charset) {
        return new String(this.zzb, zzh(), zza(), charset);
    }

    public final boolean zze() {
        int zzh = zzh();
        return zzgrl.zza(this.zzb, zzh, zza() + zzh);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgnb) || zza() != ((zzgnb) obj).zza()) {
            return false;
        }
        if (zza() == 0) {
            return true;
        }
        if (!(obj instanceof zzgni)) {
            return obj.equals(this);
        }
        zzgni zzgni = (zzgni) obj;
        int zzg = zzg();
        int zzg2 = zzgni.zzg();
        if (zzg == 0 || zzg2 == 0 || zzg == zzg2) {
            return zza(zzgni, 0, zza());
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(zzgnb zzgnb, int i, int i2) {
        if (i2 > zzgnb.zza()) {
            int zza = zza();
            StringBuilder sb = new StringBuilder(40);
            sb.append("Length too large: ");
            sb.append(i2);
            sb.append(zza);
            throw new IllegalArgumentException(sb.toString());
        } else if (i2 > zzgnb.zza()) {
            int zza2 = zzgnb.zza();
            StringBuilder sb2 = new StringBuilder(59);
            sb2.append("Ran off end of other: 0, ");
            sb2.append(i2);
            sb2.append(", ");
            sb2.append(zza2);
            throw new IllegalArgumentException(sb2.toString());
        } else if (!(zzgnb instanceof zzgni)) {
            return zzgnb.zza(0, i2).equals(zza(0, i2));
        } else {
            zzgni zzgni = (zzgni) zzgnb;
            byte[] bArr = this.zzb;
            byte[] bArr2 = zzgni.zzb;
            int zzh = zzh() + i2;
            int zzh2 = zzh();
            int zzh3 = zzgni.zzh();
            while (zzh2 < zzh) {
                if (bArr[zzh2] != bArr2[zzh3]) {
                    return false;
                }
                zzh2++;
                zzh3++;
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public final int zza(int i, int i2, int i3) {
        return zzgon.zza(i, this.zzb, zzh(), i3);
    }

    public final zzgnk zzf() {
        return zzgnk.zza(this.zzb, zzh(), zza(), true);
    }

    /* access modifiers changed from: protected */
    public int zzh() {
        return 0;
    }
}
