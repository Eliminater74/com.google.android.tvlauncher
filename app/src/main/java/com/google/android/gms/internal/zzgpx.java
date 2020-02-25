package com.google.android.gms.internal;

import com.google.android.exoplayer2.C0841C;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import sun.misc.Unsafe;

/* compiled from: MessageSchema */
final class zzgpx<T> implements zzgql<T> {
    private static final Unsafe zza = zzgrj.zzc();
    private final int[] zzb;
    private final int zzc;
    private final int zzd;
    private final zzgol<Class<?>> zze;
    private final zzgol<zzgop<?>> zzf;
    private final zzgqb zzg;
    private final zzgpd zzh;
    private final zzgpt zzi;
    private final zzgrd<?, ?> zzj;
    private final boolean zzk;
    private final zzgnw<?> zzl;
    private final boolean zzm;
    private final boolean zzn;
    private final zzgpo zzo;
    private final zzgol<Object> zzp;
    private final int[] zzq;
    private final int[] zzr;
    private final int[] zzs;
    private final int zzt;
    private final Object[] zzu;

    private zzgpx(int[] iArr, int i, int i2, boolean z, Class<T> cls, zzgol<Class<?>> zzgol, Object[] objArr, zzgol<zzgop<?>> zzgol2, zzgqb zzgqb, zzgpd zzgpd, zzgrd<?, ?> zzgrd, zzgnw<?> zzgnw, zzgpo zzgpo, zzgol<Object> zzgol3, int[] iArr2, zzgpt zzgpt, int[] iArr3, int[] iArr4, int i3) {
        zzgnw<?> zzgnw2 = zzgnw;
        this.zzb = iArr;
        this.zzc = i;
        this.zzd = i2;
        this.zze = zzgol;
        this.zzu = objArr;
        this.zzf = zzgol2;
        this.zzg = zzgqb;
        this.zzh = zzgpd;
        this.zzj = zzgrd;
        this.zzk = zzgnw2 != null && zzgnw2.zza(cls);
        this.zzl = zzgnw2;
        this.zzm = zzgoj.class.isAssignableFrom(cls);
        this.zzn = z;
        this.zzi = zzgpt;
        this.zzo = zzgpo;
        this.zzp = zzgol3;
        this.zzq = iArr2;
        this.zzr = iArr3;
        this.zzs = iArr4;
        this.zzt = i3;
    }

    static <T> zzgpx<T> zza(Class cls, zzgpr zzgpr, zzgqb zzgqb, zzgpd zzgpd, zzgrd<?, ?> zzgrd, zzgnw<?> zzgnw, zzgpo zzgpo) {
        int i;
        int i2;
        int i3;
        int[] iArr;
        Object[] objArr;
        int i4;
        int i5;
        int i6;
        zzgpr zzgpr2 = zzgpr;
        if (zzgpr2 instanceof zzgqh) {
            zzgqh zzgqh = (zzgqh) zzgpr2;
            boolean z = zzgqh.zza() == zzgoj.zzg.zzj;
            if (zzgqh.zzg() == 0) {
                i3 = 0;
                i2 = 0;
                i = 0;
            } else {
                int zze2 = zzgqh.zze();
                int zzf2 = zzgqh.zzf();
                i3 = zzgqh.zzk();
                i2 = zze2;
                i = zzf2;
            }
            int[] iArr2 = new int[(i3 << 2)];
            int[] iArr3 = zzgqh.zzh() > 0 ? new int[zzgqh.zzh()] : null;
            if (zzgqh.zzi() > 0) {
                iArr = new int[zzgqh.zzi()];
            } else {
                iArr = null;
            }
            zzgqi zzd2 = zzgqh.zzd();
            if (zzd2.zza()) {
                int zzb2 = zzd2.zzb();
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                while (true) {
                    if (zzb2 >= zzgqh.zzl() || i7 >= ((zzb2 - i2) << 2)) {
                        if (zzd2.zzd()) {
                            i6 = (int) zzgrj.zza(zzd2.zze());
                            i4 = (int) zzgrj.zza(zzd2.zzf());
                            i5 = 0;
                        } else {
                            i6 = (int) zzgrj.zza(zzd2.zzg());
                            if (zzd2.zzh()) {
                                i4 = (int) zzgrj.zza(zzd2.zzi());
                                i5 = zzd2.zzj();
                            } else {
                                i5 = 0;
                                i4 = 0;
                            }
                        }
                        iArr2[i7] = zzd2.zzb();
                        int i10 = i7 + 1;
                        iArr2[i10] = (zzd2.zzl() ? 536870912 : 0) | (zzd2.zzk() ? C0841C.ENCODING_PCM_MU_LAW : 0) | (zzd2.zzc() << 20) | i6;
                        iArr2[i7 + 2] = (i5 << 20) | i4;
                        int zzc2 = zzd2.zzc();
                        if (zzc2 == zzgod.MAP.ordinal()) {
                            iArr3[i8] = i7;
                            i8++;
                        } else if (zzc2 >= 18 && zzc2 <= 49) {
                            iArr[i9] = iArr2[i10] & 1048575;
                            i9++;
                        }
                        if (!zzd2.zza()) {
                            break;
                        }
                        zzb2 = zzd2.zzb();
                    } else {
                        for (int i11 = 0; i11 < 4; i11++) {
                            iArr2[i7 + i11] = -1;
                        }
                    }
                    i7 += 4;
                }
            }
            if (!zzd2.zzm().zzb()) {
                objArr = new Object[i3];
            } else {
                objArr = null;
            }
            return new zzgpx(iArr2, i2, i, z, cls, zzd2.zzm(), objArr, zzd2.zzn(), zzgqb, zzgpd, zzgrd, zzgnw, zzgpo, zzd2.zzo(), zzgqh.zzj(), zzgqh.zzc(), iArr3, iArr, zzgqh.zzl());
        }
        ((zzgqy) zzgpr2).zza();
        throw new NoSuchMethodError();
    }

    private static <E> List<E> zza(Object obj, long j) {
        return (List) zzgrj.zzf(obj, j);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
     arg types: [UT, com.google.android.gms.internal.zzgrx]
     candidates:
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgqk):boolean
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void */
    private static <UT, UB> void zza(zzgrd<UT, UB> zzgrd, T t, zzgrx zzgrx) {
        zzgrd.zza((Object) zzgrd.zzb(t), zzgrx);
    }

