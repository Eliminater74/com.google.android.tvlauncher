package com.google.android.gms.internal;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;

/* compiled from: ArrayDecoders */
final class zzgmu {
    static int zza(byte[] bArr, int i, zzgmv zzgmv) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza(b, bArr, i2, zzgmv);
        }
        zzgmv.zza = b;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzgmv zzgmv) {
        int i3 = i & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzgmv.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & Ascii.DEL) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzgmv.zza = i5 | (b2 << Ascii.f157SO);
            return i6;
        }
        int i7 = i5 | ((b2 & Ascii.DEL) << Ascii.f157SO);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzgmv.zza = i7 | (b3 << Ascii.NAK);
            return i8;
        }
        int i9 = i7 | ((b3 & Ascii.DEL) << Ascii.NAK);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzgmv.zza = i9 | (b4 << Ascii.f150FS);
            return i10;
        }
        int i11 = i9 | ((b4 & Ascii.DEL) << Ascii.f150FS);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzgmv.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzb(byte[] bArr, int i, zzgmv zzgmv) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            zzgmv.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & Ascii.DEL)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & Ascii.DEL)) << i4;
            int i6 = i5;
            b = b2;
            i3 = i6;
        }
        zzgmv.zzb = j2;
        return i3;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    static long zzb(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzgmv zzgmv) {
        int zza = zza(bArr, i, zzgmv);
        int i2 = zzgmv.zza;
        if (i2 == 0) {
            zzgmv.zzc = "";
            return zza;
        }
        zzgmv.zzc = new String(bArr, zza, i2, zzgon.zza);
        return zza + i2;
    }

    static int zzd(byte[] bArr, int i, zzgmv zzgmv) throws IOException {
        int zza = zza(bArr, i, zzgmv);
        int i2 = zzgmv.zza;
        if (i2 == 0) {
            zzgmv.zzc = "";
            return zza;
        }
        int i3 = zza + i2;
        if (zzgrl.zza(bArr, zza, i3)) {
            zzgmv.zzc = new String(bArr, zza, i2, zzgon.zza);
            return i3;
        }
        throw zzgot.zzi();
    }

    static int zze(byte[] bArr, int i, zzgmv zzgmv) {
        int zza = zza(bArr, i, zzgmv);
        int i2 = zzgmv.zza;
        if (i2 == 0) {
            zzgmv.zzc = zzgnb.zza;
            return zza;
        }
        zzgmv.zzc = zzgnb.zza(bArr, zza, i2);
        return zza + i2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzgos<?> zzgos, zzgmv zzgmv) {
        zzgom zzgom = (zzgom) zzgos;
        int zza = zza(bArr, i2, zzgmv);
        zzgom.zzd(zzgmv.zza);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzgmv);
            if (i != zzgmv.zza) {
                break;
            }
            zza = zza(bArr, zza2, zzgmv);
            zzgom.zzd(zzgmv.zza);
        }
        return zza;
    }

    static int zza(byte[] bArr, int i, zzgos<?> zzgos, zzgmv zzgmv) throws IOException {
        zzgom zzgom = (zzgom) zzgos;
        int zza = zza(bArr, i, zzgmv);
        int i2 = zzgmv.zza + zza;
        while (zza < i2) {
            zza = zza(bArr, zza, zzgmv);
            zzgom.zzd(zzgmv.zza);
        }
        if (zza == i2) {
            return zza;
        }
        throw zzgot.zza();
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzgre zzgre, zzgmv zzgmv) throws IOException {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzb = zzb(bArr, i2, zzgmv);
                zzgre.zza(i, Long.valueOf(zzgmv.zzb));
                return zzb;
            } else if (i4 == 1) {
                zzgre.zza(i, Long.valueOf(zzb(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int zza = zza(bArr, i2, zzgmv);
                int i5 = zzgmv.zza;
                if (i5 == 0) {
                    zzgre.zza(i, zzgnb.zza);
                } else {
                    zzgre.zza(i, zzgnb.zza(bArr, zza, i5));
                }
                return zza + i5;
            } else if (i4 == 3) {
                zzgre zzb2 = zzgre.zzb();
                int i6 = (i & -8) | 4;
                int i7 = 0;
                while (true) {
                    if (i2 >= i3) {
                        break;
                    }
                    int zza2 = zza(bArr, i2, zzgmv);
                    int i8 = zzgmv.zza;
                    if (i8 == i6) {
                        i7 = i8;
                        i2 = zza2;
                        break;
                    }
                    i7 = i8;
                    i2 = zza(i8, bArr, zza2, i3, zzb2, zzgmv);
                }
                if (i2 > i3 || i7 != i6) {
                    throw zzgot.zzh();
                }
                zzgre.zza(i, zzb2);
                return i2;
            } else if (i4 == 5) {
                zzgre.zza(i, Integer.valueOf(zza(bArr, i2)));
                return i2 + 4;
            } else {
                throw zzgot.zzd();
            }
        } else {
            throw zzgot.zzd();
        }
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzgmv zzgmv) throws zzgot {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                return zzb(bArr, i2, zzgmv);
            }
            if (i4 == 1) {
                return i2 + 8;
            }
            if (i4 == 2) {
                return zza(bArr, i2, zzgmv) + zzgmv.zza;
            }
            if (i4 == 3) {
                int i5 = (i & -8) | 4;
                int i6 = 0;
                while (i2 < i3) {
                    i2 = zza(bArr, i2, zzgmv);
                    i6 = zzgmv.zza;
                    if (i6 == i5) {
                        break;
                    }
                    i2 = zza(i6, bArr, i2, i3, zzgmv);
                }
                if (i2 <= i3 && i6 == i5) {
                    return i2;
                }
                throw zzgot.zzh();
            } else if (i4 == 5) {
                return i2 + 4;
            } else {
                throw zzgot.zzd();
            }
        } else {
            throw zzgot.zzd();
        }
    }
}
