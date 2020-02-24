package com.google.android.gms.internal;

import java.util.Iterator;
import java.util.Map;

/* compiled from: MapFieldSchemaLite */
final class zzgpp implements zzgpo {
    zzgpp() {
    }

    public final Map<?, ?> zza(Object obj) {
        return (zzgpn) obj;
    }

    public final zzgpm<?, ?> zzf(Object obj) {
        throw new NoSuchMethodError();
    }

    public final Map<?, ?> zzb(Object obj) {
        return (zzgpn) obj;
    }

    public final boolean zzc(Object obj) {
        return !((zzgpn) obj).zzd();
    }

    public final Object zzd(Object obj) {
        ((zzgpn) obj).zzc();
        return obj;
    }

    public final Object zze(Object obj) {
        return zzgpn.zza().zzb();
    }

    public final Object zza(Object obj, Object obj2) {
        zzgpn zzgpn = (zzgpn) obj;
        zzgpn zzgpn2 = (zzgpn) obj2;
        if (!zzgpn2.isEmpty()) {
            if (!zzgpn.zzd()) {
                zzgpn = zzgpn.zzb();
            }
            zzgpn.zza(zzgpn2);
        }
        return zzgpn;
    }

    public final int zza(int i, Object obj, Object obj2) {
        zzgpn zzgpn = (zzgpn) obj;
        if (zzgpn.isEmpty()) {
            return 0;
        }
        Iterator it = zzgpn.entrySet().iterator();
        if (!it.hasNext()) {
            return 0;
        }
        Map.Entry entry = (Map.Entry) it.next();
        entry.getKey();
        entry.getValue();
        throw new NoSuchMethodError();
    }
}