    private static zzgre zzd(Object obj) {
        zzgoj zzgoj = (zzgoj) obj;
        zzgre zzgre = zzgoj.zzb;
        if (zzgre != zzgre.zza()) {
            return zzgre;
        }
        zzgre zzb2 = zzgre.zzb();
        zzgoj.zzb = zzb2;
        return zzb2;
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Type inference failed for: r0v2, types: [int] */
    /* JADX WARN: Type inference failed for: r8v4, types: [int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zza(com.google.android.gms.internal.zzgql r6, byte[] r7, int r8, int r9, com.google.android.gms.internal.zzgmv r10) throws java.io.IOException {
        /*
            int r0 = r8 + 1
            byte r8 = r7[r8]
            if (r8 >= 0) goto L_0x000e
            int r0 = com.google.android.gms.internal.zzgmu.zza(r8, r7, r0, r10)
            int r8 = r10.zza
            r3 = r0
            goto L_0x000f
        L_0x000e:
            r3 = r0
        L_0x000f:
            if (r8 < 0) goto L_0x0027
            int r9 = r9 - r3
            if (r8 > r9) goto L_0x0027
            java.lang.Object r9 = r6.zza()
            int r8 = r8 + r3
            r0 = r6
            r1 = r9
            r2 = r7
            r4 = r8
            r5 = r10
            r0.zza(r1, r2, r3, r4, r5)
            r6.zzc(r9)
            r10.zzc = r9
            return r8
        L_0x0027:
            com.google.android.gms.internal.zzgot r6 = com.google.android.gms.internal.zzgot.zza()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgpx.zza(com.google.android.gms.internal.zzgql, byte[], int, int, com.google.android.gms.internal.zzgmv):int");
    }

    private static int zza(zzgql zzgql, byte[] bArr, int i, int i2, int i3, zzgmv zzgmv) throws IOException {
        zzgpx zzgpx = (zzgpx) zzgql;
        Object zza2 = zzgpx.zza();
        int zza3 = zzgpx.zza(zza2, bArr, i, i2, i3, zzgmv);
        zzgpx.zzc(zza2);
        zzgmv.zzc = zza2;
        return zza3;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(com.google.android.gms.internal.zzgql, byte[], int, int, com.google.android.gms.internal.zzgmv):int
     arg types: [com.google.android.gms.internal.zzgql<?>, byte[], int, int, com.google.android.gms.internal.zzgmv]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, java.util.Map, com.google.android.gms.internal.zzgop<?>, java.lang.Object, com.google.android.gms.internal.zzgrd):UB
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.zzgmv):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.zzgmv):void
      com.google.android.gms.internal.zzgpx.zza(com.google.android.gms.internal.zzgql, byte[], int, int, com.google.android.gms.internal.zzgmv):int */
    private static int zza(zzgql<?> zzgql, int i, byte[] bArr, int i2, int i3, zzgos<?> zzgos, zzgmv zzgmv) throws IOException {
        int zza2 = zza((zzgql) zzgql, bArr, i2, i3, zzgmv);
        zzgos.add(zzgmv.zzc);
        while (zza2 < i3) {
            int zza3 = zzgmu.zza(bArr, zza2, zzgmv);
            if (i != zzgmv.zza) {
                break;
            }
            zza2 = zza((zzgql) zzgql, bArr, zza3, i3, zzgmv);
            zzgos.add(zzgmv.zzc);
        }
        return zza2;
    }

    private static int zza(byte[] bArr, int i, int i2, zzgrr zzgrr, Class<?> cls, zzgmv zzgmv) throws IOException {
        switch (zzgpy.zza[zzgrr.ordinal()]) {
            case 1:
                int zzb2 = zzgmu.zzb(bArr, i, zzgmv);
                zzgmv.zzc = Boolean.valueOf(zzgmv.zzb != 0);
                return zzb2;
            case 2:
                return zzgmu.zze(bArr, i, zzgmv);
            case 3:
                zzgmv.zzc = Double.valueOf(zzgmu.zzc(bArr, i));
                return i + 8;
            case 4:
            case 5:
                zzgmv.zzc = Integer.valueOf(zzgmu.zza(bArr, i));
                return i + 4;
            case 6:
            case 7:
                zzgmv.zzc = Long.valueOf(zzgmu.zzb(bArr, i));
                return i + 8;
            case 8:
                zzgmv.zzc = Float.valueOf(zzgmu.zzd(bArr, i));
                return i + 4;
            case 9:
            case 10:
            case 11:
                int zza2 = zzgmu.zza(bArr, i, zzgmv);
                zzgmv.zzc = Integer.valueOf(zzgmv.zza);
                return zza2;
            case 12:
            case 13:
                int zzb3 = zzgmu.zzb(bArr, i, zzgmv);
                zzgmv.zzc = Long.valueOf(zzgmv.zzb);
                return zzb3;
            case 14:
                return zza(zzgqf.zza().zza((Class) cls), bArr, i, i2, zzgmv);
            case 15:
                int zza3 = zzgmu.zza(bArr, i, zzgmv);
                zzgmv.zzc = Integer.valueOf(zzgnk.zzg(zzgmv.zza));
                return zza3;
            case 16:
                int zzb4 = zzgmu.zzb(bArr, i, zzgmv);
                zzgmv.zzc = Long.valueOf(zzgnk.zza(zzgmv.zzb));
                return zzb4;
            case 17:
                return zzgmu.zzd(bArr, i, zzgmv);
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static int zza(int i, byte[] bArr, int i2, int i3, Object obj, zzgmv zzgmv) throws IOException {
        return zzgmu.zza(i, bArr, i2, i3, zzd(obj), zzgmv);
    }

    private static void zza(int i, Object obj, zzgrx zzgrx) {
        if (obj instanceof String) {
            zzgrx.zza(i, (String) obj);
        } else {
            zzgrx.zza(i, (zzgnb) obj);
        }
    }

    private static boolean zzc(int i) {
        return (i & 536870912) != 0;
    }

    private static <T> double zzb(T t, long j) {
        return ((Double) zzgrj.zzf(t, j)).doubleValue();
    }

    private static <T> float zzc(T t, long j) {
        return ((Float) zzgrj.zzf(t, j)).floatValue();
    }

    private static <T> int zzd(T t, long j) {
        return ((Integer) zzgrj.zzf(t, j)).intValue();
    }

    private static <T> long zze(T t, long j) {
        return ((Long) zzgrj.zzf(t, j)).longValue();
    }

    private static <T> boolean zzf(T t, long j) {
        return ((Boolean) zzgrj.zzf(t, j)).booleanValue();
    }

    public final T zza() {
        return this.zzg.zza(this.zzi);
    }

    public final boolean zza(T t, T t2) {
        int length = this.zzb.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i < length) {
                int zza2 = zza(i);
                long j = (long) (zza2 & 1048575);
                switch ((zza2 & 267386880) >>> 20) {
                    case 0:
                        if (!zzc(t, t2, i) || zzgrj.zzb(t, j) != zzgrj.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 1:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 2:
                        if (!zzc(t, t2, i) || zzgrj.zzb(t, j) != zzgrj.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 3:
                        if (!zzc(t, t2, i) || zzgrj.zzb(t, j) != zzgrj.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 4:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 5:
                        if (!zzc(t, t2, i) || zzgrj.zzb(t, j) != zzgrj.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 6:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 7:
                        if (!zzc(t, t2, i) || zzgrj.zzc(t, j) != zzgrj.zzc(t2, j)) {
                            z = false;
                            break;
                        }
                    case 8:
                        if (!zzc(t, t2, i) || !zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 9:
                        if (!zzc(t, t2, i) || !zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 10:
                        if (!zzc(t, t2, i) || !zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 11:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 12:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 13:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 14:
                        if (!zzc(t, t2, i) || zzgrj.zzb(t, j) != zzgrj.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 15:
                        if (!zzc(t, t2, i) || zzgrj.zza(t, j) != zzgrj.zza(t2, j)) {
                            z = false;
                            break;
                        }
                    case 16:
                        if (!zzc(t, t2, i) || zzgrj.zzb(t, j) != zzgrj.zzb(t2, j)) {
                            z = false;
                            break;
                        }
                    case 17:
                        if (!zzc(t, t2, i) || !zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        z = zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j));
                        break;
                    case 50:
                        z = zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j));
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                    case 60:
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                    case 68:
                        long zzb2 = (long) (zzb(i) & 1048575);
                        if (zzgrj.zza(t, zzb2) != zzgrj.zza(t2, zzb2) || !zzgqn.zza(zzgrj.zzf(t, j), zzgrj.zzf(t2, j))) {
                            z = false;
                            break;
                        }
                }
                if (!z) {
                    return false;
                }
                i += 4;
            } else if (!this.zzj.zzb(t).equals(this.zzj.zzb(t2))) {
                return false;
            } else {
                if (this.zzk) {
                    return this.zzl.zza((Object) t).equals(this.zzl.zza((Object) t2));
                }
                return true;
            }
        }
    }

    public final int zza(T t) {
        int length = this.zzb.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2 += 4) {
            int zza2 = zza(i2);
            int i3 = this.zzb[i2];
            long j = (long) (1048575 & zza2);
            int i4 = 37;
            switch ((zza2 & 267386880) >>> 20) {
                case 0:
                    i = (i * 53) + zzgon.zza(Double.doubleToLongBits(zzgrj.zze(t, j)));
                    break;
                case 1:
                    i = (i * 53) + Float.floatToIntBits(zzgrj.zzd(t, j));
                    break;
                case 2:
                    i = (i * 53) + zzgon.zza(zzgrj.zzb(t, j));
                    break;
                case 3:
                    i = (i * 53) + zzgon.zza(zzgrj.zzb(t, j));
                    break;
                case 4:
                    i = (i * 53) + zzgrj.zza(t, j);
                    break;
                case 5:
                    i = (i * 53) + zzgon.zza(zzgrj.zzb(t, j));
                    break;
                case 6:
                    i = (i * 53) + zzgrj.zza(t, j);
                    break;
                case 7:
                    i = (i * 53) + zzgon.zza(zzgrj.zzc(t, j));
                    break;
                case 8:
                    i = (i * 53) + ((String) zzgrj.zzf(t, j)).hashCode();
                    break;
                case 9:
                    Object zzf2 = zzgrj.zzf(t, j);
                    if (zzf2 != null) {
                        i4 = zzf2.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 10:
                    i = (i * 53) + zzgrj.zzf(t, j).hashCode();
                    break;
                case 11:
                    i = (i * 53) + zzgrj.zza(t, j);
                    break;
                case 12:
                    i = (i * 53) + zzgrj.zza(t, j);
                    break;
                case 13:
                    i = (i * 53) + zzgrj.zza(t, j);
                    break;
                case 14:
                    i = (i * 53) + zzgon.zza(zzgrj.zzb(t, j));
                    break;
                case 15:
                    i = (i * 53) + zzgrj.zza(t, j);
                    break;
                case 16:
                    i = (i * 53) + zzgon.zza(zzgrj.zzb(t, j));
                    break;
                case 17:
                    Object zzf3 = zzgrj.zzf(t, j);
                    if (zzf3 != null) {
                        i4 = zzf3.hashCode();
                    }
                    i = (i * 53) + i4;
                    break;
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                case 28:
                case 29:
                case 30:
                case 31:
                case 32:
                case 33:
                case 34:
                case 35:
                case 36:
                case 37:
                case 38:
                case 39:
                case 40:
                case 41:
                case 42:
                case 43:
                case 44:
                case 45:
                case 46:
                case 47:
                case 48:
                case 49:
                    i = (i * 53) + zzgrj.zzf(t, j).hashCode();
                    break;
                case 50:
                    i = (i * 53) + zzgrj.zzf(t, j).hashCode();
                    break;
                case 51:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(Double.doubleToLongBits(zzb(t, j)));
                        break;
                    }
                case 52:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + Float.floatToIntBits(zzc(t, j));
                        break;
                    }
                case 53:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(zze(t, j));
                        break;
                    }
                case 54:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(zze(t, j));
                        break;
                    }
                case 55:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 56:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(zze(t, j));
                        break;
                    }
                case 57:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 58:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(zzf(t, j));
                        break;
                    }
                case 59:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + ((String) zzgrj.zzf(t, j)).hashCode();
                        break;
                    }
                case 60:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgrj.zzf(t, j).hashCode();
                        break;
                    }
                case 61:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgrj.zzf(t, j).hashCode();
                        break;
                    }
                case 62:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 63:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 64:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 65:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(zze(t, j));
                        break;
                    }
                case 66:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzd(t, j);
                        break;
                    }
                case 67:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgon.zza(zze(t, j));
                        break;
                    }
                case 68:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        i = (i * 53) + zzgrj.zzf(t, j).hashCode();
                        break;
                    }
            }
        }
        int hashCode = (i * 53) + this.zzj.zzb(t).hashCode();
        if (this.zzk) {
            return (hashCode * 53) + this.zzl.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, int):com.google.android.gms.internal.zzgql
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, long):java.util.List<E>
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
     arg types: [T, long, long]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, int):void
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, long):double
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgql.zzb(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, int):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
     arg types: [T, long, int]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
     arg types: [T, long, boolean]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
     arg types: [T, long, float]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void */
    public final void zzb(T t, T t2) {
        if (t2 != null) {
            for (int i = 0; i < this.zzb.length; i += 4) {
                int zza2 = zza(i);
                long j = (long) (1048575 & zza2);
                int i2 = this.zzb[i];
                switch ((zza2 & 267386880) >>> 20) {
                    case 0:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza(t, j, zzgrj.zze(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 1:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzd(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 2:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzb(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 3:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzb(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 4:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zza(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 5:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzb(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 6:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zza(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 7:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzc(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 8:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza(t, j, zzgrj.zzf(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 9:
                        zza(t, t2, i);
                        break;
                    case 10:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza(t, j, zzgrj.zzf(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 11:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zza(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 12:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zza(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 13:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zza(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 14:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzb(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 15:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zza(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 16:
                        if (!zza((Object) t2, i)) {
                            break;
                        } else {
                            zzgrj.zza((Object) t, j, zzgrj.zzb(t2, j));
                            zzb((Object) t, i);
                            break;
                        }
                    case 17:
                        zza(t, t2, i);
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    case 25:
                    case 26:
                    case 27:
                    case 28:
                    case 29:
                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                    case 35:
                    case 36:
                    case 37:
                    case 38:
                    case 39:
                    case 40:
                    case 41:
                    case 42:
                    case 43:
                    case 44:
                    case 45:
                    case 46:
                    case 47:
                    case 48:
                    case 49:
                        this.zzh.zza(t, t2, j);
                        break;
                    case 50:
                        zzgqn.zza(this.zzo, t, t2, j);
                        break;
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57:
                    case 58:
                    case 59:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzgrj.zza(t, j, zzgrj.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 60:
                        zzb(t, t2, i);
                        break;
                    case 61:
                    case 62:
                    case 63:
                    case 64:
                    case 65:
                    case 66:
                    case 67:
                        if (!zza(t2, i2, i)) {
                            break;
                        } else {
                            zzgrj.zza(t, j, zzgrj.zzf(t2, j));
                            zzb(t, i2, i);
                            break;
                        }
                    case 68:
                        zzb(t, t2, i);
                        break;
                }
            }
            if (!this.zzn) {
                zzgqn.zza(this.zzj, t, t2);
                if (this.zzk) {
                    zzgqn.zza(this.zzl, t, t2);
                    return;
                }
                return;
            }
            return;
        }
        throw new NullPointerException();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, int):com.google.android.gms.internal.zzgql
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, long):java.util.List<E>
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, int):void
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, long):double
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgql.zzb(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, int):void */
    private final void zza(T t, T t2, int i) {
        long zza2 = (long) (zza(i) & 1048575);
        if (zza((Object) t2, i)) {
            Object zzf2 = zzgrj.zzf(t, zza2);
            Object zzf3 = zzgrj.zzf(t2, zza2);
            if (zzf2 != null && zzf3 != null) {
                zzgrj.zza(t, zza2, zzgon.zza(zzf2, zzf3));
                zzb((Object) t, i);
            } else if (zzf3 != null) {
                zzgrj.zza(t, zza2, zzf3);
                zzb((Object) t, i);
            }
        }
    }

    private final void zzb(T t, T t2, int i) {
        int zza2 = zza(i);
        int i2 = this.zzb[i];
        long j = (long) (zza2 & 1048575);
        if (zza(t2, i2, i)) {
            Object zzf2 = zzgrj.zzf(t, j);
            Object zzf3 = zzgrj.zzf(t2, j);
            if (zzf2 != null && zzf3 != null) {
                zzgrj.zza(t, j, zzgon.zza(zzf2, zzf3));
                zzb(t, i2, i);
            } else if (zzf3 != null) {
                zzgrj.zza(t, j, zzf3);
                zzb(t, i2, i);
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzh(int, long):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzh(int, int):int
      com.google.android.gms.internal.zzgnp.zzh(int, long):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzb(int, boolean):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzb(int, double):int
      com.google.android.gms.internal.zzgnp.zzb(int, float):int
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpa):int
      com.google.android.gms.internal.zzgnp.zzb(int, java.lang.String):int
      com.google.android.gms.internal.zzgnp.zzb(int, int):void
      com.google.android.gms.internal.zzgnp.zzb(int, long):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgnb):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpt):void
      com.google.android.gms.internal.zzgnp.zzb(int, boolean):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzg(int, long):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzg(int, int):int
      com.google.android.gms.internal.zzgnp.zzg(int, long):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzb(int, float):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzb(int, double):int
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpa):int
      com.google.android.gms.internal.zzgnp.zzb(int, java.lang.String):int
      com.google.android.gms.internal.zzgnp.zzb(int, boolean):int
      com.google.android.gms.internal.zzgnp.zzb(int, int):void
      com.google.android.gms.internal.zzgnp.zzb(int, long):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgnb):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpt):void
      com.google.android.gms.internal.zzgnp.zzb(int, float):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnp.zzb(int, double):int
     arg types: [int, int]
     candidates:
      com.google.android.gms.internal.zzgnp.zzb(int, float):int
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpa):int
      com.google.android.gms.internal.zzgnp.zzb(int, java.lang.String):int
      com.google.android.gms.internal.zzgnp.zzb(int, boolean):int
      com.google.android.gms.internal.zzgnp.zzb(int, int):void
      com.google.android.gms.internal.zzgnp.zzb(int, long):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgnb):void
      com.google.android.gms.internal.zzgnp.zzb(int, com.google.android.gms.internal.zzgpt):void
      com.google.android.gms.internal.zzgnp.zzb(int, double):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgqn.zzc(int, java.util.List<java.lang.Long>, boolean):int
     arg types: [int, java.util.List, int]
     candidates:
      com.google.android.gms.internal.zzgqn.zzc(int, java.util.List<?>, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgqn.zzc(int, java.util.List<java.lang.Long>, boolean):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgqn.zzd(int, java.util.List<java.lang.Integer>, boolean):int
     arg types: [int, java.util.List, int]
     candidates:
      com.google.android.gms.internal.zzgqn.zzd(int, java.util.List<?>, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgqn.zzd(int, java.util.List<java.lang.Integer>, boolean):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgqn.zzb(int, java.util.List<java.lang.Long>, boolean):int
     arg types: [int, java.util.List, int]
     candidates:
      com.google.android.gms.internal.zzgqn.zzb(int, java.util.List<com.google.android.gms.internal.zzgnb>, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgqn.zzb(int, java.util.List<java.lang.Long>, boolean):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgqn.zza(int, java.util.List<java.lang.Long>, boolean):int
     arg types: [int, java.util.List, int]
     candidates:
      com.google.android.gms.internal.zzgqn.zza(int, java.util.List<java.lang.String>, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgqn.zza(com.google.android.gms.internal.zzgnw, java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgqn.zza(com.google.android.gms.internal.zzgrd, java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgqn.zza(int, int, int):boolean
      com.google.android.gms.internal.zzgqn.zza(int, java.util.List<java.lang.Long>, boolean):int */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, int):com.google.android.gms.internal.zzgql
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, long):java.util.List<E>
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean */
    public final int zzb(T t) {
        int i;
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzb.length; i3 += 4) {
            int zza2 = zza(i3);
            int i4 = this.zzb[i3];
            long j = (long) (1048575 & zza2);
            switch ((zza2 & 267386880) >>> 20) {
                case 0:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzb(i4, 0.0d);
                        continue;
                        i2 += i;
                    }
                    break;
                case 1:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzb(i4, 0.0f);
                        continue;
                        i2 += i;
                    }
                    break;
                case 2:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzd(i4, zzgrj.zzb(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 3:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zze(i4, zzgrj.zzb(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 4:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzf(i4, zzgrj.zza(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 5:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzg(i4, 0L);
                        continue;
                        i2 += i;
                    }
                    break;
                case 6:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzi(i4, 0);
                        continue;
                        i2 += i;
                    }
                    break;
                case 7:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzb(i4, true);
                        continue;
                        i2 += i;
                    }
                    break;
                case 8:
                    if (zza((Object) t, i3)) {
                        Object zzf2 = zzgrj.zzf(t, j);
                        if (zzf2 instanceof zzgnb) {
                            i = zzgnp.zzc(i4, (zzgnb) zzf2);
                            continue;
                        } else {
                            i = zzgnp.zzb(i4, (String) zzf2);
                        }
                        i2 += i;
                    }
                    break;
                case 9:
                    if (zza((Object) t, i3)) {
                        i = zzgqn.zza(i4, zzgrj.zzf(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 10:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzc(i4, (zzgnb) zzgrj.zzf(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 11:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzg(i4, zzgrj.zza(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 12:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzk(i4, zzgrj.zza(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 13:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzj(i4, 0);
                        continue;
                        i2 += i;
                    }
                    break;
                case 14:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzh(i4, 0L);
                        continue;
                        i2 += i;
                    }
                    break;
                case 15:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzh(i4, zzgrj.zza(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 16:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzf(i4, zzgrj.zzb(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 17:
                    if (zza((Object) t, i3)) {
                        i = zzgnp.zzf(i4, (zzgpt) zzgrj.zzf(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 18:
                    i = zzgqn.zzi(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 19:
                    i = zzgqn.zzh(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 20:
                    i = zzgqn.zza(i4, (List<Long>) zza(t, j), false);
                    continue;
                    i2 += i;
                case 21:
                    i = zzgqn.zzb(i4, (List<Long>) zza(t, j), false);
                    continue;
                    i2 += i;
                case 22:
                    i = zzgqn.zze(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 23:
                    i = zzgqn.zzi(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 24:
                    i = zzgqn.zzh(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 25:
                    i = zzgqn.zzj(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 26:
                    i = zzgqn.zza(i4, (List<?>) zza(t, j));
                    continue;
                    i2 += i;
                case 27:
                    i = zzgqn.zzb(i4, zza(t, j));
                    continue;
                    i2 += i;
                case 28:
                    i = zzgqn.zzc(i4, zza(t, j));
                    continue;
                    i2 += i;
                case 29:
                    i = zzgqn.zzf(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 30:
                    i = zzgqn.zzd(i4, (List<Integer>) zza(t, j), false);
                    continue;
                    i2 += i;
                case 31:
                    i = zzgqn.zzh(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 32:
                    i = zzgqn.zzi(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 33:
                    i = zzgqn.zzg(i4, zza(t, j), false);
                    continue;
                    i2 += i;
                case 34:
                    i = zzgqn.zzc(i4, (List<Long>) zza(t, j), false);
                    continue;
                    i2 += i;
                case 35:
                    i = zzgqn.zzi(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 36:
                    i = zzgqn.zzh(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 37:
                    i = zzgqn.zza(i4, (List<Long>) zza(t, j), true);
                    continue;
                    i2 += i;
                case 38:
                    i = zzgqn.zzb(i4, (List<Long>) zza(t, j), true);
                    continue;
                    i2 += i;
                case 39:
                    i = zzgqn.zze(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 40:
                    i = zzgqn.zzi(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 41:
                    i = zzgqn.zzh(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 42:
                    i = zzgqn.zzj(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 43:
                    i = zzgqn.zzf(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 44:
                    i = zzgqn.zzd(i4, (List<Integer>) zza(t, j), true);
                    continue;
                    i2 += i;
                case 45:
                    i = zzgqn.zzh(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 46:
                    i = zzgqn.zzi(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 47:
                    i = zzgqn.zzg(i4, zza(t, j), true);
                    continue;
                    i2 += i;
                case 48:
                    i = zzgqn.zzc(i4, (List<Long>) zza(t, j), true);
                    continue;
                    i2 += i;
                case 49:
                    i = zzgqn.zzd(i4, zza(t, j));
                    continue;
                    i2 += i;
                case 50:
                    i = this.zzo.zza(i4, zzgrj.zzf(t, j), this.zzp.zza(i4));
                    continue;
                    i2 += i;
                case 51:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzb(i4, 0.0d);
                        continue;
                        i2 += i;
                    }
                    break;
                case 52:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzb(i4, 0.0f);
                        continue;
                        i2 += i;
                    }
                    break;
                case 53:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzd(i4, zze(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 54:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zze(i4, zze(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 55:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzf(i4, zzd(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 56:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzg(i4, 0L);
                        continue;
                        i2 += i;
                    }
                    break;
                case 57:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzi(i4, 0);
                        continue;
                        i2 += i;
                    }
                    break;
                case 58:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzb(i4, true);
                        continue;
                        i2 += i;
                    }
                    break;
                case 59:
                    if (zza(t, i4, i3)) {
                        Object zzf3 = zzgrj.zzf(t, j);
                        if (zzf3 instanceof zzgnb) {
                            i = zzgnp.zzc(i4, (zzgnb) zzf3);
                            continue;
                        } else {
                            i = zzgnp.zzb(i4, (String) zzf3);
                        }
                        i2 += i;
                    }
                    break;
                case 60:
                    if (zza(t, i4, i3)) {
                        i = zzgqn.zza(i4, zzgrj.zzf(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 61:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzc(i4, (zzgnb) zzgrj.zzf(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 62:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzg(i4, zzd(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 63:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzk(i4, zzd(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 64:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzj(i4, 0);
                        continue;
                        i2 += i;
                    }
                    break;
                case 65:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzh(i4, 0L);
                        continue;
                        i2 += i;
                    }
                    break;
                case 66:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzh(i4, zzd(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 67:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzf(i4, zze(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                case 68:
                    if (zza(t, i4, i3)) {
                        i = zzgnp.zzf(i4, (zzgpt) zzgrj.zzf(t, j));
                        continue;
                        i2 += i;
                    }
                    break;
                default:
                    i = 0;
                    continue;
                    i2 += i;
            }
            i = 0;
            i2 += i;
        }
        zzgrd<?, ?> zzgrd = this.zzj;
        int zzf4 = i2 + zzgrd.zzf(zzgrd.zzb(t));
        if (this.zzk) {
            return zzf4 + this.zzl.zza((Object) t).zzh();
        }
        return zzf4;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgqn.zza(int, java.util.List<java.lang.Double>, com.google.android.gms.internal.zzgrx, boolean):void
     arg types: [int, java.util.List, com.google.android.gms.internal.zzgrx, int]
     candidates:
      com.google.android.gms.internal.zzgqn.zza(int, int, java.lang.Object, com.google.android.gms.internal.zzgrd):UB
      com.google.android.gms.internal.zzgqn.zza(com.google.android.gms.internal.zzgpo, java.lang.Object, java.lang.Object, long):void
      com.google.android.gms.internal.zzgqn.zza(int, java.util.List<java.lang.Double>, com.google.android.gms.internal.zzgrx, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, int):com.google.android.gms.internal.zzgql
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, long):java.util.List<E>
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean */
    public final void zza(T t, zzgrx zzgrx) {
        if (zzgrx.zza() == zzgoj.zzg.zzl) {
            zza(this.zzj, t, zzgrx);
            zzgoa<?> zza2 = this.zzk ? this.zzl.zza((Object) t) : null;
            Iterator zzf2 = zza2 == null ? null : zza2.zzf();
            Map.Entry entry = (zzf2 == null || !zzf2.hasNext()) ? null : (Map.Entry) zzf2.next();
            for (int length = this.zzb.length - 4; length >= 0; length -= 4) {
                int zza3 = zza(length);
                int i = this.zzb[length];
                while (entry != null && this.zzl.zza((Map.Entry<?, ?>) entry) > i) {
                    this.zzl.zza(zzgrx, entry);
                    entry = zzf2.hasNext() ? (Map.Entry) zzf2.next() : null;
                }
                switch ((zza3 & 267386880) >>> 20) {
                    case 0:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zze(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 1:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 2:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zzb(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 3:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzc(i, zzgrj.zzb(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 4:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzc(i, zzgrj.zza(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 5:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzd(i, zzgrj.zzb(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 6:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzd(i, zzgrj.zza(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 7:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zzc(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 8:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zza(i, zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx);
                            break;
                        }
                    case 9:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 10:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, (zzgnb) zzgrj.zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 11:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zze(i, zzgrj.zza(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 12:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzb(i, zzgrj.zza(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 13:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zza(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 14:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzb(i, zzgrj.zzb(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 15:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzf(i, zzgrj.zza(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 16:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zze(i, zzgrj.zzb(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 17:
                        if (!zza((Object) t, length)) {
                            break;
                        } else {
                            zzgrx.zzb(i, zzgrj.zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 18:
                        zzgqn.zza(this.zzb[length], (List<Double>) ((List) zzgrj.zzf(t, (long) (zza3 & 1048575))), zzgrx, false);
                        break;
                    case 19:
                        zzgqn.zzb(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 20:
                        zzgqn.zzc(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 21:
                        zzgqn.zzd(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 22:
                        zzgqn.zzh(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 23:
                        zzgqn.zzf(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 24:
                        zzgqn.zzk(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 25:
                        zzgqn.zzn(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 26:
                        zzgqn.zza(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx);
                        break;
                    case 27:
                        zzgqn.zzc(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx);
                        break;
                    case 28:
                        zzgqn.zzb(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx);
                        break;
                    case 29:
                        zzgqn.zzi(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 30:
                        zzgqn.zzm(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 31:
                        zzgqn.zzl(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 32:
                        zzgqn.zzg(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 33:
                        zzgqn.zzj(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 34:
                        zzgqn.zze(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, false);
                        break;
                    case 35:
                        zzgqn.zza(this.zzb[length], (List<Double>) ((List) zzgrj.zzf(t, (long) (zza3 & 1048575))), zzgrx, true);
                        break;
                    case 36:
                        zzgqn.zzb(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 37:
                        zzgqn.zzc(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 38:
                        zzgqn.zzd(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 39:
                        zzgqn.zzh(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 40:
                        zzgqn.zzf(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 41:
                        zzgqn.zzk(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 42:
                        zzgqn.zzn(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 43:
                        zzgqn.zzi(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 44:
                        zzgqn.zzm(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 45:
                        zzgqn.zzl(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 46:
                        zzgqn.zzg(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 47:
                        zzgqn.zzj(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 48:
                        zzgqn.zze(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx, true);
                        break;
                    case 49:
                        zzgqn.zzd(this.zzb[length], (List) zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx);
                        break;
                    case 50:
                        zza(zzgrx, i, zzgrj.zzf(t, (long) (zza3 & 1048575)));
                        break;
                    case 51:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzb(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 52:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzc(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 53:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zze(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 54:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzc(i, zze(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 55:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzc(i, zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 56:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzd(i, zze(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 57:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzd(i, zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 58:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 59:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zza(i, zzgrj.zzf(t, (long) (zza3 & 1048575)), zzgrx);
                            break;
                        }
                    case 60:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzgrj.zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 61:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, (zzgnb) zzgrj.zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 62:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zze(i, zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 63:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzb(i, zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 64:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zza(i, zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 65:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzb(i, zze(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 66:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzf(i, zzd(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 67:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zze(i, zze(t, (long) (zza3 & 1048575)));
                            break;
                        }
                    case 68:
                        if (!zza(t, i, length)) {
                            break;
                        } else {
                            zzgrx.zzb(i, zzgrj.zzf(t, (long) (zza3 & 1048575)));
                            break;
                        }
                }
            }
            while (entry != null) {
                this.zzl.zza(zzgrx, entry);
                entry = zzf2.hasNext() ? (Map.Entry) zzf2.next() : null;
            }
            return;
        }
        zzgoa<?> zza4 = this.zzk ? this.zzl.zza((Object) t) : null;
        Iterator zze2 = zza4 == null ? null : zza4.zze();
        Map.Entry entry2 = (zze2 == null || !zze2.hasNext()) ? null : (Map.Entry) zze2.next();
        int length2 = this.zzb.length;
        Map.Entry entry3 = entry2;
        for (int i2 = 0; i2 < length2; i2 += 4) {
            int zza5 = zza(i2);
            int i3 = this.zzb[i2];
            while (entry3 != null && this.zzl.zza((Map.Entry<?, ?>) entry3) <= i3) {
                this.zzl.zza(zzgrx, entry3);
                entry3 = zze2.hasNext() ? (Map.Entry) zze2.next() : null;
            }
            switch ((zza5 & 267386880) >>> 20) {
                case 0:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zze(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 1:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 2:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zzb(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 3:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzc(i3, zzgrj.zzb(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 4:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzc(i3, zzgrj.zza(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 5:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzd(i3, zzgrj.zzb(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 6:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzd(i3, zzgrj.zza(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 7:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zzc(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 8:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zza(i3, zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx);
                        break;
                    }
                case 9:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 10:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, (zzgnb) zzgrj.zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 11:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zze(i3, zzgrj.zza(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 12:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzb(i3, zzgrj.zza(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 13:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zza(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 14:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzb(i3, zzgrj.zzb(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 15:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzf(i3, zzgrj.zza(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 16:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zze(i3, zzgrj.zzb(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 17:
                    if (!zza((Object) t, i2)) {
                        break;
                    } else {
                        zzgrx.zzb(i3, zzgrj.zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 18:
                    zzgqn.zza(this.zzb[i2], (List<Double>) ((List) zzgrj.zzf(t, (long) (zza5 & 1048575))), zzgrx, false);
                    break;
                case 19:
                    zzgqn.zzb(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 20:
                    zzgqn.zzc(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 21:
                    zzgqn.zzd(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 22:
                    zzgqn.zzh(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 23:
                    zzgqn.zzf(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 24:
                    zzgqn.zzk(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 25:
                    zzgqn.zzn(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 26:
                    zzgqn.zza(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx);
                    break;
                case 27:
                    zzgqn.zzc(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx);
                    break;
                case 28:
                    zzgqn.zzb(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx);
                    break;
                case 29:
                    zzgqn.zzi(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 30:
                    zzgqn.zzm(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 31:
                    zzgqn.zzl(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 32:
                    zzgqn.zzg(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 33:
                    zzgqn.zzj(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 34:
                    zzgqn.zze(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, false);
                    break;
                case 35:
                    zzgqn.zza(this.zzb[i2], (List<Double>) ((List) zzgrj.zzf(t, (long) (zza5 & 1048575))), zzgrx, true);
                    break;
                case 36:
                    zzgqn.zzb(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 37:
                    zzgqn.zzc(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 38:
                    zzgqn.zzd(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 39:
                    zzgqn.zzh(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 40:
                    zzgqn.zzf(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 41:
                    zzgqn.zzk(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 42:
                    zzgqn.zzn(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 43:
                    zzgqn.zzi(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 44:
                    zzgqn.zzm(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 45:
                    zzgqn.zzl(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 46:
                    zzgqn.zzg(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 47:
                    zzgqn.zzj(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 48:
                    zzgqn.zze(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx, true);
                    break;
                case 49:
                    zzgqn.zzd(this.zzb[i2], (List) zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx);
                    break;
                case 50:
                    zza(zzgrx, i3, zzgrj.zzf(t, (long) (zza5 & 1048575)));
                    break;
                case 51:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzb(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 52:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzc(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 53:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zze(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 54:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzc(i3, zze(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 55:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzc(i3, zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 56:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzd(i3, zze(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 57:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzd(i3, zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 58:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 59:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zza(i3, zzgrj.zzf(t, (long) (zza5 & 1048575)), zzgrx);
                        break;
                    }
                case 60:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzgrj.zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 61:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, (zzgnb) zzgrj.zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 62:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zze(i3, zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 63:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzb(i3, zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 64:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zza(i3, zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 65:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzb(i3, zze(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 66:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzf(i3, zzd(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 67:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zze(i3, zze(t, (long) (zza5 & 1048575)));
                        break;
                    }
                case 68:
                    if (!zza(t, i3, i2)) {
                        break;
                    } else {
                        zzgrx.zzb(i3, zzgrj.zzf(t, (long) (zza5 & 1048575)));
                        break;
                    }
            }
        }
        while (entry3 != null) {
            this.zzl.zza(zzgrx, entry3);
            entry3 = zze2.hasNext() ? (Map.Entry) zze2.next() : null;
        }
        zza(this.zzj, t, zzgrx);
    }

    private final <K, V> void zza(zzgrx zzgrx, int i, Object obj) {
        if (obj != null) {
            zzgrx.zza(i, this.zzo.zzf(this.zzp.zza(i)), this.zzo.zzb(obj));
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgqk):boolean
     arg types: [?, com.google.android.gms.internal.zzgqk]
     candidates:
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, com.google.android.gms.internal.zzgqk):boolean */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, int):void
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, long):double
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgql.zzb(java.lang.Object, java.lang.Object):void
      com.google.android.gms.internal.zzgpx.zzb(java.lang.Object, int):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int, com.google.android.gms.internal.zzgqk):void
     arg types: [T, int, com.google.android.gms.internal.zzgqk]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(com.google.android.gms.internal.zzgrd, java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(com.google.android.gms.internal.zzgrx, int, java.lang.Object):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, java.lang.Object, int):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int, int):boolean
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, com.google.android.gms.internal.zzgqk, com.google.android.gms.internal.zzgnv):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgqk, com.google.android.gms.internal.zzgnv):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int, com.google.android.gms.internal.zzgqk):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean
     arg types: [T, int]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, int):com.google.android.gms.internal.zzgql
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, long):java.util.List<E>
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, int):boolean */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
     arg types: [T, long, long]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
     arg types: [T, long, int]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
     arg types: [T, long, boolean]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
     arg types: [T, long, float]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void */
    public final void zza(T t, zzgqk zzgqk, zzgnv zzgnv) throws IOException {
        Object obj;
        zzgoa<?> zzgoa;
        T t2 = t;
        zzgqk zzgqk2 = zzgqk;
        zzgnv zzgnv2 = zzgnv;
        if (zzgnv2 != null) {
            zzgrd<?, ?> zzgrd = this.zzj;
            zzgnw<?> zzgnw = this.zzl;
            zzgoa<?> zzgoa2 = null;
            Object obj2 = null;
            while (true) {
                try {
                    int zza2 = zzgqk.zza();
                    int zzd2 = zzd(zza2);
                    if (zzd2 >= 0) {
                        int zza3 = zza(zzd2);
                        switch ((267386880 & zza3) >>> 20) {
                            case 0:
                                zzgrj.zza(t2, (long) (1048575 & zza3), zzgqk.zzd());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 1:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zze());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 2:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzg());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 3:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzf());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 4:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzh());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 5:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzi());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 6:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzj());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 7:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzk());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 8:
                                zza((Object) t2, zza3, zzgqk2);
                                zzb((Object) t2, zzd2);
                                continue;
                            case 9:
                                int i = zzd2 / 4;
                                if (this.zzu[i] == null) {
                                    this.zzu[i] = zzgqf.zza().zza((Class) this.zze.zza(zza2));
                                }
                                if (!zza((Object) t2, zzd2)) {
                                    zzgrj.zza(t2, (long) (1048575 & zza3), zzgqk2.zza((zzgql) this.zzu[i], zzgnv2));
                                    zzb((Object) t2, zzd2);
                                    break;
                                } else {
                                    long j = (long) (1048575 & zza3);
                                    zzgrj.zza(t2, j, zzgon.zza(zzgrj.zzf(t2, j), zzgqk2.zza((zzgql) this.zzu[i], zzgnv2)));
                                    continue;
                                }
                            case 10:
                                zzgrj.zza(t2, (long) (1048575 & zza3), zzgqk.zzn());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 11:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzo());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 12:
                                int zzp2 = zzgqk.zzp();
                                zzgop zza4 = this.zzf.zza(zza2);
                                if (zza4 != null) {
                                    if (zza4.zza(zzp2) == null) {
                                        obj2 = zzgqn.zza(zza2, zzp2, obj2, zzgrd);
                                        continue;
                                    }
                                }
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzp2);
                                zzb((Object) t2, zzd2);
                                break;
                            case 13:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzq());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 14:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzr());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 15:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzs());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 16:
                                zzgrj.zza((Object) t2, (long) (1048575 & zza3), zzgqk.zzt());
                                zzb((Object) t2, zzd2);
                                continue;
                            case 17:
                                int i2 = zzd2 / 4;
                                if (this.zzu[i2] == null) {
                                    this.zzu[i2] = zzgqf.zza().zza((Class) this.zze.zza(zza2));
                                }
                                if (!zza((Object) t2, zzd2)) {
                                    zzgrj.zza(t2, (long) (1048575 & zza3), zzgqk2.zzb((zzgql) this.zzu[i2], zzgnv2));
                                    zzb((Object) t2, zzd2);
                                    break;
                                } else {
                                    long j2 = (long) (1048575 & zza3);
                                    zzgrj.zza(t2, j2, zzgon.zza(zzgrj.zzf(t2, j2), zzgqk2.zzb((zzgql) this.zzu[i2], zzgnv2)));
                                    continue;
                                }
                            case 18:
                                zzgqk2.zza(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 19:
                                zzgqk2.zzb(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 20:
                                zzgqk2.zzd(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 21:
                                zzgqk2.zzc(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 22:
                                zzgqk2.zze(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 23:
                                zzgqk2.zzf(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 24:
                                zzgqk2.zzg(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 25:
                                zzgqk2.zzh(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 26:
                                if (!zzc(zza3)) {
                                    zzgqk2.zzi(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                    break;
                                } else {
                                    zzgqk2.zzj(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                    continue;
                                }
                            case 27:
                                int i3 = zzd2 / 4;
                                if (this.zzu[i3] == null) {
                                    this.zzu[i3] = zzgqf.zza().zza((Class) this.zze.zza(zza2));
                                }
                                zzgqk2.zza(this.zzh.zza(t2, (long) (zza3 & 1048575)), (zzgql) this.zzu[i3], zzgnv2);
                                continue;
                            case 28:
                                zzgqk2.zzk(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 29:
                                zzgqk2.zzl(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 30:
                                List zza5 = this.zzh.zza(t2, (long) (zza3 & 1048575));
                                zzgqk2.zzm(zza5);
                                obj2 = zzgqn.zza(zza2, zza5, this.zzf.zza(zza2), obj2, zzgrd);
                                continue;
                            case 31:
                                zzgqk2.zzn(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 32:
                                zzgqk2.zzo(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 33:
                                zzgqk2.zzp(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 34:
                                zzgqk2.zzq(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 35:
                                zzgqk2.zza(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 36:
                                zzgqk2.zzb(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 37:
                                zzgqk2.zzd(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 38:
                                zzgqk2.zzc(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 39:
                                zzgqk2.zze(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 40:
                                zzgqk2.zzf(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 41:
                                zzgqk2.zzg(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 42:
                                zzgqk2.zzh(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 43:
                                zzgqk2.zzl(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 44:
                                List zza6 = this.zzh.zza(t2, (long) (zza3 & 1048575));
                                zzgqk2.zzm(zza6);
                                obj2 = zzgqn.zza(zza2, zza6, this.zzf.zza(zza2), obj2, zzgrd);
                                continue;
                            case 45:
                                zzgqk2.zzn(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 46:
                                zzgqk2.zzo(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 47:
                                zzgqk2.zzp(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 48:
                                zzgqk2.zzq(this.zzh.zza(t2, (long) (zza3 & 1048575)));
                                continue;
                            case 49:
                                int i4 = zzd2 / 4;
                                if (this.zzu[i4] == null) {
                                    this.zzu[i4] = zzgqf.zza().zza((Class) this.zze.zza(zza2));
                                }
                                zzgqk2.zzb(this.zzh.zza(t2, (long) (zza3 & 1048575)), (zzgql) this.zzu[i4], zzgnv2);
                                continue;
                            case 50:
                                Object zza7 = this.zzp.zza(zza2);
                                long zza8 = (long) (zza(zzd2) & 1048575);
                                Object zzf2 = zzgrj.zzf(t2, zza8);
                                if (zzf2 == null) {
                                    zzf2 = this.zzo.zze(zza7);
                                    zzgrj.zza(t2, zza8, zzf2);
                                } else if (this.zzo.zzc(zzf2)) {
                                    Object zze2 = this.zzo.zze(zza7);
                                    this.zzo.zza(zze2, zzf2);
                                    zzgrj.zza(t2, zza8, zze2);
                                    zzf2 = zze2;
                                }
                                zzgqk2.zza(this.zzo.zza(zzf2), this.zzo.zzf(zza7), zzgnv2);
                                continue;
                            case 51:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Double.valueOf(zzgqk.zzd()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 52:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Float.valueOf(zzgqk.zze()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 53:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Long.valueOf(zzgqk.zzg()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 54:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Long.valueOf(zzgqk.zzf()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 55:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Integer.valueOf(zzgqk.zzh()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 56:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Long.valueOf(zzgqk.zzi()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 57:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Integer.valueOf(zzgqk.zzj()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 58:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Boolean.valueOf(zzgqk.zzk()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 59:
                                zza((Object) t2, zza3, zzgqk2);
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 60:
                                if (zza(t2, zza2, zzd2)) {
                                    long j3 = (long) (zza3 & 1048575);
                                    zzgrj.zza(t2, j3, zzgon.zza(zzgrj.zzf(t2, j3), zzgqk2.zza(this.zze.zza(zza2), zzgnv2)));
                                } else {
                                    zzgrj.zza(t2, (long) (zza3 & 1048575), zzgqk2.zza(this.zze.zza(zza2), zzgnv2));
                                    zzb((Object) t2, zzd2);
                                }
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 61:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), zzgqk.zzn());
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 62:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Integer.valueOf(zzgqk.zzo()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 63:
                                int zzp3 = zzgqk.zzp();
                                zzgop zza9 = this.zzf.zza(zza2);
                                if (zza9 != null) {
                                    if (zza9.zza(zzp3) == null) {
                                        obj2 = zzgqn.zza(zza2, zzp3, obj2, zzgrd);
                                        continue;
                                    }
                                }
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Integer.valueOf(zzp3));
                                zzb(t2, zza2, zzd2);
                                break;
                            case 64:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Integer.valueOf(zzgqk.zzq()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 65:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Long.valueOf(zzgqk.zzr()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 66:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Integer.valueOf(zzgqk.zzs()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 67:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), Long.valueOf(zzgqk.zzt()));
                                zzb(t2, zza2, zzd2);
                                continue;
                            case 68:
                                zzgrj.zza(t2, (long) (zza3 & 1048575), zzgqk2.zzb(this.zze.zza(zza2), zzgnv2));
                                zzb(t2, zza2, zzd2);
                                continue;
                            default:
                                if (obj2 == null) {
                                    obj2 = zzgrd.zza();
                                }
                                if (!zzgrd.zza((Object) obj2, zzgqk2)) {
                                    int[] iArr = this.zzr;
                                    if (iArr != null) {
                                        for (int zza10 : iArr) {
                                            obj2 = zza(t2, zza10, obj2, zzgrd);
                                        }
                                    }
                                    if (obj2 != null) {
                                        zzgrd.zzb(t2, obj2);
                                        return;
                                    }
                                    return;
                                }
                                continue;
                        }
                    } else if (zza2 == Integer.MAX_VALUE) {
                        int[] iArr2 = this.zzr;
                        if (iArr2 != null) {
                            for (int zza11 : iArr2) {
                                obj2 = zza(t2, zza11, obj2, zzgrd);
                            }
                        }
                        if (obj2 != null) {
                            zzgrd.zzb(t2, obj2);
                            return;
                        }
                        return;
                    } else {
                        if (!this.zzk) {
                            obj = null;
                        } else {
                            obj = zzgnw.zza(zzgnv2, this.zzi, zza2);
                        }
                        if (obj != null) {
                            if (zzgoa2 == null) {
                                zzgoa = zzgnw.zzb(t2);
                            } else {
                                zzgoa = zzgoa2;
                            }
                            obj2 = zzgnw.zza(zzgqk, obj, zzgnv, zzgoa, obj2, zzgrd);
                            zzgoa2 = zzgoa;
                        } else {
                            zzgrd.zza(zzgqk2);
                            if (obj2 == null) {
                                obj2 = zzgrd.zzc(t2);
                            }
                            if (!zzgrd.zza((Object) obj2, zzgqk2)) {
                                int[] iArr3 = this.zzr;
                                if (iArr3 != null) {
                                    for (int zza12 : iArr3) {
                                        obj2 = zza(t2, zza12, obj2, zzgrd);
                                    }
                                }
                                if (obj2 != null) {
                                    zzgrd.zzb(t2, obj2);
                                    return;
                                }
                                return;
                            }
                        }
                    }
                } catch (zzgou e) {
                    zzgrd.zza(zzgqk2);
                    if (obj2 == null) {
                        obj2 = zzgrd.zzc(t2);
                    }
                    if (!zzgrd.zza((Object) obj2, zzgqk2)) {
                        int[] iArr4 = this.zzr;
                        if (iArr4 != null) {
                            for (int zza13 : iArr4) {
                                obj2 = zza(t2, zza13, obj2, zzgrd);
                            }
                        }
                        if (obj2 != null) {
                            zzgrd.zzb(t2, obj2);
                            return;
                        }
                        return;
                    }
                } catch (Throwable th) {
                    Throwable th2 = th;
                    int[] iArr5 = this.zzr;
                    if (iArr5 != null) {
                        for (int zza14 : iArr5) {
                            obj2 = zza(t2, zza14, obj2, zzgrd);
                        }
                    }
                    if (obj2 != null) {
                        zzgrd.zzb(t2, obj2);
                    }
                    throw th2;
                }
            }
        } else {
            throw new NullPointerException();
        }
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, long j, int i7, long j2, zzgmv zzgmv) throws IOException {
        int i8;
        int i9;
        int i10;
        int i11;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i12 = i;
        int i13 = i2;
        int i14 = i3;
        int i15 = i4;
        int i16 = i5;
        int i17 = i6;
        long j3 = j2;
        zzgmv zzgmv2 = zzgmv;
        zzgos zzgos = (zzgos) zza.getObject(t2, j3);
        if (!zzgos.zza()) {
            int size = zzgos.size();
            zzgos = zzgos.zza(size == 0 ? 10 : size << 1);
            zza.putObject(t2, j3, zzgos);
        }
        switch (i7) {
            case 18:
            case 35:
                if (i16 == 2) {
                    zzgns zzgns = (zzgns) zzgos;
                    int zza2 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i18 = zzgmv2.zza + zza2;
                    while (zza2 < i18) {
                        zzgns.zza(zzgmu.zzc(bArr2, zza2));
                        zza2 += 8;
                    }
                    if (zza2 == i18) {
                        return zza2;
                    }
                    throw zzgot.zza();
                } else if (i16 == 1) {
                    zzgns zzgns2 = (zzgns) zzgos;
                    zzgns2.zza(zzgmu.zzc(bArr, i));
                    int i19 = i12 + 8;
                    while (i19 < i13) {
                        int zza3 = zzgmu.zza(bArr2, i19, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return i19;
                        }
                        zzgns2.zza(zzgmu.zzc(bArr2, zza3));
                        i19 = zza3 + 8;
                    }
                    return i19;
                }
                break;
            case 19:
            case 36:
                if (i16 == 2) {
                    zzgog zzgog = (zzgog) zzgos;
                    int zza4 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i20 = zzgmv2.zza + zza4;
                    while (zza4 < i20) {
                        zzgog.zza(zzgmu.zzd(bArr2, zza4));
                        zza4 += 4;
                    }
                    if (zza4 == i20) {
                        return zza4;
                    }
                    throw zzgot.zza();
                } else if (i16 == 5) {
                    zzgog zzgog2 = (zzgog) zzgos;
                    zzgog2.zza(zzgmu.zzd(bArr, i));
                    int i21 = i12 + 4;
                    while (i21 < i13) {
                        int zza5 = zzgmu.zza(bArr2, i21, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return i21;
                        }
                        zzgog2.zza(zzgmu.zzd(bArr2, zza5));
                        i21 = zza5 + 4;
                    }
                    return i21;
                }
                break;
            case 20:
            case 21:
            case 37:
            case 38:
                if (i16 == 2) {
                    zzgph zzgph = (zzgph) zzgos;
                    int zza6 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i22 = zzgmv2.zza + zza6;
                    while (zza6 < i22) {
                        zza6 = zzgmu.zzb(bArr2, zza6, zzgmv2);
                        zzgph.zza(zzgmv2.zzb);
                    }
                    if (zza6 == i22) {
                        return zza6;
                    }
                    throw zzgot.zza();
                } else if (i16 == 0) {
                    zzgph zzgph2 = (zzgph) zzgos;
                    int zzb2 = zzgmu.zzb(bArr2, i12, zzgmv2);
                    zzgph2.zza(zzgmv2.zzb);
                    while (zzb2 < i13) {
                        int zza7 = zzgmu.zza(bArr2, zzb2, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return zzb2;
                        }
                        zzb2 = zzgmu.zzb(bArr2, zza7, zzgmv2);
                        zzgph2.zza(zzgmv2.zzb);
                    }
                    return zzb2;
                }
                break;
            case 22:
            case 29:
            case 39:
            case 43:
                if (i16 == 2) {
                    return zzgmu.zza(bArr2, i12, zzgos, zzgmv2);
                }
                if (i16 == 0) {
                    return zzgmu.zza(i3, bArr, i, i2, zzgos, zzgmv);
                }
                break;
            case 23:
            case 32:
            case 40:
            case 46:
                if (i16 == 2) {
                    zzgph zzgph3 = (zzgph) zzgos;
                    int zza8 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i23 = zzgmv2.zza + zza8;
                    while (zza8 < i23) {
                        zzgph3.zza(zzgmu.zzb(bArr2, zza8));
                        zza8 += 8;
                    }
                    if (zza8 == i23) {
                        return zza8;
                    }
                    throw zzgot.zza();
                } else if (i16 == 1) {
                    zzgph zzgph4 = (zzgph) zzgos;
                    zzgph4.zza(zzgmu.zzb(bArr, i));
                    int i24 = i12 + 8;
                    while (i24 < i13) {
                        int zza9 = zzgmu.zza(bArr2, i24, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return i24;
                        }
                        zzgph4.zza(zzgmu.zzb(bArr2, zza9));
                        i24 = zza9 + 8;
                    }
                    return i24;
                }
                break;
            case 24:
            case 31:
            case 41:
            case 45:
                if (i16 == 2) {
                    zzgom zzgom = (zzgom) zzgos;
                    int zza10 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i25 = zzgmv2.zza + zza10;
                    while (zza10 < i25) {
                        zzgom.zzd(zzgmu.zza(bArr2, zza10));
                        zza10 += 4;
                    }
                    if (zza10 == i25) {
                        return zza10;
                    }
                    throw zzgot.zza();
                } else if (i16 == 5) {
                    zzgom zzgom2 = (zzgom) zzgos;
                    zzgom2.zzd(zzgmu.zza(bArr, i));
                    int i26 = i12 + 4;
                    while (i26 < i13) {
                        int zza11 = zzgmu.zza(bArr2, i26, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return i26;
                        }
                        zzgom2.zzd(zzgmu.zza(bArr2, zza11));
                        i26 = zza11 + 4;
                    }
                    return i26;
                }
                break;
            case 25:
            case 42:
                if (i16 == 2) {
                    zzgmz zzgmz = (zzgmz) zzgos;
                    int zza12 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i27 = zzgmv2.zza + zza12;
                    while (zza12 < i27) {
                        zza12 = zzgmu.zzb(bArr2, zza12, zzgmv2);
                        zzgmz.zza(zzgmv2.zzb != 0);
                    }
                    if (zza12 == i27) {
                        return zza12;
                    }
                    throw zzgot.zza();
                } else if (i16 == 0) {
                    zzgmz zzgmz2 = (zzgmz) zzgos;
                    int zzb3 = zzgmu.zzb(bArr2, i12, zzgmv2);
                    zzgmz2.zza(zzgmv2.zzb != 0);
                    while (zzb3 < i13) {
                        int zza13 = zzgmu.zza(bArr2, zzb3, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return zzb3;
                        }
                        zzb3 = zzgmu.zzb(bArr2, zza13, zzgmv2);
                        zzgmz2.zza(zzgmv2.zzb != 0);
                    }
                    return zzb3;
                }
                break;
            case 26:
                if (i16 == 2) {
                    if ((j & 536870912) == 0) {
                        int zza14 = zzgmu.zza(bArr2, i12, zzgmv2);
                        int i28 = zzgmv2.zza;
                        if (i28 == 0) {
                            zzgos.add("");
                        } else {
                            zzgos.add(new String(bArr2, zza14, i28, zzgon.zza));
                            zza14 += i28;
                        }
                        while (i9 < i13) {
                            int zza15 = zzgmu.zza(bArr2, i9, zzgmv2);
                            if (i14 != zzgmv2.zza) {
                                return i9;
                            }
                            i9 = zzgmu.zza(bArr2, zza15, zzgmv2);
                            int i29 = zzgmv2.zza;
                            if (i29 == 0) {
                                zzgos.add("");
                            } else {
                                zzgos.add(new String(bArr2, i9, i29, zzgon.zza));
                                i9 += i29;
                            }
                        }
                        return i9;
                    }
                    int zza16 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i30 = zzgmv2.zza;
                    if (i30 == 0) {
                        zzgos.add("");
                    } else {
                        int i31 = zza16 + i30;
                        if (zzgrl.zza(bArr2, zza16, i31)) {
                            zzgos.add(new String(bArr2, zza16, i30, zzgon.zza));
                            zza16 = i31;
                        } else {
                            throw zzgot.zzi();
                        }
                    }
                    while (i8 < i13) {
                        int zza17 = zzgmu.zza(bArr2, i8, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return i8;
                        }
                        i8 = zzgmu.zza(bArr2, zza17, zzgmv2);
                        int i32 = zzgmv2.zza;
                        if (i32 == 0) {
                            zzgos.add("");
                        } else {
                            int i33 = i8 + i32;
                            if (zzgrl.zza(bArr2, i8, i33)) {
                                zzgos.add(new String(bArr2, i8, i32, zzgon.zza));
                                i8 = i33;
                            } else {
                                throw zzgot.zzi();
                            }
                        }
                    }
                    return i8;
                }
                break;
            case 27:
                if (i16 == 2) {
                    return zza(zza(i15, i17), i3, bArr, i, i2, zzgos, zzgmv);
                }
                break;
            case 28:
                if (i16 == 2) {
                    int zza18 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i34 = zzgmv2.zza;
                    if (i34 == 0) {
                        zzgos.add(zzgnb.zza);
                    } else {
                        zzgos.add(zzgnb.zza(bArr2, zza18, i34));
                        zza18 += i34;
                    }
                    while (i10 < i13) {
                        int zza19 = zzgmu.zza(bArr2, i10, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return i10;
                        }
                        i10 = zzgmu.zza(bArr2, zza19, zzgmv2);
                        int i35 = zzgmv2.zza;
                        if (i35 == 0) {
                            zzgos.add(zzgnb.zza);
                        } else {
                            zzgos.add(zzgnb.zza(bArr2, i10, i35));
                            i10 += i35;
                        }
                    }
                    return i10;
                }
                break;
            case 30:
            case 44:
                if (i16 == 2) {
                    i11 = zzgmu.zza(bArr2, i12, zzgos, zzgmv2);
                } else if (i16 == 0) {
                    i11 = zzgmu.zza(i3, bArr, i, i2, zzgos, zzgmv);
                }
                zzgoj zzgoj = (zzgoj) t2;
                zzgre zzgre = zzgoj.zzb;
                if (zzgre == zzgre.zza()) {
                    zzgre = null;
                }
                zzgre zzgre2 = (zzgre) zzgqn.zza(i15, zzgos, this.zzf.zza(i15), zzgre, this.zzj);
                if (zzgre2 != null) {
                    zzgoj.zzb = zzgre2;
                }
                return i11;
            case 33:
            case 47:
                if (i16 == 2) {
                    zzgom zzgom3 = (zzgom) zzgos;
                    int zza20 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i36 = zzgmv2.zza + zza20;
                    while (zza20 < i36) {
                        zza20 = zzgmu.zza(bArr2, zza20, zzgmv2);
                        zzgom3.zzd(zzgnk.zzg(zzgmv2.zza));
                    }
                    if (zza20 == i36) {
                        return zza20;
                    }
                    throw zzgot.zza();
                } else if (i16 == 0) {
                    zzgom zzgom4 = (zzgom) zzgos;
                    int zza21 = zzgmu.zza(bArr2, i12, zzgmv2);
                    zzgom4.zzd(zzgnk.zzg(zzgmv2.zza));
                    while (zza21 < i13) {
                        int zza22 = zzgmu.zza(bArr2, zza21, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return zza21;
                        }
                        zza21 = zzgmu.zza(bArr2, zza22, zzgmv2);
                        zzgom4.zzd(zzgnk.zzg(zzgmv2.zza));
                    }
                    return zza21;
                }
                break;
            case 34:
            case 48:
                if (i16 == 2) {
                    zzgph zzgph5 = (zzgph) zzgos;
                    int zza23 = zzgmu.zza(bArr2, i12, zzgmv2);
                    int i37 = zzgmv2.zza + zza23;
                    while (zza23 < i37) {
                        zza23 = zzgmu.zzb(bArr2, zza23, zzgmv2);
                        zzgph5.zza(zzgnk.zza(zzgmv2.zzb));
                    }
                    if (zza23 == i37) {
                        return zza23;
                    }
                    throw zzgot.zza();
                } else if (i16 == 0) {
                    zzgph zzgph6 = (zzgph) zzgos;
                    int zzb4 = zzgmu.zzb(bArr2, i12, zzgmv2);
                    zzgph6.zza(zzgnk.zza(zzgmv2.zzb));
                    while (zzb4 < i13) {
                        int zza24 = zzgmu.zza(bArr2, zzb4, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return zzb4;
                        }
                        zzb4 = zzgmu.zzb(bArr2, zza24, zzgmv2);
                        zzgph6.zza(zzgnk.zza(zzgmv2.zzb));
                    }
                    return zzb4;
                }
                break;
            case 49:
                if (i16 == 3) {
                    zzgql zza25 = zza(i15, i17);
                    int i38 = (i14 & -8) | 4;
                    int zza26 = zza(zza25, bArr, i, i2, i38, zzgmv);
                    zzgos.add(zzgmv2.zzc);
                    while (zza26 < i13) {
                        int zza27 = zzgmu.zza(bArr2, zza26, zzgmv2);
                        if (i14 != zzgmv2.zza) {
                            return zza26;
                        }
                        zza26 = zza(zza25, bArr, zza27, i2, i38, zzgmv);
                        zzgos.add(zzgmv2.zzc);
                    }
                    return zza26;
                }
                break;
        }
        return i12;
    }

    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <K, V> int zza(java.lang.Object r8, byte[] r9, int r10, int r11, int r12, long r13, com.google.android.gms.internal.zzgmv r15) throws java.io.IOException {
        /*
            r7 = this;
            com.google.android.gms.internal.zzgol<java.lang.Object> r0 = r7.zzp
            java.lang.Object r12 = r0.zza(r12)
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r0 = r0.getObject(r8, r13)
            com.google.android.gms.internal.zzgpo r1 = r7.zzo
            boolean r1 = r1.zzc(r0)
            if (r1 == 0) goto L_0x0026
            com.google.android.gms.internal.zzgpo r1 = r7.zzo
            java.lang.Object r1 = r1.zze(r12)
            com.google.android.gms.internal.zzgpo r2 = r7.zzo
            r2.zza(r1, r0)
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            r0.putObject(r8, r13, r1)
            r0 = r1
        L_0x0026:
            com.google.android.gms.internal.zzgpo r8 = r7.zzo
            com.google.android.gms.internal.zzgpm r8 = r8.zzf(r12)
            com.google.android.gms.internal.zzgpo r12 = r7.zzo
            java.util.Map r12 = r12.zza(r0)
            int r10 = com.google.android.gms.internal.zzgmu.zza(r9, r10, r15)
            int r13 = r15.zza
            if (r13 < 0) goto L_0x00a0
            int r14 = r11 - r10
            if (r13 > r14) goto L_0x00a0
            int r13 = r13 + r10
            K r14 = r8.zzb
            V r0 = r8.zzd
        L_0x0044:
            if (r10 >= r13) goto L_0x0094
            int r1 = r10 + 1
            byte r10 = r9[r10]
            if (r10 >= 0) goto L_0x0054
            int r1 = com.google.android.gms.internal.zzgmu.zza(r10, r9, r1, r15)
            int r10 = r15.zza
            r2 = r1
            goto L_0x0055
        L_0x0054:
            r2 = r1
        L_0x0055:
            int r1 = r10 >>> 3
            r3 = r10 & 7
            r4 = 1
            if (r1 == r4) goto L_0x007a
            r4 = 2
            if (r1 == r4) goto L_0x0060
            goto L_0x008f
        L_0x0060:
            com.google.android.gms.internal.zzgrr r1 = r8.zzc
            int r1 = r1.zzb()
            if (r3 != r1) goto L_0x008f
            com.google.android.gms.internal.zzgrr r4 = r8.zzc
            V r10 = r8.zzd
            java.lang.Class r5 = r10.getClass()
            r1 = r9
            r3 = r11
            r6 = r15
            int r10 = zza(r1, r2, r3, r4, r5, r6)
            java.lang.Object r0 = r15.zzc
            goto L_0x0044
        L_0x007a:
            com.google.android.gms.internal.zzgrr r1 = r8.zza
            int r1 = r1.zzb()
            if (r3 != r1) goto L_0x008f
            com.google.android.gms.internal.zzgrr r4 = r8.zza
            r5 = 0
            r1 = r9
            r3 = r11
            r6 = r15
            int r10 = zza(r1, r2, r3, r4, r5, r6)
            java.lang.Object r14 = r15.zzc
            goto L_0x0044
        L_0x008f:
            int r10 = com.google.android.gms.internal.zzgmu.zza(r10, r9, r2, r11, r15)
            goto L_0x0044
        L_0x0094:
            if (r10 != r13) goto L_0x009b
            r12.put(r14, r0)
            return r13
        L_0x009b:
            com.google.android.gms.internal.zzgot r8 = com.google.android.gms.internal.zzgot.zzh()
            throw r8
        L_0x00a0:
            com.google.android.gms.internal.zzgot r8 = com.google.android.gms.internal.zzgot.zza()
            throw r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, byte[], int, int, int, long, com.google.android.gms.internal.zzgmv):int");
    }

    private final int zza(T t, byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j, int i8, zzgmv zzgmv) throws IOException {
        int i9;
        Object obj;
        Object obj2;
        T t2 = t;
        byte[] bArr2 = bArr;
        int i10 = i;
        int i11 = i3;
        int i12 = i4;
        int i13 = i5;
        long j2 = j;
        int i14 = i8;
        zzgmv zzgmv2 = zzgmv;
        long j3 = (long) (this.zzb[i14 + 2] & 1048575);
        boolean z = true;
        switch (i7) {
            case 51:
                if (i13 == 1) {
                    zza.putObject(t2, j2, Double.valueOf(zzgmu.zzc(bArr, i)));
                    i9 = i10 + 8;
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 52:
                if (i13 == 5) {
                    zza.putObject(t2, j2, Float.valueOf(zzgmu.zzd(bArr, i)));
                    i9 = i10 + 4;
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 53:
            case 54:
                if (i13 == 0) {
                    i9 = zzgmu.zzb(bArr2, i10, zzgmv2);
                    zza.putObject(t2, j2, Long.valueOf(zzgmv2.zzb));
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 55:
            case 62:
                if (i13 == 0) {
                    i9 = zzgmu.zza(bArr2, i10, zzgmv2);
                    zza.putObject(t2, j2, Integer.valueOf(zzgmv2.zza));
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 56:
            case 65:
                if (i13 == 1) {
                    zza.putObject(t2, j2, Long.valueOf(zzgmu.zzb(bArr, i)));
                    i9 = i10 + 8;
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 57:
            case 64:
                if (i13 == 5) {
                    zza.putObject(t2, j2, Integer.valueOf(zzgmu.zza(bArr, i)));
                    i9 = i10 + 4;
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 58:
                if (i13 == 0) {
                    i9 = zzgmu.zzb(bArr2, i10, zzgmv2);
                    Unsafe unsafe = zza;
                    if (zzgmv2.zzb == 0) {
                        z = false;
                    }
                    unsafe.putObject(t2, j2, Boolean.valueOf(z));
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 59:
                if (i13 == 2) {
                    int zza2 = zzgmu.zza(bArr2, i10, zzgmv2);
                    int i15 = zzgmv2.zza;
                    if (i15 == 0) {
                        zza.putObject(t2, j2, "");
                    } else if ((i6 & 536870912) == 0 || zzgrl.zza(bArr2, zza2, zza2 + i15)) {
                        zza.putObject(t2, j2, new String(bArr2, zza2, i15, zzgon.zza));
                        zza2 += i15;
                    } else {
                        throw zzgot.zzi();
                    }
                    zza.putInt(t2, j3, i12);
                    return zza2;
                }
                return i10;
            case 60:
                if (i13 == 2) {
                    int zza3 = zza(zza(i12, i14), bArr2, i10, i2, zzgmv2);
                    if (zza.getInt(t2, j3) == i12) {
                        obj = zza.getObject(t2, j2);
                    } else {
                        obj = null;
                    }
                    if (obj == null) {
                        zza.putObject(t2, j2, zzgmv2.zzc);
                    } else {
                        zza.putObject(t2, j2, zzgon.zza(obj, zzgmv2.zzc));
                    }
                    zza.putInt(t2, j3, i12);
                    return zza3;
                }
                return i10;
            case 61:
                if (i13 == 2) {
                    int zza4 = zzgmu.zza(bArr2, i10, zzgmv2);
                    int i16 = zzgmv2.zza;
                    if (i16 == 0) {
                        zza.putObject(t2, j2, zzgnb.zza);
                    } else {
                        zza.putObject(t2, j2, zzgnb.zza(bArr2, zza4, i16));
                        zza4 += i16;
                    }
                    zza.putInt(t2, j3, i12);
                    return zza4;
                }
                return i10;
            case 63:
                if (i13 == 0) {
                    int zza5 = zzgmu.zza(bArr2, i10, zzgmv2);
                    int i17 = zzgmv2.zza;
                    zzgop zza6 = this.zzf.zza(i12);
                    if (zza6 == null || zza6.zza(i17) != null) {
                        zza.putObject(t2, j2, Integer.valueOf(i17));
                        i9 = zza5;
                        zza.putInt(t2, j3, i12);
                        return i9;
                    }
                    zzd(t).zza(i11, Long.valueOf((long) i17));
                    return zza5;
                }
                return i10;
            case 66:
                if (i13 == 0) {
                    i9 = zzgmu.zza(bArr2, i10, zzgmv2);
                    zza.putObject(t2, j2, Integer.valueOf(zzgnk.zzg(zzgmv2.zza)));
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 67:
                if (i13 == 0) {
                    i9 = zzgmu.zzb(bArr2, i10, zzgmv2);
                    zza.putObject(t2, j2, Long.valueOf(zzgnk.zza(zzgmv2.zzb)));
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            case 68:
                if (i13 == 3) {
                    i9 = zza(zza(i12, i14), bArr, i, i2, (i11 & -8) | 4, zzgmv);
                    if (zza.getInt(t2, j3) == i12) {
                        obj2 = zza.getObject(t2, j2);
                    } else {
                        obj2 = null;
                    }
                    if (obj2 == null) {
                        zza.putObject(t2, j2, zzgmv2.zzc);
                    } else {
                        zza.putObject(t2, j2, zzgon.zza(obj2, zzgmv2.zzc));
                    }
                    zza.putInt(t2, j3, i12);
                    return i9;
                }
                return i10;
            default:
                return i10;
        }
    }

    private final zzgql zza(int i, int i2) {
        int i3 = i2 / 4;
        zzgql zzgql = (zzgql) this.zzu[i3];
        if (zzgql != null) {
            return zzgql;
        }
        zzgql zza2 = zzgqf.zza().zza((Class) this.zze.zza(i));
        this.zzu[i3] = zza2;
        return zza2;
    }

    /* JADX WARN: Type inference failed for: r30v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v71, types: [int] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
     arg types: [T, long, boolean]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
     arg types: [T, long, float]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:130:0x042a A[ADDED_TO_REGION] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final int zza(T r26, byte[] r27, int r28, int r29, int r30, com.google.android.gms.internal.zzgmv r31) throws java.io.IOException {
        /*
            r25 = this;
            r15 = r25
            r14 = r26
            r12 = r27
            r13 = r29
            r11 = r30
            r9 = r31
            r16 = 0
            r10 = -1
            r0 = r28
            r1 = 0
            r7 = 0
            r8 = -1
        L_0x0015:
            if (r0 >= r13) goto L_0x044b
            int r1 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0026
            int r0 = com.google.android.gms.internal.zzgmu.zza(r0, r12, r1, r9)
            int r1 = r9.zza
            r4 = r0
            r6 = r1
            goto L_0x0028
        L_0x0026:
            r6 = r0
            r4 = r1
        L_0x0028:
            int r5 = r6 >>> 3
            r3 = r6 & 7
            int r2 = r15.zzd(r5)
            if (r2 == r10) goto L_0x0418
            int[] r0 = r15.zzb
            int r1 = r2 + 1
            r1 = r0[r1]
            r17 = 267386880(0xff00000, float:2.3665827E-29)
            r17 = r1 & r17
            int r10 = r17 >>> 20
            r17 = 1048575(0xfffff, float:1.469367E-39)
            r11 = r1 & r17
            long r11 = (long) r11
            r28 = r1
            r1 = 17
            if (r10 > r1) goto L_0x02ea
            int r1 = r2 + 2
            r0 = r0[r1]
            int r1 = r0 >>> 20
            r13 = 1
            int r20 = r13 << r1
            r0 = r0 & r17
            if (r0 == r8) goto L_0x0074
            r1 = -1
            if (r8 == r1) goto L_0x0067
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            r21 = r11
            long r11 = (long) r8
            r1.putInt(r14, r11, r7)
            goto L_0x0069
        L_0x0067:
            r21 = r11
        L_0x0069:
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            long r7 = (long) r0
            int r1 = r1.getInt(r14, r7)
            r8 = r0
            r7 = r1
            goto L_0x0076
        L_0x0074:
            r21 = r11
        L_0x0076:
            r0 = 5
            switch(r10) {
                case 0: goto L_0x02c9;
                case 1: goto L_0x02ab;
                case 2: goto L_0x0283;
                case 3: goto L_0x0283;
                case 4: goto L_0x0262;
                case 5: goto L_0x023b;
                case 6: goto L_0x021b;
                case 7: goto L_0x01f4;
                case 8: goto L_0x01c7;
                case 9: goto L_0x018a;
                case 10: goto L_0x0167;
                case 11: goto L_0x0262;
                case 12: goto L_0x011f;
                case 13: goto L_0x021b;
                case 14: goto L_0x023b;
                case 15: goto L_0x00f9;
                case 16: goto L_0x00ce;
                case 17: goto L_0x0082;
                default: goto L_0x007a;
            }
        L_0x007a:
            r12 = r27
            r10 = r29
            r1 = r4
            r11 = -1
            goto L_0x02e5
        L_0x0082:
            r0 = 3
            if (r3 != r0) goto L_0x00c6
            int r0 = r5 << 3
            r10 = r0 | 4
            com.google.android.gms.internal.zzgql r0 = r15.zza(r5, r2)
            r11 = -1
            r1 = r27
            r2 = r4
            r3 = r29
            r4 = r10
            r5 = r31
            int r0 = zza(r0, r1, r2, r3, r4, r5)
            r1 = r7 & r20
            if (r1 != 0) goto L_0x00a9
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r2 = r9.zzc
            r12 = r21
            r1.putObject(r14, r12, r2)
            goto L_0x00ba
        L_0x00a9:
            r12 = r21
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r2 = r1.getObject(r14, r12)
            java.lang.Object r3 = r9.zzc
            java.lang.Object r2 = com.google.android.gms.internal.zzgon.zza(r2, r3)
            r1.putObject(r14, r12, r2)
        L_0x00ba:
            r7 = r7 | r20
            r12 = r27
            r13 = r29
            r11 = r30
            r1 = r6
            r10 = -1
            goto L_0x0015
        L_0x00c6:
            r11 = -1
            r12 = r27
            r10 = r29
            r1 = r4
            goto L_0x02e5
        L_0x00ce:
            r12 = r21
            r11 = -1
            if (r3 != 0) goto L_0x00f2
            r2 = r12
            r12 = r27
            int r10 = com.google.android.gms.internal.zzgmu.zzb(r12, r4, r9)
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            long r4 = r9.zzb
            long r4 = com.google.android.gms.internal.zzgnk.zza(r4)
            r1 = r26
            r0.putLong(r1, r2, r4)
            r7 = r7 | r20
            r13 = r29
            r11 = r30
            r1 = r6
            r0 = r10
            r10 = -1
            goto L_0x0015
        L_0x00f2:
            r12 = r27
            r10 = r29
            r1 = r4
            goto L_0x02e5
        L_0x00f9:
            r12 = r27
            r0 = r21
            r11 = -1
            if (r3 != 0) goto L_0x011a
            int r2 = com.google.android.gms.internal.zzgmu.zza(r12, r4, r9)
            sun.misc.Unsafe r3 = com.google.android.gms.internal.zzgpx.zza
            int r4 = r9.zza
            int r4 = com.google.android.gms.internal.zzgnk.zzg(r4)
            r3.putInt(r14, r0, r4)
            r7 = r7 | r20
            r13 = r29
            r11 = r30
            r0 = r2
            r1 = r6
            r10 = -1
            goto L_0x0015
        L_0x011a:
            r10 = r29
            r1 = r4
            goto L_0x02e5
        L_0x011f:
            r12 = r27
            r0 = r21
            r11 = -1
            if (r3 != 0) goto L_0x0162
            int r2 = com.google.android.gms.internal.zzgmu.zza(r12, r4, r9)
            int r3 = r9.zza
            com.google.android.gms.internal.zzgol<com.google.android.gms.internal.zzgop<?>> r4 = r15.zzf
            java.lang.Object r4 = r4.zza(r5)
            com.google.android.gms.internal.zzgop r4 = (com.google.android.gms.internal.zzgop) r4
            if (r4 == 0) goto L_0x0152
            com.google.android.gms.internal.zzgoo r4 = r4.zza(r3)
            if (r4 == 0) goto L_0x013d
            goto L_0x0152
        L_0x013d:
            com.google.android.gms.internal.zzgre r0 = zzd(r26)
            long r3 = (long) r3
            java.lang.Long r1 = java.lang.Long.valueOf(r3)
            r0.zza(r6, r1)
            r13 = r29
            r11 = r30
            r0 = r2
            r1 = r6
            r10 = -1
            goto L_0x0015
        L_0x0152:
            sun.misc.Unsafe r4 = com.google.android.gms.internal.zzgpx.zza
            r4.putInt(r14, r0, r3)
            r7 = r7 | r20
            r13 = r29
            r11 = r30
            r0 = r2
            r1 = r6
            r10 = -1
            goto L_0x0015
        L_0x0162:
            r10 = r29
            r1 = r4
            goto L_0x02e5
        L_0x0167:
            r12 = r27
            r0 = r21
            r11 = -1
            r2 = 2
            if (r3 != r2) goto L_0x0185
            int r2 = com.google.android.gms.internal.zzgmu.zze(r12, r4, r9)
            sun.misc.Unsafe r3 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r4 = r9.zzc
            r3.putObject(r14, r0, r4)
            r7 = r7 | r20
            r13 = r29
            r11 = r30
            r0 = r2
            r1 = r6
            r10 = -1
            goto L_0x0015
        L_0x0185:
            r10 = r29
            r1 = r4
            goto L_0x02e5
        L_0x018a:
            r12 = r27
            r0 = r21
            r11 = -1
            r10 = 2
            if (r3 != r10) goto L_0x01c2
            com.google.android.gms.internal.zzgql r2 = r15.zza(r5, r2)
            r10 = r29
            int r2 = zza(r2, r12, r4, r10, r9)
            r3 = r7 & r20
            if (r3 != 0) goto L_0x01a9
            sun.misc.Unsafe r3 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r4 = r9.zzc
            r3.putObject(r14, r0, r4)
            goto L_0x01b8
        L_0x01a9:
            sun.misc.Unsafe r3 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r4 = r3.getObject(r14, r0)
            java.lang.Object r5 = r9.zzc
            java.lang.Object r4 = com.google.android.gms.internal.zzgon.zza(r4, r5)
            r3.putObject(r14, r0, r4)
        L_0x01b8:
            r7 = r7 | r20
            r11 = r30
            r0 = r2
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x01c2:
            r10 = r29
            r1 = r4
            goto L_0x02e5
        L_0x01c7:
            r12 = r27
            r10 = r29
            r0 = r21
            r11 = -1
            r13 = 2
            if (r3 != r13) goto L_0x01f1
            r2 = 536870912(0x20000000, float:1.0842022E-19)
            r2 = r28 & r2
            if (r2 != 0) goto L_0x01dc
            int r2 = com.google.android.gms.internal.zzgmu.zzc(r12, r4, r9)
            goto L_0x01e0
        L_0x01dc:
            int r2 = com.google.android.gms.internal.zzgmu.zzd(r12, r4, r9)
        L_0x01e0:
            sun.misc.Unsafe r3 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r4 = r9.zzc
            r3.putObject(r14, r0, r4)
            r7 = r7 | r20
            r11 = r30
            r0 = r2
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x01f1:
            r1 = r4
            goto L_0x02e5
        L_0x01f4:
            r12 = r27
            r10 = r29
            r0 = r21
            r11 = -1
            if (r3 != 0) goto L_0x0218
            int r2 = com.google.android.gms.internal.zzgmu.zzb(r12, r4, r9)
            long r3 = r9.zzb
            r17 = 0
            int r5 = (r3 > r17 ? 1 : (r3 == r17 ? 0 : -1))
            if (r5 == 0) goto L_0x020a
            goto L_0x020b
        L_0x020a:
            r13 = 0
        L_0x020b:
            com.google.android.gms.internal.zzgrj.zza(r14, r0, r13)
            r7 = r7 | r20
            r11 = r30
            r0 = r2
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x0218:
            r1 = r4
            goto L_0x02e5
        L_0x021b:
            r12 = r27
            r10 = r29
            r1 = r21
            r11 = -1
            if (r3 != r0) goto L_0x0238
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            int r3 = com.google.android.gms.internal.zzgmu.zza(r12, r4)
            r0.putInt(r14, r1, r3)
            int r0 = r4 + 4
            r7 = r7 | r20
            r11 = r30
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x0238:
            r1 = r4
            goto L_0x02e5
        L_0x023b:
            r12 = r27
            r10 = r29
            r1 = r21
            r11 = -1
            if (r3 != r13) goto L_0x025e
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            long r17 = com.google.android.gms.internal.zzgmu.zzb(r12, r4)
            r2 = r1
            r1 = r26
            r13 = r4
            r4 = r17
            r0.putLong(r1, r2, r4)
            int r0 = r13 + 8
            r7 = r7 | r20
            r11 = r30
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x025e:
            r13 = r4
            r1 = r13
            goto L_0x02e5
        L_0x0262:
            r12 = r27
            r10 = r29
            r13 = r4
            r4 = r21
            r11 = -1
            if (r3 != 0) goto L_0x0280
            int r0 = com.google.android.gms.internal.zzgmu.zza(r12, r13, r9)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            int r2 = r9.zza
            r1.putInt(r14, r4, r2)
            r7 = r7 | r20
            r11 = r30
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x0280:
            r1 = r13
            goto L_0x02e5
        L_0x0283:
            r12 = r27
            r10 = r29
            r13 = r4
            r4 = r21
            r11 = -1
            if (r3 != 0) goto L_0x02a9
            int r13 = com.google.android.gms.internal.zzgmu.zzb(r12, r13, r9)
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            long r2 = r9.zzb
            r1 = r26
            r17 = r2
            r2 = r4
            r4 = r17
            r0.putLong(r1, r2, r4)
            r7 = r7 | r20
            r11 = r30
            r1 = r6
            r0 = r13
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x02a9:
            r1 = r13
            goto L_0x02e5
        L_0x02ab:
            r12 = r27
            r10 = r29
            r13 = r4
            r4 = r21
            r11 = -1
            if (r3 != r0) goto L_0x02c7
            float r0 = com.google.android.gms.internal.zzgmu.zzd(r12, r13)
            com.google.android.gms.internal.zzgrj.zza(r14, r4, r0)
            int r0 = r13 + 4
            r7 = r7 | r20
            r11 = r30
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x02c7:
            r1 = r13
            goto L_0x02e5
        L_0x02c9:
            r12 = r27
            r10 = r29
            r1 = r4
            r4 = r21
            r11 = -1
            if (r3 != r13) goto L_0x02e5
            double r2 = com.google.android.gms.internal.zzgmu.zzc(r12, r1)
            com.google.android.gms.internal.zzgrj.zza(r14, r4, r2)
            int r0 = r1 + 8
            r7 = r7 | r20
            r11 = r30
            r1 = r6
            r13 = r10
            r10 = -1
            goto L_0x0015
        L_0x02e5:
            r2 = r1
            r28 = r6
            goto L_0x0424
        L_0x02ea:
            r1 = r4
            r23 = r11
            r11 = -1
            r13 = 2
            r12 = r27
            r0 = 27
            if (r10 != r0) goto L_0x0346
            if (r3 != r13) goto L_0x033b
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            r3 = r23
            java.lang.Object r0 = r0.getObject(r14, r3)
            com.google.android.gms.internal.zzgos r0 = (com.google.android.gms.internal.zzgos) r0
            boolean r10 = r0.zza()
            if (r10 != 0) goto L_0x031e
            int r10 = r0.size()
            if (r10 != 0) goto L_0x0311
            r10 = 10
            goto L_0x0313
        L_0x0311:
            int r10 = r10 << 1
        L_0x0313:
            com.google.android.gms.internal.zzgos r0 = r0.zza(r10)
            sun.misc.Unsafe r10 = com.google.android.gms.internal.zzgpx.zza
            r10.putObject(r14, r3, r0)
            r10 = r0
            goto L_0x031f
        L_0x031e:
            r10 = r0
        L_0x031f:
            com.google.android.gms.internal.zzgql r0 = r15.zza(r5, r2)
            r4 = r1
            r1 = r6
            r2 = r27
            r3 = r4
            r4 = r29
            r5 = r10
            r10 = r6
            r6 = r31
            int r0 = zza(r0, r1, r2, r3, r4, r5, r6)
            r13 = r29
            r11 = r30
            r1 = r10
            r10 = -1
            goto L_0x0015
        L_0x033b:
            r4 = r1
            r10 = r6
            r15 = r4
            r23 = r7
            r24 = r8
            r28 = r10
            goto L_0x041f
        L_0x0346:
            r4 = r1
            r17 = r23
            r0 = 49
            if (r10 > r0) goto L_0x0398
            r1 = r28
            long r0 = (long) r1
            r19 = r0
            r0 = r25
            r1 = r26
            r21 = r2
            r2 = r27
            r13 = r3
            r3 = r4
            r15 = r4
            r4 = r29
            r22 = r5
            r5 = r6
            r28 = r6
            r6 = r22
            r23 = r7
            r7 = r13
            r24 = r8
            r8 = r21
            r11 = r10
            r13 = -1
            r9 = r19
            r12 = r17
            r14 = r31
            int r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            if (r0 != r15) goto L_0x0383
            r2 = r0
            r7 = r23
            r8 = r24
            goto L_0x0424
        L_0x0383:
            r10 = -1
            r15 = r25
            r14 = r26
            r12 = r27
            r1 = r28
            r13 = r29
            r11 = r30
            r9 = r31
            r7 = r23
            r8 = r24
            goto L_0x0015
        L_0x0398:
            r1 = r28
            r21 = r2
            r15 = r4
            r22 = r5
            r28 = r6
            r23 = r7
            r24 = r8
            r11 = r10
            r7 = r3
            r0 = 50
            if (r11 != r0) goto L_0x03e0
            if (r7 != r13) goto L_0x041f
            r0 = r25
            r1 = r26
            r2 = r27
            r3 = r15
            r4 = r29
            r5 = r22
            r6 = r17
            r8 = r31
            int r0 = r0.zza(r1, r2, r3, r4, r5, r6, r8)
            if (r0 != r15) goto L_0x03cb
            r2 = r0
            r7 = r23
            r8 = r24
            goto L_0x0424
        L_0x03cb:
            r10 = -1
            r15 = r25
            r14 = r26
            r12 = r27
            r1 = r28
            r13 = r29
            r11 = r30
            r9 = r31
            r7 = r23
            r8 = r24
            goto L_0x0015
        L_0x03e0:
            r0 = r25
            r8 = r1
            r1 = r26
            r2 = r27
            r3 = r15
            r4 = r29
            r5 = r28
            r6 = r22
            r9 = r11
            r10 = r17
            r12 = r21
            r13 = r31
            int r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 != r15) goto L_0x0403
            r2 = r0
            r7 = r23
            r8 = r24
            goto L_0x0424
        L_0x0403:
            r10 = -1
            r15 = r25
            r14 = r26
            r12 = r27
            r1 = r28
            r13 = r29
            r11 = r30
            r9 = r31
            r7 = r23
            r8 = r24
            goto L_0x0015
        L_0x0418:
            r15 = r4
            r28 = r6
            r23 = r7
            r24 = r8
        L_0x041f:
            r2 = r15
            r7 = r23
            r8 = r24
        L_0x0424:
            r9 = r28
            r6 = r30
            if (r9 != r6) goto L_0x042f
            if (r6 != 0) goto L_0x042d
            goto L_0x042f
        L_0x042d:
            r0 = r2
            goto L_0x0451
        L_0x042f:
            r0 = r9
            r1 = r27
            r3 = r29
            r4 = r26
            r5 = r31
            int r0 = zza(r0, r1, r2, r3, r4, r5)
            r10 = -1
            r15 = r25
            r14 = r26
            r12 = r27
            r13 = r29
            r11 = r6
            r1 = r9
            r9 = r31
            goto L_0x0015
        L_0x044b:
            r23 = r7
            r24 = r8
            r6 = r11
            r9 = r1
        L_0x0451:
            r1 = -1
            if (r8 == r1) goto L_0x045d
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            long r2 = (long) r8
            r4 = r26
            r1.putInt(r4, r2, r7)
            goto L_0x045f
        L_0x045d:
            r4 = r26
        L_0x045f:
            r1 = r25
            int[] r2 = r1.zzr
            if (r2 == 0) goto L_0x047d
            com.google.android.gms.internal.zzgre r2 = zzd(r26)
            int[] r3 = r1.zzr
            int r5 = r3.length
            r7 = r2
            r2 = 0
        L_0x046e:
            if (r2 >= r5) goto L_0x047d
            r8 = r3[r2]
            com.google.android.gms.internal.zzgrd<?, ?> r10 = r1.zzj
            java.lang.Object r7 = r1.zza(r4, r8, r7, r10)
            com.google.android.gms.internal.zzgre r7 = (com.google.android.gms.internal.zzgre) r7
            int r2 = r2 + 1
            goto L_0x046e
        L_0x047d:
            if (r6 != 0) goto L_0x0489
            r2 = r29
            if (r0 != r2) goto L_0x0484
            goto L_0x048f
        L_0x0484:
            com.google.android.gms.internal.zzgot r0 = com.google.android.gms.internal.zzgot.zzh()
            throw r0
        L_0x0489:
            r2 = r29
            if (r0 > r2) goto L_0x0490
            if (r9 != r6) goto L_0x0490
        L_0x048f:
            return r0
        L_0x0490:
            com.google.android.gms.internal.zzgot r0 = com.google.android.gms.internal.zzgot.zzh()
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.zzgmv):int");
    }

    /* JADX WARN: Type inference failed for: r1v31, types: [int] */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
     arg types: [T, long, boolean]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
     arg types: [T, long, float]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.zzgmv):int
     arg types: [T, byte[], int, int, int, com.google.android.gms.internal.zzgmv]
     candidates:
      com.google.android.gms.internal.zzgpx.zza(int, byte[], int, int, java.lang.Object, com.google.android.gms.internal.zzgmv):int
      com.google.android.gms.internal.zzgpx.zza(com.google.android.gms.internal.zzgql, byte[], int, int, int, com.google.android.gms.internal.zzgmv):int
      com.google.android.gms.internal.zzgpx.zza(byte[], int, int, com.google.android.gms.internal.zzgrr, java.lang.Class<?>, com.google.android.gms.internal.zzgmv):int
      com.google.android.gms.internal.zzgpx.zza(java.lang.Object, byte[], int, int, int, com.google.android.gms.internal.zzgmv):int */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(T r22, byte[] r23, int r24, int r25, com.google.android.gms.internal.zzgmv r26) throws java.io.IOException {
        /*
            r21 = this;
            r15 = r21
            r14 = r22
            r12 = r23
            r13 = r25
            r11 = r26
            boolean r0 = r15.zzn
            if (r0 == 0) goto L_0x026a
            r0 = r24
        L_0x0010:
            if (r0 >= r13) goto L_0x0260
            int r1 = r0 + 1
            byte r0 = r12[r0]
            if (r0 >= 0) goto L_0x0022
            int r0 = com.google.android.gms.internal.zzgmu.zza(r0, r12, r1, r11)
            int r1 = r11.zza
            r9 = r0
            r16 = r1
            goto L_0x0025
        L_0x0022:
            r16 = r0
            r9 = r1
        L_0x0025:
            int r6 = r16 >>> 3
            r7 = r16 & 7
            int r10 = r15.zzd(r6)
            if (r10 < 0) goto L_0x0244
            int[] r0 = r15.zzb
            int r1 = r10 + 1
            r8 = r0[r1]
            r0 = 267386880(0xff00000, float:2.3665827E-29)
            r0 = r0 & r8
            int r5 = r0 >>> 20
            r0 = 1048575(0xfffff, float:1.469367E-39)
            r0 = r0 & r8
            long r3 = (long) r0
            r0 = 17
            r1 = 2
            if (r5 > r0) goto L_0x0173
            r0 = 5
            r2 = 1
            switch(r5) {
                case 0: goto L_0x0163;
                case 1: goto L_0x0153;
                case 2: goto L_0x013c;
                case 3: goto L_0x013c;
                case 4: goto L_0x012a;
                case 5: goto L_0x0114;
                case 6: goto L_0x0102;
                case 7: goto L_0x00ea;
                case 8: goto L_0x00ce;
                case 9: goto L_0x00a2;
                case 10: goto L_0x0090;
                case 11: goto L_0x012a;
                case 12: goto L_0x007f;
                case 13: goto L_0x0102;
                case 14: goto L_0x0114;
                case 15: goto L_0x006a;
                case 16: goto L_0x0050;
                default: goto L_0x004d;
            }
        L_0x004d:
            r15 = r9
            goto L_0x0245
        L_0x0050:
            if (r7 != 0) goto L_0x0067
            int r6 = com.google.android.gms.internal.zzgmu.zzb(r12, r9, r11)
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            long r1 = r11.zzb
            long r7 = com.google.android.gms.internal.zzgnk.zza(r1)
            r1 = r22
            r2 = r3
            r4 = r7
            r0.putLong(r1, r2, r4)
            r0 = r6
            goto L_0x0010
        L_0x0067:
            r15 = r9
            goto L_0x0245
        L_0x006a:
            if (r7 != 0) goto L_0x007c
            int r0 = com.google.android.gms.internal.zzgmu.zza(r12, r9, r11)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            int r2 = r11.zza
            int r2 = com.google.android.gms.internal.zzgnk.zzg(r2)
            r1.putInt(r14, r3, r2)
            goto L_0x0010
        L_0x007c:
            r15 = r9
            goto L_0x0245
        L_0x007f:
            if (r7 != 0) goto L_0x008d
            int r0 = com.google.android.gms.internal.zzgmu.zza(r12, r9, r11)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            int r2 = r11.zza
            r1.putInt(r14, r3, r2)
            goto L_0x0010
        L_0x008d:
            r15 = r9
            goto L_0x0245
        L_0x0090:
            if (r7 != r1) goto L_0x009f
            int r0 = com.google.android.gms.internal.zzgmu.zze(r12, r9, r11)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r2 = r11.zzc
            r1.putObject(r14, r3, r2)
            goto L_0x0010
        L_0x009f:
            r15 = r9
            goto L_0x0245
        L_0x00a2:
            if (r7 != r1) goto L_0x00cb
            com.google.android.gms.internal.zzgql r0 = r15.zza(r6, r10)
            int r0 = zza(r0, r12, r9, r13, r11)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r1 = r1.getObject(r14, r3)
            if (r1 != 0) goto L_0x00be
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r2 = r11.zzc
            r1.putObject(r14, r3, r2)
            goto L_0x0010
        L_0x00be:
            sun.misc.Unsafe r2 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r5 = r11.zzc
            java.lang.Object r1 = com.google.android.gms.internal.zzgon.zza(r1, r5)
            r2.putObject(r14, r3, r1)
            goto L_0x0010
        L_0x00cb:
            r15 = r9
            goto L_0x0245
        L_0x00ce:
            if (r7 != r1) goto L_0x00e7
            r0 = 536870912(0x20000000, float:1.0842022E-19)
            r0 = r0 & r8
            if (r0 != 0) goto L_0x00da
            int r0 = com.google.android.gms.internal.zzgmu.zzc(r12, r9, r11)
            goto L_0x00de
        L_0x00da:
            int r0 = com.google.android.gms.internal.zzgmu.zzd(r12, r9, r11)
        L_0x00de:
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r2 = r11.zzc
            r1.putObject(r14, r3, r2)
            goto L_0x0010
        L_0x00e7:
            r15 = r9
            goto L_0x0245
        L_0x00ea:
            if (r7 != 0) goto L_0x00ff
            int r0 = com.google.android.gms.internal.zzgmu.zzb(r12, r9, r11)
            long r5 = r11.zzb
            r7 = 0
            int r1 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x00f9
            goto L_0x00fa
        L_0x00f9:
            r2 = 0
        L_0x00fa:
            com.google.android.gms.internal.zzgrj.zza(r14, r3, r2)
            goto L_0x0010
        L_0x00ff:
            r15 = r9
            goto L_0x0245
        L_0x0102:
            if (r7 != r0) goto L_0x0111
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            int r1 = com.google.android.gms.internal.zzgmu.zza(r12, r9)
            r0.putInt(r14, r3, r1)
            int r0 = r9 + 4
            goto L_0x0010
        L_0x0111:
            r15 = r9
            goto L_0x0245
        L_0x0114:
            if (r7 != r2) goto L_0x0127
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            long r5 = com.google.android.gms.internal.zzgmu.zzb(r12, r9)
            r1 = r22
            r2 = r3
            r4 = r5
            r0.putLong(r1, r2, r4)
            int r0 = r9 + 8
            goto L_0x0010
        L_0x0127:
            r15 = r9
            goto L_0x0245
        L_0x012a:
            if (r7 != 0) goto L_0x0139
            int r0 = com.google.android.gms.internal.zzgmu.zza(r12, r9, r11)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            int r2 = r11.zza
            r1.putInt(r14, r3, r2)
            goto L_0x0010
        L_0x0139:
            r15 = r9
            goto L_0x0245
        L_0x013c:
            if (r7 != 0) goto L_0x0150
            int r6 = com.google.android.gms.internal.zzgmu.zzb(r12, r9, r11)
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            long r7 = r11.zzb
            r1 = r22
            r2 = r3
            r4 = r7
            r0.putLong(r1, r2, r4)
            r0 = r6
            goto L_0x0010
        L_0x0150:
            r15 = r9
            goto L_0x0245
        L_0x0153:
            if (r7 != r0) goto L_0x0160
            float r0 = com.google.android.gms.internal.zzgmu.zzd(r12, r9)
            com.google.android.gms.internal.zzgrj.zza(r14, r3, r0)
            int r0 = r9 + 4
            goto L_0x0010
        L_0x0160:
            r15 = r9
            goto L_0x0245
        L_0x0163:
            if (r7 != r2) goto L_0x0170
            double r0 = com.google.android.gms.internal.zzgmu.zzc(r12, r9)
            com.google.android.gms.internal.zzgrj.zza(r14, r3, r0)
            int r0 = r9 + 8
            goto L_0x0010
        L_0x0170:
            r15 = r9
            goto L_0x0245
        L_0x0173:
            r0 = 27
            if (r5 != r0) goto L_0x01b6
            if (r7 != r1) goto L_0x01b3
            sun.misc.Unsafe r0 = com.google.android.gms.internal.zzgpx.zza
            java.lang.Object r0 = r0.getObject(r14, r3)
            com.google.android.gms.internal.zzgos r0 = (com.google.android.gms.internal.zzgos) r0
            boolean r1 = r0.zza()
            if (r1 != 0) goto L_0x019e
            int r1 = r0.size()
            if (r1 != 0) goto L_0x0191
            r1 = 10
            goto L_0x0193
        L_0x0191:
            int r1 = r1 << 1
        L_0x0193:
            com.google.android.gms.internal.zzgos r0 = r0.zza(r1)
            sun.misc.Unsafe r1 = com.google.android.gms.internal.zzgpx.zza
            r1.putObject(r14, r3, r0)
            r5 = r0
            goto L_0x019f
        L_0x019e:
            r5 = r0
        L_0x019f:
            com.google.android.gms.internal.zzgql r0 = r15.zza(r6, r10)
            r1 = r16
            r2 = r23
            r3 = r9
            r4 = r25
            r6 = r26
            int r0 = zza(r0, r1, r2, r3, r4, r5, r6)
            goto L_0x0010
        L_0x01b3:
            r15 = r9
            goto L_0x0245
        L_0x01b6:
            r0 = 49
            if (r5 > r0) goto L_0x01ed
            long r1 = (long) r8
            r0 = r21
            r17 = r1
            r1 = r22
            r2 = r23
            r19 = r3
            r3 = r9
            r4 = r25
            r8 = r5
            r5 = r16
            r24 = r8
            r8 = r10
            r15 = r9
            r9 = r17
            r11 = r24
            r12 = r19
            r14 = r26
            int r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r11, r12, r14)
            if (r0 != r15) goto L_0x01e1
            r2 = r0
            goto L_0x0246
        L_0x01e1:
            r15 = r21
            r14 = r22
            r12 = r23
            r13 = r25
            r11 = r26
            goto L_0x0010
        L_0x01ed:
            r19 = r3
            r24 = r5
            r15 = r9
            r0 = 50
            r9 = r24
            if (r9 != r0) goto L_0x021e
            if (r7 != r1) goto L_0x0245
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r15
            r4 = r25
            r5 = r6
            r6 = r19
            r8 = r26
            int r0 = r0.zza(r1, r2, r3, r4, r5, r6, r8)
            if (r0 != r15) goto L_0x0212
            r2 = r0
            goto L_0x0246
        L_0x0212:
            r15 = r21
            r14 = r22
            r12 = r23
            r13 = r25
            r11 = r26
            goto L_0x0010
        L_0x021e:
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r15
            r4 = r25
            r5 = r16
            r12 = r10
            r10 = r19
            r13 = r26
            int r0 = r0.zza(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r12, r13)
            if (r0 != r15) goto L_0x0238
            r2 = r0
            goto L_0x0246
        L_0x0238:
            r15 = r21
            r14 = r22
            r12 = r23
            r13 = r25
            r11 = r26
            goto L_0x0010
        L_0x0244:
            r15 = r9
        L_0x0245:
            r2 = r15
        L_0x0246:
            r0 = r16
            r1 = r23
            r3 = r25
            r4 = r22
            r5 = r26
            int r0 = zza(r0, r1, r2, r3, r4, r5)
            r15 = r21
            r14 = r22
            r12 = r23
            r13 = r25
            r11 = r26
            goto L_0x0010
        L_0x0260:
            r4 = r25
            if (r0 != r4) goto L_0x0265
            return
        L_0x0265:
            com.google.android.gms.internal.zzgot r0 = com.google.android.gms.internal.zzgot.zzh()
            throw r0
        L_0x026a:
            r4 = r13
            r5 = 0
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            r4 = r25
            r6 = r26
            r0.zza(r1, r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgpx.zza(java.lang.Object, byte[], int, int, com.google.android.gms.internal.zzgmv):void");
    }

    public final void zzc(T t) {
        int[] iArr = this.zzr;
        if (iArr != null) {
            for (int zza2 : iArr) {
                long zza3 = (long) (zza(zza2) & 1048575);
                Object zzf2 = zzgrj.zzf(t, zza3);
                if (zzf2 != null) {
                    zzgrj.zza(t, zza3, this.zzo.zzd(zzf2));
                }
            }
        }
        int[] iArr2 = this.zzs;
        if (iArr2 != null) {
            for (int i : iArr2) {
                this.zzh.zzb(t, (long) i);
            }
        }
        this.zzj.zzd(t);
        if (this.zzk) {
            this.zzl.zzc(t);
        }
    }

    private final <UT, UB> UB zza(Object obj, int i, UB ub, zzgrd<UT, UB> zzgrd) {
        zzgop zza2;
        int i2 = this.zzb[i];
        Object zzf2 = zzgrj.zzf(obj, (long) (zza(i) & 1048575));
        if (zzf2 == null || (zza2 = this.zzf.zza(i2)) == null) {
            return ub;
        }
        return zza(i2, this.zzo.zza(zzf2), zza2, ub, zzgrd);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, com.google.android.gms.internal.zzgnb):void
     arg types: [UB, int, com.google.android.gms.internal.zzgnb]
     candidates:
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, int):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, long):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, java.lang.Object):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, com.google.android.gms.internal.zzgnb):void */
    private final <K, V, UT, UB> UB zza(int i, Map<K, V> map, zzgop<?> zzgop, UB ub, zzgrd<UT, UB> zzgrd) {
        zzgpm<?, ?> zzf2 = this.zzo.zzf(this.zzp.zza(i));
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next = it.next();
            if (zzgop.zza(((Integer) next.getValue()).intValue()) == null) {
                if (ub == null) {
                    ub = zzgrd.zza();
                }
                zzgng zzb2 = zzgnb.zzb(zzgpl.zza(zzf2, next.getKey(), next.getValue()));
                try {
                    zzgpl.zza(zzb2.zzb(), zzf2, next.getKey(), next.getValue());
                    zzgrd.zza((Object) ub, i, zzb2.zza());
                    it.remove();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return ub;
    }

    private final void zza(Object obj, int i, zzgqk zzgqk) throws IOException {
        if (zzc(i)) {
            zzgrj.zza(obj, (long) (i & 1048575), zzgqk.zzm());
        } else if (this.zzm) {
            zzgrj.zza(obj, (long) (i & 1048575), zzgqk.zzl());
        } else {
            zzgrj.zza(obj, (long) (i & 1048575), zzgqk.zzn());
        }
    }

    private final int zza(int i) {
        return this.zzb[i + 1];
    }

    private final int zzb(int i) {
        return this.zzb[i + 2];
    }

    private final boolean zzc(T t, T t2, int i) {
        return zza(t, i) == zza(t2, i);
    }

    private final boolean zza(T t, int i) {
        if (this.zzn) {
            int zza2 = zza(i);
            long j = (long) (zza2 & 1048575);
            switch ((zza2 & 267386880) >>> 20) {
                case 0:
                    return zzgrj.zze(t, j) != 0.0d;
                case 1:
                    return zzgrj.zzd(t, j) != 0.0f;
                case 2:
                    return zzgrj.zzb(t, j) != 0;
                case 3:
                    return zzgrj.zzb(t, j) != 0;
                case 4:
                    return zzgrj.zza(t, j) != 0;
                case 5:
                    return zzgrj.zzb(t, j) != 0;
                case 6:
                    return zzgrj.zza(t, j) != 0;
                case 7:
                    return zzgrj.zzc(t, j);
                case 8:
                    Object zzf2 = zzgrj.zzf(t, j);
                    if (zzf2 instanceof String) {
                        return !((String) zzf2).isEmpty();
                    }
                    if (zzf2 instanceof zzgnb) {
                        return !zzgnb.zza.equals(zzf2);
                    }
                    throw new IllegalArgumentException();
                case 9:
                    return zzgrj.zzf(t, j) != null;
                case 10:
                    return !zzgnb.zza.equals(zzgrj.zzf(t, j));
                case 11:
                    return zzgrj.zza(t, j) != 0;
                case 12:
                    return zzgrj.zza(t, j) != 0;
                case 13:
                    return zzgrj.zza(t, j) != 0;
                case 14:
                    return zzgrj.zzb(t, j) != 0;
                case 15:
                    return zzgrj.zza(t, j) != 0;
                case 16:
                    return zzgrj.zzb(t, j) != 0;
                case 17:
                    return zzgrj.zzf(t, j) != null;
                default:
                    throw new IllegalArgumentException();
            }
        } else {
            int zzb2 = zzb(i);
            return (zzgrj.zza(t, (long) (zzb2 & 1048575)) & (1 << (zzb2 >>> 20))) != 0;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
     arg types: [T, long, int]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void */
    private final void zzb(T t, int i) {
        if (!this.zzn) {
            int zzb2 = zzb(i);
            long j = (long) (zzb2 & 1048575);
            zzgrj.zza((Object) t, j, zzgrj.zza(t, j) | (1 << (zzb2 >>> 20)));
        }
    }

    private final boolean zza(T t, int i, int i2) {
        return zzgrj.zza(t, (long) (zzb(i2) & 1048575)) == i;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void
     arg types: [T, long, int]
     candidates:
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, double):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, float):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, long):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, java.lang.Object):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, boolean):void
      com.google.android.gms.internal.zzgrj.zza(byte[], long, byte):void
      com.google.android.gms.internal.zzgrj.zza(java.lang.Object, long, int):void */
    private final void zzb(T t, int i, int i2) {
        zzgrj.zza((Object) t, (long) (zzb(i2) & 1048575), i);
    }

    private final int zzd(int i) {
        int i2 = this.zzc;
        if (i >= i2) {
            int i3 = this.zzt;
            if (i < i3) {
                int i4 = (i - i2) << 2;
                if (this.zzb[i4] == i) {
                    return i4;
                }
                return -1;
            } else if (i <= this.zzd) {
                int i5 = i3 - i2;
                int length = (this.zzb.length / 4) - 1;
                while (i5 <= length) {
                    int i6 = (length + i5) >>> 1;
                    int i7 = i6 << 2;
                    int i8 = this.zzb[i7];
                    if (i == i8) {
                        return i7;
                    }
                    if (i < i8) {
                        length = i6 - 1;
                    } else {
                        i5 = i6 + 1;
                    }
                }
                return -1;
            }
        }
        return -1;
    }
}
