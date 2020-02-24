package com.google.android.exoplayer2.util;

import android.support.annotation.Nullable;
import java.util.Arrays;

public final class TimedValueQueue<V> {
    private static final int INITIAL_BUFFER_SIZE = 10;
    private int first;
    private int size;
    private long[] timestamps;
    private V[] values;

    public TimedValueQueue() {
        this(10);
    }

    public TimedValueQueue(int initialBufferSize) {
        this.timestamps = new long[initialBufferSize];
        this.values = newArray(initialBufferSize);
    }

    public synchronized void add(long timestamp, V value) {
        clearBufferOnTimeDiscontinuity(timestamp);
        doubleCapacityIfFull();
        addUnchecked(timestamp, value);
    }

    public synchronized void clear() {
        this.first = 0;
        this.size = 0;
        Arrays.fill(this.values, (Object) null);
    }

    public synchronized int size() {
        return this.size;
    }

    @Nullable
    public synchronized V pollFloor(long timestamp) {
        return poll(timestamp, true);
    }

    @Nullable
    public synchronized V poll(long timestamp) {
        return poll(timestamp, false);
    }

    @Nullable
    private V poll(long timestamp, boolean onlyOlder) {
        V value = null;
        long previousTimeDiff = Long.MAX_VALUE;
        while (this.size > 0) {
            long timeDiff = timestamp - this.timestamps[this.first];
            if (timeDiff < 0 && (onlyOlder || (-timeDiff) >= previousTimeDiff)) {
                break;
            }
            previousTimeDiff = timeDiff;
            V[] vArr = this.values;
            int i = this.first;
            value = vArr[i];
            vArr[i] = null;
            this.first = (i + 1) % vArr.length;
            this.size--;
        }
        return value;
    }

    private void clearBufferOnTimeDiscontinuity(long timestamp) {
        int i = this.size;
        if (i > 0) {
            if (timestamp <= this.timestamps[((this.first + i) - 1) % this.values.length]) {
                clear();
            }
        }
    }

    private void doubleCapacityIfFull() {
        int capacity = this.values.length;
        if (this.size >= capacity) {
            int newCapacity = capacity * 2;
            long[] newTimestamps = new long[newCapacity];
            V[] newValues = newArray(newCapacity);
            int i = this.first;
            int length = capacity - i;
            System.arraycopy(this.timestamps, i, newTimestamps, 0, length);
            System.arraycopy(this.values, this.first, newValues, 0, length);
            int i2 = this.first;
            if (i2 > 0) {
                System.arraycopy(this.timestamps, 0, newTimestamps, length, i2);
                System.arraycopy(this.values, 0, newValues, length, this.first);
            }
            this.timestamps = newTimestamps;
            this.values = newValues;
            this.first = 0;
        }
    }

    private void addUnchecked(long timestamp, V value) {
        int i = this.first;
        int i2 = this.size;
        V[] vArr = this.values;
        int next = (i + i2) % vArr.length;
        this.timestamps[next] = timestamp;
        vArr[next] = value;
        this.size = i2 + 1;
    }

    private static <V> V[] newArray(int length) {
        return new Object[length];
    }
}
