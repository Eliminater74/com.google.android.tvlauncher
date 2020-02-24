package com.google.android.exoplayer2.util;

import java.util.Arrays;

public final class LongArray {
    private static final int DEFAULT_INITIAL_CAPACITY = 32;
    private int size;
    private long[] values;

    public LongArray() {
        this(32);
    }

    public LongArray(int initialCapacity) {
        this.values = new long[initialCapacity];
    }

    public void add(long value) {
        int i = this.size;
        long[] jArr = this.values;
        if (i == jArr.length) {
            this.values = Arrays.copyOf(jArr, i * 2);
        }
        long[] jArr2 = this.values;
        int i2 = this.size;
        this.size = i2 + 1;
        jArr2[i2] = value;
    }

    public long get(int index) {
        if (index >= 0 && index < this.size) {
            return this.values[index];
        }
        int i = this.size;
        StringBuilder sb = new StringBuilder(46);
        sb.append("Invalid index ");
        sb.append(index);
        sb.append(", size is ");
        sb.append(i);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public int size() {
        return this.size;
    }

    public long[] toArray() {
        return Arrays.copyOf(this.values, this.size);
    }
}
