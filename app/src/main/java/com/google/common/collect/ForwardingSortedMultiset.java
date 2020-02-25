package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;

@GwtCompatible(emulated = true)
@Beta
public abstract class ForwardingSortedMultiset<E> extends ForwardingMultiset<E> implements SortedMultiset<E> {
    protected ForwardingSortedMultiset() {
    }

    /* access modifiers changed from: protected */
    public abstract SortedMultiset<E> delegate();

    public NavigableSet<E> elementSet() {
        return delegate().elementSet();
    }

    public Comparator<? super E> comparator() {
        return delegate().comparator();
    }

    public SortedMultiset<E> descendingMultiset() {
        return delegate().descendingMultiset();
    }

    public Multiset.Entry<E> firstEntry() {
        return delegate().firstEntry();
    }

    /* access modifiers changed from: protected */
    public Multiset.Entry<E> standardFirstEntry() {
        Iterator<Multiset.Entry<E>> entryIterator = entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = entryIterator.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }

    public Multiset.Entry<E> lastEntry() {
        return delegate().lastEntry();
    }

    /* access modifiers changed from: protected */
    public Multiset.Entry<E> standardLastEntry() {
        Iterator<Multiset.Entry<E>> entryIterator = descendingMultiset().entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = entryIterator.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }

    public Multiset.Entry<E> pollFirstEntry() {
        return delegate().pollFirstEntry();
    }

    /* access modifiers changed from: protected */
    public Multiset.Entry<E> standardPollFirstEntry() {
        Iterator<Multiset.Entry<E>> entryIterator = entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = entryIterator.next();
        Multiset.Entry<E> entry2 = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        entryIterator.remove();
        return entry2;
    }

    public Multiset.Entry<E> pollLastEntry() {
        return delegate().pollLastEntry();
    }

    /* access modifiers changed from: protected */
    public Multiset.Entry<E> standardPollLastEntry() {
        Iterator<Multiset.Entry<E>> entryIterator = descendingMultiset().entrySet().iterator();
        if (!entryIterator.hasNext()) {
            return null;
        }
        Multiset.Entry<E> entry = entryIterator.next();
        Multiset.Entry<E> entry2 = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        entryIterator.remove();
        return entry2;
    }

    public SortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        return delegate().headMultiset(upperBound, boundType);
    }

    public SortedMultiset<E> subMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
        return delegate().subMultiset(lowerBound, lowerBoundType, upperBound, upperBoundType);
    }

    /* access modifiers changed from: protected */
    public SortedMultiset<E> standardSubMultiset(E lowerBound, BoundType lowerBoundType, E upperBound, BoundType upperBoundType) {
        return tailMultiset(lowerBound, lowerBoundType).headMultiset(upperBound, upperBoundType);
    }

    public SortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        return delegate().tailMultiset(lowerBound, boundType);
    }

    protected class StandardElementSet extends SortedMultisets.NavigableElementSet<E> {
        public StandardElementSet(ForwardingSortedMultiset this$0) {
            super(this$0);
        }
    }

    protected abstract class StandardDescendingMultiset extends DescendingMultiset<E> {
        public StandardDescendingMultiset() {
        }

        /* access modifiers changed from: package-private */
        public SortedMultiset<E> forwardMultiset() {
            return ForwardingSortedMultiset.this;
        }
    }
}
