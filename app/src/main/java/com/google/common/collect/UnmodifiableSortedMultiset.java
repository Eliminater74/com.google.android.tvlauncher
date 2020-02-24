package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Comparator;
import java.util.NavigableSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

@GwtCompatible(emulated = true)
final class UnmodifiableSortedMultiset<E> extends Multisets.UnmodifiableMultiset<E> implements SortedMultiset<E> {
    private static final long serialVersionUID = 0;
    @MonotonicNonNullDecl
    private transient UnmodifiableSortedMultiset<E> descendingMultiset;

    UnmodifiableSortedMultiset(SortedMultiset<E> delegate) {
        super(delegate);
    }

    /* access modifiers changed from: protected */
    public SortedMultiset<E> delegate() {
        return (SortedMultiset) super.delegate();
    }

    public Comparator<? super E> comparator() {
        return delegate().comparator();
    }

    /* access modifiers changed from: package-private */
    public NavigableSet<E> createElementSet() {
        return Sets.unmodifiableNavigableSet(delegate().elementSet());
    }

    public NavigableSet<E> elementSet() {
        return (NavigableSet) super.elementSet();
    }

    public SortedMultiset<E> descendingMultiset() {
        UnmodifiableSortedMultiset<E> result = this.descendingMultiset;
        if (result != null) {
            return result;
        }
        UnmodifiableSortedMultiset<E> result2 = new UnmodifiableSortedMultiset<>(delegate().descendingMultiset());
        result2.descendingMultiset = this;
        this.descendingMultiset = result2;
        return result2;
    }

    public Multiset.Entry<E> firstEntry() {
        return delegate().firstEntry();
    }

    public Multiset.Entry<E> lastEntry() {
        return delegate().lastEntry();
    }

    public Multiset.Entry<E> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    public Multiset.Entry<E> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().headMultiset(upperBound, boundType));
    }

    public SortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType));
    }

    public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(delegate().tailMultiset(lowerBound, boundType));
    }
}
