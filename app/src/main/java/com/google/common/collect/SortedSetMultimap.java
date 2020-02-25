package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;

@GwtCompatible
public interface SortedSetMultimap<K, V> extends SetMultimap<K, V> {
    Map<K, Collection<V>> asMap();

    SortedSet<V> get(@NullableDecl Object obj);

    @CanIgnoreReturnValue
    SortedSet<V> removeAll(@NullableDecl Object obj);

    @CanIgnoreReturnValue
    SortedSet<V> replaceValues(Object obj, Iterable iterable);

    Comparator<? super V> valueComparator();
}
