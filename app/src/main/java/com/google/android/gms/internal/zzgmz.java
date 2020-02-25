package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: BooleanArrayList */
final class zzgmz extends zzgms<Boolean> implements zzgos<Boolean>, RandomAccess {
    private static final zzgmz zza;

    static {
        zzgmz zzgmz = new zzgmz();
        zza = zzgmz;
        zzgmz.zzb();
    }

    private boolean[] zzb;
    private int zzc;

    zzgmz() {
        this(new boolean[10], 0);
    }

    private zzgmz(boolean[] zArr, int i) {
        this.zzb = zArr;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgmz)) {
            return super.equals(obj);
        }
        zzgmz zzgmz = (zzgmz) obj;
        if (this.zzc != zzgmz.zzc) {
            return false;
        }
        boolean[] zArr = zzgmz.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != zArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzc; i2++) {
            i = (i * 31) + zzgon.zza(this.zzb[i2]);
        }
        return i;
    }

    public final int size() {
        return this.zzc;
    }

    public final void zza(boolean z) {
        zza(this.zzc, z);
    }

    private final void zza(int i, boolean z) {
        int i2;
        zzc();
        if (i < 0 || i > (i2 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzc(i));
        }
        boolean[] zArr = this.zzb;
        if (i2 < zArr.length) {
            System.arraycopy(zArr, i, zArr, i + 1, i2 - i);
        } else {
            boolean[] zArr2 = new boolean[(((i2 * 3) / 2) + 1)];
            System.arraycopy(zArr, 0, zArr2, 0, i);
            System.arraycopy(this.zzb, i, zArr2, i + 1, this.zzc - i);
            this.zzb = zArr2;
        }
        this.zzb[i] = z;
        this.zzc++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Boolean> collection) {
        zzc();
        zzgon.zza(collection);
        if (!(collection instanceof zzgmz)) {
            return super.addAll(collection);
        }
        zzgmz zzgmz = (zzgmz) collection;
        int i = zzgmz.zzc;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzc;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            boolean[] zArr = this.zzb;
            if (i3 > zArr.length) {
                this.zzb = Arrays.copyOf(zArr, i3);
            }
            System.arraycopy(zzgmz.zzb, 0, this.zzb, this.zzc, zzgmz.zzc);
            this.zzc = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzc();
        for (int i = 0; i < this.zzc; i++) {
            if (obj.equals(Boolean.valueOf(this.zzb[i]))) {
                boolean[] zArr = this.zzb;
                System.arraycopy(zArr, i + 1, zArr, i, this.zzc - i);
                this.zzc--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzb(int i) {
        if (i < 0 || i >= this.zzc) {
            throw new IndexOutOfBoundsException(zzc(i));
        }
    }

    private final String zzc(int i) {
        int i2 = this.zzc;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        zzc();
        zzb(i);
        boolean[] zArr = this.zzb;
        boolean z = zArr[i];
        zArr[i] = booleanValue;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        zzb(i);
        boolean[] zArr = this.zzb;
        boolean z = zArr[i];
        System.arraycopy(zArr, i + 1, zArr, i, this.zzc - i);
        this.zzc--;
        this.modCount++;
        return Boolean.valueOf(z);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Boolean) obj).booleanValue());
    }

    public final /* synthetic */ zzgos zza(int i) {
        if (i >= this.zzc) {
            return new zzgmz(Arrays.copyOf(this.zzb, i), this.zzc);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzb(i);
        return Boolean.valueOf(this.zzb[i]);
    }
}
