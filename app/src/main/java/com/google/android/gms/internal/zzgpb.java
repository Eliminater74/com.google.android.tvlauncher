package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

/* compiled from: LazyStringArrayList */
public final class zzgpb extends zzgms<String> implements zzgpc, RandomAccess {
    private static final zzgpb zza;
    private static final zzgpc zzb = zza;

    static {
        zzgpb zzgpb = new zzgpb();
        zza = zzgpb;
        zzgpb.zzb();
    }

    private final List<Object> zzc;

    public zzgpb() {
        this(10);
    }

    public zzgpb(int i) {
        this(new ArrayList(i));
    }

    private zzgpb(ArrayList<Object> arrayList) {
        this.zzc = arrayList;
    }

    private static String zza(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgnb) {
            return ((zzgnb) obj).zzd();
        }
        return zzgon.zzb((byte[]) obj);
    }

    public final int size() {
        return this.zzc.size();
    }

    public final boolean addAll(Collection<? extends String> collection) {
        return addAll(size(), collection);
    }

    public final boolean addAll(int i, Collection<? extends String> collection) {
        zzc();
        if (collection instanceof zzgpc) {
            collection = ((zzgpc) collection).zzd();
        }
        boolean addAll = this.zzc.addAll(i, collection);
        this.modCount++;
        return addAll;
    }

    public final void clear() {
        zzc();
        this.zzc.clear();
        this.modCount++;
    }

    public final void zza(zzgnb zzgnb) {
        zzc();
        this.zzc.add(zzgnb);
        this.modCount++;
    }

    public final Object zzb(int i) {
        return this.zzc.get(i);
    }

    public final List<?> zzd() {
        return Collections.unmodifiableList(this.zzc);
    }

    public final zzgpc zze() {
        if (zza()) {
            return new zzgrg(this);
        }
        return this;
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        zzc();
        return zza(this.zzc.set(i, (String) obj));
    }

    public final /* bridge */ /* synthetic */ boolean retainAll(Collection collection) {
        return super.retainAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean removeAll(Collection collection) {
        return super.removeAll(collection);
    }

    public final /* bridge */ /* synthetic */ boolean remove(Object obj) {
        return super.remove(obj);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        Object remove = this.zzc.remove(i);
        this.modCount++;
        return zza(remove);
    }

    public final /* bridge */ /* synthetic */ boolean zza() {
        return super.zza();
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zzc();
        this.zzc.add(i, (String) obj);
        this.modCount++;
    }

    public final /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public final /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public final /* synthetic */ zzgos zza(int i) {
        if (i >= size()) {
            ArrayList arrayList = new ArrayList(i);
            arrayList.addAll(this.zzc);
            return new zzgpb(arrayList);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        Object obj = this.zzc.get(i);
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof zzgnb) {
            zzgnb zzgnb = (zzgnb) obj;
            String zzd = zzgnb.zzd();
            if (zzgnb.zze()) {
                this.zzc.set(i, zzd);
            }
            return zzd;
        }
        byte[] bArr = (byte[]) obj;
        String zzb2 = zzgon.zzb(bArr);
        if (zzgon.zza(bArr)) {
            this.zzc.set(i, zzb2);
        }
        return zzb2;
    }
}
