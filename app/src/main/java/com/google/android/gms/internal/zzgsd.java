package com.google.android.gms.internal;

/* compiled from: FieldArray */
public final class zzgsd implements Cloneable {
    private static final zzgse zza = new zzgse();
    private boolean zzb;
    private int[] zzc;
    private zzgse[] zzd;
    private int zze;

    zzgsd() {
        this(10);
    }

    private zzgsd(int i) {
        this.zzb = false;
        int zzd2 = zzd(i);
        this.zzc = new int[zzd2];
        this.zzd = new zzgse[zzd2];
        this.zze = 0;
    }

    private static int zzd(int i) {
        int i2 = i << 2;
        int i3 = 4;
        while (true) {
            if (i3 >= 32) {
                break;
            }
            int i4 = (1 << i3) - 12;
            if (i2 <= i4) {
                i2 = i4;
                break;
            }
            i3++;
        }
        return i2 / 4;
    }

    /* access modifiers changed from: package-private */
    public final zzgse zza(int i) {
        int zze2 = zze(i);
        if (zze2 < 0) {
            return null;
        }
        zzgse[] zzgseArr = this.zzd;
        if (zzgseArr[zze2] == zza) {
            return null;
        }
        return zzgseArr[zze2];
    }

    /* access modifiers changed from: package-private */
    public final void zzb(int i) {
        zzgse[] zzgseArr;
        zzgse zzgse;
        int zze2 = zze(i);
        if (zze2 >= 0 && (zzgseArr = this.zzd)[zze2] != (zzgse = zza)) {
            zzgseArr[zze2] = zzgse;
            this.zzb = true;
        }
    }

    private final void zzc() {
        int i = this.zze;
        int[] iArr = this.zzc;
        zzgse[] zzgseArr = this.zzd;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            zzgse zzgse = zzgseArr[i3];
            if (zzgse != zza) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    zzgseArr[i2] = zzgse;
                    zzgseArr[i3] = null;
                }
                i2++;
            }
        }
        this.zzb = false;
        this.zze = i2;
    }

    /* access modifiers changed from: package-private */
    public final void zza(int i, zzgse zzgse) {
        int zze2 = zze(i);
        if (zze2 >= 0) {
            this.zzd[zze2] = zzgse;
            return;
        }
        int i2 = zze2 ^ -1;
        if (i2 < this.zze) {
            zzgse[] zzgseArr = this.zzd;
            if (zzgseArr[i2] == zza) {
                this.zzc[i2] = i;
                zzgseArr[i2] = zzgse;
                return;
            }
        }
        if (this.zzb && this.zze >= this.zzc.length) {
            zzc();
            i2 = zze(i) ^ -1;
        }
        int i3 = this.zze;
        if (i3 >= this.zzc.length) {
            int zzd2 = zzd(i3 + 1);
            int[] iArr = new int[zzd2];
            zzgse[] zzgseArr2 = new zzgse[zzd2];
            int[] iArr2 = this.zzc;
            System.arraycopy(iArr2, 0, iArr, 0, iArr2.length);
            zzgse[] zzgseArr3 = this.zzd;
            System.arraycopy(zzgseArr3, 0, zzgseArr2, 0, zzgseArr3.length);
            this.zzc = iArr;
            this.zzd = zzgseArr2;
        }
        int i4 = this.zze;
        if (i4 - i2 != 0) {
            int[] iArr3 = this.zzc;
            int i5 = i2 + 1;
            System.arraycopy(iArr3, i2, iArr3, i5, i4 - i2);
            zzgse[] zzgseArr4 = this.zzd;
            System.arraycopy(zzgseArr4, i2, zzgseArr4, i5, this.zze - i2);
        }
        this.zzc[i2] = i;
        this.zzd[i2] = zzgse;
        this.zze++;
    }

    /* access modifiers changed from: package-private */
    public final int zza() {
        if (this.zzb) {
            zzc();
        }
        return this.zze;
    }

    public final boolean zzb() {
        return zza() == 0;
    }

    /* access modifiers changed from: package-private */
    public final zzgse zzc(int i) {
        if (this.zzb) {
            zzc();
        }
        return this.zzd[i];
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgsd)) {
            return false;
        }
        zzgsd zzgsd = (zzgsd) obj;
        if (zza() != zzgsd.zza()) {
            return false;
        }
        int[] iArr = this.zzc;
        int[] iArr2 = zzgsd.zzc;
        int i = this.zze;
        int i2 = 0;
        while (true) {
            if (i2 >= i) {
                z = true;
                break;
            } else if (iArr[i2] != iArr2[i2]) {
                z = false;
                break;
            } else {
                i2++;
            }
        }
        if (z) {
            zzgse[] zzgseArr = this.zzd;
            zzgse[] zzgseArr2 = zzgsd.zzd;
            int i3 = this.zze;
            int i4 = 0;
            while (true) {
                if (i4 >= i3) {
                    z2 = true;
                    break;
                } else if (!zzgseArr[i4].equals(zzgseArr2[i4])) {
                    z2 = false;
                    break;
                } else {
                    i4++;
                }
            }
            if (z2) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        if (this.zzb) {
            zzc();
        }
        int i = 17;
        for (int i2 = 0; i2 < this.zze; i2++) {
            i = (((i * 31) + this.zzc[i2]) * 31) + this.zzd[i2].hashCode();
        }
        return i;
    }

    private final int zze(int i) {
        int i2 = this.zze - 1;
        int i3 = 0;
        while (i3 <= i2) {
            int i4 = (i3 + i2) >>> 1;
            int i5 = this.zzc[i4];
            if (i5 < i) {
                i3 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i2 = i4 - 1;
            }
        }
        return i3 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        int zza2 = zza();
        zzgsd zzgsd = new zzgsd(zza2);
        System.arraycopy(this.zzc, 0, zzgsd.zzc, 0, zza2);
        for (int i = 0; i < zza2; i++) {
            zzgse[] zzgseArr = this.zzd;
            if (zzgseArr[i] != null) {
                zzgsd.zzd[i] = (zzgse) zzgseArr[i].clone();
            }
        }
        zzgsd.zze = zza2;
        return zzgsd;
    }
}
