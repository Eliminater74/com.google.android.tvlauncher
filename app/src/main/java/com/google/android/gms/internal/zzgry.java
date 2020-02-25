package com.google.android.gms.internal;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

import java.io.IOException;

/* compiled from: CodedInputByteBufferNano */
public final class zzgry {
    private final byte[] zza;
    private final int zzb;
    private final int zzc;
    private int zzd;
    private int zze;
    private int zzf;
    private int zzg;
    private int zzh = Integer.MAX_VALUE;
    private int zzi;
    private int zzj = 64;
    private int zzk = 67108864;
    private zzgnk zzl;

    private zzgry(byte[] bArr, int i, int i2) {
        this.zza = bArr;
        this.zzb = i;
        int i3 = i2 + i;
        this.zzd = i3;
        this.zzc = i3;
        this.zzf = i;
    }

    public static zzgry zza(byte[] bArr) {
        return zza(bArr, 0, bArr.length);
    }

    public static zzgry zza(byte[] bArr, int i, int i2) {
        return new zzgry(bArr, i, i2);
    }

    public final int zza() throws IOException {
        if (zzm()) {
            this.zzg = 0;
            return 0;
        }
        this.zzg = zzh();
        int i = this.zzg;
        if (i != 0) {
            return i;
        }
        throw new zzgsg("Protocol message contained an invalid tag (zero).");
    }

    public final void zza(int i) throws zzgsg {
        if (this.zzg != i) {
            throw new zzgsg("Protocol message end-group tag did not match expected tag.");
        }
    }

    public final boolean zzb(int i) throws IOException {
        int zza2;
        int i2 = i & 7;
        if (i2 == 0) {
            zzh();
            return true;
        } else if (i2 == 1) {
            zzk();
            return true;
        } else if (i2 == 2) {
            zzg(zzh());
            return true;
        } else if (i2 == 3) {
            do {
                zza2 = zza();
                if (zza2 == 0) {
                    break;
                }
            } while (zzb(zza2));
            zza(((i >>> 3) << 3) | 4);
            return true;
        } else if (i2 == 4) {
            return false;
        } else {
            if (i2 == 5) {
                zzj();
                return true;
            }
            throw new zzgsg("Protocol message tag had invalid wire type.");
        }
    }

    public final long zzb() throws IOException {
        return zzi();
    }

    public final int zzc() throws IOException {
        return zzh();
    }

    public final boolean zzd() throws IOException {
        return zzh() != 0;
    }

    public final String zze() throws IOException {
        int zzh2 = zzh();
        if (zzh2 >= 0) {
            int i = this.zzd;
            int i2 = this.zzf;
            if (zzh2 <= i - i2) {
                String str = new String(this.zza, i2, zzh2, zzgsf.zza);
                this.zzf += zzh2;
                return str;
            }
            throw zzgsg.zza();
        }
        throw zzgsg.zzb();
    }

    public final void zza(zzgsh zzgsh, int i) throws IOException {
        int i2 = this.zzi;
        if (i2 < this.zzj) {
            this.zzi = i2 + 1;
            zzgsh.mergeFrom(this);
            zza((i << 3) | 4);
            this.zzi--;
            return;
        }
        throw zzgsg.zzd();
    }

    public final void zza(zzgsh zzgsh) throws IOException {
        int zzh2 = zzh();
        if (this.zzi < this.zzj) {
            int zzc2 = zzc(zzh2);
            this.zzi++;
            zzgsh.mergeFrom(this);
            zza(0);
            this.zzi--;
            zzd(zzc2);
            return;
        }
        throw zzgsg.zzd();
    }

    public final byte[] zzf() throws IOException {
        int zzh2 = zzh();
        if (zzh2 < 0) {
            throw zzgsg.zzb();
        } else if (zzh2 == 0) {
            return zzgsk.zzh;
        } else {
            int i = this.zzd;
            int i2 = this.zzf;
            if (zzh2 <= i - i2) {
                byte[] bArr = new byte[zzh2];
                System.arraycopy(this.zza, i2, bArr, 0, zzh2);
                this.zzf += zzh2;
                return bArr;
            }
            throw zzgsg.zza();
        }
    }

    public final long zzg() throws IOException {
        long zzi2 = zzi();
        return (-(zzi2 & 1)) ^ (zzi2 >>> 1);
    }

    public final int zzh() throws IOException {
        byte zzp = zzp();
        if (zzp >= 0) {
            return zzp;
        }
        byte b = zzp & Ascii.DEL;
        byte zzp2 = zzp();
        if (zzp2 >= 0) {
            return b | (zzp2 << 7);
        }
        byte b2 = b | ((zzp2 & Ascii.DEL) << 7);
        byte zzp3 = zzp();
        if (zzp3 >= 0) {
            return b2 | (zzp3 << Ascii.f157SO);
        }
        byte b3 = b2 | ((zzp3 & Ascii.DEL) << Ascii.f157SO);
        byte zzp4 = zzp();
        if (zzp4 >= 0) {
            return b3 | (zzp4 << Ascii.NAK);
        }
        byte b4 = b3 | ((zzp4 & Ascii.DEL) << Ascii.NAK);
        byte zzp5 = zzp();
        byte b5 = b4 | (zzp5 << Ascii.f150FS);
        if (zzp5 >= 0) {
            return b5;
        }
        for (int i = 0; i < 5; i++) {
            if (zzp() >= 0) {
                return b5;
            }
        }
        throw zzgsg.zzc();
    }

