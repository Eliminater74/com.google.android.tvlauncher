package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Map;

@GwtCompatible
public interface MapDifference<K, V> {

    boolean areEqual();

    Map<K, ValueDifference<V>> entriesDiffering();

    Map<K, V> entriesInCommon();

    Map<K, V> entriesOnlyOnLeft();

    Map<K, V> entriesOnlyOnRight();

    boolean equals(@NullableDecl Object obj);

    int hashCode();

    public interface ValueDifference<V> {
        boolean equals(@NullableDecl Object obj);

        int hashCode();

        V leftValue();

        V rightValue();
    }
}
