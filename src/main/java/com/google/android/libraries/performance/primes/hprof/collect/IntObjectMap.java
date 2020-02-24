package com.google.android.libraries.performance.primes.hprof.collect;

import com.google.android.libraries.stitch.util.Preconditions;

public final class IntObjectMap<E> {
    /* access modifiers changed from: private */
    public static final Object DELETED = new Object();
    private int[] keys;
    private int size;
    private int sizeIndex;
    private Object[] values;

    public interface Enumerable<E> {
        int getKey();

        E getValue();

        boolean next();
    }

    public IntObjectMap() {
        init();
    }

    public void clear() {
        init();
    }

    private void init() {
        this.sizeIndex = 0;
        this.keys = new int[IntIntMap.TABLE_SIZES[this.sizeIndex]];
        this.values = new Object[IntIntMap.TABLE_SIZES[this.sizeIndex]];
    }

    public E putIfAbsent(int key, E value) {
        Preconditions.checkNotNull(value);
        int index = findKeyIndex(key);
        E[] eArr = this.values;
        if (eArr[index] != null && eArr[index] != DELETED) {
            return eArr[index];
        }
        int[] iArr = this.keys;
        iArr[index] = key;
        this.size++;
        this.values[index] = value;
        if (this.size <= iArr.length / 2) {
            return null;
        }
        resize();
        return null;
    }

    public E remove(int key) {
        E e;
        int index = findKeyIndex(key);
        E[] eArr = this.values;
        if (eArr[index] == null || eArr[index] == (e = DELETED)) {
            return null;
        }
        E itemDeleted = eArr[index];
        eArr[index] = e;
        this.size--;
        return itemDeleted;
    }

    private void resize() {
        boolean z = true;
        if (this.sizeIndex < IntIntMap.TABLE_SIZES.length - 1) {
            int[] oldKeys = this.keys;
            Object[] oldValues = this.values;
            this.sizeIndex++;
            this.keys = new int[IntIntMap.TABLE_SIZES[this.sizeIndex]];
            this.values = new Object[IntIntMap.TABLE_SIZES[this.sizeIndex]];
            int oldSize = this.size;
            int oldCap = oldKeys.length;
            this.size = 0;
            for (int i = 0; i < oldCap; i++) {
                if (!(oldValues[i] == null || oldValues[i] == DELETED)) {
                    putIfAbsent(oldKeys[i], oldValues[i]);
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

    public E get(int key) {
        Object result = this.values[findKeyIndex(key)];
        if (result == DELETED) {
            return null;
        }
        return result;
    }

    private int findKeyIndex(int key) {
        int tableSize = this.keys.length;
        int index = (IntIntMap.hash(key) & Integer.MAX_VALUE) % tableSize;
        boolean foundFirstDeletedIndex = false;
        int firstDeleteIndex = 0;
        while (true) {
            Object[] objArr = this.values;
            if (objArr[index] == DELETED) {
                if (!foundFirstDeletedIndex) {
                    foundFirstDeletedIndex = true;
                    firstDeleteIndex = index;
                }
            } else if (objArr[index] == null) {
                return foundFirstDeletedIndex ? firstDeleteIndex : index;
            } else {
                if (this.keys[index] == key) {
                    return index;
                }
            }
            index++;
            if (index >= tableSize) {
                index = 0;
            }
        }
    }

    public int size() {
        return this.size;
    }

    public boolean containsKey(int key) {
        Object value = get(key);
        return (value == null || value == DELETED) ? false : true;
    }

    public Enumerator<E> enumerator() {
        return new Enumerator<>(this.keys, this.values);
    }

    public static class Enumerator<E> implements Enumerable<E> {
        private int key;
        private final int[] keys;
        private int nextIndex;
        private Object value;
        private final Object[] values;

        private Enumerator(int[] keys2, Object[] values2) {
            this.keys = keys2;
            this.values = values2;
        }

        public boolean next() {
            this.value = null;
            while (true) {
                Object obj = this.value;
                if (obj != null && obj != IntObjectMap.DELETED) {
                    break;
                }
                int i = this.nextIndex;
                Object[] objArr = this.values;
                if (i >= objArr.length) {
                    break;
                }
                this.nextIndex = i + 1;
                this.value = objArr[i];
            }
            int i2 = this.nextIndex;
            if (i2 > 0) {
                this.key = this.keys[i2 - 1];
            }
            Object obj2 = this.value;
            if (obj2 == null || obj2 == IntObjectMap.DELETED) {
                return false;
            }
            return true;
        }

        public int getKey() {
            return this.key;
        }

        public E getValue() {
            return this.value;
        }
    }
}
