package com.google.android.gms.common.api.internal;

/* compiled from: ListenerHolder */
public final class zzcl<L> {
    private final L zza;
    private final String zzb;

    zzcl(L l, String str) {
        this.zza = l;
        this.zzb = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzcl)) {
            return false;
        }
        zzcl zzcl = (zzcl) obj;
        if (this.zza != zzcl.zza || !this.zzb.equals(zzcl.zzb)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return (System.identityHashCode(this.zza) * 31) + this.zzb.hashCode();
    }
}
