package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.Comparator;
import java.util.SortedSet;

@GwtCompatible
public final class Range<C extends Comparable> extends RangeGwtSerializationDependencies implements Predicate<C>, Serializable {
    private static final Range<Comparable> ALL = new Range<>(Cut.belowAll(), Cut.aboveAll());
    private static final long serialVersionUID = 0;
    final Cut<C> lowerBound;
    final Cut<C> upperBound;

    private Range(Cut<C> lowerBound2, Cut<C> upperBound2) {
        this.lowerBound = (Cut) Preconditions.checkNotNull(lowerBound2);
        this.upperBound = (Cut) Preconditions.checkNotNull(upperBound2);
        if (lowerBound2.compareTo((Cut) upperBound2) > 0 || lowerBound2 == Cut.aboveAll() || upperBound2 == Cut.belowAll()) {
            String valueOf = String.valueOf(toString(lowerBound2, upperBound2));
            throw new IllegalArgumentException(valueOf.length() != 0 ? "Invalid range: ".concat(valueOf) : new String("Invalid range: "));
        }
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> lowerBoundFn() {
        return LowerBoundFn.INSTANCE;
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> upperBoundFn() {
        return UpperBoundFn.INSTANCE;
    }

    static <C extends Comparable<?>> Ordering<Range<C>> rangeLexOrdering() {
        return RangeLexOrdering.INSTANCE;
    }

    static <C extends Comparable<?>> Range<C> create(Cut<C> lowerBound2, Cut<C> upperBound2) {
        return new Range<>(lowerBound2, upperBound2);
    }

    public static <C extends Comparable<?>> Range<C> open(C lower, C upper) {
        return create(Cut.aboveValue(lower), Cut.belowValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> closed(C lower, C upper) {
        return create(Cut.belowValue(lower), Cut.aboveValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C lower, C upper) {
        return create(Cut.belowValue(lower), Cut.belowValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C lower, C upper) {
        return create(Cut.aboveValue(lower), Cut.aboveValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> range(C lower, BoundType lowerType, C upper, BoundType upperType) {
        Preconditions.checkNotNull(lowerType);
        Preconditions.checkNotNull(upperType);
        return create(lowerType == BoundType.OPEN ? Cut.aboveValue(lower) : Cut.belowValue(lower), upperType == BoundType.OPEN ? Cut.belowValue(upper) : Cut.aboveValue(upper));
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C endpoint) {
        return create(Cut.belowAll(), Cut.belowValue(endpoint));
    }

    public static <C extends Comparable<?>> Range<C> atMost(C endpoint) {
        return create(Cut.belowAll(), Cut.aboveValue(endpoint));
    }

    public static <C extends Comparable<?>> Range<C> upTo(C endpoint, BoundType boundType) {
        int i = C16591.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
        if (i == 1) {
            return lessThan(endpoint);
        }
        if (i == 2) {
            return atMost(endpoint);
        }
        throw new AssertionError();
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C endpoint) {
        return create(Cut.aboveValue(endpoint), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C endpoint) {
        return create(Cut.belowValue(endpoint), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> downTo(C endpoint, BoundType boundType) {
        int i = C16591.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
        if (i == 1) {
            return greaterThan(endpoint);
        }
        if (i == 2) {
            return atLeast(endpoint);
        }
        throw new AssertionError();
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return ALL;
    }

    public static <C extends Comparable<?>> Range<C> singleton(C value) {
        return closed(value, value);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.Comparable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v2, resolved type: java.lang.Comparable} */
    /* JADX INFO: Multiple debug info for r0v1 java.util.Iterator<C>: [D('set' java.util.SortedSet<? extends C>), D('valueIterator' java.util.Iterator<C>)] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <C extends java.lang.Comparable<?>> com.google.common.collect.Range<C> encloseAll(java.lang.Iterable<C> r5) {
        /*
            com.google.common.base.Preconditions.checkNotNull(r5)
            boolean r0 = r5 instanceof java.util.SortedSet
            if (r0 == 0) goto L_0x002c
            java.util.SortedSet r0 = cast(r5)
            java.util.Comparator r1 = r0.comparator()
            com.google.common.collect.Ordering r2 = com.google.common.collect.Ordering.natural()
            boolean r2 = r2.equals(r1)
            if (r2 != 0) goto L_0x001b
            if (r1 != 0) goto L_0x002c
        L_0x001b:
            java.lang.Object r2 = r0.first()
            java.lang.Comparable r2 = (java.lang.Comparable) r2
            java.lang.Object r3 = r0.last()
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            com.google.common.collect.Range r2 = closed(r2, r3)
            return r2
        L_0x002c:
            java.util.Iterator r0 = r5.iterator()
            java.lang.Object r1 = r0.next()
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            java.lang.Object r1 = com.google.common.base.Preconditions.checkNotNull(r1)
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            r2 = r1
        L_0x003d:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0066
            java.lang.Object r3 = r0.next()
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            java.lang.Object r3 = com.google.common.base.Preconditions.checkNotNull(r3)
            java.lang.Comparable r3 = (java.lang.Comparable) r3
            com.google.common.collect.Ordering r4 = com.google.common.collect.Ordering.natural()
            java.lang.Object r4 = r4.min(r1, r3)
            r1 = r4
            java.lang.Comparable r1 = (java.lang.Comparable) r1
            com.google.common.collect.Ordering r4 = com.google.common.collect.Ordering.natural()
            java.lang.Object r4 = r4.max(r2, r3)
            r2 = r4
            java.lang.Comparable r2 = (java.lang.Comparable) r2
            goto L_0x003d
        L_0x0066:
            com.google.common.collect.Range r3 = closed(r1, r2)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Range.encloseAll(java.lang.Iterable):com.google.common.collect.Range");
    }

    private static String toString(Cut<?> lowerBound2, Cut<?> upperBound2) {
        StringBuilder sb = new StringBuilder(16);
        lowerBound2.describeAsLowerBound(sb);
        sb.append("..");
        upperBound2.describeAsUpperBound(sb);
        return sb.toString();
    }

    private static <T> SortedSet<T> cast(Iterable<T> iterable) {
        return (SortedSet) iterable;
    }

    static int compareOrThrow(Comparable left, Comparable right) {
        return left.compareTo(right);
    }

    public boolean hasLowerBound() {
        return this.lowerBound != Cut.belowAll();
    }

    public C lowerEndpoint() {
        return this.lowerBound.endpoint();
    }

    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }

    public boolean hasUpperBound() {
        return this.upperBound != Cut.aboveAll();
    }

    public C upperEndpoint() {
        return this.upperBound.endpoint();
    }

    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }

    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }

    public boolean contains(C value) {
        Preconditions.checkNotNull(value);
        return this.lowerBound.isLessThan(value) && !this.upperBound.isLessThan(value);
    }

    @Deprecated
    public boolean apply(C input) {
        return contains(input);
    }

    public boolean containsAll(Iterable<? extends C> values) {
        if (Iterables.isEmpty(values)) {
            return true;
        }
        if (values instanceof SortedSet) {
            SortedSet<? extends C> set = cast(values);
            Comparator<?> comparator = set.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                if (!contains((Comparable) set.first()) || !contains((Comparable) set.last())) {
                    return false;
                }
                return true;
            }
        }
        for (C value : values) {
            if (!contains(value)) {
                return false;
            }
        }
        return true;
    }

    public boolean encloses(Range<C> other) {
        return this.lowerBound.compareTo(other.lowerBound) <= 0 && this.upperBound.compareTo(other.upperBound) >= 0;
    }

    public boolean isConnected(Range<C> other) {
        return this.lowerBound.compareTo(other.upperBound) <= 0 && other.lowerBound.compareTo(this.upperBound) <= 0;
    }

    public Range<C> intersection(Range<C> connectedRange) {
        int lowerCmp = this.lowerBound.compareTo((Cut) connectedRange.lowerBound);
        int upperCmp = this.upperBound.compareTo((Cut) connectedRange.upperBound);
        if (lowerCmp >= 0 && upperCmp <= 0) {
            return this;
        }
        if (lowerCmp <= 0 && upperCmp >= 0) {
            return connectedRange;
        }
        return create(lowerCmp >= 0 ? this.lowerBound : connectedRange.lowerBound, upperCmp <= 0 ? this.upperBound : connectedRange.upperBound);
    }

    public Range<C> gap(Range<C> otherRange) {
        boolean isThisFirst = this.lowerBound.compareTo(otherRange.lowerBound) < 0;
        return create((isThisFirst ? this : otherRange).upperBound, (isThisFirst ? otherRange : this).lowerBound);
    }

    public Range<C> span(Range<C> other) {
        int lowerCmp = this.lowerBound.compareTo((Cut) other.lowerBound);
        int upperCmp = this.upperBound.compareTo((Cut) other.upperBound);
        if (lowerCmp <= 0 && upperCmp >= 0) {
            return this;
        }
        if (lowerCmp >= 0 && upperCmp <= 0) {
            return other;
        }
        return create(lowerCmp <= 0 ? this.lowerBound : other.lowerBound, upperCmp >= 0 ? this.upperBound : other.upperBound);
    }

    public Range<C> canonical(DiscreteDomain<C> domain) {
        Preconditions.checkNotNull(domain);
        Cut<C> lower = this.lowerBound.canonical(domain);
        Cut<C> upper = this.upperBound.canonical(domain);
        return (lower == this.lowerBound && upper == this.upperBound) ? this : create(lower, upper);
    }

    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof Range)) {
            return false;
        }
        Range<?> other = (Range) object;
        if (!this.lowerBound.equals(other.lowerBound) || !this.upperBound.equals(other.upperBound)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.lowerBound.hashCode() * 31) + this.upperBound.hashCode();
    }

    public String toString() {
        return toString(this.lowerBound, this.upperBound);
    }

    /* access modifiers changed from: package-private */
    public Object readResolve() {
        if (equals(ALL)) {
            return all();
        }
        return this;
    }

    static class LowerBoundFn implements Function<Range, Cut> {
        static final LowerBoundFn INSTANCE = new LowerBoundFn();

        LowerBoundFn() {
        }

        public Cut apply(Range range) {
            return range.lowerBound;
        }
    }

    static class UpperBoundFn implements Function<Range, Cut> {
        static final UpperBoundFn INSTANCE = new UpperBoundFn();

        UpperBoundFn() {
        }

        public Cut apply(Range range) {
            return range.upperBound;
        }
    }

    /* renamed from: com.google.common.collect.Range$1 */
    static /* synthetic */ class C16591 {
        static final /* synthetic */ int[] $SwitchMap$com$google$common$collect$BoundType = new int[BoundType.values().length];

        static {
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.OPEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$common$collect$BoundType[BoundType.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    private static class RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
        static final Ordering<Range<?>> INSTANCE = new RangeLexOrdering();
        private static final long serialVersionUID = 0;

        private RangeLexOrdering() {
        }

        public /* bridge */ /* synthetic */ int compare(Object obj, Object obj2) {
            return compare((Range<?>) ((Range) obj), (Range<?>) ((Range) obj2));
        }

        public int compare(Range<?> left, Range<?> right) {
            return ComparisonChain.start().compare(left.lowerBound, right.lowerBound).compare(left.upperBound, right.upperBound).result();
        }
    }
}
