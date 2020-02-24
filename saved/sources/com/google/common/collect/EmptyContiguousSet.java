package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
final class EmptyContiguousSet<C extends Comparable> extends ContiguousSet<C> {
    EmptyContiguousSet(DiscreteDomain<C> domain) {
        super(domain);
    }

    public C first() {
        throw new NoSuchElementException();
    }

    public C last() {
        throw new NoSuchElementException();
    }

    public int size() {
        return 0;
    }

    public ContiguousSet<C> intersection(ContiguousSet<C> contiguousSet) {
        return this;
    }

    public Range<C> range() {
        throw new NoSuchElementException();
    }

    public Range<C> range(BoundType lowerBoundType, BoundType upperBoundType) {
        throw new NoSuchElementException();
    }

    /* access modifiers changed from: package-private */
    public ContiguousSet<C> headSetImpl(C c, boolean inclusive) {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ContiguousSet<C> subSetImpl(C c, boolean fromInclusive, C c2, boolean toInclusive) {
        return this;
    }

    /* access modifiers changed from: package-private */
    public ContiguousSet<C> tailSetImpl(C c, boolean fromInclusive) {
        return this;
    }

    public boolean contains(Object object) {
        return false;
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public int indexOf(Object target) {
        return -1;
    }

    public UnmodifiableIterator<C> iterator() {
        return Iterators.emptyIterator();
    }

    @GwtIncompatible
    public UnmodifiableIterator<C> descendingIterator() {
        return Iterators.emptyIterator();
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    public boolean isEmpty() {
        return true;
    }

    public ImmutableList<C> asList() {
        return ImmutableList.m107of();
    }

    public String toString() {
        return "[]";
    }

    public boolean equals(@NullableDecl Object object) {
        if (object instanceof Set) {
            return ((Set) object).isEmpty();
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public boolean isHashCodeFast() {
        return true;
    }

    public int hashCode() {
        return 0;
    }

    @GwtIncompatible
    private static final class SerializedForm<C extends Comparable> implements Serializable {
        private static final long serialVersionUID = 0;
        private final DiscreteDomain<C> domain;

        private SerializedForm(DiscreteDomain<C> domain2) {
            this.domain = domain2;
        }

        private Object readResolve() {
            return new EmptyContiguousSet(this.domain);
        }
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public Object writeReplace() {
        return new SerializedForm(this.domain);
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public ImmutableSortedSet<C> createDescendingSet() {
        return ImmutableSortedSet.emptySet(Ordering.natural().reverse());
    }
}
