package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Extension */
public final class zzgsc<M extends zzgsb<M>, T> {
    public final int zzb;
    protected final Class<T> zza;
    protected final boolean zzc;
    private final int zzd;
    private final zzgoj<?, ?> zze;

    private zzgsc(int i, Class<T> cls, int i2, boolean z) {
        this(11, cls, null, i2, false);
    }

    private zzgsc(int i, Class<T> cls, zzgoj<?, ?> zzgoj, int i2, boolean z) {
        this.zzd = i;
        this.zza = cls;
        this.zzb = i2;
        this.zzc = false;
        this.zze = null;
    }

    public static <M extends zzgsb<M>, T extends zzgsh> zzgsc<M, T> zza(int i, Class<T> cls, long j) {
        return new zzgsc<>(11, cls, (int) j, false);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgsc)) {
            return false;
        }
        zzgsc zzgsc = (zzgsc) obj;
        if (this.zzd == zzgsc.zzd && this.zza == zzgsc.zza && this.zzb == zzgsc.zzb && this.zzc == zzgsc.zzc) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        return ((((((this.zzd + 1147) * 31) + this.zza.hashCode()) * 31) + this.zzb) * 31) + (this.zzc ? 1 : 0);
    }

    /* access modifiers changed from: package-private */
    public final T zza(List<zzgsj> list) {
        if (list == null) {
            return null;
        }
        if (this.zzc) {
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                zzgsj zzgsj = list.get(i);
                if (zzgsj.zzb.length != 0) {
                    arrayList.add(zza(zzgry.zza(zzgsj.zzb)));
                }
            }
            int size = arrayList.size();
            if (size == 0) {
                return null;
            }
            Class<T> cls = this.zza;
            T cast = cls.cast(Array.newInstance(cls.getComponentType(), size));
            for (int i2 = 0; i2 < size; i2++) {
                Array.set(cast, i2, arrayList.get(i2));
            }
            return cast;
        } else if (list.isEmpty()) {
            return null;
        } else {
            return this.zza.cast(zza(zzgry.zza(list.get(list.size() - 1).zzb)));
        }
    }

    private final Object zza(zzgry zzgry) {
        Class componentType = this.zzc ? this.zza.getComponentType() : this.zza;
        try {
            int i = this.zzd;
            if (i == 10) {
                zzgsh zzgsh = (zzgsh) componentType.newInstance();
                zzgry.zza(zzgsh, this.zzb >>> 3);
                return zzgsh;
            } else if (i == 11) {
                zzgsh zzgsh2 = (zzgsh) componentType.newInstance();
                zzgry.zza(zzgsh2);
                return zzgsh2;
            } else {
                int i2 = this.zzd;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            }
        } catch (InstantiationException e) {
            String valueOf = String.valueOf(componentType);
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 33);
            sb2.append("Error creating instance of class ");
            sb2.append(valueOf);
            throw new IllegalArgumentException(sb2.toString(), e);
        } catch (IllegalAccessException e2) {
            String valueOf2 = String.valueOf(componentType);
            StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf2).length() + 33);
            sb3.append("Error creating instance of class ");
            sb3.append(valueOf2);
            throw new IllegalArgumentException(sb3.toString(), e2);
        } catch (IOException e3) {
            throw new IllegalArgumentException("Error reading extension field", e3);
        }
    }

    /* access modifiers changed from: protected */
    public final void zza(Object obj, zzgrz zzgrz) {
        try {
            zzgrz.zzc(this.zzb);
            int i = this.zzd;
            if (i == 10) {
                ((zzgsh) obj).writeTo(zzgrz);
                zzgrz.zzc(this.zzb >>> 3, 4);
            } else if (i == 11) {
                zzgrz.zza((zzgsh) obj);
            } else {
                int i2 = this.zzd;
                StringBuilder sb = new StringBuilder(24);
                sb.append("Unknown type ");
                sb.append(i2);
                throw new IllegalArgumentException(sb.toString());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /* access modifiers changed from: protected */
    public final int zza(Object obj) {
        int i = this.zzb >>> 3;
        int i2 = this.zzd;
        if (i2 == 10) {
            return (zzgrz.zzb(i) << 1) + ((zzgsh) obj).getSerializedSize();
        }
        if (i2 == 11) {
            return zzgrz.zzb(i, (zzgsh) obj);
        }
        StringBuilder sb = new StringBuilder(24);
        sb.append("Unknown type ");
        sb.append(i2);
        throw new IllegalArgumentException(sb.toString());
    }
}
