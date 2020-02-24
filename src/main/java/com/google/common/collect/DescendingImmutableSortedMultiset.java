package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.collect.Multiset;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
final class DescendingImmutableSortedMultiset<E> extends ImmutableSortedMultiset<E> {
    private final transient ImmutableSortedMultiset<E> forward;

    DescendingImmutableSortedMultiset(ImmutableSortedMultiset<E> forward2) {
        this.forward = forward2;
    }

    public int count(@NullableDecl Object element) {
        return this.forward.count(element);
    }

    public Multiset.Entry<E> firstEntry() {
        return this.forward.lastEntry();
    }

    public Multiset.Entry<E> lastEntry() {
        return this.forward.firstEntry();
    }

    public int size() {
        return this.forward.size();
    }

    public ImmutableSortedSet<E> elementSet() {
        return this.forward.elementSet().descendingSet();
    }

    /* access modifiers changed from: package-private */
    public Multiset.Entry<E> getEntry(int index) {
        return this.forward.entrySet().asList().reverse().get(index);
    }

    public ImmutableSortedMultiset<E> descendingMultiset() {
        return this.forward;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMultiset.tailMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.ImmutableSortedMultiset<E>
     arg types: [E, com.google.common.collect.BoundType]
     candidates:
      com.google.common.collect.ImmutableSortedMultiset.tailMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.SortedMultiset
      com.google.common.collect.SortedMultiset.tailMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.SortedMultiset<E>
      com.google.common.collect.ImmutableSortedMultiset.tailMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.ImmutableSortedMultiset<E> */
    public ImmutableSortedMultiset<E> headMultiset(E upperBound, BoundType boundType) {
        return this.forward.tailMultiset((Object) upperBound, boundType).descendingMultiset();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMultiset.headMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.ImmutableSortedMultiset<E>
     arg types: [E, com.google.common.collect.BoundType]
     candidates:
      com.google.common.collect.ImmutableSortedMultiset.headMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.SortedMultiset
      com.google.common.collect.SortedMultiset.headMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.SortedMultiset<E>
      com.google.common.collect.ImmutableSortedMultiset.headMultiset(java.lang.Object, com.google.common.collect.BoundType):com.google.common.collect.ImmutableSortedMultiset<E> */
    public ImmutableSortedMultiset<E> tailMultiset(E lowerBound, BoundType boundType) {
        return this.forward.headMultiset((Object) lowerBound, boundType).descendingMultiset();
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return this.forward.isPartialView();
    }
}
