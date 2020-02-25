package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: LongArrayList */
final class zzgph extends zzgms<Long> implements zzgos<Long>, RandomAccess {
    private static final zzgph zza;

    static {
        zzgph zzgph = new zzgph();
        zza = zzgph;
        zzgph.zzb();
    }

    private long[] zzb;
    private int zzc;

    zzgph() {
        this(new long[10], 0);
    }

    private zzgph(long[] jArr, int i) {
        this.zzb = jArr;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgph)) {
            return super.equals(obj);
        }
        zzgph zzgph = (zzgph) obj;
        if (this.zzc != zzgph.zzc) {
            return false;
        }
        long[] jArr = zzgph.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != jArr[i]) {
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

    public final long zzb(int i) {
        zzc(i);
        return this.zzb[i];
    }

    public final int size() {
        return this.zzc;
    }

    public final void zza(long j) {
        zza(this.zzc, j);
    }

    private final void zza(int i, long j) {
        int i2;
        zzc();
        if (i < 0 || i > (i2 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzd(i));
        }
        long[] jArr = this.zzb;
        if (i2 < jArr.length) {
            System.arraycopy(jArr, i, jArr, i + 1, i2 - i);
        } else {
            long[] jArr2 = new long[(((i2 * 3) / 2) + 1)];
            System.arraycopy(jArr, 0, jArr2, 0, i);
            System.arraycopy(this.zzb, i, jArr2, i + 1, this.zzc - i);
            this.zzb = jArr2;
        }
        this.zzb[i] = j;
        this.zzc++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Long> collection) {
        zzc();
        zzgon.zza(collection);
        if (!(collection instanceof zzgph)) {
            return super.addAll(collection);
        }
        zzgph zzgph = (zzgph) collection;
        int i = zzgph.zzc;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzc;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            long[] jArr = this.zzb;
            if (i3 > jArr.length) {
                this.zzb = Arrays.copyOf(jArr, i3);
            }
            System.arraycopy(zzgph.zzb, 0, this.zzb, this.zzc, zzgph.zzc);
            this.zzc = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzc();
        for (int i = 0; i < this.zzc; i++) {
            if (obj.equals(Long.valueOf(this.zzb[i]))) {
                long[] jArr = this.zzb;
                System.arraycopy(jArr, i + 1, jArr, i, this.zzc - i);
                this.zzc--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    private final void zzc(int i) {
        if (i < 0 || i >= this.zzc) {
            throw new IndexOutOfBoundsException(zzd(i));
        }
    }

    private final String zzd(int i) {
        int i2 = this.zzc;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(i);
        sb.append(", Size:");
        sb.append(i2);
        return sb.toString();
    }

    public final /* synthetic */ Object set(int i, Object obj) {
        long longValue = ((Long) obj).longValue();
        zzc();
        zzc(i);
        long[] jArr = this.zzb;
        long j = jArr[i];
        jArr[i] = longValue;
        return Long.valueOf(j);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        zzc(i);
        long[] jArr = this.zzb;
        long j = jArr[i];
        System.arraycopy(jArr, i + 1, jArr, i, this.zzc - i);
        this.zzc--;
        this.modCount++;
        return Long.valueOf(j);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Long) obj).longValue());
    }

    public final /* synthetic */ zzgos zza(int i) {
        if (i >= this.zzc) {
            return new zzgph(Arrays.copyOf(this.zzb, i), this.zzc);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        return Long.valueOf(zzb(i));
    }
}
