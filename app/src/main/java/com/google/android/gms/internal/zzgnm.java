package com.google.android.gms.internal;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

import java.io.IOException;
import java.util.Arrays;

/* compiled from: CodedInputStream */
final class zzgnm extends zzgnk {
    private final byte[] zzd;
    private final boolean zze;
    private int zzf;
    private int zzg;
    private int zzh;
    private int zzi;
    private int zzj;
    private int zzk;

    private zzgnm(byte[] bArr, int i, int i2, boolean z) {
        super();
        this.zzk = Integer.MAX_VALUE;
        this.zzd = bArr;
        this.zzf = i2 + i;
        this.zzh = i;
        this.zzi = this.zzh;
        this.zze = z;
    }

    public final int zza() throws IOException {
        if (zzt()) {
            this.zzj = 0;
            return 0;
        }
        this.zzj = zzv();
        int i = this.zzj;
        if ((i >>> 3) != 0) {
            return i;
        }
        throw zzgot.zzd();
    }

    public final void zza(int i) throws zzgot {
        if (this.zzj != i) {
            throw zzgot.zze();
        }
    }

    public final boolean zzb(int i) throws IOException {
        int zza;
        int i2 = i & 7;
        int i3 = 0;
        if (i2 == 0) {
            if (this.zzf - this.zzh >= 10) {
                while (i3 < 10) {
                    byte[] bArr = this.zzd;
                    int i4 = this.zzh;
                    this.zzh = i4 + 1;
                    if (bArr[i4] < 0) {
                        i3++;
                    }
                }
                throw zzgot.zzc();
            }
            while (i3 < 10) {
                if (zzaa() < 0) {
                    i3++;
                }
            }
            throw zzgot.zzc();
            return true;
        } else if (i2 == 1) {
            zzf(8);
            return true;
        } else if (i2 == 2) {
            zzf(zzv());
            return true;
        } else if (i2 == 3) {
            do {
                zza = zza();
                if (zza == 0) {
                    break;
                }
            } while (zzb(zza));
            zza(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzf(4);
                return true;
            }
            throw zzgot.zzf();
        }
    }

    public final double zzb() throws IOException {
        return Double.longBitsToDouble(zzy());
    }

    public final float zzc() throws IOException {
        return Float.intBitsToFloat(zzx());
    }

    public final long zzd() throws IOException {
        return zzw();
    }

    public final long zze() throws IOException {
        return zzw();
    }

    public final int zzf() throws IOException {
        return zzv();
    }

    public final long zzg() throws IOException {
        return zzy();
    }

    public final int zzh() throws IOException {
        return zzx();
    }

    public final boolean zzi() throws IOException {
        return zzw() != 0;
    }

    public final String zzj() throws IOException {
        int zzv = zzv();
        if (zzv > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (zzv <= i - i2) {
                String str = new String(this.zzd, i2, zzv, zzgon.zza);
                this.zzh += zzv;
                return str;
            }
        }
        if (zzv == 0) {
            return "";
        }
        if (zzv < 0) {
            throw zzgot.zzb();
        }
        throw zzgot.zza();
    }

    public final String zzk() throws IOException {
        int zzv = zzv();
        if (zzv > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (zzv <= i - i2) {
                if (zzgrl.zza(this.zzd, i2, i2 + zzv)) {
                    int i3 = this.zzh;
                    this.zzh = i3 + zzv;
                    return new String(this.zzd, i3, zzv, zzgon.zza);
                }
                throw zzgot.zzi();
            }
        }
        if (zzv == 0) {
            return "";
        }
        if (zzv <= 0) {
            throw zzgot.zzb();
        }
        throw zzgot.zza();
    }

    public final <T extends zzgpt> T zza(zzgqe<T> zzgqe, zzgnv zzgnv) throws IOException {
        int zzv = zzv();
        if (this.zza < this.zzb) {
            int zzd2 = zzd(zzv);
            this.zza++;
            T t = (zzgpt) zzgqe.zza(this, zzgnv);
            zza(0);
            this.zza--;
            zze(zzd2);
            return t;
        }
        throw zzgot.zzg();
    }

