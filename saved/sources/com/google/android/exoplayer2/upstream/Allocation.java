package com.google.android.exoplayer2.upstream;

public final class Allocation {
    public final byte[] data;
    public final int offset;

    public Allocation(byte[] data2, int offset2) {
        this.data = data2;
        this.offset = offset2;
    }
}
