package com.google.android.libraries.performance.primes.hprof.collect;

import com.google.android.libraries.stitch.util.Preconditions;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.Arrays;

public final class IntIntMap {
    static final int[] TABLE_SIZES = {5, 11, 23, 47, 97, ClientAnalytics.LogRequest.LogSource.MOB_DOG_VALUE, ClientAnalytics.LogRequest.LogSource.BOOKS_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.FLUTTER_SAMPLE_VALUE, 1597, 3203, 6421, 12853, 25717, 51437, 102877, 205759, 411527, 823117, 1646237, 3292489, 6584983, 13169977, 26339969, 52679969, 105359939, 210719881, 421439783, 842879579, 1685759167, 2147483629};
    private final int emptyValue;
    private int[] keys;
    private int size;
    private int sizeIndex;
    private int[] values;

    static int hash(int input) {
        return (input << 1) - (input << 8);
    }

    public IntIntMap() {
        this(-1);
    }

    public IntIntMap(int emptyValue2) {
        this.emptyValue = emptyValue2;
        init();
    }

    public void clear() {
        init();
    }

    private void init() {
        this.sizeIndex = 0;
        int[] iArr = TABLE_SIZES;
        int i = this.sizeIndex;
        this.keys = new int[iArr[i]];
        this.values = new int[iArr[i]];
        Arrays.fill(this.values, this.emptyValue);
    }

    public int putIfAbsent(int key, int value) {
        Preconditions.checkArgument(value != this.emptyValue, "Cannot add emptyValue to map");
        int index = findKeyIndex(key);
        int[] iArr = this.values;
        if (iArr[index] != this.emptyValue) {
            return iArr[index];
        }
        int[] iArr2 = this.keys;
        iArr2[index] = key;
        this.size++;
        iArr[index] = value;
        if (this.size > iArr2.length / 2) {
            resize();
        }
        return this.emptyValue;
    }

    private void resize() {
        int i = this.sizeIndex;
        int[] iArr = TABLE_SIZES;
        boolean z = true;
        if (i < iArr.length - 1) {
            int[] oldKeys = this.keys;
            int[] oldValues = this.values;
            this.sizeIndex = i + 1;
            int i2 = this.sizeIndex;
            this.keys = new int[iArr[i2]];
            this.values = new int[iArr[i2]];
            Arrays.fill(this.values, this.emptyValue);
            int oldSize = this.size;
            int oldCap = oldKeys.length;
            this.size = 0;
            for (int i3 = 0; i3 < oldCap; i3++) {
                if (oldValues[i3] != this.emptyValue) {
                    putIfAbsent(oldKeys[i3], oldValues[i3]);
                }
            }
            if (oldSize != this.size) {
                z = false;
            }
            Preconditions.checkState(z);
            return;
        }
        throw new IllegalStateException("Too many items, you'd better use array map instead.");
    }

    public int get(int key) {
        return this.values[findKeyIndex(key)];
    }

    private int findKeyIndex(int key) {
        int tableSize = this.keys.length;
        int index = (hash(key) & Integer.MAX_VALUE) % tableSize;
        while (this.values[index] != this.emptyValue && this.keys[index] != key) {
            index++;
            if (index >= tableSize) {
                index = 0;
            }
        }
        return index;
    }

    public int size() {
        return this.size;
    }

    public boolean containsKey(int key) {
        return get(key) != this.emptyValue;
    }

    public Enumerator enumerator() {
        return new Enumerator(this.keys, this.values, this.emptyValue);
    }

    public static class Enumerator {
        private final int emptyValue;
        private int key;
        private final int[] keys;
        private int nextIndex;
        private int value;
        private final int[] values;

        public Enumerator(int[] keys2, int[] values2, int emptyValue2) {
            this.keys = keys2;
            this.values = values2;
            this.emptyValue = emptyValue2;
        }

        public boolean next() {
            this.value = this.emptyValue;
            while (this.value == this.emptyValue) {
                int i = this.nextIndex;
                int[] iArr = this.values;
                if (i >= iArr.length) {
                    break;
                }
                this.nextIndex = i + 1;
                this.value = iArr[i];
            }
            int i2 = this.nextIndex;
            if (i2 > 0) {
                this.key = this.keys[i2 - 1];
            }
            if (this.value != this.emptyValue) {
                return true;
            }
            return false;
        }

        public int getKey() {
            return this.key;
        }

        public int getValue() {
            return this.value;
        }
    }
}