    public final zzgnb zzl() throws IOException {
        byte[] bArr;
        int zzv = zzv();
        if (zzv > 0) {
            int i = this.zzf;
            int i2 = this.zzh;
            if (zzv <= i - i2) {
                zzgnb zza = zzgnb.zza(this.zzd, i2, zzv);
                this.zzh += zzv;
                return zza;
            }
        }
        if (zzv == 0) {
            return zzgnb.zza;
        }
        if (zzv > 0) {
            int i3 = this.zzf;
            int i4 = this.zzh;
            if (zzv <= i3 - i4) {
                this.zzh = zzv + i4;
                bArr = Arrays.copyOfRange(this.zzd, i4, this.zzh);
                return zzgnb.zzb(bArr);
            }
        }
        if (zzv > 0) {
            throw zzgot.zza();
        } else if (zzv == 0) {
            bArr = zzgon.zzb;
            return zzgnb.zzb(bArr);
        } else {
            throw zzgot.zzb();
        }
    }

    public final int zzm() throws IOException {
        return zzv();
    }

    public final int zzn() throws IOException {
        return zzv();
    }

    public final int zzo() throws IOException {
        return zzx();
    }

    public final long zzp() throws IOException {
        return zzy();
    }

    public final int zzq() throws IOException {
        return zzg(zzv());
    }

