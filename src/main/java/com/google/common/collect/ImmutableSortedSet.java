package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E> implements NavigableSet<E>, SortedIterable<E> {
    final transient Comparator<? super E> comparator;
    @GwtIncompatible
    @LazyInit
    transient ImmutableSortedSet<E> descendingSet;

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public abstract ImmutableSortedSet<E> createDescendingSet();

    @GwtIncompatible
    public abstract UnmodifiableIterator<E> descendingIterator();

    /* access modifiers changed from: package-private */
    public abstract ImmutableSortedSet<E> headSetImpl(Object obj, boolean z);

    /* access modifiers changed from: package-private */
    public abstract int indexOf(@NullableDecl Object obj);

    public abstract UnmodifiableIterator<E> iterator();

    /* access modifiers changed from: package-private */
    public abstract ImmutableSortedSet<E> subSetImpl(Object obj, boolean z, Object obj2, boolean z2);

    /* access modifiers changed from: package-private */
    public abstract ImmutableSortedSet<E> tailSetImpl(Object obj, boolean z);

    static <E> RegularImmutableSortedSet<E> emptySet(Comparator<? super E> comparator2) {
        if (Ordering.natural().equals(comparator2)) {
            return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
        }
        return new RegularImmutableSortedSet<>(ImmutableList.m107of(), comparator2);
    }

    /* renamed from: of */
    public static <E> ImmutableSortedSet<E> m187of() {
        return RegularImmutableSortedSet.NATURAL_EMPTY_SET;
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> m188of(E element) {
        return new RegularImmutableSortedSet(ImmutableList.m108of(element), Ordering.natural());
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> m189of(E e1, E e2) {
        return construct(Ordering.natural(), 2, e1, e2);
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> m190of(E e1, E e2, E e3) {
        return construct(Ordering.natural(), 3, e1, e2, e3);
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> m191of(E e1, E e2, E e3, E e4) {
        return construct(Ordering.natural(), 4, e1, e2, e3, e4);
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> m192of(E e1, E e2, E e3, E e4, E e5) {
        return construct(Ordering.natural(), 5, e1, e2, e3, e4, e5);
    }

    /* renamed from: of */
    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> m193of(E e1, E e2, E e3, E e4, E e5, E e6, E... remaining) {
        Comparable[] contents = new Comparable[(remaining.length + 6)];
        contents[0] = e1;
        contents[1] = e2;
        contents[2] = e3;
        contents[3] = e4;
        contents[4] = e5;
        contents[5] = e6;
        System.arraycopy(remaining, 0, contents, 6, remaining.length);
        return construct(Ordering.natural(), contents.length, contents);
    }

    public static <E extends Comparable<? super E>> ImmutableSortedSet<E> copyOf(E[] elements) {
        return construct(Ordering.natural(), elements.length, (Comparable[]) elements.clone());
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> elements) {
        return copyOf(Ordering.natural(), elements);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Collection):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [com.google.common.collect.Ordering<E>, java.util.Collection<? extends E>]
     candidates:
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.lang.Iterable):com.google.common.collect.ImmutableSortedSet<E>
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Iterator):com.google.common.collect.ImmutableSortedSet<E>
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Collection):com.google.common.collect.ImmutableSortedSet<E> */
    public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> elements) {
        return copyOf((Comparator) Ordering.natural(), (Collection) elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Iterator<? extends E> elements) {
        return copyOf(Ordering.natural(), elements);
    }

    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator2, Iterator<? extends E> elements) {
        return new Builder(comparator2).addAll((Iterator) elements).build();
    }

    /* JADX INFO: Multiple debug info for r1v0 java.lang.Object[]: [D('array' E[]), D('original' com.google.common.collect.ImmutableSortedSet<E>)] */
    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator2, Iterable<? extends E> elements) {
        Preconditions.checkNotNull(comparator2);
        if (SortedIterables.hasSameComparator(comparator2, elements) && (elements instanceof ImmutableSortedSet)) {
            ImmutableSortedSet<E> original = (ImmutableSortedSet) elements;
            if (!original.isPartialView()) {
                return original;
            }
        }
        E[] array = Iterables.toArray(elements);
        return construct(comparator2, array.length, array);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.lang.Iterable):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [java.util.Comparator<? super E>, java.util.Collection<? extends E>]
     candidates:
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Collection):com.google.common.collect.ImmutableSortedSet<E>
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.util.Iterator):com.google.common.collect.ImmutableSortedSet<E>
      com.google.common.collect.ImmutableSortedSet.copyOf(java.util.Comparator, java.lang.Iterable):com.google.common.collect.ImmutableSortedSet<E> */
    public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator2, Collection<? extends E> elements) {
        return copyOf((Comparator) comparator2, (Iterable) elements);
    }

    public static <E> ImmutableSortedSet<E> copyOfSorted(SortedSet<E> sortedSet) {
        Comparator<? super E> comparator2 = SortedIterables.comparator(sortedSet);
        ImmutableList<E> list = ImmutableList.copyOf((Collection) sortedSet);
        if (list.isEmpty()) {
            return emptySet(comparator2);
        }
        return new RegularImmutableSortedSet(list, comparator2);
    }

    static <E> ImmutableSortedSet<E> construct(Comparator<? super E> comparator2, int n, E... contents) {
        if (n == 0) {
            return emptySet(comparator2);
        }
        ObjectArrays.checkElementsNotNull(contents, n);
        Arrays.sort(contents, 0, n, comparator2);
        int uniques = 1;
        for (int i = 1; i < n; i++) {
            E cur = contents[i];
            if (comparator2.compare(cur, contents[uniques - 1]) != 0) {
                contents[uniques] = cur;
                uniques++;
            }
        }
        Arrays.fill(contents, uniques, n, (Object) null);
        if (uniques < contents.length / 2) {
            contents = Arrays.copyOf(contents, uniques);
        }
        return new RegularImmutableSortedSet(ImmutableList.asImmutableList(contents, uniques), comparator2);
    }

    public static <E> Builder<E> orderedBy(Comparator<E> comparator2) {
        return new Builder<>(comparator2);
    }

    public static <E extends Comparable<?>> Builder<E> reverseOrder() {
        return new Builder<>(Collections.reverseOrder());
    }

    public static <E extends Comparable<?>> Builder<E> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static final class Builder<E> extends ImmutableSet.Builder<E> {
        private final Comparator<? super E> comparator;

        public Builder(Comparator<? super E> comparator2) {
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator2);
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E element) {
            super.add((Object) element);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(E... elements) {
            super.add((Object[]) elements);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable<? extends E> elements) {
            super.addAll((Iterable) elements);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator<? extends E> elements) {
            super.addAll((Iterator) elements);
            return this;
        }

        public ImmutableSortedSet<E> build() {
            ImmutableSortedSet<E> result = ImmutableSortedSet.construct(this.comparator, this.size, this.contents);
            this.size = result.size();
            this.forceCopy = true;
            return result;
        }
    }

    /* access modifiers changed from: package-private */
    public int unsafeCompare(Object a, Object b) {
        return unsafeCompare(this.comparator, a, b);
    }

    static int unsafeCompare(Comparator<?> comparator2, Object a, Object b) {
        return comparator2.compare(a, b);
    }

    ImmutableSortedSet(Comparator<? super E> comparator2) {
        this.comparator = comparator2;
    }

    public Comparator<? super E> comparator() {
        return this.comparator;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [java.lang.Object, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.headSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    public ImmutableSortedSet<E> headSet(Object obj) {
        return headSet(obj, false);
    }

    @GwtIncompatible
    public ImmutableSortedSet<E> headSet(Object obj, boolean inclusive) {
        return headSetImpl(Preconditions.checkNotNull(obj), inclusive);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [java.lang.Object, int, java.lang.Object, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.subSet(java.lang.Object, boolean, java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    public ImmutableSortedSet<E> subSet(Object obj, Object obj2) {
        return subSet(obj, true, obj2, false);
    }

    @GwtIncompatible
    public ImmutableSortedSet<E> subSet(Object obj, boolean fromInclusive, Object obj2, boolean toInclusive) {
        Preconditions.checkNotNull(obj);
        Preconditions.checkNotNull(obj2);
        Preconditions.checkArgument(this.comparator.compare(obj, obj2) <= 0);
        return subSetImpl(obj, fromInclusive, obj2, toInclusive);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [java.lang.Object, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    public ImmutableSortedSet<E> tailSet(Object obj) {
        return tailSet(obj, true);
    }

    @GwtIncompatible
    public ImmutableSortedSet<E> tailSet(Object obj, boolean inclusive) {
        return tailSetImpl(Preconditions.checkNotNull(obj), inclusive);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.headSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    @GwtIncompatible
    public E lower(E e) {
        return Iterators.getNext(headSet((Object) e, false).descendingIterator(), null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.headSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.headSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    @GwtIncompatible
    public E floor(E e) {
        return Iterators.getNext(headSet((Object) e, true).descendingIterator(), null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    @GwtIncompatible
    public E ceiling(E e) {
        return Iterables.getFirst(tailSet((Object) e, true), null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E>
     arg types: [E, int]
     candidates:
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet
      ClspMth{java.util.NavigableSet.tailSet(java.lang.Object, boolean):java.util.NavigableSet<E>}
      com.google.common.collect.ImmutableSortedSet.tailSet(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedSet<E> */
    @GwtIncompatible
    public E higher(E e) {
        return Iterables.getFirst(tailSet((Object) e, false), null);
    }

    public E first() {
        return iterator().next();
    }

    public E last() {
        return descendingIterator().next();
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Deprecated
    public final E pollFirst() {
        throw new UnsupportedOperationException();
    }

    @GwtIncompatible
    @CanIgnoreReturnValue
    @Deprecated
    public final E pollLast() {
        throw new UnsupportedOperationException();
    }

    @GwtIncompatible
    public ImmutableSortedSet<E> descendingSet() {
        ImmutableSortedSet<E> result = this.descendingSet;
        if (result != null) {
            return result;
        }
        ImmutableSortedSet<E> createDescendingSet = createDescendingSet();
        this.descendingSet = createDescendingSet;
        ImmutableSortedSet<E> result2 = createDescendingSet;
        result2.descendingSet = this;
        return result2;
    }

    private static class SerializedForm<E> implements Serializable {
        private static final long serialVersionUID = 0;
        final Comparator<? super E> comparator;
        final Object[] elements;

        public SerializedForm(Comparator<? super E> comparator2, Object[] elements2) {
            this.comparator = comparator2;
            this.elements = elements2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return new Builder(this.comparator).add(this.elements).build();
        }
    }

    private void readObject(ObjectInputStream unused) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this.comparator, toArray());
    }
}
