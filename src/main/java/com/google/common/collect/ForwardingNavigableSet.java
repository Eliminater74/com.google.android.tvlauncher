package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

@GwtIncompatible
public abstract class ForwardingNavigableSet<E> extends ForwardingSortedSet<E> implements NavigableSet<E> {
    /* access modifiers changed from: protected */
    public abstract NavigableSet<E> delegate();

    protected ForwardingNavigableSet() {
    }

    public E lower(E e) {
        return delegate().lower(e);
    }

    /* access modifiers changed from: protected */
    public E standardLower(E e) {
        return Iterators.getNext(headSet(e, false).descendingIterator(), null);
    }

    public E floor(E e) {
        return delegate().floor(e);
    }

    /* access modifiers changed from: protected */
    public E standardFloor(E e) {
        return Iterators.getNext(headSet(e, true).descendingIterator(), null);
    }

    public E ceiling(E e) {
        return delegate().ceiling(e);
    }

    /* access modifiers changed from: protected */
    public E standardCeiling(E e) {
        return Iterators.getNext(tailSet(e, true).iterator(), null);
    }

    public E higher(E e) {
        return delegate().higher(e);
    }

    /* access modifiers changed from: protected */
    public E standardHigher(E e) {
        return Iterators.getNext(tailSet(e, false).iterator(), null);
    }

    public E pollFirst() {
        return delegate().pollFirst();
    }

    /* access modifiers changed from: protected */
    public E standardPollFirst() {
        return Iterators.pollNext(iterator());
    }

    public E pollLast() {
        return delegate().pollLast();
    }

    /* access modifiers changed from: protected */
    public E standardPollLast() {
        return Iterators.pollNext(descendingIterator());
    }

    /* access modifiers changed from: protected */
    public E standardFirst() {
        return iterator().next();
    }

    /* access modifiers changed from: protected */
    public E standardLast() {
        return descendingIterator().next();
    }

    public NavigableSet<E> descendingSet() {
        return delegate().descendingSet();
    }

    @Beta
    protected class StandardDescendingSet extends Sets.DescendingSet<E> {
        public StandardDescendingSet(ForwardingNavigableSet this$0) {
            super(this$0);
        }
    }

    public Iterator<E> descendingIterator() {
        return delegate().descendingIterator();
    }

    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return delegate().subSet(fromElement, fromInclusive, toElement, toInclusive);
    }

    /* access modifiers changed from: protected */
    @Beta
    public NavigableSet<E> standardSubSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return tailSet(fromElement, fromInclusive).headSet(toElement, toInclusive);
    }

    /* access modifiers changed from: protected */
    public SortedSet<E> standardSubSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        return delegate().headSet(toElement, inclusive);
    }

    /* access modifiers changed from: protected */
    public SortedSet<E> standardHeadSet(E toElement) {
        return headSet(toElement, false);
    }

    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        return delegate().tailSet(fromElement, inclusive);
    }

    /* access modifiers changed from: protected */
    public SortedSet<E> standardTailSet(E fromElement) {
        return tailSet(fromElement, true);
    }
}
