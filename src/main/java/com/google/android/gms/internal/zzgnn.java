package com.google.android.gms.internal;

import androidx.tvprovider.media.p005tv.TvContractCompat;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* compiled from: CodedInputStreamReader */
final class zzgnn implements zzgqk {
    private final zzgnk zza;
    private int zzb;
    private int zzc;
    private int zzd = 0;

    public static zzgnn zza(zzgnk zzgnk) {
        if (zzgnk.zzc != null) {
            return zzgnk.zzc;
        }
        return new zzgnn(zzgnk);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T
     arg types: [com.google.android.gms.internal.zzgnk, java.lang.String]
     candidates:
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.Object):java.lang.Object
      com.google.android.gms.internal.zzgon.zza(java.lang.Object, java.lang.String):T */
    private zzgnn(zzgnk zzgnk) {
        this.zza = (zzgnk) zzgon.zza((Object) zzgnk, TvContractCompat.PARAM_INPUT);
        this.zza.zzc = this;
    }

    public final int zza() throws IOException {
        int i = this.zzd;
        if (i != 0) {
            this.zzb = i;
            this.zzd = 0;
        } else {
            this.zzb = this.zza.zza();
        }
        int i2 = this.zzb;
        if (i2 == 0 || i2 == this.zzc) {
            return Integer.MAX_VALUE;
        }
        return i2 >>> 3;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final boolean zzc() throws IOException {
        int i;
        if (this.zza.zzt() || (i = this.zzb) == this.zzc) {
            return false;
        }
        return this.zza.zzb(i);
    }

    private final void zza(int i) throws IOException {
        if ((this.zzb & 7) != i) {
            throw zzgot.zzf();
        }
    }

    public final double zzd() throws IOException {
        zza(1);
        return this.zza.zzb();
    }

    public final float zze() throws IOException {
        zza(5);
        return this.zza.zzc();
    }

    public final long zzf() throws IOException {
        zza(0);
        return this.zza.zzd();
    }

    public final long zzg() throws IOException {
        zza(0);
        return this.zza.zze();
    }

    public final int zzh() throws IOException {
        zza(0);
        return this.zza.zzf();
    }

    public final long zzi() throws IOException {
        zza(1);
        return this.zza.zzg();
    }

    public final int zzj() throws IOException {
        zza(5);
        return this.zza.zzh();
    }

    public final boolean zzk() throws IOException {
        zza(0);
        return this.zza.zzi();
    }

    public final String zzl() throws IOException {
        zza(2);
        return this.zza.zzj();
    }

    public final String zzm() throws IOException {
        zza(2);
        return this.zza.zzk();
    }

    public final <T> T zza(Class<T> cls, zzgnv zzgnv) throws IOException {
        zza(2);
        return zzc(zzgqf.zza().zza((Class) cls), zzgnv);
    }

    public final <T> T zza(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        zza(2);
        return zzc(zzgql, zzgnv);
    }

    public final <T> T zzb(Class<T> cls, zzgnv zzgnv) throws IOException {
        zza(3);
        return zzd(zzgqf.zza().zza((Class) cls), zzgnv);
    }

    public final <T> T zzb(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        zza(3);
        return zzd(zzgql, zzgnv);
    }

    private final <T> T zzc(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int zzm = this.zza.zzm();
        if (this.zza.zza < this.zza.zzb) {
            int zzd2 = this.zza.zzd(zzm);
            T zza2 = zzgql.zza();
            this.zza.zza++;
            zzgql.zza(zza2, this, zzgnv);
            zzgql.zzc(zza2);
            this.zza.zza(0);
            zzgnk zzgnk = this.zza;
            zzgnk.zza--;
            this.zza.zze(zzd2);
            return zza2;
        }
        throw zzgot.zzg();
    }

    private final <T> T zzd(zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int i = this.zzc;
        this.zzc = ((this.zzb >>> 3) << 3) | 4;
        try {
            T zza2 = zzgql.zza();
            zzgql.zza(zza2, this, zzgnv);
            zzgql.zzc(zza2);
            if (this.zzb == this.zzc) {
                return zza2;
            }
            throw zzgot.zzh();
        } finally {
            this.zzc = i;
        }
    }

    public final zzgnb zzn() throws IOException {
        zza(2);
        return this.zza.zzl();
    }

    public final int zzo() throws IOException {
        zza(0);
        return this.zza.zzm();
    }

    public final int zzp() throws IOException {
        zza(0);
        return this.zza.zzn();
    }

    public final int zzq() throws IOException {
        zza(5);
        return this.zza.zzo();
    }

    public final long zzr() throws IOException {
        zza(1);
        return this.zza.zzp();
    }

    public final int zzs() throws IOException {
        zza(0);
        return this.zza.zzq();
    }

    public final long zzt() throws IOException {
        zza(0);
        return this.zza.zzr();
    }

    public final void zza(List<Double> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgns) {
            zzgns zzgns = (zzgns) list;
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    zzgns.zza(this.zza.zzb());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzm = this.zza.zzm();
                zzb(zzm);
                int zzu = this.zza.zzu() + zzm;
                do {
                    zzgns.zza(this.zza.zzb());
                } while (this.zza.zzu() < zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 1) {
                do {
                    list.add(Double.valueOf(this.zza.zzb()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzm2 = this.zza.zzm();
                zzb(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Double.valueOf(this.zza.zzb()));
                } while (this.zza.zzu() < zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzb(List<Float> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgog) {
            zzgog zzgog = (zzgog) list;
            int i = this.zzb & 7;
            if (i == 2) {
                int zzm = this.zza.zzm();
                zzc(zzm);
                int zzu = this.zza.zzu() + zzm;
                do {
                    zzgog.zza(this.zza.zzc());
                } while (this.zza.zzu() < zzu);
            } else if (i == 5) {
                do {
                    zzgog.zza(this.zza.zzc());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                int zzm2 = this.zza.zzm();
                zzc(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Float.valueOf(this.zza.zzc()));
                } while (this.zza.zzu() < zzu2);
            } else if (i2 == 5) {
                do {
                    list.add(Float.valueOf(this.zza.zzc()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzc(List<Long> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgph.zza(this.zza.zzd());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgph.zza(this.zza.zzd());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zza.zzd()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Long.valueOf(this.zza.zzd()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzd(List<Long> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgph.zza(this.zza.zze());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgph.zza(this.zza.zze());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zza.zze()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Long.valueOf(this.zza.zze()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zze(List<Integer> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgom.zzd(this.zza.zzf());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgom.zzd(this.zza.zzf());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzf()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzf()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzf(List<Long> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    zzgph.zza(this.zza.zzg());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzm = this.zza.zzm();
                zzb(zzm);
                int zzu = this.zza.zzu() + zzm;
                do {
                    zzgph.zza(this.zza.zzg());
                } while (this.zza.zzu() < zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zza.zzg()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzm2 = this.zza.zzm();
                zzb(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Long.valueOf(this.zza.zzg()));
                } while (this.zza.zzu() < zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzg(List<Integer> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i = this.zzb & 7;
            if (i == 2) {
                int zzm = this.zza.zzm();
                zzc(zzm);
                int zzu = this.zza.zzu() + zzm;
                do {
                    zzgom.zzd(this.zza.zzh());
                } while (this.zza.zzu() < zzu);
            } else if (i == 5) {
                do {
                    zzgom.zzd(this.zza.zzh());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                int zzm2 = this.zza.zzm();
                zzc(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Integer.valueOf(this.zza.zzh()));
                } while (this.zza.zzu() < zzu2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zza.zzh()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzh(List<Boolean> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgmz) {
            zzgmz zzgmz = (zzgmz) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgmz.zza(this.zza.zzi());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgmz.zza(this.zza.zzi());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Boolean.valueOf(this.zza.zzi()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Boolean.valueOf(this.zza.zzi()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnn.zza(java.util.List<java.lang.String>, boolean):void
     arg types: [java.util.List<java.lang.String>, int]
     candidates:
      com.google.android.gms.internal.zzgnn.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgnn.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgnn.zza(java.util.List<java.lang.String>, boolean):void */
    public final void zzi(List<String> list) throws IOException {
        zza(list, false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgnn.zza(java.util.List<java.lang.String>, boolean):void
     arg types: [java.util.List<java.lang.String>, int]
     candidates:
      com.google.android.gms.internal.zzgnn.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgnn.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(com.google.android.gms.internal.zzgql, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgqk.zza(java.lang.Class, com.google.android.gms.internal.zzgnv):T
      com.google.android.gms.internal.zzgnn.zza(java.util.List<java.lang.String>, boolean):void */
    public final void zzj(List<String> list) throws IOException {
        zza(list, true);
    }

    private final void zza(List<String> list, boolean z) throws IOException {
        int zza2;
        int zza3;
        if ((this.zzb & 7) != 2) {
            throw zzgot.zzf();
        } else if (!(list instanceof zzgpc) || z) {
            do {
                list.add(z ? zzm() : zzl());
                if (!this.zza.zzt()) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == this.zzb);
            this.zzd = zza2;
        } else {
            zzgpc zzgpc = (zzgpc) list;
            do {
                zzgpc.zza(zzn());
                if (!this.zza.zzt()) {
                    zza3 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza3 == this.zzb);
            this.zzd = zza3;
        }
    }

    public final <T> void zza(List<T> list, zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int zza2;
        int i = this.zzb;
        if ((i & 7) == 2) {
            do {
                list.add(zzc(zzgql, zzgnv));
                if (!this.zza.zzt() && this.zzd == 0) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == i);
            this.zzd = zza2;
            return;
        }
        throw zzgot.zzf();
    }

    public final <T> void zzb(List<T> list, zzgql<T> zzgql, zzgnv zzgnv) throws IOException {
        int zza2;
        int i = this.zzb;
        if ((i & 7) == 3) {
            do {
                list.add(zzd(zzgql, zzgnv));
                if (!this.zza.zzt() && this.zzd == 0) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == i);
            this.zzd = zza2;
            return;
        }
        throw zzgot.zzf();
    }

    public final void zzk(List<zzgnb> list) throws IOException {
        int zza2;
        if ((this.zzb & 7) == 2) {
            do {
                list.add(zzn());
                if (!this.zza.zzt()) {
                    zza2 = this.zza.zza();
                } else {
                    return;
                }
            } while (zza2 == this.zzb);
            this.zzd = zza2;
            return;
        }
        throw zzgot.zzf();
    }

    public final void zzl(List<Integer> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgom.zzd(this.zza.zzm());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgom.zzd(this.zza.zzm());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzm()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzm()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzm(List<Integer> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgom.zzd(this.zza.zzn());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgom.zzd(this.zza.zzn());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzn()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzn()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzn(List<Integer> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i = this.zzb & 7;
            if (i == 2) {
                int zzm = this.zza.zzm();
                zzc(zzm);
                int zzu = this.zza.zzu() + zzm;
                do {
                    zzgom.zzd(this.zza.zzo());
                } while (this.zza.zzu() < zzu);
            } else if (i == 5) {
                do {
                    zzgom.zzd(this.zza.zzo());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 2) {
                int zzm2 = this.zza.zzm();
                zzc(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Integer.valueOf(this.zza.zzo()));
                } while (this.zza.zzu() < zzu2);
            } else if (i2 == 5) {
                do {
                    list.add(Integer.valueOf(this.zza.zzo()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzo(List<Long> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i = this.zzb & 7;
            if (i == 1) {
                do {
                    zzgph.zza(this.zza.zzp());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzm = this.zza.zzm();
                zzb(zzm);
                int zzu = this.zza.zzu() + zzm;
                do {
                    zzgph.zza(this.zza.zzp());
                } while (this.zza.zzu() < zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 1) {
                do {
                    list.add(Long.valueOf(this.zza.zzp()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzm2 = this.zza.zzm();
                zzb(zzm2);
                int zzu2 = this.zza.zzu() + zzm2;
                do {
                    list.add(Long.valueOf(this.zza.zzp()));
                } while (this.zza.zzu() < zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzp(List<Integer> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgom) {
            zzgom zzgom = (zzgom) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgom.zzd(this.zza.zzq());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgom.zzd(this.zza.zzq());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Integer.valueOf(this.zza.zzq()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Integer.valueOf(this.zza.zzq()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    public final void zzq(List<Long> list) throws IOException {
        int zza2;
        int zza3;
        if (list instanceof zzgph) {
            zzgph zzgph = (zzgph) list;
            int i = this.zzb & 7;
            if (i == 0) {
                do {
                    zzgph.zza(this.zza.zzr());
                    if (!this.zza.zzt()) {
                        zza3 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza3 == this.zzb);
                this.zzd = zza3;
            } else if (i == 2) {
                int zzu = this.zza.zzu() + this.zza.zzm();
                do {
                    zzgph.zza(this.zza.zzr());
                } while (this.zza.zzu() < zzu);
                zzd(zzu);
            } else {
                throw zzgot.zzf();
            }
        } else {
            int i2 = this.zzb & 7;
            if (i2 == 0) {
                do {
                    list.add(Long.valueOf(this.zza.zzr()));
                    if (!this.zza.zzt()) {
                        zza2 = this.zza.zza();
                    } else {
                        return;
                    }
                } while (zza2 == this.zzb);
                this.zzd = zza2;
            } else if (i2 == 2) {
                int zzu2 = this.zza.zzu() + this.zza.zzm();
                do {
                    list.add(Long.valueOf(this.zza.zzr()));
                } while (this.zza.zzu() < zzu2);
                zzd(zzu2);
            } else {
                throw zzgot.zzf();
            }
        }
    }

    private static void zzb(int i) throws IOException {
        if ((i & 7) != 0) {
            throw zzgot.zzh();
        }
    }

    public final <K, V> void zza(Map<K, V> map, zzgpm<K, V> zzgpm, zzgnv zzgnv) throws IOException {
        zza(2);
        int zzd2 = this.zza.zzd(this.zza.zzm());
        K k = zzgpm.zzb;
        V v = zzgpm.zzd;
        while (true) {
            try {
                int zza2 = zza();
                if (zza2 == Integer.MAX_VALUE || this.zza.zzt()) {
                    map.put(k, v);
                } else if (zza2 == 1) {
                    k = zza(zzgpm.zza, (Class<?>) null, (zzgnv) null);
                } else if (zza2 == 2) {
                    v = zza(zzgpm.zzc, zzgpm.zzd.getClass(), zzgnv);
                } else if (!zzc()) {
                    throw new zzgot("Unable to parse map entry.");
                }
            } catch (zzgou e) {
                if (!zzc()) {
                    throw new zzgot("Unable to parse map entry.");
                }
            } catch (Throwable th) {
                this.zza.zze(zzd2);
                throw th;
            }
        }
        map.put(k, v);
        this.zza.zze(zzd2);
    }

    private final Object zza(zzgrr zzgrr, Class<?> cls, zzgnv zzgnv) throws IOException {
        switch (zzgno.zza[zzgrr.ordinal()]) {
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
                return zzm();
            case 16:
                return Integer.valueOf(zzo());
            case 17:
                return Long.valueOf(zzf());
            default:
                throw new RuntimeException("unsupported field type.");
        }
    }

    private static void zzc(int i) throws IOException {
        if ((i & 3) != 0) {
            throw zzgot.zzh();
        }
    }

    private final void zzd(int i) throws IOException {
        if (this.zza.zzu() != i) {
            throw zzgot.zza();
        }
    }
}
