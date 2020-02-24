package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Hide;
import java.util.NoSuchElementException;

@Hide
/* compiled from: SingleRefDataBufferIterator */
public final class zzh<T> extends zzb<T> {
    private T zzc;

    public zzh(DataBuffer<T> dataBuffer) {
        super(dataBuffer);
    }

    public final T next() {
        if (hasNext()) {
            this.zzb++;
            if (this.zzb == 0) {
                this.zzc = this.zza.get(0);
                T t = this.zzc;
                if (!(t instanceof zzc)) {
                    String valueOf = String.valueOf(t.getClass());
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 44);
                    sb.append("DataBuffer reference of type ");
                    sb.append(valueOf);
                    sb.append(" is not movable");
                    throw new IllegalStateException(sb.toString());
                }
            } else {
                ((zzc) this.zzc).zza(this.zzb);
            }
            return this.zzc;
        }
        int i = this.zzb;
        StringBuilder sb2 = new StringBuilder(46);
        sb2.append("Cannot advance the iterator beyond ");
        sb2.append(i);
        throw new NoSuchElementException(sb2.toString());
    }
}
