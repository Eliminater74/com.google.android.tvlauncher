package com.google.android.exoplayer2.upstream.cache;

import com.google.android.exoplayer2.upstream.DataSink;

public final class CacheDataSinkFactory implements DataSink.Factory {
    private final int bufferSize;
    private final Cache cache;
    private final long fragmentSize;
    private boolean syncFileDescriptor;

    public CacheDataSinkFactory(Cache cache2, long fragmentSize2) {
        this(cache2, fragmentSize2, CacheDataSink.DEFAULT_BUFFER_SIZE);
    }

    public CacheDataSinkFactory(Cache cache2, long fragmentSize2, int bufferSize2) {
        this.cache = cache2;
        this.fragmentSize = fragmentSize2;
        this.bufferSize = bufferSize2;
    }

    public CacheDataSinkFactory experimental_setSyncFileDescriptor(boolean syncFileDescriptor2) {
        this.syncFileDescriptor = syncFileDescriptor2;
        return this;
    }

    public DataSink createDataSink() {
        CacheDataSink dataSink = new CacheDataSink(this.cache, this.fragmentSize, this.bufferSize);
        dataSink.experimental_setSyncFileDescriptor(this.syncFileDescriptor);
        return dataSink;
    }
}
