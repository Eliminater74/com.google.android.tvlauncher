package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true)
public abstract class ImmutableCollection<E> extends AbstractCollection<E> implements Serializable {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    public abstract boolean contains(@NullableDecl Object obj);

    /* access modifiers changed from: package-private */
    public abstract boolean isPartialView();

    public abstract UnmodifiableIterator<E> iterator();

    ImmutableCollection() {
    }

    public final Object[] toArray() {
        return toArray(EMPTY_ARRAY);
    }

    @CanIgnoreReturnValue
    public final <T> T[] toArray(T[] other) {
        Preconditions.checkNotNull(other);
        int size = size();
        if (other.length < size) {
            Object[] internal = internalArray();
            if (internal != null) {
                return Platform.copy(internal, internalArrayStart(), internalArrayEnd(), other);
            }
            other = ObjectArrays.newArray(other, size);
        } else if (other.length > size) {
            other[size] = null;
        }
        copyIntoArray(other, 0);
        return other;
    }

    /* access modifiers changed from: package-private */
    public Object[] internalArray() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public int internalArrayStart() {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public int internalArrayEnd() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        return isEmpty() ? ImmutableList.m107of() : ImmutableList.asImmutableList(toArray());
    }

    /* access modifiers changed from: package-private */
    @CanIgnoreReturnValue
    public int copyIntoArray(Object[] dst, int offset) {
        UnmodifiableIterator it = iterator();
        while (it.hasNext()) {
            dst[offset] = it.next();
            offset++;
        }
        return offset;
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new ImmutableList.SerializedForm(toArray());
    }

    public static abstract class Builder<E> {
        static final int DEFAULT_INITIAL_CAPACITY = 4;

        @CanIgnoreReturnValue
        public abstract Builder<E> add(Object obj);

        public abstract ImmutableCollection<E> build();

        static int expandedCapacity(int oldCapacity, int minCapacity) {
            if (minCapacity >= 0) {
                int newCapacity = (oldCapacity >> 1) + oldCapacity + 1;
                if (newCapacity < minCapacity) {
                    newCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
                }
                if (newCapacity < 0) {
                    return Integer.MAX_VALUE;
                }
                return newCapacity;
            }
            throw new AssertionError("cannot store more than MAX_VALUE elements");
        }

        Builder() {
        }

        @CanIgnoreReturnValue
        public Builder<E> add(Object... objArr) {
            for (Object add : objArr) {
                add(add);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable iterable) {
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator it) {
            while (it.hasNext()) {
                add(it.next());
            }
            return this;
        }
    }

    static abstract class ArrayBasedBuilder<E> extends Builder<E> {
        Object[] contents;
        boolean forceCopy;
        int size = 0;

        ArrayBasedBuilder(int initialCapacity) {
            CollectPreconditions.checkNonnegative(initialCapacity, "initialCapacity");
            this.contents = new Object[initialCapacity];
        }

        private void getReadyToExpandTo(int minCapacity) {
            Object[] objArr = this.contents;
            if (objArr.length < minCapacity) {
                this.contents = Arrays.copyOf(objArr, expandedCapacity(objArr.length, minCapacity));
                this.forceCopy = false;
            } else if (this.forceCopy) {
                this.contents = (Object[]) objArr.clone();
                this.forceCopy = false;
            }
        }

        @CanIgnoreReturnValue
        public ArrayBasedBuilder<E> add(Object obj) {
            Preconditions.checkNotNull(obj);
            getReadyToExpandTo(this.size + 1);
            Object[] objArr = this.contents;
            int i = this.size;
            this.size = i + 1;
            objArr[i] = obj;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(Object... objArr) {
            ObjectArrays.checkElementsNotNull(objArr);
            getReadyToExpandTo(this.size + objArr.length);
            System.arraycopy(objArr, 0, this.contents, this.size, objArr.length);
            this.size += objArr.length;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable iterable) {
            if (iterable instanceof Collection) {
                Collection<?> collection = (Collection) iterable;
                getReadyToExpandTo(this.size + collection.size());
                if (collection instanceof ImmutableCollection) {
                    this.size = ((ImmutableCollection) collection).copyIntoArray(this.contents, this.size);
                    return this;
                }
            }
            super.addAll(iterable);
            return this;
        }
    }
}
