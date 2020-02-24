package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
class CompactHashSet<E> extends AbstractSet<E> implements Serializable {
    @VisibleForTesting
    static final int DEFAULT_SIZE = 3;
    private static final long HASH_MASK = -4294967296L;
    private static final float LOAD_FACTOR = 1.0f;
    private static final long NEXT_MASK = 4294967295L;
    static final int UNSET = -1;
    @MonotonicNonNullDecl
    transient Object[] elements;
    /* access modifiers changed from: private */
    @MonotonicNonNullDecl
    public transient long[] entries;
    transient int modCount;
    private transient int size;
    @MonotonicNonNullDecl
    private transient int[] table;

    public static <E> CompactHashSet<E> create() {
        return new CompactHashSet<>();
    }

    public static <E> CompactHashSet<E> create(Collection<? extends E> collection) {
        CompactHashSet<E> set = createWithExpectedSize(collection.size());
        set.addAll(collection);
        return set;
    }

    public static <E> CompactHashSet<E> create(E... elements2) {
        CompactHashSet<E> set = createWithExpectedSize(elements2.length);
        Collections.addAll(set, elements2);
        return set;
    }

    public static <E> CompactHashSet<E> createWithExpectedSize(int expectedSize) {
        return new CompactHashSet<>(expectedSize);
    }

    CompactHashSet() {
        init(3);
    }

    CompactHashSet(int expectedSize) {
        init(expectedSize);
    }

    /* access modifiers changed from: package-private */
    public void init(int expectedSize) {
        Preconditions.checkArgument(expectedSize >= 0, "Initial capacity must be non-negative");
        this.modCount = Math.max(1, expectedSize);
    }

    /* access modifiers changed from: package-private */
    public boolean needsAllocArrays() {
        return this.table == null;
    }

    /* access modifiers changed from: package-private */
    public void allocArrays() {
        Preconditions.checkState(needsAllocArrays(), "Arrays already allocated");
        int expectedSize = this.modCount;
        this.table = newTable(Hashing.closedTableSize(expectedSize, 1.0d));
        this.entries = newEntries(expectedSize);
        this.elements = new Object[expectedSize];
    }

