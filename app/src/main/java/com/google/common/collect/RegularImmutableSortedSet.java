package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

@GwtCompatible(emulated = true, serializable = true)
final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E> {
    static final RegularImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new RegularImmutableSortedSet<>(ImmutableList.m107of(), Ordering.natural());
    @VisibleForTesting
    final transient ImmutableList<E> elements;

    RegularImmutableSortedSet(ImmutableList<E> elements2, Comparator<? super E> comparator) {
        super(comparator);
        this.elements = elements2;
    }

    /* access modifiers changed from: package-private */
    public Object[] internalArray() {
        return this.elements.internalArray();
    }

    /* access modifiers changed from: package-private */
    public int internalArrayStart() {
        return this.elements.internalArrayStart();
    }

    /* access modifiers changed from: package-private */
    public int internalArrayEnd() {
        return this.elements.internalArrayEnd();
    }

    public UnmodifiableIterator<E> iterator() {
        return this.elements.iterator();
    }

    @GwtIncompatible
    public UnmodifiableIterator<E> descendingIterator() {
        return this.elements.reverse().iterator();
    }

    public int size() {
        return this.elements.size();
    }

    public boolean contains(@NullableDecl Object o) {
        if (o == null) {
            return false;
        }
        try {
            return unsafeBinarySearch(o) >= 0;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean containsAll(Collection<?> targets) {
        if (targets instanceof Multiset) {
            targets = ((Multiset) targets).elementSet();
        }
        if (!SortedIterables.hasSameComparator(comparator(), targets) || targets.size() <= 1) {
            return super.containsAll(targets);
        }
        Iterator<E> thisIterator = iterator();
        Iterator<?> thatIterator = targets.iterator();
        if (!thisIterator.hasNext()) {
            return false;
        }
        Object target = thatIterator.next();
        E current = thisIterator.next();
        while (true) {
            try {
                int cmp = unsafeCompare(current, target);
                if (cmp < 0) {
                    if (!thisIterator.hasNext()) {
                        return false;
                    }
                    current = thisIterator.next();
                } else if (cmp == 0) {
                    if (!thatIterator.hasNext()) {
                        return true;
                    }
                    target = thatIterator.next();
                } else if (cmp > 0) {
                    return false;
                }
            } catch (ClassCastException | NullPointerException e) {
                return false;
            }
        }
    }

    private int unsafeBinarySearch(Object key) throws ClassCastException {
        return Collections.binarySearch(this.elements, key, unsafeComparator());
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return this.elements.isPartialView();
    }

    /* access modifiers changed from: package-private */
    public int copyIntoArray(Object[] dst, int offset) {
        return this.elements.copyIntoArray(dst, offset);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035 A[Catch:{ ClassCastException -> 0x004b, NoSuchElementException -> 0x0049 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r9) {
        /*
            r8 = this;
            r0 = 1
            if (r9 != r8) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = r9 instanceof java.util.Set
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            r1 = r9
            java.util.Set r1 = (java.util.Set) r1
            int r3 = r8.size()
            int r4 = r1.size()
            if (r3 == r4) goto L_0x0018
            return r2
        L_0x0018:
            boolean r3 = r8.isEmpty()
            if (r3 == 0) goto L_0x001f
            return r0
        L_0x001f:
            java.util.Comparator r3 = r8.comparator
            boolean r3 = com.google.common.collect.SortedIterables.hasSameComparator(r3, r1)
            if (r3 == 0) goto L_0x004d
            java.util.Iterator r3 = r1.iterator()
            com.google.common.collect.UnmodifiableIterator r4 = r8.iterator()     // Catch:{ ClassCastException -> 0x004b, NoSuchElementException -> 0x0049 }
        L_0x002f:
            boolean r5 = r4.hasNext()     // Catch:{ ClassCastException -> 0x004b, NoSuchElementException -> 0x0049 }
            if (r5 == 0) goto L_0x0048
            java.lang.Object r5 = r4.next()     // Catch:{ ClassCastException -> 0x004b, NoSuchElementException -> 0x0049 }
            java.lang.Object r6 = r3.next()     // Catch:{ ClassCastException -> 0x004b, NoSuchElementException -> 0x0049 }
            if (r6 == 0) goto L_0x0047
            int r7 = r8.unsafeCompare(r5, r6)     // Catch:{ ClassCastException -> 0x004b, NoSuchElementException -> 0x0049 }
            if (r7 == 0) goto L_0x0046
            goto L_0x0047
        L_0x0046:
            goto L_0x002f
        L_0x0047:
            return r2
        L_0x0048:
            return r0
        L_0x0049:
            r0 = move-exception
            return r2
        L_0x004b:
            r0 = move-exception
            return r2
        L_0x004d:
            boolean r0 = r8.containsAll(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.RegularImmutableSortedSet.equals(java.lang.Object):boolean");
    }

    public E first() {
        if (!isEmpty()) {
            return this.elements.get(0);
        }
        throw new NoSuchElementException();
    }

    public E last() {
        if (!isEmpty()) {
            return this.elements.get(size() - 1);
        }
        throw new NoSuchElementException();
    }

    public E lower(E element) {
        int index = headIndex(element, false) - 1;
        if (index == -1) {
            return null;
        }
        return this.elements.get(index);
    }

    public E floor(E element) {
        int index = headIndex(element, true) - 1;
        if (index == -1) {
            return null;
        }
        return this.elements.get(index);
    }

    public E ceiling(E element) {
        int index = tailIndex(element, true);
        if (index == size()) {
            return null;
        }
        return this.elements.get(index);
    }

    public E higher(E element) {
        int index = tailIndex(element, false);
        if (index == size()) {
            return null;
        }
        return this.elements.get(index);
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive) {
        return getSubSet(0, headIndex(toElement, inclusive));
    }

    /* access modifiers changed from: package-private */
    public int headIndex(E toElement, boolean inclusive) {
        int index = Collections.binarySearch(this.elements, Preconditions.checkNotNull(toElement), comparator());
        if (index >= 0) {
            return inclusive ? index + 1 : index;
        }
        return index ^ -1;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        return tailSetImpl(fromElement, fromInclusive).headSetImpl(toElement, toInclusive);
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive) {
        return getSubSet(tailIndex(fromElement, inclusive), size());
    }

    /* access modifiers changed from: package-private */
    public int tailIndex(E fromElement, boolean inclusive) {
        int index = Collections.binarySearch(this.elements, Preconditions.checkNotNull(fromElement), comparator());
        if (index >= 0) {
            return inclusive ? index : index + 1;
        }
        return index ^ -1;
    }

    /* access modifiers changed from: package-private */
    public Comparator<Object> unsafeComparator() {
        return this.comparator;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E>
     arg types: [int, int]
     candidates:
      com.google.common.collect.ImmutableList.subList(int, int):java.util.List
      ClspMth{java.util.List.subList(int, int):java.util.List<E>}
      com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E> */
    /* access modifiers changed from: package-private */
    public RegularImmutableSortedSet<E> getSubSet(int newFromIndex, int newToIndex) {
        if (newFromIndex == 0 && newToIndex == size()) {
            return this;
        }
        if (newFromIndex < newToIndex) {
            return new RegularImmutableSortedSet<>(this.elements.subList(newFromIndex, newToIndex), this.comparator);
        }
        return emptySet(this.comparator);
    }

    /* access modifiers changed from: package-private */
    public int indexOf(@NullableDecl Object target) {
        if (target == null) {
            return -1;
        }
        try {
            int position = Collections.binarySearch(this.elements, target, unsafeComparator());
            if (position >= 0) {
                return position;
            }
            return -1;
        } catch (ClassCastException e) {
            return -1;
        }
    }

    public ImmutableList<E> asList() {
        return this.elements;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSortedSet<E> createDescendingSet() {
        Comparator<? super E> reversedOrder = Collections.reverseOrder(this.comparator);
        if (isEmpty()) {
            return emptySet(reversedOrder);
        }
        return new RegularImmutableSortedSet(this.elements.reverse(), reversedOrder);
    }
}
