package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@GwtIncompatible
class CompactHashMap<K, V> extends AbstractMap<K, V> implements Serializable {
    static final int DEFAULT_SIZE = 3;
    static final int UNSET = -1;
    private static final long HASH_MASK = -4294967296L;
    private static final float LOAD_FACTOR = 1.0f;
    private static final long NEXT_MASK = 4294967295L;
    /* access modifiers changed from: private */
    public transient int size;
    @MonotonicNonNullDecl
    @VisibleForTesting
    transient long[] entries;
    @MonotonicNonNullDecl
    @VisibleForTesting
    transient Object[] keys;
    transient int modCount;
    @MonotonicNonNullDecl
    @VisibleForTesting
    transient Object[] values;
    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> entrySetView;
    @MonotonicNonNullDecl
    private transient Set<K> keySetView;
    @MonotonicNonNullDecl
    private transient int[] table;
    @MonotonicNonNullDecl
    private transient Collection<V> valuesView;

    CompactHashMap() {
        init(3);
    }

    CompactHashMap(int expectedSize) {
        init(expectedSize);
    }

    public static <K, V> CompactHashMap<K, V> create() {
        return new CompactHashMap<>();
    }

    public static <K, V> CompactHashMap<K, V> createWithExpectedSize(int expectedSize) {
        return new CompactHashMap<>(expectedSize);
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

    private static int getHash(long entry) {
        return (int) (entry >>> 32);
    }

    private static int getNext(long entry) {
        return (int) entry;
    }

    private static long swapNext(long entry, int newNext) {
        return (HASH_MASK & entry) | (((long) newNext) & NEXT_MASK);
    }

    /* access modifiers changed from: package-private */
    public void init(int expectedSize) {
        Preconditions.checkArgument(expectedSize >= 0, "Expected size must be non-negative");
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
        this.keys = new Object[expectedSize];
        this.values = new Object[expectedSize];
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    /* access modifiers changed from: package-private */
    public void accessEntry(int index) {
    }

    @NullableDecl
    @CanIgnoreReturnValue
    public V put(@NullableDecl K key, @NullableDecl V value) {
        int last;
        long entry;
        if (needsAllocArrays()) {
            allocArrays();
        }
        long[] entries2 = this.entries;
        Object[] keys2 = this.keys;
        V[] values2 = this.values;
        int hash = Hashing.smearedHash(key);
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
                if (getHash(entry) != hash || !Objects.equal(key, keys2[next])) {
                    next = getNext(entry);
                } else {
                    V oldValue = values2[next];
                    values2[next] = value;
                    accessEntry(next);
                    return oldValue;
                }
            } while (next != -1);
            entries2[last] = swapNext(entry, newEntryIndex);
        }
        if (newEntryIndex != Integer.MAX_VALUE) {
            int newSize = newEntryIndex + 1;
            resizeMeMaybe(newSize);
            insertEntry(newEntryIndex, key, value, hash);
            this.size = newSize;
            int oldCapacity = this.table.length;
            if (Hashing.needsResizing(newEntryIndex, oldCapacity, 1.0d)) {
                resizeTable(oldCapacity * 2);
            }
            this.modCount++;
            return null;
        }
        throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
    }

