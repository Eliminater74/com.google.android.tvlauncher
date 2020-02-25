package com.google.android.exoplayer2.decoder;

public interface Decoder<I, O, E extends Exception> {
    I dequeueInputBuffer() throws Exception;

    O dequeueOutputBuffer() throws Exception;

    void flush();

    String getName();

    void queueInputBuffer(DecoderInputBuffer decoderInputBuffer) throws Exception;

    void release();
}
