package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: ListFieldSchema */
final class zzgpf extends zzgpd {
    private static final Class<?> zza = Collections.unmodifiableList(Collections.emptyList()).getClass();

    private zzgpf() {
        super();
    }

    /* access modifiers changed from: package-private */
    public final <L> List<L> zza(Object obj, long j) {
        return zza(obj, j, 10);
    }

    /* access modifiers changed from: package-private */
    public final void zzb(Object obj, long j) {
        Object obj2;
        List list = (List) zzgrj.zzf(obj, j);
        if (list instanceof zzgpc) {
            obj2 = ((zzgpc) list).zze();
        } else if (!zza.isAssignableFrom(list.getClass())) {
            obj2 = Collections.unmodifiableList(list);
        } else {
            return;
        }
        zzgrj.zza(obj, j, obj2);
    }

    private static <L> List<L> zza(Object obj, long j, int i) {
        List<L> list;
        List<L> zzc = zzc(obj, j);
        if (zzc.isEmpty()) {
            if (zzc instanceof zzgpc) {
                list = new zzgpb(i);
            } else {
                list = new ArrayList<>(i);
            }
            zzgrj.zza(obj, j, list);
            return list;
        } else if (zza.isAssignableFrom(zzc.getClass())) {
            ArrayList arrayList = new ArrayList(zzc.size() + i);
            arrayList.addAll(zzc);
            zzgrj.zza(obj, j, arrayList);
            return arrayList;
        } else if (!(zzc instanceof zzgrg)) {
            return zzc;
        } else {
            zzgpb zzgpb = new zzgpb(zzc.size() + i);
            zzgpb.addAll((zzgrg) zzc);
            zzgrj.zza(obj, j, zzgpb);
            return zzgpb;
        }
    }

    /* access modifiers changed from: package-private */
    public final <E> void zza(Object obj, Object obj2, long j) {
        List zzc = zzc(obj2, j);
        List zza2 = zza(obj, j, zzc.size());
        int size = zza2.size();
        int size2 = zzc.size();
        if (size > 0 && size2 > 0) {
            zza2.addAll(zzc);
        }
        if (size > 0) {
            zzc = zza2;
        }
        zzgrj.zza(obj, j, zzc);
    }

    private static <E> List<E> zzc(Object obj, long j) {
        return (List) zzgrj.zzf(obj, j);
    }
}
