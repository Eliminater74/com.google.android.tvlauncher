package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;

@GwtCompatible(emulated = true)
abstract class IndexedImmutableSet<E> extends ImmutableSet<E> {
    /* access modifiers changed from: package-private */
    public abstract E get(int i);

    IndexedImmutableSet() {
    }

    public UnmodifiableIterator<E> iterator() {
        return asList().iterator();
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public int copyIntoArray(Object[] dst, int offset) {
        return asList().copyIntoArray(dst, offset);
    }

    /* access modifiers changed from: package-private */
    public ImmutableList<E> createAsList() {
        return new ImmutableList<E>() {
            public E get(int index) {
                return IndexedImmutableSet.this.get(index);
            }

            /* access modifiers changed from: package-private */
            public boolean isPartialView() {
                return IndexedImmutableSet.this.isPartialView();
            }

            public int size() {
                return IndexedImmutableSet.this.size();
            }
        };
    }
}
