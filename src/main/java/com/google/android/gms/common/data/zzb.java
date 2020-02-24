package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import java.util.Iterator;
import java.util.NoSuchElementException;

@Hide
/* compiled from: DataBufferIterator */
public class zzb<T> implements Iterator<T> {
    protected final DataBuffer<T> zza;
    protected int zzb = -1;

    public zzb(DataBuffer<T> dataBuffer) {
        this.zza = (DataBuffer) zzau.zza(dataBuffer);
    }

    public boolean hasNext() {
        return this.zzb < this.zza.getCount() - 1;
    }

    public T next() {
        if (hasNext()) {
            DataBuffer<T> dataBuffer = this.zza;
            int i = this.zzb + 1;
            this.zzb = i;
            return dataBuffer.get(i);
        }
        int i2 = this.zzb;
        StringBuilder sb = new StringBuilder(46);
        sb.append("Cannot advance the iterator beyond ");
        sb.append(i2);
        throw new NoSuchElementException(sb.toString());
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
    }
}
