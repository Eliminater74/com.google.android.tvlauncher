package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.SortedLists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.Serializable;
import java.lang.Comparable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
@Beta
public class ImmutableRangeMap<K extends Comparable<?>, V> implements RangeMap<K, V>, Serializable {
    private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap<>(ImmutableList.m107of(), ImmutableList.m107of());
    private static final long serialVersionUID = 0;
    /* access modifiers changed from: private */
    public final transient ImmutableList<Range<K>> ranges;
    private final transient ImmutableList<V> values;

    /* renamed from: of */
    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> m145of() {
        return EMPTY;
    }

    /* renamed from: of */
    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> m146of(Range<K> range, V value) {
        return new ImmutableRangeMap<>(ImmutableList.m108of(range), ImmutableList.m108of(value));
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> rangeMap) {
        if (rangeMap instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap) rangeMap;
        }
        Map<Range<K>, ? extends V> map = rangeMap.asMapOfRanges();
        ImmutableList.Builder<Range<K>> rangesBuilder = new ImmutableList.Builder<>(map.size());
        ImmutableList.Builder<V> valuesBuilder = new ImmutableList.Builder<>(map.size());
        for (Map.Entry<Range<K>, ? extends V> entry : map.entrySet()) {
            rangesBuilder.add((Object) entry.getKey());
            valuesBuilder.add((Object) entry.getValue());
        }
        return new ImmutableRangeMap<>(rangesBuilder.build(), valuesBuilder.build());
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public static final class Builder<K extends Comparable<?>, V> {
        private final List<Map.Entry<Range<K>, V>> entries = Lists.newArrayList();

        @CanIgnoreReturnValue
        public Builder<K, V> put(Range<K> range, V value) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(value);
            Preconditions.checkArgument(!range.isEmpty(), "Range must not be empty, but was %s", range);
            this.entries.add(Maps.immutableEntry(range, value));
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<K, V> putAll(RangeMap<K, ? extends V> rangeMap) {
            for (Map.Entry<Range<K>, ? extends V> entry : rangeMap.asMapOfRanges().entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public ImmutableRangeMap<K, V> build() {
            Collections.sort(this.entries, Range.rangeLexOrdering().onKeys());
            ImmutableList.Builder<Range<K>> rangesBuilder = new ImmutableList.Builder<>(this.entries.size());
            ImmutableList.Builder<V> valuesBuilder = new ImmutableList.Builder<>(this.entries.size());
            for (int i = 0; i < this.entries.size(); i++) {
                Range<K> range = (Range) this.entries.get(i).getKey();
                if (i > 0) {
                    Range<K> prevRange = (Range) this.entries.get(i - 1).getKey();
                    if (range.isConnected(prevRange) && !range.intersection(prevRange).isEmpty()) {
                        String valueOf = String.valueOf(prevRange);
                        String valueOf2 = String.valueOf(range);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47 + String.valueOf(valueOf2).length());
                        sb.append("Overlapping ranges: range ");
                        sb.append(valueOf);
                        sb.append(" overlaps with entry ");
                        sb.append(valueOf2);
                        throw new IllegalArgumentException(sb.toString());
                    }
                }
                rangesBuilder.add((Object) range);
                valuesBuilder.add(this.entries.get(i).getValue());
            }
            return new ImmutableRangeMap<>(rangesBuilder.build(), valuesBuilder.build());
        }
    }

    ImmutableRangeMap(ImmutableList<Range<K>> ranges2, ImmutableList<V> values2) {
        this.ranges = ranges2;
        this.values = values2;
    }

    @NullableDecl
    public V get(K key) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (index != -1 && this.ranges.get(index).contains(key)) {
            return this.values.get(index);
        }
        return null;
    }

    @NullableDecl
    public Map.Entry<Range<K>, V> getEntry(K key) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(key), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (index == -1) {
            return null;
        }
        Range<K> range = this.ranges.get(index);
        if (range.contains(key)) {
            return Maps.immutableEntry(range, this.values.get(index));
        }
        return null;
    }

    public Range<K> span() {
        if (!this.ranges.isEmpty()) {
            ImmutableList<Range<K>> immutableList = this.ranges;
            return Range.create(this.ranges.get(0).lowerBound, immutableList.get(immutableList.size() - 1).upperBound);
        }
        throw new NoSuchElementException();
    }

    @Deprecated
    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void putCoalescing(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.m126of();
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet<>(this.ranges, Range.rangeLexOrdering()), this.values);
    }

    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableMap.m126of();
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet<>(this.ranges.reverse(), Range.rangeLexOrdering().reverse()), this.values.reverse());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E>
     arg types: [int, int]
     candidates:
      com.google.common.collect.ImmutableList.subList(int, int):java.util.List
      ClspMth{java.util.List.subList(int, int):java.util.List<E>}
      com.google.common.collect.ImmutableList.subList(int, int):com.google.common.collect.ImmutableList<E> */
    public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
        if (((Range) Preconditions.checkNotNull(range)).isEmpty()) {
            return m145of();
        }
        if (this.ranges.isEmpty() || range.encloses(span())) {
            return this;
        }
        int lowerIndex = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        int upperIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        if (lowerIndex >= upperIndex) {
            return m145of();
        }
        final int off = lowerIndex;
        final int len = upperIndex - lowerIndex;
        final Range<K> range2 = range;
        return new ImmutableRangeMap<K, V>(this, new ImmutableList<Range<K>>() {
            public int size() {
                return len;
            }

            public Range<K> get(int index) {
                Preconditions.checkElementIndex(index, len);
                if (index == 0 || index == len - 1) {
                    return ((Range) ImmutableRangeMap.this.ranges.get(off + index)).intersection(range);
                }
                return (Range) ImmutableRangeMap.this.ranges.get(off + index);
            }

            /* access modifiers changed from: package-private */
            public boolean isPartialView() {
                return true;
            }
        }, this.values.subList(lowerIndex, upperIndex)) {
            public ImmutableRangeMap<K, V> subRangeMap(Range<K> subRange) {
                if (range2.isConnected(subRange)) {
                    return this.subRangeMap((Range) subRange.intersection(range2));
                }
                return ImmutableRangeMap.m145of();
            }
        };
    }

    public int hashCode() {
        return asMapOfRanges().hashCode();
    }

    public boolean equals(@NullableDecl Object o) {
        if (o instanceof RangeMap) {
            return asMapOfRanges().equals(((RangeMap) o).asMapOfRanges());
        }
        return false;
    }

    public String toString() {
        return asMapOfRanges().toString();
    }

    private static class SerializedForm<K extends Comparable<?>, V> implements Serializable {
        private static final long serialVersionUID = 0;
        private final ImmutableMap<Range<K>, V> mapOfRanges;

        SerializedForm(ImmutableMap<Range<K>, V> mapOfRanges2) {
            this.mapOfRanges = mapOfRanges2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            if (this.mapOfRanges.isEmpty()) {
                return ImmutableRangeMap.m145of();
            }
            return createRangeMap();
        }

        /* access modifiers changed from: package-private */
        public Object createRangeMap() {
            Builder<K, V> builder = new Builder<>();
            UnmodifiableIterator<Map.Entry<Range<K>, V>> it = this.mapOfRanges.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Range<K>, V> entry = it.next();
                builder.put(entry.getKey(), entry.getValue());
            }
            return builder.build();
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(asMapOfRanges());
    }
}
