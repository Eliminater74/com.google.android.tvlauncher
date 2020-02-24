package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.internal.Hide;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T> implements DataBuffer<T> {
    @Hide
    protected final DataHolder zza;

    @Hide
    protected AbstractDataBuffer(DataHolder dataHolder) {
        this.zza = dataHolder;
    }

    public abstract T get(int i);

    public int getCount() {
        DataHolder dataHolder = this.zza;
        if (dataHolder == null) {
            return 0;
        }
        return dataHolder.getCount();
    }

    @Deprecated
    public final void close() {
        release();
    }

    @Deprecated
    public boolean isClosed() {
        DataHolder dataHolder = this.zza;
        return dataHolder == null || dataHolder.isClosed();
    }

    @Hide
    public final Bundle zza() {
        return this.zza.zza();
    }

    public Iterator<T> iterator() {
        return new zzb(this);
    }

    public Iterator<T> singleRefIterator() {
        return new zzh(this);
    }

    public void release() {
        DataHolder dataHolder = this.zza;
        if (dataHolder != null) {
            dataHolder.close();
        }
    }
}
