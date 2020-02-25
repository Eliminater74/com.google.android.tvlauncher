package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Set;

@GwtIncompatible
@Beta
public interface RangeSet<C extends Comparable> {
    void add(Range<C> range);

    void addAll(RangeSet rangeSet);

    void addAll(Iterable<Range<C>> iterable);

    Set<Range<C>> asDescendingSetOfRanges();

    Set<Range<C>> asRanges();

    void clear();

    RangeSet<C> complement();

    boolean contains(C c);

    boolean encloses(Range<C> range);

    boolean enclosesAll(RangeSet rangeSet);

    boolean enclosesAll(Iterable<Range<C>> iterable);

    boolean equals(@NullableDecl Object obj);

    int hashCode();

    boolean intersects(Range<C> range);

    boolean isEmpty();

    Range<C> rangeContaining(C c);

    void remove(Range<C> range);

    void removeAll(RangeSet rangeSet);

    void removeAll(Iterable<Range<C>> iterable);

    Range<C> span();

    RangeSet<C> subRangeSet(Range<C> range);

    String toString();
}
