package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

/* compiled from: FloatArrayList */
final class zzgog extends zzgms<Float> implements zzgos<Float>, RandomAccess {
    private static final zzgog zza;
    private float[] zzb;
    private int zzc;

    zzgog() {
        this(new float[10], 0);
    }

    private zzgog(float[] fArr, int i) {
        this.zzb = fArr;
        this.zzc = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzgog)) {
            return super.equals(obj);
        }
        zzgog zzgog = (zzgog) obj;
        if (this.zzc != zzgog.zzc) {
            return false;
        }
        float[] fArr = zzgog.zzb;
        for (int i = 0; i < this.zzc; i++) {
            if (this.zzb[i] != fArr[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int i = 1;
        for (int i2 = 0; i2 < this.zzc; i2++) {
            i = (i * 31) + Float.floatToIntBits(this.zzb[i2]);
        }
        return i;
    }

    public final int size() {
        return this.zzc;
    }

    public final void zza(float f) {
        zza(this.zzc, f);
    }

    private final void zza(int i, float f) {
        int i2;
        zzc();
        if (i < 0 || i > (i2 = this.zzc)) {
            throw new IndexOutOfBoundsException(zzc(i));
        }
        float[] fArr = this.zzb;
        if (i2 < fArr.length) {
            System.arraycopy(fArr, i, fArr, i + 1, i2 - i);
        } else {
            float[] fArr2 = new float[(((i2 * 3) / 2) + 1)];
            System.arraycopy(fArr, 0, fArr2, 0, i);
            System.arraycopy(this.zzb, i, fArr2, i + 1, this.zzc - i);
            this.zzb = fArr2;
        }
        this.zzb[i] = f;
        this.zzc++;
        this.modCount++;
    }

    public final boolean addAll(Collection<? extends Float> collection) {
        zzc();
        zzgon.zza(collection);
        if (!(collection instanceof zzgog)) {
            return super.addAll(collection);
        }
        zzgog zzgog = (zzgog) collection;
        int i = zzgog.zzc;
        if (i == 0) {
            return false;
        }
        int i2 = this.zzc;
        if (Integer.MAX_VALUE - i2 >= i) {
            int i3 = i2 + i;
            float[] fArr = this.zzb;
            if (i3 > fArr.length) {
                this.zzb = Arrays.copyOf(fArr, i3);
            }
            System.arraycopy(zzgog.zzb, 0, this.zzb, this.zzc, zzgog.zzc);
            this.zzc = i3;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public final boolean remove(Object obj) {
        zzc();
        for (int i = 0; i < this.zzc; i++) {
            if (obj.equals(Float.valueOf(this.zzb[i]))) {
                float[] fArr = this.zzb;
                System.arraycopy(fArr, i + 1, fArr, i, this.zzc - i);
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
        float floatValue = ((Float) obj).floatValue();
        zzc();
        zzb(i);
        float[] fArr = this.zzb;
        float f = fArr[i];
        fArr[i] = floatValue;
        return Float.valueOf(f);
    }

    public final /* synthetic */ Object remove(int i) {
        zzc();
        zzb(i);
        float[] fArr = this.zzb;
        float f = fArr[i];
        System.arraycopy(fArr, i + 1, fArr, i, this.zzc - i);
        this.zzc--;
        this.modCount++;
        return Float.valueOf(f);
    }

    public final /* synthetic */ void add(int i, Object obj) {
        zza(i, ((Float) obj).floatValue());
    }

    public final /* synthetic */ zzgos zza(int i) {
        if (i >= this.zzc) {
            return new zzgog(Arrays.copyOf(this.zzb, i), this.zzc);
        }
        throw new IllegalArgumentException();
    }

    public final /* synthetic */ Object get(int i) {
        zzb(i);
        return Float.valueOf(this.zzb[i]);
    }

    static {
        zzgog zzgog = new zzgog();
        zza = zzgog;
        zzgog.zzb();
    }
}
