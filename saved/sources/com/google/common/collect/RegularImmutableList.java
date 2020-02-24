package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableList<E> extends ImmutableList<E> {
    static final ImmutableList<Object> EMPTY = new RegularImmutableList(new Object[0], 0);
    @VisibleForTesting
    final transient Object[] array;
    private final transient int size;

    RegularImmutableList(Object[] array2, int size2) {
        this.array = array2;
        this.size = size2;
    }

    public int size() {
        return this.size;
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public Object[] internalArray() {
        return this.array;
    }

    /* access modifiers changed from: package-private */
    public int internalArrayStart() {
        return 0;
    }

    /* access modifiers changed from: package-private */
    public int internalArrayEnd() {
        return this.size;
    }

    /* access modifiers changed from: package-private */
    public int copyIntoArray(Object[] dst, int dstOff) {
        System.arraycopy(this.array, 0, dst, dstOff, this.size);
        return this.size + dstOff;
    }

    public E get(int index) {
        Preconditions.checkElementIndex(index, this.size);
        return this.array[index];
    }
}
