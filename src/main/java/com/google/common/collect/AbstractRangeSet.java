package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.lang.Comparable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@GwtIncompatible
abstract class AbstractRangeSet<C extends Comparable> implements RangeSet<C> {
    public abstract boolean encloses(Range<C> range);

    public abstract Range<C> rangeContaining(C c);

    AbstractRangeSet() {
    }

    public boolean contains(C value) {
        return rangeContaining(value) != null;
    }

    public boolean isEmpty() {
        return asRanges().isEmpty();
    }

    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        remove(Range.all());
    }

    public boolean enclosesAll(RangeSet<C> other) {
        return enclosesAll(other.asRanges());
    }

    public boolean enclosesAll(Iterable<Range<C>> ranges) {
        for (Range<C> range : ranges) {
            if (!encloses(range)) {
                return false;
            }
        }
        return true;
    }

    public void addAll(RangeSet<C> other) {
        addAll(other.asRanges());
    }

    public void addAll(Iterable<Range<C>> ranges) {
        for (Range<C> range : ranges) {
            add(range);
        }
    }

    public void removeAll(RangeSet<C> other) {
        removeAll(other.asRanges());
    }

    public void removeAll(Iterable<Range<C>> ranges) {
        for (Range<C> range : ranges) {
            remove(range);
        }
    }

    public boolean intersects(Range<C> otherRange) {
        return !subRangeSet(otherRange).isEmpty();
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof RangeSet) {
            return asRanges().equals(((RangeSet) obj).asRanges());
        }
        return false;
    }

    public final int hashCode() {
        return asRanges().hashCode();
    }

    public final String toString() {
        return asRanges().toString();
    }
}
