package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: IntArrayList */
final class zzgom extends zzgms<Integer> implements zzgoq, RandomAccess {
    private static final zzgom zza;
    private int[] zzb;
    private int zzc;

    public static zzgom zzd() {
        return zza;
    }

    zzgom() {
        this(new int[10], 0);
    }

    private zzgom(int[] iArr, int i) {
        this.zzb = iArr;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgom)) {
            return super.equals(obj);
        }
        zzgom zzgom = (zzgom) obj;
        if (this.zzc != zzgom.zzc) {
            return false;
        }
        int[] iArr = zzgom.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != iArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzc; i2++) {
            i = (i * 31) + this.zzb[i2];
        }
        return i;
    }

    /* renamed from: zzb */
    public final zzgoq zza(int i) {
        if (i >= this.zzc) {
            return new zzgom(Arrays.copyOf(this.zzb, i), this.zzc);
        }
        throw new IllegalArgumentException();
    }

    public final int zzc(int i) {
        zze(i);
        return this.zzb[i];
    }

    public final int size() {
        return this.zzc;
    }

    public final void zzd(int i) {
        zza(this.zzc, i);
    }

    private final void zza(int i, int i2) {
        int i3;
        zzc();
        if (i < 0 || i > (i3 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzf(i));
        }
        int[] iArr = this.zzb;
        if (i3 < iArr.length) {
            System.arraycopy(iArr, i, iArr, i + 1, i3 - i);
        } else {
            int[] iArr2 = new int[(((i3 * 3) / 2) + 1)];
            System.arraycopy(iArr, 0, iArr2, 0, i);
            System.arraycopy(this.zzb, i, iArr2, i + 1, this.zzc - i);
            this.zzb = iArr2;
        }
        this.zzb[i] = i2;
        this.zzc++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Integer> collection) {
        zzc();
        zzgon.zza(collection);
        if (!(collection instanceof zzgom)) {
            return super.addAll(collection);
        }
        zzgom zzgom = (zzgom) collection;
        int i = zzgom.zzc;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzc;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            int[] iArr = this.zzb;
            if (i3 > iArr.length) {
                this.zzb = Arrays.copyOf(iArr, i3);
            }
            System.arraycopy(zzgom.zzb, 0, this.zzb, this.zzc, zzgom.zzc);
            this.zzc = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzc();
        for (int i = 0; i < this.zzc; i++) {
            if (obj.equals(Integer.valueOf(this.zzb[i]))) {
                int[] iArr = this.zzb;
                System.arraycopy(iArr, i + 1, iArr, i, this.zzc - i);
                this.zzc--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zze(int i) {
        if (i < 0 || i >= this.zzc) {
            throw new IndexOutOfBoundsException(zzf(i));
        }
    }

    private final String zzf(int i) {
        int i2 = this.zzc;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        int intValue = ((Integer) obj).intValue();
        zzc();
        zze(i);
        int[] iArr = this.zzb;
        int i2 = iArr[i];
        iArr[i] = intValue;
        return Integer.valueOf(i2);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        zze(i);
        int[] iArr = this.zzb;
        int i2 = iArr[i];
        System.arraycopy(iArr, i + 1, iArr, i, this.zzc - i);
        this.zzc--;
        this.modCount++;
        return Integer.valueOf(i2);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Integer) obj).intValue());
    }

    public final /* synthetic */ Object get(int i) {
        return Integer.valueOf(zzc(i));
    }

    static {
        zzgom zzgom = new zzgom();
        zza = zzgom;
        zzgom.zzb();
    }
}
