package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Multiset;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
class RegularImmutableMultiset<E> extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset<>(ObjectCountHashMap.create());
    final transient ObjectCountHashMap<E> contents;
    @LazyInit
    private transient ImmutableSet<E> elementSet;
    private final transient int size;

    RegularImmutableMultiset(ObjectCountHashMap<E> contents2) {
        this.contents = contents2;
        long size2 = 0;
        for (int i = 0; i < contents2.size(); i++) {
            size2 += (long) contents2.getValue(i);
        }
        this.size = Ints.saturatedCast(size2);
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    public int count(@NullableDecl Object element) {
        return this.contents.get(element);
    }

    public int size() {
        return this.size;
    }

    public ImmutableSet<E> elementSet() {
        ImmutableSet<E> result = this.elementSet;
        if (result != null) {
            return result;
        }
        ElementSet elementSet2 = new ElementSet();
        this.elementSet = elementSet2;
        return elementSet2;
    }

    private final class ElementSet extends IndexedImmutableSet<E> {
        private ElementSet() {
        }

        /* access modifiers changed from: package-private */
        public E get(int index) {
            return RegularImmutableMultiset.this.contents.getKey(index);
        }

        public boolean contains(@NullableDecl Object object) {
            return RegularImmutableMultiset.this.contains(object);
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }

        public int size() {
            return RegularImmutableMultiset.this.contents.size();
        }
    }

    /* access modifiers changed from: package-private */
    public Multiset.Entry<E> getEntry(int index) {
        return this.contents.getEntry(index);
    }

    @GwtIncompatible
    private static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final int[] counts;
        final Object[] elements;

        SerializedForm(Multiset<?> multiset) {
            int distinct = multiset.entrySet().size();
            this.elements = new Object[distinct];
            this.counts = new int[distinct];
            int i = 0;
            for (Multiset.Entry<?> entry : multiset.entrySet()) {
                this.elements[i] = entry.getElement();
                this.counts[i] = entry.getCount();
                i++;
            }
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            ImmutableMultiset.Builder<Object> builder = new ImmutableMultiset.Builder<>(this.elements.length);
            int i = 0;
            while (true) {
                Object[] objArr = this.elements;
                if (i >= objArr.length) {
                    return builder.build();
                }
                builder.addCopies(objArr[i], this.counts[i]);
                i++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
