package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.Weak;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@GwtCompatible(emulated = true)
public abstract class ImmutableMultimap<K, V> extends BaseImmutableMultimap<K, V> implements Serializable {
    private static final long serialVersionUID = 0;
    final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
    final transient int size;

    ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> map2, int size2) {
        this.map = map2;
        this.size = size2;
    }

    /* renamed from: of */
    public static <K, V> ImmutableMultimap<K, V> m132of() {
        return ImmutableListMultimap.m120of();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V>
     arg types: [K, V]
     candidates:
      com.google.common.collect.ImmutableMultimap.of(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap<K, V>
      com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableMultimap<K, V> m133of(K k1, V v1) {
        return ImmutableListMultimap.m121of((Object) k1, (Object) v1);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V>
     arg types: [K, V, K, V]
     candidates:
      com.google.common.collect.ImmutableMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap<K, V>
      com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableMultimap<K, V> m134of(K k1, V v1, K k2, V v2) {
        return ImmutableListMultimap.m122of((Object) k1, (Object) v1, (Object) k2, (Object) v2);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V>
     arg types: [K, V, K, V, K, V]
     candidates:
      com.google.common.collect.ImmutableMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap<K, V>
      com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableMultimap<K, V> m135of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return ImmutableListMultimap.m123of((Object) k1, (Object) v1, (Object) k2, (Object) v2, (Object) k3, (Object) v3);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V>
     arg types: [K, V, K, V, K, V, K, V]
     candidates:
      com.google.common.collect.ImmutableMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap<K, V>
      com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableMultimap<K, V> m136of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableListMultimap.m124of((Object) k1, (Object) v1, (Object) k2, (Object) v2, (Object) k3, (Object) v3, (Object) k4, (Object) v4);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V>
     arg types: [K, V, K, V, K, V, K, V, K, V]
     candidates:
      com.google.common.collect.ImmutableMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableMultimap<K, V>
      com.google.common.collect.ImmutableListMultimap.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):com.google.common.collect.ImmutableListMultimap<K, V> */
    /* renamed from: of */
    public static <K, V> ImmutableMultimap<K, V> m137of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableListMultimap.m125of((Object) k1, (Object) v1, (Object) k2, (Object) v2, (Object) k3, (Object) v3, (Object) k4, (Object) v4, (Object) k5, (Object) v5);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (multimap instanceof ImmutableMultimap) {
            ImmutableMultimap<K, V> kvMultimap = (ImmutableMultimap) multimap;
            if (!kvMultimap.isPartialView()) {
                return kvMultimap;
            }
        }
        return ImmutableListMultimap.copyOf((Multimap) multimap);
    }

    @Beta
    public static <K, V> ImmutableMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return ImmutableListMultimap.copyOf((Iterable) entries);
    }

    public abstract ImmutableCollection<V> get(Object obj);

    public abstract ImmutableMultimap<V, K> inverse();

    public /* bridge */ /* synthetic */ boolean containsEntry(@NullableDecl Object obj, @NullableDecl Object obj2) {
        return super.containsEntry(obj, obj2);
    }

    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableCollection<V> removeAll(Object key) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public ImmutableCollection<V> replaceValues(Object obj, Iterable iterable) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public boolean put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public boolean remove(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return this.map.isPartialView();
    }

    public boolean containsKey(@NullableDecl Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(@NullableDecl Object value) {
        return value != null && super.containsValue(value);
    }

    public int size() {
        return this.size;
    }

    public ImmutableSet<K> keySet() {
        return this.map.keySet();
    }

    /* access modifiers changed from: package-private */
    public Set<K> createKeySet() {
        throw new AssertionError("unreachable");
    }

    public ImmutableMap<K, Collection<V>> asMap() {
        return this.map;
    }

    /* access modifiers changed from: package-private */
    public Map<K, Collection<V>> createAsMap() {
        throw new AssertionError("should never be called");
    }

    public ImmutableCollection<Map.Entry<K, V>> entries() {
        return (ImmutableCollection) super.entries();
    }

    /* access modifiers changed from: package-private */
    public ImmutableCollection<Map.Entry<K, V>> createEntries() {
        return new EntryCollection(this);
    }

    /* access modifiers changed from: package-private */
    public UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
        return new UnmodifiableIterator<Map.Entry<K, V>>() {
            final Iterator<? extends Map.Entry<K, ? extends ImmutableCollection<V>>> asMapItr = ImmutableMultimap.this.map.entrySet().iterator();
            K currentKey = null;
            Iterator<V> valueItr = Iterators.emptyIterator();

            public boolean hasNext() {
                return this.valueItr.hasNext() || this.asMapItr.hasNext();
            }

            public Map.Entry<K, V> next() {
                if (!this.valueItr.hasNext()) {
                    Map.Entry<K, ? extends ImmutableCollection<V>> entry = (Map.Entry) this.asMapItr.next();
                    this.currentKey = entry.getKey();
                    this.valueItr = ((ImmutableCollection) entry.getValue()).iterator();
                }
                return Maps.immutableEntry(this.currentKey, this.valueItr.next());
            }
        };
    }

    public ImmutableMultiset<K> keys() {
        return (ImmutableMultiset) super.keys();
    }

    /* access modifiers changed from: package-private */
    public ImmutableMultiset<K> createKeys() {
        return new Keys();
    }

    public ImmutableCollection<V> values() {
        return (ImmutableCollection) super.values();
    }

    /* access modifiers changed from: package-private */
    public ImmutableCollection<V> createValues() {
        return new Values(this);
    }

    /* access modifiers changed from: package-private */
    public UnmodifiableIterator<V> valueIterator() {
        return new UnmodifiableIterator<V>() {
            Iterator<? extends ImmutableCollection<V>> valueCollectionItr = ImmutableMultimap.this.map.values().iterator();
            Iterator<V> valueItr = Iterators.emptyIterator();

            public boolean hasNext() {
                return this.valueItr.hasNext() || this.valueCollectionItr.hasNext();
            }

            public V next() {
                if (!this.valueItr.hasNext()) {
                    this.valueItr = ((ImmutableCollection) this.valueCollectionItr.next()).iterator();
                }
                return this.valueItr.next();
            }
        };
    }

    public static class Builder<K, V> {
        Map<K, Collection<V>> builderMap = Platform.preservesInsertionOrderOnPutsMap();
        @MonotonicNonNullDecl
        Comparator<? super K> keyComparator;
        @MonotonicNonNullDecl
        Comparator<? super V> valueComparator;

        /* access modifiers changed from: package-private */
        public Collection<V> newMutableValueCollection() {
            return new ArrayList();
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Object obj, Object obj2) {
            CollectPreconditions.checkEntryNotNull(obj, obj2);
            Collection<V> valueCollection = this.builderMap.get(obj);
            if (valueCollection == null) {
                Map<K, Collection<V>> map = this.builderMap;
                Collection<V> newMutableValueCollection = newMutableValueCollection();
                valueCollection = newMutableValueCollection;
                map.put(obj, newMutableValueCollection);
            }
            valueCollection.add(obj2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry entry) {
            return put(entry.getKey(), entry.getValue());
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable iterable) {
            Iterator it = iterable.iterator();
            while (it.hasNext()) {
                put((Map.Entry) it.next());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Object obj, Iterable iterable) {
            if (obj == null) {
                String valueOf = String.valueOf(Iterables.toString(iterable));
                throw new NullPointerException(valueOf.length() != 0 ? "null key in entry: null=".concat(valueOf) : new String("null key in entry: null="));
            }
            Collection<V> valueCollection = this.builderMap.get(obj);
            if (valueCollection != null) {
                for (V value : iterable) {
                    CollectPreconditions.checkEntryNotNull(obj, value);
                    valueCollection.add(value);
                }
                return this;
            }
            Iterator<? extends V> valuesItr = iterable.iterator();
            if (!valuesItr.hasNext()) {
                return this;
            }
            Collection<V> valueCollection2 = newMutableValueCollection();
            while (valuesItr.hasNext()) {
                V value2 = valuesItr.next();
                CollectPreconditions.checkEntryNotNull(obj, value2);
                valueCollection2.add(value2);
            }
            this.builderMap.put(obj, valueCollection2);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Object obj, Object... objArr) {
            return putAll(obj, Arrays.asList(objArr));
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Multimap multimap) {
            for (Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
                putAll(entry.getKey(), (Iterable) entry.getValue());
            }
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderKeysBy(Comparator comparator) {
            this.keyComparator = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> orderValuesBy(Comparator comparator) {
            this.valueComparator = (Comparator) Preconditions.checkNotNull(comparator);
            return this;
        }

        /* access modifiers changed from: package-private */
        @CanIgnoreReturnValue
        public Builder<K, V> combine(Builder<K, V> other) {
            for (Map.Entry<K, Collection<V>> entry : other.builderMap.entrySet()) {
                putAll(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableMultimap<K, V> build() {
            Object entrySet = this.builderMap.entrySet();
            Comparator comparator = this.keyComparator;
            if (comparator != null) {
                entrySet = Ordering.from(comparator).onKeys().immutableSortedCopy(entrySet);
            }
            return ImmutableListMultimap.fromMapEntries(entrySet, this.valueComparator);
        }
    }

    @GwtIncompatible
    static class FieldSettersHolder {
        static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
        static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");

        FieldSettersHolder() {
        }
    }

    private static class EntryCollection<K, V> extends ImmutableCollection<Map.Entry<K, V>> {
        private static final long serialVersionUID = 0;
        @Weak
        final ImmutableMultimap<K, V> multimap;

        EntryCollection(ImmutableMultimap<K, V> multimap2) {
            this.multimap = multimap2;
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.multimap.entryIterator();
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return this.multimap.isPartialView();
        }

        public int size() {
            return this.multimap.size();
        }

        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) object;
            return this.multimap.containsEntry(entry.getKey(), entry.getValue());
        }
    }

    @GwtIncompatible
    private static final class KeysSerializedForm implements Serializable {
        final ImmutableMultimap<?, ?> multimap;

        KeysSerializedForm(ImmutableMultimap<?, ?> multimap2) {
            this.multimap = multimap2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return this.multimap.keys();
        }
    }

    private static final class Values<K, V> extends ImmutableCollection<V> {
        private static final long serialVersionUID = 0;
        @Weak
        private final transient ImmutableMultimap<K, V> multimap;

        Values(ImmutableMultimap<K, V> multimap2) {
            this.multimap = multimap2;
        }

        public boolean contains(@NullableDecl Object object) {
            return this.multimap.containsValue(object);
        }

        public UnmodifiableIterator<V> iterator() {
            return this.multimap.valueIterator();
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public int copyIntoArray(Object[] dst, int offset) {
            UnmodifiableIterator<? extends ImmutableCollection<V>> it = this.multimap.map.values().iterator();
            while (it.hasNext()) {
                offset = ((ImmutableCollection) it.next()).copyIntoArray(dst, offset);
            }
            return offset;
        }

        public int size() {
            return this.multimap.size();
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }
    }

    class Keys extends ImmutableMultiset<K> {
        Keys() {
        }

        public boolean contains(@NullableDecl Object object) {
            return ImmutableMultimap.this.containsKey(object);
        }

        public int count(@NullableDecl Object element) {
            Collection<V> values = (Collection) ImmutableMultimap.this.map.get(element);
            if (values == null) {
                return 0;
            }
            return values.size();
        }

        public ImmutableSet<K> elementSet() {
            return ImmutableMultimap.this.keySet();
        }

        public int size() {
            return ImmutableMultimap.this.size();
        }

        /* access modifiers changed from: package-private */
        public Multiset.Entry<K> getEntry(int index) {
            Map.Entry<K, ? extends Collection<V>> entry = ImmutableMultimap.this.map.entrySet().asList().get(index);
            return Multisets.immutableEntry(entry.getKey(), ((Collection) entry.getValue()).size());
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible
        public Object writeReplace() {
            return new KeysSerializedForm(ImmutableMultimap.this);
        }
    }
}
