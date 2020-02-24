package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Maps;
import java.lang.Comparable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public final class TreeRangeMap<K extends Comparable, V> implements RangeMap<K, V> {
    private static final RangeMap EMPTY_SUB_RANGE_MAP = new RangeMap() {
        @NullableDecl
        public Object get(Comparable key) {
            return null;
        }

        @NullableDecl
        public Map.Entry<Range, Object> getEntry(Comparable key) {
            return null;
        }

        public Range span() {
            throw new NoSuchElementException();
        }

        public void put(Range range, Object value) {
            Preconditions.checkNotNull(range);
            String valueOf = String.valueOf(range);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
            sb.append("Cannot insert range ");
            sb.append(valueOf);
            sb.append(" into an empty subRangeMap");
            throw new IllegalArgumentException(sb.toString());
        }

        public void putCoalescing(Range range, Object value) {
            Preconditions.checkNotNull(range);
            String valueOf = String.valueOf(range);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
            sb.append("Cannot insert range ");
            sb.append(valueOf);
            sb.append(" into an empty subRangeMap");
            throw new IllegalArgumentException(sb.toString());
        }

        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
            }
        }

        public void clear() {
        }

        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };
    /* access modifiers changed from: private */
    public final NavigableMap<Cut<K>, RangeMapEntry<K, V>> entriesByLowerBound = Maps.newTreeMap();

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<>();
    }

    private TreeRangeMap() {
    }

    private static final class RangeMapEntry<K extends Comparable, V> extends AbstractMapEntry<Range<K>, V> {
        private final Range<K> range;
        private final V value;

        RangeMapEntry(Cut<K> lowerBound, Cut<K> upperBound, V value2) {
            this(Range.create(lowerBound, upperBound), value2);
        }

        RangeMapEntry(Range<K> range2, V value2) {
            this.range = range2;
            this.value = value2;
        }

        public Range<K> getKey() {
            return this.range;
        }

        public V getValue() {
            return this.value;
        }

        public boolean contains(K value2) {
            return this.range.contains(value2);
        }

        /* access modifiers changed from: package-private */
        public Cut<K> getLowerBound() {
            return this.range.lowerBound;
        }

        /* access modifiers changed from: package-private */
        public Cut<K> getUpperBound() {
            return this.range.upperBound;
        }
    }

    @NullableDecl
    public V get(K key) {
        Map.Entry<Range<K>, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getValue();
    }

    @NullableDecl
    public Map.Entry<Range<K>, V> getEntry(K key) {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> mapEntry = this.entriesByLowerBound.floorEntry(Cut.belowValue(key));
        if (mapEntry == null || !mapEntry.getValue().contains(key)) {
            return null;
        }
        return mapEntry.getValue();
    }

    public void put(Range<K> range, V value) {
        if (!range.isEmpty()) {
            Preconditions.checkNotNull(value);
            remove(range);
            this.entriesByLowerBound.put(range.lowerBound, new RangeMapEntry(range, value));
        }
    }

    public void putCoalescing(Range<K> range, V value) {
        if (this.entriesByLowerBound.isEmpty()) {
            put(range, value);
        } else {
            put(coalescedRange(range, Preconditions.checkNotNull(value)), value);
        }
    }

    /* access modifiers changed from: private */
    public Range<K> coalescedRange(Range<K> range, V value) {
        return coalesce(coalesce(range, value, this.entriesByLowerBound.lowerEntry(range.lowerBound)), value, this.entriesByLowerBound.floorEntry(range.upperBound));
    }

    private static <K extends Comparable, V> Range<K> coalesce(Range<K> range, V value, @NullableDecl Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry) {
        if (entry == null || !entry.getValue().getKey().isConnected(range) || !entry.getValue().getValue().equals(value)) {
            return range;
        }
        return range.span(entry.getValue().getKey());
    }

    public void putAll(RangeMap<K, V> rangeMap) {
        for (Map.Entry<Range<K>, V> entry : rangeMap.asMapOfRanges().entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        this.entriesByLowerBound.clear();
    }

    public Range<K> span() {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> firstEntry = this.entriesByLowerBound.firstEntry();
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> lastEntry = this.entriesByLowerBound.lastEntry();
        if (firstEntry != null) {
            return Range.create(firstEntry.getValue().getKey().lowerBound, lastEntry.getValue().getKey().upperBound);
        }
        throw new NoSuchElementException();
    }

    private void putRangeMapEntry(Cut<K> lowerBound, Cut<K> upperBound, V value) {
        this.entriesByLowerBound.put(lowerBound, new RangeMapEntry(lowerBound, upperBound, value));
    }

    public void remove(Range<K> rangeToRemove) {
        if (!rangeToRemove.isEmpty()) {
            Map.Entry<Cut<K>, RangeMapEntry<K, V>> mapEntryBelowToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.lowerBound);
            if (mapEntryBelowToTruncate != null) {
                RangeMapEntry<K, V> rangeMapEntry = mapEntryBelowToTruncate.getValue();
                if (rangeMapEntry.getUpperBound().compareTo((Cut) rangeToRemove.lowerBound) > 0) {
                    if (rangeMapEntry.getUpperBound().compareTo((Cut) rangeToRemove.upperBound) > 0) {
                        putRangeMapEntry(rangeToRemove.upperBound, rangeMapEntry.getUpperBound(), mapEntryBelowToTruncate.getValue().getValue());
                    }
                    putRangeMapEntry(rangeMapEntry.getLowerBound(), rangeToRemove.lowerBound, mapEntryBelowToTruncate.getValue().getValue());
                }
            }
            Map.Entry<Cut<K>, RangeMapEntry<K, V>> mapEntryAboveToTruncate = this.entriesByLowerBound.lowerEntry(rangeToRemove.upperBound);
            if (mapEntryAboveToTruncate != null) {
                RangeMapEntry<K, V> rangeMapEntry2 = mapEntryAboveToTruncate.getValue();
                if (rangeMapEntry2.getUpperBound().compareTo((Cut) rangeToRemove.upperBound) > 0) {
                    putRangeMapEntry(rangeToRemove.upperBound, rangeMapEntry2.getUpperBound(), mapEntryAboveToTruncate.getValue().getValue());
                }
            }
            this.entriesByLowerBound.subMap(rangeToRemove.lowerBound, rangeToRemove.upperBound).clear();
        }
    }

    public Map<Range<K>, V> asMapOfRanges() {
        return new AsMapOfRanges(this.entriesByLowerBound.values());
    }

    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new AsMapOfRanges(this.entriesByLowerBound.descendingMap().values());
    }

    private final class AsMapOfRanges extends Maps.IteratorBasedAbstractMap<Range<K>, V> {
        final Iterable<Map.Entry<Range<K>, V>> entryIterable;

        AsMapOfRanges(Iterable<RangeMapEntry<K, V>> entryIterable2) {
            this.entryIterable = entryIterable2;
        }

        public boolean containsKey(@NullableDecl Object key) {
            return get(key) != null;
        }

        public V get(@NullableDecl Object key) {
            if (!(key instanceof Range)) {
                return null;
            }
            Range<?> range = (Range) key;
            RangeMapEntry<K, V> rangeMapEntry = (RangeMapEntry) TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
            if (rangeMapEntry == null || !rangeMapEntry.getKey().equals(range)) {
                return null;
            }
            return rangeMapEntry.getValue();
        }

        public int size() {
            return TreeRangeMap.this.entriesByLowerBound.size();
        }

        /* access modifiers changed from: package-private */
        public Iterator<Map.Entry<Range<K>, V>> entryIterator() {
            return this.entryIterable.iterator();
        }
    }

    public RangeMap<K, V> subRangeMap(Range<K> subRange) {
        if (subRange.equals(Range.all())) {
            return this;
        }
        return new SubRangeMap(subRange);
    }

    /* access modifiers changed from: private */
    public RangeMap<K, V> emptySubRangeMap() {
        return EMPTY_SUB_RANGE_MAP;
    }

    private class SubRangeMap implements RangeMap<K, V> {
        /* access modifiers changed from: private */
        public final Range<K> subRange;

        SubRangeMap(Range<K> subRange2) {
            this.subRange = subRange2;
        }

        @NullableDecl
        public V get(K key) {
            if (this.subRange.contains(key)) {
                return TreeRangeMap.this.get(key);
            }
            return null;
        }

        @NullableDecl
        public Map.Entry<Range<K>, V> getEntry(K key) {
            Map.Entry<Range<K>, V> entry;
            if (!this.subRange.contains(key) || (entry = TreeRangeMap.this.getEntry(key)) == null) {
                return null;
            }
            return Maps.immutableEntry(entry.getKey().intersection(this.subRange), entry.getValue());
        }

        public Range<K> span() {
            Cut cut;
            Object obj;
            Map.Entry<Cut<K>, RangeMapEntry<K, V>> lowerEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(this.subRange.lowerBound);
            if (lowerEntry == null || lowerEntry.getValue().getUpperBound().compareTo((Cut) this.subRange.lowerBound) <= 0) {
                cut = (Cut) TreeRangeMap.this.entriesByLowerBound.ceilingKey(this.subRange.lowerBound);
                if (cut == null || cut.compareTo((Cut) this.subRange.upperBound) >= 0) {
                    throw new NoSuchElementException();
                }
            } else {
                cut = this.subRange.lowerBound;
            }
            Map.Entry<Cut<K>, RangeMapEntry<K, V>> upperEntry = TreeRangeMap.this.entriesByLowerBound.lowerEntry(this.subRange.upperBound);
            if (upperEntry != null) {
                if (upperEntry.getValue().getUpperBound().compareTo((Cut) this.subRange.upperBound) >= 0) {
                    obj = this.subRange.upperBound;
                } else {
                    obj = upperEntry.getValue().getUpperBound();
                }
                return Range.create(cut, obj);
            }
            throw new NoSuchElementException();
        }

        public void put(Range<K> range, V value) {
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
            TreeRangeMap.this.put(range, value);
        }

        public void putCoalescing(Range<K> range, V value) {
            if (TreeRangeMap.this.entriesByLowerBound.isEmpty() || range.isEmpty() || !this.subRange.encloses(range)) {
                put(range, value);
            } else {
                put(TreeRangeMap.this.coalescedRange(range, Preconditions.checkNotNull(value)).intersection(this.subRange), value);
            }
        }

        public void putAll(RangeMap<K, V> rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) {
                Range<K> span = rangeMap.span();
                Preconditions.checkArgument(this.subRange.encloses(span), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", span, this.subRange);
                TreeRangeMap.this.putAll(rangeMap);
            }
        }

        public void clear() {
            TreeRangeMap.this.remove(this.subRange);
        }

        public void remove(Range<K> range) {
            if (range.isConnected(this.subRange)) {
                TreeRangeMap.this.remove(range.intersection(this.subRange));
            }
        }

        public RangeMap<K, V> subRangeMap(Range<K> range) {
            if (!range.isConnected(this.subRange)) {
                return TreeRangeMap.this.emptySubRangeMap();
            }
            return TreeRangeMap.this.subRangeMap(range.intersection(this.subRange));
        }

        public Map<Range<K>, V> asMapOfRanges() {
            return new SubRangeMapAsMap();
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [java.util.Map<com.google.common.collect.Range<K>, V>, com.google.common.collect.TreeRangeMap$SubRangeMap$1] */
        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new TreeRangeMap<K, V>.SubRangeMap.SubRangeMapAsMap() {
                /* access modifiers changed from: package-private */
                public Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                    if (SubRangeMap.this.subRange.isEmpty()) {
                        return Iterators.emptyIterator();
                    }
                    final Iterator<RangeMapEntry<K, V>> backingItr = TreeRangeMap.this.entriesByLowerBound.headMap(SubRangeMap.this.subRange.upperBound, false).descendingMap().values().iterator();
                    return new AbstractIterator<Map.Entry<Range<K>, V>>() {
                        /* access modifiers changed from: protected */
                        public Map.Entry<Range<K>, V> computeNext() {
                            if (!backingItr.hasNext()) {
                                return (Map.Entry) endOfData();
                            }
                            RangeMapEntry<K, V> entry = (RangeMapEntry) backingItr.next();
                            if (entry.getUpperBound().compareTo((Cut) SubRangeMap.this.subRange.lowerBound) <= 0) {
                                return (Map.Entry) endOfData();
                            }
                            return Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.this.subRange), entry.getValue());
                        }
                    };
                }
            };
        }

        public boolean equals(@NullableDecl Object o) {
            if (o instanceof RangeMap) {
                return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
            }
            return false;
        }

        public int hashCode() {
            return asMapOfRanges().hashCode();
        }

        public String toString() {
            return asMapOfRanges().toString();
        }

        class SubRangeMapAsMap extends AbstractMap<Range<K>, V> {
            SubRangeMapAsMap() {
            }

            public boolean containsKey(Object key) {
                return get(key) != null;
            }

            public V get(Object key) {
                try {
                    if (key instanceof Range) {
                        Range<K> r = (Range) key;
                        if (SubRangeMap.this.subRange.encloses(r)) {
                            if (!r.isEmpty()) {
                                RangeMapEntry<K, V> candidate = null;
                                if (r.lowerBound.compareTo((Cut) SubRangeMap.this.subRange.lowerBound) == 0) {
                                    Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry = TreeRangeMap.this.entriesByLowerBound.floorEntry(r.lowerBound);
                                    if (entry != null) {
                                        candidate = entry.getValue();
                                    }
                                } else {
                                    candidate = (RangeMapEntry) TreeRangeMap.this.entriesByLowerBound.get(r.lowerBound);
                                }
                                if (candidate != null && candidate.getKey().isConnected(SubRangeMap.this.subRange) && candidate.getKey().intersection(SubRangeMap.this.subRange).equals(r)) {
                                    return candidate.getValue();
                                }
                            }
                        }
                        return null;
                    }
                    return null;
                } catch (ClassCastException e) {
                    return null;
                }
            }

            public V remove(Object key) {
                V value = get(key);
                if (value == null) {
                    return null;
                }
                TreeRangeMap.this.remove((Range) key);
                return value;
            }

            public void clear() {
                SubRangeMap.this.clear();
            }

            /* access modifiers changed from: private */
            public boolean removeEntryIf(Predicate<? super Map.Entry<Range<K>, V>> predicate) {
                List<Range<K>> toRemove = Lists.newArrayList();
                for (Map.Entry<Range<K>, V> entry : entrySet()) {
                    if (predicate.apply(entry)) {
                        toRemove.add(entry.getKey());
                    }
                }
                for (Range<K> range : toRemove) {
                    TreeRangeMap.this.remove(range);
                }
                return !toRemove.isEmpty();
            }

            public Set<Range<K>> keySet() {
                return new Maps.KeySet<Range<K>, V>(this) {
                    public boolean remove(@NullableDecl Object o) {
                        return SubRangeMapAsMap.this.remove(o) != null;
                    }

                    public boolean retainAll(Collection<?> c) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.m84in(c)), Maps.keyFunction()));
                    }
                };
            }

            public Set<Map.Entry<Range<K>, V>> entrySet() {
                return new Maps.EntrySet<Range<K>, V>() {
                    /* access modifiers changed from: package-private */
                    public Map<Range<K>, V> map() {
                        return SubRangeMapAsMap.this;
                    }

                    public Iterator<Map.Entry<Range<K>, V>> iterator() {
                        return SubRangeMapAsMap.this.entryIterator();
                    }

                    public boolean retainAll(Collection<?> c) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.not(Predicates.m84in(c)));
                    }

                    public int size() {
                        return Iterators.size(iterator());
                    }

                    public boolean isEmpty() {
                        return !iterator().hasNext();
                    }
                };
            }

            /* access modifiers changed from: package-private */
            public Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                if (SubRangeMap.this.subRange.isEmpty()) {
                    return Iterators.emptyIterator();
                }
                final Iterator<RangeMapEntry<K, V>> backingItr = TreeRangeMap.this.entriesByLowerBound.tailMap((Cut) MoreObjects.firstNonNull((Cut) TreeRangeMap.this.entriesByLowerBound.floorKey(SubRangeMap.this.subRange.lowerBound), SubRangeMap.this.subRange.lowerBound), true).values().iterator();
                return new AbstractIterator<Map.Entry<Range<K>, V>>() {
                    /* access modifiers changed from: protected */
                    public Map.Entry<Range<K>, V> computeNext() {
                        while (backingItr.hasNext()) {
                            RangeMapEntry<K, V> entry = (RangeMapEntry) backingItr.next();
                            if (entry.getLowerBound().compareTo((Cut) SubRangeMap.this.subRange.upperBound) >= 0) {
                                return (Map.Entry) endOfData();
                            }
                            if (entry.getUpperBound().compareTo((Cut) SubRangeMap.this.subRange.lowerBound) > 0) {
                                return Maps.immutableEntry(entry.getKey().intersection(SubRangeMap.this.subRange), entry.getValue());
                            }
                        }
                        return (Map.Entry) endOfData();
                    }
                };
            }

            public Collection<V> values() {
                return new Maps.Values<Range<K>, V>(this) {
                    public boolean removeAll(Collection<?> c) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.m84in(c), Maps.valueFunction()));
                    }

                    public boolean retainAll(Collection<?> c) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.m84in(c)), Maps.valueFunction()));
                    }
                };
            }
        }
    }

    public boolean equals(@NullableDecl Object o) {
        if (o instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
        }
        return false;
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public String toString() {
        return this.entriesByLowerBound.values().toString();
    }
}