    public final long zzr() throws IOException {
        return zza(zzw());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        if (r2[r3] >= 0) goto L_0x006a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zzv() throws java.io.IOException {
        /*
            r5 = this;
            int r0 = r5.zzh
            int r1 = r5.zzf
            if (r1 == r0) goto L_0x006d
            byte[] r2 = r5.zzd
            int r3 = r0 + 1
            byte r0 = r2[r0]
            if (r0 < 0) goto L_0x0011
            r5.zzh = r3
            return r0
        L_0x0011:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L_0x006d
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0022
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
            goto L_0x006a
        L_0x0022:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x002f
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            r1 = r3
            goto L_0x006a
        L_0x002f:
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x003d
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            goto L_0x006a
        L_0x003d:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r4 = r1 << 28
            r0 = r0 ^ r4
            r4 = 266354560(0xfe03f80, float:2.2112565E-29)
            r0 = r0 ^ r4
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x006a
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r3 = r2[r3]
            if (r3 >= 0) goto L_0x006a
            int r3 = r1 + 1
            byte r1 = r2[r1]
            if (r1 >= 0) goto L_0x0069
            int r1 = r3 + 1
            byte r2 = r2[r3]
            if (r2 < 0) goto L_0x006d
            goto L_0x006a
        L_0x0069:
            r1 = r3
        L_0x006a:
            r5.zzh = r1
            return r0
        L_0x006d:
            long r0 = r5.zzs()
            int r1 = (int) r0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgnm.zzv():int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00b7, code lost:
        if (((long) r2[r0]) >= 0) goto L_0x00bb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final long zzw() throws java.io.IOException {
        /*
            r11 = this;
            int r0 = r11.zzh
            int r1 = r11.zzf
            if (r1 == r0) goto L_0x00be
            byte[] r2 = r11.zzd
            int r3 = r0 + 1
            byte r0 = r2[r0]
            if (r0 < 0) goto L_0x0012
            r11.zzh = r3
            long r0 = (long) r0
            return r0
        L_0x0012:
            int r1 = r1 - r3
            r4 = 9
            if (r1 < r4) goto L_0x00be
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 7
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0026
            r0 = r0 ^ -128(0xffffffffffffff80, float:NaN)
            long r2 = (long) r0
            r3 = r2
            goto L_0x00bb
        L_0x0026:
            int r3 = r1 + 1
            byte r1 = r2[r1]
            int r1 = r1 << 14
            r0 = r0 ^ r1
            if (r0 < 0) goto L_0x0037
            r0 = r0 ^ 16256(0x3f80, float:2.278E-41)
            long r0 = (long) r0
            r9 = r0
            r1 = r3
            r3 = r9
            goto L_0x00bb
        L_0x0037:
            int r1 = r3 + 1
            byte r3 = r2[r3]
            int r3 = r3 << 21
            r0 = r0 ^ r3
            if (r0 >= 0) goto L_0x0048
            r2 = -2080896(0xffffffffffe03f80, float:NaN)
            r0 = r0 ^ r2
            long r2 = (long) r0
            r3 = r2
            goto L_0x00bb
        L_0x0048:
            long r3 = (long) r0
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r5 = (long) r1
            r1 = 28
            long r5 = r5 << r1
            long r3 = r3 ^ r5
            r5 = 0
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L_0x005f
            r1 = 266354560(0xfe03f80, double:1.315966377E-315)
            long r1 = r1 ^ r3
            r3 = r1
            r1 = r0
            goto L_0x00bb
        L_0x005f:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r7 = (long) r0
            r0 = 35
            long r7 = r7 << r0
            long r3 = r3 ^ r7
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x0073
            r5 = -34093383808(0xfffffff80fe03f80, double:NaN)
            long r3 = r3 ^ r5
            goto L_0x00bb
        L_0x0073:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r7 = (long) r1
            r1 = 42
            long r7 = r7 << r1
            long r3 = r3 ^ r7
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 < 0) goto L_0x0089
            r1 = 4363953127296(0x3f80fe03f80, double:2.1560793202584E-311)
            long r1 = r1 ^ r3
            r3 = r1
            r1 = r0
            goto L_0x00bb
        L_0x0089:
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r7 = (long) r0
            r0 = 49
            long r7 = r7 << r0
            long r3 = r3 ^ r7
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 >= 0) goto L_0x009d
            r5 = -558586000294016(0xfffe03f80fe03f80, double:NaN)
            long r3 = r3 ^ r5
            goto L_0x00bb
        L_0x009d:
            int r0 = r1 + 1
            byte r1 = r2[r1]
            long r7 = (long) r1
            r1 = 56
            long r7 = r7 << r1
            long r3 = r3 ^ r7
            r7 = 71499008037633920(0xfe03f80fe03f80, double:6.838959413692434E-304)
            long r3 = r3 ^ r7
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 >= 0) goto L_0x00ba
            int r1 = r0 + 1
            byte r0 = r2[r0]
            long r7 = (long) r0
            int r0 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r0 < 0) goto L_0x00be
            goto L_0x00bb
        L_0x00ba:
            r1 = r0
        L_0x00bb:
            r11.zzh = r1
            return r3
        L_0x00be:
            long r0 = r11.zzs()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgnm.zzw():long");
    }

    /* access modifiers changed from: package-private */
    public final long zzs() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzaa = zzaa();
            j |= ((long) (zzaa & Ascii.DEL)) << i;
            if ((zzaa & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                return j;
            }
        }
        throw zzgot.zzc();
    }

    private final int zzx() throws IOException {
        int i = this.zzh;
        if (this.zzf - i >= 4) {
            byte[] bArr = this.zzd;
            this.zzh = i + 4;
            return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
        }
        throw zzgot.zza();
    }

    private final long zzy() throws IOException {
        int i = this.zzh;
        if (this.zzf - i >= 8) {
            byte[] bArr = this.zzd;
            this.zzh = i + 8;
            return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
        }
        throw zzgot.zza();
    }

    public final int zzd(int i) throws zzgot {
        if (i >= 0) {
            int zzu = i + zzu();
            int i2 = this.zzk;
            if (zzu <= i2) {
                this.zzk = zzu;
                zzz();
                return i2;
            }
            throw zzgot.zza();
        }
        throw zzgot.zzb();
    }

    private final void zzz() {
        this.zzf += this.zzg;
        int i = this.zzf;
        int i2 = i - this.zzi;
        int i3 = this.zzk;
        if (i2 > i3) {
            this.zzg = i2 - i3;
            this.zzf = i - this.zzg;
            return;
        }
        this.zzg = 0;
    }

    public final void zze(int i) {
        this.zzk = i;
        zzz();
    }

    public final boolean zzt() throws IOException {
        return this.zzh == this.zzf;
    }

    public final int zzu() {
        return this.zzh - this.zzi;
    }

    private final byte zzaa() throws IOException {
        int i = this.zzh;
        if (i != this.zzf) {
            byte[] bArr = this.zzd;
            this.zzh = i + 1;
            return bArr[i];
        }
        throw zzgot.zza();
    }

    public final void zzf(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzf;
            int i3 = this.zzh;
            if (i <= i2 - i3) {
                this.zzh = i3 + i;
                return;
            }
        }
        if (i < 0) {
            throw zzgot.zzb();
        }
        throw zzgot.zza();
    }
}
