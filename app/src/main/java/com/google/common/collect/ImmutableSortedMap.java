package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;

@GwtCompatible(emulated = true, serializable = true)
public final class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements NavigableMap<K, V> {
    private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP = new ImmutableSortedMap<>(ImmutableSortedSet.emptySet(Ordering.natural()), ImmutableList.m107of());
    private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public final transient RegularImmutableSortedSet<K> keySet;
    /* access modifiers changed from: private */
    public final transient ImmutableList<V> valueList;
    private transient ImmutableSortedMap<K, V> descendingMap;

    ImmutableSortedMap(RegularImmutableSortedSet<K> keySet2, ImmutableList<V> valueList2) {
        this(keySet2, valueList2, null);
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> keySet2, ImmutableList<V> valueList2, ImmutableSortedMap<K, V> descendingMap2) {
        this.keySet = keySet2;
        this.valueList = valueList2;
        this.descendingMap = descendingMap2;
    }

    static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
        if (Ordering.natural().equals(comparator)) {
            return m162of();
        }
        return new ImmutableSortedMap<>(ImmutableSortedSet.emptySet(comparator), ImmutableList.m107of());
    }

    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m162of() {
        return NATURAL_EMPTY_MAP;
    }

    /* renamed from: of */
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> m163of(K k1, V v1) {
        return m168of(Ordering.natural(), k1, v1);
    }

    /* access modifiers changed from: private */
    /* renamed from: of */
    public static <K, V> ImmutableSortedMap<K, V> m168of(Comparator<? super K> comparator, K k1, V v1) {
        return new ImmutableSortedMap<>(new RegularImmutableSortedSet(ImmutableList.m108of(k1), (Comparator) Preconditions.checkNotNull(comparator)), ImmutableList.m108of(v1));
    }

    /* renamed from: of */
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> m164of(K k1, V v1, K k2, V v2) {
        return ofEntries(entryOf(k1, v1), entryOf(k2, v2));
    }

    /* renamed from: of */
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> m165of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return ofEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3));
    }

    /* renamed from: of */
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> m166of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ofEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4));
    }

    /* renamed from: of */
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> m167of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ofEntries(entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5));
    }

    private static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> ofEntries(Map.Entry<K, V>... entries) {
        return fromEntries(Ordering.natural(), false, entries, entries.length);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        return copyOfInternal(map, (Ordering) NATURAL_ORDER);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return copyOfInternal(map, (Comparator) Preconditions.checkNotNull(comparator));
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        return copyOf(entries, (Ordering) NATURAL_ORDER);
    }

    @Beta
    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries, Comparator<? super K> comparator) {
        return fromEntries((Comparator) Preconditions.checkNotNull(comparator), false, entries);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> map) {
        Comparator<? super K> comparator = map.comparator();
        if (comparator == null) {
            comparator = NATURAL_ORDER;
        }
        if (map instanceof ImmutableSortedMap) {
            ImmutableSortedMap<K, V> kvMap = (ImmutableSortedMap) map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        }
        return fromEntries(comparator, true, map.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        boolean sameComparator = false;
        if (map instanceof SortedMap) {
            Comparator<?> comparator2 = ((SortedMap) map).comparator();
            sameComparator = comparator2 == null ? comparator == NATURAL_ORDER : comparator.equals(comparator2);
        }
        if (sameComparator && (map instanceof ImmutableSortedMap)) {
            ImmutableSortedMap<K, V> kvMap = (ImmutableSortedMap) map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        }
        return fromEntries(comparator, sameComparator, map.entrySet());
    }

    private static <K, V> ImmutableSortedMap<K, V> fromEntries(Comparator<? super K> comparator, boolean sameComparator, Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
        Map.Entry<K, V>[] entryArray = (Map.Entry[]) Iterables.toArray(entries, EMPTY_ENTRY_ARRAY);
        return fromEntries(comparator, sameComparator, entryArray, entryArray.length);
    }

    private static <K, V> ImmutableSortedMap<K, V> fromEntries(final Comparator<? super K> comparator, boolean sameComparator, Map.Entry<K, V>[] entryArray, int size) {
        if (size == 0) {
            return emptyMap(comparator);
        }
        if (size == 1) {
            return m168of(comparator, entryArray[0].getKey(), entryArray[0].getValue());
        }
        Object[] keys = new Object[size];
        Object[] values = new Object[size];
        if (sameComparator) {
            for (int i = 0; i < size; i++) {
                Object key = entryArray[i].getKey();
                Object value = entryArray[i].getValue();
                CollectPreconditions.checkEntryNotNull(key, value);
                keys[i] = key;
                values[i] = value;
            }
        } else {
            Arrays.sort(entryArray, 0, size, new Comparator<Map.Entry<K, V>>() {
                public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
                    return comparator.compare(e1.getKey(), e2.getKey());
                }
            });
            K prevKey = entryArray[0].getKey();
            keys[0] = prevKey;
            values[0] = entryArray[0].getValue();
            CollectPreconditions.checkEntryNotNull(keys[0], values[0]);
            for (int i2 = 1; i2 < size; i2++) {
                K key2 = entryArray[i2].getKey();
                V value2 = entryArray[i2].getValue();
                CollectPreconditions.checkEntryNotNull(key2, value2);
                keys[i2] = key2;
                values[i2] = value2;
                checkNoConflict(comparator.compare(prevKey, key2) != 0, "key", entryArray[i2 - 1], entryArray[i2]);
                prevKey = key2;
            }
        }
        return new ImmutableSortedMap<>(new RegularImmutableSortedSet(ImmutableList.asImmutableList(keys), comparator), ImmutableList.asImmutableList(values));
    }

    public static <K extends Comparable<?>, V> Builder<K, V> naturalOrder() {
        return new Builder<>(Ordering.natural());
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder<>(comparator);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> reverseOrder() {
        return new Builder<>(Ordering.natural().reverse());
    }

    public int size() {
        return this.valueList.size();
    }

    public V get(@NullableDecl Object key) {
        int index = this.keySet.indexOf(key);
        if (index == -1) {
            return null;
        }
        return this.valueList.get(index);
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return this.keySet.isPartialView() || this.valueList.isPartialView();
    }

    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    /* access modifiers changed from: package-private */
    public ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return isEmpty() ? ImmutableSet.m149of() : new ImmutableMapEntrySet<K, V>() {
            public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                return asList().iterator();
            }

            /* access modifiers changed from: package-private */
            public ImmutableList<Map.Entry<K, V>> createAsList() {
                return new ImmutableList<Map.Entry<K, V>>() {
                    public Map.Entry<K, V> get(int index) {
                        return new AbstractMap.SimpleImmutableEntry(ImmutableSortedMap.this.keySet.asList().get(index), ImmutableSortedMap.this.valueList.get(index));
                    }

                    /* access modifiers changed from: package-private */
                    public boolean isPartialView() {
                        return true;
                    }

                    public int size() {
                        return ImmutableSortedMap.this.size();
                    }
                };
            }

            /* access modifiers changed from: package-private */
            public ImmutableMap<K, V> map() {
                return ImmutableSortedMap.this;
            }
        };
    }

    public ImmutableSortedSet<K> keySet() {
        return this.keySet;
    }

    /* access modifiers changed from: package-private */
    public ImmutableSet<K> createKeySet() {
        throw new AssertionError("should never be called");
    }

    public ImmutableCollection<V> values() {
        return this.valueList;
    }

    /* access modifiers changed from: package-private */
    public ImmutableCollection<V> createValues() {
        throw new AssertionError("should never be called");
    }

    public Comparator<? super K> comparator() {
        return keySet().comparator();
    }

    public K firstKey() {
        return keySet().first();
    }

    public K lastKey() {
        return keySet().last();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E>
     arg types: [int, int]
     candidates:
      com.google.common.collect.ImmutableList.subList(int, int):java.util.List
      ClspMth{java.util.List.subList(int, int):java.util.List<E>}
      com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E> */
    private ImmutableSortedMap<K, V> getSubMap(int fromIndex, int toIndex) {
        if (fromIndex == 0 && toIndex == size()) {
            return this;
        }
        if (fromIndex == toIndex) {
            return emptyMap(comparator());
        }
        return new ImmutableSortedMap<>(this.keySet.getSubSet(fromIndex, toIndex), this.valueList.subList(fromIndex, toIndex));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.headMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public ImmutableSortedMap<K, V> headMap(K toKey) {
        return headMap((Object) toKey, false);
    }

    public ImmutableSortedMap<K, V> headMap(K toKey, boolean inclusive) {
        return getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(toKey), inclusive));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.subMap(java.lang.Object, boolean, java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int, K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.subMap(java.lang.Object, boolean, java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.subMap(java.lang.Object, boolean, java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.subMap(java.lang.Object, boolean, java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public ImmutableSortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap((Object) fromKey, true, (Object) toKey, false);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, boolean]
     candidates:
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, boolean]
     candidates:
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.headMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public ImmutableSortedMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        Preconditions.checkNotNull(fromKey);
        Preconditions.checkNotNull(toKey);
        Preconditions.checkArgument(comparator().compare(fromKey, toKey) <= 0, "expected fromKey <= toKey but %s > %s", fromKey, toKey);
        return headMap((Object) toKey, toInclusive).tailMap((Object) fromKey, fromInclusive);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public ImmutableSortedMap<K, V> tailMap(K fromKey) {
        return tailMap((Object) fromKey, true);
    }

    public ImmutableSortedMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(fromKey), inclusive), size());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.headMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public Map.Entry<K, V> lowerEntry(K key) {
        return headMap((Object) key, false).lastEntry();
    }

    public K lowerKey(K key) {
        return Maps.keyOrNull(lowerEntry(key));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.headMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.headMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public Map.Entry<K, V> floorEntry(K key) {
        return headMap((Object) key, true).lastEntry();
    }

    public K floorKey(K key) {
        return Maps.keyOrNull(floorEntry(key));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public Map.Entry<K, V> ceilingEntry(K key) {
        return tailMap((Object) key, true).firstEntry();
    }

    public K ceilingKey(K key) {
        return Maps.keyOrNull(ceilingEntry(key));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V>
     arg types: [K, int]
     candidates:
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap
      ClspMth{java.util.NavigableMap.tailMap(java.lang.Object, boolean):java.util.NavigableMap<K, V>}
      com.google.common.collect.ImmutableSortedMap.tailMap(java.lang.Object, boolean):com.google.common.collect.ImmutableSortedMap<K, V> */
    public Map.Entry<K, V> higherEntry(K key) {
        return tailMap((Object) key, false).firstEntry();
    }

    public K higherKey(K key) {
        return Maps.keyOrNull(higherEntry(key));
    }

    public Map.Entry<K, V> firstEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Map.Entry) entrySet().asList().get(0);
    }

    public Map.Entry<K, V> lastEntry() {
        if (isEmpty()) {
            return null;
        }
        return (Map.Entry) entrySet().asList().get(size() - 1);
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final Map.Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @CanIgnoreReturnValue
    @Deprecated
    public final Map.Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<K, V> result = this.descendingMap;
        if (result != null) {
            return result;
        }
        if (isEmpty()) {
            return emptyMap(Ordering.from(comparator()).reverse());
        }
        return new ImmutableSortedMap<>((RegularImmutableSortedSet) this.keySet.descendingSet(), this.valueList.reverse(), this);
    }

    public ImmutableSortedSet<K> navigableKeySet() {
        return this.keySet;
    }

    public ImmutableSortedSet<K> descendingKeySet() {
        return this.keySet.descendingSet();
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this);
    }

    public static class Builder<K, V> extends ImmutableMap.Builder<K, V> {
        private final Comparator<? super K> comparator;
        private transient Object[] keys;
        private transient Object[] values;

        public Builder(Comparator<? super K> comparator2) {
            this(comparator2, 4);
        }

        private Builder(Comparator<? super K> comparator2, int initialCapacity) {
            this.comparator = (Comparator) Preconditions.checkNotNull(comparator2);
            this.keys = new Object[initialCapacity];
            this.values = new Object[initialCapacity];
        }

        private void ensureCapacity(int minCapacity) {
            Object[] objArr = this.keys;
            if (minCapacity > objArr.length) {
                int newCapacity = ImmutableCollection.Builder.expandedCapacity(objArr.length, minCapacity);
                this.keys = Arrays.copyOf(this.keys, newCapacity);
                this.values = Arrays.copyOf(this.values, newCapacity);
            }
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(K key, V value) {
            ensureCapacity(this.size + 1);
            CollectPreconditions.checkEntryNotNull(key, value);
            this.keys[this.size] = key;
            this.values[this.size] = value;
            this.size++;
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put((Map.Entry) entry);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll((Map) map);
            return this;
        }

        @CanIgnoreReturnValue
        @Beta
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> entries) {
            super.putAll((Iterable) entries);
            return this;
        }

        @CanIgnoreReturnValue
        @Deprecated
        @Beta
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator2) {
            throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
        }

        public ImmutableSortedMap<K, V> build() {
            int i = this.size;
            if (i == 0) {
                return ImmutableSortedMap.emptyMap(this.comparator);
            }
            if (i == 1) {
                return ImmutableSortedMap.m168of(this.comparator, this.keys[0], this.values[0]);
            }
            Object[] sortedKeys = Arrays.copyOf(this.keys, this.size);
            Arrays.sort(sortedKeys, this.comparator);
            Object[] sortedValues = new Object[this.size];
            int i2 = 0;
            while (i2 < this.size) {
                if (i2 <= 0 || this.comparator.compare(sortedKeys[i2 - 1], sortedKeys[i2]) != 0) {
                    sortedValues[Arrays.binarySearch(sortedKeys, this.keys[i2], this.comparator)] = this.values[i2];
                    i2++;
                } else {
                    String valueOf = String.valueOf(sortedKeys[i2 - 1]);
                    String valueOf2 = String.valueOf(sortedKeys[i2]);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 57 + String.valueOf(valueOf2).length());
                    sb.append("keys required to be distinct but compared as equal: ");
                    sb.append(valueOf);
                    sb.append(" and ");
                    sb.append(valueOf2);
                    throw new IllegalArgumentException(sb.toString());
                }
            }
            return new ImmutableSortedMap<>(new RegularImmutableSortedSet(ImmutableList.asImmutableList(sortedKeys), this.comparator), ImmutableList.asImmutableList(sortedValues));
        }
    }

    private static class SerializedForm extends ImmutableMap.SerializedForm {
        private static final long serialVersionUID = 0;
        private final Comparator<Object> comparator;

        SerializedForm(ImmutableSortedMap<?, ?> sortedMap) {
            super(sortedMap);
            this.comparator = sortedMap.comparator();
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return createMap(new Builder<>(this.comparator));
        }
    }
}
