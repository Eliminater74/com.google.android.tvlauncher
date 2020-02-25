package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;

import java.util.Arrays;

@GwtCompatible(emulated = true, serializable = true)
class ObjectCountLinkedHashMap<K> extends ObjectCountHashMap<K> {
    private static final int ENDPOINT = -2;
    @VisibleForTesting
    transient long[] links;
    private transient int firstEntry;
    private transient int lastEntry;

    ObjectCountLinkedHashMap() {
        this(3);
    }

    ObjectCountLinkedHashMap(int expectedSize) {
        this(expectedSize, 1.0f);
    }

    ObjectCountLinkedHashMap(int expectedSize, float loadFactor) {
        super(expectedSize, loadFactor);
    }

    ObjectCountLinkedHashMap(ObjectCountHashMap objectCountHashMap) {
        init(objectCountHashMap.size(), 1.0f);
        int i = objectCountHashMap.firstIndex();
        while (i != -1) {
            put(objectCountHashMap.getKey(i), objectCountHashMap.getValue(i));
            i = objectCountHashMap.nextIndex(i);
        }
    }

    public static <K> ObjectCountLinkedHashMap<K> create() {
        return new ObjectCountLinkedHashMap<>();
    }

    public static <K> ObjectCountLinkedHashMap<K> createWithExpectedSize(int expectedSize) {
        return new ObjectCountLinkedHashMap<>(expectedSize);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.Arrays.fill(long[], long):void}
     arg types: [long[], int]
     candidates:
      ClspMth{java.util.Arrays.fill(double[], double):void}
      ClspMth{java.util.Arrays.fill(byte[], byte):void}
      ClspMth{java.util.Arrays.fill(boolean[], boolean):void}
      ClspMth{java.util.Arrays.fill(char[], char):void}
      ClspMth{java.util.Arrays.fill(short[], short):void}
      ClspMth{java.util.Arrays.fill(java.lang.Object[], java.lang.Object):void}
      ClspMth{java.util.Arrays.fill(int[], int):void}
      ClspMth{java.util.Arrays.fill(float[], float):void}
      ClspMth{java.util.Arrays.fill(long[], long):void} */
    /* access modifiers changed from: package-private */
    public void init(int expectedSize, float loadFactor) {
        super.init(expectedSize, loadFactor);
        this.firstEntry = -2;
        this.lastEntry = -2;
        this.links = new long[expectedSize];
        Arrays.fill(this.links, -1L);
    }

    /* access modifiers changed from: package-private */
    public int firstIndex() {
        int i = this.firstEntry;
        if (i == -2) {
            return -1;
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public int nextIndex(int index) {
        int result = getSuccessor(index);
        if (result == -2) {
            return -1;
        }
        return result;
    }

    /* access modifiers changed from: package-private */
    public int nextIndexAfterRemove(int oldNextIndex, int removedIndex) {
        return oldNextIndex == size() ? removedIndex : oldNextIndex;
    }

    private int getPredecessor(int entry) {
        return (int) (this.links[entry] >>> 32);
    }

    private int getSuccessor(int entry) {
        return (int) this.links[entry];
    }

    private void setSuccessor(int entry, int succ) {
        long[] jArr = this.links;
        jArr[entry] = (jArr[entry] & (-1 ^ 4294967295L)) | (((long) succ) & 4294967295L);
    }

    private void setPredecessor(int entry, int pred) {
        long[] jArr = this.links;
        jArr[entry] = (jArr[entry] & (-1 ^ -4294967296L)) | (((long) pred) << 32);
    }

    private void setSucceeds(int pred, int succ) {
        if (pred == -2) {
            this.firstEntry = succ;
        } else {
            setSuccessor(pred, succ);
        }
        if (succ == -2) {
            this.lastEntry = pred;
        } else {
            setPredecessor(succ, pred);
        }
    }

    /* access modifiers changed from: package-private */
    public void insertEntry(int entryIndex, K key, int value, int hash) {
        super.insertEntry(entryIndex, key, value, hash);
        setSucceeds(this.lastEntry, entryIndex);
        setSucceeds(entryIndex, -2);
    }

    /* access modifiers changed from: package-private */
    public void moveLastEntry(int dstIndex) {
        int srcIndex = size() - 1;
        setSucceeds(getPredecessor(dstIndex), getSuccessor(dstIndex));
        if (dstIndex < srcIndex) {
            setSucceeds(getPredecessor(srcIndex), dstIndex);
            setSucceeds(dstIndex, getSuccessor(srcIndex));
        }
        super.moveLastEntry(dstIndex);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.Arrays.fill(long[], int, int, long):void}
     arg types: [long[], int, int, int]
     candidates:
      ClspMth{java.util.Arrays.fill(java.lang.Object[], int, int, java.lang.Object):void}
      ClspMth{java.util.Arrays.fill(int[], int, int, int):void}
      ClspMth{java.util.Arrays.fill(char[], int, int, char):void}
      ClspMth{java.util.Arrays.fill(boolean[], int, int, boolean):void}
      ClspMth{java.util.Arrays.fill(byte[], int, int, byte):void}
      ClspMth{java.util.Arrays.fill(double[], int, int, double):void}
      ClspMth{java.util.Arrays.fill(float[], int, int, float):void}
      ClspMth{java.util.Arrays.fill(short[], int, int, short):void}
      ClspMth{java.util.Arrays.fill(long[], int, int, long):void} */
    /* access modifiers changed from: package-private */
    public void resizeEntries(int newCapacity) {
        super.resizeEntries(newCapacity);
        long[] jArr = this.links;
        int oldCapacity = jArr.length;
        this.links = Arrays.copyOf(jArr, newCapacity);
        Arrays.fill(this.links, oldCapacity, newCapacity, -1L);
    }

    public void clear() {
        super.clear();
        this.firstEntry = -2;
        this.lastEntry = -2;
    }
}
