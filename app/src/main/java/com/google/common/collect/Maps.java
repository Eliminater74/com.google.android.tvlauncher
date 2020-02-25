package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.j2objc.annotations.RetainedWith;
import com.google.j2objc.annotations.Weak;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible(emulated = true)
public final class Maps {

    private Maps() {
    }

    static <K> Function<Map.Entry<K, ?>, K> keyFunction() {
        return EntryFunction.KEY;
    }

    static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return EntryFunction.VALUE;
    }

    static <K, V> Iterator<K> keyIterator(Iterator<Map.Entry<K, V>> entryIterator) {
        return new TransformedIterator<Map.Entry<K, V>, K>(entryIterator) {
            /* access modifiers changed from: package-private */
            public K transform(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }

    static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> entryIterator) {
        return new TransformedIterator<Map.Entry<K, V>, V>(entryIterator) {
            /* access modifiers changed from: package-private */
            public V transform(Map.Entry<K, V> entry) {
                return entry.getValue();
            }
        };
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.util.EnumMap.put(java.lang.Enum, java.lang.Object):V}
     arg types: [K, V]
     candidates:
      ClspMth{java.util.EnumMap.put(java.lang.Object, java.lang.Object):java.lang.Object}
      ClspMth{java.util.AbstractMap.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.EnumMap.put(java.lang.Enum, java.lang.Object):V} */
    @GwtCompatible(serializable = true)
    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> map) {
        if (map instanceof ImmutableEnumMap) {
            return (ImmutableEnumMap) map;
        }
        Iterator<? extends Map.Entry<K, ? extends V>> entryItr = map.entrySet().iterator();
        if (!entryItr.hasNext()) {
            return ImmutableMap.m126of();
        }
        Map.Entry<K, ? extends V> entry1 = (Map.Entry) entryItr.next();
        K key1 = (Enum) entry1.getKey();
        V value1 = entry1.getValue();
        CollectPreconditions.checkEntryNotNull(key1, value1);
        EnumMap<K, V> enumMap = new EnumMap<>(key1.getDeclaringClass());
        enumMap.put((Enum) key1, (Object) value1);
        while (entryItr.hasNext()) {
            Map.Entry<K, ? extends V> entry = (Map.Entry) entryItr.next();
            K key = (Enum) entry.getKey();
            V value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            enumMap.put((Enum) key, (Object) value);
        }
        return ImmutableEnumMap.asImmutable(enumMap);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<>(map);
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int expectedSize) {
        return new HashMap<>(capacity(expectedSize));
    }

    static int capacity(int expectedSize) {
        if (expectedSize < 3) {
            CollectPreconditions.checkNonnegative(expectedSize, "expectedSize");
            return expectedSize + 1;
        } else if (expectedSize < 1073741824) {
            return (int) ((((float) expectedSize) / 0.75f) + 1.0f);
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int expectedSize) {
        return new LinkedHashMap<>(capacity(expectedSize));
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap();
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> map) {
        return new TreeMap<>((SortedMap) map);
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@NullableDecl Comparator<C> comparator) {
        return new TreeMap<>(comparator);
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> type) {
        return new EnumMap<>((Class) Preconditions.checkNotNull(type));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<>(map);
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<>();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.difference(java.util.SortedMap, java.util.Map):com.google.common.collect.SortedMapDifference<K, V>
     arg types: [java.util.SortedMap<K, ? extends V>, java.util.Map<? extends K, ? extends V>]
     candidates:
      com.google.common.collect.Maps.difference(java.util.Map, java.util.Map):com.google.common.collect.MapDifference<K, V>
      com.google.common.collect.Maps.difference(java.util.SortedMap, java.util.Map):com.google.common.collect.SortedMapDifference<K, V> */
    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right) {
        if (left instanceof SortedMap) {
            return difference((SortedMap) ((SortedMap) left), (Map) right);
        }
        return difference(left, right, Equivalence.equals());
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right, Equivalence<? super V> valueEquivalence) {
        Preconditions.checkNotNull(valueEquivalence);
        LinkedHashMap newLinkedHashMap = newLinkedHashMap();
        Map<K, V> onlyOnRight = new LinkedHashMap<>(right);
        LinkedHashMap newLinkedHashMap2 = newLinkedHashMap();
        LinkedHashMap newLinkedHashMap3 = newLinkedHashMap();
        doDifference(left, right, valueEquivalence, newLinkedHashMap, onlyOnRight, newLinkedHashMap2, newLinkedHashMap3);
        return new MapDifferenceImpl(newLinkedHashMap, onlyOnRight, newLinkedHashMap2, newLinkedHashMap3);
    }

    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> left, Map<? extends K, ? extends V> right) {
        Preconditions.checkNotNull(left);
        Preconditions.checkNotNull(right);
        Comparator<? super K> comparator = orNaturalOrder(left.comparator());
        TreeMap<K, V> newTreeMap = newTreeMap(comparator);
        TreeMap<K, V> newTreeMap2 = newTreeMap(comparator);
        newTreeMap2.putAll(right);
        TreeMap<K, V> newTreeMap3 = newTreeMap(comparator);
        TreeMap<K, V> newTreeMap4 = newTreeMap(comparator);
        doDifference(left, right, Equivalence.equals(), newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
        return new SortedMapDifferenceImpl(newTreeMap, newTreeMap2, newTreeMap3, newTreeMap4);
    }

    private static <K, V> void doDifference(Map<? extends K, ? extends V> left, Map<? extends K, ? extends V> right, Equivalence<? super V> valueEquivalence, Map<K, V> onlyOnLeft, Map<K, V> onlyOnRight, Map<K, V> onBoth, Map<K, MapDifference.ValueDifference<V>> differences) {
        for (Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
            K leftKey = entry.getKey();
            V leftValue = entry.getValue();
            if (right.containsKey(leftKey)) {
                V rightValue = onlyOnRight.remove(leftKey);
                if (valueEquivalence.equivalent(leftValue, rightValue)) {
                    onBoth.put(leftKey, leftValue);
                } else {
                    differences.put(leftKey, ValueDifferenceImpl.create(leftValue, rightValue));
                }
            } else {
                onlyOnLeft.put(leftKey, leftValue);
            }
        }
    }

    /* access modifiers changed from: private */
    public static <K, V> Map<K, V> unmodifiableMap(Map<K, ? extends V> map) {
        if (map instanceof SortedMap) {
            return Collections.unmodifiableSortedMap((SortedMap) map);
        }
        return Collections.unmodifiableMap(map);
    }

    static <E> Comparator<? super E> orNaturalOrder(@NullableDecl Comparator<? super E> comparator) {
        if (comparator != null) {
            return comparator;
        }
        return Ordering.natural();
    }

    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return new AsMapView(set, function);
    }

    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> set, Function<? super K, V> function) {
        return new SortedAsMapView(set, function);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> set, Function<? super K, V> function) {
        return new NavigableAsMapView(set, function);
    }

    static <K, V> Iterator<Map.Entry<K, V>> asMapEntryIterator(Set<K> set, final Function<? super K, V> function) {
        return new TransformedIterator<K, Map.Entry<K, V>>(set.iterator()) {
            /* access modifiers changed from: package-private */
            public Map.Entry<K, V> transform(K key) {
                return Maps.immutableEntry(key, function.apply(key));
            }
        };
    }

    /* access modifiers changed from: private */
    public static <E> Set<E> removeOnlySet(final Set<E> set) {
        return new ForwardingSet<E>() {
            /* access modifiers changed from: protected */
            public Set<E> delegate() {
                return set;
            }

            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }
        };
    }

    /* access modifiers changed from: private */
    public static <E> SortedSet<E> removeOnlySortedSet(final SortedSet<E> set) {
        return new ForwardingSortedSet<E>() {
            /* access modifiers changed from: protected */
            public SortedSet<E> delegate() {
                return set;
            }

            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            public SortedSet<E> headSet(E toElement) {
                return Maps.removeOnlySortedSet(super.headSet(toElement));
            }

            public SortedSet<E> subSet(E fromElement, E toElement) {
                return Maps.removeOnlySortedSet(super.subSet(fromElement, toElement));
            }

            public SortedSet<E> tailSet(E fromElement) {
                return Maps.removeOnlySortedSet(super.tailSet(fromElement));
            }
        };
    }

    /* access modifiers changed from: private */
    @GwtIncompatible
    public static <E> NavigableSet<E> removeOnlyNavigableSet(final NavigableSet<E> set) {
        return new ForwardingNavigableSet<E>() {
            /* access modifiers changed from: protected */
            public NavigableSet<E> delegate() {
                return set;
            }

            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            public SortedSet<E> headSet(E toElement) {
                return Maps.removeOnlySortedSet(super.headSet(toElement));
            }

            public NavigableSet<E> headSet(E toElement, boolean inclusive) {
                return Maps.removeOnlyNavigableSet(super.headSet(toElement, inclusive));
            }

            public SortedSet<E> subSet(E fromElement, E toElement) {
                return Maps.removeOnlySortedSet(super.subSet(fromElement, toElement));
            }

            public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
                return Maps.removeOnlyNavigableSet(super.subSet(fromElement, fromInclusive, toElement, toInclusive));
            }

            public SortedSet<E> tailSet(E fromElement) {
                return Maps.removeOnlySortedSet(super.tailSet(fromElement));
            }

            public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
                return Maps.removeOnlyNavigableSet(super.tailSet(fromElement, inclusive));
            }

            public NavigableSet<E> descendingSet() {
                return Maps.removeOnlyNavigableSet(super.descendingSet());
            }
        };
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> keys, Function<? super K, V> valueFunction) {
        return toMap(keys.iterator(), valueFunction);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> keys, Function<? super K, V> valueFunction) {
        Preconditions.checkNotNull(valueFunction);
        Map<K, V> builder = newLinkedHashMap();
        while (keys.hasNext()) {
            K key = keys.next();
            builder.put(key, valueFunction.apply(key));
        }
        return ImmutableMap.copyOf(builder);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> keyFunction) {
        return uniqueIndex(values.iterator(), keyFunction);
    }

    @CanIgnoreReturnValue
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> values, Function<? super V, K> keyFunction) {
        Preconditions.checkNotNull(keyFunction);
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        while (values.hasNext()) {
            V value = values.next();
            builder.put(keyFunction.apply(value), value);
        }
        try {
            return builder.build();
        } catch (IllegalArgumentException duplicateKeys) {
            throw new IllegalArgumentException(String.valueOf(duplicateKeys.getMessage()).concat(". To index multiple values under a key, use Multimaps.index."));
        }
    }

    @GwtIncompatible
    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            builder.put(key, properties.getProperty(key));
        }
        return builder.build();
    }

    @GwtCompatible(serializable = true)
    public static <K, V> Map.Entry<K, V> immutableEntry(@NullableDecl K key, @NullableDecl V value) {
        return new ImmutableEntry(key, value);
    }

    static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> entrySet) {
        return new UnmodifiableEntrySet(Collections.unmodifiableSet(entrySet));
    }

    static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<? extends K, ? extends V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>() {
            public K getKey() {
                return entry.getKey();
            }

            public V getValue() {
                return entry.getValue();
            }
        };
    }

    static <K, V> UnmodifiableIterator<Map.Entry<K, V>> unmodifiableEntryIterator(final Iterator<Map.Entry<K, V>> entryIterator) {
        return new UnmodifiableIterator<Map.Entry<K, V>>() {
            public boolean hasNext() {
                return entryIterator.hasNext();
            }

            public Map.Entry<K, V> next() {
                return Maps.unmodifiableEntry((Map.Entry) entryIterator.next());
            }
        };
    }

    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> bimap) {
        return new BiMapConverter(bimap);
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> bimap) {
        return Synchronized.biMap(bimap, null);
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> bimap) {
        return new UnmodifiableBiMap(bimap, null);
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> fromMap, Function<? super V1, V2> function) {
        return transformEntries(fromMap, asEntryTransformer(function));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.transformEntries(java.util.SortedMap, com.google.common.collect.Maps$EntryTransformer):java.util.SortedMap<K, V2>
     arg types: [java.util.SortedMap<K, V1>, com.google.common.collect.Maps$EntryTransformer<K, V1, V2>]
     candidates:
      com.google.common.collect.Maps.transformEntries(java.util.Map, com.google.common.collect.Maps$EntryTransformer):java.util.Map<K, V2>
      com.google.common.collect.Maps.transformEntries(java.util.NavigableMap, com.google.common.collect.Maps$EntryTransformer):java.util.NavigableMap<K, V2>
      com.google.common.collect.Maps.transformEntries(java.util.SortedMap, com.google.common.collect.Maps$EntryTransformer):java.util.SortedMap<K, V2> */
    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> fromMap, Function<? super V1, V2> function) {
        return transformEntries((SortedMap) fromMap, (EntryTransformer) asEntryTransformer(function));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.transformEntries(java.util.NavigableMap, com.google.common.collect.Maps$EntryTransformer):java.util.NavigableMap<K, V2>
     arg types: [java.util.NavigableMap<K, V1>, com.google.common.collect.Maps$EntryTransformer<K, V1, V2>]
     candidates:
      com.google.common.collect.Maps.transformEntries(java.util.Map, com.google.common.collect.Maps$EntryTransformer):java.util.Map<K, V2>
      com.google.common.collect.Maps.transformEntries(java.util.SortedMap, com.google.common.collect.Maps$EntryTransformer):java.util.SortedMap<K, V2>
      com.google.common.collect.Maps.transformEntries(java.util.NavigableMap, com.google.common.collect.Maps$EntryTransformer):java.util.NavigableMap<K, V2> */
    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> fromMap, Function<? super V1, V2> function) {
        return transformEntries((NavigableMap) fromMap, (EntryTransformer) asEntryTransformer(function));
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesMap(fromMap, transformer);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesSortedMap(fromMap, transformer);
    }

    @GwtIncompatible
    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesNavigableMap(fromMap, transformer);
    }

    static <K, V1, V2> EntryTransformer<K, V1, V2> asEntryTransformer(final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer<K, V1, V2>() {
            public V2 transformEntry(K k, V1 value) {
                return Function.this.apply(value);
            }
        };
    }

    static <K, V1, V2> Function<V1, V2> asValueToValueFunction(final EntryTransformer<? super K, V1, V2> transformer, final K key) {
        Preconditions.checkNotNull(transformer);
        return new Function<V1, V2>() {
            public V2 apply(@NullableDecl V1 v1) {
                return EntryTransformer.this.transformEntry(key, v1);
            }
        };
    }

    static <K, V1, V2> Function<Map.Entry<K, V1>, V2> asEntryToValueFunction(final EntryTransformer<? super K, ? super V1, V2> transformer) {
        Preconditions.checkNotNull(transformer);
        return new Function<Map.Entry<K, V1>, V2>() {
            public V2 apply(Map.Entry<K, V1> entry) {
                return EntryTransformer.this.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    static <V2, K, V1> Map.Entry<K, V2> transformEntry(final EntryTransformer<? super K, ? super V1, V2> transformer, final Map.Entry<K, V1> entry) {
        Preconditions.checkNotNull(transformer);
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V2>() {
            public K getKey() {
                return entry.getKey();
            }

            public V2 getValue() {
                return transformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    static <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> asEntryToEntryFunction(final EntryTransformer<? super K, ? super V1, V2> transformer) {
        Preconditions.checkNotNull(transformer);
        return new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>() {
            public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
                return Maps.transformEntry(EntryTransformer.this, entry);
            }
        };
    }

    static <K> Predicate<Map.Entry<K, ?>> keyPredicateOnEntries(Predicate<? super K> keyPredicate) {
        return Predicates.compose(keyPredicate, keyFunction());
    }

    static <V> Predicate<Map.Entry<?, V>> valuePredicateOnEntries(Predicate<? super V> valuePredicate) {
        return Predicates.compose(valuePredicate, valueFunction());
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        Preconditions.checkNotNull(keyPredicate);
        Predicate<Map.Entry<K, ?>> entryPredicate = keyPredicateOnEntries(keyPredicate);
        if (unfiltered instanceof AbstractFilteredMap) {
            return filterFiltered((AbstractFilteredMap) unfiltered, entryPredicate);
        }
        return new FilteredKeyMap((Map) Preconditions.checkNotNull(unfiltered), keyPredicate, entryPredicate);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
     arg types: [java.util.SortedMap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<K, ?>>]
     candidates:
      com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V> */
    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        return filterEntries((SortedMap) unfiltered, (Predicate) keyPredicateOnEntries(keyPredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
     arg types: [java.util.NavigableMap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<K, ?>>]
     candidates:
      com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V> */
    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        return filterEntries((NavigableMap) unfiltered, (Predicate) keyPredicateOnEntries(keyPredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
     arg types: [com.google.common.collect.BiMap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<K, ?>>]
     candidates:
      com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
      com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V> */
    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> unfiltered, Predicate<? super K> keyPredicate) {
        Preconditions.checkNotNull(keyPredicate);
        return filterEntries((BiMap) unfiltered, (Predicate) keyPredicateOnEntries(keyPredicate));
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> unfiltered, Predicate<? super V> valuePredicate) {
        return filterEntries(unfiltered, valuePredicateOnEntries(valuePredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
     arg types: [java.util.SortedMap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<?, V>>]
     candidates:
      com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V> */
    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
        return filterEntries((SortedMap) unfiltered, (Predicate) valuePredicateOnEntries(valuePredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
     arg types: [java.util.NavigableMap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<?, V>>]
     candidates:
      com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V> */
    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
        return filterEntries((NavigableMap) unfiltered, (Predicate) valuePredicateOnEntries(valuePredicate));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
     arg types: [com.google.common.collect.BiMap<K, V>, com.google.common.base.Predicate<java.util.Map$Entry<?, V>>]
     candidates:
      com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
      com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
      com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V> */
    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> unfiltered, Predicate<? super V> valuePredicate) {
        return filterEntries((BiMap) unfiltered, (Predicate) valuePredicateOnEntries(valuePredicate));
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        if (unfiltered instanceof AbstractFilteredMap) {
            return filterFiltered((AbstractFilteredMap) unfiltered, entryPredicate);
        }
        return new FilteredEntryMap((Map) Preconditions.checkNotNull(unfiltered), entryPredicate);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntrySortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
     arg types: [com.google.common.collect.Maps$FilteredEntrySortedMap, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
     candidates:
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntryBiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$AbstractFilteredMap, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntryNavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntrySortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V> */
    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        if (unfiltered instanceof FilteredEntrySortedMap) {
            return filterFiltered((FilteredEntrySortedMap) unfiltered, (Predicate) entryPredicate);
        }
        return new FilteredEntrySortedMap((SortedMap) Preconditions.checkNotNull(unfiltered), entryPredicate);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        if (unfiltered instanceof FilteredEntryNavigableMap) {
            return filterFiltered((FilteredEntryNavigableMap) unfiltered, entryPredicate);
        }
        return new FilteredEntryNavigableMap((NavigableMap) Preconditions.checkNotNull(unfiltered), entryPredicate);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntryBiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
     arg types: [com.google.common.collect.Maps$FilteredEntryBiMap, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
     candidates:
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$AbstractFilteredMap, com.google.common.base.Predicate):java.util.Map<K, V>
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntryNavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntrySortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
      com.google.common.collect.Maps.filterFiltered(com.google.common.collect.Maps$FilteredEntryBiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V> */
    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(unfiltered);
        Preconditions.checkNotNull(entryPredicate);
        if (unfiltered instanceof FilteredEntryBiMap) {
            return filterFiltered((FilteredEntryBiMap) unfiltered, (Predicate) entryPredicate);
        }
        return new FilteredEntryBiMap(unfiltered, entryPredicate);
    }

    private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return new FilteredEntryMap(map.unfiltered, Predicates.and(map.predicate, entryPredicate));
    }

    private static <K, V> SortedMap<K, V> filterFiltered(FilteredEntrySortedMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return new FilteredEntrySortedMap(map.sortedMap(), Predicates.and(map.predicate, entryPredicate));
    }

    @GwtIncompatible
    private static <K, V> NavigableMap<K, V> filterFiltered(FilteredEntryNavigableMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return new FilteredEntryNavigableMap(map.unfiltered, Predicates.and(map.entryPredicate, entryPredicate));
    }

    private static <K, V> BiMap<K, V> filterFiltered(FilteredEntryBiMap<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate) {
        return new FilteredEntryBiMap(map.unfiltered(), Predicates.and(map.predicate, entryPredicate));
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> map) {
        Preconditions.checkNotNull(map);
        if (map instanceof UnmodifiableNavigableMap) {
            return map;
        }
        return new UnmodifiableNavigableMap(map);
    }

    /* access modifiers changed from: private */
    @NullableDecl
    public static <K, V> Map.Entry<K, V> unmodifiableOrNull(@NullableDecl Map.Entry<K, ? extends V> entry) {
        if (entry == null) {
            return null;
        }
        return unmodifiableEntry(entry);
    }

    @GwtIncompatible
    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return Synchronized.navigableMap(navigableMap);
    }

    static <V> V safeGet(Map<?, V> map, @NullableDecl Object key) {
        Preconditions.checkNotNull(map);
        try {
            return map.get(key);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }

    static boolean safeContainsKey(Map<?, ?> map, Object key) {
        Preconditions.checkNotNull(map);
        try {
            return map.containsKey(key);
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }
    }

    static <V> V safeRemove(Map<?, V> map, Object key) {
        Preconditions.checkNotNull(map);
        try {
            return map.remove(key);
        } catch (ClassCastException | NullPointerException e) {
            return null;
        }
    }

    static boolean containsKeyImpl(Map<?, ?> map, @NullableDecl Object key) {
        return Iterators.contains(keyIterator(map.entrySet().iterator()), key);
    }

    static boolean containsValueImpl(Map<?, ?> map, @NullableDecl Object value) {
        return Iterators.contains(valueIterator(map.entrySet().iterator()), value);
    }

    static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        return c.contains(unmodifiableEntry((Map.Entry) o));
    }

    static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> c, Object o) {
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        return c.remove(unmodifiableEntry((Map.Entry) o));
    }

    static boolean equalsImpl(Map<?, ?> map, Object object) {
        if (map == object) {
            return true;
        }
        if (object instanceof Map) {
            return map.entrySet().equals(((Map) object).entrySet());
        }
        return false;
    }

    static String toStringImpl(Map<?, ?> map) {
        StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
        }
        sb.append('}');
        return sb.toString();
    }

    static <K, V> void putAllImpl(Map<K, V> self, Map<? extends K, ? extends V> map) {
        for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            self.put(entry.getKey(), entry.getValue());
        }
    }

    @NullableDecl
    static <K> K keyOrNull(@NullableDecl Map.Entry<K, ?> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @NullableDecl
    static <V> V valueOrNull(@NullableDecl Map.Entry<?, V> entry) {
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    static <E> ImmutableMap<E, Integer> indexMap(Collection<E> list) {
        ImmutableMap.Builder<E, Integer> builder = new ImmutableMap.Builder<>(list.size());
        int i = 0;
        for (E e : list) {
            builder.put(e, Integer.valueOf(i));
            i++;
        }
        return builder.build();
    }

    @GwtIncompatible
    @Beta
    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> map, Range<K> range) {
        boolean z = true;
        if (map.comparator() != null && map.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            Preconditions.checkArgument(map.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0, "map is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            K lowerEndpoint = range.lowerEndpoint();
            boolean z2 = range.lowerBoundType() == BoundType.CLOSED;
            K upperEndpoint = range.upperEndpoint();
            if (range.upperBoundType() != BoundType.CLOSED) {
                z = false;
            }
            return map.subMap(lowerEndpoint, z2, upperEndpoint, z);
        } else if (range.hasLowerBound()) {
            K lowerEndpoint2 = range.lowerEndpoint();
            if (range.lowerBoundType() != BoundType.CLOSED) {
                z = false;
            }
            return map.tailMap(lowerEndpoint2, z);
        } else if (!range.hasUpperBound()) {
            return (NavigableMap) Preconditions.checkNotNull(map);
        } else {
            K upperEndpoint2 = range.upperEndpoint();
            if (range.upperBoundType() != BoundType.CLOSED) {
                z = false;
            }
            return map.headMap(upperEndpoint2, z);
        }
    }

    private enum EntryFunction implements Function<Map.Entry<?, ?>, Object> {
        KEY {
            @NullableDecl
            public /* bridge */ /* synthetic */ Object apply(Object obj) {
                return apply((Map.Entry<?, ?>) ((Map.Entry) obj));
            }

            @NullableDecl
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getKey();
            }
        },
        VALUE {
            @NullableDecl
            public /* bridge */ /* synthetic */ Object apply(Object obj) {
                return apply((Map.Entry<?, ?>) ((Map.Entry) obj));
            }

            @NullableDecl
            public Object apply(Map.Entry<?, ?> entry) {
                return entry.getValue();
            }
        }
    }

    public interface EntryTransformer<K, V1, V2> {
        V2 transformEntry(@NullableDecl K k, @NullableDecl V1 v1);
    }

    static class MapDifferenceImpl<K, V> implements MapDifference<K, V> {
        final Map<K, MapDifference.ValueDifference<V>> differences;
        final Map<K, V> onBoth;
        final Map<K, V> onlyOnLeft;
        final Map<K, V> onlyOnRight;

        MapDifferenceImpl(Map<K, V> onlyOnLeft2, Map<K, V> onlyOnRight2, Map<K, V> onBoth2, Map<K, MapDifference.ValueDifference<V>> differences2) {
            this.onlyOnLeft = Maps.unmodifiableMap(onlyOnLeft2);
            this.onlyOnRight = Maps.unmodifiableMap(onlyOnRight2);
            this.onBoth = Maps.unmodifiableMap(onBoth2);
            this.differences = Maps.unmodifiableMap(differences2);
        }

        public boolean areEqual() {
            return this.onlyOnLeft.isEmpty() && this.onlyOnRight.isEmpty() && this.differences.isEmpty();
        }

        public Map<K, V> entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }

        public Map<K, V> entriesOnlyOnRight() {
            return this.onlyOnRight;
        }

        public Map<K, V> entriesInCommon() {
            return this.onBoth;
        }

        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.differences;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof MapDifference)) {
                return false;
            }
            MapDifference<?, ?> other = (MapDifference) object;
            if (!entriesOnlyOnLeft().equals(other.entriesOnlyOnLeft()) || !entriesOnlyOnRight().equals(other.entriesOnlyOnRight()) || !entriesInCommon().equals(other.entriesInCommon()) || !entriesDiffering().equals(other.entriesDiffering())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(entriesOnlyOnLeft(), entriesOnlyOnRight(), entriesInCommon(), entriesDiffering());
        }

        public String toString() {
            if (areEqual()) {
                return "equal";
            }
            StringBuilder result = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                result.append(": only on left=");
                result.append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                result.append(": only on right=");
                result.append(this.onlyOnRight);
            }
            if (!this.differences.isEmpty()) {
                result.append(": value differences=");
                result.append(this.differences);
            }
            return result.toString();
        }
    }

    static class ValueDifferenceImpl<V> implements MapDifference.ValueDifference<V> {
        @NullableDecl
        private final V left;
        @NullableDecl
        private final V right;

        private ValueDifferenceImpl(@NullableDecl V left2, @NullableDecl V right2) {
            this.left = left2;
            this.right = right2;
        }

        static <V> MapDifference.ValueDifference<V> create(@NullableDecl V left2, @NullableDecl V right2) {
            return new ValueDifferenceImpl(left2, right2);
        }

        public V leftValue() {
            return this.left;
        }

        public V rightValue() {
            return this.right;
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof MapDifference.ValueDifference)) {
                return false;
            }
            MapDifference.ValueDifference<?> that = (MapDifference.ValueDifference) object;
            if (!Objects.equal(this.left, that.leftValue()) || !Objects.equal(this.right, that.rightValue())) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }

        public String toString() {
            String valueOf = String.valueOf(this.left);
            String valueOf2 = String.valueOf(this.right);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 4 + String.valueOf(valueOf2).length());
            sb.append("(");
            sb.append(valueOf);
            sb.append(", ");
            sb.append(valueOf2);
            sb.append(")");
            return sb.toString();
        }
    }

    static class SortedMapDifferenceImpl<K, V> extends MapDifferenceImpl<K, V> implements SortedMapDifference<K, V> {
        SortedMapDifferenceImpl(SortedMap<K, V> onlyOnLeft, SortedMap<K, V> onlyOnRight, SortedMap<K, V> onBoth, SortedMap<K, MapDifference.ValueDifference<V>> differences) {
            super(onlyOnLeft, onlyOnRight, onBoth, differences);
        }

        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return (SortedMap) super.entriesDiffering();
        }

        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap) super.entriesInCommon();
        }

        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap) super.entriesOnlyOnLeft();
        }

        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap) super.entriesOnlyOnRight();
        }
    }

    private static class AsMapView<K, V> extends ViewCachingAbstractMap<K, V> {
        final Function<? super K, V> function;
        private final Set<K> set;

        AsMapView(Set<K> set2, Function<? super K, V> function2) {
            this.set = (Set) Preconditions.checkNotNull(set2);
            this.function = (Function) Preconditions.checkNotNull(function2);
        }

        /* access modifiers changed from: package-private */
        public Set<K> backingSet() {
            return this.set;
        }

        public Set<K> createKeySet() {
            return Maps.removeOnlySet(backingSet());
        }

        /* access modifiers changed from: package-private */
        public Collection<V> createValues() {
            return Collections2.transform(this.set, this.function);
        }

        public int size() {
            return backingSet().size();
        }

        public boolean containsKey(@NullableDecl Object key) {
            return backingSet().contains(key);
        }

        public V get(@NullableDecl Object key) {
            if (!Collections2.safeContains(backingSet(), key)) {
                return null;
            }
            return this.function.apply(key);
        }

        public V remove(@NullableDecl Object key) {
            if (!backingSet().remove(key)) {
                return null;
            }
            return this.function.apply(key);
        }

        public void clear() {
            backingSet().clear();
        }

        /* access modifiers changed from: protected */
        public Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet<K, V>() {
                /* access modifiers changed from: package-private */
                public Map<K, V> map() {
                    return AsMapView.this;
                }

                public Iterator<Map.Entry<K, V>> iterator() {
                    return Maps.asMapEntryIterator(AsMapView.this.backingSet(), AsMapView.this.function);
                }
            };
        }
    }

    private static class SortedAsMapView<K, V> extends AsMapView<K, V> implements SortedMap<K, V> {
        SortedAsMapView(SortedSet<K> set, Function<? super K, V> function) {
            super(set, function);
        }

        /* access modifiers changed from: package-private */
        public SortedSet<K> backingSet() {
            return (SortedSet) super.backingSet();
        }

        public Comparator<? super K> comparator() {
            return backingSet().comparator();
        }

        public Set<K> keySet() {
            return Maps.removeOnlySortedSet(backingSet());
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return Maps.asMap(backingSet().subSet(fromKey, toKey), this.function);
        }

        public SortedMap<K, V> headMap(K toKey) {
            return Maps.asMap(backingSet().headSet(toKey), this.function);
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            return Maps.asMap(backingSet().tailSet(fromKey), this.function);
        }

        public K firstKey() {
            return backingSet().first();
        }

        public K lastKey() {
            return backingSet().last();
        }
    }

    @GwtIncompatible
    private static final class NavigableAsMapView<K, V> extends AbstractNavigableMap<K, V> {
        private final Function<? super K, V> function;
        private final NavigableSet<K> set;

        NavigableAsMapView(NavigableSet<K> ks, Function<? super K, V> vFunction) {
            this.set = (NavigableSet) Preconditions.checkNotNull(ks);
            this.function = (Function) Preconditions.checkNotNull(vFunction);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableSet<K>, com.google.common.base.Function<? super K, V>]
         candidates:
          com.google.common.collect.Maps.asMap(java.util.Set, com.google.common.base.Function):java.util.Map<K, V>
          com.google.common.collect.Maps.asMap(java.util.SortedSet, com.google.common.base.Function):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return Maps.asMap((NavigableSet) this.set.subSet(fromKey, fromInclusive, toKey, toInclusive), (Function) this.function);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableSet<K>, com.google.common.base.Function<? super K, V>]
         candidates:
          com.google.common.collect.Maps.asMap(java.util.Set, com.google.common.base.Function):java.util.Map<K, V>
          com.google.common.collect.Maps.asMap(java.util.SortedSet, com.google.common.base.Function):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return Maps.asMap((NavigableSet) this.set.headSet(toKey, inclusive), (Function) this.function);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableSet<K>, com.google.common.base.Function<? super K, V>]
         candidates:
          com.google.common.collect.Maps.asMap(java.util.Set, com.google.common.base.Function):java.util.Map<K, V>
          com.google.common.collect.Maps.asMap(java.util.SortedSet, com.google.common.base.Function):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return Maps.asMap((NavigableSet) this.set.tailSet(fromKey, inclusive), (Function) this.function);
        }

        public Comparator<? super K> comparator() {
            return this.set.comparator();
        }

        @NullableDecl
        public V get(@NullableDecl Object key) {
            if (!Collections2.safeContains(this.set, key)) {
                return null;
            }
            return this.function.apply(key);
        }

        public void clear() {
            this.set.clear();
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return Maps.asMapEntryIterator(this.set, this.function);
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return descendingMap().entrySet().iterator();
        }

        public NavigableSet<K> navigableKeySet() {
            return Maps.removeOnlyNavigableSet(this.set);
        }

        public int size() {
            return this.set.size();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableSet<K>, com.google.common.base.Function<? super K, V>]
         candidates:
          com.google.common.collect.Maps.asMap(java.util.Set, com.google.common.base.Function):java.util.Map<K, V>
          com.google.common.collect.Maps.asMap(java.util.SortedSet, com.google.common.base.Function):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.asMap(java.util.NavigableSet, com.google.common.base.Function):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap((NavigableSet) this.set.descendingSet(), (Function) this.function);
        }
    }

    static class UnmodifiableEntries<K, V> extends ForwardingCollection<Map.Entry<K, V>> {
        private final Collection<Map.Entry<K, V>> entries;

        UnmodifiableEntries(Collection<Map.Entry<K, V>> entries2) {
            this.entries = entries2;
        }

        /* access modifiers changed from: protected */
        public Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }

        public Iterator<Map.Entry<K, V>> iterator() {
            return Maps.unmodifiableEntryIterator(this.entries.iterator());
        }

        public Object[] toArray() {
            return standardToArray();
        }

        public <T> T[] toArray(T[] array) {
            return standardToArray(array);
        }
    }

    static class UnmodifiableEntrySet<K, V> extends UnmodifiableEntries<K, V> implements Set<Map.Entry<K, V>> {
        UnmodifiableEntrySet(Set<Map.Entry<K, V>> entries) {
            super(entries);
        }

        public boolean equals(@NullableDecl Object object) {
            return Sets.equalsImpl(this, object);
        }

        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    private static final class BiMapConverter<A, B> extends Converter<A, B> implements Serializable {
        private static final long serialVersionUID = 0;
        private final BiMap<A, B> bimap;

        BiMapConverter(BiMap<A, B> bimap2) {
            this.bimap = (BiMap) Preconditions.checkNotNull(bimap2);
        }

        private static <X, Y> Y convert(BiMap<X, Y> bimap2, X input) {
            Y output = bimap2.get(input);
            Preconditions.checkArgument(output != null, "No non-null mapping present for input: %s", input);
            return output;
        }

        /* access modifiers changed from: protected */
        public B doForward(A a) {
            return convert(this.bimap, a);
        }

        /* access modifiers changed from: protected */
        public A doBackward(B b) {
            return convert(this.bimap.inverse(), b);
        }

        public boolean equals(@NullableDecl Object object) {
            if (object instanceof BiMapConverter) {
                return this.bimap.equals(((BiMapConverter) object).bimap);
            }
            return false;
        }

        public int hashCode() {
            return this.bimap.hashCode();
        }

        public String toString() {
            String valueOf = String.valueOf(this.bimap);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
            sb.append("Maps.asConverter(");
            sb.append(valueOf);
            sb.append(")");
            return sb.toString();
        }
    }

    private static class UnmodifiableBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable {
        private static final long serialVersionUID = 0;
        final BiMap<? extends K, ? extends V> delegate;
        final Map<K, V> unmodifiableMap;
        @RetainedWith
        @MonotonicNonNullDecl
        BiMap<V, K> inverse;
        @MonotonicNonNullDecl
        transient Set<V> values;

        UnmodifiableBiMap(BiMap<? extends K, ? extends V> delegate2, @NullableDecl BiMap<V, K> inverse2) {
            this.unmodifiableMap = Collections.unmodifiableMap(delegate2);
            this.delegate = delegate2;
            this.inverse = inverse2;
        }

        /* access modifiers changed from: protected */
        public Map<K, V> delegate() {
            return this.unmodifiableMap;
        }

        public V forcePut(K k, V v) {
            throw new UnsupportedOperationException();
        }

        public BiMap<V, K> inverse() {
            BiMap<V, K> result = this.inverse;
            if (result != null) {
                return result;
            }
            UnmodifiableBiMap unmodifiableBiMap = new UnmodifiableBiMap(this.delegate.inverse(), this);
            this.inverse = unmodifiableBiMap;
            return unmodifiableBiMap;
        }

        public Set<V> values() {
            Set<V> result = this.values;
            if (result != null) {
                return result;
            }
            Set<V> unmodifiableSet = Collections.unmodifiableSet(this.delegate.values());
            this.values = unmodifiableSet;
            return unmodifiableSet;
        }
    }

    static class TransformedEntriesMap<K, V1, V2> extends IteratorBasedAbstractMap<K, V2> {
        final Map<K, V1> fromMap;
        final EntryTransformer<? super K, ? super V1, V2> transformer;

        TransformedEntriesMap(Map<K, V1> fromMap2, EntryTransformer<? super K, ? super V1, V2> transformer2) {
            this.fromMap = (Map) Preconditions.checkNotNull(fromMap2);
            this.transformer = (EntryTransformer) Preconditions.checkNotNull(transformer2);
        }

        public int size() {
            return this.fromMap.size();
        }

        public boolean containsKey(Object key) {
            return this.fromMap.containsKey(key);
        }

        public V2 get(Object key) {
            V1 value = this.fromMap.get(key);
            if (value != null || this.fromMap.containsKey(key)) {
                return this.transformer.transformEntry(key, value);
            }
            return null;
        }

        public V2 remove(Object key) {
            if (this.fromMap.containsKey(key)) {
                return this.transformer.transformEntry(key, this.fromMap.remove(key));
            }
            return null;
        }

        public void clear() {
            this.fromMap.clear();
        }

        public Set<K> keySet() {
            return this.fromMap.keySet();
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V2>> entryIterator() {
            return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        public Collection<V2> values() {
            return new Values(this);
        }
    }

    static class TransformedEntriesSortedMap<K, V1, V2> extends TransformedEntriesMap<K, V1, V2> implements SortedMap<K, V2> {
        TransformedEntriesSortedMap(SortedMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
            super(fromMap, transformer);
        }

        /* access modifiers changed from: protected */
        public SortedMap<K, V1> fromMap() {
            return (SortedMap) this.fromMap;
        }

        public Comparator<? super K> comparator() {
            return fromMap().comparator();
        }

        public K firstKey() {
            return fromMap().firstKey();
        }

        public SortedMap<K, V2> headMap(K toKey) {
            return Maps.transformEntries(fromMap().headMap(toKey), this.transformer);
        }

        public K lastKey() {
            return fromMap().lastKey();
        }

        public SortedMap<K, V2> subMap(K fromKey, K toKey) {
            return Maps.transformEntries(fromMap().subMap(fromKey, toKey), this.transformer);
        }

        public SortedMap<K, V2> tailMap(K fromKey) {
            return Maps.transformEntries(fromMap().tailMap(fromKey), this.transformer);
        }
    }

    @GwtIncompatible
    private static class TransformedEntriesNavigableMap<K, V1, V2> extends TransformedEntriesSortedMap<K, V1, V2> implements NavigableMap<K, V2> {
        TransformedEntriesNavigableMap(NavigableMap<K, V1> fromMap, EntryTransformer<? super K, ? super V1, V2> transformer) {
            super(fromMap, transformer);
        }

        public Map.Entry<K, V2> ceilingEntry(K key) {
            return transformEntry(fromMap().ceilingEntry(key));
        }

        public K ceilingKey(K key) {
            return fromMap().ceilingKey(key);
        }

        public NavigableSet<K> descendingKeySet() {
            return fromMap().descendingKeySet();
        }

        public NavigableMap<K, V2> descendingMap() {
            return Maps.transformEntries(fromMap().descendingMap(), this.transformer);
        }

        public Map.Entry<K, V2> firstEntry() {
            return transformEntry(fromMap().firstEntry());
        }

        public Map.Entry<K, V2> floorEntry(K key) {
            return transformEntry(fromMap().floorEntry(key));
        }

        public K floorKey(K key) {
            return fromMap().floorKey(key);
        }

        public NavigableMap<K, V2> headMap(K toKey) {
            return headMap(toKey, false);
        }

        public NavigableMap<K, V2> headMap(K toKey, boolean inclusive) {
            return Maps.transformEntries(fromMap().headMap(toKey, inclusive), this.transformer);
        }

        public Map.Entry<K, V2> higherEntry(K key) {
            return transformEntry(fromMap().higherEntry(key));
        }

        public K higherKey(K key) {
            return fromMap().higherKey(key);
        }

        public Map.Entry<K, V2> lastEntry() {
            return transformEntry(fromMap().lastEntry());
        }

        public Map.Entry<K, V2> lowerEntry(K key) {
            return transformEntry(fromMap().lowerEntry(key));
        }

        public K lowerKey(K key) {
            return fromMap().lowerKey(key);
        }

        public NavigableSet<K> navigableKeySet() {
            return fromMap().navigableKeySet();
        }

        public Map.Entry<K, V2> pollFirstEntry() {
            return transformEntry(fromMap().pollFirstEntry());
        }

        public Map.Entry<K, V2> pollLastEntry() {
            return transformEntry(fromMap().pollLastEntry());
        }

        public NavigableMap<K, V2> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return Maps.transformEntries(fromMap().subMap(fromKey, fromInclusive, toKey, toInclusive), this.transformer);
        }

        public NavigableMap<K, V2> subMap(K fromKey, K toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        public NavigableMap<K, V2> tailMap(K fromKey) {
            return tailMap(fromKey, true);
        }

        public NavigableMap<K, V2> tailMap(K fromKey, boolean inclusive) {
            return Maps.transformEntries(fromMap().tailMap(fromKey, inclusive), this.transformer);
        }

        @NullableDecl
        private Map.Entry<K, V2> transformEntry(@NullableDecl Map.Entry<K, V1> entry) {
            if (entry == null) {
                return null;
            }
            return Maps.transformEntry(this.transformer, entry);
        }

        /* access modifiers changed from: protected */
        public NavigableMap<K, V1> fromMap() {
            return (NavigableMap) super.fromMap();
        }
    }

    private static abstract class AbstractFilteredMap<K, V> extends ViewCachingAbstractMap<K, V> {
        final Predicate<? super Map.Entry<K, V>> predicate;
        final Map<K, V> unfiltered;

        AbstractFilteredMap(Map<K, V> unfiltered2, Predicate<? super Map.Entry<K, V>> predicate2) {
            this.unfiltered = unfiltered2;
            this.predicate = predicate2;
        }

        /* access modifiers changed from: package-private */
        public boolean apply(@NullableDecl Object key, @NullableDecl V value) {
            return this.predicate.apply(Maps.immutableEntry(key, value));
        }

        public V put(K key, V value) {
            Preconditions.checkArgument(apply(key, value));
            return this.unfiltered.put(key, value);
        }

        public void putAll(Map<? extends K, ? extends V> map) {
            for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                Preconditions.checkArgument(apply(entry.getKey(), entry.getValue()));
            }
            this.unfiltered.putAll(map);
        }

        public boolean containsKey(Object key) {
            return this.unfiltered.containsKey(key) && apply(key, this.unfiltered.get(key));
        }

        public V get(Object key) {
            V value = this.unfiltered.get(key);
            if (value == null || !apply(key, value)) {
                return null;
            }
            return value;
        }

        public boolean isEmpty() {
            return entrySet().isEmpty();
        }

        public V remove(Object key) {
            if (containsKey(key)) {
                return this.unfiltered.remove(key);
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public Collection<V> createValues() {
            return new FilteredMapValues(this, this.unfiltered, this.predicate);
        }
    }

    private static final class FilteredMapValues<K, V> extends Values<K, V> {
        final Predicate<? super Map.Entry<K, V>> predicate;
        final Map<K, V> unfiltered;

        FilteredMapValues(Map<K, V> filteredMap, Map<K, V> unfiltered2, Predicate<? super Map.Entry<K, V>> predicate2) {
            super(filteredMap);
            this.unfiltered = unfiltered2;
            this.predicate = predicate2;
        }

        public boolean remove(Object o) {
            Iterator<Map.Entry<K, V>> entryItr = this.unfiltered.entrySet().iterator();
            while (entryItr.hasNext()) {
                Map.Entry<K, V> entry = entryItr.next();
                if (this.predicate.apply(entry) && Objects.equal(entry.getValue(), o)) {
                    entryItr.remove();
                    return true;
                }
            }
            return false;
        }

        public boolean removeAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> entryItr = this.unfiltered.entrySet().iterator();
            boolean result = false;
            while (entryItr.hasNext()) {
                Map.Entry<K, V> entry = entryItr.next();
                if (this.predicate.apply(entry) && collection.contains(entry.getValue())) {
                    entryItr.remove();
                    result = true;
                }
            }
            return result;
        }

        public boolean retainAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> entryItr = this.unfiltered.entrySet().iterator();
            boolean result = false;
            while (entryItr.hasNext()) {
                Map.Entry<K, V> entry = entryItr.next();
                if (this.predicate.apply(entry) && !collection.contains(entry.getValue())) {
                    entryItr.remove();
                    result = true;
                }
            }
            return result;
        }

        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        public <T> T[] toArray(T[] array) {
            return Lists.newArrayList(iterator()).toArray(array);
        }
    }

    private static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V> {
        final Predicate<? super K> keyPredicate;

        FilteredKeyMap(Map<K, V> unfiltered, Predicate<? super K> keyPredicate2, Predicate<? super Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
            this.keyPredicate = keyPredicate2;
        }

        /* access modifiers changed from: protected */
        public Set<Map.Entry<K, V>> createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), this.predicate);
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
        }

        public boolean containsKey(Object key) {
            return this.unfiltered.containsKey(key) && this.keyPredicate.apply(key);
        }
    }

    static class FilteredEntryMap<K, V> extends AbstractFilteredMap<K, V> {
        final Set<Map.Entry<K, V>> filteredEntrySet;

        FilteredEntryMap(Map<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
            this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
        }

        static <K, V> boolean removeAllKeys(Map<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate, Collection<?> keyCollection) {
            Iterator<Map.Entry<K, V>> entryItr = map.entrySet().iterator();
            boolean result = false;
            while (entryItr.hasNext()) {
                Map.Entry<K, V> entry = entryItr.next();
                if (entryPredicate.apply(entry) && keyCollection.contains(entry.getKey())) {
                    entryItr.remove();
                    result = true;
                }
            }
            return result;
        }

        static <K, V> boolean retainAllKeys(Map<K, V> map, Predicate<? super Map.Entry<K, V>> entryPredicate, Collection<?> keyCollection) {
            Iterator<Map.Entry<K, V>> entryItr = map.entrySet().iterator();
            boolean result = false;
            while (entryItr.hasNext()) {
                Map.Entry<K, V> entry = entryItr.next();
                if (entryPredicate.apply(entry) && !keyCollection.contains(entry.getKey())) {
                    entryItr.remove();
                    result = true;
                }
            }
            return result;
        }

        /* access modifiers changed from: protected */
        public Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet();
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return new KeySet();
        }

        private class EntrySet extends ForwardingSet<Map.Entry<K, V>> {
            private EntrySet() {
            }

            /* access modifiers changed from: protected */
            public Set<Map.Entry<K, V>> delegate() {
                return FilteredEntryMap.this.filteredEntrySet;
            }

            public Iterator<Map.Entry<K, V>> iterator() {
                return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(FilteredEntryMap.this.filteredEntrySet.iterator()) {
                    /* access modifiers changed from: package-private */
                    public Map.Entry<K, V> transform(final Map.Entry<K, V> entry) {
                        return new ForwardingMapEntry<K, V>() {
                            /* access modifiers changed from: protected */
                            public Map.Entry<K, V> delegate() {
                                return entry;
                            }

                            public V setValue(V newValue) {
                                Preconditions.checkArgument(FilteredEntryMap.this.apply(getKey(), newValue));
                                return super.setValue(newValue);
                            }
                        };
                    }
                };
            }
        }

        class KeySet extends KeySet<K, V> {
            KeySet() {
                super(FilteredEntryMap.this);
            }

            public boolean remove(Object o) {
                if (!FilteredEntryMap.this.containsKey(o)) {
                    return false;
                }
                FilteredEntryMap.this.unfiltered.remove(o);
                return true;
            }

            public boolean removeAll(Collection<?> collection) {
                return FilteredEntryMap.removeAllKeys(FilteredEntryMap.this.unfiltered, FilteredEntryMap.this.predicate, collection);
            }

            public boolean retainAll(Collection<?> collection) {
                return FilteredEntryMap.retainAllKeys(FilteredEntryMap.this.unfiltered, FilteredEntryMap.this.predicate, collection);
            }

            public Object[] toArray() {
                return Lists.newArrayList(iterator()).toArray();
            }

            public <T> T[] toArray(T[] array) {
                return Lists.newArrayList(iterator()).toArray(array);
            }
        }
    }

    private static class FilteredEntrySortedMap<K, V> extends FilteredEntryMap<K, V> implements SortedMap<K, V> {
        FilteredEntrySortedMap(SortedMap<K, V> unfiltered, Predicate<? super Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
        }

        /* access modifiers changed from: package-private */
        public SortedMap<K, V> sortedMap() {
            return (SortedMap) this.unfiltered;
        }

        public SortedSet<K> keySet() {
            return (SortedSet) super.keySet();
        }

        /* access modifiers changed from: package-private */
        public SortedSet<K> createKeySet() {
            return new SortedKeySet();
        }

        public Comparator<? super K> comparator() {
            return sortedMap().comparator();
        }

        public K firstKey() {
            return keySet().iterator().next();
        }

        public K lastKey() {
            SortedMap<K, V> headMap = sortedMap();
            while (true) {
                K key = headMap.lastKey();
                if (apply(key, this.unfiltered.get(key))) {
                    return key;
                }
                headMap = sortedMap().headMap(key);
            }
        }

        public SortedMap<K, V> headMap(K toKey) {
            return new FilteredEntrySortedMap(sortedMap().headMap(toKey), this.predicate);
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return new FilteredEntrySortedMap(sortedMap().subMap(fromKey, toKey), this.predicate);
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            return new FilteredEntrySortedMap(sortedMap().tailMap(fromKey), this.predicate);
        }

        class SortedKeySet extends FilteredEntryMap<K, V>.KeySet implements SortedSet<K> {
            SortedKeySet() {
                super();
            }

            public Comparator<? super K> comparator() {
                return FilteredEntrySortedMap.this.sortedMap().comparator();
            }

            public SortedSet<K> subSet(K fromElement, K toElement) {
                return (SortedSet) FilteredEntrySortedMap.this.subMap(fromElement, toElement).keySet();
            }

            public SortedSet<K> headSet(K toElement) {
                return (SortedSet) FilteredEntrySortedMap.this.headMap(toElement).keySet();
            }

            public SortedSet<K> tailSet(K fromElement) {
                return (SortedSet) FilteredEntrySortedMap.this.tailMap(fromElement).keySet();
            }

            public K first() {
                return FilteredEntrySortedMap.this.firstKey();
            }

            public K last() {
                return FilteredEntrySortedMap.this.lastKey();
            }
        }
    }

    @GwtIncompatible
    private static class FilteredEntryNavigableMap<K, V> extends AbstractNavigableMap<K, V> {
        /* access modifiers changed from: private */
        public final Predicate<? super Map.Entry<K, V>> entryPredicate;
        /* access modifiers changed from: private */
        public final NavigableMap<K, V> unfiltered;
        private final Map<K, V> filteredDelegate;

        FilteredEntryNavigableMap(NavigableMap<K, V> unfiltered2, Predicate<? super Map.Entry<K, V>> entryPredicate2) {
            this.unfiltered = (NavigableMap) Preconditions.checkNotNull(unfiltered2);
            this.entryPredicate = entryPredicate2;
            this.filteredDelegate = new FilteredEntryMap(unfiltered2, entryPredicate2);
        }

        public Comparator<? super K> comparator() {
            return this.unfiltered.comparator();
        }

        public NavigableSet<K> navigableKeySet() {
            return new NavigableKeySet<K, V>(this) {
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMap.removeAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, collection);
                }

                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMap.retainAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, collection);
                }
            };
        }

        public Collection<V> values() {
            return new FilteredMapValues(this, this.unfiltered, this.entryPredicate);
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> entryIterator() {
            return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
        }

        public int size() {
            return this.filteredDelegate.size();
        }

        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered.entrySet(), this.entryPredicate);
        }

        @NullableDecl
        public V get(@NullableDecl Object key) {
            return this.filteredDelegate.get(key);
        }

        public boolean containsKey(@NullableDecl Object key) {
            return this.filteredDelegate.containsKey(key);
        }

        public V put(K key, V value) {
            return this.filteredDelegate.put(key, value);
        }

        public V remove(@NullableDecl Object key) {
            return this.filteredDelegate.remove(key);
        }

        public void putAll(Map<? extends K, ? extends V> m) {
            this.filteredDelegate.putAll(m);
        }

        public void clear() {
            this.filteredDelegate.clear();
        }

        public Set<Map.Entry<K, V>> entrySet() {
            return this.filteredDelegate.entrySet();
        }

        public Map.Entry<K, V> pollFirstEntry() {
            return (Map.Entry) Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
        }

        public Map.Entry<K, V> pollLastEntry() {
            return (Map.Entry) Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableMap<K, V>, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
         candidates:
          com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries((NavigableMap) this.unfiltered.descendingMap(), (Predicate) this.entryPredicate);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableMap<K, V>, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
         candidates:
          com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return Maps.filterEntries((NavigableMap) this.unfiltered.subMap(fromKey, fromInclusive, toKey, toInclusive), (Predicate) this.entryPredicate);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableMap<K, V>, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
         candidates:
          com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return Maps.filterEntries((NavigableMap) this.unfiltered.headMap(toKey, inclusive), (Predicate) this.entryPredicate);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V>
         arg types: [java.util.NavigableMap<K, V>, com.google.common.base.Predicate<? super java.util.Map$Entry<K, V>>]
         candidates:
          com.google.common.collect.Maps.filterEntries(com.google.common.collect.BiMap, com.google.common.base.Predicate):com.google.common.collect.BiMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.Map, com.google.common.base.Predicate):java.util.Map<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.SortedMap, com.google.common.base.Predicate):java.util.SortedMap<K, V>
          com.google.common.collect.Maps.filterEntries(java.util.NavigableMap, com.google.common.base.Predicate):java.util.NavigableMap<K, V> */
        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return Maps.filterEntries((NavigableMap) this.unfiltered.tailMap(fromKey, inclusive), (Predicate) this.entryPredicate);
        }
    }

    static final class FilteredEntryBiMap<K, V> extends FilteredEntryMap<K, V> implements BiMap<K, V> {
        @RetainedWith
        private final BiMap<V, K> inverse;

        FilteredEntryBiMap(BiMap<K, V> delegate, Predicate<? super Map.Entry<K, V>> predicate) {
            super(delegate, predicate);
            this.inverse = new FilteredEntryBiMap(delegate.inverse(), inversePredicate(predicate), this);
        }

        private FilteredEntryBiMap(BiMap<K, V> delegate, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> inverse2) {
            super(delegate, predicate);
            this.inverse = inverse2;
        }

        private static <K, V> Predicate<Map.Entry<V, K>> inversePredicate(final Predicate<? super Map.Entry<K, V>> forwardPredicate) {
            return new Predicate<Map.Entry<V, K>>() {
                public boolean apply(Map.Entry<V, K> input) {
                    return Predicate.this.apply(Maps.immutableEntry(input.getValue(), input.getKey()));
                }
            };
        }

        /* access modifiers changed from: package-private */
        public BiMap<K, V> unfiltered() {
            return (BiMap) this.unfiltered;
        }

        public V forcePut(@NullableDecl K key, @NullableDecl V value) {
            Preconditions.checkArgument(apply(key, value));
            return unfiltered().forcePut(key, value);
        }

        public BiMap<V, K> inverse() {
            return this.inverse;
        }

        public Set<V> values() {
            return this.inverse.keySet();
        }
    }

    @GwtIncompatible
    static class UnmodifiableNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V>, Serializable {
        private final NavigableMap<K, ? extends V> delegate;
        @MonotonicNonNullDecl
        private transient UnmodifiableNavigableMap<K, V> descendingMap;

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> delegate2) {
            this.delegate = delegate2;
        }

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> delegate2, UnmodifiableNavigableMap<K, V> descendingMap2) {
            this.delegate = delegate2;
            this.descendingMap = descendingMap2;
        }

        /* access modifiers changed from: protected */
        public SortedMap<K, V> delegate() {
            return Collections.unmodifiableSortedMap(this.delegate);
        }

        public Map.Entry<K, V> lowerEntry(K key) {
            return Maps.unmodifiableOrNull(this.delegate.lowerEntry(key));
        }

        public K lowerKey(K key) {
            return this.delegate.lowerKey(key);
        }

        public Map.Entry<K, V> floorEntry(K key) {
            return Maps.unmodifiableOrNull(this.delegate.floorEntry(key));
        }

        public K floorKey(K key) {
            return this.delegate.floorKey(key);
        }

        public Map.Entry<K, V> ceilingEntry(K key) {
            return Maps.unmodifiableOrNull(this.delegate.ceilingEntry(key));
        }

        public K ceilingKey(K key) {
            return this.delegate.ceilingKey(key);
        }

        public Map.Entry<K, V> higherEntry(K key) {
            return Maps.unmodifiableOrNull(this.delegate.higherEntry(key));
        }

        public K higherKey(K key) {
            return this.delegate.higherKey(key);
        }

        public Map.Entry<K, V> firstEntry() {
            return Maps.unmodifiableOrNull(this.delegate.firstEntry());
        }

        public Map.Entry<K, V> lastEntry() {
            return Maps.unmodifiableOrNull(this.delegate.lastEntry());
        }

        public final Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        public final Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        public NavigableMap<K, V> descendingMap() {
            UnmodifiableNavigableMap<K, V> result = this.descendingMap;
            if (result != null) {
                return result;
            }
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap = new UnmodifiableNavigableMap<>(this.delegate.descendingMap(), this);
            this.descendingMap = unmodifiableNavigableMap;
            return unmodifiableNavigableMap;
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
        }

        public NavigableSet<K> descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return Maps.unmodifiableNavigableMap(this.delegate.subMap(fromKey, fromInclusive, toKey, toInclusive));
        }

        public SortedMap<K, V> headMap(K toKey) {
            return headMap(toKey, false);
        }

        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return Maps.unmodifiableNavigableMap(this.delegate.headMap(toKey, inclusive));
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            return tailMap(fromKey, true);
        }

        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return Maps.unmodifiableNavigableMap(this.delegate.tailMap(fromKey, inclusive));
        }
    }

    @GwtCompatible
    static abstract class ViewCachingAbstractMap<K, V> extends AbstractMap<K, V> {
        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> entrySet;
        @MonotonicNonNullDecl
        private transient Set<K> keySet;
        @MonotonicNonNullDecl
        private transient Collection<V> values;

        ViewCachingAbstractMap() {
        }

        /* access modifiers changed from: package-private */
        public abstract Set<Map.Entry<K, V>> createEntrySet();

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> result = this.entrySet;
            if (result != null) {
                return result;
            }
            Set<Map.Entry<K, V>> createEntrySet = createEntrySet();
            this.entrySet = createEntrySet;
            return createEntrySet;
        }

        public Set<K> keySet() {
            Set<K> result = this.keySet;
            if (result != null) {
                return result;
            }
            Set<K> createKeySet = createKeySet();
            this.keySet = createKeySet;
            return createKeySet;
        }

        /* access modifiers changed from: package-private */
        public Set<K> createKeySet() {
            return new KeySet(this);
        }

        public Collection<V> values() {
            Collection<V> result = this.values;
            if (result != null) {
                return result;
            }
            Collection<V> createValues = createValues();
            this.values = createValues;
            return createValues;
        }

        /* access modifiers changed from: package-private */
        public Collection<V> createValues() {
            return new Values(this);
        }
    }

    static abstract class IteratorBasedAbstractMap<K, V> extends AbstractMap<K, V> {
        IteratorBasedAbstractMap() {
        }

        /* access modifiers changed from: package-private */
        public abstract Iterator<Map.Entry<K, V>> entryIterator();

        public abstract int size();

        public Set<Map.Entry<K, V>> entrySet() {
            return new EntrySet<K, V>() {
                /* access modifiers changed from: package-private */
                public Map<K, V> map() {
                    return IteratorBasedAbstractMap.this;
                }

                public Iterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedAbstractMap.this.entryIterator();
                }
            };
        }

        public void clear() {
            Iterators.clear(entryIterator());
        }
    }

    static class KeySet<K, V> extends Sets.ImprovedAbstractSet<K> {
        @Weak
        final Map<K, V> map;

        KeySet(Map<K, V> map2) {
            this.map = (Map) Preconditions.checkNotNull(map2);
        }

        /* access modifiers changed from: package-private */
        public Map<K, V> map() {
            return this.map;
        }

        public Iterator<K> iterator() {
            return Maps.keyIterator(map().entrySet().iterator());
        }

        public int size() {
            return map().size();
        }

        public boolean isEmpty() {
            return map().isEmpty();
        }

        public boolean contains(Object o) {
            return map().containsKey(o);
        }

        public boolean remove(Object o) {
            if (!contains(o)) {
                return false;
            }
            map().remove(o);
            return true;
        }

        public void clear() {
            map().clear();
        }
    }

    static class SortedKeySet<K, V> extends KeySet<K, V> implements SortedSet<K> {
        SortedKeySet(SortedMap<K, V> map) {
            super(map);
        }

        /* access modifiers changed from: package-private */
        public SortedMap<K, V> map() {
            return (SortedMap) super.map();
        }

        public Comparator<? super K> comparator() {
            return map().comparator();
        }

        public SortedSet<K> subSet(K fromElement, K toElement) {
            return new SortedKeySet(map().subMap(fromElement, toElement));
        }

        public SortedSet<K> headSet(K toElement) {
            return new SortedKeySet(map().headMap(toElement));
        }

        public SortedSet<K> tailSet(K fromElement) {
            return new SortedKeySet(map().tailMap(fromElement));
        }

        public K first() {
            return map().firstKey();
        }

        public K last() {
            return map().lastKey();
        }
    }

    @GwtIncompatible
    static class NavigableKeySet<K, V> extends SortedKeySet<K, V> implements NavigableSet<K> {
        NavigableKeySet(NavigableMap<K, V> map) {
            super(map);
        }

        /* access modifiers changed from: package-private */
        public NavigableMap<K, V> map() {
            return (NavigableMap) this.map;
        }

        public K lower(K e) {
            return map().lowerKey(e);
        }

        public K floor(K e) {
            return map().floorKey(e);
        }

        public K ceiling(K e) {
            return map().ceilingKey(e);
        }

        public K higher(K e) {
            return map().higherKey(e);
        }

        public K pollFirst() {
            return Maps.keyOrNull(map().pollFirstEntry());
        }

        public K pollLast() {
            return Maps.keyOrNull(map().pollLastEntry());
        }

        public NavigableSet<K> descendingSet() {
            return map().descendingKeySet();
        }

        public Iterator<K> descendingIterator() {
            return descendingSet().iterator();
        }

        public NavigableSet<K> subSet(K fromElement, boolean fromInclusive, K toElement, boolean toInclusive) {
            return map().subMap(fromElement, fromInclusive, toElement, toInclusive).navigableKeySet();
        }

        public SortedSet<K> subSet(K fromElement, K toElement) {
            return subSet(fromElement, true, toElement, false);
        }

        public NavigableSet<K> headSet(K toElement, boolean inclusive) {
            return map().headMap(toElement, inclusive).navigableKeySet();
        }

        public SortedSet<K> headSet(K toElement) {
            return headSet(toElement, false);
        }

        public NavigableSet<K> tailSet(K fromElement, boolean inclusive) {
            return map().tailMap(fromElement, inclusive).navigableKeySet();
        }

        public SortedSet<K> tailSet(K fromElement) {
            return tailSet(fromElement, true);
        }
    }

    static class Values<K, V> extends AbstractCollection<V> {
        @Weak
        final Map<K, V> map;

        Values(Map<K, V> map2) {
            this.map = (Map) Preconditions.checkNotNull(map2);
        }

        /* access modifiers changed from: package-private */
        public final Map<K, V> map() {
            return this.map;
        }

        public Iterator<V> iterator() {
            return Maps.valueIterator(map().entrySet().iterator());
        }

        public boolean remove(Object o) {
            try {
                return super.remove(o);
            } catch (UnsupportedOperationException e) {
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (Objects.equal(o, entry.getValue())) {
                        map().remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }

        public boolean removeAll(Collection<?> c) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                Set<K> toRemove = Sets.newHashSet();
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (c.contains(entry.getValue())) {
                        toRemove.add(entry.getKey());
                    }
                }
                return map().keySet().removeAll(toRemove);
            }
        }

        public boolean retainAll(Collection<?> c) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                Set<K> toRetain = Sets.newHashSet();
                for (Map.Entry<K, V> entry : map().entrySet()) {
                    if (c.contains(entry.getValue())) {
                        toRetain.add(entry.getKey());
                    }
                }
                return map().keySet().retainAll(toRetain);
            }
        }

        public int size() {
            return map().size();
        }

        public boolean isEmpty() {
            return map().isEmpty();
        }

        public boolean contains(@NullableDecl Object o) {
            return map().containsValue(o);
        }

        public void clear() {
            map().clear();
        }
    }

    static abstract class EntrySet<K, V> extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        /* access modifiers changed from: package-private */
        public abstract Map<K, V> map();

        public int size() {
            return map().size();
        }

        public void clear() {
            map().clear();
        }

        public boolean contains(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            Map.Entry<?, ?> entry = (Map.Entry) o;
            Object key = entry.getKey();
            V value = Maps.safeGet(map(), key);
            if (!Objects.equal(value, entry.getValue())) {
                return false;
            }
            if (value != null || map().containsKey(key)) {
                return true;
            }
            return false;
        }

        public boolean isEmpty() {
            return map().isEmpty();
        }

        public boolean remove(Object o) {
            if (contains(o)) {
                return map().keySet().remove(((Map.Entry) o).getKey());
            }
            return false;
        }

        public boolean removeAll(Collection<?> c) {
            try {
                return super.removeAll((Collection) Preconditions.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                return Sets.removeAllImpl(this, c.iterator());
            }
        }

        public boolean retainAll(Collection<?> c) {
            try {
                return super.retainAll((Collection) Preconditions.checkNotNull(c));
            } catch (UnsupportedOperationException e) {
                Set<Object> keys = Sets.newHashSetWithExpectedSize(c.size());
                for (Object next : c) {
                    if (contains(next)) {
                        keys.add(((Map.Entry) next).getKey());
                    }
                }
                return map().keySet().retainAll(keys);
            }
        }
    }

    @GwtIncompatible
    static abstract class DescendingMap<K, V> extends ForwardingMap<K, V> implements NavigableMap<K, V> {
        @MonotonicNonNullDecl
        private transient Comparator<? super K> comparator;
        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> entrySet;
        @MonotonicNonNullDecl
        private transient NavigableSet<K> navigableKeySet;

        DescendingMap() {
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [java.util.Comparator<T>, java.util.Comparator] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static <T> com.google.common.collect.Ordering<T> reverse(java.util.Comparator<T> r1) {
            /*
                com.google.common.collect.Ordering r0 = com.google.common.collect.Ordering.from(r1)
                com.google.common.collect.Ordering r0 = r0.reverse()
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Maps.DescendingMap.reverse(java.util.Comparator):com.google.common.collect.Ordering");
        }

        /* access modifiers changed from: package-private */
        public abstract Iterator<Map.Entry<K, V>> entryIterator();

        /* access modifiers changed from: package-private */
        public abstract NavigableMap<K, V> forward();

        /* access modifiers changed from: protected */
        public final Map<K, V> delegate() {
            return forward();
        }

        public Comparator<? super K> comparator() {
            Comparator<? super K> result = this.comparator;
            if (result != null) {
                return result;
            }
            Comparator<? super K> forwardCmp = forward().comparator();
            if (forwardCmp == null) {
                forwardCmp = Ordering.natural();
            }
            Comparator<? super K> result2 = reverse(forwardCmp);
            this.comparator = result2;
            return result2;
        }

        public K firstKey() {
            return forward().lastKey();
        }

        public K lastKey() {
            return forward().firstKey();
        }

        public Map.Entry<K, V> lowerEntry(K key) {
            return forward().higherEntry(key);
        }

        public K lowerKey(K key) {
            return forward().higherKey(key);
        }

        public Map.Entry<K, V> floorEntry(K key) {
            return forward().ceilingEntry(key);
        }

        public K floorKey(K key) {
            return forward().ceilingKey(key);
        }

        public Map.Entry<K, V> ceilingEntry(K key) {
            return forward().floorEntry(key);
        }

        public K ceilingKey(K key) {
            return forward().floorKey(key);
        }

        public Map.Entry<K, V> higherEntry(K key) {
            return forward().lowerEntry(key);
        }

        public K higherKey(K key) {
            return forward().lowerKey(key);
        }

        public Map.Entry<K, V> firstEntry() {
            return forward().lastEntry();
        }

        public Map.Entry<K, V> lastEntry() {
            return forward().firstEntry();
        }

        public Map.Entry<K, V> pollFirstEntry() {
            return forward().pollLastEntry();
        }

        public Map.Entry<K, V> pollLastEntry() {
            return forward().pollFirstEntry();
        }

        public NavigableMap<K, V> descendingMap() {
            return forward();
        }

        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> result = this.entrySet;
            if (result != null) {
                return result;
            }
            Set<Map.Entry<K, V>> createEntrySet = createEntrySet();
            this.entrySet = createEntrySet;
            return createEntrySet;
        }

        /* access modifiers changed from: package-private */
        public Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet<K, V>() {
                /* access modifiers changed from: package-private */
                public Map<K, V> map() {
                    return DescendingMap.this;
                }

                public Iterator<Map.Entry<K, V>> iterator() {
                    return DescendingMap.this.entryIterator();
                }
            };
        }

        public Set<K> keySet() {
            return navigableKeySet();
        }

        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> result = this.navigableKeySet;
            if (result != null) {
                return result;
            }
            NavigableKeySet navigableKeySet2 = new NavigableKeySet(this);
            this.navigableKeySet = navigableKeySet2;
            return navigableKeySet2;
        }

        public NavigableSet<K> descendingKeySet() {
            return forward().navigableKeySet();
        }

        public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
            return forward().subMap(toKey, toInclusive, fromKey, fromInclusive).descendingMap();
        }

        public SortedMap<K, V> subMap(K fromKey, K toKey) {
            return subMap(fromKey, true, toKey, false);
        }

        public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
            return forward().tailMap(toKey, inclusive).descendingMap();
        }

        public SortedMap<K, V> headMap(K toKey) {
            return headMap(toKey, false);
        }

        public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
            return forward().headMap(fromKey, inclusive).descendingMap();
        }

        public SortedMap<K, V> tailMap(K fromKey) {
            return tailMap(fromKey, true);
        }

        public Collection<V> values() {
            return new Values(this);
        }

        public String toString() {
            return standardToString();
        }
    }
}
