package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.j2objc.annotations.RetainedWith;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtCompatible(emulated = true, serializable = true)
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
    static final Map.Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];
    @RetainedWith
    @LazyInit
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    @RetainedWith
    @LazyInit
    private transient ImmutableSet<K> keySet;
    @LazyInit
    private transient ImmutableSetMultimap<K, V> multimapView;
    @RetainedWith
    @LazyInit
    private transient ImmutableCollection<V> values;

    /* access modifiers changed from: package-private */
    public abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();

    /* access modifiers changed from: package-private */
    public abstract ImmutableSet<K> createKeySet();

    /* access modifiers changed from: package-private */
    public abstract ImmutableCollection<V> createValues();

    public abstract V get(@NullableDecl Object obj);

    /* access modifiers changed from: package-private */
    public abstract boolean isPartialView();

    /* renamed from: of */
    public static <K, V> ImmutableMap<K, V> m126of() {
        return RegularImmutableMap.EMPTY;
    }

    /* renamed from: of */
    public static <K, V> ImmutableMap<K, V> m127of(K k1, V v1) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        return RegularImmutableMap.create(1, new Object[]{k1, v1});
    }

    /* renamed from: of */
    public static <K, V> ImmutableMap<K, V> m128of(K k1, V v1, K k2, V v2) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        return RegularImmutableMap.create(2, new Object[]{k1, v1, k2, v2});
    }

    /* renamed from: of */
    public static <K, V> ImmutableMap<K, V> m129of(K k1, V v1, K k2, V v2, K k3, V v3) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        return RegularImmutableMap.create(3, new Object[]{k1, v1, k2, v2, k3, v3});
    }

    /* renamed from: of */
    public static <K, V> ImmutableMap<K, V> m130of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        return RegularImmutableMap.create(4, new Object[]{k1, v1, k2, v2, k3, v3, k4, v4});
    }

    /* renamed from: of */
    public static <K, V> ImmutableMap<K, V> m131of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        CollectPreconditions.checkEntryNotNull(k1, v1);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        CollectPreconditions.checkEntryNotNull(k5, v5);
        return RegularImmutableMap.create(5, new Object[]{k1, v1, k2, v2, k3, v3, k4, v4, k5, v5});
    }

    static <K, V> Map.Entry<K, V> entryOf(K key, V value) {
        CollectPreconditions.checkEntryNotNull(key, value);
        return new AbstractMap.SimpleImmutableEntry(key, value);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    @Beta
    public static <K, V> Builder<K, V> builderWithExpectedSize(int expectedSize) {
        CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
        return new Builder<>(expectedSize);
    }

    static void checkNoConflict(boolean safe, String conflictDescription, Map.Entry<?, ?> entry1, Map.Entry<?, ?> entry2) {
        if (!safe) {
            throw conflictException(conflictDescription, entry1, entry2);
        }
    }

    static IllegalArgumentException conflictException(String conflictDescription, Object entry1, Object entry2) {
        String valueOf = String.valueOf(entry1);
        String valueOf2 = String.valueOf(entry2);
        StringBuilder sb = new StringBuilder(String.valueOf(conflictDescription).length() + 34 + String.valueOf(valueOf).length() + String.valueOf(valueOf2).length());
        sb.append("Multiple entries with same ");
        sb.append(conflictDescription);
        sb.append(": ");
        sb.append(valueOf);
        sb.append(" and ");
        sb.append(valueOf2);
        return new IllegalArgumentException(sb.toString());
    }

    public static class Builder<K, V> {
        Object[] alternatingKeysAndValues;
        boolean entriesUsed;
        int size;
        @MonotonicNonNullDecl
        Comparator<? super V> valueComparator;

        public Builder() {
            this(4);
        }

        Builder(int initialCapacity) {
            this.alternatingKeysAndValues = new Object[(initialCapacity * 2)];
            this.size = 0;
            this.entriesUsed = false;
        }

        private void ensureCapacity(int minCapacity) {
            int i = minCapacity * 2;
            Object[] objArr = this.alternatingKeysAndValues;
            if (i > objArr.length) {
                this.alternatingKeysAndValues = Arrays.copyOf(objArr, ImmutableCollection.Builder.expandedCapacity(objArr.length, minCapacity * 2));
                this.entriesUsed = false;
            }
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K key, V value) {
            ensureCapacity(this.size + 1);
            CollectPreconditions.checkEntryNotNull(key, value);
            Object[] objArr = this.alternatingKeysAndValues;
            int i = this.size;
            objArr[i * 2] = key;
            objArr[(i * 2) + 1] = value;
            this.size = i + 1;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return putAll(map.entrySet());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            if (entries instanceof Collection) {
                ensureCapacity(this.size + ((Collection) entries).size());
            }
            for (Map.Entry<? extends K, ? extends V> entry : entries) {
                put(entry);
            }
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> valueComparator2) {
            Preconditions.checkState(this.valueComparator == null, "valueComparator was already set");
            this.valueComparator = (Comparator) Preconditions.checkNotNull(valueComparator2, "valueComparator");
            return this;
        }

        public ImmutableMap<K, V> build() {
            sortEntries();
            this.entriesUsed = true;
            return RegularImmutableMap.create(this.size, this.alternatingKeysAndValues);
        }

        /* access modifiers changed from: package-private */
        public void sortEntries() {
            int i;
            if (this.valueComparator != null) {
                if (this.entriesUsed) {
                    this.alternatingKeysAndValues = Arrays.copyOf(this.alternatingKeysAndValues, this.size * 2);
                }
                Map.Entry<K, V>[] entries = new Map.Entry[this.size];
                int i2 = 0;
                while (true) {
                    i = this.size;
                    if (i2 >= i) {
                        break;
                    }
                    Object[] objArr = this.alternatingKeysAndValues;
                    entries[i2] = new AbstractMap.SimpleImmutableEntry<>(objArr[i2 * 2], objArr[(i2 * 2) + 1]);
                    i2++;
                }
                Arrays.sort(entries, 0, i, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
                for (int i3 = 0; i3 < this.size; i3++) {
                    this.alternatingKeysAndValues[i3 * 2] = entries[i3].getKey();
                    this.alternatingKeysAndValues[(i3 * 2) + 1] = entries[i3].getValue();
                }
            }
        }
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map map) {
        if ((map instanceof ImmutableMap) && !(map instanceof SortedMap)) {
            ImmutableMap<K, V> kvMap = (ImmutableMap) map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        }
        return copyOf(map.entrySet());
    }

    @Beta
    public static <K, V> ImmutableMap<K, V> copyOf(Iterable iterable) {
        int initialCapacity;
        if (iterable instanceof Collection) {
            initialCapacity = ((Collection) iterable).size();
        } else {
            initialCapacity = 4;
        }
        Builder<K, V> builder = new Builder<>(initialCapacity);
        builder.putAll(iterable);
        return builder.build();
    }

    static abstract class IteratorBasedImmutableMap<K, V> extends ImmutableMap<K, V> {
        /* access modifiers changed from: package-private */
        public abstract UnmodifiableIterator<Map.Entry<K, V>> entryIterator();

        IteratorBasedImmutableMap() {
        }

        /* access modifiers changed from: package-private */
        public ImmutableSet<K> createKeySet() {
            return new ImmutableMapKeySet(this);
        }

        /* access modifiers changed from: package-private */
        public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
            return new ImmutableMapEntrySet<K, V>() {
                /* access modifiers changed from: package-private */
                public ImmutableMap<K, V> map() {
                    return IteratorBasedImmutableMap.this;
                }

                public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedImmutableMap.this.entryIterator();
                }
            };
        }

        /* access modifiers changed from: package-private */
        public ImmutableCollection<V> createValues() {
            return new ImmutableMapValues(this);
        }
    }

    ImmutableMap() {
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final V remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(@NullableDecl Object key) {
        return get(key) != null;
    }

    public boolean containsValue(@NullableDecl Object value) {
        return values().contains(value);
    }

    public final V getOrDefault(@NullableDecl Object key, @NullableDecl V defaultValue) {
        V result = get(key);
        return result != null ? result : defaultValue;
    }

    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> result = this.entrySet;
        if (result != null) {
            return result;
        }
        ImmutableSet<Map.Entry<K, V>> createEntrySet = createEntrySet();
        this.entrySet = createEntrySet;
        return createEntrySet;
    }

    public ImmutableSet<K> keySet() {
        ImmutableSet<K> result = this.keySet;
        if (result != null) {
            return result;
        }
        ImmutableSet<K> createKeySet = createKeySet();
        this.keySet = createKeySet;
        return createKeySet;
    }

    /* access modifiers changed from: package-private */
    public UnmodifiableIterator<K> keyIterator() {
        final UnmodifiableIterator<Map.Entry<K, V>> entryIterator = entrySet().iterator();
        return new UnmodifiableIterator<K>(this) {
            public boolean hasNext() {
                return entryIterator.hasNext();
            }

            public K next() {
                return ((Map.Entry) entryIterator.next()).getKey();
            }
        };
    }

    public ImmutableCollection<V> values() {
        ImmutableCollection<V> result = this.values;
        if (result != null) {
            return result;
        }
        ImmutableCollection<V> createValues = createValues();
        this.values = createValues;
        return createValues;
    }

    public ImmutableSetMultimap<K, V> asMultimap() {
        if (isEmpty()) {
            return ImmutableSetMultimap.m156of();
        }
        ImmutableSetMultimap<K, V> result = this.multimapView;
        if (result != null) {
            return result;
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap = new ImmutableSetMultimap<>(new MapViewOfValuesAsSingletonSets(), size(), null);
        this.multimapView = immutableSetMultimap;
        return immutableSetMultimap;
    }

    private final class MapViewOfValuesAsSingletonSets extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {
        private MapViewOfValuesAsSingletonSets() {
        }

        public int size() {
            return ImmutableMap.this.size();
        }

        /* access modifiers changed from: package-private */
        public ImmutableSet<K> createKeySet() {
            return ImmutableMap.this.keySet();
        }

        public boolean containsKey(@NullableDecl Object key) {
            return ImmutableMap.this.containsKey(key);
        }

        public ImmutableSet<V> get(@NullableDecl Object key) {
            V outerValue = ImmutableMap.this.get(key);
            if (outerValue == null) {
                return null;
            }
            return ImmutableSet.m150of(outerValue);
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return ImmutableMap.this.isPartialView();
        }

        public int hashCode() {
            return ImmutableMap.this.hashCode();
        }

        /* access modifiers changed from: package-private */
        public boolean isHashCodeFast() {
            return ImmutableMap.this.isHashCodeFast();
        }

        /* access modifiers changed from: package-private */
        public UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>> entryIterator() {
            final Iterator<Map.Entry<K, V>> backingIterator = ImmutableMap.this.entrySet().iterator();
            return new UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>>(this) {
                public boolean hasNext() {
                    return backingIterator.hasNext();
                }

                public Map.Entry<K, ImmutableSet<V>> next() {
                    final Map.Entry<K, V> backingEntry = (Map.Entry) backingIterator.next();
                    return new AbstractMapEntry<K, ImmutableSet<V>>(this) {
                        public K getKey() {
                            return backingEntry.getKey();
                        }

                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.m150of(backingEntry.getValue());
                        }
                    };
                }
            };
        }
    }

    public boolean equals(@NullableDecl Object object) {
        return Maps.equalsImpl(this, object);
    }

    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    /* access modifiers changed from: package-private */
    public boolean isHashCodeFast() {
        return false;
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final Object[] keys;
        private final Object[] values;

        SerializedForm(ImmutableMap<?, ?> map) {
            this.keys = new Object[map.size()];
            this.values = new Object[map.size()];
            int i = 0;
            UnmodifiableIterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<?, ?> entry = it.next();
                this.keys[i] = entry.getKey();
                this.values[i] = entry.getValue();
                i++;
            }
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return createMap(new Builder<>(this.keys.length));
        }

        /* access modifiers changed from: package-private */
        public Object createMap(Builder<Object, Object> builder) {
            int i = 0;
            while (true) {
                Object[] objArr = this.keys;
                if (i >= objArr.length) {
                    return builder.build();
                }
                builder.put(objArr[i], this.values[i]);
                i++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }
}
