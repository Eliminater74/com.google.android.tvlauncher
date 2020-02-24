package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMultiset<E> extends ImmutableMultisetGwtSerializationDependencies<E> implements Multiset<E> {
    @LazyInit
    private transient ImmutableList<E> asList;
    @LazyInit
    private transient ImmutableSet<Multiset.Entry<E>> entrySet;

    public abstract ImmutableSet<E> elementSet();

    /* access modifiers changed from: package-private */
    public abstract Multiset.Entry<E> getEntry(int i);

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public abstract Object writeReplace();

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m138of() {
        return RegularImmutableMultiset.EMPTY;
    }

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m139of(E element) {
        return copyFromElements(element);
    }

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m140of(E e1, E e2) {
        return copyFromElements(e1, e2);
    }

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m141of(E e1, E e2, E e3) {
        return copyFromElements(e1, e2, e3);
    }

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m142of(E e1, E e2, E e3, E e4) {
        return copyFromElements(e1, e2, e3, e4);
    }

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m143of(E e1, E e2, E e3, E e4, E e5) {
        return copyFromElements(e1, e2, e3, e4, e5);
    }

    /* renamed from: of */
    public static <E> ImmutableMultiset<E> m144of(E e1, E e2, E e3, E e4, E e5, E e6, E... others) {
        return new Builder().add((Object) e1).add((Object) e2).add((Object) e3).add((Object) e4).add((Object) e5).add((Object) e6).add((Object[]) others).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Object[] objArr) {
        return copyFromElements(objArr);
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterable iterable) {
        if (iterable instanceof ImmutableMultiset) {
            ImmutableMultiset<E> result = (ImmutableMultiset) iterable;
            if (!result.isPartialView()) {
                return result;
            }
        }
        Builder<E> builder = new Builder<>(Multisets.inferDistinctElements(iterable));
        builder.addAll(iterable);
        return builder.build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator it) {
        return new Builder().addAll(it).build();
    }

    private static <E> ImmutableMultiset<E> copyFromElements(E... elements) {
        return new Builder().add((Object[]) elements).build();
    }

    static <E> ImmutableMultiset<E> copyFromEntries(Collection<? extends Multiset.Entry<? extends E>> entries) {
        Builder<E> builder = new Builder<>(entries.size());
        for (Multiset.Entry<? extends E> entry : entries) {
            builder.addCopies(entry.getElement(), entry.getCount());
        }
        return builder.build();
    }

    ImmutableMultiset() {
    }

    public UnmodifiableIterator<E> iterator() {
        final Iterator<Multiset.Entry<E>> entryIterator = entrySet().iterator();
        return new UnmodifiableIterator<E>(this) {
            @MonotonicNonNullDecl
            E element;
            int remaining;

            public boolean hasNext() {
                return this.remaining > 0 || entryIterator.hasNext();
            }

            public E next() {
                if (this.remaining <= 0) {
                    Multiset.Entry<E> entry = (Multiset.Entry) entryIterator.next();
                    this.element = entry.getElement();
                    this.remaining = entry.getCount();
                }
                this.remaining--;
                return this.element;
            }
        };
    }

    public ImmutableList<E> asList() {
        ImmutableList<E> result = this.asList;
        if (result != null) {
            return result;
        }
        ImmutableList<E> asList2 = super.asList();
        this.asList = asList2;
        return asList2;
    }

    public boolean contains(@NullableDecl Object object) {
        return count(object) > 0;
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final int add(E e, int occurrences) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final int remove(Object element, int occurrences) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final int setCount(E e, int count) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final boolean setCount(E e, int oldCount, int newCount) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public int copyIntoArray(Object[] dst, int offset) {
        UnmodifiableIterator it = entrySet().iterator();
        while (it.hasNext()) {
            Multiset.Entry<E> entry = (Multiset.Entry) it.next();
            Arrays.fill(dst, offset, entry.getCount() + offset, entry.getElement());
            offset += entry.getCount();
        }
        return offset;
    }

    public boolean equals(@NullableDecl Object object) {
        return Multisets.equalsImpl(this, object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    public String toString() {
        return entrySet().toString();
    }

    public ImmutableSet<Multiset.Entry<E>> entrySet() {
        ImmutableSet<Multiset.Entry<E>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        ImmutableSet<Multiset.Entry<E>> createEntrySet = createEntrySet();
        this.entrySet = createEntrySet;
        return createEntrySet;
    }

    private ImmutableSet<Multiset.Entry<E>> createEntrySet() {
        return isEmpty() ? ImmutableSet.m149of() : new EntrySet();
    }

    private final class EntrySet extends IndexedImmutableSet<Multiset.Entry<E>> {
        private static final long serialVersionUID = 0;

        private EntrySet() {
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return ImmutableMultiset.this.isPartialView();
        }

        /* access modifiers changed from: package-private */
        public Multiset.Entry<E> get(int index) {
            return ImmutableMultiset.this.getEntry(index);
        }

        public int size() {
            return ImmutableMultiset.this.elementSet().size();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Multiset.Entry)) {
                return false;
            }
            Multiset.Entry<?> entry = (Multiset.Entry) o;
            if (entry.getCount() > 0 && ImmutableMultiset.this.count(entry.getElement()) == entry.getCount()) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public Object writeReplace() {
            return new EntrySetSerializedForm(ImmutableMultiset.this);
        }
    }

    @GwtIncompatible
    static class EntrySetSerializedForm<E> implements Serializable {
        final ImmutableMultiset<E> multiset;

        EntrySetSerializedForm(ImmutableMultiset<E> multiset2) {
            this.multiset = multiset2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return this.multiset.entrySet();
        }
    }

    public static <E> Builder<E> builder() {
        return new Builder<>();
    }

    public static class Builder<E> extends ImmutableCollection.Builder<E> {
        boolean buildInvoked;
        ObjectCountHashMap<E> contents;
        boolean isLinkedHash;

        public Builder() {
            this(4);
        }

        Builder(int estimatedDistinct) {
            this.buildInvoked = false;
            this.isLinkedHash = false;
            this.contents = ObjectCountHashMap.createWithExpectedSize(estimatedDistinct);
        }

        Builder(boolean forSubtype) {
            this.buildInvoked = false;
            this.isLinkedHash = false;
            this.contents = null;
        }

        @CanIgnoreReturnValue
        public Builder<E> add(Object obj) {
            return addCopies(obj, 1);
        }

        @CanIgnoreReturnValue
        public Builder<E> add(Object... objArr) {
            super.add(objArr);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addCopies(Object obj, int occurrences) {
            if (occurrences == 0) {
                return this;
            }
            if (this.buildInvoked) {
                this.contents = new ObjectCountHashMap<>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = false;
            Preconditions.checkNotNull(obj);
            ObjectCountHashMap<E> objectCountHashMap = this.contents;
            objectCountHashMap.put(obj, objectCountHashMap.get(obj) + occurrences);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> setCount(Object obj, int count) {
            if (count == 0 && !this.isLinkedHash) {
                this.contents = new ObjectCountLinkedHashMap(this.contents);
                this.isLinkedHash = true;
            } else if (this.buildInvoked) {
                this.contents = new ObjectCountHashMap<>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = false;
            Preconditions.checkNotNull(obj);
            if (count == 0) {
                this.contents.remove(obj);
            } else {
                this.contents.put(Preconditions.checkNotNull(obj), count);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterable iterable) {
            if (iterable instanceof Multiset) {
                Multiset<? extends E> multiset = Multisets.cast(iterable);
                ObjectCountHashMap<? extends E> backingMap = tryGetMap(multiset);
                if (backingMap != null) {
                    ObjectCountHashMap<E> objectCountHashMap = this.contents;
                    objectCountHashMap.ensureCapacity(Math.max(objectCountHashMap.size(), backingMap.size()));
                    for (int i = backingMap.firstIndex(); i >= 0; i = backingMap.nextIndex(i)) {
                        addCopies(backingMap.getKey(i), backingMap.getValue(i));
                    }
                } else {
                    Set<? extends Multiset.Entry<? extends E>> entries = multiset.entrySet();
                    ObjectCountHashMap<E> objectCountHashMap2 = this.contents;
                    objectCountHashMap2.ensureCapacity(Math.max(objectCountHashMap2.size(), entries.size()));
                    for (Multiset.Entry<? extends E> entry : multiset.entrySet()) {
                        addCopies(entry.getElement(), entry.getCount());
                    }
                }
            } else {
                super.addAll(iterable);
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<E> addAll(Iterator it) {
            super.addAll(it);
            return this;
        }

        @NullableDecl
        static <T> ObjectCountHashMap<T> tryGetMap(Iterable<T> multiset) {
            if (multiset instanceof RegularImmutableMultiset) {
                return ((RegularImmutableMultiset) multiset).contents;
            }
            if (multiset instanceof AbstractMapBasedMultiset) {
                return ((AbstractMapBasedMultiset) multiset).backingMap;
            }
            return null;
        }

        public ImmutableMultiset<E> build() {
            if (this.contents.size() == 0) {
                return ImmutableMultiset.m138of();
            }
            if (this.isLinkedHash) {
                this.contents = new ObjectCountHashMap<>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = true;
            return new RegularImmutableMultiset(this.contents);
        }
    }
}