    private static int[] newTable(int size2) {
        int[] array = new int[size2];
        Arrays.fill(array, -1);
        return array;
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
    private static long[] newEntries(int size2) {
        long[] array = new long[size2];
        Arrays.fill(array, -1L);
        return array;
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    /* access modifiers changed from: private */
    public static int getHash(long entry) {
        return (int) (entry >>> 32);
    }

    private static int getNext(long entry) {
        return (int) entry;
    }

    private static long swapNext(long entry, int newNext) {
        return (HASH_MASK & entry) | (((long) newNext) & NEXT_MASK);
    }

    @CanIgnoreReturnValue
    public boolean add(@NullableDecl E object) {
        int last;
        long entry;
        if (needsAllocArrays()) {
            allocArrays();
        }
        long[] entries2 = this.entries;
        Object[] elements2 = this.elements;
        int hash = Hashing.smearedHash(object);
        int tableIndex = hashTableMask() & hash;
        int newEntryIndex = this.size;
        int[] iArr = this.table;
        int next = iArr[tableIndex];
        if (next == -1) {
            iArr[tableIndex] = newEntryIndex;
        } else {
            do {
                last = next;
                entry = entries2[next];
                if (getHash(entry) == hash && Objects.equal(object, elements2[next])) {
                    return false;
                }
                next = getNext(entry);
            } while (next != -1);
            entries2[last] = swapNext(entry, newEntryIndex);
        }
        if (newEntryIndex != Integer.MAX_VALUE) {
            int newSize = newEntryIndex + 1;
            resizeMeMaybe(newSize);
            insertEntry(newEntryIndex, object, hash);
            this.size = newSize;
            int oldCapacity = this.table.length;
            if (Hashing.needsResizing(newEntryIndex, oldCapacity, 1.0d)) {
                resizeTable(oldCapacity * 2);
            }
            this.modCount++;
            return true;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    /* access modifiers changed from: package-private */
    public void insertEntry(int entryIndex, E object, int hash) {
        this.entries[entryIndex] = (((long) hash) << 32) | NEXT_MASK;
        this.elements[entryIndex] = object;
    }

    private void resizeMeMaybe(int newSize) {
        int entriesSize = this.entries.length;
        if (newSize > entriesSize) {
            int newCapacity = Math.max(1, entriesSize >>> 1) + entriesSize;
            if (newCapacity < 0) {
                newCapacity = Integer.MAX_VALUE;
            }
            if (newCapacity != entriesSize) {
                resizeEntries(newCapacity);
            }
        }
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
        this.elements = Arrays.copyOf(this.elements, newCapacity);
        long[] entries2 = this.entries;
        int oldCapacity = entries2.length;
        long[] entries3 = Arrays.copyOf(entries2, newCapacity);
        if (newCapacity > oldCapacity) {
            Arrays.fill(entries3, oldCapacity, newCapacity, -1L);
        }
        this.entries = entries3;
    }

    private void resizeTable(int newCapacity) {
        int[] newTable = newTable(newCapacity);
        long[] entries2 = this.entries;
        int mask = newTable.length - 1;
        for (int i = 0; i < this.size; i++) {
            int hash = getHash(entries2[i]);
            int tableIndex = hash & mask;
            int next = newTable[tableIndex];
            newTable[tableIndex] = i;
            entries2[i] = (((long) hash) << 32) | (NEXT_MASK & ((long) next));
        }
        this.table = newTable;
    }

    public boolean contains(@NullableDecl Object object) {
        if (needsAllocArrays()) {
            return false;
        }
        int hash = Hashing.smearedHash(object);
        int next = this.table[hashTableMask() & hash];
        while (next != -1) {
            long entry = this.entries[next];
            if (getHash(entry) == hash && Objects.equal(object, this.elements[next])) {
                return true;
            }
            next = getNext(entry);
        }
        return false;
    }

    @CanIgnoreReturnValue
    public boolean remove(@NullableDecl Object object) {
        if (needsAllocArrays()) {
            return false;
        }
        return remove(object, Hashing.smearedHash(object));
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public boolean remove(Object object, int hash) {
        int tableIndex = hashTableMask() & hash;
        int next = this.table[tableIndex];
        if (next == -1) {
            return false;
        }
        int last = -1;
        do {
            if (getHash(this.entries[next]) != hash || !Objects.equal(object, this.elements[next])) {
                last = next;
                next = getNext(this.entries[next]);
            } else {
                if (last == -1) {
                    this.table[tableIndex] = getNext(this.entries[next]);
                } else {
                    long[] jArr = this.entries;
                    jArr[last] = swapNext(jArr[last], getNext(jArr[next]));
                }
                moveLastEntry(next);
                this.size--;
                this.modCount++;
                return true;
            }
        } while (next != -1);
        return false;
    }

    /* access modifiers changed from: package-private */
    public void moveLastEntry(int dstIndex) {
        int previous;
        long entry;
        int srcIndex = size() - 1;
        if (dstIndex < srcIndex) {
            Object[] objArr = this.elements;
            objArr[dstIndex] = objArr[srcIndex];
            objArr[srcIndex] = null;
            long[] jArr = this.entries;
            long lastEntry = jArr[srcIndex];
            jArr[dstIndex] = lastEntry;
            jArr[srcIndex] = -1;
            int tableIndex = getHash(lastEntry) & hashTableMask();
            int[] iArr = this.table;
            int lastNext = iArr[tableIndex];
            if (lastNext == srcIndex) {
                iArr[tableIndex] = dstIndex;
                return;
            }
            do {
                previous = lastNext;
                long j = this.entries[lastNext];
                entry = j;
                lastNext = getNext(j);
            } while (lastNext != srcIndex);
            this.entries[previous] = swapNext(entry, dstIndex);
            return;
        }
        this.elements[dstIndex] = null;
        this.entries[dstIndex] = -1;
    }

    /* access modifiers changed from: package-private */
    public int firstEntryIndex() {
        return isEmpty() ? -1 : 0;
    }

    /* access modifiers changed from: package-private */
    public int getSuccessor(int entryIndex) {
        if (entryIndex + 1 < this.size) {
            return entryIndex + 1;
        }
        return -1;
    }

    /* access modifiers changed from: package-private */
    public int adjustAfterRemove(int indexBeforeRemove, int indexRemoved) {
        return indexBeforeRemove - 1;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int currentIndex = CompactHashSet.this.firstEntryIndex();
            int expectedModCount = CompactHashSet.this.modCount;
            int indexToRemove = -1;

            public boolean hasNext() {
                return this.currentIndex >= 0;
            }

            public E next() {
                checkForConcurrentModification();
                if (hasNext()) {
                    this.indexToRemove = this.currentIndex;
                    E[] eArr = CompactHashSet.this.elements;
                    int i = this.currentIndex;
                    E result = eArr[i];
                    this.currentIndex = CompactHashSet.this.getSuccessor(i);
                    return result;
                }
                throw new NoSuchElementException();
            }

            public void remove() {
                checkForConcurrentModification();
                CollectPreconditions.checkRemove(this.indexToRemove >= 0);
                this.expectedModCount++;
                CompactHashSet compactHashSet = CompactHashSet.this;
                boolean unused = compactHashSet.remove(compactHashSet.elements[this.indexToRemove], CompactHashSet.getHash(CompactHashSet.this.entries[this.indexToRemove]));
                this.currentIndex = CompactHashSet.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
                this.indexToRemove = -1;
            }

            private void checkForConcurrentModification() {
                if (CompactHashSet.this.modCount != this.expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public Object[] toArray() {
        if (needsAllocArrays()) {
            return new Object[0];
        }
        return Arrays.copyOf(this.elements, this.size);
    }

    @CanIgnoreReturnValue
    public <T> T[] toArray(T[] a) {
        if (!needsAllocArrays()) {
            return ObjectArrays.toArrayImpl(this.elements, 0, this.size, a);
        }
        if (a.length > 0) {
            a[0] = null;
        }
        return a;
    }

    public void trimToSize() {
        if (!needsAllocArrays()) {
            int size2 = this.size;
            if (size2 < this.entries.length) {
                resizeEntries(size2);
            }
            int minimumTableSize = Hashing.closedTableSize(size2, 1.0d);
            if (minimumTableSize < this.table.length) {
                resizeTable(minimumTableSize);
            }
        }
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
            this.modCount++;
            Arrays.fill(this.elements, 0, this.size, (Object) null);
            Arrays.fill(this.table, -1);
            Arrays.fill(this.entries, 0, this.size, -1L);
            this.size = 0;
        }
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeInt(this.size);
        int i = firstEntryIndex();
        while (i >= 0) {
            stream.writeObject(this.elements[i]);
            i = getSuccessor(i);
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int elementCount = stream.readInt();
        if (elementCount >= 0) {
            init(elementCount);
            for (int i = 0; i < elementCount; i++) {
                add(stream.readObject());
            }
            return;
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Invalid size: ");
        sb.append(elementCount);
        throw new InvalidObjectException(sb.toString());
    }
}
