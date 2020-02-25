package com.google.android.gms.internal;

import com.google.common.base.Ascii;
import com.google.common.primitives.UnsignedBytes;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/* compiled from: BinaryReader */
final class zzgmy extends zzgmw {
    private final boolean zza = true;
    private final byte[] zzb;
    private final int zzd;
    private int zzc;
    private int zze;
    private int zzf;
    private int zzg;

    public zzgmy(ByteBuffer byteBuffer, boolean z) {
        super(null);
        this.zzb = byteBuffer.array();
        int arrayOffset = byteBuffer.arrayOffset() + byteBuffer.position();
        this.zzc = arrayOffset;
        this.zzd = arrayOffset;
        this.zze = byteBuffer.arrayOffset() + byteBuffer.limit();
    }

    private final boolean zzu() {
        return this.zzc == this.zze;
    }

    public final int zza() throws IOException {
        if (zzu()) {
            return Integer.MAX_VALUE;
        }
        this.zzf = zzv();
        int i = this.zzf;
        if (i == this.zzg) {
            return Integer.MAX_VALUE;
        }
        return i >>> 3;
    }

    public final int zzb() {
        return this.zzf;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x003b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zzc() throws java.io.IOException {
        /*
            r7 = this;
            boolean r0 = r7.zzu()
            r1 = 0
            if (r0 != 0) goto L_0x008e
            int r0 = r7.zzf
            int r2 = r7.zzg
            if (r0 != r2) goto L_0x000f
            goto L_0x008e
        L_0x000f:
            r3 = r0 & 7
            r4 = 1
            if (r3 == 0) goto L_0x005d
            if (r3 == r4) goto L_0x0057
            r1 = 2
            if (r3 == r1) goto L_0x004f
            r1 = 4
            r5 = 3
            if (r3 == r5) goto L_0x002a
            r0 = 5
            if (r3 != r0) goto L_0x0025
            r7.zza(r1)
            return r4
        L_0x0025:
            com.google.android.gms.internal.zzgou r0 = com.google.android.gms.internal.zzgot.zzf()
            throw r0
        L_0x002a:
            int r0 = r0 >>> r5
            int r0 = r0 << r5
            r0 = r0 | r1
            r7.zzg = r0
        L_0x0032:
            int r0 = r7.zza()
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r0 == r1) goto L_0x0041
            boolean r0 = r7.zzc()
            if (r0 != 0) goto L_0x0032
        L_0x0041:
            int r0 = r7.zzf
            int r1 = r7.zzg
            if (r0 != r1) goto L_0x004a
            r7.zzg = r2
            return r4
        L_0x004a:
            com.google.android.gms.internal.zzgot r0 = com.google.android.gms.internal.zzgot.zzh()
            throw r0
        L_0x004f:
            int r0 = r7.zzv()
            r7.zza(r0)
            return r4
        L_0x0057:
            r0 = 8
            r7.zza(r0)
            return r4
        L_0x005d:
            int r0 = r7.zze
            int r2 = r7.zzc
            int r0 = r0 - r2
            r3 = 10
            if (r0 < r3) goto L_0x007b
            byte[] r0 = r7.zzb
            r5 = r2
            r2 = 0
        L_0x006c:
            if (r2 >= r3) goto L_0x007b
            int r6 = r5 + 1
            byte r5 = r0[r5]
            if (r5 < 0) goto L_0x0077
            r7.zzc = r6
            goto L_0x0088
        L_0x0077:
            int r2 = r2 + 1
            r5 = r6
            goto L_0x006c
        L_0x007b:
        L_0x007d:
            if (r1 >= r3) goto L_0x0089
            byte r0 = r7.zzy()
            if (r0 >= 0) goto L_0x0088
            int r1 = r1 + 1
            goto L_0x007d
        L_0x0088:
            return r4
        L_0x0089:
            com.google.android.gms.internal.zzgot r0 = com.google.android.gms.internal.zzgot.zzc()
            throw r0
        L_0x008e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgmy.zzc():boolean");
    }

    public final double zzd() throws IOException {
        zzc(1);
        return Double.longBitsToDouble(zzaa());
    }

    public final float zze() throws IOException {
        zzc(5);
        return Float.intBitsToFloat(zzz());
    }

    public final long zzf() throws IOException {
        zzc(0);
        return zzw();
    }

    public final long zzg() throws IOException {
        zzc(0);
        return zzw();
    }

    public final int zzh() throws IOException {
        zzc(0);
        return zzv();
    }

    public final long zzi() throws IOException {
        zzc(1);
        return zzaa();
    }

    public final int zzj() throws IOException {
        zzc(5);
        return zzz();
    }

    public final boolean zzk() throws IOException {
        zzc(0);
        if (zzv() != 0) {
            return true;
        }
        return false;
    }

    public final String zzl() throws IOException {
        return zza(false);
    }

    public final String zzm() throws IOException {
        return zza(true);
    }

    private final String zza(boolean z) throws IOException {
        zzc(2);
        int zzv = zzv();
        if (zzv == 0) {
            return "";
        }
        zzb(zzv);
        if (z) {
            byte[] bArr = this.zzb;
            int i = this.zzc;
            if (!zzgrl.zza(bArr, i, i + zzv)) {
                throw zzgot.zzi();
            }
        }
        String str = new String(this.zzb, this.zzc, zzv, zzgon.zza);
        this.zzc += zzv;
        return str;
    }

    public final <T> T zza(Class<T> cls, zzgnv zzgnv) throws IOException {
        zzc(2);
        return zzc(zzgqf.zza().zza((Class) cls), zzgnv);
    }

    public final <T> T zza(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        zzc(2);
        return zzc(zzgql, zzgnv);
    }

    private final <T> T zzc(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int zzv = zzv();
        zzb(zzv);
        int i = this.zze;
        int i2 = this.zzc + zzv;
        this.zze = i2;
        try {
            T zza2 = zzgql.zza();
            zzgql.zza(zza2, this, zzgnv);
            if (zza2 instanceof zzgoj) {
                ((zzgoj) zza2).zzl();
            } else {
                zzgql.zzc(zza2);
            }
            if (this.zzc == i2) {
                return zza2;
            }
            throw zzgot.zzh();
        } finally {
            this.zze = i;
        }
    }

    public final <T> T zzb(Class<T> cls, zzgnv zzgnv) throws IOException {
        zzc(3);
        return zzd(zzgqf.zza().zza((Class) cls), zzgnv);
    }

    public final <T> T zzb(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        zzc(3);
        return zzd(zzgql, zzgnv);
    }

    private final <T> T zzd(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int i = this.zzg;
        this.zzg = ((this.zzf >>> 3) << 3) | 4;
        try {
            T zza2 = zzgql.zza();
            zzgql.zza(zza2, this, zzgnv);
            zzgql.zzc(zza2);
            if (this.zzf == this.zzg) {
                return zza2;
            }
            throw zzgot.zzh();
        } finally {
            this.zzg = i;
        }
    }

    public final zzgnb zzn() throws IOException {
        zzgnb zzgnb;
        zzc(2);
        int zzv = zzv();
        if (zzv == 0) {
            return zzgnb.zza;
        }
        zzb(zzv);
        if (this.zza) {
            zzgnb = zzgnb.zzb(this.zzb, this.zzc, zzv);
        } else {
            zzgnb = zzgnb.zza(this.zzb, this.zzc, zzv);
        }
        this.zzc += zzv;
        return zzgnb;
    }

    public final int zzo() throws IOException {
        zzc(0);
        return zzv();
    }

    public final int zzp() throws IOException {
        zzc(0);
        return zzv();
    }

    public final int zzq() throws IOException {
        zzc(5);
        return zzz();
    }

    public final long zzr() throws IOException {
        zzc(1);
        return zzaa();
    }

    public final int zzs() throws IOException {
        zzc(0);
        return zzgnk.zzg(zzv());
    }

    public final long zzt() throws IOException {
        zzc(0);
        return zzgnk.zza(zzw());
    }

    public final void zza(List<Double> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgns) {
            zzgns zzgns = (zzgns) list;
            int i3 = this.zzf & 7;
            if (i3 == 1) {
                do {
                    zzgns.zza(zzd());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = zzv();
                zzd(zzv);
                int i4 = this.zzc + zzv;
                while (this.zzc < i4) {
                    zzgns.zza(Double.longBitsToDouble(zzac()));
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i5 = this.zzf & 7;
            if (i5 == 1) {
                do {
                    list.add(Double.valueOf(zzd()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i5 == 2) {
                int zzv2 = zzv();
                zzd(zzv2);
                int i6 = this.zzc + zzv2;
                while (this.zzc < i6) {
                    list.add(Double.valueOf(Double.longBitsToDouble(zzac())));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzb(List<Float> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgog) {
            zzgog zzgog = (zzgog) list;
            int i3 = this.zzf & 7;
            if (i3 == 2) {
                int zzv = zzv();
                zze(zzv);
                int i4 = this.zzc + zzv;
                while (this.zzc < i4) {
                    zzgog.zza(Float.intBitsToFloat(zzab()));
                }
            } else if (i3 == 5) {
                do {
                    zzgog.zza(zze());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i5 = this.zzf & 7;
            if (i5 == 2) {
                int zzv2 = zzv();
                zze(zzv2);
                int i6 = this.zzc + zzv2;
                while (this.zzc < i6) {
                    list.add(Float.valueOf(Float.intBitsToFloat(zzab())));
                }
            } else if (i5 == 5) {
                do {
                    list.add(Float.valueOf(zze()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzc(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgph.zza(zzf());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgph.zza(zzw());
                }
                zzf(zzv);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Long.valueOf(zzf()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Long.valueOf(zzw()));
                }
                zzf(zzv2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzd(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgph.zza(zzg());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgph.zza(zzw());
                }
                zzf(zzv);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Long.valueOf(zzg()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Long.valueOf(zzw()));
                }
                zzf(zzv2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zze(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgom.zzd(zzh());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgom.zzd(zzv());
                }
                zzf(zzv);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Integer.valueOf(zzh()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzv()));
                }
                zzf(zzv2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzf(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i3 = this.zzf & 7;
            if (i3 == 1) {
                do {
                    zzgph.zza(zzi());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = zzv();
                zzd(zzv);
                int i4 = this.zzc + zzv;
                while (this.zzc < i4) {
                    zzgph.zza(zzac());
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i5 = this.zzf & 7;
            if (i5 == 1) {
                do {
                    list.add(Long.valueOf(zzi()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i5 == 2) {
                int zzv2 = zzv();
                zzd(zzv2);
                int i6 = this.zzc + zzv2;
                while (this.zzc < i6) {
                    list.add(Long.valueOf(zzac()));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzg(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i3 = this.zzf & 7;
            if (i3 == 2) {
                int zzv = zzv();
                zze(zzv);
                int i4 = this.zzc + zzv;
                while (this.zzc < i4) {
                    zzgom.zzd(zzab());
                }
            } else if (i3 == 5) {
                do {
                    zzgom.zzd(zzj());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i5 = this.zzf & 7;
            if (i5 == 2) {
                int zzv2 = zzv();
                zze(zzv2);
                int i6 = this.zzc + zzv2;
                while (this.zzc < i6) {
                    list.add(Integer.valueOf(zzab()));
                }
            } else if (i5 == 5) {
                do {
                    list.add(Integer.valueOf(zzj()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzh(List<Boolean> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgmz) {
            zzgmz zzgmz = (zzgmz) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgmz.zza(zzk());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgmz.zza(zzv() != 0);
                }
                zzf(zzv);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Boolean.valueOf(zzk()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Boolean.valueOf(zzv() != 0));
                }
                zzf(zzv2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgmy.zza(java.util.List<java.lang.String>, boolean):void
     arg types: [java.util.List<java.lang.String>, int]
     candidates:
      com.google.android.gms.internal.zzgmy.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgmy.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgmy.zza(java.util.List<java.lang.String>, boolean):void */
    public final void zzi(List<String> list) throws IOException {
        zza(list, false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgmy.zza(java.util.List<java.lang.String>, boolean):void
     arg types: [java.util.List<java.lang.String>, int]
     candidates:
      com.google.android.gms.internal.zzgmy.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgmy.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgmy.zza(java.util.List<java.lang.String>, boolean):void */
    public final void zzj(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int i;
        int i2;
        if ((this.zzf & 7) != 2) {
            throw zzgot.zzf();
        } else if (!(list instanceof zzgpc) || z) {
            do {
                list.add(zza(z));
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i;
        } else {
            zzgpc zzgpc = (zzgpc) list;
            do {
                zzgpc.zza(zzn());
                if (!zzu()) {
                    i2 = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i2;
        }
    }

    public final <T> void zza(List<T> list, zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int i;
        int i2 = this.zzf;
        if ((i2 & 7) == 2) {
            do {
                list.add(zzc(zzgql, zzgnv));
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == i2);
            this.zzc = i;
            return;
        }
        throw zzgot.zzf();
    }

    public final <T> void zzb(List<T> list, zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int i;
        int i2 = this.zzf;
        if ((i2 & 7) == 3) {
            do {
                list.add(zzd(zzgql, zzgnv));
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == i2);
            this.zzc = i;
            return;
        }
        throw zzgot.zzf();
    }

    public final void zzk(List<zzgnb> list) throws IOException {
        int i;
        if ((this.zzf & 7) == 2) {
            do {
                list.add(zzn());
                if (!zzu()) {
                    i = this.zzc;
                } else {
                    return;
                }
            } while (zzv() == this.zzf);
            this.zzc = i;
            return;
        }
        throw zzgot.zzf();
    }

    public final void zzl(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgom.zzd(zzo());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgom.zzd(zzv());
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Integer.valueOf(zzo()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzv()));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzm(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgom.zzd(zzp());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgom.zzd(zzv());
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Integer.valueOf(zzp()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzv()));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzn(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i3 = this.zzf & 7;
            if (i3 == 2) {
                int zzv = zzv();
                zze(zzv);
                int i4 = this.zzc + zzv;
                while (this.zzc < i4) {
                    zzgom.zzd(zzab());
                }
            } else if (i3 == 5) {
                do {
                    zzgom.zzd(zzq());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i5 = this.zzf & 7;
            if (i5 == 2) {
                int zzv2 = zzv();
                zze(zzv2);
                int i6 = this.zzc + zzv2;
                while (this.zzc < i6) {
                    list.add(Integer.valueOf(zzab()));
                }
            } else if (i5 == 5) {
                do {
                    list.add(Integer.valueOf(zzq()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzo(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i3 = this.zzf & 7;
            if (i3 == 1) {
                do {
                    zzgph.zza(zzr());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = zzv();
                zzd(zzv);
                int i4 = this.zzc + zzv;
                while (this.zzc < i4) {
                    zzgph.zza(zzac());
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i5 = this.zzf & 7;
            if (i5 == 1) {
                do {
                    list.add(Long.valueOf(zzr()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i5 == 2) {
                int zzv2 = zzv();
                zzd(zzv2);
                int i6 = this.zzc + zzv2;
                while (this.zzc < i6) {
                    list.add(Long.valueOf(zzac()));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzp(List<Integer> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgom.zzd(zzs());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgom.zzd(zzgnk.zzg(zzv()));
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Integer.valueOf(zzs()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Integer.valueOf(zzgnk.zzg(zzv())));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzq(List<Long> list) throws IOException {
        int i;
        int i2;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i3 = this.zzf & 7;
            if (i3 == 0) {
                do {
                    zzgph.zza(zzt());
                    if (!zzu()) {
                        i2 = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i2;
            } else if (i3 == 2) {
                int zzv = this.zzc + zzv();
                while (this.zzc < zzv) {
                    zzgph.zza(zzgnk.zza(zzw()));
                }
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i4 = this.zzf & 7;
            if (i4 == 0) {
                do {
                    list.add(Long.valueOf(zzt()));
                    if (!zzu()) {
                        i = this.zzc;
                    } else {
                        return;
                    }
                } while (zzv() == this.zzf);
                this.zzc = i;
            } else if (i4 == 2) {
                int zzv2 = this.zzc + zzv();
                while (this.zzc < zzv2) {
                    list.add(Long.valueOf(zzgnk.zza(zzw())));
                }
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final <K, V> void zza(Map<K, V> map, zzgpm<K, V> zzgpm, zzgnv zzgnv) throws IOException {
        zzc(2);
        int zzv = zzv();
        zzb(zzv);
        int i = this.zze;
        this.zze = this.zzc + zzv;
        try {
            K k = zzgpm.zzb;
            V v = zzgpm.zzd;
            while (true) {
                int zza2 = zza();
                if (zza2 == Integer.MAX_VALUE) {
                    map.put(k, v);
                    this.zze = i;
                    return;
                } else if (zza2 == 1) {
                    k = zza(zzgpm.zza, (Class<?>) null, (zzgnv) null);
                } else if (zza2 == 2) {
                    v = zza(zzgpm.zzc, zzgpm.zzd.getClass(), zzgnv);
                } else if (!zzc()) {
                    throw new zzgot("Unable to parse map entry.");
                }
            }
        } catch (zzgou e) {
            if (!zzc()) {
                throw new zzgot("Unable to parse map entry.");
            }
        } catch (Throwable th) {
            this.zze = i;
            throw th;
        }
    }

    private final Object zza(zzgrr zzgrr, Class<?> cls, zzgnv zzgnv) throws IOException {
        switch (zzgmx.zza[zzgrr.ordinal()]) {
            case 1:
                return Boolean.valueOf(zzk());
            case 2:
                return zzn();
            case 3:
                return Double.valueOf(zzd());
            case 4:
                return Integer.valueOf(zzp());
            case 5:
                return Integer.valueOf(zzj());
            case 6:
                return Long.valueOf(zzi());
            case 7:
                return Float.valueOf(zze());
            case 8:
                return Integer.valueOf(zzh());
            case 9:
                return Long.valueOf(zzg());
            case 10:
                return zza(cls, zzgnv);
            case 11:
                return Integer.valueOf(zzq());
            case 12:
                return Long.valueOf(zzr());
            case 13:
                return Integer.valueOf(zzs());
            case 14:
                return Long.valueOf(zzt());
            case 15:
                return zza(true);
            case 16:
                return Integer.valueOf(zzo());
            case 17:
                return Long.valueOf(zzf());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private final int zzv() throws IOException {
        byte b;
        int i = this.zzc;
        int i2 = this.zze;
        if (i2 != i) {
            byte[] bArr = this.zzb;
            int i3 = i + 1;
            byte b2 = bArr[i];
            if (b2 >= 0) {
                this.zzc = i3;
                return b2;
            } else if (i2 - i3 < 9) {
                return (int) zzx();
            } else {
                int i4 = i3 + 1;
                byte b3 = b2 ^ (bArr[i3] << 7);
                if (b3 < 0) {
                    b = b3 ^ UnsignedBytes.MAX_POWER_OF_TWO;
                } else {
                    int i5 = i4 + 1;
                    byte b4 = b3 ^ (bArr[i4] << Ascii.f157SO);
                    if (b4 >= 0) {
                        b = b4 ^ UnsignedBytes.MAX_POWER_OF_TWO;
                        i4 = i5;
                    } else {
                        i4 = i5 + 1;
                        byte b5 = b4 ^ (bArr[i5] << Ascii.NAK);
                        if (b5 < 0) {
                            b = b5 ^ UnsignedBytes.MAX_POWER_OF_TWO;
                        } else {
                            int i6 = i4 + 1;
                            byte b6 = bArr[i4];
                            b = (b5 ^ (b6 << Ascii.f150FS)) ^ UnsignedBytes.MAX_POWER_OF_TWO;
                            if (b6 < 0) {
                                i4 = i6 + 1;
                                if (bArr[i6] < 0) {
                                    i6 = i4 + 1;
                                    if (bArr[i4] < 0) {
                                        i4 = i6 + 1;
                                        if (bArr[i6] < 0) {
                                            i6 = i4 + 1;
                                            if (bArr[i4] < 0) {
                                                i4 = i6 + 1;
                                                if (bArr[i6] < 0) {
                                                    throw zzgot.zzc();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            i4 = i6;
                        }
                    }
                }
                this.zzc = i4;
                return b;
            }
        } else {
            throw zzgot.zza();
        }
    }

    private final long zzw() throws IOException {
        long j;
        int i = this.zzc;
        int i2 = this.zze;
        if (i2 != i) {
            byte[] bArr = this.zzb;
            int i3 = i + 1;
            byte b = bArr[i];
            if (b >= 0) {
                this.zzc = i3;
                return (long) b;
            } else if (i2 - i3 < 9) {
                return zzx();
            } else {
                int i4 = i3 + 1;
                byte b2 = b ^ (bArr[i3] << 7);
                if (b2 < 0) {
                    j = (long) (b2 ^ UnsignedBytes.MAX_POWER_OF_TWO);
                } else {
                    int i5 = i4 + 1;
                    byte b3 = b2 ^ (bArr[i4] << Ascii.f157SO);
                    if (b3 >= 0) {
                        i4 = i5;
                        j = (long) (b3 ^ UnsignedBytes.MAX_POWER_OF_TWO);
                    } else {
                        i4 = i5 + 1;
                        byte b4 = b3 ^ (bArr[i5] << Ascii.NAK);
                        if (b4 < 0) {
                            j = (long) (b4 ^ UnsignedBytes.MAX_POWER_OF_TWO);
                        } else {
                            long j2 = (long) b4;
                            int i6 = i4 + 1;
                            long j3 = j2 ^ (((long) bArr[i4]) << 28);
                            if (j3 >= 0) {
                                j = 266354560 ^ j3;
                                i4 = i6;
                            } else {
                                i4 = i6 + 1;
                                long j4 = j3 ^ (((long) bArr[i6]) << 35);
                                if (j4 < 0) {
                                    j = j4 ^ -34093383808L;
                                } else {
                                    int i7 = i4 + 1;
                                    long j5 = j4 ^ (((long) bArr[i4]) << 42);
                                    if (j5 >= 0) {
                                        j = 4363953127296L ^ j5;
                                        i4 = i7;
                                    } else {
                                        i4 = i7 + 1;
                                        long j6 = j5 ^ (((long) bArr[i7]) << 49);
                                        if (j6 < 0) {
                                            j = j6 ^ -558586000294016L;
                                        } else {
                                            int i8 = i4 + 1;
                                            j = (j6 ^ (((long) bArr[i4]) << 56)) ^ 71499008037633920L;
                                            if (j < 0) {
                                                i4 = i8 + 1;
                                                if (((long) bArr[i8]) < 0) {
                                                    throw zzgot.zzc();
                                                }
                                            } else {
                                                i4 = i8;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                this.zzc = i4;
                return j;
            }
        } else {
            throw zzgot.zza();
        }
    }

    private final long zzx() throws IOException {
        long j = 0;
        for (int i = 0; i < 64; i += 7) {
            byte zzy = zzy();
            j |= ((long) (zzy & Ascii.DEL)) << i;
            if ((zzy & UnsignedBytes.MAX_POWER_OF_TWO) == 0) {
                return j;
            }
        }
        throw zzgot.zzc();
    }

    private final byte zzy() throws IOException {
        int i = this.zzc;
        if (i != this.zze) {
            byte[] bArr = this.zzb;
            this.zzc = i + 1;
            return bArr[i];
        }
        throw zzgot.zza();
    }

    private final int zzz() throws IOException {
        zzb(4);
        return zzab();
    }

    private final long zzaa() throws IOException {
        zzb(8);
        return zzac();
    }

    private final int zzab() {
        int i = this.zzc;
        byte[] bArr = this.zzb;
        this.zzc = i + 4;
        return ((bArr[i + 3] & UnsignedBytes.MAX_VALUE) << Ascii.CAN) | (bArr[i] & UnsignedBytes.MAX_VALUE) | ((bArr[i + 1] & UnsignedBytes.MAX_VALUE) << 8) | ((bArr[i + 2] & UnsignedBytes.MAX_VALUE) << Ascii.DLE);
    }

    private final long zzac() {
        int i = this.zzc;
        byte[] bArr = this.zzb;
        this.zzc = i + 8;
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    private final void zza(int i) throws IOException {
        zzb(i);
        this.zzc += i;
    }

    private final void zzb(int i) throws IOException {
        if (i < 0 || i > this.zze - this.zzc) {
            throw zzgot.zza();
        }
    }

    private final void zzc(int i) throws IOException {
        if ((this.zzf & 7) != i) {
            throw zzgot.zzf();
        }
    }

    private final void zzd(int i) throws IOException {
        zzb(i);
        if ((i & 7) != 0) {
            throw zzgot.zzh();
        }
    }

    private final void zze(int i) throws IOException {
        zzb(i);
        if ((i & 3) != 0) {
            throw zzgot.zzh();
        }
    }

    private final void zzf(int i) throws IOException {
        if (this.zzc != i) {
            throw zzgot.zza();
        }
    }
}
