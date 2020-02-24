package com.google.android.gms.internal;

import java.util.Arrays;

/* compiled from: ImmutableCollection */
class zzfkh<E> extends zzfki<E> {
    Object[] zza = new Object[4];
    int zzb = 0;
    boolean zzc;

    zzfkh(int i) {
    }

    public zzfkh<E> zza(E e) {
        zzfkb.zza(e);
        int i = this.zzb + 1;
        Object[] objArr = this.zza;
        if (objArr.length < i) {
            int length = objArr.length;
            if (i >= 0) {
                int i2 = length + (length >> 1) + 1;
                if (i2 < i) {
                    i2 = Integer.highestOneBit(i - 1) << 1;
                }
                if (i2 < 0) {
                    i2 = Integer.MAX_VALUE;
                }
                this.zza = Arrays.copyOf(objArr, i2);
                this.zzc = false;
            } else {
                throw new AssertionError("cannot store more than MAX_VALUE elements");
            }
        } else if (this.zzc) {
            this.zza = (Object[]) objArr.clone();
            this.zzc = false;
        }
        Object[] objArr2 = this.zza;
        int i3 = this.zzb;
        this.zzb = i3 + 1;
        objArr2[i3] = e;
        return this;
    }
}
