package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
final class DescendingImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    private final ImmutableSortedSet<E> forward;

    DescendingImmutableSortedSet(ImmutableSortedSet<E> forward2) {
        super(Ordering.from(forward2.comparator()).reverse());
        this.forward = forward2;
    }

    public boolean contains(@NullableDecl Object object) {
        return this.forward.contains(object);
    }

    public int size() {
        return this.forward.size();
    }

    public UnmodifiableIterator<E> iterator() {
        return this.forward.descendingIterator();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, boolean]
     candidates:
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return this.forward.tailSet((Object) toElement, inclusive).descendingSet();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, boolean, E, boolean]
     candidates:
      com.google.common.collect.ImmutableSortedSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return this.forward.subSet((Object) toElement, toInclusive, (Object) fromElement, fromInclusive).descendingSet();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, boolean]
     candidates:
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.headSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return this.forward.headSet((Object) fromElement, inclusive).descendingSet();
    }

    @GwtIncompatible("NavigableSet")
    public ImmutableSortedSet<E> descendingSet() {
        return this.forward;
    }

    @GwtIncompatible("NavigableSet")
    public UnmodifiableIterator<E> descendingIterator() {
        return this.forward.iterator();
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible("NavigableSet")
    public ImmutableSortedSet<E> createDescendingSet() {
        throw new AssertionError("should never be called");
    }

    public E lower(E element) {
        return this.forward.higher(element);
    }

    public E floor(E element) {
        return this.forward.ceiling(element);
    }

    public E ceiling(E element) {
        return this.forward.floor(element);
    }

    public E higher(E element) {
        return this.forward.lower(element);
    }

    /* access modifiers changed from: package-private */
    public int indexOf(@NullableDecl Object target) {
        int index = this.forward.indexOf(target);
        if (index == -1) {
            return index;
        }
        return (size() - 1) - index;
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return this.forward.isPartialView();
    }
}
