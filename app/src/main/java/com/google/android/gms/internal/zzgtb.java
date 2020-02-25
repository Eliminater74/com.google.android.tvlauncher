package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;

/* compiled from: ClientAnalytics */
public final class zzgtb extends zzgsb<zzgtb> implements Cloneable {
    private String[] zza = zzgsk.zzf;
    private String[] zzb = zzgsk.zzf;
    private int[] zzc = zzgsk.zza;
    private long[] zzd = zzgsk.zzb;
    private long[] zze = zzgsk.zzb;

    public zzgtb() {
        this.zzay = null;
        this.zzaz = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final zzgtb clone() {
        try {
            zzgtb zzgtb = (zzgtb) super.clone();
            String[] strArr = this.zza;
            if (strArr != null && strArr.length > 0) {
                zzgtb.zza = (String[]) strArr.clone();
            }
            String[] strArr2 = this.zzb;
            if (strArr2 != null && strArr2.length > 0) {
                zzgtb.zzb = (String[]) strArr2.clone();
            }
            int[] iArr = this.zzc;
            if (iArr != null && iArr.length > 0) {
                zzgtb.zzc = (int[]) iArr.clone();
            }
            long[] jArr = this.zzd;
            if (jArr != null && jArr.length > 0) {
                zzgtb.zzd = (long[]) jArr.clone();
            }
            long[] jArr2 = this.zze;
            if (jArr2 != null && jArr2.length > 0) {
                zzgtb.zze = (long[]) jArr2.clone();
            }
            return zzgtb;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgtb)) {
            return false;
        }
        zzgtb zzgtb = (zzgtb) obj;
        if (!zzgsf.zza(this.zza, zzgtb.zza) || !zzgsf.zza(this.zzb, zzgtb.zzb) || !zzgsf.zza(this.zzc, zzgtb.zzc) || !zzgsf.zza(this.zzd, zzgtb.zzd) || !zzgsf.zza(this.zze, zzgtb.zze)) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgtb.zzay);
        }
        if (zzgtb.zzay == null || zzgtb.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int hashCode = (((((((((((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + zzgsf.zza(this.zza)) * 31) + zzgsf.zza(this.zzb)) * 31) + zzgsf.zza(this.zzc)) * 31) + zzgsf.zza(this.zzd)) * 31) + zzgsf.zza(this.zze)) * 31;
        if (this.zzay == null || this.zzay.zzb()) {
            i = 0;
        } else {
            i = this.zzay.hashCode();
        }
        return hashCode + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        String[] strArr = this.zza;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = this.zza;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    zzgrz.zza(1, str);
                }
                i2++;
            }
        }
        String[] strArr3 = this.zzb;
        if (strArr3 != null && strArr3.length > 0) {
            int i3 = 0;
            while (true) {
                String[] strArr4 = this.zzb;
                if (i3 >= strArr4.length) {
                    break;
                }
                String str2 = strArr4[i3];
                if (str2 != null) {
                    zzgrz.zza(2, str2);
                }
                i3++;
            }
        }
        int[] iArr = this.zzc;
        if (iArr != null && iArr.length > 0) {
            int i4 = 0;
            while (true) {
                int[] iArr2 = this.zzc;
                if (i4 >= iArr2.length) {
                    break;
                }
                zzgrz.zza(3, iArr2[i4]);
                i4++;
            }
        }
        long[] jArr = this.zzd;
        if (jArr != null && jArr.length > 0) {
            int i5 = 0;
            while (true) {
                long[] jArr2 = this.zzd;
                if (i5 >= jArr2.length) {
                    break;
                }
                zzgrz.zzb(4, jArr2[i5]);
                i5++;
            }
        }
        long[] jArr3 = this.zze;
        if (jArr3 != null && jArr3.length > 0) {
            while (true) {
                long[] jArr4 = this.zze;
                if (i >= jArr4.length) {
                    break;
                }
                zzgrz.zzb(5, jArr4[i]);
                i++;
            }
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        long[] jArr;
        int[] iArr;
        int computeSerializedSize = super.computeSerializedSize();
        String[] strArr = this.zza;
        int i = 0;
        if (strArr != null && strArr.length > 0) {
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (true) {
                String[] strArr2 = this.zza;
                if (i2 >= strArr2.length) {
                    break;
                }
                String str = strArr2[i2];
                if (str != null) {
                    i4++;
                    i3 += zzgrz.zza(str);
                }
                i2++;
            }
            computeSerializedSize = computeSerializedSize + i3 + (i4 * 1);
        }
        String[] strArr3 = this.zzb;
        if (strArr3 != null && strArr3.length > 0) {
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            while (true) {
                String[] strArr4 = this.zzb;
                if (i5 >= strArr4.length) {
                    break;
                }
                String str2 = strArr4[i5];
                if (str2 != null) {
                    i7++;
                    i6 += zzgrz.zza(str2);
                }
                i5++;
            }
            computeSerializedSize = computeSerializedSize + i6 + (i7 * 1);
        }
        int[] iArr2 = this.zzc;
        if (iArr2 != null && iArr2.length > 0) {
            int i8 = 0;
            int i9 = 0;
            while (true) {
                iArr = this.zzc;
                if (i8 >= iArr.length) {
                    break;
                }
                i9 += zzgrz.zza(iArr[i8]);
                i8++;
            }
            computeSerializedSize = computeSerializedSize + i9 + (iArr.length * 1);
        }
        long[] jArr2 = this.zzd;
        if (jArr2 != null && jArr2.length > 0) {
            int i10 = 0;
            int i11 = 0;
            while (true) {
                jArr = this.zzd;
                if (i10 >= jArr.length) {
                    break;
                }
                i11 += zzgrz.zzb(jArr[i10]);
                i10++;
            }
            computeSerializedSize = computeSerializedSize + i11 + (jArr.length * 1);
        }
        long[] jArr3 = this.zze;
        if (jArr3 == null || jArr3.length <= 0) {
            return computeSerializedSize;
        }
        int i12 = 0;
        while (true) {
            long[] jArr4 = this.zze;
            if (i >= jArr4.length) {
                return computeSerializedSize + i12 + (jArr4.length * 1);
            }
            i12 += zzgrz.zzb(jArr4[i]);
            i++;
        }
    }

    public final /* synthetic */ zzgsh mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 10) {
                int zza3 = zzgsk.zza(zzgry, 10);
                String[] strArr = this.zza;
                int length = strArr == null ? 0 : strArr.length;
                String[] strArr2 = new String[(zza3 + length)];
                if (length != 0) {
                    System.arraycopy(this.zza, 0, strArr2, 0, length);
                }
                while (length < strArr2.length - 1) {
                    strArr2[length] = zzgry.zze();
                    zzgry.zza();
                    length++;
                }
                strArr2[length] = zzgry.zze();
                this.zza = strArr2;
            } else if (zza2 == 18) {
                int zza4 = zzgsk.zza(zzgry, 18);
                String[] strArr3 = this.zzb;
                int length2 = strArr3 == null ? 0 : strArr3.length;
                String[] strArr4 = new String[(zza4 + length2)];
                if (length2 != 0) {
                    System.arraycopy(this.zzb, 0, strArr4, 0, length2);
                }
                while (length2 < strArr4.length - 1) {
                    strArr4[length2] = zzgry.zze();
                    zzgry.zza();
                    length2++;
                }
                strArr4[length2] = zzgry.zze();
                this.zzb = strArr4;
            } else if (zza2 == 24) {
                int zza5 = zzgsk.zza(zzgry, 24);
                int[] iArr = this.zzc;
                int length3 = iArr == null ? 0 : iArr.length;
                int[] iArr2 = new int[(zza5 + length3)];
                if (length3 != 0) {
                    System.arraycopy(this.zzc, 0, iArr2, 0, length3);
                }
                while (length3 < iArr2.length - 1) {
                    iArr2[length3] = zzgry.zzc();
                    zzgry.zza();
                    length3++;
                }
                iArr2[length3] = zzgry.zzc();
                this.zzc = iArr2;
            } else if (zza2 == 26) {
                int zzc2 = zzgry.zzc(zzgry.zzh());
                int zzn = zzgry.zzn();
                int i = 0;
                while (zzgry.zzl() > 0) {
                    zzgry.zzc();
                    i++;
                }
                zzgry.zze(zzn);
                int[] iArr3 = this.zzc;
                int length4 = iArr3 == null ? 0 : iArr3.length;
                int[] iArr4 = new int[(i + length4)];
                if (length4 != 0) {
                    System.arraycopy(this.zzc, 0, iArr4, 0, length4);
                }
                while (length4 < iArr4.length) {
                    iArr4[length4] = zzgry.zzc();
                    length4++;
                }
                this.zzc = iArr4;
                zzgry.zzd(zzc2);
            } else if (zza2 == 32) {
                int zza6 = zzgsk.zza(zzgry, 32);
                long[] jArr = this.zzd;
                int length5 = jArr == null ? 0 : jArr.length;
                long[] jArr2 = new long[(zza6 + length5)];
                if (length5 != 0) {
                    System.arraycopy(this.zzd, 0, jArr2, 0, length5);
                }
                while (length5 < jArr2.length - 1) {
                    jArr2[length5] = zzgry.zzb();
                    zzgry.zza();
                    length5++;
                }
                jArr2[length5] = zzgry.zzb();
                this.zzd = jArr2;
            } else if (zza2 == 34) {
                int zzc3 = zzgry.zzc(zzgry.zzh());
                int zzn2 = zzgry.zzn();
                int i2 = 0;
                while (zzgry.zzl() > 0) {
                    zzgry.zzb();
                    i2++;
                }
                zzgry.zze(zzn2);
                long[] jArr3 = this.zzd;
                int length6 = jArr3 == null ? 0 : jArr3.length;
                long[] jArr4 = new long[(i2 + length6)];
                if (length6 != 0) {
                    System.arraycopy(this.zzd, 0, jArr4, 0, length6);
                }
                while (length6 < jArr4.length) {
                    jArr4[length6] = zzgry.zzb();
                    length6++;
                }
                this.zzd = jArr4;
                zzgry.zzd(zzc3);
            } else if (zza2 == 40) {
                int zza7 = zzgsk.zza(zzgry, 40);
                long[] jArr5 = this.zze;
                int length7 = jArr5 == null ? 0 : jArr5.length;
                long[] jArr6 = new long[(zza7 + length7)];
                if (length7 != 0) {
                    System.arraycopy(this.zze, 0, jArr6, 0, length7);
                }
                while (length7 < jArr6.length - 1) {
                    jArr6[length7] = zzgry.zzb();
                    zzgry.zza();
                    length7++;
                }
                jArr6[length7] = zzgry.zzb();
                this.zze = jArr6;
            } else if (zza2 == 42) {
                int zzc4 = zzgry.zzc(zzgry.zzh());
                int zzn3 = zzgry.zzn();
                int i3 = 0;
                while (zzgry.zzl() > 0) {
                    zzgry.zzb();
                    i3++;
                }
                zzgry.zze(zzn3);
                long[] jArr7 = this.zze;
                int length8 = jArr7 == null ? 0 : jArr7.length;
                long[] jArr8 = new long[(i3 + length8)];
                if (length8 != 0) {
                    System.arraycopy(this.zze, 0, jArr8, 0, length8);
                }
                while (length8 < jArr8.length) {
                    jArr8[length8] = zzgry.zzb();
                    length8++;
                }
                this.zze = jArr8;
                zzgry.zzd(zzc4);
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
