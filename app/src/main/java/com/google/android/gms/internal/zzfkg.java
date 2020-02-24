package com.google.android.gms.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;

/* compiled from: ImmutableCollection */
public abstract class zzfkg<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] zza = new Object[0];

    zzfkg() {
    }

    /* renamed from: zza */
    public abstract zzfko<E> iterator();

    /* access modifiers changed from: package-private */
    public abstract boolean zzc();

    public final Object[] toArray() {
        int size = size();
        if (size == 0) {
            return zza;
        }
        Object[] objArr = new Object[size];
        zza(objArr, 0);
        return objArr;
    }

    public final <T> T[] toArray(T[] tArr) {
        zzfkb.zza(tArr);
        int size = size();
        if (tArr.length < size) {
            tArr = (Object[]) Array.newInstance(tArr.getClass().getComponentType(), size);
        } else if (tArr.length > size) {
            tArr[size] = null;
        }
        zza(tArr, 0);
        return tArr;
    }

    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public zzfkj<E> zzb() {
        return isEmpty() ? zzfkj.zzd() : zzfkj.zza(toArray());
    }

    /* access modifiers changed from: package-private */
    public int zza(Object[] objArr, int i) {
        zzfko zzfko = (zzfko) iterator();
        while (zzfko.hasNext()) {
            objArr[i] = zzfko.next();
            i++;
        }
        return i;
    }
}
