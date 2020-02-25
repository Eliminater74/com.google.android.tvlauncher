package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

@GwtCompatible
abstract class AbstractMultiset<E> extends AbstractCollection<E> implements Multiset<E> {
    @MonotonicNonNullDecl
    private transient Set<E> elementSet;
    @MonotonicNonNullDecl
    private transient Set<Multiset.Entry<E>> entrySet;

    AbstractMultiset() {
    }

    public abstract void clear();

    /* access modifiers changed from: package-private */
    public abstract int distinctElements();

    /* access modifiers changed from: package-private */
    public abstract Iterator<E> elementIterator();

    /* access modifiers changed from: package-private */
    public abstract Iterator<Multiset.Entry<E>> entryIterator();

    public boolean isEmpty() {
        return entrySet().isEmpty();
    }

    public boolean contains(@NullableDecl Object element) {
        return count(element) > 0;
    }

    @CanIgnoreReturnValue
    public final boolean add(@NullableDecl E element) {
        add(element, 1);
        return true;
    }

    @CanIgnoreReturnValue
    public int add(@NullableDecl E e, int occurrences) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public final boolean remove(@NullableDecl Object element) {
        return remove(element, 1) > 0;
    }

    @CanIgnoreReturnValue
    public int remove(@NullableDecl Object element, int occurrences) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    public int setCount(@NullableDecl E element, int count) {
        return Multisets.setCountImpl(this, element, count);
    }

    @CanIgnoreReturnValue
    public boolean setCount(@NullableDecl E element, int oldCount, int newCount) {
        return Multisets.setCountImpl(this, element, oldCount, newCount);
    }

    @CanIgnoreReturnValue
    public final boolean addAll(Collection<? extends E> elementsToAdd) {
        return Multisets.addAllImpl(this, elementsToAdd);
    }

    @CanIgnoreReturnValue
    public final boolean removeAll(Collection<?> elementsToRemove) {
        return Multisets.removeAllImpl(this, elementsToRemove);
    }

    @CanIgnoreReturnValue
    public final boolean retainAll(Collection<?> elementsToRetain) {
        return Multisets.retainAllImpl(this, elementsToRetain);
    }

    public Set<E> elementSet() {
        Set<E> result = this.elementSet;
        if (result != null) {
            return result;
        }
        Set<E> createElementSet = createElementSet();
        Set<E> result2 = createElementSet;
        this.elementSet = createElementSet;
        return result2;
    }

    /* access modifiers changed from: package-private */
    public Set<E> createElementSet() {
        return new ElementSet();
    }

    public Set<Multiset.Entry<E>> entrySet() {
        Set<Multiset.Entry<E>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        Set<Multiset.Entry<E>> createEntrySet = createEntrySet();
        Set<Multiset.Entry<E>> result2 = createEntrySet;
        this.entrySet = createEntrySet;
        return result2;
    }

    /* access modifiers changed from: package-private */
    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    public final boolean equals(@NullableDecl Object object) {
        return Multisets.equalsImpl(this, object);
    }

    public final int hashCode() {
        return entrySet().hashCode();
    }

    public final String toString() {
        return entrySet().toString();
    }

    class ElementSet extends Multisets.ElementSet<E> {
        ElementSet() {
        }

        /* access modifiers changed from: package-private */
        public Multiset<E> multiset() {
            return AbstractMultiset.this;
        }

        public Iterator<E> iterator() {
            return AbstractMultiset.this.elementIterator();
        }
    }

    class EntrySet extends Multisets.EntrySet<E> {
        EntrySet() {
        }

        /* access modifiers changed from: package-private */
        public Multiset<E> multiset() {
            return AbstractMultiset.this;
        }

        public Iterator<Multiset.Entry<E>> iterator() {
            return AbstractMultiset.this.entryIterator();
        }

        public int size() {
            return AbstractMultiset.this.distinctElements();
        }
    }
}
