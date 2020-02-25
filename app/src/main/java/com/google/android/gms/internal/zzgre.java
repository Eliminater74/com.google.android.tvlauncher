package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;
import java.util.Arrays;

/* compiled from: UnknownFieldSetLite */
public final class zzgre {
    private static final zzgre zza = new zzgre(0, new int[0], new Object[0], false);
    private int zzb;
    private int[] zzc;
    private Object[] zzd;
    private int zze;
    private boolean zzf;

    private zzgre() {
        this(0, new int[8], new Object[8], true);
    }

    private zzgre(int i, int[] iArr, Object[] objArr, boolean z) {
        this.zze = -1;
        this.zzb = i;
        this.zzc = iArr;
        this.zzd = objArr;
        this.zzf = z;
    }

    public static zzgre zza() {
        return zza;
    }

    static zzgre zzb() {
        return new zzgre();
    }

    static zzgre zza(zzgre zzgre, zzgre zzgre2) {
        int i = zzgre.zzb + zzgre2.zzb;
        int[] copyOf = Arrays.copyOf(zzgre.zzc, i);
        System.arraycopy(zzgre2.zzc, 0, copyOf, zzgre.zzb, zzgre2.zzb);
        Object[] copyOf2 = Arrays.copyOf(zzgre.zzd, i);
        System.arraycopy(zzgre2.zzd, 0, copyOf2, zzgre.zzb, zzgre2.zzb);
        return new zzgre(i, copyOf, copyOf2, true);
    }

    private static void zza(int i, Object obj, zzgrx zzgrx) {
        int i2 = i >>> 3;
        int i3 = i & 7;
        if (i3 == 0) {
            zzgrx.zza(i2, ((Long) obj).longValue());
        } else if (i3 == 1) {
            zzgrx.zzd(i2, ((Long) obj).longValue());
        } else if (i3 == 2) {
            zzgrx.zza(i2, (zzgnb) obj);
        } else if (i3 != 3) {
            if (i3 == 5) {
                zzgrx.zzd(i2, ((Integer) obj).intValue());
                return;
            }
            throw new RuntimeException(zzgot.zzf());
        } else if (zzgrx.zza() == zzgoj.zzg.zzk) {
            zzgrx.zza(i2);
            ((zzgre) obj).zzb(zzgrx);
            zzgrx.zzb(i2);
        } else {
            zzgrx.zzb(i2);
            ((zzgre) obj).zzb(zzgrx);
            zzgrx.zza(i2);
        }
    }

    public final void zzc() {
        this.zzf = false;
    }

    public final void zza(zzgnp zzgnp) throws IOException {
        for (int i = 0; i < this.zzb; i++) {
            int i2 = this.zzc[i];
            int i3 = i2 >>> 3;
            int i4 = i2 & 7;
            if (i4 == 0) {
                zzgnp.zza(i3, ((Long) this.zzd[i]).longValue());
            } else if (i4 == 1) {
                zzgnp.zzc(i3, ((Long) this.zzd[i]).longValue());
            } else if (i4 == 2) {
                zzgnp.zza(i3, (zzgnb) this.zzd[i]);
            } else if (i4 == 3) {
                zzgnp.zza(i3, 3);
                ((zzgre) this.zzd[i]).zza(zzgnp);
                zzgnp.zza(i3, 4);
            } else if (i4 == 5) {
                zzgnp.zze(i3, ((Integer) this.zzd[i]).intValue());
            } else {
                throw zzgot.zzf();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzgrx zzgrx) {
        if (zzgrx.zza() == zzgoj.zzg.zzl) {
            for (int i = this.zzb - 1; i >= 0; i--) {
                zzgrx.zzc(this.zzc[i] >>> 3, this.zzd[i]);
            }
            return;
        }
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzgrx.zzc(this.zzc[i2] >>> 3, this.zzd[i2]);
        }
    }

    public final void zzb(zzgrx zzgrx) {
        if (this.zzb != 0) {
            if (zzgrx.zza() == zzgoj.zzg.zzk) {
                for (int i = 0; i < this.zzb; i++) {
                    zza(this.zzc[i], this.zzd[i], zzgrx);
                }
                return;
            }
            for (int i2 = this.zzb - 1; i2 >= 0; i2--) {
                zza(this.zzc[i2], this.zzd[i2], zzgrx);
            }
        }
    }

    public final int zzd() {
        int i = this.zze;
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb; i3++) {
            i2 += zzgnp.zzd(this.zzc[i3] >>> 3, (zzgnb) this.zzd[i3]);
        }
        this.zze = i2;
        return i2;
    }

    public final int zze() {
        int i;
        int i2 = this.zze;
        if (i2 != -1) {
            return i2;
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.zzb; i4++) {
            int i5 = this.zzc[i4];
            int i6 = i5 >>> 3;
            int i7 = i5 & 7;
            if (i7 == 0) {
                i = zzgnp.zze(i6, ((Long) this.zzd[i4]).longValue());
            } else if (i7 == 1) {
                i = zzgnp.zzg(i6, ((Long) this.zzd[i4]).longValue());
            } else if (i7 == 2) {
                i = zzgnp.zzc(i6, (zzgnb) this.zzd[i4]);
            } else if (i7 == 3) {
                i = (zzgnp.zze(i6) << 1) + ((zzgre) this.zzd[i4]).zze();
            } else if (i7 == 5) {
                i = zzgnp.zzi(i6, ((Integer) this.zzd[i4]).intValue());
            } else {
                throw new IllegalStateException(zzgot.zzf());
            }
            i3 += i;
        }
        this.zze = i3;
        return i3;
    }

    public final boolean equals(Object obj) {
        boolean z;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof zzgre)) {
            return false;
        }
        zzgre zzgre = (zzgre) obj;
        int i = this.zzb;
        if (i == zzgre.zzb) {
            int[] iArr = this.zzc;
            int[] iArr2 = zzgre.zzc;
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
                Object[] objArr = this.zzd;
                Object[] objArr2 = zzgre.zzd;
                int i3 = this.zzb;
                int i4 = 0;
                while (true) {
                    if (i4 >= i3) {
                        z2 = true;
                        break;
                    } else if (!objArr[i4].equals(objArr2[i4])) {
                        z2 = false;
                        break;
                    } else {
                        i4++;
                    }
                }
                if (!z2) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return ((((this.zzb + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + Arrays.hashCode(this.zzc)) * 31) + Arrays.deepHashCode(this.zzd);
    }

    /* access modifiers changed from: package-private */
    public final void zza(StringBuilder sb, int i) {
        for (int i2 = 0; i2 < this.zzb; i2++) {
            zzgpw.zza(sb, i, String.valueOf(this.zzc[i2] >>> 3), this.zzd[i2]);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(int i, Object obj) {
        if (this.zzf) {
            int i2 = this.zzb;
            if (i2 == this.zzc.length) {
                int i3 = this.zzb + (i2 < 4 ? 8 : i2 >> 1);
                this.zzc = Arrays.copyOf(this.zzc, i3);
                this.zzd = Arrays.copyOf(this.zzd, i3);
            }
            int[] iArr = this.zzc;
            int i4 = this.zzb;
            iArr[i4] = i;
            this.zzd[i4] = obj;
            this.zzb = i4 + 1;
            return;
        }
        throw new UnsupportedOperationException();
    }
}
