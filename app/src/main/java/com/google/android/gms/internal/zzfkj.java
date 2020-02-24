package com.google.android.gms.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/* compiled from: ImmutableList */
public abstract class zzfkj<E> extends zzfkg<E> implements List<E>, RandomAccess {
    private static final zzfkp<Object> zza = new zzfkl(zzfkn.zza, 0);

    public static <E> zzfkj<E> zzd() {
        return zzfkn.zza;
    }

    public static <E> zzfkj<E> zza(Collection collection) {
        if (collection instanceof zzfkg) {
            zzfkj<E> zzb = ((zzfkg) collection).zzb();
            if (!zzb.zzc()) {
                return zzb;
            }
            Object[] array = zzb.toArray();
            return zzb(array, array.length);
        }
        Object[] array2 = collection.toArray();
        int length = array2.length;
        int i = 0;
        while (i < length) {
            if (array2[i] != null) {
                i++;
            } else {
                StringBuilder sb = new StringBuilder(20);
                sb.append("at index ");
                sb.append(i);
                throw new NullPointerException(sb.toString());
            }
        }
        return zzb(array2, array2.length);
    }

    static <E> zzfkj<E> zza(Object[] objArr) {
        return zzb(objArr, objArr.length);
    }

    static <E> zzfkj<E> zzb(Object[] objArr, int i) {
        if (i == 0) {
            return zzfkn.zza;
        }
        return new zzfkn(objArr, i);
    }

    zzfkj() {
    }

    public final zzfko<E> zza() {
        return (zzfkp) listIterator();
    }

    public int indexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (this instanceof RandomAccess) {
            int size = size();
            for (int i = 0; i < size; i++) {
                if (obj.equals(get(i))) {
                    return i;
                }
            }
            return -1;
        }
        ListIterator listIterator = listIterator();
        while (listIterator.hasNext()) {
            if (zzfjx.zza(obj, listIterator.next())) {
                return listIterator.previousIndex();
            }
        }
        return -1;
    }

    public int lastIndexOf(Object obj) {
        if (obj == null) {
            return -1;
        }
        if (this instanceof RandomAccess) {
            for (int size = size() - 1; size >= 0; size--) {
                if (obj.equals(get(size))) {
                    return size;
                }
            }
            return -1;
        }
        ListIterator listIterator = listIterator(size());
        while (listIterator.hasPrevious()) {
            if (zzfjx.zza(obj, listIterator.previous())) {
                return listIterator.nextIndex();
            }
        }
        return -1;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) >= 0;
    }

    /* renamed from: zza */
    public zzfkj<E> subList(int i, int i2) {
        zzfkb.zza(i, i2, size());
        int i3 = i2 - i;
        if (i3 == size()) {
            return this;
        }
        if (i3 == 0) {
            return zzfkn.zza;
        }
        return new zzfkm(this, i, i3);
    }

    @Deprecated
    public final boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final E remove(int i) {
        throw new UnsupportedOperationException();
    }

    public final zzfkj<E> zzb() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public int zza(Object[] objArr, int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            objArr[i2] = get(i2);
        }
        return size;
    }

    public boolean equals(Object obj) {
        if (obj == zzfkb.zza(this)) {
            return true;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            int size = size();
            if (size == list.size()) {
                if (!(this instanceof RandomAccess) || !(list instanceof RandomAccess)) {
                    zzfkj zzfkj = this;
                    int size2 = zzfkj.size();
                    Iterator it = list.iterator();
                    int i = 0;
                    while (true) {
                        if (i < size2) {
                            if (!it.hasNext()) {
                                break;
                            }
                            Object obj2 = zzfkj.get(i);
                            i++;
                            if (!zzfjx.zza(obj2, it.next())) {
                                break;
                            }
                        } else if (!it.hasNext()) {
                            return true;
                        }
                    }
                } else {
                    int i2 = 0;
                    while (i2 < size) {
                        if (zzfjx.zza(get(i2), list.get(i2))) {
                            i2++;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        int size = size();
        int i = 1;
        for (int i2 = 0; i2 < size; i2++) {
            i = (((i * 31) + get(i2).hashCode()) ^ -1) ^ -1;
        }
        return i;
    }

    public static <E> zzfkk<E> zze() {
        return new zzfkk<>();
    }

    public /* synthetic */ Iterator iterator() {
        return iterator();
    }

    public /* synthetic */ ListIterator listIterator(int i) {
        zzfkb.zzb(i, size());
        if (isEmpty()) {
            return zza;
        }
        return new zzfkl(this, i);
    }

    public /* synthetic */ ListIterator listIterator() {
        return (zzfkp) listIterator(0);
    }
}
