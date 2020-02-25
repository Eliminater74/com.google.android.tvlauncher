package com.google.android.gms.common.api;

import android.os.Bundle;

import com.google.android.gms.common.data.AbstractDataBuffer;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.Hide;

import java.util.Iterator;

@Hide
/* compiled from: DataBufferResponse */
public class zzb<T, R extends AbstractDataBuffer<T> & Result> extends Response<R> implements DataBuffer<T> {
    public int getCount() {
        return ((AbstractDataBuffer) getResult()).getCount();
    }

    public T get(int i) {
        return ((AbstractDataBuffer) getResult()).get(i);
    }

    public final Bundle zza() {
        return ((AbstractDataBuffer) getResult()).zza();
    }

    public void close() {
        ((AbstractDataBuffer) getResult()).close();
    }

    public boolean isClosed() {
        return ((AbstractDataBuffer) getResult()).isClosed();
    }

    public Iterator<T> iterator() {
        return ((AbstractDataBuffer) getResult()).iterator();
    }

    public Iterator<T> singleRefIterator() {
        return ((AbstractDataBuffer) getResult()).singleRefIterator();
    }

    public void release() {
        ((AbstractDataBuffer) getResult()).release();
    }
}
