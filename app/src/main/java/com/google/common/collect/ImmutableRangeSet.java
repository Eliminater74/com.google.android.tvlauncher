package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.concurrent.LazyInit;

import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@GwtIncompatible
@Beta
public final class ImmutableRangeSet<C extends Comparable> extends AbstractRangeSet<C> implements Serializable {
    private static final ImmutableRangeSet<Comparable<?>> ALL = new ImmutableRangeSet<>(ImmutableList.m108of(Range.all()));
    private static final ImmutableRangeSet<Comparable<?>> EMPTY = new ImmutableRangeSet<>(ImmutableList.m107of());
    /* access modifiers changed from: private */
    public final transient ImmutableList<Range<C>> ranges;
    @LazyInit
    private transient ImmutableRangeSet<C> complement;

    ImmutableRangeSet(ImmutableList<Range<C>> ranges2) {
        this.ranges = ranges2;
    }

    private ImmutableRangeSet(ImmutableList<Range<C>> ranges2, ImmutableRangeSet<C> complement2) {
        this.ranges = ranges2;
        this.complement = complement2;
    }

    /* renamed from: of */
    public static <C extends Comparable> ImmutableRangeSet<C> m147of() {
        return EMPTY;
    }

    /* renamed from: of */
    public static <C extends Comparable> ImmutableRangeSet<C> m148of(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return m147of();
        }
        if (range.equals(Range.all())) {
            return all();
        }
        return new ImmutableRangeSet<>(ImmutableList.m108of(range));
    }

    static <C extends Comparable> ImmutableRangeSet<C> all() {
        return ALL;
    }

    public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
        Preconditions.checkNotNull(rangeSet);
        if (rangeSet.isEmpty()) {
            return m147of();
        }
        if (rangeSet.encloses(Range.all())) {
            return all();
        }
        if (rangeSet instanceof ImmutableRangeSet) {
            ImmutableRangeSet<C> immutableRangeSet = (ImmutableRangeSet) rangeSet;
            if (!immutableRangeSet.isPartialView()) {
                return immutableRangeSet;
            }
        }
        return new ImmutableRangeSet<>(ImmutableList.copyOf((Collection) rangeSet.asRanges()));
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> copyOf(Iterable<Range<C>> ranges2) {
        return new Builder().addAll(ranges2).build();
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> unionOf(Iterable<Range<C>> ranges2) {
        return copyOf(TreeRangeSet.create(ranges2));
    }

    public static <C extends Comparable<?>> Builder<C> builder() {
        return new Builder<>();
    }

    public /* bridge */ /* synthetic */ void clear() {
        super.clear();
    }

    public /* bridge */ /* synthetic */ boolean contains(Comparable comparable) {
        return super.contains(comparable);
    }

    public /* bridge */ /* synthetic */ boolean enclosesAll(RangeSet rangeSet) {
        return super.enclosesAll(rangeSet);
    }

    public /* bridge */ /* synthetic */ boolean enclosesAll(Iterable iterable) {
        return super.enclosesAll(iterable);
    }

    public /* bridge */ /* synthetic */ boolean equals(@NullableDecl Object obj) {
        return super.equals(obj);
    }

    public boolean intersects(Range<C> otherRange) {
        int ceilingIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), otherRange.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        if (ceilingIndex < this.ranges.size() && this.ranges.get(ceilingIndex).isConnected(otherRange) && !this.ranges.get(ceilingIndex).intersection(otherRange).isEmpty()) {
            return true;
        }
        if (ceilingIndex <= 0 || !this.ranges.get(ceilingIndex - 1).isConnected(otherRange) || this.ranges.get(ceilingIndex - 1).intersection(otherRange).isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean encloses(Range<C> otherRange) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), otherRange.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        return index != -1 && this.ranges.get(index).encloses(otherRange);
    }

    public Range<C> rangeContaining(C value) {
        int index = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(value), Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (index == -1) {
            return null;
        }
        Range<C> range = this.ranges.get(index);
        if (range.contains(value)) {
            return range;
        }
        return null;
    }

    public Range<C> span() {
        if (!this.ranges.isEmpty()) {
            Cut<C> cut = this.ranges.get(0).lowerBound;
            ImmutableList<Range<C>> immutableList = this.ranges;
            return Range.create(cut, immutableList.get(immutableList.size() - 1).upperBound);
        }
        throw new NoSuchElementException();
    }

    public boolean isEmpty() {
        return this.ranges.isEmpty();
    }

    @Deprecated
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void addAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void addAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void removeAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    public void removeAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    public ImmutableSet<Range<C>> asRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableSet.m149of();
        }
        return new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering());
    }

    public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
        if (this.ranges.isEmpty()) {
            return ImmutableSet.m149of();
        }
        return new RegularImmutableSortedSet(this.ranges.reverse(), Range.rangeLexOrdering().reverse());
    }

    public ImmutableRangeSet<C> complement() {
        ImmutableRangeSet<C> result = this.complement;
        if (result != null) {
            return result;
        }
        if (this.ranges.isEmpty()) {
            ImmutableRangeSet<C> all = all();
            this.complement = all;
            return all;
        } else if (this.ranges.size() != 1 || !this.ranges.get(0).equals(Range.all())) {
            ImmutableRangeSet<C> result2 = new ImmutableRangeSet<>(new ComplementRanges(), this);
            this.complement = result2;
            return result2;
        } else {
            ImmutableRangeSet<C> of = m147of();
            this.complement = of;
            return of;
        }
    }

    public ImmutableRangeSet<C> union(RangeSet<C> other) {
        return unionOf(Iterables.concat(asRanges(), other.asRanges()));
    }

    public ImmutableRangeSet<C> intersection(RangeSet<C> other) {
        RangeSet<C> copy = TreeRangeSet.create(this);
        copy.removeAll(other.complement());
        return copyOf(copy);
    }

    public ImmutableRangeSet<C> difference(RangeSet<C> other) {
        RangeSet<C> copy = TreeRangeSet.create(this);
        copy.removeAll(other);
        return copyOf(copy);
    }

    private ImmutableList<Range<C>> intersectRanges(final Range<C> range) {
        final int fromIndex;
        int toIndex;
        if (this.ranges.isEmpty() || range.isEmpty()) {
            return ImmutableList.m107of();
        }
        if (range.encloses(span())) {
            return this.ranges;
        }
        if (range.hasLowerBound()) {
            fromIndex = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        } else {
            fromIndex = 0;
        }
        if (range.hasUpperBound()) {
            toIndex = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        } else {
            toIndex = this.ranges.size();
        }
        final int length = toIndex - fromIndex;
        if (length == 0) {
            return ImmutableList.m107of();
        }
        return new ImmutableList<Range<C>>() {
            public int size() {
                return length;
            }

            public Range<C> get(int index) {
                Preconditions.checkElementIndex(index, length);
                if (index == 0 || index == length - 1) {
                    return ((Range) ImmutableRangeSet.this.ranges.get(fromIndex + index)).intersection(range);
                }
                return (Range) ImmutableRangeSet.this.ranges.get(fromIndex + index);
            }

            /* access modifiers changed from: package-private */
            public boolean isPartialView() {
                return true;
            }
        };
    }

    public ImmutableRangeSet<C> subRangeSet(Range<C> range) {
        if (!isEmpty()) {
            Range<C> span = span();
            if (range.encloses(span)) {
                return this;
            }
            if (range.isConnected(span)) {
                return new ImmutableRangeSet<>(intersectRanges(range));
            }
        }
        return m147of();
    }

    public ImmutableSortedSet<C> asSet(DiscreteDomain<C> domain) {
        Preconditions.checkNotNull(domain);
        if (isEmpty()) {
            return ImmutableSortedSet.m187of();
        }
        Range<C> span = span().canonical(domain);
        if (span.hasLowerBound()) {
            if (!span.hasUpperBound()) {
                try {
                    domain.maxValue();
                } catch (NoSuchElementException e) {
                    throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
                }
            }
            return new AsSet(domain);
        }
        throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return this.ranges.isPartialView();
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return new SerializedForm(this.ranges);
    }

    private static class AsSetSerializedForm<C extends Comparable> implements Serializable {
        private final DiscreteDomain<C> domain;
        private final ImmutableList<Range<C>> ranges;

        AsSetSerializedForm(ImmutableList<Range<C>> ranges2, DiscreteDomain<C> domain2) {
            this.ranges = ranges2;
            this.domain = domain2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return new ImmutableRangeSet(this.ranges).asSet(this.domain);
        }
    }

    public static class Builder<C extends Comparable<?>> {
        private final List<Range<C>> ranges = Lists.newArrayList();

        @CanIgnoreReturnValue
        public Builder<C> add(Range<C> range) {
            Preconditions.checkArgument(!range.isEmpty(), "range must not be empty, but was %s", range);
            this.ranges.add(range);
            return this;
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(RangeSet<C> ranges2) {
            return addAll(ranges2.asRanges());
        }

        @CanIgnoreReturnValue
        public Builder<C> addAll(Iterable<Range<C>> ranges2) {
            for (Range<C> range : ranges2) {
                add(range);
            }
            return this;
        }

        public ImmutableRangeSet<C> build() {
            ImmutableList.Builder<Range<C>> mergedRangesBuilder = new ImmutableList.Builder<>(this.ranges.size());
            Collections.sort(this.ranges, Range.rangeLexOrdering());
            PeekingIterator<Range<C>> peekingItr = Iterators.peekingIterator(this.ranges.iterator());
            while (peekingItr.hasNext()) {
                Range<C> range = peekingItr.next();
                while (peekingItr.hasNext()) {
                    Range<C> nextRange = peekingItr.peek();
                    if (!range.isConnected(nextRange)) {
                        break;
                    }
                    Preconditions.checkArgument(range.intersection(nextRange).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", range, nextRange);
                    range = range.span(peekingItr.next());
                }
                mergedRangesBuilder.add((Object) range);
            }
            ImmutableList<Range<C>> mergedRanges = mergedRangesBuilder.build();
            if (mergedRanges.isEmpty()) {
                return ImmutableRangeSet.m147of();
            }
            if (mergedRanges.size() != 1 || !((Range) Iterables.getOnlyElement(mergedRanges)).equals(Range.all())) {
                return new ImmutableRangeSet<>(mergedRanges);
            }
            return ImmutableRangeSet.all();
        }
    }

    private static final class SerializedForm<C extends Comparable> implements Serializable {
        private final ImmutableList<Range<C>> ranges;

        SerializedForm(ImmutableList<Range<C>> ranges2) {
            this.ranges = ranges2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            if (this.ranges.isEmpty()) {
                return ImmutableRangeSet.m147of();
            }
            if (this.ranges.equals(ImmutableList.m108of(Range.all()))) {
                return ImmutableRangeSet.all();
            }
            return new ImmutableRangeSet(this.ranges);
        }
    }

    private final class ComplementRanges extends ImmutableList<Range<C>> {
        private final boolean positiveBoundedAbove;
        private final boolean positiveBoundedBelow;
        private final int size;

        ComplementRanges() {
            this.positiveBoundedBelow = ((Range) ImmutableRangeSet.this.ranges.get(0)).hasLowerBound();
            this.positiveBoundedAbove = ((Range) Iterables.getLast(ImmutableRangeSet.this.ranges)).hasUpperBound();
            int size2 = ImmutableRangeSet.this.ranges.size() - 1;
            size2 = this.positiveBoundedBelow ? size2 + 1 : size2;
            this.size = this.positiveBoundedAbove ? size2 + 1 : size2;
        }

        public int size() {
            return this.size;
        }

        public Range<C> get(int index) {
            Cut<C> lowerBound;
            Cut<C> upperBound;
            Preconditions.checkElementIndex(index, this.size);
            if (this.positiveBoundedBelow) {
                lowerBound = index == 0 ? Cut.belowAll() : ((Range) ImmutableRangeSet.this.ranges.get(index - 1)).upperBound;
            } else {
                lowerBound = ((Range) ImmutableRangeSet.this.ranges.get(index)).upperBound;
            }
            if (!this.positiveBoundedAbove || index != this.size - 1) {
                upperBound = ((Range) ImmutableRangeSet.this.ranges.get((this.positiveBoundedBelow ^ true ? 1 : 0) + index)).lowerBound;
            } else {
                upperBound = Cut.aboveAll();
            }
            return Range.create(lowerBound, upperBound);
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return true;
        }
    }

    private final class AsSet extends ImmutableSortedSet<C> {
        /* access modifiers changed from: private */
        public final DiscreteDomain<C> domain;
        @MonotonicNonNullDecl
        private transient Integer size;

        AsSet(DiscreteDomain<C> domain2) {
            super(Ordering.natural());
            this.domain = domain2;
        }

        public int size() {
            Integer result = this.size;
            if (result == null) {
                long total = 0;
                UnmodifiableIterator it = ImmutableRangeSet.this.ranges.iterator();
                while (it.hasNext()) {
                    total += (long) ContiguousSet.create((Range) it.next(), this.domain).size();
                    if (total >= 2147483647L) {
                        break;
                    }
                }
                Integer valueOf = Integer.valueOf(Ints.saturatedCast(total));
                this.size = valueOf;
                result = valueOf;
            }
            return result.intValue();
        }

        public UnmodifiableIterator<C> iterator() {
            return new AbstractIterator<C>() {
                final Iterator<Range<C>> rangeItr = ImmutableRangeSet.this.ranges.iterator();
                Iterator<C> elemItr = Iterators.emptyIterator();

                /* access modifiers changed from: protected */
                public C computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (!this.rangeItr.hasNext()) {
                            return (Comparable) endOfData();
                        }
                        this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.this.domain).iterator();
                    }
                    return (Comparable) this.elemItr.next();
                }
            };
        }

        @GwtIncompatible("NavigableSet")
        public UnmodifiableIterator<C> descendingIterator() {
            return new AbstractIterator<C>() {
                final Iterator<Range<C>> rangeItr = ImmutableRangeSet.this.ranges.reverse().iterator();
                Iterator<C> elemItr = Iterators.emptyIterator();

                /* access modifiers changed from: protected */
                public C computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (!this.rangeItr.hasNext()) {
                            return (Comparable) endOfData();
                        }
                        this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.this.domain).descendingIterator();
                    }
                    return (Comparable) this.elemItr.next();
                }
            };
        }

        /* access modifiers changed from: package-private */
        public ImmutableSortedSet<C> subSet(Range<C> range) {
            return ImmutableRangeSet.this.subRangeSet((Range) range).asSet(this.domain);
        }

        /* access modifiers changed from: package-private */
        public ImmutableSortedSet<C> headSetImpl(C toElement, boolean inclusive) {
            return subSet(Range.upTo(toElement, BoundType.forBoolean(inclusive)));
        }

        /* access modifiers changed from: package-private */
        public ImmutableSortedSet<C> subSetImpl(C fromElement, boolean fromInclusive, C toElement, boolean toInclusive) {
            if (fromInclusive || toInclusive || Range.compareOrThrow(fromElement, toElement) != 0) {
                return subSet(Range.range(fromElement, BoundType.forBoolean(fromInclusive), toElement, BoundType.forBoolean(toInclusive)));
            }
            return ImmutableSortedSet.m187of();
        }

        /* access modifiers changed from: package-private */
        public ImmutableSortedSet<C> tailSetImpl(C fromElement, boolean inclusive) {
            return subSet(Range.downTo(fromElement, BoundType.forBoolean(inclusive)));
        }

        public boolean contains(@NullableDecl Object o) {
            if (o == null) {
                return false;
            }
            try {
                return ImmutableRangeSet.this.contains((Comparable) o);
            } catch (ClassCastException e) {
                return false;
            }
        }

        /* access modifiers changed from: package-private */
        public int indexOf(Object target) {
            if (!contains(target)) {
                return -1;
            }
            C c = (Comparable) target;
            long total = 0;
            UnmodifiableIterator it = ImmutableRangeSet.this.ranges.iterator();
            while (it.hasNext()) {
                Range<C> range = (Range) it.next();
                if (range.contains(c)) {
                    return Ints.saturatedCast(((long) ContiguousSet.create(range, this.domain).indexOf(c)) + total);
                }
                total += (long) ContiguousSet.create(range, this.domain).size();
            }
            throw new AssertionError("impossible");
        }

        /* access modifiers changed from: package-private */
        public ImmutableSortedSet<C> createDescendingSet() {
            return new DescendingImmutableSortedSet(this);
        }

        /* access modifiers changed from: package-private */
        public boolean isPartialView() {
            return ImmutableRangeSet.this.ranges.isPartialView();
        }

        public String toString() {
            return ImmutableRangeSet.this.ranges.toString();
        }

        /* access modifiers changed from: package-private */
        public Object writeReplace() {
            return new AsSetSerializedForm(ImmutableRangeSet.this.ranges, this.domain);
        }
    }
}
