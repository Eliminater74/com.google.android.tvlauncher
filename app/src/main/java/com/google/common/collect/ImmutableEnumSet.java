package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.concurrent.LazyInit;

import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;

@GwtCompatible(emulated = true, serializable = true)
final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E> {
    private final transient EnumSet<E> delegate;
    @LazyInit
    private transient int hashCode;

    private ImmutableEnumSet(EnumSet<E> delegate2) {
        this.delegate = delegate2;
    }

    static ImmutableSet asImmutable(EnumSet set) {
        int size = set.size();
        if (size == 0) {
            return ImmutableSet.m149of();
        }
        if (size != 1) {
            return new ImmutableEnumSet(set);
        }
        return ImmutableSet.m150of(Iterables.getOnlyElement(set));
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.unmodifiableIterator(this.delegate.iterator());
    }

    public int size() {
        return this.delegate.size();
    }

    public boolean contains(Object object) {
        return this.delegate.contains(object);
    }

    public boolean containsAll(Collection<?> collection) {
        if (collection instanceof ImmutableEnumSet) {
            collection = ((ImmutableEnumSet) collection).delegate;
        }
        return this.delegate.containsAll(collection);
    }

    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof ImmutableEnumSet) {
            object = ((ImmutableEnumSet) object).delegate;
        }
        return this.delegate.equals(object);
    }

    /* access modifiers changed from: package-private */
    public boolean isHashCodeFast() {
        return true;
    }

    public int hashCode() {
        int result = this.hashCode;
        if (result != 0) {
            return result;
        }
        int hashCode2 = this.delegate.hashCode();
        this.hashCode = hashCode2;
        return hashCode2;
    }

    public String toString() {
        return this.delegate.toString();
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new EnumSerializedForm(this.delegate);
    }

    private static class EnumSerializedForm<E extends Enum<E>> implements Serializable {
        private static final long serialVersionUID = 0;
        final EnumSet<E> delegate;

        EnumSerializedForm(EnumSet<E> delegate2) {
            this.delegate = delegate2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return new ImmutableEnumSet(this.delegate.clone());
        }
    }
}