    /* access modifiers changed from: package-private */
    public void insertEntry(int entryIndex, @NullableDecl K key, @NullableDecl V value, int hash) {
        this.entries[entryIndex] = (((long) hash) << 32) | NEXT_MASK;
        this.keys[entryIndex] = key;
        this.values[entryIndex] = value;
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
        this.keys = Arrays.copyOf(this.keys, newCapacity);
        this.values = Arrays.copyOf(this.values, newCapacity);
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

    /* access modifiers changed from: private */
    public int indexOf(@NullableDecl Object key) {
        if (needsAllocArrays()) {
            return -1;
        }
        int hash = Hashing.smearedHash(key);
        int next = this.table[hashTableMask() & hash];
        while (next != -1) {
            long entry = this.entries[next];
            if (getHash(entry) == hash && Objects.equal(key, this.keys[next])) {
                return next;
            }
            next = getNext(entry);
        }
        return -1;
    }

    public boolean containsKey(@NullableDecl Object key) {
        return indexOf(key) != -1;
    }

    public V get(@NullableDecl Object key) {
        int index = indexOf(key);
        accessEntry(index);
        if (index == -1) {
            return null;
        }
        return this.values[index];
    }

    @NullableDecl
    @CanIgnoreReturnValue
    public V remove(@NullableDecl Object key) {
        if (needsAllocArrays()) {
            return null;
        }
        return remove(key, Hashing.smearedHash(key));
    }

    @NullableDecl
    private V remove(@NullableDecl Object key, int hash) {
        int tableIndex = hashTableMask() & hash;
        int next = this.table[tableIndex];
        if (next == -1) {
            return null;
        }
        int last = -1;
        do {
            if (getHash(this.entries[next]) != hash || !Objects.equal(key, this.keys[next])) {
                last = next;
                next = getNext(this.entries[next]);
            } else {
                V oldValue = this.values[next];
                if (last == -1) {
                    this.table[tableIndex] = getNext(this.entries[next]);
                } else {
                    long[] jArr = this.entries;
                    jArr[last] = swapNext(jArr[last], getNext(jArr[next]));
                }
                moveLastEntry(next);
                this.size--;
                this.modCount++;
                return oldValue;
            }
        } while (next != -1);
        return null;
    }

    /* access modifiers changed from: private */
    @CanIgnoreReturnValue
    public V removeEntry(int entryIndex) {
        return remove(this.keys[entryIndex], getHash(this.entries[entryIndex]));
    }

    /* access modifiers changed from: package-private */
    public void moveLastEntry(int dstIndex) {
        int previous;
        long entry;
        int srcIndex = size() - 1;
        if (dstIndex < srcIndex) {
            Object[] objArr = this.keys;
            objArr[dstIndex] = objArr[srcIndex];
            Object[] objArr2 = this.values;
            objArr2[dstIndex] = objArr2[srcIndex];
            objArr[srcIndex] = null;
            objArr2[srcIndex] = null;
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
        this.keys[dstIndex] = null;
        this.values[dstIndex] = null;
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

    public Set<K> keySet() {
        Set<K> set = this.keySetView;
        if (set != null) {
            return set;
        }
        Set<K> createKeySet = createKeySet();
        this.keySetView = createKeySet;
        return createKeySet;
    }

    /* access modifiers changed from: package-private */
    public Set<K> createKeySet() {
        return new KeySetView();
    }

    /* access modifiers changed from: package-private */
    public Iterator<K> keySetIterator() {
        return new CompactHashMap<K, V>.Itr<K>() {
            /* access modifiers changed from: package-private */
            public K getOutput(int entry) {
                return CompactHashMap.this.keys[entry];
            }
        };
    }

    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = this.entrySetView;
        if (set != null) {
            return set;
        }
        Set<Map.Entry<K, V>> createEntrySet = createEntrySet();
        this.entrySetView = createEntrySet;
        return createEntrySet;
    }

    /* access modifiers changed from: package-private */
    public Set<Map.Entry<K, V>> createEntrySet() {
        return new EntrySetView();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<K, V>> entrySetIterator() {
        return new CompactHashMap<K, V>.Itr<Map.Entry<K, V>>() {
            /* access modifiers changed from: package-private */
            public Map.Entry<K, V> getOutput(int entry) {
                return new MapEntry(entry);
            }
        };
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean containsValue(@NullableDecl Object value) {
        for (int i = 0; i < this.size; i++) {
            if (Objects.equal(value, this.values[i])) {
                return true;
            }
        }
        return false;
    }

    public Collection<V> values() {
        Collection<V> collection = this.valuesView;
        if (collection != null) {
            return collection;
        }
        Collection<V> createValues = createValues();
        this.valuesView = createValues;
        return createValues;
    }

    /* access modifiers changed from: package-private */
    public Collection<V> createValues() {
        return new ValuesView();
    }

    /* access modifiers changed from: package-private */
    public Iterator<V> valuesIterator() {
        return new CompactHashMap<K, V>.Itr<V>() {
            /* access modifiers changed from: package-private */
            public V getOutput(int entry) {
                return CompactHashMap.this.values[entry];
            }
        };
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
            Arrays.fill(this.keys, 0, this.size, (Object) null);
            Arrays.fill(this.values, 0, this.size, (Object) null);
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
            stream.writeObject(this.keys[i]);
            stream.writeObject(this.values[i]);
            i = getSuccessor(i);
        }
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        int elementCount = stream.readInt();
        if (elementCount >= 0) {
            init(elementCount);
            for (int i = 0; i < elementCount; i++) {
                put(stream.readObject(), stream.readObject());
            }
            return;
        }
        StringBuilder sb = new StringBuilder(25);
        sb.append("Invalid size: ");
        sb.append(elementCount);
        throw new InvalidObjectException(sb.toString());
    }

    private abstract class Itr<T> implements Iterator<T> {
        int currentIndex;
        int expectedModCount;
        int indexToRemove;

        private Itr() {
            this.expectedModCount = CompactHashMap.this.modCount;
            this.currentIndex = CompactHashMap.this.firstEntryIndex();
            this.indexToRemove = -1;
        }

        /* access modifiers changed from: package-private */
        public abstract T getOutput(int i);

        public boolean hasNext() {
            return this.currentIndex >= 0;
        }

        public T next() {
            checkForConcurrentModification();
            if (hasNext()) {
                int i = this.currentIndex;
                this.indexToRemove = i;
                T result = getOutput(i);
                this.currentIndex = CompactHashMap.this.getSuccessor(this.currentIndex);
                return result;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            checkForConcurrentModification();
            CollectPreconditions.checkRemove(this.indexToRemove >= 0);
            this.expectedModCount++;
            Object unused = CompactHashMap.this.removeEntry(this.indexToRemove);
            this.currentIndex = CompactHashMap.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
            this.indexToRemove = -1;
        }

        private void checkForConcurrentModification() {
            if (CompactHashMap.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    class KeySetView extends AbstractSet<K> {
        KeySetView() {
        }

        public int size() {
            return CompactHashMap.this.size;
        }

        public boolean contains(Object o) {
            return CompactHashMap.this.containsKey(o);
        }

        public boolean remove(@NullableDecl Object o) {
            int index = CompactHashMap.this.indexOf(o);
            if (index == -1) {
                return false;
            }
            Object unused = CompactHashMap.this.removeEntry(index);
            return true;
        }

        public Iterator<K> iterator() {
            return CompactHashMap.this.keySetIterator();
        }

        public void clear() {
            CompactHashMap.this.clear();
        }
    }

    class EntrySetView extends AbstractSet<Map.Entry<K, V>> {
        EntrySetView() {
        }

        public int size() {
            return CompactHashMap.this.size;
        }

        public void clear() {
            CompactHashMap.this.clear();
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return CompactHashMap.this.entrySetIterator();
        }

        public boolean contains(@NullableDecl Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            int index = CompactHashMap.this.indexOf(entry.getKey());
            if (index == -1 || !Objects.equal(CompactHashMap.this.values[index], entry.getValue())) {
                return false;
            }
            return true;
        }

        public boolean remove(@NullableDecl Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            int index = CompactHashMap.this.indexOf(entry.getKey());
            if (index == -1 || !Objects.equal(CompactHashMap.this.values[index], entry.getValue())) {
                return false;
            }
            Object unused = CompactHashMap.this.removeEntry(index);
            return true;
        }
    }

    final class MapEntry extends AbstractMapEntry<K, V> {
        @NullableDecl
        private final K key;
        private int lastKnownIndex;

        MapEntry(int index) {
            this.key = CompactHashMap.this.keys[index];
            this.lastKnownIndex = index;
        }

        public K getKey() {
            return this.key;
        }

        private void updateLastKnownIndex() {
            int i = this.lastKnownIndex;
            if (i == -1 || i >= CompactHashMap.this.size() || !Objects.equal(this.key, CompactHashMap.this.keys[this.lastKnownIndex])) {
                this.lastKnownIndex = CompactHashMap.this.indexOf(this.key);
            }
        }

        public V getValue() {
            updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                return null;
            }
            return CompactHashMap.this.values[this.lastKnownIndex];
        }

        public V setValue(V value) {
            updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                CompactHashMap.this.put(this.key, value);
                return null;
            }
            V old = CompactHashMap.this.values[this.lastKnownIndex];
            CompactHashMap.this.values[this.lastKnownIndex] = value;
            return old;
        }
    }

    class ValuesView extends AbstractCollection<V> {
        ValuesView() {
        }

        public int size() {
            return CompactHashMap.this.size;
        }

        public void clear() {
            CompactHashMap.this.clear();
        }

        public Iterator<V> iterator() {
            return CompactHashMap.this.valuesIterator();
        }
    }
}
