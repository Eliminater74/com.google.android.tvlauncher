package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Objects;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible
public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {
    /* access modifiers changed from: protected */
    public abstract Multiset<E> delegate();

    protected ForwardingMultiset() {
    }

    public int count(Object element) {
        return delegate().count(element);
    }

    @CanIgnoreReturnValue
    public int add(E element, int occurrences) {
        return delegate().add(element, occurrences);
    }

    @CanIgnoreReturnValue
    public int remove(Object element, int occurrences) {
        return delegate().remove(element, occurrences);
    }

    public Set<E> elementSet() {
        return delegate().elementSet();
    }

    public Set<Multiset.Entry<E>> entrySet() {
        return delegate().entrySet();
    }

    public boolean equals(@NullableDecl Object object) {
        return object == this || delegate().equals(object);
    }

    public int hashCode() {
        return delegate().hashCode();
    }

    @CanIgnoreReturnValue
    public int setCount(E element, int count) {
        return delegate().setCount(element, count);
    }

    @CanIgnoreReturnValue
    public boolean setCount(E element, int oldCount, int newCount) {
        return delegate().setCount(element, oldCount, newCount);
    }

    /* access modifiers changed from: protected */
    public boolean standardContains(@NullableDecl Object object) {
        return count(object) > 0;
    }

    /* access modifiers changed from: protected */
    public void standardClear() {
        Iterators.clear(entrySet().iterator());
    }

    /* access modifiers changed from: protected */
    @Beta
    public int standardCount(@NullableDecl Object object) {
        for (Multiset.Entry<?> entry : entrySet()) {
            if (Objects.equal(entry.getElement(), object)) {
                return entry.getCount();
            }
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public boolean standardAdd(E element) {
        add(element, 1);
        return true;
    }

    /* access modifiers changed from: protected */
    @Beta
    public boolean standardAddAll(Collection<? extends E> elementsToAdd) {
        return Multisets.addAllImpl(this, elementsToAdd);
    }

    /* access modifiers changed from: protected */
    public boolean standardRemove(Object element) {
        return remove(element, 1) > 0;
    }

    /* access modifiers changed from: protected */
    public boolean standardRemoveAll(Collection<?> elementsToRemove) {
        return Multisets.removeAllImpl(this, elementsToRemove);
    }

    /* access modifiers changed from: protected */
    public boolean standardRetainAll(Collection<?> elementsToRetain) {
        return Multisets.retainAllImpl(this, elementsToRetain);
    }

    /* access modifiers changed from: protected */
    public int standardSetCount(E element, int count) {
        return Multisets.setCountImpl(this, element, count);
    }

    /* access modifiers changed from: protected */
    public boolean standardSetCount(E element, int oldCount, int newCount) {
        return Multisets.setCountImpl(this, element, oldCount, newCount);
    }

    @Beta
    protected class StandardElementSet extends Multisets.ElementSet<E> {
        public StandardElementSet() {
        }

        /* access modifiers changed from: package-private */
        public Multiset<E> multiset() {
            return ForwardingMultiset.this;
        }

        public Iterator<E> iterator() {
            return Multisets.elementIterator(multiset().entrySet().iterator());
        }
    }

    /* access modifiers changed from: protected */
    public Iterator<E> standardIterator() {
        return Multisets.iteratorImpl(this);
    }

    /* access modifiers changed from: protected */
    public int standardSize() {
        return Multisets.linearTimeSizeImpl(this);
    }

    /* access modifiers changed from: protected */
    public boolean standardEquals(@NullableDecl Object object) {
        return Multisets.equalsImpl(this, object);
    }

    /* access modifiers changed from: protected */
    public int standardHashCode() {
        return entrySet().hashCode();
    }

    /* access modifiers changed from: protected */
    public String standardToString() {
        return entrySet().toString();
    }
}