    public final long zzi() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzp = zzp();
            j |= ((long) (zzp & Ascii.DEL)) << i;
            if ((zzp & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                return j;
            }
        }
        throw zzgsg.zzc();
    }

    public final int zzj() throws IOException {
        return (zzp() & UnsignedBytes.MAX_VALUE) | ((zzp() & UnsignedBytes.MAX_VALUE) << 8) | ((zzp() & UnsignedBytes.MAX_VALUE) << Ascii.DLE) | ((zzp() & UnsignedBytes.MAX_VALUE) << Ascii.CAN);
    }

    public final long zzk() throws IOException {
        byte zzp = zzp();
        byte zzp2 = zzp();
        return ((((long) zzp2) & 255) << 8) | (((long) zzp) & 255) | ((((long) zzp()) & 255) << 16) | ((((long) zzp()) & 255) << 24) | ((((long) zzp()) & 255) << 32) | ((((long) zzp()) & 255) << 40) | ((((long) zzp()) & 255) << 48) | ((((long) zzp()) & 255) << 56);
    }

    public final <T extends zzgoj<T, ?>> T zza(zzgqe zzgqe) throws IOException {
        if (this.zzl == null) {
            this.zzl = zzgnk.zza(this.zza, this.zzb, this.zzc);
        }
        int zzu = this.zzl.zzu();
        int i = this.zzf - this.zzb;
        if (zzu <= i) {
            this.zzl.zzf(i - zzu);
            this.zzl.zzc(this.zzj - this.zzi);
            T t = (zzgoj) this.zzl.zza(zzgqe, zzgnv.zzb());
            zzb(this.zzg);
            return t;
        }
        throw new IOException(String.format("CodedInputStream read ahead of CodedInputByteBufferNano: %s > %s", Integer.valueOf(zzu), Integer.valueOf(i)));
    }

    public final int zzc(int i) throws zzgsg {
        if (i >= 0) {
            int i2 = i + this.zzf;
            int i3 = this.zzh;
            if (i2 <= i3) {
                this.zzh = i2;
                zzo();
                return i3;
            }
            throw zzgsg.zza();
        }
        throw zzgsg.zzb();
    }

    private final void zzo() {
        this.zzd += this.zze;
        int i = this.zzd;
        int i2 = this.zzh;
        if (i > i2) {
            this.zze = i - i2;
            this.zzd = i - this.zze;
            return;
        }
        this.zze = 0;
    }

    public final void zzd(int i) {
        this.zzh = i;
        zzo();
    }

    public final int zzl() {
        int i = this.zzh;
        if (i == Integer.MAX_VALUE) {
            return -1;
        }
        return i - this.zzf;
    }

    public final boolean zzm() {
        return this.zzf == this.zzd;
    }

    public final int zzn() {
        return this.zzf - this.zzb;
    }

    public final byte[] zza(int i, int i2) {
        if (i2 == 0) {
            return zzgsk.zzh;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(this.zza, this.zzb + i, bArr, 0, i2);
        return bArr;
    }

    public final void zze(int i) {
        zzb(i, this.zzg);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(int i, int i2) {
        int i3 = this.zzf;
        int i4 = this.zzb;
        if (i > i3 - i4) {
            StringBuilder sb = new StringBuilder(50);
            sb.append("Position ");
            sb.append(i);
            sb.append(" is beyond current ");
            sb.append(i3 - i4);
            throw new IllegalArgumentException(sb.toString());
        } else if (i >= 0) {
            this.zzf = i4 + i;
            this.zzg = i2;
        } else {
            StringBuilder sb2 = new StringBuilder(24);
            sb2.append("Bad position ");
            sb2.append(i);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    private final byte zzp() throws IOException {
        int i = this.zzf;
        if (i != this.zzd) {
            byte[] bArr = this.zza;
            this.zzf = i + 1;
            return bArr[i];
        }
        throw zzgsg.zza();
    }

    public final byte[] zzf(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzf;
            int i3 = i2 + i;
            int i4 = this.zzh;
            if (i3 > i4) {
                zzg(i4 - i2);
                throw zzgsg.zza();
            } else if (i <= this.zzd - i2) {
                byte[] bArr = new byte[i];
                System.arraycopy(this.zza, i2, bArr, 0, i);
                this.zzf += i;
                return bArr;
            } else {
                throw zzgsg.zza();
            }
        } else {
            throw zzgsg.zzb();
        }
    }

    private final void zzg(int i) throws IOException {
        if (i >= 0) {
            int i2 = this.zzf;
            int i3 = i2 + i;
            int i4 = this.zzh;
            if (i3 > i4) {
                zzg(i4 - i2);
                throw zzgsg.zza();
            } else if (i <= this.zzd - i2) {
                this.zzf = i2 + i;
            } else {
                throw zzgsg.zza();
            }
        } else {
            throw zzgsg.zzb();
        }
    }
}
