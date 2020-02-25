package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

import java.util.Arrays;

@GwtIncompatible
class CompactLinkedHashMap<K, V> extends CompactHashMap<K, V> {
    private static final int ENDPOINT = -2;
    private final boolean accessOrder;
    @MonotonicNonNullDecl
    @VisibleForTesting
    transient long[] links;
    private transient int firstEntry;
    private transient int lastEntry;

    CompactLinkedHashMap() {
        this(3);
    }

    CompactLinkedHashMap(int expectedSize) {
        this(expectedSize, false);
    }

    CompactLinkedHashMap(int expectedSize, boolean accessOrder2) {
        super(expectedSize);
        this.accessOrder = accessOrder2;
    }

    public static <K, V> CompactLinkedHashMap<K, V> create() {
        return new CompactLinkedHashMap<>();
    }

    public static <K, V> CompactLinkedHashMap<K, V> createWithExpectedSize(int expectedSize) {
        return new CompactLinkedHashMap<>(expectedSize);
    }

    /* access modifiers changed from: package-private */
    public void init(int expectedSize) {
        super.init(expectedSize);
        this.firstEntry = -2;
        this.lastEntry = -2;
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
    public void allocArrays() {
        super.allocArrays();
        this.links = new long[this.keys.length];
        Arrays.fill(this.links, -1L);
    }

    private int getPredecessor(int entry) {
        return (int) (this.links[entry] >>> 32);
    }

    /* access modifiers changed from: package-private */
    public int getSuccessor(int entry) {
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
    public void insertEntry(int entryIndex, K key, V value, int hash) {
        super.insertEntry(entryIndex, key, value, hash);
        setSucceeds(this.lastEntry, entryIndex);
        setSucceeds(entryIndex, -2);
    }

    /* access modifiers changed from: package-private */
    public void accessEntry(int index) {
        if (this.accessOrder) {
            setSucceeds(getPredecessor(index), getSuccessor(index));
            setSucceeds(this.lastEntry, index);
            setSucceeds(index, -2);
            this.modCount++;
        }
    }

    /* access modifiers changed from: package-private */
    public void moveLastEntry(int dstIndex) {
        int srcIndex = size() - 1;
        super.moveLastEntry(dstIndex);
        setSucceeds(getPredecessor(dstIndex), getSuccessor(dstIndex));
        if (dstIndex < srcIndex) {
            setSucceeds(getPredecessor(srcIndex), dstIndex);
            setSucceeds(dstIndex, getSuccessor(srcIndex));
        }
        this.links[srcIndex] = -1;
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
        if (oldCapacity < newCapacity) {
            Arrays.fill(this.links, oldCapacity, newCapacity, -1L);
        }
    }

    /* access modifiers changed from: package-private */
    public int firstEntryIndex() {
        return this.firstEntry;
    }

    /* access modifiers changed from: package-private */
    public int adjustAfterRemove(int indexBeforeRemove, int indexRemoved) {
        return indexBeforeRemove >= size() ? indexRemoved : indexBeforeRemove;
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
    public void clear() {
        if (!needsAllocArrays()) {
            this.firstEntry = -2;
            this.lastEntry = -2;
            Arrays.fill(this.links, 0, size(), -1L);
            super.clear();
        }
    }
}
