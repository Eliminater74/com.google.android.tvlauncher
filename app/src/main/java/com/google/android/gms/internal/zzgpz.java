package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/* compiled from: MessageSetSchema */
final class zzgpz<T> implements zzgql<T> {
    private final zzgpt zza;
    private final zzgrd<?, ?> zzb;
    private final boolean zzc;
    private final zzgnw<?> zzd;

    private zzgpz(Class<T> cls, zzgrd<?, ?> zzgrd, zzgnw<?> zzgnw, zzgpt zzgpt) {
        this.zzb = zzgrd;
        this.zzc = zzgnw.zza((Class<?>) cls);
        this.zzd = zzgnw;
        this.zza = zzgpt;
    }

    static <T> zzgpz<T> zza(Class<T> cls, zzgrd<?, ?> zzgrd, zzgnw<?> zzgnw, zzgpt zzgpt) {
        return new zzgpz<>(cls, zzgrd, zzgnw, zzgpt);
    }

    public final T zza() {
        return this.zza.zzq().zze();
    }

    public final boolean zza(T t, T t2) {
        if (!this.zzb.zzb(t).equals(this.zzb.zzb(t2))) {
            return false;
        }
        if (this.zzc) {
            return this.zzd.zza((Object) t).equals(this.zzd.zza((Object) t2));
        }
        return true;
    }

    public final int zza(T t) {
        int hashCode = this.zzb.zzb(t).hashCode();
        if (this.zzc) {
            return (hashCode * 53) + this.zzd.zza((Object) t).hashCode();
        }
        return hashCode;
    }

    public final void zzb(T t, T t2) {
        zzgqn.zza(this.zzb, t, t2);
        if (this.zzc) {
            zzgqn.zza(this.zzd, t, t2);
        }
    }

    public final void zza(T t, zzgrx zzgrx) {
        Iterator<Map.Entry<?, Object>> zze = this.zzd.zza((Object) t).zze();
        while (zze.hasNext()) {
            Map.Entry next = zze.next();
            zzgoc zzgoc = (zzgoc) next.getKey();
            if (zzgoc.zzc() != zzgrw.MESSAGE || zzgoc.zzd() || zzgoc.zze()) {
                throw new IllegalStateException("Found invalid MessageSet item.");
            } else if (next instanceof zzgoy) {
                zzgrx.zzc(zzgoc.zza(), ((zzgoy) next).zza().zzc());
            } else {
                zzgrx.zzc(zzgoc.zza(), next.getValue());
            }
        }
        zzgrd<?, ?> zzgrd = this.zzb;
        zzgrd.zzb(zzgrd.zzb(t), zzgrx);
    }

    public final void zza(T t, byte[] bArr, int i, int i2, zzgmv zzgmv) throws IOException {
        zzgre zzgre;
        zzgoj zzgoj = (zzgoj) t;
        zzgre zzgre2 = zzgoj.zzb;
        if (zzgre2 == zzgre.zza()) {
            zzgre zzb2 = zzgre.zzb();
            zzgoj.zzb = zzb2;
            zzgre = zzb2;
        } else {
            zzgre = zzgre2;
        }
        while (i < i2) {
            int zza2 = zzgmu.zza(bArr, i, zzgmv);
            int i3 = zzgmv.zza;
            if (i3 == 11) {
                int i4 = 0;
                zzgnb zzgnb = null;
                while (zza2 < i2) {
                    zza2 = zzgmu.zza(bArr, zza2, zzgmv);
                    int i5 = zzgmv.zza;
                    int i6 = i5 >>> 3;
                    int i7 = i5 & 7;
                    if (i6 != 2) {
                        if (i6 == 3 && i7 == 2) {
                            zza2 = zzgmu.zze(bArr, zza2, zzgmv);
                            zzgnb = (zzgnb) zzgmv.zzc;
                        }
                    } else if (i7 == 0) {
                        zza2 = zzgmu.zza(bArr, zza2, zzgmv);
                        i4 = zzgmv.zza;
                    }
                    if (i5 == 12) {
                        break;
                    }
                    zza2 = zzgmu.zza(i5, bArr, zza2, i2, zzgmv);
                }
                if (zzgnb != null) {
                    zzgre.zza((i4 << 3) | 2, zzgnb);
                }
                i = zza2;
            } else if ((i3 & 7) == 2) {
                i = zzgmu.zza(i3, bArr, zza2, i2, zzgre, zzgmv);
            } else {
                i = zzgmu.zza(i3, bArr, zza2, i2, zzgmv);
            }
        }
        if (i != i2) {
            throw zzgot.zzh();
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
     method: com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, com.google.android.gms.internal.zzgnb):void
     arg types: [?, int, com.google.android.gms.internal.zzgnb]
     candidates:
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, int):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, long):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, java.lang.Object):void
      com.google.android.gms.internal.zzgrd.zza(java.lang.Object, int, com.google.android.gms.internal.zzgnb):void */
    public final void zza(T t, zzgqk zzgqk, zzgnv zzgnv) throws IOException {
        boolean z;
        zzgrd<?, ?> zzgrd = this.zzb;
        zzgnw<?> zzgnw = this.zzd;
        Object zzc2 = zzgrd.zzc(t);
        zzgoa<?> zzb2 = zzgnw.zzb(t);
        do {
            try {
                if (zzgqk.zza() == Integer.MAX_VALUE) {
                    zzgrd.zzb(t, zzc2);
                    return;
                }
                int zzb3 = zzgqk.zzb();
                if (zzb3 == 11) {
                    int i = 0;
                    Object obj = null;
                    zzgnb zzgnb = null;
                    while (zzgqk.zza() != Integer.MAX_VALUE) {
                        int zzb4 = zzgqk.zzb();
                        if (zzb4 == 16) {
                            i = zzgqk.zzo();
                            obj = zzgnw.zza(zzgnv, this.zza, i);
                        } else if (zzb4 == 26) {
                            if (obj != null) {
                                zzgnw.zza(zzgqk, obj, zzgnv, zzb2);
                            } else {
                                zzgnb = zzgqk.zzn();
                            }
                        } else if (!zzgqk.zzc()) {
                            break;
                        }
                    }
                    if (zzgqk.zzb() != 12) {
                        throw zzgot.zze();
                    } else if (zzgnb != null) {
                        if (obj != null) {
                            zzgnw.zza(zzgnb, obj, zzgnv, zzb2);
                        } else {
                            zzgrd.zza((Object) zzc2, i, zzgnb);
                        }
                    }
                } else if ((zzb3 & 7) == 2) {
                    Object zza2 = zzgnw.zza(zzgnv, this.zza, zzb3 >>> 3);
                    if (zza2 != null) {
                        zzgnw.zza(zzgqk, zza2, zzgnv, zzb2);
                    } else {
                        z = zzgrd.zza((Object) zzc2, zzgqk);
                        continue;
                    }
                } else {
                    z = zzgqk.zzc();
                    continue;
                }
                z = true;
                continue;
            } finally {
                zzgrd.zzb(t, zzc2);
            }
        } while (z);
    }

    public final void zzc(T t) {
        this.zzb.zzd(t);
        this.zzd.zzc(t);
    }

    public final int zzb(T t) {
        zzgrd<?, ?> zzgrd = this.zzb;
        int zze = zzgrd.zze(zzgrd.zzb(t)) + 0;
        if (this.zzc) {
            return zze + this.zzd.zza((Object) t).zzi();
        }
        return zze;
    }
}
