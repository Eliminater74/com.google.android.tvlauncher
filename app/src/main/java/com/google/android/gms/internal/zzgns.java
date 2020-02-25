package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: DoubleArrayList */
final class zzgns extends zzgms<Double> implements zzgos<Double>, RandomAccess {
    private static final zzgns zza;

    static {
        zzgns zzgns = new zzgns();
        zza = zzgns;
        zzgns.zzb();
    }

    private double[] zzb;
    private int zzc;

    zzgns() {
        this(new double[10], 0);
    }

    private zzgns(double[] dArr, int i) {
        this.zzb = dArr;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgns)) {
            return super.equals(obj);
        }
        zzgns zzgns = (zzgns) obj;
        if (this.zzc != zzgns.zzc) {
            return false;
        }
        double[] dArr = zzgns.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != dArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzc; i2++) {
            i = (i * 31) + zzgon.zza(Double.doubleToLongBits(this.zzb[i2]));
        }
        return i;
    }

    public final int size() {
        return this.zzc;
    }

    public final void zza(double d) {
        zza(this.zzc, d);
    }

    private final void zza(int i, double d) {
        int i2;
        zzc();
        if (i < 0 || i > (i2 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzc(i));
        }
        double[] dArr = this.zzb;
        if (i2 < dArr.length) {
            System.arraycopy(dArr, i, dArr, i + 1, i2 - i);
        } else {
            double[] dArr2 = new double[(((i2 * 3) / 2) + 1)];
            System.arraycopy(dArr, 0, dArr2, 0, i);
            System.arraycopy(this.zzb, i, dArr2, i + 1, this.zzc - i);
            this.zzb = dArr2;
        }
        this.zzb[i] = d;
        this.zzc++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Double> collection) {
        zzc();
        zzgon.zza(collection);
        if (!(collection instanceof zzgns)) {
            return super.addAll(collection);
        }
        zzgns zzgns = (zzgns) collection;
        int i = zzgns.zzc;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzc;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            double[] dArr = this.zzb;
            if (i3 > dArr.length) {
                this.zzb = Arrays.copyOf(dArr, i3);
            }
            System.arraycopy(zzgns.zzb, 0, this.zzb, this.zzc, zzgns.zzc);
            this.zzc = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzc();
        for (int i = 0; i < this.zzc; i++) {
            if (obj.equals(Double.valueOf(this.zzb[i]))) {
                double[] dArr = this.zzb;
                System.arraycopy(dArr, i + 1, dArr, i, this.zzc - i);
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
        double doubleValue = ((Double) obj).doubleValue();
        zzc();
        zzb(i);
        double[] dArr = this.zzb;
        double d = dArr[i];
        dArr[i] = doubleValue;
        return Double.valueOf(d);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        zzb(i);
        double[] dArr = this.zzb;
        double d = dArr[i];
        System.arraycopy(dArr, i + 1, dArr, i, this.zzc - i);
        this.zzc--;
        this.modCount++;
        return Double.valueOf(d);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Double) obj).doubleValue());
    }

    public final /* synthetic */ zzgos zza(int i) {
        if (i >= this.zzc) {
            return new zzgns(Arrays.copyOf(this.zzb, i), this.zzc);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzb(i);
        return Double.valueOf(this.zzb[i]);
    }
}
