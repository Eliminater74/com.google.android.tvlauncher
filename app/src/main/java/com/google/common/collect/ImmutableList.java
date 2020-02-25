package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableList<E> extends ImmutableCollection<E> implements List<E>, RandomAccess {
    private static final UnmodifiableListIterator<Object> EMPTY_ITR = new Itr(RegularImmutableList.EMPTY, 0);

    ImmutableList() {
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m107of() {
        return RegularImmutableList.EMPTY;
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m108of(E element) {
        return construct(element);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m109of(E e1, E e2) {
        return construct(e1, e2);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m110of(E e1, E e2, E e3) {
        return construct(e1, e2, e3);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m111of(E e1, E e2, E e3, E e4) {
        return construct(e1, e2, e3, e4);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m112of(E e1, E e2, E e3, E e4, E e5) {
        return construct(e1, e2, e3, e4, e5);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m113of(E e1, E e2, E e3, E e4, E e5, E e6) {
        return construct(e1, e2, e3, e4, e5, e6);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m114of(E e1, E e2, E e3, E e4, E e5, E e6, E e7) {
        return construct(e1, e2, e3, e4, e5, e6, e7);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m115of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m116of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m117of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    /* renamed from: of */
    public static <E> ImmutableList<E> m118of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
        return construct(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11);
    }

    @SafeVarargs
    /* renamed from: of */
    public static <E> ImmutableList<E> m119of(E e1, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E... others) {
        E[] eArr = others;
        Preconditions.checkArgument(eArr.length <= 2147483635, "the total number of elements must fit in an int");
        Object[] array = new Object[(eArr.length + 12)];
        array[0] = e1;
        array[1] = e2;
        array[2] = e3;
        array[3] = e4;
        array[4] = e5;
        array[5] = e6;
        array[6] = e7;
        array[7] = e8;
        array[8] = e9;
        array[9] = e10;
        array[10] = e11;
        array[11] = e12;
        System.arraycopy(eArr, 0, array, 12, eArr.length);
        return construct(array);
    }

    public static <E> ImmutableList<E> copyOf(Iterable iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof Collection) {
            return copyOf((Collection) iterable);
        }
        return copyOf(iterable.iterator());
    }

    public static <E> ImmutableList<E> copyOf(Collection collection) {
        if (!(collection instanceof ImmutableCollection)) {
            return construct(collection.toArray());
        }
        ImmutableList<E> list = ((ImmutableCollection) collection).asList();
        return list.isPartialView() ? asImmutableList(list.toArray()) : list;
    }

    public static <E> ImmutableList<E> copyOf(Iterator it) {
        if (!it.hasNext()) {
            return m107of();
        }
        E first = it.next();
        if (!it.hasNext()) {
            return m108of(first);
        }
        return new Builder().add((Object) first).addAll(it).build();
    }

    public static <E> ImmutableList<E> copyOf(Object[] objArr) {
        if (objArr.length == 0) {
            return m107of();
        }
        return construct((Object[]) objArr.clone());
    }

    public static <E extends Comparable<? super E>> ImmutableList<E> sortedCopyOf(Iterable<? extends E> elements) {
        Comparable<?>[] array = (Comparable[]) Iterables.toArray(elements, new Comparable[0]);
        ObjectArrays.checkElementsNotNull((Object[]) array);
        Arrays.sort(array);
        return asImmutableList(array);
    }

    public static <E> ImmutableList<E> sortedCopyOf(Comparator<? super E> comparator, Iterable<? extends E> elements) {
        Preconditions.checkNotNull(comparator);
        E[] array = Iterables.toArray(elements);
        ObjectArrays.checkElementsNotNull(array);
        Arrays.sort(array, comparator);
        return asImmutableList(array);
    }

    private static <E> ImmutableList<E> construct(Object... elements) {
        return asImmutableList(ObjectArrays.checkElementsNotNull(elements));
    }

    static <E> ImmutableList<E> asImmutableList(Object[] elements) {
        return asImmutableList(elements, elements.length);
    }

    static <E> ImmutableList<E> asImmutableList(Object[] elements, int length) {
        if (length == 0) {
            return m107of();
        }
        return new RegularImmutableList(elements, length);
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    @Beta
    public static <E> Builder<E> builderWithExpectedSize(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        return new Builder<>(expectedSize);
    }

    public UnmodifiableIterator<E> iterator() {
        return listIterator();
    }

    public UnmodifiableListIterator<E> listIterator() {
        return listIterator(0);
    }

    public UnmodifiableListIterator<E> listIterator(int index) {
        Preconditions.checkPositionIndex(index, size());
        if (isEmpty()) {
            return EMPTY_ITR;
        }
        return new Itr(this, index);
    }

    public int indexOf(@NullableDecl Object object) {
        if (object == null) {
            return -1;
        }
        return Lists.indexOfImpl(this, object);
    }

    public int lastIndexOf(@NullableDecl Object object) {
        if (object == null) {
            return -1;
        }
        return Lists.lastIndexOfImpl(this, object);
    }

    public boolean contains(@NullableDecl Object object) {
        return indexOf(object) >= 0;
    }

    public ImmutableList<E> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        int length = toIndex - fromIndex;
        if (length == size()) {
            return this;
        }
        if (length == 0) {
            return m107of();
        }
        return subListUnchecked(fromIndex, toIndex);
    }

    /* access modifiers changed from: package-private */
    public ImmutableList<E> subListUnchecked(int fromIndex, int toIndex) {
        return new SubList(fromIndex, toIndex - fromIndex);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean addAll(int index, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final E set(int index, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void add(int index, E e) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final E remove(int index) {
        throw new UnsupportedOperationException();
    }

    public final ImmutableList<E> asList() {
        return this;
    }

    /* access modifiers changed from: package-private */
    public int copyIntoArray(Object[] dst, int offset) {
        int size = size();
        for (int i = 0; i < size; i++) {
            dst[offset + i] = get(i);
        }
        return offset + size;
    }

    public ImmutableList<E> reverse() {
        return size() <= 1 ? this : new ReverseImmutableList(this);
    }

    public boolean equals(@NullableDecl Object obj) {
        return Lists.equalsImpl(this, obj);
    }

    public int hashCode() {
        int hashCode = 1;
        int n = size();
        for (int i = 0; i < n; i++) {
            hashCode = (((hashCode * 31) + get(i).hashCode()) ^ -1) ^ -1;
        }
        return hashCode;
    }

    private void readObject(ObjectInputStream stream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(toArray());
    }

    static class Itr<E> extends AbstractIndexedListIterator<E> {
        private final ImmutableList<E> list;

        Itr(ImmutableList<E> list2, int index) {
            super(list2.size(), index);
            this.list = list2;
        }

        /* access modifiers changed from: protected */
        public E get(int index) {
            return this.list.get(index);
        }
    }

    private static class ReverseImmutableList<E> extends ImmutableList<E> {
        private final transient ImmutableList<E> forwardList;

        ReverseImmutableList(ImmutableList<E> backingList) {
            this.forwardList = backingList;
        }

        private int reverseIndex(int index) {
            return (size() - 1) - index;
        }

        private int reversePosition(int index) {
            return size() - index;
        }

        public ImmutableList<E> reverse() {
            return this.forwardList;
        }

        public boolean contains(@NullableDecl Object object) {
            return this.forwardList.contains(object);
        }

        public int indexOf(@NullableDecl Object object) {
            int index = this.forwardList.lastIndexOf(object);
            if (index >= 0) {
                return reverseIndex(index);
            }
            return -1;
        }

        public int lastIndexOf(@NullableDecl Object object) {
            int index = this.forwardList.indexOf(object);
            if (index >= 0) {
                return reverseIndex(index);
            }
            return -1;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E>
         arg types: [int, int]
         candidates:
          com.google.common.collect.ImmutableList.subList(int, int):java.util.List
          ClspMth{java.util.List.subList(int, int):java.util.List<E>}
          com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E> */
        public ImmutableList<E> subList(int fromIndex, int toIndex) {
            Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
            return this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)).reverse();
        }

        public E get(int index) {
            Preconditions.checkElementIndex(index, size());
            return this.forwardList.get(reverseIndex(index));
        }

        public int size() {
            return this.forwardList.size();
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return this.forwardList.isPartialView();
        }
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final Object[] elements;

        SerializedForm(Object[] elements2) {
            this.elements = elements2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }

    public static final class Builder<E> extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Builder() {
            this(4);
        }

        Builder(int capacity) {
            super(capacity);
        }

        @CanIgnoreReturnValue
        public Builder<E> add(Object obj) {
            super.add(obj);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(Object... objArr) {
            super.add(objArr);
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

        public ImmutableList<E> build() {
            this.forceCopy = true;
            return ImmutableList.asImmutableList(this.contents, this.size);
        }
    }

    class SubList extends ImmutableList<E> {
        final transient int length;
        final transient int offset;

        SubList(int offset2, int length2) {
            this.offset = offset2;
            this.length = length2;
        }

        public int size() {
            return this.length;
        }

        /* access modifiers changed from: package-private */
        public Object[] internalArray() {
            return ImmutableList.this.internalArray();
        }

        /* access modifiers changed from: package-private */
        public int internalArrayStart() {
            return ImmutableList.this.internalArrayStart() + this.offset;
        }

        /* access modifiers changed from: package-private */
        public int internalArrayEnd() {
            return ImmutableList.this.internalArrayStart() + this.offset + this.length;
        }

        public E get(int index) {
            Preconditions.checkElementIndex(index, this.length);
            return ImmutableList.this.get(this.offset + index);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E>
         arg types: [int, int]
         candidates:
          com.google.common.collect.ImmutableList.subList(int, int):java.util.List
          ClspMth{java.util.List.subList(int, int):java.util.List<E>}
          com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E> */
        public ImmutableList<E> subList(int fromIndex, int toIndex) {
            Preconditions.checkPositionIndexes(fromIndex, toIndex, this.length);
            ImmutableList immutableList = ImmutableList.this;
            int i = this.offset;
            return immutableList.subList(fromIndex + i, i + toIndex);
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }
    }
}
